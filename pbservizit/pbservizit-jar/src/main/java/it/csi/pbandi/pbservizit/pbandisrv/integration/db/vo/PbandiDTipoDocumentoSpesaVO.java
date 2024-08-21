/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/


package it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo;

import java.math.*;
import java.sql.Date;
import it.csi.pbandi.pbservizit.pbandisrv.integration.util.DataFilter;



public class PbandiDTipoDocumentoSpesaVO extends GenericVO {

  	
  	private String descTipoDocumentoSpesa;
  	
  	private BigDecimal idTipoDocumentoSpesa;
  	
  	private Date dtFineValidita;
  	
  	private String descBreveTipoDocSpesa;
  	
  	private Date dtInizioValidita;
  	
	public PbandiDTipoDocumentoSpesaVO() {}
  	
	public PbandiDTipoDocumentoSpesaVO (BigDecimal idTipoDocumentoSpesa) {
	
		this. idTipoDocumentoSpesa =  idTipoDocumentoSpesa;
	}
  	
	public PbandiDTipoDocumentoSpesaVO (String descTipoDocumentoSpesa, BigDecimal idTipoDocumentoSpesa, Date dtFineValidita, String descBreveTipoDocSpesa, Date dtInizioValidita) {
	
		this. descTipoDocumentoSpesa =  descTipoDocumentoSpesa;
		this. idTipoDocumentoSpesa =  idTipoDocumentoSpesa;
		this. dtFineValidita =  dtFineValidita;
		this. descBreveTipoDocSpesa =  descBreveTipoDocSpesa;
		this. dtInizioValidita =  dtInizioValidita;
	}
  	
  	
	public String getDescTipoDocumentoSpesa() {
		return descTipoDocumentoSpesa;
	}
	
	public void setDescTipoDocumentoSpesa(String descTipoDocumentoSpesa) {
		this.descTipoDocumentoSpesa = descTipoDocumentoSpesa;
	}
	
	public BigDecimal getIdTipoDocumentoSpesa() {
		return idTipoDocumentoSpesa;
	}
	
	public void setIdTipoDocumentoSpesa(BigDecimal idTipoDocumentoSpesa) {
		this.idTipoDocumentoSpesa = idTipoDocumentoSpesa;
	}
	
	public Date getDtFineValidita() {
		return dtFineValidita;
	}
	
	public void setDtFineValidita(Date dtFineValidita) {
		this.dtFineValidita = dtFineValidita;
	}
	
	public String getDescBreveTipoDocSpesa() {
		return descBreveTipoDocSpesa;
	}
	
	public void setDescBreveTipoDocSpesa(String descBreveTipoDocSpesa) {
		this.descBreveTipoDocSpesa = descBreveTipoDocSpesa;
	}
	
	public Date getDtInizioValidita() {
		return dtInizioValidita;
	}
	
	public void setDtInizioValidita(Date dtInizioValidita) {
		this.dtInizioValidita = dtInizioValidita;
	}
	
	public boolean isValid() {
		boolean isValid = false;
                if (isPKValid() && descTipoDocumentoSpesa != null && descBreveTipoDocSpesa != null && dtInizioValidita != null) {
   			isValid = true;
   		}
   		return isValid;
	}
	public boolean isPKValid() {
		boolean isPkValid = false;
		if (idTipoDocumentoSpesa != null ) {
   			isPkValid = true;
   		}

   		return isPkValid;
	}
	
	public String toString() {
		
	    String temp = "";
	    StringBuffer sb = new StringBuffer();
	    sb.append("\t\n" + this.getClass().getName() + "\t\n");
	    
	    temp = DataFilter.removeNull( descTipoDocumentoSpesa);
	    if (!DataFilter.isEmpty(temp)) sb.append(" descTipoDocumentoSpesa: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idTipoDocumentoSpesa);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idTipoDocumentoSpesa: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtFineValidita);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtFineValidita: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( descBreveTipoDocSpesa);
	    if (!DataFilter.isEmpty(temp)) sb.append(" descBreveTipoDocSpesa: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtInizioValidita);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtInizioValidita: " + temp + "\t\n");
	    
	    return sb.toString();
	}
	
	public java.util.List<String> getPK() {
		java.util.List<String> pk=new java.util.ArrayList<String>();
		
			pk.add("idTipoDocumentoSpesa");
		
	    return pk;
	}
	
	
}
