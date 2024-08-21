/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/


package it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo;

import java.math.*;
import java.sql.Date;
import it.csi.pbandi.pbservizit.pbandisrv.integration.util.DataFilter;



public class PbandiDIndicatoriVO extends GenericVO {

  	
  	private String flagObbligatorio;
  	
  	private Date dtInizioValidita;
  	
  	private BigDecimal idIndicatori;
  	
  	private BigDecimal idTipoIndicatore;
  	
  	private String codIgrue;
  	
  	private Date dtFineValidita;
  	
  	private BigDecimal idLineaDiIntervento;
  	
  	private BigDecimal idUnitaMisura;
  	
  	private String descIndicatore;
  	
	public PbandiDIndicatoriVO() {}
  	
	public PbandiDIndicatoriVO (BigDecimal idIndicatori) {
	
		this. idIndicatori =  idIndicatori;
	}
  	
	public PbandiDIndicatoriVO (String flagObbligatorio, Date dtInizioValidita, BigDecimal idIndicatori, BigDecimal idTipoIndicatore, String codIgrue, Date dtFineValidita, BigDecimal idLineaDiIntervento, BigDecimal idUnitaMisura, String descIndicatore) {
	
		this. flagObbligatorio =  flagObbligatorio;
		this. dtInizioValidita =  dtInizioValidita;
		this. idIndicatori =  idIndicatori;
		this. idTipoIndicatore =  idTipoIndicatore;
		this. codIgrue =  codIgrue;
		this. dtFineValidita =  dtFineValidita;
		this. idLineaDiIntervento =  idLineaDiIntervento;
		this. idUnitaMisura =  idUnitaMisura;
		this. descIndicatore =  descIndicatore;
	}
  	
  	
	public String getFlagObbligatorio() {
		return flagObbligatorio;
	}
	
	public void setFlagObbligatorio(String flagObbligatorio) {
		this.flagObbligatorio = flagObbligatorio;
	}
	
	public Date getDtInizioValidita() {
		return dtInizioValidita;
	}
	
	public void setDtInizioValidita(Date dtInizioValidita) {
		this.dtInizioValidita = dtInizioValidita;
	}
	
	public BigDecimal getIdIndicatori() {
		return idIndicatori;
	}
	
	public void setIdIndicatori(BigDecimal idIndicatori) {
		this.idIndicatori = idIndicatori;
	}
	
	public BigDecimal getIdTipoIndicatore() {
		return idTipoIndicatore;
	}
	
	public void setIdTipoIndicatore(BigDecimal idTipoIndicatore) {
		this.idTipoIndicatore = idTipoIndicatore;
	}
	
	public String getCodIgrue() {
		return codIgrue;
	}
	
	public void setCodIgrue(String codIgrue) {
		this.codIgrue = codIgrue;
	}
	
	public Date getDtFineValidita() {
		return dtFineValidita;
	}
	
	public void setDtFineValidita(Date dtFineValidita) {
		this.dtFineValidita = dtFineValidita;
	}
	
	public BigDecimal getIdLineaDiIntervento() {
		return idLineaDiIntervento;
	}
	
	public void setIdLineaDiIntervento(BigDecimal idLineaDiIntervento) {
		this.idLineaDiIntervento = idLineaDiIntervento;
	}
	
	public BigDecimal getIdUnitaMisura() {
		return idUnitaMisura;
	}
	
	public void setIdUnitaMisura(BigDecimal idUnitaMisura) {
		this.idUnitaMisura = idUnitaMisura;
	}
	
	public String getDescIndicatore() {
		return descIndicatore;
	}
	
	public void setDescIndicatore(String descIndicatore) {
		this.descIndicatore = descIndicatore;
	}
	
	public boolean isValid() {
		boolean isValid = false;
                if (isPKValid() && flagObbligatorio != null && dtInizioValidita != null && idTipoIndicatore != null && codIgrue != null && descIndicatore != null) {
   			isValid = true;
   		}
   		return isValid;
	}
	public boolean isPKValid() {
		boolean isPkValid = false;
		if (idIndicatori != null ) {
   			isPkValid = true;
   		}

   		return isPkValid;
	}
	
	public String toString() {
		
	    String temp = "";
	    StringBuffer sb = new StringBuffer();
	    sb.append("\t\n" + this.getClass().getName() + "\t\n");
	    
	    temp = DataFilter.removeNull( flagObbligatorio);
	    if (!DataFilter.isEmpty(temp)) sb.append(" flagObbligatorio: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtInizioValidita);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtInizioValidita: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idIndicatori);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idIndicatori: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idTipoIndicatore);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idTipoIndicatore: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( codIgrue);
	    if (!DataFilter.isEmpty(temp)) sb.append(" codIgrue: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtFineValidita);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtFineValidita: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idLineaDiIntervento);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idLineaDiIntervento: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idUnitaMisura);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idUnitaMisura: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( descIndicatore);
	    if (!DataFilter.isEmpty(temp)) sb.append(" descIndicatore: " + temp + "\t\n");
	    
	    return sb.toString();
	}
	
	public java.util.List<String> getPK() {
		java.util.List<String> pk=new java.util.ArrayList<String>();
		
			pk.add("idIndicatori");
		
	    return pk;
	}
	
	
}
