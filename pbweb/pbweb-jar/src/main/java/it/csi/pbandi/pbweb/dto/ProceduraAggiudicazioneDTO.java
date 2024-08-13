/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweb.dto;

public class ProceduraAggiudicazioneDTO implements java.io.Serializable {

	static final long serialVersionUID = 1;

	private java.lang.Long idProceduraAggiudicaz = null;
	private java.lang.Long idProgetto = null;
	private java.lang.Long idTipologiaAggiudicaz = null;
	private java.lang.Long idMotivoAssenzaCIG = null;
	private java.lang.String descProcAgg = null;
	private java.lang.String cigProcAgg = null;
	private java.lang.String codProcAgg = null;
	private java.lang.Double importo = null;
	private java.lang.Double iva = null;
	private java.util.Date dtAggiudicazione = null;
	private java.util.Date dtInizioValidita = null;
	private java.util.Date dtFineValidita = null;
	private java.lang.Long idUtenteIns = null;
	private java.lang.Long idUtenteAgg = null;
	public java.lang.Long getIdProceduraAggiudicaz() {
		return idProceduraAggiudicaz;
	}
	public void setIdProceduraAggiudicaz(java.lang.Long idProceduraAggiudicaz) {
		this.idProceduraAggiudicaz = idProceduraAggiudicaz;
	}
	public java.lang.Long getIdProgetto() {
		return idProgetto;
	}
	public void setIdProgetto(java.lang.Long idProgetto) {
		this.idProgetto = idProgetto;
	}
	public java.lang.Long getIdTipologiaAggiudicaz() {
		return idTipologiaAggiudicaz;
	}
	public void setIdTipologiaAggiudicaz(java.lang.Long idTipologiaAggiudicaz) {
		this.idTipologiaAggiudicaz = idTipologiaAggiudicaz;
	}
	public java.lang.Long getIdMotivoAssenzaCIG() {
		return idMotivoAssenzaCIG;
	}
	public void setIdMotivoAssenzaCIG(java.lang.Long idMotivoAssenzaCIG) {
		this.idMotivoAssenzaCIG = idMotivoAssenzaCIG;
	}
	public java.lang.String getDescProcAgg() {
		return descProcAgg;
	}
	public void setDescProcAgg(java.lang.String descProcAgg) {
		this.descProcAgg = descProcAgg;
	}
	public java.lang.String getCigProcAgg() {
		return cigProcAgg;
	}
	public void setCigProcAgg(java.lang.String cigProcAgg) {
		this.cigProcAgg = cigProcAgg;
	}
	public java.lang.String getCodProcAgg() {
		return codProcAgg;
	}
	public void setCodProcAgg(java.lang.String codProcAgg) {
		this.codProcAgg = codProcAgg;
	}
	public java.lang.Double getImporto() {
		return importo;
	}
	public void setImporto(java.lang.Double importo) {
		this.importo = importo;
	}
	public java.lang.Double getIva() {
		return iva;
	}
	public void setIva(java.lang.Double iva) {
		this.iva = iva;
	}
	public java.util.Date getDtAggiudicazione() {
		return dtAggiudicazione;
	}
	public void setDtAggiudicazione(java.util.Date dtAggiudicazione) {
		this.dtAggiudicazione = dtAggiudicazione;
	}
	public java.util.Date getDtInizioValidita() {
		return dtInizioValidita;
	}
	public void setDtInizioValidita(java.util.Date dtInizioValidita) {
		this.dtInizioValidita = dtInizioValidita;
	}
	public java.util.Date getDtFineValidita() {
		return dtFineValidita;
	}
	public void setDtFineValidita(java.util.Date dtFineValidita) {
		this.dtFineValidita = dtFineValidita;
	}
	public java.lang.Long getIdUtenteIns() {
		return idUtenteIns;
	}
	public void setIdUtenteIns(java.lang.Long idUtenteIns) {
		this.idUtenteIns = idUtenteIns;
	}
	public java.lang.Long getIdUtenteAgg() {
		return idUtenteAgg;
	}
	public void setIdUtenteAgg(java.lang.Long idUtenteAgg) {
		this.idUtenteAgg = idUtenteAgg;
	}

}
