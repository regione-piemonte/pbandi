/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/


package it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo;

import java.math.*;
import java.sql.Date;
import it.csi.pbandi.pbservizit.pbandisrv.integration.util.DataFilter;



public class PbandiDOccupazioneVO extends GenericVO {

  	
  	private String descOccupazione;
  	
  	private Date dtFineValidita;
  	
  	private String codIgrueT44;
  	
  	private Date dtInizioValidita;
  	
  	private BigDecimal idOccupazione;
  	
	public PbandiDOccupazioneVO() {}
  	
	public PbandiDOccupazioneVO (BigDecimal idOccupazione) {
	
		this. idOccupazione =  idOccupazione;
	}
  	
	public PbandiDOccupazioneVO (String descOccupazione, Date dtFineValidita, String codIgrueT44, Date dtInizioValidita, BigDecimal idOccupazione) {
	
		this. descOccupazione =  descOccupazione;
		this. dtFineValidita =  dtFineValidita;
		this. codIgrueT44 =  codIgrueT44;
		this. dtInizioValidita =  dtInizioValidita;
		this. idOccupazione =  idOccupazione;
	}
  	
  	
	public String getDescOccupazione() {
		return descOccupazione;
	}
	
	public void setDescOccupazione(String descOccupazione) {
		this.descOccupazione = descOccupazione;
	}
	
	public Date getDtFineValidita() {
		return dtFineValidita;
	}
	
	public void setDtFineValidita(Date dtFineValidita) {
		this.dtFineValidita = dtFineValidita;
	}
	
	public String getCodIgrueT44() {
		return codIgrueT44;
	}
	
	public void setCodIgrueT44(String codIgrueT44) {
		this.codIgrueT44 = codIgrueT44;
	}
	
	public Date getDtInizioValidita() {
		return dtInizioValidita;
	}
	
	public void setDtInizioValidita(Date dtInizioValidita) {
		this.dtInizioValidita = dtInizioValidita;
	}
	
	public BigDecimal getIdOccupazione() {
		return idOccupazione;
	}
	
	public void setIdOccupazione(BigDecimal idOccupazione) {
		this.idOccupazione = idOccupazione;
	}
	
	public boolean isValid() {
		boolean isValid = false;
                if (isPKValid() && descOccupazione != null && codIgrueT44 != null && dtInizioValidita != null) {
   			isValid = true;
   		}
   		return isValid;
	}
	public boolean isPKValid() {
		boolean isPkValid = false;
		if (idOccupazione != null ) {
   			isPkValid = true;
   		}

   		return isPkValid;
	}
	
	public String toString() {
		
	    String temp = "";
	    StringBuffer sb = new StringBuffer();
	    sb.append("\t\n" + this.getClass().getName() + "\t\n");
	    
	    temp = DataFilter.removeNull( descOccupazione);
	    if (!DataFilter.isEmpty(temp)) sb.append(" descOccupazione: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtFineValidita);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtFineValidita: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( codIgrueT44);
	    if (!DataFilter.isEmpty(temp)) sb.append(" codIgrueT44: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtInizioValidita);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtInizioValidita: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idOccupazione);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idOccupazione: " + temp + "\t\n");
	    
	    return sb.toString();
	}
	
	public java.util.List<String> getPK() {
		java.util.List<String> pk=new java.util.ArrayList<String>();
		
			pk.add("idOccupazione");
		
	    return pk;
	}
	
	
}
