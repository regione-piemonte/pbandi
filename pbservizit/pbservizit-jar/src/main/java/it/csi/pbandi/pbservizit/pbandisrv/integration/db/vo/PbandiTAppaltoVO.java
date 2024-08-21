/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/


package it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo;

import java.math.*;
import java.sql.Date;
import it.csi.pbandi.pbservizit.pbandisrv.integration.util.DataFilter;



public class PbandiTAppaltoVO extends GenericVO {

  	
  	private String interventoPisu;
  	
  	private Date dtFirmaContratto;
  	
  	private BigDecimal bilancioPreventivo;
  	
  	private Date dtWebStazAppaltante;
  	
  	private BigDecimal idUtenteIns;
  	
  	private Date dtGuri;
  	
  	private Date dtInserimento;
  	
  	private Date dtGuue;
  	
  	private Date dtWebOsservatorio;
  	
  	private String impresaAppaltatrice;
  	
  	private BigDecimal idTipologiaAppalto;
  	
  	private String oggettoAppalto;
  	
  	private Date dtInizioPrevista;
  	
  	private Date dtConsegnaLavori;
  	
  	private BigDecimal idUtenteAgg;
  	
  	private BigDecimal importoContratto;
  	
  	private Date dtQuotNazionali;
  	
  	private BigDecimal idAppalto;
  	
  	private BigDecimal idProceduraAggiudicaz;
  	
  	private Date dtAggiornamento;
  	
  	private BigDecimal idTipoAffidamento;
  	
  	private BigDecimal idTipoPercettore;
  	
  	private String sopraSoglia;
  	
  	private BigDecimal impRibassoAsta;
  	
  	private BigDecimal percRibassoAsta;
  	
  	private BigDecimal impRendicontabile;
  	
  	private BigDecimal idStatoAffidamento;
  	
  	private BigDecimal idNorma;
  	
	public BigDecimal getIdNorma() {
		return idNorma;
	}

	public void setIdNorma(BigDecimal idNorma) {
		this.idNorma = idNorma;
	}

	public BigDecimal getIdStatoAffidamento() {
		return idStatoAffidamento;
	}

	public void setIdStatoAffidamento(BigDecimal idStatoAffidamento) {
		this.idStatoAffidamento = idStatoAffidamento;
	}

	public PbandiTAppaltoVO() {}
  	
	public PbandiTAppaltoVO (BigDecimal idAppalto) {
	
		this. idAppalto =  idAppalto;
	}
  	
	public PbandiTAppaltoVO (String interventoPisu, Date dtFirmaContratto, BigDecimal bilancioPreventivo, Date dtWebStazAppaltante, BigDecimal idUtenteIns, Date dtGuri, Date dtInserimento, Date dtGuue, Date dtWebOsservatorio, String impresaAppaltatrice, BigDecimal idTipologiaAppalto, String oggettoAppalto, Date dtInizioPrevista, Date dtConsegnaLavori, BigDecimal idUtenteAgg, BigDecimal importoContratto, Date dtQuotNazionali, BigDecimal idAppalto, BigDecimal idProceduraAggiudicaz, 
			Date dtAggiornamento, BigDecimal idTipoPercettore, BigDecimal idTipoAffidamento,
			String sopraSoglia, BigDecimal impRibassoAsta, BigDecimal percRibassoAsta, BigDecimal impRendicontabile) {
	
		this. interventoPisu =  interventoPisu;
		this. dtFirmaContratto =  dtFirmaContratto;
		this. bilancioPreventivo =  bilancioPreventivo;
		this. dtWebStazAppaltante =  dtWebStazAppaltante;
		this. idUtenteIns =  idUtenteIns;
		this. dtGuri =  dtGuri;
		this. dtInserimento =  dtInserimento;
		this. dtGuue =  dtGuue;
		this. dtWebOsservatorio =  dtWebOsservatorio;
		this. impresaAppaltatrice =  impresaAppaltatrice;
		this. idTipologiaAppalto =  idTipologiaAppalto;
		this. oggettoAppalto =  oggettoAppalto;
		this. dtInizioPrevista =  dtInizioPrevista;
		this. dtConsegnaLavori =  dtConsegnaLavori;
		this. idUtenteAgg =  idUtenteAgg;
		this. importoContratto =  importoContratto;
		this. dtQuotNazionali =  dtQuotNazionali;
		this. idAppalto =  idAppalto;
		this. idProceduraAggiudicaz =  idProceduraAggiudicaz;
		this. dtAggiornamento =  dtAggiornamento;
		this. idTipoAffidamento = idTipoAffidamento;
		this. idTipoPercettore = idTipoPercettore;
		this. sopraSoglia = sopraSoglia;
		this. impRibassoAsta = impRibassoAsta;   
		this. percRibassoAsta = percRibassoAsta;                   
		this. impRendicontabile = impRendicontabile;
	}
  	
  	
	public String getInterventoPisu() {
		return interventoPisu;
	}
	
	public void setInterventoPisu(String interventoPisu) {
		this.interventoPisu = interventoPisu;
	}
	
	public Date getDtFirmaContratto() {
		return dtFirmaContratto;
	}
	
	public void setDtFirmaContratto(Date dtFirmaContratto) {
		this.dtFirmaContratto = dtFirmaContratto;
	}
	
	public BigDecimal getBilancioPreventivo() {
		return bilancioPreventivo;
	}
	
	public void setBilancioPreventivo(BigDecimal bilancioPreventivo) {
		this.bilancioPreventivo = bilancioPreventivo;
	}
	
	public Date getDtWebStazAppaltante() {
		return dtWebStazAppaltante;
	}
	
	public void setDtWebStazAppaltante(Date dtWebStazAppaltante) {
		this.dtWebStazAppaltante = dtWebStazAppaltante;
	}
	
	public BigDecimal getIdUtenteIns() {
		return idUtenteIns;
	}
	
	public void setIdUtenteIns(BigDecimal idUtenteIns) {
		this.idUtenteIns = idUtenteIns;
	}
	
	public Date getDtGuri() {
		return dtGuri;
	}
	
	public void setDtGuri(Date dtGuri) {
		this.dtGuri = dtGuri;
	}
	
	public Date getDtInserimento() {
		return dtInserimento;
	}
	
	public void setDtInserimento(Date dtInserimento) {
		this.dtInserimento = dtInserimento;
	}
	
	public Date getDtGuue() {
		return dtGuue;
	}
	
	public void setDtGuue(Date dtGuue) {
		this.dtGuue = dtGuue;
	}
	
	public Date getDtWebOsservatorio() {
		return dtWebOsservatorio;
	}
	
	public void setDtWebOsservatorio(Date dtWebOsservatorio) {
		this.dtWebOsservatorio = dtWebOsservatorio;
	}
	
	public String getImpresaAppaltatrice() {
		return impresaAppaltatrice;
	}
	
	public void setImpresaAppaltatrice(String impresaAppaltatrice) {
		this.impresaAppaltatrice = impresaAppaltatrice;
	}
	
	public BigDecimal getIdTipologiaAppalto() {
		return idTipologiaAppalto;
	}
	
	public void setIdTipologiaAppalto(BigDecimal idTipologiaAppalto) {
		this.idTipologiaAppalto = idTipologiaAppalto;
	}
	
	public String getOggettoAppalto() {
		return oggettoAppalto;
	}
	
	public void setOggettoAppalto(String oggettoAppalto) {
		this.oggettoAppalto = oggettoAppalto;
	}
	
	public Date getDtInizioPrevista() {
		return dtInizioPrevista;
	}
	
	public void setDtInizioPrevista(Date dtInizioPrevista) {
		this.dtInizioPrevista = dtInizioPrevista;
	}
	
	public Date getDtConsegnaLavori() {
		return dtConsegnaLavori;
	}
	
	public void setDtConsegnaLavori(Date dtConsegnaLavori) {
		this.dtConsegnaLavori = dtConsegnaLavori;
	}
	
	public BigDecimal getIdUtenteAgg() {
		return idUtenteAgg;
	}
	
	public void setIdUtenteAgg(BigDecimal idUtenteAgg) {
		this.idUtenteAgg = idUtenteAgg;
	}
	
	public BigDecimal getImportoContratto() {
		return importoContratto;
	}
	
	public void setImportoContratto(BigDecimal importoContratto) {
		this.importoContratto = importoContratto;
	}
	
	public Date getDtQuotNazionali() {
		return dtQuotNazionali;
	}
	
	public void setDtQuotNazionali(Date dtQuotNazionali) {
		this.dtQuotNazionali = dtQuotNazionali;
	}
	
	public BigDecimal getIdAppalto() {
		return idAppalto;
	}
	
	public void setIdAppalto(BigDecimal idAppalto) {
		this.idAppalto = idAppalto;
	}
	
	public BigDecimal getIdProceduraAggiudicaz() {
		return idProceduraAggiudicaz;
	}
	
	public void setIdProceduraAggiudicaz(BigDecimal idProceduraAggiudicaz) {
		this.idProceduraAggiudicaz = idProceduraAggiudicaz;
	}
	
	public Date getDtAggiornamento() {
		return dtAggiornamento;
	}
	
	public void setDtAggiornamento(Date dtAggiornamento) {
		this.dtAggiornamento = dtAggiornamento;
	}
	
	public boolean isValid() {
		boolean isValid = false;
                if (isPKValid() && dtFirmaContratto != null && idUtenteIns != null && dtInserimento != null && idTipologiaAppalto != null && oggettoAppalto != null && importoContratto != null && idProceduraAggiudicaz != null) {
   			isValid = true;
   		}
   		return isValid;
	}
	public boolean isPKValid() {
		boolean isPkValid = false;
		if (idAppalto != null ) {
   			isPkValid = true;
   		}

   		return isPkValid;
	}
	
	public String toString() {
		
	    String temp = "";
	    StringBuffer sb = new StringBuffer();
	    sb.append("\t\n" + this.getClass().getName() + "\t\n");
	    
	    temp = DataFilter.removeNull( interventoPisu);
	    if (!DataFilter.isEmpty(temp)) sb.append(" interventoPisu: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtFirmaContratto);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtFirmaContratto: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( bilancioPreventivo);
	    if (!DataFilter.isEmpty(temp)) sb.append(" bilancioPreventivo: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtWebStazAppaltante);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtWebStazAppaltante: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idUtenteIns);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idUtenteIns: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtGuri);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtGuri: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtInserimento);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtInserimento: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtGuue);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtGuue: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtWebOsservatorio);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtWebOsservatorio: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( impresaAppaltatrice);
	    if (!DataFilter.isEmpty(temp)) sb.append(" impresaAppaltatrice: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idTipologiaAppalto);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idTipologiaAppalto: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( oggettoAppalto);
	    if (!DataFilter.isEmpty(temp)) sb.append(" oggettoAppalto: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtInizioPrevista);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtInizioPrevista: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtConsegnaLavori);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtConsegnaLavori: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idUtenteAgg);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idUtenteAgg: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( importoContratto);
	    if (!DataFilter.isEmpty(temp)) sb.append(" importoContratto: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtQuotNazionali);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtQuotNazionali: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idAppalto);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idAppalto: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idProceduraAggiudicaz);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idProceduraAggiudicaz: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtAggiornamento);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtAggiornamento: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idTipoAffidamento);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idTipoAffidamento: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idTipoPercettore);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idTipoPercettore: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( sopraSoglia);
	    if (!DataFilter.isEmpty(temp)) sb.append(" sopraSoglia: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( impRibassoAsta);
	    if (!DataFilter.isEmpty(temp)) sb.append(" impRibassoAsta: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( percRibassoAsta);
	    if (!DataFilter.isEmpty(temp)) sb.append(" percRibassoAsta: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( impRendicontabile);
	    if (!DataFilter.isEmpty(temp)) sb.append(" impRendicontabile: " + temp + "\t\n");
	    
	    return sb.toString();
	}
	
	
	public BigDecimal getIdTipoAffidamento() {
		return idTipoAffidamento;
	}

	public void setIdTipoAffidamento(BigDecimal idTipoAffidamento) {
		this.idTipoAffidamento = idTipoAffidamento;
	}

	public BigDecimal getIdTipoPercettore() {
		return idTipoPercettore;
	}

	public void setIdTipoPercettore(BigDecimal idTipoPercettore) {
		this.idTipoPercettore = idTipoPercettore;
	}
	
	public String getSopraSoglia() {
		return sopraSoglia;
	}

	public void setSopraSoglia(String sopraSoglia) {
		this.sopraSoglia = sopraSoglia;
	}

	public BigDecimal getImpRibassoAsta() {
		return impRibassoAsta;
	}

	public void setImpRibassoAsta(BigDecimal impRibassoAsta) {
		this.impRibassoAsta = impRibassoAsta;
	}

	public BigDecimal getPercRibassoAsta() {
		return percRibassoAsta;
	}

	public void setPercRibassoAsta(BigDecimal percRibassoAsta) {
		this.percRibassoAsta = percRibassoAsta;
	}

	public BigDecimal getImpRendicontabile() {
		return impRendicontabile;
	}

	public void setImpRendicontabile(BigDecimal impRendicontabile) {
		this.impRendicontabile = impRendicontabile;
	}

	public java.util.List getPK() {
		java.util.List pk=new java.util.ArrayList();
		
			pk.add("idAppalto");
		
	    return pk;
	}
	
	
}
