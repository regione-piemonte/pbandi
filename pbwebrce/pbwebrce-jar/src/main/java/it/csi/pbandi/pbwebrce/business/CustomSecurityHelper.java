/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebrce.business;

import static it.csi.pbandi.pbservizit.pbandiutil.common.BeanUtil.ACTIVITY_ASSIGNMENT_OPERATOR;
import static it.csi.pbandi.pbservizit.pbandiutil.common.BeanUtil.ACTIVITY_TOKEN_SEPARATOR;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import it.csi.csi.wrapper.CSIException;
import it.csi.csi.wrapper.SystemException;
import it.csi.csi.wrapper.UnrecoverableException;
import it.csi.pbandi.pbservizit.pbandisrv.exception.profilazione.UtenteException;
import it.csi.pbandi.pbservizit.pbandisrv.interfacecsi.profilazione.ProfilazioneSrv;
import it.csi.pbandi.pbservizit.security.UserInfoSec;
import it.csi.pbandi.pbwebrce.exception.BEException;
import it.csi.pbandi.pbwebrce.util.Constants;

public class CustomSecurityHelper extends it.csi.pbandi.pbwebrce.business.SecurityHelper{

	protected static final Logger log = Logger.getLogger(Constants.APPLICATION_CODE + ".security");

	@Autowired
	private ProfilazioneSrv profilazioneSrv;
	
//	private ProfilazioneBusinessImpl profilazioneBusinessImpl;

	@Override
	public boolean verifyCurrentUserForActor(@SuppressWarnings("rawtypes") Map session, String actorCode)
			throws BEException {
		log.debug("[CustomSecurityHelper::verifyCurrentUserForActor] BEGIN-END");
		return false;
	}

	@Override
	public boolean verifyCurrentUserForRole(@SuppressWarnings("rawtypes") Map session, String roleCode,
			String domainCode) throws BEException {
		log.debug("[CustomSecurityHelper::verifyCurrentUserForRole] BEGIN-END");
		return false;
	}

// PK : questo metodo prevede di trovare in sessione l'oggetto "appDatacurrentUser"
//	
//	public boolean verifyCurrentUserForUC(@SuppressWarnings("rawtypes") Map session, String useCaseCode)
//			throws BEException {
//		log.debug("[CustomSecurityHelper::verifyCurrentUserForUC] BEGIN-END, useCaseCode="+useCaseCode);
//		//return getProfilazioneCommonBusiness().hasPermessoCurrentUser(useCaseCode);
//		log.debug("[CustomSecurityHelper::verifyCurrentUserForUC] profilazioneBusinessImpl="+profilazioneBusinessImpl);
//		return profilazioneBusinessImpl.hasPermessoCurrentUser(useCaseCode);
//	}


//	public void setProfilazioneCommonBusiness(ProfilazioneBusinessInterface profilazioneCommonBusiness) {
//		this.profilazioneCommonBusiness = profilazioneCommonBusiness;
//	}
//
//	public ProfilazioneBusinessInterface getProfilazioneCommonBusiness() {
//		return profilazioneCommonBusiness;
//	}

	public boolean verifyCurrentUserForUC(UserInfoSec userInfo, String useCaseCode) throws UtenteException, SystemException, UnrecoverableException, CSIException {
		log.debug("[CustomSecurityHelper::verifyCurrentUserForUC] BEGIN-END, useCaseCode="+useCaseCode);
		
		log.debug("[CustomSecurityHelper::verifyCurrentUserForUC] userInfo="+userInfo);
		
		String eq = ACTIVITY_ASSIGNMENT_OPERATOR; //"£a£";
		String sep = ACTIVITY_TOKEN_SEPARATOR; //"£A£";
		
		//PK Ricostruisco la stringa che usa il vecchio sistema....
		
		String identitaDigitale = "ruoloHelp" + eq + userInfo.getRuoloHelp();
		identitaDigitale += sep + "nome" + eq + userInfo.getNome();
		identitaDigitale += sep + "taskName" + eq + "null";
		identitaDigitale += sep + "class" + eq + "";
		identitaDigitale += sep + "descBreveTipoAnagrafica" + eq + userInfo.getCodiceRuolo();
		identitaDigitale += sep + "cognome" + eq + userInfo.getCognome();
		identitaDigitale += sep + "idSoggetto" + eq + userInfo.getIdSoggetto();
		identitaDigitale += sep + "idUtente" + eq + userInfo.getIdUtente();
		identitaDigitale += sep + "codFiscale" + eq + userInfo.getCodFisc();
		String codUtenteFlux = "";
		if(userInfo.getIdSoggetto()!=null)
			codUtenteFlux += userInfo.getIdSoggetto();
		if(userInfo.getCodFisc()!=null) {
			if(userInfo.getIdSoggetto()!=null) {
				codUtenteFlux += "_";
			}
			codUtenteFlux += userInfo.getCodFisc();
		}
		if(userInfo.getCodiceRuolo()!=null) {
			codUtenteFlux += "@" + userInfo.getCodiceRuolo();
		}
		identitaDigitale += sep + "codUtenteFlux" + eq + codUtenteFlux;  
		identitaDigitale += sep + "idIstanzaAttivitaProcesso" + eq + "null";
		identitaDigitale += sep + "identitaIride" + eq + userInfo.getIdIride();
		
		log.debug("[CustomSecurityHelper::verifyCurrentUserForUC] identitaDigitale="+identitaDigitale);
		
		return profilazioneSrv.hasPermesso(userInfo.getIdUtente(), identitaDigitale, userInfo.getCodiceRuolo(), useCaseCode);
	}
	
}
