/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/


package it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo;

import java.math.*;
import it.csi.pbandi.pbservizit.pbandisrv.integration.util.DataFilter;

public class PbandiRDsDettRevocaIrregVO extends GenericVO {
  	
	private BigDecimal idDettRevocaIrreg;  	
	private BigDecimal idDichiarazioneSpesa;
  	private BigDecimal importoIrregolareDs;
  	
	public PbandiRDsDettRevocaIrregVO() {}
  	
	public PbandiRDsDettRevocaIrregVO (BigDecimal idDettRevocaIrreg, BigDecimal idDichiarazioneSpesa, BigDecimal importoIrregolareDs) {
		this. idDettRevocaIrreg =  idDettRevocaIrreg;
		this. idDichiarazioneSpesa =  idDichiarazioneSpesa;
		this. importoIrregolareDs =  importoIrregolareDs;
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
		if (idDettRevocaIrreg != null && idDichiarazioneSpesa != null) {
   			isPkValid = true;
   		}
   		return isPkValid;
	}
	
	public java.util.List getPK() {
		java.util.List pk=new java.util.ArrayList();		
			pk.add("idDettRevocaIrreg");
			pk.add("idDichiarazioneSpesa");
	    return pk;
	}
	
	public String toString() {
		
	    String temp = "";
	    StringBuffer sb = new StringBuffer();
	    sb.append("\t\n" + this.getClass().getName() + "\t\n");
	    
	    temp = DataFilter.removeNull( idDettRevocaIrreg);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idDettRevocaIrreg: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idDichiarazioneSpesa);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idDichiarazioneSpesa: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( importoIrregolareDs);
	    if (!DataFilter.isEmpty(temp)) sb.append(" importoIrregolareDs: " + temp + "\t\n");	    
	    
	    return sb.toString();
	}

	public BigDecimal getIdDettRevocaIrreg() {
		return idDettRevocaIrreg;
	}

	public void setIdDettRevocaIrreg(BigDecimal idDettRevocaIrreg) {
		this.idDettRevocaIrreg = idDettRevocaIrreg;
	}

	public BigDecimal getIdDichiarazioneSpesa() {
		return idDichiarazioneSpesa;
	}

	public void setIdDichiarazioneSpesa(BigDecimal idDichiarazioneSpesa) {
		this.idDichiarazioneSpesa = idDichiarazioneSpesa;
	}

	public BigDecimal getImportoIrregolareDs() {
		return importoIrregolareDs;
	}

	public void setImportoIrregolareDs(BigDecimal importoIrregolareDs) {
		this.importoIrregolareDs = importoIrregolareDs;
	}

}
