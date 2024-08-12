
package it.csi.pbandi.pbweb.pbandisrv.integration.db.vo;

import java.math.*;
import java.sql.Date;
import it.csi.pbandi.pbweb.pbandisrv.integration.util.DataFilter;



public class PbandiRLineaPrioritaQsnVO extends GenericVO {

  	
  	private BigDecimal idPrioritaQsn;
  	
  	private Date dtFineValidita;
  	
  	private BigDecimal idLineaDiIntervento;
  	
  	private BigDecimal idUtenteAgg;
  	
  	private BigDecimal idUtenteIns;
  	
	public PbandiRLineaPrioritaQsnVO() {}
  	
	public PbandiRLineaPrioritaQsnVO (BigDecimal idPrioritaQsn, BigDecimal idLineaDiIntervento) {
	
		this. idPrioritaQsn =  idPrioritaQsn;
		this. idLineaDiIntervento =  idLineaDiIntervento;
	}
  	
	public PbandiRLineaPrioritaQsnVO (BigDecimal idPrioritaQsn, Date dtFineValidita, BigDecimal idLineaDiIntervento, BigDecimal idUtenteAgg, BigDecimal idUtenteIns) {
	
		this. idPrioritaQsn =  idPrioritaQsn;
		this. dtFineValidita =  dtFineValidita;
		this. idLineaDiIntervento =  idLineaDiIntervento;
		this. idUtenteAgg =  idUtenteAgg;
		this. idUtenteIns =  idUtenteIns;
	}
  	
  	
	public BigDecimal getIdPrioritaQsn() {
		return idPrioritaQsn;
	}
	
	public void setIdPrioritaQsn(BigDecimal idPrioritaQsn) {
		this.idPrioritaQsn = idPrioritaQsn;
	}
	
	public Date getDtFineValidita() {
		return dtFineValidita;
	}
	
	public void setDtFineValidita(Date dtFineValidita) {
		this.dtFineValidita = dtFineValidita;
	}
	
	public BigDecimal getIdLineaDiIntervento() {
		return idLineaDiIntervento;
	}
	
	public void setIdLineaDiIntervento(BigDecimal idLineaDiIntervento) {
		this.idLineaDiIntervento = idLineaDiIntervento;
	}
	
	public BigDecimal getIdUtenteAgg() {
		return idUtenteAgg;
	}
	
	public void setIdUtenteAgg(BigDecimal idUtenteAgg) {
		this.idUtenteAgg = idUtenteAgg;
	}
	
	public BigDecimal getIdUtenteIns() {
		return idUtenteIns;
	}
	
	public void setIdUtenteIns(BigDecimal idUtenteIns) {
		this.idUtenteIns = idUtenteIns;
	}
	
	public boolean isValid() {
		boolean isValid = false;
                if (isPKValid() && idUtenteIns != null) {
   			isValid = true;
   		}
   		return isValid;
	}
	public boolean isPKValid() {
		boolean isPkValid = false;
		if (idPrioritaQsn != null && idLineaDiIntervento != null ) {
   			isPkValid = true;
   		}

   		return isPkValid;
	}
	
	public String toString() {
		
	    String temp = "";
	    StringBuffer sb = new StringBuffer();
	    sb.append("\t\n" + this.getClass().getName() + "\t\n");
	    
	    temp = DataFilter.removeNull( idPrioritaQsn);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idPrioritaQsn: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtFineValidita);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtFineValidita: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idLineaDiIntervento);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idLineaDiIntervento: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idUtenteAgg);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idUtenteAgg: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idUtenteIns);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idUtenteIns: " + temp + "\t\n");
	    
	    return sb.toString();
	}
	
	public java.util.List<String> getPK() {
		java.util.List<String> pk=new java.util.ArrayList<String>();
		
			pk.add("idPrioritaQsn");
		
			pk.add("idLineaDiIntervento");
		
	    return pk;
	}
	
	
}
