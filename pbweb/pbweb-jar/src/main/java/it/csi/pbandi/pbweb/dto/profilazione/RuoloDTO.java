/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweb.dto.profilazione;

public class RuoloDTO implements java.io.Serializable {

	static final long serialVersionUID = 1;

	private String id = null;
	private String descrizione = null;
	private String descrizioneBreve = null;
	private String ruoloHelp = null;
	
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
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

	public String getRuoloHelp() {
		return ruoloHelp;
	}

	public void setRuoloHelp(String ruoloHelp) {
		this.ruoloHelp = ruoloHelp;
	}


	
}
