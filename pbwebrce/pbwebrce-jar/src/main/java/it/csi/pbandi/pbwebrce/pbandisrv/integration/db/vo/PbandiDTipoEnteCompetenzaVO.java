/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/


package it.csi.pbandi.pbwebrce.pbandisrv.integration.db.vo;

import java.math.*;
import java.sql.Date;
import it.csi.pbandi.pbwebrce.pbandisrv.integration.util.DataFilter;



public class PbandiDTipoEnteCompetenzaVO extends GenericVO {

  	
  	private BigDecimal codIgrueT51;
  	
  	private Date dtFineValidita;
  	
  	private String descBreveTipoEnteCompetenz;
  	
  	private String descTipoEnteCompetenza;
  	
  	private Date dtInizioValidita;
  	
  	private BigDecimal idTipoEnteCompetenza;
  	
	public PbandiDTipoEnteCompetenzaVO() {}
  	
	public PbandiDTipoEnteCompetenzaVO (BigDecimal idTipoEnteCompetenza) {
	
		this. idTipoEnteCompetenza =  idTipoEnteCompetenza;
	}
  	
	public PbandiDTipoEnteCompetenzaVO (BigDecimal codIgrueT51, Date dtFineValidita, String descBreveTipoEnteCompetenz, String descTipoEnteCompetenza, Date dtInizioValidita, BigDecimal idTipoEnteCompetenza) {
	
		this. codIgrueT51 =  codIgrueT51;
		this. dtFineValidita =  dtFineValidita;
		this. descBreveTipoEnteCompetenz =  descBreveTipoEnteCompetenz;
		this. descTipoEnteCompetenza =  descTipoEnteCompetenza;
		this. dtInizioValidita =  dtInizioValidita;
		this. idTipoEnteCompetenza =  idTipoEnteCompetenza;
	}
  	
  	
	public BigDecimal getCodIgrueT51() {
		return codIgrueT51;
	}
	
	public void setCodIgrueT51(BigDecimal codIgrueT51) {
		this.codIgrueT51 = codIgrueT51;
	}
	
	public Date getDtFineValidita() {
		return dtFineValidita;
	}
	
	public void setDtFineValidita(Date dtFineValidita) {
		this.dtFineValidita = dtFineValidita;
	}
	
	public String getDescBreveTipoEnteCompetenz() {
		return descBreveTipoEnteCompetenz;
	}
	
	public void setDescBreveTipoEnteCompetenz(String descBreveTipoEnteCompetenz) {
		this.descBreveTipoEnteCompetenz = descBreveTipoEnteCompetenz;
	}
	
	public String getDescTipoEnteCompetenza() {
		return descTipoEnteCompetenza;
	}
	
	public void setDescTipoEnteCompetenza(String descTipoEnteCompetenza) {
		this.descTipoEnteCompetenza = descTipoEnteCompetenza;
	}
	
	public Date getDtInizioValidita() {
		return dtInizioValidita;
	}
	
	public void setDtInizioValidita(Date dtInizioValidita) {
		this.dtInizioValidita = dtInizioValidita;
	}
	
	public BigDecimal getIdTipoEnteCompetenza() {
		return idTipoEnteCompetenza;
	}
	
	public void setIdTipoEnteCompetenza(BigDecimal idTipoEnteCompetenza) {
		this.idTipoEnteCompetenza = idTipoEnteCompetenza;
	}
	
	public boolean isValid() {
		boolean isValid = false;
                if (isPKValid() && descBreveTipoEnteCompetenz != null && descTipoEnteCompetenza != null && dtInizioValidita != null) {
   			isValid = true;
   		}
   		return isValid;
	}
	public boolean isPKValid() {
		boolean isPkValid = false;
		if (idTipoEnteCompetenza != null ) {
   			isPkValid = true;
   		}

   		return isPkValid;
	}
	
	public String toString() {
		
	    String temp = "";
	    StringBuffer sb = new StringBuffer();
	    sb.append("\t\n" + this.getClass().getName() + "\t\n");
	    
	    temp = DataFilter.removeNull( codIgrueT51);
	    if (!DataFilter.isEmpty(temp)) sb.append(" codIgrueT51: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtFineValidita);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtFineValidita: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( descBreveTipoEnteCompetenz);
	    if (!DataFilter.isEmpty(temp)) sb.append(" descBreveTipoEnteCompetenz: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( descTipoEnteCompetenza);
	    if (!DataFilter.isEmpty(temp)) sb.append(" descTipoEnteCompetenza: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtInizioValidita);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtInizioValidita: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idTipoEnteCompetenza);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idTipoEnteCompetenza: " + temp + "\t\n");
	    
	    return sb.toString();
	}
	
	public java.util.List<String> getPK() {
		java.util.List<String> pk=new java.util.ArrayList<String>();
		
			pk.add("idTipoEnteCompetenza");
		
	    return pk;
	}
	
	
}
