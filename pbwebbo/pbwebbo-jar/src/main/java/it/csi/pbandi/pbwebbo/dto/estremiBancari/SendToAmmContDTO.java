/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebbo.dto.estremiBancari;

/*
 * contiene informazioni relative al bando e all’ente di competenza
 */
public class SendToAmmContDTO {
	
	private Long idBando;
	private Long idModalitaAgevolazione;
	private Long idBanca;
	private Long idEstremiBancari;
	private String iban;
	private Integer sendToAmmCont;
	
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
	public Long getIdBanca() {
		return idBanca;
	}
	public void setIdBanca(Long idBanca) {
		this.idBanca = idBanca;
	}
	public Long getIdEstremiBancari() {
		return idEstremiBancari;
	}
	public void setIdEstremiBancari(Long idEstremiBancari) {
		this.idEstremiBancari = idEstremiBancari;
	}
	public String getIban() {
		return iban;
	}
	public void setIban(String iban) {
		this.iban = iban;
	}
	
	public Integer getSendToAmmCont() {
		return sendToAmmCont;
	}
	public void setSendToAmmCont(Integer sendToAmmCont) {
		this.sendToAmmCont = sendToAmmCont;
	}
	public boolean isSendToAmCont() {
		
		if(sendToAmmCont != null && sendToAmmCont == 1)
			return true;
		
		return false;
	}
	
	
	
	
	
		
	
	
	
	

}
