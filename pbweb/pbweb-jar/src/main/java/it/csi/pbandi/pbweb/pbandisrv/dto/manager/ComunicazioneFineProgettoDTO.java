/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweb.pbandisrv.dto.manager;

import java.math.BigDecimal;
import java.util.Date;

public class ComunicazioneFineProgettoDTO {
	
	private byte [] bytesModuloPdf;
	
	private String codiceProgetto;

	private BigDecimal idComunicazFineProg;

	private BigDecimal idProgetto;
	
	private String nomeFile;
	
	private String uid;
	
	private Date dtComunicazione;
	private BigDecimal idBeneficiario;
	private String cfBeneficiario;
	
	/**
	 * @return the idBeneficiario
	 */
	public BigDecimal getIdBeneficiario() {
		return idBeneficiario;
	}

	/**
	 * @param idBeneficiario the idBeneficiario to set
	 */
	public void setIdBeneficiario(BigDecimal idBeneficiario) {
		this.idBeneficiario = idBeneficiario;
	}

	/**
	 * @return the cfBeneficiario
	 */
	public String getCfBeneficiario() {
		return cfBeneficiario;
	}

	/**
	 * @param cfBeneficiario the cfBeneficiario to set
	 */
	public void setCfBeneficiario(String cfBeneficiario) {
		this.cfBeneficiario = cfBeneficiario;
	}

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

	public void setIdComunicazFineProg(BigDecimal idComunicazFineProg) {
		this.idComunicazFineProg = idComunicazFineProg;
	}

	public BigDecimal getIdComunicazFineProg() {
		return idComunicazFineProg;
	}

	public void setDtComunicazione(Date dtComunicazione) {
		this.dtComunicazione = dtComunicazione;
	}

	public Date getDtComunicazione() {
		return dtComunicazione;
	}

	

}
