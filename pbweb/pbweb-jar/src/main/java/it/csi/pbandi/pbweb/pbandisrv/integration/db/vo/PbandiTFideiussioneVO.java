/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/


package it.csi.pbandi.pbweb.pbandisrv.integration.db.vo;

import java.math.*;
import java.sql.Date;
import it.csi.pbandi.pbweb.pbandisrv.integration.util.DataFilter;



public class PbandiTFideiussioneVO extends GenericVO {

  	
  	private Date dtScadenza;
  	
  	private BigDecimal idUtenteAgg;
  	
  	private Date dtDecorrenza;
  	
  	private String descEnteEmittente;
  	
  	private Date dtAggiornamento;
  	
  	private Date dtInserimento;
  	
  	private String codRiferimentoFideiussione;
  	
  	private String noteFideiussione;
  	
  	private BigDecimal idTipoTrattamento;
  	
  	private BigDecimal idProgetto;
  	
  	private BigDecimal idUtenteIns;
  	
  	private BigDecimal idFideiussione;
  	
  	private BigDecimal importoFideiussione;
  	
	public PbandiTFideiussioneVO() {}
  	
	public PbandiTFideiussioneVO (BigDecimal idFideiussione) {
	
		this. idFideiussione =  idFideiussione;
	}
  	
	public PbandiTFideiussioneVO (Date dtScadenza, BigDecimal idUtenteAgg, Date dtDecorrenza, String descEnteEmittente, Date dtAggiornamento, Date dtInserimento, String codRiferimentoFideiussione, String noteFideiussione, BigDecimal idTipoTrattamento, BigDecimal idProgetto, BigDecimal idUtenteIns, BigDecimal idFideiussione, BigDecimal importoFideiussione) {
	
		this. dtScadenza =  dtScadenza;
		this. idUtenteAgg =  idUtenteAgg;
		this. dtDecorrenza =  dtDecorrenza;
		this. descEnteEmittente =  descEnteEmittente;
		this. dtAggiornamento =  dtAggiornamento;
		this. dtInserimento =  dtInserimento;
		this. codRiferimentoFideiussione =  codRiferimentoFideiussione;
		this. noteFideiussione =  noteFideiussione;
		this. idTipoTrattamento =  idTipoTrattamento;
		this. idProgetto =  idProgetto;
		this. idUtenteIns =  idUtenteIns;
		this. idFideiussione =  idFideiussione;
		this. importoFideiussione =  importoFideiussione;
	}
  	
  	
	public Date getDtScadenza() {
		return dtScadenza;
	}
	
	public void setDtScadenza(Date dtScadenza) {
		this.dtScadenza = dtScadenza;
	}
	
	public BigDecimal getIdUtenteAgg() {
		return idUtenteAgg;
	}
	
	public void setIdUtenteAgg(BigDecimal idUtenteAgg) {
		this.idUtenteAgg = idUtenteAgg;
	}
	
	public Date getDtDecorrenza() {
		return dtDecorrenza;
	}
	
	public void setDtDecorrenza(Date dtDecorrenza) {
		this.dtDecorrenza = dtDecorrenza;
	}
	
	public String getDescEnteEmittente() {
		return descEnteEmittente;
	}
	
	public void setDescEnteEmittente(String descEnteEmittente) {
		this.descEnteEmittente = descEnteEmittente;
	}
	
	public Date getDtAggiornamento() {
		return dtAggiornamento;
	}
	
	public void setDtAggiornamento(Date dtAggiornamento) {
		this.dtAggiornamento = dtAggiornamento;
	}
	
	public Date getDtInserimento() {
		return dtInserimento;
	}
	
	public void setDtInserimento(Date dtInserimento) {
		this.dtInserimento = dtInserimento;
	}
	
	public String getCodRiferimentoFideiussione() {
		return codRiferimentoFideiussione;
	}
	
	public void setCodRiferimentoFideiussione(String codRiferimentoFideiussione) {
		this.codRiferimentoFideiussione = codRiferimentoFideiussione;
	}
	
	public String getNoteFideiussione() {
		return noteFideiussione;
	}
	
	public void setNoteFideiussione(String noteFideiussione) {
		this.noteFideiussione = noteFideiussione;
	}
	
	public BigDecimal getIdTipoTrattamento() {
		return idTipoTrattamento;
	}
	
	public void setIdTipoTrattamento(BigDecimal idTipoTrattamento) {
		this.idTipoTrattamento = idTipoTrattamento;
	}
	
	public BigDecimal getIdProgetto() {
		return idProgetto;
	}
	
	public void setIdProgetto(BigDecimal idProgetto) {
		this.idProgetto = idProgetto;
	}
	
	public BigDecimal getIdUtenteIns() {
		return idUtenteIns;
	}
	
	public void setIdUtenteIns(BigDecimal idUtenteIns) {
		this.idUtenteIns = idUtenteIns;
	}
	
	public BigDecimal getIdFideiussione() {
		return idFideiussione;
	}
	
	public void setIdFideiussione(BigDecimal idFideiussione) {
		this.idFideiussione = idFideiussione;
	}
	
	public BigDecimal getImportoFideiussione() {
		return importoFideiussione;
	}
	
	public void setImportoFideiussione(BigDecimal importoFideiussione) {
		this.importoFideiussione = importoFideiussione;
	}
	
	public boolean isValid() {
		boolean isValid = false;
                if (isPKValid() && dtDecorrenza != null && dtInserimento != null && idTipoTrattamento != null && idProgetto != null && idUtenteIns != null && importoFideiussione != null) {
   			isValid = true;
   		}
   		return isValid;
	}
	public boolean isPKValid() {
		boolean isPkValid = false;
		if (idFideiussione != null ) {
   			isPkValid = true;
   		}

   		return isPkValid;
	}
	
	public String toString() {
		
	    String temp = "";
	    StringBuffer sb = new StringBuffer();
	    sb.append("\t\n" + this.getClass().getName() + "\t\n");
	    
	    temp = DataFilter.removeNull( dtScadenza);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtScadenza: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idUtenteAgg);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idUtenteAgg: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtDecorrenza);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtDecorrenza: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( descEnteEmittente);
	    if (!DataFilter.isEmpty(temp)) sb.append(" descEnteEmittente: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtAggiornamento);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtAggiornamento: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtInserimento);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtInserimento: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( codRiferimentoFideiussione);
	    if (!DataFilter.isEmpty(temp)) sb.append(" codRiferimentoFideiussione: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( noteFideiussione);
	    if (!DataFilter.isEmpty(temp)) sb.append(" noteFideiussione: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idTipoTrattamento);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idTipoTrattamento: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idProgetto);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idProgetto: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idUtenteIns);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idUtenteIns: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idFideiussione);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idFideiussione: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( importoFideiussione);
	    if (!DataFilter.isEmpty(temp)) sb.append(" importoFideiussione: " + temp + "\t\n");
	    
	    return sb.toString();
	}
	
	public java.util.List<String> getPK() {
		java.util.List<String> pk=new java.util.ArrayList<String>();
		
			pk.add("idFideiussione");
		
	    return pk;
	}
	
	
}
