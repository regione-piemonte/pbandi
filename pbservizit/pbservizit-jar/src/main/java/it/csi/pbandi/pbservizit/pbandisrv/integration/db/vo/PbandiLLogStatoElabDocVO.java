/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/


package it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo;

import java.math.*;
import java.sql.Date;
import it.csi.pbandi.pbservizit.pbandisrv.integration.util.DataFilter;

public class PbandiLLogStatoElabDocVO extends GenericVO {
  	
	private BigDecimal idChiamata;
	
	private BigDecimal idAttoLiquidazione;
  	
	// Dati relativi al servizio elaboraDocumentoAsync() di Contabilia.
	private String esitoElaboraDocumento;
  	private String erroreElaboraDocumento;
  	private Date dtElaboraDocumento;
  	private BigDecimal idOperazioneAsincrona;
  	private String requestelaboradocumento;
  	
 // Dati relativi al servizio leggiStatoElaborazione() di Contabilia.
  	private String esitoLeggiStato;
  	private String statoElaborazione;
  	private String erroreLeggiStato;
  	private Date dtLeggiStato;
  	
	public PbandiLLogStatoElabDocVO() {}
  	
	public PbandiLLogStatoElabDocVO (BigDecimal idChiamata,BigDecimal idAttoLiquidazione,String esitoElaboraDocumento,String erroreElaboraDocumento,Date dtElaboraDocumento,BigDecimal idOperazioneAsincrona,String esitoLeggiStato,String statoElaborazione,String erroreLeggiStato,Date dtLeggiStato) {
		this.idChiamata = idChiamata;
		this.idAttoLiquidazione = idAttoLiquidazione;
		this.esitoElaboraDocumento = esitoElaboraDocumento;
		this.erroreElaboraDocumento = erroreElaboraDocumento;
		this.dtElaboraDocumento = dtElaboraDocumento;
		this.idOperazioneAsincrona = idOperazioneAsincrona;
		this.esitoLeggiStato = esitoLeggiStato;
		this.statoElaborazione = statoElaborazione;
		this.erroreLeggiStato = erroreLeggiStato;
		this.dtLeggiStato = dtLeggiStato;
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
		if (idChiamata != null ) {
   			isPkValid = true;
   		}
   		return isPkValid;
	}
	
	public java.util.List<String> getPK() {
		java.util.List<String> pk=new java.util.ArrayList<String>();
			pk.add("idChiamata");	
	    return pk;
	}
	
	public String toString() {	
		
	    String temp = "";
	    StringBuffer sb = new StringBuffer();
	    sb.append("\t\n" + this.getClass().getName() + "\t\n");
	    
	    temp = DataFilter.removeNull( idChiamata);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idChiamata: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idAttoLiquidazione);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idAttoLiquidazione: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( esitoElaboraDocumento);
	    if (!DataFilter.isEmpty(temp)) sb.append(" esitoElaboraDocumento: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( erroreElaboraDocumento);
	    if (!DataFilter.isEmpty(temp)) sb.append(" erroreElaboraDocumento: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtElaboraDocumento);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtElaboraDocumento: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idOperazioneAsincrona);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idOperazioneAsincrona: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( esitoLeggiStato);
	    if (!DataFilter.isEmpty(temp)) sb.append(" esitoLeggiStato: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( statoElaborazione);
	    if (!DataFilter.isEmpty(temp)) sb.append(" statoElaborazione: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( erroreLeggiStato);
	    if (!DataFilter.isEmpty(temp)) sb.append(" erroreLeggiStato: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtLeggiStato);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtLeggiStato: " + temp + "\t\n");
	    
	    return sb.toString();
	}

	public BigDecimal getIdChiamata() {
		return idChiamata;
	}

	public void setIdChiamata(BigDecimal idChiamata) {
		this.idChiamata = idChiamata;
	}

	public BigDecimal getIdAttoLiquidazione() {
		return idAttoLiquidazione;
	}

	public void setIdAttoLiquidazione(BigDecimal idAttoLiquidazione) {
		this.idAttoLiquidazione = idAttoLiquidazione;
	}

	public String getEsitoElaboraDocumento() {
		return esitoElaboraDocumento;
	}

	public void setEsitoElaboraDocumento(String esitoElaboraDocumento) {
		this.esitoElaboraDocumento = esitoElaboraDocumento;
	}

	public String getErroreElaboraDocumento() {
		return erroreElaboraDocumento;
	}

	public void setErroreElaboraDocumento(String erroreElaboraDocumento) {
		this.erroreElaboraDocumento = erroreElaboraDocumento;
	}

	public Date getDtElaboraDocumento() {
		return dtElaboraDocumento;
	}

	public void setDtElaboraDocumento(Date dtElaboraDocumento) {
		this.dtElaboraDocumento = dtElaboraDocumento;
	}

	public BigDecimal getIdOperazioneAsincrona() {
		return idOperazioneAsincrona;
	}

	public void setIdOperazioneAsincrona(BigDecimal idOperazioneAsincrona) {
		this.idOperazioneAsincrona = idOperazioneAsincrona;
	}

	public String getEsitoLeggiStato() {
		return esitoLeggiStato;
	}

	public void setEsitoLeggiStato(String esitoLeggiStato) {
		this.esitoLeggiStato = esitoLeggiStato;
	}

	public String getStatoElaborazione() {
		return statoElaborazione;
	}

	public void setStatoElaborazione(String statoElaborazione) {
		this.statoElaborazione = statoElaborazione;
	}

	public String getErroreLeggiStato() {
		return erroreLeggiStato;
	}

	public void setErroreLeggiStato(String erroreLeggiStato) {
		this.erroreLeggiStato = erroreLeggiStato;
	}

	public Date getDtLeggiStato() {
		return dtLeggiStato;
	}

	public void setDtLeggiStato(Date dtLeggiStato) {
		this.dtLeggiStato = dtLeggiStato;
	}

	public String getRequestelaboradocumento() {
		return requestelaboradocumento;
	}

	public void setRequestelaboradocumento(String requestelaboradocumento) {
		this.requestelaboradocumento = requestelaboradocumento;
	}
	
}
