/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebbo.dto.estremiBancari;

/*
 * contiene informazioni relative al bando e all’ente di competenza
 */
public class AnagraficaDTO {
	
	private Long idBando;
	private String titoloBando;
	private Long idEnteCompetenza;
	
	
	public Long getIdBando() {
		return idBando;
	}
	public void setIdBando(Long idBnado) {
		this.idBando = idBnado;
	}
	public String getTitoloBando() {
		return titoloBando;
	}
	public void setTitoloBando(String titoloBando) {
		this.titoloBando = titoloBando;
	}
	public Long getIdEnteCompetenza() {
		return idEnteCompetenza;
	}
	public void setIdEnteCompetenza(Long idEnteCompetenza) {
		this.idEnteCompetenza = idEnteCompetenza;
	}
	
	
	@Override
	public String toString() {
		return "AnagraficaDTO [idBando=" + idBando + ", titoloBando=" + titoloBando + ", idEnteCompetenza="
				+ idEnteCompetenza + "]";
	}
	
	
	
	
	

}
