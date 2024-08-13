/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/


package it.csi.pbandi.pbweb.pbandisrv.integration.db.vo;

import java.math.*;
import java.sql.Date;
import it.csi.pbandi.pbweb.pbandisrv.integration.util.DataFilter;



public class PbandiTContoEconomicoVO extends GenericVO {

  	
  	private String contrattiDaStipulare;
  	
  	private Date dtInizioValidita;
  	
  	private BigDecimal idUtenteAgg;
  	
  	private BigDecimal idContoEconomico;
  	
  	private BigDecimal idDomanda;
  	
  	private BigDecimal importoImpegnoContabile;
  	
  	private String noteContoEconomico;
  	
  	private Date dtFineValidita;
  	
  	private String riferimento;
  	
  	private BigDecimal idStatoContoEconomico;
  	
  	private BigDecimal idUtenteIns;
  	
  	private BigDecimal importoFinanziamentoBanca;
  	
  	private BigDecimal importoImpegnoVincolante;
  	
  	private BigDecimal importoFinanzBancaRichiesto;
  	
	public PbandiTContoEconomicoVO() {}
  	
	public PbandiTContoEconomicoVO (BigDecimal idContoEconomico) {
	
		this. idContoEconomico =  idContoEconomico;
	}
  	
	public PbandiTContoEconomicoVO (String contrattiDaStipulare, Date dtInizioValidita, BigDecimal idUtenteAgg, BigDecimal idContoEconomico, BigDecimal idDomanda, BigDecimal importoImpegnoContabile, String noteContoEconomico, Date dtFineValidita, String riferimento, BigDecimal idStatoContoEconomico, BigDecimal idUtenteIns, BigDecimal importoFinanziamentoBanca, BigDecimal importoImpegnoVincolante, BigDecimal importoFinanzBancaRichiesto) {
	
		this. contrattiDaStipulare =  contrattiDaStipulare;
		this. dtInizioValidita =  dtInizioValidita;
		this. idUtenteAgg =  idUtenteAgg;
		this. idContoEconomico =  idContoEconomico;
		this. idDomanda =  idDomanda;
		this. importoImpegnoContabile =  importoImpegnoContabile;
		this. noteContoEconomico =  noteContoEconomico;
		this. dtFineValidita =  dtFineValidita;
		this. riferimento =  riferimento;
		this. idStatoContoEconomico =  idStatoContoEconomico;
		this. idUtenteIns =  idUtenteIns;
		this. importoFinanziamentoBanca =  importoFinanziamentoBanca;
		this. importoImpegnoVincolante =  importoImpegnoVincolante;
		this. importoFinanzBancaRichiesto =  importoFinanzBancaRichiesto;
	}
  	
  	
	public String getContrattiDaStipulare() {
		return contrattiDaStipulare;
	}
	
	public void setContrattiDaStipulare(String contrattiDaStipulare) {
		this.contrattiDaStipulare = contrattiDaStipulare;
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
	
	public BigDecimal getIdDomanda() {
		return idDomanda;
	}
	
	public void setIdDomanda(BigDecimal idDomanda) {
		this.idDomanda = idDomanda;
	}
	
	public BigDecimal getImportoImpegnoContabile() {
		return importoImpegnoContabile;
	}
	
	public void setImportoImpegnoContabile(BigDecimal importoImpegnoContabile) {
		this.importoImpegnoContabile = importoImpegnoContabile;
	}
	
	public String getNoteContoEconomico() {
		return noteContoEconomico;
	}
	
	public void setNoteContoEconomico(String noteContoEconomico) {
		this.noteContoEconomico = noteContoEconomico;
	}
	
	public Date getDtFineValidita() {
		return dtFineValidita;
	}
	
	public void setDtFineValidita(Date dtFineValidita) {
		this.dtFineValidita = dtFineValidita;
	}
	
	public String getRiferimento() {
		return riferimento;
	}
	
	public void setRiferimento(String riferimento) {
		this.riferimento = riferimento;
	}
	
	public BigDecimal getIdStatoContoEconomico() {
		return idStatoContoEconomico;
	}
	
	public void setIdStatoContoEconomico(BigDecimal idStatoContoEconomico) {
		this.idStatoContoEconomico = idStatoContoEconomico;
	}
	
	public BigDecimal getIdUtenteIns() {
		return idUtenteIns;
	}
	
	public void setIdUtenteIns(BigDecimal idUtenteIns) {
		this.idUtenteIns = idUtenteIns;
	}
	
	public BigDecimal getImportoFinanziamentoBanca() {
		return importoFinanziamentoBanca;
	}
	
	public void setImportoFinanziamentoBanca(BigDecimal importoFinanziamentoBanca) {
		this.importoFinanziamentoBanca = importoFinanziamentoBanca;
	}
	
	public BigDecimal getImportoImpegnoVincolante() {
		return importoImpegnoVincolante;
	}
	
	public void setImportoImpegnoVincolante(BigDecimal importoImpegnoVincolante) {
		this.importoImpegnoVincolante = importoImpegnoVincolante;
	}
	
	public BigDecimal getImportoFinanzBancaRichiesto() {
		return importoFinanzBancaRichiesto;
	}
	
	public void setImportoFinanzBancaRichiesto(BigDecimal importoFinanzBancaRichiesto) {
		this.importoFinanzBancaRichiesto = importoFinanzBancaRichiesto;
	}
	
	public boolean isValid() {
		boolean isValid = false;
                if (isPKValid() && dtInizioValidita != null && idDomanda != null && idStatoContoEconomico != null && idUtenteIns != null) {
   			isValid = true;
   		}
   		return isValid;
	}
	public boolean isPKValid() {
		boolean isPkValid = false;
		if (idContoEconomico != null ) {
   			isPkValid = true;
   		}

   		return isPkValid;
	}
	
	public String toString() {
		
	    String temp = "";
	    StringBuffer sb = new StringBuffer();
	    sb.append("\t\n" + this.getClass().getName() + "\t\n");
	    
	    temp = DataFilter.removeNull( contrattiDaStipulare);
	    if (!DataFilter.isEmpty(temp)) sb.append(" contrattiDaStipulare: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtInizioValidita);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtInizioValidita: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idUtenteAgg);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idUtenteAgg: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idContoEconomico);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idContoEconomico: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idDomanda);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idDomanda: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( importoImpegnoContabile);
	    if (!DataFilter.isEmpty(temp)) sb.append(" importoImpegnoContabile: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( noteContoEconomico);
	    if (!DataFilter.isEmpty(temp)) sb.append(" noteContoEconomico: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtFineValidita);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtFineValidita: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( riferimento);
	    if (!DataFilter.isEmpty(temp)) sb.append(" riferimento: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idStatoContoEconomico);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idStatoContoEconomico: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idUtenteIns);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idUtenteIns: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( importoFinanziamentoBanca);
	    if (!DataFilter.isEmpty(temp)) sb.append(" importoFinanziamentoBanca: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( importoImpegnoVincolante);
	    if (!DataFilter.isEmpty(temp)) sb.append(" importoImpegnoVincolante: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( importoFinanzBancaRichiesto);
	    if (!DataFilter.isEmpty(temp)) sb.append(" importoFinanzBancaRichiesto: " + temp + "\t\n");
	    
	    return sb.toString();
	}
	
	public java.util.List getPK() {
		java.util.List pk=new java.util.ArrayList();
		
			pk.add("idContoEconomico");
		
	    return pk;
	}
	
	
}
