
package it.csi.pbandi.pbweb.pbandisrv.integration.db.vo;

import java.math.*;
import java.sql.Date;
import it.csi.pbandi.pbweb.pbandisrv.integration.util.DataFilter;



public class PbandiTDocumentoVO extends GenericVO {

  	
  	private BigDecimal idDocumento;
  	
  	private BigDecimal idUtenteAgg;
  	
  	private Date dtInizioValidita;
  	
  	private Date dtScadenzaDocumento;
  	
  	private Date dtFineValidita;
  	
  	private BigDecimal idTipoDocumento;
  	
  	private String documentoRilasciatoDa;
  	
  	private Date dtRilascioDocumento;
  	
  	private BigDecimal idUtenteIns;
  	
  	private String numeroDocumento;
  	
	public PbandiTDocumentoVO() {}
  	
	public PbandiTDocumentoVO (BigDecimal idDocumento) {
	
		this. idDocumento =  idDocumento;
	}
  	
	public PbandiTDocumentoVO (BigDecimal idDocumento, BigDecimal idUtenteAgg, Date dtInizioValidita, Date dtScadenzaDocumento, Date dtFineValidita, BigDecimal idTipoDocumento, String documentoRilasciatoDa, Date dtRilascioDocumento, BigDecimal idUtenteIns, String numeroDocumento) {
	
		this. idDocumento =  idDocumento;
		this. idUtenteAgg =  idUtenteAgg;
		this. dtInizioValidita =  dtInizioValidita;
		this. dtScadenzaDocumento =  dtScadenzaDocumento;
		this. dtFineValidita =  dtFineValidita;
		this. idTipoDocumento =  idTipoDocumento;
		this. documentoRilasciatoDa =  documentoRilasciatoDa;
		this. dtRilascioDocumento =  dtRilascioDocumento;
		this. idUtenteIns =  idUtenteIns;
		this. numeroDocumento =  numeroDocumento;
	}
  	
  	
	public BigDecimal getIdDocumento() {
		return idDocumento;
	}
	
	public void setIdDocumento(BigDecimal idDocumento) {
		this.idDocumento = idDocumento;
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
	
	public Date getDtScadenzaDocumento() {
		return dtScadenzaDocumento;
	}
	
	public void setDtScadenzaDocumento(Date dtScadenzaDocumento) {
		this.dtScadenzaDocumento = dtScadenzaDocumento;
	}
	
	public Date getDtFineValidita() {
		return dtFineValidita;
	}
	
	public void setDtFineValidita(Date dtFineValidita) {
		this.dtFineValidita = dtFineValidita;
	}
	
	public BigDecimal getIdTipoDocumento() {
		return idTipoDocumento;
	}
	
	public void setIdTipoDocumento(BigDecimal idTipoDocumento) {
		this.idTipoDocumento = idTipoDocumento;
	}
	
	public String getDocumentoRilasciatoDa() {
		return documentoRilasciatoDa;
	}
	
	public void setDocumentoRilasciatoDa(String documentoRilasciatoDa) {
		this.documentoRilasciatoDa = documentoRilasciatoDa;
	}
	
	public Date getDtRilascioDocumento() {
		return dtRilascioDocumento;
	}
	
	public void setDtRilascioDocumento(Date dtRilascioDocumento) {
		this.dtRilascioDocumento = dtRilascioDocumento;
	}
	
	public BigDecimal getIdUtenteIns() {
		return idUtenteIns;
	}
	
	public void setIdUtenteIns(BigDecimal idUtenteIns) {
		this.idUtenteIns = idUtenteIns;
	}
	
	public String getNumeroDocumento() {
		return numeroDocumento;
	}
	
	public void setNumeroDocumento(String numeroDocumento) {
		this.numeroDocumento = numeroDocumento;
	}
	
	public boolean isValid() {
		boolean isValid = false;
                if (isPKValid() && dtInizioValidita != null && idTipoDocumento != null && idUtenteIns != null) {
   			isValid = true;
   		}
   		return isValid;
	}
	public boolean isPKValid() {
		boolean isPkValid = false;
		if (idDocumento != null ) {
   			isPkValid = true;
   		}

   		return isPkValid;
	}
	
	public String toString() {
		
	    String temp = "";
	    StringBuffer sb = new StringBuffer();
	    sb.append("\t\n" + this.getClass().getName() + "\t\n");
	    
	    temp = DataFilter.removeNull( idDocumento);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idDocumento: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idUtenteAgg);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idUtenteAgg: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtInizioValidita);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtInizioValidita: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtScadenzaDocumento);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtScadenzaDocumento: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtFineValidita);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtFineValidita: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idTipoDocumento);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idTipoDocumento: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( documentoRilasciatoDa);
	    if (!DataFilter.isEmpty(temp)) sb.append(" documentoRilasciatoDa: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtRilascioDocumento);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtRilascioDocumento: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idUtenteIns);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idUtenteIns: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( numeroDocumento);
	    if (!DataFilter.isEmpty(temp)) sb.append(" numeroDocumento: " + temp + "\t\n");
	    
	    return sb.toString();
	}
	
	public java.util.List<String> getPK() {
		java.util.List<String> pk=new java.util.ArrayList<String>();
		
			pk.add("idDocumento");
		
	    return pk;
	}
	
	
}
