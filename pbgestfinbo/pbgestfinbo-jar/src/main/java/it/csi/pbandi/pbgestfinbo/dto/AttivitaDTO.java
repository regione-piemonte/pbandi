/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.dto;

import java.math.BigDecimal;
import java.sql.Date;

public class AttivitaDTO {
	
	private Long idAttivita;
	private String descAttivita;
	private String nome; 
	private String cognome; 
	private Long idSoggetto; 
	private Long idSoggProg; 
	private Long progBandoLinea; 
	private Date dataInserimento;
	private boolean selected; 
	private BigDecimal idDocumentoIndex; 
	private BigDecimal idTipodocumentoIndex; 
	private BigDecimal idProgetto; 
	private BigDecimal idDomanda; 
	
	
	
	public BigDecimal getIdProgetto() {
		return idProgetto;
	}
	public void setIdProgetto(BigDecimal idProgetto) {
		this.idProgetto = idProgetto;
	}
	public BigDecimal getIdDomanda() {
		return idDomanda;
	}
	public void setIdDomanda(BigDecimal idDomanda) {
		this.idDomanda = idDomanda;
	}
	public BigDecimal getIdDocumentoIndex() {
		return idDocumentoIndex;
	}
	public void setIdDocumentoIndex(BigDecimal idDocumentoIndex) {
		this.idDocumentoIndex = idDocumentoIndex;
	}
	public BigDecimal getIdTipodocumentoIndex() {
		return idTipodocumentoIndex;
	}
	public void setIdTipodocumentoIndex(BigDecimal idTipodocumentoIndex) {
		this.idTipodocumentoIndex = idTipodocumentoIndex;
	}
	public boolean isSelected() {
		return selected;
	}
	public void setSelected(boolean selected) {
		this.selected = selected;
	}
	public void setDataInserimento(Date dataInserimento) {
		this.dataInserimento = dataInserimento;
	}
	public Date getDataInserimento() {
		return dataInserimento;
	}
	
	public Long getProgBandoLinea() {
		return progBandoLinea;
	}
	public void setProgBandoLinea(Long progBandoLinea) {
		this.progBandoLinea = progBandoLinea;
	}
	
	public Long getIdSoggProg() {
		return idSoggProg;
	}
	public void setIdSoggProg(Long idSoggPRog) {
		this.idSoggProg = idSoggPRog;
	}
	public Long getIdSoggetto() {
		return idSoggetto;
	}
	public void setIdSoggetto(Long idSoggetto) {
		this.idSoggetto = idSoggetto;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getCognome() {
		return cognome;
	}
	public void setCognome(String cognome) {
		this.cognome = cognome;
	}
	public Long getIdAttivita() {
		return idAttivita;
	}
	public void setIdAttivita(Long idAttivita) {
		this.idAttivita = idAttivita;
	}
	public String getDescAttivita() {
		return descAttivita;
	}   
	public void setDescAttivita(String descAttivita) {
		this.descAttivita = descAttivita;
	} 
	

}
