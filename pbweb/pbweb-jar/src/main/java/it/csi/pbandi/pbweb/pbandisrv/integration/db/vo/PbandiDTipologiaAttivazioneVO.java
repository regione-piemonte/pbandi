/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/


package it.csi.pbandi.pbweb.pbandisrv.integration.db.vo;

import java.math.*;
import java.sql.Date;
import it.csi.pbandi.pbweb.pbandisrv.integration.util.DataFilter;



public class PbandiDTipologiaAttivazioneVO extends GenericVO {

  	
  	private BigDecimal idTipologiaAttivazione;
  	
  	private BigDecimal codIgrueT50;
  	
  	private Date dtFineValidita;
  	
  	private String descTipologiaAttivazione;
  	
  	private Date dtInizioValidita;
  	
	public PbandiDTipologiaAttivazioneVO() {}
  	
	public PbandiDTipologiaAttivazioneVO (BigDecimal idTipologiaAttivazione) {
	
		this. idTipologiaAttivazione =  idTipologiaAttivazione;
	}
  	
	public PbandiDTipologiaAttivazioneVO (BigDecimal idTipologiaAttivazione, BigDecimal codIgrueT50, Date dtFineValidita, String descTipologiaAttivazione, Date dtInizioValidita) {
	
		this. idTipologiaAttivazione =  idTipologiaAttivazione;
		this. codIgrueT50 =  codIgrueT50;
		this. dtFineValidita =  dtFineValidita;
		this. descTipologiaAttivazione =  descTipologiaAttivazione;
		this. dtInizioValidita =  dtInizioValidita;
	}
  	
  	
	public BigDecimal getIdTipologiaAttivazione() {
		return idTipologiaAttivazione;
	}
	
	public void setIdTipologiaAttivazione(BigDecimal idTipologiaAttivazione) {
		this.idTipologiaAttivazione = idTipologiaAttivazione;
	}
	
	public BigDecimal getCodIgrueT50() {
		return codIgrueT50;
	}
	
	public void setCodIgrueT50(BigDecimal codIgrueT50) {
		this.codIgrueT50 = codIgrueT50;
	}
	
	public Date getDtFineValidita() {
		return dtFineValidita;
	}
	
	public void setDtFineValidita(Date dtFineValidita) {
		this.dtFineValidita = dtFineValidita;
	}
	
	public String getDescTipologiaAttivazione() {
		return descTipologiaAttivazione;
	}
	
	public void setDescTipologiaAttivazione(String descTipologiaAttivazione) {
		this.descTipologiaAttivazione = descTipologiaAttivazione;
	}
	
	public Date getDtInizioValidita() {
		return dtInizioValidita;
	}
	
	public void setDtInizioValidita(Date dtInizioValidita) {
		this.dtInizioValidita = dtInizioValidita;
	}
	
	public boolean isValid() {
		boolean isValid = false;
                if (isPKValid() && codIgrueT50 != null && descTipologiaAttivazione != null && dtInizioValidita != null) {
   			isValid = true;
   		}
   		return isValid;
	}
	public boolean isPKValid() {
		boolean isPkValid = false;
		if (idTipologiaAttivazione != null ) {
   			isPkValid = true;
   		}

   		return isPkValid;
	}
	
	public String toString() {
		
	    String temp = "";
	    StringBuffer sb = new StringBuffer();
	    sb.append("\t\n" + this.getClass().getName() + "\t\n");
	    
	    temp = DataFilter.removeNull( idTipologiaAttivazione);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idTipologiaAttivazione: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( codIgrueT50);
	    if (!DataFilter.isEmpty(temp)) sb.append(" codIgrueT50: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtFineValidita);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtFineValidita: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( descTipologiaAttivazione);
	    if (!DataFilter.isEmpty(temp)) sb.append(" descTipologiaAttivazione: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtInizioValidita);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtInizioValidita: " + temp + "\t\n");
	    
	    return sb.toString();
	}
	
	public java.util.List<String> getPK() {
		java.util.List<String> pk=new java.util.ArrayList<String>();
		
			pk.add("idTipologiaAttivazione");
		
	    return pk;
	}
	
	
}
