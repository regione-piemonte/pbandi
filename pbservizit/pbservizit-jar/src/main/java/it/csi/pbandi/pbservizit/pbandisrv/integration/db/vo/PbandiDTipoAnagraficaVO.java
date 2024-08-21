/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/


package it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo;

import java.math.*;
import java.sql.Date;
import it.csi.pbandi.pbservizit.pbandisrv.integration.util.DataFilter;



public class PbandiDTipoAnagraficaVO extends GenericVO {

  	
  	private String descBreveTipoAnagrafica;
  	
  	private BigDecimal idTipoAnagrafica;
  	
  	private Date dtFineValidita;
  	
  	private String descTipoAnagrafica;
  	
  	private Date dtInizioValidita;
  	
  	private BigDecimal idRuoloHelp;
  	
  	private BigDecimal idCategAnagrafica;
  	
	public BigDecimal getIdCategAnagrafica() {
		return idCategAnagrafica;
	}

	public void setIdCategAnagrafica(BigDecimal idCategAnagrafica) {
		this.idCategAnagrafica = idCategAnagrafica;
	}

	public PbandiDTipoAnagraficaVO() {}
  	
	public PbandiDTipoAnagraficaVO (BigDecimal idTipoAnagrafica) {
	
		this. idTipoAnagrafica =  idTipoAnagrafica;
	}
  	
	public PbandiDTipoAnagraficaVO (String descBreveTipoAnagrafica, BigDecimal idTipoAnagrafica, Date dtFineValidita, String descTipoAnagrafica, Date dtInizioValidita,BigDecimal idRuoloHelp, BigDecimal idCategAnagrafica) {
	
		this. descBreveTipoAnagrafica =  descBreveTipoAnagrafica;
		this. idTipoAnagrafica =  idTipoAnagrafica;
		this. dtFineValidita =  dtFineValidita;
		this. descTipoAnagrafica =  descTipoAnagrafica;
		this. dtInizioValidita =  dtInizioValidita;
		this. idRuoloHelp = idRuoloHelp;
		this. idCategAnagrafica = idCategAnagrafica;
	}
  	
  	
	public String getDescBreveTipoAnagrafica() {
		return descBreveTipoAnagrafica;
	}
	
	public void setDescBreveTipoAnagrafica(String descBreveTipoAnagrafica) {
		this.descBreveTipoAnagrafica = descBreveTipoAnagrafica;
	}
	
	public BigDecimal getIdTipoAnagrafica() {
		return idTipoAnagrafica;
	}
	
	public void setIdTipoAnagrafica(BigDecimal idTipoAnagrafica) {
		this.idTipoAnagrafica = idTipoAnagrafica;
	}
	
	public Date getDtFineValidita() {
		return dtFineValidita;
	}
	
	public void setDtFineValidita(Date dtFineValidita) {
		this.dtFineValidita = dtFineValidita;
	}
	
	public String getDescTipoAnagrafica() {
		return descTipoAnagrafica;
	}
	
	public void setDescTipoAnagrafica(String descTipoAnagrafica) {
		this.descTipoAnagrafica = descTipoAnagrafica;
	}
	
	public Date getDtInizioValidita() {
		return dtInizioValidita;
	}
	
	public void setDtInizioValidita(Date dtInizioValidita) {
		this.dtInizioValidita = dtInizioValidita;
	}
	public BigDecimal getIdRuoloHelp() {
		return idRuoloHelp;
	}

	public void setIdRuoloHelp(BigDecimal idRuoloHelp) {
		this.idRuoloHelp = idRuoloHelp;
	}
	
	public boolean isValid() {
		boolean isValid = false;
                if (isPKValid() && descBreveTipoAnagrafica != null && descTipoAnagrafica != null && dtInizioValidita != null) {
   			isValid = true;
   		}
   		return isValid;
	}
	public boolean isPKValid() {
		boolean isPkValid = false;
		if (idTipoAnagrafica != null ) {
   			isPkValid = true;
   		}

   		return isPkValid;
	}
	
	public String toString() {
		
	    String temp = "";
	    StringBuffer sb = new StringBuffer();
	    sb.append("\t\n" + this.getClass().getName() + "\t\n");
	    
	    temp = DataFilter.removeNull( descBreveTipoAnagrafica);
	    if (!DataFilter.isEmpty(temp)) sb.append(" descBreveTipoAnagrafica: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idTipoAnagrafica);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idTipoAnagrafica: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtFineValidita);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtFineValidita: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( descTipoAnagrafica);
	    if (!DataFilter.isEmpty(temp)) sb.append(" descTipoAnagrafica: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtInizioValidita);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtInizioValidita: " + temp + "\t\n");
	   
	    temp = DataFilter.removeNull( idRuoloHelp);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idRuoloHelp: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idCategAnagrafica);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idCategAnagrafica: " + temp + "\t\n");
	    return sb.toString();
	}
	
	public java.util.List<String> getPK() {
		java.util.List<String> pk=new java.util.ArrayList<String>();
		
			pk.add("idTipoAnagrafica");
		
	    return pk;
	}


	
}
