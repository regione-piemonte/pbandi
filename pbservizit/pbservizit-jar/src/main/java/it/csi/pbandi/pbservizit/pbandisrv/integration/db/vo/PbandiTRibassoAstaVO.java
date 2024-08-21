/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/


package it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo;

import java.math.*;
import java.sql.Date;
import it.csi.pbandi.pbservizit.pbandisrv.integration.util.DataFilter;



public class PbandiTRibassoAstaVO extends GenericVO {

  	
  	private BigDecimal idRibassoAsta;
  	
  	private BigDecimal idUtenteAgg;
  	
  	private BigDecimal idContoEconomico;
  	
  	private Date dtAggiornamento;
  	
  	private Date dtInserimento;
  	
  	private BigDecimal percentuale;
  	
  	private BigDecimal idProceduraAggiudicaz;
  	
  	private BigDecimal importo;
  	
  	private BigDecimal idUtenteIns;
  	
	public PbandiTRibassoAstaVO() {}
  	
	public PbandiTRibassoAstaVO (BigDecimal idRibassoAsta) {
	
		this. idRibassoAsta =  idRibassoAsta;
	}
  	
	public PbandiTRibassoAstaVO (BigDecimal idRibassoAsta, BigDecimal idUtenteAgg, BigDecimal idContoEconomico, Date dtAggiornamento, Date dtInserimento, BigDecimal percentuale, BigDecimal idProceduraAggiudicaz, BigDecimal importo, BigDecimal idUtenteIns) {
	
		this. idRibassoAsta =  idRibassoAsta;
		this. idUtenteAgg =  idUtenteAgg;
		this. idContoEconomico =  idContoEconomico;
		this. dtAggiornamento =  dtAggiornamento;
		this. dtInserimento =  dtInserimento;
		this. percentuale =  percentuale;
		this. idProceduraAggiudicaz =  idProceduraAggiudicaz;
		this. importo =  importo;
		this. idUtenteIns =  idUtenteIns;
	}
  	
  	
	public BigDecimal getIdRibassoAsta() {
		return idRibassoAsta;
	}
	
	public void setIdRibassoAsta(BigDecimal idRibassoAsta) {
		this.idRibassoAsta = idRibassoAsta;
	}
	
	public BigDecimal getIdUtenteAgg() {
		return idUtenteAgg;
	}
	
	public void setIdUtenteAgg(BigDecimal idUtenteAgg) {
		this.idUtenteAgg = idUtenteAgg;
	}
	
	public BigDecimal getIdContoEconomico() {
		return idContoEconomico;
	}
	
	public void setIdContoEconomico(BigDecimal idContoEconomico) {
		this.idContoEconomico = idContoEconomico;
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
	
	public BigDecimal getPercentuale() {
		return percentuale;
	}
	
	public void setPercentuale(BigDecimal percentuale) {
		this.percentuale = percentuale;
	}
	
	public BigDecimal getIdProceduraAggiudicaz() {
		return idProceduraAggiudicaz;
	}
	
	public void setIdProceduraAggiudicaz(BigDecimal idProceduraAggiudicaz) {
		this.idProceduraAggiudicaz = idProceduraAggiudicaz;
	}
	
	public BigDecimal getImporto() {
		return importo;
	}
	
	public void setImporto(BigDecimal importo) {
		this.importo = importo;
	}
	
	public BigDecimal getIdUtenteIns() {
		return idUtenteIns;
	}
	
	public void setIdUtenteIns(BigDecimal idUtenteIns) {
		this.idUtenteIns = idUtenteIns;
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
		if (idRibassoAsta != null ) {
   			isPkValid = true;
   		}

   		return isPkValid;
	}
	
	public String toString() {
		
	    String temp = "";
	    StringBuffer sb = new StringBuffer();
	    sb.append("\t\n" + this.getClass().getName() + "\t\n");
	    
	    temp = DataFilter.removeNull( idRibassoAsta);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idRibassoAsta: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idUtenteAgg);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idUtenteAgg: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idContoEconomico);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idContoEconomico: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtAggiornamento);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtAggiornamento: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtInserimento);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtInserimento: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( percentuale);
	    if (!DataFilter.isEmpty(temp)) sb.append(" percentuale: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idProceduraAggiudicaz);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idProceduraAggiudicaz: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( importo);
	    if (!DataFilter.isEmpty(temp)) sb.append(" importo: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idUtenteIns);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idUtenteIns: " + temp + "\t\n");
	    
	    return sb.toString();
	}
	
	public java.util.List getPK() {
		java.util.List pk=new java.util.ArrayList();
		
			pk.add("idRibassoAsta");
		
	    return pk;
	}
	
	
}
