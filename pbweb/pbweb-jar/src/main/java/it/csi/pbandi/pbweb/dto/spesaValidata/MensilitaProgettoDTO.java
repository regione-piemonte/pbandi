/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweb.dto.spesaValidata;

import java.io.Serializable;

public class MensilitaProgettoDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Long anno;
	private String mese;
	private Long idPrg;
	private String esito;
	private String sabbatico;
	private Long idDichSpesa;
	private Boolean erogato;
	private String note;
	
	public Long getAnno() {
		return anno;
	}
	public void setAnno(Long anno) {
		this.anno = anno;
	}
	public String getMese() {
		return mese;
	}
	public void setMese(String mese) {
		this.mese = mese;
	}
	public Long getIdPrg() {
		return idPrg;
	}
	public void setIdPrg(Long idPrg) {
		this.idPrg = idPrg;
	}
	public String getEsito() {
		return esito;
	}
	public void setEsito(String esito) {
		this.esito = esito;
	}
	public String getSabbatico() {
		return sabbatico;
	}
	public void setSabbatico(String sabbatico) {
		this.sabbatico = sabbatico;
	}
	public Long getIdDichSpesa() {
		return idDichSpesa;
	}
	public void setIdDichSpesa(Long idDichSpesa) {
		this.idDichSpesa = idDichSpesa;
	}
	public Boolean getErogato() {
		return erogato;
	}
	public void setErogato(Boolean erogato) {
		this.erogato = erogato;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	
}
