/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservrest.model;

public class IstruttoreAsfTmp {

	private Integer idIstruttore;
	private String nomeIstrASF;
	private String cognomeIstrASF;
	private String codicefiscaleIstrASF;
	
	public IstruttoreAsfTmp() {
		// TODO Auto-generated constructor stub
	}

	public IstruttoreAsfTmp(Integer idIstruttore, String nomeIstrASF, String cognomeIstrASF,
			String codicefiscaleIstrASF) {
		super();
		this.idIstruttore = idIstruttore;
		this.nomeIstrASF = nomeIstrASF;
		this.cognomeIstrASF = cognomeIstrASF;
		this.codicefiscaleIstrASF = codicefiscaleIstrASF;
	}

	public Integer getIdIstruttore() {
		return idIstruttore;
	}

	public void setIdIstruttore(Integer idIstruttore) {
		this.idIstruttore = idIstruttore;
	}

	public String getNomeIstrASF() {
		return nomeIstrASF;
	}

	public void setNomeIstrASF(String nomeIstrASF) {
		this.nomeIstrASF = nomeIstrASF;
	}

	public String getCognomeIstrASF() {
		return cognomeIstrASF;
	}

	public void setCognomeIstrASF(String cognomeIstrASF) {
		this.cognomeIstrASF = cognomeIstrASF;
	}

	public String getCodicefiscaleIstrASF() {
		return codicefiscaleIstrASF;
	}

	public void setCodicefiscaleIstrASF(String codicefiscaleIstrASF) {
		this.codicefiscaleIstrASF = codicefiscaleIstrASF;
	}
	
}
