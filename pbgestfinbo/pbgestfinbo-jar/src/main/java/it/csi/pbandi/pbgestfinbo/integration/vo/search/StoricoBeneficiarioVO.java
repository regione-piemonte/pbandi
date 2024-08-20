/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.integration.vo.search;

public class StoricoBeneficiarioVO {
	
	//PERSONA FISICA
	private String cognome;
	private String nome;
	private String idSoggetto;
	private String tipoSoggetto;
	private String codiceFiscale;
	private String ndg;
	
	
	public String getNdg() {
		return ndg;
	}
	public void setNdg(String ndg) {
		this.ndg = ndg;
	}
	public String getCognome() {
		return cognome;
	}
	public void setCognome(String cognome) {
		this.cognome = cognome;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getIdSoggetto() {
		return idSoggetto;
	}
	public void setIdSoggetto(String idSoggetto) {
		this.idSoggetto = idSoggetto;
	}
	public String getTipoSoggetto() {
		return tipoSoggetto;
	}
	public void setTipoSoggetto(String tipoSoggetto) {
		this.tipoSoggetto = tipoSoggetto;
	}
	public String getCodiceFiscale() {
		return codiceFiscale;
	}
	public void setCodiceFiscale(String codiceFiscale) {
		this.codiceFiscale = codiceFiscale;
	}

	//PERSONA GIURIDICA
	private String denominazione;
	private String cF;
	private Long idSogg;
	private String pIva;
	private String tipoSogg;
	
	
	public String getDenominazione() {
		return denominazione;
	}
	public void setDenominazione(String denominazione) {
		this.denominazione = denominazione;
	}
	public String getcF() {
		return cF;
	}
	public void setcF(String cF) {
		this.cF = cF;
	}
	public Long getIdSogg() {
		return idSogg;
	}
	public void setIdSogg(Long idSogg) {
		this.idSogg = idSogg;
	}
	public String getpIva() {
		return pIva;
	}
	public void setpIva(String pIva) {
		this.pIva = pIva;
	}
	public String getTipoSogg() {
		return tipoSogg;
	}
	public void setTipoSogg(String tipoSogg) {
		this.tipoSogg = tipoSogg;
	}
	@Override
	public String toString() {
		return "StoricoBeneficiarioVO [cognome=" + cognome + ", nome=" + nome + ", idSoggetto=" + idSoggetto
				+ ", tipoSoggetto=" + tipoSoggetto + ", codiceFiscale=" + codiceFiscale + ", denominazione="
				+ denominazione + ", cF=" + cF + ", idSogg=" + idSogg + ", pIva=" + pIva + ", tipoSogg=" + tipoSogg
				+ "]";
	}
	
	
	
	

}
