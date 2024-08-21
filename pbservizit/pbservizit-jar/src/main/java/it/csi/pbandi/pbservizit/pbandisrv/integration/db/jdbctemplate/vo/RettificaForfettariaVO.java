/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo;

import java.math.BigDecimal;
import java.sql.Date;

public class RettificaForfettariaVO extends GenericVO {
	private BigDecimal idRettificaForfett;
	private Date dataInserimento;
	private Double percRett;
	private BigDecimal idCategAnagrafica;
	private String descCategAnagrafica;
	private BigDecimal idProceduraAggiudicaz;
	private String codProcAgg;
	private String cigProcAgg;
	private String descProcAgg;
	private BigDecimal idDocumentoIndex;
	private String nomeFile;
	private BigDecimal idEsitoIntermedio;
	private String esitoIntermedio;
	private String flagRettificaIntermedio;
	private BigDecimal idEsitoDefinitivo;
	private String esitoDefinitivo;
	private String flagRettificaDefinitivo;
	private BigDecimal idPropostaCertificaz;
	private Date dtOraCreazione;
	private BigDecimal idProgetto;
	private String codiceVisualizzato;
	private BigDecimal idSoggettoBeneficiario;
	private String denominazioneBeneficiario;
	private BigDecimal idAppalto;
	
	public BigDecimal getIdAppalto() {
		return idAppalto;
	}
	public void setIdAppalto(BigDecimal idAppalto) {
		this.idAppalto = idAppalto;
	}
	public BigDecimal getIdCategAnagrafica() {
		return idCategAnagrafica;
	}
	public void setIdCategAnagrafica(BigDecimal idCategAnagrafica) {
		this.idCategAnagrafica = idCategAnagrafica;
	}
	public BigDecimal getIdRettificaForfett() {
		return idRettificaForfett;
	}
	public void setIdRettificaForfett(BigDecimal idRettificaForfett) {
		this.idRettificaForfett = idRettificaForfett;
	}
	
	public Date getDataInserimento() {
		return dataInserimento;
	}
	public void setDataInserimento(Date dataInserimento) {
		this.dataInserimento = dataInserimento;
	}
	public Double getPercRett() {
		return percRett;
	}
	public void setPercRett(Double percRett) {
		this.percRett = percRett;
	}
	public String getDescCategAnagrafica() {
		return descCategAnagrafica;
	}
	public void setDescCategAnagrafica(String descCategAnagrafica) {
		this.descCategAnagrafica = descCategAnagrafica;
	}
	public BigDecimal getIdProceduraAggiudicaz() {
		return idProceduraAggiudicaz;
	}
	public void setIdProceduraAggiudicaz(BigDecimal idProceduraAggiudicaz) {
		this.idProceduraAggiudicaz = idProceduraAggiudicaz;
	}
	public String getCodProcAgg() {
		return codProcAgg;
	}
	public void setCodProcAgg(String codProcAgg) {
		this.codProcAgg = codProcAgg;
	}
	public String getCigProcAgg() {
		return cigProcAgg;
	}
	public void setCigProcAgg(String cigProcAgg) {
		this.cigProcAgg = cigProcAgg;
	}
	public BigDecimal getIdDocumentoIndex() {
		return idDocumentoIndex;
	}
	public void setIdDocumentoIndex(BigDecimal idDocumentoIndex) {
		this.idDocumentoIndex = idDocumentoIndex;
	}
	public String getNomeFile() {
		return nomeFile;
	}
	public void setNomeFile(String nomeFile) {
		this.nomeFile = nomeFile;
	}
	public BigDecimal getIdEsitoIntermedio() {
		return idEsitoIntermedio;
	}
	public void setIdEsitoIntermedio(BigDecimal idEsitoIntermedio) {
		this.idEsitoIntermedio = idEsitoIntermedio;
	}
	public String getEsitoIntermedio() {
		return esitoIntermedio;
	}
	public void setEsitoIntermedio(String esitoIntermedio) {
		this.esitoIntermedio = esitoIntermedio;
	}
	public String getFlagRettificaIntermedio() {
		return flagRettificaIntermedio;
	}
	public void setFlagRettificaIntermedio(String flagRettificaIntermedio) {
		this.flagRettificaIntermedio = flagRettificaIntermedio;
	}
	public BigDecimal getIdEsitoDefinitivo() {
		return idEsitoDefinitivo;
	}
	public void setIdEsitoDefinitivo(BigDecimal idEsitoDefinitivo) {
		this.idEsitoDefinitivo = idEsitoDefinitivo;
	}
	public String getEsitoDefinitivo() {
		return esitoDefinitivo;
	}
	public void setEsitoDefinitivo(String esitoDefinitivo) {
		this.esitoDefinitivo = esitoDefinitivo;
	}
	public String getFlagRettificaDefinitivo() {
		return flagRettificaDefinitivo;
	}
	public void setFlagRettificaDefinitivo(String flagRettificaDefinitivo) {
		this.flagRettificaDefinitivo = flagRettificaDefinitivo;
	}
	public BigDecimal getIdPropostaCertificaz() {
		return idPropostaCertificaz;
	}
	public void setIdPropostaCertificaz(BigDecimal idPropostaCertificaz) {
		this.idPropostaCertificaz = idPropostaCertificaz;
	}
	public Date getDtOraCreazione() {
		return dtOraCreazione;
	}
	public void setDtOraCreazione(Date dtOraCreazione) {
		this.dtOraCreazione = dtOraCreazione;
	}
	public BigDecimal getIdProgetto() {
		return idProgetto;
	}
	public void setIdProgetto(BigDecimal idProgetto) {
		this.idProgetto = idProgetto;
	}
	public String getCodiceVisualizzato() {
		return codiceVisualizzato;
	}
	public void setCodiceVisualizzato(String codiceVisualizzato) {
		this.codiceVisualizzato = codiceVisualizzato;
	}
	public BigDecimal getIdSoggettoBeneficiario() {
		return idSoggettoBeneficiario;
	}
	public void setIdSoggettoBeneficiario(BigDecimal idSoggettoBeneficiario) {
		this.idSoggettoBeneficiario = idSoggettoBeneficiario;
	}
	public String getDenominazioneBeneficiario() {
		return denominazioneBeneficiario;
	}
	public void setDenominazioneBeneficiario(String denominazioneBeneficiario) {
		this.denominazioneBeneficiario = denominazioneBeneficiario;
	}
}
