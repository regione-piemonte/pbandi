/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweb.dto.validazioneRendicontazione;

public class IdDescModalitaAgevolazioneDTO implements java.io.Serializable {

	static final long serialVersionUID = 1;

	private int idModalitaAgevolazione;
	private String descModalitaAgevolazione;
	private String descBreveModalitaAgevolazione;

	public int getIdModalitaAgevolazione() {
		return idModalitaAgevolazione;
	}

	public void setIdModalitaAgevolazione(int idModalitaAgevolazione) {
		this.idModalitaAgevolazione = idModalitaAgevolazione;
	}

	public String getDescModalitaAgevolazione() {
		return descModalitaAgevolazione;
	}

	public void setDescModalitaAgevolazione(String descModalitaAgevolazione) {
		this.descModalitaAgevolazione = descModalitaAgevolazione;
	}

	public String getDescBreveModalitaAgevolazione() {
		return descBreveModalitaAgevolazione;
	}

	public void setDescBreveModalitaAgevolazione(String descBreveModalitaAgevolazione) {
		this.descBreveModalitaAgevolazione = descBreveModalitaAgevolazione;
	}

}
