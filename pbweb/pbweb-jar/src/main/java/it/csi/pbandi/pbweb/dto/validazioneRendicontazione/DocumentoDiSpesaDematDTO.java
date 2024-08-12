package it.csi.pbandi.pbweb.dto.validazioneRendicontazione;


import it.csi.pbandi.pbweb.dto.AffidamentoValidazione;
import it.csi.pbandi.pbweb.dto.DocumentoAllegatoDTO;
import it.csi.pbandi.pbweb.dto.DocumentoAllegatoPagamentoDTO;
import it.csi.pbandi.pbweb.dto.RigaNotaDiCreditoItemDTO;
import it.csi.pbandi.pbweb.dto.RigaValidazioneItemDTO;

import java.util.List;


public class DocumentoDiSpesaDematDTO extends DocumentoDiSpesaDTO{

    private List<List<DocumentoAllegatoDTO>> documentoAllegato;
    private List<DocumentoAllegatoDTO> documentoAllegatoGenerico;

    private List<DocumentoAllegatoPagamentoDTO> documentoAllegatoPagamento;

    private DocumentoAllegatoDTO[] documentoAllegatoNotaDiCredito;
    private RigaNotaDiCreditoItemDTO[] noteDiCredito;
    private RigaValidazioneItemDTO[] rigaValidazioneItem;
    private DocumentoAllegatoDTO file;
    private String messaggiErrore;
    private DocumentoAllegatoDTO[] documentoAllegatoFornitore;
    private DocumentoAllegatoDTO[] documentoAllegatoQualificaFornitore;
    // Jira PBANDI-2768
    private Double importoValidatoSuVoceDiSpesa;

    private String descrizioneStatoValidazione;

    private AffidamentoValidazione affidamento;

    public AffidamentoValidazione getAffidamento() {
        return affidamento;
    }
    public void setAffidamento(AffidamentoValidazione affidamento) {
        this.affidamento = affidamento;
    }
    public String getDescrizioneStatoValidazione() {
        return descrizioneStatoValidazione;
    }
    public void setDescrizioneStatoValidazione(String descrizioneStatoValidazione) {
        this.descrizioneStatoValidazione = descrizioneStatoValidazione;
    }

    public List<List<DocumentoAllegatoDTO>> getDocumentoAllegato() {
        return documentoAllegato;
    }

    public void setDocumentoAllegato(List<List<DocumentoAllegatoDTO>> documentoAllegato) {
        this.documentoAllegato = documentoAllegato;
    }

    public List<DocumentoAllegatoDTO> getDocumentoAllegatoGenerico() {
        return documentoAllegatoGenerico;
    }

    public void setDocumentoAllegatoGenerico(List<DocumentoAllegatoDTO> documentoAllegatoGenerico) {
        this.documentoAllegatoGenerico = documentoAllegatoGenerico;
    }

    public List<DocumentoAllegatoPagamentoDTO> getDocumentoAllegatoPagamento() {
        return documentoAllegatoPagamento;
    }

    public void setDocumentoAllegatoPagamento(List<DocumentoAllegatoPagamentoDTO> documentoAllegatoPagamento) {
        this.documentoAllegatoPagamento = documentoAllegatoPagamento;
    }

    public DocumentoAllegatoDTO[] getDocumentoAllegatoNotaDiCredito() {
        return documentoAllegatoNotaDiCredito;
    }
    public void setDocumentoAllegatoNotaDiCredito(DocumentoAllegatoDTO[] documentoAllegatoNotaDiCredito) {
        this.documentoAllegatoNotaDiCredito = documentoAllegatoNotaDiCredito;
    }
    public RigaNotaDiCreditoItemDTO[] getNoteDiCredito() {
        return noteDiCredito;
    }
    public void setNoteDiCredito(RigaNotaDiCreditoItemDTO[] noteDiCredito) {
        this.noteDiCredito = noteDiCredito;
    }
    public RigaValidazioneItemDTO[] getRigaValidazioneItem() {
        return rigaValidazioneItem;
    }
    public void setRigaValidazioneItem(RigaValidazioneItemDTO[] rigaValidazioneItem) {
        this.rigaValidazioneItem = rigaValidazioneItem;
    }
    public DocumentoAllegatoDTO getFile() {
        return file;
    }
    public void setFile(DocumentoAllegatoDTO file) {
        this.file = file;
    }
    public String getMessaggiErrore() {
        return messaggiErrore;
    }
    public void setMessaggiErrore(String messaggiErrore) {
        this.messaggiErrore = messaggiErrore;
    }
    public DocumentoAllegatoDTO[] getDocumentoAllegatoFornitore() {
        return documentoAllegatoFornitore;
    }
    public void setDocumentoAllegatoFornitore(DocumentoAllegatoDTO[] documentoAllegatoFornitore) {
        this.documentoAllegatoFornitore = documentoAllegatoFornitore;
    }
    public DocumentoAllegatoDTO[] getDocumentoAllegatoQualificaFornitore() {
        return documentoAllegatoQualificaFornitore;
    }
    public void setDocumentoAllegatoQualificaFornitore(DocumentoAllegatoDTO[] documentoAllegatoQualificaFornitore) {
        this.documentoAllegatoQualificaFornitore = documentoAllegatoQualificaFornitore;
    }
    public Double getImportoValidatoSuVoceDiSpesa() {
        return importoValidatoSuVoceDiSpesa;
    }
    public void setImportoValidatoSuVoceDiSpesa(Double importoValidatoSuVoceDiSpesa) {
        this.importoValidatoSuVoceDiSpesa = importoValidatoSuVoceDiSpesa;
    }

}
