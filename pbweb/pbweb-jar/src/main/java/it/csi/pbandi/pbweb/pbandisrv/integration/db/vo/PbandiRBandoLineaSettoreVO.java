
package it.csi.pbandi.pbweb.pbandisrv.integration.db.vo;

import java.math.*;
import java.sql.Date;
import it.csi.pbandi.pbweb.pbandisrv.integration.util.DataFilter;



public class PbandiRBandoLineaSettoreVO extends GenericVO {

  	
	private Date dtFineValidita;
  	private BigDecimal idRuoloEnteCompetenza;
  	private BigDecimal idSettoreEnte;
  	private BigDecimal idUtenteAgg;
  	private BigDecimal idUtenteIns;
  	private BigDecimal progrBandoLineaIntervento;
  	private BigDecimal progrBandoLineaSettore;
  	
  	
	public PbandiRBandoLineaSettoreVO() {}
  	
	public PbandiRBandoLineaSettoreVO (BigDecimal progrBandoLineaSettore) {
	
		this. progrBandoLineaSettore =  progrBandoLineaSettore;
	}
  	
	public PbandiRBandoLineaSettoreVO (BigDecimal idRuoloEnteCompetenza, BigDecimal progrBandoLineaIntervento, Date dtFineValidita, BigDecimal idSettoreEnte, BigDecimal idUtenteAgg, BigDecimal idUtenteIns, BigDecimal progrBandoLineaSettore) {
	
		this. dtFineValidita =  dtFineValidita;
		this. idRuoloEnteCompetenza =  idRuoloEnteCompetenza;
		this. idSettoreEnte =  idSettoreEnte;
		this. idUtenteAgg =  idUtenteAgg;
		this. idUtenteIns =  idUtenteIns;
		this. progrBandoLineaIntervento =  progrBandoLineaIntervento;
		this. progrBandoLineaSettore =  progrBandoLineaSettore;
	}
  	
	public BigDecimal getProgrBandoLineaSettore() {
		return progrBandoLineaSettore;
	}

	public void setProgrBandoLineaSettore(BigDecimal progrBandoLineaSettore) {
		this.progrBandoLineaSettore = progrBandoLineaSettore;
	}

	public BigDecimal getIdSettoreEnte() {
		return idSettoreEnte;
	}

	public void setIdSettoreEnte(BigDecimal idSettoreEnte) {
		this.idSettoreEnte = idSettoreEnte;
	}
  	
	public BigDecimal getIdRuoloEnteCompetenza() {
		return idRuoloEnteCompetenza;
	}
	
	public void setIdRuoloEnteCompetenza(BigDecimal idRuoloEnteCompetenza) {
		this.idRuoloEnteCompetenza = idRuoloEnteCompetenza;
	}
	
	public BigDecimal getProgrBandoLineaIntervento() {
		return progrBandoLineaIntervento;
	}
	
	public void setProgrBandoLineaIntervento(BigDecimal progrBandoLineaIntervento) {
		this.progrBandoLineaIntervento = progrBandoLineaIntervento;
	}
	
	public Date getDtFineValidita() {
		return dtFineValidita;
	}
	
	public void setDtFineValidita(Date dtFineValidita) {
		this.dtFineValidita = dtFineValidita;
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
                if (isPKValid() && idRuoloEnteCompetenza != null && progrBandoLineaIntervento != null && idSettoreEnte != null && idUtenteIns != null) {
   			isValid = true;
   		}
   		return isValid;
	}
	public boolean isPKValid() {
		boolean isPkValid = false;
		if (progrBandoLineaSettore != null ) {
   			isPkValid = true;
   		}

   		return isPkValid;
	}
	
	public String toString() {
		
	    String temp = "";
	    StringBuffer sb = new StringBuffer();
	    sb.append("\t\n" + this.getClass().getName() + "\t\n");
	    
	    temp = DataFilter.removeNull( idRuoloEnteCompetenza);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idRuoloEnteCompetenza: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( progrBandoLineaIntervento);
	    if (!DataFilter.isEmpty(temp)) sb.append(" progrBandoLineaIntervento: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtFineValidita);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtFineValidita: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idSettoreEnte);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idSettoreEnte: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idUtenteAgg);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idUtenteAgg: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idUtenteIns);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idUtenteIns: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( progrBandoLineaSettore);
	    if (!DataFilter.isEmpty(temp)) sb.append(" progrBandoLineaSettore: " + temp + "\t\n");
	    
	    return sb.toString();
	}
	
	public java.util.List<String> getPK() {
		java.util.List<String> pk=new java.util.ArrayList<String>();
		
			pk.add("progrBandoLineaSettore");
		
	    return pk;
	}
	
	
}
