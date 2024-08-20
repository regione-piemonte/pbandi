/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/


package it.csi.pbandi.pbwebrce.pbandisrv.integration.db.vo;

import java.math.*;
import java.sql.Date;
import it.csi.pbandi.pbwebrce.pbandisrv.integration.util.DataFilter;



public class PbandiTCspProgIndicatoriVO extends GenericVO {

  	
  	private BigDecimal idCspProgetto;
  	
  	private BigDecimal valoreProgIniziale;
  	
  	private BigDecimal idCspProgIndicatori;
  	
  	private BigDecimal idUtenteAgg;
  	
  	private Date dtElaborazione;
  	
  	private Date dtInizioValidita;
  	
  	private BigDecimal idUtenteIns;
  	
  	private BigDecimal idIndicatori;
  	
	public PbandiTCspProgIndicatoriVO() {}
  	
	public PbandiTCspProgIndicatoriVO (BigDecimal idCspProgIndicatori) {
	
		this. idCspProgIndicatori =  idCspProgIndicatori;
	}
  	
	public PbandiTCspProgIndicatoriVO (BigDecimal idCspProgetto, BigDecimal valoreProgIniziale, BigDecimal idCspProgIndicatori, BigDecimal idUtenteAgg, Date dtElaborazione, Date dtInizioValidita, BigDecimal idUtenteIns, BigDecimal idIndicatori) {
	
		this. idCspProgetto =  idCspProgetto;
		this. valoreProgIniziale =  valoreProgIniziale;
		this. idCspProgIndicatori =  idCspProgIndicatori;
		this. idUtenteAgg =  idUtenteAgg;
		this. dtElaborazione =  dtElaborazione;
		this. dtInizioValidita =  dtInizioValidita;
		this. idUtenteIns =  idUtenteIns;
		this. idIndicatori =  idIndicatori;
	}
  	
  	
	public BigDecimal getIdCspProgetto() {
		return idCspProgetto;
	}
	
	public void setIdCspProgetto(BigDecimal idCspProgetto) {
		this.idCspProgetto = idCspProgetto;
	}
	
	public BigDecimal getValoreProgIniziale() {
		return valoreProgIniziale;
	}
	
	public void setValoreProgIniziale(BigDecimal valoreProgIniziale) {
		this.valoreProgIniziale = valoreProgIniziale;
	}
	
	public BigDecimal getIdCspProgIndicatori() {
		return idCspProgIndicatori;
	}
	
	public void setIdCspProgIndicatori(BigDecimal idCspProgIndicatori) {
		this.idCspProgIndicatori = idCspProgIndicatori;
	}
	
	public BigDecimal getIdUtenteAgg() {
		return idUtenteAgg;
	}
	
	public void setIdUtenteAgg(BigDecimal idUtenteAgg) {
		this.idUtenteAgg = idUtenteAgg;
	}
	
	public Date getDtElaborazione() {
		return dtElaborazione;
	}
	
	public void setDtElaborazione(Date dtElaborazione) {
		this.dtElaborazione = dtElaborazione;
	}
	
	public Date getDtInizioValidita() {
		return dtInizioValidita;
	}
	
	public void setDtInizioValidita(Date dtInizioValidita) {
		this.dtInizioValidita = dtInizioValidita;
	}
	
	public BigDecimal getIdUtenteIns() {
		return idUtenteIns;
	}
	
	public void setIdUtenteIns(BigDecimal idUtenteIns) {
		this.idUtenteIns = idUtenteIns;
	}
	
	public BigDecimal getIdIndicatori() {
		return idIndicatori;
	}
	
	public void setIdIndicatori(BigDecimal idIndicatori) {
		this.idIndicatori = idIndicatori;
	}
	
	public boolean isValid() {
		boolean isValid = false;
                if (isPKValid() && idCspProgetto != null && dtInizioValidita != null && idUtenteIns != null && idIndicatori != null) {
   			isValid = true;
   		}
   		return isValid;
	}
	public boolean isPKValid() {
		boolean isPkValid = false;
		if (idCspProgIndicatori != null ) {
   			isPkValid = true;
   		}

   		return isPkValid;
	}
	
	public String toString() {
		
	    String temp = "";
	    StringBuffer sb = new StringBuffer();
	    sb.append("\t\n" + this.getClass().getName() + "\t\n");
	    
	    temp = DataFilter.removeNull( idCspProgetto);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idCspProgetto: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( valoreProgIniziale);
	    if (!DataFilter.isEmpty(temp)) sb.append(" valoreProgIniziale: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idCspProgIndicatori);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idCspProgIndicatori: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idUtenteAgg);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idUtenteAgg: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtElaborazione);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtElaborazione: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtInizioValidita);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtInizioValidita: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idUtenteIns);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idUtenteIns: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idIndicatori);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idIndicatori: " + temp + "\t\n");
	    
	    return sb.toString();
	}
	
	public java.util.List<String> getPK() {
		java.util.List<String> pk=new java.util.ArrayList<String>();
		
			pk.add("idCspProgIndicatori");
		
	    return pk;
	}
	
	
}
