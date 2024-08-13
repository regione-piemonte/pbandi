/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/


package it.csi.pbandi.pbweb.pbandisrv.integration.db.vo;

import java.math.*;
import java.sql.Date;
import it.csi.pbandi.pbweb.pbandisrv.integration.util.DataFilter;



public class PbandiTTracciamentoEntitaVO extends GenericVO {

  	
  	private String valoreSuccessivo;
  	
  	private BigDecimal idAttributo;
  	
  	private String descAttivita;
  	
  	private BigDecimal idTracciamentoEntita;
  	
  	private String valorePrecedente;
  	
  	private String target;
  	
  	private BigDecimal idEntita;
  	
  	private BigDecimal idTracciamento;
  	
	public PbandiTTracciamentoEntitaVO() {}
  	
	public PbandiTTracciamentoEntitaVO (BigDecimal idTracciamentoEntita) {
	
		this. idTracciamentoEntita =  idTracciamentoEntita;
	}
  	
	public PbandiTTracciamentoEntitaVO (String valoreSuccessivo, BigDecimal idAttributo, String descAttivita, BigDecimal idTracciamentoEntita, String valorePrecedente, String target, BigDecimal idEntita, BigDecimal idTracciamento) {
	
		this. valoreSuccessivo =  valoreSuccessivo;
		this. idAttributo =  idAttributo;
		this. descAttivita =  descAttivita;
		this. idTracciamentoEntita =  idTracciamentoEntita;
		this. valorePrecedente =  valorePrecedente;
		this. target =  target;
		this. idEntita =  idEntita;
		this. idTracciamento =  idTracciamento;
	}
  	
  	
	public String getValoreSuccessivo() {
		return valoreSuccessivo;
	}
	
	public void setValoreSuccessivo(String valoreSuccessivo) {
		this.valoreSuccessivo = valoreSuccessivo;
	}
	
	public BigDecimal getIdAttributo() {
		return idAttributo;
	}
	
	public void setIdAttributo(BigDecimal idAttributo) {
		this.idAttributo = idAttributo;
	}
	
	public String getDescAttivita() {
		return descAttivita;
	}
	
	public void setDescAttivita(String descAttivita) {
		this.descAttivita = descAttivita;
	}
	
	public BigDecimal getIdTracciamentoEntita() {
		return idTracciamentoEntita;
	}
	
	public void setIdTracciamentoEntita(BigDecimal idTracciamentoEntita) {
		this.idTracciamentoEntita = idTracciamentoEntita;
	}
	
	public String getValorePrecedente() {
		return valorePrecedente;
	}
	
	public void setValorePrecedente(String valorePrecedente) {
		this.valorePrecedente = valorePrecedente;
	}
	
	public String getTarget() {
		return target;
	}
	
	public void setTarget(String target) {
		this.target = target;
	}
	
	public BigDecimal getIdEntita() {
		return idEntita;
	}
	
	public void setIdEntita(BigDecimal idEntita) {
		this.idEntita = idEntita;
	}
	
	public BigDecimal getIdTracciamento() {
		return idTracciamento;
	}
	
	public void setIdTracciamento(BigDecimal idTracciamento) {
		this.idTracciamento = idTracciamento;
	}
	
	public boolean isValid() {
		boolean isValid = false;
                if (isPKValid() && descAttivita != null && target != null && idEntita != null && idTracciamento != null) {
   			isValid = true;
   		}
   		return isValid;
	}
	public boolean isPKValid() {
		boolean isPkValid = false;
		if (idTracciamentoEntita != null ) {
   			isPkValid = true;
   		}

   		return isPkValid;
	}
	
	public String toString() {
		
	    String temp = "";
	    StringBuffer sb = new StringBuffer();
	    sb.append("\t\n" + this.getClass().getName() + "\t\n");
	    
	    temp = DataFilter.removeNull( valoreSuccessivo);
	    if (!DataFilter.isEmpty(temp)) sb.append(" valoreSuccessivo: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idAttributo);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idAttributo: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( descAttivita);
	    if (!DataFilter.isEmpty(temp)) sb.append(" descAttivita: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idTracciamentoEntita);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idTracciamentoEntita: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( valorePrecedente);
	    if (!DataFilter.isEmpty(temp)) sb.append(" valorePrecedente: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( target);
	    if (!DataFilter.isEmpty(temp)) sb.append(" target: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idEntita);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idEntita: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idTracciamento);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idTracciamento: " + temp + "\t\n");
	    
	    return sb.toString();
	}
	
	public java.util.List<String> getPK() {
		java.util.List<String> pk=new java.util.ArrayList<String>();
		
			pk.add("idTracciamentoEntita");
		
	    return pk;
	}
	
	
}
