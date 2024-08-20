/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.integration.dao;

import java.math.BigDecimal;
import java.util.List;

import it.csi.pbandi.pbgestfinbo.dto.DocumentoIndexVO;
import it.csi.pbandi.pbgestfinbo.dto.GestioneAllegatiVO;
import it.csi.pbandi.pbgestfinbo.dto.profilazione.AttivitaDTO;
import it.csi.pbandi.pbgestfinbo.dto.profilazione.StoricoRevocaDTO;
import it.csi.pbandi.pbgestfinbo.integration.vo.NoteGeneraliVO;
import it.csi.pbandi.pbgestfinbo.integration.vo.PassaggioPerditaVO;
import it.csi.pbandi.pbgestfinbo.integration.vo.TransazioneVO;

public interface PassaggioPerditaDAO {
	
	/// passaggio perdita
	Boolean salvaPassaggioPerdita(PassaggioPerditaVO pp, int idModalitaAgev); 
	PassaggioPerditaVO getPassaggioPerdita(Long idProgetto, int idModalitaAgev); 
	List<StoricoRevocaDTO> getStoricoPassaggioPerdita(Long idProgetto, int idModalitaAgev); 
	
	// Transazioni
	Boolean salvaTransazione(TransazioneVO transazioneVO, int idModalitaAgev); 
	TransazioneVO getTransazione(Long idProgetto, int idModalitaAgev); 
	List<StoricoRevocaDTO> getStoricoTransazioni(Long idProgetto, int idModalitaAgev); 
	List<AttivitaDTO> getListaBanche(String string); 
	
	// Note generali
	// Spostate in AttivitaIstruttoreAreaCreditiDAO //
	//Long salvaNote(NoteGeneraliVO noteGeneraliVO, boolean isModifica, int idModalitaAgevolazione);
	//List<NoteGeneraliVO> getNoteGenerale(Long idProgetto, int idModalitaAgevolazione);
	//List<StoricoRevocaDTO> getStoricoNote(Long idProgetto, int idModalitaAgev);
	//Boolean salvaUploadAllegato(byte[] file,String nomeFile, Long idUtente, BigDecimal idTarget, BigDecimal idTipoDocumentoIndex,BigDecimal idProgetto, BigDecimal idEntita);
	//Boolean aggiornaAllegati(List<GestioneAllegatiVO> allegatiPresenti, Long idTarget);
}
