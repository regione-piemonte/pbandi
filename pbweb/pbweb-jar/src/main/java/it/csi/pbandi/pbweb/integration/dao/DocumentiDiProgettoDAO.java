package it.csi.pbandi.pbweb.integration.dao;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;

import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;

import it.csi.pbandi.pbweb.dto.CodiceDescrizioneDTO;
import it.csi.pbandi.pbweb.dto.DatiIntegrazioneDsDTO;
import it.csi.pbandi.pbweb.dto.DocumentoAllegatoDTO;
import it.csi.pbandi.pbweb.dto.ProgettoBeneficiarioDTO;
import it.csi.pbandi.pbweb.dto.dichiarazioneDiSpesa.AllegatiDichiarazioneDiSpesaDTO;
import it.csi.pbandi.pbweb.dto.documentiDiProgetto.DocumentoIndexDTO;
import it.csi.pbandi.pbweb.dto.documentiDiProgetto.FiltroRicercaDocumentiDTO;
import it.csi.pbandi.pbweb.dto.documentiDiProgetto.InizializzaDocumentiDiProgettoDTO;
import it.csi.pbandi.pbweb.integration.vo.EnteProgettoDomandaVO;
import it.csi.pbandi.pbweb.integration.vo.PbandiTDocumentoIndexVO;

public interface DocumentiDiProgettoDAO {

	InizializzaDocumentiDiProgettoDTO inizializzaDocumentiDiProgetto(String codiceRuolo, Long idSoggetto, Long idUtente,
			String idIride) throws InvalidParameterException, Exception;

	List<CodiceDescrizioneDTO> getBeneficiariDocProgettoByDenomOrIdBen(String denominazione, Long idBeneficiario,
			String codiceRuolo, Long idSoggetto, Long idUtente, String idIride) throws Exception;

	ArrayList<ProgettoBeneficiarioDTO> progettiBeneficiario(String codiceRuolo, Long idSoggettoBeneficiario,
			Long idSoggetto, Long idUtente, String idIride) throws InvalidParameterException, Exception;

	ArrayList<DocumentoIndexDTO> cercaDocumenti(FiltroRicercaDocumentiDTO filtroRicercaDocumentiDTO, Long idUtente,
			String idIride) throws InvalidParameterException, Exception;

	Boolean salvaUpload(MultipartFormDataInput multipartFormData, Long idUtente, String idIride)
			throws InvalidParameterException, Exception;

	List<DocumentoAllegatoDTO> allegati(String codTipoDocIndex, Long idDocumentoIndex, Long idUtente, String idIride)
			throws InvalidParameterException, Exception;

	List<DatiIntegrazioneDsDTO> allegatiIntegrazioniDS(Long idDichiarazioneSpesa, String codTipoDocIndex, Long idUtente,
			String idIride) throws InvalidParameterException, Exception;

	List<DocumentoAllegatoDTO> allegatiVerbaleChecklist(String codTipoDocIndex, Long idChecklist, Long idDocIndex,
			Long idUtente, String idIride) throws InvalidParameterException, Exception;

	List<DocumentoAllegatoDTO> getAllegatiChecklist(Long idChecklist, Long idUtente) throws Exception;

	// List<DocumentoAllegatoDTO> allegatiVerbaleChecklist(Long idDocIndex, Long
	// idUtente, String idIride) throws InvalidParameterException, Exception;
	List<DocumentoAllegatoDTO> allegatiVerbaleChecklistAffidamento(String codTipoDocIndex, Long idDocumentoIndex,
			Long idUtente, String idIride) throws InvalidParameterException, Exception;

	Boolean cancellaFileConVisibilita(Long idDocumentoIndex, Long idUtente, String idIride)
			throws InvalidParameterException, Exception;

	Boolean cancellaRecordDocumentoIndex(Long idDocumentoIndex, Long idUtente, String idIride)
			throws InvalidParameterException, Exception;

	String codiceStatoDocumentoIndex(Long idDocumentoIndex, Long idUtente, String idIride)
			throws InvalidParameterException, Exception;

	int salvaDocumentoIndex(PbandiTDocumentoIndexVO documentoIndexVO) throws InvalidParameterException, Exception;

	EnteProgettoDomandaVO getEnteProgettoDomandaVO(Long idProgetto);

	Long getDocumentoIndex(PbandiTDocumentoIndexVO p);

	Boolean numeroProtocolloExists(String numProtocollo);

	Long getIdChecklist(Long idDichiarazioneSpesa, Long idDocumentoIndex);

	AllegatiDichiarazioneDiSpesaDTO getAllegatiByTipoDoc(Long idTarget, Long idProgetto, String descBreveTipoDoc, Long idUtente, String idIride)
			throws InvalidParameterException, Exception;

}
