/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo;

import java.math.BigDecimal;

public class TipoDocumentoDiSpesaVO extends GenericVO {
	
	
	private BigDecimal idTipoDocumentoSpesa;
	private String descTipoDocumentoSpesa;
	private String descBreveTipoDocSpesa;
	public BigDecimal getIdTipoDocumentoSpesa() {
		return idTipoDocumentoSpesa;
	}
	public void setIdTipoDocumentoSpesa(BigDecimal idTipoDocumentoSpesa) {
		this.idTipoDocumentoSpesa = idTipoDocumentoSpesa;
	}
	public String getDescTipoDocumentoSpesa() {
		return descTipoDocumentoSpesa;
	}
	public void setDescTipoDocumentoSpesa(String descTipoDocumentoSpesa) {
		this.descTipoDocumentoSpesa = descTipoDocumentoSpesa;
	}
	public String getDescBreveTipoDocSpesa() {
		return descBreveTipoDocSpesa;
	}
	public void setDescBreveTipoDocSpesa(String descBreveTipoDocSpesa) {
		this.descBreveTipoDocSpesa = descBreveTipoDocSpesa;
	}
	
	

}
