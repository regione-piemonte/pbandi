/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebbo.dto.gestionenews;

import java.beans.IntrospectionException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.Set;

import it.csi.pbandi.pbwebbo.dto.CodiceDescrizioneDTO;
import it.csi.pbandi.pbwebbo.util.BeanUtil;

public class AvvisoDTO implements Serializable {
	
	private static final long serialVersionUID = -1;
	
	private Long idNews;
	private String titolo;
	private String descrizione;
	private String tipoNews;
	private String urlPagina;
	private Date dtInizio;
	private Date dtFine;
	private Long utenteIns;
	private ArrayList<CodiceDescrizioneDTO> tipiAnagrafica;
	
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

	public Long getUtenteIns() {
		return utenteIns;
	}

	public void setUtenteIns(Long utenteIns) {
		this.utenteIns = utenteIns;
	}

	public ArrayList<CodiceDescrizioneDTO> getTipiAnagrafica() {
		return tipiAnagrafica;
	}

	public void setTipiAnagrafica(ArrayList<CodiceDescrizioneDTO> tipiAnagrafica) {
		this.tipiAnagrafica = tipiAnagrafica;
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("\n"+this.getClass().getName()+": ");
		try {
			Set<String> properties = BeanUtil.getProperties(this.getClass());
			for (String propName : properties) {
				if ("class".equalsIgnoreCase(propName))
					continue;
				if ("tipiAnagrafica".equalsIgnoreCase(propName)) {
					ArrayList<CodiceDescrizioneDTO> lista = (ArrayList<CodiceDescrizioneDTO>) BeanUtil.getPropertyValueByName(this, propName);
					if (lista != null) {
						sb.append("\ntipiAnagrafica:");
						for (CodiceDescrizioneDTO item : lista) {
							sb.append(item.toString());
						}
					} else {
						sb.append("\ntipiAnagrafica = null");
					}
				} else {
					sb.append("\n"+propName+" = "+BeanUtil.getPropertyValueByName(this, propName));	
				}				
			}
		} catch (IntrospectionException e) {
		}
		return sb.toString();
	}
	
}
