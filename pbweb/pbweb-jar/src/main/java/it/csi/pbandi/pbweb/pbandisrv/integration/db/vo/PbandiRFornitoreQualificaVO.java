
package it.csi.pbandi.pbweb.pbandisrv.integration.db.vo;

import java.math.*;
import java.sql.Date;
import it.csi.pbandi.pbweb.pbandisrv.integration.util.DataFilter;



public class PbandiRFornitoreQualificaVO extends GenericVO {

  	
  	private Date dtFineValidita;
  	
  	private BigDecimal idQualifica;
  	
  	private BigDecimal progrFornitoreQualifica;
  	
  	private BigDecimal idFornitore;
  	
  	private String noteQualifica;
  	
  	private Date dtInizioValidita;
  	
  	private BigDecimal costoOrario;
  	
	public PbandiRFornitoreQualificaVO() {}
  	
	public PbandiRFornitoreQualificaVO (BigDecimal progrFornitoreQualifica) {
	
		this. progrFornitoreQualifica =  progrFornitoreQualifica;
	}
  	
	public PbandiRFornitoreQualificaVO (Date dtFineValidita, BigDecimal idQualifica, BigDecimal progrFornitoreQualifica, BigDecimal idFornitore, String noteQualifica, Date dtInizioValidita, BigDecimal costoOrario) {
	
		this. dtFineValidita =  dtFineValidita;
		this. idQualifica =  idQualifica;
		this. progrFornitoreQualifica =  progrFornitoreQualifica;
		this. idFornitore =  idFornitore;
		this. noteQualifica =  noteQualifica;
		this. dtInizioValidita =  dtInizioValidita;
		this. costoOrario =  costoOrario;
	}
  	
  	
	public Date getDtFineValidita() {
		return dtFineValidita;
	}
	
	public void setDtFineValidita(Date dtFineValidita) {
		this.dtFineValidita = dtFineValidita;
	}
	
	public BigDecimal getIdQualifica() {
		return idQualifica;
	}
	
	public void setIdQualifica(BigDecimal idQualifica) {
		this.idQualifica = idQualifica;
	}
	
	public BigDecimal getProgrFornitoreQualifica() {
		return progrFornitoreQualifica;
	}
	
	public void setProgrFornitoreQualifica(BigDecimal progrFornitoreQualifica) {
		this.progrFornitoreQualifica = progrFornitoreQualifica;
	}
	
	public BigDecimal getIdFornitore() {
		return idFornitore;
	}
	
	public void setIdFornitore(BigDecimal idFornitore) {
		this.idFornitore = idFornitore;
	}
	
	public String getNoteQualifica() {
		return noteQualifica;
	}
	
	public void setNoteQualifica(String noteQualifica) {
		this.noteQualifica = noteQualifica;
	}
	
	public Date getDtInizioValidita() {
		return dtInizioValidita;
	}
	
	public void setDtInizioValidita(Date dtInizioValidita) {
		this.dtInizioValidita = dtInizioValidita;
	}
	
	public BigDecimal getCostoOrario() {
		return costoOrario;
	}
	
	public void setCostoOrario(BigDecimal costoOrario) {
		this.costoOrario = costoOrario;
	}
	
	public boolean isValid() {
		boolean isValid = false;
                if (isPKValid() && idQualifica != null && idFornitore != null && dtInizioValidita != null) {
   			isValid = true;
   		}
   		return isValid;
	}
	public boolean isPKValid() {
		boolean isPkValid = false;
		if (progrFornitoreQualifica != null ) {
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
	    
	    temp = DataFilter.removeNull( idQualifica);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idQualifica: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( progrFornitoreQualifica);
	    if (!DataFilter.isEmpty(temp)) sb.append(" progrFornitoreQualifica: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idFornitore);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idFornitore: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( noteQualifica);
	    if (!DataFilter.isEmpty(temp)) sb.append(" noteQualifica: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtInizioValidita);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtInizioValidita: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( costoOrario);
	    if (!DataFilter.isEmpty(temp)) sb.append(" costoOrario: " + temp + "\t\n");
	    
	    return sb.toString();
	}
	
	public java.util.List getPK() {
		java.util.List pk=new java.util.ArrayList();
		
			pk.add("progrFornitoreQualifica");
		
	    return pk;
	}
	
	
}
