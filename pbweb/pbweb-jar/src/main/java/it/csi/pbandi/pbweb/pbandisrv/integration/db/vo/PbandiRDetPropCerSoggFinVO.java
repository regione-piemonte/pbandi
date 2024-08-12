
package it.csi.pbandi.pbweb.pbandisrv.integration.db.vo;

import java.math.*;
import java.sql.Date;
import java.util.List;

import it.csi.pbandi.pbweb.pbandisrv.integration.util.DataFilter;



public class PbandiRDetPropCerSoggFinVO extends GenericVO {

  	
  	private BigDecimal idDettPropostaCertif;
  	
  	private BigDecimal impQuotaSoggFinanziatore;
  	
  	private BigDecimal percTipoSoggFinanzFesr;
  	
  	private BigDecimal percTipoSoggFinanziatore;
  	
  	private BigDecimal idUtenteAgg;
  	
  	private BigDecimal idTipoSoggFinanziat;
  	
  	private BigDecimal idUtenteIns;
  	
	public PbandiRDetPropCerSoggFinVO() {}
  	
	public PbandiRDetPropCerSoggFinVO (BigDecimal idDettPropostaCertif, BigDecimal idTipoSoggFinanziat) {
	
		this. idDettPropostaCertif =  idDettPropostaCertif;
		this. idTipoSoggFinanziat =  idTipoSoggFinanziat;
	}
  	
	public PbandiRDetPropCerSoggFinVO (BigDecimal idDettPropostaCertif, BigDecimal impQuotaSoggFinanziatore, BigDecimal percTipoSoggFinanzFesr, BigDecimal percTipoSoggFinanziatore, BigDecimal idUtenteAgg, BigDecimal idTipoSoggFinanziat, BigDecimal idUtenteIns) {
	
		this. idDettPropostaCertif =  idDettPropostaCertif;
		this. impQuotaSoggFinanziatore =  impQuotaSoggFinanziatore;
		this. percTipoSoggFinanzFesr =  percTipoSoggFinanzFesr;
		this. percTipoSoggFinanziatore =  percTipoSoggFinanziatore;
		this. idUtenteAgg =  idUtenteAgg;
		this. idTipoSoggFinanziat =  idTipoSoggFinanziat;
		this. idUtenteIns =  idUtenteIns;
	}
  	
  	
	public BigDecimal getIdDettPropostaCertif() {
		return idDettPropostaCertif;
	}
	
	public void setIdDettPropostaCertif(BigDecimal idDettPropostaCertif) {
		this.idDettPropostaCertif = idDettPropostaCertif;
	}
	
	public BigDecimal getImpQuotaSoggFinanziatore() {
		return impQuotaSoggFinanziatore;
	}
	
	public void setImpQuotaSoggFinanziatore(BigDecimal impQuotaSoggFinanziatore) {
		this.impQuotaSoggFinanziatore = impQuotaSoggFinanziatore;
	}
	
	public BigDecimal getPercTipoSoggFinanzFesr() {
		return percTipoSoggFinanzFesr;
	}
	
	public void setPercTipoSoggFinanzFesr(BigDecimal percTipoSoggFinanzFesr) {
		this.percTipoSoggFinanzFesr = percTipoSoggFinanzFesr;
	}
	
	public BigDecimal getPercTipoSoggFinanziatore() {
		return percTipoSoggFinanziatore;
	}
	
	public void setPercTipoSoggFinanziatore(BigDecimal percTipoSoggFinanziatore) {
		this.percTipoSoggFinanziatore = percTipoSoggFinanziatore;
	}
	
	public BigDecimal getIdUtenteAgg() {
		return idUtenteAgg;
	}
	
	public void setIdUtenteAgg(BigDecimal idUtenteAgg) {
		this.idUtenteAgg = idUtenteAgg;
	}
	
	public BigDecimal getIdTipoSoggFinanziat() {
		return idTipoSoggFinanziat;
	}
	
	public void setIdTipoSoggFinanziat(BigDecimal idTipoSoggFinanziat) {
		this.idTipoSoggFinanziat = idTipoSoggFinanziat;
	}
	
	public BigDecimal getIdUtenteIns() {
		return idUtenteIns;
	}
	
	public void setIdUtenteIns(BigDecimal idUtenteIns) {
		this.idUtenteIns = idUtenteIns;
	}
	
	public boolean isValid() {
		boolean isValid = false;
                if (isPKValid() && percTipoSoggFinanziatore != null && idUtenteIns != null) {
   			isValid = true;
   		}
   		return isValid;
	}
	public boolean isPKValid() {
		boolean isPkValid = false;
		if (idDettPropostaCertif != null && idTipoSoggFinanziat != null ) {
   			isPkValid = true;
   		}

   		return isPkValid;
	}
	
	public String toString() {
		
	    String temp = "";
	    StringBuffer sb = new StringBuffer();
	    sb.append("\t\n" + this.getClass().getName() + "\t\n");
	    
	    temp = DataFilter.removeNull( idDettPropostaCertif);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idDettPropostaCertif: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( impQuotaSoggFinanziatore);
	    if (!DataFilter.isEmpty(temp)) sb.append(" impQuotaSoggFinanziatore: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( percTipoSoggFinanzFesr);
	    if (!DataFilter.isEmpty(temp)) sb.append(" percTipoSoggFinanzFesr: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( percTipoSoggFinanziatore);
	    if (!DataFilter.isEmpty(temp)) sb.append(" percTipoSoggFinanziatore: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idUtenteAgg);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idUtenteAgg: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idTipoSoggFinanziat);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idTipoSoggFinanziat: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idUtenteIns);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idUtenteIns: " + temp + "\t\n");
	    
	    return sb.toString();
	}

	@Override
	public List getPK() {
		java.util.List pk=new java.util.ArrayList();
		
		pk.add("idDettPropostaCertif");
		pk.add("idTipoSoggFinanziat");
	
		return pk;
	}
	
	
	
}
