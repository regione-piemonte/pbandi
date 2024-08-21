/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo;

public class SezioneContenutoVO extends GenericVO {
	
	private String contenutoMicroSezione;
	private Long idTipoDocumentoIndex;

	public Long getIdTipoDocumentoIndex() {
		return idTipoDocumentoIndex;
	}

	public void setIdTipoDocumentoIndex(Long idTipoDocumentoIndex) {
		this.idTipoDocumentoIndex = idTipoDocumentoIndex;
	}

	public String getContenutoMicroSezione() {
		return contenutoMicroSezione;
	}

	public void setContenutoMicroSezione(String contenutoMicroSezione) {
		this.contenutoMicroSezione = contenutoMicroSezione;
	}

}
