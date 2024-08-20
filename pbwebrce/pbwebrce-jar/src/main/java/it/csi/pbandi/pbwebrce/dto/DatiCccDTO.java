/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebrce.dto;

import java.util.Date;

public class DatiCccDTO implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String descEnte;
	private String descSettore;
	private String titoloBando;
	private String nomeBandoLinea;
	private String numeroDomanda;
	private String cup;
	private String descComune;
	private String descProvincia;
	private Date dtFineEffettiva;
	private String estremiAttoAmministrativo;

	public String getDescEnte() {
		return descEnte;
	}

	public void setDescEnte(String descEnte) {
		this.descEnte = descEnte;
	}

	public String getDescSettore() {
		return descSettore;
	}

	public void setDescSettore(String descSettore) {
		this.descSettore = descSettore;
	}

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

	public String getNumeroDomanda() {
		return numeroDomanda;
	}

	public void setNumeroDomanda(String numeroDomanda) {
		this.numeroDomanda = numeroDomanda;
	}

	public String getCup() {
		return cup;
	}

	public void setCup(String cup) {
		this.cup = cup;
	}

	public String getDescComune() {
		return descComune;
	}

	public void setDescComune(String descComune) {
		this.descComune = descComune;
	}

	public String getDescProvincia() {
		return descProvincia;
	}

	public void setDescProvincia(String descProvincia) {
		this.descProvincia = descProvincia;
	}

	public Date getDtFineEffettiva() {
		return dtFineEffettiva;
	}

	public void setDtFineEffettiva(Date dtFineEffettiva) {
		this.dtFineEffettiva = dtFineEffettiva;
	}

	public String getEstremiAttoAmministrativo() {
		return estremiAttoAmministrativo;
	}

	public void setEstremiAttoAmministrativo(String estremiAttoAmministrativo) {
		this.estremiAttoAmministrativo = estremiAttoAmministrativo;
	}

}
