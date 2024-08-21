/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/


package it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo;

import java.math.*;
import java.sql.Date;
import it.csi.pbandi.pbservizit.pbandisrv.integration.util.DataFilter;



public class PbandiRPermessoTipoAnagrafVO extends GenericVO {

  	
  	private BigDecimal idTipoAnagrafica;
  	
  	private Date dtFineValidita;
  	
  	private Date dtInizioValidita;
  	
  	private BigDecimal idPermesso;
  	
	public PbandiRPermessoTipoAnagrafVO() {}
  	
	public PbandiRPermessoTipoAnagrafVO (BigDecimal idTipoAnagrafica, BigDecimal idPermesso) {
	
		this. idTipoAnagrafica =  idTipoAnagrafica;
		this. idPermesso =  idPermesso;
	}
  	
	public PbandiRPermessoTipoAnagrafVO (BigDecimal idTipoAnagrafica, Date dtFineValidita, Date dtInizioValidita, BigDecimal idPermesso) {
	
		this. idTipoAnagrafica =  idTipoAnagrafica;
		this. dtFineValidita =  dtFineValidita;
		this. dtInizioValidita =  dtInizioValidita;
		this. idPermesso =  idPermesso;
	}
  	
  	
	public BigDecimal getIdTipoAnagrafica() {
		return idTipoAnagrafica;
	}
	
	public void setIdTipoAnagrafica(BigDecimal idTipoAnagrafica) {
		this.idTipoAnagrafica = idTipoAnagrafica;
	}
	
	public Date getDtFineValidita() {
		return dtFineValidita;
	}
	
	public void setDtFineValidita(Date dtFineValidita) {
		this.dtFineValidita = dtFineValidita;
	}
	
	public Date getDtInizioValidita() {
		return dtInizioValidita;
	}
	
	public void setDtInizioValidita(Date dtInizioValidita) {
		this.dtInizioValidita = dtInizioValidita;
	}
	
	public BigDecimal getIdPermesso() {
		return idPermesso;
	}
	
	public void setIdPermesso(BigDecimal idPermesso) {
		this.idPermesso = idPermesso;
	}
	
	public boolean isValid() {
		boolean isValid = false;
                if (isPKValid() && dtInizioValidita != null) {
   			isValid = true;
   		}
   		return isValid;
	}
	public boolean isPKValid() {
		boolean isPkValid = false;
		if (idTipoAnagrafica != null && idPermesso != null ) {
   			isPkValid = true;
   		}

   		return isPkValid;
	}
	
	public String toString() {
		
	    String temp = "";
	    StringBuffer sb = new StringBuffer();
	    sb.append("\t\n" + this.getClass().getName() + "\t\n");
	    
	    temp = DataFilter.removeNull( idTipoAnagrafica);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idTipoAnagrafica: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtFineValidita);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtFineValidita: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtInizioValidita);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtInizioValidita: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idPermesso);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idPermesso: " + temp + "\t\n");
	    
	    return sb.toString();
	}
	
	public java.util.List<String> getPK() {
		java.util.List<String> pk=new java.util.ArrayList<String>();
		
			pk.add("idTipoAnagrafica");
		
			pk.add("idPermesso");
		
	    return pk;
	}
	
	
}
