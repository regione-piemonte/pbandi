/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebcert.integration.vo;

import java.math.BigDecimal;
import java.sql.Date;


public class DettPropostaCertifVO extends PbandiTDettPropostaCertifVO {

	private String codiceVisualizzato;
	private String descBreveStatoPropostaCert;
	private String descStatoPropostaCertif;
	private Date dtCutOffFideiussioni;
	private Date dtOraCreazione;
	private Date dtCutOffPagamenti;
	private Date dtCutOffErogazioni;
	private Date dtCutOffValidazioni;
	private String descProposta;
	private String titoloProgetto;
	private Long idStatoPropostaCertif;
	private String attivita;
	private BigDecimal percContributoPubblico;
	private BigDecimal percCofinFesr;
	private String descBreveCompletaAttivita;
	private String beneficiario;
	private String titoloProgettoAttuale;
	
	public void setCodiceVisualizzato(String codiceVisualizzato) {
		this.codiceVisualizzato = codiceVisualizzato;
	}

	public String getCodiceVisualizzato() {
		return codiceVisualizzato;
	}

	public void setDescBreveStatoPropostaCert(String descBreveStatoPropostaCert) {
		this.descBreveStatoPropostaCert = descBreveStatoPropostaCert;
	}

	public String getDescBreveStatoPropostaCert() {
		return descBreveStatoPropostaCert;
	}

	public void setDescStatoPropostaCertif(String descStatoPropostaCertif) {
		this.descStatoPropostaCertif = descStatoPropostaCertif;
	}

	public String getDescStatoPropostaCertif() {
		return descStatoPropostaCertif;
	}

	public void setDtCutOffFideiussioni(Date dtCutOffFideiussioni) {
		this.dtCutOffFideiussioni = dtCutOffFideiussioni;
	}

	public Date getDtCutOffFideiussioni() {
		return dtCutOffFideiussioni;
	}

	public void setDtOraCreazione(Date dtOraCreazione) {
		this.dtOraCreazione = dtOraCreazione;
	}

	public Date getDtOraCreazione() {
		return dtOraCreazione;
	}

	public void setDtCutOffPagamenti(Date dtCutOffPagamenti) {
		this.dtCutOffPagamenti = dtCutOffPagamenti;
	}

	public Date getDtCutOffPagamenti() {
		return dtCutOffPagamenti;
	}

	public void setDtCutOffErogazioni(Date dtCutOffErogazioni) {
		this.dtCutOffErogazioni = dtCutOffErogazioni;
	}

	public Date getDtCutOffErogazioni() {
		return dtCutOffErogazioni;
	}

	public void setDtCutOffValidazioni(Date dtCutOffValidazioni) {
		this.dtCutOffValidazioni = dtCutOffValidazioni;
	}

	public Date getDtCutOffValidazioni() {
		return dtCutOffValidazioni;
	}

	public void setDescProposta(String descProposta) {
		this.descProposta = descProposta;
	}

	public String getDescProposta() {
		return descProposta;
	}

	public void setTitoloProgetto(String titoloProgetto) {
		this.titoloProgetto = titoloProgetto;
	}

	public String getTitoloProgetto() {
		return titoloProgetto;
	}

	public void setIdStatoPropostaCertif(Long idStatoPropostaCertif) {
		this.idStatoPropostaCertif = idStatoPropostaCertif;
	}

	public Long getIdStatoPropostaCertif() {
		return idStatoPropostaCertif;
	}

	public void setAttivita(String attivita) {
		this.attivita = attivita;
	}

	public String getAttivita() {
		return attivita;
	}

	public void setPercContributoPubblico(BigDecimal percContributoPubblico) {
		this.percContributoPubblico = percContributoPubblico;
	}

	public BigDecimal getPercContributoPubblico() {
		return percContributoPubblico;
	}

	public void setPercCofinFesr(BigDecimal percCofinFesr) {
		this.percCofinFesr = percCofinFesr;
	}

	public BigDecimal getPercCofinFesr() {
		return percCofinFesr;
	}

	public void setDescBreveCompletaAttivita(String descBreveCompletaAttivita) {
		this.descBreveCompletaAttivita = descBreveCompletaAttivita;
	}

	public String getDescBreveCompletaAttivita() {
		return descBreveCompletaAttivita;
	}

	public void setBeneficiario(String beneficiario) {
		this.beneficiario = beneficiario;
	}

	public String getBeneficiario() {
		return beneficiario;
	}

	public void setTitoloProgettoAttuale(String titoloProgettoAttuale) {
		this.titoloProgettoAttuale = titoloProgettoAttuale;
	}

	public String getTitoloProgettoAttuale() {
		return titoloProgettoAttuale;
	}
}
