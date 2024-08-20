/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/


package it.csi.pbandi.pbwebrce.pbandisrv.integration.db.vo;

import java.math.*;
import java.sql.Date;
import it.csi.pbandi.pbwebrce.pbandisrv.integration.util.DataFilter;



public class PbandiDTipoAllegatoVO extends GenericVO {

  	
  	private BigDecimal idTipoAllegato;
  	
  	private Date dtFineValidita;
  	
  	private String descBreveTipoAllegato;
  	
  	private String descTipoAllegato;
  	
  	private Date dtInizioValidita;
  	
	public PbandiDTipoAllegatoVO() {}
  	
	public PbandiDTipoAllegatoVO (BigDecimal idTipoAllegato) {
	
		this. idTipoAllegato =  idTipoAllegato;
	}
  	
	public PbandiDTipoAllegatoVO (BigDecimal idTipoAllegato, Date dtFineValidita, String descBreveTipoAllegato, String descTipoAllegato, Date dtInizioValidita) {
	
		this. idTipoAllegato =  idTipoAllegato;
		this. dtFineValidita =  dtFineValidita;
		this. descBreveTipoAllegato =  descBreveTipoAllegato;
		this. descTipoAllegato =  descTipoAllegato;
		this. dtInizioValidita =  dtInizioValidita;
	}
  	
  	
	public BigDecimal getIdTipoAllegato() {
		return idTipoAllegato;
	}
	
	public void setIdTipoAllegato(BigDecimal idTipoAllegato) {
		this.idTipoAllegato = idTipoAllegato;
	}
	
	public Date getDtFineValidita() {
		return dtFineValidita;
	}
	
	public void setDtFineValidita(Date dtFineValidita) {
		this.dtFineValidita = dtFineValidita;
	}
	
	public String getDescBreveTipoAllegato() {
		return descBreveTipoAllegato;
	}
	
	public void setDescBreveTipoAllegato(String descBreveTipoAllegato) {
		this.descBreveTipoAllegato = descBreveTipoAllegato;
	}
	
	public String getDescTipoAllegato() {
		return descTipoAllegato;
	}
	
	public void setDescTipoAllegato(String descTipoAllegato) {
		this.descTipoAllegato = descTipoAllegato;
	}
	
	public Date getDtInizioValidita() {
		return dtInizioValidita;
	}
	
	public void setDtInizioValidita(Date dtInizioValidita) {
		this.dtInizioValidita = dtInizioValidita;
	}
	
	public boolean isValid() {
		boolean isValid = false;
                if (isPKValid() && descBreveTipoAllegato != null && descTipoAllegato != null && dtInizioValidita != null) {
   			isValid = true;
   		}
   		return isValid;
	}
	public boolean isPKValid() {
		boolean isPkValid = false;
		if (idTipoAllegato != null ) {
   			isPkValid = true;
   		}

   		return isPkValid;
	}
	
	public String toString() {
		
	    String temp = "";
	    StringBuffer sb = new StringBuffer();
	    sb.append("\t\n" + this.getClass().getName() + "\t\n");
	    
	    temp = DataFilter.removeNull( idTipoAllegato);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idTipoAllegato: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtFineValidita);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtFineValidita: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( descBreveTipoAllegato);
	    if (!DataFilter.isEmpty(temp)) sb.append(" descBreveTipoAllegato: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( descTipoAllegato);
	    if (!DataFilter.isEmpty(temp)) sb.append(" descTipoAllegato: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtInizioValidita);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtInizioValidita: " + temp + "\t\n");
	    
	    return sb.toString();
	}
	
	public java.util.List<String> getPK() {
		java.util.List<String> pk=new java.util.ArrayList<String>();
		
			pk.add("idTipoAllegato");
		
	    return pk;
	}
	
	
}
