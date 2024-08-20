/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebrce.dto.profilazione;

public class DecodificaDTO implements java.io.Serializable {

	static final long serialVersionUID = 1;
	
	private Long id = null;

	private String descrizione = null;

	private String descrizioneBreve = null;

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


	@Override
	public String toString() {
		return "DecodificaDTO [id=" + id + ", descrizione=" + descrizione + ", descrizioneBreve=" + descrizioneBreve + "]";
	}
	
}
