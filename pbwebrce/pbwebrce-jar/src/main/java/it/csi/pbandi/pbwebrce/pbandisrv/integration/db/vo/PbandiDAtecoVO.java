/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebrce.pbandisrv.integration.db.vo;

import java.math.BigDecimal;

import it.csi.pbandi.pbwebrce.pbandisrv.integration.util.DataFilter;

public class PbandiDAtecoVO extends GenericVO {
	
	private BigDecimal idAteco;
	private String codAteco;
	private String descAteco;
	private BigDecimal codLivello;
	private String codAtecoNorm;
	private String codSezione;
	
	public PbandiDAtecoVO() {};
	
	public boolean isPKValid() {
		boolean isPkValid = false;
		if (idAteco != null) {
   			isPkValid = true;
   		}
   		return isPkValid;
	}
	
	public boolean isValid() {
		boolean isValid = false;
                if (isPKValid()) {
   			isValid = true;
   		}
   		return isValid;
	}
	
	public java.util.List<String> getPK() {
		java.util.List<String> pk = new java.util.ArrayList<String>();
		pk.add("idAteco");
	    return pk;
	}
	
	public String toString() {
		
	    String temp = "";
	    StringBuffer sb = new StringBuffer();
	    sb.append("\t\n" + this.getClass().getName() + "\t\n");
	    
	    temp = DataFilter.removeNull( idAteco);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idAteco: " + temp + "\t\n");
	    temp = DataFilter.removeNull( codAteco);
	    if (!DataFilter.isEmpty(temp)) sb.append(" codAteco: " + temp + "\t\n");
	    temp = DataFilter.removeNull( descAteco);
	    if (!DataFilter.isEmpty(temp)) sb.append(" descAteco: " + temp + "\t\n");
	    temp = DataFilter.removeNull( codLivello);
	    if (!DataFilter.isEmpty(temp)) sb.append(" codLivello: " + temp + "\t\n");
	    temp = DataFilter.removeNull( codAtecoNorm);
	    if (!DataFilter.isEmpty(temp)) sb.append(" codAtecoNorm: " + temp + "\t\n");
	    temp = DataFilter.removeNull( codSezione);
	    if (!DataFilter.isEmpty(temp)) sb.append(" codSezione: " + temp + "\t\n");
	    
	    return sb.toString();
	}

	public BigDecimal getIdAteco() {
		return idAteco;
	}

	public void setIdAteco(BigDecimal idAteco) {
		this.idAteco = idAteco;
	}

	public String getCodAteco() {
		return codAteco;
	}

	public void setCodAteco(String codAteco) {
		this.codAteco = codAteco;
	}

	public String getDescAteco() {
		return descAteco;
	}

	public void setDescAteco(String descAteco) {
		this.descAteco = descAteco;
	}

	public BigDecimal getCodLivello() {
		return codLivello;
	}

	public void setCodLivello(BigDecimal codLivello) {
		this.codLivello = codLivello;
	}

	public String getCodAtecoNorm() {
		return codAtecoNorm;
	}

	public void setCodAtecoNorm(String codAtecoNorm) {
		this.codAtecoNorm = codAtecoNorm;
	}

	public String getCodSezione() {
		return codSezione;
	}

	public void setCodSezione(String codSezione) {
		this.codSezione = codSezione;
	}

}
