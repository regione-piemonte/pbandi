/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/


package it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo;

import java.math.*;
import java.sql.Date;
import it.csi.pbandi.pbservizit.pbandisrv.integration.util.DataFilter;



public class PbandiDTipoStrumentoProgrVO extends GenericVO {

  	
  	private String descStrumento;
  	
  	private Date dtFineValidita;
  	
  	private String codStrumento;
  	
  	private BigDecimal idTipoStrumentoProgr;
  	
  	private Date dtInizioValidita;
  	
	public PbandiDTipoStrumentoProgrVO() {}
  	
	public PbandiDTipoStrumentoProgrVO (BigDecimal idTipoStrumentoProgr) {
	
		this. idTipoStrumentoProgr =  idTipoStrumentoProgr;
	}
  	
	public PbandiDTipoStrumentoProgrVO (String descStrumento, Date dtFineValidita, String codStrumento, BigDecimal idTipoStrumentoProgr, Date dtInizioValidita) {
	
		this. descStrumento =  descStrumento;
		this. dtFineValidita =  dtFineValidita;
		this. codStrumento =  codStrumento;
		this. idTipoStrumentoProgr =  idTipoStrumentoProgr;
		this. dtInizioValidita =  dtInizioValidita;
	}
  	
  	
	public String getDescStrumento() {
		return descStrumento;
	}
	
	public void setDescStrumento(String descStrumento) {
		this.descStrumento = descStrumento;
	}
	
	public Date getDtFineValidita() {
		return dtFineValidita;
	}
	
	public void setDtFineValidita(Date dtFineValidita) {
		this.dtFineValidita = dtFineValidita;
	}
	
	public String getCodStrumento() {
		return codStrumento;
	}
	
	public void setCodStrumento(String codStrumento) {
		this.codStrumento = codStrumento;
	}
	
	public BigDecimal getIdTipoStrumentoProgr() {
		return idTipoStrumentoProgr;
	}
	
	public void setIdTipoStrumentoProgr(BigDecimal idTipoStrumentoProgr) {
		this.idTipoStrumentoProgr = idTipoStrumentoProgr;
	}
	
	public Date getDtInizioValidita() {
		return dtInizioValidita;
	}
	
	public void setDtInizioValidita(Date dtInizioValidita) {
		this.dtInizioValidita = dtInizioValidita;
	}
	
	public boolean isValid() {
		boolean isValid = false;
                if (isPKValid() && descStrumento != null && codStrumento != null && dtInizioValidita != null) {
   			isValid = true;
   		}
   		return isValid;
	}
	public boolean isPKValid() {
		boolean isPkValid = false;
		if (idTipoStrumentoProgr != null ) {
   			isPkValid = true;
   		}

   		return isPkValid;
	}
	
	public String toString() {
		
	    String temp = "";
	    StringBuffer sb = new StringBuffer();
	    sb.append("\t\n" + this.getClass().getName() + "\t\n");
	    
	    temp = DataFilter.removeNull( descStrumento);
	    if (!DataFilter.isEmpty(temp)) sb.append(" descStrumento: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtFineValidita);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtFineValidita: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( codStrumento);
	    if (!DataFilter.isEmpty(temp)) sb.append(" codStrumento: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idTipoStrumentoProgr);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idTipoStrumentoProgr: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtInizioValidita);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtInizioValidita: " + temp + "\t\n");
	    
	    return sb.toString();
	}
	
	public java.util.List<String> getPK() {
		java.util.List<String> pk=new java.util.ArrayList<String>();
		
			pk.add("idTipoStrumentoProgr");
		
	    return pk;
	}
	
	
}
