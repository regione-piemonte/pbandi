/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebrce.pbandisrv.integration.db.jdbctemplate.vo.neoflux;

import java.math.BigDecimal;

import it.csi.pbandi.pbwebrce.pbandisrv.integration.db.jdbctemplate.vo.GenericVO;

public class MetaDataVO extends GenericVO {
	
	
	private String nome;
	private String valore;
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getValore() {
		return valore;
	}
	public void setValore(String valore) {
		this.valore = valore;
	}
	

	
}
