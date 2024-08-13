/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebbo.integration.dao.impl;

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
import it.csi.pbandi.pbservizit.exception.ErroreGestitoException;
import it.csi.pbandi.pbservizit.exception.UtenteException;
import it.csi.pbandi.pbservizit.integration.dao.ProfilazioneDAO;
import it.csi.pbandi.pbservizit.security.BeneficiarioSec;
import it.csi.pbandi.pbservizit.security.UserInfoSec;
import it.csi.pbandi.pbservizit.util.UseCaseConstants;
import it.csi.pbandi.pbwebbo.integration.dao.InizializzazioneDAO;
import it.csi.pbandi.pbwebbo.util.BeanUtil;
import it.csi.pbandi.pbwebbo.util.Constants;
import it.csi.pbandi.pbwebbo.util.FileSqlUtil;

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

//		miniAppUrl: ----> http://<VH_dev>/finanziamenti/bandijp/pbandiwebbosrv2/shib/HomePage.do?
		// idSg=2139302 & --> pbandi_t_soggetto
		// idSgBen=5570 &
		// role=CSI-ASSISTENZA &
		// idU=25846 --> pbandi_t_utente

		HttpSession session = req.getSession();
		UserInfoSec userInfo2 = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
		LOG.info(prf + "userInfo=" + userInfo2);

		if (userInfo2 == null) {
			LOG.warn(prf + "userInfo in sessione null");
			throw new UtenteException("Utente in sessione non valido");
		}

		try {

			UserInfoDTO user = inizializza(idSg, idSgBen, idU, role, userInfo2);

			LOG.info(prf + "user=" + user);

			userInfo2 = aggiornaConUserInfoDTO(user, userInfo2);
			userInfo2.setCodiceRuolo(role);

			session.setAttribute(Constants.USERINFO_SESSIONATTR, userInfo2);
			LOG.info(prf + "userInfo2=" + userInfo2);

		} catch (Exception e) {
			LOG.error(prf + " Exception " + e.getMessage());
			e.printStackTrace();
			throw new ErroreGestitoException("Errore gestito");
		}

		LOG.info(prf + " END");
		return userInfo2;
	}

	private UserInfoDTO inizializza(Long idSg, Long idSgBen, Long idU, String role, UserInfoSec userInfo2)
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
				LOG.debug(prf + " ruolo in sessione (" + ruolo.getDescrizioneBreve() + ") corretto");
			}
		}
		if (!checkRole) {
			// parametro "role" passato in request non corrisponde a nessun ruolo utente
			LOG.warn(prf + "parametro \"role\" passato in request non corrisponde a nessun ruolo utente");
			UtenteException ue = new UtenteException(ErrorConstants.ERRORE_UTENTE_NON_AUTORIZZATO);
			throw ue;
		}

		// verifico l'utente in sessione possa accedere a questa funzionalita
		if (!profilazioneDao.hasPermesso(idSg, idU, role, UseCaseConstants.UC_MENUAMM)) {
			LOG.warn(prf + " idSoggetto in sessione (" + idSg + ") non ha i permessi per accedere ");
			UtenteException ue = new UtenteException(ErrorConstants.ERRORE_UTENTE_NON_AUTORIZZATO);
			throw ue;
		}

//		 verifico i beneficiari
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

	private UserInfoSec aggiornaConUserInfoDTO(UserInfoDTO user, UserInfoSec userInfo) {
		String prf = "[InizializzazioneDAOImpl::aggiornaConUserInfoDTO]";
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
			LOG.info(prf + " ruoli trovati=" + user.getRuoli().length);
			List<Ruolo> y = Arrays.asList(user.getRuoli());
			u.setRuoli(new ArrayList<Ruolo>(y));
		}
		LOG.info(prf + " END");
		return u;
	}
}
