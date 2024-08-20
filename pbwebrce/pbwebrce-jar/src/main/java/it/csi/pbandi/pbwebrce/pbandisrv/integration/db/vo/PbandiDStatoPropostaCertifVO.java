/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/


package it.csi.pbandi.pbwebrce.pbandisrv.integration.db.vo;

import java.math.*;
import java.sql.Date;
import it.csi.pbandi.pbwebrce.pbandisrv.integration.util.DataFilter;



public class PbandiDStatoPropostaCertifVO extends GenericVO {

  	
  	private String descBreveStatoPropostaCert;
  	
  	private BigDecimal idStatoPropostaCertif;
  	
  	private Date dtFineValidita;
  	
  	private String descStatoPropostaCertif;
  	
  	private BigDecimal idTipoStatoPropCert;
  	
  	private Date dtInizioValidita;
  	
	public PbandiDStatoPropostaCertifVO() {}
  	
	public PbandiDStatoPropostaCertifVO (BigDecimal idStatoPropostaCertif) {
	
		this. idStatoPropostaCertif =  idStatoPropostaCertif;
	}
  	
	public PbandiDStatoPropostaCertifVO (String descBreveStatoPropostaCert, BigDecimal idStatoPropostaCertif, Date dtFineValidita, String descStatoPropostaCertif, BigDecimal idTipoStatoPropCert, Date dtInizioValidita) {
	
		this. descBreveStatoPropostaCert =  descBreveStatoPropostaCert;
		this. idStatoPropostaCertif =  idStatoPropostaCertif;
		this. dtFineValidita =  dtFineValidita;
		this. descStatoPropostaCertif =  descStatoPropostaCertif;
		this. idTipoStatoPropCert =  idTipoStatoPropCert;
		this. dtInizioValidita =  dtInizioValidita;
	}
  	
  	
	public String getDescBreveStatoPropostaCert() {
		return descBreveStatoPropostaCert;
	}
	
	public void setDescBreveStatoPropostaCert(String descBreveStatoPropostaCert) {
		this.descBreveStatoPropostaCert = descBreveStatoPropostaCert;
	}
	
	public BigDecimal getIdStatoPropostaCertif() {
		return idStatoPropostaCertif;
	}
	
	public void setIdStatoPropostaCertif(BigDecimal idStatoPropostaCertif) {
		this.idStatoPropostaCertif = idStatoPropostaCertif;
	}
	
	public Date getDtFineValidita() {
		return dtFineValidita;
	}
	
	public void setDtFineValidita(Date dtFineValidita) {
		this.dtFineValidita = dtFineValidita;
	}
	
	public String getDescStatoPropostaCertif() {
		return descStatoPropostaCertif;
	}
	
	public void setDescStatoPropostaCertif(String descStatoPropostaCertif) {
		this.descStatoPropostaCertif = descStatoPropostaCertif;
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
                if (isPKValid() && descBreveStatoPropostaCert != null && descStatoPropostaCertif != null && idTipoStatoPropCert != null && dtInizioValidita != null) {
   			isValid = true;
   		}
   		return isValid;
	}
	public boolean isPKValid() {
		boolean isPkValid = false;
		if (idStatoPropostaCertif != null ) {
   			isPkValid = true;
   		}

   		return isPkValid;
	}
	
	public String toString() {
		
	    String temp = "";
	    StringBuffer sb = new StringBuffer();
	    sb.append("\t\n" + this.getClass().getName() + "\t\n");
	    
	    temp = DataFilter.removeNull( descBreveStatoPropostaCert);
	    if (!DataFilter.isEmpty(temp)) sb.append(" descBreveStatoPropostaCert: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idStatoPropostaCertif);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idStatoPropostaCertif: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtFineValidita);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtFineValidita: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( descStatoPropostaCertif);
	    if (!DataFilter.isEmpty(temp)) sb.append(" descStatoPropostaCertif: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idTipoStatoPropCert);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idTipoStatoPropCert: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtInizioValidita);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtInizioValidita: " + temp + "\t\n");
	    
	    return sb.toString();
	}
	
	public java.util.List<String> getPK() {
		java.util.List<String> pk=new java.util.ArrayList<String>();
		
			pk.add("idStatoPropostaCertif");
		
	    return pk;
	}
	
	
}
