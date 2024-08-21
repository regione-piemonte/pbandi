/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.integration.vo;

import java.util.Date;

public class EsitoInviaPropostaRimodulazioneDTO implements java.io.Serializable {

	static final long serialVersionUID = 1;
	
	private Boolean esito;
	private Long idDocumentoIndex;
	private Long idContoEconomico;
	private String nomeFile;
	private Date dataProposta;

	public Boolean getEsito() {
		return esito;
	}

	public void setEsito(Boolean esito) {
		this.esito = esito;
	}

	public Long getIdDocumentoIndex() {
		return idDocumentoIndex;
	}

	public void setIdDocumentoIndex(Long idDocumentoIndex) {
		this.idDocumentoIndex = idDocumentoIndex;
	}

	public Long getIdContoEconomico() {
		return idContoEconomico;
	}

	public void setIdContoEconomico(Long idContoEconomico) {
		this.idContoEconomico = idContoEconomico;
	}

	public String getNomeFile() {
		return nomeFile;
	}

	public void setNomeFile(String nomeFile) {
		this.nomeFile = nomeFile;
	}

	public Date getDataProposta() {
		return dataProposta;
	}

	public void setDataProposta(Date dataProposta) {
		this.dataProposta = dataProposta;
	}
}
