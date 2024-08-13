/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/


package it.csi.pbandi.pbweb.pbandisrv.integration.db.vo;

import java.math.*;
import java.sql.Date;
import it.csi.pbandi.pbweb.pbandisrv.integration.util.DataFilter;



public class PbandiRRuoloTipoAnagraficaVO extends GenericVO {

  	
  	private BigDecimal idTipoAnagrafica;
  	
  	private BigDecimal idRuoloTipoAnagrafica;
  	
  	private BigDecimal idRuoloDiProcesso;
  	
  	private BigDecimal idTipoSoggettoCorrelato;
  	
	public PbandiRRuoloTipoAnagraficaVO() {}
  	
	public PbandiRRuoloTipoAnagraficaVO (BigDecimal idRuoloTipoAnagrafica) {
	
		this. idRuoloTipoAnagrafica =  idRuoloTipoAnagrafica;
	}
  	
	public PbandiRRuoloTipoAnagraficaVO (BigDecimal idTipoAnagrafica, BigDecimal idRuoloTipoAnagrafica, BigDecimal idRuoloDiProcesso, BigDecimal idTipoSoggettoCorrelato) {
	
		this. idTipoAnagrafica =  idTipoAnagrafica;
		this. idRuoloTipoAnagrafica =  idRuoloTipoAnagrafica;
		this. idRuoloDiProcesso =  idRuoloDiProcesso;
		this. idTipoSoggettoCorrelato =  idTipoSoggettoCorrelato;
	}
  	
  	
	public BigDecimal getIdTipoAnagrafica() {
		return idTipoAnagrafica;
	}
	
	public void setIdTipoAnagrafica(BigDecimal idTipoAnagrafica) {
		this.idTipoAnagrafica = idTipoAnagrafica;
	}
	
	public BigDecimal getIdRuoloTipoAnagrafica() {
		return idRuoloTipoAnagrafica;
	}
	
	public void setIdRuoloTipoAnagrafica(BigDecimal idRuoloTipoAnagrafica) {
		this.idRuoloTipoAnagrafica = idRuoloTipoAnagrafica;
	}
	
	public BigDecimal getIdRuoloDiProcesso() {
		return idRuoloDiProcesso;
	}
	
	public void setIdRuoloDiProcesso(BigDecimal idRuoloDiProcesso) {
		this.idRuoloDiProcesso = idRuoloDiProcesso;
	}
	
	public BigDecimal getIdTipoSoggettoCorrelato() {
		return idTipoSoggettoCorrelato;
	}
	
	public void setIdTipoSoggettoCorrelato(BigDecimal idTipoSoggettoCorrelato) {
		this.idTipoSoggettoCorrelato = idTipoSoggettoCorrelato;
	}
	
	public boolean isValid() {
		boolean isValid = false;
                if (isPKValid() && idTipoAnagrafica != null && idRuoloDiProcesso != null) {
   			isValid = true;
   		}
   		return isValid;
	}
	public boolean isPKValid() {
		boolean isPkValid = false;
		if (idRuoloTipoAnagrafica != null ) {
   			isPkValid = true;
   		}

   		return isPkValid;
	}
	
	public String toString() {
		
	    String temp = "";
	    StringBuffer sb = new StringBuffer();
	    sb.append("\t\n" + this.getClass().getName() + "\t\n");
	    
	    temp = DataFilter.removeNull( idTipoAnagrafica);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idTipoAnagrafica: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idRuoloTipoAnagrafica);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idRuoloTipoAnagrafica: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idRuoloDiProcesso);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idRuoloDiProcesso: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idTipoSoggettoCorrelato);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idTipoSoggettoCorrelato: " + temp + "\t\n");
	    
	    return sb.toString();
	}
	
	public java.util.List<String> getPK() {
		java.util.List<String> pk=new java.util.ArrayList<String>();
		
			pk.add("idRuoloTipoAnagrafica");
		
	    return pk;
	}
	
	
}
