/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.gestionepagamenti;

import java.math.BigDecimal;

import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.GenericVO;

public class DocumentoIndexAssociatoPagamentoVO extends GenericVO {

	private BigDecimal idDocumentoIndex;
	private BigDecimal idPagamento;
	private BigDecimal idProgetto;
	private String nomeFile;
	private String uuidNodo;

	
	
	public BigDecimal getIdDocumentoIndex() {
		return idDocumentoIndex;
	}
	public void setIdDocumentoIndex(BigDecimal idDocumentoIndex) {
		this.idDocumentoIndex = idDocumentoIndex;
	}
	
	public String getNomeFile() {
		return nomeFile;
	}
	public void setNomeFile(String nomeFile) {
		this.nomeFile = nomeFile;
	}
	public BigDecimal getIdPagamento() {
		return idPagamento;
	}
	public void setIdPagamento(BigDecimal idPagamento) {
		this.idPagamento = idPagamento;
	}
	public BigDecimal getIdProgetto() {
		return idProgetto;
	}
	public void setIdProgetto(BigDecimal idProgetto) {
		this.idProgetto = idProgetto;
	}
	public String getUuidNodo() {
		return uuidNodo;
	}
	public void setUuidNodo(String uuidNodo) {
		this.uuidNodo = uuidNodo;
	}

}
