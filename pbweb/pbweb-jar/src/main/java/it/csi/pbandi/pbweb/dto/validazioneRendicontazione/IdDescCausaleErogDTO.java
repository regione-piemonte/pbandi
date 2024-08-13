/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweb.dto.validazioneRendicontazione;

public class IdDescCausaleErogDTO implements java.io.Serializable {

	static final long serialVersionUID = 1;

	private int idCausaleErogazione;
	private String descCausaleErogazione;
	private String descBreveCausaleErogazione;

	public int getIdCausaleErogazione() {
		return idCausaleErogazione;
	}

	public void setIdCausaleErogazione(int idCausaleErogazione) {
		this.idCausaleErogazione = idCausaleErogazione;
	}

	public String getDescCausaleErogazione() {
		return descCausaleErogazione;
	}

	public void setDescCausaleErogazione(String descCausaleErogazione) {
		this.descCausaleErogazione = descCausaleErogazione;
	}

	public String getDescBreveCausaleErogazione() {
		return descBreveCausaleErogazione;
	}

	public void setDescBreveCausaleErogazione(String descBreveCausaleErogazione) {
		this.descBreveCausaleErogazione = descBreveCausaleErogazione;
	}

}
