/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebrce.pbandisrv.dto.gestioneaffidamenti;

import java.util.Date;

public class ProceduraAggiudicazioneDTO implements java.io.Serializable {

	static final long serialVersionUID = 1;

	private Long idProceduraAggiudicaz;
	private Long idProgetto;
	private Long idTipologiaAggiudicaz;
	private Long idMotivoAssenzaCIG;
	
	private String descProcAgg;
	
	private String cigProcAgg;
	
	private String codProcAgg;	

	private Double importo;
	
	private Double iva;

	private Date dtAggiudicazione;

	private Date dtInizioValidita;

	private Date dtFineValidita;


	private Long idUtenteIns;

	private Long idUtenteAgg;

	public Long getIdProceduraAggiudicaz() {
		return idProceduraAggiudicaz;
	}

	public void setIdProceduraAggiudicaz(Long idProceduraAggiudicaz) {
		this.idProceduraAggiudicaz = idProceduraAggiudicaz;
	}

	public Long getIdProgetto() {
		return idProgetto;
	}

	public void setIdProgetto(Long idProgetto) {
		this.idProgetto = idProgetto;
	}

	public Long getIdTipologiaAggiudicaz() {
		return idTipologiaAggiudicaz;
	}

	public void setIdTipologiaAggiudicaz(Long idTipologiaAggiudicaz) {
		this.idTipologiaAggiudicaz = idTipologiaAggiudicaz;
	}

	public Long getIdMotivoAssenzaCIG() {
		return idMotivoAssenzaCIG;
	}

	public void setIdMotivoAssenzaCIG(Long idMotivoAssenzaCIG) {
		this.idMotivoAssenzaCIG = idMotivoAssenzaCIG;
	}

	public String getDescProcAgg() {
		return descProcAgg;
	}

	public void setDescProcAgg(String descProcAgg) {
		this.descProcAgg = descProcAgg;
	}

	public String getCigProcAgg() {
		return cigProcAgg;
	}

	public void setCigProcAgg(String cigProcAgg) {
		this.cigProcAgg = cigProcAgg;
	}

	public String getCodProcAgg() {
		return codProcAgg;
	}

	public void setCodProcAgg(String codProcAgg) {
		this.codProcAgg = codProcAgg;
	}

	public Double getImporto() {
		return importo;
	}

	public void setImporto(Double importo) {
		this.importo = importo;
	}

	public Double getIva() {
		return iva;
	}

	public void setIva(Double iva) {
		this.iva = iva;
	}

	public Date getDtAggiudicazione() {
		return dtAggiudicazione;
	}

	public void setDtAggiudicazione(Date dtAggiudicazione) {
		this.dtAggiudicazione = dtAggiudicazione;
	}

	public Date getDtInizioValidita() {
		return dtInizioValidita;
	}

	public void setDtInizioValidita(Date dtInizioValidita) {
		this.dtInizioValidita = dtInizioValidita;
	}

	public Date getDtFineValidita() {
		return dtFineValidita;
	}

	public void setDtFineValidita(Date dtFineValidita) {
		this.dtFineValidita = dtFineValidita;
	}

	public Long getIdUtenteIns() {
		return idUtenteIns;
	}

	public void setIdUtenteIns(Long idUtenteIns) {
		this.idUtenteIns = idUtenteIns;
	}

	public Long getIdUtenteAgg() {
		return idUtenteAgg;
	}

	public void setIdUtenteAgg(Long idUtenteAgg) {
		this.idUtenteAgg = idUtenteAgg;
	}

	


}
