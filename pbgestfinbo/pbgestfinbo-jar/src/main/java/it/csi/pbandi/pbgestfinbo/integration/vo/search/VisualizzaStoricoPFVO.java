/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.integration.vo.search;

public class VisualizzaStoricoPFVO {
	//Dati anagrafici:
	private String cognome;
	private String nome;
	private String tipoSoggetto;
	private String codiceFiscale;
	private String dataDiNascita;
	private String comuneDiNascita;
	private String provinciaDiNascita;
	private String regioneDiNascita;
	private String nazioneDiNascita;
    //RESIDENZA:
	private String indirizzoPF;
	private String comunePF;
	private String provinciaPF;
	private String capPF;
	private String regionePF;
	private String nazionePF;
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
	public String getDataDiNascita() {
		return dataDiNascita;
	}
	public void setDataDiNascita(String dataDiNascita) {
		this.dataDiNascita = dataDiNascita;
	}
	public String getComuneDiNascita() {
		return comuneDiNascita;
	}
	public void setComuneDiNascita(String comuneDiNascita) {
		this.comuneDiNascita = comuneDiNascita;
	}
	public String getProvinciaDiNascita() {
		return provinciaDiNascita;
	}
	public void setProvinciaDiNascita(String provinciaDiNascita) {
		this.provinciaDiNascita = provinciaDiNascita;
	}
	public String getRegioneDiNascita() {
		return regioneDiNascita;
	}
	public void setRegioneDiNascita(String regioneDiNascita) {
		this.regioneDiNascita = regioneDiNascita;
	}
	public String getNazioneDiNascita() {
		return nazioneDiNascita;
	}
	public void setNazioneDiNascita(String nazioneDiNascita) {
		this.nazioneDiNascita = nazioneDiNascita;
	}
	public String getIndirizzoPF() {
		return indirizzoPF;
	}
	public void setIndirizzoPF(String indirizzoPF) {
		this.indirizzoPF = indirizzoPF;
	}
	public String getComunePF() {
		return comunePF;
	}
	public void setComunePF(String comunePF) {
		this.comunePF = comunePF;
	}
	public String getProvinciaPF() {
		return provinciaPF;
	}
	public void setProvinciaPF(String provinciaPF) {
		this.provinciaPF = provinciaPF;
	}
	public String getCapPF() {
		return capPF;
	}
	public void setCapPF(String capPF) {
		this.capPF = capPF;
	}
	public String getRegionePF() {
		return regionePF;
	}
	public void setRegionePF(String regionePF) {
		this.regionePF = regionePF;
	}
	public String getNazionePF() {
		return nazionePF;
	}
	public void setNazionePF(String nazionePF) {
		this.nazionePF = nazionePF;
	}
	@Override
	public String toString() {
		return "VisualizzaStoricoPFVO [cognome=" + cognome + ", nome=" + nome + ", tipoSoggetto=" + tipoSoggetto
				+ ", codiceFiscale=" + codiceFiscale + ", dataDiNascita=" + dataDiNascita + ", comuneDiNascita="
				+ comuneDiNascita + ", provinciaDiNascita=" + provinciaDiNascita + ", regioneDiNascita="
				+ regioneDiNascita + ", nazioneDiNascita=" + nazioneDiNascita + ", indirizzoPF=" + indirizzoPF
				+ ", comunePF=" + comunePF + ", provinciaPF=" + provinciaPF + ", capPF=" + capPF + ", regionePF="
				+ regionePF + ", nazionePF=" + nazionePF + "]";
	}
    
    
	

}
