/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/


package it.csi.pbandi.pbwebrce.pbandisrv.integration.db.vo;

import java.math.*;
import java.sql.Date;
import it.csi.pbandi.pbwebrce.pbandisrv.integration.util.DataFilter;



public class PbandiTDatiPagamentoAttoVO extends GenericVO {

  	
  	private BigDecimal idRecapiti;
  	
  	private BigDecimal idModalitaErogazione;
  	
  	private BigDecimal idUtenteAgg;
  	
  	private BigDecimal idIndirizzo;
  	
  	private BigDecimal idDatiPagamentoAtto;
  	
  	private Date dtInserimento;
  	
  	private Date dtAggiornamento;
  	
  	private BigDecimal idEstremiBancari;
  	
  	private BigDecimal codModPagBilancio;
  	
  	private BigDecimal idUtenteIns;
  	
  	private BigDecimal idSede;
  	
	public PbandiTDatiPagamentoAttoVO() {}
  	
	public PbandiTDatiPagamentoAttoVO (BigDecimal idDatiPagamentoAtto) {
	
		this. idDatiPagamentoAtto =  idDatiPagamentoAtto;
	}
  	
	public PbandiTDatiPagamentoAttoVO (BigDecimal idRecapiti, BigDecimal idModalitaErogazione, BigDecimal idUtenteAgg, BigDecimal idIndirizzo, BigDecimal idDatiPagamentoAtto, Date dtInserimento, Date dtAggiornamento, BigDecimal idEstremiBancari, BigDecimal codModPagBilancio, BigDecimal idUtenteIns, BigDecimal idSede) {
	
		this. idRecapiti =  idRecapiti;
		this. idModalitaErogazione =  idModalitaErogazione;
		this. idUtenteAgg =  idUtenteAgg;
		this. idIndirizzo =  idIndirizzo;
		this. idDatiPagamentoAtto =  idDatiPagamentoAtto;
		this. dtInserimento =  dtInserimento;
		this. dtAggiornamento =  dtAggiornamento;
		this. idEstremiBancari =  idEstremiBancari;
		this. codModPagBilancio =  codModPagBilancio;
		this. idUtenteIns =  idUtenteIns;
		this. idSede =  idSede;
	}
  	
  	
	public BigDecimal getIdRecapiti() {
		return idRecapiti;
	}
	
	public void setIdRecapiti(BigDecimal idRecapiti) {
		this.idRecapiti = idRecapiti;
	}
	
	public BigDecimal getIdModalitaErogazione() {
		return idModalitaErogazione;
	}
	
	public void setIdModalitaErogazione(BigDecimal idModalitaErogazione) {
		this.idModalitaErogazione = idModalitaErogazione;
	}
	
	public BigDecimal getIdUtenteAgg() {
		return idUtenteAgg;
	}
	
	public void setIdUtenteAgg(BigDecimal idUtenteAgg) {
		this.idUtenteAgg = idUtenteAgg;
	}
	
	public BigDecimal getIdIndirizzo() {
		return idIndirizzo;
	}
	
	public void setIdIndirizzo(BigDecimal idIndirizzo) {
		this.idIndirizzo = idIndirizzo;
	}
	
	public BigDecimal getIdDatiPagamentoAtto() {
		return idDatiPagamentoAtto;
	}
	
	public void setIdDatiPagamentoAtto(BigDecimal idDatiPagamentoAtto) {
		this.idDatiPagamentoAtto = idDatiPagamentoAtto;
	}
	
	public Date getDtInserimento() {
		return dtInserimento;
	}
	
	public void setDtInserimento(Date dtInserimento) {
		this.dtInserimento = dtInserimento;
	}
	
	public Date getDtAggiornamento() {
		return dtAggiornamento;
	}
	
	public void setDtAggiornamento(Date dtAggiornamento) {
		this.dtAggiornamento = dtAggiornamento;
	}
	
	public BigDecimal getIdEstremiBancari() {
		return idEstremiBancari;
	}
	
	public void setIdEstremiBancari(BigDecimal idEstremiBancari) {
		this.idEstremiBancari = idEstremiBancari;
	}
	
	public BigDecimal getCodModPagBilancio() {
		return codModPagBilancio;
	}
	
	public void setCodModPagBilancio(BigDecimal codModPagBilancio) {
		this.codModPagBilancio = codModPagBilancio;
	}
	
	public BigDecimal getIdUtenteIns() {
		return idUtenteIns;
	}
	
	public void setIdUtenteIns(BigDecimal idUtenteIns) {
		this.idUtenteIns = idUtenteIns;
	}
	
	public BigDecimal getIdSede() {
		return idSede;
	}
	
	public void setIdSede(BigDecimal idSede) {
		this.idSede = idSede;
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
		if (idDatiPagamentoAtto != null ) {
   			isPkValid = true;
   		}

   		return isPkValid;
	}
	
	public String toString() {
		
	    String temp = "";
	    StringBuffer sb = new StringBuffer();
	    sb.append("\t\n" + this.getClass().getName() + "\t\n");
	    
	    temp = DataFilter.removeNull( idRecapiti);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idRecapiti: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idModalitaErogazione);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idModalitaErogazione: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idUtenteAgg);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idUtenteAgg: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idIndirizzo);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idIndirizzo: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idDatiPagamentoAtto);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idDatiPagamentoAtto: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtInserimento);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtInserimento: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtAggiornamento);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtAggiornamento: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idEstremiBancari);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idEstremiBancari: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( codModPagBilancio);
	    if (!DataFilter.isEmpty(temp)) sb.append(" codModPagBilancio: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idUtenteIns);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idUtenteIns: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idSede);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idSede: " + temp + "\t\n");
	    
	    return sb.toString();
	}
	
	public java.util.List getPK() {
		java.util.List<String> pk=new java.util.ArrayList<String>();

		pk.add("idDatiPagamentoAtto");
		
	    return pk;
	}
	
	
}
