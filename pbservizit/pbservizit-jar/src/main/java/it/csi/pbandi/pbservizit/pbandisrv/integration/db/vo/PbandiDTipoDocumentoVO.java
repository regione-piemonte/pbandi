/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/


package it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo;

import java.math.*;
import java.sql.Date;
import it.csi.pbandi.pbservizit.pbandisrv.integration.util.DataFilter;



public class PbandiDTipoDocumentoVO extends GenericVO {

  	
  	private Date dtFineValidita;
  	
  	private String descTipoDocumento;
  	
  	private String codGefo;
  	
  	private Date dtInizioValidita;
  	
  	private BigDecimal idTipoDocumento;
  	
  	private String descBreveTipoDocumento;
  	
	public PbandiDTipoDocumentoVO() {}
  	
	public PbandiDTipoDocumentoVO (BigDecimal idTipoDocumento) {
	
		this. idTipoDocumento =  idTipoDocumento;
	}
  	
	public PbandiDTipoDocumentoVO (Date dtFineValidita, String descTipoDocumento, String codGefo, Date dtInizioValidita, BigDecimal idTipoDocumento, String descBreveTipoDocumento) {
	
		this. dtFineValidita =  dtFineValidita;
		this. descTipoDocumento =  descTipoDocumento;
		this. codGefo =  codGefo;
		this. dtInizioValidita =  dtInizioValidita;
		this. idTipoDocumento =  idTipoDocumento;
		this. descBreveTipoDocumento =  descBreveTipoDocumento;
	}
  	
  	
	public Date getDtFineValidita() {
		return dtFineValidita;
	}
	
	public void setDtFineValidita(Date dtFineValidita) {
		this.dtFineValidita = dtFineValidita;
	}
	
	public String getDescTipoDocumento() {
		return descTipoDocumento;
	}
	
	public void setDescTipoDocumento(String descTipoDocumento) {
		this.descTipoDocumento = descTipoDocumento;
	}
	
	public String getCodGefo() {
		return codGefo;
	}
	
	public void setCodGefo(String codGefo) {
		this.codGefo = codGefo;
	}
	
	public Date getDtInizioValidita() {
		return dtInizioValidita;
	}
	
	public void setDtInizioValidita(Date dtInizioValidita) {
		this.dtInizioValidita = dtInizioValidita;
	}
	
	public BigDecimal getIdTipoDocumento() {
		return idTipoDocumento;
	}
	
	public void setIdTipoDocumento(BigDecimal idTipoDocumento) {
		this.idTipoDocumento = idTipoDocumento;
	}
	
	public String getDescBreveTipoDocumento() {
		return descBreveTipoDocumento;
	}
	
	public void setDescBreveTipoDocumento(String descBreveTipoDocumento) {
		this.descBreveTipoDocumento = descBreveTipoDocumento;
	}
	
	public boolean isValid() {
		boolean isValid = false;
                if (isPKValid() && descTipoDocumento != null && dtInizioValidita != null && descBreveTipoDocumento != null) {
   			isValid = true;
   		}
   		return isValid;
	}
	public boolean isPKValid() {
		boolean isPkValid = false;
		if (idTipoDocumento != null ) {
   			isPkValid = true;
   		}

   		return isPkValid;
	}
	
	public String toString() {
		
	    String temp = "";
	    StringBuffer sb = new StringBuffer();
	    sb.append("\t\n" + this.getClass().getName() + "\t\n");
	    
	    temp = DataFilter.removeNull( dtFineValidita);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtFineValidita: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( descTipoDocumento);
	    if (!DataFilter.isEmpty(temp)) sb.append(" descTipoDocumento: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( codGefo);
	    if (!DataFilter.isEmpty(temp)) sb.append(" codGefo: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtInizioValidita);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtInizioValidita: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idTipoDocumento);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idTipoDocumento: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( descBreveTipoDocumento);
	    if (!DataFilter.isEmpty(temp)) sb.append(" descBreveTipoDocumento: " + temp + "\t\n");
	    
	    return sb.toString();
	}
	
	public java.util.List<String> getPK() {
		java.util.List<String> pk=new java.util.ArrayList<String>();
		
			pk.add("idTipoDocumento");
		
	    return pk;
	}
	
	
}
