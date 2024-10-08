/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweb.integration.dao;

import java.math.BigDecimal;
import java.security.InvalidParameterException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

//import it.csi.pbandi.pbweb.dto.EsitoAssociaFilesDTO;
import it.csi.pbandi.pbservizit.dto.archivioFile.EsitoAssociaFilesDTO;
//import it.csi.pbandi.pbweb.integration.dao.request.AssociaFilesRequest;
import it.csi.pbandi.pbservizit.integration.dao.request.AssociaFilesRequest;
import it.csi.pbandi.pbweb.dto.AffidamentoRendicontazioneDTO;
import it.csi.pbandi.pbweb.dto.DocumentoAllegatoDTO;
import it.csi.pbandi.pbweb.dto.EsitoDTO;
import it.csi.pbandi.pbweb.dto.EsitoImportoSaldoDTO;
import it.csi.pbandi.pbweb.dto.EsitoOperazioneDTO;
import it.csi.pbandi.pbweb.dto.FiltroRicercaDocumentiSpesa;
import it.csi.pbandi.pbweb.dto.ParametroCompensiDTO;
import it.csi.pbandi.pbweb.dto.documentoDiSpesa.DocumentoDiPagamentoDTO;
import it.csi.pbandi.pbweb.dto.documentoDiSpesa.EsitoLetturaXmlFattElett;
import it.csi.pbandi.pbweb.dto.documentoDiSpesa.EsitoRicercaDocumentiDiSpesa;
import it.csi.pbandi.pbweb.dto.documentoDiSpesa.FiltroRicercaRendicontazionePartners;
import it.csi.pbandi.pbweb.dto.documentoDiSpesa.FormAssociaDocSpesa;
import it.csi.pbandi.pbweb.dto.documentoDiSpesa.VoceDiSpesaDTO;
import it.csi.pbandi.pbweb.dto.documentoDiSpesa.VoceDiSpesaPadre;
import it.csi.pbandi.pbweb.dto.profilazione.DecodificaDTO;
import it.csi.pbandi.pbweb.integration.dao.request.AssociaPagamentoRequest;
import it.csi.pbandi.pbweb.integration.dao.request.PagamentiAssociatiRequest;
import it.csi.pbandi.pbweb.integration.vo.TipoDocumentiSpesaVO;
import it.csi.pbandi.pbweb.pbandisrv.dto.gestionedocumentidispesa.DocumentoDiSpesaDTO;

public interface DocumentoDiSpesaDAO {
	List<TipoDocumentiSpesaVO> ottieniTipologieDocumentiDiSpesaByBandoLinea(int idBandoLinea);
	List<AffidamentoRendicontazioneDTO> elencoAffidamenti(long idProgetto, long idFornitoreDocSpesa, String codiceRuolo) throws InvalidParameterException, Exception;
	List<DocumentoAllegatoDTO> allegatiFornitore(long idFornitore, long idProgetto, long idUtente, String idiride) throws InvalidParameterException, Exception;
	List<DocumentoAllegatoDTO> allegatiDocumentoDiSpesa(long idDocumentoDiSpesa, String flagDocElettronico, long idProgetto, long idUtente, String idTriade) throws InvalidParameterException, Exception;
	EsitoDTO disassociaAllegatoDocumentoDiSpesa(long idDocumentoIndex, long idDocumentoDiSpesa, long idProgetto, long idUtente, HttpServletRequest req) throws InvalidParameterException, Exception;
	EsitoDTO disassociaAllegatoPagamento(long idDocumentoIndex, long idPagamento, long idProgetto, long idUtente, String idIride) throws InvalidParameterException, Exception;
	EsitoDTO salvaDocumentoDiSpesa(DocumentoDiSpesaDTO documentoDiSpesaDTO, Long progrBandoLinea, long idUtente, HttpServletRequest req) throws InvalidParameterException, Exception;
	EsitoOperazioneDTO salvaDocumentoDiSpesaValidazione(DocumentoDiSpesaDTO documentoDiSpesaDTO, long idUtente, HttpServletRequest req) throws InvalidParameterException, Exception;
	List<VoceDiSpesaDTO> vociDiSpesaAssociateRendicontazione(long idDocumentoDiSpesa, long idProgetto, long idSoggetto, String codiceRuolo, String tipoOperazioneDocSpesa, String descBreveStatoDocSpesa, long idUtente, HttpServletRequest req) throws InvalidParameterException, Exception;
	List<VoceDiSpesaDTO> vociDiSpesaAssociateValidazione(long idDocumentoDiSpesa, long idProgetto, long idSoggetto, String codiceRuolo, String tipoOperazioneDocSpesa, String descBreveStatoDocSpesa, long idUtente, HttpServletRequest req) throws InvalidParameterException, Exception;
	List<VoceDiSpesaPadre> macroVociDiSpesa(long idDocumentoDiSpesa, long idProgetto, long idUtente, HttpServletRequest req) throws InvalidParameterException, Exception;
	EsitoDTO associaVoceDiSpesa(VoceDiSpesaDTO voceDiSpesaDTO, long idUtente, HttpServletRequest req) throws InvalidParameterException, Exception;
	EsitoDTO disassociaVoceDiSpesa(long idQuotaParteDocSpesa, long idUtente, HttpServletRequest req) throws InvalidParameterException, Exception;
	List<DecodificaDTO> modalitaPagamento(long idDocumentoDiSpesa, long idProgetto, long idUtente, HttpServletRequest req) throws InvalidParameterException, Exception;
	List<DecodificaDTO> causaliPagamento(HttpServletRequest req) throws InvalidParameterException, Exception;
	//EsitoDTO associaPagamento(AssociaPagamentoRequest associaPagamentoRequest, HttpServletRequest req) throws InvalidParameterException, Exception;
	EsitoDTO associaPagamento(AssociaPagamentoRequest associaPagamentoRequest, Long idUtente, String idIride) throws InvalidParameterException, Exception;
	EsitoDTO disassociaPagamento(long idPagamento, HttpServletRequest req) throws InvalidParameterException, Exception;
	EsitoAssociaFilesDTO associaAllegatiAPagamento(AssociaFilesRequest associaFilesRequest, Long idUtente) throws InvalidParameterException, Exception;
	EsitoAssociaFilesDTO associaAllegatiADocSpesa(AssociaFilesRequest associaFilesRequest, Long idUtente) throws InvalidParameterException, Exception;
	List<VoceDiSpesaPadre> vociDiSpesaRicerca(long idProgetto, HttpServletRequest req) throws InvalidParameterException, Exception;
	FiltroRicercaRendicontazionePartners partners(long idProgetto, long idBandoLinea, HttpServletRequest req) throws InvalidParameterException, Exception;
	EsitoRicercaDocumentiDiSpesa ricercaDocumentiDiSpesa(Long idProgetto, String codiceProgettoCorrente, Long idSoggettoBeneficiario, String codiceRuolo, FiltroRicercaDocumentiSpesa filtro, Long idUtente, String idIride, Long idSoggetto) throws Exception;
	DocumentoDiSpesaDTO documentoDiSpesa(long idDocumentoDiSpesa, long idProgetto, long idUtente, String idIride) throws InvalidParameterException, Exception;
	EsitoDTO eliminaDocumentoDiSpesa(long idDocumentoDiSpesa, long idProgetto, long idUtente, String idIride) throws InvalidParameterException, Exception;
	//List<DocumentoDiPagamentoDTO> pagamentiAssociati(long idDocumentoDiSpesa, String tipoInvioDocumentoDiSpesa, String descBreveStatoDocSpesa,  String tipoOperazioneDocSpesa, long idProgetto, long idBandoLinea, String codiceRuolo, boolean validazione, long idUtente, String idIride, long idSoggetto) throws InvalidParameterException, Exception;	
	List<DocumentoDiPagamentoDTO> pagamentiAssociati(PagamentiAssociatiRequest pagamentiAssociatiRequest, long idUtente, String idIride, long idSoggetto) throws InvalidParameterException, Exception;
	List<DocumentoDiSpesaDTO> noteDiCreditoFattura(long idDocumentoDiSpesa, long idProgetto, long idUtente, String idIride) throws InvalidParameterException, Exception;
	FormAssociaDocSpesa popolaFormAssociaDocSpesa(Long idDocumentoDiSpesa, Long idUtente, String idIride) throws InvalidParameterException, Exception;
	EsitoOperazioneDTO associaDocumentoDiSpesaAProgetto(Long idDocumentoDiSpesa, Long idProgetto, Long idProgettoDocumento, String task, Double importoRendicontabile, Long idUtente, String idIride) throws InvalidParameterException, Exception;
	EsitoLetturaXmlFattElett esitoLetturaXmlFattElett(Long idDocumentoIndex, Long idSoggettoBeneficiario, Long idTipoOperazioneProgetto, Long idUtente, String idIride) throws InvalidParameterException, Exception;
	BigDecimal getTipoBeneficiario(Long idProgetto, Long idSoggetto, String codiceRuolo, Long idUtente, String idIride) throws InvalidParameterException, Exception;
	Double getQuotaImportoAgevolato(Long idProgetto, Long idSoggetto, String codiceRuolo, Long idUtente, String idIride, Long idBeneficiario) throws InvalidParameterException, Exception;
	BigDecimal getTipoBeneficiario(Long idProgetto) throws Exception;
	String getDocumentazioneDaAllegare (Long progrBandoLineaIntervento, Long idTipoDocumentoSpesa, Long idUtente, String idIride) throws InvalidParameterException, Exception;
	List<ParametroCompensiDTO> getParametriCompensi(Long idUtente, String idIride) throws InvalidParameterException, Exception;
	EsitoImportoSaldoDTO getImportoASaldo(Long idProgetto, Long idSoggetto, String codiceRuolo, Long idUtente, String idIride, Long idBeneficiario) throws InvalidParameterException, Exception;
	Long getGiorniMassimiQuietanzaPerBando(Long idBandoLinea, Long idUtente, String idIride) throws InvalidParameterException, Exception;
}
