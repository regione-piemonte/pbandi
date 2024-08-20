/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebrce.pbandisrv.integration.db.vo;

import java.math.*;
import it.csi.pbandi.pbwebrce.pbandisrv.integration.util.DataFilter;



public class PbandiCTemplChecklistVO extends GenericVO {

  	
  	private String codControllo;
	private String descrRiga;
	private BigDecimal idAttributo;
	private BigDecimal idTipoModello;
	private String nomeCampoEdit;
	private BigDecimal progrOrdinamento;
  	
  	
	public PbandiCTemplChecklistVO() {}
  	
	public PbandiCTemplChecklistVO (BigDecimal idTipoModello,BigDecimal progrOrdinamento) {
	
		this. idTipoModello =  idTipoModello;
		this. progrOrdinamento =  progrOrdinamento;
	}
  	
	 
	public boolean isValid() {
		boolean isValid = false;
                if (isPKValid()) {
   			isValid = true;
   		}
   		return isValid;
	}
	public boolean isPKValid() {
		boolean isPkValid = false;
		if (idTipoModello != null && progrOrdinamento != null) {
   			isPkValid = true;
   		}

   		return isPkValid;
	}
	
	public String toString() {
		
	    String temp = "";
	    StringBuffer sb = new StringBuffer();
	    sb.append("\t\n" + this.getClass().getName() + "\t\n");
	    
	    temp = DataFilter.removeNull( codControllo);
	    if (!DataFilter.isEmpty(temp)) sb.append(" codControllo: " + temp + "\t\n");
		
	    temp = DataFilter.removeNull( descrRiga);
	    if (!DataFilter.isEmpty(temp)) sb.append(" descrRiga: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idAttributo);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idAttributo: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idTipoModello);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idTipoModello: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( nomeCampoEdit);
	    if (!DataFilter.isEmpty(temp)) sb.append(" nomeCampoEdit: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( progrOrdinamento);
	    if (!DataFilter.isEmpty(temp)) sb.append(" progrOrdinamento: " + temp + "\t\n");
	    
	    return sb.toString();
	}
	
	public String getCodControllo() {
		return codControllo;
	}

	public void setCodControllo(String codControllo) {
		this.codControllo = codControllo;
	}

	public String getDescrRiga() {
		return descrRiga;
	}

	public void setDescrRiga(String descrRiga) {
		this.descrRiga = descrRiga;
	}

	public BigDecimal getIdAttributo() {
		return idAttributo;
	}

	public void setIdAttributo(BigDecimal idAttributo) {
		this.idAttributo = idAttributo;
	}

	public BigDecimal getIdTipoModello() {
		return idTipoModello;
	}

	public void setIdTipoModello(BigDecimal idTipoModello) {
		this.idTipoModello = idTipoModello;
	}

	public String getNomeCampoEdit() {
		return nomeCampoEdit;
	}

	public void setNomeCampoEdit(String nomeCampoEdit) {
		this.nomeCampoEdit = nomeCampoEdit;
	}

	public BigDecimal getProgrOrdinamento() {
		return progrOrdinamento;
	}

	public void setProgrOrdinamento(BigDecimal progrOrdinamento) {
		this.progrOrdinamento = progrOrdinamento;
	}

	public java.util.List getPK() {
		java.util.List pk=new java.util.ArrayList();
		
			pk.add("idTipoModello");
			
			pk.add("progrOrdinamento");
	    return pk;
	}
	
	
}
