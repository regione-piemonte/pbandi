/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebcert.dto.certificazione;

import java.util.Date;

public class BeneficiarioDTO implements java.io.Serializable {

	static final long serialVersionUID = 1;

	private Long idSoggetto;
	private String denominazioneBeneficiario;
	private String cognome;
	private String descrizione;
	private Date dataInizioValidita;

	private String codiceFiscale;

	public void setCodiceFiscale(String val) {
		codiceFiscale = val;
	}

	public String getCodiceFiscale() {
		return codiceFiscale;
	}

	

	public void setCognome(String val) {
		cognome = val;
	}

	public String getCognome() {
		return cognome;
	}

	private String nome;

	public void setNome(String val) {
		nome = val;
	}

	public String getNome() {
		return nome;
	}

	
	public void setDescrizione(String val) {
		descrizione = val;
	}

	public String getDescrizione() {
		return descrizione;
	}

	

	public void setDataInizioValidita(java.util.Date val) {
		dataInizioValidita = val;
	}

	public java.util.Date getDataInizioValidita() {
		return dataInizioValidita;
	}

	public String getDenominazioneBeneficiario() {
		return denominazioneBeneficiario;
	}

	public void setDenominazioneBeneficiario(String denominazioneBeneficiario) {
		this.denominazioneBeneficiario = denominazioneBeneficiario;
	}

	public Long getIdSoggetto() {
		return idSoggetto;
	}

	public void setIdSoggetto(Long idSoggetto) {
		this.idSoggetto = idSoggetto;
	}
}
