
package it.csi.pbandi.pbweb.pbandisrv.integration.db.vo;

import java.math.*;
import java.sql.Date;
import it.csi.pbandi.pbweb.pbandisrv.integration.util.DataFilter;



public class PbandiDTipoIndicatoreVO extends GenericVO {

  	
  	private String descTipoIndicatore;
  	
  	private Date dtFineValidita;
  	
  	private BigDecimal idTipoIndicatore;
  	
  	private String flagMonit;
  	
  	private Date dtInizioValidita;
  	
  	private String descBreveTipoIndicatore;
  	
	public PbandiDTipoIndicatoreVO() {}
  	
	public PbandiDTipoIndicatoreVO (BigDecimal idTipoIndicatore) {
	
		this. idTipoIndicatore =  idTipoIndicatore;
	}
  	
	public PbandiDTipoIndicatoreVO (String descTipoIndicatore, Date dtFineValidita, BigDecimal idTipoIndicatore, String flagMonit, Date dtInizioValidita, String descBreveTipoIndicatore) {
	
		this. descTipoIndicatore =  descTipoIndicatore;
		this. dtFineValidita =  dtFineValidita;
		this. idTipoIndicatore =  idTipoIndicatore;
		this. flagMonit =  flagMonit;
		this. dtInizioValidita =  dtInizioValidita;
		this. descBreveTipoIndicatore =  descBreveTipoIndicatore;
	}
  	
  	
	public String getDescTipoIndicatore() {
		return descTipoIndicatore;
	}
	
	public void setDescTipoIndicatore(String descTipoIndicatore) {
		this.descTipoIndicatore = descTipoIndicatore;
	}
	
	public Date getDtFineValidita() {
		return dtFineValidita;
	}
	
	public void setDtFineValidita(Date dtFineValidita) {
		this.dtFineValidita = dtFineValidita;
	}
	
	public BigDecimal getIdTipoIndicatore() {
		return idTipoIndicatore;
	}
	
	public void setIdTipoIndicatore(BigDecimal idTipoIndicatore) {
		this.idTipoIndicatore = idTipoIndicatore;
	}
	
	public String getFlagMonit() {
		return flagMonit;
	}
	
	public void setFlagMonit(String flagMonit) {
		this.flagMonit = flagMonit;
	}
	
	public Date getDtInizioValidita() {
		return dtInizioValidita;
	}
	
	public void setDtInizioValidita(Date dtInizioValidita) {
		this.dtInizioValidita = dtInizioValidita;
	}
	
	public String getDescBreveTipoIndicatore() {
		return descBreveTipoIndicatore;
	}
	
	public void setDescBreveTipoIndicatore(String descBreveTipoIndicatore) {
		this.descBreveTipoIndicatore = descBreveTipoIndicatore;
	}
	
	public boolean isValid() {
		boolean isValid = false;
                if (isPKValid() && descTipoIndicatore != null && flagMonit != null && dtInizioValidita != null && descBreveTipoIndicatore != null) {
   			isValid = true;
   		}
   		return isValid;
	}
	public boolean isPKValid() {
		boolean isPkValid = false;
		if (idTipoIndicatore != null ) {
   			isPkValid = true;
   		}

   		return isPkValid;
	}
	
	public String toString() {
		
	    String temp = "";
	    StringBuffer sb = new StringBuffer();
	    sb.append("\t\n" + this.getClass().getName() + "\t\n");
	    
	    temp = DataFilter.removeNull( descTipoIndicatore);
	    if (!DataFilter.isEmpty(temp)) sb.append(" descTipoIndicatore: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtFineValidita);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtFineValidita: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idTipoIndicatore);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idTipoIndicatore: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( flagMonit);
	    if (!DataFilter.isEmpty(temp)) sb.append(" flagMonit: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtInizioValidita);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtInizioValidita: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( descBreveTipoIndicatore);
	    if (!DataFilter.isEmpty(temp)) sb.append(" descBreveTipoIndicatore: " + temp + "\t\n");
	    
	    return sb.toString();
	}
	
	public java.util.List<String> getPK() {
		java.util.List<String> pk=new java.util.ArrayList<String>();
		
			pk.add("idTipoIndicatore");
		
	    return pk;
	}
	
	
}
