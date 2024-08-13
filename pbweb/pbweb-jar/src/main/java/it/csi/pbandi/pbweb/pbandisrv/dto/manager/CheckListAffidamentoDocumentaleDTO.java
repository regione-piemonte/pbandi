/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweb.pbandisrv.dto.manager;

import java.math.BigDecimal;
import java.util.Date;

public class CheckListAffidamentoDocumentaleDTO {
	
	private byte [] bytesModuloPdf;
	
	private String codiceProgetto;

	private BigDecimal idAppalto;

	private BigDecimal idProgetto;
	
	private BigDecimal versione;

	private String nomeFile;
	
	private String uid;
	
	private Date dataChiusura;
	
	
	public void setBytesModuloPdf(byte [] bytesModuloPdf) {
		this.bytesModuloPdf = bytesModuloPdf;
	}

	public byte [] getBytesModuloPdf() {
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


	public BigDecimal getIdAppalto() {
		return idAppalto;
	}

	public void setIdAppalto(BigDecimal idAppalto) {
		this.idAppalto = idAppalto;
	}
	
	public BigDecimal getIdAffidamento(){
		return idAppalto;
	}
	
	public void setIdAffidamento(BigDecimal idAffidamento){
		this.idAppalto = idAffidamento;
	}

	public void setCodiceProgetto(String codiceProgetto) {
		this.codiceProgetto = codiceProgetto;
	}

	public String getCodiceProgetto() {
		return codiceProgetto;
	}

	public void setDataChiusura(Date dataChiusura) {
		this.dataChiusura = dataChiusura;
	}

	public Date getDataChiusura() {
		return dataChiusura;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getUid() {
		return uid;
	}

	public void setVersione(BigDecimal versione) {
		this.versione = versione;
	}

	public BigDecimal getVersione() {
		return versione;
	}

}
