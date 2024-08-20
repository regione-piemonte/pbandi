/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/


package it.csi.pbandi.pbwebrce.pbandisrv.integration.db.vo;

import java.math.*;
import java.sql.Date;
import it.csi.pbandi.pbwebrce.pbandisrv.integration.util.DataFilter;



public class PbandiRSoggettoDomandaSedeVO extends GenericVO {

  	
  	private BigDecimal idUtenteAgg;
  	
  	private BigDecimal progrSoggettoDomanda;
  	
  	private BigDecimal idRecapiti;
  	
  	private BigDecimal idUtenteIns;
  	
  	private BigDecimal idTipoSede;
  	
  	private BigDecimal idIndirizzo;
  	
  	private BigDecimal idSede;
  	
  	private BigDecimal progrSoggettoDomandaSede;
  	
	public PbandiRSoggettoDomandaSedeVO() {}
  	
	public PbandiRSoggettoDomandaSedeVO (BigDecimal progrSoggettoDomandaSede) {
	
		this. progrSoggettoDomandaSede =  progrSoggettoDomandaSede;
	}
  	
	public PbandiRSoggettoDomandaSedeVO (BigDecimal idUtenteAgg, BigDecimal progrSoggettoDomanda, BigDecimal idRecapiti, BigDecimal idUtenteIns, BigDecimal idTipoSede, BigDecimal idIndirizzo, BigDecimal idSede, BigDecimal progrSoggettoDomandaSede) {
	
		this. idUtenteAgg =  idUtenteAgg;
		this. progrSoggettoDomanda =  progrSoggettoDomanda;
		this. idRecapiti =  idRecapiti;
		this. idUtenteIns =  idUtenteIns;
		this. idTipoSede =  idTipoSede;
		this. idIndirizzo =  idIndirizzo;
		this. idSede =  idSede;
		this. progrSoggettoDomandaSede =  progrSoggettoDomandaSede;
	}
  	
  	
	public BigDecimal getIdUtenteAgg() {
		return idUtenteAgg;
	}
	
	public void setIdUtenteAgg(BigDecimal idUtenteAgg) {
		this.idUtenteAgg = idUtenteAgg;
	}
	
	public BigDecimal getProgrSoggettoDomanda() {
		return progrSoggettoDomanda;
	}
	
	public void setProgrSoggettoDomanda(BigDecimal progrSoggettoDomanda) {
		this.progrSoggettoDomanda = progrSoggettoDomanda;
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
	
	public BigDecimal getProgrSoggettoDomandaSede() {
		return progrSoggettoDomandaSede;
	}
	
	public void setProgrSoggettoDomandaSede(BigDecimal progrSoggettoDomandaSede) {
		this.progrSoggettoDomandaSede = progrSoggettoDomandaSede;
	}
	
	public boolean isValid() {
		boolean isValid = false;
                if (isPKValid() && progrSoggettoDomanda != null && idUtenteIns != null && idTipoSede != null && idSede != null) {
   			isValid = true;
   		}
   		return isValid;
	}
	public boolean isPKValid() {
		boolean isPkValid = false;
		if (progrSoggettoDomandaSede != null ) {
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
	    
	    temp = DataFilter.removeNull( progrSoggettoDomanda);
	    if (!DataFilter.isEmpty(temp)) sb.append(" progrSoggettoDomanda: " + temp + "\t\n");
	    
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
	    
	    temp = DataFilter.removeNull( progrSoggettoDomandaSede);
	    if (!DataFilter.isEmpty(temp)) sb.append(" progrSoggettoDomandaSede: " + temp + "\t\n");
	    
	    return sb.toString();
	}
	
	public java.util.List<String> getPK() {
		java.util.List<String> pk=new java.util.ArrayList<String>();
		
			pk.add("progrSoggettoDomandaSede");
		
	    return pk;
	}
	
	
}
