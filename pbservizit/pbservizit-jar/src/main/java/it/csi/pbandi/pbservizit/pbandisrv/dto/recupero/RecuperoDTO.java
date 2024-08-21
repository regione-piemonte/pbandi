/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.pbandisrv.dto.recupero;

public class RecuperoDTO implements java.io.Serializable {

	static final long serialVersionUID = 1;
	private Long idModalitaAgevolazione;
	private String descModalitaAgevolazione;
	private Long idProgetto;
	private Double totaleImportoRevocato;
	private Double totaleImportoRecuperato;
	private java.util.Date dtUltimaRevoca;
	private String estremiUltimaRevoca;
	private MotivoRevocaDTO[] motiviRevoche;
	private Double importoRecupero;
	private java.util.Date dtRecupero;
	private String estremiRecupero;
	private Long idTipoRecupero;
	private Double importoErogazioni;
	private Double quotaImportoAgevolato;
	private String noteRecupero;
	private Long idPeriodo;
	public Long getIdModalitaAgevolazione() {
		return idModalitaAgevolazione;
	}
	public void setIdModalitaAgevolazione(Long idModalitaAgevolazione) {
		this.idModalitaAgevolazione = idModalitaAgevolazione;
	}
	public String getDescModalitaAgevolazione() {
		return descModalitaAgevolazione;
	}
	public void setDescModalitaAgevolazione(String descModalitaAgevolazione) {
		this.descModalitaAgevolazione = descModalitaAgevolazione;
	}
	public Long getIdProgetto() {
		return idProgetto;
	}
	public void setIdProgetto(Long idProgetto) {
		this.idProgetto = idProgetto;
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
	public java.util.Date getDtUltimaRevoca() {
		return dtUltimaRevoca;
	}
	public void setDtUltimaRevoca(java.util.Date dtUltimaRevoca) {
		this.dtUltimaRevoca = dtUltimaRevoca;
	}
	public String getEstremiUltimaRevoca() {
		return estremiUltimaRevoca;
	}
	public void setEstremiUltimaRevoca(String estremiUltimaRevoca) {
		this.estremiUltimaRevoca = estremiUltimaRevoca;
	}
	public MotivoRevocaDTO[] getMotiviRevoche() {
		return motiviRevoche;
	}
	public void setMotiviRevoche(MotivoRevocaDTO[] motiviRevoche) {
		this.motiviRevoche = motiviRevoche;
	}
	public Double getImportoRecupero() {
		return importoRecupero;
	}
	public void setImportoRecupero(Double importoRecupero) {
		this.importoRecupero = importoRecupero;
	}
	public java.util.Date getDtRecupero() {
		return dtRecupero;
	}
	public void setDtRecupero(java.util.Date dtRecupero) {
		this.dtRecupero = dtRecupero;
	}
	public String getEstremiRecupero() {
		return estremiRecupero;
	}
	public void setEstremiRecupero(String estremiRecupero) {
		this.estremiRecupero = estremiRecupero;
	}
	public Long getIdTipoRecupero() {
		return idTipoRecupero;
	}
	public void setIdTipoRecupero(Long idTipoRecupero) {
		this.idTipoRecupero = idTipoRecupero;
	}
	public Double getImportoErogazioni() {
		return importoErogazioni;
	}
	public void setImportoErogazioni(Double importoErogazioni) {
		this.importoErogazioni = importoErogazioni;
	}
	public Double getQuotaImportoAgevolato() {
		return quotaImportoAgevolato;
	}
	public void setQuotaImportoAgevolato(Double quotaImportoAgevolato) {
		this.quotaImportoAgevolato = quotaImportoAgevolato;
	}
	public String getNoteRecupero() {
		return noteRecupero;
	}
	public void setNoteRecupero(String noteRecupero) {
		this.noteRecupero = noteRecupero;
	}
	public Long getIdPeriodo() {
		return idPeriodo;
	}
	public void setIdPeriodo(Long idPeriodo) {
		this.idPeriodo = idPeriodo;
	}
	
	
	

}
