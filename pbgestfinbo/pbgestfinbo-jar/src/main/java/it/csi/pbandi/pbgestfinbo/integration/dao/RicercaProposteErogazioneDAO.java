/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.integration.dao;

import it.csi.pbandi.pbgestfinbo.integration.vo.PropostaErogazioneVO;
import it.csi.pbandi.pbgestfinbo.integration.vo.suggestion.SuggestIdDescVO;

import java.util.List;

public interface RicercaProposteErogazioneDAO {

	List<PropostaErogazioneVO> getProposteErogazione(Long progrBandoLinea, Long idModalitaAgevolazione, Long idSoggetto, Long idProgetto, String contrPreErogazione) throws Exception;

	List<SuggestIdDescVO> getSuggestion(String value, String id) throws Exception;

}
