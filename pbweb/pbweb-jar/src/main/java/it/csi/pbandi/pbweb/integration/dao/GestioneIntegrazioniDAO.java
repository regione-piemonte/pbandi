/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweb.integration.dao;

import it.csi.pbandi.pbweb.dto.IntegrazioneRendicontazioneDTO;
import it.csi.pbandi.pbweb.dto.IntegrazioneRevocaDTO;
import it.csi.pbandi.pbweb.dto.rendicontazione.AllegatoDTO;
import it.csi.pbandi.pbweb.dto.rendicontazione.DocIntegrRendDTO;
import it.csi.pbandi.pbweb.dto.rendicontazione.DocumentoDiSpesaSospesoDTO;
import it.csi.pbandi.pbweb.dto.rendicontazione.QuietanzaDTO;
import it.csi.pbandi.pbweb.dto.utils.ResponseCodeMessage;
import it.csi.pbandi.pbweb.integration.vo.AllegatiDichiarazioneSpesaVO;
import it.csi.pbandi.pbweb.integration.vo.AllegatoDocSpesaQuietanzaVO;
import it.csi.pbandi.pbweb.integration.vo.DocumentiSpesaSospesiVO;
import it.csi.pbandi.pbweb.integration.vo.VisualizzaIntegrazioniVO;
import it.csi.pbandi.pbweb.pbandisrv.dto.validazionerendicontazione.RegoleAllegatiIntegrazioneDTO;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface GestioneIntegrazioniDAO {

    Boolean getAbilitaInviaIntegr(int idRichIntegrazione);

    Boolean updateRichIntegrazione(int idUtente, int idRichIntegrazione);

    Boolean insertFileEntita(List<VisualizzaIntegrazioniVO> allegati, Long idRichIntegraz);

    List<VisualizzaIntegrazioniVO> getAllegatiIstruttoreRevoche(int idRichIntegraz);
    List<VisualizzaIntegrazioniVO> getAllegati(int idRichIntegraz);
    List<VisualizzaIntegrazioniVO> getLetteraIstruttore(int idRichIntegraz);

    Boolean deleteAllegato(int idFileEntita);

    List<AllegatiDichiarazioneSpesaVO> getAllegatiDichSpesa(int idDichSpesa);

    List<DocumentiSpesaSospesiVO> getDocumentiSpesaSospesi(int idProgetto);

    Boolean salvaAllegatiGenerici(List<AllegatiDichiarazioneSpesaVO> allegati, Long idDichiarazioneSpesa, Long idIntegrazioneSpesa);

    //INTEGRAZIONE ALLA REVOCA
    List<IntegrazioneRevocaDTO> getIntegrazioniRevoca(Long idProgetto, Long idBandoLinea) throws Exception;
    List<IntegrazioneRevocaDTO> getRichProroga(Long idIntegrazione);
    Boolean inserisciRichProroga(Long idIntegrazione, IntegrazioneRevocaDTO integrazioneRevocaDTO, HttpServletRequest req);
    Boolean inviaIntegrazione(Long idIntegrazione, HttpServletRequest req);

    //INTEGRAZIONE ALLA RENDICONTAZIONE
    List<IntegrazioneRendicontazioneDTO> getIntegrazioniRendicontazione(Long idProgetto, HttpServletRequest req) throws Exception;
    RegoleAllegatiIntegrazioneDTO getRegoleIntegrazione(Long idBandoLinea, HttpServletRequest req);
    List<IntegrazioneRendicontazioneDTO> getRichProrogaRendicontazione(Long idIntegrazione);
    Boolean inserisciRichProrogaRendicontazione(Long idIntegrazione, IntegrazioneRendicontazioneDTO integrazioneRendicontazioneDTO, HttpServletRequest req);
    Boolean inviaIntegrazioneRendicontazione(Long idIntegrazione, HttpServletRequest req);

    //ALLEGATI
    List<DocIntegrRendDTO> getLetteraAccIntegrazRendicont(Long idIntegrazione);

    List<DocIntegrRendDTO> getReportValidazione(Long idIntegrazione);

    List<DocumentoDiSpesaSospesoDTO> getDocumentiDiSpesaSospesi(Long idDichiarazioneSpesa, Long idProgetto);
    List<DocumentoDiSpesaSospesoDTO> getDocumentiDiSpesaIntegrati(Long idIntegrazione, Long idDichiarazioneSpesa, Long idProgetto);
    List<DocIntegrRendDTO> getAllegatiDichiarazioneSpesa(Long idDichiarazioneSpesa);
    List<List<DocIntegrRendDTO>> getAllegatiIntegrazioneDS(Long idIntegrazioneSpesaCorrente, Long idDichiarazioneSpesa);
    List<DocIntegrRendDTO> getAllegatiNuovaIntegrazioneDS(Long idIntegrazioneSpesaCorrente, Long idDichiarazioneSpesa);

    List<DocIntegrRendDTO> getAllegatiDocumentoSpesa(Long idDichiarazioneSpesa, Long idDocumentoSpesa);
    List<List<DocIntegrRendDTO>> getAllegatiIntegrazioneDocS(Long idIntegrazioneSpesaCorrente, Long idDichiarazioneSpesa, Long idDocumentoSpesa);
    List<DocIntegrRendDTO> getAllegatiNuovaIntegrazioneDocS(Long idIntegrazioneSpesaCorrente, Long idDocumentoSpesa);

    List<QuietanzaDTO> getQuietanze(Long idDocumentoSpesa);
    List<DocIntegrRendDTO> getAllegatiQuietanza(Long idDichiarazioneSpesa, Long idQuietanza);
    List<List<DocIntegrRendDTO>> getAllegatiIntegrazioneQuietanza(Long idIntegrazioneSpesaCorrente, Long idDichiarazioneSpesa, Long idQuietanza);
    List<DocIntegrRendDTO> getAllegatiNuovaIntegrazioneQuietanza(Long idIntegrazioneSpesaCorrente, Long idQuietanza);

    ResponseCodeMessage salvaAllegati(List<AllegatoDTO> allegati, HttpServletRequest req);
    Boolean rimuoviAllegato(Long idFileEntita, HttpServletRequest req);
    
    List<AllegatoDocSpesaQuietanzaVO> getAllegatiDocSpesaQuietanzeDaInviare(Long idIntegrazioneSpesa, Long idUtente, String idIride,
			String codiceRuolo) throws Exception;
}
