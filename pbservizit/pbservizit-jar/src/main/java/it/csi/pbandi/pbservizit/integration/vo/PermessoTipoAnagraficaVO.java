/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.integration.vo;

public class PermessoTipoAnagraficaVO {
	
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

	@Override
	public String toString() {
		return "PermessoTipoAnagraficaVO [descPermesso=" + descPermesso + ", descBrevePermesso=" + descBrevePermesso
				+ ", descBreveTipoAnagrafica=" + descBreveTipoAnagrafica + ", descTipoAnagrafica=" + descTipoAnagrafica
				+ "]";
	}

}
