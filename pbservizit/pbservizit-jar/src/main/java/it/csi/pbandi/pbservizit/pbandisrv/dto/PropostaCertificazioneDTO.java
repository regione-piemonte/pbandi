/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.pbandisrv.dto;

import java.util.Date;

public class PropostaCertificazioneDTO implements java.io.Serializable {

	static final long serialVersionUID = 1;

	private Date dataPagamenti;
	private Date dataValidazioni;
	private Date dataFideiussioni;
	private Date dataErogazioni;
	private String descProposta;
	private Long idPropostaCertificaz;
	private Date dtOraCreazione;
	private Long numeroProposta;
	private Long idDettPropostaCertif;
	private Double importoErogazioni;
	private Double spesaCertificabileLorda;
	private Double costoAmmesso;
	private Double importoPagamentiValidati;
	private Double importoEccendenzeValidazione;
	private Long idProgetto;
	private Double importoFideiussioni;
	private String codiceVisualizzato;
	private String descBreveStatoPropostaCert;
	private String descStatoPropostaCertif;
	private String titoloProgetto;
	private String attivita;
	private Double percContributoPubblico;
	private Double percCofinFesr;
	private String descBreveCompletaAttivita;
	private String beneficiario;
	private Boolean isBozza;
	public Date getDataPagamenti() {
		return dataPagamenti;
	}
	public void setDataPagamenti(Date dataPagamenti) {
		this.dataPagamenti = dataPagamenti;
	}
	public Date getDataValidazioni() {
		return dataValidazioni;
	}
	public void setDataValidazioni(Date dataValidazioni) {
		this.dataValidazioni = dataValidazioni;
	}
	public Date getDataFideiussioni() {
		return dataFideiussioni;
	}
	public void setDataFideiussioni(Date dataFideiussioni) {
		this.dataFideiussioni = dataFideiussioni;
	}
	public Date getDataErogazioni() {
		return dataErogazioni;
	}
	public void setDataErogazioni(Date dataErogazioni) {
		this.dataErogazioni = dataErogazioni;
	}
	public String getDescProposta() {
		return descProposta;
	}
	public void setDescProposta(String descProposta) {
		this.descProposta = descProposta;
	}
	public Long getIdPropostaCertificaz() {
		return idPropostaCertificaz;
	}
	public void setIdPropostaCertificaz(Long idPropostaCertificaz) {
		this.idPropostaCertificaz = idPropostaCertificaz;
	}
	public Date getDtOraCreazione() {
		return dtOraCreazione;
	}
	public void setDtOraCreazione(Date dtOraCreazione) {
		this.dtOraCreazione = dtOraCreazione;
	}
	public Long getNumeroProposta() {
		return numeroProposta;
	}
	public void setNumeroProposta(Long numeroProposta) {
		this.numeroProposta = numeroProposta;
	}
	public Long getIdDettPropostaCertif() {
		return idDettPropostaCertif;
	}
	public void setIdDettPropostaCertif(Long idDettPropostaCertif) {
		this.idDettPropostaCertif = idDettPropostaCertif;
	}
	public Double getImportoErogazioni() {
		return importoErogazioni;
	}
	public void setImportoErogazioni(Double importoErogazioni) {
		this.importoErogazioni = importoErogazioni;
	}
	public Double getSpesaCertificabileLorda() {
		return spesaCertificabileLorda;
	}
	public void setSpesaCertificabileLorda(Double spesaCertificabileLorda) {
		this.spesaCertificabileLorda = spesaCertificabileLorda;
	}
	public Double getCostoAmmesso() {
		return costoAmmesso;
	}
	public void setCostoAmmesso(Double costoAmmesso) {
		this.costoAmmesso = costoAmmesso;
	}
	public Double getImportoPagamentiValidati() {
		return importoPagamentiValidati;
	}
	public void setImportoPagamentiValidati(Double importoPagamentiValidati) {
		this.importoPagamentiValidati = importoPagamentiValidati;
	}
	public Double getImportoEccendenzeValidazione() {
		return importoEccendenzeValidazione;
	}
	public void setImportoEccendenzeValidazione(Double importoEccendenzeValidazione) {
		this.importoEccendenzeValidazione = importoEccendenzeValidazione;
	}
	public Long getIdProgetto() {
		return idProgetto;
	}
	public void setIdProgetto(Long idProgetto) {
		this.idProgetto = idProgetto;
	}
	public Double getImportoFideiussioni() {
		return importoFideiussioni;
	}
	public void setImportoFideiussioni(Double importoFideiussioni) {
		this.importoFideiussioni = importoFideiussioni;
	}
	public String getCodiceVisualizzato() {
		return codiceVisualizzato;
	}
	public void setCodiceVisualizzato(String codiceVisualizzato) {
		this.codiceVisualizzato = codiceVisualizzato;
	}
	public String getDescBreveStatoPropostaCert() {
		return descBreveStatoPropostaCert;
	}
	public void setDescBreveStatoPropostaCert(String descBreveStatoPropostaCert) {
		this.descBreveStatoPropostaCert = descBreveStatoPropostaCert;
	}
	public String getDescStatoPropostaCertif() {
		return descStatoPropostaCertif;
	}
	public void setDescStatoPropostaCertif(String descStatoPropostaCertif) {
		this.descStatoPropostaCertif = descStatoPropostaCertif;
	}
	public String getTitoloProgetto() {
		return titoloProgetto;
	}
	public void setTitoloProgetto(String titoloProgetto) {
		this.titoloProgetto = titoloProgetto;
	}
	public String getAttivita() {
		return attivita;
	}
	public void setAttivita(String attivita) {
		this.attivita = attivita;
	}
	public Double getPercContributoPubblico() {
		return percContributoPubblico;
	}
	public void setPercContributoPubblico(Double percContributoPubblico) {
		this.percContributoPubblico = percContributoPubblico;
	}
	public Double getPercCofinFesr() {
		return percCofinFesr;
	}
	public void setPercCofinFesr(Double percCofinFesr) {
		this.percCofinFesr = percCofinFesr;
	}
	public String getDescBreveCompletaAttivita() {
		return descBreveCompletaAttivita;
	}
	public void setDescBreveCompletaAttivita(String descBreveCompletaAttivita) {
		this.descBreveCompletaAttivita = descBreveCompletaAttivita;
	}
	public String getBeneficiario() {
		return beneficiario;
	}
	public void setBeneficiario(String beneficiario) {
		this.beneficiario = beneficiario;
	}
	public Boolean getIsBozza() {
		return isBozza;
	}
	public void setIsBozza(Boolean isBozza) {
		this.isBozza = isBozza;
	}
	
	
	
	

}
