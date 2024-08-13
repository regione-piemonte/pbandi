/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo;

import java.math.BigDecimal;
import java.sql.Date;

public class InfoFileVO extends GenericVO {
	private String codiceProgetto;
	private String descBandoLinea;
	private Date  dtAggiornamento ;
	private Date  dtInserimento ;
	private BigDecimal idDocumentoIndex;
	private BigDecimal idEntita;
	private BigDecimal idFolder;
	private BigDecimal idSoggettoBen;
	private BigDecimal idTarget;
	private String nomeFile;
	private BigDecimal sizeFile;
	private String titoloProgetto;

 
	public Date getDtAggiornamento() {
		return dtAggiornamento;
	}
	public void setDtAggiornamento(Date dtAggiornamento) {
		this.dtAggiornamento = dtAggiornamento;
	}
	public Date getDtInserimento() {
		return dtInserimento;
	}
	public void setDtInserimento(Date dtInserimento) {
		this.dtInserimento = dtInserimento;
	}
	 
	public BigDecimal getIdDocumentoIndex() {
		return idDocumentoIndex;
	}
	public void setIdDocumentoIndex(BigDecimal idDocumentoIndex) {
		this.idDocumentoIndex = idDocumentoIndex;
	}
 
	public BigDecimal getIdEntita() {
		return idEntita;
	}
	public void setIdEntita(BigDecimal idEntita) {
		this.idEntita = idEntita;
	}
	public BigDecimal getIdFolder() {
		return idFolder;
	}
	public void setIdFolder(BigDecimal idFolder) {
		this.idFolder = idFolder;
	}
	 
	public BigDecimal getIdSoggettoBen() {
		return idSoggettoBen;
	}
	public void setIdSoggettoBen(BigDecimal idSoggettoBen) {
		this.idSoggettoBen = idSoggettoBen;
	}
	 
	public BigDecimal getIdTarget() {
		return idTarget;
	}
	public void setIdTarget(BigDecimal idTarget) {
		this.idTarget = idTarget;
	}
	public String getNomeFile() {
		return nomeFile;
	}
	public void setNomeFile(String nomeFile) {
		this.nomeFile = nomeFile;
	}
	public BigDecimal getSizeFile() {
		return sizeFile;
	}
	public void setSizeFile(BigDecimal sizeFile) {
		this.sizeFile = sizeFile;
	}
	public String getCodiceProgetto() {
		return codiceProgetto;
	}
	public void setCodiceProgetto(String codiceProgetto) {
		this.codiceProgetto = codiceProgetto;
	}
	public String getDescBandoLinea() {
		return descBandoLinea;
	}
	public void setDescBandoLinea(String descBandoLinea) {
		this.descBandoLinea = descBandoLinea;
	}
	public String getTitoloProgetto() {
		return titoloProgetto;
	}
	public void setTitoloProgetto(String titoloProgetto) {
		this.titoloProgetto = titoloProgetto;
	}
}
