/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/


package it.csi.pbandi.pbwebrce.pbandisrv.integration.db.vo;

import java.math.*;
import java.sql.Date;
import it.csi.pbandi.pbwebrce.pbandisrv.integration.util.DataFilter;



public class PbandiRLineaIntervMappingVO extends GenericVO {

  	
  	private BigDecimal idLineaDiInterventoMigrata;
  	
  	private BigDecimal idLineaDiInterventoAttuale;
  	
	public PbandiRLineaIntervMappingVO() {}
  	
	public PbandiRLineaIntervMappingVO (BigDecimal idLineaDiInterventoMigrata, BigDecimal idLineaDiInterventoAttuale) {
	
		this. idLineaDiInterventoMigrata =  idLineaDiInterventoMigrata;
		this. idLineaDiInterventoAttuale =  idLineaDiInterventoAttuale;
	}
  	
	public BigDecimal getIdLineaDiInterventoMigrata() {
		return idLineaDiInterventoMigrata;
	}
	
	public void setIdLineaDiInterventoMigrata(BigDecimal idLineaDiInterventoMigrata) {
		this.idLineaDiInterventoMigrata = idLineaDiInterventoMigrata;
	}
	
	public BigDecimal getIdLineaDiInterventoAttuale() {
		return idLineaDiInterventoAttuale;
	}
	
	public void setIdLineaDiInterventoAttuale(BigDecimal idLineaDiInterventoAttuale) {
		this.idLineaDiInterventoAttuale = idLineaDiInterventoAttuale;
	}
	
	public boolean isValid() {
		boolean isValid = false;
                if (isPKValid()) {
   			isValid = true;
   		}
   		return isValid;
	}
	public boolean isPKValid() {
		boolean isPkValid = false;
		if (idLineaDiInterventoMigrata != null && idLineaDiInterventoAttuale != null ) {
   			isPkValid = true;
   		}

   		return isPkValid;
	}
	
	public String toString() {
		
	    String temp = "";
	    StringBuffer sb = new StringBuffer();
	    sb.append("\t\n" + this.getClass().getName() + "\t\n");
	    
	    temp = DataFilter.removeNull( idLineaDiInterventoMigrata);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idLineaDiInterventoMigrata: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idLineaDiInterventoAttuale);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idLineaDiInterventoAttuale: " + temp + "\t\n");
	    
	    return sb.toString();
	}
	
	public java.util.List<String> getPK() {
		java.util.List<String> pk=new java.util.ArrayList<String>();
		
			pk.add("idLineaDiInterventoMigrata");
		
			pk.add("idLineaDiInterventoAttuale");
		
	    return pk;
	}
	
	
}
