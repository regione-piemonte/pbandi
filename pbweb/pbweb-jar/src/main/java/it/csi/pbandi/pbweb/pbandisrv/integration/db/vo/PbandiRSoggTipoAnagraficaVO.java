
package it.csi.pbandi.pbweb.pbandisrv.integration.db.vo;

import it.csi.pbandi.pbweb.pbandisrv.integration.util.DataFilter;

import java.math.BigDecimal;
import java.sql.Date;



public class PbandiRSoggTipoAnagraficaVO extends GenericVO {
	private Date dtAggiornamentoFlux;
	private Date dtInizioValidita;
  	private Date dtFineValidita;
  	private String flagAggiornatoFlux;
  	private BigDecimal idSoggetto;
  	private BigDecimal idTipoAnagrafica;
  	private BigDecimal idUtenteAgg;
  	private BigDecimal idUtenteIns;
  	
  	
  	
  	
	public PbandiRSoggTipoAnagraficaVO() {}
  	
	public PbandiRSoggTipoAnagraficaVO (BigDecimal idTipoAnagrafica, BigDecimal idSoggetto) {
	
		this. idTipoAnagrafica =  idTipoAnagrafica;
		this. idSoggetto =  idSoggetto;
	}
  	
	public PbandiRSoggTipoAnagraficaVO (BigDecimal idTipoAnagrafica, Date dtFineValidita, String flagAggiornatoFlux, BigDecimal idUtenteAgg, BigDecimal idSoggetto, Date dtInizioValidita, BigDecimal idUtenteIns,Date dtAggiornamentoFlux) {
	
		this. idTipoAnagrafica =  idTipoAnagrafica;
		this. dtFineValidita =  dtFineValidita;
		this. flagAggiornatoFlux =  flagAggiornatoFlux;
		this. idUtenteAgg =  idUtenteAgg;
		this. idSoggetto =  idSoggetto;
		this. dtInizioValidita =  dtInizioValidita;
		this. idUtenteIns =  idUtenteIns;
		this. dtAggiornamentoFlux=dtAggiornamentoFlux;
	}
  	
  	
	public BigDecimal getIdTipoAnagrafica() {
		return idTipoAnagrafica;
	}
	
	public void setIdTipoAnagrafica(BigDecimal idTipoAnagrafica) {
		this.idTipoAnagrafica = idTipoAnagrafica;
	}
	
	public Date getDtFineValidita() {
		return dtFineValidita;
	}
	
	public void setDtFineValidita(Date dtFineValidita) {
		this.dtFineValidita = dtFineValidita;
	}
	
	public String getFlagAggiornatoFlux() {
		return flagAggiornatoFlux;
	}
	
	public void setFlagAggiornatoFlux(String flagAggiornatoFlux) {
		this.flagAggiornatoFlux = flagAggiornatoFlux;
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
	
	public boolean isValid() {
		boolean isValid = false;
                if (isPKValid() && flagAggiornatoFlux != null && dtInizioValidita != null && idUtenteIns != null) {
   			isValid = true;
   		}
   		return isValid;
	}
	public boolean isPKValid() {
		boolean isPkValid = false;
		if (idTipoAnagrafica != null && idSoggetto != null ) {
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
	    
	    temp = DataFilter.removeNull( dtFineValidita);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtFineValidita: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( flagAggiornatoFlux);
	    if (!DataFilter.isEmpty(temp)) sb.append(" flagAggiornatoFlux: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idUtenteAgg);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idUtenteAgg: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idSoggetto);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idSoggetto: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtInizioValidita);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtInizioValidita: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idUtenteIns);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idUtenteIns: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtAggiornamentoFlux);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtAggiornamentoFlux: " + temp + "\t\n");
	    return sb.toString();
	}
	
	public java.util.List<String> getPK() {
		java.util.List<String> pk=new java.util.ArrayList<String>();
		
			pk.add("idTipoAnagrafica");
		
			pk.add("idSoggetto");
		
	    return pk;
	}

	public Date getDtAggiornamentoFlux() {
		return dtAggiornamentoFlux;
	}

	public void setDtAggiornamentoFlux(Date dtAggiornamentoFlux) {
		this.dtAggiornamentoFlux = dtAggiornamentoFlux;
	}
	
 
}
