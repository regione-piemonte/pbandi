
package it.csi.pbandi.pbweb.pbandisrv.integration.db.vo;

import java.math.*;
import java.sql.Date;
import it.csi.pbandi.pbweb.pbandisrv.integration.util.DataFilter;



public class PbandiRRegolaBandoLineaVO extends GenericVO {

  	
  	private BigDecimal progrBandoLineaIntervento;
  	
  	private BigDecimal idRegola;
  	
	public PbandiRRegolaBandoLineaVO() {}
  	
	public PbandiRRegolaBandoLineaVO (BigDecimal progrBandoLineaIntervento, BigDecimal idRegola) {
	
		this. progrBandoLineaIntervento =  progrBandoLineaIntervento;
		this. idRegola =  idRegola;
	}
  	
	public BigDecimal getProgrBandoLineaIntervento() {
		return progrBandoLineaIntervento;
	}
	
	public void setProgrBandoLineaIntervento(BigDecimal progrBandoLineaIntervento) {
		this.progrBandoLineaIntervento = progrBandoLineaIntervento;
	}
	
	public BigDecimal getIdRegola() {
		return idRegola;
	}
	
	public void setIdRegola(BigDecimal idRegola) {
		this.idRegola = idRegola;
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
		if (progrBandoLineaIntervento != null && idRegola != null ) {
   			isPkValid = true;
   		}

   		return isPkValid;
	}
	
	public String toString() {
		
	    String temp = "";
	    StringBuffer sb = new StringBuffer();
	    sb.append("\t\n" + this.getClass().getName() + "\t\n");
	    
	    temp = DataFilter.removeNull( progrBandoLineaIntervento);
	    if (!DataFilter.isEmpty(temp)) sb.append(" progrBandoLineaIntervento: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idRegola);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idRegola: " + temp + "\t\n");
	    
	    return sb.toString();
	}
	
	public java.util.List<String> getPK() {
		java.util.List<String> pk=new java.util.ArrayList<String>();
		
			pk.add("progrBandoLineaIntervento");
		
			pk.add("idRegola");
		
	    return pk;
	}
	
	
}
