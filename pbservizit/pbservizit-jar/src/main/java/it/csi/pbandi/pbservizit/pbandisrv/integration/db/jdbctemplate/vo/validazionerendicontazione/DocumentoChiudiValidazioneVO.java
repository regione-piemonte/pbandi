/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.validazionerendicontazione;


import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiTDocumentoDiSpesaVO;

import java.math.BigDecimal;

public class DocumentoChiudiValidazioneVO extends PbandiTDocumentoDiSpesaVO{
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
	private java.lang.Long idProgetto=null;
	private Double importoRendicontabile= null;
	private java.lang.String noteChiusuraValidazione= null;
	private BigDecimal noteCreditoRenTotale = null;
	private BigDecimal noteCreditoTotale= null;
	private java.lang.Long numeroPagamenti;
	private java.lang.Long numeroPagamentiAperti;
	private java.lang.Long numeroDichiarazioniAperte;
	private BigDecimal totaleImportoPagamenti;
	private BigDecimal totaleNoteCredito;
	private BigDecimal totaleImportoQuotaParte;
	private BigDecimal validatoTotale;

	public java.lang.Long getNumeroPagamenti() {
		return numeroPagamenti;
	}
	public void setNumeroPagamenti(java.lang.Long numeroPagamenti) {
		this.numeroPagamenti = numeroPagamenti;
	}
	public java.lang.Long getNumeroPagamentiAperti() {
		return numeroPagamentiAperti;
	}
	public void setNumeroPagamentiAperti(java.lang.Long numeroPagamentiAperti) {
		this.numeroPagamentiAperti = numeroPagamentiAperti;
	}
	public java.lang.Long getNumeroDichiarazioniAperte() {
		return numeroDichiarazioniAperte;
	}
	public void setNumeroDichiarazioniAperte(
			java.lang.Long numeroDichiarazioniAperte) {
		this.numeroDichiarazioniAperte = numeroDichiarazioniAperte;
	}
	public Double getImportoRendicontabile() {
		return importoRendicontabile;
	}
	public void setImportoRendicontabile(Double importoRendicontabile) {
		this.importoRendicontabile = importoRendicontabile;
	}
	public BigDecimal getTotaleImportoPagamenti() {
		return totaleImportoPagamenti;
	}
	public void setTotaleImportoPagamenti(BigDecimal totaleImportoPagamenti) {
		this.totaleImportoPagamenti = totaleImportoPagamenti;
	}
	public BigDecimal getTotaleNoteCredito() {
		return totaleNoteCredito;
	}
	public void setTotaleNoteCredito(BigDecimal totaleNoteCredito) {
		this.totaleNoteCredito = totaleNoteCredito;
	}
	public BigDecimal getTotaleImportoQuotaParte() {
		return totaleImportoQuotaParte;
	}
	public void setTotaleImportoQuotaParte(BigDecimal totaleImportoQuotaParte) {
		this.totaleImportoQuotaParte = totaleImportoQuotaParte;
	}

	public java.lang.String getCodiceFiscaleBeneficiario() {
		return codiceFiscaleBeneficiario;
	}
	public void setCodiceFiscaleBeneficiario(
			java.lang.String codiceFiscaleBeneficiario) {
		this.codiceFiscaleBeneficiario = codiceFiscaleBeneficiario;
	}
	public java.lang.String getCodiceProgetto() {
		return codiceProgetto;
	}
	public void setCodiceProgetto(java.lang.String codiceProgetto) {
		this.codiceProgetto = codiceProgetto;
	}
	public java.util.Date getDtChiusuraValidazione() {
		return dtChiusuraValidazione;
	}
	public void setDtChiusuraValidazione(java.util.Date dtChiusuraValidazione) {
		this.dtChiusuraValidazione = dtChiusuraValidazione;
	}
	public java.util.Date getDataDichiarazioneDiSpesa() {
		return dataDichiarazioneDiSpesa;
	}
	public void setDataDichiarazioneDiSpesa(java.util.Date dataDichiarazioneDiSpesa) {
		this.dataDichiarazioneDiSpesa = dataDichiarazioneDiSpesa;
	}
	public java.util.Date getDataInizioRendicontazione() {
		return dataInizioRendicontazione;
	}
	public void setDataInizioRendicontazione(
			java.util.Date dataInizioRendicontazione) {
		this.dataInizioRendicontazione = dataInizioRendicontazione;
	}
	public java.lang.Long getIdBandoLinea() {
		return idBandoLinea;
	}
	public void setIdBandoLinea(java.lang.Long idBandoLinea) {
		this.idBandoLinea = idBandoLinea;
	}
	public java.lang.Long getIdDichiarazione() {
		return idDichiarazione;
	}
	public void setIdDichiarazione(java.lang.Long idDichiarazione) {
		this.idDichiarazione = idDichiarazione;
	}
	public java.lang.Long getIdProgetto() {
		return idProgetto;
	}
	public void setIdProgetto(java.lang.Long idProgetto) {
		this.idProgetto = idProgetto;
	}
	public java.lang.String getNoteChiusuraValidazione() {
		return noteChiusuraValidazione;
	}
	public void setNoteChiusuraValidazione(java.lang.String noteChiusuraValidazione) {
		this.noteChiusuraValidazione = noteChiusuraValidazione;
	}

	public void setNoteCreditoTotale(BigDecimal noteCreditoTotale) {
		this.noteCreditoTotale = noteCreditoTotale;
	}
	public BigDecimal getNoteCreditoTotale() {
		return noteCreditoTotale;
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
