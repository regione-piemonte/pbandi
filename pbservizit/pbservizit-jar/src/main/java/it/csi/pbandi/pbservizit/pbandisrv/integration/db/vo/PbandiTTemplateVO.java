/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/


package it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo;

import java.math.*;
import java.sql.Date;
import java.util.List;

import it.csi.pbandi.pbservizit.pbandisrv.integration.util.DataFilter;



public class PbandiTTemplateVO extends GenericVO {

  	
  	private BigDecimal idMacroSezioneModulo;
  	
  	private String sezReportParamName;
  	
  	private BigDecimal idUtenteAgg;
  	
  	private Date dtInizioValidita;
  	
  	private String subReportParamName;
  	
  	private BigDecimal idTipoTemplate;
  	
  	private BigDecimal idTipoDocumentoIndex;
  	
  	private String subDsParamName;
  	
  	private Date dtFineValidita;
  	
  	private BigDecimal progrBandoLineaIntervento;
  	
  	private BigDecimal idUtenteIns;
  	
  	private String nomeTemplate;
  	
  	private BigDecimal idTemplate;
  	
  	private String sezDsParamName;
  	
  	private Date dtInserimento;
  	
  	private byte[] jasperblob;
  	
	public PbandiTTemplateVO() {}
  	
	public PbandiTTemplateVO (BigDecimal idTemplate) {
	
		this. idTemplate =  idTemplate;
	}
  	
	public PbandiTTemplateVO (BigDecimal idMacroSezioneModulo, String sezReportParamName, BigDecimal idUtenteAgg, Date dtInizioValidita, String subReportParamName, BigDecimal idTipoTemplate, BigDecimal idTipoDocumentoIndex, String uuidNodo, String subDsParamName, Date dtFineValidita, BigDecimal progrBandoLineaIntervento, BigDecimal idUtenteIns, String nomeTemplate, BigDecimal idTemplate, String sezDsParamName,
			Date dtInserimento) {
	
		this. idMacroSezioneModulo =  idMacroSezioneModulo;
		this. sezReportParamName =  sezReportParamName;
		this. idUtenteAgg =  idUtenteAgg;
		this. dtInizioValidita =  dtInizioValidita;
		this. subReportParamName =  subReportParamName;
		this. idTipoTemplate =  idTipoTemplate;
		this. idTipoDocumentoIndex =  idTipoDocumentoIndex;
		this. subDsParamName =  subDsParamName;
		this. dtFineValidita =  dtFineValidita;
		this. progrBandoLineaIntervento =  progrBandoLineaIntervento;
		this. idUtenteIns =  idUtenteIns;
		this. nomeTemplate =  nomeTemplate;
		this. idTemplate =  idTemplate;
		this. sezDsParamName =  sezDsParamName;
		this. dtInserimento = dtInserimento;
	}
  	
  	
	public BigDecimal getIdMacroSezioneModulo() {
		return idMacroSezioneModulo;
	}
	
	public void setIdMacroSezioneModulo(BigDecimal idMacroSezioneModulo) {
		this.idMacroSezioneModulo = idMacroSezioneModulo;
	}
	
	public String getSezReportParamName() {
		return sezReportParamName;
	}
	
	public void setSezReportParamName(String sezReportParamName) {
		this.sezReportParamName = sezReportParamName;
	}
	
	public BigDecimal getIdUtenteAgg() {
		return idUtenteAgg;
	}
	
	public void setIdUtenteAgg(BigDecimal idUtenteAgg) {
		this.idUtenteAgg = idUtenteAgg;
	}
	
	public Date getDtInizioValidita() {
		return dtInizioValidita;
	}
	

	public void setDtInserimento(Date dtInserimento) {
		this.dtInserimento = dtInserimento;
	}

	public Date getDtInserimento() {
		return dtInserimento;
	}

	public void setDtInizioValidita(Date dtInizioValidita) {
		this.dtInizioValidita = dtInizioValidita;
	}
	
	public String getSubReportParamName() {
		return subReportParamName;
	}
	
	public void setSubReportParamName(String subReportParamName) {
		this.subReportParamName = subReportParamName;
	}
	
	public BigDecimal getIdTipoTemplate() {
		return idTipoTemplate;
	}
	
	public void setIdTipoTemplate(BigDecimal idTipoTemplate) {
		this.idTipoTemplate = idTipoTemplate;
	}
	
	public BigDecimal getIdTipoDocumentoIndex() {
		return idTipoDocumentoIndex;
	}
	
	public void setIdTipoDocumentoIndex(BigDecimal idTipoDocumentoIndex) {
		this.idTipoDocumentoIndex = idTipoDocumentoIndex;
	}
	
	public String getSubDsParamName() {
		return subDsParamName;
	}
	
	public void setSubDsParamName(String subDsParamName) {
		this.subDsParamName = subDsParamName;
	}
	
	public Date getDtFineValidita() {
		return dtFineValidita;
	}
	
	public void setDtFineValidita(Date dtFineValidita) {
		this.dtFineValidita = dtFineValidita;
	}
	
	public BigDecimal getProgrBandoLineaIntervento() {
		return progrBandoLineaIntervento;
	}
	
	public void setProgrBandoLineaIntervento(BigDecimal progrBandoLineaIntervento) {
		this.progrBandoLineaIntervento = progrBandoLineaIntervento;
	}
	
	public BigDecimal getIdUtenteIns() {
		return idUtenteIns;
	}
	
	public void setIdUtenteIns(BigDecimal idUtenteIns) {
		this.idUtenteIns = idUtenteIns;
	}
	
	public String getNomeTemplate() {
		return nomeTemplate;
	}
	
	public void setNomeTemplate(String nomeTemplate) {
		this.nomeTemplate = nomeTemplate;
	}
	
	public BigDecimal getIdTemplate() {
		return idTemplate;
	}
	
	public void setIdTemplate(BigDecimal idTemplate) {
		this.idTemplate = idTemplate;
	}
	
	public String getSezDsParamName() {
		return sezDsParamName;
	}
	
	public void setSezDsParamName(String sezDsParamName) {
		this.sezDsParamName = sezDsParamName;
	}
	
	public boolean isValid() {
		boolean isValid = false;
                if (isPKValid() && dtInizioValidita != null && idTipoTemplate != null && idTipoDocumentoIndex != null  && progrBandoLineaIntervento != null && idUtenteIns != null && nomeTemplate != null &&  dtInserimento != null) {
   			isValid = true;
   		}
   		return isValid;
	}
	public boolean isPKValid() {
		boolean isPkValid = false;
		if (idTemplate != null ) {
   			isPkValid = true;
   		}

   		return isPkValid;
	}
	
	public String toString() {
		
	    String temp = "";
	    StringBuffer sb = new StringBuffer();
	    sb.append("\t\n" + this.getClass().getName() + "\t\n");
	    
	    temp = DataFilter.removeNull( idMacroSezioneModulo);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idMacroSezioneModulo: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( sezReportParamName);
	    if (!DataFilter.isEmpty(temp)) sb.append(" sezReportParamName: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idUtenteAgg);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idUtenteAgg: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtInizioValidita);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtInizioValidita: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( subReportParamName);
	    if (!DataFilter.isEmpty(temp)) sb.append(" subReportParamName: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idTipoTemplate);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idTipoTemplate: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idTipoDocumentoIndex);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idTipoDocumentoIndex: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( subDsParamName);
	    if (!DataFilter.isEmpty(temp)) sb.append(" subDsParamName: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtFineValidita);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtFineValidita: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( progrBandoLineaIntervento);
	    if (!DataFilter.isEmpty(temp)) sb.append(" progrBandoLineaIntervento: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idUtenteIns);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idUtenteIns: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( nomeTemplate);
	    if (!DataFilter.isEmpty(temp)) sb.append(" nomeTemplate: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idTemplate);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idTemplate: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( sezDsParamName);
	    if (!DataFilter.isEmpty(temp)) sb.append(" sezDsParamName: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtInserimento);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtInserimento: " + temp + "\t\n");
	    
	    return sb.toString();
	}

	@Override
	public List getPK() {
		java.util.List pk=new java.util.ArrayList();
		
		pk.add("idTemplate");
	
		return pk;
	}

	public void setJasperblob(byte[] jasperblob) {
		this.jasperblob = jasperblob;
	}

	public byte[] getJasperblob() {
		return jasperblob;
	}
	
	
	
}
