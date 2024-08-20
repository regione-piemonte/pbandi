/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebrce.business.intf;

import it.csi.pbandi.pbservizit.dto.profilazione.Beneficiario;
import it.csi.pbandi.pbservizit.dto.profilazione.UserInfoDTO;
import it.csi.pbandi.pbservizit.exception.UtenteException;
import it.csi.pbandi.pbwebrce.dto.profilazione.ConsensoInvioComunicazioneDTO;
import it.csi.pbandi.pbwebrce.dto.profilazione.EsitoControlloAccessoDTO;
import it.csi.pbandi.pbwebrce.dto.profilazione.FiltroBeneficiarioProgettoSoggettoRuoloDTO;
import it.csi.pbandi.pbwebrce.dto.profilazione.ProgettoDTO;
import it.csi.pbandi.pbwebrce.dto.profilazione.UserParamsDTO;
import it.csi.pbandi.pbwebrce.exception.ProfilazioneException;

public interface ProfilazioneSrv {
//	public UserInfoDTO getInfoUtente(Long idUtente,String identitaDigitale) throws it.csi.csi.wrapper.CSIException,
//					it.csi.csi.wrapper.SystemException, it.csi.csi.wrapper.UnrecoverableException, UtenteException;
//
//	public Beneficiario[] findBeneficiari(Long idUtente, String identitaDigitale, String codiceFiscale, String ruoloIride) throws it.csi.csi.wrapper.CSIException,
//							it.csi.csi.wrapper.SystemException,
//								it.csi.csi.wrapper.UnrecoverableException, ProfilazioneException;
//
//	public Beneficiario[] findBeneficiariProgetto(Long idUtente,String identitaDigitale,Long idProgetto)throws it.csi.csi.wrapper.CSIException,
//			it.csi.csi.wrapper.SystemException,
//			it.csi.csi.wrapper.UnrecoverableException, ProfilazioneException;

	public java.lang.Boolean isRegolaApplicabileForBandoLinea(Long idUtente, String identitaDigitale, Long idBando, String codRegola) throws it.csi.csi.wrapper.CSIException,
			it.csi.csi.wrapper.SystemException,
			it.csi.csi.wrapper.UnrecoverableException;


//	public UserInfoDTO getMiniAppInfoUtente(Long idUtente,String identitaDigitale,UserParamsDTO userParams)
//			throws it.csi.csi.wrapper.CSIException,
//			it.csi.csi.wrapper.SystemException,
//			it.csi.csi.wrapper.UnrecoverableException, ProfilazioneException;
//
//	public Beneficiario[] findBeneficiario(Long idUtente, String identitaDigitale, Long idSoggettoBeneficiario)
//			throws it.csi.csi.wrapper.CSIException,
//			it.csi.csi.wrapper.SystemException,
//			it.csi.csi.wrapper.UnrecoverableException, ProfilazioneException;
//
//	public Beneficiario[] findBeneficiariByProgettoSoggettoRuolo(Long idUtente, String identitaDigitale,FiltroBeneficiarioProgettoSoggettoRuoloDTO filtro)
//			throws it.csi.csi.wrapper.CSIException,
//			it.csi.csi.wrapper.SystemException,
//			it.csi.csi.wrapper.UnrecoverableException, ProfilazioneException;
//
//	public ProgettoDTO[] findProgettiByBeneficiarioSoggettoRuolo(Long idUtente, String identitaDigitale, FiltroBeneficiarioProgettoSoggettoRuoloDTO filtro) throws it.csi.csi.wrapper.CSIException,
//			it.csi.csi.wrapper.SystemException,
//			it.csi.csi.wrapper.UnrecoverableException, UtenteException;
//
//	public java.lang.Boolean hasPermesso(Long idUtente,String identitaDigitale,String descBreveRuolo,String descBrevePermesso) throws it.csi.csi.wrapper.CSIException,
//			it.csi.csi.wrapper.SystemException,
//			it.csi.csi.wrapper.UnrecoverableException, UtenteException;
//
//	public EsitoControlloAccessoDTO controllaStoricizzaAccesso(Long idUtente,String identitaDigitale,Long tipoAccesso, Boolean isSpid) throws it.csi.csi.wrapper.CSIException,
//			it.csi.csi.wrapper.SystemException,
//			it.csi.csi.wrapper.UnrecoverableException, UtenteException;
//
//	public void memorizzaIride(Long idUtente, String identitaDigitale, String idShib, String idIride, String sessionId, String msg) throws it.csi.csi.wrapper.CSIException,
//			it.csi.csi.wrapper.SystemException,
//			it.csi.csi.wrapper.UnrecoverableException, UtenteException;
//
//	public void tracciaControlloCookie(Long idUtente, String identitaDigitale, String msg ) throws it.csi.csi.wrapper.CSIException,
//			it.csi.csi.wrapper.SystemException,
//			it.csi.csi.wrapper.UnrecoverableException , UtenteException;
//
//	public java.lang.Boolean existBandolineaWithRegolaForBeneficiario( Long idUtente, String identitaDigitale, Long idSoggettoBeneficiario, String codiceRegola ) throws it.csi.csi.wrapper.CSIException,
//			it.csi.csi.wrapper.SystemException,
//			it.csi.csi.wrapper.UnrecoverableException , UtenteException;
//
//	public Boolean isRegolaApplicabileForProgetto( Long idUtente, String identitaDigitale, Long idProgetto, String codRegola
//
//	) throws it.csi.csi.wrapper.CSIException,
//			it.csi.csi.wrapper.SystemException,
//			it.csi.csi.wrapper.UnrecoverableException;
//
//	public ConsensoInvioComunicazioneDTO findConsensoInvioComunicazione( Long idUtente, String identitaDigitale ) throws it.csi.csi.wrapper.CSIException,
//			it.csi.csi.wrapper.SystemException,
//			it.csi.csi.wrapper.UnrecoverableException , UtenteException;
//
//	public java.lang.Boolean salvaConsensoInvioComunicazione( Long idUtente, String identitaDigitale, ConsensoInvioComunicazioneDTO consensoInvioComunicazioneDTO ) throws it.csi.csi.wrapper.CSIException,
//			it.csi.csi.wrapper.SystemException,
//			it.csi.csi.wrapper.UnrecoverableException , UtenteException;
}
