/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/


package it.csi.pbandi.pbwebrce.pbandisrv.integration.db.vo;

import java.math.*;
import java.sql.Date;
import it.csi.pbandi.pbwebrce.pbandisrv.integration.util.DataFilter;



public class PbandiCRuoloDiProcessoVO extends GenericVO {

  	
  	private String descRuoloDiProcesso;
  	
  	private BigDecimal idDefinizioneProcesso;
  	
  	private BigDecimal idRuoloDiProcesso;
  	
  	private String codice;
  	
	public PbandiCRuoloDiProcessoVO() {}
  	
	public PbandiCRuoloDiProcessoVO (BigDecimal idRuoloDiProcesso) {
	
		this. idRuoloDiProcesso =  idRuoloDiProcesso;
	}
  	
	public PbandiCRuoloDiProcessoVO (String descRuoloDiProcesso, BigDecimal idDefinizioneProcesso, BigDecimal idRuoloDiProcesso, String codice) {
	
		this. descRuoloDiProcesso =  descRuoloDiProcesso;
		this. idDefinizioneProcesso =  idDefinizioneProcesso;
		this. idRuoloDiProcesso =  idRuoloDiProcesso;
		this. codice =  codice;
	}
  	
  	
	public String getDescRuoloDiProcesso() {
		return descRuoloDiProcesso;
	}
	
	public void setDescRuoloDiProcesso(String descRuoloDiProcesso) {
		this.descRuoloDiProcesso = descRuoloDiProcesso;
	}
	
	public BigDecimal getIdDefinizioneProcesso() {
		return idDefinizioneProcesso;
	}
	
	public void setIdDefinizioneProcesso(BigDecimal idDefinizioneProcesso) {
		this.idDefinizioneProcesso = idDefinizioneProcesso;
	}
	
	public BigDecimal getIdRuoloDiProcesso() {
		return idRuoloDiProcesso;
	}
	
	public void setIdRuoloDiProcesso(BigDecimal idRuoloDiProcesso) {
		this.idRuoloDiProcesso = idRuoloDiProcesso;
	}
	
	public String getCodice() {
		return codice;
	}
	
	public void setCodice(String codice) {
		this.codice = codice;
	}
	
	public boolean isValid() {
		boolean isValid = false;
                if (isPKValid() && idDefinizioneProcesso != null && codice != null) {
   			isValid = true;
   		}
   		return isValid;
	}
	public boolean isPKValid() {
		boolean isPkValid = false;
		if (idRuoloDiProcesso != null ) {
   			isPkValid = true;
   		}

   		return isPkValid;
	}
	
	public String toString() {
		
	    String temp = "";
	    StringBuffer sb = new StringBuffer();
	    sb.append("\t\n" + this.getClass().getName() + "\t\n");
	    
	    temp = DataFilter.removeNull( descRuoloDiProcesso);
	    if (!DataFilter.isEmpty(temp)) sb.append(" descRuoloDiProcesso: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idDefinizioneProcesso);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idDefinizioneProcesso: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idRuoloDiProcesso);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idRuoloDiProcesso: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( codice);
	    if (!DataFilter.isEmpty(temp)) sb.append(" codice: " + temp + "\t\n");
	    
	    return sb.toString();
	}
	
	public java.util.List getPK() {
		java.util.List pk=new java.util.ArrayList();
		
			pk.add("idRuoloDiProcesso");
		
	    return pk;
	}
	
	
}
