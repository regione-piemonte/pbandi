/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebbo.integration.vo;

import java.io.Serializable;

public class BandoLineaDaAssociareAIstruttoreVO implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private String nomeBandolinea;
	private Long progBandoLinaIntervento;
	private Long idEnteDiCompetenza;
	private String descBreveEnte;
	
	public String getNomeBandolinea() {
		return nomeBandolinea;
	}
	public void setNomeBandolinea(String nomeBandolinea) {
		this.nomeBandolinea = nomeBandolinea;
	}
	public Long getProgBandoLinaIntervento() {
		return progBandoLinaIntervento;
	}
	public void setProgBandoLinaIntervento(Long progBandoLinaIntervento) {
		this.progBandoLinaIntervento = progBandoLinaIntervento;
	}
	public Long getIdEnteDiCompetenza() {
		return idEnteDiCompetenza;
	}
	public void setIdEnteDiCompetenza(Long idEnteDiCompetenza) {
		this.idEnteDiCompetenza = idEnteDiCompetenza;
	}
	public String getDescBreveEnte() {
		return descBreveEnte;
	}
	public void setDescBreveEnte(String descBreveEnte) {
		this.descBreveEnte = descBreveEnte;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("BandoLineaDaAssociareAIstruttoreVO [nomeBandolinea=");
		builder.append(nomeBandolinea);
		builder.append(", progBandoLinaIntervento=");
		builder.append(progBandoLinaIntervento);
		builder.append(", idDnteDiCompetenza=");
		builder.append(", descBreveEnte=");
		builder.append(descBreveEnte);
		builder.append("]");
		return builder.toString();
	}

}
