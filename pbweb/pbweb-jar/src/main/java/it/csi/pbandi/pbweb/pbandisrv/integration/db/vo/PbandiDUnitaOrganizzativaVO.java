/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/


package it.csi.pbandi.pbweb.pbandisrv.integration.db.vo;

import java.math.*;
import java.sql.Date;
import it.csi.pbandi.pbweb.pbandisrv.integration.util.DataFilter;



public class PbandiDUnitaOrganizzativaVO extends GenericVO {

  	
  	private BigDecimal idUnitaOrganizzativa;
  	
  	private String descUnitaOrganizzativa;
  	
  	private Date dtFineValidita;
  	
  	private BigDecimal idEnteCompetenza;
  	
  	private Date dtInizioValidita;
  	
  	private String codCipeUo;
  	
	public PbandiDUnitaOrganizzativaVO() {}
  	
	public PbandiDUnitaOrganizzativaVO (BigDecimal idUnitaOrganizzativa) {
	
		this. idUnitaOrganizzativa =  idUnitaOrganizzativa;
	}
  	
	public PbandiDUnitaOrganizzativaVO (BigDecimal idUnitaOrganizzativa, String descUnitaOrganizzativa, Date dtFineValidita, BigDecimal idEnteCompetenza, Date dtInizioValidita, String codCipeUo) {
	
		this. idUnitaOrganizzativa =  idUnitaOrganizzativa;
		this. descUnitaOrganizzativa =  descUnitaOrganizzativa;
		this. dtFineValidita =  dtFineValidita;
		this. idEnteCompetenza =  idEnteCompetenza;
		this. dtInizioValidita =  dtInizioValidita;
		this. codCipeUo =  codCipeUo;
	}
  	
  	
	public BigDecimal getIdUnitaOrganizzativa() {
		return idUnitaOrganizzativa;
	}
	
	public void setIdUnitaOrganizzativa(BigDecimal idUnitaOrganizzativa) {
		this.idUnitaOrganizzativa = idUnitaOrganizzativa;
	}
	
	public String getDescUnitaOrganizzativa() {
		return descUnitaOrganizzativa;
	}
	
	public void setDescUnitaOrganizzativa(String descUnitaOrganizzativa) {
		this.descUnitaOrganizzativa = descUnitaOrganizzativa;
	}
	
	public Date getDtFineValidita() {
		return dtFineValidita;
	}
	
	public void setDtFineValidita(Date dtFineValidita) {
		this.dtFineValidita = dtFineValidita;
	}
	
	public BigDecimal getIdEnteCompetenza() {
		return idEnteCompetenza;
	}
	
	public void setIdEnteCompetenza(BigDecimal idEnteCompetenza) {
		this.idEnteCompetenza = idEnteCompetenza;
	}
	
	public Date getDtInizioValidita() {
		return dtInizioValidita;
	}
	
	public void setDtInizioValidita(Date dtInizioValidita) {
		this.dtInizioValidita = dtInizioValidita;
	}
	
	public String getCodCipeUo() {
		return codCipeUo;
	}
	
	public void setCodCipeUo(String codCipeUo) {
		this.codCipeUo = codCipeUo;
	}
	
	public boolean isValid() {
		boolean isValid = false;
                if (isPKValid() && descUnitaOrganizzativa != null && dtInizioValidita != null && codCipeUo != null) {
   			isValid = true;
   		}
   		return isValid;
	}
	public boolean isPKValid() {
		boolean isPkValid = false;
		if (idUnitaOrganizzativa != null ) {
   			isPkValid = true;
   		}

   		return isPkValid;
	}
	
	public String toString() {
		
	    String temp = "";
	    StringBuffer sb = new StringBuffer();
	    sb.append("\t\n" + this.getClass().getName() + "\t\n");
	    
	    temp = DataFilter.removeNull( idUnitaOrganizzativa);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idUnitaOrganizzativa: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( descUnitaOrganizzativa);
	    if (!DataFilter.isEmpty(temp)) sb.append(" descUnitaOrganizzativa: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtFineValidita);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtFineValidita: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idEnteCompetenza);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idEnteCompetenza: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtInizioValidita);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtInizioValidita: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( codCipeUo);
	    if (!DataFilter.isEmpty(temp)) sb.append(" codCipeUo: " + temp + "\t\n");
	    
	    return sb.toString();
	}
	
	public java.util.List<String> getPK() {
		java.util.List<String> pk=new java.util.ArrayList<String>();
		
			pk.add("idUnitaOrganizzativa");
		
	    return pk;
	}
	
	
}
