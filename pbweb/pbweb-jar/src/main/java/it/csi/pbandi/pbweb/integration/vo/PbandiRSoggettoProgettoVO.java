/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/


package it.csi.pbandi.pbweb.integration.vo;

import java.beans.IntrospectionException;
import java.math.*;
import java.sql.Date;
import java.util.Set;

import it.csi.pbandi.pbweb.util.BeanUtil;



public class PbandiRSoggettoProgettoVO {
  	
  	private BigDecimal idProgetto;
  	
  	private BigDecimal progrSoggettoProgetto;
  	
  	private BigDecimal idEnteGiuridico;
  	
  	private BigDecimal idIscrizionePersonaGiurid;
  	
  	private BigDecimal progrSoggettoDomanda;
  	
  	private String flagAggiornatoFlux;
  	
  	private Date dtInizioValidita;
  	
  	private Date dtInserimento;
  	
  	private BigDecimal idUtenteIns;
  	
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
  	
  	private Date dtAggiornamentoFlux;
  	
	public PbandiRSoggettoProgettoVO() {}
  	
	public PbandiRSoggettoProgettoVO (BigDecimal progrSoggettoProgetto) {
	
		this. progrSoggettoProgetto =  progrSoggettoProgetto;
	}
  	
	public PbandiRSoggettoProgettoVO (BigDecimal idProgetto, BigDecimal progrSoggettoProgetto, BigDecimal idEnteGiuridico, BigDecimal idIscrizionePersonaGiurid, BigDecimal progrSoggettoDomanda, String flagAggiornatoFlux, Date dtInizioValidita, Date dtInserimento, BigDecimal idUtenteIns, BigDecimal idRecapitiPersonaFisica, BigDecimal idDocumentoPersonaFisica, Date dtFineValidita, BigDecimal idSoggetto, BigDecimal idEstremiBancari, BigDecimal idUtenteAgg, BigDecimal idTipoAnagrafica, BigDecimal idPersonaFisica, BigDecimal idTipoBeneficiario, BigDecimal idIndirizzoPersonaFisica, Date dtAggiornamento,Date dtAggiornamentoFlux) {
	
		this. idProgetto =  idProgetto;
		this. progrSoggettoProgetto =  progrSoggettoProgetto;
		this. idEnteGiuridico =  idEnteGiuridico;
		this. idIscrizionePersonaGiurid =  idIscrizionePersonaGiurid;
		this. progrSoggettoDomanda =  progrSoggettoDomanda;
		this. flagAggiornatoFlux =  flagAggiornatoFlux;
		this. dtInizioValidita =  dtInizioValidita;
		this. dtInserimento =  dtInserimento;
		this. idUtenteIns =  idUtenteIns;
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
		this. dtAggiornamentoFlux =  dtAggiornamentoFlux;		  
	}
  	
  	
	public BigDecimal getIdProgetto() {
		return idProgetto;
	}
	
	public void setIdProgetto(BigDecimal idProgetto) {
		this.idProgetto = idProgetto;
	}
	
	public BigDecimal getProgrSoggettoProgetto() {
		return progrSoggettoProgetto;
	}
	
	public void setProgrSoggettoProgetto(BigDecimal progrSoggettoProgetto) {
		this.progrSoggettoProgetto = progrSoggettoProgetto;
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
	
	public String getFlagAggiornatoFlux() {
		return flagAggiornatoFlux;
	}
	
	public void setFlagAggiornatoFlux(String flagAggiornatoFlux) {
		this.flagAggiornatoFlux = flagAggiornatoFlux;
	}
	
	public Date getDtInizioValidita() {
		return dtInizioValidita;
	}
	
	public void setDtInizioValidita(Date dtInizioValidita) {
		this.dtInizioValidita = dtInizioValidita;
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
	
	public Date getDtAggiornamentoFlux() {
		return dtAggiornamentoFlux;
	}

	public void setDtAggiornamentoFlux(Date dtAggiornamentoFlux) {
		this.dtAggiornamentoFlux = dtAggiornamentoFlux;
	}
	
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("\nPbandiTAppaltoVO: ");
		try {
			Set<String> properties = BeanUtil.getProperties(this.getClass());
			for (String propName : properties) {
				sb.append("\n"+propName+" = "+BeanUtil.getPropertyValueByName(this, propName));
			}
		} catch (IntrospectionException e) {
		}
		return sb.toString();
	}
	
}
