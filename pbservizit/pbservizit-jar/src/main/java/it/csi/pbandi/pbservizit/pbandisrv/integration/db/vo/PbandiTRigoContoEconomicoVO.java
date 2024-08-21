/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/


package it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo;

import java.math.*;
import java.sql.Date;
import it.csi.pbandi.pbservizit.pbandisrv.integration.util.DataFilter;



public class PbandiTRigoContoEconomicoVO extends GenericVO {

  	
  	private BigDecimal importoAmmessoFinanziamento;
  	
  	private Date dtInizioValidita;
  	
  	private BigDecimal idUtenteAgg;
  	
  	private BigDecimal idContoEconomico;
  	
  	private BigDecimal idVoceDiSpesa;
  	
  	private BigDecimal importoSpesa;
  	
  	private Date dtFineValidita;
  	
  	private BigDecimal idRigoContoEconomico;
  	
  	private BigDecimal importoContributo;
  	
  	private BigDecimal idUtenteIns;

		private BigDecimal idVoceDiEntrata;

		private BigDecimal importoErogato;

		private String completamento;

	public PbandiTRigoContoEconomicoVO() {}
  	
	public PbandiTRigoContoEconomicoVO (BigDecimal idRigoContoEconomico) {
	
		this. idRigoContoEconomico =  idRigoContoEconomico;
	}
  	
	public PbandiTRigoContoEconomicoVO (BigDecimal importoAmmessoFinanziamento, Date dtInizioValidita, BigDecimal idUtenteAgg, BigDecimal idContoEconomico, BigDecimal idVoceDiSpesa, BigDecimal importoSpesa, Date dtFineValidita, BigDecimal idRigoContoEconomico, BigDecimal importoContributo, BigDecimal idUtenteIns) {
	
		this. importoAmmessoFinanziamento =  importoAmmessoFinanziamento;
		this. dtInizioValidita =  dtInizioValidita;
		this. idUtenteAgg =  idUtenteAgg;
		this. idContoEconomico =  idContoEconomico;
		this. idVoceDiSpesa =  idVoceDiSpesa;
		this. importoSpesa =  importoSpesa;
		this. dtFineValidita =  dtFineValidita;
		this. idRigoContoEconomico =  idRigoContoEconomico;
		this. importoContributo =  importoContributo;
		this. idUtenteIns =  idUtenteIns;
	}
  	
  	
	public BigDecimal getImportoAmmessoFinanziamento() {
		return importoAmmessoFinanziamento;
	}
	
	public void setImportoAmmessoFinanziamento(BigDecimal importoAmmessoFinanziamento) {
		this.importoAmmessoFinanziamento = importoAmmessoFinanziamento;
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
	
	public BigDecimal getIdContoEconomico() {
		return idContoEconomico;
	}
	
	public void setIdContoEconomico(BigDecimal idContoEconomico) {
		this.idContoEconomico = idContoEconomico;
	}
	
	public BigDecimal getIdVoceDiSpesa() {
		return idVoceDiSpesa;
	}
	
	public void setIdVoceDiSpesa(BigDecimal idVoceDiSpesa) {
		this.idVoceDiSpesa = idVoceDiSpesa;
	}
	
	public BigDecimal getImportoSpesa() {
		return importoSpesa;
	}
	
	public void setImportoSpesa(BigDecimal importoSpesa) {
		this.importoSpesa = importoSpesa;
	}
	
	public Date getDtFineValidita() {
		return dtFineValidita;
	}
	
	public void setDtFineValidita(Date dtFineValidita) {
		this.dtFineValidita = dtFineValidita;
	}
	
	public BigDecimal getIdRigoContoEconomico() {
		return idRigoContoEconomico;
	}
	
	public void setIdRigoContoEconomico(BigDecimal idRigoContoEconomico) {
		this.idRigoContoEconomico = idRigoContoEconomico;
	}
	
	public BigDecimal getImportoContributo() {
		return importoContributo;
	}
	
	public void setImportoContributo(BigDecimal importoContributo) {
		this.importoContributo = importoContributo;
	}
	
	public BigDecimal getIdUtenteIns() {
		return idUtenteIns;
	}
	
	public void setIdUtenteIns(BigDecimal idUtenteIns) {
		this.idUtenteIns = idUtenteIns;
	}
	
	public boolean isValid() {
		return isPKValid() && dtInizioValidita != null && idContoEconomico != null && idVoceDiSpesa != null && idUtenteIns != null;
	}
	public boolean isPKValid() {

		return idRigoContoEconomico != null;
	}
	
	public String toString() {
		
	    String temp = "";
	    StringBuffer sb = new StringBuffer();
	    sb.append("\t\n").append(this.getClass().getName()).append("\t\n");
	    
	    temp = DataFilter.removeNull( importoAmmessoFinanziamento);
	    if (!DataFilter.isEmpty(temp)) sb.append(" importoAmmessoFinanziamento: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtInizioValidita);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtInizioValidita: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idUtenteAgg);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idUtenteAgg: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idContoEconomico);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idContoEconomico: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idVoceDiSpesa);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idVoceDiSpesa: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( importoSpesa);
	    if (!DataFilter.isEmpty(temp)) sb.append(" importoSpesa: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtFineValidita);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtFineValidita: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idRigoContoEconomico);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idRigoContoEconomico: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( importoContributo);
	    if (!DataFilter.isEmpty(temp)) sb.append(" importoContributo: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idUtenteIns);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idUtenteIns: " + temp + "\t\n");

			temp = DataFilter.removeNull( completamento);
			if (!DataFilter.isEmpty(temp)) sb.append(" completamento: " + temp + "\t\n");

			temp = DataFilter.removeNull( idVoceDiEntrata);
			if (!DataFilter.isEmpty(temp)) sb.append(" idVoceDiEntrata: " + temp + "\t\n");

			temp = DataFilter.removeNull( importoErogato);
			if (!DataFilter.isEmpty(temp)) sb.append(" importoErogato: " + temp + "\t\n");

			return sb.toString();
	}
	
	public java.util.List<String> getPK() {
		java.util.List<String> pk=new java.util.ArrayList<String>();
		
			pk.add("idRigoContoEconomico");
		
	    return pk;
	}


	public BigDecimal getIdVoceDiEntrata() {
		return idVoceDiEntrata;
	}

	public void setIdVoceDiEntrata(BigDecimal idVoceDiEntrata) {
		this.idVoceDiEntrata = idVoceDiEntrata;
	}

	public BigDecimal getImportoErogato() {
		return importoErogato;
	}

	public void setImportoErogato(BigDecimal importoErogato) {
		this.importoErogato = importoErogato;
	}

	public String getCompletamento() {
		return completamento;
	}

	public void setCompletamento(String completamento) {
		this.completamento = completamento;
	}

}
