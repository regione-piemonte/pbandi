/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweb.dto.validazioneRendicontazione;

public class InitIntegrazioneDTO implements java.io.Serializable {

	static final long serialVersionUID = 1;
	
	private Boolean esito;
	private String msg;

	private Long idIntegrazioneSpesa;
	private Long idStatoRichiesta;
	private String statoRichiesta;
	private String dataInvio;
	private String dataNotifica;

	private String dataRichiesta;

	public Long getIdIntegrazioneSpesa() {
		return idIntegrazioneSpesa;
	}

	public void setIdIntegrazioneSpesa(Long idIntegrazioneSpesa) {
		this.idIntegrazioneSpesa = idIntegrazioneSpesa;
	}

	public String getDataRichiesta() {
		return dataRichiesta;
	}

	public void setDataRichiesta(String dataRichiesta) {
		this.dataRichiesta = dataRichiesta;
	}

	public String getDataNotifica() {
		return dataNotifica;
	}

	public void setDataNotifica(String dataNotifica) {
		this.dataNotifica = dataNotifica;
	}

	public String getDataInvio() {
		return dataInvio;
	}
	public void setDataInvio(String dataInvio) {
		this.dataInvio = dataInvio;
	}
	public Boolean getEsito() {
		return esito;
	}
	public void setEsito(Boolean esito) {
		this.esito = esito;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public Long getIdStatoRichiesta() {
		return idStatoRichiesta;
	}
	public void setIdStatoRichiesta(Long idStatoRichiesta) {
		this.idStatoRichiesta = idStatoRichiesta;
	}
	public String getStatoRichiesta() {
		return statoRichiesta;
	}
	public void setStatoRichiesta(String statoRichiesta) {
		this.statoRichiesta = statoRichiesta;
	}
	
	

}
