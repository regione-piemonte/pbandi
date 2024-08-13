/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebbo.security;

import java.util.ArrayList;
import java.util.List;

public class UserInfo implements java.io.Serializable {

	private static final long serialVersionUID = -7999400417528768575L;

	private String nome;
	private String cognome;
	private String codFisc;
	private String ente;
	private String ruolo;
	private String idIride;
	private String codRuolo;
	private Long idFunzionario;
//	private List<Autorizzazioni> autorizzazioni = new ArrayList<>();

//	public List<Autorizzazioni> getAutorizzazioni() {
//		return autorizzazioni;
//	}
//
//	public void setAutorizzazioni(List<Autorizzazioni> autorizzazioni) {
//		this.autorizzazioni = autorizzazioni;
//	}

	public Long getIdFunzionario() {
		return idFunzionario;
	}

	public void setIdFunzionario(Long idFunzionario) {
		this.idFunzionario = idFunzionario;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCognome() {
		return cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	public String getCodFisc() {
		return codFisc;
	}

	public void setCodFisc(String codFisc) {
		this.codFisc = codFisc;
	}

	public String getEnte() {
		return ente;
	}

	public void setEnte(String ente) {
		this.ente = ente;
	}

	public String getRuolo() {
		return ruolo;
	}

	public void setRuolo(String ruolo) {
		this.ruolo = ruolo;
	}

	public String getIdIride() {
		return idIride;
	}

	public void setIdIride(String idIride) {
		this.idIride = idIride;
	}

	public String getCodRuolo() {
		return codRuolo;
	}

	public void setCodRuolo(String codRuolo) {
		this.codRuolo = codRuolo;
	}

	@Override
	public String toString() {
		return "UserInfo [nome=" + nome + ", cognome=" + cognome + ", codFisc=" + codFisc + ", ente=" + ente
				+ ", ruolo=" + ruolo + ", idIride=" + idIride + ", codRuolo=" + codRuolo + ", idFunzionario="
				+ idFunzionario + "]"; // ", autorizzazioni=" + autorizzazioni + "]";
	}

}