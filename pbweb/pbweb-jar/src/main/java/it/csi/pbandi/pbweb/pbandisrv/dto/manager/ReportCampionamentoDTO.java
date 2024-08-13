/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweb.pbandisrv.dto.manager;

import java.math.BigDecimal;

public class ReportCampionamentoDTO {
	
	private byte[] excelByte;
	private String templateName;
	private String nomeFile;
	private BigDecimal idCampione;
	
	private String categAnag;
	private BigDecimal idUtenteIns;
	private String tipoDocumento;
	
	public byte[] getExcelByte() {
		return excelByte;
	}
	public void setExcelByte(byte[] excelByte) {
		this.excelByte = excelByte;
	}
	public String getTemplateName() {
		return templateName;
	}
	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}
	public String getNomeFile() {
		return nomeFile;
	}
	public void setNomeFile(String nomeFile) {
		this.nomeFile = nomeFile;
	}
	public BigDecimal getIdCampione() {
		return idCampione;
	}
	public void setIdCampione(BigDecimal idCampione) {
		this.idCampione = idCampione;
	}
	public String getCategAnag() {
		return categAnag;
	}
	public void setCategAnag(String categAnag) {
		this.categAnag = categAnag;
	}
	public BigDecimal getIdUtenteIns() {
		return idUtenteIns;
	}
	public void setIdUtenteIns(BigDecimal idUtenteIns) {
		this.idUtenteIns = idUtenteIns;
	}
	public String getTipoDocumento() {
		return tipoDocumento;
	}
	public void setTipoDocumento(String tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}
}
