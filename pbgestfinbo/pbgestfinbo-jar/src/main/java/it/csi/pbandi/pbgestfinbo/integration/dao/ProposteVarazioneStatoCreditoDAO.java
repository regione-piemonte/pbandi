/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.integration.dao;
import java.util.List;
import it.csi.pbandi.pbgestfinbo.dto.AttivitaDTO;
import it.csi.pbandi.pbgestfinbo.dto.profilazione.StoricoPropostaVarazioneStatoCreditoDTO;
import it.csi.pbandi.pbgestfinbo.integration.vo.CercaPropostaVarazioneStatoCreditoSearchVO;
import it.csi.pbandi.pbgestfinbo.integration.vo.SalvaVariazioneStatoCreditoVO;

public interface ProposteVarazioneStatoCreditoDAO {
	
	List<AttivitaDTO> getSugggest(int id, String stringa);
//	List<AttivitaDTO> listaCodiciFiscali(); // fatto
//	List<AttivitaDTO> listaPartitaIVA();  
//	List<AttivitaDTO> listaDenomiazioni(); 
	List<AttivitaDTO> listaTipoAgevolazione(); 
//	List<AttivitaDTO> listaBando(); 
//	List<AttivitaDTO> listaCodiceProgetto(); 
	List<AttivitaDTO> listaStatoProposta(); 
	List<StoricoPropostaVarazioneStatoCreditoDTO> getElencoProposte(CercaPropostaVarazioneStatoCreditoSearchVO statoCreditoSearchDTO); 
	List<StoricoPropostaVarazioneStatoCreditoDTO> getEleTest (Long idSog); 
//	List<StoricoPropostaVarazioneStatoCreditoDTO> listaProfilazioni();
	Boolean salvaStatoProposta(SalvaVariazioneStatoCreditoVO salvaVariazioneStatoCreditoVO); 
	

}
