
package it.csi.pbandi.pbweb.pbandisrv.integration.db.vo;

import java.math.*;
import java.sql.Date;
import it.csi.pbandi.pbweb.pbandisrv.integration.util.DataFilter;



public class PbandiWProgettoDocAllegVO extends GenericVO {
  	
  	private BigDecimal idMicroSezioneModulo;
  	
  	private BigDecimal numOrdinamentoMicroSezione;
  	
  	private String flagAllegato;
  	
  	private BigDecimal idProgetto;
  	
	public PbandiWProgettoDocAllegVO() {}
  	
	public PbandiWProgettoDocAllegVO (BigDecimal numOrdinamentoMicroSezione, BigDecimal idMicroSezioneModulo, BigDecimal idProgetto) {
	
		this. numOrdinamentoMicroSezione = numOrdinamentoMicroSezione;
		this. idMicroSezioneModulo =  idMicroSezioneModulo;
		this. idProgetto = idProgetto;
	}
  	
	public PbandiWProgettoDocAllegVO (
			BigDecimal idMicroSezioneModulo, String flagAllegato, 
			BigDecimal idUtenteIns, BigDecimal numOrdinamentoMicroSezione,BigDecimal idProgetto) {
	
		this. idMicroSezioneModulo =  idMicroSezioneModulo;
		this. flagAllegato =  flagAllegato;
		this. numOrdinamentoMicroSezione = numOrdinamentoMicroSezione;
		this. idProgetto = idProgetto;
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
	
	

	public BigDecimal getIdProgetto() {
		return idProgetto;
	}

	public void setIdProgetto(BigDecimal idProgetto) {
		this.idProgetto = idProgetto;
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
		if (idProgetto != null && idMicroSezioneModulo != null && numOrdinamentoMicroSezione != null) {
   			isPkValid = true;
   		}

   		return isPkValid;
	}
	
	public String toString() {
		
	    String temp = "";
	    StringBuffer sb = new StringBuffer();
	    sb.append("\t\n" + this.getClass().getName() + "\t\n");
	    
	    temp = DataFilter.removeNull( idMicroSezioneModulo);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idMicroSezioneModulo: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( numOrdinamentoMicroSezione);
	    if(!DataFilter.isEmpty(temp)) sb.append(" numOrdinamentoMicroSezione: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( flagAllegato);
	    if (!DataFilter.isEmpty(temp)) sb.append(" flagAllegato: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idProgetto);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idProgetto: " + temp + "\t\n");
	    
	    return sb.toString();
	}
	
	public java.util.List<String> getPK() {
		java.util.List<String> pk=new java.util.ArrayList<String>();
		
			pk.add("idProgetto");
			
			pk.add("idMicroSezioneModulo");
			
			pk.add("numOrdinamentoMicroSezione");

	    return pk;
	}
	
	
	
	
}
