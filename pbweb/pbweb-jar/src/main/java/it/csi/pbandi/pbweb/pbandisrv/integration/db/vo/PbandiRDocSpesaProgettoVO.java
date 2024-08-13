/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/


package it.csi.pbandi.pbweb.pbandisrv.integration.db.vo;

import java.math.*;
import java.sql.Date;

import it.csi.pbandi.pbweb.pbandisrv.integration.util.DataFilter;

public class PbandiRDocSpesaProgettoVO extends GenericVO {

	private String task;

	private BigDecimal idUtenteAgg;

	private BigDecimal idStatoDocumentoSpesa;

	private BigDecimal idStatoDocumentoSpesaValid;

	private String noteValidazione;

	private BigDecimal idProgetto;

	private BigDecimal idDocumentoDiSpesa;

	private BigDecimal importoRendicontazione;

	private String tipoInvio;

	private BigDecimal idUtenteIns;

	private BigDecimal idAppalto;

	private String rilievoContabile;

	private Date dtRilievoContabile;

	public PbandiRDocSpesaProgettoVO() {
	}

	public PbandiRDocSpesaProgettoVO(BigDecimal idProgetto, BigDecimal idDocumentoDiSpesa) {

		this.idProgetto = idProgetto;
		this.idDocumentoDiSpesa = idDocumentoDiSpesa;
	}

	public PbandiRDocSpesaProgettoVO(String task, BigDecimal idUtenteAgg, BigDecimal idStatoDocumentoSpesa,
			BigDecimal idStatoDocumentoSpesaValid, String noteValidazione, BigDecimal idProgetto,
			BigDecimal idDocumentoDiSpesa, BigDecimal importoRendicontazione, String tipoInvio, BigDecimal idUtenteIns,
			BigDecimal idAppalto) {

		this.task = task;
		this.idUtenteAgg = idUtenteAgg;
		this.idStatoDocumentoSpesa = idStatoDocumentoSpesa;
		this.idStatoDocumentoSpesaValid = idStatoDocumentoSpesaValid;
		this.noteValidazione = noteValidazione;
		this.idProgetto = idProgetto;
		this.idDocumentoDiSpesa = idDocumentoDiSpesa;
		this.importoRendicontazione = importoRendicontazione;
		this.tipoInvio = tipoInvio;
		this.idUtenteIns = idUtenteIns;
		this.idAppalto = idAppalto;
	}

	public BigDecimal getIdAppalto() {
		return idAppalto;
	}

	public void setIdAppalto(BigDecimal idAppalto) {
		this.idAppalto = idAppalto;
	}

	public String getTask() {
		return task;
	}

	public void setTask(String task) {
		this.task = task;
	}

	public BigDecimal getIdUtenteAgg() {
		return idUtenteAgg;
	}

	public void setIdUtenteAgg(BigDecimal idUtenteAgg) {
		this.idUtenteAgg = idUtenteAgg;
	}

	public BigDecimal getIdStatoDocumentoSpesa() {
		return idStatoDocumentoSpesa;
	}

	public void setIdStatoDocumentoSpesa(BigDecimal idStatoDocumentoSpesa) {
		this.idStatoDocumentoSpesa = idStatoDocumentoSpesa;
	}

	public BigDecimal getIdStatoDocumentoSpesaValid() {
		return idStatoDocumentoSpesaValid;
	}

	public void setIdStatoDocumentoSpesaValid(BigDecimal idStatoDocumentoSpesaValid) {
		this.idStatoDocumentoSpesaValid = idStatoDocumentoSpesaValid;
	}

	public String getNoteValidazione() {
		return noteValidazione;
	}

	public void setNoteValidazione(String noteValidazione) {
		this.noteValidazione = noteValidazione;
	}

	public BigDecimal getIdProgetto() {
		return idProgetto;
	}

	public void setIdProgetto(BigDecimal idProgetto) {
		this.idProgetto = idProgetto;
	}

	public BigDecimal getIdDocumentoDiSpesa() {
		return idDocumentoDiSpesa;
	}

	public void setIdDocumentoDiSpesa(BigDecimal idDocumentoDiSpesa) {
		this.idDocumentoDiSpesa = idDocumentoDiSpesa;
	}

	public BigDecimal getImportoRendicontazione() {
		return importoRendicontazione;
	}

	public void setImportoRendicontazione(BigDecimal importoRendicontazione) {
		this.importoRendicontazione = importoRendicontazione;
	}

	public String getTipoInvio() {
		return tipoInvio;
	}

	public void setTipoInvio(String tipoInvio) {
		this.tipoInvio = tipoInvio;
	}

	public BigDecimal getIdUtenteIns() {
		return idUtenteIns;
	}

	public void setIdUtenteIns(BigDecimal idUtenteIns) {
		this.idUtenteIns = idUtenteIns;
	}

	public String getRilievoContabile() {
		return rilievoContabile;
	}

	public void setRilievoContabile(String rilievoContabile) {
		this.rilievoContabile = rilievoContabile;
	}

	public Date getDtRilievoContabile() {
		return dtRilievoContabile;
	}

	public void setDtRilievoContabile(Date dtRilievoContabile) {
		this.dtRilievoContabile = dtRilievoContabile;
	}

	public boolean isValid() {
		boolean isValid = false;
		if (isPKValid() && idStatoDocumentoSpesa != null && tipoInvio != null && idUtenteIns != null) {
			isValid = true;
		}
		return isValid;
	}

	public boolean isPKValid() {
		boolean isPkValid = false;
		if (idProgetto != null && idDocumentoDiSpesa != null) {
			isPkValid = true;
		}

		return isPkValid;
	}

	public String toString() {

		String temp = "";
		StringBuffer sb = new StringBuffer();
		sb.append("\t\n" + this.getClass().getName() + "\t\n");

		temp = DataFilter.removeNull(task);
		if (!DataFilter.isEmpty(temp))
			sb.append(" task: " + temp + "\t\n");

		temp = DataFilter.removeNull(idUtenteAgg);
		if (!DataFilter.isEmpty(temp))
			sb.append(" idUtenteAgg: " + temp + "\t\n");

		temp = DataFilter.removeNull(idStatoDocumentoSpesa);
		if (!DataFilter.isEmpty(temp))
			sb.append(" idStatoDocumentoSpesa: " + temp + "\t\n");

		temp = DataFilter.removeNull(idStatoDocumentoSpesaValid);
		if (!DataFilter.isEmpty(temp))
			sb.append(" idStatoDocumentoSpesaValid: " + temp + "\t\n");

		temp = DataFilter.removeNull(noteValidazione);
		if (!DataFilter.isEmpty(temp))
			sb.append(" noteValidazione: " + temp + "\t\n");

		temp = DataFilter.removeNull(idProgetto);
		if (!DataFilter.isEmpty(temp))
			sb.append(" idProgetto: " + temp + "\t\n");

		temp = DataFilter.removeNull(idDocumentoDiSpesa);
		if (!DataFilter.isEmpty(temp))
			sb.append(" idDocumentoDiSpesa: " + temp + "\t\n");

		temp = DataFilter.removeNull(importoRendicontazione);
		if (!DataFilter.isEmpty(temp))
			sb.append(" importoRendicontazione: " + temp + "\t\n");

		temp = DataFilter.removeNull(tipoInvio);
		if (!DataFilter.isEmpty(temp))
			sb.append(" tipoInvio: " + temp + "\t\n");

		temp = DataFilter.removeNull(idUtenteIns);
		if (!DataFilter.isEmpty(temp))
			sb.append(" idUtenteIns: " + temp + "\t\n");

		temp = DataFilter.removeNull(idAppalto);
		if (!DataFilter.isEmpty(temp))
			sb.append(" idAppalto: " + temp + "\t\n");
		
		temp = DataFilter.removeNull(rilievoContabile);
		if (!DataFilter.isEmpty(temp))
			sb.append(" rilievoContabile: " + temp + "\t\n");
		
		temp = DataFilter.removeNull(dtRilievoContabile);
		if (!DataFilter.isEmpty(temp))
			sb.append(" dtRilievoContabile: " + temp + "\t\n");

		return sb.toString();
	}

	public java.util.List getPK() {
		java.util.List pk = new java.util.ArrayList();

		pk.add("idProgetto");

		pk.add("idDocumentoDiSpesa");

		return pk;
	}

}
