/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/


package it.csi.pbandi.pbwebrce.pbandisrv.integration.db.vo;

import java.math.*;
import java.sql.Date;
import it.csi.pbandi.pbwebrce.pbandisrv.integration.util.DataFilter;



public class PbandiDStatoDocumentoSpesaVO extends GenericVO {

  	
  	private BigDecimal idStatoDocumentoSpesa;
  	
  	private Date dtFineValidita;
  	
  	private String descStatoDocumentoSpesa;
  	
  	private String descBreveStatoDocSpesa;
  	
  	private Date dtInizioValidita;
  	
	public PbandiDStatoDocumentoSpesaVO() {}
  	
	public PbandiDStatoDocumentoSpesaVO (BigDecimal idStatoDocumentoSpesa) {
	
		this. idStatoDocumentoSpesa =  idStatoDocumentoSpesa;
	}
  	
	public PbandiDStatoDocumentoSpesaVO (BigDecimal idStatoDocumentoSpesa, Date dtFineValidita, String descStatoDocumentoSpesa, String descBreveStatoDocSpesa, Date dtInizioValidita) {
	
		this. idStatoDocumentoSpesa =  idStatoDocumentoSpesa;
		this. dtFineValidita =  dtFineValidita;
		this. descStatoDocumentoSpesa =  descStatoDocumentoSpesa;
		this. descBreveStatoDocSpesa =  descBreveStatoDocSpesa;
		this. dtInizioValidita =  dtInizioValidita;
	}
  	
  	
	public BigDecimal getIdStatoDocumentoSpesa() {
		return idStatoDocumentoSpesa;
	}
	
	public void setIdStatoDocumentoSpesa(BigDecimal idStatoDocumentoSpesa) {
		this.idStatoDocumentoSpesa = idStatoDocumentoSpesa;
	}
	
	public Date getDtFineValidita() {
		return dtFineValidita;
	}
	
	public void setDtFineValidita(Date dtFineValidita) {
		this.dtFineValidita = dtFineValidita;
	}
	
	public String getDescStatoDocumentoSpesa() {
		return descStatoDocumentoSpesa;
	}
	
	public void setDescStatoDocumentoSpesa(String descStatoDocumentoSpesa) {
		this.descStatoDocumentoSpesa = descStatoDocumentoSpesa;
	}
	
	public String getDescBreveStatoDocSpesa() {
		return descBreveStatoDocSpesa;
	}
	
	public void setDescBreveStatoDocSpesa(String descBreveStatoDocSpesa) {
		this.descBreveStatoDocSpesa = descBreveStatoDocSpesa;
	}
	
	public Date getDtInizioValidita() {
		return dtInizioValidita;
	}
	
	public void setDtInizioValidita(Date dtInizioValidita) {
		this.dtInizioValidita = dtInizioValidita;
	}
	
	public boolean isValid() {
		boolean isValid = false;
                if (isPKValid() && descStatoDocumentoSpesa != null && descBreveStatoDocSpesa != null && dtInizioValidita != null) {
   			isValid = true;
   		}
   		return isValid;
	}
	public boolean isPKValid() {
		boolean isPkValid = false;
		if (idStatoDocumentoSpesa != null ) {
   			isPkValid = true;
   		}

   		return isPkValid;
	}
	
	public String toString() {
		
	    String temp = "";
	    StringBuffer sb = new StringBuffer();
	    sb.append("\t\n" + this.getClass().getName() + "\t\n");
	    
	    temp = DataFilter.removeNull( idStatoDocumentoSpesa);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idStatoDocumentoSpesa: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtFineValidita);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtFineValidita: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( descStatoDocumentoSpesa);
	    if (!DataFilter.isEmpty(temp)) sb.append(" descStatoDocumentoSpesa: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( descBreveStatoDocSpesa);
	    if (!DataFilter.isEmpty(temp)) sb.append(" descBreveStatoDocSpesa: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtInizioValidita);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtInizioValidita: " + temp + "\t\n");
	    
	    return sb.toString();
	}
	
	public java.util.List<String> getPK() {
		java.util.List<String> pk=new java.util.ArrayList<String>();
		
			pk.add("idStatoDocumentoSpesa");
		
	    return pk;
	}
	
	
}
