/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebcert.business.pbandisrv.interfacecsi.certificazione;

import java.util.List;

import it.csi.csi.wrapper.CSIException;
import it.csi.csi.wrapper.SystemException;
import it.csi.csi.wrapper.UnrecoverableException;
import it.csi.pbandi.pbservizit.pbandisrv.dto.certificazione.*;
import it.csi.pbandi.pbservizit.pbandisrv.exception.certificazione.CertificazioneException;



public interface CertificazioneSrv {

	public java.lang.String lanciaCreazionePropostaCertificazione(

			java.lang.Long idUtente,

			java.lang.String identitaDigitale,

			it.csi.pbandi.pbservizit.pbandisrv.dto.certificazione.PropostaCertificazioneDTO propostaCertificazione,

			java.lang.Long[] idLineeDiIntervento
			
			)
			throws it.csi.csi.wrapper.CSIException,
			it.csi.csi.wrapper.SystemException,
			it.csi.csi.wrapper.UnrecoverableException

			,
			it.csi.pbandi.pbwebcert.business.pbandisrv.exception.certificazione.CertificazioneException;
	
	public java.lang.String inviaReportPostGestione(

			java.lang.Long idUtente,

			java.lang.String identitaDigitale,

			it.csi.pbandi.pbservizit.pbandisrv.dto.certificazione.PropostaCertificazioneDTO propostaCertificazione,

			java.lang.Long[] idLineeDiIntervento)
			throws it.csi.csi.wrapper.CSIException,
			it.csi.csi.wrapper.SystemException,
			it.csi.csi.wrapper.UnrecoverableException

			,
			it.csi.pbandi.pbwebcert.business.pbandisrv.exception.certificazione.CertificazioneException;

	public it.csi.pbandi.pbservizit.pbandisrv.dto.certificazione.PropostaCertificazioneDTO[] findProposteCertificazione(

	java.lang.Long idUtente,

	java.lang.String identitaDigitale,

	List<String> statiProposte

	)
			throws it.csi.csi.wrapper.CSIException,
			it.csi.csi.wrapper.SystemException,
			it.csi.csi.wrapper.UnrecoverableException

			,
			it.csi.pbandi.pbwebcert.business.pbandisrv.exception.certificazione.CertificazioneException;
	
	public java.lang.Long findIdLineaDiInterventoFromProposta(java.lang.Long idProposta) 
			throws it.csi.csi.wrapper.CSIException,
			it.csi.csi.wrapper.SystemException,
			it.csi.csi.wrapper.UnrecoverableException,
			it.csi.pbandi.pbwebcert.business.pbandisrv.exception.certificazione.CertificazioneException;
		
}
