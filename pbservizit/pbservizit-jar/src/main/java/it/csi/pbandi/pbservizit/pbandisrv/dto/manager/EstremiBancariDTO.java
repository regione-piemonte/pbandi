/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.pbandisrv.dto.manager;

public class EstremiBancariDTO implements java.io.Serializable {

	private static final long serialVersionUID = 3L;

	private String abi = null;
	private String cab = null;
	private String numeroConto = null;
	private String iban = null;
	private String descrizioneBanca = null;
	private String descrizioneAgenzia = null;

	public String getAbi() {
		return abi;
	}

	public void setAbi(String val) {
		abi = val;
	}

	public String getCab() {
		return cab;
	}

	public void setCab(String val) {
		cab = val;
	}

	public String getNumeroConto() {
		return numeroConto;
	}

	public void setNumeroConto(String val) {
		numeroConto = val;
	}

	public String getIban() {
		return iban;
	}

	public void setIban(String val) {
		iban = val;
	}

	public String getDescrizioneBanca() {
		return descrizioneBanca;
	}

	public void setDescrizioneBanca(String val) {
		descrizioneBanca = val;
	}

	public String getDescrizioneAgenzia() {
		return descrizioneAgenzia;
	}

	public void setDescrizioneAgenzia(String val) {
		descrizioneAgenzia = val;
	}

	@Override
	public String toString() {
		return "EstremiBancariDTO{" +
				"abi='" + abi + '\'' +
				", cab='" + cab + '\'' +
				", numeroConto='" + numeroConto + '\'' +
				", iban='" + iban + '\'' +
				", descrizioneBanca='" + descrizioneBanca + '\'' +
				", descrizioneAgenzia='" + descrizioneAgenzia + '\'' +
				'}';
	}
}