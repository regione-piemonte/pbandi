/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebbo.integration.vo;

import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;

import it.csi.pbandi.pbwebbo.dto.CodiceDescrizioneDTO;

public class PbandiTNewsVO implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private Long idNews;
	private String titolo;
	private String descrizione;
	private String tipoNews;
	private String urlPagina;
	private Date dtInizio;
	private Date dtFine;
	private Long utenteInse;
	private ArrayList<CodiceDescrizioneDTO> tipiAnagrafica;
	
	public ArrayList<CodiceDescrizioneDTO> getTipiAnagrafica() {
		return tipiAnagrafica;
	}
	public void setTipiAnagrafica(ArrayList<CodiceDescrizioneDTO> tipiAnagrafica) {
		this.tipiAnagrafica = tipiAnagrafica;
	}
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
	public String getUrlPagina() {
		return urlPagina;
	}
	public void setUrlPagina(String urlPagina) {
		this.urlPagina = urlPagina;
	}
	public Date getDtInizio() {
		return dtInizio;
	}
	public void setDtInizio(Date dtInizio) {
		this.dtInizio = dtInizio;
	}
	public Date getDtFine() {
		return dtFine;
	}
	public void setDtFine(Date dtFine) {
		this.dtFine = dtFine;
	}
	public Long getUtenteInse() {
		return utenteInse;
	}
	public void setUtenteInse(Long utenteInse) {
		this.utenteInse = utenteInse;
	}
	
}
