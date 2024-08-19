/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweberog.integration.dao;

import java.util.ArrayList;

import it.csi.pbandi.pbservizit.pbandisrv.dto.manager.CodiceDescrizioneDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.rilevazionicontrolli.EsitoCampionamentoDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.rilevazionicontrolli.EsitoElencoProgettiCampione;
import it.csi.pbandi.pbservizit.pbandisrv.dto.rilevazionicontrolli.EsitoGenerazioneReportDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.rilevazionicontrolli.FiltroRilevazioniDTO;
import it.csi.pbandi.pbweberog.dto.monitoraggiocontrolli.ElenchiProgettiCampionamento;
import it.csi.pbandi.pbweberog.dto.monitoraggiocontrolli.FiltroRilevazione;

public interface MonitoraggioControlliDAO {

	CodiceDescrizioneDTO[] findAnnoContabili(Long idUtente, String idIride, Boolean isConsultazione);

	EsitoGenerazioneReportDTO generaReportCampionamento(Long idUtente, String idIride, FiltroRilevazioniDTO filtroDTO) throws Exception;

	CodiceDescrizioneDTO[] findNormative(Long idUtente, String idIride, Boolean isConsultazione,
			String flagRilevazioneControlli);

	CodiceDescrizioneDTO[] findAsse(Long idUtente, String idIride, Boolean isConsultazione,
			FiltroRilevazioniDTO filtroDTO);

	CodiceDescrizioneDTO[] findBandi(Long idUtente, String idIride, Boolean isConsultazione,
			FiltroRilevazioniDTO filtroDTO);

	CodiceDescrizioneDTO[] findAutoritaControlli(Long idUtente, String idIride, Boolean isConsultazione);

	EsitoElencoProgettiCampione caricaProgettiCampione(Long idUtente, String idIride, Long[] longs,
			FiltroRilevazioniDTO filtroDTO) throws Exception;

	EsitoCampionamentoDTO registraCampionamentoProgetti(Long idUtente, String idIride, FiltroRilevazioniDTO filtroDTO,
			Long[] longs) throws Exception;

}
