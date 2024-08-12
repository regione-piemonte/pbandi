package it.csi.pbandi.pbweb.integration.dao;

import it.csi.pbandi.pbweb.dto.*;
import it.csi.pbandi.pbweb.dto.documentoDiSpesa.*;
import it.csi.pbandi.pbweb.integration.dao.request.PagamentiAssociatiRequest;
import it.csi.pbandi.pbweb.integration.vo.TipoDocumentiSpesaVO;
import it.csi.pbandi.pbweb.pbandisrv.dto.gestionedocumentidispesa.DocumentoDiSpesaDTO;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface DocumentoDiSpesaCulturaDAO {
  List<TipoDocumentiSpesaVO> ottieniTipologieDocumentiDiSpesaByBandoLinea(Long idBandoLinea, Long idProgetto);
  List<DocumentoAllegatoDTO> allegatiFornitore(long idFornitore, long idProgetto, long idUtente, String idiride) throws Exception;
  EsitoDTO salvaDocumentoDiSpesa(DocumentoDiSpesaDTO documentoDiSpesaDTO, long idUtente, long progrBandoLinea,  long tipoBeneficiario, HttpServletRequest req) throws Exception;
  EsitoDTO associaVoceDiSpesa(VoceDiSpesaDTO voceDiSpesaDTO, long idUtente, HttpServletRequest req) throws Exception;
  List<VoceDiSpesaPadre> vociDiSpesaRicerca(long idProgetto, HttpServletRequest req) throws Exception;
  FiltroRicercaRendicontazionePartners partners(long idProgetto, long idBandoLinea, HttpServletRequest req) throws Exception;
  DocumentoDiSpesaDTO documentoDiSpesa(long idDocumentoDiSpesa, long idProgetto, long idUtente, String idIride) throws Exception;
List<DocumentoDiPagamentoDTO> pagamentiAssociati(PagamentiAssociatiRequest pagamentiAssociatiRequest, long idUtente, String idIride, long idSoggetto) throws Exception;
}
