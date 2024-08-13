/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweb.dto.profilazione;

public class DecodificaDTO implements java.io.Serializable {

	static final long serialVersionUID = 1;
	
	private Long id = null;

	private String descrizione = null;

	private String descrizioneBreve = null;
	
	private boolean regolaAttiva = true;

	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getDescrizione() {
		return descrizione;
	}


	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}


	public String getDescrizioneBreve() {
		return descrizioneBreve;
	}


	public void setDescrizioneBreve(String descrizioneBreve) {
		this.descrizioneBreve = descrizioneBreve;
	}
	
	

	public boolean getRegolaAttiva() {
		return regolaAttiva;
	}


	public void setRegolaAttiva(boolean isRegola) {
		this.regolaAttiva = isRegola;
	}


	@Override
	public String toString() {
		return "DecodificaDTO [id=" + id + ", descrizione=" + descrizione + ", descrizioneBreve=" + descrizioneBreve + ", regola=" + regolaAttiva + "]";
	}
	
}
