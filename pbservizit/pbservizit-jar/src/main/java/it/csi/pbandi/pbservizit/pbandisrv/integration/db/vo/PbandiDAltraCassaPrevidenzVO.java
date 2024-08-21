/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/


package it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo;

import java.math.*;
import java.sql.Date;
import it.csi.pbandi.pbservizit.pbandisrv.integration.util.DataFilter;



public class PbandiDAltraCassaPrevidenzVO extends GenericVO {

  	
  	private String codAltraCassa;
  	
  	private Date dtFineValidita;
  	
  	private String descAltraCassa;
  	
  	private Date dtInizioValidita;
  	
  	private BigDecimal idAltraCassaPrevidenz;
  	
	public PbandiDAltraCassaPrevidenzVO() {}
  	
	public PbandiDAltraCassaPrevidenzVO (BigDecimal idAltraCassaPrevidenz) {
	
		this. idAltraCassaPrevidenz =  idAltraCassaPrevidenz;
	}
  	
	public PbandiDAltraCassaPrevidenzVO (String codAltraCassa, Date dtFineValidita, String descAltraCassa, Date dtInizioValidita, BigDecimal idAltraCassaPrevidenz) {
	
		this. codAltraCassa =  codAltraCassa;
		this. dtFineValidita =  dtFineValidita;
		this. descAltraCassa =  descAltraCassa;
		this. dtInizioValidita =  dtInizioValidita;
		this. idAltraCassaPrevidenz =  idAltraCassaPrevidenz;
	}
  	
  	
	public String getCodAltraCassa() {
		return codAltraCassa;
	}
	
	public void setCodAltraCassa(String codAltraCassa) {
		this.codAltraCassa = codAltraCassa;
	}
	
	public Date getDtFineValidita() {
		return dtFineValidita;
	}
	
	public void setDtFineValidita(Date dtFineValidita) {
		this.dtFineValidita = dtFineValidita;
	}
	
	public String getDescAltraCassa() {
		return descAltraCassa;
	}
	
	public void setDescAltraCassa(String descAltraCassa) {
		this.descAltraCassa = descAltraCassa;
	}
	
	public Date getDtInizioValidita() {
		return dtInizioValidita;
	}
	
	public void setDtInizioValidita(Date dtInizioValidita) {
		this.dtInizioValidita = dtInizioValidita;
	}
	
	public BigDecimal getIdAltraCassaPrevidenz() {
		return idAltraCassaPrevidenz;
	}
	
	public void setIdAltraCassaPrevidenz(BigDecimal idAltraCassaPrevidenz) {
		this.idAltraCassaPrevidenz = idAltraCassaPrevidenz;
	}
	
	public boolean isValid() {
		boolean isValid = false;
                if (isPKValid() && codAltraCassa != null && descAltraCassa != null && dtInizioValidita != null) {
   			isValid = true;
   		}
   		return isValid;
	}
	public boolean isPKValid() {
		boolean isPkValid = false;
		if (idAltraCassaPrevidenz != null ) {
   			isPkValid = true;
   		}

   		return isPkValid;
	}
	
	public String toString() {
		
	    String temp = "";
	    StringBuffer sb = new StringBuffer();
	    sb.append("\t\n" + this.getClass().getName() + "\t\n");
	    
	    temp = DataFilter.removeNull( codAltraCassa);
	    if (!DataFilter.isEmpty(temp)) sb.append(" codAltraCassa: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtFineValidita);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtFineValidita: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( descAltraCassa);
	    if (!DataFilter.isEmpty(temp)) sb.append(" descAltraCassa: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtInizioValidita);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtInizioValidita: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idAltraCassaPrevidenz);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idAltraCassaPrevidenz: " + temp + "\t\n");
	    
	    return sb.toString();
	}
	
	public java.util.List getPK() {
		java.util.List pk=new java.util.ArrayList();
		
			pk.add("idAltraCassaPrevidenz");
		
	    return pk;
	}
	
	
}
