
package it.csi.pbandi.pbweb.pbandisrv.integration.db.vo;

import java.math.*;
import java.sql.Date;
import it.csi.pbandi.pbweb.pbandisrv.integration.util.DataFilter;



public class PbandiDTipoFondoVO extends GenericVO {

  	
  	private BigDecimal idTipoFondo;
  	
  	private Date dtFineValidita;
  	
  	private String descTipoFondo;
  	
  	private Date dtInizioValidita;
  	
  	private String descBreveTipoFondo;
  	
	public PbandiDTipoFondoVO() {}
  	
	public PbandiDTipoFondoVO (BigDecimal idTipoFondo) {
	
		this. idTipoFondo =  idTipoFondo;
	}
  	
	public PbandiDTipoFondoVO (BigDecimal idTipoFondo, Date dtFineValidita, String descTipoFondo, Date dtInizioValidita, String descBreveTipoFondo) {
	
		this. idTipoFondo =  idTipoFondo;
		this. dtFineValidita =  dtFineValidita;
		this. descTipoFondo =  descTipoFondo;
		this. dtInizioValidita =  dtInizioValidita;
		this. descBreveTipoFondo =  descBreveTipoFondo;
	}
  	
  	
	public BigDecimal getIdTipoFondo() {
		return idTipoFondo;
	}
	
	public void setIdTipoFondo(BigDecimal idTipoFondo) {
		this.idTipoFondo = idTipoFondo;
	}
	
	public Date getDtFineValidita() {
		return dtFineValidita;
	}
	
	public void setDtFineValidita(Date dtFineValidita) {
		this.dtFineValidita = dtFineValidita;
	}
	
	public String getDescTipoFondo() {
		return descTipoFondo;
	}
	
	public void setDescTipoFondo(String descTipoFondo) {
		this.descTipoFondo = descTipoFondo;
	}
	
	public Date getDtInizioValidita() {
		return dtInizioValidita;
	}
	
	public void setDtInizioValidita(Date dtInizioValidita) {
		this.dtInizioValidita = dtInizioValidita;
	}
	
	public String getDescBreveTipoFondo() {
		return descBreveTipoFondo;
	}
	
	public void setDescBreveTipoFondo(String descBreveTipoFondo) {
		this.descBreveTipoFondo = descBreveTipoFondo;
	}
	
	public boolean isValid() {
		boolean isValid = false;
                if (isPKValid() && descTipoFondo != null && dtInizioValidita != null && descBreveTipoFondo != null) {
   			isValid = true;
   		}
   		return isValid;
	}
	public boolean isPKValid() {
		boolean isPkValid = false;
		if (idTipoFondo != null ) {
   			isPkValid = true;
   		}

   		return isPkValid;
	}
	
	public String toString() {
		
	    String temp = "";
	    StringBuffer sb = new StringBuffer();
	    sb.append("\t\n" + this.getClass().getName() + "\t\n");
	    
	    temp = DataFilter.removeNull( idTipoFondo);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idTipoFondo: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtFineValidita);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtFineValidita: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( descTipoFondo);
	    if (!DataFilter.isEmpty(temp)) sb.append(" descTipoFondo: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtInizioValidita);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtInizioValidita: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( descBreveTipoFondo);
	    if (!DataFilter.isEmpty(temp)) sb.append(" descBreveTipoFondo: " + temp + "\t\n");
	    
	    return sb.toString();
	}
	
	public java.util.List getPK() {
		java.util.List pk=new java.util.ArrayList();
		
			pk.add("idTipoFondo");
		
	    return pk;
	}
	
	
}
