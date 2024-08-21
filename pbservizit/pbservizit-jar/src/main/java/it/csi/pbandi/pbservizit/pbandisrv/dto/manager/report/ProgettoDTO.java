/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.pbandisrv.dto.manager.report;

public class ProgettoDTO implements java.io.Serializable {
	
	static final long serialVersionUID = 1;
	protected java.lang.Long idProgetto = null;
	protected java.lang.String titoloProgetto = null;
	protected java.lang.String codiceProgetto = null;
	protected java.lang.String codiceVisualizzato= null;
	protected java.lang.Double totaleQuietanzato = null;
	protected java.lang.String numeroConcessione = null;
	protected java.lang.String tipologia = null;
	protected java.util.Date dataConcessione = null;
	protected java.lang.String cup = null;
	protected Double sogliaSpesaCalcErogazioni = null;
	
	public java.lang.Long getIdProgetto() {
		return idProgetto;
	}
	public void setIdProgetto(java.lang.Long idProgetto) {
		this.idProgetto = idProgetto;
	}
	public java.lang.String getTitoloProgetto() {
		return titoloProgetto;
	}
	public void setTitoloProgetto(java.lang.String titoloProgetto) {
		this.titoloProgetto = titoloProgetto;
	}
	public java.lang.String getCodiceProgetto() {
		return codiceProgetto;
	}
	public void setCodiceProgetto(java.lang.String codiceProgetto) {
		this.codiceProgetto = codiceProgetto;
	}
	public java.lang.Double getTotaleQuietanzato() {
		return totaleQuietanzato;
	}
	public void setTotaleQuietanzato(java.lang.Double totaleQuietanzato) {
		this.totaleQuietanzato = totaleQuietanzato;
	}
	public java.lang.String getNumeroConcessione() {
		return numeroConcessione;
	}
	public void setNumeroConcessione(java.lang.String numeroConcessione) {
		this.numeroConcessione = numeroConcessione;
	}
	public java.lang.String getTipologia() {
		return tipologia;
	}
	public void setTipologia(java.lang.String tipologia) {
		this.tipologia = tipologia;
	}
	public java.util.Date getDataConcessione() {
		return dataConcessione;
	}
	public void setDataConcessione(java.util.Date dataConcessione) {
		this.dataConcessione = dataConcessione;
	}
	public java.lang.String getCup() {
		return cup;
	}
	public void setCup(java.lang.String cup) {
		this.cup = cup;
	}
	public void setCodiceVisualizzato(java.lang.String codiceVisualizzato) {
		this.codiceVisualizzato = codiceVisualizzato;
	}
	public java.lang.String getCodiceVisualizzato() {
		return codiceVisualizzato;
	}
	public void setSogliaSpesaCalcErogazioni(Double sogliaSpesaCalcErogazioni) {
		this.sogliaSpesaCalcErogazioni = sogliaSpesaCalcErogazioni;
	}
	public Double getSogliaSpesaCalcErogazioni() {
		return sogliaSpesaCalcErogazioni;
	}
	@Override
	public String toString() {
		return "ProgettoDTO [idProgetto=" + idProgetto + ", titoloProgetto=" + titoloProgetto + ", codiceProgetto="
				+ codiceProgetto + ", codiceVisualizzato=" + codiceVisualizzato + ", totaleQuietanzato="
				+ totaleQuietanzato + ", numeroConcessione=" + numeroConcessione + ", tipologia=" + tipologia
				+ ", dataConcessione=" + dataConcessione + ", cup=" + cup + ", sogliaSpesaCalcErogazioni="
				+ sogliaSpesaCalcErogazioni + "]";
	}

	


}
