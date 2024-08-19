/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweberog.dto.datiprogetto;

import java.math.BigDecimal;
import java.util.Date;

public class SoggettoProgettoDTO implements java.io.Serializable {

	static final long serialVersionUID = 1;
	private String codiceFiscaleSoggetto;
	private BigDecimal idSoggetto;
	private Long idTipoAnagrafica;
	private String descBreveTipoAnagrafica;
	private String descTipoAnagrafica;
	private String denominazione;
	private Long idTipoSoggettoCorrelato;
	private String descTipoSoggettoCorrelato;
	private String descTipoSoggetto;
	private Long progrSoggettoProgetto;
	private Long progrSoggettiCorrelati;
	private Date dtFineValidita;
	private Long idTipoSoggetto;
	private Long flagPubblicoPrivato;
	private String codUniIpa;
	private boolean isInAttesaEsito;
	private String abilitatoAccesso;
	private boolean rifiutata;
	private boolean disattivazioneDefinitiva;
	
	public String getCodiceFiscaleSoggetto() {
		return codiceFiscaleSoggetto;
	}
	public void setCodiceFiscaleSoggetto(String codiceFiscaleSoggetto) {
		this.codiceFiscaleSoggetto = codiceFiscaleSoggetto;
	}
	public BigDecimal getIdSoggetto() {
		return idSoggetto;
	}
	public void setIdSoggetto(BigDecimal idSoggetto) {
		this.idSoggetto = idSoggetto;
	}
	public Long getIdTipoAnagrafica() {
		return idTipoAnagrafica;
	}
	public void setIdTipoAnagrafica(Long idTipoAnagrafica) {
		this.idTipoAnagrafica = idTipoAnagrafica;
	}
	public String getDescBreveTipoAnagrafica() {
		return descBreveTipoAnagrafica;
	}
	public void setDescBreveTipoAnagrafica(String descBreveTipoAnagrafica) {
		this.descBreveTipoAnagrafica = descBreveTipoAnagrafica;
	}
	public String getDescTipoAnagrafica() {
		return descTipoAnagrafica;
	}
	public void setDescTipoAnagrafica(String descTipoAnagrafica) {
		this.descTipoAnagrafica = descTipoAnagrafica;
	}
	public String getDenominazione() {
		return denominazione;
	}
	public void setDenominazione(String denominazione) {
		this.denominazione = denominazione;
	}
	public Long getIdTipoSoggettoCorrelato() {
		return idTipoSoggettoCorrelato;
	}
	public void setIdTipoSoggettoCorrelato(Long idTipoSoggettoCorrelato) {
		this.idTipoSoggettoCorrelato = idTipoSoggettoCorrelato;
	}
	public String getDescTipoSoggettoCorrelato() {
		return descTipoSoggettoCorrelato;
	}
	public void setDescTipoSoggettoCorrelato(String descTipoSoggettoCorrelato) {
		this.descTipoSoggettoCorrelato = descTipoSoggettoCorrelato;
	}
	public String getDescTipoSoggetto() {
		return descTipoSoggetto;
	}
	public void setDescTipoSoggetto(String descTipoSoggetto) {
		this.descTipoSoggetto = descTipoSoggetto;
	}
	public Long getProgrSoggettoProgetto() {
		return progrSoggettoProgetto;
	}
	public void setProgrSoggettoProgetto(Long progrSoggettoProgetto) {
		this.progrSoggettoProgetto = progrSoggettoProgetto;
	}
	public Long getProgrSoggettiCorrelati() {
		return progrSoggettiCorrelati;
	}
	public void setProgrSoggettiCorrelati(Long progrSoggettiCorrelati) {
		this.progrSoggettiCorrelati = progrSoggettiCorrelati;
	}
	public Date getDtFineValidita() {
		return dtFineValidita;
	}
	public void setDtFineValidita(Date dtFineValidita) {
		this.dtFineValidita = dtFineValidita;
	}
	public Long getIdTipoSoggetto() {
		return idTipoSoggetto;
	}
	public void setIdTipoSoggetto(Long idTipoSoggetto) {
		this.idTipoSoggetto = idTipoSoggetto;
	}
	public Long getFlagPubblicoPrivato() {
		return flagPubblicoPrivato;
	}
	public void setFlagPubblicoPrivato(Long flagPubblicoPrivato) {
		this.flagPubblicoPrivato = flagPubblicoPrivato;
	}
	public String getCodUniIpa() {
		return codUniIpa;
	}
	public void setCodUniIpa(String codUniIpa) {
		this.codUniIpa = codUniIpa;
	}
	public boolean isInAttesaEsito() {
		return isInAttesaEsito;
	}
	public void setInAttesaEsito(boolean isInAttesaEsito) {
		this.isInAttesaEsito = isInAttesaEsito;
	}
	public String getAbilitatoAccesso() {
		return abilitatoAccesso;
	}
	public void setAbilitatoAccesso(String abilitatoAccesso) {
		this.abilitatoAccesso = abilitatoAccesso;
	}
	public boolean isRifiutata() {
		return rifiutata;
	}
	public void setRifiutata(boolean rifiutata) {
		this.rifiutata = rifiutata;
	}
	public boolean isDisattivazioneDefinitiva() {
		return disattivazioneDefinitiva;
	}
	public void setDisattivazioneDefinitiva(boolean disattivazioneDefinitiva) {
		this.disattivazioneDefinitiva = disattivazioneDefinitiva;
	}


}
