/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweb.integration.dao.impl;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;
import javax.ws.rs.core.Response;

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
import it.csi.pbandi.pbservizit.exception.ErroreGestitoException;
import it.csi.pbandi.pbservizit.exception.UtenteException;
import it.csi.pbandi.pbservizit.integration.dao.ProfilazioneDAO;
import it.csi.pbandi.pbservizit.integration.vo.TipoAnagraficaVO;
import it.csi.pbandi.pbservizit.security.BeneficiarioSec;
import it.csi.pbandi.pbservizit.security.UserInfoSec;
import it.csi.pbandi.pbservizit.util.UseCaseConstants;
import it.csi.pbandi.pbweb.dto.DatiProgettoInizializzazioneDTO;
import it.csi.pbandi.pbweb.dto.InizializzaDTO;
import it.csi.pbandi.pbweb.dto.profilazione.BeneficiarioDTO;
import it.csi.pbandi.pbweb.integration.dao.InizializzazioneDAO;
import it.csi.pbandi.pbweb.integration.dao.impl.rowmapper.BeanRowMapper;
import it.csi.pbandi.pbweb.integration.dao.impl.rowmapper.BeneficiarioRowMapper;
import it.csi.pbandi.pbweb.integration.vo.BeneficiarioVO;
import it.csi.pbandi.pbweb.integration.vo.ProgettoBandoLineaVO;
import it.csi.pbandi.pbweb.util.BeanUtil;
import it.csi.pbandi.pbweb.util.Constants;
import it.csi.pbandi.pbweb.util.FileSqlUtil;

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
	// Restituisce i dati con cui popolare Rendicontazione all'apertura:
	// - dati utente: userInfoSec
	// - dati del progetto
	public InizializzaDTO inizializzaHome(Long idPrj, Long idSg, Long idSgBen, Long idU, String role,
			String taskIdentity, String taskName, String wkfAction, HttpServletRequest req) throws UtenteException {
		String prf = "[InizializzazioneDAOImpl::inizializzaHome] ";
		LOG.info(prf + " BEGIN");
		InizializzaDTO result = new InizializzaDTO();
		try {

			result.setUserInfoSec(
					completaDatiUtente(idPrj, idSg, idSgBen, idU, role, taskIdentity, taskName, wkfAction, req));
			if (result.getUserInfoSec() == null)
				throw new UtenteException("Errore nella inizializzazione dei dati utente.");

			// result.setDatiProgetto(completaDatiProgetto(idPrj));

		} catch (Exception e) {
			LOG.error(prf + " ERRORE durante la inizializzazione: ", e);
			throw new UtenteException(e.getMessage(), e);
		} finally {
			LOG.info(prf + " END");
		}

		return result;
	}

	public UserInfoSec completaDatiUtente(Long idPrj, Long idSg, Long idSgBen, Long idU, String role,
			String taskIdentity, String taskName, String wkfAction, HttpServletRequest req) throws UtenteException {
		String prf = "[InizializzazioneDAOImpl::completaDatiUtente] ";
		LOG.info(prf + " BEGIN");

		LOG.info(prf + " idPrj=" + idPrj + ", idSg=" + idSg + ", idSgBen=" + idSgBen + ", idU=" + idU);
		LOG.info(prf + " role=" + role + ", taskIdentity=" + taskIdentity + ", taskName=" + taskName + ", wkfAction="
				+ wkfAction);

//		[idPrj], v[0]=19241
//		[idSg], v[0]=2139302
//		[idSgBen], v[0]=17408
//		[idU], v[0]=25846
//		[role], v[0]=BEN-MASTER
//		[taskIdentity], v[0]=DICH-DI-SPESA
//		[taskName], v[0]=Dichiarazione di spesa
//		[wkfAction], v[0]=StartTaskNeoFlux

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
		String prf = "[InizializzazioneDAOImpl::inizializza] ";
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
			LOG.debug("Beneficiario non selezionato");
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

		taskIdentity = taskIdentity.trim();
		LOG.info(prf + "taskIdentity = " + taskIdentity);
		BeneficiarioSec beneficiarioSelezionato = null;
		if (StringUtils.equals(taskIdentity, Constants.TASK_IDENTITY_DICHDISPESA)
				|| StringUtils.equals(taskIdentity, Constants.TASK_IDENTITY_GESTCHECKLIST)
				|| StringUtils.equals(taskIdentity, Constants.TASK_IDENTITY_VALIDAZIONE)
				|| StringUtils.equals(taskIdentity, Constants.TASK_IDENTITY_VALIDAZIONE_FINALE)
				|| StringUtils.equals(taskIdentity, Constants.TASK_IDENTITY_SPESA_VALIDATA)) {

			LOG.info(prf + " Gestione miniapp di processo.");

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

				LOG.info(prf + "beneficiariSRV[0]=" + beneficiariSRV[0]);

				if (Long.compare(idSgBen, beneficiariSRV[0].getIdSoggetto()) == 0) {
					LOG.info(prf + "beneficiario e progetto combinano");

					beneficiarioSelezionato = new BeneficiarioSec();
					beneficiarioSelezionato.setCodiceFiscale(beneficiariSRV[0].getCodiceFiscale());
					beneficiarioSelezionato.setDenominazione(beneficiariSRV[0].getDescrizione());
					beneficiarioSelezionato.setIdBeneficiario(beneficiariSRV[0].getIdSoggetto());

					LOG.info(prf + " Beneficiario assegnato: " + beneficiariSRV[0].getDescrizione() + "; IdSoggetto = "
							+ beneficiariSRV[0].getIdSoggetto());

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
			LOG.info(prf + " Gestione miniApp non di processo, carico il beneficiario ");
			beneficiarioSelezionato = new BeneficiarioSec();

			it.csi.pbandi.pbservizit.integration.vo.BeneficiarioVO bene = profilazioneDao.findBeneficiario(idSg,
					userInfo2.getIdIride(), idSgBen);
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
		String prf = "[InizializzazioneDAOImpl::findBeneficiariProgetto] ";
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

	private UserInfoSec aggiornaConUserInfoDTO(UserInfoDTO user, UserInfoSec userInfo) {
		String prf = "[RendicontazioneServiceImpl::aggiornaConUserInfoDTO] ";
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

	// Copiato da pbweberog inizializza/home2 che a sua volta ha copiato da pbweb
	// :-)
	@Override
	public UserInfoSec inizializzaHome2(Long idSg, Long idSgBen, Long idU, String role, HttpServletRequest req)
			throws UtenteException {
		String prf = "[InizializzazioneDAOImpl::inizializzaHome2]";
		LOG.info(prf + " BEGIN");
		HttpSession session = req.getSession();
		UserInfoSec userInfo2 = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
		LOG.info(prf + "userInfo=" + userInfo2);

		if (userInfo2 == null) {
			LOG.warn(prf + "userInfo in sessione null");
			throw new UtenteException("Utente in sessione non valido");
		}

		// TODO : verifico la valorizzazione dei parametri idSg, idSgBen, idU, role,

		LOG.info(prf + " idSg=" + idSg + ", idSgBen=" + idSgBen + ", idU=" + idU + " role=" + role);

		try {
			UserInfoDTO user = inizializzaHome2(idSg, idSgBen, idU, role, userInfo2);
			userInfo2 = aggiornaConUserInfoDTO(user, userInfo2);
			userInfo2.setCodiceRuolo(role);
			List<TipoAnagraficaVO> listaTA = profilazioneDao.findTipiAnagrafica(BigDecimal.valueOf(idSg));
			for (TipoAnagraficaVO tipoAnagraficaVO : listaTA) {
				if (StringUtils.equals(role, tipoAnagraficaVO.getDescBreveTipoAnagrafica())) {
					userInfo2.setRuolo(tipoAnagraficaVO.getDescTipoAnagrafica());
					break;
				}
			}

			if (idSgBen.compareTo(0L) == 0) {
				// beneficiario non selezionato
				userInfo2.setBeneficiarioSelezionato(null);
				LOG.debug("Beneficiario non selezionato");
			} else {
				Beneficiario[] y = user.getBeneficiari();
				for (Beneficiario beneficiario : user.getBeneficiari()) {
					if (Long.compare(idSgBen, beneficiario.getId()) == 0) {
						BeneficiarioSec beneficiarioSelezionato = new BeneficiarioSec();
						beneficiarioSelezionato.setCodiceFiscale(beneficiario.getCodiceFiscale());
						beneficiarioSelezionato.setDenominazione(beneficiario.getDescrizione());
						beneficiarioSelezionato.setIdBeneficiario(idSgBen);
						userInfo2.setBeneficiarioSelezionato(beneficiarioSelezionato);
						break;
					}
				}
			}

			LOG.info(prf + " userInfo2=" + userInfo2);

		} catch (Exception e) {
			LOG.error(prf + " Exception " + e.getMessage());
			e.printStackTrace();
			throw new ErroreGestitoException("Errore gestito");
		}

		LOG.info(prf + " END");
		return userInfo2;
	}

	private UserInfoDTO inizializzaHome2(Long idSg, Long idSgBen, Long idU, String role, UserInfoSec userInfo2)
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
		boolean checkRole = false;
		for (Ruolo ruolo : ruoli) {
			if (StringUtils.equals(ruolo.getDescrizioneBreve(), role)) {
				checkRole = true;
			}
		}
		if (!checkRole) {
			// parametro "role" passato in request non corrisponde a nessun ruolo utente
			LOG.warn(prf + "parametro \"role\" passato in request non corrisponde a nessun ruolo utente");
			UtenteException ue = new UtenteException(ErrorConstants.ERRORE_UTENTE_NON_AUTORIZZATO);
			throw ue;
		}

		// verifico l'utente in sessione possa accedere a questa funzionalita
		if (!profilazioneDao.hasPermesso(idSg, idU, role, UseCaseConstants.UC_TRSBKO010)) {

			LOG.warn(prf + " idSoggetto in sessione (" + idSg + ") non ha i permessi per accedere");
			UtenteException ue = new UtenteException(ErrorConstants.ERRORE_UTENTE_NON_AUTORIZZATO);
			throw ue;
		}

		// verifico i beneficiari
		if (idSgBen.compareTo(0L) == 0) {
			// beneficiario non selezionato
			LOG.debug("Beneficiario non selezionato");
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

		LOG.info(prf + " END");
		return userInfoDTO;
	}

}
