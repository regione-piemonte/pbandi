/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweb.dto;

import java.util.ArrayList;

public class AttivitaAtecoNodoDTO implements java.io.Serializable {
	
	static final long serialVersionUID = 1;

	private java.lang.Long idAttivitaAteco = null;
	private java.lang.String codAteco = null;
	private java.lang.String codDescAteco = null;
	private ArrayList<AttivitaAtecoNodoDTO> figli = null;
	
	public java.lang.Long getIdAttivitaAteco() {
		return idAttivitaAteco;
	}
	public void setIdAttivitaAteco(java.lang.Long idAttivitaAteco) {
		this.idAttivitaAteco = idAttivitaAteco;
	}
	public java.lang.String getCodAteco() {
		return codAteco;
	}
	public void setCodAteco(java.lang.String codAteco) {
		this.codAteco = codAteco;
	}
	public java.lang.String getCodDescAteco() {
		return codDescAteco;
	}
	public void setCodDescAteco(java.lang.String codDescAteco) {
		this.codDescAteco = codDescAteco;
	}
	public ArrayList<AttivitaAtecoNodoDTO> getFigli() {
		return figli;
	}
	public void setFigli(ArrayList<AttivitaAtecoNodoDTO> figli) {
		this.figli = figli;
	}
	
}
