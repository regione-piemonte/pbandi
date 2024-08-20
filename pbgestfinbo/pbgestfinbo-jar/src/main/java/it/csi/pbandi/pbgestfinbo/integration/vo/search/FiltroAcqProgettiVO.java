/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.integration.vo.search;

import java.util.Date;
import java.util.List;

public class FiltroAcqProgettiVO {

	
	private Long idBandoLineaIntervent;
	private Long numCampionamento; 
	private Date dataCampione; 
	private String descCampionamento; 
	private String progetti;
	
	private List<Long> progettiConfermati; 
	public List<Long> getProgettiConfermati() {
		return progettiConfermati;
	}
	public void setProgettiConfermati(List<Long> progettiConfermati) {
		this.progettiConfermati = progettiConfermati;
	}
	public Long getIdBandoLineaIntervent() {
		return idBandoLineaIntervent;
	}
	public void setIdBandoLineaIntervent(Long idBandoLineaIntervent) {
		this.idBandoLineaIntervent = idBandoLineaIntervent;
	}
	public Long getNumCampionamento() {
		return numCampionamento;
	}
	public void setNumCampionamento(Long numCampionamento) {
		this.numCampionamento = numCampionamento;
	}
	public Date getDataCampione() {
		return dataCampione;
	}
	public void setDataCampione(Date dataCampione) {
		this.dataCampione = dataCampione;
	}
	public String getDescCampionamento() {
		return descCampionamento;
	}
	public void setDescCampionamento(String descCampionamento) {
		this.descCampionamento = descCampionamento;
	}
	public String getProgetti() {
		return progetti;
	}
	public void setProgetti(String progetti) {
		this.progetti = progetti;
	}
	@Override
	public String toString() {
		return "FiltroAcqProgettiVO [idBandoLineaIntervent=" + idBandoLineaIntervent + ", numCampionamento="
				+ numCampionamento + ", dataCampione=" + dataCampione + ", descCampionamento=" + descCampionamento
				+ ", progetti=" + progetti + "]";
	} 
	

	
	

}
