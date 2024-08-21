/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.dto;

import java.beans.IntrospectionException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.Set;

import it.csi.pbandi.pbservizit.util.BeanUtil;

public class AvvisoUtenteDTO implements Serializable {
	
	private static final long serialVersionUID = -1;
	
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
