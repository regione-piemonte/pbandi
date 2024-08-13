/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/


package it.csi.pbandi.pbweb.pbandisrv.integration.db.vo;

import java.math.*;
import java.sql.Date;
import it.csi.pbandi.pbweb.pbandisrv.integration.util.DataFilter;



public class PbandiDAttivitaAtecoVO extends GenericVO {

  	
  	private BigDecimal idAttivitaEconomica;
  	
  	private String descAteco;
  	
  	private Date dtFineValidita;
  	
  	private String codAteco;
  	
  	private BigDecimal idSettoreAttivita;
  	
  	private Date dtInizioValidita;
  	
  	private BigDecimal idAttivitaAteco;
  	
	public PbandiDAttivitaAtecoVO() {}
  	
	public PbandiDAttivitaAtecoVO (BigDecimal idAttivitaAteco) {
	
		this. idAttivitaAteco =  idAttivitaAteco;
	}
  	
	public PbandiDAttivitaAtecoVO (BigDecimal idAttivitaEconomica, String descAteco, Date dtFineValidita, String codAteco, BigDecimal idSettoreAttivita, Date dtInizioValidita, BigDecimal idAttivitaAteco) {
	
		this. idAttivitaEconomica =  idAttivitaEconomica;
		this. descAteco =  descAteco;
		this. dtFineValidita =  dtFineValidita;
		this. codAteco =  codAteco;
		this. idSettoreAttivita =  idSettoreAttivita;
		this. dtInizioValidita =  dtInizioValidita;
		this. idAttivitaAteco =  idAttivitaAteco;
	}
  	
  	
	public BigDecimal getIdAttivitaEconomica() {
		return idAttivitaEconomica;
	}
	
	public void setIdAttivitaEconomica(BigDecimal idAttivitaEconomica) {
		this.idAttivitaEconomica = idAttivitaEconomica;
	}
	
	public String getDescAteco() {
		return descAteco;
	}
	
	public void setDescAteco(String descAteco) {
		this.descAteco = descAteco;
	}
	
	public Date getDtFineValidita() {
		return dtFineValidita;
	}
	
	public void setDtFineValidita(Date dtFineValidita) {
		this.dtFineValidita = dtFineValidita;
	}
	
	public String getCodAteco() {
		return codAteco;
	}
	
	public void setCodAteco(String codAteco) {
		this.codAteco = codAteco;
	}
	
	public BigDecimal getIdSettoreAttivita() {
		return idSettoreAttivita;
	}
	
	public void setIdSettoreAttivita(BigDecimal idSettoreAttivita) {
		this.idSettoreAttivita = idSettoreAttivita;
	}
	
	public Date getDtInizioValidita() {
		return dtInizioValidita;
	}
	
	public void setDtInizioValidita(Date dtInizioValidita) {
		this.dtInizioValidita = dtInizioValidita;
	}
	
	public BigDecimal getIdAttivitaAteco() {
		return idAttivitaAteco;
	}
	
	public void setIdAttivitaAteco(BigDecimal idAttivitaAteco) {
		this.idAttivitaAteco = idAttivitaAteco;
	}
	
	public boolean isValid() {
		boolean isValid = false;
                if (isPKValid() && idAttivitaEconomica != null && descAteco != null && codAteco != null && dtInizioValidita != null) {
   			isValid = true;
   		}
   		return isValid;
	}
	public boolean isPKValid() {
		boolean isPkValid = false;
		if (idAttivitaAteco != null ) {
   			isPkValid = true;
   		}

   		return isPkValid;
	}
	
	public String toString() {
		
	    String temp = "";
	    StringBuffer sb = new StringBuffer();
	    sb.append("\t\n" + this.getClass().getName() + "\t\n");
	    
	    temp = DataFilter.removeNull( idAttivitaEconomica);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idAttivitaEconomica: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( descAteco);
	    if (!DataFilter.isEmpty(temp)) sb.append(" descAteco: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtFineValidita);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtFineValidita: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( codAteco);
	    if (!DataFilter.isEmpty(temp)) sb.append(" codAteco: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idSettoreAttivita);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idSettoreAttivita: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtInizioValidita);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtInizioValidita: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idAttivitaAteco);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idAttivitaAteco: " + temp + "\t\n");
	    
	    return sb.toString();
	}
	
	public java.util.List<String> getPK() {
		java.util.List<String> pk=new java.util.ArrayList<String>();
		
			pk.add("idAttivitaAteco");
		
	    return pk;
	}
	
	
}
