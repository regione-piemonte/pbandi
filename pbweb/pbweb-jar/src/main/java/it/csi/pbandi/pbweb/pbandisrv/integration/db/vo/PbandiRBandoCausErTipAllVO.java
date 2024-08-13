/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/


package it.csi.pbandi.pbweb.pbandisrv.integration.db.vo;

import java.math.*;
import java.sql.Date;
import it.csi.pbandi.pbweb.pbandisrv.integration.util.DataFilter;



public class PbandiRBandoCausErTipAllVO extends GenericVO {

  	
  	private BigDecimal idTipoAllegato;
  	
  	private BigDecimal idBando;
  	
  	private BigDecimal idCausaleErogazione;
  	
  	private BigDecimal idUtenteAgg;
  	
  	private BigDecimal idUtenteIns;
  	
	public PbandiRBandoCausErTipAllVO() {}
  	
	public PbandiRBandoCausErTipAllVO (BigDecimal idTipoAllegato, BigDecimal idBando, BigDecimal idCausaleErogazione) {
	
		this. idTipoAllegato =  idTipoAllegato;
		this. idBando =  idBando;
		this. idCausaleErogazione =  idCausaleErogazione;
	}
  	
	public PbandiRBandoCausErTipAllVO (BigDecimal idTipoAllegato, BigDecimal idBando, BigDecimal idCausaleErogazione, BigDecimal idUtenteAgg, BigDecimal idUtenteIns) {
	
		this. idTipoAllegato =  idTipoAllegato;
		this. idBando =  idBando;
		this. idCausaleErogazione =  idCausaleErogazione;
		this. idUtenteAgg =  idUtenteAgg;
		this. idUtenteIns =  idUtenteIns;
	}
  	
  	
	public BigDecimal getIdTipoAllegato() {
		return idTipoAllegato;
	}
	
	public void setIdTipoAllegato(BigDecimal idTipoAllegato) {
		this.idTipoAllegato = idTipoAllegato;
	}
	
	public BigDecimal getIdBando() {
		return idBando;
	}
	
	public void setIdBando(BigDecimal idBando) {
		this.idBando = idBando;
	}
	
	public BigDecimal getIdCausaleErogazione() {
		return idCausaleErogazione;
	}
	
	public void setIdCausaleErogazione(BigDecimal idCausaleErogazione) {
		this.idCausaleErogazione = idCausaleErogazione;
	}
	
	public BigDecimal getIdUtenteAgg() {
		return idUtenteAgg;
	}
	
	public void setIdUtenteAgg(BigDecimal idUtenteAgg) {
		this.idUtenteAgg = idUtenteAgg;
	}
	
	public BigDecimal getIdUtenteIns() {
		return idUtenteIns;
	}
	
	public void setIdUtenteIns(BigDecimal idUtenteIns) {
		this.idUtenteIns = idUtenteIns;
	}
	
	public boolean isValid() {
		boolean isValid = false;
                if (isPKValid() && idUtenteIns != null) {
   			isValid = true;
   		}
   		return isValid;
	}
	public boolean isPKValid() {
		boolean isPkValid = false;
		if (idTipoAllegato != null && idBando != null && idCausaleErogazione != null ) {
   			isPkValid = true;
   		}

   		return isPkValid;
	}
	
	public String toString() {
		
	    String temp = "";
	    StringBuffer sb = new StringBuffer();
	    sb.append("\t\n" + this.getClass().getName() + "\t\n");
	    
	    temp = DataFilter.removeNull( idTipoAllegato);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idTipoAllegato: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idBando);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idBando: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idCausaleErogazione);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idCausaleErogazione: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idUtenteAgg);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idUtenteAgg: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idUtenteIns);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idUtenteIns: " + temp + "\t\n");
	    
	    return sb.toString();
	}
	
	public java.util.List<String> getPK() {
		java.util.List<String> pk=new java.util.ArrayList<String>();
		
			pk.add("idTipoAllegato");
		
			pk.add("idBando");
		
			pk.add("idCausaleErogazione");
		
	    return pk;
	}
	
	
}
