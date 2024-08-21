/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.pbandisrv.dto.richiestaerogazione;

public class EstremiBancariDTO implements java.io.Serializable {

	static final long serialVersionUID = 1;
	private String abi;
	private String cab;
	private String numeroConto;
	private String iban;
	private String descrizioneBanca;
	private String descrizioneAgenzia;
	public String getAbi() {
		return abi;
	}
	public void setAbi(String abi) {
		this.abi = abi;
	}
	public String getCab() {
		return cab;
	}
	public void setCab(String cab) {
		this.cab = cab;
	}
	public String getNumeroConto() {
		return numeroConto;
	}
	public void setNumeroConto(String numeroConto) {
		this.numeroConto = numeroConto;
	}
	public String getIban() {
		return iban;
	}
	public void setIban(String iban) {
		this.iban = iban;
	}
	public String getDescrizioneBanca() {
		return descrizioneBanca;
	}
	public void setDescrizioneBanca(String descrizioneBanca) {
		this.descrizioneBanca = descrizioneBanca;
	}
	public String getDescrizioneAgenzia() {
		return descrizioneAgenzia;
	}
	public void setDescrizioneAgenzia(String descrizioneAgenzia) {
		this.descrizioneAgenzia = descrizioneAgenzia;
	}
	
	


}
