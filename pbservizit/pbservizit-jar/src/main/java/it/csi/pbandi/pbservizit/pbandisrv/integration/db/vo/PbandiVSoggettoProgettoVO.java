/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;

public class PbandiVSoggettoProgettoVO extends GenericVO {

	private String codiceFiscaleSoggetto;
	private String codiceVisualizzatoProgetto;
	private String codUtente;
	private String descBreveTipoAnagrafica;
	private Date dtFineValidita;
	private Date dtInizioValidita;
	private String flagAggiornatoFlux;
	private String idIstanzaProcesso;
	private BigDecimal idProgetto;
	private BigDecimal idProcesso;
	private BigDecimal idSoggetto;
	private BigDecimal idTipoAnagrafica;
	private BigDecimal idTipoSoggetto;
	private BigDecimal idUtenteAgg;
	private BigDecimal idUtenteIns;
	private String nomeBandoLinea;
	private BigDecimal progrBandoLineaIntervento;
	private BigDecimal progrSoggettoProgetto;
	private String titoloProgetto;

	@SuppressWarnings("unchecked")
	@Override
	public List getPK() {
		return null;
	}

	@Override
	public boolean isPKValid() {
		return false;
	}

	public String getCodUtente() {
		return codUtente;
	}

	public void setCodUtente(String codUtente) {
		this.codUtente = codUtente;
	}

	public String getDescBreveTipoAnagrafica() {
		return descBreveTipoAnagrafica;
	}

	public void setDescBreveTipoAnagrafica(String descBreveTipoAnagrafica) {
		this.descBreveTipoAnagrafica = descBreveTipoAnagrafica;
	}

	public BigDecimal getIdProgetto() {
		return idProgetto;
	}

	public void setIdProgetto(BigDecimal idProgetto) {
		this.idProgetto = idProgetto;
	}

	public BigDecimal getProgrBandoLineaIntervento() {
		return progrBandoLineaIntervento;
	}

	public void setProgrBandoLineaIntervento(
			BigDecimal progrBandoLineaIntervento) {
		this.progrBandoLineaIntervento = progrBandoLineaIntervento;
	}

	public String getNomeBandoLinea() {
		return nomeBandoLinea;
	}

	public void setNomeBandoLinea(String nomeBandoLinea) {
		this.nomeBandoLinea = nomeBandoLinea;
	}

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

	public BigDecimal getIdTipoAnagrafica() {
		return idTipoAnagrafica;
	}

	public void setIdTipoAnagrafica(BigDecimal idTipoAnagrafica) {
		this.idTipoAnagrafica = idTipoAnagrafica;
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

	public BigDecimal getProgrSoggettoProgetto() {
		return progrSoggettoProgetto;
	}

	public void setProgrSoggettoProgetto(BigDecimal progrSoggettoProgetto) {
		this.progrSoggettoProgetto = progrSoggettoProgetto;
	}

	public String getFlagAggiornatoFlux() {
		return flagAggiornatoFlux;
	}

	public void setFlagAggiornatoFlux(String flagAggiornatoFlux) {
		this.flagAggiornatoFlux = flagAggiornatoFlux;
	}

	public BigDecimal getIdTipoSoggetto() {
		return idTipoSoggetto;
	}

	public void setIdTipoSoggetto(BigDecimal idTipoSoggetto) {
		this.idTipoSoggetto = idTipoSoggetto;
	}

	public BigDecimal getIdUtenteAgg() {
		return idUtenteAgg;
	}

	public void setIdUtenteAgg(BigDecimal idUtenteAgg) {
		this.idUtenteAgg = idUtenteAgg;
	}

	public BigDecimal getIdUtenteIns() {
		return idUtenteIns;
	}

	public void setIdUtenteIns(BigDecimal idUtenteIns) {
		this.idUtenteIns = idUtenteIns;
	}

	public String getIdIstanzaProcesso() {
		return idIstanzaProcesso;
	}

	public void setIdIstanzaProcesso(String idIstanzaProcesso) {
		this.idIstanzaProcesso = idIstanzaProcesso;
	}

	public String getCodiceVisualizzatoProgetto() {
		return codiceVisualizzatoProgetto;
	}

	public void setCodiceVisualizzatoProgetto(String codiceVisualizzatoProgetto) {
		this.codiceVisualizzatoProgetto = codiceVisualizzatoProgetto;
	}

	public String getTitoloProgetto() {
		return titoloProgetto;
	}

	public void setTitoloProgetto(String titoloProgetto) {
		this.titoloProgetto = titoloProgetto;
	}

	public BigDecimal getIdProcesso() {
		return idProcesso;
	}

	public void setIdProcesso(BigDecimal idProcesso) {
		this.idProcesso = idProcesso;
	}

}
