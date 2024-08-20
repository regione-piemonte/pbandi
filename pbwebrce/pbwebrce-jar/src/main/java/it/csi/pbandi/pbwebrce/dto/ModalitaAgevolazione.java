/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebrce.dto;

public class ModalitaAgevolazione implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	
	private java.lang.String descModalita = null;
	private java.lang.Double importoMassimoAgevolato = null;
	private java.lang.Double percentualeMassimoAgevolato = null;
	private java.lang.Double ultimoImportoRichiesto = null;
	private java.lang.Double importoRichiesto = null;
	private java.lang.Double percentualeImportoRichiesto = null;
	private java.lang.Double importoErogato = null;
	private java.lang.Double ultimoImportoAgevolato = null;
	private java.lang.Double percentualeUltimoImportoAgevolato = null;
	private java.lang.Boolean modificabile = false;
	private java.lang.Double importoAgevolato = null;
	private java.lang.Double percentualeImportoAgevolato = null;
	private java.lang.Boolean hasImportoRichiestoError = false;
	private java.lang.String errorImportoRichiestoMsg = null;
	private java.lang.Boolean hasPercImportoRichiestoError = false;
	private java.lang.String errorPercImportoRichiestoMsg = null;
	private java.lang.Boolean hasImportoAgevolatoError = false;
	private java.lang.String errorImportoAgevolatoMsg = null;
	private java.lang.Boolean hasPercImportoAgevolatoError = false;
	private java.lang.String errorPercImportoAgevolatoMsg = null;
	private java.lang.Long idModalitaAgevolazione = null;
	private java.lang.Boolean isModalitaTotale = false;
	
	public ModalitaAgevolazione() {
		super();
	}

	public java.lang.String getDescModalita() {
		return descModalita;
	}

	public void setDescModalita(java.lang.String descModalita) {
		this.descModalita = descModalita;
	}

	public java.lang.Double getImportoMassimoAgevolato() {
		return importoMassimoAgevolato;
	}

	public void setImportoMassimoAgevolato(java.lang.Double importoMassimoAgevolato) {
		this.importoMassimoAgevolato = importoMassimoAgevolato;
	}

	public java.lang.Double getPercentualeMassimoAgevolato() {
		return percentualeMassimoAgevolato;
	}

	public void setPercentualeMassimoAgevolato(java.lang.Double percentualeMassimoAgevolato) {
		this.percentualeMassimoAgevolato = percentualeMassimoAgevolato;
	}

	public java.lang.Double getUltimoImportoRichiesto() {
		return ultimoImportoRichiesto;
	}

	public void setUltimoImportoRichiesto(java.lang.Double ultimoImportoRichiesto) {
		this.ultimoImportoRichiesto = ultimoImportoRichiesto;
	}

	public java.lang.Double getImportoRichiesto() {
		return importoRichiesto;
	}

	public void setImportoRichiesto(java.lang.Double importoRichiesto) {
		this.importoRichiesto = importoRichiesto;
	}

	public java.lang.Double getPercentualeImportoRichiesto() {
		return percentualeImportoRichiesto;
	}

	public void setPercentualeImportoRichiesto(java.lang.Double percentualeImportoRichiesto) {
		this.percentualeImportoRichiesto = percentualeImportoRichiesto;
	}

	public java.lang.Double getImportoErogato() {
		return importoErogato;
	}

	public void setImportoErogato(java.lang.Double importoErogato) {
		this.importoErogato = importoErogato;
	}

	public java.lang.Double getUltimoImportoAgevolato() {
		return ultimoImportoAgevolato;
	}

	public void setUltimoImportoAgevolato(java.lang.Double ultimoImportoAgevolato) {
		this.ultimoImportoAgevolato = ultimoImportoAgevolato;
	}

	public java.lang.Double getPercentualeUltimoImportoAgevolato() {
		return percentualeUltimoImportoAgevolato;
	}

	public void setPercentualeUltimoImportoAgevolato(java.lang.Double percentualeUltimoImportoAgevolato) {
		this.percentualeUltimoImportoAgevolato = percentualeUltimoImportoAgevolato;
	}

	public java.lang.Boolean getModificabile() {
		return modificabile;
	}

	public void setModificabile(java.lang.Boolean modificabile) {
		this.modificabile = modificabile;
	}

	public java.lang.Double getImportoAgevolato() {
		return importoAgevolato;
	}

	public void setImportoAgevolato(java.lang.Double importoAgevolato) {
		this.importoAgevolato = importoAgevolato;
	}

	public java.lang.Double getPercentualeImportoAgevolato() {
		return percentualeImportoAgevolato;
	}

	public void setPercentualeImportoAgevolato(java.lang.Double percentualeImportoAgevolato) {
		this.percentualeImportoAgevolato = percentualeImportoAgevolato;
	}

	public java.lang.Boolean getHasImportoRichiestoError() {
		return hasImportoRichiestoError;
	}

	public void setHasImportoRichiestoError(java.lang.Boolean hasImportoRichiestoError) {
		this.hasImportoRichiestoError = hasImportoRichiestoError;
	}

	public java.lang.String getErrorImportoRichiestoMsg() {
		return errorImportoRichiestoMsg;
	}

	public void setErrorImportoRichiestoMsg(java.lang.String errorImportoRichiestoMsg) {
		this.errorImportoRichiestoMsg = errorImportoRichiestoMsg;
	}

	public java.lang.Boolean getHasPercImportoRichiestoError() {
		return hasPercImportoRichiestoError;
	}

	public void setHasPercImportoRichiestoError(java.lang.Boolean hasPercImportoRichiestoError) {
		this.hasPercImportoRichiestoError = hasPercImportoRichiestoError;
	}

	public java.lang.String getErrorPercImportoRichiestoMsg() {
		return errorPercImportoRichiestoMsg;
	}

	public void setErrorPercImportoRichiestoMsg(java.lang.String errorPercImportoRichiestoMsg) {
		this.errorPercImportoRichiestoMsg = errorPercImportoRichiestoMsg;
	}

	public java.lang.Boolean getHasImportoAgevolatoError() {
		return hasImportoAgevolatoError;
	}

	public void setHasImportoAgevolatoError(java.lang.Boolean hasImportoAgevolatoError) {
		this.hasImportoAgevolatoError = hasImportoAgevolatoError;
	}

	public java.lang.String getErrorImportoAgevolatoMsg() {
		return errorImportoAgevolatoMsg;
	}

	public void setErrorImportoAgevolatoMsg(java.lang.String errorImportoAgevolatoMsg) {
		this.errorImportoAgevolatoMsg = errorImportoAgevolatoMsg;
	}

	public java.lang.Boolean getHasPercImportoAgevolatoError() {
		return hasPercImportoAgevolatoError;
	}

	public void setHasPercImportoAgevolatoError(java.lang.Boolean hasPercImportoAgevolatoError) {
		this.hasPercImportoAgevolatoError = hasPercImportoAgevolatoError;
	}

	public java.lang.String getErrorPercImportoAgevolatoMsg() {
		return errorPercImportoAgevolatoMsg;
	}

	public void setErrorPercImportoAgevolatoMsg(java.lang.String errorPercImportoAgevolatoMsg) {
		this.errorPercImportoAgevolatoMsg = errorPercImportoAgevolatoMsg;
	}

	public java.lang.Long getIdModalitaAgevolazione() {
		return idModalitaAgevolazione;
	}

	public void setIdModalitaAgevolazione(java.lang.Long idModalitaAgevolazione) {
		this.idModalitaAgevolazione = idModalitaAgevolazione;
	}

	public java.lang.Boolean getIsModalitaTotale() {
		return isModalitaTotale;
	}

	public void setIsModalitaTotale(java.lang.Boolean isModalitaTotale) {
		this.isModalitaTotale = isModalitaTotale;
	}

}
