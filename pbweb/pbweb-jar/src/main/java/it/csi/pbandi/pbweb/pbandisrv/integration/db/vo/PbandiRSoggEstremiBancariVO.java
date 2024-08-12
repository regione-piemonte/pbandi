
package it.csi.pbandi.pbweb.pbandisrv.integration.db.vo;

import java.math.*;
import java.sql.Date;
import it.csi.pbandi.pbweb.pbandisrv.integration.util.DataFilter;



public class PbandiRSoggEstremiBancariVO extends GenericVO {

  	
  	private Date dtFineValidita;
  	
  	private BigDecimal idEstremiBancari;
  	
  	private BigDecimal idUtenteAgg;
  	
  	private BigDecimal idSoggetto;
  	
  	private BigDecimal idUtenteIns;
  	
	public PbandiRSoggEstremiBancariVO() {}
  	
	public PbandiRSoggEstremiBancariVO (BigDecimal idEstremiBancari, BigDecimal idSoggetto) {
	
		this. idEstremiBancari =  idEstremiBancari;
		this. idSoggetto =  idSoggetto;
	}
  	
	public PbandiRSoggEstremiBancariVO (Date dtFineValidita, BigDecimal idEstremiBancari, BigDecimal idUtenteAgg, BigDecimal idSoggetto, BigDecimal idUtenteIns) {
	
		this. dtFineValidita =  dtFineValidita;
		this. idEstremiBancari =  idEstremiBancari;
		this. idUtenteAgg =  idUtenteAgg;
		this. idSoggetto =  idSoggetto;
		this. idUtenteIns =  idUtenteIns;
	}
  	
  	
	public Date getDtFineValidita() {
		return dtFineValidita;
	}
	
	public void setDtFineValidita(Date dtFineValidita) {
		this.dtFineValidita = dtFineValidita;
	}
	
	public BigDecimal getIdEstremiBancari() {
		return idEstremiBancari;
	}
	
	public void setIdEstremiBancari(BigDecimal idEstremiBancari) {
		this.idEstremiBancari = idEstremiBancari;
	}
	
	public BigDecimal getIdUtenteAgg() {
		return idUtenteAgg;
	}
	
	public void setIdUtenteAgg(BigDecimal idUtenteAgg) {
		this.idUtenteAgg = idUtenteAgg;
	}
	
	public BigDecimal getIdSoggetto() {
		return idSoggetto;
	}
	
	public void setIdSoggetto(BigDecimal idSoggetto) {
		this.idSoggetto = idSoggetto;
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
		if (idEstremiBancari != null && idSoggetto != null ) {
   			isPkValid = true;
   		}

   		return isPkValid;
	}
	
	public String toString() {
		
	    String temp = "";
	    StringBuffer sb = new StringBuffer();
	    sb.append("\t\n" + this.getClass().getName() + "\t\n");
	    
	    temp = DataFilter.removeNull( dtFineValidita);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtFineValidita: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idEstremiBancari);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idEstremiBancari: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idUtenteAgg);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idUtenteAgg: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idSoggetto);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idSoggetto: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idUtenteIns);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idUtenteIns: " + temp + "\t\n");
	    
	    return sb.toString();
	}
	
	public java.util.List<String> getPK() {
		java.util.List<String> pk=new java.util.ArrayList<String>();
		
			pk.add("idEstremiBancari");
		
			pk.add("idSoggetto");
		
	    return pk;
	}
	
	
}
