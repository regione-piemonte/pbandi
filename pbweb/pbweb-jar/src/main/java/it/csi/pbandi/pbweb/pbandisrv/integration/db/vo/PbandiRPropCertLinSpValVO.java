/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/


package it.csi.pbandi.pbweb.pbandisrv.integration.db.vo;

import java.math.*;
import java.sql.Date;
import it.csi.pbandi.pbweb.pbandisrv.integration.util.DataFilter;



public class PbandiRPropCertLinSpValVO extends GenericVO {

  	
  	private BigDecimal idPropostaCertificaz;
  	
  	private BigDecimal spesaValidata;
  	
  	private BigDecimal idLineaDiIntervento;
  	
  	private BigDecimal idTipoSoggFinanziat;
  	
	public PbandiRPropCertLinSpValVO() {}
  	
	public PbandiRPropCertLinSpValVO (BigDecimal idPropostaCertificaz, BigDecimal idLineaDiIntervento, BigDecimal idTipoSoggFinanziat) {
	
		this. idPropostaCertificaz =  idPropostaCertificaz;
		this. idLineaDiIntervento =  idLineaDiIntervento;
		this. idTipoSoggFinanziat =  idTipoSoggFinanziat;
	}
  	
	public PbandiRPropCertLinSpValVO (BigDecimal idPropostaCertificaz, BigDecimal spesaValidata, BigDecimal idLineaDiIntervento, BigDecimal idTipoSoggFinanziat) {
	
		this. idPropostaCertificaz =  idPropostaCertificaz;
		this. spesaValidata =  spesaValidata;
		this. idLineaDiIntervento =  idLineaDiIntervento;
		this. idTipoSoggFinanziat =  idTipoSoggFinanziat;
	}
  	
  	
	public BigDecimal getIdPropostaCertificaz() {
		return idPropostaCertificaz;
	}
	
	public void setIdPropostaCertificaz(BigDecimal idPropostaCertificaz) {
		this.idPropostaCertificaz = idPropostaCertificaz;
	}
	
	public BigDecimal getSpesaValidata() {
		return spesaValidata;
	}
	
	public void setSpesaValidata(BigDecimal spesaValidata) {
		this.spesaValidata = spesaValidata;
	}
	
	public BigDecimal getIdLineaDiIntervento() {
		return idLineaDiIntervento;
	}
	
	public void setIdLineaDiIntervento(BigDecimal idLineaDiIntervento) {
		this.idLineaDiIntervento = idLineaDiIntervento;
	}
	
	public BigDecimal getIdTipoSoggFinanziat() {
		return idTipoSoggFinanziat;
	}
	
	public void setIdTipoSoggFinanziat(BigDecimal idTipoSoggFinanziat) {
		this.idTipoSoggFinanziat = idTipoSoggFinanziat;
	}
	
	public boolean isValid() {
		boolean isValid = false;
                if (isPKValid() && spesaValidata != null) {
   			isValid = true;
   		}
   		return isValid;
	}
	public boolean isPKValid() {
		boolean isPkValid = false;
		if (idPropostaCertificaz != null && idLineaDiIntervento != null && idTipoSoggFinanziat != null ) {
   			isPkValid = true;
   		}

   		return isPkValid;
	}
	
	public String toString() {
		
	    String temp = "";
	    StringBuffer sb = new StringBuffer();
	    sb.append("\t\n" + this.getClass().getName() + "\t\n");
	    
	    temp = DataFilter.removeNull( idPropostaCertificaz);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idPropostaCertificaz: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( spesaValidata);
	    if (!DataFilter.isEmpty(temp)) sb.append(" spesaValidata: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idLineaDiIntervento);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idLineaDiIntervento: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idTipoSoggFinanziat);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idTipoSoggFinanziat: " + temp + "\t\n");
	    
	    return sb.toString();
	}
	
	public java.util.List getPK() {
		java.util.List pk=new java.util.ArrayList();
		
			pk.add("idPropostaCertificaz");
		
			pk.add("idLineaDiIntervento");
		
			pk.add("idTipoSoggFinanziat");
		
	    return pk;
	}
	
	
}
