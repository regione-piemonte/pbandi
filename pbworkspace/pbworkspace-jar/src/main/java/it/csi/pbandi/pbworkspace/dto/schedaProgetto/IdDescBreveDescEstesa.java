/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbworkspace.dto.schedaProgetto;

public class IdDescBreveDescEstesa implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	
	private java.lang.String id = null;
	private java.lang.String descBreve = null;
	private java.lang.String descEstesa = null;

	public IdDescBreveDescEstesa() {
		super();
	}

	public java.lang.String getId() {
		return id;
	}

	public void setId(java.lang.String id) {
		this.id = id;
	}

	public java.lang.String getDescBreve() {
		return descBreve;
	}

	public void setDescBreve(java.lang.String descBreve) {
		this.descBreve = descBreve;
	}

	public java.lang.String getDescEstesa() {
		return descEstesa;
	}

	public void setDescEstesa(java.lang.String descEstesa) {
		this.descEstesa = descEstesa;
	}
	
}
