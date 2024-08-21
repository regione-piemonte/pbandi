/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/


package it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo;

import java.math.*;
import java.sql.Date;
import it.csi.pbandi.pbservizit.pbandisrv.integration.util.DataFilter;



public class PbandiDTipoStatoPropCertVO extends GenericVO {

  	
  	private Date dtFineValidita;
  	
  	private String descTipoStatoPropCert;
  	
  	private String descBreveTipoStatoProCert;
  	
  	private BigDecimal idTipoStatoPropCert;
  	
  	private Date dtInizioValidita;
  	
	public PbandiDTipoStatoPropCertVO() {}
  	
	public PbandiDTipoStatoPropCertVO (BigDecimal idTipoStatoPropCert) {
	
		this. idTipoStatoPropCert =  idTipoStatoPropCert;
	}
  	
	public PbandiDTipoStatoPropCertVO (Date dtFineValidita, String descTipoStatoPropCert, String descBreveTipoStatoProCert, BigDecimal idTipoStatoPropCert, Date dtInizioValidita) {
	
		this. dtFineValidita =  dtFineValidita;
		this. descTipoStatoPropCert =  descTipoStatoPropCert;
		this. descBreveTipoStatoProCert =  descBreveTipoStatoProCert;
		this. idTipoStatoPropCert =  idTipoStatoPropCert;
		this. dtInizioValidita =  dtInizioValidita;
	}
  	
  	
	public Date getDtFineValidita() {
		return dtFineValidita;
	}
	
	public void setDtFineValidita(Date dtFineValidita) {
		this.dtFineValidita = dtFineValidita;
	}
	
	public String getDescTipoStatoPropCert() {
		return descTipoStatoPropCert;
	}
	
	public void setDescTipoStatoPropCert(String descTipoStatoPropCert) {
		this.descTipoStatoPropCert = descTipoStatoPropCert;
	}
	
	public String getDescBreveTipoStatoProCert() {
		return descBreveTipoStatoProCert;
	}
	
	public void setDescBreveTipoStatoProCert(String descBreveTipoStatoProCert) {
		this.descBreveTipoStatoProCert = descBreveTipoStatoProCert;
	}
	
	public BigDecimal getIdTipoStatoPropCert() {
		return idTipoStatoPropCert;
	}
	
	public void setIdTipoStatoPropCert(BigDecimal idTipoStatoPropCert) {
		this.idTipoStatoPropCert = idTipoStatoPropCert;
	}
	
	public Date getDtInizioValidita() {
		return dtInizioValidita;
	}
	
	public void setDtInizioValidita(Date dtInizioValidita) {
		this.dtInizioValidita = dtInizioValidita;
	}
	
	public boolean isValid() {
		boolean isValid = false;
                if (isPKValid() && descTipoStatoPropCert != null && descBreveTipoStatoProCert != null && dtInizioValidita != null) {
   			isValid = true;
   		}
   		return isValid;
	}
	public boolean isPKValid() {
		boolean isPkValid = false;
		if (idTipoStatoPropCert != null ) {
   			isPkValid = true;
   		}

   		return isPkValid;
	}
	
	public String toString() {
		
	    String temp = "";
	    StringBuffer sb = new StringBuffer();
	    sb.append("\t\n" + this.getClass().getName() + "\t\n");
	    
	    temp = DataFilter.removeNull( dtFineValidita);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtFineValidita: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( descTipoStatoPropCert);
	    if (!DataFilter.isEmpty(temp)) sb.append(" descTipoStatoPropCert: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( descBreveTipoStatoProCert);
	    if (!DataFilter.isEmpty(temp)) sb.append(" descBreveTipoStatoProCert: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idTipoStatoPropCert);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idTipoStatoPropCert: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtInizioValidita);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtInizioValidita: " + temp + "\t\n");
	    
	    return sb.toString();
	}
	
	public java.util.List<String> getPK() {
		java.util.List<String> pk=new java.util.ArrayList<String>();
		
			pk.add("idTipoStatoPropCert");
		
	    return pk;
	}
	
	
}
