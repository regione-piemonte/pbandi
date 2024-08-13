/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweb.pbandisrv.integration.db.vo;

import java.math.BigDecimal;
import java.util.List;

import it.csi.pbandi.pbweb.pbandisrv.integration.util.DataFilter;

public class PbandiVAtecoAmmessiVO extends GenericVO {
	
	private BigDecimal idBando;
	private BigDecimal idAteco;
	private String codAteco;
	private String codAtecoNorm;
	private String descAteco;
	private String codSettore;
	private String descSettore;
	
	public PbandiVAtecoAmmessiVO() {};
	
	@SuppressWarnings("unchecked")
	@Override
	public List getPK() {
		return null;
	}

	@Override
	public boolean isPKValid() {
		return false;
	}
	
	public String toString() {
		
	    String temp = "";
	    StringBuffer sb = new StringBuffer();
	    sb.append("\t\n" + this.getClass().getName() + "\t\n");
	    
	    temp = DataFilter.removeNull( idBando);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idBando: " + temp + "\t\n");
	    temp = DataFilter.removeNull( idAteco);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idAteco: " + temp + "\t\n");
	    temp = DataFilter.removeNull( codAteco);
	    if (!DataFilter.isEmpty(temp)) sb.append(" codAteco: " + temp + "\t\n");
	    temp = DataFilter.removeNull( codAtecoNorm);
	    if (!DataFilter.isEmpty(temp)) sb.append(" codAtecoNorm: " + temp + "\t\n");
	    temp = DataFilter.removeNull( descAteco);
	    if (!DataFilter.isEmpty(temp)) sb.append(" descAteco: " + temp + "\t\n");
	    temp = DataFilter.removeNull( codSettore);
	    if (!DataFilter.isEmpty(temp)) sb.append(" codSettore: " + temp + "\t\n");
	    temp = DataFilter.removeNull( descSettore);
	    if (!DataFilter.isEmpty(temp)) sb.append(" descSettore: " + temp + "\t\n");
	    
	    return sb.toString();
	}

	public BigDecimal getIdBando() {
		return idBando;
	}

	public void setIdBando(BigDecimal idBando) {
		this.idBando = idBando;
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

	public String getCodAtecoNorm() {
		return codAtecoNorm;
	}

	public void setCodAtecoNorm(String codAtecoNorm) {
		this.codAtecoNorm = codAtecoNorm;
	}

	public String getDescAteco() {
		return descAteco;
	}

	public void setDescAteco(String descAteco) {
		this.descAteco = descAteco;
	}

	public String getCodSettore() {
		return codSettore;
	}

	public void setCodSettore(String codSettore) {
		this.codSettore = codSettore;
	}

	public String getDescSettore() {
		return descSettore;
	}

	public void setDescSettore(String descSettore) {
		this.descSettore = descSettore;
	}

}
