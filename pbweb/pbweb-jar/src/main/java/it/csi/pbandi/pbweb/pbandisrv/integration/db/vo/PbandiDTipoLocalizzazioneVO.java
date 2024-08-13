/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/


package it.csi.pbandi.pbweb.pbandisrv.integration.db.vo;

import java.math.*;
import java.sql.Date;
import it.csi.pbandi.pbweb.pbandisrv.integration.util.DataFilter;



public class PbandiDTipoLocalizzazioneVO extends GenericVO {

  	
  	private BigDecimal idTipoLocalizzazione;
  	
  	private String descBreveTipoLocal;
  	
  	private String descTipoLocalizzazione;
  	
  	private Date dtInizioValidita;
  	
  	private Date dtFineValidita;
  	
  	private String tc10;
  	
	public PbandiDTipoLocalizzazioneVO() {}
  	
	public PbandiDTipoLocalizzazioneVO (BigDecimal idTipoLocalizzazione) {	
		this.idTipoLocalizzazione =  idTipoLocalizzazione;
	}
  	
	public PbandiDTipoLocalizzazioneVO (BigDecimal idTipoLocalizzazione, String descBreveTipoLocal, String descTipoLocalizzazione,  Date dtInizioValidita, Date dtFineValidita, String tc10) {
		this.idTipoLocalizzazione =  idTipoLocalizzazione;
		this.descBreveTipoLocal =  descBreveTipoLocal;
		this.descTipoLocalizzazione =  descTipoLocalizzazione;
		this.dtInizioValidita =  dtInizioValidita;
		this.dtFineValidita =  dtFineValidita;
		this.tc10 =  tc10;		
	}
  	
	public boolean isValid() {
		boolean isValid = false;
                if (isPKValid() && descBreveTipoLocal != null && descTipoLocalizzazione != null && dtInizioValidita != null) {
   			isValid = true;
   		}
   		return isValid;
	}
	public boolean isPKValid() {
		boolean isPkValid = false;
		if (idTipoLocalizzazione != null ) {
   			isPkValid = true;
   		}
   		return isPkValid;
	}
	
	public String toString() {
		
	    String temp = "";
	    StringBuffer sb = new StringBuffer();
	    sb.append("\t\n" + this.getClass().getName() + "\t\n");
	    
	    temp = DataFilter.removeNull( idTipoLocalizzazione);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idTipoLocalizzazione: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( descBreveTipoLocal);
	    if (!DataFilter.isEmpty(temp)) sb.append(" descBreveTipoLocal: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( descTipoLocalizzazione);
	    if (!DataFilter.isEmpty(temp)) sb.append(" descTipoLocalizzazione: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtInizioValidita);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtInizioValidita: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtFineValidita);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtFineValidita: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( tc10);
	    if (!DataFilter.isEmpty(temp)) sb.append(" tc10: " + temp + "\t\n");
	   	    
	    return sb.toString();
	}
	
	public java.util.List<String> getPK() {
		java.util.List<String> pk=new java.util.ArrayList<String>();		
			pk.add("idTipoLocalizzazione");		
	    return pk;
	}

	public BigDecimal getIdTipoLocalizzazione() {
		return idTipoLocalizzazione;
	}

	public void setIdTipoLocalizzazione(BigDecimal idTipoLocalizzazione) {
		this.idTipoLocalizzazione = idTipoLocalizzazione;
	}

	public String getDescBreveTipoLocal() {
		return descBreveTipoLocal;
	}

	public void setDescBreveTipoLocal(String descBreveTipoLocal) {
		this.descBreveTipoLocal = descBreveTipoLocal;
	}

	public String getDescTipoLocalizzazione() {
		return descTipoLocalizzazione;
	}

	public void setDescTipoLocalizzazione(String descTipoLocalizzazione) {
		this.descTipoLocalizzazione = descTipoLocalizzazione;
	}

	public Date getDtInizioValidita() {
		return dtInizioValidita;
	}

	public void setDtInizioValidita(Date dtInizioValidita) {
		this.dtInizioValidita = dtInizioValidita;
	}

	public Date getDtFineValidita() {
		return dtFineValidita;
	}

	public void setDtFineValidita(Date dtFineValidita) {
		this.dtFineValidita = dtFineValidita;
	}

	public String getTc10() {
		return tc10;
	}

	public void setTc10(String tc10) {
		this.tc10 = tc10;
	}

}
