/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebrce.integration.dao.impl;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Service;

import it.csi.pbandi.pbservizit.business.intf.ErrorConstants;
import it.csi.pbandi.pbservizit.dto.profilazione.Beneficiario;
import it.csi.pbandi.pbservizit.dto.profilazione.Ruolo;
import it.csi.pbandi.pbservizit.dto.profilazione.UserInfoDTO;
import it.csi.pbandi.pbservizit.exception.DaoException;
import it.csi.pbandi.pbservizit.exception.UtenteException;
import it.csi.pbandi.pbservizit.integration.dao.ProfilazioneDAO;
import it.csi.pbandi.pbservizit.integration.dao.impl.rowmapper.BeanRowMapper;
import it.csi.pbandi.pbservizit.security.BeneficiarioSec;
import it.csi.pbandi.pbservizit.security.UserInfoSec;
import it.csi.pbandi.pbwebrce.dto.profilazione.BeneficiarioDTO;
import it.csi.pbandi.pbwebrce.integration.dao.InizializzazioneDAO;
import it.csi.pbandi.pbwebrce.integration.dao.impl.rowmapper.affidamenti.BeneficiarioRowMapper;
import it.csi.pbandi.pbwebrce.integration.vo.affidamenti.BeneficiarioVO;
import it.csi.pbandi.pbwebrce.util.BeanUtil;
import it.csi.pbandi.pbwebrce.util.Constants;
import it.csi.pbandi.pbwebrce.util.FileSqlUtil;

@Service
public class InizializzazioneDAOImpl extends JdbcDaoSupport implements InizializzazioneDAO {

	private Logger LOG = Logger.getLogger(Constants.COMPONENT_NAME);

	@Autowired
	ProfilazioneDAO profilazioneDao;

	@Autowired
	protected FileSqlUtil fileSqlUtil;

	@Autowired
	protected BeanUtil beanUtil;

	@Autowired
	public InizializzazioneDAOImpl(DataSource dataSource) {
		setDataSource(dataSource);
	}

	@Override
	public UserInfoSec completaDatiUtente(Long idPrj, Long idSg, Long idSgBen, Long idU, String role,
			String taskIdentity, String taskName, String wkfAction, HttpServletRequest req) throws UtenteException {
		String prf = "[InizializzazioneDAOImpl::completaDatiUtente]";
		LOG.info(prf + " BEGIN");

		LOG.info(prf + " idPrj=" + idPrj + ", idSg=" + idSg + ", idSgBen=" + idSgBen + ", idU=" + idU);
		LOG.info(prf + " role=" + role + ", taskIdentity=" + taskIdentity + ", taskName=" + taskName + ", wkfAction="
				+ wkfAction);

//	miniAppUrl: ----> ../pbandiwebrcesrv2/shib/HomePage.do?
//		wkfAction=StartTaskNeoFlux
//		taskName=Gestione+affidamenti
//		taskIdentity=GEST-AFFIDAMENTI
//		idSg=2139302
//		idPrj=19233
//		idSgBen=3499
//		role=ISTR-AFFIDAMENTI
//		idU=25846

		HttpSession session = req.getSession();
		UserInfoSec userInfo2 = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
		LOG.info(prf + "userInfo=" + userInfo2);

		if (userInfo2 == null) {
			LOG.warn(prf + "userInfo in sessione null");
			throw new UtenteException("Utente in sessione non valido");
		}

		UserInfoSec userInfoSec = null;
		try {

			// TODO PK tracciamento ??
			userInfoSec = inizializza(idPrj, idSg, idSgBen, idU, role, taskIdentity, taskName, wkfAction, req,
					userInfo2);

			session.setAttribute(Constants.USERINFO_SESSIONATTR, userInfoSec);
			LOG.info(prf + "user=" + userInfoSec);

		} catch (Exception e) {
			LOG.error(prf + " Exception " + e.getMessage());
			e.printStackTrace();
		}

		LOG.info(prf + " END");
		return userInfoSec;
	}

	private UserInfoSec inizializza(Long idPrj, Long idSg, Long idSgBen, Long idU, String role, String taskIdentity,
			String taskName, String wkfAction, HttpServletRequest req, UserInfoSec userInfo2)
			throws UtenteException, DaoException {
		String prf = "[InizializzazioneDAOImpl::inizializza]";
		LOG.info(prf + " BEGIN");

		UserInfoDTO userInfoDTO = profilazioneDao.getInfoUtente(userInfo2.getCodFisc(), userInfo2.getNome(),
				userInfo2.getCognome());
		LOG.info(prf + " userInfoDTO=" + userInfoDTO);

		// verifico che utente sia presente sul DB
		if (userInfoDTO == null) {
			LOG.warn(prf + "Utente[" + idU + " - " + userInfo2.getCodFisc() + "] non censito in PBANDI");
			UtenteException ue = new UtenteException(ErrorConstants.ERRORE_UTENTE_NON_AUTORIZZATO);
			throw ue;
		}

		// verifico che parametro idU corrisponda all'idUtente in sessione
		if (Long.compare(idU, userInfoDTO.getIdUtente().longValue()) == 0) {
			LOG.debug(prf + " idUtente in sessione (" + userInfoDTO.getIdUtente() + ") corretto");
		} else {

			LOG.warn(prf + " idUtente in sessione (" + userInfoDTO.getIdUtente() + ") diverso da quello in request ("
					+ idU + ")");
			UtenteException ue = new UtenteException(ErrorConstants.ERRORE_UTENTE_NON_AUTORIZZATO);
			throw ue;
		}

		// verifico che parametro idS corrisponda all'idSoggetto dell'utente in sessione
		if (Long.compare(idSg, userInfoDTO.getIdSoggetto().longValue()) == 0) {
			LOG.debug(prf + " idSoggetto in sessione (" + userInfoDTO.getIdSoggetto() + ") corretto");
		} else {
			LOG.warn(prf + " idSoggetto in sessione (" + userInfoDTO.getIdSoggetto()
					+ ") diverso da quello in request (" + idSg + ")");
			UtenteException ue = new UtenteException(ErrorConstants.ERRORE_UTENTE_NON_AUTORIZZATO);
			throw ue;
		}

		// verifico che parametro role corrisponda ad almeno un ruolo dell'utente in
		// sessione
		Ruolo[] ruoli = userInfoDTO.getRuoli();
		Ruolo ruoloSelez = null;
		boolean checkRole = false;
		for (Ruolo ruolo : ruoli) {
			if (StringUtils.equals(ruolo.getDescrizioneBreve(), role)) {
				LOG.debug(prf + " ruolo.getDescrizione()=" + ruolo.getDescrizione());
				ruoloSelez = ruolo;
				checkRole = true;
			}
		}
		if (!checkRole) {
			// parametro "role" passato in request non corrisponde a nessun ruolo utente
			LOG.warn(prf + "parametro \"role\" passato in request non corrisponde a nessun ruolo utente");
			UtenteException ue = new UtenteException(ErrorConstants.ERRORE_UTENTE_NON_AUTORIZZATO);
			throw ue;
		}

//		it.csi.pbandi.pbandiworkspace.business.impl.NeofluxBusinessImpl::isAttivitaLocked] isAttivitaLocked: descrBreveAttivitaDICH-DI-SPESA, progetto:19233

		// TODO PK : verificare se serve chiamare hasPermesso

		/*
		 * // verifico l'utente in sessione possa accedere a questa funzionalita if
		 * (!profilazioneDao.hasPermesso(idSg, idU, role, UseCaseConstants.UC_MENUCERT
		 * )) { // TODO ----> verificare cosa passare, da dove arriva MENUCERT ??
		 * 
		 * LOG.warn(prf + " idSoggetto in sessione (" + idSg +
		 * ") non ha i permessi per accedere alla certificazione" ); UtenteException ue
		 * = new UtenteException(ErrorConstants.ERRORE_UTENTE_NON_AUTORIZZATO); throw
		 * ue; }
		 */

		// verifico i beneficiari
		if (idSgBen.compareTo(0L) == 0) {
			// beneficiario non selezionato
			LOG.debug(prf + "Beneficiario non selezionato");
		} else {
			BeneficiarioSec beneficiarioSec = profilazioneDao.findBeneficiarioByIdSoggettoBen(idU,
					userInfo2.getCodFisc(), role, idSgBen);

			if (beneficiarioSec != null && Long.compare(idSgBen, beneficiarioSec.getIdBeneficiario()) == 0) {
				// beneficiario valido
				LOG.debug("Beneficiario idSgBen=" + idSgBen + " trovato");

				Beneficiario b = new Beneficiario();
				b.setId(beneficiarioSec.getIdBeneficiario());
				b.setCodiceFiscale(beneficiarioSec.getCodiceFiscale());
				b.setDescrizione(beneficiarioSec.getDenominazione());

				Beneficiario[] arrBenef = new Beneficiario[1];
				arrBenef[0] = b;

				userInfoDTO.setBeneficiari(arrBenef);
			} else {
				LOG.warn(prf + " id soggetto beneficiario (" + idSgBen + ") non e' associato all'utente " + idU);
				UtenteException ue = new UtenteException(ErrorConstants.ERRORE_UTENTE_NON_AUTORIZZATO);
				throw ue;
			}
		}

		BeneficiarioSec beneficiarioSelezionato = null;
		if (StringUtils.equals(taskIdentity, Constants.TASKNAME_DICHDISPESA)
				|| StringUtils.equals(taskIdentity, Constants.TASKNAME_GESTCHECKLIST)
				|| StringUtils.equals(taskIdentity, Constants.TASKNAME_GESTAFFIDAMENTI)) {

			// Gestione della miniapp di processo
			if (StringUtils.equals(wkfAction, "StartTaskNeoFlux")) {

			} else {
//					 * ATTENZIONE: siamo stati chiamati da una attivita' di processo
//					 * che pero' non e' riconosciuta come attivita' valida (manca
//					 * StartTask ) o come attivita' fake (non e' presente -fake- nel
//					 * taskName). 
//					 * Solleviamo una eccezione e non consentiamo l'accesso
				UtenteException e = new UtenteException("Attivita' di processo non riconosciuta come valida");
				LOG.error("TaskIdentity:" + taskIdentity + " workflowAction:" + wkfAction, e);
				throw e;
			}

//			Verifico che ci sia il beneficiario selezionato per il progetto selezionato
			try {
				BeneficiarioDTO[] beneficiariSRV = this.findBeneficiariProgetto(idPrj);

				if (beneficiariSRV == null || beneficiariSRV.length == 0) {
					LOG.error(prf + "Errore");
					throw new UtenteException(" Nessun beneficiario trovato.");
				}
				if (beneficiariSRV.length > 1) {
					LOG.error(prf + "Errore");
					throw new UtenteException(" Errore di dati: Trovato piu' di un beneficiario.");
				}

				LOG.debug(prf + "beneficiariSRV[0]=" + beneficiariSRV[0]);

				if (Long.compare(idSgBen, beneficiariSRV[0].getIdSoggetto()) == 0) {
					LOG.debug(prf + "beneficiario e progetto combinano");

					beneficiarioSelezionato = new BeneficiarioSec();
					beneficiarioSelezionato.setCodiceFiscale(beneficiariSRV[0].getCodiceFiscale());
					beneficiarioSelezionato.setDenominazione(beneficiariSRV[0].getDescrizione());
					beneficiarioSelezionato.setIdBeneficiario(beneficiariSRV[0].getIdSoggetto());

				} else {
					LOG.error(prf + "Errore");
					throw new UtenteException(
							" Errore di dati: Il beneficiario selezionato non puo agire sul progetto trovato");
				}

			} catch (DaoException e) {
				LOG.error(prf + "Errore DaoException");
				throw new DaoException(" " + e.getMessage());
			}

		} else {
			LOG.info(prf + " Gestione della miniApp non di processo, carico il beneficiario ");
			beneficiarioSelezionato = new BeneficiarioSec();

			// it.csi.pbandi.pbservizit.integration.vo.BeneficiarioVO bene =
			// profilazioneDao.findBeneficiario(idSg, userInfo2.getIdIride(), idSgBen);
			BeneficiarioVO bene = retriveBeneficiariProgetto(idSgBen, idPrj);
			beneficiarioSelezionato.setCodiceFiscale(bene.getCodiceFiscale());
			beneficiarioSelezionato.setDenominazione(bene.getDescrizione());
			beneficiarioSelezionato.setIdBeneficiario(idSgBen);

			LOG.info(prf + " beneficiario=" + bene);

		}

		if (userInfoDTO != null) {
			userInfo2 = aggiornaConUserInfoDTO(userInfoDTO, userInfo2);
			userInfo2.setCodiceRuolo(role);
			userInfo2.setBeneficiarioSelezionato(beneficiarioSelezionato);
			userInfo2.setRuolo(ruoloSelez.getDescrizione());
		}

		LOG.info(prf + " END");
		return userInfo2;
	}

	private BeneficiarioDTO[] findBeneficiariProgetto(Long idProgetto) throws DaoException {
		String prf = "[InizializzazioneDAOImpl::findBeneficiariProgetto]";
		LOG.info(prf + " BEGIN");
		LOG.info(prf + " idProgetto=" + idProgetto);

		BeneficiarioDTO[] arr = null;

		String sql;
		try {
			sql = fileSqlUtil.getQuery("FindBeneficiariProgetto.sql");
			LOG.debug(sql.toString());

			List<BeneficiarioVO> listBeneficiari = getJdbcTemplate().query(sql.toString(), new Object[] { idProgetto },
					new BeneficiarioRowMapper());

			if (listBeneficiari != null && !listBeneficiari.isEmpty()) {
				LOG.info(prf + " listBeneficiari.size()=" + listBeneficiari.size());

				List<BeneficiarioDTO> list = new ArrayList<BeneficiarioDTO>();

				for (BeneficiarioVO beneficiarioVO : listBeneficiari) {
					BeneficiarioDTO ben = beanUtil.transform(beneficiarioVO, BeneficiarioDTO.class);
					list.add(ben);
				}

				LOG.info(prf + " list.size()=" + list.size());
				arr = (BeneficiarioDTO[]) list.toArray(new BeneficiarioDTO[list.size()]);
			}

		} catch (FileNotFoundException e) {
			LOG.error(prf + "FileNotFoundException FindBeneficiariProgetto.sql", e);
			throw new DaoException("DaoException while trying to read FindBeneficiariProgetto", e);
		} catch (IOException e) {
			LOG.error(prf + "IOException FindBeneficiariProgetto.sql", e);
			throw new DaoException("DaoException while trying to read FindBeneficiariProgetto", e);
		} catch (Exception e) {
			LOG.error(prf + "Exception while trying to read FindBeneficiariProgetto", e);
			throw new DaoException("DaoException while trying to read FindBeneficiariProgetto", e);
		} finally {
			LOG.info(prf + " END");
		}
		return arr;
	}

	private BeneficiarioVO retriveBeneficiariProgetto(Long idSoggetto, Long idProgetto) throws DaoException {
		String prf = "[InizializzazioneDAOImpl::retriveBeneficiariProgetto]";
		LOG.info(prf + " BEGIN");
		LOG.info(prf + " idSoggetto=" + idSoggetto + ", idProgetto=" + idProgetto);

		BeneficiarioVO result = null;

		String sql;
		try {
			sql = "SELECT 												\r\n"
					+ "prp.id_soggetto,										\r\n"
					+ "prp.id_progetto,										\r\n"
					+ "pts.codice_fiscale_soggetto as codice_fiscale,		\r\n"
					+ "( CASE WHEN pteg.denominazione_ente_giuridico IS NOT NULL THEN pteg.denominazione_ente_giuridico \r\n"
					+ "ELSE ptpf.cognome || ' ' || ptpf.nome				\r\n"
					+ "END ) AS descrizione,								\r\n"
					+ "pteg.id_forma_giuridica, 							\r\n"
					+ "pteg.id_dimensione_impresa,							\r\n"
					+ "pteg.dt_inizio_validita 								\r\n"
					+ "FROM PBANDI_R_SOGGETTO_PROGETTO  prp					\r\n"
					+ "LEFT JOIN PBANDI_T_ENTE_GIURIDICO  pteg				\r\n"
					+ "ON prp.id_ente_giuridico = pteg.id_ente_giuridico 	\r\n"
					+ "LEFT JOIN PBANDI.PBANDI_T_PERSONA_FISICA  ptpf		\r\n"
					+ "ON prp.id_persona_fisica = ptpf.id_persona_fisica 	\r\n"
					+ "JOIN pbandi_t_soggetto pts 							\r\n"
					+ "ON prp.id_soggetto = pts.id_soggetto 				\r\n"
					+ "WHERE prp.id_soggetto = ?							\r\n"
					+ "AND prp.id_progetto = ?								\r\n"
					+ "AND prp.id_tipo_anagrafica = 1						\r\n"
					+ "AND prp.id_tipo_beneficiario <> 4 				        ";

			LOG.debug(sql.toString());

			Object[] par = { idSoggetto.toString(), idProgetto.toString() };
			result = (BeneficiarioVO) getJdbcTemplate().queryForObject(sql, par,
					new BeanRowMapper(BeneficiarioVO.class));

		} catch (Exception e) {
			LOG.error(prf + "Exception while trying to read FindBeneficiariProgetto", e);
			throw new DaoException("DaoException while trying to read FindBeneficiariProgetto", e);
		} finally {
			LOG.info(prf + " END");
		}
		return result;
	}

	private UserInfoSec aggiornaConUserInfoDTO(UserInfoDTO user, UserInfoSec userInfo) {
		String prf = "[RendicontazioneServiceImpl::aggiornaConUserInfoDTO]";
		LOG.info(prf + " BEGIN");
		UserInfoSec u = userInfo;

		if (user.getBeneficiari() != null && user.getBeneficiari().length > 0) {
			Beneficiario ben = user.getBeneficiari()[0];
			BeneficiarioSec b = new BeneficiarioSec();
			b.setCodiceFiscale(ben.getCodiceFiscale());
			b.setDenominazione(ben.getCodiceFiscale());
			b.setIdBeneficiario(new Long(ben.getId()));
			userInfo.setBeneficiarioSelezionato(b);
		}

		u.setIdSoggetto(user.getIdSoggetto());
		u.setIdUtente(user.getIdUtente());
		u.setIsIncaricato(user.getIsIncaricato());

		if (user.getRuoli() != null && user.getRuoli().length > 0) {
			List<Ruolo> y = Arrays.asList(user.getRuoli());
			u.setRuoli(new ArrayList<Ruolo>(y));
		}
		LOG.info(prf + " END");
		return u;
	}
}
