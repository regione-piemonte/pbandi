/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebrce.dto.affidamenti;

import java.util.Date;

public class FornitoreDTO implements java.io.Serializable {
	
	static final long serialVersionUID = 1;
	private String codiceFiscaleFornitore;
	private String[] codQualificheNotIn;
	private String[] codQualificheIn;
	private String cognomeFornitore;
	private Double costoOrario;
	private Double costoRisorsa;
	private String denominazioneFornitore;
	private String descQualifica;
	private String descBreveTipoSoggetto;
	private String descTipoSoggetto;
	private Date dtFineValidita;
	private Boolean flagHasQualifica;
	private Long idAttivitaAteco;
	private Long idFormaGiuridica;
	private Long idFornitore;
	private Long idNazione;
	private Long idQualifica;
	private Long idSoggettoFornitore;
	private Long idTipoSoggetto;
	private Boolean includiFornitoriInvalidi;
	private Double monteOre;
	private String nomeFornitore;
	private String partitaIvaFornitore;
	private String altroCodice;
	private String codUniIpa;
	private Long flagPubblicoPrivato;
	public String getCodiceFiscaleFornitore() {
		return codiceFiscaleFornitore;
	}
	public void setCodiceFiscaleFornitore(String codiceFiscaleFornitore) {
		this.codiceFiscaleFornitore = codiceFiscaleFornitore;
	}
	public String[] getCodQualificheNotIn() {
		return codQualificheNotIn;
	}
	public void setCodQualificheNotIn(String[] codQualificheNotIn) {
		this.codQualificheNotIn = codQualificheNotIn;
	}
	public String[] getCodQualificheIn() {
		return codQualificheIn;
	}
	public void setCodQualificheIn(String[] codQualificheIn) {
		this.codQualificheIn = codQualificheIn;
	}
	public String getCognomeFornitore() {
		return cognomeFornitore;
	}
	public void setCognomeFornitore(String cognomeFornitore) {
		this.cognomeFornitore = cognomeFornitore;
	}
	public Double getCostoOrario() {
		return costoOrario;
	}
	public void setCostoOrario(Double costoOrario) {
		this.costoOrario = costoOrario;
	}
	public Double getCostoRisorsa() {
		return costoRisorsa;
	}
	public void setCostoRisorsa(Double costoRisorsa) {
		this.costoRisorsa = costoRisorsa;
	}
	public String getDenominazioneFornitore() {
		return denominazioneFornitore;
	}
	public void setDenominazioneFornitore(String denominazioneFornitore) {
		this.denominazioneFornitore = denominazioneFornitore;
	}
	public String getDescQualifica() {
		return descQualifica;
	}
	public void setDescQualifica(String descQualifica) {
		this.descQualifica = descQualifica;
	}
	public String getDescBreveTipoSoggetto() {
		return descBreveTipoSoggetto;
	}
	public void setDescBreveTipoSoggetto(String descBreveTipoSoggetto) {
		this.descBreveTipoSoggetto = descBreveTipoSoggetto;
	}
	public String getDescTipoSoggetto() {
		return descTipoSoggetto;
	}
	public void setDescTipoSoggetto(String descTipoSoggetto) {
		this.descTipoSoggetto = descTipoSoggetto;
	}
	public Date getDtFineValidita() {
		return dtFineValidita;
	}
	public void setDtFineValidita(Date dtFineValidita) {
		this.dtFineValidita = dtFineValidita;
	}
	public Boolean getFlagHasQualifica() {
		return flagHasQualifica;
	}
	public void setFlagHasQualifica(Boolean flagHasQualifica) {
		this.flagHasQualifica = flagHasQualifica;
	}
	public Long getIdAttivitaAteco() {
		return idAttivitaAteco;
	}
	public void setIdAttivitaAteco(Long idAttivitaAteco) {
		this.idAttivitaAteco = idAttivitaAteco;
	}
	public Long getIdFormaGiuridica() {
		return idFormaGiuridica;
	}
	public void setIdFormaGiuridica(Long idFormaGiuridica) {
		this.idFormaGiuridica = idFormaGiuridica;
	}
	public Long getIdFornitore() {
		return idFornitore;
	}
	public void setIdFornitore(Long idFornitore) {
		this.idFornitore = idFornitore;
	}
	public Long getIdNazione() {
		return idNazione;
	}
	public void setIdNazione(Long idNazione) {
		this.idNazione = idNazione;
	}
	public Long getIdQualifica() {
		return idQualifica;
	}
	public void setIdQualifica(Long idQualifica) {
		this.idQualifica = idQualifica;
	}
	public Long getIdSoggettoFornitore() {
		return idSoggettoFornitore;
	}
	public void setIdSoggettoFornitore(Long idSoggettoFornitore) {
		this.idSoggettoFornitore = idSoggettoFornitore;
	}
	public Long getIdTipoSoggetto() {
		return idTipoSoggetto;
	}
	public void setIdTipoSoggetto(Long idTipoSoggetto) {
		this.idTipoSoggetto = idTipoSoggetto;
	}
	public Boolean getIncludiFornitoriInvalidi() {
		return includiFornitoriInvalidi;
	}
	public void setIncludiFornitoriInvalidi(Boolean includiFornitoriInvalidi) {
		this.includiFornitoriInvalidi = includiFornitoriInvalidi;
	}
	public Double getMonteOre() {
		return monteOre;
	}
	public void setMonteOre(Double monteOre) {
		this.monteOre = monteOre;
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
