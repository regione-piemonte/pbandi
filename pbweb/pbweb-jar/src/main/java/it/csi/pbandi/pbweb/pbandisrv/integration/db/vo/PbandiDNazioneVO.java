
package it.csi.pbandi.pbweb.pbandisrv.integration.db.vo;

import java.math.*;
import java.sql.Date;
import it.csi.pbandi.pbweb.pbandisrv.integration.util.DataFilter;



public class PbandiDNazioneVO extends GenericVO {

  	
  	private Date dtFineValidita;
  	
  	private String flagAppartenenzaUe;
  	
  	private BigDecimal idNazione;
  	
  	private String descNazione;
  	
  	private String codIstatNazione;
  	
  	private Date dtInizioValidita;
  	
	public PbandiDNazioneVO() {}
  	
	public PbandiDNazioneVO (BigDecimal idNazione) {
	
		this. idNazione =  idNazione;
	}
  	
	public PbandiDNazioneVO (Date dtFineValidita, String flagAppartenenzaUe, BigDecimal idNazione, String descNazione, String codIstatNazione, Date dtInizioValidita) {
	
		this. dtFineValidita =  dtFineValidita;
		this. flagAppartenenzaUe =  flagAppartenenzaUe;
		this. idNazione =  idNazione;
		this. descNazione =  descNazione;
		this. codIstatNazione =  codIstatNazione;
		this. dtInizioValidita =  dtInizioValidita;
	}
  	
  	
	public Date getDtFineValidita() {
		return dtFineValidita;
	}
	
	public void setDtFineValidita(Date dtFineValidita) {
		this.dtFineValidita = dtFineValidita;
	}
	
	public String getFlagAppartenenzaUe() {
		return flagAppartenenzaUe;
	}
	
	public void setFlagAppartenenzaUe(String flagAppartenenzaUe) {
		this.flagAppartenenzaUe = flagAppartenenzaUe;
	}
	
	public BigDecimal getIdNazione() {
		return idNazione;
	}
	
	public void setIdNazione(BigDecimal idNazione) {
		this.idNazione = idNazione;
	}
	
	public String getDescNazione() {
		return descNazione;
	}
	
	public void setDescNazione(String descNazione) {
		this.descNazione = descNazione;
	}
	
	public String getCodIstatNazione() {
		return codIstatNazione;
	}
	
	public void setCodIstatNazione(String codIstatNazione) {
		this.codIstatNazione = codIstatNazione;
	}
	
	public Date getDtInizioValidita() {
		return dtInizioValidita;
	}
	
	public void setDtInizioValidita(Date dtInizioValidita) {
		this.dtInizioValidita = dtInizioValidita;
	}
	
	public boolean isValid() {
		boolean isValid = false;
                if (isPKValid() && flagAppartenenzaUe != null && descNazione != null && dtInizioValidita != null) {
   			isValid = true;
   		}
   		return isValid;
	}
	public boolean isPKValid() {
		boolean isPkValid = false;
		if (idNazione != null ) {
   			isPkValid = true;
   		}

   		return isPkValid;
	}
	
	public String toString() {
		
	    String temp = "";
	    StringBuffer sb = new StringBuffer();
	    sb.append("\t\n" + this.getClass().getName() + "\t\n");
	    
	    temp = DataFilter.removeNull( dtFineValidita);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtFineValidita: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( flagAppartenenzaUe);
	    if (!DataFilter.isEmpty(temp)) sb.append(" flagAppartenenzaUe: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idNazione);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idNazione: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( descNazione);
	    if (!DataFilter.isEmpty(temp)) sb.append(" descNazione: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( codIstatNazione);
	    if (!DataFilter.isEmpty(temp)) sb.append(" codIstatNazione: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtInizioValidita);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtInizioValidita: " + temp + "\t\n");
	    
	    return sb.toString();
	}
	
	public java.util.List<String> getPK() {
		java.util.List<String> pk=new java.util.ArrayList<String>();
		
			pk.add("idNazione");
		
	    return pk;
	}
	
	
}
