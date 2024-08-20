/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.integration.dao;
import java.math.BigDecimal;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import it.csi.pbandi.pbgestfinbo.dto.AttivitaDTO;
import it.csi.pbandi.pbgestfinbo.dto.profilazione.StoricoPropostaVarazioneStatoCreditoDTO;
import it.csi.pbandi.pbgestfinbo.integration.vo.CercaPropostaVarazioneStatoCreditoSearchVO;
import it.csi.pbandi.pbgestfinbo.integration.vo.SalvaVariazioneStatoCreditoVO;
import it.csi.pbandi.pbgestfinbo.integration.vo.StatoCreditoVO;

public interface ProposteVariazioneStatoCreditoDAO {
	
	List<AttivitaDTO> getSugggest(int id, String stringa); 
	List<AttivitaDTO> listaTipoAgevolazione(); 
	List<AttivitaDTO> listaStatoProposta(); 
	List<StoricoPropostaVarazioneStatoCreditoDTO> getElencoProposte(CercaPropostaVarazioneStatoCreditoSearchVO statoCreditoSearchDTO); 
	//List<StoricoPropostaVarazioneStatoCreditoDTO> getEleTest (Long idSog); 
	Boolean salvaStatoProposta(SalvaVariazioneStatoCreditoVO salvaVariazioneStatoCreditoVO, HttpServletRequest request);
	List<StatoCreditoVO> statoCredito();
	Boolean rifiutaAccettaMassivaStatoCredito(List<Long> proposteDaConfermare, String flagConferma,
			HttpServletRequest request); 
	

}
