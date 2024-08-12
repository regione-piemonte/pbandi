
package it.csi.pbandi.pbweb.pbandisrv.integration.db.vo;

import java.math.*;
import java.sql.Date;
import it.csi.pbandi.pbweb.pbandisrv.integration.util.DataFilter;



public class PbandiRDettPropCerFideiusVO extends GenericVO {

  	
  	private BigDecimal importoUtilizzato;
  	
  	private BigDecimal idDettPropostaCertif;
  	
  	private BigDecimal idUtenteAgg;
  	
  	private BigDecimal importoDocumento;
  	
  	private BigDecimal idFideiussione;
  	
  	private BigDecimal idUtenteIns;
  	
	public PbandiRDettPropCerFideiusVO() {}
  	
	public PbandiRDettPropCerFideiusVO (BigDecimal idDettPropostaCertif, BigDecimal idFideiussione) {
	
		this. idDettPropostaCertif =  idDettPropostaCertif;
		this. idFideiussione =  idFideiussione;
	}
  	
	public PbandiRDettPropCerFideiusVO (BigDecimal importoUtilizzato, BigDecimal idDettPropostaCertif, BigDecimal idUtenteAgg, BigDecimal importoDocumento, BigDecimal idFideiussione, BigDecimal idUtenteIns) {
	
		this. importoUtilizzato =  importoUtilizzato;
		this. idDettPropostaCertif =  idDettPropostaCertif;
		this. idUtenteAgg =  idUtenteAgg;
		this. importoDocumento =  importoDocumento;
		this. idFideiussione =  idFideiussione;
		this. idUtenteIns =  idUtenteIns;
	}
  	
  	
	public BigDecimal getImportoUtilizzato() {
		return importoUtilizzato;
	}
	
	public void setImportoUtilizzato(BigDecimal importoUtilizzato) {
		this.importoUtilizzato = importoUtilizzato;
	}
	
	public BigDecimal getIdDettPropostaCertif() {
		return idDettPropostaCertif;
	}
	
	public void setIdDettPropostaCertif(BigDecimal idDettPropostaCertif) {
		this.idDettPropostaCertif = idDettPropostaCertif;
	}
	
	public BigDecimal getIdUtenteAgg() {
		return idUtenteAgg;
	}
	
	public void setIdUtenteAgg(BigDecimal idUtenteAgg) {
		this.idUtenteAgg = idUtenteAgg;
	}
	
	public BigDecimal getImportoDocumento() {
		return importoDocumento;
	}
	
	public void setImportoDocumento(BigDecimal importoDocumento) {
		this.importoDocumento = importoDocumento;
	}
	
	public BigDecimal getIdFideiussione() {
		return idFideiussione;
	}
	
	public void setIdFideiussione(BigDecimal idFideiussione) {
		this.idFideiussione = idFideiussione;
	}
	
	public BigDecimal getIdUtenteIns() {
		return idUtenteIns;
	}
	
	public void setIdUtenteIns(BigDecimal idUtenteIns) {
		this.idUtenteIns = idUtenteIns;
	}
	
	public boolean isValid() {
		boolean isValid = false;
                if (isPKValid() && importoUtilizzato != null && importoDocumento != null && idUtenteIns != null) {
   			isValid = true;
   		}
   		return isValid;
	}
	public boolean isPKValid() {
		boolean isPkValid = false;
		if (idDettPropostaCertif != null && idFideiussione != null ) {
   			isPkValid = true;
   		}

   		return isPkValid;
	}
	
	public String toString() {
		
	    String temp = "";
	    StringBuffer sb = new StringBuffer();
	    sb.append("\t\n" + this.getClass().getName() + "\t\n");
	    
	    temp = DataFilter.removeNull( importoUtilizzato);
	    if (!DataFilter.isEmpty(temp)) sb.append(" importoUtilizzato: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idDettPropostaCertif);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idDettPropostaCertif: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idUtenteAgg);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idUtenteAgg: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( importoDocumento);
	    if (!DataFilter.isEmpty(temp)) sb.append(" importoDocumento: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idFideiussione);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idFideiussione: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idUtenteIns);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idUtenteIns: " + temp + "\t\n");
	    
	    return sb.toString();
	}
	
	public java.util.List<String> getPK() {
		java.util.List<String> pk=new java.util.ArrayList<String>();
		
			pk.add("idDettPropostaCertif");
		
			pk.add("idFideiussione");
		
	    return pk;
	}
	
	
}
