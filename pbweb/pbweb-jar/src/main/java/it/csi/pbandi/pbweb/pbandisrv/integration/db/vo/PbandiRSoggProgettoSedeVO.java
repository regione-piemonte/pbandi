/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/


package it.csi.pbandi.pbweb.pbandisrv.integration.db.vo;

import java.math.*;
import java.sql.Date;
import it.csi.pbandi.pbweb.pbandisrv.integration.util.DataFilter;



public class PbandiRSoggProgettoSedeVO extends GenericVO {

  	
  	private BigDecimal progrSoggettoProgettoSede;
  	
  	private BigDecimal progrSoggettoProgetto;
  	
  	private BigDecimal idUtenteAgg;
  	
  	private BigDecimal idRecapiti;
  	
  	private BigDecimal idUtenteIns;
  	
  	private BigDecimal idTipoSede;
  	
  	private BigDecimal idIndirizzo;
  	
  	private BigDecimal idSede;
  	
  	private String flagSedeAmministrativa;
  	
	public PbandiRSoggProgettoSedeVO() {}
  	
	public PbandiRSoggProgettoSedeVO (BigDecimal progrSoggettoProgettoSede) {
	
		this. progrSoggettoProgettoSede =  progrSoggettoProgettoSede;
	}
  	
	public PbandiRSoggProgettoSedeVO (BigDecimal progrSoggettoProgettoSede, BigDecimal progrSoggettoProgetto, BigDecimal idUtenteAgg, BigDecimal idRecapiti, BigDecimal idUtenteIns, BigDecimal idTipoSede, BigDecimal idIndirizzo, BigDecimal idSede, String flagSedeAmministrativa) {
	
		this. progrSoggettoProgettoSede =  progrSoggettoProgettoSede;
		this. progrSoggettoProgetto =  progrSoggettoProgetto;
		this. idUtenteAgg =  idUtenteAgg;
		this. idRecapiti =  idRecapiti;
		this. idUtenteIns =  idUtenteIns;
		this. idTipoSede =  idTipoSede;
		this. idIndirizzo =  idIndirizzo;
		this. idSede =  idSede;
		this. flagSedeAmministrativa = flagSedeAmministrativa;
	}
  	
  	
	public BigDecimal getProgrSoggettoProgettoSede() {
		return progrSoggettoProgettoSede;
	}
	
	public void setProgrSoggettoProgettoSede(BigDecimal progrSoggettoProgettoSede) {
		this.progrSoggettoProgettoSede = progrSoggettoProgettoSede;
	}
	
	public BigDecimal getProgrSoggettoProgetto() {
		return progrSoggettoProgetto;
	}
	
	public void setProgrSoggettoProgetto(BigDecimal progrSoggettoProgetto) {
		this.progrSoggettoProgetto = progrSoggettoProgetto;
	}
	
	public BigDecimal getIdUtenteAgg() {
		return idUtenteAgg;
	}
	
	public void setIdUtenteAgg(BigDecimal idUtenteAgg) {
		this.idUtenteAgg = idUtenteAgg;
	}
	
	public BigDecimal getIdRecapiti() {
		return idRecapiti;
	}
	
	public void setIdRecapiti(BigDecimal idRecapiti) {
		this.idRecapiti = idRecapiti;
	}
	
	public BigDecimal getIdUtenteIns() {
		return idUtenteIns;
	}
	
	public void setIdUtenteIns(BigDecimal idUtenteIns) {
		this.idUtenteIns = idUtenteIns;
	}
	
	public BigDecimal getIdTipoSede() {
		return idTipoSede;
	}
	
	public void setIdTipoSede(BigDecimal idTipoSede) {
		this.idTipoSede = idTipoSede;
	}
	
	public BigDecimal getIdIndirizzo() {
		return idIndirizzo;
	}
	
	public void setIdIndirizzo(BigDecimal idIndirizzo) {
		this.idIndirizzo = idIndirizzo;
	}
	
	public BigDecimal getIdSede() {
		return idSede;
	}
	
	public void setIdSede(BigDecimal idSede) {
		this.idSede = idSede;
	}
	
	public String getFlagSedeAmministrativa() {
		return flagSedeAmministrativa;
	}

	public void setFlagSedeAmministrativa(String flagSedeAmministrativa) {
		this.flagSedeAmministrativa = flagSedeAmministrativa;
	}

	public boolean isValid() {
		boolean isValid = false;
                if (isPKValid() && progrSoggettoProgetto != null && idUtenteIns != null && idTipoSede != null && idSede != null) {
   			isValid = true;
   		}
   		return isValid;
	}
	public boolean isPKValid() {
		boolean isPkValid = false;
		if (progrSoggettoProgettoSede != null ) {
   			isPkValid = true;
   		}

   		return isPkValid;
	}
	
	public String toString() {
		
	    String temp = "";
	    StringBuffer sb = new StringBuffer();
	    sb.append("\t\n" + this.getClass().getName() + "\t\n");
	    
	    temp = DataFilter.removeNull( progrSoggettoProgettoSede);
	    if (!DataFilter.isEmpty(temp)) sb.append(" progrSoggettoProgettoSede: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( progrSoggettoProgetto);
	    if (!DataFilter.isEmpty(temp)) sb.append(" progrSoggettoProgetto: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idUtenteAgg);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idUtenteAgg: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idRecapiti);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idRecapiti: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idUtenteIns);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idUtenteIns: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idTipoSede);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idTipoSede: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idIndirizzo);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idIndirizzo: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idSede);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idSede: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( flagSedeAmministrativa);
	    if(!DataFilter.isEmpty(temp)) sb.append(" flagSedeAmministrativa: " +  temp + "\t\n");
	    
	    return sb.toString();
	}
	
	public java.util.List<String> getPK() {
		java.util.List<String> pk=new java.util.ArrayList<String>();
		
			pk.add("progrSoggettoProgettoSede");
		
	    return pk;
	}
	
	
}
