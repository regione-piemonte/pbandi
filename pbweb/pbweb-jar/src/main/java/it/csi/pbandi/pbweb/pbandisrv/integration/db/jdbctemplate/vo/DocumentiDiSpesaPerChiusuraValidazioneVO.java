/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo;

import java.math.BigDecimal;
import java.util.Date;

public class DocumentiDiSpesaPerChiusuraValidazioneVO extends GenericVO {
	private BigDecimal idDichiarazioneSpesa;
	private BigDecimal idDocumentoDiSpesa;
	private BigDecimal idProgetto;
	private BigDecimal idStatoDocumentoSpesa;
	private BigDecimal idTipoDocumentoSpesa;
	private String descBreveTipoDocSpesa;
	private String descTipoDocumentoSpesa;
	private String numeroDocumento;
	private Date dtEmissioneDocumento;
	private String descStatoDocumentoSpesa;
	private String descBreveStatoDocSpesa;
	private BigDecimal importoTotaleDocumento;
	private BigDecimal totaleRendicontabili;
	private BigDecimal importoRendicontazione;
	private BigDecimal noteCreditoTotale;
	private BigDecimal noteCreditoTotaleRen;
//	private BigDecimal numeroPagamenti;
	private BigDecimal  totalePagamenti;
//	private BigDecimal  numeroPagamentiAperti;
	private BigDecimal  totaleValidato;
	private BigDecimal totaleValidatoPrj;
	
   public BigDecimal getIdProgetto() {
		return idProgetto;
	}

	public void setIdProgetto(BigDecimal idProgetto) {
		this.idProgetto = idProgetto;
	}

	public BigDecimal getImportoRendicontazione() {
		return importoRendicontazione;
	}

	public void setImportoRendicontazione(BigDecimal importoRendicontazione) {
		this.importoRendicontazione = importoRendicontazione;
	}

	public BigDecimal getTotaleValidatoPrj() {
		return totaleValidatoPrj;
	}

	public void setTotaleValidatoPrj(BigDecimal totaleValidatoPrj) {
		this.totaleValidatoPrj = totaleValidatoPrj;
	}

	//	private BigDecimal  numeroDichiarazioniAperte;
	private BigDecimal idStatoDocumentoSpesaValid;
	private String descBreveStatoDocValid;
	private String descStatoDocumentoValid;

	
	public BigDecimal getIdStatoDocumentoSpesaValid() {
		return idStatoDocumentoSpesaValid;
	}

	public void setIdStatoDocumentoSpesaValid(BigDecimal idStatoDocumentoSpesaValid) {
		this.idStatoDocumentoSpesaValid = idStatoDocumentoSpesaValid;
	}

	public String getDescBreveStatoDocValid() {
		return descBreveStatoDocValid;
	}

	public void setDescBreveStatoDocValid(String descBreveStatoDocValid) {
		this.descBreveStatoDocValid = descBreveStatoDocValid;
	}

	public String getDescStatoDocumentoValid() {
		return descStatoDocumentoValid;
	}

	public void setDescStatoDocumentoValid(String descStatoDocumentoValid) {
		this.descStatoDocumentoValid = descStatoDocumentoValid;
	}

	public BigDecimal getIdStatoDocumentoSpesa() {
		return idStatoDocumentoSpesa;
	}

	public void setIdStatoDocumentoSpesa(BigDecimal idStatoDocumentoSpesa) {
		this.idStatoDocumentoSpesa = idStatoDocumentoSpesa;
	}

	public String getDescBreveTipoDocSpesa() {
		return descBreveTipoDocSpesa;
	}

	public void setDescBreveTipoDocSpesa(String descBreveTipoDocSpesa) {
		this.descBreveTipoDocSpesa = descBreveTipoDocSpesa;
	}

	public String getDescTipoDocumentoSpesa() {
		return descTipoDocumentoSpesa;
	}

	public void setDescTipoDocumentoSpesa(String descTipoDocumentoSpesa) {
		this.descTipoDocumentoSpesa = descTipoDocumentoSpesa;
	}

	public String getDescStatoDocumentoSpesa() {
		return descStatoDocumentoSpesa;
	}

	public void setDescStatoDocumentoSpesa(String descStatoDocumentoSpesa) {
		this.descStatoDocumentoSpesa = descStatoDocumentoSpesa;
	}

	public String getDescBreveStatoDocSpesa() {
		return descBreveStatoDocSpesa;
	}

	public void setDescBreveStatoDocSpesa(String descBreveStatoDocSpesa) {
		this.descBreveStatoDocSpesa = descBreveStatoDocSpesa;
	}

	public BigDecimal getTotaleRendicontabili() {
		return totaleRendicontabili;
	}

	public void setTotaleRendicontabili(BigDecimal totaleRendicontabili) {
		this.totaleRendicontabili = totaleRendicontabili;
	}

	public BigDecimal getNoteCreditoTotaleRen() {
		return noteCreditoTotaleRen;
	}

	public void setNoteCreditoTotaleRen(BigDecimal noteCreditoTotaleRen) {
		this.noteCreditoTotaleRen = noteCreditoTotaleRen;
	}

	public BigDecimal getTotalePagamenti() {
		return totalePagamenti;
	}

	public void setTotalePagamenti(BigDecimal totalePagamenti) {
		this.totalePagamenti = totalePagamenti;
	}

	public BigDecimal getTotaleValidato() {
		return totaleValidato;
	}

	public void setTotaleValidato(BigDecimal totaleValidato) {
		this.totaleValidato = totaleValidato;
	}

	

	

	public void setNoteCreditoTotale(BigDecimal noteCreditoTotale) {
		this.noteCreditoTotale = noteCreditoTotale;
	}

	public BigDecimal getNoteCreditoTotale() {
		return noteCreditoTotale;
	}


	public void setIdDichiarazioneSpesa(BigDecimal idDichiarazioneSpesa) {
		this.idDichiarazioneSpesa = idDichiarazioneSpesa;
	}

	public BigDecimal getIdDichiarazioneSpesa() {
		return idDichiarazioneSpesa;
	}

	public void setIdDocumentoDiSpesa(BigDecimal idDocumentoDiSpesa) {
		this.idDocumentoDiSpesa = idDocumentoDiSpesa;
	}

	public BigDecimal getIdDocumentoDiSpesa() {
		return idDocumentoDiSpesa;
	}

	public void setIdTipoDocumentoSpesa(BigDecimal idTipoDocumentoSpesa) {
		this.idTipoDocumentoSpesa = idTipoDocumentoSpesa;
	}

	public BigDecimal getIdTipoDocumentoSpesa() {
		return idTipoDocumentoSpesa;
	}

	public void setNumeroDocumento(String numeroDocumento) {
		this.numeroDocumento = numeroDocumento;
	}

	public String getNumeroDocumento() {
		return numeroDocumento;
	}

	public void setImportoTotaleDocumento(BigDecimal importoTotaleDocumento) {
		this.importoTotaleDocumento = importoTotaleDocumento;
	}

	public BigDecimal getImportoTotaleDocumento() {
		return importoTotaleDocumento;
	}

	public void setDtEmissioneDocumento(Date dtEmissioneDocumento) {
		this.dtEmissioneDocumento = dtEmissioneDocumento;
	}

	public Date getDtEmissioneDocumento() {
		return dtEmissioneDocumento;
	}


}
