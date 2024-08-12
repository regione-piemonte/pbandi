
package it.csi.pbandi.pbweb.pbandisrv.integration.db.vo;

import java.math.*;
import java.sql.Date;
import it.csi.pbandi.pbweb.pbandisrv.integration.util.DataFilter;



public class PbandiRDomandaIndicatoriVO extends GenericVO {

  	
  	private BigDecimal idUtenteAgg;
  	
  	private BigDecimal idIndicatori;
  	
  	private BigDecimal idDomanda;
  	
  	private BigDecimal valoreProgIniziale;
  	
  	private Date dtAggiornamento;
  	
  	private BigDecimal valoreConcluso;
  	
  	private Date dtInserimento;
  	
  	private String flagNonApplicabile;
  	
  	private BigDecimal valoreProgAgg;
  	
  	private BigDecimal idUtenteIns;
  	
	public PbandiRDomandaIndicatoriVO() {}
  	
	public PbandiRDomandaIndicatoriVO (BigDecimal idIndicatori, BigDecimal idDomanda) {
	
		this. idIndicatori =  idIndicatori;
		this. idDomanda =  idDomanda;
	}
  	
	public PbandiRDomandaIndicatoriVO (BigDecimal idUtenteAgg, BigDecimal idIndicatori, BigDecimal idDomanda, BigDecimal valoreProgIniziale, Date dtAggiornamento, BigDecimal valoreConcluso, Date dtInserimento, String flagNonApplicabile, BigDecimal valoreProgAgg, BigDecimal idUtenteIns) {
	
		this. idUtenteAgg =  idUtenteAgg;
		this. idIndicatori =  idIndicatori;
		this. idDomanda =  idDomanda;
		this. valoreProgIniziale =  valoreProgIniziale;
		this. dtAggiornamento =  dtAggiornamento;
		this. valoreConcluso =  valoreConcluso;
		this. dtInserimento =  dtInserimento;
		this. flagNonApplicabile =  flagNonApplicabile;
		this. valoreProgAgg =  valoreProgAgg;
		this. idUtenteIns =  idUtenteIns;
	}
  	
  	
	public BigDecimal getIdUtenteAgg() {
		return idUtenteAgg;
	}
	
	public void setIdUtenteAgg(BigDecimal idUtenteAgg) {
		this.idUtenteAgg = idUtenteAgg;
	}
	
	public BigDecimal getIdIndicatori() {
		return idIndicatori;
	}
	
	public void setIdIndicatori(BigDecimal idIndicatori) {
		this.idIndicatori = idIndicatori;
	}
	
	public BigDecimal getIdDomanda() {
		return idDomanda;
	}
	
	public void setIdDomanda(BigDecimal idDomanda) {
		this.idDomanda = idDomanda;
	}
	
	public BigDecimal getValoreProgIniziale() {
		return valoreProgIniziale;
	}
	
	public void setValoreProgIniziale(BigDecimal valoreProgIniziale) {
		this.valoreProgIniziale = valoreProgIniziale;
	}
	
	public Date getDtAggiornamento() {
		return dtAggiornamento;
	}
	
	public void setDtAggiornamento(Date dtAggiornamento) {
		this.dtAggiornamento = dtAggiornamento;
	}
	
	public BigDecimal getValoreConcluso() {
		return valoreConcluso;
	}
	
	public void setValoreConcluso(BigDecimal valoreConcluso) {
		this.valoreConcluso = valoreConcluso;
	}
	
	public Date getDtInserimento() {
		return dtInserimento;
	}
	
	public void setDtInserimento(Date dtInserimento) {
		this.dtInserimento = dtInserimento;
	}
	
	public String getFlagNonApplicabile() {
		return flagNonApplicabile;
	}
	
	public void setFlagNonApplicabile(String flagNonApplicabile) {
		this.flagNonApplicabile = flagNonApplicabile;
	}
	
	public BigDecimal getValoreProgAgg() {
		return valoreProgAgg;
	}
	
	public void setValoreProgAgg(BigDecimal valoreProgAgg) {
		this.valoreProgAgg = valoreProgAgg;
	}
	
	public BigDecimal getIdUtenteIns() {
		return idUtenteIns;
	}
	
	public void setIdUtenteIns(BigDecimal idUtenteIns) {
		this.idUtenteIns = idUtenteIns;
	}
	
	public boolean isValid() {
		boolean isValid = false;
                if (isPKValid() && dtInserimento != null && flagNonApplicabile != null && idUtenteIns != null) {
   			isValid = true;
   		}
   		return isValid;
	}
	public boolean isPKValid() {
		boolean isPkValid = false;
		if (idIndicatori != null && idDomanda != null ) {
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
	    
	    temp = DataFilter.removeNull( idIndicatori);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idIndicatori: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idDomanda);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idDomanda: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( valoreProgIniziale);
	    if (!DataFilter.isEmpty(temp)) sb.append(" valoreProgIniziale: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtAggiornamento);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtAggiornamento: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( valoreConcluso);
	    if (!DataFilter.isEmpty(temp)) sb.append(" valoreConcluso: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtInserimento);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtInserimento: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( flagNonApplicabile);
	    if (!DataFilter.isEmpty(temp)) sb.append(" flagNonApplicabile: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( valoreProgAgg);
	    if (!DataFilter.isEmpty(temp)) sb.append(" valoreProgAgg: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idUtenteIns);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idUtenteIns: " + temp + "\t\n");
	    
	    return sb.toString();
	}
	
	public java.util.List getPK() {
		java.util.List pk=new java.util.ArrayList();
		
			pk.add("idIndicatori");
		
			pk.add("idDomanda");
		
	    return pk;
	}
	
	
}
