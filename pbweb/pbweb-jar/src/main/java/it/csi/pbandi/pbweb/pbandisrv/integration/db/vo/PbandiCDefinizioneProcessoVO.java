/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/


package it.csi.pbandi.pbweb.pbandisrv.integration.db.vo;

import java.math.*;
import java.sql.Date;
import it.csi.pbandi.pbweb.pbandisrv.integration.util.DataFilter;



public class PbandiCDefinizioneProcessoVO extends GenericVO {

  	
  	private BigDecimal idDefinizioneProcesso;
  	
  	private String uuidProcesso;
  	
	public PbandiCDefinizioneProcessoVO() {}
  	
	public PbandiCDefinizioneProcessoVO (BigDecimal idDefinizioneProcesso) {
	
		this. idDefinizioneProcesso =  idDefinizioneProcesso;
	}
  	
	public PbandiCDefinizioneProcessoVO (BigDecimal idDefinizioneProcesso, String uuidProcesso) {
	
		this. idDefinizioneProcesso =  idDefinizioneProcesso;
		this. uuidProcesso =  uuidProcesso;
	}
  	
  	
	public BigDecimal getIdDefinizioneProcesso() {
		return idDefinizioneProcesso;
	}
	
	public void setIdDefinizioneProcesso(BigDecimal idDefinizioneProcesso) {
		this.idDefinizioneProcesso = idDefinizioneProcesso;
	}
	
	public String getUuidProcesso() {
		return uuidProcesso;
	}
	
	public void setUuidProcesso(String uuidProcesso) {
		this.uuidProcesso = uuidProcesso;
	}
	
	public boolean isValid() {
		boolean isValid = false;
                if (isPKValid() && uuidProcesso != null) {
   			isValid = true;
   		}
   		return isValid;
	}
	public boolean isPKValid() {
		boolean isPkValid = false;
		if (idDefinizioneProcesso != null ) {
   			isPkValid = true;
   		}

   		return isPkValid;
	}
	
	public String toString() {
		
	    String temp = "";
	    StringBuffer sb = new StringBuffer();
	    sb.append("\t\n" + this.getClass().getName() + "\t\n");
	    
	    temp = DataFilter.removeNull( idDefinizioneProcesso);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idDefinizioneProcesso: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( uuidProcesso);
	    if (!DataFilter.isEmpty(temp)) sb.append(" uuidProcesso: " + temp + "\t\n");
	    
	    return sb.toString();
	}
	
	public java.util.List getPK() {
		java.util.List pk=new java.util.ArrayList();
		
			pk.add("idDefinizioneProcesso");
		
	    return pk;
	}
	
	
}
