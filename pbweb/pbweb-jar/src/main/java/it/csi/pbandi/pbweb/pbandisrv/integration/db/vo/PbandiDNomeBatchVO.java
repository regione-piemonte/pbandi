
package it.csi.pbandi.pbweb.pbandisrv.integration.db.vo;

import java.math.*;
import java.sql.Date;
import it.csi.pbandi.pbweb.pbandisrv.integration.util.DataFilter;



public class PbandiDNomeBatchVO extends GenericVO {

  	
  	private String descBatch;
  	
  	private BigDecimal idNomeBatch;
  	
  	private String nomeBatch;
  	
	public PbandiDNomeBatchVO() {}
  	
	public PbandiDNomeBatchVO (BigDecimal idNomeBatch) {
	
		this. idNomeBatch =  idNomeBatch;
	}
  	
	public PbandiDNomeBatchVO (String descBatch, BigDecimal idNomeBatch, String nomeBatch) {
	
		this. descBatch =  descBatch;
		this. idNomeBatch =  idNomeBatch;
		this. nomeBatch =  nomeBatch;
	}
  	
  	
	public String getDescBatch() {
		return descBatch;
	}
	
	public void setDescBatch(String descBatch) {
		this.descBatch = descBatch;
	}
	
	public BigDecimal getIdNomeBatch() {
		return idNomeBatch;
	}
	
	public void setIdNomeBatch(BigDecimal idNomeBatch) {
		this.idNomeBatch = idNomeBatch;
	}
	
	public String getNomeBatch() {
		return nomeBatch;
	}
	
	public void setNomeBatch(String nomeBatch) {
		this.nomeBatch = nomeBatch;
	}
	
	public boolean isValid() {
		boolean isValid = false;
                if (isPKValid() && descBatch != null && nomeBatch != null) {
   			isValid = true;
   		}
   		return isValid;
	}
	public boolean isPKValid() {
		boolean isPkValid = false;
		if (idNomeBatch != null ) {
   			isPkValid = true;
   		}

   		return isPkValid;
	}
	
	public String toString() {
		
	    String temp = "";
	    StringBuffer sb = new StringBuffer();
	    sb.append("\t\n" + this.getClass().getName() + "\t\n");
	    
	    temp = DataFilter.removeNull( descBatch);
	    if (!DataFilter.isEmpty(temp)) sb.append(" descBatch: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idNomeBatch);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idNomeBatch: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( nomeBatch);
	    if (!DataFilter.isEmpty(temp)) sb.append(" nomeBatch: " + temp + "\t\n");
	    
	    return sb.toString();
	}
	
	public java.util.List<String> getPK() {
		java.util.List<String> pk=new java.util.ArrayList<String>();
		
			pk.add("idNomeBatch");
		
	    return pk;
	}
	
	
}
