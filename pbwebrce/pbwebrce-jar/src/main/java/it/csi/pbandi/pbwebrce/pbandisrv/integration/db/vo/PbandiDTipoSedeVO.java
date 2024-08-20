/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/


package it.csi.pbandi.pbwebrce.pbandisrv.integration.db.vo;

import java.math.*;
import java.sql.Date;
import it.csi.pbandi.pbwebrce.pbandisrv.integration.util.DataFilter;



public class PbandiDTipoSedeVO extends GenericVO {

  	
  	private String descBreveTipoSede;
  	
  	private Date dtFineValidita;
  	
  	private String descTipoSede;
  	
  	private Date dtInizioValidita;
  	
  	private BigDecimal idTipoSede;
  	
	public PbandiDTipoSedeVO() {}
  	
	public PbandiDTipoSedeVO (BigDecimal idTipoSede) {
	
		this. idTipoSede =  idTipoSede;
	}
  	
	public PbandiDTipoSedeVO (String descBreveTipoSede, Date dtFineValidita, String descTipoSede, Date dtInizioValidita, BigDecimal idTipoSede) {
	
		this. descBreveTipoSede =  descBreveTipoSede;
		this. dtFineValidita =  dtFineValidita;
		this. descTipoSede =  descTipoSede;
		this. dtInizioValidita =  dtInizioValidita;
		this. idTipoSede =  idTipoSede;
	}
  	
  	
	public String getDescBreveTipoSede() {
		return descBreveTipoSede;
	}
	
	public void setDescBreveTipoSede(String descBreveTipoSede) {
		this.descBreveTipoSede = descBreveTipoSede;
	}
	
	public Date getDtFineValidita() {
		return dtFineValidita;
	}
	
	public void setDtFineValidita(Date dtFineValidita) {
		this.dtFineValidita = dtFineValidita;
	}
	
	public String getDescTipoSede() {
		return descTipoSede;
	}
	
	public void setDescTipoSede(String descTipoSede) {
		this.descTipoSede = descTipoSede;
	}
	
	public Date getDtInizioValidita() {
		return dtInizioValidita;
	}
	
	public void setDtInizioValidita(Date dtInizioValidita) {
		this.dtInizioValidita = dtInizioValidita;
	}
	
	public BigDecimal getIdTipoSede() {
		return idTipoSede;
	}
	
	public void setIdTipoSede(BigDecimal idTipoSede) {
		this.idTipoSede = idTipoSede;
	}
	
	public boolean isValid() {
		boolean isValid = false;
                if (isPKValid() && descBreveTipoSede != null && descTipoSede != null && dtInizioValidita != null) {
   			isValid = true;
   		}
   		return isValid;
	}
	public boolean isPKValid() {
		boolean isPkValid = false;
		if (idTipoSede != null ) {
   			isPkValid = true;
   		}

   		return isPkValid;
	}
	
	public String toString() {
		
	    String temp = "";
	    StringBuffer sb = new StringBuffer();
	    sb.append("\t\n" + this.getClass().getName() + "\t\n");
	    
	    temp = DataFilter.removeNull( descBreveTipoSede);
	    if (!DataFilter.isEmpty(temp)) sb.append(" descBreveTipoSede: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtFineValidita);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtFineValidita: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( descTipoSede);
	    if (!DataFilter.isEmpty(temp)) sb.append(" descTipoSede: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtInizioValidita);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtInizioValidita: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idTipoSede);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idTipoSede: " + temp + "\t\n");
	    
	    return sb.toString();
	}
	
	public java.util.List<String> getPK() {
		java.util.List<String> pk=new java.util.ArrayList<String>();
		
			pk.add("idTipoSede");
		
	    return pk;
	}
	
	
}
