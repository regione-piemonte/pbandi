/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweberog.dto.datiprogetto;


public class Comune implements java.io.Serializable {

	private Long idComune;
	private String codIstatComune;
	private String descrizione;
	private static final long serialVersionUID = 1L;
	
	


	public Long getIdComune() {
		return idComune;
	}




	public void setIdComune(Long idComune) {
		this.idComune = idComune;
	}




	public String getCodIstatComune() {
		return codIstatComune;
	}




	public void setCodIstatComune(String codIstatComune) {
		this.codIstatComune = codIstatComune;
	}




	public String getDescrizione() {
		return descrizione;
	}




	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}




	public Comune() {
		super();

	}

}
