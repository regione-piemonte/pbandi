
package it.csi.pbandi.pbweb.pbandisrv.integration.db.vo;

import java.math.*;
import it.csi.pbandi.pbweb.pbandisrv.integration.util.DataFilter;



public class PbandiRLineaTipologiaAttivVO extends GenericVO {
  	
  	private BigDecimal idTipologiaAttivazione;
  	
  	private BigDecimal idLineaDiIntervento;
  	
	public PbandiRLineaTipologiaAttivVO() {}
	
	public boolean isValid() {
		boolean isValid = false;
                if (isPKValid()) {
   			isValid = true;
   		}
   		return isValid;
	}
	public boolean isPKValid() {
		boolean isPkValid = false;
		if (idTipologiaAttivazione != null && idLineaDiIntervento != null ) {
   			isPkValid = true;
   		}
   		return isPkValid;
	}
	
	public String toString() {		
	    String temp = "";
	    StringBuffer sb = new StringBuffer();
	    sb.append("\t\n" + this.getClass().getName() + "\t\n");	    
	    temp = DataFilter.removeNull( idTipologiaAttivazione);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idTipologiaAttivazione: " + temp + "\t\n");	    
	    temp = DataFilter.removeNull( idLineaDiIntervento);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idLineaDiIntervento: " + temp + "\t\n");	    
	    return sb.toString();
	}
	
	public java.util.List<String> getPK() {
		java.util.List<String> pk=new java.util.ArrayList<String>();
			pk.add("idTipologiaAttivazione");
			pk.add("idLineaDiIntervento");
	    return pk;
	}

	public BigDecimal getIdTipologiaAttivazione() {
		return idTipologiaAttivazione;
	}

	public void setIdTipologiaAttivazione(BigDecimal idTipologiaAttivazione) {
		this.idTipologiaAttivazione = idTipologiaAttivazione;
	}

	public BigDecimal getIdLineaDiIntervento() {
		return idLineaDiIntervento;
	}

	public void setIdLineaDiIntervento(BigDecimal idLineaDiIntervento) {
		this.idLineaDiIntervento = idLineaDiIntervento;
	}
	
}
