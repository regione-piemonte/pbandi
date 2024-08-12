
package it.csi.pbandi.pbweb.pbandisrv.integration.db.vo;

import java.math.*;
import java.sql.Date;
import it.csi.pbandi.pbweb.pbandisrv.integration.util.DataFilter;



public class PbandiDStatoDocumentoIndexVO extends GenericVO {

  	
  	private String descrizione;
  	
  	private BigDecimal idStatoDocumento;
  	
  	private String descBreve;
  	
	public PbandiDStatoDocumentoIndexVO() {}
  	
	public PbandiDStatoDocumentoIndexVO (BigDecimal idStatoDocumento) {
	
		this. idStatoDocumento =  idStatoDocumento;
	}
  	
	public PbandiDStatoDocumentoIndexVO (String descrizione, BigDecimal idStatoDocumento, String descBreve) {
	
		this. descrizione =  descrizione;
		this. idStatoDocumento =  idStatoDocumento;
		this. descBreve =  descBreve;
	}
  	
  	
	public String getDescrizione() {
		return descrizione;
	}
	
	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}
	
	public BigDecimal getIdStatoDocumento() {
		return idStatoDocumento;
	}
	
	public void setIdStatoDocumento(BigDecimal idStatoDocumento) {
		this.idStatoDocumento = idStatoDocumento;
	}
	
	public String getDescBreve() {
		return descBreve;
	}
	
	public void setDescBreve(String descBreve) {
		this.descBreve = descBreve;
	}
	
	public boolean isValid() {
		boolean isValid = false;
                if (isPKValid()) {
   			isValid = true;
   		}
   		return isValid;
	}
	public boolean isPKValid() {
		boolean isPkValid = false;
		if (idStatoDocumento != null ) {
   			isPkValid = true;
   		}

   		return isPkValid;
	}
	
	public String toString() {
		
	    String temp = "";
	    StringBuffer sb = new StringBuffer();
	    sb.append("\t\n" + this.getClass().getName() + "\t\n");
	    
	    temp = DataFilter.removeNull( descrizione);
	    if (!DataFilter.isEmpty(temp)) sb.append(" descrizione: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idStatoDocumento);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idStatoDocumento: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( descBreve);
	    if (!DataFilter.isEmpty(temp)) sb.append(" descBreve: " + temp + "\t\n");
	    
	    return sb.toString();
	}
	
	public java.util.List getPK() {
		java.util.List pk=new java.util.ArrayList();
		
			pk.add("idStatoDocumento");
		
	    return pk;
	}
	
	
}
