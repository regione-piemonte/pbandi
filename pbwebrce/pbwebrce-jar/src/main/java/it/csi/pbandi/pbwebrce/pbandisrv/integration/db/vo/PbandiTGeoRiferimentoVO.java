/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/


package it.csi.pbandi.pbwebrce.pbandisrv.integration.db.vo;

import java.math.*;
import java.sql.Date;
import it.csi.pbandi.pbwebrce.pbandisrv.integration.util.DataFilter;



public class PbandiTGeoRiferimentoVO extends GenericVO {

  	
  	private BigDecimal idZonaCensuaria;
  	
  	private BigDecimal coordY;
  	
  	private Date dtInizioValidita;
  	
  	private BigDecimal idUtenteAgg;
  	
  	private BigDecimal coordX;
  	
  	private String strutturale;
  	
  	private BigDecimal idTipoInsCoordinate;
  	
  	private BigDecimal idGeoRiferimento;
  	
  	private Date dtFineValidita;
  	
  	private Date dtUltimaValidZonaCensuaria;
  	
  	private BigDecimal idUtenteIns;
  	
	public PbandiTGeoRiferimentoVO() {}
  	
	public PbandiTGeoRiferimentoVO (BigDecimal idGeoRiferimento) {
	
		this. idGeoRiferimento =  idGeoRiferimento;
	}
  	
	public PbandiTGeoRiferimentoVO (BigDecimal idZonaCensuaria, BigDecimal coordY, Date dtInizioValidita, BigDecimal idUtenteAgg, BigDecimal coordX, String strutturale, BigDecimal idTipoInsCoordinate, BigDecimal idGeoRiferimento, Date dtFineValidita, Date dtUltimaValidZonaCensuaria, BigDecimal idUtenteIns) {
	
		this. idZonaCensuaria =  idZonaCensuaria;
		this. coordY =  coordY;
		this. dtInizioValidita =  dtInizioValidita;
		this. idUtenteAgg =  idUtenteAgg;
		this. coordX =  coordX;
		this. strutturale =  strutturale;
		this. idTipoInsCoordinate =  idTipoInsCoordinate;
		this. idGeoRiferimento =  idGeoRiferimento;
		this. dtFineValidita =  dtFineValidita;
		this. dtUltimaValidZonaCensuaria =  dtUltimaValidZonaCensuaria;
		this. idUtenteIns =  idUtenteIns;
	}
  	
  	
	public BigDecimal getIdZonaCensuaria() {
		return idZonaCensuaria;
	}
	
	public void setIdZonaCensuaria(BigDecimal idZonaCensuaria) {
		this.idZonaCensuaria = idZonaCensuaria;
	}
	
	public BigDecimal getCoordY() {
		return coordY;
	}
	
	public void setCoordY(BigDecimal coordY) {
		this.coordY = coordY;
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
	
	public BigDecimal getCoordX() {
		return coordX;
	}
	
	public void setCoordX(BigDecimal coordX) {
		this.coordX = coordX;
	}
	
	public String getStrutturale() {
		return strutturale;
	}
	
	public void setStrutturale(String strutturale) {
		this.strutturale = strutturale;
	}
	
	public BigDecimal getIdTipoInsCoordinate() {
		return idTipoInsCoordinate;
	}
	
	public void setIdTipoInsCoordinate(BigDecimal idTipoInsCoordinate) {
		this.idTipoInsCoordinate = idTipoInsCoordinate;
	}
	
	public BigDecimal getIdGeoRiferimento() {
		return idGeoRiferimento;
	}
	
	public void setIdGeoRiferimento(BigDecimal idGeoRiferimento) {
		this.idGeoRiferimento = idGeoRiferimento;
	}
	
	public Date getDtFineValidita() {
		return dtFineValidita;
	}
	
	public void setDtFineValidita(Date dtFineValidita) {
		this.dtFineValidita = dtFineValidita;
	}
	
	public Date getDtUltimaValidZonaCensuaria() {
		return dtUltimaValidZonaCensuaria;
	}
	
	public void setDtUltimaValidZonaCensuaria(Date dtUltimaValidZonaCensuaria) {
		this.dtUltimaValidZonaCensuaria = dtUltimaValidZonaCensuaria;
	}
	
	public BigDecimal getIdUtenteIns() {
		return idUtenteIns;
	}
	
	public void setIdUtenteIns(BigDecimal idUtenteIns) {
		this.idUtenteIns = idUtenteIns;
	}
	
	public boolean isValid() {
		boolean isValid = false;
                if (isPKValid() && coordY != null && dtInizioValidita != null && coordX != null && idTipoInsCoordinate != null && idUtenteIns != null) {
   			isValid = true;
   		}
   		return isValid;
	}
	public boolean isPKValid() {
		boolean isPkValid = false;
		if (idGeoRiferimento != null ) {
   			isPkValid = true;
   		}

   		return isPkValid;
	}
	
	public String toString() {
		
	    String temp = "";
	    StringBuffer sb = new StringBuffer();
	    sb.append("\t\n" + this.getClass().getName() + "\t\n");
	    
	    temp = DataFilter.removeNull( idZonaCensuaria);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idZonaCensuaria: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( coordY);
	    if (!DataFilter.isEmpty(temp)) sb.append(" coordY: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtInizioValidita);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtInizioValidita: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idUtenteAgg);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idUtenteAgg: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( coordX);
	    if (!DataFilter.isEmpty(temp)) sb.append(" coordX: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( strutturale);
	    if (!DataFilter.isEmpty(temp)) sb.append(" strutturale: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idTipoInsCoordinate);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idTipoInsCoordinate: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idGeoRiferimento);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idGeoRiferimento: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtFineValidita);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtFineValidita: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtUltimaValidZonaCensuaria);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtUltimaValidZonaCensuaria: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idUtenteIns);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idUtenteIns: " + temp + "\t\n");
	    
	    return sb.toString();
	}
	
	public java.util.List<String> getPK() {
		java.util.List<String> pk=new java.util.ArrayList<String>();
		
			pk.add("idGeoRiferimento");
		
	    return pk;
	}
	
	
}
