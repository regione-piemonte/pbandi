/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/


package it.csi.pbandi.pbweb.pbandisrv.integration.db.vo;

import java.math.*;
import java.sql.Date;
import it.csi.pbandi.pbweb.pbandisrv.integration.util.DataFilter;



public class PbandiRSoggDomSoggCorrelVO extends GenericVO {

  	
  	private BigDecimal idUtenteAgg;
  	
  	private BigDecimal progrSoggettoDomanda;
  	
  	private Date dtAggiornamento;
  	
  	private Date dtInserimento;
  	
  	private BigDecimal idUtenteIns;
  	
  	private BigDecimal progrSoggettiCorrelati;
  	
	public PbandiRSoggDomSoggCorrelVO() {}
  	
	public PbandiRSoggDomSoggCorrelVO (BigDecimal progrSoggettoDomanda, BigDecimal progrSoggettiCorrelati) {
	
		this. progrSoggettoDomanda =  progrSoggettoDomanda;
		this. progrSoggettiCorrelati =  progrSoggettiCorrelati;
	}
  	
	public PbandiRSoggDomSoggCorrelVO (BigDecimal idUtenteAgg, BigDecimal progrSoggettoDomanda, Date dtAggiornamento, Date dtInserimento, BigDecimal idUtenteIns, BigDecimal progrSoggettiCorrelati) {
	
		this. idUtenteAgg =  idUtenteAgg;
		this. progrSoggettoDomanda =  progrSoggettoDomanda;
		this. dtAggiornamento =  dtAggiornamento;
		this. dtInserimento =  dtInserimento;
		this. idUtenteIns =  idUtenteIns;
		this. progrSoggettiCorrelati =  progrSoggettiCorrelati;
	}
  	
  	
	public BigDecimal getIdUtenteAgg() {
		return idUtenteAgg;
	}
	
	public void setIdUtenteAgg(BigDecimal idUtenteAgg) {
		this.idUtenteAgg = idUtenteAgg;
	}
	
	public BigDecimal getProgrSoggettoDomanda() {
		return progrSoggettoDomanda;
	}
	
	public void setProgrSoggettoDomanda(BigDecimal progrSoggettoDomanda) {
		this.progrSoggettoDomanda = progrSoggettoDomanda;
	}
	
	public Date getDtAggiornamento() {
		return dtAggiornamento;
	}
	
	public void setDtAggiornamento(Date dtAggiornamento) {
		this.dtAggiornamento = dtAggiornamento;
	}
	
	public Date getDtInserimento() {
		return dtInserimento;
	}
	
	public void setDtInserimento(Date dtInserimento) {
		this.dtInserimento = dtInserimento;
	}
	
	public BigDecimal getIdUtenteIns() {
		return idUtenteIns;
	}
	
	public void setIdUtenteIns(BigDecimal idUtenteIns) {
		this.idUtenteIns = idUtenteIns;
	}
	
	public BigDecimal getProgrSoggettiCorrelati() {
		return progrSoggettiCorrelati;
	}
	
	public void setProgrSoggettiCorrelati(BigDecimal progrSoggettiCorrelati) {
		this.progrSoggettiCorrelati = progrSoggettiCorrelati;
	}
	
	public boolean isValid() {
		boolean isValid = false;
                if (isPKValid() && dtInserimento != null && idUtenteIns != null) {
   			isValid = true;
   		}
   		return isValid;
	}
	public boolean isPKValid() {
		boolean isPkValid = false;
		if (progrSoggettoDomanda != null && progrSoggettiCorrelati != null ) {
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
	    
	    temp = DataFilter.removeNull( progrSoggettoDomanda);
	    if (!DataFilter.isEmpty(temp)) sb.append(" progrSoggettoDomanda: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtAggiornamento);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtAggiornamento: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtInserimento);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtInserimento: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idUtenteIns);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idUtenteIns: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( progrSoggettiCorrelati);
	    if (!DataFilter.isEmpty(temp)) sb.append(" progrSoggettiCorrelati: " + temp + "\t\n");
	    
	    return sb.toString();
	}
	
	public java.util.List<String> getPK() {
		java.util.List<String> pk=new java.util.ArrayList<String>();
		
			pk.add("progrSoggettoDomanda");
		
			pk.add("progrSoggettiCorrelati");
		
	    return pk;
	}
	
	
}
