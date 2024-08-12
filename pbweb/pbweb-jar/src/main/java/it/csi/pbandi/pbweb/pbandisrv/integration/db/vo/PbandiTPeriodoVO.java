
package it.csi.pbandi.pbweb.pbandisrv.integration.db.vo;

import java.math.*;
import java.sql.Date;
import it.csi.pbandi.pbweb.pbandisrv.integration.util.DataFilter;



public class PbandiTPeriodoVO extends GenericVO {

  	
  	private Date dtFineContabile;
  	
  	private Date dtInizioValidita;
  	
  	private BigDecimal idUtenteAgg;
  	
  	private String descPeriodo;
  	
  	private Date dtFineValidita;
  	
  	private Date dtInizioContabile;
  	
  	private BigDecimal idTipoPeriodo;
  	
  	private BigDecimal idPeriodo;
  	
  	private BigDecimal idUtenteIns;
  	
  	private String descPeriodoVisualizzata;
  	
	public PbandiTPeriodoVO() {}
  	
	public PbandiTPeriodoVO (BigDecimal idPeriodo) {
	
		this. idPeriodo =  idPeriodo;
	}
  	
	public PbandiTPeriodoVO (Date dtFineContabile, Date dtInizioValidita, BigDecimal idUtenteAgg, String descPeriodo, Date dtFineValidita, Date dtInizioContabile, BigDecimal idTipoPeriodo, BigDecimal idPeriodo, BigDecimal idUtenteIns, String descPeriodoVisualizzata) {
	
		this. dtFineContabile =  dtFineContabile;
		this. dtInizioValidita =  dtInizioValidita;
		this. idUtenteAgg =  idUtenteAgg;
		this. descPeriodo =  descPeriodo;
		this. dtFineValidita =  dtFineValidita;
		this. dtInizioContabile =  dtInizioContabile;
		this. idTipoPeriodo =  idTipoPeriodo;
		this. idPeriodo =  idPeriodo;
		this. idUtenteIns =  idUtenteIns;
		this. descPeriodoVisualizzata =  descPeriodoVisualizzata;
	}
  	
  	
	public Date getDtFineContabile() {
		return dtFineContabile;
	}
	
	public void setDtFineContabile(Date dtFineContabile) {
		this.dtFineContabile = dtFineContabile;
	}
	
	public Date getDtInizioValidita() {
		return dtInizioValidita;
	}
	
	public void setDtInizioValidita(Date dtInizioValidita) {
		this.dtInizioValidita = dtInizioValidita;
	}
	
	public BigDecimal getIdUtenteAgg() {
		return idUtenteAgg;
	}
	
	public void setIdUtenteAgg(BigDecimal idUtenteAgg) {
		this.idUtenteAgg = idUtenteAgg;
	}
	
	public String getDescPeriodo() {
		return descPeriodo;
	}
	
	public void setDescPeriodo(String descPeriodo) {
		this.descPeriodo = descPeriodo;
	}
	
	public Date getDtFineValidita() {
		return dtFineValidita;
	}
	
	public void setDtFineValidita(Date dtFineValidita) {
		this.dtFineValidita = dtFineValidita;
	}
	
	public Date getDtInizioContabile() {
		return dtInizioContabile;
	}
	
	public void setDtInizioContabile(Date dtInizioContabile) {
		this.dtInizioContabile = dtInizioContabile;
	}
	
	public BigDecimal getIdTipoPeriodo() {
		return idTipoPeriodo;
	}
	
	public void setIdTipoPeriodo(BigDecimal idTipoPeriodo) {
		this.idTipoPeriodo = idTipoPeriodo;
	}
	
	public BigDecimal getIdPeriodo() {
		return idPeriodo;
	}
	
	public void setIdPeriodo(BigDecimal idPeriodo) {
		this.idPeriodo = idPeriodo;
	}
	
	public BigDecimal getIdUtenteIns() {
		return idUtenteIns;
	}
	
	public void setIdUtenteIns(BigDecimal idUtenteIns) {
		this.idUtenteIns = idUtenteIns;
	}
	
	public String getDescPeriodoVisualizzata() {
		return descPeriodoVisualizzata;
	}
	
	public void setDescPeriodoVisualizzata(String descPeriodoVisualizzata) {
		this.descPeriodoVisualizzata = descPeriodoVisualizzata;
	}
	
	public boolean isValid() {
		boolean isValid = false;
                if (isPKValid() && dtInizioValidita != null && idTipoPeriodo != null && idUtenteIns != null) {
   			isValid = true;
   		}
   		return isValid;
	}
	public boolean isPKValid() {
		boolean isPkValid = false;
		if (idPeriodo != null ) {
   			isPkValid = true;
   		}

   		return isPkValid;
	}
	
	public String toString() {
		
	    String temp = "";
	    StringBuffer sb = new StringBuffer();
	    sb.append("\t\n" + this.getClass().getName() + "\t\n");
	    
	    temp = DataFilter.removeNull( dtFineContabile);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtFineContabile: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtInizioValidita);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtInizioValidita: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idUtenteAgg);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idUtenteAgg: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( descPeriodo);
	    if (!DataFilter.isEmpty(temp)) sb.append(" descPeriodo: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtFineValidita);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtFineValidita: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtInizioContabile);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtInizioContabile: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idTipoPeriodo);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idTipoPeriodo: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idPeriodo);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idPeriodo: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idUtenteIns);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idUtenteIns: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( descPeriodoVisualizzata);
	    if (!DataFilter.isEmpty(temp)) sb.append(" descPeriodoVisualizzata: " + temp + "\t\n");
	    
	    return sb.toString();
	}
	
	public java.util.List getPK() {
		java.util.List pk=new java.util.ArrayList();
		
			pk.add("idPeriodo");
		
	    return pk;
	}
	
	
}
