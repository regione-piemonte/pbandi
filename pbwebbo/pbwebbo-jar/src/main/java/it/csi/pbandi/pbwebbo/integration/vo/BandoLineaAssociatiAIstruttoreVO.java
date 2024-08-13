/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebbo.integration.vo;

import java.io.Serializable;

public class BandoLineaAssociatiAIstruttoreVO implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private String nomeBandolinea;
	private Long progBandoLina;
	private Long numIstruttoriAssociati;
	private String nomneIstruttore;
	private String cognomeIstruttore;
	
	public String getNomeBandolinea() {
		return nomeBandolinea;
	}
	public void setNomeBandolinea(String nomeBandolinea) {
		this.nomeBandolinea = nomeBandolinea;
	}
	public Long getProgBandoLina() {
		return progBandoLina;
	}
	public void setProgBandoLina(Long progBandoLina) {
		this.progBandoLina = progBandoLina;
	}
	public Long getNumIstruttoriAssociati() {
		return numIstruttoriAssociati;
	}
	public void setNumIstruttoriAssociati(Long numIstruttoriAssociati) {
		this.numIstruttoriAssociati = numIstruttoriAssociati;
	}
	public String getNomneIstruttore() {
		return nomneIstruttore;
	}
	public void setNomneIstruttore(String nomneIstruttore) {
		this.nomneIstruttore = nomneIstruttore;
	}
	public String getCognomeIstruttore() {
		return cognomeIstruttore;
	}
	public void setCognomeIstruttore(String cognomeIstruttore) {
		this.cognomeIstruttore = cognomeIstruttore;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("BandoLineaAssociatiAIstruttoreVO [nomeBandolinea=");
		builder.append(nomeBandolinea);
		builder.append(", progBandoLina=");
		builder.append(progBandoLina);
		builder.append(", numIstruttoriAssociati=");
		builder.append(numIstruttoriAssociati);
		builder.append(", nomneIstruttore=");
		builder.append(nomneIstruttore);
		builder.append(", cognomeIstruttore=");
		builder.append(cognomeIstruttore);
		builder.append("]");
		return builder.toString();
	}

}
