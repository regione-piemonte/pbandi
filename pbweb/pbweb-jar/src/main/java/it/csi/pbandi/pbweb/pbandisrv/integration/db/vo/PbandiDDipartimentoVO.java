
package it.csi.pbandi.pbweb.pbandisrv.integration.db.vo;

import java.math.*;
import java.sql.Date;
import it.csi.pbandi.pbweb.pbandisrv.integration.util.DataFilter;



public class PbandiDDipartimentoVO extends GenericVO {

  	
  	private String descBreveDipartimento;
  	
  	private Date dtFineValidita;
  	
  	private BigDecimal idAteneo;
  	
  	private String descDipartimento;
  	
  	private Date dtInizioValidita;
  	
  	private String codiceFiscaleDipartimento;
  	
  	private BigDecimal idDipartimento;
  	
	public PbandiDDipartimentoVO() {}
  	
	public PbandiDDipartimentoVO (BigDecimal idDipartimento) {
	
		this. idDipartimento =  idDipartimento;
	}
  	
	public PbandiDDipartimentoVO (String descBreveDipartimento, Date dtFineValidita, BigDecimal idAteneo, String descDipartimento, Date dtInizioValidita, String codiceFiscaleDipartimento, BigDecimal idDipartimento) {
	
		this. descBreveDipartimento =  descBreveDipartimento;
		this. dtFineValidita =  dtFineValidita;
		this. idAteneo =  idAteneo;
		this. descDipartimento =  descDipartimento;
		this. dtInizioValidita =  dtInizioValidita;
		this. codiceFiscaleDipartimento =  codiceFiscaleDipartimento;
		this. idDipartimento =  idDipartimento;
	}
  	
  	
	public String getDescBreveDipartimento() {
		return descBreveDipartimento;
	}
	
	public void setDescBreveDipartimento(String descBreveDipartimento) {
		this.descBreveDipartimento = descBreveDipartimento;
	}
	
	public Date getDtFineValidita() {
		return dtFineValidita;
	}
	
	public void setDtFineValidita(Date dtFineValidita) {
		this.dtFineValidita = dtFineValidita;
	}
	
	public BigDecimal getIdAteneo() {
		return idAteneo;
	}
	
	public void setIdAteneo(BigDecimal idAteneo) {
		this.idAteneo = idAteneo;
	}
	
	public String getDescDipartimento() {
		return descDipartimento;
	}
	
	public void setDescDipartimento(String descDipartimento) {
		this.descDipartimento = descDipartimento;
	}
	
	public Date getDtInizioValidita() {
		return dtInizioValidita;
	}
	
	public void setDtInizioValidita(Date dtInizioValidita) {
		this.dtInizioValidita = dtInizioValidita;
	}
	
	public String getCodiceFiscaleDipartimento() {
		return codiceFiscaleDipartimento;
	}
	
	public void setCodiceFiscaleDipartimento(String codiceFiscaleDipartimento) {
		this.codiceFiscaleDipartimento = codiceFiscaleDipartimento;
	}
	
	public BigDecimal getIdDipartimento() {
		return idDipartimento;
	}
	
	public void setIdDipartimento(BigDecimal idDipartimento) {
		this.idDipartimento = idDipartimento;
	}
	
	public boolean isValid() {
		boolean isValid = false;
                if (isPKValid() && descBreveDipartimento != null && idAteneo != null && descDipartimento != null && dtInizioValidita != null) {
   			isValid = true;
   		}
   		return isValid;
	}
	public boolean isPKValid() {
		boolean isPkValid = false;
		if (idDipartimento != null ) {
   			isPkValid = true;
   		}

   		return isPkValid;
	}
	
	public String toString() {
		
	    String temp = "";
	    StringBuffer sb = new StringBuffer();
	    sb.append("\t\n" + this.getClass().getName() + "\t\n");
	    
	    temp = DataFilter.removeNull( descBreveDipartimento);
	    if (!DataFilter.isEmpty(temp)) sb.append(" descBreveDipartimento: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtFineValidita);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtFineValidita: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idAteneo);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idAteneo: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( descDipartimento);
	    if (!DataFilter.isEmpty(temp)) sb.append(" descDipartimento: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtInizioValidita);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtInizioValidita: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( codiceFiscaleDipartimento);
	    if (!DataFilter.isEmpty(temp)) sb.append(" codiceFiscaleDipartimento: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idDipartimento);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idDipartimento: " + temp + "\t\n");
	    
	    return sb.toString();
	}
	
	public java.util.List<String> getPK() {
		java.util.List<String> pk=new java.util.ArrayList<String>();
		
			pk.add("idDipartimento");
		
	    return pk;
	}
	
	
}
