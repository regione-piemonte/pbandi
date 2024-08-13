/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/


package it.csi.pbandi.pbweb.pbandisrv.integration.db.vo;

import java.math.*;
import java.sql.Date;
import it.csi.pbandi.pbweb.pbandisrv.integration.util.DataFilter;



public class PbandiRBlTipoDocSezModVO extends GenericVO {

  	
  	private BigDecimal idTipoDocumentoIndex;
  	
  	private BigDecimal progrBandoLineaIntervento;
  	
  	private BigDecimal progrBlTipoDocSezMod;
  	
  	private BigDecimal numOrdinamentoMacroSezione;
  	
  	private String templateJrxml;
  	
  	private String reportJrxml;
  	
  	private BigDecimal idMacroSezioneModulo;
  	
	public PbandiRBlTipoDocSezModVO() {}
  	
	public PbandiRBlTipoDocSezModVO (BigDecimal progrBlTipoDocSezMod) {
	
		this. progrBlTipoDocSezMod =  progrBlTipoDocSezMod;
	}
  	
	public PbandiRBlTipoDocSezModVO (BigDecimal idTipoDocumentoIndex, BigDecimal progrBandoLineaIntervento, BigDecimal progrBlTipoDocSezMod, BigDecimal numOrdinamentoMacroSezione, String templateJrxml, String reportJrxml, BigDecimal idMacroSezioneModulo) {
	
		this. idTipoDocumentoIndex =  idTipoDocumentoIndex;
		this. progrBandoLineaIntervento =  progrBandoLineaIntervento;
		this. progrBlTipoDocSezMod =  progrBlTipoDocSezMod;
		this. numOrdinamentoMacroSezione =  numOrdinamentoMacroSezione;
		this. templateJrxml =  templateJrxml;
		this. reportJrxml =  reportJrxml;
		this. idMacroSezioneModulo =  idMacroSezioneModulo;
	}
  	
  	
	public BigDecimal getIdTipoDocumentoIndex() {
		return idTipoDocumentoIndex;
	}
	
	public void setIdTipoDocumentoIndex(BigDecimal idTipoDocumentoIndex) {
		this.idTipoDocumentoIndex = idTipoDocumentoIndex;
	}
	
	public BigDecimal getProgrBandoLineaIntervento() {
		return progrBandoLineaIntervento;
	}
	
	public void setProgrBandoLineaIntervento(BigDecimal progrBandoLineaIntervento) {
		this.progrBandoLineaIntervento = progrBandoLineaIntervento;
	}
	
	public BigDecimal getProgrBlTipoDocSezMod() {
		return progrBlTipoDocSezMod;
	}
	
	public void setProgrBlTipoDocSezMod(BigDecimal progrBlTipoDocSezMod) {
		this.progrBlTipoDocSezMod = progrBlTipoDocSezMod;
	}
	
	public BigDecimal getNumOrdinamentoMacroSezione() {
		return numOrdinamentoMacroSezione;
	}
	
	public void setNumOrdinamentoMacroSezione(BigDecimal numOrdinamentoMacroSezione) {
		this.numOrdinamentoMacroSezione = numOrdinamentoMacroSezione;
	}
	
	public String getTemplateJrxml() {
		return templateJrxml;
	}
	
	public void setTemplateJrxml(String templateJrxml) {
		this.templateJrxml = templateJrxml;
	}
	
	public String getReportJrxml() {
		return reportJrxml;
	}
	
	public void setReportJrxml(String reportJrxml) {
		this.reportJrxml = reportJrxml;
	}
	
	public BigDecimal getIdMacroSezioneModulo() {
		return idMacroSezioneModulo;
	}
	
	public void setIdMacroSezioneModulo(BigDecimal idMacroSezioneModulo) {
		this.idMacroSezioneModulo = idMacroSezioneModulo;
	}
	
	public boolean isValid() {
		boolean isValid = false;
                if (isPKValid() && idTipoDocumentoIndex != null && progrBandoLineaIntervento != null) {
   			isValid = true;
   		}
   		return isValid;
	}
	public boolean isPKValid() {
		boolean isPkValid = false;
		if (progrBlTipoDocSezMod != null ) {
   			isPkValid = true;
   		}

   		return isPkValid;
	}
	
	public String toString() {
		
	    String temp = "";
	    StringBuffer sb = new StringBuffer();
	    sb.append("\t\n" + this.getClass().getName() + "\t\n");
	    
	    temp = DataFilter.removeNull( idTipoDocumentoIndex);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idTipoDocumentoIndex: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( progrBandoLineaIntervento);
	    if (!DataFilter.isEmpty(temp)) sb.append(" progrBandoLineaIntervento: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( progrBlTipoDocSezMod);
	    if (!DataFilter.isEmpty(temp)) sb.append(" progrBlTipoDocSezMod: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( numOrdinamentoMacroSezione);
	    if (!DataFilter.isEmpty(temp)) sb.append(" numOrdinamentoMacroSezione: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( templateJrxml);
	    if (!DataFilter.isEmpty(temp)) sb.append(" templateJrxml: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( reportJrxml);
	    if (!DataFilter.isEmpty(temp)) sb.append(" reportJrxml: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idMacroSezioneModulo);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idMacroSezioneModulo: " + temp + "\t\n");
	    
	    return sb.toString();
	}
	
	public java.util.List getPK() {
		java.util.List pk=new java.util.ArrayList();
		
			pk.add("progrBlTipoDocSezMod");
		
	    return pk;
	}
	
	
}
