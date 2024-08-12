
package it.csi.pbandi.pbweb.pbandisrv.integration.db.vo;

import java.math.*;
import java.sql.Date;
import it.csi.pbandi.pbweb.pbandisrv.integration.util.DataFilter;



public class PbandiTEsitoControlliVO extends GenericVO {
 	
	private BigDecimal idEsitoControllo;
  	
  	private BigDecimal idProgetto;
  	
  	private String esitoControllo;
  	
  	private BigDecimal idPeriodo;
  	
  	private BigDecimal idCategAnagrafica;
  	
  	private String tipoControlli;
  	
  	private Date dtComunicazione;
  	
  	private Date dataInizioControlli;
  	
  	private Date dataFineControlli;  	 	
  	
  	private String note;
  	
  	private Date dataCampione;
  	
  	
	public PbandiTEsitoControlliVO() {}
  	
	public PbandiTEsitoControlliVO (BigDecimal idEsitoControllo) {
		this. idEsitoControllo =  idEsitoControllo;
	}
  	
	public PbandiTEsitoControlliVO (BigDecimal idEsitoControllo,BigDecimal idProgetto,String esitoControllo,BigDecimal idPeriodo,BigDecimal idCategAnagrafica,String tipoControlli,Date dtComunicazione,Date dataInizioControlli,Date dataFineControlli,String note, Date dataCampione) {
	
		this. idEsitoControllo =  idEsitoControllo;
		this. idProgetto =  idProgetto;
		this. esitoControllo =  esitoControllo;
		this. idPeriodo =  idPeriodo;
		this. idCategAnagrafica =  idCategAnagrafica;
		this. tipoControlli =  tipoControlli;
		this. dtComunicazione =  dtComunicazione;
		this. dataInizioControlli =  dataInizioControlli;
		this. dataFineControlli =  dataFineControlli;
		this. note =  note;
		this.dataCampione = dataCampione; 
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
		if (idEsitoControllo != null ) {
   			isPkValid = true;
   		}
   		return isPkValid;
	}
	
	public String toString() {
		
	    String temp = "";
	    StringBuffer sb = new StringBuffer();
	    sb.append("\t\n" + this.getClass().getName() + "\t\n");
	    
	    temp = DataFilter.removeNull( idEsitoControllo);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idEsitoControllo: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idProgetto);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idProgetto: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( esitoControllo);
	    if (!DataFilter.isEmpty(temp)) sb.append(" esitoControllo: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idPeriodo);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idPeriodo: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idCategAnagrafica);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idCategAnagrafica: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( tipoControlli);
	    if (!DataFilter.isEmpty(temp)) sb.append(" tipoControlli: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtComunicazione);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtComunicazione: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dataInizioControlli);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dataInizioControlli: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dataFineControlli);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dataFineControlli: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( note);
	    if (!DataFilter.isEmpty(temp)) sb.append(" note: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dataCampione);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dataCampione: " + temp + "\t\n");
	    
	    return sb.toString();
	}
	
	public java.util.List<String> getPK() {
		java.util.List<String> pk=new java.util.ArrayList<String>();		
		pk.add("idEsitoControllo");		
	    return pk;
	}

	public BigDecimal getIdEsitoControllo() {
		return idEsitoControllo;
	}

	public void setIdEsitoControllo(BigDecimal idEsitoControllo) {
		this.idEsitoControllo = idEsitoControllo;
	}

	public BigDecimal getIdProgetto() {
		return idProgetto;
	}

	public void setIdProgetto(BigDecimal idProgetto) {
		this.idProgetto = idProgetto;
	}

	public String getEsitoControllo() {
		return esitoControllo;
	}

	public void setEsitoControllo(String esitoControllo) {
		this.esitoControllo = esitoControllo;
	}

	public BigDecimal getIdPeriodo() {
		return idPeriodo;
	}

	public void setIdPeriodo(BigDecimal idPeriodo) {
		this.idPeriodo = idPeriodo;
	}

	public BigDecimal getIdCategAnagrafica() {
		return idCategAnagrafica;
	}

	public void setIdCategAnagrafica(BigDecimal idCategAnagrafica) {
		this.idCategAnagrafica = idCategAnagrafica;
	}

	public String getTipoControlli() {
		return tipoControlli;
	}

	public void setTipoControlli(String tipoControlli) {
		this.tipoControlli = tipoControlli;
	}

	public Date getDtComunicazione() {
		return dtComunicazione;
	}

	public void setDtComunicazione(Date dtComunicazione) {
		this.dtComunicazione = dtComunicazione;
	}

	public Date getDataInizioControlli() {
		return dataInizioControlli;
	}

	public void setDataInizioControlli(Date dataInizioControlli) {
		this.dataInizioControlli = dataInizioControlli;
	}

	public Date getDataFineControlli() {
		return dataFineControlli;
	}

	public void setDataFineControlli(Date dataFineControlli) {
		this.dataFineControlli = dataFineControlli;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public Date getDataCampione() {
		return dataCampione;
	}

	public void setDataCampione(Date dataCampione) {
		this.dataCampione = dataCampione;
	}
	
}
