
package it.csi.pbandi.pbweb.pbandisrv.integration.db.vo;

import java.math.*;
import java.sql.Date;
import it.csi.pbandi.pbweb.pbandisrv.integration.util.DataFilter;



public class PbandiDClassificazioneMetVO extends GenericVO {

  	
  	private BigDecimal idClassificazioneMet;
  	
  	private String descBreveClassMet;
  	
  	private String descClassMet;
  	
  	private Date dtInizioValidita;
  	
  	private Date dtFineValidita;
  	
  	private String tc12_4;
  	
	public PbandiDClassificazioneMetVO() {}
  	
	public PbandiDClassificazioneMetVO (BigDecimal idClassificazioneMet) {	
		this.idClassificazioneMet =  idClassificazioneMet;
	}
  	
	public PbandiDClassificazioneMetVO (BigDecimal idClassificazioneMet, String descBreveClassMet, String descClassMet,  Date dtInizioValidita, Date dtFineValidita, String tc12_4) {
		this.idClassificazioneMet =  idClassificazioneMet;
		this.descBreveClassMet =  descBreveClassMet;
		this.descClassMet =  descClassMet;
		this.dtInizioValidita =  dtInizioValidita;
		this.dtFineValidita =  dtFineValidita;
		this.tc12_4 =  tc12_4;		
	}
  	
	public boolean isValid() {
		boolean isValid = false;
                if (isPKValid() && descBreveClassMet != null && descClassMet != null && dtInizioValidita != null) {
   			isValid = true;
   		}
   		return isValid;
	}
	public boolean isPKValid() {
		boolean isPkValid = false;
		if (idClassificazioneMet != null ) {
   			isPkValid = true;
   		}
   		return isPkValid;
	}
	
	public String toString() {
		
	    String temp = "";
	    StringBuffer sb = new StringBuffer();
	    sb.append("\t\n" + this.getClass().getName() + "\t\n");
	    
	    temp = DataFilter.removeNull( idClassificazioneMet);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idClassificazioneMet: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( descBreveClassMet);
	    if (!DataFilter.isEmpty(temp)) sb.append(" descBreveClassMet: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( descClassMet);
	    if (!DataFilter.isEmpty(temp)) sb.append(" descClassMet: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtInizioValidita);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtInizioValidita: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtFineValidita);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtFineValidita: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( tc12_4);
	    if (!DataFilter.isEmpty(temp)) sb.append(" tc12_4: " + temp + "\t\n");
	   	    
	    return sb.toString();
	}
	
	public java.util.List<String> getPK() {
		java.util.List<String> pk=new java.util.ArrayList<String>();		
			pk.add("idClassificazioneMet");		
	    return pk;
	}

	public BigDecimal getIdClassificazioneMet() {
		return idClassificazioneMet;
	}

	public void setIdClassificazioneMet(BigDecimal idClassificazioneMet) {
		this.idClassificazioneMet = idClassificazioneMet;
	}

	public String getDescBreveClassMet() {
		return descBreveClassMet;
	}

	public void setDescBreveClassMet(String descBreveClassMet) {
		this.descBreveClassMet = descBreveClassMet;
	}

	public String getDescClassMet() {
		return descClassMet;
	}

	public void setDescClassMet(String descClassMet) {
		this.descClassMet = descClassMet;
	}

	public Date getDtInizioValidita() {
		return dtInizioValidita;
	}

	public void setDtInizioValidita(Date dtInizioValidita) {
		this.dtInizioValidita = dtInizioValidita;
	}

	public Date getDtFineValidita() {
		return dtFineValidita;
	}

	public void setDtFineValidita(Date dtFineValidita) {
		this.dtFineValidita = dtFineValidita;
	}

	public String getTc12_4() {
		return tc12_4;
	}

	public void setTc12_4(String tc12_4) {
		this.tc12_4 = tc12_4;
	}

}
