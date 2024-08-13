/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/


package it.csi.pbandi.pbweb.pbandisrv.integration.db.vo;

import java.math.*;
import java.sql.Date;
import it.csi.pbandi.pbweb.pbandisrv.integration.util.DataFilter;



public class PbandiTIntegrazioneSpesaVO extends GenericVO {

	private BigDecimal idIntegrazioneSpesa;
	private BigDecimal idDichiarazioneSpesa;
	private String descrizione;	
  	private Date dataRichiesta;
  	private Date dataInvio;
  	private BigDecimal idUtenteRichiesta;
  	private BigDecimal idUtenteInvio;
  	
	public PbandiTIntegrazioneSpesaVO() {}
  	
	public PbandiTIntegrazioneSpesaVO (BigDecimal idIntegrazioneSpesa) {
		this.idIntegrazioneSpesa = idIntegrazioneSpesa;
	}
  	
	public PbandiTIntegrazioneSpesaVO (BigDecimal idIntegrazioneSpesa,BigDecimal idDichiarazioneSpesa,String descrizione,Date dataRichiesta,Date dataInvio,BigDecimal idUtenteRichiesta,BigDecimal idUtenteInvio) {
		this.idIntegrazioneSpesa = idIntegrazioneSpesa;
		this.idDichiarazioneSpesa = idDichiarazioneSpesa;
		this.descrizione = descrizione;
		this.dataRichiesta = dataRichiesta;
		this.dataInvio = dataInvio;
		this.idUtenteRichiesta = idUtenteRichiesta;
		this.idUtenteInvio = idUtenteInvio;
	}
  		
	public boolean isValid() {
		boolean isValid = false;
		if (isPKValid()) {
   			isValid = true;
   		}
   		return isValid;
	}
	
	public boolean isPKValid() {
		boolean isPkValid = false;
		if (idIntegrazioneSpesa != null ) {
   			isPkValid = true;
   		}
   		return isPkValid;
	}
	
	public java.util.List<String> getPK() {
		java.util.List<String> pk = new java.util.ArrayList<String>();
		pk.add("idIntegrazioneSpesa");		
	    return pk;
	}
	
	public String toString() {
		
	    String temp = "";
	    StringBuffer sb = new StringBuffer();
	    sb.append("\t\n" + this.getClass().getName() + "\t\n");
	    
	    temp = DataFilter.removeNull( idIntegrazioneSpesa);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idIntegrazioneSpesa: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idDichiarazioneSpesa);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idDichiarazioneSpesa: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( descrizione);
	    if (!DataFilter.isEmpty(temp)) sb.append(" descrizione: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dataRichiesta);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dataRichiesta: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dataInvio);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dataInvio: " + temp + "\t\n");

	    temp = DataFilter.removeNull( idUtenteRichiesta);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idUtenteRichiesta: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idUtenteInvio);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idUtenteInvio: " + temp + "\t\n");
	    return sb.toString();
	}

	public BigDecimal getIdIntegrazioneSpesa() {
		return idIntegrazioneSpesa;
	}

	public void setIdIntegrazioneSpesa(BigDecimal idIntegrazioneSpesa) {
		this.idIntegrazioneSpesa = idIntegrazioneSpesa;
	}

	public BigDecimal getIdDichiarazioneSpesa() {
		return idDichiarazioneSpesa;
	}

	public void setIdDichiarazioneSpesa(BigDecimal idDichiarazioneSpesa) {
		this.idDichiarazioneSpesa = idDichiarazioneSpesa;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public Date getDataRichiesta() {
		return dataRichiesta;
	}

	public void setDataRichiesta(Date dataRichiesta) {
		this.dataRichiesta = dataRichiesta;
	}

	public Date getDataInvio() {
		return dataInvio;
	}

	public void setDataInvio(Date dataInvio) {
		this.dataInvio = dataInvio;
	}

	public BigDecimal getIdUtenteRichiesta() {
		return idUtenteRichiesta;
	}

	public void setIdUtenteRichiesta(BigDecimal idUtenteRichiesta) {
		this.idUtenteRichiesta = idUtenteRichiesta;
	}

	public BigDecimal getIdUtenteInvio() {
		return idUtenteInvio;
	}

	public void setIdUtenteInvio(BigDecimal idUtenteInvio) {
		this.idUtenteInvio = idUtenteInvio;
	}
	
}
