/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweberog.integration.dao;

import it.csi.pbandi.pbservizit.pbandisrv.dto.PropostaCertificazioneDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.irregolarita.ChecklistRettificaForfettariaDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.irregolarita.EsitoSalvaIrregolaritaDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.irregolarita.EsitoSalvaRettificaForfettariaDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.irregolarita.IrregolaritaDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.irregolarita.ProgettoDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.irregolarita.RettificaForfettariaDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.manager.CodiceDescrizioneDTO;
import it.csi.pbandi.pbservizit.pbandisrv.exception.FormalParameterException;
import it.csi.pbandi.pbservizit.pbandisrv.exception.irregolarita.IrregolaritaException;
import it.csi.pbandi.pbweberog.dto.EsitoGenerazioneReportDTO;
import it.csi.pbandi.pbweberog.dto.MotivoIrregolarita;

public interface RegistroControlliDAO {

	IrregolaritaDTO[] findIrregolarita(Long idUtente, String idIride, IrregolaritaDTO filtroSrv) throws Exception;

	IrregolaritaDTO[] findEsitiRegolari(Long idUtente, String idIride, IrregolaritaDTO filtroSrv) throws Exception;

	RettificaForfettariaDTO[] findRettificheForfettarie(Long idUtente, String idIride, IrregolaritaDTO filtroSrv) throws Exception;

	IrregolaritaDTO findDettaglioEsitoRegolare(Long idUtente, String idIride, Long idEsitoControllo) throws Exception;

	IrregolaritaDTO findDettaglioIrregolarita(Long idUtente, String idIride, Long idIrregolarita) throws Exception;
	
	CodiceDescrizioneDTO[] findDataCampione(Long idUtente, String idIride,  Long idProgetto, String tipoControlli, Long idPeriodo,
			Long idCategAnagrafica) throws Exception;

	EsitoSalvaIrregolaritaDTO modificaIrregolarita(Long idUtente, String idIride, IrregolaritaDTO dto,
			boolean updateDatiAggiuntivi) throws Exception;

	EsitoSalvaIrregolaritaDTO modificaEsitoRegolare(Long idUtente, String idIride, IrregolaritaDTO dto) throws Exception;

	EsitoSalvaIrregolaritaDTO modificaIrregolaritaProvvisoria(Long idUtente, String idIride, IrregolaritaDTO dto) throws Exception;
	
	PropostaCertificazioneDTO[] findProposteCertificazioneAperteByIdLineaIntervento(Long idUtente, String idIride,
			Long idLineaDiIntervento) throws Exception;

	ChecklistRettificaForfettariaDTO[] findChecklistRettificheForfettarie(Long idUtente, String idIride,
			Long idProgetto) throws Exception;

	EsitoGenerazioneReportDTO getContenutoDocumentoById(Long idUtente, String idIride, Long idDocumentoIndex) throws Exception;

	EsitoSalvaRettificaForfettariaDTO salvaRettificaForfettaria(Long idUtente, String idIride,
			RettificaForfettariaDTO rettifica) throws Exception;

	ProgettoDTO findProgetto(Long idUtente, String idIride, Long idProgetto) throws Exception;
	

	EsitoSalvaIrregolaritaDTO creaEsitoRegolare(Long idUtente, String idIride, IrregolaritaDTO dto) throws Exception;

	EsitoSalvaIrregolaritaDTO creaIrregolarita(Long idUtente, String idIride, IrregolaritaDTO dto) throws Exception;

	EsitoSalvaIrregolaritaDTO creaIrregolaritaProvvisoria(Long idUtente, String idIride, IrregolaritaDTO dto) throws Exception;

	EsitoSalvaRettificaForfettariaDTO eliminaRettificaForfettaria(Long idUtente, String idIride,
			Long idRettificaForfett) throws FormalParameterException, IrregolaritaException;

	EsitoSalvaIrregolaritaDTO cancellaIrregolaritaRegolare(Long idUtente, String idIride, Long idIrregolarita) throws IrregolaritaException, FormalParameterException;

	EsitoSalvaIrregolaritaDTO cancellaIrregolarita(Long idUtente, String idIride, Long idIrregolarita) throws IrregolaritaException, FormalParameterException;

	EsitoSalvaIrregolaritaDTO cancellaIrregolaritaProvvisoria(Long idUtente, String idIride, Long idIrregolarita) throws IrregolaritaException, FormalParameterException;

	EsitoSalvaIrregolaritaDTO registraInvioIrregolarita(Long idUtente, String idIride, IrregolaritaDTO dto) throws FormalParameterException, IrregolaritaException;

	

	

}
