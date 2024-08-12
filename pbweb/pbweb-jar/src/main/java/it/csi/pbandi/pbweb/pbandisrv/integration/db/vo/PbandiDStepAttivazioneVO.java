
package it.csi.pbandi.pbweb.pbandisrv.integration.db.vo;

import java.math.*;
import java.sql.Date;
import it.csi.pbandi.pbweb.pbandisrv.integration.util.DataFilter;



public class PbandiDStepAttivazioneVO extends GenericVO {

  	
  	private Date dtFineValidita;
  	
  	private Date dtInizioValidita;
  	
  	private BigDecimal idStepAttivazione;
  	
  	private String descStepAttivazione;
  	
  	private BigDecimal codIgrueT52;
  	
	public PbandiDStepAttivazioneVO() {}
  	
	public PbandiDStepAttivazioneVO (BigDecimal idStepAttivazione) {
	
		this. idStepAttivazione =  idStepAttivazione;
	}
  	
	public PbandiDStepAttivazioneVO (Date dtFineValidita, Date dtInizioValidita, BigDecimal idStepAttivazione, String descStepAttivazione, BigDecimal codIgrueT52) {
	
		this. dtFineValidita =  dtFineValidita;
		this. dtInizioValidita =  dtInizioValidita;
		this. idStepAttivazione =  idStepAttivazione;
		this. descStepAttivazione =  descStepAttivazione;
		this. codIgrueT52 =  codIgrueT52;
	}
  	
  	
	public Date getDtFineValidita() {
		return dtFineValidita;
	}
	
	public void setDtFineValidita(Date dtFineValidita) {
		this.dtFineValidita = dtFineValidita;
	}
	
	public Date getDtInizioValidita() {
		return dtInizioValidita;
	}
	
	public void setDtInizioValidita(Date dtInizioValidita) {
		this.dtInizioValidita = dtInizioValidita;
	}
	
	public BigDecimal getIdStepAttivazione() {
		return idStepAttivazione;
	}
	
	public void setIdStepAttivazione(BigDecimal idStepAttivazione) {
		this.idStepAttivazione = idStepAttivazione;
	}
	
	public String getDescStepAttivazione() {
		return descStepAttivazione;
	}
	
	public void setDescStepAttivazione(String descStepAttivazione) {
		this.descStepAttivazione = descStepAttivazione;
	}
	
	public BigDecimal getCodIgrueT52() {
		return codIgrueT52;
	}
	
	public void setCodIgrueT52(BigDecimal codIgrueT52) {
		this.codIgrueT52 = codIgrueT52;
	}
	
	public boolean isValid() {
		boolean isValid = false;
                if (isPKValid() && dtInizioValidita != null && descStepAttivazione != null && codIgrueT52 != null) {
   			isValid = true;
   		}
   		return isValid;
	}
	public boolean isPKValid() {
		boolean isPkValid = false;
		if (idStepAttivazione != null ) {
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
	    
	    temp = DataFilter.removeNull( dtInizioValidita);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtInizioValidita: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idStepAttivazione);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idStepAttivazione: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( descStepAttivazione);
	    if (!DataFilter.isEmpty(temp)) sb.append(" descStepAttivazione: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( codIgrueT52);
	    if (!DataFilter.isEmpty(temp)) sb.append(" codIgrueT52: " + temp + "\t\n");
	    
	    return sb.toString();
	}
	
	public java.util.List<String> getPK() {
		java.util.List<String> pk=new java.util.ArrayList<String>();
		
			pk.add("idStepAttivazione");
		
	    return pk;
	}
	
	
}
