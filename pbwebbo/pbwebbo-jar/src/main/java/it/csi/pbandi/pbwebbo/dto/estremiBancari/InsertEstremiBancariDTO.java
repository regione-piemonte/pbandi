/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebbo.dto.estremiBancari;

import java.util.Date;

public class InsertEstremiBancariDTO {
	
	private Long idBanca; 
	private String iban;
	private Long idModalitaAgevolazione;
	private Long idBando; 
	private Long idEstremiBancari; 
	private String moltiplicatore; 
	//private Date dtInizioValidita; 
	private String tipologiaConto;
	
	
	
	
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
	public Long getIdModalitaAgevolazione() {
		return idModalitaAgevolazione;
	}
	public void setIdModalitaAgevolazione(Long idModalitaAgevolazione) {
		this.idModalitaAgevolazione = idModalitaAgevolazione;
	}
	public Long getIdBando() {
		return idBando;
	}
	public void setIdBando(Long idBando) {
		this.idBando = idBando;
	}
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
	/*
	public Date getDtInizioValidita() {
		return dtInizioValidita;
	}
	public void setDtInizioValidita(Date dtInizioValidita) {
		this.dtInizioValidita = dtInizioValidita;
	}*/
	public String getTipologiaConto() {
		return tipologiaConto;
	}
	public void setTipologiaConto(String tipologiaConto) {
		this.tipologiaConto = tipologiaConto;
	}
	
	

}
