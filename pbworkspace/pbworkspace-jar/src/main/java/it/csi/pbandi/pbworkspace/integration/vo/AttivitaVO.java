/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbworkspace.integration.vo;

public class AttivitaVO {
	private String acronimoProgetto;
	private String codiceVisualizzato;
	private String denominazioneLock;
	private String descrBreveTask;
	private String descrTask;
	private String flagOpt;
	private String flagLock;
	private Long idBusiness;
	private Long idNotifica;
	private Long idProgetto;
	private String nomeBandoLinea;
	private Long progrBandoLineaIntervento;
	private String titoloProgetto;
	private String flagUpdRecapito;
	private String flagRichAbilitazUtente;

	public String getFlagRichAbilitazUtente() {
		return flagRichAbilitazUtente;
	}

	public void setFlagRichAbilitazUtente(String flagRichAbilitazUtente) {
		this.flagRichAbilitazUtente = flagRichAbilitazUtente;
	}

	public String getFlagUpdRecapito() {
		return flagUpdRecapito;
	}

	public void setFlagUpdRecapito(String flagUpdRecapito) {
		this.flagUpdRecapito = flagUpdRecapito;
	}

	public String getAcronimoProgetto() {
		return acronimoProgetto;
	}

	public void setAcronimoProgetto(String acronimoProgetto) {
		this.acronimoProgetto = acronimoProgetto;
	}

	public String getCodiceVisualizzato() {
		return codiceVisualizzato;
	}

	public void setCodiceVisualizzato(String codiceVisualizzato) {
		this.codiceVisualizzato = codiceVisualizzato;
	}

	public String getDenominazioneLock() {
		return denominazioneLock;
	}

	public void setDenominazioneLock(String denominazioneLock) {
		this.denominazioneLock = denominazioneLock;
	}

	public String getDescrBreveTask() {
		return descrBreveTask;
	}

	public void setDescrBreveTask(String descrBreveTask) {
		this.descrBreveTask = descrBreveTask;
	}

	public String getDescrTask() {
		return descrTask;
	}

	public void setDescrTask(String descrTask) {
		this.descrTask = descrTask;
	}

	public String getFlagOpt() {
		return flagOpt;
	}

	public void setFlagOpt(String flagOpt) {
		this.flagOpt = flagOpt;
	}

	public String getFlagLock() {
		return flagLock;
	}

	public void setFlagLock(String flagLock) {
		this.flagLock = flagLock;
	}

	public Long getIdBusiness() {
		return idBusiness;
	}

	public void setIdBusiness(Long idBusiness) {
		this.idBusiness = idBusiness;
	}

	public Long getIdNotifica() {
		return idNotifica;
	}

	public void setIdNotifica(Long idNotifica) {
		this.idNotifica = idNotifica;
	}

	public Long getIdProgetto() {
		return idProgetto;
	}

	public void setIdProgetto(Long idProgetto) {
		this.idProgetto = idProgetto;
	}

	public String getNomeBandoLinea() {
		return nomeBandoLinea;
	}

	public void setNomeBandoLinea(String nomeBandoLinea) {
		this.nomeBandoLinea = nomeBandoLinea;
	}

	public Long getProgrBandoLineaIntervento() {
		return progrBandoLineaIntervento;
	}

	public void setProgrBandoLineaIntervento(Long progrBandoLineaIntervento) {
		this.progrBandoLineaIntervento = progrBandoLineaIntervento;
	}

	public String getTitoloProgetto() {
		return titoloProgetto;
	}

	public void setTitoloProgetto(String titoloProgetto) {
		this.titoloProgetto = titoloProgetto;
	}

	@Override
	public String toString() {
		return "AttivitaVO [acronimoProgetto=" + acronimoProgetto + ", codiceVisualizzato=" + codiceVisualizzato
				+ ", denominazioneLock=" + denominazioneLock + ", descrBreveTask=" + descrBreveTask + ", descrTask="
				+ descrTask + ", flagOpt=" + flagOpt + ", flagLock=" + flagLock + ", idBusiness=" + idBusiness
				+ ", idNotifica=" + idNotifica + ", idProgetto=" + idProgetto + ", nomeBandoLinea=" + nomeBandoLinea
				+ ", progrBandoLineaIntervento=" + progrBandoLineaIntervento + ", titoloProgetto=" + titoloProgetto + ", flagUpdRecapito=" + flagUpdRecapito
				+ "]";
	}

}
