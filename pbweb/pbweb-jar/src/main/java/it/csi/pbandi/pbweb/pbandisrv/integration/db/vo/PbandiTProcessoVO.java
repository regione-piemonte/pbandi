package it.csi.pbandi.pbweb.pbandisrv.integration.db.vo;

import it.csi.pbandi.pbweb.pbandisrv.integration.util.DataFilter;

import java.math.BigDecimal;
import java.util.List;

public class PbandiTProcessoVO extends GenericVO {
	
	private BigDecimal idProcesso;
	private String descrProcesso;
	
	public PbandiTProcessoVO() {}
	
	public PbandiTProcessoVO (BigDecimal idProcesso) {
		this.idProcesso =  idProcesso;
	}
	
	public PbandiTProcessoVO ( BigDecimal idProcesso, String descrProcesso) {
		this.idProcesso =  idProcesso;
		this.descrProcesso =  descrProcesso;
	}
	
	public BigDecimal getIdProcesso() {
		return idProcesso;
	}
	public void setIdProcesso(BigDecimal idProcesso) {
		this.idProcesso = idProcesso;
	}
	public String getDescrProcesso() {
		return descrProcesso;
	}
	public void setDescrProcesso(String descrProcesso) {
		this.descrProcesso = descrProcesso;
	}
	
	public boolean isValid() {
		boolean isValid = false;
        if (isPKValid() && descrProcesso != null) {
   			isValid = true;
   		}
   		return isValid;
	}
	public boolean isPKValid() {
		boolean isPkValid = false;
		if (idProcesso != null ) {
   			isPkValid = true;
   		}
   		return isPkValid;
	}
	
	public String toString() {
		
	    String temp = "";
	    StringBuffer sb = new StringBuffer();
	    sb.append("\t\n" + this.getClass().getName() + "\t\n");
	    
	    temp = DataFilter.removeNull( idProcesso);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idProcesso: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( descrProcesso);
	    if (!DataFilter.isEmpty(temp)) sb.append(" descrProcesso: " + temp + "\t\n");
	    
	    return sb.toString();
	}
	
	public List getPK() {
		java.util.List pk=new java.util.ArrayList();
		
		pk.add("idProcesso");
		
		return pk;
	}

}
