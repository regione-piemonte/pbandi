/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/


package it.csi.pbandi.pbwebrce.pbandisrv.integration.db.vo;

import java.math.*;
import java.sql.Date;
import it.csi.pbandi.pbwebrce.pbandisrv.integration.util.DataFilter;



public class PbandiRSoggettoDomandaVO extends GenericVO {

  	
  	private BigDecimal idEnteGiuridico;
  	
  	private BigDecimal idIscrizionePersonaGiurid;
  	
  	private BigDecimal progrSoggettoDomanda;
  	
  	private Date dtInizioValidita;
  	
  	private BigDecimal idDomanda;
  	
  	private BigDecimal idUtenteIns;
  	
  	private Date dtInserimento;
  	
  	private BigDecimal idRecapitiPersonaFisica;
  	
  	private BigDecimal idDocumentoPersonaFisica;
  	
  	private Date dtFineValidita;
  	
  	private BigDecimal idSoggetto;
  	
  	private BigDecimal idEstremiBancari;
  	
  	private BigDecimal idUtenteAgg;
  	
  	private BigDecimal idTipoAnagrafica;
  	
  	private BigDecimal idPersonaFisica;
  	
  	private BigDecimal idTipoBeneficiario;
  	
  	private BigDecimal idIndirizzoPersonaFisica;
  	
  	private Date dtAggiornamento;
  	
	public PbandiRSoggettoDomandaVO() {}
  	
	public PbandiRSoggettoDomandaVO (BigDecimal progrSoggettoDomanda) {
	
		this. progrSoggettoDomanda =  progrSoggettoDomanda;
	}
  	
	public PbandiRSoggettoDomandaVO (BigDecimal idEnteGiuridico, BigDecimal idIscrizionePersonaGiurid, BigDecimal progrSoggettoDomanda, Date dtInizioValidita, BigDecimal idDomanda, BigDecimal idUtenteIns, Date dtInserimento, BigDecimal idRecapitiPersonaFisica, BigDecimal idDocumentoPersonaFisica, Date dtFineValidita, BigDecimal idSoggetto, BigDecimal idEstremiBancari, BigDecimal idUtenteAgg, BigDecimal idTipoAnagrafica, BigDecimal idPersonaFisica, BigDecimal idTipoBeneficiario, BigDecimal idIndirizzoPersonaFisica, Date dtAggiornamento) {
	
		this. idEnteGiuridico =  idEnteGiuridico;
		this. idIscrizionePersonaGiurid =  idIscrizionePersonaGiurid;
		this. progrSoggettoDomanda =  progrSoggettoDomanda;
		this. dtInizioValidita =  dtInizioValidita;
		this. idDomanda =  idDomanda;
		this. idUtenteIns =  idUtenteIns;
		this. dtInserimento =  dtInserimento;
		this. idRecapitiPersonaFisica =  idRecapitiPersonaFisica;
		this. idDocumentoPersonaFisica =  idDocumentoPersonaFisica;
		this. dtFineValidita =  dtFineValidita;
		this. idSoggetto =  idSoggetto;
		this. idEstremiBancari =  idEstremiBancari;
		this. idUtenteAgg =  idUtenteAgg;
		this. idTipoAnagrafica =  idTipoAnagrafica;
		this. idPersonaFisica =  idPersonaFisica;
		this. idTipoBeneficiario =  idTipoBeneficiario;
		this. idIndirizzoPersonaFisica =  idIndirizzoPersonaFisica;
		this. dtAggiornamento =  dtAggiornamento;
	}
  	
  	
	public BigDecimal getIdEnteGiuridico() {
		return idEnteGiuridico;
	}
	
	public void setIdEnteGiuridico(BigDecimal idEnteGiuridico) {
		this.idEnteGiuridico = idEnteGiuridico;
	}
	
	public BigDecimal getIdIscrizionePersonaGiurid() {
		return idIscrizionePersonaGiurid;
	}
	
	public void setIdIscrizionePersonaGiurid(BigDecimal idIscrizionePersonaGiurid) {
		this.idIscrizionePersonaGiurid = idIscrizionePersonaGiurid;
	}
	
	public BigDecimal getProgrSoggettoDomanda() {
		return progrSoggettoDomanda;
	}
	
	public void setProgrSoggettoDomanda(BigDecimal progrSoggettoDomanda) {
		this.progrSoggettoDomanda = progrSoggettoDomanda;
	}
	
	public Date getDtInizioValidita() {
		return dtInizioValidita;
	}
	
	public void setDtInizioValidita(Date dtInizioValidita) {
		this.dtInizioValidita = dtInizioValidita;
	}
	
	public BigDecimal getIdDomanda() {
		return idDomanda;
	}
	
	public void setIdDomanda(BigDecimal idDomanda) {
		this.idDomanda = idDomanda;
	}
	
	public BigDecimal getIdUtenteIns() {
		return idUtenteIns;
	}
	
	public void setIdUtenteIns(BigDecimal idUtenteIns) {
		this.idUtenteIns = idUtenteIns;
	}
	
	public Date getDtInserimento() {
		return dtInserimento;
	}
	
	public void setDtInserimento(Date dtInserimento) {
		this.dtInserimento = dtInserimento;
	}
	
	public BigDecimal getIdRecapitiPersonaFisica() {
		return idRecapitiPersonaFisica;
	}
	
	public void setIdRecapitiPersonaFisica(BigDecimal idRecapitiPersonaFisica) {
		this.idRecapitiPersonaFisica = idRecapitiPersonaFisica;
	}
	
	public BigDecimal getIdDocumentoPersonaFisica() {
		return idDocumentoPersonaFisica;
	}
	
	public void setIdDocumentoPersonaFisica(BigDecimal idDocumentoPersonaFisica) {
		this.idDocumentoPersonaFisica = idDocumentoPersonaFisica;
	}
	
	public Date getDtFineValidita() {
		return dtFineValidita;
	}
	
	public void setDtFineValidita(Date dtFineValidita) {
		this.dtFineValidita = dtFineValidita;
	}
	
	public BigDecimal getIdSoggetto() {
		return idSoggetto;
	}
	
	public void setIdSoggetto(BigDecimal idSoggetto) {
		this.idSoggetto = idSoggetto;
	}
	
	public BigDecimal getIdEstremiBancari() {
		return idEstremiBancari;
	}
	
	public void setIdEstremiBancari(BigDecimal idEstremiBancari) {
		this.idEstremiBancari = idEstremiBancari;
	}
	
	public BigDecimal getIdUtenteAgg() {
		return idUtenteAgg;
	}
	
	public void setIdUtenteAgg(BigDecimal idUtenteAgg) {
		this.idUtenteAgg = idUtenteAgg;
	}
	
	public BigDecimal getIdTipoAnagrafica() {
		return idTipoAnagrafica;
	}
	
	public void setIdTipoAnagrafica(BigDecimal idTipoAnagrafica) {
		this.idTipoAnagrafica = idTipoAnagrafica;
	}
	
	public BigDecimal getIdPersonaFisica() {
		return idPersonaFisica;
	}
	
	public void setIdPersonaFisica(BigDecimal idPersonaFisica) {
		this.idPersonaFisica = idPersonaFisica;
	}
	
	public BigDecimal getIdTipoBeneficiario() {
		return idTipoBeneficiario;
	}
	
	public void setIdTipoBeneficiario(BigDecimal idTipoBeneficiario) {
		this.idTipoBeneficiario = idTipoBeneficiario;
	}
	
	public BigDecimal getIdIndirizzoPersonaFisica() {
		return idIndirizzoPersonaFisica;
	}
	
	public void setIdIndirizzoPersonaFisica(BigDecimal idIndirizzoPersonaFisica) {
		this.idIndirizzoPersonaFisica = idIndirizzoPersonaFisica;
	}
	
	public Date getDtAggiornamento() {
		return dtAggiornamento;
	}
	
	public void setDtAggiornamento(Date dtAggiornamento) {
		this.dtAggiornamento = dtAggiornamento;
	}
	
	public boolean isValid() {
		boolean isValid = false;
                if (isPKValid() && dtInizioValidita != null && idDomanda != null && idUtenteIns != null && dtInserimento != null && idSoggetto != null && idTipoAnagrafica != null) {
   			isValid = true;
   		}
   		return isValid;
	}
	public boolean isPKValid() {
		boolean isPkValid = false;
		if (progrSoggettoDomanda != null ) {
   			isPkValid = true;
   		}

   		return isPkValid;
	}
	
	public String toString() {
		
	    String temp = "";
	    StringBuffer sb = new StringBuffer();
	    sb.append("\t\n" + this.getClass().getName() + "\t\n");
	    
	    temp = DataFilter.removeNull( idEnteGiuridico);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idEnteGiuridico: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idIscrizionePersonaGiurid);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idIscrizionePersonaGiurid: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( progrSoggettoDomanda);
	    if (!DataFilter.isEmpty(temp)) sb.append(" progrSoggettoDomanda: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtInizioValidita);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtInizioValidita: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idDomanda);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idDomanda: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idUtenteIns);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idUtenteIns: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtInserimento);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtInserimento: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idRecapitiPersonaFisica);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idRecapitiPersonaFisica: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idDocumentoPersonaFisica);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idDocumentoPersonaFisica: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtFineValidita);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtFineValidita: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idSoggetto);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idSoggetto: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idEstremiBancari);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idEstremiBancari: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idUtenteAgg);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idUtenteAgg: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idTipoAnagrafica);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idTipoAnagrafica: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idPersonaFisica);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idPersonaFisica: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idTipoBeneficiario);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idTipoBeneficiario: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idIndirizzoPersonaFisica);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idIndirizzoPersonaFisica: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtAggiornamento);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtAggiornamento: " + temp + "\t\n");
	    
	    return sb.toString();
	}
	
	public java.util.List<String> getPK() {
		java.util.List<String> pk=new java.util.ArrayList<String>();
		
			pk.add("progrSoggettoDomanda");
		
	    return pk;
	}
	
	
}
