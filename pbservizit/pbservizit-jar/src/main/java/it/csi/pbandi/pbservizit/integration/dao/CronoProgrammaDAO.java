/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.integration.dao;

import java.util.Date;

import it.csi.csi.wrapper.UnrecoverableException;
import it.csi.pbandi.pbservizit.pbandisrv.dto.gestionecronoprogramma.EsitoFindFasiMonitoraggio;
import it.csi.pbandi.pbservizit.pbandisrv.dto.gestionecronoprogramma.EsitoSaveFasiMonitoraggio;
import it.csi.pbandi.pbservizit.pbandisrv.dto.gestionecronoprogramma.FaseMonitoraggioDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.gestionecronoprogramma.IterDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.gestionecronoprogramma.MotivoScostamentoDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.gestionecronoprogramma.TipoOperazioneDTO;
import it.csi.pbandi.pbservizit.pbandisrv.exception.FormalParameterException;

public interface CronoProgrammaDAO {

	String findProgrammazioneByIdProgetto(Long idUtente, String idIride, Long idProgetto) throws FormalParameterException;

	TipoOperazioneDTO[] findTipoOperazione(Long idUtente, String idIride, Long idProgetto, String programmazione) throws UnrecoverableException, FormalParameterException;

	String findCodiceProgetto(Long idProgetto);

	EsitoFindFasiMonitoraggio findFasiMonitoraggioGestione(Long idUtente, String idIride, Long idProgetto,
			String programmazione) throws FormalParameterException, UnrecoverableException;

	MotivoScostamentoDTO[] findMotivoScostamento(Long idUtente, String idIride) throws UnrecoverableException, FormalParameterException;

	IterDTO[] findIter(Long idUtente, String idIride, Long idTipoOperazione, String programazzione) throws UnrecoverableException, FormalParameterException;

	EsitoSaveFasiMonitoraggio salvaFasiMonitoraggioGestione(Long long1, String string, Long idProgetto, FaseMonitoraggioDTO[] fasiDto) throws UnrecoverableException, FormalParameterException;

	Date findDataConcessione(Long idProgetto);

	Date findDataPresentazioneDomanda(Long idProgetto);

	EsitoFindFasiMonitoraggio findFasiMonitoraggioAvvio(Long idUtente, String idIride, Long idProgetto,
			String programmazione, Long idIter) throws FormalParameterException, UnrecoverableException;

	EsitoSaveFasiMonitoraggio salvaFasiMonitoraggioAvvio(Long idUtente, String idIride, Long idProgetto,
			Long idTipoOperazione, FaseMonitoraggioDTO[] fasiDto) throws FormalParameterException, UnrecoverableException;
}
