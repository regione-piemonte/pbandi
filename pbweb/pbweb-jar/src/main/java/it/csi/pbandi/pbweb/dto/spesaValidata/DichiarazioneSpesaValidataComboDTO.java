package it.csi.pbandi.pbweb.dto.spesaValidata;

import java.io.Serializable;
import java.util.Date;

public class DichiarazioneSpesaValidataComboDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private String codice;
	private String descrizione;
	private String descBreveTipoDichiarazioneSpesa;
	private String noteRilievoContabile;
	private Date dtRilievoContabile;
	private Date dtChiusuraRilievi;
	private Date dtConfermaValiditaRilievi;

	public String getCodice() {
		return codice;
	}

	public void setCodice(String codice) {
		this.codice = codice;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public String getDescBreveTipoDichiarazioneSpesa() {
		return descBreveTipoDichiarazioneSpesa;
	}

	public void setDescBreveTipoDichiarazioneSpesa(String descBreveTipoDichiarazioneSpesa) {
		this.descBreveTipoDichiarazioneSpesa = descBreveTipoDichiarazioneSpesa;
	}

	public String getNoteRilievoContabile() {
		return noteRilievoContabile;
	}

	public void setNoteRilievoContabile(String noteRilievoContabile) {
		this.noteRilievoContabile = noteRilievoContabile;
	}

	public Date getDtRilievoContabile() {
		return dtRilievoContabile;
	}

	public void setDtRilievoContabile(Date dtRilievoContabile) {
		this.dtRilievoContabile = dtRilievoContabile;
	}

	public Date getDtChiusuraRilievi() {
		return dtChiusuraRilievi;
	}

	public void setDtChiusuraRilievi(Date dtChiusuraRilievi) {
		this.dtChiusuraRilievi = dtChiusuraRilievi;
	}

	public Date getDtConfermaValiditaRilievi() {
		return dtConfermaValiditaRilievi;
	}

	public void setDtConfermaValiditaRilievi(Date dtConfermaValiditaRilievi) {
		this.dtConfermaValiditaRilievi = dtConfermaValiditaRilievi;
	}

}
