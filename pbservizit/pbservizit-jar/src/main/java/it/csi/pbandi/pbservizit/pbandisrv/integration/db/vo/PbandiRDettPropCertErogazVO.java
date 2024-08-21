/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/


package it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo;

import java.math.*;
import java.sql.Date;
import it.csi.pbandi.pbservizit.pbandisrv.integration.util.DataFilter;



public class PbandiRDettPropCertErogazVO extends GenericVO {

  	
  	private BigDecimal importoUtilizzato;
  	
  	private BigDecimal idDettPropostaCertif;
  	
  	private BigDecimal idUtenteAgg;
  	
  	private BigDecimal importoDocumento;
  	
  	private BigDecimal idUtenteIns;
  	
  	private BigDecimal idErogazione;
  	
	public PbandiRDettPropCertErogazVO() {}
  	
	public PbandiRDettPropCertErogazVO (BigDecimal idDettPropostaCertif, BigDecimal idErogazione) {
	
		this. idDettPropostaCertif =  idDettPropostaCertif;
		this. idErogazione =  idErogazione;
	}
  	
	public PbandiRDettPropCertErogazVO (BigDecimal importoUtilizzato, BigDecimal idDettPropostaCertif, BigDecimal idUtenteAgg, BigDecimal importoDocumento, BigDecimal idUtenteIns, BigDecimal idErogazione) {
	
		this. importoUtilizzato =  importoUtilizzato;
		this. idDettPropostaCertif =  idDettPropostaCertif;
		this. idUtenteAgg =  idUtenteAgg;
		this. importoDocumento =  importoDocumento;
		this. idUtenteIns =  idUtenteIns;
		this. idErogazione =  idErogazione;
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
	
	public BigDecimal getIdUtenteIns() {
		return idUtenteIns;
	}
	
	public void setIdUtenteIns(BigDecimal idUtenteIns) {
		this.idUtenteIns = idUtenteIns;
	}
	
	public BigDecimal getIdErogazione() {
		return idErogazione;
	}
	
	public void setIdErogazione(BigDecimal idErogazione) {
		this.idErogazione = idErogazione;
	}
	
	public boolean isValid() {
		boolean isValid = false;
                if (isPKValid() && importoUtilizzato != null && importoDocumento != null) {
   			isValid = true;
   		}
   		return isValid;
	}
	public boolean isPKValid() {
		boolean isPkValid = false;
		if (idDettPropostaCertif != null && idErogazione != null ) {
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
	    
	    temp = DataFilter.removeNull( idUtenteIns);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idUtenteIns: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idErogazione);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idErogazione: " + temp + "\t\n");
	    
	    return sb.toString();
	}
	
	public java.util.List<String> getPK() {
		java.util.List<String> pk=new java.util.ArrayList<String>();
		
			pk.add("idDettPropostaCertif");
		
			pk.add("idErogazione");
		
	    return pk;
	}
	
	
}
