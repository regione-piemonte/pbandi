/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebrce.dto.affidamenti;

public class TipoOperazione implements java.io.Serializable {
	private Long idTipoOperazione;
	private String descTipoOperazione;
	private String codIgrue;
	private static final long serialVersionUID = 1L;
	
	public void setIdTipoOperazione(Long val) {
		idTipoOperazione = val;
	}

	public Long getIdTipoOperazione() {
		return idTipoOperazione;
	}

	

	public void setDescTipoOperazione(String val) {
		descTipoOperazione = val;
	}

	public String getDescTipoOperazione() {
		return descTipoOperazione;
	}

	

	public void setCodIgrue(String val) {
		codIgrue = val;
	}

	public String getCodIgrue() {
		return codIgrue;
	}



	public TipoOperazione() {
		super();

	}

	public String toString() {
		return super.toString();
	}
}
