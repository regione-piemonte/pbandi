/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebbo.dto.estremiBancari;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class EstremiBancariEstesiDTO implements Serializable {
	
	private Long idBando;
	private Long idMonitAmmvoCont;
	private Long idModalitaAgevolazione;
	private Long idEstremiBancari;
	private Integer moltiplicatore;
	private String tipologiaConto;
	private Long idBanca;
	private String descBanca;
	private String iban;
	private int sendToAmmCont;
	
	
	
	public EstremiBancariEstesiDTO() {
		super();
	}
	
	
	
	public EstremiBancariEstesiDTO(Long idModalitaAgevolazione, Long idEstremiBancari, int moltiplicatore, String tipologiaConto,
			Long idBanca, String descBanca,  String iban, int sendToAmmCont) {
		
		this.idModalitaAgevolazione = idModalitaAgevolazione;
		this.idEstremiBancari = idEstremiBancari;
		this.moltiplicatore = moltiplicatore;
		this.tipologiaConto = tipologiaConto;
		this.idBanca = idBanca;
		this.descBanca = descBanca;
		this.iban = iban;
		this.sendToAmmCont = sendToAmmCont;
	}



	
	
	public Long getIdBando() {
		return idBando;
	}
	public void setIdBando(Long idBando) {
		this.idBando = idBando;
	}
	public Long getIdModalitaAgevolazione() {
		return idModalitaAgevolazione;
	}
	public void setIdModalitaAgevolazione(Long idModalitaAgevolazione) {
		this.idModalitaAgevolazione = idModalitaAgevolazione;
	}
	public Long getIdEstremiBancari() {
		return idEstremiBancari;
	}
	public void setIdEstremiBancari(Long idEstremiBancari) {
		this.idEstremiBancari = idEstremiBancari;
	}
	public int getMoltiplicatore() {
		return moltiplicatore;
	}
	public void setMoltiplicatore(int moltiplicatore) {
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
	public String getDescBanca() {
		return descBanca;
	}
	public void setDescBanca(String descBanca) {
		this.descBanca = descBanca;
	}
	public String getIban() {
		return iban;
	}
	public void setIban(String iban) {
		this.iban = iban;
	}
	public int getSendToAmmCont() {
		return sendToAmmCont;
	}
	public void setSendToAmmCont(int sendToAmmCont) {
		this.sendToAmmCont = sendToAmmCont;
	}
	public Long getIdMonitAmmvoCont() {
		return idMonitAmmvoCont;
	}
	public void setIdMonitAmmvoCont(Long idMonitAmmvoCont) {
		this.idMonitAmmvoCont = idMonitAmmvoCont;
	}



	public static List<EstremiBancariEstesiDTO>  getMockElement() {
		
		 List<EstremiBancariEstesiDTO> list = new ArrayList<EstremiBancariEstesiDTO>();
		 list.add(new EstremiBancariEstesiDTO(1L, 1L, 1, "str", 1L, "Banca", "iban", 0));
		 list.add(new EstremiBancariEstesiDTO(1L, 2L, 1, "str2", 2L, "Banca","iban", 0));
		 
		 return list;
		
	}



	@Override
	public String toString() {
		return "EstremiBancariEstesiDTO [idBando=" + idBando + ", idMonitAmmvoCont=" + idMonitAmmvoCont
				+ ", idModalitaAgevolazione=" + idModalitaAgevolazione + ", idEstremiBancari=" + idEstremiBancari
				+ ", moltiplicatore=" + moltiplicatore + ", tipologiaConto=" + tipologiaConto + ", idBanca=" + idBanca
				+ ", descBanca=" + descBanca + ", iban=" + iban + ", sendToAmmCont=" + sendToAmmCont + "]";
	}
	
	
	
	
}
