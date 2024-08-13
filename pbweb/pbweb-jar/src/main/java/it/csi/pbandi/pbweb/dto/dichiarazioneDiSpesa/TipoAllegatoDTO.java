/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweb.dto.dichiarazioneDiSpesa;

public class TipoAllegatoDTO implements java.io.Serializable {
	
	static final long serialVersionUID = 1;

	private java.lang.String descTipoAllegato = null;
	private java.lang.Long idTipoDocumentoIndex = null;
	private java.lang.Long progrBandoLineaIntervento = null;
	private java.lang.Long idDichiarazioneSpesa = null;
	private java.lang.Long idMicroSezioneModulo = null;
	private java.lang.String flagAllegato = null;
	private java.lang.Long numOrdinamentoMicroSezione = null;
	private java.lang.Long idProgetto = null;
	
	public java.lang.String getDescTipoAllegato() {
		return descTipoAllegato;
	}
	public void setDescTipoAllegato(java.lang.String descTipoAllegato) {
		this.descTipoAllegato = descTipoAllegato;
	}
	public java.lang.Long getIdTipoDocumentoIndex() {
		return idTipoDocumentoIndex;
	}
	public void setIdTipoDocumentoIndex(java.lang.Long idTipoDocumentoIndex) {
		this.idTipoDocumentoIndex = idTipoDocumentoIndex;
	}
	public java.lang.Long getProgrBandoLineaIntervento() {
		return progrBandoLineaIntervento;
	}
	public void setProgrBandoLineaIntervento(java.lang.Long progrBandoLineaIntervento) {
		this.progrBandoLineaIntervento = progrBandoLineaIntervento;
	}
	public java.lang.Long getIdDichiarazioneSpesa() {
		return idDichiarazioneSpesa;
	}
	public void setIdDichiarazioneSpesa(java.lang.Long idDichiarazioneSpesa) {
		this.idDichiarazioneSpesa = idDichiarazioneSpesa;
	}
	public java.lang.Long getIdMicroSezioneModulo() {
		return idMicroSezioneModulo;
	}
	public void setIdMicroSezioneModulo(java.lang.Long idMicroSezioneModulo) {
		this.idMicroSezioneModulo = idMicroSezioneModulo;
	}
	public java.lang.String getFlagAllegato() {
		return flagAllegato;
	}
	public void setFlagAllegato(java.lang.String flagAllegato) {
		this.flagAllegato = flagAllegato;
	}
	public java.lang.Long getNumOrdinamentoMicroSezione() {
		return numOrdinamentoMicroSezione;
	}
	public void setNumOrdinamentoMicroSezione(java.lang.Long numOrdinamentoMicroSezione) {
		this.numOrdinamentoMicroSezione = numOrdinamentoMicroSezione;
	}
	public java.lang.Long getIdProgetto() {
		return idProgetto;
	}
	public void setIdProgetto(java.lang.Long idProgetto) {
		this.idProgetto = idProgetto;
	}

}
