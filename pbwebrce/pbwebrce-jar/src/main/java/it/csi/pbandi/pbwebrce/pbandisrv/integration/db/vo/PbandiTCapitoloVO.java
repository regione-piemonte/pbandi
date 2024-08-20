/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/


package it.csi.pbandi.pbwebrce.pbandisrv.integration.db.vo;

import java.math.*;
import java.sql.Date;
import it.csi.pbandi.pbwebrce.pbandisrv.integration.util.DataFilter;



public class PbandiTCapitoloVO extends GenericVO {

  	
  	private BigDecimal numeroCapitolo;
  	
  	private BigDecimal idUtenteAgg;
  	
  	private BigDecimal idProvenienzaCapitolo;
  	
  	private BigDecimal numeroArticolo;
  	
  	private BigDecimal idEnteCompetenza;
  	
  	private BigDecimal idTipoFondo;
  	
  	private String descCapitolo;
  	
  	private BigDecimal idCapitolo;
  	
  	private BigDecimal idUtenteIns;
  	
	public PbandiTCapitoloVO() {}
  	
	public PbandiTCapitoloVO (BigDecimal idCapitolo) {
	
		this. idCapitolo =  idCapitolo;
	}
  	
	public PbandiTCapitoloVO (BigDecimal numeroCapitolo, BigDecimal idUtenteAgg, BigDecimal idProvenienzaCapitolo, BigDecimal numeroArticolo, BigDecimal idEnteCompetenza, BigDecimal idTipoFondo, String descCapitolo, BigDecimal idCapitolo, BigDecimal idUtenteIns) {
	
		this. numeroCapitolo =  numeroCapitolo;
		this. idUtenteAgg =  idUtenteAgg;
		this. idProvenienzaCapitolo =  idProvenienzaCapitolo;
		this. numeroArticolo =  numeroArticolo;
		this. idEnteCompetenza =  idEnteCompetenza;
		this. idTipoFondo =  idTipoFondo;
		this. descCapitolo =  descCapitolo;
		this. idCapitolo =  idCapitolo;
		this. idUtenteIns =  idUtenteIns;
	}
  	
  	
	public BigDecimal getNumeroCapitolo() {
		return numeroCapitolo;
	}
	
	public void setNumeroCapitolo(BigDecimal numeroCapitolo) {
		this.numeroCapitolo = numeroCapitolo;
	}
	
	public BigDecimal getIdUtenteAgg() {
		return idUtenteAgg;
	}
	
	public void setIdUtenteAgg(BigDecimal idUtenteAgg) {
		this.idUtenteAgg = idUtenteAgg;
	}
	
	public BigDecimal getIdProvenienzaCapitolo() {
		return idProvenienzaCapitolo;
	}
	
	public void setIdProvenienzaCapitolo(BigDecimal idProvenienzaCapitolo) {
		this.idProvenienzaCapitolo = idProvenienzaCapitolo;
	}
	
	public BigDecimal getNumeroArticolo() {
		return numeroArticolo;
	}
	
	public void setNumeroArticolo(BigDecimal numeroArticolo) {
		this.numeroArticolo = numeroArticolo;
	}
	
	public BigDecimal getIdEnteCompetenza() {
		return idEnteCompetenza;
	}
	
	public void setIdEnteCompetenza(BigDecimal idEnteCompetenza) {
		this.idEnteCompetenza = idEnteCompetenza;
	}
	
	public BigDecimal getIdTipoFondo() {
		return idTipoFondo;
	}
	
	public void setIdTipoFondo(BigDecimal idTipoFondo) {
		this.idTipoFondo = idTipoFondo;
	}
	
	public String getDescCapitolo() {
		return descCapitolo;
	}
	
	public void setDescCapitolo(String descCapitolo) {
		this.descCapitolo = descCapitolo;
	}
	
	public BigDecimal getIdCapitolo() {
		return idCapitolo;
	}
	
	public void setIdCapitolo(BigDecimal idCapitolo) {
		this.idCapitolo = idCapitolo;
	}
	
	public BigDecimal getIdUtenteIns() {
		return idUtenteIns;
	}
	
	public void setIdUtenteIns(BigDecimal idUtenteIns) {
		this.idUtenteIns = idUtenteIns;
	}
	
	public boolean isValid() {
		boolean isValid = false;
                if (isPKValid() && numeroCapitolo != null && numeroArticolo != null && idTipoFondo != null && idUtenteIns != null) {
   			isValid = true;
   		}
   		return isValid;
	}
	public boolean isPKValid() {
		boolean isPkValid = false;
		if (idCapitolo != null ) {
   			isPkValid = true;
   		}

   		return isPkValid;
	}
	
	public String toString() {
		
	    String temp = "";
	    StringBuffer sb = new StringBuffer();
	    sb.append("\t\n" + this.getClass().getName() + "\t\n");
	    
	    temp = DataFilter.removeNull( numeroCapitolo);
	    if (!DataFilter.isEmpty(temp)) sb.append(" numeroCapitolo: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idUtenteAgg);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idUtenteAgg: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idProvenienzaCapitolo);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idProvenienzaCapitolo: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( numeroArticolo);
	    if (!DataFilter.isEmpty(temp)) sb.append(" numeroArticolo: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idEnteCompetenza);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idEnteCompetenza: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idTipoFondo);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idTipoFondo: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( descCapitolo);
	    if (!DataFilter.isEmpty(temp)) sb.append(" descCapitolo: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idCapitolo);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idCapitolo: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idUtenteIns);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idUtenteIns: " + temp + "\t\n");
	    
	    return sb.toString();
	}
	
	public java.util.List getPK() {
		java.util.List pk=new java.util.ArrayList();
		
			pk.add("idCapitolo");
		
	    return pk;
	}
	
	
}
