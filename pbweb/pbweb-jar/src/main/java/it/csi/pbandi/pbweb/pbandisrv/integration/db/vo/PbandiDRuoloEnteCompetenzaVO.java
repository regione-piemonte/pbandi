
package it.csi.pbandi.pbweb.pbandisrv.integration.db.vo;

import java.math.*;
import java.sql.Date;
import it.csi.pbandi.pbweb.pbandisrv.integration.util.DataFilter;



public class PbandiDRuoloEnteCompetenzaVO extends GenericVO {

  	
  	private BigDecimal idRuoloEnteCompetenza;
  	
  	private Date dtFineValidita;
  	
  	private String flagVisibile;
  	
  	private String descRuoloEnte;
  	
  	private Date dtInizioValidita;
  	
  	private String descBreveRuoloEnte;
  	
  	private BigDecimal codIgrue;
  	
	public PbandiDRuoloEnteCompetenzaVO() {}
  	
	public PbandiDRuoloEnteCompetenzaVO (BigDecimal idRuoloEnteCompetenza) {
	
		this. idRuoloEnteCompetenza =  idRuoloEnteCompetenza;
	}
  	
	public PbandiDRuoloEnteCompetenzaVO (BigDecimal idRuoloEnteCompetenza, Date dtFineValidita, String flagVisibile, String descRuoloEnte, Date dtInizioValidita, String descBreveRuoloEnte, BigDecimal codIgrue) {
	
		this. idRuoloEnteCompetenza =  idRuoloEnteCompetenza;
		this. dtFineValidita =  dtFineValidita;
		this. flagVisibile =  flagVisibile;
		this. descRuoloEnte =  descRuoloEnte;
		this. dtInizioValidita =  dtInizioValidita;
		this. descBreveRuoloEnte =  descBreveRuoloEnte;
		this. codIgrue =  codIgrue;
	}
  	
  	
	public BigDecimal getIdRuoloEnteCompetenza() {
		return idRuoloEnteCompetenza;
	}
	
	public void setIdRuoloEnteCompetenza(BigDecimal idRuoloEnteCompetenza) {
		this.idRuoloEnteCompetenza = idRuoloEnteCompetenza;
	}
	
	public Date getDtFineValidita() {
		return dtFineValidita;
	}
	
	public void setDtFineValidita(Date dtFineValidita) {
		this.dtFineValidita = dtFineValidita;
	}
	
	public String getFlagVisibile() {
		return flagVisibile;
	}
	
	public void setFlagVisibile(String flagVisibile) {
		this.flagVisibile = flagVisibile;
	}
	
	public String getDescRuoloEnte() {
		return descRuoloEnte;
	}
	
	public void setDescRuoloEnte(String descRuoloEnte) {
		this.descRuoloEnte = descRuoloEnte;
	}
	
	public Date getDtInizioValidita() {
		return dtInizioValidita;
	}
	
	public void setDtInizioValidita(Date dtInizioValidita) {
		this.dtInizioValidita = dtInizioValidita;
	}
	
	public String getDescBreveRuoloEnte() {
		return descBreveRuoloEnte;
	}
	
	public void setDescBreveRuoloEnte(String descBreveRuoloEnte) {
		this.descBreveRuoloEnte = descBreveRuoloEnte;
	}
	
	public BigDecimal getCodIgrue() {
		return codIgrue;
	}
	
	public void setCodIgrue(BigDecimal codIgrue) {
		this.codIgrue = codIgrue;
	}
	
	public boolean isValid() {
		boolean isValid = false;
                if (isPKValid() && flagVisibile != null && descRuoloEnte != null && dtInizioValidita != null && descBreveRuoloEnte != null) {
   			isValid = true;
   		}
   		return isValid;
	}
	public boolean isPKValid() {
		boolean isPkValid = false;
		if (idRuoloEnteCompetenza != null ) {
   			isPkValid = true;
   		}

   		return isPkValid;
	}
	
	public String toString() {
		
	    String temp = "";
	    StringBuffer sb = new StringBuffer();
	    sb.append("\t\n" + this.getClass().getName() + "\t\n");
	    
	    temp = DataFilter.removeNull( idRuoloEnteCompetenza);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idRuoloEnteCompetenza: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtFineValidita);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtFineValidita: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( flagVisibile);
	    if (!DataFilter.isEmpty(temp)) sb.append(" flagVisibile: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( descRuoloEnte);
	    if (!DataFilter.isEmpty(temp)) sb.append(" descRuoloEnte: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtInizioValidita);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtInizioValidita: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( descBreveRuoloEnte);
	    if (!DataFilter.isEmpty(temp)) sb.append(" descBreveRuoloEnte: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( codIgrue);
	    if (!DataFilter.isEmpty(temp)) sb.append(" codIgrue: " + temp + "\t\n");
	    
	    return sb.toString();
	}
	
	public java.util.List<String> getPK() {
		java.util.List<String> pk=new java.util.ArrayList<String>();
		
			pk.add("idRuoloEnteCompetenza");
		
	    return pk;
	}
	
	
}
