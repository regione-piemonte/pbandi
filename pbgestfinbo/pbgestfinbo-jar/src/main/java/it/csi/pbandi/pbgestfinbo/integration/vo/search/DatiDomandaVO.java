/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.integration.vo.search;

import java.math.BigDecimal;

public class DatiDomandaVO {
	//STATO DOMANDA
	private String numeroDomanda;
	private String statoDomanda;
	private String dataPresentazioneDomanda;
	//DOCUMENTO IDENTITA'
	private String documentoIdentita;
	private String numeroDocumento;
	private String dataRilascio;
	private String enteRilascio;
	private String scadenzaDocumento;
	private Long idTipoDocumentoIdentita; 
	//RECAPITI
	private String telefono;
	private String fax;
	private String email;
	private String pec;
	//CONTO CORRENTE
	private String numeroConto;
	private String iban;
	//BANCA DI APPOGGIO
	private String banca;
	private String abi;
	private String cab;
	private BigDecimal idProgetto; 
	private Long idBanca; 
	private Long progrSoggettoDomanda;
	private Long progrSoggettoProgetto;
	


	@Override
	public String toString() {
		return "DatiDomandaVO [numeroDomanda=" + numeroDomanda + ", statoDomanda=" + statoDomanda
				+ ", dataPresentazioneDomanda=" + dataPresentazioneDomanda + ", documentoIdentita=" + documentoIdentita
				+ ", numeroDocumento=" + numeroDocumento
				+  ", dataRilascio=" + dataRilascio
				+ ", enteRilascio=" + enteRilascio + ", scadenzaDocumento=" + scadenzaDocumento + ", telefono="
				+ telefono + ", fax=" + fax + ", email=" + email + ", numeroConto=" + numeroConto + ", iban=" + iban
				+ ", banca=" + banca + ", abi=" + abi + ", cab=" + cab + "]";
	}
	
	
	

	public String getPec() {
		return pec;
	}




	public void setPec(String pec) {
		this.pec = pec;
	}




	public Long getProgrSoggettoDomanda() {
		return progrSoggettoDomanda;
	}




	public void setProgrSoggettoDomanda(Long progrSoggettoDomanda) {
		this.progrSoggettoDomanda = progrSoggettoDomanda;
	}




	public Long getProgrSoggettoProgetto() {
		return progrSoggettoProgetto;
	}




	public void setProgrSoggettoProgetto(Long progrSoggettoProgetto) {
		this.progrSoggettoProgetto = progrSoggettoProgetto;
	}




	public Long getIdTipoDocumentoIdentita() {
		return idTipoDocumentoIdentita;
	}




	public void setIdTipoDocumentoIdentita(Long idTipoDocumentoIdentita) {
		this.idTipoDocumentoIdentita = idTipoDocumentoIdentita;
	}




	public Long getIdBanca() {
		return idBanca;
	}



	public void setIdBanca(Long idBanca) {
		this.idBanca = idBanca;
	}



	public BigDecimal getIdProgetto() {
		return idProgetto;
	}
	public void setIdProgetto(BigDecimal idProgetto) {
		this.idProgetto = idProgetto;
	}
	public String getNumeroDomanda() {
		return numeroDomanda;
	}
	public void setNumeroDomanda(String numeroDomanda) {
		this.numeroDomanda = numeroDomanda;
	}
	public String getStatoDomanda() {
		return statoDomanda;
	}
	public void setStatoDomanda(String statoDomanda) {
		this.statoDomanda = statoDomanda;
	}
	public String getDataPresentazioneDomanda() {
		return dataPresentazioneDomanda;
	}
	public void setDataPresentazioneDomanda(String dataPresentazioneDomanda) {
		this.dataPresentazioneDomanda = dataPresentazioneDomanda;
	}
	public String getDocumentoIdentita() {
		return documentoIdentita;
	}
	public void setDocumentoIdentita(String documentoIdentita) {
		this.documentoIdentita = documentoIdentita;
	}
	public String getNumeroDocumento() {
		return numeroDocumento;
	}
	public void setNumeroDocumento(String numeroDocumento) {
		this.numeroDocumento = numeroDocumento;
	}
	public String getDataRilascio() {
		return dataRilascio;
	}
	public void setDataRilascio(String dataRilascio) {
		this.dataRilascio = dataRilascio;
	}
	public String getEnteRilascio() {
		return enteRilascio;
	}
	public void setEnteRilascio(String enteRilascio) {
		this.enteRilascio = enteRilascio;
	}
	public String getScadenzaDocumento() {
		return scadenzaDocumento;
	}
	public void setScadenzaDocumento(String scadenzaDocumento) {
		this.scadenzaDocumento = scadenzaDocumento;
	}
	public String getTelefono() {
		return telefono;
	}
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	public String getFax() {
		return fax;
	}
	public void setFax(String fax) {
		this.fax = fax;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getNumeroConto() {
		return numeroConto;
	}
	public void setNumeroConto(String numeroConto) {
		this.numeroConto = numeroConto;
	}
	public String getIban() {
		return iban;
	}
	public void setIban(String iban) {
		this.iban = iban;
	}
	public String getBanca() {
		return banca;
	}
	public void setBanca(String banca) {
		this.banca = banca;
	}
	public String getAbi() {
		return abi;
	}
	public void setAbi(String abi) {
		this.abi = abi;
	}
	public String getCab() {
		return cab;
	}
	public void setCab(String cab) {
		this.cab = cab;
	}

}
