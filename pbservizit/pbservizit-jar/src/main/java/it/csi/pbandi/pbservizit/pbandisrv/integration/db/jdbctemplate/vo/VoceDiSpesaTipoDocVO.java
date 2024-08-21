/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo;

import it.csi.pbandi.pbservizit.pbandisrv.integration.util.DataFilter;

import java.math.BigDecimal;

public class VoceDiSpesaTipoDocVO extends GenericVO {
	
	private BigDecimal idBando;
	private BigDecimal idTipoDocumentoSpesa;
	private BigDecimal idVoceDiSpesa;
	private BigDecimal idVoceDiSpesaPadre;
	private String descVoceDiSpesa;
	private String descVoceDiSpesaPadre;
	
	public String toString() {
		
	    String temp = "";
	    StringBuffer sb = new StringBuffer();
	    sb.append("\t\n" + this.getClass().getName() + "\t\n");
	    
	    temp = DataFilter.removeNull( idBando);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idBando: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idTipoDocumentoSpesa);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idTipoDocumentoSpesa: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idVoceDiSpesa);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idVoceDiSpesa: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( descVoceDiSpesa);
	    if (!DataFilter.isEmpty(temp)) sb.append(" descVoceDiSpesa: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idVoceDiSpesaPadre);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idVoceDiSpesaPadre: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( descVoceDiSpesaPadre);
	    if (!DataFilter.isEmpty(temp)) sb.append(" descVoceDiSpesaPadre: " + temp + "\t\n");
	    
	    return sb.toString();
	}
	
	public BigDecimal getIdBando() {
		return idBando;
	}
	public void setIdBando(BigDecimal idBando) {
		this.idBando = idBando;
	}
	public BigDecimal getIdTipoDocumentoSpesa() {
		return idTipoDocumentoSpesa;
	}
	public void setIdTipoDocumentoSpesa(BigDecimal idTipoDocumentoSpesa) {
		this.idTipoDocumentoSpesa = idTipoDocumentoSpesa;
	}
	public BigDecimal getIdVoceDiSpesa() {
		return idVoceDiSpesa;
	}
	public void setIdVoceDiSpesa(BigDecimal idVoceDiSpesa) {
		this.idVoceDiSpesa = idVoceDiSpesa;
	}
	public BigDecimal getIdVoceDiSpesaPadre() {
		return idVoceDiSpesaPadre;
	}
	public void setIdVoceDiSpesaPadre(BigDecimal idVoceDiSpesaPadre) {
		this.idVoceDiSpesaPadre = idVoceDiSpesaPadre;
	}
	public String getDescVoceDiSpesa() {
		return descVoceDiSpesa;
	}
	public void setDescVoceDiSpesa(String descVoceDiSpesa) {
		this.descVoceDiSpesa = descVoceDiSpesa;
	}
	public String getDescVoceDiSpesaPadre() {
		return descVoceDiSpesaPadre;
	}
	public void setDescVoceDiSpesaPadre(String descVoceDiSpesaPadre) {
		this.descVoceDiSpesaPadre = descVoceDiSpesaPadre;
	}
}
