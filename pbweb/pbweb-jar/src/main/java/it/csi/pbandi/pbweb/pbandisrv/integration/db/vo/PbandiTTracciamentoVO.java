
package it.csi.pbandi.pbweb.pbandisrv.integration.db.vo;

import it.csi.pbandi.pbweb.pbandisrv.integration.util.DataFilter;

import java.math.BigDecimal;
import java.sql.Timestamp;



public class PbandiTTracciamentoVO extends GenericVO {

  	
  	private BigDecimal durata;
  	
  	private BigDecimal idUtente;
  	
  	private String flagEsito;
  	
  	private String messaggioErrore;
  	
  	private Timestamp dtTracciamento;
  	
  	private BigDecimal idTracciamento;
  	
  	private BigDecimal idOperazione;
  	
  	private String dettaglioErrore;
  	
	public PbandiTTracciamentoVO() {}
  	
	public PbandiTTracciamentoVO (BigDecimal idTracciamento) {
	
		this. idTracciamento =  idTracciamento;
	}
  	
	public PbandiTTracciamentoVO (BigDecimal durata, BigDecimal idUtente, String flagEsito, String messaggioErrore, Timestamp dtTracciamento, BigDecimal idTracciamento, BigDecimal idOperazione) {
	
		this. durata =  durata;
		this. idUtente =  idUtente;
		this. flagEsito =  flagEsito;
		this. messaggioErrore =  messaggioErrore;
		this. dtTracciamento =  dtTracciamento;
		this. idTracciamento =  idTracciamento;
		this. idOperazione =  idOperazione;
	}
  	
  	
	public BigDecimal getDurata() {
		return durata;
	}
	
	public void setDurata(BigDecimal durata) {
		this.durata = durata;
	}
	
	public BigDecimal getIdUtente() {
		return idUtente;
	}
	
	public void setIdUtente(BigDecimal idUtente) {
		this.idUtente = idUtente;
	}
	
	public String getFlagEsito() {
		return flagEsito;
	}
	
	public void setFlagEsito(String flagEsito) {
		this.flagEsito = flagEsito;
	}
	
	public String getMessaggioErrore() {
		return messaggioErrore;
	}
	
	public void setMessaggioErrore(String messaggioErrore) {
		this.messaggioErrore = messaggioErrore;
	}
	
	public Timestamp getDtTracciamento() {
		return dtTracciamento;
	}
	
	public void setDtTracciamento(Timestamp dtTracciamento) {
		this.dtTracciamento = dtTracciamento;
	}
	
	public BigDecimal getIdTracciamento() {
		return idTracciamento;
	}
	
	public void setIdTracciamento(BigDecimal idTracciamento) {
		this.idTracciamento = idTracciamento;
	}
	
	public BigDecimal getIdOperazione() {
		return idOperazione;
	}
	
	public void setIdOperazione(BigDecimal idOperazione) {
		this.idOperazione = idOperazione;
	}
	
	public String getDettaglioErrore() {
		return dettaglioErrore;
	}

	public void setDettaglioErrore(String dettaglioErrore) {
		this.dettaglioErrore = dettaglioErrore;
	}

	public boolean isValid() {
		boolean isValid = false;
                if (isPKValid() && idUtente != null && flagEsito != null && dtTracciamento != null && idOperazione != null) {
   			isValid = true;
   		}
   		return isValid;
	}
	public boolean isPKValid() {
		boolean isPkValid = false;
		if (idTracciamento != null ) {
   			isPkValid = true;
   		}

   		return isPkValid;
	}
	
	public String toString() {
		
	    String temp = "";
	    StringBuffer sb = new StringBuffer();
	    sb.append("\t\n" + this.getClass().getName() + "\t\n");
	    
	    temp = DataFilter.removeNull( durata);
	    if (!DataFilter.isEmpty(temp)) sb.append(" durata: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idUtente);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idUtente: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( flagEsito);
	    if (!DataFilter.isEmpty(temp)) sb.append(" flagEsito: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( messaggioErrore);
	    if (!DataFilter.isEmpty(temp)) sb.append(" messaggioErrore: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtTracciamento);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtTracciamento: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idTracciamento);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idTracciamento: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idOperazione);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idOperazione: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dettaglioErrore);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dettaglioErrore: " + temp + "\t\n");

	    return sb.toString();
	}
	
	public java.util.List<String> getPK() {
		java.util.List<String> pk=new java.util.ArrayList<String>();
		
			pk.add("idTracciamento");
		
	    return pk;
	}
	
	
}
