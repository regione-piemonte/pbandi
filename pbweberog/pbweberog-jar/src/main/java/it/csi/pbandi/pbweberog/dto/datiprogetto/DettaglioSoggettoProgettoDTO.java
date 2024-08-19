/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweberog.dto.datiprogetto;

public class DettaglioSoggettoProgettoDTO implements java.io.Serializable {

	static final long serialVersionUID = 1;
	private String progrSoggettoProgetto;
	private String idTipoSoggettoCorrelato;
	private String nome;
	private String cognome;
	private String codiceFiscale;
	private String sesso;
	private String dataNascita;
	private String idNazione;
	private String idProvincia;
	private String idComune;
	private String indirizzoRes;
	private String numCivicoRes;
	private String idNazioneRes;
	private String idRegioneRes;
	private String idProvinciaRes;
	private String idComuneRes;
	private String dataFineValidita;
	private String idPersonaFisica;
	private String idIndirizzo;
	private String idSoggetto;
	private String idProgetto;
	private String progrSoggettiCorrelati;
	private String idTipoSoggettoCorrelatoAttuale;
	private String capRes;
	private boolean isAccessoSistema;
	private String codiceRuolo;
	
	@Override
	public String toString() {
		return "DettaglioSoggettoProgettoDTO [progrSoggettoProgetto=" + progrSoggettoProgetto
				+ ", idTipoSoggettoCorrelato=" + idTipoSoggettoCorrelato + ", nome=" + nome + ", cognome=" + cognome
				+ ", codiceFiscale=" + codiceFiscale + ", sesso=" + sesso + ", dataNascita=" + dataNascita
				+ ", idNazione=" + idNazione + ", idProvincia=" + idProvincia + ", idComune=" + idComune
				+ ", indirizzoRes=" + indirizzoRes + ", numCivicoRes=" + numCivicoRes + ", idNazioneRes=" + idNazioneRes
				+ ", idRegioneRes=" + idRegioneRes + ", idProvinciaRes=" + idProvinciaRes + ", idComuneRes="
				+ idComuneRes + ", dataFineValidita=" + dataFineValidita + ", idPersonaFisica=" + idPersonaFisica
				+ ", idIndirizzo=" + idIndirizzo + ", idSoggetto=" + idSoggetto + ", idProgetto=" + idProgetto
				+ ", progrSoggettiCorrelati=" + progrSoggettiCorrelati + ", idTipoSoggettoCorrelatoAttuale="
				+ idTipoSoggettoCorrelatoAttuale + "]";
	}
	public String getProgrSoggettoProgetto() {
		return progrSoggettoProgetto;
	}
	public void setProgrSoggettoProgetto(String progrSoggettoProgetto) {
		this.progrSoggettoProgetto = progrSoggettoProgetto;
	}
	public String getIdTipoSoggettoCorrelato() {
		return idTipoSoggettoCorrelato;
	}
	public void setIdTipoSoggettoCorrelato(String idTipoSoggettoCorrelato) {
		this.idTipoSoggettoCorrelato = idTipoSoggettoCorrelato;
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
	public String getCodiceFiscale() {
		return codiceFiscale;
	}
	public void setCodiceFiscale(String codiceFiscale) {
		this.codiceFiscale = codiceFiscale;
	}
	public String getSesso() {
		return sesso;
	}
	public void setSesso(String sesso) {
		this.sesso = sesso;
	}
	public String getDataNascita() {
		return dataNascita;
	}
	public void setDataNascita(String dataNascita) {
		this.dataNascita = dataNascita;
	}
	public String getIdNazione() {
		return idNazione;
	}
	public void setIdNazione(String idNazione) {
		this.idNazione = idNazione;
	}
	public String getIdProvincia() {
		return idProvincia;
	}
	public void setIdProvincia(String idProvincia) {
		this.idProvincia = idProvincia;
	}
	public String getIdComune() {
		return idComune;
	}
	public void setIdComune(String idComune) {
		this.idComune = idComune;
	}
	public String getIndirizzoRes() {
		return indirizzoRes;
	}
	public void setIndirizzoRes(String indirizzoRes) {
		this.indirizzoRes = indirizzoRes;
	}
	public String getNumCivicoRes() {
		return numCivicoRes;
	}
	public void setNumCivicoRes(String numCivicoRes) {
		this.numCivicoRes = numCivicoRes;
	}
	public String getIdNazioneRes() {
		return idNazioneRes;
	}
	public void setIdNazioneRes(String idNazioneRes) {
		this.idNazioneRes = idNazioneRes;
	}
	public String getIdRegioneRes() {
		return idRegioneRes;
	}
	public void setIdRegioneRes(String idRegioneRes) {
		this.idRegioneRes = idRegioneRes;
	}
	public String getIdProvinciaRes() {
		return idProvinciaRes;
	}
	public void setIdProvinciaRes(String idProvinciaRes) {
		this.idProvinciaRes = idProvinciaRes;
	}
	public String getIdComuneRes() {
		return idComuneRes;
	}
	public void setIdComuneRes(String idComuneRes) {
		this.idComuneRes = idComuneRes;
	}
	public String getDataFineValidita() {
		return dataFineValidita;
	}
	public void setDataFineValidita(String dataFineValidita) {
		this.dataFineValidita = dataFineValidita;
	}
	public String getIdPersonaFisica() {
		return idPersonaFisica;
	}
	public void setIdPersonaFisica(String idPersonaFisica) {
		this.idPersonaFisica = idPersonaFisica;
	}
	public String getIdIndirizzo() {
		return idIndirizzo;
	}
	public void setIdIndirizzo(String idIndirizzo) {
		this.idIndirizzo = idIndirizzo;
	}
	public String getIdSoggetto() {
		return idSoggetto;
	}
	public void setIdSoggetto(String idSoggetto) {
		this.idSoggetto = idSoggetto;
	}
	public String getIdProgetto() {
		return idProgetto;
	}
	public void setIdProgetto(String idProgetto) {
		this.idProgetto = idProgetto;
	}
	public String getProgrSoggettiCorrelati() {
		return progrSoggettiCorrelati;
	}
	public void setProgrSoggettiCorrelati(String progrSoggettiCorrelati) {
		this.progrSoggettiCorrelati = progrSoggettiCorrelati;
	}
	public String getIdTipoSoggettoCorrelatoAttuale() {
		return idTipoSoggettoCorrelatoAttuale;
	}
	public void setIdTipoSoggettoCorrelatoAttuale(String idTipoSoggettoCorrelatoAttuale) {
		this.idTipoSoggettoCorrelatoAttuale = idTipoSoggettoCorrelatoAttuale;
	}
	public String getCapRes() {
		return capRes;
	}
	public void setCapRes(String capRes) {
		this.capRes = capRes;
	}
	public boolean isAccessoSistema() {
		return isAccessoSistema;
	}
	public void setAccessoSistema(boolean isAccessoSistema) {
		this.isAccessoSistema = isAccessoSistema;
	}
	public String getCodiceRuolo() {
		return codiceRuolo;
	}
	public void setCodiceRuolo(String codiceRuolo) {
		this.codiceRuolo = codiceRuolo;
	}


}
