/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/


package it.csi.pbandi.pbwebrce.pbandisrv.integration.db.vo;

import java.math.*;
import java.sql.Date;
import it.csi.pbandi.pbwebrce.pbandisrv.integration.util.DataFilter;



public class PbandiRDichSpesaDocAllegVO extends GenericVO {

  	
  	private BigDecimal idDichiarazioneSpesa;
  	
  	private BigDecimal idMicroSezioneModulo;
  	
  	private BigDecimal numOrdinamentoMicroSezione;
  	
  	private String flagAllegato;
  	  	
	public PbandiRDichSpesaDocAllegVO() {}
  	
	public PbandiRDichSpesaDocAllegVO (BigDecimal idDichiarazioneSpesa, BigDecimal idMicroSezioneModulo, BigDecimal numOrdinamentoMicroSezione) {
	
		this. idDichiarazioneSpesa =  idDichiarazioneSpesa;
		this. idMicroSezioneModulo =  idMicroSezioneModulo;
		this. numOrdinamentoMicroSezione = numOrdinamentoMicroSezione;

	}
  	
	public PbandiRDichSpesaDocAllegVO (BigDecimal idDichiarazioneSpesa, 
			BigDecimal idMicroSezioneModulo, String flagAllegato, 
			BigDecimal idUtenteIns, BigDecimal numOrdinamentoMicroSezione,BigDecimal idProgetto) {
	
		this. idDichiarazioneSpesa =  idDichiarazioneSpesa;
		this. idMicroSezioneModulo =  idMicroSezioneModulo;
		this. flagAllegato =  flagAllegato;
		this. numOrdinamentoMicroSezione = numOrdinamentoMicroSezione;
	}
  	
  	
	public BigDecimal getidDichiarazioneSpesa() {
		return idDichiarazioneSpesa;
	}
	
	public void setidDichiarazioneSpesa(BigDecimal idDichiarazioneSpesa) {
		this.idDichiarazioneSpesa = idDichiarazioneSpesa;
	}
	
	public BigDecimal getidMicroSezioneModulo() {
		return idMicroSezioneModulo;
	}
	
	public void setidMicroSezioneModulo(BigDecimal idMicroSezioneModulo) {
		this.idMicroSezioneModulo = idMicroSezioneModulo;
	}
	
	public String getflagAllegato() {
		return flagAllegato;
	}
	
	public void setflagAllegato(String flagAllegato) {
		this.flagAllegato = flagAllegato;
	}
	
	public BigDecimal getNumOrdinamentoMicroSezione() {
		return numOrdinamentoMicroSezione;
	}

	public void setNumOrdinamentoMicroSezione(BigDecimal numOrdinamentoMicroSezione) {
		this.numOrdinamentoMicroSezione = numOrdinamentoMicroSezione;
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
		if (idDichiarazioneSpesa != null && idMicroSezioneModulo != null && numOrdinamentoMicroSezione != null) {
   			isPkValid = true;
   		}

   		return isPkValid;
	}
	
	public String toString() {
		
	    String temp = "";
	    StringBuffer sb = new StringBuffer();
	    sb.append("\t\n" + this.getClass().getName() + "\t\n");
	    
	    temp = DataFilter.removeNull( idDichiarazioneSpesa);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idDichiarazioneSpesa: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idMicroSezioneModulo);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idMicroSezioneModulo: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( numOrdinamentoMicroSezione);
	    if(!DataFilter.isEmpty(temp)) sb.append(" numOrdinamentoMicroSezione: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( flagAllegato);
	    if (!DataFilter.isEmpty(temp)) sb.append(" flagAllegato: " + temp + "\t\n");
	    
	    return sb.toString();
	}
	
	public java.util.List<String> getPK() {
		java.util.List<String> pk=new java.util.ArrayList<String>();
		
			pk.add("idDichiarazioneSpesa");
			
			pk.add("idMicroSezioneModulo");
			
			pk.add("numOrdinamentoMicroSezione");

	    return pk;
	}
	
	
	
	
}
