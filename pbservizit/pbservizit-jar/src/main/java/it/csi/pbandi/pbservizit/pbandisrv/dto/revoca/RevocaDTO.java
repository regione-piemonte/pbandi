/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.pbandisrv.dto.revoca;

public class RevocaDTO implements java.io.Serializable {

	static final long serialVersionUID = 1;
	private String causaleDisimpegno;
	private String codCausaleDisimpegno;
	private String codiceVisualizzato;
	private String descMotivoRevoca;
	private String descModalitaAgevolazione;
	private String descBreveModalitaAgevolaz;
	private java.util.Date dtRevoca;
	private String estremi;
	private Long idPeriodo;
	private Long idRevoca;
	private Long idProgetto;
	private Long idModalitaAgevolazione;
	private Long idMotivoRevoca;
	private Double importo;
	private String note;
	private RevocaIrregolaritaDTO[] revocaIrregolarita;
	private String flagOrdineRecupero;
	private Long idMancatoREcupero;
	private String descMancatoRecupero;
	public String getCausaleDisimpegno() {
		return causaleDisimpegno;
	}
	public void setCausaleDisimpegno(String causaleDisimpegno) {
		this.causaleDisimpegno = causaleDisimpegno;
	}
	public String getCodCausaleDisimpegno() {
		return codCausaleDisimpegno;
	}
	public void setCodCausaleDisimpegno(String codCausaleDisimpegno) {
		this.codCausaleDisimpegno = codCausaleDisimpegno;
	}
	public String getCodiceVisualizzato() {
		return codiceVisualizzato;
	}
	public void setCodiceVisualizzato(String codiceVisualizzato) {
		this.codiceVisualizzato = codiceVisualizzato;
	}
	public String getDescMotivoRevoca() {
		return descMotivoRevoca;
	}
	public void setDescMotivoRevoca(String descMotivoRevoca) {
		this.descMotivoRevoca = descMotivoRevoca;
	}
	public String getDescModalitaAgevolazione() {
		return descModalitaAgevolazione;
	}
	public void setDescModalitaAgevolazione(String descModalitaAgevolazione) {
		this.descModalitaAgevolazione = descModalitaAgevolazione;
	}
	public String getDescBreveModalitaAgevolaz() {
		return descBreveModalitaAgevolaz;
	}
	public void setDescBreveModalitaAgevolaz(String descBreveModalitaAgevolaz) {
		this.descBreveModalitaAgevolaz = descBreveModalitaAgevolaz;
	}
	public java.util.Date getDtRevoca() {
		return dtRevoca;
	}
	public void setDtRevoca(java.util.Date dtRevoca) {
		this.dtRevoca = dtRevoca;
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
	public Long getIdRevoca() {
		return idRevoca;
	}
	public void setIdRevoca(Long idRevoca) {
		this.idRevoca = idRevoca;
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
	public Double getImporto() {
		return importo;
	}
	public void setImporto(Double importo) {
		this.importo = importo;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public RevocaIrregolaritaDTO[] getRevocaIrregolarita() {
		return revocaIrregolarita;
	}
	public void setRevocaIrregolarita(RevocaIrregolaritaDTO[] revocaIrregolarita) {
		this.revocaIrregolarita = revocaIrregolarita;
	}
	public String getFlagOrdineRecupero() {
		return flagOrdineRecupero;
	}
	public void setFlagOrdineRecupero(String flagOrdineRecupero) {
		this.flagOrdineRecupero = flagOrdineRecupero;
	}
	public Long getIdMancatoREcupero() {
		return idMancatoREcupero;
	}
	public void setIdMancatoREcupero(Long idMancatoREcupero) {
		this.idMancatoREcupero = idMancatoREcupero;
	}
	public String getDescMancatoRecupero() {
		return descMancatoRecupero;
	}
	public void setDescMancatoRecupero(String descMancatoRecupero) {
		this.descMancatoRecupero = descMancatoRecupero;
	}
	
	
	
}
