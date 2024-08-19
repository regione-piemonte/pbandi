/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweberog.integration.dao;

import java.util.List;

import it.csi.pbandi.pbservizit.pbandisrv.dto.trasferimenti.CausaleTrasferimentoDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.trasferimenti.EsitoSalvaTrasferimentoDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.trasferimenti.SoggettoTrasferimentoDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.trasferimenti.TrasferimentoDTO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiDLineaDiInterventoVO;
import it.csi.pbandi.pbweberog.dto.trasferimenti.FiltroTrasferimento;

public interface TrasferimentiDAO {

	SoggettoTrasferimentoDTO[] findSoggettiTrasferimento(Long idUtente, String idIride) throws Exception;

	CausaleTrasferimentoDTO[] findElencoCausaliTrasferimenti(Long idUtente, String idIride) throws Exception;

	TrasferimentoDTO[] ricercaTrasferimenti(Long idUtente, String idIride, FiltroTrasferimento filtro) throws Exception;

	EsitoSalvaTrasferimentoDTO modificaTrasferimento(Long idUtente, String idIride, TrasferimentoDTO dto) throws Exception;

	TrasferimentoDTO findDettaglioTrasferimento(Long idUtente, String idIride, Long idTrasferimento) throws Exception;

	EsitoSalvaTrasferimentoDTO eliminaTrasferimento(Long idUtente, String idIride, Long idTrasferimento) throws Exception;

	EsitoSalvaTrasferimentoDTO creaTrasferimento(Long idUtente, String idIride, TrasferimentoDTO dto) throws Exception;
	
	List<PbandiDLineaDiInterventoVO> findLineaDiIntervento() throws Exception;

}
