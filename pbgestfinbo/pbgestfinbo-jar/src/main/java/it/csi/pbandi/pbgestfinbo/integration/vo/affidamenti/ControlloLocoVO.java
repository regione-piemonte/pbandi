/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.integration.vo.affidamenti;

import java.math.BigDecimal;
import java.sql.Date;

public class ControlloLocoVO {
	
	private String descBando; 
	private String codVisualizzato; 
	private String denominazione; 
	private String descStatoControllo; 
	private String descTipoControllo; 
	private BigDecimal importoDaControllare; 
	private String descAttivita; 
	private BigDecimal idControllo;
	private boolean isPersonaGiuridica; 
	private BigDecimal idGiuPersFis;
	private Date dataAvvioControlli; 
	private Date dataInizioControlli; 
	private Date dataFineControlli; 
	private Date dataVisitaControllo; 
	private BigDecimal importoIrregolarita; 
	private BigDecimal importoAgevIrreg;
	private String istruttoreVisita; 
	private String descTipoVisita; 
	private String flagSif; 
	private BigDecimal idProgetto; 
	private int idStatoControllo; 
	private int idAttivitaContrLoco;
	private int  idAutoritaControllante; 
	private String tipoControllo; 
	private BigDecimal idSoggettoBenef; 
	private String numProtocollo; 
	private BigDecimal progrBandoLinea;
	private String descAutoritaControllante; 
	private String descStatoChecklist; 
	private int idStatoChecklist; 
	
	
	
	
	public String getDescStatoChecklist() {
		return descStatoChecklist;
	}
	public void setDescStatoChecklist(String descStatoChecklist) {
		this.descStatoChecklist = descStatoChecklist;
	}
	public int getIdStatoChecklist() {
		return idStatoChecklist;
	}
	public void setIdStatoChecklist(int idStatoChecklist) {
		this.idStatoChecklist = idStatoChecklist;
	}
	public String getDescAutoritaControllante() {
		return descAutoritaControllante;
	}
	public void setDescAutoritaControllante(String descAutoritaControllante) {
		this.descAutoritaControllante = descAutoritaControllante;
	}
	public BigDecimal getProgrBandoLinea() {
		return progrBandoLinea;
	}
	public void setProgrBandoLinea(BigDecimal progrBandoLinea) {
		this.progrBandoLinea = progrBandoLinea;
	}
	public int getIdAutoritaControllante() {
		return idAutoritaControllante;
	}
	public void setIdAutoritaControllante(int idAutoritaControllante) {
		this.idAutoritaControllante = idAutoritaControllante;
	}
	public String getTipoControllo() {
		return tipoControllo;
	}
	public void setTipoControllo(String tipoControllo) {
		this.tipoControllo = tipoControllo;
	}
	public BigDecimal getIdSoggettoBenef() {
		return idSoggettoBenef;
	}
	public void setIdSoggettoBenef(BigDecimal idSoggettoBenef) {
		this.idSoggettoBenef = idSoggettoBenef;
	}
	public String getNumProtocollo() {
		return numProtocollo;
	}
	public void setNumProtocollo(String numProtocollo) {
		this.numProtocollo = numProtocollo;
	}
	public int getIdAttivitaContrLoco() {
		return idAttivitaContrLoco;
	}
	public void setIdAttivitaContrLoco(int idAttivitaContrLoco) {
		this.idAttivitaContrLoco = idAttivitaContrLoco;
	}
	public int getIdStatoControllo() {
		return idStatoControllo;
	}
	public void setIdStatoControllo(int idStatoControllo) {
		this.idStatoControllo = idStatoControllo;
	}
	public BigDecimal getIdProgetto() {
		return idProgetto;
	}
	public void setIdProgetto(BigDecimal idProgetto) {
		this.idProgetto = idProgetto;
	}
	public String getFlagSif() {
		return flagSif;
	}
	public void setFlagSif(String flagSif) {
		this.flagSif = flagSif;
	}
	public String getDescBando() {
		return descBando;
	}
	public void setDescBando(String descBando) {
		this.descBando = descBando;
	}
	public String getCodVisualizzato() {
		return codVisualizzato;
	}
	public void setCodVisualizzato(String codVisualizzato) {
		this.codVisualizzato = codVisualizzato;
	}
	public String getDenominazione() {
		return denominazione;
	}
	public void setDenominazione(String denominazione) {
		this.denominazione = denominazione;
	}
	public String getDescStatoControllo() {
		return descStatoControllo;
	}
	public void setDescStatoControllo(String descStatoControllo) {
		this.descStatoControllo = descStatoControllo;
	}
	public String getDescTipoControllo() {
		return descTipoControllo;
	}
	public void setDescTipoControllo(String descTipoControllo) {
		this.descTipoControllo = descTipoControllo;
	}
	public BigDecimal getImportoDaControllare() {
		return importoDaControllare;
	}
	public void setImportoDaControllare(BigDecimal importoDaControllare) {
		this.importoDaControllare = importoDaControllare;
	}
	public String getDescAttivita() {
		return descAttivita;
	}
	public void setDescAttivita(String descAttivita) {
		this.descAttivita = descAttivita;
	}
	public BigDecimal getIdControllo() {
		return idControllo;
	}
	public void setIdControllo(BigDecimal idControllo) {
		this.idControllo = idControllo;
	}
	public boolean isPersonaGiuridica() {
		return isPersonaGiuridica;
	}
	public void setPersonaGiuridica(boolean isPersonaGiuridica) {
		this.isPersonaGiuridica = isPersonaGiuridica;
	}
	public BigDecimal getIdGiuPersFis() {
		return idGiuPersFis;
	}
	public void setIdGiuPersFis(BigDecimal idGiuPersFis) {
		this.idGiuPersFis = idGiuPersFis;
	}
	public Date getDataAvvioControlli() {
		return dataAvvioControlli;
	}
	public void setDataAvvioControlli(Date dataAvvioControlli) {
		this.dataAvvioControlli = dataAvvioControlli;
	}
	public Date getDataInizioControlli() {
		return dataInizioControlli;
	}
	public void setDataInizioControlli(Date dataInizioControlli) {
		this.dataInizioControlli = dataInizioControlli;
	}
	public Date getDataFineControlli() {
		return dataFineControlli;
	}
	public void setDataFineControlli(Date dataFineControlli) {
		this.dataFineControlli = dataFineControlli;
	}
	public Date getDataVisitaControllo() {
		return dataVisitaControllo;
	}
	public void setDataVisitaControllo(Date dataVisitaControllo) {
		this.dataVisitaControllo = dataVisitaControllo;
	}
	public BigDecimal getImportoIrregolarita() {
		return importoIrregolarita;
	}
	public void setImportoIrregolarita(BigDecimal importoIrregolarita) {
		this.importoIrregolarita = importoIrregolarita;
	}
	public BigDecimal getImportoAgevIrreg() {
		return importoAgevIrreg;
	}
	public void setImportoAgevIrreg(BigDecimal importoAgevIrreg) {
		this.importoAgevIrreg = importoAgevIrreg;
	}
	public String getIstruttoreVisita() {
		return istruttoreVisita;
	}
	public void setIstruttoreVisita(String istruttoreVisita) {
		this.istruttoreVisita = istruttoreVisita;
	}
	public String getDescTipoVisita() {
		return descTipoVisita;
	}
	public void setDescTipoVisita(String descTipoVisita) {
		this.descTipoVisita = descTipoVisita;
	}
	
	
	
	
	

}
