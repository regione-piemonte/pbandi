/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebcert.dto.rilevazionicontrolli;

import java.util.Date;

public class ReportCampionamentoDTO implements java.io.Serializable {

	static final long serialVersionUID = 1;

	private Long idDocumentoIndex;
	private Long idNormativa;
	private String descNormativa;
	private Long idAnnoContabile;
	private String descAnnoContabile;
	private Date dataCampionamento;
	private String nomeFile;
	
	public void setIdDocumentoIndex(Long val) {
		idDocumentoIndex = val;
	}

	public Long getIdDocumentoIndex() {
		return idDocumentoIndex;
	}

	public void setIdNormativa(Long val) {
		idNormativa = val;
	}

	public Long getIdNormativa() {
		return idNormativa;
	}

	

	public void setDescNormativa(String val) {
		descNormativa = val;
	}

	public String getDescNormativa() {
		return descNormativa;
	}



	public void setIdAnnoContabile(Long val) {
		idAnnoContabile = val;
	}

	public Long getIdAnnoContabile() {
		return idAnnoContabile;
	}

	

	public void setDescAnnoContabile(String val) {
		descAnnoContabile = val;
	}

	public String getDescAnnoContabile() {
		return descAnnoContabile;
	}

	

	public void setDataCampionamento(Date val) {
		dataCampionamento = val;
	}

	public Date getDataCampionamento() {
		return dataCampionamento;
	}



	public void setNomeFile(String val) {
		nomeFile = val;
	}

	public String getNomeFile() {
		return nomeFile;
	}
}
