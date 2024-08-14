/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebcert.dto.certificazione;

import java.util.Date;

public class PropostaCertificazioneDTO implements java.io.Serializable {
	static final long serialVersionUID = 1;
	private Date dataPagamenti = null;
	private Date dataValidazioni = null;
	private Date dataFideiussioni = null;
	private Date dataErogazioni = null;
	private String descProposta = null;
	private Long idPropostaCertificaz = null;
	private Date dtOraCreazione = null;
	private Long numeroProposta = null;
	private Long idDettPropostaCertif = null;
	private Double importoErogazioni = null;
	private Double spesaCertificabileLorda = null;
	private Double costoAmmesso = null;
	private Double importoPagamentiValidati = null;
	private Long idLineaDiIntervento;
	private Boolean isApprovataeNuovaProgrammazione;
	private Long idDocumentoIndex;
	private String nomeDocumento; 
	//nuovo
	private boolean esistePropostaApertaSuccessiva;
	
	public void setDataPagamenti(Date val) {
		dataPagamenti = val;
	}

	public Date getDataPagamenti() {
		return dataPagamenti;
	}

	public void setDataValidazioni(Date val) {
		dataValidazioni = val;
	}

	public Date getDataValidazioni() {
		return dataValidazioni;
	}

	public void setDataFideiussioni(Date val) {
		dataFideiussioni = val;
	}

	public Date getDataFideiussioni() {
		return dataFideiussioni;
	}

	public void setDataErogazioni(Date val) {
		dataErogazioni = val;
	}

	public Date getDataErogazioni() {
		return dataErogazioni;
	}

	public void setDescProposta(String val) {
		descProposta = val;
	}

	public String getDescProposta() {
		return descProposta;
	}

	public void setIdPropostaCertificaz(Long val) {
		idPropostaCertificaz = val;
	}

	public Long getIdPropostaCertificaz() {
		return idPropostaCertificaz;
	}

	public void setDtOraCreazione(Date val) {
		dtOraCreazione = val;
	}

	public Date getDtOraCreazione() {
		return dtOraCreazione;
	}

	public void setNumeroProposta(Long val) {
		numeroProposta = val;
	}

	public Long getNumeroProposta() {
		return numeroProposta;
	}

	public void setIdDettPropostaCertif(Long val) {
		idDettPropostaCertif = val;
	}

	public Long getIdDettPropostaCertif() {
		return idDettPropostaCertif;
	}

	public void setImportoErogazioni(Double val) {
		importoErogazioni = val;
	}

	public Double getImportoErogazioni() {
		return importoErogazioni;
	}

	public void setSpesaCertificabileLorda(Double val) {
		spesaCertificabileLorda = val;
	}

	public Double getSpesaCertificabileLorda() {
		return spesaCertificabileLorda;
	}

	public void setCostoAmmesso(Double val) {
		costoAmmesso = val;
	}

	public Double getCostoAmmesso() {
		return costoAmmesso;
	}

	public void setImportoPagamentiValidati(Double val) {
		importoPagamentiValidati = val;
	}

	public Double getImportoPagamentiValidati() {
		return importoPagamentiValidati;
	}

	private Double importoEccendenzeValidazione = null;

	public void setImportoEccendenzeValidazione(Double val) {
		importoEccendenzeValidazione = val;
	}

	public Double getImportoEccendenzeValidazione() {
		return importoEccendenzeValidazione;
	}

	private Long idProgetto = null;

	public void setIdProgetto(Long val) {
		idProgetto = val;
	}

	public Long getIdProgetto() {
		return idProgetto;
	}

	private Double importoFideiussioni = null;

	public void setImportoFideiussioni(Double val) {
		importoFideiussioni = val;
	}

	public Double getImportoFideiussioni() {
		return importoFideiussioni;
	}

	private String codiceVisualizzato = null;

	public void setCodiceVisualizzato(String val) {
		codiceVisualizzato = val;
	}

	public String getCodiceVisualizzato() {
		return codiceVisualizzato;
	}

	private String descBreveStatoPropostaCert = null;

	public void setDescBreveStatoPropostaCert(String val) {
		descBreveStatoPropostaCert = val;
	}

	public String getDescBreveStatoPropostaCert() {
		return descBreveStatoPropostaCert;
	}

	private String descStatoPropostaCertif = null;

	public void setDescStatoPropostaCertif(String val) {
		descStatoPropostaCertif = val;
	}

	public String getDescStatoPropostaCertif() {
		return descStatoPropostaCertif;
	}

	private String titoloProgetto = null;

	public void setTitoloProgetto(String val) {
		titoloProgetto = val;
	}

	public String getTitoloProgetto() {
		return titoloProgetto;
	}

	private String attivita = null;

	public void setAttivita(String val) {
		attivita = val;
	}

	public String getAttivita() {
		return attivita;
	}

	private Double percContributoPubblico = null;

	public void setPercContributoPubblico(Double val) {
		percContributoPubblico = val;
	}

	public Double getPercContributoPubblico() {
		return percContributoPubblico;
	}

	private Double percCofinFesr = null;

	public void setPercCofinFesr(Double val) {
		percCofinFesr = val;
	}

	public Double getPercCofinFesr() {
		return percCofinFesr;
	}

	private String descBreveCompletaAttivita = null;

	public void setDescBreveCompletaAttivita(String val) {
		descBreveCompletaAttivita = val;
	}

	public String getDescBreveCompletaAttivita() {
		return descBreveCompletaAttivita;
	}

	private String beneficiario = null;

	public void setBeneficiario(String val) {
		beneficiario = val;
	}

	public String getBeneficiario() {
		return beneficiario;
	}

	private java.lang.Boolean isBozza = null;

	public void setIsBozza(java.lang.Boolean val) {
		isBozza = val;
	}

	public java.lang.Boolean getIsBozza() {
		return isBozza;
	}

	public Long getIdLineaDiIntervento() {
		return idLineaDiIntervento;
	}

	public void setIdLineaDiIntervento(Long idLineaDiIntervento) {
		this.idLineaDiIntervento = idLineaDiIntervento;
	}

	public Boolean getIsApprovataeNuovaProgrammazione() {
		return isApprovataeNuovaProgrammazione;
	}

	public void setIsApprovataeNuovaProgrammazione(Boolean isApprovataeNuovaProgrammazione) {
		this.isApprovataeNuovaProgrammazione = isApprovataeNuovaProgrammazione;
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("[");
		sb.append("Attivita=" + getAttivita());
		sb.append("\nBeneficiario=" + getBeneficiario());
		sb.append("\nCodiceVisualizzato=" + getCodiceVisualizzato());
		sb.append("\nCostoAmmesso=" + getCostoAmmesso());
		sb.append("\nDataErogazioni=" + getDataErogazioni());
		sb.append("\nDataFideiussioni=" + getDataFideiussioni());
		sb.append("\nDataPagamenti=" + getDataPagamenti());
		sb.append("\nDataValidazioni=" + getDataValidazioni());
		sb.append("\nDescBreveCompletaAttivita=" + getDescBreveCompletaAttivita());
		sb.append("\nDescBreveStatoPropostaCert=" + getDescBreveStatoPropostaCert());
		sb.append("\nDescProposta=" + getDescProposta());
		sb.append("\nDescStatoPropostaCertif=" + getDescStatoPropostaCertif());
		sb.append("\nDtOraCreazione=" + getDtOraCreazione());
		sb.append("\nIdDettPropostaCertif=" + getIdDettPropostaCertif());
		sb.append("\nIdProgetto=" + getIdProgetto());
		sb.append("\nIdPropostaCertificaz=" + getIdPropostaCertificaz());
		sb.append("\nImportoEccendenzeValidazione=" + getImportoEccendenzeValidazione());
		sb.append("\nImportoErogazioni=" + getImportoErogazioni());
		sb.append("\nImportoFideiussioni=" + getImportoFideiussioni());
		sb.append("\nImportoPagamentiValidati=" + getImportoPagamentiValidati());
		sb.append("\nIsBozza=" + getIsBozza());
		sb.append("\nNumeroProposta=" + getNumeroProposta());
		sb.append("\nPercCofinFesr=" + getPercCofinFesr());
		sb.append("\nPercContributoPubblico=" + getPercContributoPubblico());
		sb.append("\nSpesaCertificabileLorda=" + getSpesaCertificabileLorda());
		sb.append("\nTitoloProgetto=" + getTitoloProgetto());
		sb.append("]");
		return sb.toString();
	}

	public Long getIdDocumentoIndex() {
		return idDocumentoIndex;
	}

	public void setIdDocumentoIndex(Long idDocumentoIndex) {
		this.idDocumentoIndex = idDocumentoIndex;
	}

	public String getNomeDocumento() {
		return nomeDocumento;
	}

	public void setNomeDocumento(String nomeDocumento) {
		this.nomeDocumento = nomeDocumento;
	}

	public boolean isEsistePropostaApertaSuccessiva() {
		return esistePropostaApertaSuccessiva;
	}

	public void setEsistePropostaApertaSuccessiva(boolean esistePropostaApertaSuccessiva) {
		this.esistePropostaApertaSuccessiva = esistePropostaApertaSuccessiva;
	}
}
