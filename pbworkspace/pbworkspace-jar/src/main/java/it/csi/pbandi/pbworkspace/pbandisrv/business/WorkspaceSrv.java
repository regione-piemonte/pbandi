/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbworkspace.pbandisrv.business;

import it.csi.pbandi.pbworkspace.pbandisrv.dto.workspace.*;
import it.csi.pbandi.pbworkspace.pbandisrv.exception.*;


public interface WorkspaceSrv {
	
	public it.csi.pbandi.pbworkspace.pbandisrv.dto.workspace.DefinizioneProcessoDTO[] ricercaDefinizioneProcesso(

			java.lang.Long idUtente,

			java.lang.String identitaDigitale,

			java.lang.String codUtente,

			it.csi.pbandi.pbworkspace.pbandisrv.dto.workspace.DefinizioneProcessoDTO processoRicercato

	) throws it.csi.csi.wrapper.CSIException,
			it.csi.csi.wrapper.SystemException,
			it.csi.csi.wrapper.UnrecoverableException

			, it.csi.pbandi.pbworkspace.pbandisrv.exception.WorkspaceException;

	
	/* 
	public void avviaIstanzaProcesso(

			java.lang.Long idUtente,

			java.lang.String identitaDigitale,

			java.lang.String codUtente,

			it.csi.pbandi.pbworkspace.pbandisrv.dto.workspace.DefinizioneProcessoDTO processoDaAvviare

	) throws it.csi.csi.wrapper.CSIException,
			it.csi.csi.wrapper.SystemException,
			it.csi.csi.wrapper.UnrecoverableException

			, it.csi.pbandi.pbworkspace.pbandisrv.exception.WorkspaceException;

	
	 
	public it.csi.pbandi.pbworkspace.pbandisrv.dto.workspace.StatoAttivitaDTO[] caricaElencoStatiAttivita(

	java.lang.Long idUtente,

	java.lang.String identitaDigitale

	) throws it.csi.csi.wrapper.CSIException,
			it.csi.csi.wrapper.SystemException,
			it.csi.csi.wrapper.UnrecoverableException

			, it.csi.pbandi.pbworkspace.pbandisrv.exception.WorkspaceException;

	
	////{PROTECTED REGION END////}
	public it.csi.pbandi.pbworkspace.pbandisrv.dto.workspace.EsitoRicercaAttivitaDTO ricercaAttivita(

	java.lang.Long idUtente,

	java.lang.String identitaDigitale,

	it.csi.pbandi.pbworkspace.pbandisrv.dto.workspace.FiltroRicercaAttivitaDTO filtro

	) throws it.csi.csi.wrapper.CSIException,
			it.csi.csi.wrapper.SystemException,
			it.csi.csi.wrapper.UnrecoverableException

			, it.csi.pbandi.pbworkspace.pbandisrv.exception.WorkspaceException;

	
	public it.csi.pbandi.pbworkspace.pbandisrv.dto.workspace.EsitoCaricaUrlMiniAppDTO caricaUrlMiniAppAttivita(

	java.lang.Long idUtente,

	java.lang.String identitaDigitale,

	java.lang.String codUtente,

	it.csi.pbandi.pbworkspace.pbandisrv.dto.workspace.IstanzaAttivitaDTO istanzaAttivita

	) throws it.csi.csi.wrapper.CSIException,
			it.csi.csi.wrapper.SystemException,
			it.csi.csi.wrapper.UnrecoverableException

			, it.csi.pbandi.pbworkspace.pbandisrv.exception.WorkspaceException;


	public it.csi.pbandi.pbworkspace.pbandisrv.dto.workspace.UrlDTO caricaUrlMiniAppAttivitaPreliminare(

			java.lang.Long idUtente,

			java.lang.String identitaDigitale,

			java.lang.String codUtente,

			it.csi.pbandi.pbworkspace.pbandisrv.dto.workspace.DefinizioneProcessoDTO definizioneProcesso

	) throws it.csi.csi.wrapper.CSIException,
			it.csi.csi.wrapper.SystemException,
			it.csi.csi.wrapper.UnrecoverableException

			, it.csi.pbandi.pbworkspace.pbandisrv.exception.WorkspaceException;

	
	public it.csi.pbandi.pbworkspace.pbandisrv.dto.workspace.ElencoIstanzeProcessiEBandiAssociatiDTO caricaElencoIstanzeProcessiEBandiAssociati(

	java.lang.Long idUtente,

	java.lang.String identitaDigitale,

	it.csi.pbandi.pbworkspace.pbandisrv.dto.workspace.FiltroIstanzaProcessoDTO filtro

	) throws it.csi.csi.wrapper.CSIException,
			it.csi.csi.wrapper.SystemException,
			it.csi.csi.wrapper.UnrecoverableException

			, it.csi.pbandi.pbworkspace.pbandisrv.exception.WorkspaceException;

	 */
}
