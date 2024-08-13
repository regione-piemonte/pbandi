/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweb.pbandisrv.dto.manager.report;

public class ChecklistHtmlIrregolaritaDTO implements java.io.Serializable {
	static final long serialVersionUID = 1;
	 

	private String importoIrregolarita;
	private String descIrregolarita;
	private String flagIrregolarita;
	
	public String getImportoIrregolarita() {
		return importoIrregolarita;
	}
	public String getDescIrregolarita() {
		return descIrregolarita;
	}
	public void setImportoIrregolarita(String importoIrregolarita) {
		this.importoIrregolarita = importoIrregolarita;
	}
	public void setDescIrregolarita(String descIrregolarita) {
		this.descIrregolarita = descIrregolarita;
	}
	public String getFlagIrregolarita() {
		return flagIrregolarita;
	}
	public void setFlagIrregolarita(String flagIrregolarita) {
		this.flagIrregolarita = flagIrregolarita;
	}
    
    
}
