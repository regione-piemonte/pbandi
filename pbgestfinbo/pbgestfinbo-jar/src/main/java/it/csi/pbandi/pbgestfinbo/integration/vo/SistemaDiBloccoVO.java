/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.integration.vo;


public class SistemaDiBloccoVO {

	private Integer idDomanda;
	private String numeroDomanda;
	private Integer progrBandoLinea;
	private Integer idCampoRegola;
	private Integer idRegola;
	private Integer idCausaleBlocco;
	
		

	public SistemaDiBloccoVO(Integer idDomanda, String numeroDomanda, Integer progrBandoLinea, Integer idCampoRegola,
			Integer idRegola, Integer idCausaleBlocco) {
		this.idDomanda = idDomanda;
		this.numeroDomanda = numeroDomanda;
		this.progrBandoLinea = progrBandoLinea;
		this.idCampoRegola = idCampoRegola;
		this.idRegola = idRegola;
		this.idCausaleBlocco = idCausaleBlocco;
	}


	public SistemaDiBloccoVO() {
	}


	public Integer getIdDomanda() {
		return idDomanda;
	}


	public void setIdDomanda(Integer idDomanda) {
		this.idDomanda = idDomanda;
	}


	public String getNumeroDomanda() {
		return numeroDomanda;
	}


	public void setNumeroDomanda(String numeroDomanda) {
		this.numeroDomanda = numeroDomanda;
	}


	public Integer getProgrBandoLinea() {
		return progrBandoLinea;
	}


	public void setProgrBandoLinea(Integer progrBandoLinea) {
		this.progrBandoLinea = progrBandoLinea;
	}


	public Integer getIdCampoRegola() {
		return idCampoRegola;
	}


	public void setIdCampoRegola(Integer idCampoRegola) {
		this.idCampoRegola = idCampoRegola;
	}


	public Integer getIdRegola() {
		return idRegola;
	}


	public void setIdRegola(Integer idRegola) {
		this.idRegola = idRegola;
	}


	public Integer getIdCausaleBlocco() {
		return idCausaleBlocco;
	}


	public void setIdCausaleBlocco(Integer idCausaleBlocco) {
		this.idCausaleBlocco = idCausaleBlocco;
	}


	@Override
	public String toString() {
		return "SistemaDiBloccoVO [idDomanda=" + idDomanda + ", numeroDomanda=" + numeroDomanda + ", progrBandoLinea="
				+ progrBandoLinea + ", idCampoRegola=" + idCampoRegola + ", idRegola=" + idRegola + ", idCausaleBlocco="
				+ idCausaleBlocco + "]";
	}


}
