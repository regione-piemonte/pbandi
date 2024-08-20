/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.integration.dao;

import java.util.ArrayList;

import it.csi.pbandi.pbgestfinbo.dto.AttivitaDTO;
import it.csi.pbandi.pbgestfinbo.dto.profilazione.StoricoAbbattimentoGaranzieDTO;
import it.csi.pbandi.pbgestfinbo.integration.vo.AbbattimentoGaranzieVO;

public interface AbbattimentoGaranzieDAO {
	
	Boolean insertAbbattimento(AbbattimentoGaranzieVO abbattimento, Long idUtente, Long idProgetto, int idModalitaAgev);
	Boolean modificaAbbattimento(AbbattimentoGaranzieVO abbattimento, Long idUtente, Long idProgetto, Long idAbbattimentoGraranzie, int idModalitaAgev ); 
	AbbattimentoGaranzieVO getAbbatimento(Long idUtente, Long idProgetto,Long idAbbattimentoGraranzie, int idModalitaAgev); 
	ArrayList<AttivitaDTO> getListaAttivitaAbbattimento();
	ArrayList<StoricoAbbattimentoGaranzieDTO> storicoAbbattimenti(Long idUtente, Long idProgetto, int idModalitaAgev);
	ArrayList<StoricoAbbattimentoGaranzieDTO> storicoAbbattimentoGaranzie(Long idUtente, Long idProgetto, int idModalitaAgev); 

}
