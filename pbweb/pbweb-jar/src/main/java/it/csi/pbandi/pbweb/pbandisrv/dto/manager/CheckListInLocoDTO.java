/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweb.pbandisrv.dto.manager;

import java.math.BigDecimal;
import java.util.Date;

public class CheckListInLocoDTO {

	private byte[] bytesModuloPdf;

	private String codiceProgetto;

	private BigDecimal idProgetto;

	private BigDecimal idChecklist;

	private String nomeFile;

	private String uid;

	private Date dataInserimento;

	public void setBytesModuloPdf(byte[] bytesModuloPdf) {
		this.bytesModuloPdf = bytesModuloPdf;
	}

	public byte[] getBytesModuloPdf() {
		return bytesModuloPdf;
	}

	public void setNomeFile(String nomeFile) {
		this.nomeFile = nomeFile;
	}

	public String getNomeFile() {
		return nomeFile;
	}

	public void setIdProgetto(BigDecimal idProgetto) {
		this.idProgetto = idProgetto;
	}

	public BigDecimal getIdProgetto() {
		return idProgetto;
	}

	public void setCodiceProgetto(String codiceProgetto) {
		this.codiceProgetto = codiceProgetto;
	}

	public String getCodiceProgetto() {
		return codiceProgetto;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getUid() {
		return uid;
	}

	public void setDataInserimento(Date dataInserimento) {
		this.dataInserimento = dataInserimento;
	}

	public Date getDataInserimento() {
		return dataInserimento;
	}

	public void setIdChecklist(BigDecimal idChecklist) {
		this.idChecklist = idChecklist;
	}

	public BigDecimal getIdChecklist() {
		return idChecklist;
	}

}
