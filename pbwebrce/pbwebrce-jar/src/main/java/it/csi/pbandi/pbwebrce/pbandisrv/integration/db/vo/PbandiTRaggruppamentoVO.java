/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/


package it.csi.pbandi.pbwebrce.pbandisrv.integration.db.vo;

import java.math.*;
import java.sql.Date;
import it.csi.pbandi.pbwebrce.pbandisrv.integration.util.DataFilter;



public class PbandiTRaggruppamentoVO extends GenericVO {

  	
  	private BigDecimal spesaAmmessa;
  	
  	private Date dtAttoCostitutivoAts;
  	
  	private BigDecimal idRaggruppamento;
  	
  	private String acronimoProgetto;
  	
  	private BigDecimal idDomanda;
  	
  	private BigDecimal durataProgetto;
  	
  	private Date dtInserimento;
  	
  	private BigDecimal idUtenteIns;
  	
  	private BigDecimal idAreaScientifica;
  	
  	private String attoCostitutivoAts;
  	
  	private String nomeCompletoProgetto;
  	
  	private String organizzazione;
  	
  	private BigDecimal idCoordinatore;
  	
  	private BigDecimal idTematica;
  	
  	private BigDecimal totalePersoneMese;
  	
  	private BigDecimal costoTotaleProgetto;
  	
  	private BigDecimal richiestaContributo;
  	
  	private BigDecimal idUtenteAgg;
  	
  	private String raggruppamentoAts;
  	
  	private Date dtAggiornamento;
  	
	public PbandiTRaggruppamentoVO() {}
  	
	public PbandiTRaggruppamentoVO (BigDecimal idRaggruppamento) {
	
		this. idRaggruppamento =  idRaggruppamento;
	}
  	
	public PbandiTRaggruppamentoVO (BigDecimal spesaAmmessa, Date dtAttoCostitutivoAts, BigDecimal idRaggruppamento, String acronimoProgetto, BigDecimal idDomanda, BigDecimal durataProgetto, Date dtInserimento, BigDecimal idUtenteIns, BigDecimal idAreaScientifica, String attoCostitutivoAts, String nomeCompletoProgetto, String organizzazione, BigDecimal idCoordinatore, BigDecimal idTematica, BigDecimal totalePersoneMese, BigDecimal costoTotaleProgetto, BigDecimal richiestaContributo, BigDecimal idUtenteAgg, String raggruppamentoAts, Date dtAggiornamento) {
	
		this. spesaAmmessa =  spesaAmmessa;
		this. dtAttoCostitutivoAts =  dtAttoCostitutivoAts;
		this. idRaggruppamento =  idRaggruppamento;
		this. acronimoProgetto =  acronimoProgetto;
		this. idDomanda =  idDomanda;
		this. durataProgetto =  durataProgetto;
		this. dtInserimento =  dtInserimento;
		this. idUtenteIns =  idUtenteIns;
		this. idAreaScientifica =  idAreaScientifica;
		this. attoCostitutivoAts =  attoCostitutivoAts;
		this. nomeCompletoProgetto =  nomeCompletoProgetto;
		this. organizzazione =  organizzazione;
		this. idCoordinatore =  idCoordinatore;
		this. idTematica =  idTematica;
		this. totalePersoneMese =  totalePersoneMese;
		this. costoTotaleProgetto =  costoTotaleProgetto;
		this. richiestaContributo =  richiestaContributo;
		this. idUtenteAgg =  idUtenteAgg;
		this. raggruppamentoAts =  raggruppamentoAts;
		this. dtAggiornamento =  dtAggiornamento;
	}
  	
  	
	public BigDecimal getSpesaAmmessa() {
		return spesaAmmessa;
	}
	
	public void setSpesaAmmessa(BigDecimal spesaAmmessa) {
		this.spesaAmmessa = spesaAmmessa;
	}
	
	public Date getDtAttoCostitutivoAts() {
		return dtAttoCostitutivoAts;
	}
	
	public void setDtAttoCostitutivoAts(Date dtAttoCostitutivoAts) {
		this.dtAttoCostitutivoAts = dtAttoCostitutivoAts;
	}
	
	public BigDecimal getIdRaggruppamento() {
		return idRaggruppamento;
	}
	
	public void setIdRaggruppamento(BigDecimal idRaggruppamento) {
		this.idRaggruppamento = idRaggruppamento;
	}
	
	public String getAcronimoProgetto() {
		return acronimoProgetto;
	}
	
	public void setAcronimoProgetto(String acronimoProgetto) {
		this.acronimoProgetto = acronimoProgetto;
	}
	
	public BigDecimal getIdDomanda() {
		return idDomanda;
	}
	
	public void setIdDomanda(BigDecimal idDomanda) {
		this.idDomanda = idDomanda;
	}
	
	public BigDecimal getDurataProgetto() {
		return durataProgetto;
	}
	
	public void setDurataProgetto(BigDecimal durataProgetto) {
		this.durataProgetto = durataProgetto;
	}
	
	public Date getDtInserimento() {
		return dtInserimento;
	}
	
	public void setDtInserimento(Date dtInserimento) {
		this.dtInserimento = dtInserimento;
	}
	
	public BigDecimal getIdUtenteIns() {
		return idUtenteIns;
	}
	
	public void setIdUtenteIns(BigDecimal idUtenteIns) {
		this.idUtenteIns = idUtenteIns;
	}
	
	public BigDecimal getIdAreaScientifica() {
		return idAreaScientifica;
	}
	
	public void setIdAreaScientifica(BigDecimal idAreaScientifica) {
		this.idAreaScientifica = idAreaScientifica;
	}
	
	public String getAttoCostitutivoAts() {
		return attoCostitutivoAts;
	}
	
	public void setAttoCostitutivoAts(String attoCostitutivoAts) {
		this.attoCostitutivoAts = attoCostitutivoAts;
	}
	
	public String getNomeCompletoProgetto() {
		return nomeCompletoProgetto;
	}
	
	public void setNomeCompletoProgetto(String nomeCompletoProgetto) {
		this.nomeCompletoProgetto = nomeCompletoProgetto;
	}
	
	public String getOrganizzazione() {
		return organizzazione;
	}
	
	public void setOrganizzazione(String organizzazione) {
		this.organizzazione = organizzazione;
	}
	
	public BigDecimal getIdCoordinatore() {
		return idCoordinatore;
	}
	
	public void setIdCoordinatore(BigDecimal idCoordinatore) {
		this.idCoordinatore = idCoordinatore;
	}
	
	public BigDecimal getIdTematica() {
		return idTematica;
	}
	
	public void setIdTematica(BigDecimal idTematica) {
		this.idTematica = idTematica;
	}
	
	public BigDecimal getTotalePersoneMese() {
		return totalePersoneMese;
	}
	
	public void setTotalePersoneMese(BigDecimal totalePersoneMese) {
		this.totalePersoneMese = totalePersoneMese;
	}
	
	public BigDecimal getCostoTotaleProgetto() {
		return costoTotaleProgetto;
	}
	
	public void setCostoTotaleProgetto(BigDecimal costoTotaleProgetto) {
		this.costoTotaleProgetto = costoTotaleProgetto;
	}
	
	public BigDecimal getRichiestaContributo() {
		return richiestaContributo;
	}
	
	public void setRichiestaContributo(BigDecimal richiestaContributo) {
		this.richiestaContributo = richiestaContributo;
	}
	
	public BigDecimal getIdUtenteAgg() {
		return idUtenteAgg;
	}
	
	public void setIdUtenteAgg(BigDecimal idUtenteAgg) {
		this.idUtenteAgg = idUtenteAgg;
	}
	
	public String getRaggruppamentoAts() {
		return raggruppamentoAts;
	}
	
	public void setRaggruppamentoAts(String raggruppamentoAts) {
		this.raggruppamentoAts = raggruppamentoAts;
	}
	
	public Date getDtAggiornamento() {
		return dtAggiornamento;
	}
	
	public void setDtAggiornamento(Date dtAggiornamento) {
		this.dtAggiornamento = dtAggiornamento;
	}
	
	public boolean isValid() {
		boolean isValid = false;
                if (isPKValid() && acronimoProgetto != null && idDomanda != null && dtInserimento != null && idUtenteIns != null) {
   			isValid = true;
   		}
   		return isValid;
	}
	public boolean isPKValid() {
		boolean isPkValid = false;
		if (idRaggruppamento != null ) {
   			isPkValid = true;
   		}

   		return isPkValid;
	}
	
	public String toString() {
		
	    String temp = "";
	    StringBuffer sb = new StringBuffer();
	    sb.append("\t\n" + this.getClass().getName() + "\t\n");
	    
	    temp = DataFilter.removeNull( spesaAmmessa);
	    if (!DataFilter.isEmpty(temp)) sb.append(" spesaAmmessa: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtAttoCostitutivoAts);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtAttoCostitutivoAts: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idRaggruppamento);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idRaggruppamento: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( acronimoProgetto);
	    if (!DataFilter.isEmpty(temp)) sb.append(" acronimoProgetto: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idDomanda);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idDomanda: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( durataProgetto);
	    if (!DataFilter.isEmpty(temp)) sb.append(" durataProgetto: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtInserimento);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtInserimento: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idUtenteIns);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idUtenteIns: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idAreaScientifica);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idAreaScientifica: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( attoCostitutivoAts);
	    if (!DataFilter.isEmpty(temp)) sb.append(" attoCostitutivoAts: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( nomeCompletoProgetto);
	    if (!DataFilter.isEmpty(temp)) sb.append(" nomeCompletoProgetto: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( organizzazione);
	    if (!DataFilter.isEmpty(temp)) sb.append(" organizzazione: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idCoordinatore);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idCoordinatore: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idTematica);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idTematica: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( totalePersoneMese);
	    if (!DataFilter.isEmpty(temp)) sb.append(" totalePersoneMese: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( costoTotaleProgetto);
	    if (!DataFilter.isEmpty(temp)) sb.append(" costoTotaleProgetto: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( richiestaContributo);
	    if (!DataFilter.isEmpty(temp)) sb.append(" richiestaContributo: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idUtenteAgg);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idUtenteAgg: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( raggruppamentoAts);
	    if (!DataFilter.isEmpty(temp)) sb.append(" raggruppamentoAts: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtAggiornamento);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtAggiornamento: " + temp + "\t\n");
	    
	    return sb.toString();
	}
	
	public java.util.List<String> getPK() {
		java.util.List<String> pk=new java.util.ArrayList<String>();
		
			pk.add("idRaggruppamento");
		
	    return pk;
	}
	
	
}
