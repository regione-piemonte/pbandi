/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.validazionerendicontazione;


import java.math.BigDecimal;

public class DocumentoDiSpesaVO {
	static final long serialVersionUID = 1;
	
	
	private java.lang.String codiceFiscaleBeneficiario = null;
	private java.lang.String codiceProgetto = null;
	private java.util.Date dtChiusuraValidazione= null;
	private java.util.Date dataDichiarazioneDiSpesa = null;
	private java.util.Date dataEmissione= null;
	private java.util.Date dataInizioRendicontazione = null;
	private java.lang.String descStatoDocumento =null;
	private java.lang.String descTipoDocumento =null;
	private java.lang.Long idBandoLinea = null;
	private java.lang.Long idDichiarazione = null;
	private java.lang.Long idDocumentoDiSpesa= null;
	private java.lang.Long idFornitore=null;
	private java.lang.Long idProgetto=null;
	private java.lang.Long idSoggetto = null;
	private java.lang.Long idTipoDocumentoDiSpesa= null;
	private Double importoTotaleDocumento= null;
	private Double importoRendicontabile= null;
	private java.lang.Long idUtente = null;
	private java.lang.String numeroDocumento= null;
	private java.lang.String noteChiusuraValidazione= null;
	private BigDecimal noteCreditoRenTotale = null;
	private BigDecimal noteCreditoTotale= null;
	private BigDecimal totaleImportoPagamenti;
	private BigDecimal totaleNoteCredito;
	private BigDecimal totaleImportoQuotaParte;
	private BigDecimal totaleTuttiPagamenti;
	private BigDecimal validatoTotale;
	private java.lang.Long numeroPagamenti;
	private java.lang.Long numeroPagamentiAperti;
	private java.lang.Long numeroDichiarazioniAperte;
	/**
	 * @return the numeroPagamenti
	 */
	public java.lang.Long getNumeroPagamenti() {
		return numeroPagamenti;
	}
	/**
	 * @param numeroPagamenti the numeroPagamenti to set
	 */
	public void setNumeroPagamenti(java.lang.Long numeroPagamenti) {
		this.numeroPagamenti = numeroPagamenti;
	}
	/**
	 * @return the numeroPagamentiAperti
	 */
	public java.lang.Long getNumeroPagamentiAperti() {
		return numeroPagamentiAperti;
	}
	/**
	 * @param numeroPagamentiAperti the numeroPagamentiAperti to set
	 */
	public void setNumeroPagamentiAperti(java.lang.Long numeroPagamentiAperti) {
		this.numeroPagamentiAperti = numeroPagamentiAperti;
	}
	/**
	 * @return the numeroDichiarazioniAperte
	 */
	public java.lang.Long getNumeroDichiarazioniAperte() {
		return numeroDichiarazioniAperte;
	}
	/**
	 * @param numeroDichiarazioniAperte the numeroDichiarazioniAperte to set
	 */
	public void setNumeroDichiarazioniAperte(
			java.lang.Long numeroDichiarazioniAperte) {
		this.numeroDichiarazioniAperte = numeroDichiarazioniAperte;
	}
	/**
	 * @return the idDocumentoDiSpesa
	 */
	public java.lang.Long getIdDocumentoDiSpesa() {
		return idDocumentoDiSpesa;
	}
	/**
	 * @param idDocumentoDiSpesa the idDocumentoDiSpesa to set
	 */
	public void setIdDocumentoDiSpesa(java.lang.Long idDocumentoDiSpesa) {
		this.idDocumentoDiSpesa = idDocumentoDiSpesa;
	}
	/**
	 * @return the importoTotaleDocumento
	 */
	public Double getImportoTotaleDocumento() {
		return importoTotaleDocumento;
	}
	/**
	 * @param importoTotaleDocumento the importoTotaleDocumento to set
	 */
	public void setImportoTotaleDocumento(Double importoTotaleDocumento) {
		this.importoTotaleDocumento = importoTotaleDocumento;
	}
	/**
	 * @return the importoRendicontabile
	 */
	public Double getImportoRendicontabile() {
		return importoRendicontabile;
	}
	/**
	 * @param importoRendicontabile the importoRendicontabile to set
	 */
	public void setImportoRendicontabile(Double importoRendicontabile) {
		this.importoRendicontabile = importoRendicontabile;
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
	/**
	 * @return the totaleImportoQuotaParte
	 */
	public BigDecimal getTotaleImportoQuotaParte() {
		return totaleImportoQuotaParte;
	}
	/**
	 * @param totaleImportoQuotaParte the totaleImportoQuotaParte to set
	 */
	public void setTotaleImportoQuotaParte(BigDecimal totaleImportoQuotaParte) {
		this.totaleImportoQuotaParte = totaleImportoQuotaParte;
	}
	/**
	 * @return the totaleTuttiPagamenti
	 */
	public BigDecimal getTotaleTuttiPagamenti() {
		return totaleTuttiPagamenti;
	}
	/**
	 * @param totaleTuttiPagamenti the totaleTuttiPagamenti to set
	 */
	public void setTotaleTuttiPagamenti(BigDecimal totaleTuttiPagamenti) {
		this.totaleTuttiPagamenti = totaleTuttiPagamenti;
	}
	/**
	 * @return the codiceFiscaleBeneficiario
	 */
	public java.lang.String getCodiceFiscaleBeneficiario() {
		return codiceFiscaleBeneficiario;
	}
	/**
	 * @param codiceFiscaleBeneficiario the codiceFiscaleBeneficiario to set
	 */
	public void setCodiceFiscaleBeneficiario(
			java.lang.String codiceFiscaleBeneficiario) {
		this.codiceFiscaleBeneficiario = codiceFiscaleBeneficiario;
	}
	/**
	 * @return the codiceProgetto
	 */
	public java.lang.String getCodiceProgetto() {
		return codiceProgetto;
	}
	/**
	 * @param codiceProgetto the codiceProgetto to set
	 */
	public void setCodiceProgetto(java.lang.String codiceProgetto) {
		this.codiceProgetto = codiceProgetto;
	}
	/**
	 * @return the dtChiusuraValidazione
	 */
	public java.util.Date getDtChiusuraValidazione() {
		return dtChiusuraValidazione;
	}
	/**
	 * @param dtChiusuraValidazione the dtChiusuraValidazione to set
	 */
	public void setDtChiusuraValidazione(java.util.Date dtChiusuraValidazione) {
		this.dtChiusuraValidazione = dtChiusuraValidazione;
	}
	/**
	 * @return the dataDichiarazioneDiSpesa
	 */
	public java.util.Date getDataDichiarazioneDiSpesa() {
		return dataDichiarazioneDiSpesa;
	}
	/**
	 * @param dataDichiarazioneDiSpesa the dataDichiarazioneDiSpesa to set
	 */
	public void setDataDichiarazioneDiSpesa(java.util.Date dataDichiarazioneDiSpesa) {
		this.dataDichiarazioneDiSpesa = dataDichiarazioneDiSpesa;
	}
	/**
	 * @return the dataInizioRendicontazione
	 */
	public java.util.Date getDataInizioRendicontazione() {
		return dataInizioRendicontazione;
	}
	/**
	 * @param dataInizioRendicontazione the dataInizioRendicontazione to set
	 */
	public void setDataInizioRendicontazione(
			java.util.Date dataInizioRendicontazione) {
		this.dataInizioRendicontazione = dataInizioRendicontazione;
	}
	/**
	 * @return the idBandoLinea
	 */
	public java.lang.Long getIdBandoLinea() {
		return idBandoLinea;
	}
	/**
	 * @param idBandoLinea the idBandoLinea to set
	 */
	public void setIdBandoLinea(java.lang.Long idBandoLinea) {
		this.idBandoLinea = idBandoLinea;
	}
	/**
	 * @return the idDichiarazione
	 */
	public java.lang.Long getIdDichiarazione() {
		return idDichiarazione;
	}
	/**
	 * @param idDichiarazione the idDichiarazione to set
	 */
	public void setIdDichiarazione(java.lang.Long idDichiarazione) {
		this.idDichiarazione = idDichiarazione;
	}
	/**
	 * @return the idProgetto
	 */
	public java.lang.Long getIdProgetto() {
		return idProgetto;
	}
	/**
	 * @param idProgetto the idProgetto to set
	 */
	public void setIdProgetto(java.lang.Long idProgetto) {
		this.idProgetto = idProgetto;
	}
	/**
	 * @return the idSoggetto
	 */
	public java.lang.Long getIdSoggetto() {
		return idSoggetto;
	}
	/**
	 * @param idSoggetto the idSoggetto to set
	 */
	public void setIdSoggetto(java.lang.Long idSoggetto) {
		this.idSoggetto = idSoggetto;
	}
	/**
	 * @return the idUtente
	 */
	public java.lang.Long getIdUtente() {
		return idUtente;
	}
	/**
	 * @param idUtente the idUtente to set
	 */
	public void setIdUtente(java.lang.Long idUtente) {
		this.idUtente = idUtente;
	}
	/**
	 * @return the noteChiusuraValidazione
	 */
	public java.lang.String getNoteChiusuraValidazione() {
		return noteChiusuraValidazione;
	}
	/**
	 * @param noteChiusuraValidazione the noteChiusuraValidazione to set
	 */
	public void setNoteChiusuraValidazione(java.lang.String noteChiusuraValidazione) {
		this.noteChiusuraValidazione = noteChiusuraValidazione;
	}
	public void setIdTipoDocumentoDiSpesa(java.lang.Long idTipoDocumentoDiSpesa) {
		this.idTipoDocumentoDiSpesa = idTipoDocumentoDiSpesa;
	}
	public java.lang.Long getIdTipoDocumentoDiSpesa() {
		return idTipoDocumentoDiSpesa;
	}
	public void setIdFornitore(java.lang.Long idFornitore) {
		this.idFornitore = idFornitore;
	}
	public java.lang.Long getIdFornitore() {
		return idFornitore;
	}
	public void setNoteCreditoTotale(BigDecimal noteCreditoTotale) {
		this.noteCreditoTotale = noteCreditoTotale;
	}
	public BigDecimal getNoteCreditoTotale() {
		return noteCreditoTotale;
	}
	public void setNumeroDocumento(java.lang.String numeroDocumento) {
		this.numeroDocumento = numeroDocumento;
	}
	public java.lang.String getNumeroDocumento() {
		return numeroDocumento;
	}
	public void setDescTipoDocumento(java.lang.String descTipoDocumento) {
		this.descTipoDocumento = descTipoDocumento;
	}
	public java.lang.String getDescTipoDocumento() {
		return descTipoDocumento;
	}
	public void setDataEmissione(java.util.Date dataEmissione) {
		this.dataEmissione = dataEmissione;
	}
	public java.util.Date getDataEmissione() {
		return dataEmissione;
	}
	public void setDescStatoDocumento(java.lang.String descStatoDocumento) {
		this.descStatoDocumento = descStatoDocumento;
	}
	public java.lang.String getDescStatoDocumento() {
		return descStatoDocumento;
	}
	public void setNoteCreditoRenTotale(BigDecimal noteCreditoRenTotale) {
		this.noteCreditoRenTotale = noteCreditoRenTotale;
	}
	public BigDecimal getNoteCreditoRenTotale() {
		return noteCreditoRenTotale;
	}
	public void setValidatoTotale(BigDecimal validatoTotale) {
		this.validatoTotale = validatoTotale;
	}
	public BigDecimal getValidatoTotale() {
		return validatoTotale;
	}

}
