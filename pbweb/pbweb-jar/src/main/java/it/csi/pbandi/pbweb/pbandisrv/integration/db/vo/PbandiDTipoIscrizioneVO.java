/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/


package it.csi.pbandi.pbweb.pbandisrv.integration.db.vo;

import java.math.*;
import java.sql.Date;
import it.csi.pbandi.pbweb.pbandisrv.integration.util.DataFilter;



public class PbandiDTipoIscrizioneVO extends GenericVO {

  	
  	private String codTipoIscrizione;
  	
  	private BigDecimal idTipoIscrizione;
  	
  	private Date dtFineValidita;
  	
  	private String descTipoIscrizione;
  	
  	private Date dtInizioValidita;
  	
	public PbandiDTipoIscrizioneVO() {}
  	
	public PbandiDTipoIscrizioneVO (BigDecimal idTipoIscrizione) {
	
		this. idTipoIscrizione =  idTipoIscrizione;
	}
  	
	public PbandiDTipoIscrizioneVO (String codTipoIscrizione, BigDecimal idTipoIscrizione, Date dtFineValidita, String descTipoIscrizione, Date dtInizioValidita) {
	
		this. codTipoIscrizione =  codTipoIscrizione;
		this. idTipoIscrizione =  idTipoIscrizione;
		this. dtFineValidita =  dtFineValidita;
		this. descTipoIscrizione =  descTipoIscrizione;
		this. dtInizioValidita =  dtInizioValidita;
	}
  	
  	
	public String getCodTipoIscrizione() {
		return codTipoIscrizione;
	}
	
	public void setCodTipoIscrizione(String codTipoIscrizione) {
		this.codTipoIscrizione = codTipoIscrizione;
	}
	
	public BigDecimal getIdTipoIscrizione() {
		return idTipoIscrizione;
	}
	
	public void setIdTipoIscrizione(BigDecimal idTipoIscrizione) {
		this.idTipoIscrizione = idTipoIscrizione;
	}
	
	public Date getDtFineValidita() {
		return dtFineValidita;
	}
	
	public void setDtFineValidita(Date dtFineValidita) {
		this.dtFineValidita = dtFineValidita;
	}
	
	public String getDescTipoIscrizione() {
		return descTipoIscrizione;
	}
	
	public void setDescTipoIscrizione(String descTipoIscrizione) {
		this.descTipoIscrizione = descTipoIscrizione;
	}
	
	public Date getDtInizioValidita() {
		return dtInizioValidita;
	}
	
	public void setDtInizioValidita(Date dtInizioValidita) {
		this.dtInizioValidita = dtInizioValidita;
	}
	
	public boolean isValid() {
		boolean isValid = false;
                if (isPKValid() && codTipoIscrizione != null && descTipoIscrizione != null && dtInizioValidita != null) {
   			isValid = true;
   		}
   		return isValid;
	}
	public boolean isPKValid() {
		boolean isPkValid = false;
		if (idTipoIscrizione != null ) {
   			isPkValid = true;
   		}

   		return isPkValid;
	}
	
	public String toString() {
		
	    String temp = "";
	    StringBuffer sb = new StringBuffer();
	    sb.append("\t\n" + this.getClass().getName() + "\t\n");
	    
	    temp = DataFilter.removeNull( codTipoIscrizione);
	    if (!DataFilter.isEmpty(temp)) sb.append(" codTipoIscrizione: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idTipoIscrizione);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idTipoIscrizione: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtFineValidita);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtFineValidita: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( descTipoIscrizione);
	    if (!DataFilter.isEmpty(temp)) sb.append(" descTipoIscrizione: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtInizioValidita);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtInizioValidita: " + temp + "\t\n");
	    
	    return sb.toString();
	}
	
	public java.util.List<String> getPK() {
		java.util.List<String> pk=new java.util.ArrayList<String>();
		
			pk.add("idTipoIscrizione");
		
	    return pk;
	}
	
	
}
