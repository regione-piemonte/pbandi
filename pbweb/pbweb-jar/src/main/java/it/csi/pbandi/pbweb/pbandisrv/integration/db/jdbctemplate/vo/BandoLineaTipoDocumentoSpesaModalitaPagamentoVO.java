/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo;

import java.math.BigDecimal;

public class BandoLineaTipoDocumentoSpesaModalitaPagamentoVO extends GenericVO {
	
	private BigDecimal progrBandoLineaIntervento;
	private BigDecimal idModalitaPagamento;
	private BigDecimal idTipoDocumentoSpesa;
	private String descBreveModalitaPagamento;
	private String descModalitaPagamento;
	private String descBreveTipoDocSpesa;
	private String descTipoDocumentoSpesa;
	
	public BigDecimal getIdModalitaPagamento() {
		return idModalitaPagamento;
	}
	public void setIdModalitaPagamento(BigDecimal idModalitaPagamento) {
		this.idModalitaPagamento = idModalitaPagamento;
	}
	public BigDecimal getIdTipoDocumentoSpesa() {
		return idTipoDocumentoSpesa;
	}
	public void setIdTipoDocumentoSpesa(BigDecimal idTipoDocumentoSpesa) {
		this.idTipoDocumentoSpesa = idTipoDocumentoSpesa;
	}
	
	public BigDecimal getProgrBandoLineaIntervento() {
		return progrBandoLineaIntervento;
	}
	public void setProgrBandoLineaIntervento(BigDecimal progrBandoLineaIntervento) {
		this.progrBandoLineaIntervento = progrBandoLineaIntervento;
	}
	public String getDescBreveModalitaPagamento() {
		return descBreveModalitaPagamento;
	}
	public void setDescBreveModalitaPagamento(String descBreveModalitaPagamento) {
		this.descBreveModalitaPagamento = descBreveModalitaPagamento;
	}
	public String getDescModalitaPagamento() {
		return descModalitaPagamento;
	}
	public void setDescModalitaPagamento(String descModalitaPagamento) {
		this.descModalitaPagamento = descModalitaPagamento;
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
	
	

}
