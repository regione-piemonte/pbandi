/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo;

import it.csi.pbandi.pbservizit.pbandisrv.integration.util.DataFilter;

import java.math.BigDecimal;

public class PbandiRCampionamentoVO extends GenericVO{
	
	/**
	 	id_campione, 
		progr_operazione, 
		id_progetto, 
		avanzamento
	 */
	
	private BigDecimal idCampione;
	private BigDecimal progrOperazione;
	private BigDecimal idProgetto;
	private BigDecimal avanzamento;
	private String asse;
	private String titoloProgetto;
	private String universo;
	
	public PbandiRCampionamentoVO(){}
	
	public PbandiRCampionamentoVO(BigDecimal idCampione, BigDecimal idProgetto){
		this.idCampione = idCampione;
		this.idProgetto = idProgetto;
	}
	
	public BigDecimal getIdCampione() {
		return idCampione;
	}
	public void setIdCampione(BigDecimal idCampione) {
		this.idCampione = idCampione;
	}
	
	
	public BigDecimal getProgrOperazione() {
		return progrOperazione;
	}

	public void setProgrOperazione(BigDecimal progrOperazione) {
		this.progrOperazione = progrOperazione;
	}

	public BigDecimal getIdProgetto() {
		return idProgetto;
	}
	public void setIdProgetto(BigDecimal idProgetto) {
		this.idProgetto = idProgetto;
	}
	public BigDecimal getAvanzamento() {
		return avanzamento;
	}
	public void setAvanzamento(BigDecimal avanzamento) {
		this.avanzamento = avanzamento;
	}
	
	public boolean isValid() {
		boolean isValid = false;
                if (isPKValid() && progrOperazione != null) {
   			isValid = true;
   		}
   		return isValid;
	}
	public boolean isPKValid() {
		boolean isPkValid = false;
		if (idCampione != null && idProgetto != null ) {
   			isPkValid = true;
   		}

   		return isPkValid;
	}
	
	public String toString() {
		
	    String temp = "";
	    StringBuffer sb = new StringBuffer();
	    sb.append("\t\n" + this.getClass().getName() + "\t\n");
	    
	    temp = DataFilter.removeNull( idCampione);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idCampione: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idProgetto);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idProgetto: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( progrOperazione);
	    if (!DataFilter.isEmpty(temp)) sb.append(" progrOperazione: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( avanzamento);
	    if (!DataFilter.isEmpty(temp)) sb.append(" avanzamento: " + temp + "\t\n");
	    
	    return sb.toString();
	}
	
	public java.util.List getPK() {
		java.util.List pk=new java.util.ArrayList();
		
			pk.add("idCampione");
		
			pk.add("idProgetto");
		
	    return pk;
	}

	public String getAsse() {
		return asse;
	}

	public void setAsse(String asse) {
		this.asse = asse;
	}

	public String getTitoloProgetto() {
		return titoloProgetto;
	}

	public void setTitoloProgetto(String titoloProgetto) {
		this.titoloProgetto = titoloProgetto;
	}

	public String getUniverso() {
		return universo;
	}

	public void setUniverso(String universo) {
		this.universo = universo;
	}
}
