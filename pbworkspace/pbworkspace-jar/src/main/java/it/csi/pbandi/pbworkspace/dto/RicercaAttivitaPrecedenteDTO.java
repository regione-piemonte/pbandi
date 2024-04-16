/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbworkspace.dto;

import java.util.List;

import it.csi.pbandi.pbworkspace.integration.vo.AttivitaVO;

public class RicercaAttivitaPrecedenteDTO implements java.io.Serializable {

	static final long serialVersionUID = 1;

	private Boolean esisteRicercaPrecedente;
	private List<AttivitaVO> listaAttivita;
	private Long progrBandoLineaIntervento;
	private Long idProgetto;
	private String descAttivita;
	
	public Boolean getEsisteRicercaPrecedente() {
		return esisteRicercaPrecedente;
	}
	public void setEsisteRicercaPrecedente(Boolean esisteRicercaPrecedente) {
		this.esisteRicercaPrecedente = esisteRicercaPrecedente;
	}
	public List<AttivitaVO> getListaAttivita() {
		return listaAttivita;
	}
	public void setListaAttivita(List<AttivitaVO> listaAttivita) {
		this.listaAttivita = listaAttivita;
	}
	public Long getProgrBandoLineaIntervento() {
		return progrBandoLineaIntervento;
	}
	public void setProgrBandoLineaIntervento(Long progrBandoLineaIntervento) {
		this.progrBandoLineaIntervento = progrBandoLineaIntervento;
	}
	public Long getIdProgetto() {
		return idProgetto;
	}
	public void setIdProgetto(Long idProgetto) {
		this.idProgetto = idProgetto;
	}
	public String getDescAttivita() {
		return descAttivita;
	}
	public void setDescAttivita(String descAttivita) {
		this.descAttivita = descAttivita;
	}
	
}
