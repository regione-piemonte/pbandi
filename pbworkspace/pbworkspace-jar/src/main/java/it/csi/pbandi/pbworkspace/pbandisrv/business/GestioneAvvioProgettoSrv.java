/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbworkspace.pbandisrv.business;

import it.csi.pbandi.pbworkspace.pbandisrv.dto.gestioneavvioprogetto.*;
import it.csi.pbandi.pbworkspace.pbandisrv.exception.*;

public interface GestioneAvvioProgettoSrv {
	
	public it.csi.pbandi.pbworkspace.pbandisrv.dto.gestioneavvioprogetto.ProgettoDTO[] findProgetti(

	java.lang.Long idUtente,

	java.lang.String identitaIride,

	java.lang.String codUtente,

	it.csi.pbandi.pbworkspace.pbandisrv.dto.gestioneavvioprogetto.ProgettoDTO progetto,
	
	Boolean visualizzaSoloSchedeProgetto

	)
			throws it.csi.csi.wrapper.CSIException,
			it.csi.csi.wrapper.SystemException,
			it.csi.csi.wrapper.UnrecoverableException

			,
			it.csi.pbandi.pbworkspace.pbandisrv.exception.GestioneAvvioProgettoException;



	
	public it.csi.pbandi.pbworkspace.pbandisrv.dto.gestioneavvioprogetto.EsitoAvvioProcessoDTO[] avviaProgetto(

	java.lang.Long idUtente,

	java.lang.String identitaIride,

	java.lang.String codUtente,

	java.lang.String idDefinizioneProcesso,

	java.lang.Long[] idProgetti,
	
    Boolean visualizzaSoloSchedeProgetto

	)
			throws it.csi.csi.wrapper.CSIException,
			it.csi.csi.wrapper.SystemException,
			it.csi.csi.wrapper.UnrecoverableException

			,
			it.csi.pbandi.pbworkspace.pbandisrv.exception.GestioneAvvioProgettoException;
	
	public java.lang.Boolean hasProgettiSifAvviati(

	java.lang.Long idUtente,

	java.lang.String identitaIride,

	java.lang.Long progrBandoLinea

	)
			throws it.csi.csi.wrapper.CSIException,
			it.csi.csi.wrapper.SystemException,
			it.csi.csi.wrapper.UnrecoverableException

			,
			it.csi.pbandi.pbworkspace.pbandisrv.exception.GestioneAvvioProgettoException;

	public it.csi.pbandi.pbworkspace.pbandisrv.dto.gestioneavvioprogetto.EsitoSchedaProgettoDTO caricaSchedaProgetto(

			java.lang.Long idUtente,

			java.lang.String identitaIride,

			it.csi.pbandi.pbworkspace.pbandisrv.dto.gestioneavvioprogetto.SchedaProgettoDTO schedaProgetto

	)
			throws it.csi.csi.wrapper.CSIException,
			it.csi.csi.wrapper.SystemException,
			it.csi.csi.wrapper.UnrecoverableException

			,
			it.csi.pbandi.pbworkspace.pbandisrv.exception.GestioneAvvioProgettoException;

	public it.csi.pbandi.pbworkspace.pbandisrv.dto.gestioneavvioprogetto.BeneficiarioCspDTO[] ricercaBeneficiarioCsp(

	java.lang.Long idUtente,

	java.lang.String identitaIride,

	java.lang.String codiceFiscale

	)
			throws it.csi.csi.wrapper.CSIException,
			it.csi.csi.wrapper.SystemException,
			it.csi.csi.wrapper.UnrecoverableException

			,
			it.csi.pbandi.pbworkspace.pbandisrv.exception.GestioneAvvioProgettoException;

	
	public it.csi.pbandi.pbworkspace.pbandisrv.dto.gestioneavvioprogetto.EsitoSchedaProgettoDTO salvaSchedaProgetto(

			java.lang.Long idUtente,

			java.lang.String identitaIride,

			it.csi.pbandi.pbworkspace.pbandisrv.dto.gestioneavvioprogetto.SchedaProgettoDTO schedaProgetto,

			java.lang.String flagDettaglioCup,
			
			 boolean datiCompletiPerAvvio

	)
			throws it.csi.csi.wrapper.CSIException,
			it.csi.csi.wrapper.SystemException,
			it.csi.csi.wrapper.UnrecoverableException

			,
			it.csi.pbandi.pbworkspace.pbandisrv.exception.GestioneAvvioProgettoException;

	public it.csi.pbandi.pbworkspace.pbandisrv.dto.gestioneavvioprogetto.RapprLegaleCspDTO[] ricercaRapprLegaleCsp(

	java.lang.Long idUtente,

	java.lang.String identitaIride,

	java.lang.String codiceFiscale

	)
			throws it.csi.csi.wrapper.CSIException,
			it.csi.csi.wrapper.SystemException,
			it.csi.csi.wrapper.UnrecoverableException

			,
			it.csi.pbandi.pbworkspace.pbandisrv.exception.GestioneAvvioProgettoException;



	/*
	public it.csi.pbandi.pbworkspace.pbandisrv.dto.gestioneavvioprogetto.EsitoSchedaProgettoDTO caricaDettaglioCup(

			java.lang.Long idUtente,

			java.lang.String identitaIride,

			it.csi.pbandi.pbworkspace.pbandisrv.dto.gestioneavvioprogetto.SchedaProgettoDTO schedaProgetto

	)
			throws it.csi.csi.wrapper.CSIException,
			it.csi.csi.wrapper.SystemException,
			it.csi.csi.wrapper.UnrecoverableException

			,
			it.csi.pbandi.pbworkspace.pbandisrv.exception.gestioneavvioprogetto.GestioneAvvioProgettoException;


	public it.csi.pbandi.pbworkspace.pbandisrv.dto.gestioneavvioprogetto.EsitoOperazioneDTO verificaCup(

	java.lang.Long idUtente,

	java.lang.String identitaIride,

	java.lang.String codiceCup,

	java.lang.String idCspProgetto

	)
			throws it.csi.csi.wrapper.CSIException,
			it.csi.csi.wrapper.SystemException,
			it.csi.csi.wrapper.UnrecoverableException

			,
			it.csi.pbandi.pbworkspace.pbandisrv.exception.gestioneavvioprogetto.GestioneAvvioProgettoException;


	public it.csi.pbandi.pbworkspace.pbandisrv.dto.gestioneavvioprogetto.EsitoSoggettoDTO caricaInfoDirezioneRegionale(

	java.lang.Long idUtente,

	java.lang.String identitaIride,

	it.csi.pbandi.pbworkspace.pbandisrv.dto.gestioneavvioprogetto.SoggettoPGDTO datiPG

	)
			throws it.csi.csi.wrapper.CSIException,
			it.csi.csi.wrapper.SystemException,
			it.csi.csi.wrapper.UnrecoverableException

			,
			it.csi.pbandi.pbworkspace.pbandisrv.exception.gestioneavvioprogetto.GestioneAvvioProgettoException;


	public void prenotaAvvioProgetti(

	java.lang.Long idUtente,

	java.lang.String identitaIride,

	java.lang.Long[] idProgetti

	)
			throws it.csi.csi.wrapper.CSIException,
			it.csi.csi.wrapper.SystemException,
			it.csi.csi.wrapper.UnrecoverableException

			,
			it.csi.pbandi.pbworkspace.pbandisrv.exception.gestioneavvioprogetto.GestioneAvvioProgettoException;







	public java.lang.Boolean isBandoFinpiemonte(

	java.lang.Long idUtente,

	java.lang.String identitaIride,

	java.lang.Long progrBandoLinea

	)
			throws it.csi.csi.wrapper.CSIException,
			it.csi.csi.wrapper.SystemException,
			it.csi.csi.wrapper.UnrecoverableException

			,
			it.csi.pbandi.pbworkspace.pbandisrv.exception.gestioneavvioprogetto.GestioneAvvioProgettoException;


	public it.csi.pbandi.pbworkspace.pbandisrv.dto.gestioneavvioprogetto.EsitoSoggettoDTO caricaInfoPubblicaAmministrazione(

	java.lang.Long idUtente,

	java.lang.String identitaIride,

	it.csi.pbandi.pbworkspace.pbandisrv.dto.gestioneavvioprogetto.SoggettoPGDTO datiPG

	)
			throws it.csi.csi.wrapper.CSIException,
			it.csi.csi.wrapper.SystemException,
			it.csi.csi.wrapper.UnrecoverableException

			,
			it.csi.pbandi.pbworkspace.pbandisrv.exception.gestioneavvioprogetto.GestioneAvvioProgettoException;
	 */
}
