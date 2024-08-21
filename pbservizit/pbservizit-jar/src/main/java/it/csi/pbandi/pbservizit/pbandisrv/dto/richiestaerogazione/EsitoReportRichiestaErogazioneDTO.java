/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.pbandisrv.dto.richiestaerogazione;

public class EsitoReportRichiestaErogazioneDTO implements java.io.Serializable {

	static final long serialVersionUID = 1;

	private String[] messages;
	private Boolean esito;
	private String nomeReport;
	private byte[] pdfBytes;
	private Long idDocIndex;
	public String[] getMessages() {
		return messages;
	}
	public void setMessages(String[] messages) {
		this.messages = messages;
	}
	public Boolean getEsito() {
		return esito;	}
	public void setEsito(Boolean esito) {
		this.esito = esito;
	}
	public String getNomeReport() {
		return nomeReport;
	}
	public void setNomeReport(String nomeReport) {
		this.nomeReport = nomeReport;
	}
	public byte[] getPdfBytes() {
		return pdfBytes;
	}
	public void setPdfBytes(byte[] pdfBytes) {
		this.pdfBytes = pdfBytes;
	}
	public Long getIdDocIndex() {
		return idDocIndex;
	}
	public void setIdDocIndex(Long idDocIndex) {
		this.idDocIndex = idDocIndex;
	}
	
	

	
	
}
