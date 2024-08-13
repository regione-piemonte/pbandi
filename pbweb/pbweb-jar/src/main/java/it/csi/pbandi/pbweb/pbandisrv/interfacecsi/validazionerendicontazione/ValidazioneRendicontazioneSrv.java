/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweb.pbandisrv.interfacecsi.validazionerendicontazione;

import it.csi.pbandi.pbweb.pbandisrv.dto.validazionerendicontazione.*;
import it.csi.pbandi.pbweb.pbandisrv.exception.validazionerendicontazione.*;

////{PROTECTED REGION ID(R1549030208) ENABLED START////}
/**
 * Inserire qui la documentazione dell'interfaccia pubblica del servizio validazione_rendicontazione.
 * Consigli:
 * <ul>
 * <li> Descrivere qual'� lo scopo generale del servizio
 * <li> Se necessario fornire una overview delle funzioni messe a disposizione
 *      eventualmente raggruppandole logicamente. Il dettaglio dei singoli
 *      metodi va documentato in corrispondenza dei metodi stessi
 * <li> Se necessario descrivere gli scenari di utilizzo pi� frequenti, ovvero
 *      le "coreografie" (nel caso sia necessario richiamare in una sequenza
 *      particolare i metodi
 * <li> Inserire informazioni quali il livello di securizzazione A0-A3
 * <li> Inserire varie ed eventuali... 
 * </ul>
 * @generated
 */
////{PROTECTED REGION END////}
public interface ValidazioneRendicontazioneSrv {

	////{PROTECTED REGION END////}
	public it.csi.pbandi.pbweb.pbandisrv.dto.validazionerendicontazione.IntegrazioneSpesaDTO[] findIntegrazioniSpesaByIdProgetto(

	java.lang.Long idUtente,

	java.lang.String identitaDigitale,

	java.lang.Long idProgetto

	)
			throws it.csi.csi.wrapper.CSIException,
			it.csi.csi.wrapper.SystemException,
			it.csi.csi.wrapper.UnrecoverableException

			,
			it.csi.pbandi.pbweb.pbandisrv.exception.validazionerendicontazione.ValidazioneRendicontazioneException;

}
