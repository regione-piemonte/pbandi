/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebrce.dto;

public class QteHtmlDTO implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Long idQtesHtmlProgetto;
	private String htmlQtesProgetto;

	public Long getIdQtesHtmlProgetto() {
		return idQtesHtmlProgetto;
	}

	public void setIdQtesHtmlProgetto(Long idQtesHtmlProgetto) {
		this.idQtesHtmlProgetto = idQtesHtmlProgetto;
	}

	public String getHtmlQtesProgetto() {
		return htmlQtesProgetto;
	}

	public void setHtmlQtesProgetto(String htmlQtesProgetto) {
		this.htmlQtesProgetto = htmlQtesProgetto;
	}

}
