/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/


package it.csi.pbandi.pbweb.pbandisrv.integration.db.vo;

import java.math.*;
import java.sql.Date;
import it.csi.pbandi.pbweb.pbandisrv.integration.util.DataFilter;



public class PbandiDMicroSezioneDinVO extends GenericVO {

  	
  	
  	private String nome;
  	private String valore;
  	private BigDecimal idLineaNormativa;
  	
	public PbandiDMicroSezioneDinVO() {}
  	
	public PbandiDMicroSezioneDinVO ( String nome,String valore, BigDecimal idLineaNormativa) {
	
		this.setNome(nome);
		this.setValore(valore);
		this.setIdLineaNormativa(idLineaNormativa);
	}
  	
	 
  
	
	public boolean isValid() {
   		return true;
	}
	public boolean isPKValid() {
	 
   		return true;
	}
	
	public String toString() {
		
	    String temp = "";
	    StringBuffer sb = new StringBuffer();
	    sb.append("\t\n" + this.getClass().getName() + "\t\n");
	    
	    temp = DataFilter.removeNull( nome);
	    if (!DataFilter.isEmpty(temp)) sb.append(" nome: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( valore);
	    if (!DataFilter.isEmpty(temp)) sb.append(" valore: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idLineaNormativa);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idLineaNormativa: " + temp + "\t\n");
	    
	    return sb.toString();
	}
	
	public java.util.List getPK() {
		java.util.List pk=new java.util.ArrayList();
		
		pk.add("nome");
		
	    return pk;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getValore() {
		return valore;
	}

	public void setValore(String valore) {
		this.valore = valore;
	}

	public BigDecimal getIdLineaNormativa() {
		return idLineaNormativa;
	}

	public void setIdLineaNormativa(BigDecimal idLineaNormativa) {
		this.idLineaNormativa = idLineaNormativa;
	}
	
	
}
