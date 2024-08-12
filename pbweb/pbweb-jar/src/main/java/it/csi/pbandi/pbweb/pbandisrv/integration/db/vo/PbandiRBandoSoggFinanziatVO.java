
package it.csi.pbandi.pbweb.pbandisrv.integration.db.vo;

import java.math.*;
import java.sql.Date;
import it.csi.pbandi.pbweb.pbandisrv.integration.util.DataFilter;



public class PbandiRBandoSoggFinanziatVO extends GenericVO {

  	
  	private BigDecimal percentualeQuotaSoggFinanz;
  	
  	private BigDecimal idSoggettoFinanziatore;
  	
  	private BigDecimal idBando;
  	
  	private BigDecimal idUtenteAgg;
  	
  	private BigDecimal percQuotaContributoPub;
  	
  	private BigDecimal idUtenteIns;
  	
	public PbandiRBandoSoggFinanziatVO() {}
  	
	public PbandiRBandoSoggFinanziatVO (BigDecimal idSoggettoFinanziatore, BigDecimal idBando) {
	
		this. idSoggettoFinanziatore =  idSoggettoFinanziatore;
		this. idBando =  idBando;
	}
  	
	public PbandiRBandoSoggFinanziatVO (BigDecimal percentualeQuotaSoggFinanz, BigDecimal idSoggettoFinanziatore, BigDecimal idBando, BigDecimal idUtenteAgg, BigDecimal percQuotaContributoPub, BigDecimal idUtenteIns) {
	
		this. percentualeQuotaSoggFinanz =  percentualeQuotaSoggFinanz;
		this. idSoggettoFinanziatore =  idSoggettoFinanziatore;
		this. idBando =  idBando;
		this. idUtenteAgg =  idUtenteAgg;
		this. percQuotaContributoPub =  percQuotaContributoPub;
		this. idUtenteIns =  idUtenteIns;
	}
  	
  	
	public BigDecimal getPercentualeQuotaSoggFinanz() {
		return percentualeQuotaSoggFinanz;
	}
	
	public void setPercentualeQuotaSoggFinanz(BigDecimal percentualeQuotaSoggFinanz) {
		this.percentualeQuotaSoggFinanz = percentualeQuotaSoggFinanz;
	}
	
	public BigDecimal getIdSoggettoFinanziatore() {
		return idSoggettoFinanziatore;
	}
	
	public void setIdSoggettoFinanziatore(BigDecimal idSoggettoFinanziatore) {
		this.idSoggettoFinanziatore = idSoggettoFinanziatore;
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
	
	public BigDecimal getPercQuotaContributoPub() {
		return percQuotaContributoPub;
	}
	
	public void setPercQuotaContributoPub(BigDecimal percQuotaContributoPub) {
		this.percQuotaContributoPub = percQuotaContributoPub;
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
		if (idSoggettoFinanziatore != null && idBando != null ) {
   			isPkValid = true;
   		}

   		return isPkValid;
	}
	
	public String toString() {
		
	    String temp = "";
	    StringBuffer sb = new StringBuffer();
	    sb.append("\t\n" + this.getClass().getName() + "\t\n");
	    
	    temp = DataFilter.removeNull( percentualeQuotaSoggFinanz);
	    if (!DataFilter.isEmpty(temp)) sb.append(" percentualeQuotaSoggFinanz: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idSoggettoFinanziatore);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idSoggettoFinanziatore: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idBando);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idBando: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idUtenteAgg);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idUtenteAgg: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( percQuotaContributoPub);
	    if (!DataFilter.isEmpty(temp)) sb.append(" percQuotaContributoPub: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idUtenteIns);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idUtenteIns: " + temp + "\t\n");
	    
	    return sb.toString();
	}
	
	public java.util.List getPK() {
		java.util.List pk=new java.util.ArrayList();
		
			pk.add("idSoggettoFinanziatore");
		
			pk.add("idBando");
		
	    return pk;
	}
	
	
}
