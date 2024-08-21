/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.business;

import java.io.InputStream;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import it.csi.csi.porte.InfoPortaDelegata;
import it.csi.csi.porte.proxy.PDProxy;
import it.csi.csi.util.xml.PDConfigReader;
import it.csi.csi.wrapper.SystemException;
import it.csi.csi.wrapper.UnrecoverableException;
import it.csi.iride2.iridefed.entity.Ruolo;
import it.csi.iride2.iridefed.exceptions.BadRuoloException;
import it.csi.iride2.policy.entity.Actor;
import it.csi.iride2.policy.entity.Application;
import it.csi.iride2.policy.entity.Identita;
import it.csi.iride2.policy.entity.UseCase;
import it.csi.iride2.policy.exceptions.IdentitaNonAutenticaException;
import it.csi.iride2.policy.exceptions.InternalException;
import it.csi.iride2.policy.exceptions.NoSuchApplicationException;
import it.csi.iride2.policy.exceptions.NoSuchUseCaseException;
import it.csi.iride2.policy.interfaces.PolicyEnforcerBaseService;
import it.csi.pbandi.pbservizit.exception.BEException;
import it.csi.pbandi.pbservizit.pbandisrv.business.gestionedatigenerali.GestioneDatiGeneraliBusinessImpl;
import it.csi.pbandi.pbservizit.pbandisrv.dto.gestionedatigenerali.DatiGeneraliDTO;
import it.csi.pbandi.pbservizit.pbandiutil.common.LoggerUtil;
import it.csi.pbandi.pbservizit.pbandiutil.common.RegoleConstants;
import it.csi.pbandi.pbservizit.security.UserInfoSec;
//import it.csi.pbandi.pbwebrce.dto.DatiGenerali;
//import it.csi.pbandi.pbwebrce.exception.BEException;
//import it.csi.pbandi.pbwebrce.security.UserInfo;
//import it.csi.pbandi.pbwebrce.util.Constants;
import it.csi.pbandi.pbservizit.pbandiutil.commonweb.business.intf.EsecuzioneAttivitaBusinessInterface;
import it.csi.pbandi.pbservizit.pbandiutil.commonweb.business.intf.ProfilazioneBusinessInterface;
import it.csi.pbandi.pbservizit.pbandisrv.interfacecsi.profilazione.ProfilazioneSrv;
import it.csi.pbandi.pbservizit.pbandisrv.util.Constants;

public class SecurityHelper {

	/**  */
	protected static final Logger log = Logger
			.getLogger(Constants.APPLICATION_CODE + ".security");

	//////////////////////////////////////////////////////////////////////////////
	/// Metodi di supporto alla sicurezza.
	//////////////////////////////////////////////////////////////////////////////

	///////////////////////////////////////////////////////////////////////////////
	//// Metodi per la sicurezza basata su IRIDE2
	///////////////////////////////////////////////////////////////////////////////

//	public final static String IRIDE_PEP_RESOURCE = "/META-INF/iride2_pep_defPD_ejb.xml";

//	it.csi.iride2.policy.interfaces.PolicyEnforcerBaseService iride = null;

//	protected PolicyEnforcerBaseService getIridePEP() {
//		if (this.iride == null) {
//			InputStream is = getClass().getResourceAsStream(IRIDE_PEP_RESOURCE);
//			if (is != null) {
//				try {
//					InfoPortaDelegata info = PDConfigReader.read(is);
//					this.iride = (PolicyEnforcerBaseService) PDProxy
//							.newInstance(info);
//					return this.iride;
//				} catch (Exception e) {
//					log
//							.error(
//									"[SecurityHelper::getIridePEP] errore nella parsificazione della configurazione di IRIDE2:"
//											+ e, e);
//					throw new IllegalArgumentException(
//							"errore nella parsificazione della configurazione di IRIDE2");
//				}
//			} else
//				log
//						.error("[SecurityHelper::getIridePEP] configurazione di IRIDE2 non trovata");
//			throw new IllegalArgumentException(
//					"configurazione di IRIDE2 non trovata");
//		} else
//			return this.iride;
//	}

	public static final String IRIDE_ID_SESSIONATTR = "iride2_id";

	protected Identita getCurrentUser(Map session) throws BEException {
		Identita id = (Identita) session.get(IRIDE_ID_SESSIONATTR);
		if (id == null) {
			log
					.error("[SecurityHelper::getCurrentUser] Errore nel reperimento del current user dalla sessione: attributo non trovato");
			throw new IllegalStateException(
					"Errore nel reperimento del current user dalla sessione: attributo non trovato");
		} else {
			return id;
		}
	}

//	protected it.csi.pbandi.pbwebrce.dto.UserInfo getCurrentUserInfo(
//			Map session) throws BEException {
//		it.csi.pbandi.pbwebrce.dto.UserInfo userInfo = (it.csi.pbandi.pbwebrce.dto.UserInfo) session
//				.get("appDatacurrentUser");
//		if (userInfo == null) {
//			log
//					.error("[SecurityHelper::getCurrentUser] Errore nel reperimento del current user dalla sessione: attributo non trovato");
//			throw new IllegalStateException(
//					"Errore nel reperimento del current user dalla sessione: attributo non trovato");
//		} else {
//			return userInfo;
//		}
//	}

	public final static Application IRIDE2_APPLICATION = new Application("PBANDI");;

	public boolean verifyCurrentUserForRole(Map session, String roleCode,
			String domainCode) throws BEException {
		
		log.debug("[SecurityHelper::verifyCurrentUserForRole] overridden");
		return false;
		
//		Identita currentUser = getCurrentUser(session);
//		Ruolo rol = new Ruolo();
//
//		rol.setCodiceRuolo(roleCode);
//		rol.setCodiceDominio(domainCode);
//		try {
//			return getIridePEP().isPersonaInRuolo(getCurrentUser(session), rol);
//		} catch (InternalException e) {
//			log
//					.error(
//							"[SecurityHelper::verifyCurrentUserForRole] Errore interno in verifyCurrentUserForUC:"
//									+ e, e);
//			throw new BEException("Errore interno in verifyCurrentUserForRole:"
//					+ e, e);
//		} catch (IdentitaNonAutenticaException e) {
//			log
//					.error("[SecurityHelper::verifyCurrentUserForRole] identita' non autentica ["
//							+ currentUser + "]," + e);
//			throw new BEException(
//					"verifyCurrentUserForRole: identita' non autentica ["
//							+ currentUser + "]," + e, e);
//		} catch (BadRuoloException e) {
//			log.error("[SecurityHelper::verifyCurrentUserForRole] ruolo ["
//					+ rol + "] non valido" + e);
//			throw new BEException("verifyCurrentUserForRole: ruolo [" + rol
//					+ "] non valido" + e, e);
//		} catch (UnrecoverableException e) {
//			log
//					.error(
//							"[SecurityHelper::verifyCurrentUserForRole] Errore non recuperabile in verifyCurrentUserForUC:"
//									+ e, e);
//			throw new BEException(
//					"Errore non recuperabile in verifyCurrentUserForRole:" + e,
//					e);
//		} catch (SystemException e) {
//			log
//					.error(
//							"[SecurityHelper::verifyCurrentUserForRole] Errore di sistema in verifyCurrentUserForUC:"
//									+ e, e);
//			throw new BEException(
//					"Errore di sistema in verifyCurrentUserForRole:" + e, e);
//		} catch (Exception e) {
//			log
//					.error(
//							"[SecurityHelper::verifyCurrentUserForRole] Errore interno in verifyCurrentUserForUC:"
//									+ e, e);
//			throw new BEException("Errore interno in verifyCurrentUserForRole:"
//					+ e, e);
//		}
	}

	/**
	 * estrae il prefisso dal cod ruolo in formato <idruolo>@<coddominio>
	 * @param codRuolo
	 */
	private String getPrefixFromCodRuolo(String codRuolo) {
		return codRuolo.substring(0, codRuolo.indexOf("@"));
	}

	/**
	 * estrae il codice dominio dal cod ruolo in formato <idruolo>@<coddominio>
	 * @param codRuolo
	 */
	private String getDomainFromCodRuolo(String codRuolo) {
		return codRuolo.substring(codRuolo.indexOf("@") + 1);
	}

	public boolean verifyCurrentUserForUC(Map session, String useCaseCode)
			throws BEException {
		
		log.debug("[SecurityHelper::verifyCurrentUserForUC] overridden");
		return false;
		
//		Identita currentUser = getCurrentUser(session);
//		UseCase uc = new UseCase();
//
//		uc.setAppId(IRIDE2_APPLICATION);
//		uc.setId(useCaseCode);
//		try {
//			it.csi.pbandi.pbwebrce.dto.UserInfo currentUserInfo = getCurrentUserInfo(session);
//			if (currentUserInfo == null
//					|| currentUserInfo.getCodRuolo() == null
//					|| currentUserInfo.getCodRuolo().length() == 0) {
//				// verifica dell'abilitazione allo UC senza tener conto del ruolo corrente
//				return getIridePEP().isPersonaAutorizzataInUseCase(
//						getCurrentUser(session), uc);
//			} else {
//				// verifica dell'abilitazione allo UC tenendo conto del ruolo corrente
//				Ruolo[] ruoliForPersonaInUseCase = getIridePEP()
//						.findRuoliForPersonaInUseCase(currentUser, uc);
//
//				String codDominioRuolo = getPrefixFromCodRuolo(currentUserInfo
//						.getCodRuolo());
//				String codRuoloRuolo = getDomainFromCodRuolo(currentUserInfo
//						.getCodRuolo());
//				boolean authorized = false;
//				Ruolo currentRole = new Ruolo(codRuoloRuolo, codDominioRuolo);
//				for (Ruolo ruolo : ruoliForPersonaInUseCase) {
//					if (ruolo != null
//							&& currentRole.getMnemonico().equals(
//									ruolo.getMnemonico())) {
//						authorized = true;
//						break;
//					}
//				}
//				logger.end();
//				return authorized;
//			}
//		} catch (InternalException e) {
//			log
//					.error(
//							"[SecurityHelper::verifyCurrentUserForUC] Errore interno in verifyCurrentUserForUC:"
//									+ e, e);
//			throw new BEException("Errore interno in verifyCurrentUserForUC:"
//					+ e, e);
//		} catch (IdentitaNonAutenticaException e) {
//			log
//					.error("[SecurityHelper::verifyCurrentUserForUC] identita' non autentica ["
//							+ currentUser + "]," + e);
//			throw new BEException(
//					"verifyCurrentUserForUC: identita' non autentica ["
//							+ currentUser + "]," + e, e);
//		} catch (NoSuchUseCaseException e) {
//			log.error("[SecurityHelper::verifyCurrentUserForUC] use case ["
//					+ uc + "] non valido" + e);
//			throw new BEException("verifyCurrentUserForUC: use case [" + uc
//					+ "] non valido" + e, e);
//		} catch (NoSuchApplicationException e) {
//			log.error("[SecurityHelper::verifyCurrentUserForUC] applicazione "
//					+ IRIDE2_APPLICATION + " non valida" + e);
//			throw new BEException("verifyCurrentUserForUC: applicazione "
//					+ IRIDE2_APPLICATION + " non valida" + e, e);
//		} catch (UnrecoverableException e) {
//			log
//					.error(
//							"[SecurityHelper::verifyCurrentUserForUC] Errore non recuperabile in verifyCurrentUserForUC:"
//									+ e, e);
//			throw new BEException(
//					"Errore non recuperabile in verifyCurrentUserForUC:" + e, e);
//		} catch (SystemException e) {
//			log
//					.error(
//							"[SecurityHelper::verifyCurrentUserForUC] Errore di sistema in verifyCurrentUserForUC:"
//									+ e, e);
//			throw new BEException(
//					"Errore di sistema in verifyCurrentUserForUC:" + e, e);
//		} catch (Exception e) {
//			log
//					.error(
//							"[SecurityHelper::verifyCurrentUserForUC] Errore interno in verifyCurrentUserForUC:"
//									+ e, e);
//			throw new BEException("Errore interno in verifyCurrentUserForUC:"
//					+ e, e);
//		}
	}

	public boolean verifyCurrentUserForActor(Map session, String actorCode)
			throws BEException {
		
		log.debug("[SecurityHelper::verifyCurrentUserForActor] overridden");
		return false;
		
//		Identita currentUser = getCurrentUser(session);
//		Actor act = new Actor();
//
//		act.setAppId(IRIDE2_APPLICATION);
//		act.setId(actorCode);
//		try {
//			Actor[] actors = getIridePEP().findActorsForPersonaInApplication(
//					currentUser, IRIDE2_APPLICATION);
//			if (actors != null) {
//				for (int i = 0; i < actors.length; i++) {
//					Actor actor = actors[i];
//					if (actor.equals(act))
//						return true;
//				}
//				return false;
//			} else
//				return false;
//		} catch (InternalException e) {
//			log
//					.error("[SecurityHelper::verifyCurentUserForActor] Errore interno in verifyCurrentUserForUC:"
//							+ e);
//			throw new BEException("Errore interno in verifyCurrentUserForUC:"
//					+ e, e);
//		} catch (IdentitaNonAutenticaException e) {
//			log.error(
//					"[SecurityHelper::verifyCurentUserForActor] identita' non autentica ["
//							+ currentUser + "]," + e, e);
//			throw new BEException(
//					"verifyCurrentUserForUC: identita' non autentica ["
//							+ currentUser + "]," + e, e);
//		} catch (NoSuchApplicationException e) {
//			log.error(
//					"[SecurityHelper::verifyCurentUserForActor] applicazione "
//							+ IRIDE2_APPLICATION + " non valida" + e, e);
//			throw new BEException("verifyCurrentUserForUC: applicazione "
//					+ IRIDE2_APPLICATION + " non valida" + e, e);
//		} catch (UnrecoverableException e) {
//			log
//					.error(
//							"[SecurityHelper::verifyCurentUserForActor] Errore non recuperabile in verifyCurrentUserForUC:"
//									+ e, e);
//			throw new BEException(
//					"Errore non recuperabile in verifyCurrentUserForUC:" + e, e);
//		} catch (SystemException e) {
//			log
//					.error(
//							"[SecurityHelper::verifyCurentUserForActor] Errore di sistema in verifyCurrentUserForUC:"
//									+ e, e);
//			throw new BEException(
//					"Errore di sistema in verifyCurrentUserForUC:" + e, e);
//		} catch (Exception e) {
//			log
//					.error(
//							"[SecurityHelper::verifyCurentUserForActor] Errore interno in verifyCurrentUserForUC:"
//									+ e, e);
//			throw new BEException("Errore interno in verifyCurrentUserForUC:"
//					+ e, e);
//		}
	}

	///////////////////////////////////////////////////////////////////////////////
	//// Metodi per la sicurezza custom
	///////////////////////////////////////////////////////////////////////////////

	public boolean customCheckIsPercRibassoAstaVisible(Map session)
			throws BEException {
		/*PROTECTED REGION ID(R-504192927) ENABLED START*//// inserire qui il codice del controllo custom
		try {

			return percRibassoAstaVisible(session);
		} catch (Exception ex) {
			log
					.error(
							"[SecurityHelper::customCheckIsPercRibassoAstaVisible] Errore durante l'esecuzione del metodo",
							ex);
			throw new BEException(
					"Errore durante l'esecuzione del metodo customCheckIsPercRibassoAstaVisible",
					ex);
		}
		/*PROTECTED REGION END*/
	}
	public boolean customCheckIsPercRibassoAstaConcludiPropostaVisible(
			Map session) throws BEException {
		/*PROTECTED REGION ID(R1611801630) ENABLED START*//// inserire qui il codice del controllo custom
		try {
			return percRibassoAstaVisible(session);
		} catch (Exception ex) {
			log
					.error(
							"[SecurityHelper::customCheckIsPercRibassoAstaConcludiPropostaVisible] Errore durante l'esecuzione del metodo",
							ex);
			throw new BEException(
					"Errore durante l'esecuzione del metodo customCheckIsPercRibassoAstaConcludiPropostaVisible",
					ex);
		}
		/*PROTECTED REGION END*/
	}
//	public boolean customCheckIsComboDelegatiVisible(Map session)
//			throws BEException {
//		/*PROTECTED REGION ID(R-731366308) ENABLED START*//// inserire qui il codice del controllo custom
//		try {
//			UserInfo currentUser = getCurrentUserInfo(session);
//			DatiGenerali datiGenerali = (DatiGenerali) session.get(BackEndFacade.APPDATA_DATIGENERALI_CODE);
//			Boolean invioDigitale = getProfilazioneSrv()
//					.isRegolaApplicabileForProgetto(currentUser.getIdUtente(),
//							currentUser.getIdIride(),
//							datiGenerali.getProgetto().getId(),
//							RegoleConstants.BR42_ABILITAZIONE_ALLEGA_FILE);
//			logger.info("++++ invio digitale: " + invioDigitale + "+++");
//			return invioDigitale;
//		} catch (Exception ex) {
//			log
//					.error(
//							"[SecurityHelper::customCheckIsComboDelegatiVisible] Errore durante l'esecuzione del metodo",
//							ex);
//			throw new BEException(
//					"Errore durante l'esecuzione del metodo customCheckIsComboDelegatiVisible",
//					ex);
//		}
//		/*PROTECTED REGION END*/
//	}
//	public boolean customCheckIsPercRibassoAstaVisibleInRimodulazione(
//			Map session) throws BEException {
//		/*PROTECTED REGION ID(R1289617682) ENABLED START*//// inserire qui il codice del controllo custom
//		try {
//			return percRibassoAstaVisible(session);
//		} catch (Exception ex) {
//			log
//					.error(
//							"[SecurityHelper::customCheckIsPercRibassoAstaVisibleInRimodulazione] Errore durante l'esecuzione del metodo",
//							ex);
//			throw new BEException(
//					"Errore durante l'esecuzione del metodo customCheckIsPercRibassoAstaVisibleInRimodulazione",
//					ex);
//		}
//		/*PROTECTED REGION END*/
//	}
//	public boolean customCheckIsPercRibassoAstaPropostaVisibleInRimodulazione(
//			Map session) throws BEException {
//		/*PROTECTED REGION ID(R1343339110) ENABLED START*//// inserire qui il codice del controllo custom
//		try {
//
//			return percRibassoAstaUltimaPropostaVisible(session);
//		} catch (Exception ex) {
//			log
//					.error(
//							"[SecurityHelper::customCheckIsPercRibassoAstaPropostaVisibleInRimodulazione] Errore durante l'esecuzione del metodo",
//							ex);
//			throw new BEException(
//					"Errore durante l'esecuzione del metodo customCheckIsPercRibassoAstaPropostaVisibleInRimodulazione",
//					ex);
//		}
//		/*PROTECTED REGION END*/
//	}
//	public boolean customCheckIsPercRibassoAstaConcludiRimodulazioneVisibleInRimodulazione(
//			Map session) throws BEException {
//		/*PROTECTED REGION ID(R-1661099175) ENABLED START*//// inserire qui il codice del controllo custom
//		try {
//			return percRibassoAstaVisible(session);
//		} catch (Exception ex) {
//			log
//					.error(
//							"[SecurityHelper::customCheckIsPercRibassoAstaConcludiRimodulazioneVisibleInRimodulazione] Errore durante l'esecuzione del metodo",
//							ex);
//			throw new BEException(
//					"Errore durante l'esecuzione del metodo customCheckIsPercRibassoAstaConcludiRimodulazioneVisibleInRimodulazione",
//					ex);
//		}
//		/*PROTECTED REGION END*/
//	}

//	public boolean customCheckIsMnuPropostaRimodulazioneVisible(Map session)
//			throws BEException {
//		/*PROTECTED REGION ID(R424998177) ENABLED START*//// inserire qui il codice del controllo custom
//		try {
//			/*
//			 * Visibile se l'attivita' corrente e'
//			 * quella di proposta rimodulazione conto economico
//			 */
//			boolean isVisible = false;
//			IstanzaAttivitaCorrenteDTO currentActivity = getEsecuzioneAttivitaBusiness()
//					.getCurrentActivity(session);
//
//			isVisible = currentActivity
//					.getTaskName()
//					.equalsIgnoreCase(
//							it.csi.pbandi.pbandiutil.common.Constants.TASKNAME_PROPOSTA_RIMODULAZIONE);
//
//			return isVisible;
//		} catch (Exception ex) {
//			log
//					.error(
//							"[SecurityHelper::customCheckIsMnuPropostaRimodulazioneVisible] Errore durante l'esecuzione del metodo",
//							ex);
//			throw new BEException(
//					"Errore durante l'esecuzione del metodo customCheckIsMnuPropostaRimodulazioneVisible",
//					ex);
//		}
//		/*PROTECTED REGION END*/
//	}

//	public boolean customCheckIsMnuRimodulazioneVisible(Map session)
//			throws BEException {
//		/*PROTECTED REGION ID(R-2110261043) ENABLED START*//// inserire qui il codice del controllo custom
//		try {
//			/*
//			 * Visibile se l'attivita' corrente e'
//			 * quella di rimodulazione conto economico
//			 */
//			boolean isVisible = false;
//			IstanzaAttivitaCorrenteDTO currentActivity = getEsecuzioneAttivitaBusiness()
//					.getCurrentActivity(session);
//
//			isVisible = currentActivity
//					.getTaskName()
//					.equalsIgnoreCase(
//							it.csi.pbandi.pbandiutil.common.Constants.TASKNAME_RIMODULAZIONE_CONTO_ECONOMICO);
//
//			return isVisible;
//		} catch (Exception ex) {
//			log
//					.error(
//							"[SecurityHelper::customCheckIsMnuRimodulazioneVisible] Errore durante l'esecuzione del metodo",
//							ex);
//			throw new BEException(
//					"Errore durante l'esecuzione del metodo customCheckIsMnuRimodulazioneVisible",
//					ex);
//		}
//		/*PROTECTED REGION END*/
//	}

//	public boolean customCheckIsMnuNotificaVisible(Map session)
//			throws BEException {
//		/*PROTECTED REGION ID(R-964869400) ENABLED START*//// inserire qui il codice del controllo custom
//		try {
//			boolean isVisible = false;
//			IstanzaAttivitaCorrenteDTO currentActivity = getEsecuzioneAttivitaBusiness()
//					.getCurrentActivity(session);
//
//			isVisible = currentActivity
//					.getTaskName()
//					.startsWith(
//							it.csi.pbandi.pbandiutil.common.Constants.TASKNAME_NOTIFICA_PREFIX);
//
//			return isVisible;
//		} catch (Exception ex) {
//			log
//					.error(
//							"[SecurityHelper::customCheckIsMnuNotificaVisible] Errore durante l'esecuzione del metodo",
//							ex);
//			throw new BEException(
//					"Errore durante l'esecuzione del metodo customCheckIsMnuNotificaVisible",
//					ex);
//		}
//		/*PROTECTED REGION END*/
//	}

//	public boolean customCheckIsMnuArchivioFileVisible(Map session)
//			throws BEException {
//		/*PROTECTED REGION ID(R-1677485958) ENABLED START*//// inserire qui il codice del controllo custom
//		try {
//			return true;
//		} catch (Exception ex) {
//			log
//					.error(
//							"[SecurityHelper::customCheckIsMnuArchivioFileVisible] Errore durante l'esecuzione del metodo",
//							ex);
//			throw new BEException(
//					"Errore durante l'esecuzione del metodo customCheckIsMnuArchivioFileVisible",
//					ex);
//		}
//		/*PROTECTED REGION END*/
//	}

//	public boolean customCheckIsMnuDocumentiDiProgettoVisible(Map session)
//			throws BEException {
//		/*PROTECTED REGION ID(R-692241458) ENABLED START*//// inserire qui il codice del controllo custom
//		try {
//			return true;
//		} catch (Exception ex) {
//			log
//					.error(
//							"[SecurityHelper::customCheckIsMnuDocumentiDiProgettoVisible] Errore durante l'esecuzione del metodo",
//							ex);
//			throw new BEException(
//					"Errore durante l'esecuzione del metodo customCheckIsMnuDocumentiDiProgettoVisible",
//					ex);
//		}
//		/*PROTECTED REGION END*/
//	}

	//////////////////////////////////////////////////////////////////////////////
	/// Property aggiuntive del bean
	//////////////////////////////////////////////////////////////////////////////
	/*PROTECTED REGION ID(R-1742205394) ENABLED START*/
	private EsecuzioneAttivitaBusinessInterface esecuzioneAttivitaBusiness;
	private LoggerUtil logger;
	private ProfilazioneSrv profilazioneSrv;

	public void setEsecuzioneAttivitaBusiness(
			EsecuzioneAttivitaBusinessInterface esecuzioneAttivitaBusiness) {
		this.esecuzioneAttivitaBusiness = esecuzioneAttivitaBusiness;
	}

	public EsecuzioneAttivitaBusinessInterface getEsecuzioneAttivitaBusiness() {
		return esecuzioneAttivitaBusiness;
	}
	public LoggerUtil getLogger() {
		return logger;
	}

	public void setLogger(LoggerUtil logger) {
		this.logger = logger;
	}

	public ProfilazioneSrv getProfilazioneSrv() {
		return profilazioneSrv;
	}

	public void setProfilazioneSrv(ProfilazioneSrv profilazioneSrv) {
		this.profilazioneSrv = profilazioneSrv;
	}

	private boolean percRibassoAstaVisible(Map session) {
		boolean isVisible = false;
		/*
		 * VN: La percentuale e' visualizzabile
		 * solo se e' riferita ad un ribasso d' asta
		 */
		Boolean flagRibassoAsta = (java.lang.Boolean) (session
				.get("appDataflagRibassoAsta"));
		if (flagRibassoAsta == null || !flagRibassoAsta)
			isVisible = false;
		else
			isVisible = true;
		return isVisible;
	}

	public boolean percRibassoAstaUltimaPropostaVisible(Map session) {
		boolean isVisible = false;
		/*
		 * VN: la percentuale e' visualizzabile solo se e'
		 * l' ultima proposta di rimodulazione e' stata
		 * eseguita per ribasso d'asta
		 */
		Boolean flagRibassoAstaUltimaProposta = (java.lang.Boolean) session
				.get("appDataflagUltimoRibassoAstaInProposta");
		if (flagRibassoAstaUltimaProposta == null
				|| !flagRibassoAstaUltimaProposta)
			isVisible = false;
		else
			isVisible = true;
		return isVisible;
	}
	/*PROTECTED REGION END*/
	
}
