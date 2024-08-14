/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebcert.integration.vo;

import java.math.BigDecimal;
import java.sql.Date;

public class BandoLineaInterventoCertificazioneVO {
	private BigDecimal idPropostaCertificaz;
	private BigDecimal idTipoLineaIntervento;
	private BigDecimal idLineaDiIntervento;
	private String codIgrueT13T14;
  	private BigDecimal idProcesso;
	private Date dtInizioValidita;
  	private BigDecimal idTipoStrumentoProgr;
  	private BigDecimal numDelibera;
  	private BigDecimal idStrumentoAttuativo;
  	private BigDecimal idLineaDiInterventoPadre;
	private BigDecimal annoDelibera;
	private Date dtFineValidita;
  	private String codLivGerarchico;
	private String descBreveLinea;
	private String descLinea;
  	private String flagCampionRilev;
	public BigDecimal getIdPropostaCertificaz() {
		return idPropostaCertificaz;
	}
	public void setIdPropostaCertificaz(BigDecimal idPropostaCertificaz) {
		this.idPropostaCertificaz = idPropostaCertificaz;
	}
	public BigDecimal getIdTipoLineaIntervento() {
		return idTipoLineaIntervento;
	}
	public void setIdTipoLineaIntervento(BigDecimal idTipoLineaIntervento) {
		this.idTipoLineaIntervento = idTipoLineaIntervento;
	}
	public BigDecimal getIdLineaDiIntervento() {
		return idLineaDiIntervento;
	}
	public void setIdLineaDiIntervento(BigDecimal idLineaDiIntervento) {
		this.idLineaDiIntervento = idLineaDiIntervento;
	}
	public String getCodIgrueT13T14() {
		return codIgrueT13T14;
	}
	public void setCodIgrueT13T14(String codIgrueT13T14) {
		this.codIgrueT13T14 = codIgrueT13T14;
	}
	public BigDecimal getIdProcesso() {
		return idProcesso;
	}
	public void setIdProcesso(BigDecimal idProcesso) {
		this.idProcesso = idProcesso;
	}
	public Date getDtInizioValidita() {
		return dtInizioValidita;
	}
	public void setDtInizioValidita(Date dtInizioValidita) {
		this.dtInizioValidita = dtInizioValidita;
	}
	public BigDecimal getIdTipoStrumentoProgr() {
		return idTipoStrumentoProgr;
	}
	public void setIdTipoStrumentoProgr(BigDecimal idTipoStrumentoProgr) {
		this.idTipoStrumentoProgr = idTipoStrumentoProgr;
	}
	public BigDecimal getNumDelibera() {
		return numDelibera;
	}
	public void setNumDelibera(BigDecimal numDelibera) {
		this.numDelibera = numDelibera;
	}
	public BigDecimal getIdStrumentoAttuativo() {
		return idStrumentoAttuativo;
	}
	public void setIdStrumentoAttuativo(BigDecimal idStrumentoAttuativo) {
		this.idStrumentoAttuativo = idStrumentoAttuativo;
	}
	public BigDecimal getIdLineaDiInterventoPadre() {
		return idLineaDiInterventoPadre;
	}
	public void setIdLineaDiInterventoPadre(BigDecimal idLineaDiInterventoPadre) {
		this.idLineaDiInterventoPadre = idLineaDiInterventoPadre;
	}
	public BigDecimal getAnnoDelibera() {
		return annoDelibera;
	}
	public void setAnnoDelibera(BigDecimal annoDelibera) {
		this.annoDelibera = annoDelibera;
	}
	public Date getDtFineValidita() {
		return dtFineValidita;
	}
	public void setDtFineValidita(Date dtFineValidita) {
		this.dtFineValidita = dtFineValidita;
	}
	public String getCodLivGerarchico() {
		return codLivGerarchico;
	}
	public void setCodLivGerarchico(String codLivGerarchico) {
		this.codLivGerarchico = codLivGerarchico;
	}
	public String getDescBreveLinea() {
		return descBreveLinea;
	}
	public void setDescBreveLinea(String descBreveLinea) {
		this.descBreveLinea = descBreveLinea;
	}
	public String getDescLinea() {
		return descLinea;
	}
	public void setDescLinea(String descLinea) {
		this.descLinea = descLinea;
	}
	public String getFlagCampionRilev() {
		return flagCampionRilev;
	}
	public void setFlagCampionRilev(String flagCampionRilev) {
		this.flagCampionRilev = flagCampionRilev;
	}
  	
  	
}
