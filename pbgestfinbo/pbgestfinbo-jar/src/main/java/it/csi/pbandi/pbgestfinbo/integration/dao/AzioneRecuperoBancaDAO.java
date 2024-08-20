/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.integration.dao;

import java.util.ArrayList;

import it.csi.pbandi.pbgestfinbo.dto.AttivitaDTO;
import it.csi.pbandi.pbgestfinbo.dto.profilazione.StoricoAzioneRecuperoBancaDTO;
import it.csi.pbandi.pbgestfinbo.integration.vo.AzioneRecuperoBancaVO;

public interface AzioneRecuperoBancaDAO {
	Boolean insertAzioneRecuperoBanca(AzioneRecuperoBancaVO azioneRecupVO,Long idUtente, Long idProgetto,int idModalitaAgev);
	Boolean modificaAzioneRecuperoBanca(AzioneRecuperoBancaVO azioneRecupVO,Long idUtente, Long idProgetto, Long idAzioneRecupero,int idModalitaAgev);
	AzioneRecuperoBancaVO getAzioneRecuperoBanca(Long idUtente, Long idProgetto, Long idAzioneRecpero,int idModalitaAgev);
	ArrayList<AttivitaDTO> getListaAttivitaAzione(); 
	ArrayList<StoricoAzioneRecuperoBancaDTO> getStoricoAzioni (Long idUtente, Long idProgetto,int idModalitaAgev);
	ArrayList<StoricoAzioneRecuperoBancaDTO> getStoricoAzioneRecupBanca (Long idUtente, Long idProgetto,int idModalitaAgev);
	
	

}
