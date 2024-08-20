/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.integration.dao;

import java.util.ArrayList;

import it.csi.pbandi.pbgestfinbo.dto.profilazione.StoricoSegnalazioneCorteContiDTO;
import it.csi.pbandi.pbgestfinbo.integration.vo.SegnalazioneCorteContiVO;

public interface SegnalazioneCorteContiDAO {
	
	Boolean insertSegnalazione(SegnalazioneCorteContiVO segnalazioneCorteContiVO, int idModalitaAgev); 
	Boolean modificaSegnalazione(SegnalazioneCorteContiVO segnalazioneCorteContiVO,  Long idSegnalazioneCorteConti, int idModalitaAgev); 
	SegnalazioneCorteContiVO getSegnalazione(Long idSegnalazione); 
	ArrayList<StoricoSegnalazioneCorteContiDTO> getStorico(Long idProgetto, int idModalitaAgev);  // tutte le segnalazioni
	ArrayList<StoricoSegnalazioneCorteContiDTO> getSotricoSegnalazioni(Long idProgetto, int idModalitaAgev); // questo per le segnalazioni dove data Fine validita == null 

}
