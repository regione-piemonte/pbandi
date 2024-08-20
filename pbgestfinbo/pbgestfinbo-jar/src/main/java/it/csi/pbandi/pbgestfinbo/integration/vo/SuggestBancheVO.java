/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.integration.vo;


public class SuggestBancheVO {

	private Long idBanca;
	private String iban;
	private String banca;
	
	
	
	public SuggestBancheVO(Long idBanca, String iban, String banca) {
		this.idBanca = idBanca;
		this.iban = iban;
		this.banca = banca;
	}



	public SuggestBancheVO() {
	}



	public Long getIdBanca() {
		return idBanca;
	}



	public void setIdBanca(Long idBanca) {
		this.idBanca = idBanca;
	}



	public String getIban() {
		return iban;
	}



	public void setIban(String iban) {
		this.iban = iban;
	}



	public String getBanca() {
		return banca;
	}



	public void setBanca(String banca) {
		this.banca = banca;
	}



	@Override
	public String toString() {
		return "SuggestBancheVO [idBanca=" + idBanca + ", iban=" + iban + ", banca=" + banca + "]";
	}
	
}
	