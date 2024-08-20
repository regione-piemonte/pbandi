/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.integration.dao;

import java.math.BigDecimal;
import java.security.InvalidParameterException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;

import it.csi.pbandi.pbgestfinbo.dto.EsitoDTO;
import it.csi.pbandi.pbgestfinbo.dto.GestioneAllegatiVO;
import it.csi.pbandi.pbgestfinbo.dto.profilazione.StoricoRevocaDTO;
import it.csi.pbandi.pbgestfinbo.exception.ErroreGestitoException;
import it.csi.pbandi.pbgestfinbo.integration.vo.BeneficiarioCreditiVO;
import it.csi.pbandi.pbgestfinbo.integration.vo.BoxListVO;
import it.csi.pbandi.pbgestfinbo.integration.vo.CessioneQuotaVO;
import it.csi.pbandi.pbgestfinbo.integration.vo.EscussioneConfidiVO;
import it.csi.pbandi.pbgestfinbo.integration.vo.IscrizioneRuoloVO;
import it.csi.pbandi.pbgestfinbo.integration.vo.LavorazioneBoxListVO;
import it.csi.pbandi.pbgestfinbo.integration.vo.LiberazioneBancaVO;
import it.csi.pbandi.pbgestfinbo.integration.vo.LiberazioneGaranteVO;
import it.csi.pbandi.pbgestfinbo.integration.vo.NoteGeneraliVO;
import it.csi.pbandi.pbgestfinbo.integration.vo.PianoRientroVO;
import it.csi.pbandi.pbgestfinbo.integration.vo.RevocaAmministrativaCreditiVO;
import it.csi.pbandi.pbgestfinbo.integration.vo.SaveSchedaClienteAllVO;
import it.csi.pbandi.pbgestfinbo.integration.vo.SchedaClienteMainVO;


public interface AttivitaIstruttoreAreaCreditiDAO {

	
	BoxListVO getBoxList(Long idModalitaAgevolazione, Long idArea) throws Exception, InvalidParameterException;
	
	List<LavorazioneBoxListVO> getLavorazioneBox(Long idModalitaAgevolazione, Long idProgetto) throws Exception, InvalidParameterException;

	EsitoDTO salvaAllegati(Long idProgetto, Boolean letteraAccompagnatoria, int ambitoAllegato, byte[] allegato, String nomeAllegato, HttpServletRequest req) throws Exception;

	// Iscrizione a Ruolo //
	
	List<IscrizioneRuoloVO> getIscrizioneRuolo(String idProgetto, Long idModalitaAgevolazione) throws Exception;
	
	void setIscrizioneRuolo(IscrizioneRuoloVO iscrizioneRuolo, int idModalitaAgevolazione) throws ErroreGestitoException;

	// Note Generali //
	
	Long salvaNote(NoteGeneraliVO noteGeneraliVO, boolean isModifica, int idModalitaAgevolazione);
	
	List<NoteGeneraliVO> getNoteGenerale(Long idProgetto, int idModalitaAgevolazione);
	
	List<StoricoRevocaDTO> getStoricoNote(Long idProgetto, int idModalitaAgev);
	
	Boolean salvaUploadAllegato(byte[] file,String nomeFile, Long idUtente, BigDecimal idTarget, BigDecimal idTipoDocumentoIndex,BigDecimal idProgetto, BigDecimal idEntita);
	
	Boolean aggiornaAllegati(List<GestioneAllegatiVO> allegatiPresenti, Long idTarget);
	
	Boolean eliminaNota(int idAnnotazione, int idModalitaAgevolazione);
	
	Boolean eliminaAllegato(int idAnnotazione, int idModalitaAgevolazione);
}
