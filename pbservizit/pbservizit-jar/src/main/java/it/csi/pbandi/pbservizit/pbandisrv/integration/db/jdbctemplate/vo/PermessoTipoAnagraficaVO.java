/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo;

import java.math.BigDecimal;

import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiRPermessoTipoAnagrafVO;

public class PermessoTipoAnagraficaVO extends PbandiRPermessoTipoAnagrafVO {

	private String descPermesso;
  	
	private String descBrevePermesso;
  	
  	private String descBreveTipoAnagrafica;
  	
  	private String descTipoAnagrafica;
  	
  	public String getDescPermesso() {
		return descPermesso;
	}

	public void setDescPermesso(String descPermesso) {
		this.descPermesso = descPermesso;
	}

	public String getDescBrevePermesso() {
		return descBrevePermesso;
	}

	public void setDescBrevePermesso(String descBrevePermesso) {
		this.descBrevePermesso = descBrevePermesso;
	}

	public String getDescBreveTipoAnagrafica() {
		return descBreveTipoAnagrafica;
	}

	public void setDescBreveTipoAnagrafica(String descBreveTipoAnagrafica) {
		this.descBreveTipoAnagrafica = descBreveTipoAnagrafica;
	}

	public String getDescTipoAnagrafica() {
		return descTipoAnagrafica;
	}

	public void setDescTipoAnagrafica(String descTipoAnagrafica) {
		this.descTipoAnagrafica = descTipoAnagrafica;
	}

}