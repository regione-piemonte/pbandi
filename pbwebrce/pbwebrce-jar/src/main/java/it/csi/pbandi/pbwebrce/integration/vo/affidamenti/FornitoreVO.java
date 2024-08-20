/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebrce.integration.vo.affidamenti;

public class FornitoreVO {
	static final long serialVersionUID = 1;
	private String codiceFiscaleFornitore;	
	private String cognomeFornitore;	
	private String denominazioneFornitore;
	private Long idFornitore;
	private Long idTipoSoggetto;
	private String nomeFornitore;
	private String partitaIvaFornitore;
	private Long idSoggettoFornitore;
	private String descTipoSoggetto;	
	private Long idQualifica;
	private String descQualifica;	
	private java.lang.Double costoOrario;
	private java.util.Date dtFineValidita= null;
	private Long idFormaGiuridica= null;
	private Long idAttivitaAteco= null;
	private Long idNazione= null;
	private String altroCodice;
	private String codUniIpa;
	private Long flagPubblicoPrivato;
	public String getCodiceFiscaleFornitore() {
		return codiceFiscaleFornitore;
	}
	public void setCodiceFiscaleFornitore(String codiceFiscaleFornitore) {
		this.codiceFiscaleFornitore = codiceFiscaleFornitore;
	}
	public String getCognomeFornitore() {
		return cognomeFornitore;
	}
	public void setCognomeFornitore(String cognomeFornitore) {
		this.cognomeFornitore = cognomeFornitore;
	}
	public String getDenominazioneFornitore() {
		return denominazioneFornitore;
	}
	public void setDenominazioneFornitore(String denominazioneFornitore) {
		this.denominazioneFornitore = denominazioneFornitore;
	}
	public Long getIdFornitore() {
		return idFornitore;
	}
	public void setIdFornitore(Long idFornitore) {
		this.idFornitore = idFornitore;
	}
	public Long getIdTipoSoggetto() {
		return idTipoSoggetto;
	}
	public void setIdTipoSoggetto(Long idTipoSoggetto) {
		this.idTipoSoggetto = idTipoSoggetto;
	}
	public String getNomeFornitore() {
		return nomeFornitore;
	}
	public void setNomeFornitore(String nomeFornitore) {
		this.nomeFornitore = nomeFornitore;
	}
	public String getPartitaIvaFornitore() {
		return partitaIvaFornitore;
	}
	public void setPartitaIvaFornitore(String partitaIvaFornitore) {
		this.partitaIvaFornitore = partitaIvaFornitore;
	}
	public Long getIdSoggettoFornitore() {
		return idSoggettoFornitore;
	}
	public void setIdSoggettoFornitore(Long idSoggettoFornitore) {
		this.idSoggettoFornitore = idSoggettoFornitore;
	}
	public String getDescTipoSoggetto() {
		return descTipoSoggetto;
	}
	public void setDescTipoSoggetto(String descTipoSoggetto) {
		this.descTipoSoggetto = descTipoSoggetto;
	}
	public Long getIdQualifica() {
		return idQualifica;
	}
	public void setIdQualifica(Long idQualifica) {
		this.idQualifica = idQualifica;
	}
	public String getDescQualifica() {
		return descQualifica;
	}
	public void setDescQualifica(String descQualifica) {
		this.descQualifica = descQualifica;
	}
	public java.lang.Double getCostoOrario() {
		return costoOrario;
	}
	public void setCostoOrario(java.lang.Double costoOrario) {
		this.costoOrario = costoOrario;
	}
	public java.util.Date getDtFineValidita() {
		return dtFineValidita;
	}
	public void setDtFineValidita(java.util.Date dtFineValidita) {
		this.dtFineValidita = dtFineValidita;
	}
	public Long getIdFormaGiuridica() {
		return idFormaGiuridica;
	}
	public void setIdFormaGiuridica(Long idFormaGiuridica) {
		this.idFormaGiuridica = idFormaGiuridica;
	}
	public Long getIdAttivitaAteco() {
		return idAttivitaAteco;
	}
	public void setIdAttivitaAteco(Long idAttivitaAteco) {
		this.idAttivitaAteco = idAttivitaAteco;
	}
	public Long getIdNazione() {
		return idNazione;
	}
	public void setIdNazione(Long idNazione) {
		this.idNazione = idNazione;
	}
	public String getAltroCodice() {
		return altroCodice;
	}
	public void setAltroCodice(String altroCodice) {
		this.altroCodice = altroCodice;
	}
	public String getCodUniIpa() {
		return codUniIpa;
	}
	public void setCodUniIpa(String codUniIpa) {
		this.codUniIpa = codUniIpa;
	}
	public Long getFlagPubblicoPrivato() {
		return flagPubblicoPrivato;
	}
	public void setFlagPubblicoPrivato(Long flagPubblicoPrivato) {
		this.flagPubblicoPrivato = flagPubblicoPrivato;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
	
	
}
