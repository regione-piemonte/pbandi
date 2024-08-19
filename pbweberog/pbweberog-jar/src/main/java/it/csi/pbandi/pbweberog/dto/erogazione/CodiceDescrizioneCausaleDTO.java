/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweberog.dto.erogazione;

public class CodiceDescrizioneCausaleDTO {
	private String codice;
	private String descrizione;
	private Double percErogazione;
	private Double percLimite;
	
	
	public void setCodice(String val) {
		codice = val;
	}

	public String getCodice() {
		return codice;
	}

	

	public void setDescrizione(String val) {
		descrizione = val;
	}

	public String getDescrizione() {
		return descrizione;
	}

	
	
	
	public Double getPercErogazione() {
		return percErogazione;
	}

	public void setPercErogazione(Double percErogazione) {
		this.percErogazione = percErogazione;
	}

	public Double getPercLimite() {
		return percLimite;
	}

	public void setPercLimite(Double percLimite) {
		this.percLimite = percLimite;
	}




	private static final long serialVersionUID = 1L;

	public CodiceDescrizioneCausaleDTO() {
		super();

	}

	@Override
	public String toString() {
		return "CodiceDescrizioneCausaleDTO{" +
				"codice='" + codice + '\'' +
				", descrizione='" + descrizione + '\'' +
				", percErogazione=" + percErogazione +
				", percLimite=" + percLimite +
				'}';
	}
}
