/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo;

import java.math.BigDecimal;
import java.sql.Date;

public class InfoFileAssociatedPagamentoVO extends InfoFileVO {
	private String  cfFornitore ;
	private String  codiceProgetto ;
	private String descBandoLinea;
	private String descModalitaPagamento;
	private String descTipoDocumentoSpesa;
	private Date  dtEmissioneDocumento;
	private Date  dtPagamento;
	private BigDecimal 	idModalitaPagamento;
	private BigDecimal importoPagamento;
	private BigDecimal idPagamento;
	private String  nomeFornitore ;
	
	
	
	public String getCfFornitore() {
		return cfFornitore;
	}
	public void setCfFornitore(String cfFornitore) {
		this.cfFornitore = cfFornitore;
	}
	public String getNomeFornitore() {
		return nomeFornitore;
	}
	public void setNomeFornitore(String nomeFornitore) {
		this.nomeFornitore = nomeFornitore;
	}
	public String getDescTipoDocumentoSpesa() {
		return descTipoDocumentoSpesa;
	}
	public void setDescTipoDocumentoSpesa(String descTipoDocumentoSpesa) {
		this.descTipoDocumentoSpesa = descTipoDocumentoSpesa;
	}
	public Date getDtEmissioneDocumento() {
		return dtEmissioneDocumento;
	}
	public void setDtEmissioneDocumento(Date dtEmissioneDocumento) {
		this.dtEmissioneDocumento = dtEmissioneDocumento;
	}
	public String getNumeroDocumento() {
		return numeroDocumento;
	}
	public void setNumeroDocumento(String numeroDocumento) {
		this.numeroDocumento = numeroDocumento;
	}
	private String numeroDocumento;
	
	public String getDescModalitaPagamento() {
		return descModalitaPagamento;
	}
	public void setDescModalitaPagamento(String descModalitaPagamento) {
		this.descModalitaPagamento = descModalitaPagamento;
	}
 
	public String getCodiceProgetto() {
		return codiceProgetto;
	}
	public void setCodiceProgetto(String codiceProgetto) {
		this.codiceProgetto = codiceProgetto;
	}
	public String getDescBandoLinea() {
		return descBandoLinea;
	}
	public void setDescBandoLinea(String descBandoLinea) {
		this.descBandoLinea = descBandoLinea;
	}
	public Date getDtPagamento() {
		return dtPagamento;
	}
	public void setDtPagamento(Date dtPagamento) {
		this.dtPagamento = dtPagamento;
	}
	 
	public BigDecimal getIdModalitaPagamento() {
		return idModalitaPagamento;
	}
	public void setIdModalitaPagamento(BigDecimal idModalitaPagamento) {
		this.idModalitaPagamento = idModalitaPagamento;
	}
	public BigDecimal getImportoPagamento() {
		return importoPagamento;
	}
	public void setImportoPagamento(BigDecimal importoPagamento) {
		this.importoPagamento = importoPagamento;
	}
	public BigDecimal getIdPagamento() {
		return idPagamento;
	}
	public void setIdPagamento(BigDecimal idPagamento) {
		this.idPagamento = idPagamento;
	}
	
	
 
}
