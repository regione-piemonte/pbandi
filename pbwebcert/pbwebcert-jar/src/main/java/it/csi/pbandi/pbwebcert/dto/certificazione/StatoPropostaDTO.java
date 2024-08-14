/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebcert.dto.certificazione;

public class StatoPropostaDTO implements java.io.Serializable {
	static final long serialVersionUID = 1;

	private Long idStatoPropostaCertif;
	private String descStatoPropostaCertif;
	private String descBreveStatoPropostaCert;
	
	public void setIdStatoPropostaCertif(Long val) {
		idStatoPropostaCertif = val;
	}

	public Long getIdStatoPropostaCertif() {
		return idStatoPropostaCertif;
	}

	

	public void setDescStatoPropostaCertif(String val) {
		descStatoPropostaCertif = val;
	}

	public String getDescStatoPropostaCertif() {
		return descStatoPropostaCertif;
	}

	

	public void setDescBreveStatoPropostaCert(String val) {
		descBreveStatoPropostaCert = val;
	}

	public String getDescBreveStatoPropostaCert() {
		return descBreveStatoPropostaCert;
	}


}
