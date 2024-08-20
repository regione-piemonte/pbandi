/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebrce.dto;

public class QteFaseDTO implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long idColonnaQtes;
	private String descBreveColonnaQtes;
	private String descColonnaQtes;

	public Long getIdColonnaQtes() {
		return idColonnaQtes;
	}

	public void setIdColonnaQtes(Long idColonnaQtes) {
		this.idColonnaQtes = idColonnaQtes;
	}

	public String getDescBreveColonnaQtes() {
		return descBreveColonnaQtes;
	}

	public void setDescBreveColonnaQtes(String descBreveColonnaQtes) {
		this.descBreveColonnaQtes = descBreveColonnaQtes;
	}

	public String getDescColonnaQtes() {
		return descColonnaQtes;
	}

	public void setDescColonnaQtes(String descColonnaQtes) {
		this.descColonnaQtes = descColonnaQtes;
	}

}
