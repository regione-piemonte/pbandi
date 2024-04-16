/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbworkspace.dto.schedaProgetto;

public class SchedaProgetto implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	
	private java.lang.String idProgetto = null;
	private java.lang.String idBandoLinea = null;
	private java.lang.String idLineaNormativa = null;
	private java.lang.String idLineaAsse = null;
	private java.lang.String flagSalvaIntermediario = null;
	private it.csi.pbandi.pbworkspace.dto.schedaProgetto.InformazioniBase informazioniBase = null;
	private java.util.ArrayList<it.csi.pbandi.pbworkspace.dto.schedaProgetto.SedeIntervento> sediIntervento = new java.util.ArrayList<it.csi.pbandi.pbworkspace.dto.schedaProgetto.SedeIntervento>();
	private it.csi.pbandi.pbworkspace.dto.schedaProgetto.SoggettoGenerico beneficiario = null;
	private it.csi.pbandi.pbworkspace.dto.schedaProgetto.SoggettoGenerico rappresentanteLegale = null;
	private it.csi.pbandi.pbworkspace.dto.schedaProgetto.SoggettoGenerico intermediario = null;
	private java.util.ArrayList<it.csi.pbandi.pbworkspace.dto.schedaProgetto.SoggettoGenerico> altriSoggetti = new java.util.ArrayList<it.csi.pbandi.pbworkspace.dto.schedaProgetto.SoggettoGenerico>();
	private it.csi.pbandi.pbworkspace.dto.schedaProgetto.SedeIntervento sedeInterventoDefault = null;
	private it.csi.pbandi.pbworkspace.dto.schedaProgetto.SoggettoGenerico altroSoggettoDefault = null;

	public SchedaProgetto() {
		super();
	}

	public java.lang.String getIdProgetto() {
		return idProgetto;
	}

	public void setIdProgetto(java.lang.String idProgetto) {
		this.idProgetto = idProgetto;
	}

	public java.lang.String getIdBandoLinea() {
		return idBandoLinea;
	}

	public void setIdBandoLinea(java.lang.String idBandoLinea) {
		this.idBandoLinea = idBandoLinea;
	}

	public java.lang.String getIdLineaNormativa() {
		return idLineaNormativa;
	}

	public void setIdLineaNormativa(java.lang.String idLineaNormativa) {
		this.idLineaNormativa = idLineaNormativa;
	}

	public java.lang.String getIdLineaAsse() {
		return idLineaAsse;
	}

	public void setIdLineaAsse(java.lang.String idLineaAsse) {
		this.idLineaAsse = idLineaAsse;
	}

	public java.lang.String getFlagSalvaIntermediario() {
		return flagSalvaIntermediario;
	}

	public void setFlagSalvaIntermediario(java.lang.String flagSalvaIntermediario) {
		this.flagSalvaIntermediario = flagSalvaIntermediario;
	}

	public it.csi.pbandi.pbworkspace.dto.schedaProgetto.InformazioniBase getInformazioniBase() {
		return informazioniBase;
	}

	public void setInformazioniBase(it.csi.pbandi.pbworkspace.dto.schedaProgetto.InformazioniBase informazioniBase) {
		this.informazioniBase = informazioniBase;
	}

	public java.util.ArrayList<it.csi.pbandi.pbworkspace.dto.schedaProgetto.SedeIntervento> getSediIntervento() {
		return sediIntervento;
	}

	public void setSediIntervento(
			java.util.ArrayList<it.csi.pbandi.pbworkspace.dto.schedaProgetto.SedeIntervento> sediIntervento) {
		this.sediIntervento = sediIntervento;
	}

	public it.csi.pbandi.pbworkspace.dto.schedaProgetto.SoggettoGenerico getBeneficiario() {
		return beneficiario;
	}

	public void setBeneficiario(it.csi.pbandi.pbworkspace.dto.schedaProgetto.SoggettoGenerico beneficiario) {
		this.beneficiario = beneficiario;
	}

	public it.csi.pbandi.pbworkspace.dto.schedaProgetto.SoggettoGenerico getRappresentanteLegale() {
		return rappresentanteLegale;
	}

	public void setRappresentanteLegale(
			it.csi.pbandi.pbworkspace.dto.schedaProgetto.SoggettoGenerico rappresentanteLegale) {
		this.rappresentanteLegale = rappresentanteLegale;
	}

	public it.csi.pbandi.pbworkspace.dto.schedaProgetto.SoggettoGenerico getIntermediario() {
		return intermediario;
	}

	public void setIntermediario(it.csi.pbandi.pbworkspace.dto.schedaProgetto.SoggettoGenerico intermediario) {
		this.intermediario = intermediario;
	}

	public java.util.ArrayList<it.csi.pbandi.pbworkspace.dto.schedaProgetto.SoggettoGenerico> getAltriSoggetti() {
		return altriSoggetti;
	}

	public void setAltriSoggetti(
			java.util.ArrayList<it.csi.pbandi.pbworkspace.dto.schedaProgetto.SoggettoGenerico> altriSoggetti) {
		this.altriSoggetti = altriSoggetti;
	}

	public it.csi.pbandi.pbworkspace.dto.schedaProgetto.SedeIntervento getSedeInterventoDefault() {
		return sedeInterventoDefault;
	}

	public void setSedeInterventoDefault(
			it.csi.pbandi.pbworkspace.dto.schedaProgetto.SedeIntervento sedeInterventoDefault) {
		this.sedeInterventoDefault = sedeInterventoDefault;
	}

	public it.csi.pbandi.pbworkspace.dto.schedaProgetto.SoggettoGenerico getAltroSoggettoDefault() {
		return altroSoggettoDefault;
	}

	public void setAltroSoggettoDefault(
			it.csi.pbandi.pbworkspace.dto.schedaProgetto.SoggettoGenerico altroSoggettoDefault) {
		this.altroSoggettoDefault = altroSoggettoDefault;
	}

}
