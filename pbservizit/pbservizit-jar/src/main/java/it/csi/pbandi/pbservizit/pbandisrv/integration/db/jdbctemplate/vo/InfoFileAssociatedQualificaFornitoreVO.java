/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo;

import java.math.BigDecimal;

public class InfoFileAssociatedQualificaFornitoreVO extends InfoFileVO {
	
	private String cfFornitore;
	private String descQualifica;
	private String nomeFornitore;
	private BigDecimal progrFornitoreQualifica;
	public String getCfFornitore() {
		return cfFornitore;
	}
	public void setCfFornitore(String cfFornitore) {
		this.cfFornitore = cfFornitore;
	}
	public String getDescQualifica() {
		return descQualifica;
	}
	public void setDescQualifica(String descQualifica) {
		this.descQualifica = descQualifica;
	}
	public String getNomeFornitore() {
		return nomeFornitore;
	}
	public void setNomeFornitore(String nomeFornitore) {
		this.nomeFornitore = nomeFornitore;
	}
	public BigDecimal getProgrFornitoreQualifica() {
		return progrFornitoreQualifica;
	}
	public void setProgrFornitoreQualifica(BigDecimal progrFornitoreQualifica) {
		this.progrFornitoreQualifica = progrFornitoreQualifica;
	}

 


}
