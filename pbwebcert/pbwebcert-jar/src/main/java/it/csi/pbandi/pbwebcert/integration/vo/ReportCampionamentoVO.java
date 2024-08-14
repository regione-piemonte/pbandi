/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebcert.integration.vo;

import java.math.BigDecimal;
import java.util.Date;

public class ReportCampionamentoVO {
	private BigDecimal idDocumentoIndex;
	private String	nomeFile;
	private BigDecimal idNormativa;
	
	private String descNormativa;
	private String descAnnoContabile;
	
	private BigDecimal idAnnoContabile;
	private Date dataCampionamento;
	
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

	public BigDecimal getIdNormativa() {
		return idNormativa;
	}
	public void setIdNormativa(BigDecimal idNormativa) {
		this.idNormativa = idNormativa;
	}
	public String getDescNormativa() {
		return descNormativa;
	}
	public void setDescNormativa(String descNormativa) {
		this.descNormativa = descNormativa;
	}
	public String getDescAnnoContabile() {
		return descAnnoContabile;
	}
	public void setDescAnnoContabile(String descAnnoContabile) {
		this.descAnnoContabile = descAnnoContabile;
	}

	public BigDecimal getIdAnnoContabile() {
		return idAnnoContabile;
	}
	public void setIdAnnoContabile(BigDecimal idAnnoContabile) {
		this.idAnnoContabile = idAnnoContabile;
	}
	public Date getDataCampionamento() {
		return dataCampionamento;
	}
	public void setDataCampionamento(Date dataCampionamento) {
		this.dataCampionamento = dataCampionamento;
	}
	
	
}
