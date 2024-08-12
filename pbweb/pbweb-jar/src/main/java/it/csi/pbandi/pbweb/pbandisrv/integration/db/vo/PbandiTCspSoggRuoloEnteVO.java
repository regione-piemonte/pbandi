
package it.csi.pbandi.pbweb.pbandisrv.integration.db.vo;

import java.math.*;
import java.sql.Date;
import it.csi.pbandi.pbweb.pbandisrv.integration.util.DataFilter;



public class PbandiTCspSoggRuoloEnteVO extends GenericVO {

  	
  	private BigDecimal idCspSoggetto;
  	
  	private BigDecimal idRuoloEnteCompetenza;
  	
  	private Date dtElaborazione;
  	
  	private BigDecimal idUtenteAgg;
  	
  	private BigDecimal idCspSoggRuoloEnte;
  	
  	private Date dtInizioValidita;
  	
  	private BigDecimal idUtenteIns;
  	
	public PbandiTCspSoggRuoloEnteVO() {}
  	
	public PbandiTCspSoggRuoloEnteVO (BigDecimal idCspSoggRuoloEnte) {
	
		this. idCspSoggRuoloEnte =  idCspSoggRuoloEnte;
	}
  	
	public PbandiTCspSoggRuoloEnteVO (BigDecimal idCspSoggetto, BigDecimal idRuoloEnteCompetenza, Date dtElaborazione, BigDecimal idUtenteAgg, BigDecimal idCspSoggRuoloEnte, Date dtInizioValidita, BigDecimal idUtenteIns) {
	
		this. idCspSoggetto =  idCspSoggetto;
		this. idRuoloEnteCompetenza =  idRuoloEnteCompetenza;
		this. dtElaborazione =  dtElaborazione;
		this. idUtenteAgg =  idUtenteAgg;
		this. idCspSoggRuoloEnte =  idCspSoggRuoloEnte;
		this. dtInizioValidita =  dtInizioValidita;
		this. idUtenteIns =  idUtenteIns;
	}
  	
  	
	public BigDecimal getIdCspSoggetto() {
		return idCspSoggetto;
	}
	
	public void setIdCspSoggetto(BigDecimal idCspSoggetto) {
		this.idCspSoggetto = idCspSoggetto;
	}
	
	public BigDecimal getIdRuoloEnteCompetenza() {
		return idRuoloEnteCompetenza;
	}
	
	public void setIdRuoloEnteCompetenza(BigDecimal idRuoloEnteCompetenza) {
		this.idRuoloEnteCompetenza = idRuoloEnteCompetenza;
	}
	
	public Date getDtElaborazione() {
		return dtElaborazione;
	}
	
	public void setDtElaborazione(Date dtElaborazione) {
		this.dtElaborazione = dtElaborazione;
	}
	
	public BigDecimal getIdUtenteAgg() {
		return idUtenteAgg;
	}
	
	public void setIdUtenteAgg(BigDecimal idUtenteAgg) {
		this.idUtenteAgg = idUtenteAgg;
	}
	
	public BigDecimal getIdCspSoggRuoloEnte() {
		return idCspSoggRuoloEnte;
	}
	
	public void setIdCspSoggRuoloEnte(BigDecimal idCspSoggRuoloEnte) {
		this.idCspSoggRuoloEnte = idCspSoggRuoloEnte;
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
                if (isPKValid() && idCspSoggetto != null && idRuoloEnteCompetenza != null && dtInizioValidita != null && idUtenteIns != null) {
   			isValid = true;
   		}
   		return isValid;
	}
	public boolean isPKValid() {
		boolean isPkValid = false;
		if (idCspSoggRuoloEnte != null ) {
   			isPkValid = true;
   		}

   		return isPkValid;
	}
	
	public String toString() {
		
	    String temp = "";
	    StringBuffer sb = new StringBuffer();
	    sb.append("\t\n" + this.getClass().getName() + "\t\n");
	    
	    temp = DataFilter.removeNull( idCspSoggetto);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idCspSoggetto: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idRuoloEnteCompetenza);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idRuoloEnteCompetenza: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtElaborazione);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtElaborazione: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idUtenteAgg);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idUtenteAgg: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idCspSoggRuoloEnte);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idCspSoggRuoloEnte: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtInizioValidita);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtInizioValidita: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idUtenteIns);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idUtenteIns: " + temp + "\t\n");
	    
	    return sb.toString();
	}
	
	public java.util.List<String> getPK() {
		java.util.List<String> pk=new java.util.ArrayList<String>();
		
			pk.add("idCspSoggRuoloEnte");
		
	    return pk;
	}
	
	
}
