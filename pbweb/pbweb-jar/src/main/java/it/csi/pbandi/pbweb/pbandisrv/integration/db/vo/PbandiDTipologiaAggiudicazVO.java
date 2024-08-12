
package it.csi.pbandi.pbweb.pbandisrv.integration.db.vo;

import java.math.*;
import java.sql.Date;
import it.csi.pbandi.pbweb.pbandisrv.integration.util.DataFilter;



public class PbandiDTipologiaAggiudicazVO extends GenericVO {

  	
  	private Date dtFineValidita;
  	
  	private BigDecimal codIgrueT47;
  	
  	private Date dtInizioValidita;
  	
  	private String descTipologiaAggiudicazione;
  	
  	private BigDecimal idTipologiaAggiudicaz;
  	
	public PbandiDTipologiaAggiudicazVO() {}
  	
	public PbandiDTipologiaAggiudicazVO (BigDecimal idTipologiaAggiudicaz) {
	
		this. idTipologiaAggiudicaz =  idTipologiaAggiudicaz;
	}
  	
	public PbandiDTipologiaAggiudicazVO (Date dtFineValidita, BigDecimal codIgrueT47, Date dtInizioValidita, String descTipologiaAggiudicazione, BigDecimal idTipologiaAggiudicaz) {
	
		this. dtFineValidita =  dtFineValidita;
		this. codIgrueT47 =  codIgrueT47;
		this. dtInizioValidita =  dtInizioValidita;
		this. descTipologiaAggiudicazione =  descTipologiaAggiudicazione;
		this. idTipologiaAggiudicaz =  idTipologiaAggiudicaz;
	}
  	
  	
	public Date getDtFineValidita() {
		return dtFineValidita;
	}
	
	public void setDtFineValidita(Date dtFineValidita) {
		this.dtFineValidita = dtFineValidita;
	}
	
	public BigDecimal getCodIgrueT47() {
		return codIgrueT47;
	}
	
	public void setCodIgrueT47(BigDecimal codIgrueT47) {
		this.codIgrueT47 = codIgrueT47;
	}
	
	public Date getDtInizioValidita() {
		return dtInizioValidita;
	}
	
	public void setDtInizioValidita(Date dtInizioValidita) {
		this.dtInizioValidita = dtInizioValidita;
	}
	
	public String getDescTipologiaAggiudicazione() {
		return descTipologiaAggiudicazione;
	}
	
	public void setDescTipologiaAggiudicazione(String descTipologiaAggiudicazione) {
		this.descTipologiaAggiudicazione = descTipologiaAggiudicazione;
	}
	
	public BigDecimal getIdTipologiaAggiudicaz() {
		return idTipologiaAggiudicaz;
	}
	
	public void setIdTipologiaAggiudicaz(BigDecimal idTipologiaAggiudicaz) {
		this.idTipologiaAggiudicaz = idTipologiaAggiudicaz;
	}
	
	public boolean isValid() {
		boolean isValid = false;
                if (isPKValid() && codIgrueT47 != null && dtInizioValidita != null && descTipologiaAggiudicazione != null) {
   			isValid = true;
   		}
   		return isValid;
	}
	public boolean isPKValid() {
		boolean isPkValid = false;
		if (idTipologiaAggiudicaz != null ) {
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
	    
	    temp = DataFilter.removeNull( codIgrueT47);
	    if (!DataFilter.isEmpty(temp)) sb.append(" codIgrueT47: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtInizioValidita);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtInizioValidita: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( descTipologiaAggiudicazione);
	    if (!DataFilter.isEmpty(temp)) sb.append(" descTipologiaAggiudicazione: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idTipologiaAggiudicaz);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idTipologiaAggiudicaz: " + temp + "\t\n");
	    
	    return sb.toString();
	}
	
	public java.util.List<String> getPK() {
		java.util.List<String> pk=new java.util.ArrayList<String>();
		
			pk.add("idTipologiaAggiudicaz");
		
	    return pk;
	}
	
	
}
