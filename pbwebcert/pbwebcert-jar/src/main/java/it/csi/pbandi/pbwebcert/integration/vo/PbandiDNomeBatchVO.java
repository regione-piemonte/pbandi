/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebcert.integration.vo;

import java.math.BigDecimal;


public class PbandiDNomeBatchVO {
private String descBatch;
  	
  	private BigDecimal idNomeBatch;
  	
  	private String nomeBatch;
  	
	public PbandiDNomeBatchVO() {}
  	
	public PbandiDNomeBatchVO (BigDecimal idNomeBatch) {
	
		this. idNomeBatch =  idNomeBatch;
	}
  	
	public PbandiDNomeBatchVO (String descBatch, BigDecimal idNomeBatch, String nomeBatch) {
	
		this. descBatch =  descBatch;
		this. idNomeBatch =  idNomeBatch;
		this. nomeBatch =  nomeBatch;
	}
  	
  	
	public String getDescBatch() {
		return descBatch;
	}
	
	public void setDescBatch(String descBatch) {
		this.descBatch = descBatch;
	}
	
	public BigDecimal getIdNomeBatch() {
		return idNomeBatch;
	}
	
	public void setIdNomeBatch(BigDecimal idNomeBatch) {
		this.idNomeBatch = idNomeBatch;
	}
	
	public String getNomeBatch() {
		return nomeBatch;
	}
	
	public void setNomeBatch(String nomeBatch) {
		this.nomeBatch = nomeBatch;
	}
	
	public boolean isValid() {
		boolean isValid = false;
                if (isPKValid() && descBatch != null && nomeBatch != null) {
   			isValid = true;
   		}
   		return isValid;
	}
	public boolean isPKValid() {
		boolean isPkValid = false;
		if (idNomeBatch != null ) {
   			isPkValid = true;
   		}

   		return isPkValid;
	}
	
	public java.util.List<String> getPK() {
		java.util.List<String> pk=new java.util.ArrayList<String>();
		
			pk.add("idNomeBatch");
		
	    return pk;
	}
}
