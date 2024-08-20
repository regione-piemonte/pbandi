/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebrce.dto;

import java.util.List;

public class QteProgettoDTO implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long idQtesHtmlProgetto;
	private String htmlQtesProgetto;
	private Long idProgetto;
	private List<DatiQteDTO> datiQte;

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

	public Long getIdProgetto() {
		return idProgetto;
	}

	public void setIdProgetto(Long idProgetto) {
		this.idProgetto = idProgetto;
	}

	public List<DatiQteDTO> getDatiQte() {
		return datiQte;
	}

	public void setDatiQte(List<DatiQteDTO> datiQte) {
		this.datiQte = datiQte;
	}

}
