/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.pbandisrv.dto.revoca;

public class RevocaModalitaAgevolazioneDTO implements java.io.Serializable {

	static final long serialVersionUID = 1;
	private CausaleErogazioneDTO[] causaliErogazioni;
	private String codCausaleDisimpegno;
	private String descBreveModalitaAgevolaz;
	private String descModalitaAgevolazione;
	private java.util.Date dtRevoca;
	private java.util.Date dtDecorRevoca;
	private String estremi;
	private Long idPeriodo;
	private Long idProgetto;
	private Long idModalitaAgevolazione;
	private Long idMotivoRevoca;
	private Double importoErogazioni;
	private Double importoRevoca;
	private String note;
	private Double quotaImportoAgevolato;
	private Double totaleImportoRevocato;
	private Double totaleImportoRecuperato;
	private String flagOrdineRecupero;
	private Long idMancatoRecupero;
	public CausaleErogazioneDTO[] getCausaliErogazioni() {
		return causaliErogazioni;
	}
	public void setCausaliErogazioni(CausaleErogazioneDTO[] causaliErogazioni) {
		this.causaliErogazioni = causaliErogazioni;
	}
	public String getCodCausaleDisimpegno() {
		return codCausaleDisimpegno;
	}
	public void setCodCausaleDisimpegno(String codCausaleDisimpegno) {
		this.codCausaleDisimpegno = codCausaleDisimpegno;
	}
	public String getDescBreveModalitaAgevolaz() {
		return descBreveModalitaAgevolaz;
	}
	public void setDescBreveModalitaAgevolaz(String descBreveModalitaAgevolaz) {
		this.descBreveModalitaAgevolaz = descBreveModalitaAgevolaz;
	}
	public String getDescModalitaAgevolazione() {
		return descModalitaAgevolazione;
	}
	public void setDescModalitaAgevolazione(String descModalitaAgevolazione) {
		this.descModalitaAgevolazione = descModalitaAgevolazione;
	}
	public java.util.Date getDtRevoca() {
		return dtRevoca;
	}
	public void setDtRevoca(java.util.Date dtRevoca) {
		this.dtRevoca = dtRevoca;
	}
	public java.util.Date getDtDecorRevoca() {
		return dtDecorRevoca;
	}
	public void setDtDecorRevoca(java.util.Date dtDecorRevoca) {
		this.dtDecorRevoca = dtDecorRevoca;
	}
	public String getEstremi() {
		return estremi;
	}
	public void setEstremi(String estremi) {
		this.estremi = estremi;
	}
	public Long getIdPeriodo() {
		return idPeriodo;
	}
	public void setIdPeriodo(Long idPeriodo) {
		this.idPeriodo = idPeriodo;
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
	public Long getIdMotivoRevoca() {
		return idMotivoRevoca;
	}
	public void setIdMotivoRevoca(Long idMotivoRevoca) {
		this.idMotivoRevoca = idMotivoRevoca;
	}
	public Double getImportoErogazioni() {
		return importoErogazioni;
	}
	public void setImportoErogazioni(Double importoErogazioni) {
		this.importoErogazioni = importoErogazioni;
	}
	public Double getImportoRevoca() {
		return importoRevoca;
	}
	public void setImportoRevoca(Double importoRevoca) {
		this.importoRevoca = importoRevoca;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public Double getQuotaImportoAgevolato() {
		return quotaImportoAgevolato;
	}
	public void setQuotaImportoAgevolato(Double quotaImportoAgevolato) {
		this.quotaImportoAgevolato = quotaImportoAgevolato;
	}
	public Double getTotaleImportoRevocato() {
		return totaleImportoRevocato;
	}
	public void setTotaleImportoRevocato(Double totaleImportoRevocato) {
		this.totaleImportoRevocato = totaleImportoRevocato;
	}
	public Double getTotaleImportoRecuperato() {
		return totaleImportoRecuperato;
	}
	public void setTotaleImportoRecuperato(Double totaleImportoRecuperato) {
		this.totaleImportoRecuperato = totaleImportoRecuperato;
	}
	public String getFlagOrdineRecupero() {
		return flagOrdineRecupero;
	}
	public void setFlagOrdineRecupero(String flagOrdineRecupero) {
		this.flagOrdineRecupero = flagOrdineRecupero;
	}
	public Long getIdMancatoRecupero() {
		return idMancatoRecupero;
	}
	public void setIdMancatoRecupero(Long idMancatoRecupero) {
		this.idMancatoRecupero = idMancatoRecupero;
	}
	
	
	

	
	
}
