/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo;

import java.math.BigDecimal;
import java.sql.Date;

public class BeneficiarioProgettoVO extends GenericVO {

	private String codiceFiscaleSoggetto;
	private String codiceVisualizzatoProgetto;
	private String denominazioneBeneficiario;
	private Date dtInizioValidita;
	private Date dtFineValidita;
	private String idIstanzaProcesso;
	private BigDecimal idSoggetto;
	private BigDecimal idProgetto;
	private BigDecimal idTipoAnagrafica;
	private BigDecimal idTipoBeneficiario;
	private BigDecimal idTipoSoggetto;
	private BigDecimal idUtenteIns;
	private BigDecimal idUtenteAgg;
	private BigDecimal idPersonaFisica;
	private BigDecimal idEnteGiuridico;
	private String titoloProgetto;
	private String nomeBandoLinea;
	private BigDecimal ProgrBandoLineaIntervento;
	
	public String getNomeBandoLinea() {
		return nomeBandoLinea;
	}

	public void setNomeBandoLinea(String nomeBandoLinea) {
		this.nomeBandoLinea = nomeBandoLinea;
	}

	public BigDecimal getIdTipoAnagrafica() {
		return idTipoAnagrafica;
	}

	public void setIdTipoAnagrafica(BigDecimal idTipoAnagrafica) {
		this.idTipoAnagrafica = idTipoAnagrafica;
	}

	public BigDecimal getIdTipoBeneficiario() {
		return idTipoBeneficiario;
	}

	public void setIdTipoBeneficiario(BigDecimal idTipoBeneficiario) {
		this.idTipoBeneficiario = idTipoBeneficiario;
	}

	public BigDecimal getIdSoggetto() {
		return idSoggetto;
	}

	public void setIdSoggetto(BigDecimal idSoggetto) {
		this.idSoggetto = idSoggetto;
	}

	public String getCodiceFiscaleSoggetto() {
		return codiceFiscaleSoggetto;
	}

	public void setCodiceFiscaleSoggetto(String codiceFiscaleSoggetto) {
		this.codiceFiscaleSoggetto = codiceFiscaleSoggetto;
	}

	public BigDecimal getIdTipoSoggetto() {
		return idTipoSoggetto;
	}

	public void setIdTipoSoggetto(BigDecimal idTipoSoggetto) {
		this.idTipoSoggetto = idTipoSoggetto;
	}

	public BigDecimal getIdUtenteIns() {
		return idUtenteIns;
	}

	public void setIdUtenteIns(BigDecimal idUtenteIns) {
		this.idUtenteIns = idUtenteIns;
	}

	public BigDecimal getIdUtenteAgg() {
		return idUtenteAgg;
	}

	public void setIdUtenteAgg(BigDecimal idUtenteAgg) {
		this.idUtenteAgg = idUtenteAgg;
	}

	public void setIdProgetto(BigDecimal idProgetto) {
		this.idProgetto = idProgetto;
	}

	public BigDecimal getIdProgetto() {
		return idProgetto;
	}

	public void setCodiceVisualizzatoProgetto(String codiceVisualizzatoProgetto) {
		this.codiceVisualizzatoProgetto = codiceVisualizzatoProgetto;
	}

	public String getCodiceVisualizzatoProgetto() {
		return codiceVisualizzatoProgetto;
	}

	public void setDenominazioneBeneficiario(String denominazioneBeneficiario) {
		this.denominazioneBeneficiario = denominazioneBeneficiario;
	}

	public String getDenominazioneBeneficiario() {
		return denominazioneBeneficiario;
	}

	public Date getDtInizioValidita() {
		return dtInizioValidita;
	}

	public void setDtInizioValidita(Date dtInizioValidita) {
		this.dtInizioValidita = dtInizioValidita;
	}

	public Date getDtFineValidita() {
		return dtFineValidita;
	}

	public void setDtFineValidita(Date dtFineValidita) {
		this.dtFineValidita = dtFineValidita;
	}

	public void setIdIstanzaProcesso(String idIstanzaProcesso) {
		this.idIstanzaProcesso = idIstanzaProcesso;
	}

	public String getIdIstanzaProcesso() {
		return idIstanzaProcesso;
	}

	public BigDecimal getIdPersonaFisica() {
		return idPersonaFisica;
	}

	public void setIdPersonaFisica(BigDecimal idPersonaFisica) {
		this.idPersonaFisica = idPersonaFisica;
	}

	public BigDecimal getIdEnteGiuridico() {
		return idEnteGiuridico;
	}

	public void setIdEnteGiuridico(BigDecimal idEnteGiuridico) {
		this.idEnteGiuridico = idEnteGiuridico;
	}

	public String getTitoloProgetto() {
		return titoloProgetto;
	}

	public void setTitoloProgetto(String titoloProgetto) {
		this.titoloProgetto = titoloProgetto;
	}

	public BigDecimal getProgrBandoLineaIntervento() {
		return ProgrBandoLineaIntervento;
	}

	public void setProgrBandoLineaIntervento(BigDecimal progrBandoLineaIntervento) {
		ProgrBandoLineaIntervento = progrBandoLineaIntervento;
	}	
	
}
