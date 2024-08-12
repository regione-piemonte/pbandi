
package it.csi.pbandi.pbweb.pbandisrv.integration.db.vo;

import java.math.*;
import java.sql.Date;
import it.csi.pbandi.pbweb.pbandisrv.integration.util.DataFilter;



public class PbandiTIscrizioneVO extends GenericVO {

  	
  	private String flagIscrizioneInCorso;
  	
  	private Date dtInizioValidita;
  	
  	private BigDecimal idUtenteAgg;
  	
  	private BigDecimal idTipoIscrizione;
  	
  	private BigDecimal idProvincia;
  	
  	private BigDecimal idIscrizione;
  	
  	private Date dtIscrizione;
  	
  	private Date dtFineValidita;
  	
  	private BigDecimal idUtenteIns;
  	
  	private BigDecimal idRegione;
  	
  	private String numIscrizione;
  	
	public PbandiTIscrizioneVO() {}
  	
	public PbandiTIscrizioneVO (BigDecimal idIscrizione) {
	
		this. idIscrizione =  idIscrizione;
	}
  	
	public PbandiTIscrizioneVO (String flagIscrizioneInCorso, Date dtInizioValidita, BigDecimal idUtenteAgg, BigDecimal idTipoIscrizione, BigDecimal idProvincia, BigDecimal idIscrizione, Date dtIscrizione, Date dtFineValidita, BigDecimal idUtenteIns, BigDecimal idRegione, String numIscrizione) {
	
		this. flagIscrizioneInCorso =  flagIscrizioneInCorso;
		this. dtInizioValidita =  dtInizioValidita;
		this. idUtenteAgg =  idUtenteAgg;
		this. idTipoIscrizione =  idTipoIscrizione;
		this. idProvincia =  idProvincia;
		this. idIscrizione =  idIscrizione;
		this. dtIscrizione =  dtIscrizione;
		this. dtFineValidita =  dtFineValidita;
		this. idUtenteIns =  idUtenteIns;
		this. idRegione =  idRegione;
		this. numIscrizione =  numIscrizione;
	}
  	
  	
	public String getFlagIscrizioneInCorso() {
		return flagIscrizioneInCorso;
	}
	
	public void setFlagIscrizioneInCorso(String flagIscrizioneInCorso) {
		this.flagIscrizioneInCorso = flagIscrizioneInCorso;
	}
	
	public Date getDtInizioValidita() {
		return dtInizioValidita;
	}
	
	public void setDtInizioValidita(Date dtInizioValidita) {
		this.dtInizioValidita = dtInizioValidita;
	}
	
	public BigDecimal getIdUtenteAgg() {
		return idUtenteAgg;
	}
	
	public void setIdUtenteAgg(BigDecimal idUtenteAgg) {
		this.idUtenteAgg = idUtenteAgg;
	}
	
	public BigDecimal getIdTipoIscrizione() {
		return idTipoIscrizione;
	}
	
	public void setIdTipoIscrizione(BigDecimal idTipoIscrizione) {
		this.idTipoIscrizione = idTipoIscrizione;
	}
	
	public BigDecimal getIdProvincia() {
		return idProvincia;
	}
	
	public void setIdProvincia(BigDecimal idProvincia) {
		this.idProvincia = idProvincia;
	}
	
	public BigDecimal getIdIscrizione() {
		return idIscrizione;
	}
	
	public void setIdIscrizione(BigDecimal idIscrizione) {
		this.idIscrizione = idIscrizione;
	}
	
	public Date getDtIscrizione() {
		return dtIscrizione;
	}
	
	public void setDtIscrizione(Date dtIscrizione) {
		this.dtIscrizione = dtIscrizione;
	}
	
	public Date getDtFineValidita() {
		return dtFineValidita;
	}
	
	public void setDtFineValidita(Date dtFineValidita) {
		this.dtFineValidita = dtFineValidita;
	}
	
	public BigDecimal getIdUtenteIns() {
		return idUtenteIns;
	}
	
	public void setIdUtenteIns(BigDecimal idUtenteIns) {
		this.idUtenteIns = idUtenteIns;
	}
	
	public BigDecimal getIdRegione() {
		return idRegione;
	}
	
	public void setIdRegione(BigDecimal idRegione) {
		this.idRegione = idRegione;
	}
	
	public String getNumIscrizione() {
		return numIscrizione;
	}
	
	public void setNumIscrizione(String numIscrizione) {
		this.numIscrizione = numIscrizione;
	}
	
	public boolean isValid() {
		boolean isValid = false;
                if (isPKValid() && flagIscrizioneInCorso != null && dtInizioValidita != null && idTipoIscrizione != null && idUtenteIns != null) {
   			isValid = true;
   		}
   		return isValid;
	}
	public boolean isPKValid() {
		boolean isPkValid = false;
		if (idIscrizione != null ) {
   			isPkValid = true;
   		}

   		return isPkValid;
	}
	
	public String toString() {
		
	    String temp = "";
	    StringBuffer sb = new StringBuffer();
	    sb.append("\t\n" + this.getClass().getName() + "\t\n");
	    
	    temp = DataFilter.removeNull( flagIscrizioneInCorso);
	    if (!DataFilter.isEmpty(temp)) sb.append(" flagIscrizioneInCorso: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtInizioValidita);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtInizioValidita: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idUtenteAgg);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idUtenteAgg: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idTipoIscrizione);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idTipoIscrizione: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idProvincia);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idProvincia: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idIscrizione);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idIscrizione: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtIscrizione);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtIscrizione: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtFineValidita);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtFineValidita: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idUtenteIns);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idUtenteIns: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idRegione);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idRegione: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( numIscrizione);
	    if (!DataFilter.isEmpty(temp)) sb.append(" numIscrizione: " + temp + "\t\n");
	    
	    return sb.toString();
	}
	
	public java.util.List<String> getPK() {
		java.util.List<String> pk=new java.util.ArrayList<String>();
		
			pk.add("idIscrizione");
		
	    return pk;
	}
	
	
}
