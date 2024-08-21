/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.pbandisrv.dto.richiestaerogazione;

public class TipoAllegatoDTO implements java.io.Serializable {
	static final long serialVersionUID = 1;

	private Double idTipoAllegato;
	private String descTipoAllegato;
	public Double getIdTipoAllegato() {
		return idTipoAllegato;
	}
	public void setIdTipoAllegato(Double idTipoAllegato) {
		this.idTipoAllegato = idTipoAllegato;
	}
	public String getDescTipoAllegato() {
		return descTipoAllegato;
	}
	public void setDescTipoAllegato(String descTipoAllegato) {
		this.descTipoAllegato = descTipoAllegato;
	}
	
	

	
}
