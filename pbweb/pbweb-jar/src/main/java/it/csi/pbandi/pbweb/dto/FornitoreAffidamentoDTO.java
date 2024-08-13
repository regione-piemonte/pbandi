/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweb.dto;

public class FornitoreAffidamentoDTO implements java.io.Serializable {

	static final long serialVersionUID = 1;

	private java.lang.Long idFornitore = null;
	private java.lang.Long idAppalto = null;
	private java.lang.Long idTipoPercettore = null;
	private java.lang.Long idTipoSoggetto = null;
	private java.lang.String descTipoPercettore = null;
	private java.util.Date dtInvioVerificaAffidamento = null;
	private java.lang.String flgInvioVerificaAffidamento = null;
	private java.lang.String codiceFiscaleFornitore = null;
	private java.lang.String nomeFornitore = null;
	private java.lang.String cognomeFornitore = null;
	private java.lang.String partitaIvaFornitore = null;
	private java.lang.String denominazioneFornitore = null;
	
	public java.lang.Long getIdFornitore() {
		return idFornitore;
	}
	public void setIdFornitore(java.lang.Long idFornitore) {
		this.idFornitore = idFornitore;
	}
	public java.lang.Long getIdAppalto() {
		return idAppalto;
	}
	public void setIdAppalto(java.lang.Long idAppalto) {
		this.idAppalto = idAppalto;
	}
	public java.lang.Long getIdTipoPercettore() {
		return idTipoPercettore;
	}
	public void setIdTipoPercettore(java.lang.Long idTipoPercettore) {
		this.idTipoPercettore = idTipoPercettore;
	}
	public java.lang.Long getIdTipoSoggetto() {
		return idTipoSoggetto;
	}
	public void setIdTipoSoggetto(java.lang.Long idTipoSoggetto) {
		this.idTipoSoggetto = idTipoSoggetto;
	}
	public java.lang.String getDescTipoPercettore() {
		return descTipoPercettore;
	}
	public void setDescTipoPercettore(java.lang.String descTipoPercettore) {
		this.descTipoPercettore = descTipoPercettore;
	}
	public java.util.Date getDtInvioVerificaAffidamento() {
		return dtInvioVerificaAffidamento;
	}
	public void setDtInvioVerificaAffidamento(java.util.Date dtInvioVerificaAffidamento) {
		this.dtInvioVerificaAffidamento = dtInvioVerificaAffidamento;
	}
	public java.lang.String getFlgInvioVerificaAffidamento() {
		return flgInvioVerificaAffidamento;
	}
	public void setFlgInvioVerificaAffidamento(java.lang.String flgInvioVerificaAffidamento) {
		this.flgInvioVerificaAffidamento = flgInvioVerificaAffidamento;
	}
	public java.lang.String getCodiceFiscaleFornitore() {
		return codiceFiscaleFornitore;
	}
	public void setCodiceFiscaleFornitore(java.lang.String codiceFiscaleFornitore) {
		this.codiceFiscaleFornitore = codiceFiscaleFornitore;
	}
	public java.lang.String getNomeFornitore() {
		return nomeFornitore;
	}
	public void setNomeFornitore(java.lang.String nomeFornitore) {
		this.nomeFornitore = nomeFornitore;
	}
	public java.lang.String getCognomeFornitore() {
		return cognomeFornitore;
	}
	public void setCognomeFornitore(java.lang.String cognomeFornitore) {
		this.cognomeFornitore = cognomeFornitore;
	}
	public java.lang.String getPartitaIvaFornitore() {
		return partitaIvaFornitore;
	}
	public void setPartitaIvaFornitore(java.lang.String partitaIvaFornitore) {
		this.partitaIvaFornitore = partitaIvaFornitore;
	}
	public java.lang.String getDenominazioneFornitore() {
		return denominazioneFornitore;
	}
	public void setDenominazioneFornitore(java.lang.String denominazioneFornitore) {
		this.denominazioneFornitore = denominazioneFornitore;
	}

}
