
package it.csi.pbandi.pbweb.pbandisrv.integration.db.vo;

import java.math.*;
import java.sql.Date;
import it.csi.pbandi.pbweb.pbandisrv.integration.util.DataFilter;



public class PbandiRDetPropCerQpDocVO extends GenericVO {

  	
  	private BigDecimal importoUtilizzato;
  	
  	private BigDecimal idDettPropostaCertif;
  	
  	private BigDecimal idDichiarazioneSpesa;
  	
  	private BigDecimal idUtenteAgg;
  	
  	private BigDecimal progrPagQuotParteDocSp;
  	
  	private BigDecimal importoDocumento;
  	
  	private BigDecimal idUtenteIns;
  	
	public PbandiRDetPropCerQpDocVO() {}
  	
	public PbandiRDetPropCerQpDocVO (BigDecimal idDettPropostaCertif, BigDecimal progrPagQuotParteDocSp) {
	
		this. idDettPropostaCertif =  idDettPropostaCertif;
		this. progrPagQuotParteDocSp =  progrPagQuotParteDocSp;
	}
  	
	public PbandiRDetPropCerQpDocVO (BigDecimal importoUtilizzato, BigDecimal idDettPropostaCertif, BigDecimal idDichiarazioneSpesa, BigDecimal idUtenteAgg, BigDecimal progrPagQuotParteDocSp, BigDecimal importoDocumento, BigDecimal idUtenteIns) {
	
		this. importoUtilizzato =  importoUtilizzato;
		this. idDettPropostaCertif =  idDettPropostaCertif;
		this. idDichiarazioneSpesa =  idDichiarazioneSpesa;
		this. idUtenteAgg =  idUtenteAgg;
		this. progrPagQuotParteDocSp =  progrPagQuotParteDocSp;
		this. importoDocumento =  importoDocumento;
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
	
	public BigDecimal getIdDichiarazioneSpesa() {
		return idDichiarazioneSpesa;
	}
	
	public void setIdDichiarazioneSpesa(BigDecimal idDichiarazioneSpesa) {
		this.idDichiarazioneSpesa = idDichiarazioneSpesa;
	}
	
	public BigDecimal getIdUtenteAgg() {
		return idUtenteAgg;
	}
	
	public void setIdUtenteAgg(BigDecimal idUtenteAgg) {
		this.idUtenteAgg = idUtenteAgg;
	}
	
	public BigDecimal getProgrPagQuotParteDocSp() {
		return progrPagQuotParteDocSp;
	}
	
	public void setProgrPagQuotParteDocSp(BigDecimal progrPagQuotParteDocSp) {
		this.progrPagQuotParteDocSp = progrPagQuotParteDocSp;
	}
	
	public BigDecimal getImportoDocumento() {
		return importoDocumento;
	}
	
	public void setImportoDocumento(BigDecimal importoDocumento) {
		this.importoDocumento = importoDocumento;
	}
	
	public BigDecimal getIdUtenteIns() {
		return idUtenteIns;
	}
	
	public void setIdUtenteIns(BigDecimal idUtenteIns) {
		this.idUtenteIns = idUtenteIns;
	}
	
	public boolean isValid() {
		boolean isValid = false;
                if (isPKValid() && importoUtilizzato != null && idDichiarazioneSpesa != null && importoDocumento != null && idUtenteIns != null) {
   			isValid = true;
   		}
   		return isValid;
	}
	public boolean isPKValid() {
		boolean isPkValid = false;
		if (idDettPropostaCertif != null && progrPagQuotParteDocSp != null ) {
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
	    
	    temp = DataFilter.removeNull( idDichiarazioneSpesa);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idDichiarazioneSpesa: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idUtenteAgg);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idUtenteAgg: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( progrPagQuotParteDocSp);
	    if (!DataFilter.isEmpty(temp)) sb.append(" progrPagQuotParteDocSp: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( importoDocumento);
	    if (!DataFilter.isEmpty(temp)) sb.append(" importoDocumento: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idUtenteIns);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idUtenteIns: " + temp + "\t\n");
	    
	    return sb.toString();
	}
	
	public java.util.List<String> getPK() {
		java.util.List<String> pk=new java.util.ArrayList<String>();
		
			pk.add("idDettPropostaCertif");
		
			pk.add("progrPagQuotParteDocSp");
		
	    return pk;
	}
	
	
}
