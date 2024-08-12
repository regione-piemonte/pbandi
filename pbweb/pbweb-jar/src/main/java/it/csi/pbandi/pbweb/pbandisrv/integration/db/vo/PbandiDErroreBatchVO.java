
package it.csi.pbandi.pbweb.pbandisrv.integration.db.vo;

import java.math.*;
import java.sql.Date;
import it.csi.pbandi.pbweb.pbandisrv.integration.util.DataFilter;



public class PbandiDErroreBatchVO extends GenericVO {

  	
  	private String descrizione;
  	
  	private String codiceErrore;
  	
	public PbandiDErroreBatchVO() {}
  	
	public PbandiDErroreBatchVO (String codiceErrore) {
	
		this. codiceErrore =  codiceErrore;
	}
  	
	public PbandiDErroreBatchVO (String descrizione, String codiceErrore) {
	
		this. descrizione =  descrizione;
		this. codiceErrore =  codiceErrore;
	}
  	
  	
	public String getDescrizione() {
		return descrizione;
	}
	
	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}
	
	public String getCodiceErrore() {
		return codiceErrore;
	}
	
	public void setCodiceErrore(String codiceErrore) {
		this.codiceErrore = codiceErrore;
	}
	
	public boolean isValid() {
		boolean isValid = false;
                if (isPKValid() && descrizione != null) {
   			isValid = true;
   		}
   		return isValid;
	}
	public boolean isPKValid() {
		boolean isPkValid = false;
		if (codiceErrore != null ) {
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
	    
	    temp = DataFilter.removeNull( codiceErrore);
	    if (!DataFilter.isEmpty(temp)) sb.append(" codiceErrore: " + temp + "\t\n");
	    
	    return sb.toString();
	}
	
	public java.util.List<String> getPK() {
		java.util.List<String> pk=new java.util.ArrayList<String>();
		
			pk.add("codiceErrore");
		
	    return pk;
	}
	
	
}
