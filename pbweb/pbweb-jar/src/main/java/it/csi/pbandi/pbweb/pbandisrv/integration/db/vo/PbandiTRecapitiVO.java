
package it.csi.pbandi.pbweb.pbandisrv.integration.db.vo;

import java.math.*;
import java.sql.Date;
import it.csi.pbandi.pbweb.pbandisrv.integration.util.DataFilter;



public class PbandiTRecapitiVO extends GenericVO {

  	
  	private Date dtFineValidita;
  	
  	private String fax;
  	
  	private String email;
  	
  	private BigDecimal idUtenteAgg;
  	
  	private Date dtInizioValidita;
  	
  	private String telefono;
  	
  	private BigDecimal idRecapiti;
  	
  	private BigDecimal idUtenteIns;
  	
	public PbandiTRecapitiVO() {}
  	
	public PbandiTRecapitiVO (BigDecimal idRecapiti) {
	
		this. idRecapiti =  idRecapiti;
	}
  	
	public PbandiTRecapitiVO (Date dtFineValidita, String fax, String email, BigDecimal idUtenteAgg, Date dtInizioValidita, String telefono, BigDecimal idRecapiti, BigDecimal idUtenteIns) {
	
		this. dtFineValidita =  dtFineValidita;
		this. fax =  fax;
		this. email =  email;
		this. idUtenteAgg =  idUtenteAgg;
		this. dtInizioValidita =  dtInizioValidita;
		this. telefono =  telefono;
		this. idRecapiti =  idRecapiti;
		this. idUtenteIns =  idUtenteIns;
	}
  	
  	
	public Date getDtFineValidita() {
		return dtFineValidita;
	}
	
	public void setDtFineValidita(Date dtFineValidita) {
		this.dtFineValidita = dtFineValidita;
	}
	
	public String getFax() {
		return fax;
	}
	
	public void setFax(String fax) {
		this.fax = fax;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public BigDecimal getIdUtenteAgg() {
		return idUtenteAgg;
	}
	
	public void setIdUtenteAgg(BigDecimal idUtenteAgg) {
		this.idUtenteAgg = idUtenteAgg;
	}
	
	public Date getDtInizioValidita() {
		return dtInizioValidita;
	}
	
	public void setDtInizioValidita(Date dtInizioValidita) {
		this.dtInizioValidita = dtInizioValidita;
	}
	
	public String getTelefono() {
		return telefono;
	}
	
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	
	public BigDecimal getIdRecapiti() {
		return idRecapiti;
	}
	
	public void setIdRecapiti(BigDecimal idRecapiti) {
		this.idRecapiti = idRecapiti;
	}
	
	public BigDecimal getIdUtenteIns() {
		return idUtenteIns;
	}
	
	public void setIdUtenteIns(BigDecimal idUtenteIns) {
		this.idUtenteIns = idUtenteIns;
	}
	
	public boolean isValid() {
		boolean isValid = false;
                if (isPKValid() && dtInizioValidita != null && idUtenteIns != null) {
   			isValid = true;
   		}
   		return isValid;
	}
	public boolean isPKValid() {
		boolean isPkValid = false;
		if (idRecapiti != null ) {
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
	    
	    temp = DataFilter.removeNull( fax);
	    if (!DataFilter.isEmpty(temp)) sb.append(" fax: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( email);
	    if (!DataFilter.isEmpty(temp)) sb.append(" email: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idUtenteAgg);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idUtenteAgg: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtInizioValidita);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtInizioValidita: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( telefono);
	    if (!DataFilter.isEmpty(temp)) sb.append(" telefono: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idRecapiti);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idRecapiti: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idUtenteIns);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idUtenteIns: " + temp + "\t\n");
	    
	    return sb.toString();
	}
	
	public java.util.List<String> getPK() {
		java.util.List<String> pk=new java.util.ArrayList<String>();
		
			pk.add("idRecapiti");
		
	    return pk;
	}
	
	
}
