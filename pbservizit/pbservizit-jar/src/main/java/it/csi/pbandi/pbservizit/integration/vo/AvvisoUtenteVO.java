/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.integration.vo;

import java.io.Serializable;
import java.sql.Date;

public class AvvisoUtenteVO implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private Long idNews;
	private String titolo;
	private String descrizione;
	private String tipoNews;
	private Date dtInizio;
	public Long getIdNews() {
		return idNews;
	}
	public void setIdNews(Long idNews) {
		this.idNews = idNews;
	}
	public String getTitolo() {
		return titolo;
	}
	public void setTitolo(String titolo) {
		this.titolo = titolo;
	}
	public String getDescrizione() {
		return descrizione;
	}
	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}
	public String getTipoNews() {
		return tipoNews;
	}
	public void setTipoNews(String tipoNews) {
		this.tipoNews = tipoNews;
	}
	public Date getDtInizio() {
		return dtInizio;
	}
	public void setDtInizio(Date dtInizio) {
		this.dtInizio = dtInizio;
	}

}
