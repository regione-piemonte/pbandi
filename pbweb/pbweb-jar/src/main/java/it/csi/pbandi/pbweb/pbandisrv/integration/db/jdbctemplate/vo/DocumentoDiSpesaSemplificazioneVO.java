package it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo;

import java.math.BigDecimal;
import java.sql.Date;

public class DocumentoDiSpesaSemplificazioneVO extends GenericVO {

	private String codiceProgetto;
	private String codiceFiscaleFornitore;
	private Date dataDocumentoDiSpesa;
	//private Date dataDa;
//	private Date dataA;
	private String descBreveStatoDocSpesa;
	private String descStatoDocumentoSpesa;
	private String descTipoDocumentoDiSpesa;
	private String estremiTabella;
	private String flagGestInProgetto;
	private String fornitoreTabella;
	private BigDecimal idDocumentoDiSpesa;
	private String tipoInvio;
	private BigDecimal idDocRiferimento;
	private BigDecimal idFornitore;
	private BigDecimal idProgetto;
	private BigDecimal idSoggetto;
	private BigDecimal idStatoDocumentoSpesa;
	private BigDecimal idTipoDocumentoDiSpesa;
	private BigDecimal idVoceDiSpesa;
	private String importiTotaliDocumentiIvati;
	private BigDecimal importoTotaleDocumentoIvato;
	private BigDecimal importoTotaleValidato;
	private String numeroDocumento;
	private String flagModificabile;
	private String flagClonabile;
	private String flagEliminabile;
	private String flagAssociabile;
	private String flagAssociato;
	private String flagAllegati;
	private BigDecimal totalePagamenti;
	private BigDecimal totaleNoteCredito;
	private BigDecimal idCategoria;

	public BigDecimal getIdFornitore() {
		return idFornitore;
	}

	public void setIdFornitore(BigDecimal idFornitore) {
		this.idFornitore = idFornitore;
	}

	public BigDecimal getIdDocumentoDiSpesa() {
		return idDocumentoDiSpesa;
	}

	public void setIdDocumentoDiSpesa(BigDecimal idDocumentoDiSpesa) {
		this.idDocumentoDiSpesa = idDocumentoDiSpesa;
	}

	public Date getDataDocumentoDiSpesa() {
		return dataDocumentoDiSpesa;
	}

	public void setDataDocumentoDiSpesa(Date dataDocumentoDiSpesa) {
		this.dataDocumentoDiSpesa = dataDocumentoDiSpesa;
	}

	public BigDecimal getIdStatoDocumentoSpesa() {
		return idStatoDocumentoSpesa;
	}

	public void setIdStatoDocumentoSpesa(BigDecimal idStatoDocumentoSpesa) {
		this.idStatoDocumentoSpesa = idStatoDocumentoSpesa;
	}

	public String getDescBreveStatoDocSpesa() {
		return descBreveStatoDocSpesa;
	}

	public void setDescBreveStatoDocSpesa(String descBreveStatoDocSpesa) {
		this.descBreveStatoDocSpesa = descBreveStatoDocSpesa;
	}

	public String getDescStatoDocumentoSpesa() {
		return descStatoDocumentoSpesa;
	}

	public void setDescStatoDocumentoSpesa(String descStatoDocumentoSpesa) {
		this.descStatoDocumentoSpesa = descStatoDocumentoSpesa;
	}

	public BigDecimal getIdSoggetto() {
		return idSoggetto;
	}

	public void setIdSoggetto(BigDecimal idSoggetto) {
		this.idSoggetto = idSoggetto;
	}

	public BigDecimal getIdTipoDocumentoDiSpesa() {
		return idTipoDocumentoDiSpesa;
	}

	public void setIdTipoDocumentoDiSpesa(BigDecimal idTipoDocumentoDiSpesa) {
		this.idTipoDocumentoDiSpesa = idTipoDocumentoDiSpesa;
	}

	public BigDecimal getImportoTotaleDocumentoIvato() {
		return importoTotaleDocumentoIvato;
	}

	public void setImportoTotaleDocumentoIvato(
			BigDecimal importoTotaleDocumentoIvato) {
		this.importoTotaleDocumentoIvato = importoTotaleDocumentoIvato;
	}

	public String getImportiTotaliDocumentiIvati() {
		return importiTotaliDocumentiIvati;
	}

	public void setImportiTotaliDocumentiIvati(
			String importiTotaliDocumentiIvati) {
		this.importiTotaliDocumentiIvati = importiTotaliDocumentiIvati;
	}

	public String getNumeroDocumento() {
		return numeroDocumento;
	}

	public void setNumeroDocumento(String numeroDocumento) {
		this.numeroDocumento = numeroDocumento;
	}

	public BigDecimal getIdProgetto() {
		return idProgetto;
	}

	public void setIdProgetto(BigDecimal idProgetto) {
		this.idProgetto = idProgetto;
	}

	public String getFlagGestInProgetto() {
		return flagGestInProgetto;
	}

	public void setFlagGestInProgetto(String flagGestInProgetto) {
		this.flagGestInProgetto = flagGestInProgetto;
	}

	public BigDecimal getImportoTotaleValidato() {
		return importoTotaleValidato;
	}

	public void setImportoTotaleValidato(BigDecimal importoTotaleValidato) {
		this.importoTotaleValidato = importoTotaleValidato;
	}

	public void setCodiceProgetto(String codiceProgetto) {
		this.codiceProgetto = codiceProgetto;
	}

	public String getCodiceProgetto() {
		return codiceProgetto;
	}

	public String getCodiceFiscaleFornitore() {
		return codiceFiscaleFornitore;
	}

	public void setCodiceFiscaleFornitore(String codiceFiscaleFornitore) {
		this.codiceFiscaleFornitore = codiceFiscaleFornitore;
	}

	public void setDescTipoDocumentoDiSpesa(String descTipoDocumentoDiSpesa) {
		this.descTipoDocumentoDiSpesa = descTipoDocumentoDiSpesa;
	}

	public String getDescTipoDocumentoDiSpesa() {
		return descTipoDocumentoDiSpesa;
	}

	public String getFornitoreTabella() {
		return fornitoreTabella;
	}

	public void setFornitoreTabella(String fornitoreTabella) {
		this.fornitoreTabella = fornitoreTabella;
	}

	public String getEstremiTabella() {
		return estremiTabella;
	}

	public void setEstremiTabella(String estremiTabella) {
		this.estremiTabella = estremiTabella;
	}

	public BigDecimal getIdVoceDiSpesa() {
		return idVoceDiSpesa;
	}

	public void setIdVoceDiSpesa(BigDecimal idVoceDiSpesa) {
		this.idVoceDiSpesa = idVoceDiSpesa;
	}

	public String getFlagModificabile() {
		return flagModificabile;
	}

	public void setFlagModificabile(String flagModificabile) {
		this.flagModificabile = flagModificabile;
	}

	public String getFlagClonabile() {
		return flagClonabile;
	}

	public void setFlagClonabile(String flagClonabile) {
		this.flagClonabile = flagClonabile;
	}

	public String getFlagEliminabile() {
		return flagEliminabile;
	}

	public void setFlagEliminabile(String flagEliminabile) {
		this.flagEliminabile = flagEliminabile;
	}

	public String getFlagAssociabile() {
		return flagAssociabile;
	}

	public void setFlagAssociabile(String flagAssociabile) {
		this.flagAssociabile = flagAssociabile;
	}

	public String getFlagAssociato() {
		return flagAssociato;
	}

	public void setFlagAssociato(String flagAssociato) {
		this.flagAssociato = flagAssociato;
	}

	public BigDecimal getTotalePagamenti() {
		return totalePagamenti;
	}

	public void setTotalePagamenti(BigDecimal totalePagamenti) {
		this.totalePagamenti = totalePagamenti;
	}

	public BigDecimal getTotaleNoteCredito() {
		return totaleNoteCredito;
	}

	public void setTotaleNoteCredito(BigDecimal totaleNoteCredito) {
		this.totaleNoteCredito = totaleNoteCredito;
	}

	public BigDecimal getIdDocRiferimento() {
		return idDocRiferimento;
	}

	public void setIdDocRiferimento(BigDecimal idDocRiferimento) {
		this.idDocRiferimento = idDocRiferimento;
	}

	public String getTipoInvio() {
		return tipoInvio;
	}

	public void setTipoInvio(String tipoInvio) {
		this.tipoInvio = tipoInvio;
	}

	public String getFlagAllegati() {
		return flagAllegati;
	}

	public void setFlagAllegati(String flagAllegati) {
		this.flagAllegati = flagAllegati;
	}


	public BigDecimal getIdCategoria() {
		return idCategoria;
	}

	public void setIdCategoria(BigDecimal idCategoria) {
		this.idCategoria = idCategoria;
	}

	/*public Date getDataA() {
		return dataA;
	}

	public void setDataA(Date dataA) {
		this.dataA = dataA;
	}

	public Date getDataDa() {
		return dataDa;
	}

	public void setDataDa(Date dataDa) {
		this.dataDa = dataDa;
	}*/

}
