/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweberog.dto.disimpegni;

import java.io.Serializable;
import java.util.Date;

public class DettaglioRecuperoDTO implements Serializable {
	static final long serialVersionUID = 1;
	private Long idTipoRecupero;
	private Long idProgetto;
	private Long idModalitaAgevolazione;
	private Double importoRecupero;
	private Date dtRecupero;
	private String codRiferimentoRecupero;
	private String descTipoRecupero;
	private String estremiRecupero;
	private String note;
	private String descModalitaAgevolazione;
	private String codiceVisualizzato;
	private String descBreveTipoRecupero;
	private Long idPeriodo;
	public Long getIdTipoRecupero() {
		return idTipoRecupero;
	}
	public void setIdTipoRecupero(Long idTipoRecupero) {
		this.idTipoRecupero = idTipoRecupero;
	}
	public Long getIdProgetto() {
		return idProgetto;
	}
	public void setIdProgetto(Long idProgetto) {
		this.idProgetto = idProgetto;
	}
	public Long getIdModalitaAgevolazione() {
		return idModalitaAgevolazione;
	}
	public void setIdModalitaAgevolazione(Long idModalitaAgevolazione) {
		this.idModalitaAgevolazione = idModalitaAgevolazione;
	}
	public Double getImportoRecupero() {
		return importoRecupero;
	}
	public void setImportoRecupero(Double importoRecupero) {
		this.importoRecupero = importoRecupero;
	}
	public Date getDtRecupero() {
		return dtRecupero;
	}
	public void setDtRecupero(Date dtRecupero) {
		this.dtRecupero = dtRecupero;
	}
	public String getCodRiferimentoRecupero() {
		return codRiferimentoRecupero;
	}
	public void setCodRiferimentoRecupero(String codRiferimentoRecupero) {
		this.codRiferimentoRecupero = codRiferimentoRecupero;
	}
	public String getDescTipoRecupero() {
		return descTipoRecupero;
	}
	public void setDescTipoRecupero(String descTipoRecupero) {
		this.descTipoRecupero = descTipoRecupero;
	}
	public String getEstremiRecupero() {
		return estremiRecupero;
	}
	public void setEstremiRecupero(String estremiRecupero) {
		this.estremiRecupero = estremiRecupero;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public String getDescModalitaAgevolazione() {
		return descModalitaAgevolazione;
	}
	public void setDescModalitaAgevolazione(String descModalitaAgevolazione) {
		this.descModalitaAgevolazione = descModalitaAgevolazione;
	}
	public String getCodiceVisualizzato() {
		return codiceVisualizzato;
	}
	public void setCodiceVisualizzato(String codiceVisualizzato) {
		this.codiceVisualizzato = codiceVisualizzato;
	}
	public String getDescBreveTipoRecupero() {
		return descBreveTipoRecupero;
	}
	public void setDescBreveTipoRecupero(String descBreveTipoRecupero) {
		this.descBreveTipoRecupero = descBreveTipoRecupero;
	}
	public Long getIdPeriodo() {
		return idPeriodo;
	}
	public void setIdPeriodo(Long idPeriodo) {
		this.idPeriodo = idPeriodo;
	}

	

}
