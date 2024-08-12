
package it.csi.pbandi.pbweb.pbandisrv.integration.db.vo;

import java.math.*;
import java.sql.Date;
import it.csi.pbandi.pbweb.pbandisrv.integration.util.DataFilter;



public class PbandiLProcessoBatchVO extends GenericVO {

  	
  	private Date dtFineElaborazione;
  	
  	private BigDecimal idProcessoBatch;
  	
  	private String flagEsito;
  	
  	private BigDecimal idNomeBatch;
  	
  	private Date dtInizioElaborazione;
  	
	public PbandiLProcessoBatchVO() {}
  	
	public PbandiLProcessoBatchVO (BigDecimal idProcessoBatch) {
	
		this. idProcessoBatch =  idProcessoBatch;
	}
  	
	public PbandiLProcessoBatchVO (Date dtFineElaborazione, BigDecimal idProcessoBatch, String flagEsito, BigDecimal idNomeBatch, Date dtInizioElaborazione) {
	
		this. dtFineElaborazione =  dtFineElaborazione;
		this. idProcessoBatch =  idProcessoBatch;
		this. flagEsito =  flagEsito;
		this. idNomeBatch =  idNomeBatch;
		this. dtInizioElaborazione =  dtInizioElaborazione;
	}
  	
  	
	public Date getDtFineElaborazione() {
		return dtFineElaborazione;
	}
	
	public void setDtFineElaborazione(Date dtFineElaborazione) {
		this.dtFineElaborazione = dtFineElaborazione;
	}
	
	public BigDecimal getIdProcessoBatch() {
		return idProcessoBatch;
	}
	
	public void setIdProcessoBatch(BigDecimal idProcessoBatch) {
		this.idProcessoBatch = idProcessoBatch;
	}
	
	public String getFlagEsito() {
		return flagEsito;
	}
	
	public void setFlagEsito(String flagEsito) {
		this.flagEsito = flagEsito;
	}
	
	public BigDecimal getIdNomeBatch() {
		return idNomeBatch;
	}
	
	public void setIdNomeBatch(BigDecimal idNomeBatch) {
		this.idNomeBatch = idNomeBatch;
	}
	
	public Date getDtInizioElaborazione() {
		return dtInizioElaborazione;
	}
	
	public void setDtInizioElaborazione(Date dtInizioElaborazione) {
		this.dtInizioElaborazione = dtInizioElaborazione;
	}
	
	public boolean isValid() {
		boolean isValid = false;
                if (isPKValid() && idNomeBatch != null && dtInizioElaborazione != null) {
   			isValid = true;
   		}
   		return isValid;
	}
	public boolean isPKValid() {
		boolean isPkValid = false;
		if (idProcessoBatch != null ) {
   			isPkValid = true;
   		}

   		return isPkValid;
	}
	
	public String toString() {
		
	    String temp = "";
	    StringBuffer sb = new StringBuffer();
	    sb.append("\t\n" + this.getClass().getName() + "\t\n");
	    
	    temp = DataFilter.removeNull( dtFineElaborazione);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtFineElaborazione: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idProcessoBatch);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idProcessoBatch: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( flagEsito);
	    if (!DataFilter.isEmpty(temp)) sb.append(" flagEsito: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idNomeBatch);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idNomeBatch: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtInizioElaborazione);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtInizioElaborazione: " + temp + "\t\n");
	    
	    return sb.toString();
	}
	
	public java.util.List getPK() {
		java.util.List pk=new java.util.ArrayList();
		
			pk.add("idProcessoBatch");
		
	    return pk;
	}
	
	
}
