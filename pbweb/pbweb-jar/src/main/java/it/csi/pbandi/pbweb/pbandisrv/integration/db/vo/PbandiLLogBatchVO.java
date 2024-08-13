/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/


package it.csi.pbandi.pbweb.pbandisrv.integration.db.vo;

import java.math.*;
import java.sql.Date;
import it.csi.pbandi.pbweb.pbandisrv.integration.util.DataFilter;



public class PbandiLLogBatchVO extends GenericVO {

  	
  	private BigDecimal idProcessoBatch;
  	
  	private String codiceErrore;
  	
  	private BigDecimal idLogBatch;
  	
  	private String messaggioErrore;
  	
  	private Date dtInserimento;
  	
	public PbandiLLogBatchVO() {}
  	
	public PbandiLLogBatchVO (BigDecimal idLogBatch) {
	
		this. idLogBatch =  idLogBatch;
	}
  	
	public PbandiLLogBatchVO (BigDecimal idProcessoBatch, String codiceErrore, BigDecimal idLogBatch, String messaggioErrore, Date dtInserimento) {
	
		this. idProcessoBatch =  idProcessoBatch;
		this. codiceErrore =  codiceErrore;
		this. idLogBatch =  idLogBatch;
		this. messaggioErrore =  messaggioErrore;
		this. dtInserimento =  dtInserimento;
	}
  	
  	
	public BigDecimal getIdProcessoBatch() {
		return idProcessoBatch;
	}
	
	public void setIdProcessoBatch(BigDecimal idProcessoBatch) {
		this.idProcessoBatch = idProcessoBatch;
	}
	
	public String getCodiceErrore() {
		return codiceErrore;
	}
	
	public void setCodiceErrore(String codiceErrore) {
		this.codiceErrore = codiceErrore;
	}
	
	public BigDecimal getIdLogBatch() {
		return idLogBatch;
	}
	
	public void setIdLogBatch(BigDecimal idLogBatch) {
		this.idLogBatch = idLogBatch;
	}
	
	public String getMessaggioErrore() {
		return messaggioErrore;
	}
	
	public void setMessaggioErrore(String messaggioErrore) {
		this.messaggioErrore = messaggioErrore;
	}
	
	public Date getDtInserimento() {
		return dtInserimento;
	}
	
	public void setDtInserimento(Date dtInserimento) {
		this.dtInserimento = dtInserimento;
	}
	
	public boolean isValid() {
		boolean isValid = false;
                if (isPKValid() && idProcessoBatch != null && codiceErrore != null && dtInserimento != null) {
   			isValid = true;
   		}
   		return isValid;
	}
	public boolean isPKValid() {
		boolean isPkValid = false;
		if (idLogBatch != null ) {
   			isPkValid = true;
   		}

   		return isPkValid;
	}
	
	public String toString() {
		
	    String temp = "";
	    StringBuffer sb = new StringBuffer();
	    sb.append("\t\n" + this.getClass().getName() + "\t\n");
	    
	    temp = DataFilter.removeNull( idProcessoBatch);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idProcessoBatch: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( codiceErrore);
	    if (!DataFilter.isEmpty(temp)) sb.append(" codiceErrore: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idLogBatch);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idLogBatch: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( messaggioErrore);
	    if (!DataFilter.isEmpty(temp)) sb.append(" messaggioErrore: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtInserimento);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtInserimento: " + temp + "\t\n");
	    
	    return sb.toString();
	}
	
	public java.util.List getPK() {
		java.util.List pk=new java.util.ArrayList();
		
			pk.add("idLogBatch");
		
	    return pk;
	}
	
	
}
