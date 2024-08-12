
package it.csi.pbandi.pbweb.pbandisrv.integration.db.vo;

import java.math.*;
import java.sql.Date;
import it.csi.pbandi.pbweb.pbandisrv.integration.util.DataFilter;



public class PbandiTLogProtocollazioneVO extends GenericVO {

  	
  	private String messaggio;
  	
  	private BigDecimal duration;
  	
  	private String flagEsito;
  	
  	private BigDecimal idUtente;
  	
  	private BigDecimal idDocumentoIndex;
  	
  	private Date dtLog;
  	
  	private BigDecimal idLog;
  	
  	private String metodo;
  	
	public PbandiTLogProtocollazioneVO() {}
  	
	public PbandiTLogProtocollazioneVO (BigDecimal idLog) {
	
		this. idLog =  idLog;
	}
  	
	public PbandiTLogProtocollazioneVO (String messaggio, BigDecimal duration, String flagEsito, BigDecimal idUtente, BigDecimal idDocumentoIndex, Date dtLog, BigDecimal idLog, String metodo) {
	
		this. messaggio =  messaggio;
		this. duration =  duration;
		this. flagEsito =  flagEsito;
		this. idUtente =  idUtente;
		this. idDocumentoIndex =  idDocumentoIndex;
		this. dtLog =  dtLog;
		this. idLog =  idLog;
		this.metodo = metodo;
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
	
	public void setDuration(BigDecimal duration) {
		this.duration = duration;
	}
	

	
	public String getFlagEsito() {
		return flagEsito;
	}

	public void setFlagEsito(String flagEsito) {
		this.flagEsito = flagEsito;
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
	
	public String getMetodo() {
		return metodo;
	}

	public void setMetodo(String metodo) {
		this.metodo = metodo;
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
	    
	    temp = DataFilter.removeNull( metodo);
	    if (!DataFilter.isEmpty(temp)) sb.append(" metodo: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( duration);
	    if (!DataFilter.isEmpty(temp)) sb.append(" duration: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( flagEsito);
	    if (!DataFilter.isEmpty(temp)) sb.append(" flagEsito: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idUtente);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idUtente: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idDocumentoIndex);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idDocumentoIndex: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtLog);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtLog: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idLog);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idLog: " + temp + "\t\n");
	    
	    return sb.toString();
	}
	
	public java.util.List getPK() {
		java.util.List pk=new java.util.ArrayList();
		
			pk.add("idLog");
		
	    return pk;
	}
	
	
}
