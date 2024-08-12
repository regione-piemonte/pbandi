package it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.rettifica;

import java.math.BigDecimal;
import java.sql.Date;

import it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.GenericVO;

public class DichiarazioneSpesaRettificaVO extends GenericVO {

	private Date periodoAl;

	private Date dtDichiarazione;

	private String noteChiusuraValidazione;

	private BigDecimal idProgetto;

	private Date periodoDal;

	private BigDecimal idDichiarazioneSpesa;

	private Date dtChiusuraValidazione;

	private BigDecimal idTipoDichiarazSpesa;

	private String rilievoContabile;

	private Date dtRilievoContabile;

	private Date dtChiusuraRilievo;

	private Date dtConfermaValidita;

	public Date getPeriodoAl() {
		return periodoAl;
	}

	public void setPeriodoAl(Date periodoAl) {
		this.periodoAl = periodoAl;
	}

	public Date getDtDichiarazione() {
		return dtDichiarazione;
	}

	public void setDtDichiarazione(Date dtDichiarazione) {
		this.dtDichiarazione = dtDichiarazione;
	}

	public String getNoteChiusuraValidazione() {
		return noteChiusuraValidazione;
	}

	public void setNoteChiusuraValidazione(String noteChiusuraValidazione) {
		this.noteChiusuraValidazione = noteChiusuraValidazione;
	}

	public BigDecimal getIdProgetto() {
		return idProgetto;
	}

	public void setIdProgetto(BigDecimal idProgetto) {
		this.idProgetto = idProgetto;
	}

	public Date getPeriodoDal() {
		return periodoDal;
	}

	public void setPeriodoDal(Date periodoDal) {
		this.periodoDal = periodoDal;
	}

	public BigDecimal getIdDichiarazioneSpesa() {
		return idDichiarazioneSpesa;
	}

	public void setIdDichiarazioneSpesa(BigDecimal idDichiarazioneSpesa) {
		this.idDichiarazioneSpesa = idDichiarazioneSpesa;
	}

	public Date getDtChiusuraValidazione() {
		return dtChiusuraValidazione;
	}

	public void setDtChiusuraValidazione(Date dtChiusuraValidazione) {
		this.dtChiusuraValidazione = dtChiusuraValidazione;
	}

	public BigDecimal getIdTipoDichiarazSpesa() {
		return idTipoDichiarazSpesa;
	}

	public void setIdTipoDichiarazSpesa(BigDecimal idTipoDichiarazSpesa) {
		this.idTipoDichiarazSpesa = idTipoDichiarazSpesa;
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

	public Date getDtChiusuraRilievo() {
		return dtChiusuraRilievo;
	}

	public void setDtChiusuraRilievo(Date dtChiusuraRilievo) {
		this.dtChiusuraRilievo = dtChiusuraRilievo;
	}

	public Date getDtConfermaValidita() {
		return dtConfermaValidita;
	}

	public void setDtConfermaValidita(Date dtConfermaValidita) {
		this.dtConfermaValidita = dtConfermaValidita;
	}

}
