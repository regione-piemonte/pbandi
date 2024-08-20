/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.integration.dao;

import java.util.ArrayList;

import it.csi.pbandi.pbgestfinbo.dto.AttivitaDTO;
import it.csi.pbandi.pbgestfinbo.dto.profilazione.StoricoMessaMoraDTO;
import it.csi.pbandi.pbgestfinbo.integration.vo.MessaMoraVO;

public interface MessaMoraDAO {
	
	Boolean insertMessaMora(MessaMoraVO messaMora, int idModalitaAgev);
	Boolean modificaMessaMora(MessaMoraVO messaMora, Long idMessaMora, int idModalitaAgev); 
	MessaMoraVO getMessaMora(Long idMessaMora, int idModalitaAgev); 
	 
	ArrayList<AttivitaDTO> getAttivitaMessaMora();
	ArrayList<AttivitaDTO> getAttivitaRecupero();
	
	ArrayList<StoricoMessaMoraDTO> getStoricoMessaMora(Long idProgetto, int idModalitaAgev); 
	ArrayList<StoricoMessaMoraDTO> getStorico(Long IdProgetto, int idModalitaAgev); 

}
