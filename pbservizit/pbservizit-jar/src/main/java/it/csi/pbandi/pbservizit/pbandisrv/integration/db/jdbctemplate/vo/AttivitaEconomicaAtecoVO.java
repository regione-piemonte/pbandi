/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo;

import java.math.BigDecimal;


public class AttivitaEconomicaAtecoVO extends GenericVO {
	
	private String codAteco;
	private String codDescAteco;
	private String codIgrueT6;
	private String codSettore;
	private String descAteco;
	private String descAttivitaEconomica;
	private String descSettore;
	private BigDecimal idAttivitaAteco;
	private BigDecimal idAttivitaEconomica;
  	private BigDecimal idSettoreAttivita;
  	private BigDecimal livello;
  	
	public String getCodAteco() {
		return codAteco;
	}
	 
	public String getCodIgrueT6() {
		return codIgrueT6;
	}
	public String getCodSettore() {
		return codSettore;
	}
	public String getDescAteco() {
		return descAteco;
	}
	public String getDescAttivitaEconomica() {
		return descAttivitaEconomica;
	}
	public String getDescSettore() {
		return descSettore;
	}
	public BigDecimal getIdAttivitaAteco() {
		return idAttivitaAteco;
	}
	public BigDecimal getIdAttivitaEconomica() {
		return idAttivitaEconomica;
	}
	public BigDecimal getIdSettoreAttivita() {
		return idSettoreAttivita;
	}
	public BigDecimal getLivello() {
		return livello;
	}
	public void setCodAteco(String codAteco) {
		this.codAteco = codAteco;
	}
	public void setCodIgrueT6(String codIgrueT6) {
		this.codIgrueT6 = codIgrueT6;
	}
	public void setCodSettore(String codSettore) {
		this.codSettore = codSettore;
	}
	public void setDescAteco(String descAteco) {
		this.descAteco = descAteco;
	}
	public void setDescAttivitaEconomica(String descAttivitaEconomica) {
		this.descAttivitaEconomica = descAttivitaEconomica;
	}
	public void setDescSettore(String descSettore) {
		this.descSettore = descSettore;
	}
	public void setIdAttivitaAteco(BigDecimal idAttivitaAteco) {
		this.idAttivitaAteco = idAttivitaAteco;
	}
	public void setIdAttivitaEconomica(BigDecimal idAttivitaEconomica) {
		this.idAttivitaEconomica = idAttivitaEconomica;
	}
	public void setIdSettoreAttivita(BigDecimal idSettoreAttivita) {
		this.idSettoreAttivita = idSettoreAttivita;
	}
	public void setLivello(BigDecimal livello) {
		this.livello = livello;
	}

	public String getCodDescAteco() {
		return codDescAteco;
	}

	public void setCodDescAteco(String codDescAteco) {
		this.codDescAteco = codDescAteco;
	}
 
  	
}
