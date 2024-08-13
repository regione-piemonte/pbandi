/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo;

import it.csi.pbandi.pbweb.pbandisrv.integration.util.DataFilter;

import java.math.BigDecimal;
import java.sql.Date;

public class DocumentoDiSpesaDichiarazioneVO extends GenericVO {

	private String descTipoDichiarazioneSpesa;
	private String nomeFile;
	private Date dtChiusuraValidazione;
	private Date dtDichiarazione;
	private BigDecimal idDichiarazioneSpesa;
	private BigDecimal idDocumentoDiSpesa;
	private BigDecimal idDocumentoIndex;
	private BigDecimal idProgetto;
	private BigDecimal idTipoDichiarazSpesa;
	private BigDecimal idUtenteIns;
	private String noteChiusuraValidazione;
	private Date periodoAl;
	private Date periodoDal;

	public String toString() {

		String temp = "";
		StringBuffer sb = new StringBuffer();
		sb.append("\t\n" + this.getClass().getName() + "\t\n");

		temp = DataFilter.removeNull(idDichiarazioneSpesa);
		if (!DataFilter.isEmpty(temp))
			sb.append(" idDichiarazioneSpesa: " + temp + "\t\n");

		temp = DataFilter.removeNull(idDocumentoDiSpesa);
		if (!DataFilter.isEmpty(temp))
			sb.append(" idDocumentoDiSpesa: " + temp + "\t\n");

		temp = DataFilter.removeNull(idProgetto);
		if (!DataFilter.isEmpty(temp))
			sb.append(" idProgetto: " + temp + "\t\n");

		temp = DataFilter.removeNull(idTipoDichiarazSpesa);
		if (!DataFilter.isEmpty(temp))
			sb.append(" idTipoDichiarazSpesa: " + temp + "\t\n");

		temp = DataFilter.removeNull(descTipoDichiarazioneSpesa);
		if (!DataFilter.isEmpty(temp))
			sb.append(" descTipoDichiarazioneSpesa: " + temp + "\t\n");

		temp = DataFilter.removeNull(idDocumentoIndex);
		if (!DataFilter.isEmpty(temp))
			sb.append(" idDocumentoIndex: " + temp + "\t\n");

		temp = DataFilter.removeNull(nomeFile);
		if (!DataFilter.isEmpty(temp))
			sb.append(" nomeFile: " + temp + "\t\n");

		temp = DataFilter.removeNull(dtChiusuraValidazione);
		if (!DataFilter.isEmpty(temp))
			sb.append(" dtChiusuraValidazione: " + temp + "\t\n");

		temp = DataFilter.removeNull(dtDichiarazione);
		if (!DataFilter.isEmpty(temp))
			sb.append(" dtDichiarazione: " + temp + "\t\n");

		temp = DataFilter.removeNull(noteChiusuraValidazione);
		if (!DataFilter.isEmpty(temp))
			sb.append(" noteChiusuraValidazione: " + temp + "\t\n");

		temp = DataFilter.removeNull(periodoAl);
		if (!DataFilter.isEmpty(temp))
			sb.append(" periodoAl: " + temp + "\t\n");

		temp = DataFilter.removeNull(periodoDal);
		if (!DataFilter.isEmpty(temp))
			sb.append(" periodoDal: " + temp + "\t\n");

		return sb.toString();
	}

	public BigDecimal getIdDichiarazioneSpesa() {
		return idDichiarazioneSpesa;
	}

	public void setIdDichiarazioneSpesa(BigDecimal idDichiarazioneSpesa) {
		this.idDichiarazioneSpesa = idDichiarazioneSpesa;
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

	public Date getDtChiusuraValidazione() {
		return dtChiusuraValidazione;
	}

	public void setDtChiusuraValidazione(Date dtChiusuraValidazione) {
		this.dtChiusuraValidazione = dtChiusuraValidazione;
	}

	public Date getDtDichiarazione() {
		return dtDichiarazione;
	}

	public void setDtDichiarazione(Date dtDichiarazione) {
		this.dtDichiarazione = dtDichiarazione;
	}

	public BigDecimal getIdTipoDichiarazSpesa() {
		return idTipoDichiarazSpesa;
	}

	public void setIdTipoDichiarazSpesa(BigDecimal idTipoDichiarazSpesa) {
		this.idTipoDichiarazSpesa = idTipoDichiarazSpesa;
	}

	public BigDecimal getIdUtenteIns() {
		return idUtenteIns;
	}

	public void setIdUtenteIns(BigDecimal idUtenteIns) {
		this.idUtenteIns = idUtenteIns;
	}

	public Date getPeriodoAl() {
		return periodoAl;
	}

	public void setPeriodoAl(Date periodoAl) {
		this.periodoAl = periodoAl;
	}

	public Date getPeriodoDal() {
		return periodoDal;
	}

	public void setPeriodoDal(Date periodoDal) {
		this.periodoDal = periodoDal;
	}

	public String getNoteChiusuraValidazione() {
		return noteChiusuraValidazione;
	}

	public void setNoteChiusuraValidazione(String noteChiusuraValidazione) {
		this.noteChiusuraValidazione = noteChiusuraValidazione;
	}

	public String getDescTipoDichiarazioneSpesa() {
		return descTipoDichiarazioneSpesa;
	}

	public void setDescTipoDichiarazioneSpesa(String descTipoDichiarazioneSpesa) {
		this.descTipoDichiarazioneSpesa = descTipoDichiarazioneSpesa;
	}

	public BigDecimal getIdDocumentoIndex() {
		return idDocumentoIndex;
	}

	public void setIdDocumentoIndex(BigDecimal idDocumentoIndex) {
		this.idDocumentoIndex = idDocumentoIndex;
	}

	public String getNomeFile() {
		return nomeFile;
	}

	public void setNomeFile(String nomeFile) {
		this.nomeFile = nomeFile;
	}

}
