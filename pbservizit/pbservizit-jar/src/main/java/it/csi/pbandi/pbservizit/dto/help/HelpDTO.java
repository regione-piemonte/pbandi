/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.dto.help;

public class HelpDTO implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long idHelp;
	private String testoHelp;
	public Long getIdHelp() {
		return idHelp;
	}
	public void setIdHelp(Long idHelp) {
		this.idHelp = idHelp;
	}
	public String getTestoHelp() {
		return testoHelp;
	}
	public void setTestoHelp(String testoHelp) {
		this.testoHelp = testoHelp;
	}
	
}
