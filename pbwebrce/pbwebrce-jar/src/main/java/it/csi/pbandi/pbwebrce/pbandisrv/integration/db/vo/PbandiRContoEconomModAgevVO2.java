/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/


package it.csi.pbandi.pbwebrce.pbandisrv.integration.db.vo;

import java.math.*;
import java.sql.Date;
import it.csi.pbandi.pbwebrce.pbandisrv.integration.util.DataFilter;



public class PbandiRContoEconomModAgevVO2 extends GenericVO {

  	
  	private BigDecimal quotaImportoRichiesto;
  	
  	private BigDecimal idUtenteAgg;
  	
  	private BigDecimal idContoEconomico;
  	
  	private BigDecimal quotaImportoAgevolato;
  	
  	private Date dtAggiornamento;
  	
  	private Date dtInserimento;
  	
  	private BigDecimal idModalitaAgevolazione;
  	
  	private BigDecimal idUtenteIns;
  	
  	private BigDecimal percImportoAgevolato;
  	
  	private BigDecimal idBando;
  	
	public PbandiRContoEconomModAgevVO2() {}
  	
	public PbandiRContoEconomModAgevVO2 (BigDecimal idContoEconomico, BigDecimal idModalitaAgevolazione) {
	
		this. idContoEconomico =  idContoEconomico;
		this. idModalitaAgevolazione =  idModalitaAgevolazione;
	}
  	
	public PbandiRContoEconomModAgevVO2 (BigDecimal quotaImportoRichiesto, BigDecimal idUtenteAgg, BigDecimal idContoEconomico, BigDecimal quotaImportoAgevolato, Date dtAggiornamento, Date dtInserimento, BigDecimal idModalitaAgevolazione, BigDecimal idUtenteIns, BigDecimal percImportoAgevolato, BigDecimal idBando) {
	
		this. quotaImportoRichiesto =  quotaImportoRichiesto;
		this. idUtenteAgg =  idUtenteAgg;
		this. idContoEconomico =  idContoEconomico;
		this. quotaImportoAgevolato =  quotaImportoAgevolato;
		this. dtAggiornamento =  dtAggiornamento;
		this. dtInserimento =  dtInserimento;
		this. idModalitaAgevolazione =  idModalitaAgevolazione;
		this. idUtenteIns =  idUtenteIns;
		this. percImportoAgevolato =  percImportoAgevolato;
		this. idBando = idBando;
	}
  	
  	
	public BigDecimal getIdBando() {
		return idBando;
	}

	public void setIdBando(BigDecimal idBando) {
		this.idBando = idBando;
	}

	public BigDecimal getQuotaImportoRichiesto() {
		return quotaImportoRichiesto;
	}
	
	public void setQuotaImportoRichiesto(BigDecimal quotaImportoRichiesto) {
		this.quotaImportoRichiesto = quotaImportoRichiesto;
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
	
	public BigDecimal getQuotaImportoAgevolato() {
		return quotaImportoAgevolato;
	}
	
	public void setQuotaImportoAgevolato(BigDecimal quotaImportoAgevolato) {
		this.quotaImportoAgevolato = quotaImportoAgevolato;
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
	
	public BigDecimal getIdModalitaAgevolazione() {
		return idModalitaAgevolazione;
	}
	
	public void setIdModalitaAgevolazione(BigDecimal idModalitaAgevolazione) {
		this.idModalitaAgevolazione = idModalitaAgevolazione;
	}
	
	public BigDecimal getIdUtenteIns() {
		return idUtenteIns;
	}
	
	public void setIdUtenteIns(BigDecimal idUtenteIns) {
		this.idUtenteIns = idUtenteIns;
	}
	
	public BigDecimal getPercImportoAgevolato() {
		return percImportoAgevolato;
	}
	
	public void setPercImportoAgevolato(BigDecimal percImportoAgevolato) {
		this.percImportoAgevolato = percImportoAgevolato;
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
		if (idContoEconomico != null && idModalitaAgevolazione != null ) {
   			isPkValid = true;
   		}

   		return isPkValid;
	}
	
	public String toString() {
		
	    String temp = "";
	    StringBuffer sb = new StringBuffer();
	    sb.append("\t\n" + this.getClass().getName() + "\t\n");
	    
	    temp = DataFilter.removeNull( quotaImportoRichiesto);
	    if (!DataFilter.isEmpty(temp)) sb.append(" quotaImportoRichiesto: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idUtenteAgg);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idUtenteAgg: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idContoEconomico);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idContoEconomico: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( quotaImportoAgevolato);
	    if (!DataFilter.isEmpty(temp)) sb.append(" quotaImportoAgevolato: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtAggiornamento);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtAggiornamento: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtInserimento);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtInserimento: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idModalitaAgevolazione);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idModalitaAgevolazione: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idUtenteIns);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idUtenteIns: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( percImportoAgevolato);
	    if (!DataFilter.isEmpty(temp)) sb.append(" percImportoAgevolato: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idBando);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idBando: " + temp + "\t\n");
	    
	    return sb.toString();
	}
	
	public java.util.List<String> getPK() {
		java.util.List<String> pk=new java.util.ArrayList<String>();
		
			pk.add("idContoEconomico");
		
			pk.add("idModalitaAgevolazione");
		
	    return pk;
	}
	
	
}
