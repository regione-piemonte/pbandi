/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/


package it.csi.pbandi.pbweb.pbandisrv.integration.db.vo;

import java.math.*;
import java.sql.Date;
import it.csi.pbandi.pbweb.pbandisrv.integration.util.DataFilter;



public class PbandiREnteGiuridicoSedeVO extends GenericVO {

  	
  	private Date dtFineValidita;
  	
  	private BigDecimal progrEnteGiuridicoSede;
  	
  	private BigDecimal idEnteGiuridico;
  	
  	private BigDecimal idUtenteAgg;
  	
  	private BigDecimal idTipoSede;
  	
  	private BigDecimal idUtenteIns;
  	
  	private BigDecimal idSede;
  	
	public PbandiREnteGiuridicoSedeVO() {}
  	
	public PbandiREnteGiuridicoSedeVO (BigDecimal progrEnteGiuridicoSede) {
	
		this. progrEnteGiuridicoSede =  progrEnteGiuridicoSede;
	}
  	
	public PbandiREnteGiuridicoSedeVO (Date dtFineValidita, BigDecimal progrEnteGiuridicoSede, BigDecimal idEnteGiuridico, BigDecimal idUtenteAgg, BigDecimal idTipoSede, BigDecimal idUtenteIns, BigDecimal idSede) {
	
		this. dtFineValidita =  dtFineValidita;
		this. progrEnteGiuridicoSede =  progrEnteGiuridicoSede;
		this. idEnteGiuridico =  idEnteGiuridico;
		this. idUtenteAgg =  idUtenteAgg;
		this. idTipoSede =  idTipoSede;
		this. idUtenteIns =  idUtenteIns;
		this. idSede =  idSede;
	}
  	
  	
	public Date getDtFineValidita() {
		return dtFineValidita;
	}
	
	public void setDtFineValidita(Date dtFineValidita) {
		this.dtFineValidita = dtFineValidita;
	}
	
	public BigDecimal getProgrEnteGiuridicoSede() {
		return progrEnteGiuridicoSede;
	}
	
	public void setProgrEnteGiuridicoSede(BigDecimal progrEnteGiuridicoSede) {
		this.progrEnteGiuridicoSede = progrEnteGiuridicoSede;
	}
	
	public BigDecimal getIdEnteGiuridico() {
		return idEnteGiuridico;
	}
	
	public void setIdEnteGiuridico(BigDecimal idEnteGiuridico) {
		this.idEnteGiuridico = idEnteGiuridico;
	}
	
	public BigDecimal getIdUtenteAgg() {
		return idUtenteAgg;
	}
	
	public void setIdUtenteAgg(BigDecimal idUtenteAgg) {
		this.idUtenteAgg = idUtenteAgg;
	}
	
	public BigDecimal getIdTipoSede() {
		return idTipoSede;
	}
	
	public void setIdTipoSede(BigDecimal idTipoSede) {
		this.idTipoSede = idTipoSede;
	}
	
	public BigDecimal getIdUtenteIns() {
		return idUtenteIns;
	}
	
	public void setIdUtenteIns(BigDecimal idUtenteIns) {
		this.idUtenteIns = idUtenteIns;
	}
	
	public BigDecimal getIdSede() {
		return idSede;
	}
	
	public void setIdSede(BigDecimal idSede) {
		this.idSede = idSede;
	}
	
	public boolean isValid() {
		boolean isValid = false;
                if (isPKValid() && idEnteGiuridico != null && idTipoSede != null && idUtenteIns != null && idSede != null) {
   			isValid = true;
   		}
   		return isValid;
	}
	public boolean isPKValid() {
		boolean isPkValid = false;
		if (progrEnteGiuridicoSede != null ) {
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
	    
	    temp = DataFilter.removeNull( progrEnteGiuridicoSede);
	    if (!DataFilter.isEmpty(temp)) sb.append(" progrEnteGiuridicoSede: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idEnteGiuridico);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idEnteGiuridico: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idUtenteAgg);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idUtenteAgg: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idTipoSede);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idTipoSede: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idUtenteIns);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idUtenteIns: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idSede);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idSede: " + temp + "\t\n");
	    
	    return sb.toString();
	}
	
	public java.util.List<String> getPK() {
		java.util.List<String> pk=new java.util.ArrayList<String>();
		
			pk.add("progrEnteGiuridicoSede");
		
	    return pk;
	}
	
	
}
