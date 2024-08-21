/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/


package it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo;

import java.math.*;
import java.sql.Date;
import it.csi.pbandi.pbservizit.pbandisrv.integration.util.DataFilter;



public class PbandiCAttributoVO extends GenericVO {

  	
  	private String nomeAttributo;
  	
  	private BigDecimal keyPositionId;
  	
  	private BigDecimal idAttributo;
  	
  	private BigDecimal idEntita;
  	
  	private String flagDaTracciare;
  	
	public PbandiCAttributoVO() {}
  	
	public PbandiCAttributoVO (BigDecimal idAttributo) {
	
		this. idAttributo =  idAttributo;
	}
  	
	public PbandiCAttributoVO (String nomeAttributo, BigDecimal keyPositionId, BigDecimal idAttributo, BigDecimal idEntita, String flagDaTracciare) {
	
		this. nomeAttributo =  nomeAttributo;
		this. keyPositionId =  keyPositionId;
		this. idAttributo =  idAttributo;
		this. idEntita =  idEntita;
		this. flagDaTracciare =  flagDaTracciare;
	}
  	
  	
	public String getNomeAttributo() {
		return nomeAttributo;
	}
	
	public void setNomeAttributo(String nomeAttributo) {
		this.nomeAttributo = nomeAttributo;
	}
	
	public BigDecimal getKeyPositionId() {
		return keyPositionId;
	}
	
	public void setKeyPositionId(BigDecimal keyPositionId) {
		this.keyPositionId = keyPositionId;
	}
	
	public BigDecimal getIdAttributo() {
		return idAttributo;
	}
	
	public void setIdAttributo(BigDecimal idAttributo) {
		this.idAttributo = idAttributo;
	}
	
	public BigDecimal getIdEntita() {
		return idEntita;
	}
	
	public void setIdEntita(BigDecimal idEntita) {
		this.idEntita = idEntita;
	}
	
	public String getFlagDaTracciare() {
		return flagDaTracciare;
	}
	
	public void setFlagDaTracciare(String flagDaTracciare) {
		this.flagDaTracciare = flagDaTracciare;
	}
	
	public boolean isValid() {
		boolean isValid = false;
                if (isPKValid() && nomeAttributo != null && idEntita != null && flagDaTracciare != null) {
   			isValid = true;
   		}
   		return isValid;
	}
	public boolean isPKValid() {
		boolean isPkValid = false;
		if (idAttributo != null ) {
   			isPkValid = true;
   		}

   		return isPkValid;
	}
	
	public String toString() {
		
	    String temp = "";
	    StringBuffer sb = new StringBuffer();
	    sb.append("\t\n" + this.getClass().getName() + "\t\n");
	    
	    temp = DataFilter.removeNull( nomeAttributo);
	    if (!DataFilter.isEmpty(temp)) sb.append(" nomeAttributo: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( keyPositionId);
	    if (!DataFilter.isEmpty(temp)) sb.append(" keyPositionId: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idAttributo);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idAttributo: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idEntita);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idEntita: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( flagDaTracciare);
	    if (!DataFilter.isEmpty(temp)) sb.append(" flagDaTracciare: " + temp + "\t\n");
	    
	    return sb.toString();
	}
	
	public java.util.List getPK() {
		java.util.List pk=new java.util.ArrayList();
		
			pk.add("idAttributo");
		
	    return pk;
	}
	
	
}
