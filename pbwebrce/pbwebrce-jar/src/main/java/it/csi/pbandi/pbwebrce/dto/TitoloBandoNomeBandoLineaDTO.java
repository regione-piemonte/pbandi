/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebrce.dto;

public class TitoloBandoNomeBandoLineaDTO implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String titoloBando;
	private String nomeBandoLinea;

	public String getTitoloBando() {
		return titoloBando;
	}

	public void setTitoloBando(String titoloBando) {
		this.titoloBando = titoloBando;
	}

	public String getNomeBandoLinea() {
		return nomeBandoLinea;
	}

	public void setNomeBandoLinea(String nomeBandoLinea) {
		this.nomeBandoLinea = nomeBandoLinea;
	}

}
