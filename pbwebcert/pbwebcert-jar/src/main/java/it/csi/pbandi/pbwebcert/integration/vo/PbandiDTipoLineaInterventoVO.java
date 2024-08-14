/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebcert.integration.vo;

import java.math.BigDecimal;
import java.sql.Date;

public class PbandiDTipoLineaInterventoVO {
private String codTipoLinea;
  	
  	private Date dtFineValidita;
  	
  	private BigDecimal idTipoLineaIntervento;
  	
  	private Date dtInizioValidita;
  	
  	private String descTipoLineaFas;
  	
  	private BigDecimal livelloTipoLinea;
  	
  	private String descTipoLinea;
  	
	public PbandiDTipoLineaInterventoVO() {}
  	
	public PbandiDTipoLineaInterventoVO (BigDecimal idTipoLineaIntervento) {
	
		this. idTipoLineaIntervento =  idTipoLineaIntervento;
	}
  	
	public PbandiDTipoLineaInterventoVO (String codTipoLinea, Date dtFineValidita, BigDecimal idTipoLineaIntervento, Date dtInizioValidita, String descTipoLineaFas, BigDecimal livelloTipoLinea, String descTipoLinea) {
	
		this. codTipoLinea =  codTipoLinea;
		this. dtFineValidita =  dtFineValidita;
		this. idTipoLineaIntervento =  idTipoLineaIntervento;
		this. dtInizioValidita =  dtInizioValidita;
		this. descTipoLineaFas =  descTipoLineaFas;
		this. livelloTipoLinea =  livelloTipoLinea;
		this. descTipoLinea =  descTipoLinea;
	}
  	
  	
	public String getCodTipoLinea() {
		return codTipoLinea;
	}
	
	public void setCodTipoLinea(String codTipoLinea) {
		this.codTipoLinea = codTipoLinea;
	}
	
	public Date getDtFineValidita() {
		return dtFineValidita;
	}
	
	public void setDtFineValidita(Date dtFineValidita) {
		this.dtFineValidita = dtFineValidita;
	}
	
	public BigDecimal getIdTipoLineaIntervento() {
		return idTipoLineaIntervento;
	}
	
	public void setIdTipoLineaIntervento(BigDecimal idTipoLineaIntervento) {
		this.idTipoLineaIntervento = idTipoLineaIntervento;
	}
	
	public Date getDtInizioValidita() {
		return dtInizioValidita;
	}
	
	public void setDtInizioValidita(Date dtInizioValidita) {
		this.dtInizioValidita = dtInizioValidita;
	}
	
	public String getDescTipoLineaFas() {
		return descTipoLineaFas;
	}
	
	public void setDescTipoLineaFas(String descTipoLineaFas) {
		this.descTipoLineaFas = descTipoLineaFas;
	}
	
	public BigDecimal getLivelloTipoLinea() {
		return livelloTipoLinea;
	}
	
	public void setLivelloTipoLinea(BigDecimal livelloTipoLinea) {
		this.livelloTipoLinea = livelloTipoLinea;
	}
	
	public String getDescTipoLinea() {
		return descTipoLinea;
	}
	
	public void setDescTipoLinea(String descTipoLinea) {
		this.descTipoLinea = descTipoLinea;
	}
	
	public boolean isValid() {
		boolean isValid = false;
                if (isPKValid() && codTipoLinea != null && dtInizioValidita != null && descTipoLineaFas != null && livelloTipoLinea != null && descTipoLinea != null) {
   			isValid = true;
   		}
   		return isValid;
	}
	public boolean isPKValid() {
		boolean isPkValid = false;
		if (idTipoLineaIntervento != null ) {
   			isPkValid = true;
   		}

   		return isPkValid;
	}
	
//	public String toString() {
//		
//	    String temp = "";
//	    StringBuffer sb = new StringBuffer();
//	    sb.append("\t\n" + this.getClass().getName() + "\t\n");
//	    
//	    temp = DataFilter.removeNull( codTipoLinea);
//	    if (!DataFilter.isEmpty(temp)) sb.append(" codTipoLinea: " + temp + "\t\n");
//	    
//	    temp = DataFilter.removeNull( dtFineValidita);
//	    if (!DataFilter.isEmpty(temp)) sb.append(" dtFineValidita: " + temp + "\t\n");
//	    
//	    temp = DataFilter.removeNull( idTipoLineaIntervento);
//	    if (!DataFilter.isEmpty(temp)) sb.append(" idTipoLineaIntervento: " + temp + "\t\n");
//	    
//	    temp = DataFilter.removeNull( dtInizioValidita);
//	    if (!DataFilter.isEmpty(temp)) sb.append(" dtInizioValidita: " + temp + "\t\n");
//	    
//	    temp = DataFilter.removeNull( descTipoLineaFas);
//	    if (!DataFilter.isEmpty(temp)) sb.append(" descTipoLineaFas: " + temp + "\t\n");
//	    
//	    temp = DataFilter.removeNull( livelloTipoLinea);
//	    if (!DataFilter.isEmpty(temp)) sb.append(" livelloTipoLinea: " + temp + "\t\n");
//	    
//	    temp = DataFilter.removeNull( descTipoLinea);
//	    if (!DataFilter.isEmpty(temp)) sb.append(" descTipoLinea: " + temp + "\t\n");
//	    
//	    return sb.toString();
//	}
	
	public java.util.List<String> getPK() {
		java.util.List<String> pk=new java.util.ArrayList<String>();
		
			pk.add("idTipoLineaIntervento");
		
	    return pk;
	}
}
