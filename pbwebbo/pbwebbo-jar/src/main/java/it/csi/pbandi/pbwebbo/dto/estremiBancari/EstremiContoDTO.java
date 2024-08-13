/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebbo.dto.estremiBancari;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class EstremiContoDTO implements Serializable {
	
	private Long idEstremiBancari;
	private Integer moltiplicatore;
	private String tipologiaConto;
	private String iban;
	private boolean isSendToAmministrativoContabile;
	
	
	
	public EstremiContoDTO() {
		super();
		this.isSendToAmministrativoContabile = false;
	}
	
	
	
	public EstremiContoDTO(Long idEstremiBancari, Integer moltiplicatore, String tipologiaConto,String iban, boolean isSendToAmministrativoContabile) {
		
		this.idEstremiBancari = idEstremiBancari;
		this.moltiplicatore = moltiplicatore;
		this.tipologiaConto = tipologiaConto;
		this.iban = iban;
		this.isSendToAmministrativoContabile = isSendToAmministrativoContabile;
	}
	
	
public EstremiContoDTO(Long idEstremiBancari, Integer moltiplicatore, String tipologiaConto,String iban, Integer isSendToAmministrativoContabile) {
		
		this.idEstremiBancari = idEstremiBancari;
		this.moltiplicatore = moltiplicatore;
		this.tipologiaConto = tipologiaConto;
		this.iban = iban;
		this.isSendToAmministrativoContabile = isSendToAmministrativoContabile==1? true : false;
	}



	
	
	public Long getIdEstremiBancari() {
		return idEstremiBancari;
	}
	public void setIdEstremiBancari(Long idEstremiBancari) {
		this.idEstremiBancari = idEstremiBancari;
	}
	public Integer getMoltiplicatore() {
		return moltiplicatore;
	}
	public void setMoltiplicatore(Integer moltiplicatore) {
		this.moltiplicatore = moltiplicatore;
	}
	public String getTipologiaConto() {
		return tipologiaConto;
	}
	public void setTipologiaConto(String tipologiaConto) {
		this.tipologiaConto = tipologiaConto;
	}	
	public String getIban() {
		return iban;
	}
	public void setIban(String iban) {
		this.iban = iban;
	}	
	public boolean getIsSendToAmministrativoContabile() {
		return isSendToAmministrativoContabile;
	}
	public void setSendToAmministrativoContabile(boolean isSendToAmministrativoContabile) {
		this.isSendToAmministrativoContabile = isSendToAmministrativoContabile;
	}
	



	public static List<EstremiContoDTO>  getMockElement() {
		
		 List<EstremiContoDTO> list = new ArrayList<EstremiContoDTO>();
		 list.add(new EstremiContoDTO( 1L, 1, "str", "iban", false));
		 list.add(new EstremiContoDTO( 2L, 1, "str2","iban", false));
		 
		 return list;
		
	}
	
}
