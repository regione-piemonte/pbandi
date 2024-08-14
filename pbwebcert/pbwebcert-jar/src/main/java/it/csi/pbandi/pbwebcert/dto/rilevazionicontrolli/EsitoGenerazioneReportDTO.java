/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebcert.dto.rilevazionicontrolli;

public class EsitoGenerazioneReportDTO implements java.io.Serializable {

	static final long serialVersionUID = 1;
	private Long idDocumentoIndex;
	private Boolean esito;
	private String messagge;
	private byte[] report;
	private String nomeDocumento;
	
	public void setEsito(Boolean val) {
		esito = val;
	}

	public Boolean getEsito() {
		return esito;
	}

	public void setReport(byte[] val) {
		report = val;
	}

	public byte[] getReport() {
		return report;
	}

	
	public void setMessagge(String val) {
		messagge = val;
	}

	public String getMessagge() {
		return messagge;
	}

	public void setIdDocumentoIndex(Long val) {
		idDocumentoIndex = val;
	}
	public Long getIdDocumentoIndex() {
		return idDocumentoIndex;
	}

	public String getNomeDocumento() {
		return nomeDocumento;
	}

	public void setNomeDocumento(String nomeDocumento) {
		this.nomeDocumento = nomeDocumento;
	}

}
