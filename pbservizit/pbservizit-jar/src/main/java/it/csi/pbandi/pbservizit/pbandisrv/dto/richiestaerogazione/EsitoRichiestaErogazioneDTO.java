/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.pbandisrv.dto.richiestaerogazione;


public class EsitoRichiestaErogazioneDTO implements java.io.Serializable {

	static final long serialVersionUID = 1;

	private String[] messages;
	private it.csi.pbandi.pbservizit.pbandisrv.dto.richiestaerogazione.RichiestaErogazioneDTO richiestaErogazione;
	private Boolean esito;
	private Boolean isRegolaAttiva;
	private Long idDocIndex;
	
	public String[] getMessages() {
		return messages;
	}
	public void setMessages(String[] messages) {
		this.messages = messages;
	}
	public it.csi.pbandi.pbservizit.pbandisrv.dto.richiestaerogazione.RichiestaErogazioneDTO getRichiestaErogazione() {
		return richiestaErogazione;
	}
	public void setRichiestaErogazione(
			it.csi.pbandi.pbservizit.pbandisrv.dto.richiestaerogazione.RichiestaErogazioneDTO richiestaErogazione) {
		this.richiestaErogazione = richiestaErogazione;
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
	public Long getIdDocIndex() {
		return idDocIndex;
	}
	public void setIdDocIndex(Long idDocIndex) {
		this.idDocIndex = idDocIndex;
	}
	
	
}
