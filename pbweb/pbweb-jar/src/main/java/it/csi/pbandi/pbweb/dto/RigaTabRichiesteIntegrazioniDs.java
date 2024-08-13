/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweb.dto;

public class RigaTabRichiesteIntegrazioniDs implements java.io.Serializable {

	private java.lang.Long idIntegrazioneSpesa = null;
	private java.lang.String idDichiarazioneSpesa = null;
	private java.lang.String descrizione = null;
	private java.lang.String dataRichiesta = null;
	private java.lang.String dataInvio = null;
	private java.lang.String dataDichiarazione = null;
	private java.lang.String dataNumDichiarazione = null;	
	private java.util.ArrayList<it.csi.pbandi.pbweb.dto.AllegatoIntegrazioneDs> allegati = new java.util.ArrayList<it.csi.pbandi.pbweb.dto.AllegatoIntegrazioneDs>();
	
	/*
	private java.lang.String linkAllegati = null;
	private java.lang.String iconaInvia = null;
	private java.lang.String iconaAllega = null;
	*/

	public RigaTabRichiesteIntegrazioniDs() {}

	public java.lang.Long getIdIntegrazioneSpesa() {
		return idIntegrazioneSpesa;
	}

	public void setIdIntegrazioneSpesa(java.lang.Long idIntegrazioneSpesa) {
		this.idIntegrazioneSpesa = idIntegrazioneSpesa;
	}

	public java.lang.String getIdDichiarazioneSpesa() {
		return idDichiarazioneSpesa;
	}

	public void setIdDichiarazioneSpesa(java.lang.String idDichiarazioneSpesa) {
		this.idDichiarazioneSpesa = idDichiarazioneSpesa;
	}

	public java.lang.String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(java.lang.String descrizione) {
		this.descrizione = descrizione;
	}

	public java.lang.String getDataRichiesta() {
		return dataRichiesta;
	}

	public void setDataRichiesta(java.lang.String dataRichiesta) {
		this.dataRichiesta = dataRichiesta;
	}

	public java.lang.String getDataInvio() {
		return dataInvio;
	}

	public void setDataInvio(java.lang.String dataInvio) {
		this.dataInvio = dataInvio;
	}

	public java.lang.String getDataDichiarazione() {
		return dataDichiarazione;
	}

	public void setDataDichiarazione(java.lang.String dataDichiarazione) {
		this.dataDichiarazione = dataDichiarazione;
	}

	public java.lang.String getDataNumDichiarazione() {
		return dataNumDichiarazione;
	}

	public void setDataNumDichiarazione(java.lang.String dataNumDichiarazione) {
		this.dataNumDichiarazione = dataNumDichiarazione;
	}

	public java.util.ArrayList<it.csi.pbandi.pbweb.dto.AllegatoIntegrazioneDs> getAllegati() {
		return allegati;
	}

	public void setAllegati(java.util.ArrayList<it.csi.pbandi.pbweb.dto.AllegatoIntegrazioneDs> allegati) {
		this.allegati = allegati;
	}

}
