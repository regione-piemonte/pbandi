/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweb.dto.dichiarazioneDiSpesa;

import java.io.Serializable;

public class DocumentoDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer idPagamento;
	private Integer idDocumentoSpesa;
	private String statoDocumento;
	
	public Integer getIdPagamento() {
		return idPagamento;
	}
	public void setIdPagamento(Integer idPagamento) {
		this.idPagamento = idPagamento;
	}
	public Integer getIdDocumentoSpesa() {
		return idDocumentoSpesa;
	}
	public void setIdDocumentoSpesa(Integer idDocumentoSpesa) {
		this.idDocumentoSpesa = idDocumentoSpesa;
	}
	public String getStatoDocumento() {
		return statoDocumento;
	}
	public void setStatoDocumento(String statoDocumento) {
		this.statoDocumento = statoDocumento;
	}
	
	

}
