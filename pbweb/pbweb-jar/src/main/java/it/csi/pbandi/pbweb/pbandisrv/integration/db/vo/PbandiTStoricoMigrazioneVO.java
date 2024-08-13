/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/


package it.csi.pbandi.pbweb.pbandisrv.integration.db.vo;

import java.math.*;
import java.sql.Date;
import it.csi.pbandi.pbweb.pbandisrv.integration.util.DataFilter;



public class PbandiTStoricoMigrazioneVO extends GenericVO {

  	
  	private BigDecimal idStoricoMigrazione;
  	
  	private BigDecimal idUtenteAgg;
  	
  	private String uuidProcesso;
  	
  	private Date dtInizioValidita;
  	
  	private Date dtInizioStoricizzazione;
  	
  	private Date dtFineStoricizzazione;
  	
  	private BigDecimal idProgetto;
  	
  	private Date dtFineValidita;
  	
  	private String idIstanzaProcesso;
  	
  	private BigDecimal idUtenteIns;
  	
  	private String idNuovaIstanza;
  	
	public PbandiTStoricoMigrazioneVO() {}
  	
	public PbandiTStoricoMigrazioneVO (BigDecimal idStoricoMigrazione) {
	
		this. idStoricoMigrazione =  idStoricoMigrazione;
	}
  	
	public PbandiTStoricoMigrazioneVO (BigDecimal idStoricoMigrazione, BigDecimal idUtenteAgg, String uuidProcesso, Date dtInizioValidita, Date dtInizioStoricizzazione, Date dtFineStoricizzazione, BigDecimal idProgetto, Date dtFineValidita, String idIstanzaProcesso, BigDecimal idUtenteIns, String idNuovaIstanza) {
	
		this. idStoricoMigrazione =  idStoricoMigrazione;
		this. idUtenteAgg =  idUtenteAgg;
		this. uuidProcesso =  uuidProcesso;
		this. dtInizioValidita =  dtInizioValidita;
		this. dtInizioStoricizzazione =  dtInizioStoricizzazione;
		this. dtFineStoricizzazione =  dtFineStoricizzazione;
		this. idProgetto =  idProgetto;
		this. dtFineValidita =  dtFineValidita;
		this. idIstanzaProcesso =  idIstanzaProcesso;
		this. idUtenteIns =  idUtenteIns;
		this. idNuovaIstanza =  idNuovaIstanza;
	}
  	
  	
	public BigDecimal getIdStoricoMigrazione() {
		return idStoricoMigrazione;
	}
	
	public void setIdStoricoMigrazione(BigDecimal idStoricoMigrazione) {
		this.idStoricoMigrazione = idStoricoMigrazione;
	}
	
	public BigDecimal getIdUtenteAgg() {
		return idUtenteAgg;
	}
	
	public void setIdUtenteAgg(BigDecimal idUtenteAgg) {
		this.idUtenteAgg = idUtenteAgg;
	}
	
	public String getUuidProcesso() {
		return uuidProcesso;
	}
	
	public void setUuidProcesso(String uuidProcesso) {
		this.uuidProcesso = uuidProcesso;
	}
	
	public Date getDtInizioValidita() {
		return dtInizioValidita;
	}
	
	public void setDtInizioValidita(Date dtInizioValidita) {
		this.dtInizioValidita = dtInizioValidita;
	}
	
	public Date getDtInizioStoricizzazione() {
		return dtInizioStoricizzazione;
	}
	
	public void setDtInizioStoricizzazione(Date dtInizioStoricizzazione) {
		this.dtInizioStoricizzazione = dtInizioStoricizzazione;
	}
	
	public Date getDtFineStoricizzazione() {
		return dtFineStoricizzazione;
	}
	
	public void setDtFineStoricizzazione(Date dtFineStoricizzazione) {
		this.dtFineStoricizzazione = dtFineStoricizzazione;
	}
	
	public BigDecimal getIdProgetto() {
		return idProgetto;
	}
	
	public void setIdProgetto(BigDecimal idProgetto) {
		this.idProgetto = idProgetto;
	}
	
	public Date getDtFineValidita() {
		return dtFineValidita;
	}
	
	public void setDtFineValidita(Date dtFineValidita) {
		this.dtFineValidita = dtFineValidita;
	}
	
	public String getIdIstanzaProcesso() {
		return idIstanzaProcesso;
	}
	
	public void setIdIstanzaProcesso(String idIstanzaProcesso) {
		this.idIstanzaProcesso = idIstanzaProcesso;
	}
	
	public BigDecimal getIdUtenteIns() {
		return idUtenteIns;
	}
	
	public void setIdUtenteIns(BigDecimal idUtenteIns) {
		this.idUtenteIns = idUtenteIns;
	}
	
	public String getIdNuovaIstanza() {
		return idNuovaIstanza;
	}
	
	public void setIdNuovaIstanza(String idNuovaIstanza) {
		this.idNuovaIstanza = idNuovaIstanza;
	}
	
	public boolean isValid() {
		boolean isValid = false;
                if (isPKValid() && uuidProcesso != null && dtInizioValidita != null && dtInizioStoricizzazione != null && idProgetto != null && idIstanzaProcesso != null && idUtenteIns != null) {
   			isValid = true;
   		}
   		return isValid;
	}
	public boolean isPKValid() {
		boolean isPkValid = false;
		if (idStoricoMigrazione != null ) {
   			isPkValid = true;
   		}

   		return isPkValid;
	}
	
	public String toString() {
		
	    String temp = "";
	    StringBuffer sb = new StringBuffer();
	    sb.append("\t\n" + this.getClass().getName() + "\t\n");
	    
	    temp = DataFilter.removeNull( idStoricoMigrazione);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idStoricoMigrazione: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idUtenteAgg);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idUtenteAgg: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( uuidProcesso);
	    if (!DataFilter.isEmpty(temp)) sb.append(" uuidProcesso: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtInizioValidita);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtInizioValidita: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtInizioStoricizzazione);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtInizioStoricizzazione: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtFineStoricizzazione);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtFineStoricizzazione: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idProgetto);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idProgetto: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtFineValidita);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtFineValidita: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idIstanzaProcesso);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idIstanzaProcesso: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idUtenteIns);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idUtenteIns: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idNuovaIstanza);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idNuovaIstanza: " + temp + "\t\n");
	    
	    return sb.toString();
	}
	
	public java.util.List getPK() {
		java.util.List pk=new java.util.ArrayList();
		
			pk.add("idStoricoMigrazione");
		
	    return pk;
	}
	
	
}
