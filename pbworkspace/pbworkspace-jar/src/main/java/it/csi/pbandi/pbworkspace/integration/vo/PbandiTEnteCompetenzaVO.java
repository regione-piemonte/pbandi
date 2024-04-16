/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/


package it.csi.pbandi.pbworkspace.integration.vo;

import java.math.*;
import java.sql.Date;

import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.GenericVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.util.DataFilter;



public class PbandiTEnteCompetenzaVO extends GenericVO {

  	
  	private Date dtInizioValidita;
  	
  	private BigDecimal idUtenteAgg;
  	
  	private BigDecimal idFormaGiuridica;
  	
  	private BigDecimal idTipoEnteCompetenza;
  	
  	private String descEnte;
  	
  	private BigDecimal idIndirizzo;
  	
  	private BigDecimal idAttivitaAteco;
  	
  	private BigDecimal idEnteCompetenza;
  	
  	private String passCipe;
  	
  	private Date dtFineValidita;
  	
  	private String codiceFiscaleEnte;
  	
  	private String partitaIvaEnte;
  	
  	private String userCipe;
  	
  	private String descBreveEnte;
  	
  	private String codCipe;
  	
  	private BigDecimal idUtenteIns;
  	
  	private String indirizzoMailPec;

	public PbandiTEnteCompetenzaVO() {}
  	
	public PbandiTEnteCompetenzaVO (BigDecimal idEnteCompetenza) {
	
		this. idEnteCompetenza =  idEnteCompetenza;
	}
  	
	public PbandiTEnteCompetenzaVO (Date dtInizioValidita, BigDecimal idUtenteAgg, BigDecimal idFormaGiuridica, BigDecimal idTipoEnteCompetenza, String descEnte, BigDecimal idIndirizzo, BigDecimal idAttivitaAteco, BigDecimal idEnteCompetenza, String passCipe, Date dtFineValidita, String codiceFiscaleEnte, String userCipe, String descBreveEnte, String codCipe, BigDecimal idUtenteIns, String partitaIvaEnte) {
	
		this. dtInizioValidita =  dtInizioValidita;
		this. idUtenteAgg =  idUtenteAgg;
		this. idFormaGiuridica =  idFormaGiuridica;
		this. idTipoEnteCompetenza =  idTipoEnteCompetenza;
		this. descEnte =  descEnte;
		this. idIndirizzo =  idIndirizzo;
		this. idAttivitaAteco =  idAttivitaAteco;
		this. idEnteCompetenza =  idEnteCompetenza;
		this. passCipe =  passCipe;
		this. dtFineValidita =  dtFineValidita;
		this. codiceFiscaleEnte =  codiceFiscaleEnte;
		this. userCipe =  userCipe;
		this. descBreveEnte =  descBreveEnte;
		this. codCipe =  codCipe;
		this. idUtenteIns =  idUtenteIns;
		this. partitaIvaEnte = partitaIvaEnte;
	}
  	
	public String getPartitaIvaEnte() {
		return partitaIvaEnte;
	}

	public void setPartitaIvaEnte(String partitaIvaEnte) {
		this.partitaIvaEnte = partitaIvaEnte;
	}

	public Date getDtInizioValidita() {
		return dtInizioValidita;
	}
	
	public void setDtInizioValidita(Date dtInizioValidita) {
		this.dtInizioValidita = dtInizioValidita;
	}
	
	public BigDecimal getIdUtenteAgg() {
		return idUtenteAgg;
	}
	
	public void setIdUtenteAgg(BigDecimal idUtenteAgg) {
		this.idUtenteAgg = idUtenteAgg;
	}
	
	public BigDecimal getIdFormaGiuridica() {
		return idFormaGiuridica;
	}
	
	public void setIdFormaGiuridica(BigDecimal idFormaGiuridica) {
		this.idFormaGiuridica = idFormaGiuridica;
	}
	
	public BigDecimal getIdTipoEnteCompetenza() {
		return idTipoEnteCompetenza;
	}
	
	public void setIdTipoEnteCompetenza(BigDecimal idTipoEnteCompetenza) {
		this.idTipoEnteCompetenza = idTipoEnteCompetenza;
	}
	
	public String getDescEnte() {
		return descEnte;
	}
	
	public void setDescEnte(String descEnte) {
		this.descEnte = descEnte;
	}
	
	public BigDecimal getIdIndirizzo() {
		return idIndirizzo;
	}
	
	public void setIdIndirizzo(BigDecimal idIndirizzo) {
		this.idIndirizzo = idIndirizzo;
	}
	
	public BigDecimal getIdAttivitaAteco() {
		return idAttivitaAteco;
	}
	
	public void setIdAttivitaAteco(BigDecimal idAttivitaAteco) {
		this.idAttivitaAteco = idAttivitaAteco;
	}
	
	public BigDecimal getIdEnteCompetenza() {
		return idEnteCompetenza;
	}
	
	public void setIdEnteCompetenza(BigDecimal idEnteCompetenza) {
		this.idEnteCompetenza = idEnteCompetenza;
	}
	
	public String getPassCipe() {
		return passCipe;
	}
	
	public void setPassCipe(String passCipe) {
		this.passCipe = passCipe;
	}
	
	public Date getDtFineValidita() {
		return dtFineValidita;
	}
	
	public void setDtFineValidita(Date dtFineValidita) {
		this.dtFineValidita = dtFineValidita;
	}
	
	public String getCodiceFiscaleEnte() {
		return codiceFiscaleEnte;
	}
	
	public void setCodiceFiscaleEnte(String codiceFiscaleEnte) {
		this.codiceFiscaleEnte = codiceFiscaleEnte;
	}
	
	public String getUserCipe() {
		return userCipe;
	}
	
	public void setUserCipe(String userCipe) {
		this.userCipe = userCipe;
	}
	
	public String getDescBreveEnte() {
		return descBreveEnte;
	}
	
	public void setDescBreveEnte(String descBreveEnte) {
		this.descBreveEnte = descBreveEnte;
	}
	
	public String getCodCipe() {
		return codCipe;
	}
	
	public void setCodCipe(String codCipe) {
		this.codCipe = codCipe;
	}
	
	public BigDecimal getIdUtenteIns() {
		return idUtenteIns;
	}
	
	public void setIdUtenteIns(BigDecimal idUtenteIns) {
		this.idUtenteIns = idUtenteIns;
	}
	
	public String getIndirizzoMailPec() {
		return indirizzoMailPec;
	}

	public void setIndirizzoMailPec(String indirizzoMailPec) {
		this.indirizzoMailPec = indirizzoMailPec;
	}
	
	public boolean isValid() {
		boolean isValid = false;
                if (isPKValid() && dtInizioValidita != null && idFormaGiuridica != null && idIndirizzo != null && idAttivitaAteco != null && descBreveEnte != null && idUtenteIns != null) {
   			isValid = true;
   		}
   		return isValid;
	}
	public boolean isPKValid() {
		boolean isPkValid = false;
		if (idEnteCompetenza != null ) {
   			isPkValid = true;
   		}

   		return isPkValid;
	}
	
	public String toString() {
		
	    String temp = "";
	    StringBuffer sb = new StringBuffer();
	    sb.append("\t\n" + this.getClass().getName() + "\t\n");
	    
	    temp = DataFilter.removeNull( dtInizioValidita);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtInizioValidita: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idUtenteAgg);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idUtenteAgg: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idFormaGiuridica);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idFormaGiuridica: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idTipoEnteCompetenza);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idTipoEnteCompetenza: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( descEnte);
	    if (!DataFilter.isEmpty(temp)) sb.append(" descEnte: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idIndirizzo);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idIndirizzo: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idAttivitaAteco);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idAttivitaAteco: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idEnteCompetenza);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idEnteCompetenza: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( passCipe);
	    if (!DataFilter.isEmpty(temp)) sb.append(" passCipe: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtFineValidita);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtFineValidita: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( codiceFiscaleEnte);
	    if (!DataFilter.isEmpty(temp)) sb.append(" codiceFiscaleEnte: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( partitaIvaEnte);
	    if (!DataFilter.isEmpty(temp)) sb.append(" partitaIvaEnte: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( userCipe);
	    if (!DataFilter.isEmpty(temp)) sb.append(" userCipe: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( descBreveEnte);
	    if (!DataFilter.isEmpty(temp)) sb.append(" descBreveEnte: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( codCipe);
	    if (!DataFilter.isEmpty(temp)) sb.append(" codCipe: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idUtenteIns);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idUtenteIns: " + temp + "\t\n");
	    
	    return sb.toString();
	}
	
	public java.util.List<String> getPK() {
		java.util.List<String> pk=new java.util.ArrayList<String>();
		
			pk.add("idEnteCompetenza");
		
	    return pk;
	}
	
	
}
