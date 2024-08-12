
package it.csi.pbandi.pbweb.pbandisrv.integration.db.vo;

import java.math.*;
import java.sql.Date;
import it.csi.pbandi.pbweb.pbandisrv.integration.util.DataFilter;



public class PbandiTCspProgFaseMonitVO extends GenericVO {

  	
  	private BigDecimal idUtenteAgg;
  	
  	private Date dtInizioValidita;
  	
  	private BigDecimal idCspProgetto;
  	
  	private Date dtFinePrevista;
  	
  	private Date dtElaborazione;
  	
  	private BigDecimal idCspProgFaseMonit;
  	
  	private BigDecimal idFaseMonit;
  	
  	private Date dtInizioEffettiva;
  	
  	private Date dtFineEffettiva;
  	
  	private BigDecimal idUtenteIns;
  	
  	private BigDecimal idMotivoScostamento;
  	
  	private Date dtInizioPrevista;
  	
	public PbandiTCspProgFaseMonitVO() {}
  	
	public PbandiTCspProgFaseMonitVO (BigDecimal idCspProgFaseMonit) {
	
		this. idCspProgFaseMonit =  idCspProgFaseMonit;
	}
  	
	public PbandiTCspProgFaseMonitVO (BigDecimal idUtenteAgg, Date dtInizioValidita, BigDecimal idCspProgetto, Date dtFinePrevista, Date dtElaborazione, BigDecimal idCspProgFaseMonit, BigDecimal idFaseMonit, Date dtInizioEffettiva, Date dtFineEffettiva, BigDecimal idUtenteIns, BigDecimal idMotivoScostamento, Date dtInizioPrevista) {
	
		this. idUtenteAgg =  idUtenteAgg;
		this. dtInizioValidita =  dtInizioValidita;
		this. idCspProgetto =  idCspProgetto;
		this. dtFinePrevista =  dtFinePrevista;
		this. dtElaborazione =  dtElaborazione;
		this. idCspProgFaseMonit =  idCspProgFaseMonit;
		this. idFaseMonit =  idFaseMonit;
		this. dtInizioEffettiva =  dtInizioEffettiva;
		this. dtFineEffettiva =  dtFineEffettiva;
		this. idUtenteIns =  idUtenteIns;
		this. idMotivoScostamento =  idMotivoScostamento;
		this. dtInizioPrevista =  dtInizioPrevista;
	}
  	
  	
	public BigDecimal getIdUtenteAgg() {
		return idUtenteAgg;
	}
	
	public void setIdUtenteAgg(BigDecimal idUtenteAgg) {
		this.idUtenteAgg = idUtenteAgg;
	}
	
	public Date getDtInizioValidita() {
		return dtInizioValidita;
	}
	
	public void setDtInizioValidita(Date dtInizioValidita) {
		this.dtInizioValidita = dtInizioValidita;
	}
	
	public BigDecimal getIdCspProgetto() {
		return idCspProgetto;
	}
	
	public void setIdCspProgetto(BigDecimal idCspProgetto) {
		this.idCspProgetto = idCspProgetto;
	}
	
	public Date getDtFinePrevista() {
		return dtFinePrevista;
	}
	
	public void setDtFinePrevista(Date dtFinePrevista) {
		this.dtFinePrevista = dtFinePrevista;
	}
	
	public Date getDtElaborazione() {
		return dtElaborazione;
	}
	
	public void setDtElaborazione(Date dtElaborazione) {
		this.dtElaborazione = dtElaborazione;
	}
	
	public BigDecimal getIdCspProgFaseMonit() {
		return idCspProgFaseMonit;
	}
	
	public void setIdCspProgFaseMonit(BigDecimal idCspProgFaseMonit) {
		this.idCspProgFaseMonit = idCspProgFaseMonit;
	}
	
	public BigDecimal getIdFaseMonit() {
		return idFaseMonit;
	}
	
	public void setIdFaseMonit(BigDecimal idFaseMonit) {
		this.idFaseMonit = idFaseMonit;
	}
	
	public Date getDtInizioEffettiva() {
		return dtInizioEffettiva;
	}
	
	public void setDtInizioEffettiva(Date dtInizioEffettiva) {
		this.dtInizioEffettiva = dtInizioEffettiva;
	}
	
	public Date getDtFineEffettiva() {
		return dtFineEffettiva;
	}
	
	public void setDtFineEffettiva(Date dtFineEffettiva) {
		this.dtFineEffettiva = dtFineEffettiva;
	}
	
	public BigDecimal getIdUtenteIns() {
		return idUtenteIns;
	}
	
	public void setIdUtenteIns(BigDecimal idUtenteIns) {
		this.idUtenteIns = idUtenteIns;
	}
	
	public BigDecimal getIdMotivoScostamento() {
		return idMotivoScostamento;
	}
	
	public void setIdMotivoScostamento(BigDecimal idMotivoScostamento) {
		this.idMotivoScostamento = idMotivoScostamento;
	}
	
	public Date getDtInizioPrevista() {
		return dtInizioPrevista;
	}
	
	public void setDtInizioPrevista(Date dtInizioPrevista) {
		this.dtInizioPrevista = dtInizioPrevista;
	}
	
	public boolean isValid() {
		boolean isValid = false;
                if (isPKValid() && dtInizioValidita != null && idCspProgetto != null && idFaseMonit != null && idUtenteIns != null) {
   			isValid = true;
   		}
   		return isValid;
	}
	public boolean isPKValid() {
		boolean isPkValid = false;
		if (idCspProgFaseMonit != null ) {
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
	    
	    temp = DataFilter.removeNull( dtInizioValidita);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtInizioValidita: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idCspProgetto);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idCspProgetto: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtFinePrevista);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtFinePrevista: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtElaborazione);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtElaborazione: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idCspProgFaseMonit);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idCspProgFaseMonit: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idFaseMonit);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idFaseMonit: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtInizioEffettiva);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtInizioEffettiva: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtFineEffettiva);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtFineEffettiva: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idUtenteIns);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idUtenteIns: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idMotivoScostamento);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idMotivoScostamento: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtInizioPrevista);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtInizioPrevista: " + temp + "\t\n");
	    
	    return sb.toString();
	}
	
	public java.util.List<String> getPK() {
		java.util.List<String> pk=new java.util.ArrayList<String>();
		
			pk.add("idCspProgFaseMonit");
		
	    return pk;
	}
	
	
}
