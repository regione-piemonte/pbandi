
package it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo;

import java.math.*;
import it.csi.pbandi.pbweb.pbandisrv.integration.util.DataFilter;

public class ClassificazioneRaVO extends GenericVO {
  	
 	private BigDecimal idClassificazioneRa;
  	
  	private String codClassificazioneRa;
  	
  	private String descrClassificazioneRa;
  	
  	private BigDecimal idObiettivoTem;
	
	public String toString() {
		
	    String temp = "";
	    StringBuffer sb = new StringBuffer();
	    sb.append("\t\n" + this.getClass().getName() + "\t\n");
	    
	    temp = DataFilter.removeNull( idClassificazioneRa);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idClassificazioneRa: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( codClassificazioneRa);
	    if (!DataFilter.isEmpty(temp)) sb.append(" codClassificazioneRa: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( descrClassificazioneRa);
	    if (!DataFilter.isEmpty(temp)) sb.append(" descrClassificazioneRa: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idObiettivoTem);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idObiettivoTem: " + temp + "\t\n");
	   	    
	    return sb.toString();
	}

	public BigDecimal getIdClassificazioneRa() {
		return idClassificazioneRa;
	}

	public void setIdClassificazioneRa(BigDecimal idClassificazioneRa) {
		this.idClassificazioneRa = idClassificazioneRa;
	}

	public String getCodClassificazioneRa() {
		return codClassificazioneRa;
	}

	public void setCodClassificazioneRa(String codClassificazioneRa) {
		this.codClassificazioneRa = codClassificazioneRa;
	}

	public String getDescrClassificazioneRa() {
		return descrClassificazioneRa;
	}

	public void setDescrClassificazioneRa(String descrClassificazioneRa) {
		this.descrClassificazioneRa = descrClassificazioneRa;
	}

	public BigDecimal getIdObiettivoTem() {
		return idObiettivoTem;
	}

	public void setIdObiettivoTem(BigDecimal idObiettivoTem) {
		this.idObiettivoTem = idObiettivoTem;
	}
	
}
