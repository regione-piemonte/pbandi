/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/


package it.csi.pbandi.pbweb.pbandisrv.integration.db.vo;

import java.math.*;
import java.sql.Date;
import it.csi.pbandi.pbweb.pbandisrv.integration.util.DataFilter;



public class PbandiTIrregolaritaVO extends GenericVO {

  	
  	private BigDecimal idProgetto;
  	
  	private BigDecimal idTipoIrregolarita;
  	
  	private BigDecimal idIrregolarita;

  	private BigDecimal idIrregolaritaProvv;
  	
  	private BigDecimal idMetodoIndividuazione;
  	
  	private String flagBlocco;
  	
  	private Date dtInizioValidita;
  	
  	private BigDecimal idUtenteIns;
  	
  	private String numeroIms;
  	
  	private BigDecimal idStatoFinanziario;
  	
  	private BigDecimal idDispComunitaria;
  	
  	private Date dtIms;
  	
  	private BigDecimal idQualificazioneIrreg;
  	
  	private String soggettoResponsabile;
  	
  	private String notePraticaUsata;
  	
  	private Date dtFineValidita;
  	
  	private BigDecimal numeroVersione;
  	
  	private Date dtComunicazione;
  	
  	private BigDecimal idNaturaSanzione;
  	
  	private String flagCasoChiuso;
  	
  	private String flagProvvedimento;

  	private BigDecimal idUtenteAgg;
  	
  	private BigDecimal idStatoAmministrativo;
  	
  	private BigDecimal idIrregolaritaCollegata;
  	
  	private BigDecimal importoIrregolarita;
  	
  	private BigDecimal quotaImpIrregCertificato;
  	
  	private Date dataInizioControlli;
  	
  	private Date dataFineControlli;
  	
  	private BigDecimal importoAgevolazioneIrreg;
  	
  	private String tipoControlli;
  	
  	private BigDecimal idMotivoRevoca;
  	
  	private BigDecimal idPeriodo;
  	
  	private BigDecimal idCategAnagrafica;
  	
  	private String note;
  	
  	private Date dataCampione;
  	
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

	public BigDecimal getImportoIrregolarita() {
		return importoIrregolarita;
	}

	public void setImportoIrregolarita(BigDecimal importoIrregolarita) {
		this.importoIrregolarita = importoIrregolarita;
	}

	public BigDecimal getQuotaImpIrregCertificato() {
		return quotaImpIrregCertificato;
	}

	public void setQuotaImpIrregCertificato(BigDecimal quotaImpIrregCertificato) {
		this.quotaImpIrregCertificato = quotaImpIrregCertificato;
	}

	public PbandiTIrregolaritaVO() {}
  	
	public PbandiTIrregolaritaVO (BigDecimal idIrregolarita) {
	
		this. idIrregolarita =  idIrregolarita;
	}
  	
	public PbandiTIrregolaritaVO (BigDecimal idProgetto, BigDecimal idTipoIrregolarita, BigDecimal idIrregolarita, 
			BigDecimal idMetodoIndividuazione, String flagBlocco, Date dtInizioValidita, BigDecimal idUtenteIns, 
			String numeroIms, BigDecimal idStatoFinanziario, BigDecimal idDispComunitaria, Date dtIms, 
			BigDecimal idQualificazioneIrreg, String soggettoResponsabile, String notePraticaUsata, Date dtFineValidita, 
			BigDecimal numeroVersione, Date dtComunicazione, BigDecimal idNaturaSanzione, String flagCasoChiuso, 
			BigDecimal idUtenteAgg, BigDecimal idStatoAmministrativo, BigDecimal idIrregolaritaCollegata, 
			BigDecimal importoIrregolarita, BigDecimal quotaImpIrregCertificato, String flagProvvedimento, 
			BigDecimal idIrregolaritaProvv, Date dataInizioControlli, Date dataFineControlli, 
			BigDecimal importoAgevolazioneIrreg, String tipoControlli, BigDecimal idMotivoRevoca,BigDecimal idPeriodo, 
			BigDecimal idCategAnagrafica,String note, Date dataCampione
			) {
	
		this. idProgetto =  idProgetto;
		this. idIrregolaritaProvv =  idIrregolaritaProvv;
		this. idTipoIrregolarita =  idTipoIrregolarita;
		this. idIrregolarita =  idIrregolarita;
		this. idMetodoIndividuazione =  idMetodoIndividuazione;
		this. flagBlocco =  flagBlocco;
		this. dtInizioValidita =  dtInizioValidita;
		this. idUtenteIns =  idUtenteIns;
		this. numeroIms =  numeroIms;
		this. idStatoFinanziario =  idStatoFinanziario;
		this. idDispComunitaria =  idDispComunitaria;
		this. dtIms =  dtIms;
		this. idQualificazioneIrreg =  idQualificazioneIrreg;
		this. soggettoResponsabile =  soggettoResponsabile;
		this. notePraticaUsata =  notePraticaUsata;
		this. dtFineValidita =  dtFineValidita;
		this. numeroVersione =  numeroVersione;
		this. dtComunicazione =  dtComunicazione;
		this. idNaturaSanzione =  idNaturaSanzione;
		this. flagCasoChiuso =  flagCasoChiuso;
		this. idUtenteAgg =  idUtenteAgg;
		this. idStatoAmministrativo =  idStatoAmministrativo;
		this. idIrregolaritaCollegata =  idIrregolaritaCollegata;
		this. importoIrregolarita = importoIrregolarita;
		this. quotaImpIrregCertificato = quotaImpIrregCertificato;
		this. flagProvvedimento = flagProvvedimento;
		this. dataInizioControlli = dataInizioControlli;
		this. dataFineControlli = dataFineControlli;
		this. importoAgevolazioneIrreg = importoAgevolazioneIrreg;
		this. tipoControlli = tipoControlli;
		this. idMotivoRevoca = idMotivoRevoca;
		this. idPeriodo = idPeriodo;
		this. idCategAnagrafica = idCategAnagrafica;
		this. note = note;
		this.dataCampione = dataCampione;
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

	public BigDecimal getImportoAgevolazioneIrreg() {
		return importoAgevolazioneIrreg;
	}

	public void setImportoAgevolazioneIrreg(BigDecimal importoAgevolazioneIrreg) {
		this.importoAgevolazioneIrreg = importoAgevolazioneIrreg;
	}

	public String getTipoControlli() {
		return tipoControlli;
	}

	public void setTipoControlli(String tipoControlli) {
		this.tipoControlli = tipoControlli;
	}

	public BigDecimal getIdMotivoRevoca() {
		return idMotivoRevoca;
	}

	public void setIdMotivoRevoca(BigDecimal idMotivoRevoca) {
		this.idMotivoRevoca = idMotivoRevoca;
	}

	public BigDecimal getIdProgetto() {
		return idProgetto;
	}
	
	public void setIdProgetto(BigDecimal idProgetto) {
		this.idProgetto = idProgetto;
	}
	
	public BigDecimal getIdTipoIrregolarita() {
		return idTipoIrregolarita;
	}
	
	public void setIdTipoIrregolarita(BigDecimal idTipoIrregolarita) {
		this.idTipoIrregolarita = idTipoIrregolarita;
	}
	
	public BigDecimal getIdIrregolarita() {
		return idIrregolarita;
	}
	
	public void setIdIrregolarita(BigDecimal idIrregolarita) {
		this.idIrregolarita = idIrregolarita;
	}
	
	public BigDecimal getIdMetodoIndividuazione() {
		return idMetodoIndividuazione;
	}
	
	public void setIdMetodoIndividuazione(BigDecimal idMetodoIndividuazione) {
		this.idMetodoIndividuazione = idMetodoIndividuazione;
	}
	
	public String getFlagBlocco() {
		return flagBlocco;
	}
	
	public void setFlagBlocco(String flagBlocco) {
		this.flagBlocco = flagBlocco;
	}
	
	public Date getDtInizioValidita() {
		return dtInizioValidita;
	}
	
	public void setDtInizioValidita(Date dtInizioValidita) {
		this.dtInizioValidita = dtInizioValidita;
	}
	
	public BigDecimal getIdUtenteIns() {
		return idUtenteIns;
	}
	
	public void setIdUtenteIns(BigDecimal idUtenteIns) {
		this.idUtenteIns = idUtenteIns;
	}
	
	public String getNumeroIms() {
		return numeroIms;
	}
	
	public void setNumeroIms(String numeroIms) {
		this.numeroIms = numeroIms;
	}
	
	public String getFlagProvvedimento() {
		return flagProvvedimento;
	}

	public void setFlagProvvedimento(String flagProvvedimento) {
		this.flagProvvedimento = flagProvvedimento;
	}
	
	public BigDecimal getIdStatoFinanziario() {
		return idStatoFinanziario;
	}
	
	public void setIdStatoFinanziario(BigDecimal idStatoFinanziario) {
		this.idStatoFinanziario = idStatoFinanziario;
	}
	
	public BigDecimal getIdDispComunitaria() {
		return idDispComunitaria;
	}
	
	public void setIdDispComunitaria(BigDecimal idDispComunitaria) {
		this.idDispComunitaria = idDispComunitaria;
	}
	
	public Date getDtIms() {
		return dtIms;
	}
	
	public void setDtIms(Date dtIms) {
		this.dtIms = dtIms;
	}
	
	public BigDecimal getIdIrregolaritaProvv() {
		return idIrregolaritaProvv;
	}

	public void setIdIrregolaritaProvv(BigDecimal idIrregolaritaProvv) {
		this.idIrregolaritaProvv = idIrregolaritaProvv;
	}

	public BigDecimal getIdQualificazioneIrreg() {
		return idQualificazioneIrreg;
	}
	
	public void setIdQualificazioneIrreg(BigDecimal idQualificazioneIrreg) {
		this.idQualificazioneIrreg = idQualificazioneIrreg;
	}
	
	public String getSoggettoResponsabile() {
		return soggettoResponsabile;
	}
	
	public void setSoggettoResponsabile(String soggettoResponsabile) {
		this.soggettoResponsabile = soggettoResponsabile;
	}
	
	public String getNotePraticaUsata() {
		return notePraticaUsata;
	}
	
	public void setNotePraticaUsata(String notePraticaUsata) {
		this.notePraticaUsata = notePraticaUsata;
	}
	
	public Date getDtFineValidita() {
		return dtFineValidita;
	}
	
	public void setDtFineValidita(Date dtFineValidita) {
		this.dtFineValidita = dtFineValidita;
	}
	
	public BigDecimal getNumeroVersione() {
		return numeroVersione;
	}
	
	public void setNumeroVersione(BigDecimal numeroVersione) {
		this.numeroVersione = numeroVersione;
	}
	
	public Date getDtComunicazione() {
		return dtComunicazione;
	}
	
	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public void setDtComunicazione(Date dtComunicazione) {
		this.dtComunicazione = dtComunicazione;
	}
	
	public BigDecimal getIdNaturaSanzione() {
		return idNaturaSanzione;
	}
	
	public void setIdNaturaSanzione(BigDecimal idNaturaSanzione) {
		this.idNaturaSanzione = idNaturaSanzione;
	}
	
	public String getFlagCasoChiuso() {
		return flagCasoChiuso;
	}
	
	public void setFlagCasoChiuso(String flagCasoChiuso) {
		this.flagCasoChiuso = flagCasoChiuso;
	}
	
	public BigDecimal getIdUtenteAgg() {
		return idUtenteAgg;
	}
	
	public void setIdUtenteAgg(BigDecimal idUtenteAgg) {
		this.idUtenteAgg = idUtenteAgg;
	}
	
	public BigDecimal getIdStatoAmministrativo() {
		return idStatoAmministrativo;
	}
	
	public void setIdStatoAmministrativo(BigDecimal idStatoAmministrativo) {
		this.idStatoAmministrativo = idStatoAmministrativo;
	}
	
	public BigDecimal getIdIrregolaritaCollegata() {
		return idIrregolaritaCollegata;
	}
	
	public void setIdIrregolaritaCollegata(BigDecimal idIrregolaritaCollegata) {
		this.idIrregolaritaCollegata = idIrregolaritaCollegata;
	}
	
	public Date getDataCampione() {
		return dataCampione;
	}

	public void setDataCampione(Date dataCampione) {
		this.dataCampione = dataCampione;
	}
	
	
	public boolean isValid() {
		boolean isValid = false;
        if (isPKValid()) {
   			isValid = true;
   		}
   		return isValid;
	}

	public boolean isPKValid() {
		boolean isPkValid = false;
		if (idIrregolarita != null ) {
   			isPkValid = true;
   		}
   		return isPkValid;
	}
	
	public String toString() {
		
	    String temp = "";
	    StringBuffer sb = new StringBuffer();
	    sb.append("\t\n" + this.getClass().getName() + "\t\n");
	    
	    temp = DataFilter.removeNull( idProgetto);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idProgetto: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idTipoIrregolarita);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idTipoIrregolarita: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idIrregolarita);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idIrregolarita: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idIrregolaritaProvv);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idIrregolaritaProvv: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idMetodoIndividuazione);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idMetodoIndividuazione: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( flagBlocco);
	    if (!DataFilter.isEmpty(temp)) sb.append(" flagBlocco: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtInizioValidita);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtInizioValidita: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idUtenteIns);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idUtenteIns: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( numeroIms);
	    if (!DataFilter.isEmpty(temp)) sb.append(" numeroIms: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idStatoFinanziario);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idStatoFinanziario: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idDispComunitaria);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idDispComunitaria: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtIms);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtIms: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idQualificazioneIrreg);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idQualificazioneIrreg: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( soggettoResponsabile);
	    if (!DataFilter.isEmpty(temp)) sb.append(" soggettoResponsabile: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( notePraticaUsata);
	    if (!DataFilter.isEmpty(temp)) sb.append(" notePraticaUsata: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtFineValidita);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtFineValidita: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( numeroVersione);
	    if (!DataFilter.isEmpty(temp)) sb.append(" numeroVersione: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtComunicazione);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtComunicazione: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idNaturaSanzione);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idNaturaSanzione: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( flagCasoChiuso);
	    if (!DataFilter.isEmpty(temp)) sb.append(" flagCasoChiuso: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idUtenteAgg);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idUtenteAgg: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idStatoAmministrativo);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idStatoAmministrativo: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idIrregolaritaCollegata);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idIrregolaritaCollegata: " + temp + "\t\n");	    
	    
	    temp = DataFilter.removeNull( importoIrregolarita);
	    if (!DataFilter.isEmpty(temp)) sb.append(" importoIrregolarita: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( quotaImpIrregCertificato);
	    if (!DataFilter.isEmpty(temp)) sb.append(" quotaImpIrregCertificato: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dataInizioControlli);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dataInizioControlli: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dataFineControlli);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dataFineControlli: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idMotivoRevoca);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idMotivoRevoca: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( importoAgevolazioneIrreg);
	    if (!DataFilter.isEmpty(temp)) sb.append(" importoAgevolazioneIrreg: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( tipoControlli);
	    if (!DataFilter.isEmpty(temp)) sb.append(" tipoControlli: " + temp + "\t\n");

	    temp = DataFilter.removeNull( idPeriodo);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idPeriodo: " + temp + "\t\n");

	    temp = DataFilter.removeNull( idCategAnagrafica);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idCategAnagrafica: " + temp + "\t\n");

	    temp = DataFilter.removeNull( dataCampione);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dataCampione: " + temp + "\t\n");
	    
	    return sb.toString();
	}
	
	public java.util.List<String> getPK() {
		java.util.List<String> pk=new java.util.ArrayList<String>();
		
			pk.add("idIrregolarita");
		
	    return pk;
	}

	
	
}
