/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/


package it.csi.pbandi.pbwebrce.pbandisrv.integration.db.vo;

import java.math.*;
import java.sql.Date;
import it.csi.pbandi.pbwebrce.pbandisrv.integration.util.DataFilter;



public class PbandiDTipoDichiarazSpesaVO extends GenericVO {

  	
  	private Date dtFineValidita;
  	
  	private BigDecimal idTipoDichiarazSpesa;
  	
  	private String descTipoDichiarazioneSpesa;
  	
  	private Date dtInizioValidita;
  	
  	private String descBreveTipoDichiaraSpesa;
  	
  	private String flagSelezionabile;
  	
	public PbandiDTipoDichiarazSpesaVO() {}
  	
	public PbandiDTipoDichiarazSpesaVO (BigDecimal idTipoDichiarazSpesa) {
	
		this. idTipoDichiarazSpesa =  idTipoDichiarazSpesa;
	}
  	
	public PbandiDTipoDichiarazSpesaVO (Date dtFineValidita, BigDecimal idTipoDichiarazSpesa, String descTipoDichiarazioneSpesa, Date dtInizioValidita, String descBreveTipoDichiaraSpesa,String flagSelezionabile) {
	
		this. dtFineValidita =  dtFineValidita;
		this. idTipoDichiarazSpesa =  idTipoDichiarazSpesa;
		this. descTipoDichiarazioneSpesa =  descTipoDichiarazioneSpesa;
		this. dtInizioValidita =  dtInizioValidita;
		this. descBreveTipoDichiaraSpesa =  descBreveTipoDichiaraSpesa;
		this. flagSelezionabile = flagSelezionabile; 
	}
  	
  	
	public Date getDtFineValidita() {
		return dtFineValidita;
	}
	
	public void setDtFineValidita(Date dtFineValidita) {
		this.dtFineValidita = dtFineValidita;
	}
	
	public BigDecimal getIdTipoDichiarazSpesa() {
		return idTipoDichiarazSpesa;
	}
	
	public void setIdTipoDichiarazSpesa(BigDecimal idTipoDichiarazSpesa) {
		this.idTipoDichiarazSpesa = idTipoDichiarazSpesa;
	}
	
	public String getDescTipoDichiarazioneSpesa() {
		return descTipoDichiarazioneSpesa;
	}
	
	public void setDescTipoDichiarazioneSpesa(String descTipoDichiarazioneSpesa) {
		this.descTipoDichiarazioneSpesa = descTipoDichiarazioneSpesa;
	}
	
	public Date getDtInizioValidita() {
		return dtInizioValidita;
	}
	
	public void setDtInizioValidita(Date dtInizioValidita) {
		this.dtInizioValidita = dtInizioValidita;
	}
	
	public String getDescBreveTipoDichiaraSpesa() {
		return descBreveTipoDichiaraSpesa;
	}
	
	public void setDescBreveTipoDichiaraSpesa(String descBreveTipoDichiaraSpesa) {
		this.descBreveTipoDichiaraSpesa = descBreveTipoDichiaraSpesa;
	}
	
	public void setFlagSelezionabile(String flagSelezionabile) {
		this.flagSelezionabile = flagSelezionabile;
	}

	public String getFlagSelezionabile() {
		return flagSelezionabile;
	}
	
	public boolean isValid() {
		boolean isValid = false;
                if (isPKValid() && descTipoDichiarazioneSpesa != null && dtInizioValidita != null && descBreveTipoDichiaraSpesa != null) {
   			isValid = true;
   		}
   		return isValid;
	}
	public boolean isPKValid() {
		boolean isPkValid = false;
		if (idTipoDichiarazSpesa != null ) {
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
	    
	    temp = DataFilter.removeNull( idTipoDichiarazSpesa);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idTipoDichiarazSpesa: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( descTipoDichiarazioneSpesa);
	    if (!DataFilter.isEmpty(temp)) sb.append(" descTipoDichiarazioneSpesa: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtInizioValidita);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtInizioValidita: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( descBreveTipoDichiaraSpesa);
	    if (!DataFilter.isEmpty(temp)) sb.append(" descBreveTipoDichiaraSpesa: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( flagSelezionabile);
	    if (!DataFilter.isEmpty(temp)) sb.append(" flagSelezionabile: " + temp + "\t\n");
	    
	    return sb.toString();
	}
	
	public java.util.List<String> getPK() {
		java.util.List<String> pk=new java.util.ArrayList<String>();
		
			pk.add("idTipoDichiarazSpesa");
		
	    return pk;
	}


	
	
}
