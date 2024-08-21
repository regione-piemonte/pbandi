/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.business.api.impl;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.csi.pbandi.pbservizit.business.api.UserApi;
import it.csi.pbandi.pbservizit.business.intf.GestioneProfilazioneBusiness;
import it.csi.pbandi.pbservizit.dto.profilazione.BeneficiarioCount;
import it.csi.pbandi.pbservizit.dto.profilazione.Ruolo;
import it.csi.pbandi.pbservizit.exception.DaoException;
import it.csi.pbandi.pbservizit.exception.ErroreGestitoException;
import it.csi.pbandi.pbservizit.exception.UtenteException;
import it.csi.pbandi.pbservizit.integration.dao.DecodificheDAO;
import it.csi.pbandi.pbservizit.integration.dao.ProfilazioneDAO;
import it.csi.pbandi.pbservizit.pbandisrv.business.gestionebackoffice.GestioneBackofficeBusinessImpl;
import it.csi.pbandi.pbservizit.pbandisrv.business.gestionedatigenerali.GestioneDatiGeneraliBusinessImpl;
import it.csi.pbandi.pbservizit.pbandisrv.dto.gestionedatigenerali.DatiGeneraliDTO;
import it.csi.pbandi.pbservizit.security.BeneficiarioSec;
import it.csi.pbandi.pbservizit.security.UserInfoSec;
import it.csi.pbandi.pbservizit.util.Constants;

@Service
public class UserApiServiceImpl implements UserApi {

	private Logger LOG = Logger.getLogger(Constants.COMPONENT_NAME);

	@Autowired
	private GestioneProfilazioneBusiness gestioneProfilazioneBusiness;
	
	@Autowired
	GestioneDatiGeneraliBusinessImpl datiGeneraliBusinessImpl;

	@Autowired
	private ProfilazioneDAO profilazioneDAO;

	@Autowired
	private DecodificheDAO decodificheDAO;
	
	@Autowired
	private GestioneBackofficeBusinessImpl gestioneBackofficeBusinessImpl;

	@Override
	public void SSOLogout(HttpServletRequest req) {
		String prf = "[UserApiServiceImpl::SSOLogout]";
		LOG.info(prf + " BEGIN-END");
		req.getSession().invalidate();
	}

	@Override
	public Response getInfoUtente(HttpServletRequest req) throws UtenteException, ErroreGestitoException {
		String prf = "[UserApiServiceImpl::getInfoUtente]";

		LOG.info(prf + " BEGIN");
		Response response = null;

		UserInfoSec userInfo = (UserInfoSec) req.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);
		LOG.info(prf + "userInfo IN SESSIONE=" + userInfo); // questo arriva dal filtro IrideAdapterFilter

		if (userInfo == null) {
			LOG.error(prf + "userInfo in sessione null");
			throw new UtenteException("Utente in sessione nullo");
		}

		// se è il primo accesso
		// completo l'oggetto in sessione con altri dati
		try {
			userInfo = gestioneProfilazioneBusiness.getInfoUtente(req.getSession());
			LOG.info(prf + "userInfo: " + userInfo);
		} catch (UtenteException e) {
			LOG.error(prf + "UtenteException " + e, e);
			e.printStackTrace();
			return Response.status(401).entity(e.getMessage()).build();
		} catch (Exception e) {
			LOG.error(prf + "Exception " + e, e);
			e.printStackTrace();
			throw new ErroreGestitoException("Impossibile completare utente in sessione 1", "COD_21");
//			} catch (CSIException e) {
//				LOG.error(prf + "CSIException " + e, e);
//				e.printStackTrace();
//				throw new ErroreGestitoException("Impossibile completare utente in sessione 2", "COD_22");
//			} catch (Exception e) {
//				LOG.error(prf + "Exception " + e, e);
//				e.printStackTrace();
//				throw new ErroreGestitoException("Impossibile completare utente in sessione 3", "COD_23");
		}

		if (userInfo == null) {
			LOG.info(prf + "userInfo restituito da profilazioneService.getInfoUtente(): NULL");
		} else {
			LOG.info(prf + "userInfo restituito da profilazioneBusiness.getInfoUtente(): " + userInfo.toString());
		}

		////////////////////////// RUOLI ////////////////////////////

		/*
		 * Se l' utente ha piu' di un ruolo valido allora deve selezionarle uno. Se l'
		 * utente ha un solo ruolo valido allora lo seleziono Se l' utente non ha ruoli
		 * validi non accede.
		 */
		int countRuoliValidi = 0;
		if (userInfo.getRuoli() != null) {
			countRuoliValidi = userInfo.getRuoli().size();
		}
		LOG.info(prf + "countRuoliValidi = " + countRuoliValidi);

		if (countRuoliValidi > 1) {

			LOG.info(prf + "countRuoliValidi > 1 ");

		} else if (countRuoliValidi == 1) {

			Ruolo ruolo = userInfo.getRuoli().get(0);
			if (ruolo != null) {
				userInfo.setRuolo(ruolo.getDescrizione());
				userInfo.setCodiceRuolo(ruolo.getDescrizioneBreve());
			} else {
				LOG.warn(prf + "Nessun ruolo trovato nello userInfo");
			}

			/*
			 * Carico i beneficiari nello user info solo se l' utente e' abilitato al caso
			 * d'uso TRSACC002. Nel caso in cui ho un solo beneficiario allora lo seleziono.
			 * Nel caso di accesso di un incaricato allora seleziono come beneficiario
			 * quello ricevuto da Gestione Incarichi.
			 */

			String codiceRuolo = userInfo.getCodiceRuolo();
			BeneficiarioCount beneficiarioCount = null;
			try {
				LOG.debug(prf + " cerco i beneficiari");
				beneficiarioCount = gestioneProfilazioneBusiness.countBeneficiari(userInfo, codiceRuolo);
			} catch (DaoException e) {
				LOG.error(prf + "Errore " + e.getMessage());
				e.printStackTrace();
				throw new ErroreGestitoException("Impossibile caricare i beneficiario", "COD_BEN1");
			}

			if (beneficiarioCount != null)
				LOG.debug(prf + " trovati beneficiari = " + beneficiarioCount.getCount());
			else
				LOG.debug(prf + " beneficiari NULL");

			
			userInfo.setBeneficiariCount(beneficiarioCount.getCount());

//			if (beneficiari != null) {
//				if (userInfoHelper.isLoginIncaricato(userInfo)) {
//					UtenteGIDTO utenteIncaricato = (UtenteGIDTO) theModel.getSession().get(it.csi.pbandi.pbandiutil.commonweb.Constants.KEY_UTENTE_INCARICATO);
//					for (BeneficiarioDTO beneficiario : beneficiari) {
//						if (beneficiario.getCodiceFiscale().equalsIgnoreCase( utenteIncaricato.getCodAzienza())) {
//							userInfo.setBeneficiarioSelezionato(beneficiario);
//							break;
//						}
//
//					}
//				} else 
			if (beneficiarioCount.getCount().equals(1L)) {
				LOG.debug(prf + " impostato beneficiario =" + beneficiarioCount.getBeneficiario().toString());
				userInfo.setBeneficiarioSelezionato(beneficiarioCount.getBeneficiario());
			}

			req.getSession().setAttribute(Constants.USERINFO_SESSIONATTR, userInfo);
		}
		response = Response.ok().entity(userInfo).build();
		LOG.info(prf + " END");
		return response;
	}

	/*
	 * Quando l'utente sceglie un ruolo/profilo, viene salvato in sessione e vengono
	 * caricati i beneficiari Quando l'utente sceglie un beneficiario, viene salvato
	 * in sessione
	 */
	@Override
	public Response accedi(HttpServletRequest req, UserInfoSec userInfo)
			throws UtenteException, ErroreGestitoException {
		String prf = "[UserApiServiceImpl::accedi]";
		LOG.info(prf + " BEGIN");

		if (userInfo == null || (userInfo != null && StringUtils.isEmpty(userInfo.getCodFisc()))) {
			LOG.error(prf + "userInfo passato come parametro null");
			throw new UtenteException("Parametro utente nullo");
		} else {
			LOG.info(prf + "userInfo passato come parametro: " + userInfo.toString());
		}

		if (!checkCodiceFiscale(userInfo.getCodFisc())) {
			LOG.error(prf + "CodiceFiscale in userInfo non è conforme allo standard. userInfo.getCodFisc() = "
					+ userInfo.getCodFisc());
			throw new UtenteException("CodiceFiscale in userInfo non è conforme allo standard. userInfo.getCodFisc() = "
					+ userInfo.getCodFisc());
		} else {
			LOG.info(prf + "CodiceFiscale in userInfo è conforme allo standard. userInfo.getCodFisc() = "
					+ userInfo.getCodFisc());
		}

		if (!checkCodRuolo(userInfo.getCodiceRuolo())) {
			LOG.error(prf + "CodiceRuolo in userInfo non è conforme allo standard. userInfo.getCodiceRuolo() = "
					+ userInfo.getCodiceRuolo());
			throw new UtenteException(
					"CodiceRuolo in userInfo non è conforme allo standard. userInfo.getCodiceRuolo() = "
							+ userInfo.getCodiceRuolo());
		} else {
			LOG.info(prf + "CodiceRuolo in userInfo è conforme allo standard. userInfo.getCodiceRuolo() = "
					+ userInfo.getCodiceRuolo());
		}

		////////////////////////// RUOLI ////////////////////////////

		/*
		 * Se l' utente ha piu' di un ruolo valido allora deve selezionarle uno. Se l'
		 * utente ha un solo ruolo valido allora lo seleziono Se l' utente non ha ruoli
		 * validi non accede.
		 */

		if (userInfo.getRuolo() != null && userInfo.getRuolo().length() > 0 && userInfo.getRuoli().size() > 1) {
			for (Ruolo ruolo : userInfo.getRuoli()) {
				if (ruolo.getDescrizione().equals(userInfo.getRuolo())) {
					userInfo.setCodiceRuolo(ruolo.getDescrizioneBreve());
					break;
				}
			}

			/*
			 * Carico i beneficiari nello user info solo se l' utente e' abilitato al caso
			 * d'uso TRSACC002. Nel caso in cui ho un solo beneficiario allora lo seleziono.
			 * Nel caso di accesso di un incaricato allora seleziono come beneficiario
			 * quello ricevuto da Gestione Incarichi.
			 */
			String codiceRuolo = userInfo.getCodiceRuolo();
			BeneficiarioCount beneficiarioCount = null;
			try {
				LOG.debug(prf + " cerco i beneficiari");

				// TODO :PK non cercarli se ruolo tipo "Certificatore"....
				beneficiarioCount = gestioneProfilazioneBusiness.countBeneficiari(userInfo, codiceRuolo);
			} catch (DaoException e) {
				LOG.error(prf + "Errore " + e.getMessage());
				e.printStackTrace();
				throw new ErroreGestitoException("Impossibile caricare i beneficiari", "COD_BEN1");
			}

			if (beneficiarioCount != null)
				LOG.debug(prf + " trovati beneficiari.size()=" + beneficiarioCount.getCount());
			else
				LOG.debug(prf + " beneficiari NULL");

			//userInfo.setBeneficiari((ArrayList<BeneficiarioSec>) beneficiari);
			userInfo.setBeneficiariCount(beneficiarioCount.getCount());
			if (beneficiarioCount != null && beneficiarioCount.getCount().equals(1L)) {
				LOG.debug(prf + " impostato beneficiario =" + beneficiarioCount.getBeneficiario().toString());
				userInfo.setBeneficiarioSelezionato(beneficiarioCount.getBeneficiario());
			} else {
				userInfo.setBeneficiarioSelezionato(null);
			}
		}

		req.getSession().setAttribute(Constants.USERINFO_SESSIONATTR, userInfo);
		LOG.info(prf + " END");
		return Response.ok().entity(userInfo).build();
	}

	@Override
	public Response getBeneficiari(String denominazione, @Context HttpServletRequest req)
			throws UtenteException, ErroreGestitoException {
		String prf = "[UserApiServiceImpl::getBeneficiari]";
		LOG.info(prf + " BEGIN");

		if (denominazione == null) {
			LOG.error(prf + "denominazione passato come parametro null");
			throw new ErroreGestitoException("Parametro beneficario nullo");
		} else {
			LOG.info(prf + "denominazione passata come parametro: " + denominazione);
		}

		UserInfoSec userInfo = (UserInfoSec) req.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);
		LOG.info(prf + "userInfo=" + userInfo);

		if (userInfo == null) {
			LOG.error(prf + "userInfo in sessione null");
			throw new UtenteException("Utente in sessione nullo");
		}
		
		List<BeneficiarioSec> beneficiari = new ArrayList<BeneficiarioSec>();
		try {
			LOG.debug(prf + " cerco i beneficiari");
			beneficiari = gestioneProfilazioneBusiness.findBeneficiariByDenominazione(userInfo, denominazione);
		} catch (Exception e) {
			LOG.error(prf + "Errore " + e.getMessage());
			e.printStackTrace();
			throw new ErroreGestitoException("Impossibile caricare i beneficiari", "COD_BEN1");
		}

		if (beneficiari != null)
			LOG.debug(prf + " trovati beneficiari.size()=" + beneficiari.size());
		else
			LOG.debug(prf + " beneficiari NULL");

		
		LOG.info(prf + " END");
		return Response.ok(beneficiari).build();
	}
	
	@Override
	public Response salvaBeneficiarioSelezionato(HttpServletRequest req, BeneficiarioSec beneficiario)
			throws UtenteException, ErroreGestitoException {

		// TODO PK : questo metodo che senso ha? salva il beneficiario nello userInfo e
		// poi mette lo userInfo in sessione....
		// scrivere in PBANDI_T_TRACCIAMENTO E CSI_LOG_AUDIT ??

		String prf = "[UserApiServiceImpl::salvaBeneficiarioSelezionato]";
		LOG.info(prf + " BEGIN");

		if (beneficiario == null) {
			LOG.error(prf + "beneficario passato come parametro null");
			throw new UtenteException("Parametro beneficario nullo");
		} else {
			LOG.info(prf + "beneficario passato come parametro: " + beneficiario.toString());
		}

		UserInfoSec userInfo = (UserInfoSec) req.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);
		LOG.info(prf + "userInfo=" + userInfo);

		if (userInfo == null) {
			LOG.error(prf + "userInfo in sessione null");
			throw new UtenteException("Utente in sessione nullo");
		}
		userInfo.setBeneficiarioSelezionato(beneficiario);

		req.getSession().setAttribute(Constants.USERINFO_SESSIONATTR, userInfo);
		LOG.info(prf + " END");
		return Response.ok().build();
	}

	@Override
	public Response avvisi(HttpServletRequest req) throws InvalidParameterException, Exception {
		UserInfoSec userInfo = (UserInfoSec) req.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);
		return Response.ok().entity(
				profilazioneDAO.avvisi(userInfo.getCodiceRuolo(), userInfo.getIdUtente(), userInfo.getIdIride()))
				.build();
	}

	@Override
	public Response isRegolaApplicabileForBandoLinea(Long idBandoLinea, String codiceRegola, HttpServletRequest req)
			throws InvalidParameterException, Exception {
		UserInfoSec userInfo = (UserInfoSec) req.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);
		return Response.ok().entity(profilazioneDAO.isRegolaApplicabileForBandoLinea(idBandoLinea, codiceRegola,
				userInfo.getIdUtente(), userInfo.getIdIride())).build();
	}

	private boolean checkCodiceFiscale(String codiceFiscale) {

		boolean isCodiceFiscale = false;

		String regex = "[a-zA-Z]{6}[0-9]{2}[abcdehlmprstABCDEHLMPRST]{1}[0-9]{2}([a-zA-Z]{1}[0-9]{3})[a-zA-Z]{1}";
		Pattern pattern = Pattern.compile(regex);

		Matcher matcher = pattern.matcher(codiceFiscale);

		if (matcher.matches())
			isCodiceFiscale = true;

		return isCodiceFiscale;

	}

	private boolean checkCodRuolo(String codiceRuolo) {

		boolean isCodiceRuolo = false;
		if (codiceRuolo != null && !codiceRuolo.isEmpty()) {
			String regex = "[a-zA-Z-]+";
			Pattern pattern = Pattern.compile(regex);
			Matcher matcher = pattern.matcher(codiceRuolo);
			if (matcher.matches())
				isCodiceRuolo = true;
		} else {
			isCodiceRuolo = true;
		}
		return isCodiceRuolo;
	}

	@Override
	public Response isCostanteFinpiemonte(HttpServletRequest req) throws Exception {
		String valore = decodificheDAO.costante("SVILUPPO_FINP", true);
		Boolean value;
		if (valore.equals("1")) {
			value = Boolean.FALSE;
		} else if (valore.equals("2")) {
			value = Boolean.TRUE;
		} else {
			LOG.error("Valore della costante SVILUPPO_FINP non valido");
			throw new Exception("Valore della costante SVILUPPO_FINP non valido");
		}
		return Response.ok().entity(value).build();
	}
	
	@Override
	public Response bandoIsEnteCompetenzaFinpiemonte(Long progBandoLineaIntervento, HttpServletRequest req) throws Exception { 
	
		String className = Thread.currentThread().getStackTrace()[1].getClassName();
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		String prf = "[" + className + "::" + methodName + "]";
		LOG.info(prf + " BEGIN");	
		
		Boolean response = null;
		
		try {
			response = gestioneBackofficeBusinessImpl.bandoIsEnteCompetenzaFinpiemonte(progBandoLineaIntervento);
		
		}catch (Exception e) {	
			LOG.error(prf + ": "+e);
			e.getStackTrace();
			throw e;
		
		}
		
		LOG.info(prf + " END");	
		
		return Response.ok().entity(response).build();
	
	}
	
	@Override
	public Response isBandoSif(Long progBandoLineaIntervento, HttpServletRequest req) throws Exception { 
	
		String className = Thread.currentThread().getStackTrace()[1].getClassName();
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		String prf = "[" + className + "::" + methodName + "]";
		LOG.info(prf + " BEGIN");	
		
		Boolean response = null;
		
		try {
			response = gestioneBackofficeBusinessImpl.isBandoSif(progBandoLineaIntervento);
		
		}catch (Exception e) {	
			LOG.error(prf + ": "+e);
			e.getStackTrace();
			throw e;
		
		}
		
		LOG.info(prf + " END");	
		
		return Response.ok().entity(response).build();
	
	}

	@Override
	public Response isBandoSiffino(Long progBandoLineaIntervento, Long idBando, HttpServletRequest req)
			throws InvalidParameterException, Exception {
		String className = Thread.currentThread().getStackTrace()[1].getClassName();
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		String prf = "[" + className + "::" + methodName + "]";
		LOG.info(prf + " BEGIN");	
		
		Boolean response = null;
		
		try {
			response = gestioneBackofficeBusinessImpl.isBandoSiffino(progBandoLineaIntervento, idBando);
		
		}catch (Exception e) {	
			LOG.error(prf + ": "+e);
			e.getStackTrace();
			throw e;
		
		}
		
		LOG.info(prf + " END");	
		
		return Response.ok().entity(response).build();
	}

	@Override
	public Response hasProgettoLineaByDescBreve(HttpServletRequest req, Long idProgetto, String descBreveLinea)
			throws UtenteException, Exception {
		String prf = "[UserApiServiceImpl::hasProgettoLineaByDescBreve]";
		LOG.info(prf + " BEGIN");
		try {
			HttpSession session = req.getSession();
			UserInfoSec userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
			Long idUtente = userInfo.getIdUtente();
			String idIride = userInfo.getIdIride();

			if (idProgetto == null || descBreveLinea == null) {
				throw new InvalidParameterException("idProgetto o descBreveLinea sono a null");
			}
			return Response.ok().entity(isLineaByIdProgettoDescBreveLinea(idProgetto, descBreveLinea, idUtente, idIride,
					userInfo.getBeneficiarioSelezionato().getIdBeneficiario())).build();
		} catch (Exception e) {
			throw e;
		} finally {
			LOG.info(prf + " END");
		}
	}
	

	public Boolean isLineaByIdProgettoDescBreveLinea(Long idProgetto, String descBreveLinea, Long idUtente,
			String idIride, Long idBeneficiario) throws UtenteException, Exception {
		String prf = "[GestioneDatiProgettoServiceImpl::isLineaByIdProgettoDescBreveLinea]";
		LOG.info(prf + " BEGIN");
		try {
			Boolean hasLinea = Boolean.FALSE;
			DatiGeneraliDTO datiGenerali = datiGeneraliBusinessImpl.caricaDatiGenerali(idUtente, idIride, idProgetto,
					idBeneficiario);
			if (datiGenerali.getDescBreveLineaNormativa() != null && datiGenerali.getDescBreveLineaNormativa().compareTo(descBreveLinea) == 0) {
				hasLinea = Boolean.TRUE;
			}
			return hasLinea;
		} catch (Exception e) {
			throw e;
		} finally {
			LOG.info(prf + " END");
		}
	}

	@Override
	public Response getProgrBandoLineaByIdProgetto(HttpServletRequest req, Long idProgetto)
			throws UtenteException, Exception {
		String className = Thread.currentThread().getStackTrace()[1].getClassName();
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		String prf = "[" + className + "::" + methodName + "]";
		LOG.info(prf + " BEGIN");	
		
		Long response = null;
		
		try {
			response = gestioneBackofficeBusinessImpl.getProgrBandoLineaByIdProgetto(idProgetto);
		
		}catch (Exception e) {	
			LOG.error(prf + ": "+e);
			e.getStackTrace();
			throw e;
		
		}
		
		LOG.info(prf + " END");	
		
		return Response.ok().entity(response).build();
	}

}
