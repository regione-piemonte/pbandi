/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebrce.integration.dao;

import java.util.List;

import it.csi.pbandi.pbservizit.pbandisrv.dto.gestioneappalti.AppaltoDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.gestioneappalti.EsitoGestioneAppalti;
import it.csi.pbandi.pbservizit.pbandisrv.exception.FormalParameterException;
import it.csi.pbandi.pbservizit.security.UserInfoSec;
import it.csi.pbandi.pbwebrce.dto.CodiceDescrizione;
import it.csi.pbandi.pbwebrce.dto.EsitoProceduraAggiudicazioneDTO;
import it.csi.pbandi.pbwebrce.dto.ProceduraAggiudicazione;
import it.csi.pbandi.pbwebrce.dto.ProceduraAggiudicazioneDTO;

public interface AppaltiDAO {

	AppaltoDTO[] findAppalti(Long idUtente, String idIride, AppaltoDTO filtroDTO) throws FormalParameterException;

	List<CodiceDescrizione> findTipologieAppalti(UserInfoSec userInfo) throws Exception;

	String findCodiceProgetto(Long idProgetto);

	List<CodiceDescrizione> findTipologieProcedureAggiudicazione(UserInfoSec userInfo, Long progBandoLinea) throws Exception;

	ProceduraAggiudicazioneDTO[] findAllProcedureAggiudicazione(Long idUtente, String idIride,
			ProceduraAggiudicazioneDTO f) throws FormalParameterException;

	ProceduraAggiudicazioneDTO findDettaglioProceduraAggiudicazione(Long idUtente, String idIride, Long idProcedura) throws Exception;

	EsitoProceduraAggiudicazioneDTO modificaProceduraAggiudicazione(Long idUtente, String idIride,
			ProceduraAggiudicazioneDTO p) throws Exception;

	EsitoGestioneAppalti creaAppalto(Long idUtente, String idIride, AppaltoDTO appaltoDTO);

	EsitoProceduraAggiudicazioneDTO creaProceduraAggiudicazione(Long idUtente, String idIride,ProceduraAggiudicazione dto, Long idProgettto) throws FormalParameterException, Exception;

	EsitoGestioneAppalti eliminaAppalto(Long idAppalto);
	
	Boolean eliminazioneAppaltoAbilitata(Long idSoggetto, Long idUtente, String codiceRuolo);
}
