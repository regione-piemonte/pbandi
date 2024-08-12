
package it.csi.pbandi.pbweb.pbandisrv.integration.db.vo;

import java.math.*;
import java.sql.Date;
import it.csi.pbandi.pbweb.pbandisrv.integration.util.DataFilter;



public class PbandiRIterProcAttVO extends GenericVO {

  	
  	private Date dtEffettiva;
  	
  	private Date dtPrevista;
  	
  	private Date dtFineValidita;
  	
  	private BigDecimal idMotivoScostamento;
  	
  	private BigDecimal idBando;
  	
  	private BigDecimal importoFinale;
  	
  	private Date dtInizioValidita;
  	
  	private BigDecimal idStepAttivazione;
  	
	public PbandiRIterProcAttVO() {}
  	
	public PbandiRIterProcAttVO (BigDecimal idBando, BigDecimal idStepAttivazione) {
	
		this. idBando =  idBando;
		this. idStepAttivazione =  idStepAttivazione;
	}
  	
	public PbandiRIterProcAttVO (Date dtEffettiva, Date dtPrevista, Date dtFineValidita, BigDecimal idMotivoScostamento, BigDecimal idBando, BigDecimal importoFinale, Date dtInizioValidita, BigDecimal idStepAttivazione) {
	
		this. dtEffettiva =  dtEffettiva;
		this. dtPrevista =  dtPrevista;
		this. dtFineValidita =  dtFineValidita;
		this. idMotivoScostamento =  idMotivoScostamento;
		this. idBando =  idBando;
		this. importoFinale =  importoFinale;
		this. dtInizioValidita =  dtInizioValidita;
		this. idStepAttivazione =  idStepAttivazione;
	}
  	
  	
	public Date getDtEffettiva() {
		return dtEffettiva;
	}
	
	public void setDtEffettiva(Date dtEffettiva) {
		this.dtEffettiva = dtEffettiva;
	}
	
	public Date getDtPrevista() {
		return dtPrevista;
	}
	
	public void setDtPrevista(Date dtPrevista) {
		this.dtPrevista = dtPrevista;
	}
	
	public Date getDtFineValidita() {
		return dtFineValidita;
	}
	
	public void setDtFineValidita(Date dtFineValidita) {
		this.dtFineValidita = dtFineValidita;
	}
	
	public BigDecimal getIdMotivoScostamento() {
		return idMotivoScostamento;
	}
	
	public void setIdMotivoScostamento(BigDecimal idMotivoScostamento) {
		this.idMotivoScostamento = idMotivoScostamento;
	}
	
	public BigDecimal getIdBando() {
		return idBando;
	}
	
	public void setIdBando(BigDecimal idBando) {
		this.idBando = idBando;
	}
	
	public BigDecimal getImportoFinale() {
		return importoFinale;
	}
	
	public void setImportoFinale(BigDecimal importoFinale) {
		this.importoFinale = importoFinale;
	}
	
	public Date getDtInizioValidita() {
		return dtInizioValidita;
	}
	
	public void setDtInizioValidita(Date dtInizioValidita) {
		this.dtInizioValidita = dtInizioValidita;
	}
	
	public BigDecimal getIdStepAttivazione() {
		return idStepAttivazione;
	}
	
	public void setIdStepAttivazione(BigDecimal idStepAttivazione) {
		this.idStepAttivazione = idStepAttivazione;
	}
	
	public boolean isValid() {
		boolean isValid = false;
                if (isPKValid() && dtPrevista != null && dtInizioValidita != null) {
   			isValid = true;
   		}
   		return isValid;
	}
	public boolean isPKValid() {
		boolean isPkValid = false;
		if (idBando != null && idStepAttivazione != null ) {
   			isPkValid = true;
   		}

   		return isPkValid;
	}
	
	public String toString() {
		
	    String temp = "";
	    StringBuffer sb = new StringBuffer();
	    sb.append("\t\n" + this.getClass().getName() + "\t\n");
	    
	    temp = DataFilter.removeNull( dtEffettiva);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtEffettiva: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtPrevista);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtPrevista: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtFineValidita);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtFineValidita: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idMotivoScostamento);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idMotivoScostamento: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idBando);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idBando: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( importoFinale);
	    if (!DataFilter.isEmpty(temp)) sb.append(" importoFinale: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtInizioValidita);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtInizioValidita: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idStepAttivazione);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idStepAttivazione: " + temp + "\t\n");
	    
	    return sb.toString();
	}
	
	public java.util.List<String> getPK() {
		java.util.List<String> pk=new java.util.ArrayList<String>();
		
			pk.add("idBando");
		
			pk.add("idStepAttivazione");
		
	    return pk;
	}
	
	
}
