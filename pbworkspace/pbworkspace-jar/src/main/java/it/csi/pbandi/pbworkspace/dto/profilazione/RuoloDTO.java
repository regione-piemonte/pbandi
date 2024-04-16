/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbworkspace.dto.profilazione;

public class RuoloDTO implements java.io.Serializable {

	static final long serialVersionUID = 1;

	private java.lang.String id = null;
	private java.lang.String descrizione = null;
	private java.lang.String descrizioneBreve = null;
	private java.lang.String ruoloHelp = null;

	
	
	
	
	public java.lang.String getId() {
		return id;
	}

	public void setId(java.lang.String id) {
		this.id = id;
	}

	public java.lang.String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(java.lang.String descrizione) {
		this.descrizione = descrizione;
	}

	public java.lang.String getDescrizioneBreve() {
		return descrizioneBreve;
	}

	public void setDescrizioneBreve(java.lang.String descrizioneBreve) {
		this.descrizioneBreve = descrizioneBreve;
	}

	public java.lang.String getRuoloHelp() {
		return ruoloHelp;
	}

	public void setRuoloHelp(java.lang.String ruoloHelp) {
		this.ruoloHelp = ruoloHelp;
	}


	
}
