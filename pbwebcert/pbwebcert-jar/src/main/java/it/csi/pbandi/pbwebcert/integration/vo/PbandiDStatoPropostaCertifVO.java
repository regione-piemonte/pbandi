/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebcert.integration.vo;

import java.math.BigDecimal;
import java.sql.Date;



public class PbandiDStatoPropostaCertifVO {
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

	
	public java.util.List<String> getPK() {
		java.util.List<String> pk=new java.util.ArrayList<String>();
		
			pk.add("idStatoPropostaCertif");
		
	    return pk;
	}
}
