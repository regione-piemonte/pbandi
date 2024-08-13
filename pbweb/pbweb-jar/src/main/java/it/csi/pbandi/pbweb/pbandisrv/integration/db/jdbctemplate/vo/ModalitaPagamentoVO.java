/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo;

import java.math.BigDecimal;

public class ModalitaPagamentoVO extends GenericVO {
	
	private BigDecimal idModalitaPagamento;
	private String descBreveModalitaPagamento;
	private String descModalitaPagamento;
	
	public BigDecimal getIdModalitaPagamento() {
		return idModalitaPagamento;
	}
	public void setIdModalitaPagamento(BigDecimal idModalitaPagamento) {
		this.idModalitaPagamento = idModalitaPagamento;
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

}
