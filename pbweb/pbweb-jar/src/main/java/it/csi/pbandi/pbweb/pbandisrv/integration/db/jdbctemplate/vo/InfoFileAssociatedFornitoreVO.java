/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo;

import java.math.BigDecimal;


public class InfoFileAssociatedFornitoreVO extends InfoFileVO {
	
	private String cfFornitore;
	private String nomeFornitore;
	private BigDecimal idFornitore;
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
	public BigDecimal getIdFornitore() {
		return idFornitore;
	}
	public void setIdFornitore(BigDecimal idFornitore) {
		this.idFornitore = idFornitore;
	}
 

 

}
