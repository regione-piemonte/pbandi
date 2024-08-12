package it.csi.pbandi.pbweb.dto.spesaValidata;

import java.io.Serializable;

public class DichiarazioneSpesaValidataDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private String dtChiusuraValidazione;
	private String descTipoDichiarazioneSpesa;
	private Long idDichiarazioneSpesa;
	private Long idProgetto;
	private Long idUtenteIns;
	private String noteChiusuraValidazione;
	private String periodoAl;
	private String periodoDal;
	private String periodo;

	private Long idDocIndexDichiarazioneDiSpesa;
	private Long idDocIndexReportDettaglio;

	public String getDtChiusuraValidazione() {
		return dtChiusuraValidazione;
	}

	public void setDtChiusuraValidazione(String dtChiusuraValidazione) {
		this.dtChiusuraValidazione = dtChiusuraValidazione;
	}

	public String getDescTipoDichiarazioneSpesa() {
		return descTipoDichiarazioneSpesa;
	}

	public void setDescTipoDichiarazioneSpesa(String descTipoDichiarazioneSpesa) {
		this.descTipoDichiarazioneSpesa = descTipoDichiarazioneSpesa;
	}

	public Long getIdDichiarazioneSpesa() {
		return idDichiarazioneSpesa;
	}

	public void setIdDichiarazioneSpesa(Long idDichiarazioneSpesa) {
		this.idDichiarazioneSpesa = idDichiarazioneSpesa;
	}

	public Long getIdProgetto() {
		return idProgetto;
	}

	public void setIdProgetto(Long idProgetto) {
		this.idProgetto = idProgetto;
	}

	public Long getIdUtenteIns() {
		return idUtenteIns;
	}

	public void setIdUtenteIns(Long idUtenteIns) {
		this.idUtenteIns = idUtenteIns;
	}

	public String getNoteChiusuraValidazione() {
		return noteChiusuraValidazione;
	}

	public void setNoteChiusuraValidazione(String noteChiusuraValidazione) {
		this.noteChiusuraValidazione = noteChiusuraValidazione;
	}

	public String getPeriodoAl() {
		return periodoAl;
	}

	public void setPeriodoAl(String periodoAl) {
		this.periodoAl = periodoAl;
	}

	public String getPeriodoDal() {
		return periodoDal;
	}

	public void setPeriodoDal(String periodoDal) {
		this.periodoDal = periodoDal;
	}

	public String getPeriodo() {
		return periodo;
	}

	public void setPeriodo(String periodo) {
		this.periodo = periodo;
	}

	public Long getIdDocIndexDichiarazioneDiSpesa() {
		return idDocIndexDichiarazioneDiSpesa;
	}

	public void setIdDocIndexDichiarazioneDiSpesa(Long idDocIndexDichiarazioneDiSpesa) {
		this.idDocIndexDichiarazioneDiSpesa = idDocIndexDichiarazioneDiSpesa;
	}

	public Long getIdDocIndexReportDettaglio() {
		return idDocIndexReportDettaglio;
	}

	public void setIdDocIndexReportDettaglio(Long idDocIndexReportDettaglio) {
		this.idDocIndexReportDettaglio = idDocIndexReportDettaglio;
	}

}
