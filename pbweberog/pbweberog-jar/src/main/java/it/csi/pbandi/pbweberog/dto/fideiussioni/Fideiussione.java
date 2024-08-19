/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweberog.dto.fideiussioni;


import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;


public class Fideiussione implements java.io.Serializable {

	private Long idFideiussione;
	private Double importoFideiussione;
	private String codRiferimentoFideiussione;
	
	private Date dtDecorrenza;
	private Date dtScadenza;
	
	private String descEnteEmittente;
	private String noteFideiussione;
	private Long idTipoTrattamento;
	private Long idProgetto;
	private String numero;
	private Double importo;
	private String descrizioneTipoTrattamento;
	private Double importoTotaleTipoTrattamento;
	private static final long serialVersionUID = 1L;

	
	
	public Long getIdFideiussione() {
		return idFideiussione;
	}

	public void setIdFideiussione(Long idFideiussione) {
		this.idFideiussione = idFideiussione;
	}

	public Double getImportoFideiussione() {
		return importoFideiussione;
	}

	public void setImportoFideiussione(Double importoFideiussione) {
		this.importoFideiussione = importoFideiussione;
	}

	public String getCodRiferimentoFideiussione() {
		return codRiferimentoFideiussione;
	}

	public void setCodRiferimentoFideiussione(String codRiferimentoFideiussione) {
		this.codRiferimentoFideiussione = codRiferimentoFideiussione;
	}
	
	public Date getDtDecorrenza() {
		return dtDecorrenza;
	}

	public void setDtDecorrenza(Date dtDecorrenza) {
		this.dtDecorrenza = dtDecorrenza;
	}

	public Date getDtScadenza() {
		return dtScadenza;
	}
	
	public void setDtScadenza(Date dtScadenza) {
		this.dtScadenza = dtScadenza;
	}

	public String getDescEnteEmittente() {
		return descEnteEmittente;
	}

	public void setDescEnteEmittente(String descEnteEmittente) {
		this.descEnteEmittente = descEnteEmittente;
	}

	public String getNoteFideiussione() {
		return noteFideiussione;
	}

	public void setNoteFideiussione(String noteFideiussione) {
		this.noteFideiussione = noteFideiussione;
	}

	public Long getIdTipoTrattamento() {
		return idTipoTrattamento;
	}

	public void setIdTipoTrattamento(Long idTipoTrattamento) {
		this.idTipoTrattamento = idTipoTrattamento;
	}

	public Long getIdProgetto() {
		return idProgetto;
	}

	public void setIdProgetto(Long idProgetto) {
		this.idProgetto = idProgetto;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public Double getImporto() {
		return importo;
	}

	public void setImporto(Double importo) {
		this.importo = importo;
	}

	public String getDescrizioneTipoTrattamento() {
		return descrizioneTipoTrattamento;
	}

	public void setDescrizioneTipoTrattamento(String descrizioneTipoTrattamento) {
		this.descrizioneTipoTrattamento = descrizioneTipoTrattamento;
	}

	public Double getImportoTotaleTipoTrattamento() {
		return importoTotaleTipoTrattamento;
	}

	public void setImportoTotaleTipoTrattamento(Double importoTotaleTipoTrattamento) {
		this.importoTotaleTipoTrattamento = importoTotaleTipoTrattamento;
	}

	public Fideiussione() {
		super();

	}

	public String toString() {
		return super.toString();
	}

}
