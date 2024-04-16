/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbworkspace.integration.vo;

import java.math.BigDecimal;
import java.sql.Date;

public class PbandiDTipoSoggettoVO {

	private BigDecimal idTipoSoggetto;
  	
  	private Date dtFineValidita;
  	
  	private Date dtInizioValidita;
  	
  	private String descBreveTipoSoggetto;
  	
  	private String descTipoSoggetto;
  	
	public PbandiDTipoSoggettoVO() {}
  	
	public PbandiDTipoSoggettoVO (BigDecimal idTipoSoggetto) {
	
		this. idTipoSoggetto =  idTipoSoggetto;
	}
  	
	public PbandiDTipoSoggettoVO (BigDecimal idTipoSoggetto, Date dtFineValidita, Date dtInizioValidita, String descBreveTipoSoggetto, String descTipoSoggetto) {
	
		this. idTipoSoggetto =  idTipoSoggetto;
		this. dtFineValidita =  dtFineValidita;
		this. dtInizioValidita =  dtInizioValidita;
		this. descBreveTipoSoggetto =  descBreveTipoSoggetto;
		this. descTipoSoggetto =  descTipoSoggetto;
	}
  	
  	
	public BigDecimal getIdTipoSoggetto() {
		return idTipoSoggetto;
	}
	
	public void setIdTipoSoggetto(BigDecimal idTipoSoggetto) {
		this.idTipoSoggetto = idTipoSoggetto;
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
	
	public String getDescBreveTipoSoggetto() {
		return descBreveTipoSoggetto;
	}
	
	public void setDescBreveTipoSoggetto(String descBreveTipoSoggetto) {
		this.descBreveTipoSoggetto = descBreveTipoSoggetto;
	}
	
	public String getDescTipoSoggetto() {
		return descTipoSoggetto;
	}
	
	public void setDescTipoSoggetto(String descTipoSoggetto) {
		this.descTipoSoggetto = descTipoSoggetto;
	}
	
	public boolean isValid() {
		boolean isValid = false;
                if (isPKValid() && dtInizioValidita != null && descBreveTipoSoggetto != null && descTipoSoggetto != null) {
   			isValid = true;
   		}
   		return isValid;
	}
	public boolean isPKValid() {
		boolean isPkValid = false;
		if (idTipoSoggetto != null ) {
   			isPkValid = true;
   		}

   		return isPkValid;
	}
	
	public String toString() {
		
	    String temp = "";
	    StringBuffer sb = new StringBuffer();
	    sb.append("\t\n" + this.getClass().getName() + "\t\n");
	    
//	    temp = DataFilter.removeNull( idTipoSoggetto);
//	    if (!DataFilter.isEmpty(temp)) sb.append(" idTipoSoggetto: " + temp + "\t\n");
//	    
//	    temp = DataFilter.removeNull( dtFineValidita);
//	    if (!DataFilter.isEmpty(temp)) sb.append(" dtFineValidita: " + temp + "\t\n");
//	    
//	    temp = DataFilter.removeNull( dtInizioValidita);
//	    if (!DataFilter.isEmpty(temp)) sb.append(" dtInizioValidita: " + temp + "\t\n");
//	    
//	    temp = DataFilter.removeNull( descBreveTipoSoggetto);
//	    if (!DataFilter.isEmpty(temp)) sb.append(" descBreveTipoSoggetto: " + temp + "\t\n");
//	    
//	    temp = DataFilter.removeNull( descTipoSoggetto);
//	    if (!DataFilter.isEmpty(temp)) sb.append(" descTipoSoggetto: " + temp + "\t\n");
	    
	    return sb.toString();
	}
	
	public java.util.List<String> getPK() {
		java.util.List<String> pk=new java.util.ArrayList<String>();
		
			pk.add("idTipoSoggetto");
		
	    return pk;
	}
}
