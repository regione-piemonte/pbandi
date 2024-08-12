
package it.csi.pbandi.pbweb.pbandisrv.integration.db.vo;

import java.math.*;
import java.sql.Date;
import it.csi.pbandi.pbweb.pbandisrv.integration.util.DataFilter;



public class PbandiTLogValidazFirmaVO extends GenericVO {

  	
  	private String messaggio;
  	
  	private BigDecimal duration;
  	
  	private String flagStatoValidazione;
  	
  	private BigDecimal idUtente;
  	
  	private BigDecimal idDocumentoIndex;
  	
  	private Date dtLog;
  	
  	private BigDecimal idLog;
  	
  	private String metodo;
  	
 	private String codiceErrore;
  	
	private String idMessaggioAppl;
	
	public PbandiTLogValidazFirmaVO() {}
  	
	public PbandiTLogValidazFirmaVO (BigDecimal idLog) {
	
		this. idLog =  idLog;
	}
  	
	public PbandiTLogValidazFirmaVO (String messaggio, BigDecimal duration, String flagStatoValidazione, BigDecimal idUtente, BigDecimal idDocumentoIndex, Date dtLog, BigDecimal idLog,String metodo,String codiceErrore,String idMessaggioAppl) {
	
		this. messaggio =  messaggio;
		this. duration =  duration;
		this. flagStatoValidazione =  flagStatoValidazione;
		this. idUtente =  idUtente;
		this. idDocumentoIndex =  idDocumentoIndex;
		this. dtLog =  dtLog;
		this. idLog =  idLog;
		this. metodo=metodo;
		this. codiceErrore=codiceErrore;
		this. idMessaggioAppl=idMessaggioAppl;
	}
  	
  	
	public String getMessaggio() {
		return messaggio;
	}
	
	public void setMessaggio(String messaggio) {
		this.messaggio = messaggio;
	}
	
	public BigDecimal getDuration() {
		return duration;
	}
	
	public String getIdMessaggioAppl() {
		return idMessaggioAppl;
	}

	public void setIdMessaggioAppl(String idMessaggioAppl) {
		this.idMessaggioAppl = idMessaggioAppl;
	}

	public String getMetodo() {
		return metodo;
	}

	public void setMetodo(String metodo) {
		this.metodo = metodo;
	}

	public void setDuration(BigDecimal duration) {
		this.duration = duration;
	}
	
	public String getFlagStatoValidazione() {
		return flagStatoValidazione;
	}
	
	public void setFlagStatoValidazione(String flagStatoValidazione) {
		this.flagStatoValidazione = flagStatoValidazione;
	}
	
	public String getCodiceErrore() {
		return codiceErrore;
	}

	public void setCodiceErrore(String codiceErrore) {
		this.codiceErrore = codiceErrore;
	}

	public BigDecimal getIdUtente() {
		return idUtente;
	}
	
	public void setIdUtente(BigDecimal idUtente) {
		this.idUtente = idUtente;
	}
	
	public BigDecimal getIdDocumentoIndex() {
		return idDocumentoIndex;
	}
	
	public void setIdDocumentoIndex(BigDecimal idDocumentoIndex) {
		this.idDocumentoIndex = idDocumentoIndex;
	}
	
	public Date getDtLog() {
		return dtLog;
	}
	
	public void setDtLog(Date dtLog) {
		this.dtLog = dtLog;
	}
	
	public BigDecimal getIdLog() {
		return idLog;
	}
	
	public void setIdLog(BigDecimal idLog) {
		this.idLog = idLog;
	}
	
	public boolean isValid() {
		boolean isValid = false;
                if (isPKValid() && messaggio != null) {
   			isValid = true;
   		}
   		return isValid;
	}
	public boolean isPKValid() {
		boolean isPkValid = false;
		if (idLog != null ) {
   			isPkValid = true;
   		}

   		return isPkValid;
	}
	
	public String toString() {
		
	    String temp = "";
	    StringBuffer sb = new StringBuffer();
	    sb.append("\t\n" + this.getClass().getName() + "\t\n");
	    
	    temp = DataFilter.removeNull( messaggio);
	    if (!DataFilter.isEmpty(temp)) sb.append(" messaggio: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( duration);
	    if (!DataFilter.isEmpty(temp)) sb.append(" duration: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( flagStatoValidazione);
	    if (!DataFilter.isEmpty(temp)) sb.append(" flagStatoValidazione: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idUtente);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idUtente: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idDocumentoIndex);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idDocumentoIndex: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtLog);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtLog: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idLog);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idLog: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( metodo);
	    if (!DataFilter.isEmpty(temp)) sb.append(" metodo: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( codiceErrore);
	    if (!DataFilter.isEmpty(temp)) sb.append(" codiceErrore: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idMessaggioAppl);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idMessaggioAppl: " + temp + "\t\n");
	    
	    return sb.toString();
	}
	
	public java.util.List getPK() {
		java.util.List pk=new java.util.ArrayList();
		
			pk.add("idLog");
		
	    return pk;
	}
	
	
}
