/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweb.dto;

public class IntegrazioneRendicontazioneDTO implements java.io.Serializable {

	static final long serialVersionUID = 1;
	
	private java.lang.Long nDichiarazioneSpesa = null;
	private java.lang.Long idIntegrazioneSpesa = null;	
	private java.lang.String dataRichiesta = null;
	private java.lang.String dataNotifica = null;
	private java.lang.String nGgScadenza = null;

	private java.lang.String dataScadenza = null;
	private java.lang.String dataInvio = null;
	private java.lang.Long idStatoRichiesta = null;
	private java.lang.String statoRichiesta = null;
	private java.lang.String longStatoRichiesta = null;
	private java.lang.Boolean allegatiInseriti = null;

	private java.lang.String dtRichiesta = null;
	private java.lang.Long ggRichiesti = null;
	private java.lang.String motivazione = null;
	private java.lang.Long ggApprovati = null;

	private java.lang.Long idStatoProroga = null;
	private java.lang.String statoProroga = null;

	private java.lang.Boolean richiediProroga = null;

	private java.lang.Boolean allegatiAmmessiDocumentoSpesa = null;
	private java.lang.Boolean allegatiAmmessiQuietanze = null;
	private java.lang.Boolean allegatiAmmessiGenerici = null;
	private java.lang.Boolean allegatiAmmessi = null;

	public Long getIdStatoProroga() {
		return idStatoProroga;
	}

	public void setIdStatoProroga(Long idStatoProroga) {
		this.idStatoProroga = idStatoProroga;
	}

	public Boolean getRichiediProroga() {
		return richiediProroga;
	}

	public void setRichiediProroga(Boolean richiediProroga) {
		this.richiediProroga = richiediProroga;
	}

	public String getDataScadenza() {
		return dataScadenza;
	}

	public void setDataScadenza(String dataScadenza) {
		this.dataScadenza = dataScadenza;
	}

	public String getStatoProroga() {
		return statoProroga;
	}

	public void setStatoProroga(String statoProroga) {
		this.statoProroga = statoProroga;
	}

	public String getDtRichiesta() {
		return dtRichiesta;
	}

	public void setDtRichiesta(String dtRichiesta) {
		this.dtRichiesta = dtRichiesta;
	}

	public Long getGgRichiesti() {
		return ggRichiesti;
	}

	public void setGgRichiesti(Long ggRichiesti) {
		this.ggRichiesti = ggRichiesti;
	}

	public String getMotivazione() {
		return motivazione;
	}

	public void setMotivazione(String motivazione) {
		this.motivazione = motivazione;
	}

	public Long getGgApprovati() {
		return ggApprovati;
	}

	public void setGgApprovati(Long ggApprovati) {
		this.ggApprovati = ggApprovati;
	}

	public Boolean getAllegatiInseriti() {
		return allegatiInseriti;
	}

	public void setAllegatiInseriti(Boolean allegatiInseriti) {
		this.allegatiInseriti = allegatiInseriti;
	}

	public java.lang.Long getIdIntegrazioneSpesa() {
		return idIntegrazioneSpesa;
	}
	public void setIdIntegrazioneSpesa(java.lang.Long idIntegrazioneSpesa) {
		this.idIntegrazioneSpesa = idIntegrazioneSpesa;
	}
	public java.lang.Long getnDichiarazioneSpesa() {
		return nDichiarazioneSpesa;
	}
	public void setnDichiarazioneSpesa(java.lang.Long nDichiarazioneSpesa) {
		this.nDichiarazioneSpesa = nDichiarazioneSpesa;
	}
	public java.lang.Long getIdStatoRichiesta() {
		return idStatoRichiesta;
	}
	public void setIdStatoRichiesta(java.lang.Long idStatoRichiesta) {
		this.idStatoRichiesta = idStatoRichiesta;
	}
	public java.lang.String getLongStatoRichiesta() {
		return longStatoRichiesta;
	}
	public void setLongStatoRichiesta(java.lang.String longStatoRichiesta) {
		this.longStatoRichiesta = longStatoRichiesta;
	}
	public java.lang.String getDataRichiesta() {
		return dataRichiesta;
	}
	public void setDataRichiesta(java.lang.String dataRichiesta) {
		this.dataRichiesta = dataRichiesta;
	}
	public java.lang.String getDataNotifica() {
		return dataNotifica;
	}
	public void setDataNotifica(java.lang.String dataNotifica) {
		this.dataNotifica = dataNotifica;
	}
	
	public java.lang.String getnGgScadenza() {
		return nGgScadenza;
	}
	public void setnGgScadenza(java.lang.String nGgScadenza) {
		this.nGgScadenza = nGgScadenza;
	}
	public java.lang.String getDataInvio() {
		return dataInvio;
	}
	public void setDataInvio(java.lang.String dataInvio) {
		this.dataInvio = dataInvio;
	}
	public java.lang.String getStatoRichiesta() {
		return statoRichiesta;
	}
	public void setStatoRichiesta(java.lang.String statoRichiesta) {
		this.statoRichiesta = statoRichiesta;
	}

}
