/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/


package it.csi.pbandi.pbweb.pbandisrv.integration.db.vo;

import java.math.*;
import java.sql.Date;
import it.csi.pbandi.pbweb.pbandisrv.integration.util.DataFilter;



public class PbandiTRigoQuadroPrevisioVO extends GenericVO {

  	
  	private BigDecimal idRigoQuadroPrevisio;
  	
  	private BigDecimal idUtenteAgg;
  	
  	private Date dtInizioValidita;
  	
  	private BigDecimal idVoceDiSpesa;
  	
  	private BigDecimal importoPreventivo;
  	
  	private String periodo;
  	
  	private BigDecimal idProgetto;
  	
  	private Date dtFineValidita;
  	
  	private BigDecimal idUtenteIns;
  	
	public PbandiTRigoQuadroPrevisioVO() {}
  	
	public PbandiTRigoQuadroPrevisioVO (BigDecimal idRigoQuadroPrevisio) {
	
		this. idRigoQuadroPrevisio =  idRigoQuadroPrevisio;
	}
  	
	public PbandiTRigoQuadroPrevisioVO (BigDecimal idRigoQuadroPrevisio, BigDecimal idUtenteAgg, Date dtInizioValidita, BigDecimal idVoceDiSpesa, BigDecimal importoPreventivo, String periodo, BigDecimal idProgetto, Date dtFineValidita, BigDecimal idUtenteIns) {
	
		this. idRigoQuadroPrevisio =  idRigoQuadroPrevisio;
		this. idUtenteAgg =  idUtenteAgg;
		this. dtInizioValidita =  dtInizioValidita;
		this. idVoceDiSpesa =  idVoceDiSpesa;
		this. importoPreventivo =  importoPreventivo;
		this. periodo =  periodo;
		this. idProgetto =  idProgetto;
		this. dtFineValidita =  dtFineValidita;
		this. idUtenteIns =  idUtenteIns;
	}
  	
  	
	public BigDecimal getIdRigoQuadroPrevisio() {
		return idRigoQuadroPrevisio;
	}
	
	public void setIdRigoQuadroPrevisio(BigDecimal idRigoQuadroPrevisio) {
		this.idRigoQuadroPrevisio = idRigoQuadroPrevisio;
	}
	
	public BigDecimal getIdUtenteAgg() {
		return idUtenteAgg;
	}
	
	public void setIdUtenteAgg(BigDecimal idUtenteAgg) {
		this.idUtenteAgg = idUtenteAgg;
	}
	
	public Date getDtInizioValidita() {
		return dtInizioValidita;
	}
	
	public void setDtInizioValidita(Date dtInizioValidita) {
		this.dtInizioValidita = dtInizioValidita;
	}
	
	public BigDecimal getIdVoceDiSpesa() {
		return idVoceDiSpesa;
	}
	
	public void setIdVoceDiSpesa(BigDecimal idVoceDiSpesa) {
		this.idVoceDiSpesa = idVoceDiSpesa;
	}
	
	public BigDecimal getImportoPreventivo() {
		return importoPreventivo;
	}
	
	public void setImportoPreventivo(BigDecimal importoPreventivo) {
		this.importoPreventivo = importoPreventivo;
	}
	
	public String getPeriodo() {
		return periodo;
	}
	
	public void setPeriodo(String periodo) {
		this.periodo = periodo;
	}
	
	public BigDecimal getIdProgetto() {
		return idProgetto;
	}
	
	public void setIdProgetto(BigDecimal idProgetto) {
		this.idProgetto = idProgetto;
	}
	
	public Date getDtFineValidita() {
		return dtFineValidita;
	}
	
	public void setDtFineValidita(Date dtFineValidita) {
		this.dtFineValidita = dtFineValidita;
	}
	
	public BigDecimal getIdUtenteIns() {
		return idUtenteIns;
	}
	
	public void setIdUtenteIns(BigDecimal idUtenteIns) {
		this.idUtenteIns = idUtenteIns;
	}
	
	public boolean isValid() {
		boolean isValid = false;
                if (isPKValid() && dtInizioValidita != null && idVoceDiSpesa != null && periodo != null && idProgetto != null && idUtenteIns != null) {
   			isValid = true;
   		}
   		return isValid;
	}
	public boolean isPKValid() {
		boolean isPkValid = false;
		if (idRigoQuadroPrevisio != null ) {
   			isPkValid = true;
   		}

   		return isPkValid;
	}
	
	public String toString() {
		
	    String temp = "";
	    StringBuffer sb = new StringBuffer();
	    sb.append("\t\n" + this.getClass().getName() + "\t\n");
	    
	    temp = DataFilter.removeNull( idRigoQuadroPrevisio);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idRigoQuadroPrevisio: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idUtenteAgg);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idUtenteAgg: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtInizioValidita);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtInizioValidita: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idVoceDiSpesa);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idVoceDiSpesa: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( importoPreventivo);
	    if (!DataFilter.isEmpty(temp)) sb.append(" importoPreventivo: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( periodo);
	    if (!DataFilter.isEmpty(temp)) sb.append(" periodo: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idProgetto);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idProgetto: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtFineValidita);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtFineValidita: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idUtenteIns);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idUtenteIns: " + temp + "\t\n");
	    
	    return sb.toString();
	}
	
	public java.util.List<String> getPK() {
		java.util.List<String> pk=new java.util.ArrayList<String>();
		
			pk.add("idRigoQuadroPrevisio");
		
	    return pk;
	}
	
	
}
