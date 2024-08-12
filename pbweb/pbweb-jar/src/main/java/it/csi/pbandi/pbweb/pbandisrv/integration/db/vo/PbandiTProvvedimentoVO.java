
package it.csi.pbandi.pbweb.pbandisrv.integration.db.vo;

import java.math.*;
import java.sql.Date;
import it.csi.pbandi.pbweb.pbandisrv.integration.util.DataFilter;



public class PbandiTProvvedimentoVO extends GenericVO {

  	
  	private String numeroProvvedimento;
  	
  	private Date dtProvvedimento;
  	
  	private BigDecimal idEnteCompetenza;
  	
  	private BigDecimal idTipoProvvedimento;
  	
  	private BigDecimal idUtenteAgg;
  	
  	private BigDecimal annoProvvedimento;
  	
  	private BigDecimal idUtenteIns;
  	
  	private BigDecimal idProvvedimento;
  	
	public PbandiTProvvedimentoVO() {}
  	
	public PbandiTProvvedimentoVO (BigDecimal idProvvedimento) {
	
		this. idProvvedimento =  idProvvedimento;
	}
  	
	public PbandiTProvvedimentoVO (String numeroProvvedimento, Date dtProvvedimento, BigDecimal idEnteCompetenza, BigDecimal idTipoProvvedimento, BigDecimal idUtenteAgg, BigDecimal annoProvvedimento, BigDecimal idUtenteIns, BigDecimal idProvvedimento) {
	
		this. numeroProvvedimento =  numeroProvvedimento;
		this. dtProvvedimento =  dtProvvedimento;
		this. idEnteCompetenza =  idEnteCompetenza;
		this. idTipoProvvedimento =  idTipoProvvedimento;
		this. idUtenteAgg =  idUtenteAgg;
		this. annoProvvedimento =  annoProvvedimento;
		this. idUtenteIns =  idUtenteIns;
		this. idProvvedimento =  idProvvedimento;
	}
  	
  	
	public String getNumeroProvvedimento() {
		return numeroProvvedimento;
	}
	
	public void setNumeroProvvedimento(String numeroProvvedimento) {
		this.numeroProvvedimento = numeroProvvedimento;
	}
	
	public Date getDtProvvedimento() {
		return dtProvvedimento;
	}
	
	public void setDtProvvedimento(Date dtProvvedimento) {
		this.dtProvvedimento = dtProvvedimento;
	}
	
	public BigDecimal getIdEnteCompetenza() {
		return idEnteCompetenza;
	}
	
	public void setIdEnteCompetenza(BigDecimal idEnteCompetenza) {
		this.idEnteCompetenza = idEnteCompetenza;
	}
	
	public BigDecimal getIdTipoProvvedimento() {
		return idTipoProvvedimento;
	}
	
	public void setIdTipoProvvedimento(BigDecimal idTipoProvvedimento) {
		this.idTipoProvvedimento = idTipoProvvedimento;
	}
	
	public BigDecimal getIdUtenteAgg() {
		return idUtenteAgg;
	}
	
	public void setIdUtenteAgg(BigDecimal idUtenteAgg) {
		this.idUtenteAgg = idUtenteAgg;
	}
	
	public BigDecimal getAnnoProvvedimento() {
		return annoProvvedimento;
	}
	
	public void setAnnoProvvedimento(BigDecimal annoProvvedimento) {
		this.annoProvvedimento = annoProvvedimento;
	}
	
	public BigDecimal getIdUtenteIns() {
		return idUtenteIns;
	}
	
	public void setIdUtenteIns(BigDecimal idUtenteIns) {
		this.idUtenteIns = idUtenteIns;
	}
	
	public BigDecimal getIdProvvedimento() {
		return idProvvedimento;
	}
	
	public void setIdProvvedimento(BigDecimal idProvvedimento) {
		this.idProvvedimento = idProvvedimento;
	}
	
	public boolean isValid() {
		boolean isValid = false;
                if (isPKValid() && numeroProvvedimento != null && idEnteCompetenza != null && idTipoProvvedimento != null && annoProvvedimento != null && idUtenteIns != null) {
   			isValid = true;
   		}
   		return isValid;
	}
	public boolean isPKValid() {
		boolean isPkValid = false;
		if (idProvvedimento != null ) {
   			isPkValid = true;
   		}

   		return isPkValid;
	}
	
	public String toString() {
		
	    String temp = "";
	    StringBuffer sb = new StringBuffer();
	    sb.append("\t\n" + this.getClass().getName() + "\t\n");
	    
	    temp = DataFilter.removeNull( numeroProvvedimento);
	    if (!DataFilter.isEmpty(temp)) sb.append(" numeroProvvedimento: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtProvvedimento);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtProvvedimento: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idEnteCompetenza);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idEnteCompetenza: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idTipoProvvedimento);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idTipoProvvedimento: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idUtenteAgg);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idUtenteAgg: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( annoProvvedimento);
	    if (!DataFilter.isEmpty(temp)) sb.append(" annoProvvedimento: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idUtenteIns);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idUtenteIns: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idProvvedimento);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idProvvedimento: " + temp + "\t\n");
	    
	    return sb.toString();
	}
	
	public java.util.List getPK() {
		java.util.List pk=new java.util.ArrayList();
		
			pk.add("idProvvedimento");
		
	    return pk;
	}
	
	
}
