/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/


package it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo;

import java.math.*;
import java.sql.Date;
import it.csi.pbandi.pbservizit.pbandisrv.integration.util.DataFilter;



public class PbandiTAgenziaVO extends GenericVO {

  	
  	private Date dtInizioValidita;
  	
  	private BigDecimal idUtenteAgg;
  	
  	private String descAgenzia;
  	
  	private BigDecimal idIndirizzo;
  	
  	private String codAgenzia;
  	
  	private BigDecimal idAgenzia;
  	
  	private Date dtFineValidita;
  	
  	private BigDecimal idUtenteIns;
  	
  	private BigDecimal idBanca;
  	
	public PbandiTAgenziaVO() {}
  	
	public PbandiTAgenziaVO (BigDecimal idAgenzia) {
	
		this. idAgenzia =  idAgenzia;
	}
  	
	public PbandiTAgenziaVO (Date dtInizioValidita, BigDecimal idUtenteAgg, String descAgenzia, BigDecimal idIndirizzo, String codAgenzia, BigDecimal idAgenzia, Date dtFineValidita, BigDecimal idUtenteIns, BigDecimal idBanca) {
	
		this. dtInizioValidita =  dtInizioValidita;
		this. idUtenteAgg =  idUtenteAgg;
		this. descAgenzia =  descAgenzia;
		this. idIndirizzo =  idIndirizzo;
		this. codAgenzia =  codAgenzia;
		this. idAgenzia =  idAgenzia;
		this. dtFineValidita =  dtFineValidita;
		this. idUtenteIns =  idUtenteIns;
		this. idBanca =  idBanca;
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
	
	public String getDescAgenzia() {
		return descAgenzia;
	}
	
	public void setDescAgenzia(String descAgenzia) {
		this.descAgenzia = descAgenzia;
	}
	
	public BigDecimal getIdIndirizzo() {
		return idIndirizzo;
	}
	
	public void setIdIndirizzo(BigDecimal idIndirizzo) {
		this.idIndirizzo = idIndirizzo;
	}
	
	public String getCodAgenzia() {
		return codAgenzia;
	}
	
	public void setCodAgenzia(String codAgenzia) {
		this.codAgenzia = codAgenzia;
	}
	
	public BigDecimal getIdAgenzia() {
		return idAgenzia;
	}
	
	public void setIdAgenzia(BigDecimal idAgenzia) {
		this.idAgenzia = idAgenzia;
	}
	
	public Date getDtFineValidita() {
		return dtFineValidita;
	}
	
	public void setDtFineValidita(Date dtFineValidita) {
		this.dtFineValidita = dtFineValidita;
	}
	
	public BigDecimal getIdUtenteIns() {
		return idUtenteIns;
	}
	
	public void setIdUtenteIns(BigDecimal idUtenteIns) {
		this.idUtenteIns = idUtenteIns;
	}
	
	public BigDecimal getIdBanca() {
		return idBanca;
	}
	
	public void setIdBanca(BigDecimal idBanca) {
		this.idBanca = idBanca;
	}
	
	public boolean isValid() {
		boolean isValid = false;
                if (isPKValid() && dtInizioValidita != null && codAgenzia != null && idUtenteIns != null && idBanca != null) {
   			isValid = true;
   		}
   		return isValid;
	}
	public boolean isPKValid() {
		boolean isPkValid = false;
		if (idAgenzia != null ) {
   			isPkValid = true;
   		}

   		return isPkValid;
	}
	
	public String toString() {
		
	    String temp = "";
	    StringBuffer sb = new StringBuffer();
	    sb.append("\t\n" + this.getClass().getName() + "\t\n");
	    
	    temp = DataFilter.removeNull( dtInizioValidita);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtInizioValidita: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idUtenteAgg);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idUtenteAgg: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( descAgenzia);
	    if (!DataFilter.isEmpty(temp)) sb.append(" descAgenzia: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idIndirizzo);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idIndirizzo: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( codAgenzia);
	    if (!DataFilter.isEmpty(temp)) sb.append(" codAgenzia: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idAgenzia);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idAgenzia: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtFineValidita);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtFineValidita: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idUtenteIns);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idUtenteIns: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idBanca);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idBanca: " + temp + "\t\n");
	    
	    return sb.toString();
	}
	
	public java.util.List<String> getPK() {
		java.util.List<String> pk=new java.util.ArrayList<String>();
		
			pk.add("idAgenzia");
		
	    return pk;
	}
	
	
}
