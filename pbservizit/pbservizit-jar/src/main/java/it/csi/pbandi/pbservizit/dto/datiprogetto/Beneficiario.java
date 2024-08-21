/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.dto.datiprogetto;

import java.io.Serializable;

public class Beneficiario implements Serializable{
	
	private Long idBeneficiario;
	private String nome;
	private String cognome;
	private Integer idCombo;
	private String descrizione;
	private String codiceFiscale;
	private String sede;
	private Long idSoggetto;
	private Long idFormaGiuridica;
	private Long idDimensioneImpresa;
	private boolean isCapofila = false;
	private String sedeLegale;
	private String pIvaSedeLegale;
	
	
	
	public void setNome(String val) {
		nome = val;
	}

	public String getNome() {
		return nome;
	}	

	public void setCognome(String val) {
		cognome = val;
	}

	public String getCognome() {
		return cognome;
	}

	public void setIdCombo(Integer val) {
		idCombo = val;
	}

	public Integer getIdCombo() {
		return idCombo;
	}

	public void setDescrizione(String val) {
		descrizione = val;
	}

	public String getDescrizione() {
		return descrizione;
	}


	public void setCodiceFiscale(String val) {
		codiceFiscale = val;
	}

	public String getCodiceFiscale() {
		return codiceFiscale;
	}

	public void setSede(String val) {
		sede = val;
	}

	public String getSede() {
		return sede;
	}



	public void setIdSoggetto(Long val) {
		idSoggetto = val;
	}

	public Long getIdSoggetto() {
		return idSoggetto;
	}



	public void setIdBeneficiario(Long val) {
		idBeneficiario = val;
	}

	public Long getIdBeneficiario() {
		return idBeneficiario;
	}

	public void setIdFormaGiuridica(Long val) {
		idFormaGiuridica = val;
	}

	public Long getIdFormaGiuridica() {
		return idFormaGiuridica;
	}


	public void setIdDimensioneImpresa(Long val) {
		idDimensioneImpresa = val;
	}

	public Long getIdDimensioneImpresa() {
		return idDimensioneImpresa;
	}

	public void setIsCapofila(boolean val) {
		isCapofila = val;
	}

	public boolean getIsCapofila() {
		return isCapofila;
	}


	public void setSedeLegale(String val) {
		sedeLegale = val;
	}

	public String getSedeLegale() {
		return sedeLegale;
	}

	public void setPIvaSedeLegale(String val) {
		pIvaSedeLegale = val;
	}

	public String getPIvaSedeLegale() {
		return pIvaSedeLegale;
	}

	private String pIvaSedeIntervento;

	public void setPIvaSedeIntervento(String val) {
		pIvaSedeIntervento = val;
	}

	public String getPIvaSedeIntervento() {
		return pIvaSedeIntervento;
	}

	private static final long serialVersionUID = 1L;

	public Beneficiario() {
		super();

	}

	public String toString() {
		return super.toString();
	}
}
