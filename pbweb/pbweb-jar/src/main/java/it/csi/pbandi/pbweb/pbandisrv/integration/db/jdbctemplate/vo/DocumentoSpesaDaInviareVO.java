package it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo;

import java.math.BigDecimal;
import java.sql.Date;

public class DocumentoSpesaDaInviareVO extends GenericVO {
	
	private String codiceFiscaleFornitore;
	private String denominazioneFornitore;
	private String cognomeFornitore;
	private String nomeFornitore;
	private String flagAllegati;
	private Date dataDocumentoDiSpesa;
	private String descTipoDocumentoDiSpesa;
	private String descBreveTipoDocSpesa;  
	private BigDecimal idDocumentoDiSpesa;
	private BigDecimal idDocDiRiferimento;
	private BigDecimal idFornitore;
	private BigDecimal idSoggetto;
	private BigDecimal idTipoDocumentoDiSpesa;
	private String numeroDocumentoDiSpesa;
	private BigDecimal importoTotaleDocumento;
	private BigDecimal idProgetto;
	private BigDecimal importoRendicontazione;
	private String task;
	private BigDecimal totaleImportoPagamenti;
	private BigDecimal totaleNoteCredito;
	private BigDecimal totaleImportoQuotaParte;
	public String getDenominazioneFornitore() {
		return denominazioneFornitore;
	}
	public String getCognomeFornitore() {
		return cognomeFornitore;
	}
	public String getNomeFornitore() {
		return nomeFornitore;
	}
	public void setDenominazioneFornitore(String denominazioneFornitore) {
		this.denominazioneFornitore = denominazioneFornitore;
	}
	public void setCognomeFornitore(String cognomeFornitore) {
		this.cognomeFornitore = cognomeFornitore;
	}
	public void setNomeFornitore(String nomeFornitore) {
		this.nomeFornitore = nomeFornitore;
	}
	private BigDecimal numPagamentiInviabili;
	private String motivazione;
	private BigDecimal idStatoDocumentoSpesa;
	
	private String tipoInvio;
	private String flagElettronico;
	
	/**
	 * @return the totaleNoteCredito
	 */
	public BigDecimal getTotaleNoteCredito() {
		return totaleNoteCredito;
	}
	/**
	 * @param totaleNoteCredito the totaleNoteCredito to set
	 */
	public void setTotaleNoteCredito(BigDecimal totaleNoteCredito) {
		this.totaleNoteCredito = totaleNoteCredito;
	}
	
	
	public BigDecimal getNumPagamentiInviabili() {
		return numPagamentiInviabili;
	}
	
	public void setNumPagamentiInviabili(BigDecimal numPagamentiInviabili) {
		this.numPagamentiInviabili = numPagamentiInviabili;
	}
	



	/**
	 * @return the idDocumentoDiSpesa
	 */
	public BigDecimal getIdDocumentoDiSpesa() {
		return idDocumentoDiSpesa;
	}
	/**
	 * @param idDocumentoDiSpesa the idDocumentoDiSpesa to set
	 */
	public void setIdDocumentoDiSpesa(BigDecimal idDocumentoDiSpesa) {
		this.idDocumentoDiSpesa = idDocumentoDiSpesa;
	}

	/**
	 * @return the idFornitore
	 */
	public BigDecimal getIdFornitore() {
		return idFornitore;
	}
	/**
	 * @param idFornitore the idFornitore to set
	 */
	public void setIdFornitore(BigDecimal idFornitore) {
		this.idFornitore = idFornitore;
	}
	/**
	 * @return the idSoggetto
	 */
	public BigDecimal getIdSoggetto() {
		return idSoggetto;
	}
	/**
	 * @param idSoggetto the idSoggetto to set
	 */
	public void setIdSoggetto(BigDecimal idSoggetto) {
		this.idSoggetto = idSoggetto;
	}


	/**
	 * @return the numeroDocumentoDiSpesa
	 */
	public String getNumeroDocumentoDiSpesa() {
		return numeroDocumentoDiSpesa;
	}
	/**
	 * @param numeroDocumentoDiSpesa the numeroDocumentoDiSpesa to set
	 */
	public void setNumeroDocumentoDiSpesa(String numeroDocumentoDiSpesa) {
		this.numeroDocumentoDiSpesa = numeroDocumentoDiSpesa;
	}
	/**
	 * @return the importoTotaleDocumento
	 */
	public BigDecimal getImportoTotaleDocumento() {
		return importoTotaleDocumento;
	}
	/**
	 * @param importoTotaleDocumento the importoTotaleDocumento to set
	 */
	public void setImportoTotaleDocumento(BigDecimal importoTotaleDocumento) {
		this.importoTotaleDocumento = importoTotaleDocumento;
	}
	/**
	 * @return the idProgetto
	 */
	public BigDecimal getIdProgetto() {
		return idProgetto;
	}
	/**
	 * @param idProgetto the idProgetto to set
	 */
	public void setIdProgetto(BigDecimal idProgetto) {
		this.idProgetto = idProgetto;
	}
	/**
	 * @return the importoRendicontazione
	 */
	public BigDecimal getImportoRendicontazione() {
		return importoRendicontazione;
	}
	/**
	 * @param importoRendicontazione the importoRendicontazione to set
	 */
	public void setImportoRendicontazione(BigDecimal importoRendicontazione) {
		this.importoRendicontazione = importoRendicontazione;
	}
	/**
	 * @return the task
	 */
	public String getTask() {
		return task;
	}
	/**
	 * @param task the task to set
	 */
	public void setTask(String task) {
		this.task = task;
	}
	/**
	 * @return the totaleImportoPagamenti
	 */
	public BigDecimal getTotaleImportoPagamenti() {
		return totaleImportoPagamenti;
	}
	/**
	 * @param totaleImportoPagamenti the totaleImportoPagamenti to set
	 */
	public void setTotaleImportoPagamenti(BigDecimal totaleImportoPagamenti) {
		this.totaleImportoPagamenti = totaleImportoPagamenti;
	}
	

	/**
	 * @return the codiceFiscaleFornitore
	 */
	public String getCodiceFiscaleFornitore() {
		return codiceFiscaleFornitore;
	}
	/**
	 * @param codiceFiscaleFornitore the codiceFiscaleFornitore to set
	 */
	public void setCodiceFiscaleFornitore(String codiceFiscaleFornitore) {
		this.codiceFiscaleFornitore = codiceFiscaleFornitore;
	}
	
	/*
	public BigDecimal getTotaleTuttiPagamenti() {
		return totaleTuttiPagamenti;
	}
	
	public void setTotaleTuttiPagamenti(BigDecimal totaleTuttiPagamenti) {
		this.totaleTuttiPagamenti = totaleTuttiPagamenti;
	}
	*/
	public void setIdTipoDocumentoDiSpesa(BigDecimal idTipoDocumentoDiSpesa) {
		this.idTipoDocumentoDiSpesa = idTipoDocumentoDiSpesa;
	}
	public BigDecimal getIdTipoDocumentoDiSpesa() {
		return idTipoDocumentoDiSpesa;
	}
	public void setDescBreveTipoDocSpesa(String descBreveTipoDocSpesa) {
		this.descBreveTipoDocSpesa = descBreveTipoDocSpesa;
	}
	public String getDescBreveTipoDocSpesa() {
		return descBreveTipoDocSpesa;
	}
	public void setMotivazione(String motivazione) {
		this.motivazione = motivazione;
	}
	public String getMotivazione() {
		return motivazione;
	}
	public void setDataDocumentoDiSpesa(Date dataDocumentoDiSpesa) {
		this.dataDocumentoDiSpesa = dataDocumentoDiSpesa;
	}
	public Date getDataDocumentoDiSpesa() {
		return dataDocumentoDiSpesa;
	}
	public void setTotaleImportoQuotaParte(BigDecimal totaleImportoQuotaParte) {
		this.totaleImportoQuotaParte = totaleImportoQuotaParte;
	}
	public BigDecimal getTotaleImportoQuotaParte() {
		return totaleImportoQuotaParte;
	}
	public void setIdDocDiRiferimento(BigDecimal idDocDiRiferimento) {
		this.idDocDiRiferimento = idDocDiRiferimento;
	}
	public BigDecimal getIdDocDiRiferimento() {
		return idDocDiRiferimento;
	}
	public void setDescTipoDocumentoDiSpesa(String descTipoDocumentoDiSpesa) {
		this.descTipoDocumentoDiSpesa = descTipoDocumentoDiSpesa;
	}
	public String getDescTipoDocumentoDiSpesa() {
		return descTipoDocumentoDiSpesa;
	}
	public BigDecimal getIdStatoDocumentoSpesa() {
		return idStatoDocumentoSpesa;
	}
	public void setIdStatoDocumentoSpesa(BigDecimal idStatoDocumentoSpesa) {
		this.idStatoDocumentoSpesa = idStatoDocumentoSpesa;
	}
	public String getTipoInvio() {
		return tipoInvio;
	}
	public void setTipoInvio(String tipoInvio) {
		this.tipoInvio = tipoInvio;
	}
	public String getFlagElettronico() {
		return flagElettronico;
	}
	public void setFlagElettronico(String flagElettronico) {
		this.flagElettronico = flagElettronico;
	}
	public String getFlagAllegati() {
		return flagAllegati;
	}
	public void setFlagAllegati(String flagAllegati) {
		this.flagAllegati = flagAllegati;
	}
 
	
	
	

}
