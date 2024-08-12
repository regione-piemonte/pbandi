
package it.csi.pbandi.pbweb.pbandisrv.integration.db.vo;

import java.math.*;
import java.sql.Date;
import it.csi.pbandi.pbweb.pbandisrv.integration.util.DataFilter;



public class PbandiTComunicazFineProgVO extends GenericVO {

  	
  	private BigDecimal idUtenteAgg;
  	
  	private String noteComunicazFineProgetto;
  	
  	private BigDecimal idComunicazFineProg;
  	
  	private BigDecimal idUtenteIns;
  	
  	private BigDecimal importoRichErogazioneSaldo;
  	
  	private BigDecimal idProgetto;
  	
  	private Date dtComunicazione;
  	
	public PbandiTComunicazFineProgVO() {}
  	
	public PbandiTComunicazFineProgVO (BigDecimal idComunicazFineProg) {
	
		this. idComunicazFineProg =  idComunicazFineProg;
	}
  	
	public PbandiTComunicazFineProgVO (BigDecimal idUtenteAgg, String noteComunicazFineProgetto, BigDecimal idComunicazFineProg, BigDecimal idUtenteIns, BigDecimal importoRichErogazioneSaldo, BigDecimal idProgetto, Date dtComunicazione) {
	
		this. idUtenteAgg =  idUtenteAgg;
		this. noteComunicazFineProgetto =  noteComunicazFineProgetto;
		this. idComunicazFineProg =  idComunicazFineProg;
		this. idUtenteIns =  idUtenteIns;
		this. importoRichErogazioneSaldo =  importoRichErogazioneSaldo;
		this. idProgetto =  idProgetto;
		this. dtComunicazione =  dtComunicazione;
	}
  	
  	
	public BigDecimal getIdUtenteAgg() {
		return idUtenteAgg;
	}
	
	public void setIdUtenteAgg(BigDecimal idUtenteAgg) {
		this.idUtenteAgg = idUtenteAgg;
	}
	
	public String getNoteComunicazFineProgetto() {
		return noteComunicazFineProgetto;
	}
	
	public void setNoteComunicazFineProgetto(String noteComunicazFineProgetto) {
		this.noteComunicazFineProgetto = noteComunicazFineProgetto;
	}
	
	public BigDecimal getIdComunicazFineProg() {
		return idComunicazFineProg;
	}
	
	public void setIdComunicazFineProg(BigDecimal idComunicazFineProg) {
		this.idComunicazFineProg = idComunicazFineProg;
	}
	
	public BigDecimal getIdUtenteIns() {
		return idUtenteIns;
	}
	
	public void setIdUtenteIns(BigDecimal idUtenteIns) {
		this.idUtenteIns = idUtenteIns;
	}
	
	public BigDecimal getImportoRichErogazioneSaldo() {
		return importoRichErogazioneSaldo;
	}
	
	public void setImportoRichErogazioneSaldo(BigDecimal importoRichErogazioneSaldo) {
		this.importoRichErogazioneSaldo = importoRichErogazioneSaldo;
	}
	
	public BigDecimal getIdProgetto() {
		return idProgetto;
	}
	
	public void setIdProgetto(BigDecimal idProgetto) {
		this.idProgetto = idProgetto;
	}
	
	public Date getDtComunicazione() {
		return dtComunicazione;
	}
	
	public void setDtComunicazione(Date dtComunicazione) {
		this.dtComunicazione = dtComunicazione;
	}
	
	public boolean isValid() {
		boolean isValid = false;
                if (isPKValid() && idUtenteIns != null && idProgetto != null && dtComunicazione != null) {
   			isValid = true;
   		}
   		return isValid;
	}
	public boolean isPKValid() {
		boolean isPkValid = false;
		if (idComunicazFineProg != null ) {
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
	    
	    temp = DataFilter.removeNull( noteComunicazFineProgetto);
	    if (!DataFilter.isEmpty(temp)) sb.append(" noteComunicazFineProgetto: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idComunicazFineProg);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idComunicazFineProg: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idUtenteIns);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idUtenteIns: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( importoRichErogazioneSaldo);
	    if (!DataFilter.isEmpty(temp)) sb.append(" importoRichErogazioneSaldo: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idProgetto);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idProgetto: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtComunicazione);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtComunicazione: " + temp + "\t\n");
	    
	    return sb.toString();
	}
	
	public java.util.List<String> getPK() {
		java.util.List<String> pk=new java.util.ArrayList<String>();
		
			pk.add("idComunicazFineProg");
		
	    return pk;
	}	
	
}
