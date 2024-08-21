/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/


package it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo;

import java.math.*;
import java.sql.Date;
import it.csi.pbandi.pbservizit.pbandisrv.integration.util.DataFilter;



public class PbandiTFlussiUploadVO extends GenericVO {

  	
  	private BigDecimal idUtenteUpload;
  	
  	private BigDecimal idProcessoBatch;
  	
  	private BigDecimal idFlusso;
  	
  	private Date dtAcquisizione;
  	
  	private String nomeFlusso;
  	
	public PbandiTFlussiUploadVO() {}
  	
	public PbandiTFlussiUploadVO (BigDecimal idFlusso) {
	
		this. idFlusso =  idFlusso;
	}
  	
	public PbandiTFlussiUploadVO (BigDecimal idUtenteUpload, BigDecimal idProcessoBatch, BigDecimal idFlusso, Date dtAcquisizione, String nomeFlusso) {
	
		this. idUtenteUpload =  idUtenteUpload;
		this. idProcessoBatch =  idProcessoBatch;
		this. idFlusso =  idFlusso;
		this. dtAcquisizione =  dtAcquisizione;
		this. nomeFlusso =  nomeFlusso;
	}
  	
  	
	public BigDecimal getIdUtenteUpload() {
		return idUtenteUpload;
	}
	
	public void setIdUtenteUpload(BigDecimal idUtenteUpload) {
		this.idUtenteUpload = idUtenteUpload;
	}
	
	public BigDecimal getIdProcessoBatch() {
		return idProcessoBatch;
	}
	
	public void setIdProcessoBatch(BigDecimal idProcessoBatch) {
		this.idProcessoBatch = idProcessoBatch;
	}
	
	public BigDecimal getIdFlusso() {
		return idFlusso;
	}
	
	public void setIdFlusso(BigDecimal idFlusso) {
		this.idFlusso = idFlusso;
	}
	
	public Date getDtAcquisizione() {
		return dtAcquisizione;
	}
	
	public void setDtAcquisizione(Date dtAcquisizione) {
		this.dtAcquisizione = dtAcquisizione;
	}
	
	public String getNomeFlusso() {
		return nomeFlusso;
	}
	
	public void setNomeFlusso(String nomeFlusso) {
		this.nomeFlusso = nomeFlusso;
	}
	
	public boolean isValid() {
		boolean isValid = false;
                if (isPKValid() && idUtenteUpload != null && dtAcquisizione != null && nomeFlusso != null) {
   			isValid = true;
   		}
   		return isValid;
	}
	public boolean isPKValid() {
		boolean isPkValid = false;
		if (idFlusso != null ) {
   			isPkValid = true;
   		}

   		return isPkValid;
	}
	
	public String toString() {
		
	    String temp = "";
	    StringBuffer sb = new StringBuffer();
	    sb.append("\t\n" + this.getClass().getName() + "\t\n");
	    
	    temp = DataFilter.removeNull( idUtenteUpload);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idUtenteUpload: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idProcessoBatch);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idProcessoBatch: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idFlusso);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idFlusso: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtAcquisizione);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtAcquisizione: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( nomeFlusso);
	    if (!DataFilter.isEmpty(temp)) sb.append(" nomeFlusso: " + temp + "\t\n");
	    
	    return sb.toString();
	}
	
	public java.util.List getPK() {
		java.util.List pk=new java.util.ArrayList();
		
			pk.add("idFlusso");
		
	    return pk;
	}
	
	
}
