/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.integration.dao.boxAttivitaIstruttore;

import java.util.ArrayList;
import java.util.List;

import it.csi.pbandi.pbgestfinbo.dto.AttivitaDTO;
import it.csi.pbandi.pbgestfinbo.dto.profilazione.StoricoSaldoStralcioDTO;
import it.csi.pbandi.pbgestfinbo.integration.vo.SaldoStralcioVO;
import it.csi.pbandi.pbgestfinbo.integration.vo.boxAttivitaIstruttore.BoxSaldoStralcioVO;
import it.csi.pbandi.pbgestfinbo.integration.vo.boxAttivitaIstruttore.InitBoxSaldoStralcioVO;

public interface BoxSaldoStralcioDAO {
	
	List<BoxSaldoStralcioVO> getSaldoStralcio(Long idProgetto, int idModalitaAgevolazione);
	
	InitBoxSaldoStralcioVO initSaldoStralcio();
	
	Boolean insertSaldoStralcio(SaldoStralcioVO saldoStralcioVO, Long idUtente,Long idProgetto, int idModalitaAgev) throws Exception;
	Boolean modificaSaldoStralcio(SaldoStralcioVO saldoStralcioVO,Long idUtente, Long idProgetto, Long idSaldoStralcio, int idModalitaAgev); 
	//SaldoStralcioVO getSaldoStralcio(Long idSaldoStralcio, int idModalitaAgev);
	ArrayList<AttivitaDTO> getAttivitaSaldoStralcio(); 
	ArrayList<AttivitaDTO> getAttivitaEsito();
	ArrayList<AttivitaDTO> getAttivitaRecupero();
	// storico dei saldi e stralci dove id fine validita == null
	ArrayList<StoricoSaldoStralcioDTO> getListaSaldoStralcio(Long idProgetto, int idModalitaAgev); 
	// storico di tutti i saldi e stralci
	ArrayList<StoricoSaldoStralcioDTO> getStorico(Long idProgetto, int idModalitaAgev);

}