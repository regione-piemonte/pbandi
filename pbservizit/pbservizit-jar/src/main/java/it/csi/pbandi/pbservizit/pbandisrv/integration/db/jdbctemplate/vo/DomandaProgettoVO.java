/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo;

import java.math.BigDecimal;
import java.sql.Date;

public class DomandaProgettoVO extends GenericVO  { 
	
	private BigDecimal idProgetto;
	private String titoloProgetto;
	private String cup;
	private String acronimoProgetto;
	private String codiceProgetto;
  	
  	private Date dtInizioProgettoPrevista;
  	
  	private BigDecimal idDomandaPadre;
  	
  	private BigDecimal progrBandoLineaIntervento;
  	
  	private BigDecimal numeroDomandaGefo;
  	
  	private String oraPresentazioneDomanda;
  	
  	private BigDecimal idDomanda;
  	
  	private Date dtInserimento;
  	
  	private BigDecimal idUtenteIns;
  	
  	private BigDecimal idStatoInvioDomanda;
  	
  	private String numeroDomanda;
  	
  	private BigDecimal idStatoDomanda;
  	
  	private Date dtFineProgettoPrevista;
  	
  	private String flagDomandaMaster;
  	
  	private Date dtPresentazioneDomanda;
  	
  	private Date dtOraInvio;
  	
  	private BigDecimal idUtenteAgg;
  	
  	private String numProtocolloDomanda;
  	
  	private Date dtProtocollazioneDomanda;
  	
  	private Date dtAggiornamento;
  	
  	private BigDecimal idTipoAiuto;
	
	private BigDecimal idAliquotaRitenuta;
	
	public BigDecimal getIdProgetto() {
		return idProgetto;
	}
	public void setIdProgetto(BigDecimal idProgetto) {
		this.idProgetto = idProgetto;
	}
	public String getTitoloProgetto() {
		return titoloProgetto;
	}
	public void setTitoloProgetto(String titoloProgetto) {
		this.titoloProgetto = titoloProgetto;
	}
	public String getCup() {
		return cup;
	}
	public void setCup(String cup) {
		this.cup = cup;
	}
	public String getAcronimoProgetto() {
		return acronimoProgetto;
	}
	public void setAcronimoProgetto(String acronimoProgetto) {
		this.acronimoProgetto = acronimoProgetto;
	}
	public String getCodiceProgetto() {
		return codiceProgetto;
	}
	public void setCodiceProgetto(String codiceProgetto) {
		this.codiceProgetto = codiceProgetto;
	}
	public Date getDtInizioProgettoPrevista() {
		return dtInizioProgettoPrevista;
	}
	public void setDtInizioProgettoPrevista(Date dtInizioProgettoPrevista) {
		this.dtInizioProgettoPrevista = dtInizioProgettoPrevista;
	}
	public BigDecimal getIdDomandaPadre() {
		return idDomandaPadre;
	}
	public void setIdDomandaPadre(BigDecimal idDomandaPadre) {
		this.idDomandaPadre = idDomandaPadre;
	}
	public BigDecimal getProgrBandoLineaIntervento() {
		return progrBandoLineaIntervento;
	}
	public void setProgrBandoLineaIntervento(BigDecimal progrBandoLineaIntervento) {
		this.progrBandoLineaIntervento = progrBandoLineaIntervento;
	}
	public BigDecimal getNumeroDomandaGefo() {
		return numeroDomandaGefo;
	}
	public void setNumeroDomandaGefo(BigDecimal numeroDomandaGefo) {
		this.numeroDomandaGefo = numeroDomandaGefo;
	}
	public String getOraPresentazioneDomanda() {
		return oraPresentazioneDomanda;
	}
	public void setOraPresentazioneDomanda(String oraPresentazioneDomanda) {
		this.oraPresentazioneDomanda = oraPresentazioneDomanda;
	}
	public BigDecimal getIdDomanda() {
		return idDomanda;
	}
	public void setIdDomanda(BigDecimal idDomanda) {
		this.idDomanda = idDomanda;
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
	public BigDecimal getIdStatoInvioDomanda() {
		return idStatoInvioDomanda;
	}
	public void setIdStatoInvioDomanda(BigDecimal idStatoInvioDomanda) {
		this.idStatoInvioDomanda = idStatoInvioDomanda;
	}
	public String getNumeroDomanda() {
		return numeroDomanda;
	}
	public void setNumeroDomanda(String numeroDomanda) {
		this.numeroDomanda = numeroDomanda;
	}
	public BigDecimal getIdStatoDomanda() {
		return idStatoDomanda;
	}
	public void setIdStatoDomanda(BigDecimal idStatoDomanda) {
		this.idStatoDomanda = idStatoDomanda;
	}
	public Date getDtFineProgettoPrevista() {
		return dtFineProgettoPrevista;
	}
	public void setDtFineProgettoPrevista(Date dtFineProgettoPrevista) {
		this.dtFineProgettoPrevista = dtFineProgettoPrevista;
	}
	public String getFlagDomandaMaster() {
		return flagDomandaMaster;
	}
	public void setFlagDomandaMaster(String flagDomandaMaster) {
		this.flagDomandaMaster = flagDomandaMaster;
	}
	public Date getDtPresentazioneDomanda() {
		return dtPresentazioneDomanda;
	}
	public void setDtPresentazioneDomanda(Date dtPresentazioneDomanda) {
		this.dtPresentazioneDomanda = dtPresentazioneDomanda;
	}
	public Date getDtOraInvio() {
		return dtOraInvio;
	}
	public void setDtOraInvio(Date dtOraInvio) {
		this.dtOraInvio = dtOraInvio;
	}
	public BigDecimal getIdUtenteAgg() {
		return idUtenteAgg;
	}
	public void setIdUtenteAgg(BigDecimal idUtenteAgg) {
		this.idUtenteAgg = idUtenteAgg;
	}
	public String getNumProtocolloDomanda() {
		return numProtocolloDomanda;
	}
	public void setNumProtocolloDomanda(String numProtocolloDomanda) {
		this.numProtocolloDomanda = numProtocolloDomanda;
	}
	public Date getDtProtocollazioneDomanda() {
		return dtProtocollazioneDomanda;
	}
	public void setDtProtocollazioneDomanda(Date dtProtocollazioneDomanda) {
		this.dtProtocollazioneDomanda = dtProtocollazioneDomanda;
	}
	public Date getDtAggiornamento() {
		return dtAggiornamento;
	}
	public void setDtAggiornamento(Date dtAggiornamento) {
		this.dtAggiornamento = dtAggiornamento;
	}
	public BigDecimal getIdTipoAiuto() {
		return idTipoAiuto;
	}
	public void setIdTipoAiuto(BigDecimal idTipoAiuto) {
		this.idTipoAiuto = idTipoAiuto;
	}
	public BigDecimal getIdAliquotaRitenuta() {
		return idAliquotaRitenuta;
	}
	public void setIdAliquotaRitenuta(BigDecimal idAliquotaRitenuta) {
		this.idAliquotaRitenuta = idAliquotaRitenuta;
	}

}
