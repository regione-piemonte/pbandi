/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/


package it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo;

import java.math.*;
import java.sql.Date;
import it.csi.pbandi.pbservizit.pbandisrv.integration.util.DataFilter;



public class PbandiDTipoSoggCorrelatoVO extends GenericVO {

  	
  	private String descBreveTipoSoggCorrelato;
  	
  	private Date dtFineValidita;
  	
  	private String flagVisibile;
  	
  	private String codGefo;
  	
  	private Date dtInizioValidita;
  	
  	private String descTipoSoggettoCorrelato;
  	
  	private BigDecimal idTipoSoggettoCorrelato;
  	
	public PbandiDTipoSoggCorrelatoVO() {}
  	
	public PbandiDTipoSoggCorrelatoVO (BigDecimal idTipoSoggettoCorrelato) {
	
		this. idTipoSoggettoCorrelato =  idTipoSoggettoCorrelato;
	}
  	
	public PbandiDTipoSoggCorrelatoVO (String descBreveTipoSoggCorrelato, Date dtFineValidita, String flagVisibile, String codGefo, Date dtInizioValidita, String descTipoSoggettoCorrelato, BigDecimal idTipoSoggettoCorrelato) {
	
		this. descBreveTipoSoggCorrelato =  descBreveTipoSoggCorrelato;
		this. dtFineValidita =  dtFineValidita;
		this. flagVisibile =  flagVisibile;
		this. codGefo =  codGefo;
		this. dtInizioValidita =  dtInizioValidita;
		this. descTipoSoggettoCorrelato =  descTipoSoggettoCorrelato;
		this. idTipoSoggettoCorrelato =  idTipoSoggettoCorrelato;
	}
  	
  	
	public String getDescBreveTipoSoggCorrelato() {
		return descBreveTipoSoggCorrelato;
	}
	
	public void setDescBreveTipoSoggCorrelato(String descBreveTipoSoggCorrelato) {
		this.descBreveTipoSoggCorrelato = descBreveTipoSoggCorrelato;
	}
	
	public Date getDtFineValidita() {
		return dtFineValidita;
	}
	
	public void setDtFineValidita(Date dtFineValidita) {
		this.dtFineValidita = dtFineValidita;
	}
	
	public String getFlagVisibile() {
		return flagVisibile;
	}
	
	public void setFlagVisibile(String flagVisibile) {
		this.flagVisibile = flagVisibile;
	}
	
	public String getCodGefo() {
		return codGefo;
	}
	
	public void setCodGefo(String codGefo) {
		this.codGefo = codGefo;
	}
	
	public Date getDtInizioValidita() {
		return dtInizioValidita;
	}
	
	public void setDtInizioValidita(Date dtInizioValidita) {
		this.dtInizioValidita = dtInizioValidita;
	}
	
	public String getDescTipoSoggettoCorrelato() {
		return descTipoSoggettoCorrelato;
	}
	
	public void setDescTipoSoggettoCorrelato(String descTipoSoggettoCorrelato) {
		this.descTipoSoggettoCorrelato = descTipoSoggettoCorrelato;
	}
	
	public BigDecimal getIdTipoSoggettoCorrelato() {
		return idTipoSoggettoCorrelato;
	}
	
	public void setIdTipoSoggettoCorrelato(BigDecimal idTipoSoggettoCorrelato) {
		this.idTipoSoggettoCorrelato = idTipoSoggettoCorrelato;
	}
	
	public boolean isValid() {
		boolean isValid = false;
                if (isPKValid() && descBreveTipoSoggCorrelato != null && flagVisibile != null && dtInizioValidita != null && descTipoSoggettoCorrelato != null) {
   			isValid = true;
   		}
   		return isValid;
	}
	public boolean isPKValid() {
		boolean isPkValid = false;
		if (idTipoSoggettoCorrelato != null ) {
   			isPkValid = true;
   		}

   		return isPkValid;
	}
	
	public String toString() {
		
	    String temp = "";
	    StringBuffer sb = new StringBuffer();
	    sb.append("\t\n" + this.getClass().getName() + "\t\n");
	    
	    temp = DataFilter.removeNull( descBreveTipoSoggCorrelato);
	    if (!DataFilter.isEmpty(temp)) sb.append(" descBreveTipoSoggCorrelato: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtFineValidita);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtFineValidita: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( flagVisibile);
	    if (!DataFilter.isEmpty(temp)) sb.append(" flagVisibile: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( codGefo);
	    if (!DataFilter.isEmpty(temp)) sb.append(" codGefo: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtInizioValidita);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtInizioValidita: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( descTipoSoggettoCorrelato);
	    if (!DataFilter.isEmpty(temp)) sb.append(" descTipoSoggettoCorrelato: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idTipoSoggettoCorrelato);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idTipoSoggettoCorrelato: " + temp + "\t\n");
	    
	    return sb.toString();
	}
	
	public java.util.List<String> getPK() {
		java.util.List<String> pk=new java.util.ArrayList<String>();
		
			pk.add("idTipoSoggettoCorrelato");
		
	    return pk;
	}
	
	
}
