/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/


package it.csi.pbandi.pbweb.pbandisrv.integration.db.vo;

import java.math.*;
import java.sql.Date;
import java.util.List;

import it.csi.pbandi.pbweb.pbandisrv.integration.util.DataFilter;



public class PbandiDMicroSezioneModuloVO extends GenericVO {

  	
  	private String contenutoMicroSezione;
  	
  	private BigDecimal idMicroSezioneModulo;
  	
  	private Date dtFineValidita;
  	
  	private Date dtInizioValidita;
  	
  	private String descMicroSezione;
  	
	public PbandiDMicroSezioneModuloVO() {}
  	
	public PbandiDMicroSezioneModuloVO (BigDecimal idMicroSezioneModulo) {
	
		this. idMicroSezioneModulo =  idMicroSezioneModulo;
	}
  	
	public PbandiDMicroSezioneModuloVO (String contenutoMicroSezione, BigDecimal idMicroSezioneModulo, Date dtFineValidita, Date dtInizioValidita, String descMicroSezione) {
	
		this. contenutoMicroSezione =  contenutoMicroSezione;
		this. idMicroSezioneModulo =  idMicroSezioneModulo;
		this. dtFineValidita =  dtFineValidita;
		this. dtInizioValidita =  dtInizioValidita;
		this. descMicroSezione =  descMicroSezione;
	}
  	
  	
	public String getContenutoMicroSezione() {
		return contenutoMicroSezione;
	}
	
	public void setContenutoMicroSezione(String contenutoMicroSezione) {
		this.contenutoMicroSezione = contenutoMicroSezione;
	}
	
	public BigDecimal getIdMicroSezioneModulo() {
		return idMicroSezioneModulo;
	}
	
	public void setIdMicroSezioneModulo(BigDecimal idMicroSezioneModulo) {
		this.idMicroSezioneModulo = idMicroSezioneModulo;
	}
	
	public Date getDtFineValidita() {
		return dtFineValidita;
	}
	
	public void setDtFineValidita(Date dtFineValidita) {
		this.dtFineValidita = dtFineValidita;
	}
	
	public Date getDtInizioValidita() {
		return dtInizioValidita;
	}
	
	public void setDtInizioValidita(Date dtInizioValidita) {
		this.dtInizioValidita = dtInizioValidita;
	}
	
	public String getDescMicroSezione() {
		return descMicroSezione;
	}
	
	public void setDescMicroSezione(String descMicroSezione) {
		this.descMicroSezione = descMicroSezione;
	}
	
	public boolean isValid() {
		boolean isValid = false;
                if (isPKValid() && contenutoMicroSezione != null && dtInizioValidita != null && descMicroSezione != null) {
   			isValid = true;
   		}
   		return isValid;
	}
	public boolean isPKValid() {
		boolean isPkValid = false;
		if (idMicroSezioneModulo != null ) {
   			isPkValid = true;
   		}

   		return isPkValid;
	}
	
	public String toString() {
		
	    String temp = "";
	    StringBuffer sb = new StringBuffer();
	    sb.append("\t\n" + this.getClass().getName() + "\t\n");
	    
	    temp = DataFilter.removeNull( contenutoMicroSezione);
	    if (!DataFilter.isEmpty(temp)) sb.append(" contenutoMicroSezione: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idMicroSezioneModulo);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idMicroSezioneModulo: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtFineValidita);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtFineValidita: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtInizioValidita);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtInizioValidita: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( descMicroSezione);
	    if (!DataFilter.isEmpty(temp)) sb.append(" descMicroSezione: " + temp + "\t\n");
	    
	    return sb.toString();
	}

	@Override
	public List getPK() {
		java.util.List<String> pk=new java.util.ArrayList<String>();
		
		pk.add("idMicroSezioneModulo");
	
    return pk;
	}
	
	
	
}
