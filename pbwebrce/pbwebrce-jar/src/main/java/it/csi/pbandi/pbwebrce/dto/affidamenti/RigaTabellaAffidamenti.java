/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebrce.dto.affidamenti;

public class RigaTabellaAffidamenti extends Affidamento  implements java.io.Serializable {
	static final long serialVersionUID = 1L;
	private String iconaPdf;
	private String iconaDettaglio;
	private String iconaElimina;
	private String iconaInviaInVerifica;
	
	
	public void setIconaPdf(String val) {
		iconaPdf = val;
	}

	public String getIconaPdf() {
		return iconaPdf;
	}

	public void setIconaDettaglio(String val) {
		iconaDettaglio = val;
	}

	public String getIconaDettaglio() {
		return iconaDettaglio;
	}

	public void setIconaElimina(String val) {
		iconaElimina = val;
	}

	public String getIconaElimina() {
		return iconaElimina;
	}
	

	public void setIconaInviaInVerifica(String val) {
		iconaInviaInVerifica = val;
	}

	public String getIconaInviaInVerifica() {
		return iconaInviaInVerifica;
	}

	public RigaTabellaAffidamenti() {
		super();

	}

	public String toString() {	
		return super.toString();
	}

}
