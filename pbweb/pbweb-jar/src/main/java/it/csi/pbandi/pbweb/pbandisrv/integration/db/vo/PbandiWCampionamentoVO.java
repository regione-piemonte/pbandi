/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweb.pbandisrv.integration.db.vo;

import java.math.BigDecimal;
import java.util.List;

public class PbandiWCampionamentoVO extends GenericVO{
	
	/*
	    ID_CAMPIONE
		PROGR_OPERAZIONE
		ID_DETT_PROPOSTA_CERTIF
		ASSE
		ID_PROGETTO
		TITOLO_PROGETTO
		CODICE_VISUALIZZATO
		AVANZAMENTO
		UNIVERSO
		TIPO_CAMPIONE
		FLAG_ESCLUDI
		FLAG_PRIMO_ESTRATTO
	 * */
	
	private BigDecimal idCampione;
	private BigDecimal progrOperazione;
	private BigDecimal idDettPropostaCertif;
	private String asse;
	private BigDecimal idProgetto;
	private String titoloProgetto;
	private String codiceVisualizzato;
	private BigDecimal avanzamento;
	private String universo;
	private String tipoCampione;
	private String flagEscludi;
	private String	flagPrimoEstratto;

	@Override
	public List getPK() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isPKValid() {
		// TODO Auto-generated method stub
		return false;
	}

	public BigDecimal getIdCampione() {
		return idCampione;
	}

	public void setIdCampione(BigDecimal idCampione) {
		this.idCampione = idCampione;
	}

	public BigDecimal getProgrOperazione() {
		return progrOperazione;
	}

	public void setProgrOperazione(BigDecimal progrOperazione) {
		this.progrOperazione = progrOperazione;
	}

	public BigDecimal getIdDettPropostaCertif() {
		return idDettPropostaCertif;
	}

	public void setIdDettPropostaCertif(BigDecimal idDettPropostaCertif) {
		this.idDettPropostaCertif = idDettPropostaCertif;
	}

	public String getAsse() {
		return asse;
	}

	public void setAsse(String asse) {
		this.asse = asse;
	}

	public BigDecimal getIdProgetto() {
		return idProgetto;
	}

	public void setIdProgetto(BigDecimal idProgetto) {
		this.idProgetto = idProgetto;
	}

	public String getTitoloProgetto() {
		return titoloProgetto;
	}

	public void setTitoloProgetto(String titoloProgetto) {
		this.titoloProgetto = titoloProgetto;
	}

	public String getCodiceVisualizzato() {
		return codiceVisualizzato;
	}

	public void setCodiceVisualizzato(String codiceVisualizzato) {
		this.codiceVisualizzato = codiceVisualizzato;
	}

	public BigDecimal getAvanzamento() {
		return avanzamento;
	}

	public void setAvanzamento(BigDecimal avanzamento) {
		this.avanzamento = avanzamento;
	}

	public String getUniverso() {
		return universo;
	}

	public void setUniverso(String universo) {
		this.universo = universo;
	}

	public String getTipoCampione() {
		return tipoCampione;
	}

	public void setTipoCampione(String tipoCampione) {
		this.tipoCampione = tipoCampione;
	}

	public String getFlagEscludi() {
		return flagEscludi;
	}

	public void setFlagEscludi(String flagEscludi) {
		this.flagEscludi = flagEscludi;
	}

	public String getFlagPrimoEstratto() {
		return flagPrimoEstratto;
	}

	public void setFlagPrimoEstratto(String flagPrimoEstratto) {
		this.flagPrimoEstratto = flagPrimoEstratto;
	}

	@Override
	public String toString() {
		return "PbandiWCampionamentoVO [asse=" + asse + ", avanzamento="
				+ avanzamento + ", codiceVisualizzato=" + codiceVisualizzato
				+ ", flagEscludi=" + flagEscludi + ", flagPrimoEstratto="
				+ flagPrimoEstratto + ", idCampione=" + idCampione
				+ ", idDettPropostaCertif=" + idDettPropostaCertif
				+ ", idProgetto=" + idProgetto + ", progrOperazione="
				+ progrOperazione + ", tipoCampione=" + tipoCampione
				+ ", titoloProgetto=" + titoloProgetto + ", universo="
				+ universo + "]";
	}
	
}
