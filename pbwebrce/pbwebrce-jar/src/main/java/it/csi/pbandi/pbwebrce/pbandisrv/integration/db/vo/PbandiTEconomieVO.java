/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebrce.pbandisrv.integration.db.vo;

import it.csi.pbandi.pbwebrce.pbandisrv.integration.util.DataFilter;

import java.math.BigDecimal;
import java.sql.Date;


public class PbandiTEconomieVO extends GenericVO{
	
  	private BigDecimal 	idEconomia;
  	
  	private BigDecimal 	idProgettoCedente;
  	
  	private Double 		importoCeduto;
  	
  	private BigDecimal 	idProgettoRicevente;
  	
  	private Date 		dataRicezione;
  	
  	private Date 		dataUtilizzo;
  	
  	private String 		noteCessione;
  	
  	private String 		noteRicezione;
  	
  	private Date 		dataCessione;
  	
  	private Date 		dataInserimento;
  	
  	private Date 		dataModifica;
  	
  	private BigDecimal 	idUtenteIns;
  	
  	private BigDecimal 	idUtenteAgg;
  	
  	public PbandiTEconomieVO(){}
  	
  	public PbandiTEconomieVO(BigDecimal idEconomia){
  		this.idEconomia = idEconomia;
  	}
  	
  	public PbandiTEconomieVO(BigDecimal idEconomia,             
  			BigDecimal idProgettoCedente,    	                    
  			Double importoCeduto,       	                    
  			BigDecimal idProgettoRicevente,	                    
  			Date dataRicezione,       	                    
  			Date dataUtilizzo,         	                    
  			String noteCessione,       	                    
  			String noteRicezione,         	                    
  			Date dataCessione,        	                    
  			Date dataInserimento,      	                    
  			Date dataModifica,         	                    
  			BigDecimal idUtenteIns,          	                    
  			BigDecimal idUtenteAgg){

  		this.idEconomia  			=  idEconomia ;                                                      
  		this.idProgettoCedente       = idProgettoCedente  ;  		                                              
  		this.importoCeduto           = importoCeduto      ; 		                                              
  		this.idProgettoRicevente     = idProgettoRicevente;  		                                           
  		this.dataRicezione          = dataRicezione      ;  		                                              
  		this.dataUtilizzo            = dataUtilizzo       ; 		                                              
  		this.noteCessione            = noteCessione       ; 		                                              
  		this.noteRicezione            = noteRicezione      ; 		                                              
  		this.dataCessione            = dataCessione;  		                                              
  		this.dataInserimento         = dataInserimento    ;  		                                              
  		this.dataModifica            = dataModifica       ;  		                                             
  		this.idUtenteIns             = idUtenteIns        ;	                                              
  		this.idUtenteAgg             = idUtenteAgg;       ;

  	}

	public BigDecimal getIdEconomia() {
		return idEconomia;
	}

	public void setIdEconomia(BigDecimal idEconomia) {
		this.idEconomia = idEconomia;
	}

	public BigDecimal getIdProgettoCedente() {
		return idProgettoCedente;
	}

	public void setIdProgettoCedente(BigDecimal idProgettoCedente) {
		this.idProgettoCedente = idProgettoCedente;
	}

	public Double getImportoCeduto() {
		return importoCeduto;
	}

	public void setImportoCeduto(Double importoCeduto) {
		this.importoCeduto = importoCeduto;
	}

	public BigDecimal getIdProgettoRicevente() {
		return idProgettoRicevente;
	}

	public void setIdProgettoRicevente(BigDecimal idProgettoRicevente) {
		this.idProgettoRicevente = idProgettoRicevente;
	}

	public Date getDataRicezione() {
		return dataRicezione;
	}

	public void setDataRicezione(Date dataRicezione) {
		this.dataRicezione = dataRicezione;
	}

	public Date getDataUtilizzo() {
		return dataUtilizzo;
	}

	public void setDataUtilizzo(Date dataUtilizzo) {
		this.dataUtilizzo = dataUtilizzo;
	}

	public String getNoteCessione() {
		return noteCessione;
	}

	public void setNoteCessione(String noteCessione) {
		this.noteCessione = noteCessione;
	}

	public String getNoteRicezione() {
		return noteRicezione;
	}

	public void setNoteRicezione(String noteRicezione) {
		this.noteRicezione = noteRicezione;
	}

	public Date getDataCessione() {
		return dataCessione;
	}

	public void setDataCessione(Date dataCessione) {
		this.dataCessione = dataCessione;
	}

	public Date getDataInserimento() {
		return dataInserimento;
	}

	public void setDataInserimento(Date dataInserimento) {
		this.dataInserimento = dataInserimento;
	}

	public Date getDataModifica() {
		return dataModifica;
	}

	public void setDataModifica(Date dataModifica) {
		this.dataModifica = dataModifica;
	}

	public BigDecimal getIdUtenteIns() {
		return idUtenteIns;
	}

	public void setIdUtenteIns(BigDecimal idUtenteIns) {
		this.idUtenteIns = idUtenteIns;
	}

	public BigDecimal getIdUtenteAgg() {
		return idUtenteAgg;
	}

	public void setIdUtenteAgg(BigDecimal idUtenteAgg) {
		this.idUtenteAgg = idUtenteAgg;
	}
  	
	
	public boolean isValid() {
		boolean isValid = false;
                if (isPKValid() && idProgettoCedente != null && importoCeduto != null && idUtenteIns != null && dataCessione != null) {
   			isValid = true;
   		}
   		return isValid;
	}
	public boolean isPKValid() {
		boolean isPkValid = false;
		if (idEconomia != null ) {
   			isPkValid = true;
   		}

   		return isPkValid;
	}
	
	public String toString() {
		
	    String temp = "";
	    StringBuffer sb = new StringBuffer();
	    sb.append("\t\n" + this.getClass().getName() + "\t\n");
	    
	    temp = DataFilter.removeNull( idEconomia);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idEconomia: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idProgettoCedente);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idProgettoCedente: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( importoCeduto);
	    if (!DataFilter.isEmpty(temp)) sb.append(" importoCeduto: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idProgettoRicevente);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idProgettoRicevente: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dataRicezione);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dataRicezione: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dataUtilizzo);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dataUtilizzo: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( noteCessione);
	    if (!DataFilter.isEmpty(temp)) sb.append(" noteCessione: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idUtenteIns);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idUtenteIns: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( noteRicezione);
	    if (!DataFilter.isEmpty(temp)) sb.append(" noteRicezione: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dataCessione);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dataCessione: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dataInserimento);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dataInserimento: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dataModifica);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dataModifica: " + temp + "\t\n");
	    
	    return sb.toString();
	}
	
	public java.util.List<String> getPK() {
		java.util.List<String> pk=new java.util.ArrayList<String>();
		
			pk.add("idEconomia");
		
	    return pk;
	}

  	

}
