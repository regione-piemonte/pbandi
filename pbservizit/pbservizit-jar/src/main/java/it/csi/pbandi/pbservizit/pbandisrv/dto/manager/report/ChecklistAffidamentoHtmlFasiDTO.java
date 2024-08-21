/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.pbandisrv.dto.manager.report;

import java.io.Serializable;


public class ChecklistAffidamentoHtmlFasiDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2342285072920763009L;
	
	
	private Boolean fase1;
	
	private Boolean fase2;
	
	private String noteFase1;
	
	private String noteFase2;

	public Boolean getFase1() {
		return fase1;
	}

	public void setFase1(Boolean fase1) {
		this.fase1 = fase1;
	}

	public Boolean getFase2() {
		return fase2;
	}

	public void setFase2(Boolean fase2) {
		this.fase2 = fase2;
	}

	public String getNoteFase1() {
		return noteFase1;
	}

	public void setNoteFase1(String noteFase1) {
		this.noteFase1 = noteFase1;
	}

	public String getNoteFase2() {
		return noteFase2;
	}

	public void setNoteFase2(String noteFase2) {
		this.noteFase2 = noteFase2;
	}
	
	// Jira PBANDI-2829.
	
	private String flagRettifica1;
	
	private String flagRettifica2;

	public String getFlagRettifica1() {
		return flagRettifica1;
	}

	public void setFlagRettifica1(String flagRettifica1) {
		this.flagRettifica1 = flagRettifica1;
	}

	public String getFlagRettifica2() {
		return flagRettifica2;
	}

	public void setFlagRettifica2(String flagRettifica2) {
		this.flagRettifica2 = flagRettifica2;
	}
	
}