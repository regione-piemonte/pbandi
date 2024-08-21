/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/


package it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo;

import java.math.*;
import java.sql.Date;
import it.csi.pbandi.pbservizit.pbandisrv.integration.util.DataFilter;



public class PbandiRBlTipoDocMicroSezVO extends GenericVO {

  	
  	private BigDecimal idMicroSezioneModulo;
  	
  	private BigDecimal numOrdinamentoMicroSezione;
  	
  	private BigDecimal progrBlTipoDocSezMod;
  	
	public PbandiRBlTipoDocMicroSezVO() {}
  	
	public PbandiRBlTipoDocMicroSezVO (BigDecimal idMicroSezioneModulo, BigDecimal progrBlTipoDocSezMod) {
	
		this. idMicroSezioneModulo =  idMicroSezioneModulo;
		this. progrBlTipoDocSezMod =  progrBlTipoDocSezMod;
	}
  	
	public PbandiRBlTipoDocMicroSezVO (BigDecimal idMicroSezioneModulo, BigDecimal numOrdinamentoMicroSezione, BigDecimal progrBlTipoDocSezMod) {
	
		this. idMicroSezioneModulo =  idMicroSezioneModulo;
		this. numOrdinamentoMicroSezione =  numOrdinamentoMicroSezione;
		this. progrBlTipoDocSezMod =  progrBlTipoDocSezMod;
	}
  	
  	
	public BigDecimal getIdMicroSezioneModulo() {
		return idMicroSezioneModulo;
	}
	
	public void setIdMicroSezioneModulo(BigDecimal idMicroSezioneModulo) {
		this.idMicroSezioneModulo = idMicroSezioneModulo;
	}
	
	public BigDecimal getNumOrdinamentoMicroSezione() {
		return numOrdinamentoMicroSezione;
	}
	
	public void setNumOrdinamentoMicroSezione(BigDecimal numOrdinamentoMicroSezione) {
		this.numOrdinamentoMicroSezione = numOrdinamentoMicroSezione;
	}
	
	public BigDecimal getProgrBlTipoDocSezMod() {
		return progrBlTipoDocSezMod;
	}
	
	public void setProgrBlTipoDocSezMod(BigDecimal progrBlTipoDocSezMod) {
		this.progrBlTipoDocSezMod = progrBlTipoDocSezMod;
	}
	
	public boolean isValid() {
		boolean isValid = false;
                if (isPKValid() && numOrdinamentoMicroSezione != null) {
   			isValid = true;
   		}
   		return isValid;
	}
	public boolean isPKValid() {
		boolean isPkValid = false;
		if (idMicroSezioneModulo != null && progrBlTipoDocSezMod != null ) {
   			isPkValid = true;
   		}

   		return isPkValid;
	}
	
	public String toString() {
		
	    String temp = "";
	    StringBuffer sb = new StringBuffer();
	    sb.append("\t\n" + this.getClass().getName() + "\t\n");
	    
	    temp = DataFilter.removeNull( idMicroSezioneModulo);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idMicroSezioneModulo: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( numOrdinamentoMicroSezione);
	    if (!DataFilter.isEmpty(temp)) sb.append(" numOrdinamentoMicroSezione: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( progrBlTipoDocSezMod);
	    if (!DataFilter.isEmpty(temp)) sb.append(" progrBlTipoDocSezMod: " + temp + "\t\n");
	    
	    return sb.toString();
	}
	
	public java.util.List getPK() {
		java.util.List pk=new java.util.ArrayList();
		
			pk.add("idMicroSezioneModulo");
		
			pk.add("progrBlTipoDocSezMod");
		
	    return pk;
	}
	
	
}
