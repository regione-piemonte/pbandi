/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweberog.dto.erogazione;

public class EsitoErogazioneDTO implements java.io.Serializable {
	static final long serialVersionUID = 1;
	private String[] messages;
	private ErogazioneDTO erogazione;
	private Boolean esito;
	private Boolean isRegolaAttiva;
	
	public String[] getMessages() {
		return messages;
	}
	public void setMessages(String[] messages) {
		this.messages = messages;
	}
	public ErogazioneDTO getErogazione() {
		return erogazione;
	}
	public void setErogazione(ErogazioneDTO erogazione) {
		this.erogazione = erogazione;
	}
	public Boolean getEsito() {
		return esito;
	}
	public void setEsito(Boolean esito) {
		this.esito = esito;
	}
	public Boolean getIsRegolaAttiva() {
		return isRegolaAttiva;
	}
	public void setIsRegolaAttiva(Boolean isRegolaAttiva) {
		this.isRegolaAttiva = isRegolaAttiva;
	}
	public EsitoErogazioneDTO() {
		super();
	}
	
	


}
