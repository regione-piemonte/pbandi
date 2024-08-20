/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebrce.pbandisrv.integration.db.vo;

import it.csi.pbandi.pbwebrce.pbandisrv.integration.util.DataFilter;

import java.math.BigDecimal;
import java.sql.Date;

public class PbandiTIrregolaritaProvvVO extends GenericVO {

	private BigDecimal idIrregolaritaProvv;
	private Date dtComunicazione;
	private Date dtFineProvvisoria;
	private BigDecimal idProgetto;
	private BigDecimal idMotivoRevoca;
	private BigDecimal importoIrregolarita;
	private BigDecimal importoAgevolazioneIrreg;
	private BigDecimal importoIrregolareCertificato;
	private Date dtFineValidita;
  	private Date dataInizioControlli;
  	private Date dataFineControlli;
  	private String tipoControlli;
  	private String irregolaritaAnnullata;
  	private BigDecimal idPeriodo;  	
  	private BigDecimal idCategAnagrafica;
  	private String note;
  	private Date dataCampione;
  	
  	private BigDecimal idEsitoControllo;  	
  	
	public BigDecimal getIdEsitoControllo() {
		return idEsitoControllo;
	}

	public void setIdEsitoControllo(BigDecimal idEsitoControllo) {
		this.idEsitoControllo = idEsitoControllo;
	}

	public PbandiTIrregolaritaProvvVO() {}

	public PbandiTIrregolaritaProvvVO(BigDecimal idIrregolaritaProvv) {
		this.idIrregolaritaProvv = idIrregolaritaProvv;
	}

	public PbandiTIrregolaritaProvvVO(BigDecimal idIrregolaritaProvv, Date dtComunicazione, Date dtFineProvvisoria, BigDecimal idProgetto, 
			BigDecimal idMotivoRevoca, BigDecimal importoIrregolarita, BigDecimal importoAgevolazioneIrreg, 
			BigDecimal importoIrregolareCertificato, Date dtFineValidita, Date dataInizioControlli, Date dataFineControlli, String tipoControlli, String irregolaritaAnnullata,
			BigDecimal idPeriodo, BigDecimal idCategAnagrafica,String note, Date dataCampione) {
		this.idIrregolaritaProvv = idIrregolaritaProvv;
		this.dtComunicazione = dtComunicazione;
		this.dtFineProvvisoria = dtFineProvvisoria;
		this.idProgetto = idProgetto;
		this.idMotivoRevoca = idMotivoRevoca;
		this.importoIrregolarita = importoIrregolarita;
		this.importoAgevolazioneIrreg = importoAgevolazioneIrreg;
		this.importoIrregolareCertificato = importoIrregolareCertificato;
		this.dtFineValidita = dtFineValidita;
		this.dataInizioControlli = dataInizioControlli;
	  	this.dataFineControlli = dataFineControlli;
	  	this.tipoControlli = tipoControlli;
	  	this.irregolaritaAnnullata = irregolaritaAnnullata;
	  	this.idPeriodo = idPeriodo;
	  	this.idCategAnagrafica = idCategAnagrafica;
	  	this.note = note;
	  	this.dataCampione = dataCampione;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public BigDecimal getIdPeriodo() {
		return idPeriodo;
	}

	public void setIdPeriodo(BigDecimal idPeriodo) {
		this.idPeriodo = idPeriodo;
	}

	public BigDecimal getIdCategAnagrafica() {
		return idCategAnagrafica;
	}

	public void setIdCategAnagrafica(BigDecimal idCategAnagrafica) {
		this.idCategAnagrafica = idCategAnagrafica;
	}

	public Date getDataInizioControlli() {
		return dataInizioControlli;
	}

	public void setDataInizioControlli(Date dataInizioControlli) {
		this.dataInizioControlli = dataInizioControlli;
	}

	public Date getDataFineControlli() {
		return dataFineControlli;
	}

	public void setDataFineControlli(Date dataFineControlli) {
		this.dataFineControlli = dataFineControlli;
	}

	public String getTipoControlli() {
		return tipoControlli;
	}

	public void setTipoControlli(String tipoControlli) {
		this.tipoControlli = tipoControlli;
	}

	public String getIrregolaritaAnnullata() {
		return irregolaritaAnnullata;
	}

	public void setIrregolaritaAnnullata(String irregolaritaAnnullata) {
		this.irregolaritaAnnullata = irregolaritaAnnullata;
	}
	
	public BigDecimal getIdIrregolaritaProvv() {
		return idIrregolaritaProvv;
	}

	public void setIdIrregolaritaProvv(BigDecimal idIrregolaritaProvv) {
		this.idIrregolaritaProvv = idIrregolaritaProvv;
	}

	public Date getDtComunicazione() {
		return dtComunicazione;
	}

	public void setDtComunicazione(Date dtComunicazione) {
		this.dtComunicazione = dtComunicazione;
	}

	public Date getDtFineProvvisoria() {
		return dtFineProvvisoria;
	}

	public void setDtFineProvvisoria(Date dtFineProvvisoria) {
		this.dtFineProvvisoria = dtFineProvvisoria;
	}

	public BigDecimal getIdProgetto() {
		return idProgetto;
	}

	public void setIdProgetto(BigDecimal idProgetto) {
		this.idProgetto = idProgetto;
	}

	public BigDecimal getIdMotivoRevoca() {
		return idMotivoRevoca;
	}

	public void setIdMotivoRevoca(BigDecimal idMotivoRevoca) {
		this.idMotivoRevoca = idMotivoRevoca;
	}

	public BigDecimal getImportoIrregolarita() {
		return importoIrregolarita;
	}

	public void setImportoIrregolarita(BigDecimal importoIrregolarita) {
		this.importoIrregolarita = importoIrregolarita;
	}

	public BigDecimal getImportoAgevolazioneIrreg() {
		return importoAgevolazioneIrreg;
	}

	public void setImportoAgevolazioneIrreg(BigDecimal importoAgevolazioneIrreg) {
		this.importoAgevolazioneIrreg = importoAgevolazioneIrreg;
	}

	public BigDecimal getImportoIrregolareCertificato() {
		return importoIrregolareCertificato;
	}

	public void setImportoIrregolareCertificato(
			BigDecimal importoIrregolareCertificato) {
		this.importoIrregolareCertificato = importoIrregolareCertificato;
	}

	public Date getDtFineValidita() {
		return dtFineValidita;
	}

	public void setDtFineValidita(Date dtFineValidita) {
		this.dtFineValidita = dtFineValidita;
	}

	public boolean isValid() {
		boolean isValid = false;
		if (isPKValid() && idProgetto != null && importoIrregolarita != null
				&& dtComunicazione != null) {
			isValid = true;
		}
		return isValid;
	}

	public boolean isPKValid() {
		boolean isPkValid = false;
		if (idIrregolaritaProvv != null) {
			isPkValid = true;
		}

		return isPkValid;
	}

	public String toString() {
		String temp = "";
		StringBuffer sb = new StringBuffer();
		sb.append("\t\n" + this.getClass().getName() + "\t\n");

		temp = DataFilter.removeNull(dtComunicazione);
		if (!DataFilter.isEmpty(temp))
			sb.append(" dtComunicazione: " + temp + "\t\n");

		temp = DataFilter.removeNull(dtFineProvvisoria);
		if (!DataFilter.isEmpty(temp))
			sb.append(" dtFineProvvisoria: " + temp + "\t\n");

		temp = DataFilter.removeNull(idProgetto);
		if (!DataFilter.isEmpty(temp))
			sb.append(" idProgetto: " + temp + "\t\n");

		temp = DataFilter.removeNull(idMotivoRevoca);
		if (!DataFilter.isEmpty(temp))
			sb.append(" idMotivoRevoca: " + temp + "\t\n");

		temp = DataFilter.removeNull(importoIrregolarita);
		if (!DataFilter.isEmpty(temp))
			sb.append(" importoIrregolarita: " + temp + "\t\n");

		temp = DataFilter.removeNull(importoAgevolazioneIrreg);
		if (!DataFilter.isEmpty(temp))
			sb.append(" importoAgevolazioneIrreg: " + temp + "\t\n");

		temp = DataFilter.removeNull(importoIrregolareCertificato);
		if (!DataFilter.isEmpty(temp))
			sb.append(" importoIrregolareCertificato: " + temp + "\t\n");

		temp = DataFilter.removeNull(dtFineValidita);
		if (!DataFilter.isEmpty(temp))
			sb.append(" dtFineValidita: " + temp + "\t\n");
		
		temp = DataFilter.removeNull(idPeriodo);
		if (!DataFilter.isEmpty(temp))
			sb.append(" idPeriodo: " + temp + "\t\n");
		
		temp = DataFilter.removeNull(idCategAnagrafica);
		if (!DataFilter.isEmpty(temp))
			sb.append(" idCategAnagrafica: " + temp + "\t\n");

		temp = DataFilter.removeNull(note);
		if (!DataFilter.isEmpty(temp))
			sb.append(" note: " + temp + "\t\n");
		
		temp = DataFilter.removeNull(dataCampione);
		if (!DataFilter.isEmpty(temp))
			sb.append(" dataCampione: " + temp + "\t\n");
		
		return sb.toString();
	}

	public java.util.List<String> getPK() {
		java.util.List<String> pk = new java.util.ArrayList<String>();
		pk.add("idIrregolaritaProvv");
		return pk;
	}

	public Date getDataCampione() {
		return dataCampione;
	}

	public void setDataCampione(Date dataCampione) {
		this.dataCampione = dataCampione;
	}

}
