/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebrce.dto;

import java.util.Date;

public class ProceduraAggiudicazioneDTO implements java.io.Serializable {

	static final long serialVersionUID = 1;

	private Long idProceduraAggiudicaz;
	private String descProcAgg;
	private Double importo;
	private String codProcAgg;
	private String cigProcAgg;
	private Long idTipologiaAggiudicaz;
	private String descTipologiaAggiudicazione;
	private Long idProgetto;
	private Double percentuale;
	private Double importoPercentuale;
	private Double iva;
	private String codice;
	private StepAggiudicazione[] iter;
	private Long idMotivoAssenzaCig;
	private Date dtAggiudicazione;
	private String oggettoAffidamento;
	public Long getIdProceduraAggiudicaz() {
		return idProceduraAggiudicaz;
	}
	public void setIdProceduraAggiudicaz(Long idProceduraAggiudicaz) {
		this.idProceduraAggiudicaz = idProceduraAggiudicaz;
	}
	public String getDescProcAgg() {
		return descProcAgg;
	}
	public void setDescProcAgg(String descProcAgg) {
		this.descProcAgg = descProcAgg;
	}
	public Double getImporto() {
		return importo;
	}
	public void setImporto(Double importo) {
		this.importo = importo;
	}
	public String getCodProcAgg() {
		return codProcAgg;
	}
	public void setCodProcAgg(String codProcAgg) {
		this.codProcAgg = codProcAgg;
	}
	public String getCigProcAgg() {
		return cigProcAgg;
	}
	public void setCigProcAgg(String cigProcAgg) {
		this.cigProcAgg = cigProcAgg;
	}
	public Long getIdTipologiaAggiudicaz() {
		return idTipologiaAggiudicaz;
	}
	public void setIdTipologiaAggiudicaz(Long idTipologiaAggiudicaz) {
		this.idTipologiaAggiudicaz = idTipologiaAggiudicaz;
	}
	public String getDescTipologiaAggiudicazione() {
		return descTipologiaAggiudicazione;
	}
	public void setDescTipologiaAggiudicazione(String descTipologiaAggiudicazione) {
		this.descTipologiaAggiudicazione = descTipologiaAggiudicazione;
	}
	public Long getIdProgetto() {
		return idProgetto;
	}
	public void setIdProgetto(Long idProgetto) {
		this.idProgetto = idProgetto;
	}
	public Double getPercentuale() {
		return percentuale;
	}
	public void setPercentuale(Double percentuale) {
		this.percentuale = percentuale;
	}
	public Double getImportoPercentuale() {
		return importoPercentuale;
	}
	public void setImportoPercentuale(Double importoPercentuale) {
		this.importoPercentuale = importoPercentuale;
	}
	public Double getIva() {
		return iva;
	}
	public void setIva(Double iva) {
		this.iva = iva;
	}
	public String getCodice() {
		return codice;
	}
	public void setCodice(String codice) {
		this.codice = codice;
	}
	public StepAggiudicazione[] getIter() {
		return iter;
	}
	public void setIter(StepAggiudicazione[] iter) {
		this.iter = iter;
	}
	public Long getIdMotivoAssenzaCig() {
		return idMotivoAssenzaCig;
	}
	public void setIdMotivoAssenzaCig(Long idMotivoAssenzaCig) {
		this.idMotivoAssenzaCig = idMotivoAssenzaCig;
	}
	public Date getDtAggiudicazione() {
		return dtAggiudicazione;
	}
	public void setDtAggiudicazione(Date dtAggiudicazione) {
		this.dtAggiudicazione = dtAggiudicazione;
	}
	public String getOggettoAffidamento() {
		return oggettoAffidamento;
	}
	public void setOggettoAffidamento(String oggettoAffidamento) {
		this.oggettoAffidamento = oggettoAffidamento;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
