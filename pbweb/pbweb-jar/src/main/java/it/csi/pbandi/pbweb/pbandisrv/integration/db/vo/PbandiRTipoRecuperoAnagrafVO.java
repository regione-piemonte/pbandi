
package it.csi.pbandi.pbweb.pbandisrv.integration.db.vo;

import java.math.*;
import java.sql.Date;
import it.csi.pbandi.pbweb.pbandisrv.integration.util.DataFilter;



public class PbandiRTipoRecuperoAnagrafVO extends GenericVO {

  	
  	private BigDecimal idTipoAnagrafica;
  	
  	private BigDecimal idTipoRecupero;
  	
	public PbandiRTipoRecuperoAnagrafVO() {}
  	
	public PbandiRTipoRecuperoAnagrafVO (BigDecimal idTipoAnagrafica, BigDecimal idTipoRecupero) {
	
		this. idTipoAnagrafica =  idTipoAnagrafica;
		this. idTipoRecupero =  idTipoRecupero;
	}
  	
	public BigDecimal getIdTipoAnagrafica() {
		return idTipoAnagrafica;
	}
	
	public void setIdTipoAnagrafica(BigDecimal idTipoAnagrafica) {
		this.idTipoAnagrafica = idTipoAnagrafica;
	}
	
	public BigDecimal getIdTipoRecupero() {
		return idTipoRecupero;
	}
	
	public void setIdTipoRecupero(BigDecimal idTipoRecupero) {
		this.idTipoRecupero = idTipoRecupero;
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
		if (idTipoAnagrafica != null && idTipoRecupero != null ) {
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
	    
	    temp = DataFilter.removeNull( idTipoRecupero);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idTipoRecupero: " + temp + "\t\n");
	    
	    return sb.toString();
	}
	
	public java.util.List<String> getPK() {
		java.util.List<String> pk=new java.util.ArrayList<String>();
		
			pk.add("idTipoAnagrafica");
		
			pk.add("idTipoRecupero");
		
	    return pk;
	}
	
	
}
