/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/


package it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo;

import java.math.*;
import java.sql.Date;
import it.csi.pbandi.pbservizit.pbandisrv.integration.util.DataFilter;



public class PbandiDMotivoScostamentoVO extends GenericVO {

  	
  	private BigDecimal codIgrueT37T49T53;
  	
  	private Date dtFineValidita;
  	
  	private String descMotivoScostamento;
  	
  	private BigDecimal idMotivoScostamento;
  	
  	private Date dtInizioValidita;
  	
	public PbandiDMotivoScostamentoVO() {}
  	
	public PbandiDMotivoScostamentoVO (BigDecimal idMotivoScostamento) {
	
		this. idMotivoScostamento =  idMotivoScostamento;
	}
  	
	public PbandiDMotivoScostamentoVO (BigDecimal codIgrueT37T49T53, Date dtFineValidita, String descMotivoScostamento, BigDecimal idMotivoScostamento, Date dtInizioValidita) {
	
		this. codIgrueT37T49T53 =  codIgrueT37T49T53;
		this. dtFineValidita =  dtFineValidita;
		this. descMotivoScostamento =  descMotivoScostamento;
		this. idMotivoScostamento =  idMotivoScostamento;
		this. dtInizioValidita =  dtInizioValidita;
	}
  	
  	
	public BigDecimal getCodIgrueT37T49T53() {
		return codIgrueT37T49T53;
	}
	
	public void setCodIgrueT37T49T53(BigDecimal codIgrueT37T49T53) {
		this.codIgrueT37T49T53 = codIgrueT37T49T53;
	}
	
	public Date getDtFineValidita() {
		return dtFineValidita;
	}
	
	public void setDtFineValidita(Date dtFineValidita) {
		this.dtFineValidita = dtFineValidita;
	}
	
	public String getDescMotivoScostamento() {
		return descMotivoScostamento;
	}
	
	public void setDescMotivoScostamento(String descMotivoScostamento) {
		this.descMotivoScostamento = descMotivoScostamento;
	}
	
	public BigDecimal getIdMotivoScostamento() {
		return idMotivoScostamento;
	}
	
	public void setIdMotivoScostamento(BigDecimal idMotivoScostamento) {
		this.idMotivoScostamento = idMotivoScostamento;
	}
	
	public Date getDtInizioValidita() {
		return dtInizioValidita;
	}
	
	public void setDtInizioValidita(Date dtInizioValidita) {
		this.dtInizioValidita = dtInizioValidita;
	}
	
	public boolean isValid() {
		boolean isValid = false;
                if (isPKValid() && codIgrueT37T49T53 != null && descMotivoScostamento != null && dtInizioValidita != null) {
   			isValid = true;
   		}
   		return isValid;
	}
	public boolean isPKValid() {
		boolean isPkValid = false;
		if (idMotivoScostamento != null ) {
   			isPkValid = true;
   		}

   		return isPkValid;
	}
	
	public String toString() {
		
	    String temp = "";
	    StringBuffer sb = new StringBuffer();
	    sb.append("\t\n" + this.getClass().getName() + "\t\n");
	    
	    temp = DataFilter.removeNull( codIgrueT37T49T53);
	    if (!DataFilter.isEmpty(temp)) sb.append(" codIgrueT37T49T53: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtFineValidita);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtFineValidita: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( descMotivoScostamento);
	    if (!DataFilter.isEmpty(temp)) sb.append(" descMotivoScostamento: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idMotivoScostamento);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idMotivoScostamento: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtInizioValidita);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtInizioValidita: " + temp + "\t\n");
	    
	    return sb.toString();
	}
	
	public java.util.List<String> getPK() {
		java.util.List<String> pk=new java.util.ArrayList<String>();
		
			pk.add("idMotivoScostamento");
		
	    return pk;
	}
	
	
}
