/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/


package it.csi.pbandi.pbweb.pbandisrv.integration.db.vo;

import java.math.*;
import java.sql.Date;
import it.csi.pbandi.pbweb.pbandisrv.integration.util.DataFilter;



public class PbandiTFornitoreVO extends GenericVO {

  	
  	private String cognomeFornitore;
  	
  	private Date dtInizioValidita;
  	
  	private BigDecimal idUtenteAgg;
  	
  	private String partitaIvaFornitore;
  	
  	private BigDecimal idFormaGiuridica;
  	
  	private BigDecimal idSoggettoFornitore;
  	
  	private BigDecimal idTipoSoggetto;
  	
  	private String nomeFornitore;
  	
  	private BigDecimal idAttivitaAteco;
  	
  	private BigDecimal idFornitore;
  	
  	private String denominazioneFornitore;
  	
  	private Date dtFineValidita;
  	
  	private BigDecimal idNazione;
  	
  	private BigDecimal idUtenteIns;
  	
  	private String codiceFiscaleFornitore;
  	
  	private String altroCodice;
  	
  	private String codUniIpa;
  	
  	private Long flagPubblicoPrivato;
  	
  	
	public PbandiTFornitoreVO() {}
  	
	public PbandiTFornitoreVO (BigDecimal idFornitore) {
	
		this. idFornitore =  idFornitore;
	}
  	
	public PbandiTFornitoreVO (String cognomeFornitore, Date dtInizioValidita, BigDecimal idUtenteAgg, String partitaIvaFornitore, BigDecimal idFormaGiuridica, BigDecimal idSoggettoFornitore, BigDecimal idTipoSoggetto, String nomeFornitore, BigDecimal idAttivitaAteco, BigDecimal idFornitore, String denominazioneFornitore, Date dtFineValidita, BigDecimal idNazione, BigDecimal idUtenteIns, String codiceFiscaleFornitore, String altroCodice) {
	
		this. cognomeFornitore =  cognomeFornitore;
		this. dtInizioValidita =  dtInizioValidita;
		this. idUtenteAgg =  idUtenteAgg;
		this. partitaIvaFornitore =  partitaIvaFornitore;
		this. idFormaGiuridica =  idFormaGiuridica;
		this. idSoggettoFornitore =  idSoggettoFornitore;
		this. idTipoSoggetto =  idTipoSoggetto;
		this. nomeFornitore =  nomeFornitore;
		this. idAttivitaAteco =  idAttivitaAteco;
		this. idFornitore =  idFornitore;
		this. denominazioneFornitore =  denominazioneFornitore;
		this. dtFineValidita =  dtFineValidita;
		this. idNazione =  idNazione;
		this. idUtenteIns =  idUtenteIns;
		this. codiceFiscaleFornitore =  codiceFiscaleFornitore;
		this. altroCodice = altroCodice;
	}
  	
  	
	public String getCognomeFornitore() {
		return cognomeFornitore;
	}
	
	public void setCognomeFornitore(String cognomeFornitore) {
		this.cognomeFornitore = cognomeFornitore;
	}
	
	public Date getDtInizioValidita() {
		return dtInizioValidita;
	}
	
	public void setDtInizioValidita(Date dtInizioValidita) {
		this.dtInizioValidita = dtInizioValidita;
	}
	
	public BigDecimal getIdUtenteAgg() {
		return idUtenteAgg;
	}
	
	public void setIdUtenteAgg(BigDecimal idUtenteAgg) {
		this.idUtenteAgg = idUtenteAgg;
	}
	
	public String getPartitaIvaFornitore() {
		return partitaIvaFornitore;
	}
	
	public void setPartitaIvaFornitore(String partitaIvaFornitore) {
		this.partitaIvaFornitore = partitaIvaFornitore;
	}
	
	public BigDecimal getIdFormaGiuridica() {
		return idFormaGiuridica;
	}
	
	public void setIdFormaGiuridica(BigDecimal idFormaGiuridica) {
		this.idFormaGiuridica = idFormaGiuridica;
	}
	
	public BigDecimal getIdSoggettoFornitore() {
		return idSoggettoFornitore;
	}
	
	public void setIdSoggettoFornitore(BigDecimal idSoggettoFornitore) {
		this.idSoggettoFornitore = idSoggettoFornitore;
	}
	
	public BigDecimal getIdTipoSoggetto() {
		return idTipoSoggetto;
	}
	
	public void setIdTipoSoggetto(BigDecimal idTipoSoggetto) {
		this.idTipoSoggetto = idTipoSoggetto;
	}
	
	public String getNomeFornitore() {
		return nomeFornitore;
	}
	
	public void setNomeFornitore(String nomeFornitore) {
		this.nomeFornitore = nomeFornitore;
	}
	
	public BigDecimal getIdAttivitaAteco() {
		return idAttivitaAteco;
	}
	
	public void setIdAttivitaAteco(BigDecimal idAttivitaAteco) {
		this.idAttivitaAteco = idAttivitaAteco;
	}
	
	public BigDecimal getIdFornitore() {
		return idFornitore;
	}
	
	public void setIdFornitore(BigDecimal idFornitore) {
		this.idFornitore = idFornitore;
	}
	
	public String getDenominazioneFornitore() {
		return denominazioneFornitore;
	}
	
	public void setDenominazioneFornitore(String denominazioneFornitore) {
		this.denominazioneFornitore = denominazioneFornitore;
	}
	
	public Date getDtFineValidita() {
		return dtFineValidita;
	}
	
	public void setDtFineValidita(Date dtFineValidita) {
		this.dtFineValidita = dtFineValidita;
	}
	
	public BigDecimal getIdNazione() {
		return idNazione;
	}
	
	public void setIdNazione(BigDecimal idNazione) {
		this.idNazione = idNazione;
	}
	
	public BigDecimal getIdUtenteIns() {
		return idUtenteIns;
	}
	
	public void setIdUtenteIns(BigDecimal idUtenteIns) {
		this.idUtenteIns = idUtenteIns;
	}
	
	public String getCodiceFiscaleFornitore() {
		return codiceFiscaleFornitore;
	}
	
	public void setCodiceFiscaleFornitore(String codiceFiscaleFornitore) {
		this.codiceFiscaleFornitore = codiceFiscaleFornitore;
	}
	
	public String getAltroCodice() {
		return altroCodice;
	}

	public void setAltroCodice(String altroCodice) {
		this.altroCodice = altroCodice;
	}

	public String getCodUniIpa() {
		return codUniIpa;
	}

	public void setCodUniIpa(String codUniIpa) {
		this.codUniIpa = codUniIpa;
	}

	public Long getFlagPubblicoPrivato() {
		return flagPubblicoPrivato;
	}

	public void setFlagPubblicoPrivato(Long flagPubblicoPrivato) {
		this.flagPubblicoPrivato = flagPubblicoPrivato;
	}

	public boolean isValid() {
		boolean isValid = false;
                if (isPKValid() && dtInizioValidita != null && idSoggettoFornitore != null && idTipoSoggetto != null && idUtenteIns != null && codiceFiscaleFornitore != null) {
   			isValid = true;
   		}
   		return isValid;
	}
	public boolean isPKValid() {
		boolean isPkValid = false;
		if (idFornitore != null ) {
   			isPkValid = true;
   		}

   		return isPkValid;
	}
	
	public String toString() {
		
	    String temp = "";
	    StringBuffer sb = new StringBuffer();
	    sb.append("\t\n" + this.getClass().getName() + "\t\n");
	    
	    temp = DataFilter.removeNull( cognomeFornitore);
	    if (!DataFilter.isEmpty(temp)) sb.append(" cognomeFornitore: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtInizioValidita);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtInizioValidita: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idUtenteAgg);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idUtenteAgg: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( partitaIvaFornitore);
	    if (!DataFilter.isEmpty(temp)) sb.append(" partitaIvaFornitore: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idFormaGiuridica);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idFormaGiuridica: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idSoggettoFornitore);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idSoggettoFornitore: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idTipoSoggetto);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idTipoSoggetto: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( nomeFornitore);
	    if (!DataFilter.isEmpty(temp)) sb.append(" nomeFornitore: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idAttivitaAteco);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idAttivitaAteco: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idFornitore);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idFornitore: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( denominazioneFornitore);
	    if (!DataFilter.isEmpty(temp)) sb.append(" denominazioneFornitore: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtFineValidita);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtFineValidita: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idNazione);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idNazione: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idUtenteIns);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idUtenteIns: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( codiceFiscaleFornitore);
	    if (!DataFilter.isEmpty(temp)) sb.append(" codiceFiscaleFornitore: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( codUniIpa);
	    if (!DataFilter.isEmpty(temp)) sb.append(" codUniIpa: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( flagPubblicoPrivato);
	    if (!DataFilter.isEmpty(temp)) sb.append(" flagPubblicoPrivato: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( altroCodice);
	    if (!DataFilter.isEmpty(temp)) sb.append(" altroCodice: " + temp + "\t\n");
	    
	    return sb.toString();
	}
	
	public java.util.List getPK() {
		java.util.List pk=new java.util.ArrayList();
		
			pk.add("idFornitore");
		
	    return pk;
	}
	
	
}
