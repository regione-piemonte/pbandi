/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebbo.dto.estremiBancari;

/*
 * contiene informazioni relative al bando e all’ente di competenza
 */
public class IbanDTO {
	
	private Long idEstremiBancari;
	private String moltiplicatore; 
	private String tipologiaConto;
	private Long idBanca;
	private String iban;
	
	
	
	public Long getIdEstremiBancari() {
		return idEstremiBancari;
	}
	public void setIdEstremiBancari(Long idEstremiBancari) {
		this.idEstremiBancari = idEstremiBancari;
	}
	public String getMoltiplicatore() {
		return moltiplicatore;
	}
	public void setMoltiplicatore(String moltiplicatore) {
		this.moltiplicatore = moltiplicatore;
	}
	public String getTipologiaConto() {
		return tipologiaConto;
	}
	public void setTipologiaConto(String tipologiaConto) {
		this.tipologiaConto = tipologiaConto;
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
	
	
	@Override
	public String toString() {
		return "IbanDTO [idEstremiBancari=" + idEstremiBancari + ", moltiplicatore=" + moltiplicatore
				+ ", tipologiaConto=" + tipologiaConto + ", idBanca=" + idBanca + ", iban=" + iban + "]";
	}
	
	
	
	
	

}
