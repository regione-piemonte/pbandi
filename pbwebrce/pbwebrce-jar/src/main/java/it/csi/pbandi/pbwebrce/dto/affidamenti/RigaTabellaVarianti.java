/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebrce.dto.affidamenti;

public class RigaTabellaVarianti extends VarianteAffidamento implements java.io.Serializable{

	private java.lang.String _iconaDettaglio = null;

	public void setIconaDettaglio(java.lang.String val) {
		_iconaDettaglio = val;
	}

	public java.lang.String getIconaDettaglio() {
		return _iconaDettaglio;
	}

	private java.lang.String _iconaElimina = null;

	public void setIconaElimina(java.lang.String val) {
		_iconaElimina = val;
	}

	public java.lang.String getIconaElimina() {
		return _iconaElimina;
	}

	private static final long serialVersionUID = 1L;


	public RigaTabellaVarianti() {
		super();

	}

	public String toString() {
		return super.toString();
	}
	
}
