/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/


package it.csi.pbandi.pbwebrce.pbandisrv.integration.db.vo;

import java.math.*;
import java.sql.Date;
import it.csi.pbandi.pbwebrce.pbandisrv.integration.util.DataFilter;



public class PbandiDTipoInsCoordinateVO extends GenericVO {

  	
  	private String descTipoInsCoordinate;
  	
  	private BigDecimal idTipoInsCoordinate;
  	
  	private Date dtFineValidita;
  	
  	private String descBreveTipoInsCoordinate;
  	
  	private Date dtInizioValidita;
  	
	public PbandiDTipoInsCoordinateVO() {}
  	
	public PbandiDTipoInsCoordinateVO (BigDecimal idTipoInsCoordinate) {
	
		this. idTipoInsCoordinate =  idTipoInsCoordinate;
	}
  	
	public PbandiDTipoInsCoordinateVO (String descTipoInsCoordinate, BigDecimal idTipoInsCoordinate, Date dtFineValidita, String descBreveTipoInsCoordinate, Date dtInizioValidita) {
	
		this. descTipoInsCoordinate =  descTipoInsCoordinate;
		this. idTipoInsCoordinate =  idTipoInsCoordinate;
		this. dtFineValidita =  dtFineValidita;
		this. descBreveTipoInsCoordinate =  descBreveTipoInsCoordinate;
		this. dtInizioValidita =  dtInizioValidita;
	}
  	
  	
	public String getDescTipoInsCoordinate() {
		return descTipoInsCoordinate;
	}
	
	public void setDescTipoInsCoordinate(String descTipoInsCoordinate) {
		this.descTipoInsCoordinate = descTipoInsCoordinate;
	}
	
	public BigDecimal getIdTipoInsCoordinate() {
		return idTipoInsCoordinate;
	}
	
	public void setIdTipoInsCoordinate(BigDecimal idTipoInsCoordinate) {
		this.idTipoInsCoordinate = idTipoInsCoordinate;
	}
	
	public Date getDtFineValidita() {
		return dtFineValidita;
	}
	
	public void setDtFineValidita(Date dtFineValidita) {
		this.dtFineValidita = dtFineValidita;
	}
	
	public String getDescBreveTipoInsCoordinate() {
		return descBreveTipoInsCoordinate;
	}
	
	public void setDescBreveTipoInsCoordinate(String descBreveTipoInsCoordinate) {
		this.descBreveTipoInsCoordinate = descBreveTipoInsCoordinate;
	}
	
	public Date getDtInizioValidita() {
		return dtInizioValidita;
	}
	
	public void setDtInizioValidita(Date dtInizioValidita) {
		this.dtInizioValidita = dtInizioValidita;
	}
	
	public boolean isValid() {
		boolean isValid = false;
                if (isPKValid() && descTipoInsCoordinate != null && descBreveTipoInsCoordinate != null && dtInizioValidita != null) {
   			isValid = true;
   		}
   		return isValid;
	}
	public boolean isPKValid() {
		boolean isPkValid = false;
		if (idTipoInsCoordinate != null ) {
   			isPkValid = true;
   		}

   		return isPkValid;
	}
	
	public String toString() {
		
	    String temp = "";
	    StringBuffer sb = new StringBuffer();
	    sb.append("\t\n" + this.getClass().getName() + "\t\n");
	    
	    temp = DataFilter.removeNull( descTipoInsCoordinate);
	    if (!DataFilter.isEmpty(temp)) sb.append(" descTipoInsCoordinate: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idTipoInsCoordinate);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idTipoInsCoordinate: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtFineValidita);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtFineValidita: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( descBreveTipoInsCoordinate);
	    if (!DataFilter.isEmpty(temp)) sb.append(" descBreveTipoInsCoordinate: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtInizioValidita);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtInizioValidita: " + temp + "\t\n");
	    
	    return sb.toString();
	}
	
	public java.util.List<String> getPK() {
		java.util.List<String> pk=new java.util.ArrayList<String>();
		
			pk.add("idTipoInsCoordinate");
		
	    return pk;
	}
	
	
}
