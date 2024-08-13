/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/


package it.csi.pbandi.pbweb.pbandisrv.integration.db.vo;

import java.math.*;
import java.sql.Date;
import it.csi.pbandi.pbweb.pbandisrv.integration.util.DataFilter;



public class PbandiTPropostaCertificazVO extends GenericVO {

  	
  	private BigDecimal idUtenteAgg;
  	
  	private BigDecimal idStatoPropostaCertif;
  	
  	private Date dtCutOffFideiussioni;
  	
  	private BigDecimal idPropostaPrec;
  	
  	private Date dtOraCreazione;
  	
  	private Date dtCutOffPagamenti;
  	
  	private Date dtCutOffErogazioni;
  	
  	private Date dtCutOffValidazioni;
  	
  	private BigDecimal idUtenteIns;
  	
  	private String descProposta;
  	
  	private BigDecimal idPropostaCertificaz;
  	
	public PbandiTPropostaCertificazVO() {}
  	
	public PbandiTPropostaCertificazVO (BigDecimal idPropostaCertificaz) {
	
		this. idPropostaCertificaz =  idPropostaCertificaz;
	}
  	
	public PbandiTPropostaCertificazVO (BigDecimal idUtenteAgg, BigDecimal idStatoPropostaCertif, Date dtCutOffFideiussioni, BigDecimal idPropostaPrec, Date dtOraCreazione, Date dtCutOffPagamenti, Date dtCutOffErogazioni, Date dtCutOffValidazioni, BigDecimal idUtenteIns, String descProposta, BigDecimal idPropostaCertificaz) {
	
		this. idUtenteAgg =  idUtenteAgg;
		this. idStatoPropostaCertif =  idStatoPropostaCertif;
		this. dtCutOffFideiussioni =  dtCutOffFideiussioni;
		this. idPropostaPrec =  idPropostaPrec;
		this. dtOraCreazione =  dtOraCreazione;
		this. dtCutOffPagamenti =  dtCutOffPagamenti;
		this. dtCutOffErogazioni =  dtCutOffErogazioni;
		this. dtCutOffValidazioni =  dtCutOffValidazioni;
		this. idUtenteIns =  idUtenteIns;
		this. descProposta =  descProposta;
		this. idPropostaCertificaz =  idPropostaCertificaz;
	}
  	
  	
	public BigDecimal getIdUtenteAgg() {
		return idUtenteAgg;
	}
	
	public void setIdUtenteAgg(BigDecimal idUtenteAgg) {
		this.idUtenteAgg = idUtenteAgg;
	}
	
	public BigDecimal getIdStatoPropostaCertif() {
		return idStatoPropostaCertif;
	}
	
	public void setIdStatoPropostaCertif(BigDecimal idStatoPropostaCertif) {
		this.idStatoPropostaCertif = idStatoPropostaCertif;
	}
	
	public Date getDtCutOffFideiussioni() {
		return dtCutOffFideiussioni;
	}
	
	public void setDtCutOffFideiussioni(Date dtCutOffFideiussioni) {
		this.dtCutOffFideiussioni = dtCutOffFideiussioni;
	}
	
	public BigDecimal getIdPropostaPrec() {
		return idPropostaPrec;
	}
	
	public void setIdPropostaPrec(BigDecimal idPropostaPrec) {
		this.idPropostaPrec = idPropostaPrec;
	}
	
	public Date getDtOraCreazione() {
		return dtOraCreazione;
	}
	
	public void setDtOraCreazione(Date dtOraCreazione) {
		this.dtOraCreazione = dtOraCreazione;
	}
	
	public Date getDtCutOffPagamenti() {
		return dtCutOffPagamenti;
	}
	
	public void setDtCutOffPagamenti(Date dtCutOffPagamenti) {
		this.dtCutOffPagamenti = dtCutOffPagamenti;
	}
	
	public Date getDtCutOffErogazioni() {
		return dtCutOffErogazioni;
	}
	
	public void setDtCutOffErogazioni(Date dtCutOffErogazioni) {
		this.dtCutOffErogazioni = dtCutOffErogazioni;
	}
	
	public Date getDtCutOffValidazioni() {
		return dtCutOffValidazioni;
	}
	
	public void setDtCutOffValidazioni(Date dtCutOffValidazioni) {
		this.dtCutOffValidazioni = dtCutOffValidazioni;
	}
	
	public BigDecimal getIdUtenteIns() {
		return idUtenteIns;
	}
	
	public void setIdUtenteIns(BigDecimal idUtenteIns) {
		this.idUtenteIns = idUtenteIns;
	}
	
	public String getDescProposta() {
		return descProposta;
	}
	
	public void setDescProposta(String descProposta) {
		this.descProposta = descProposta;
	}
	
	public BigDecimal getIdPropostaCertificaz() {
		return idPropostaCertificaz;
	}
	
	public void setIdPropostaCertificaz(BigDecimal idPropostaCertificaz) {
		this.idPropostaCertificaz = idPropostaCertificaz;
	}
	
	public boolean isValid() {
		boolean isValid = false;
                if (isPKValid() && idStatoPropostaCertif != null && dtCutOffFideiussioni != null && dtCutOffPagamenti != null && dtCutOffErogazioni != null && dtCutOffValidazioni != null && idUtenteIns != null) {
   			isValid = true;
   		}
   		return isValid;
	}
	public boolean isPKValid() {
		boolean isPkValid = false;
		if (idPropostaCertificaz != null ) {
   			isPkValid = true;
   		}

   		return isPkValid;
	}
	
	public String toString() {
		
	    String temp = "";
	    StringBuffer sb = new StringBuffer();
	    sb.append("\t\n" + this.getClass().getName() + "\t\n");
	    
	    temp = DataFilter.removeNull( idUtenteAgg);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idUtenteAgg: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idStatoPropostaCertif);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idStatoPropostaCertif: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtCutOffFideiussioni);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtCutOffFideiussioni: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idPropostaPrec);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idPropostaPrec: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtOraCreazione);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtOraCreazione: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtCutOffPagamenti);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtCutOffPagamenti: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtCutOffErogazioni);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtCutOffErogazioni: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtCutOffValidazioni);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtCutOffValidazioni: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idUtenteIns);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idUtenteIns: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( descProposta);
	    if (!DataFilter.isEmpty(temp)) sb.append(" descProposta: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idPropostaCertificaz);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idPropostaCertificaz: " + temp + "\t\n");
	    
	    return sb.toString();
	}
	
	public java.util.List<String> getPK() {
		java.util.List<String> pk=new java.util.ArrayList<String>();
		
			pk.add("idPropostaCertificaz");
		
	    return pk;
	}
	
	
}
