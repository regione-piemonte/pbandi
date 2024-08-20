/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/


package it.csi.pbandi.pbwebrce.pbandisrv.integration.db.vo;

import java.math.*;
import java.sql.Date;
import it.csi.pbandi.pbwebrce.pbandisrv.integration.util.DataFilter;



public class PbandiRBandoTipoTrattamentVO extends GenericVO {

  	
  	private BigDecimal idBando;
  	
  	private BigDecimal idUtenteAgg;
  	
  	private BigDecimal idTipoTrattamento;
  	
  	private String flagAssociazioneDefault;
  	
  	private BigDecimal idUtenteIns;
  	
	public PbandiRBandoTipoTrattamentVO() {}
  	
	public PbandiRBandoTipoTrattamentVO (BigDecimal idBando, BigDecimal idTipoTrattamento) {
	
		this. idBando =  idBando;
		this. idTipoTrattamento =  idTipoTrattamento;
	}
  	
	public PbandiRBandoTipoTrattamentVO (BigDecimal idBando, BigDecimal idUtenteAgg, BigDecimal idTipoTrattamento, String flagAssociazioneDefault, BigDecimal idUtenteIns) {
	
		this. idBando =  idBando;
		this. idUtenteAgg =  idUtenteAgg;
		this. idTipoTrattamento =  idTipoTrattamento;
		this. flagAssociazioneDefault =  flagAssociazioneDefault;
		this. idUtenteIns =  idUtenteIns;
	}
  	
  	
	public BigDecimal getIdBando() {
		return idBando;
	}
	
	public void setIdBando(BigDecimal idBando) {
		this.idBando = idBando;
	}
	
	public BigDecimal getIdUtenteAgg() {
		return idUtenteAgg;
	}
	
	public void setIdUtenteAgg(BigDecimal idUtenteAgg) {
		this.idUtenteAgg = idUtenteAgg;
	}
	
	public BigDecimal getIdTipoTrattamento() {
		return idTipoTrattamento;
	}
	
	public void setIdTipoTrattamento(BigDecimal idTipoTrattamento) {
		this.idTipoTrattamento = idTipoTrattamento;
	}
	
	public String getFlagAssociazioneDefault() {
		return flagAssociazioneDefault;
	}
	
	public void setFlagAssociazioneDefault(String flagAssociazioneDefault) {
		this.flagAssociazioneDefault = flagAssociazioneDefault;
	}
	
	public BigDecimal getIdUtenteIns() {
		return idUtenteIns;
	}
	
	public void setIdUtenteIns(BigDecimal idUtenteIns) {
		this.idUtenteIns = idUtenteIns;
	}
	
	public boolean isValid() {
		boolean isValid = false;
                if (isPKValid() && flagAssociazioneDefault != null && idUtenteIns != null) {
   			isValid = true;
   		}
   		return isValid;
	}
	public boolean isPKValid() {
		boolean isPkValid = false;
		if (idBando != null && idTipoTrattamento != null ) {
   			isPkValid = true;
   		}

   		return isPkValid;
	}
	
	public String toString() {
		
	    String temp = "";
	    StringBuffer sb = new StringBuffer();
	    sb.append("\t\n" + this.getClass().getName() + "\t\n");
	    
	    temp = DataFilter.removeNull( idBando);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idBando: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idUtenteAgg);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idUtenteAgg: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idTipoTrattamento);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idTipoTrattamento: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( flagAssociazioneDefault);
	    if (!DataFilter.isEmpty(temp)) sb.append(" flagAssociazioneDefault: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idUtenteIns);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idUtenteIns: " + temp + "\t\n");
	    
	    return sb.toString();
	}
	
	public java.util.List<String> getPK() {
		java.util.List<String> pk=new java.util.ArrayList<String>();
		
			pk.add("idBando");
		
			pk.add("idTipoTrattamento");
		
	    return pk;
	}
	
	
}
