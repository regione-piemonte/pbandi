/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweberog.integration.dao;

import it.csi.pbandi.pbservizit.pbandisrv.dto.recupero.DettaglioRecuperoDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.recupero.EsitoSalvaRecuperoDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.recupero.ModalitaAgevolazioneDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.recupero.RecuperoDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.recupero.TipologiaRecuperoDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.revoca.PeriodoDTO;
import it.csi.pbandi.pbservizit.pbandisrv.exception.FormalParameterException;
import it.csi.pbandi.pbweberog.dto.EsitoOperazioni;
import it.csi.pbandi.pbweberog.dto.recupero.Soppressione;
import it.csi.pbandi.pbweberog.integration.response.ResponseInizializzaSoppressioniDTO;

public interface RecuperiDAO {

	boolean isRecuperoAccessibile(Long idUtente, String idIride, Long idProgetto) throws Exception;

	TipologiaRecuperoDTO[] findTipologieRecuperi(Long idUtente, String idIride, String codiceRuolo) throws Exception;

	PeriodoDTO[] findPeriodi(Long idUtente, String idIride) throws Exception;

	RecuperoDTO[] findRecuperi(Long idUtente, String idIride, Long idProgetto) throws Exception;

	EsitoSalvaRecuperoDTO checkRecuperi(Long idUtente, String idIride, RecuperoDTO[] transform) throws FormalParameterException;

	EsitoSalvaRecuperoDTO salvaRecuperi(Long idUtente, String idIride, RecuperoDTO[] transform) throws Exception;

	ModalitaAgevolazioneDTO[] findRiepilogoRecuperi(Long idUtente, String idIride, DettaglioRecuperoDTO filtro) throws FormalParameterException, Exception;

	Boolean checkPropostaCertificazione(Long idUtente, String idIride, Long idProgetto) throws FormalParameterException;

	EsitoSalvaRecuperoDTO modificaRecupero(Long idUtente, String idIride, ModalitaAgevolazioneDTO modalitaDTO ) throws Exception;

	EsitoSalvaRecuperoDTO eliminaRecupero(Long idUtente, String idIride, Long idRecupero) throws Exception;

	ResponseInizializzaSoppressioniDTO inizializzaSoppressioni(Long idProgetto, String codiceRuolo, Long idSoggetto, Long idUtente) throws Exception;
	
	EsitoOperazioni salvaSoppressione(Soppressione soppressione, Long idUtente) throws Exception;
}
