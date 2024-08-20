/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebrce.integration.request;

public class SalvaVarianteRequest {
	private Long idTipologiaVariante;
	private Double importoVariante;
	private String noteVariante;
	private Long idVariante;
	private Long idAppalto;
	public Long getIdTipologiaVariante() {
		return idTipologiaVariante;
	}
	public void setIdTipologiaVariante(Long idTipologiaVariante) {
		this.idTipologiaVariante = idTipologiaVariante;
	}
	public Double getImportoVariante() {
		return importoVariante;
	}
	public void setImportoVariante(Double importoVariante) {
		this.importoVariante = importoVariante;
	}
	public String getNoteVariante() {
		return noteVariante;
	}
	public void setNoteVariante(String noteVariante) {
		this.noteVariante = noteVariante;
	}
	public Long getIdVariante() {
		return idVariante;
	}
	public void setIdVariante(Long idVariante) {
		this.idVariante = idVariante;
	}
	public Long getIdAppalto() {
		return idAppalto;
	}
	public void setIdAppalto(Long idAppalto) {
		this.idAppalto = idAppalto;
	}
	
	
	
	
}
