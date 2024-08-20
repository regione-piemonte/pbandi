/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.integration.vo;

import java.sql.Date;

public class DatiAreaCreditiVO {
	private Long idStatoAzienda;
	private String idSogetto; 
	private Date dataFineVal; 
	private Date dataInizioVal; 
	private String descStatoAzienda; 
	private Date dataClassificazione;
	private Long idRating; 
	private String codiceRating; 
	private String descRating; 
	private Long idProvider; 
	private String descProvider;
	private String idSoggStatoAzienda; 
	private String idSoggettoRating; 
	
	
	
	
	
	
	public String getIdSoggettoRating() {
		return idSoggettoRating;
	}
	public void setIdSoggettoRating(String idSoggettoRating) {
		this.idSoggettoRating = idSoggettoRating;
	}
	public String getIdSoggStatoAzienda() {
		return idSoggStatoAzienda;
	}
	public void setIdSoggStatoAzienda(String idSoggStatoAzienda) {
		this.idSoggStatoAzienda = idSoggStatoAzienda;
	}
	public Long getIdStatoAzienda() {
		return idStatoAzienda;
	}
	public void setIdStatoAzienda(Long idStatoAzienda) {
		this.idStatoAzienda = idStatoAzienda;
	}
	public String getIdSogetto() {
		return idSogetto;
	}
	public void setIdSogetto(String idSogetto) {
		this.idSogetto = idSogetto;
	}
	public Date getDataFineVal() {
		return dataFineVal;
	}
	public void setDataFineVal(Date dataFineVal) {
		this.dataFineVal = dataFineVal;
	}
	public Date getDataInizioVal() {
		return dataInizioVal;
	}
	public void setDataInizioVal(Date dataInizioVal) {
		this.dataInizioVal = dataInizioVal;
	}
	
	public String getDescStatoAzienda() {
		return descStatoAzienda;
	}
	public void setDescStatoAzienda(String descStatoAzienda) {
		this.descStatoAzienda = descStatoAzienda;
	}
	public Date getDataClassificazione() {
		return dataClassificazione;
	}
	public void setDataClassificazione(Date dataClassificazione) {
		this.dataClassificazione = dataClassificazione;
	}
	public Long getIdRating() {
		return idRating;
	}
	public void setIdRating(Long idRating) {
		this.idRating = idRating;
	}
	public String getCodiceRating() {
		return codiceRating;
	}
	public void setCodiceRating(String codiceRating) {
		this.codiceRating = codiceRating;
	}
	public String getDescRating() {
		return descRating;
	}
	public void setDescRating(String descRating) {
		this.descRating = descRating;
	}
	public Long getIdProvider() {
		return idProvider;
	}
	public void setIdProvider(Long idProvider) {
		this.idProvider = idProvider;
	}
	public String getDescProvider() {
		return descProvider;
	}
	public void setDescProvider(String descProvider) {
		this.descProvider = descProvider;
	}
	
	
	
	
	
}
