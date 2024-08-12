package it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.gestionedocumentidispesa;

import java.math.BigDecimal;

import it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.GenericVO;

public class ProgettoAssociatoDocumentoDiSpesaVO extends GenericVO {
	
	private BigDecimal idDocumentoDiSpesa;
	private BigDecimal idProgetto;
	private BigDecimal idTipoDocumentoSpesa;
	private BigDecimal idStatoDocumentoSpesa;
	private String descBreveStatoDocSpesa;
	private String descStatoDocumentoSpesa;
	private BigDecimal massimoRendicontabile;
	private String codiceProgetto;
	private String codiceVisualizzato;
	private BigDecimal importoRendicontazione;
	//private String flagPagModificabili;
	private String flagDocRiferito;
	private String task;

	public BigDecimal getIdDocumentoDiSpesa() {
		return idDocumentoDiSpesa;
	}
	public void setIdDocumentoDiSpesa(BigDecimal idDocumentoDiSpesa) {
		this.idDocumentoDiSpesa = idDocumentoDiSpesa;
	}
	public BigDecimal getIdProgetto() {
		return idProgetto;
	}
	public void setIdProgetto(BigDecimal idProgetto) {
		this.idProgetto = idProgetto;
	}
	public BigDecimal getIdTipoDocumentoSpesa() {
		return idTipoDocumentoSpesa;
	}
	public void setIdTipoDocumentoSpesa(BigDecimal idTipoDocumentoSpesa) {
		this.idTipoDocumentoSpesa = idTipoDocumentoSpesa;
	}
	public BigDecimal getIdStatoDocumentoSpesa() {
		return idStatoDocumentoSpesa;
	}
	public void setIdStatoDocumentoSpesa(BigDecimal idStatoDocumentoSpesa) {
		this.idStatoDocumentoSpesa = idStatoDocumentoSpesa;
	}
	public BigDecimal getMassimoRendicontabile() {
		return massimoRendicontabile;
	}
	public void setMassimoRendicontabile(BigDecimal massimoRendicontabile) {
		this.massimoRendicontabile = massimoRendicontabile;
	}
	public String getCodiceProgetto() {
		return codiceProgetto;
	}
	public void setCodiceProgetto(String codiceProgetto) {
		this.codiceProgetto = codiceProgetto;
	}
	public String getCodiceVisualizzato() {
		return codiceVisualizzato;
	}
	public void setCodiceVisualizzato(String codiceVisualizzato) {
		this.codiceVisualizzato = codiceVisualizzato;
	}
	public BigDecimal getImportoRendicontazione() {
		return importoRendicontazione;
	}
	public void setImportoRendicontazione(BigDecimal importoRendicontazione) {
		this.importoRendicontazione = importoRendicontazione;
	}

	/*
	public String getFlagPagModificabili() {
		return flagPagModificabili;
	}
	public void setFlagPagModificabili(String flagPagModificabili) {
		this.flagPagModificabili = flagPagModificabili;
	}
	*/
	
	public String getFlagDocRiferito() {
		return flagDocRiferito;
	}
	public void setFlagDocRiferito(String flagDocRiferito) {
		this.flagDocRiferito = flagDocRiferito;
	}
	public String getTask() {
		return task;
	}
	public void setTask(String task) {
		this.task = task;
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

}
