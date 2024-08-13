/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/


package it.csi.pbandi.pbweb.pbandisrv.integration.db.vo;

import java.math.*;
import java.sql.Date;
import it.csi.pbandi.pbweb.pbandisrv.integration.util.DataFilter;



public class PbandiCOperazioneVO extends GenericVO {

  	
  	private String descFisica;
  	
  	private String descLogica;
  	
  	private Date dtFineValidita;
  	
  	private Date dtInizioValidita;
  	
  	private BigDecimal idOperazione;
  	
  	private String flagDaTracciare;
  	
	public PbandiCOperazioneVO() {}
  	
	public PbandiCOperazioneVO (BigDecimal idOperazione) {
	
		this. idOperazione =  idOperazione;
	}
  	
	public PbandiCOperazioneVO (String descFisica, String descLogica, Date dtFineValidita, Date dtInizioValidita, BigDecimal idOperazione, String flagDaTracciare) {
	
		this. descFisica =  descFisica;
		this. descLogica =  descLogica;
		this. dtFineValidita =  dtFineValidita;
		this. dtInizioValidita =  dtInizioValidita;
		this. idOperazione =  idOperazione;
		this. flagDaTracciare =  flagDaTracciare;
	}
  	
  	
	public String getDescFisica() {
		return descFisica;
	}
	
	public void setDescFisica(String descFisica) {
		this.descFisica = descFisica;
	}
	
	public String getDescLogica() {
		return descLogica;
	}
	
	public void setDescLogica(String descLogica) {
		this.descLogica = descLogica;
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
	
	public BigDecimal getIdOperazione() {
		return idOperazione;
	}
	
	public void setIdOperazione(BigDecimal idOperazione) {
		this.idOperazione = idOperazione;
	}
	
	public String getFlagDaTracciare() {
		return flagDaTracciare;
	}
	
	public void setFlagDaTracciare(String flagDaTracciare) {
		this.flagDaTracciare = flagDaTracciare;
	}
	
	public boolean isValid() {
		boolean isValid = false;
                if (isPKValid() && dtInizioValidita != null && flagDaTracciare != null) {
   			isValid = true;
   		}
   		return isValid;
	}
	public boolean isPKValid() {
		boolean isPkValid = false;
		if (idOperazione != null ) {
   			isPkValid = true;
   		}

   		return isPkValid;
	}
	
	public String toString() {
		
	    String temp = "";
	    StringBuffer sb = new StringBuffer();
	    sb.append("\t\n" + this.getClass().getName() + "\t\n");
	    
	    temp = DataFilter.removeNull( descFisica);
	    if (!DataFilter.isEmpty(temp)) sb.append(" descFisica: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( descLogica);
	    if (!DataFilter.isEmpty(temp)) sb.append(" descLogica: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtFineValidita);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtFineValidita: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtInizioValidita);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtInizioValidita: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idOperazione);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idOperazione: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( flagDaTracciare);
	    if (!DataFilter.isEmpty(temp)) sb.append(" flagDaTracciare: " + temp + "\t\n");
	    
	    return sb.toString();
	}
	
	public java.util.List getPK() {
		java.util.List pk=new java.util.ArrayList();
		
			pk.add("idOperazione");
		
	    return pk;
	}
	
	
}
