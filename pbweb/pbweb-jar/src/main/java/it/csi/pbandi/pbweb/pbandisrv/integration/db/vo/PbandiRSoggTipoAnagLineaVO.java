/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/


package it.csi.pbandi.pbweb.pbandisrv.integration.db.vo;

import java.math.*;
import java.sql.Date;
import it.csi.pbandi.pbweb.pbandisrv.integration.util.DataFilter;



public class PbandiRSoggTipoAnagLineaVO extends GenericVO {

  	
  	private BigDecimal idTipoAnagrafica;
  	
  	private BigDecimal idLineaDiIntervento;
  	
  	private BigDecimal idUtenteAgg;
  	
  	private BigDecimal idSoggetto;
  	
  	private BigDecimal idUtenteIns;
  	
	public PbandiRSoggTipoAnagLineaVO() {}
  	
	public PbandiRSoggTipoAnagLineaVO (BigDecimal idTipoAnagrafica, BigDecimal idLineaDiIntervento, BigDecimal idSoggetto) {
	
		this. idTipoAnagrafica =  idTipoAnagrafica;
		this. idLineaDiIntervento =  idLineaDiIntervento;
		this. idSoggetto =  idSoggetto;
	}
  	
	public PbandiRSoggTipoAnagLineaVO (BigDecimal idTipoAnagrafica, BigDecimal idLineaDiIntervento, BigDecimal idUtenteAgg, BigDecimal idSoggetto, BigDecimal idUtenteIns) {
	
		this. idTipoAnagrafica =  idTipoAnagrafica;
		this. idLineaDiIntervento =  idLineaDiIntervento;
		this. idUtenteAgg =  idUtenteAgg;
		this. idSoggetto =  idSoggetto;
		this. idUtenteIns =  idUtenteIns;
	}
  	
  	
	public BigDecimal getIdTipoAnagrafica() {
		return idTipoAnagrafica;
	}
	
	public void setIdTipoAnagrafica(BigDecimal idTipoAnagrafica) {
		this.idTipoAnagrafica = idTipoAnagrafica;
	}
	
	public BigDecimal getIdLineaDiIntervento() {
		return idLineaDiIntervento;
	}
	
	public void setIdLineaDiIntervento(BigDecimal idLineaDiIntervento) {
		this.idLineaDiIntervento = idLineaDiIntervento;
	}
	
	public BigDecimal getIdUtenteAgg() {
		return idUtenteAgg;
	}
	
	public void setIdUtenteAgg(BigDecimal idUtenteAgg) {
		this.idUtenteAgg = idUtenteAgg;
	}
	
	public BigDecimal getIdSoggetto() {
		return idSoggetto;
	}
	
	public void setIdSoggetto(BigDecimal idSoggetto) {
		this.idSoggetto = idSoggetto;
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
		if (idTipoAnagrafica != null && idLineaDiIntervento != null && idSoggetto != null ) {
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
	    
	    temp = DataFilter.removeNull( idLineaDiIntervento);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idLineaDiIntervento: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idUtenteAgg);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idUtenteAgg: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idSoggetto);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idSoggetto: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idUtenteIns);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idUtenteIns: " + temp + "\t\n");
	    
	    return sb.toString();
	}
	
	public java.util.List getPK() {
		java.util.List pk=new java.util.ArrayList();
		
			pk.add("idTipoAnagrafica");
		
			pk.add("idLineaDiIntervento");
		
			pk.add("idSoggetto");
		
	    return pk;
	}
	
	
}
