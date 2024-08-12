
package it.csi.pbandi.pbweb.pbandisrv.integration.db.vo;

import java.math.*;
import java.sql.Date;
import it.csi.pbandi.pbweb.pbandisrv.integration.util.DataFilter;



public class PbandiDBancaVO extends GenericVO {

  	
  	private Date dtFineValidita;
  	
  	private String codBanca;
  	
  	private BigDecimal idBanca;
  	
  	private Date dtInizioValidita;
  	
  	private BigDecimal idIndirizzo;
  	
  	private String descBanca;
  	
	public PbandiDBancaVO() {}
  	
	public PbandiDBancaVO (BigDecimal idBanca) {
	
		this. idBanca =  idBanca;
	}
  	
	public PbandiDBancaVO (Date dtFineValidita, String codBanca, BigDecimal idBanca, Date dtInizioValidita, BigDecimal idIndirizzo, String descBanca) {
	
		this. dtFineValidita =  dtFineValidita;
		this. codBanca =  codBanca;
		this. idBanca =  idBanca;
		this. dtInizioValidita =  dtInizioValidita;
		this. idIndirizzo =  idIndirizzo;
		this. descBanca =  descBanca;
	}
  	
  	
	public Date getDtFineValidita() {
		return dtFineValidita;
	}
	
	public void setDtFineValidita(Date dtFineValidita) {
		this.dtFineValidita = dtFineValidita;
	}
	
	public String getCodBanca() {
		return codBanca;
	}
	
	public void setCodBanca(String codBanca) {
		this.codBanca = codBanca;
	}
	
	public BigDecimal getIdBanca() {
		return idBanca;
	}
	
	public void setIdBanca(BigDecimal idBanca) {
		this.idBanca = idBanca;
	}
	
	public Date getDtInizioValidita() {
		return dtInizioValidita;
	}
	
	public void setDtInizioValidita(Date dtInizioValidita) {
		this.dtInizioValidita = dtInizioValidita;
	}
	
	public BigDecimal getIdIndirizzo() {
		return idIndirizzo;
	}
	
	public void setIdIndirizzo(BigDecimal idIndirizzo) {
		this.idIndirizzo = idIndirizzo;
	}
	
	public String getDescBanca() {
		return descBanca;
	}
	
	public void setDescBanca(String descBanca) {
		this.descBanca = descBanca;
	}
	
	public boolean isValid() {
		boolean isValid = false;
                if (isPKValid() && codBanca != null && dtInizioValidita != null && descBanca != null) {
   			isValid = true;
   		}
   		return isValid;
	}
	public boolean isPKValid() {
		boolean isPkValid = false;
		if (idBanca != null ) {
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
	    
	    temp = DataFilter.removeNull( codBanca);
	    if (!DataFilter.isEmpty(temp)) sb.append(" codBanca: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idBanca);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idBanca: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtInizioValidita);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtInizioValidita: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idIndirizzo);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idIndirizzo: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( descBanca);
	    if (!DataFilter.isEmpty(temp)) sb.append(" descBanca: " + temp + "\t\n");
	    
	    return sb.toString();
	}
	
	public java.util.List<String> getPK() {
		java.util.List<String> pk=new java.util.ArrayList<String>();
		
			pk.add("idBanca");
		
	    return pk;
	}
	
	
}
