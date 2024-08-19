/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweberog.dto.datiprogetto;

public class DatiProgetto implements java.io.Serializable {

	private DettaglioDatiProgetto dettaglio;
	private String cup;
	private String titoloProgetto;
	private String codiceVisualizzato;
	private String numeroDomanda;
	private Long idProgetto;
	private Long idLineaInterventoAsse;
	private Long progrSoggettoProgetto;
	private Long idSedeIntervento;
	private String codiceProgettoCipe;
	private Long idDomanda;
	private static final long serialVersionUID = 1L;	
	
	public DettaglioDatiProgetto getDettaglio() {
		return dettaglio;
	}

	public void setDettaglio(DettaglioDatiProgetto dettaglio) {
		this.dettaglio = dettaglio;
	}

	public String getCup() {
		return cup;
	}

	public void setCup(String cup) {
		this.cup = cup;
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

	public String getNumeroDomanda() {
		return numeroDomanda;
	}

	public void setNumeroDomanda(String numeroDomanda) {
		this.numeroDomanda = numeroDomanda;
	}

	public Long getIdProgetto() {
		return idProgetto;
	}

	public void setIdProgetto(Long idProgetto) {
		this.idProgetto = idProgetto;
	}

	public Long getIdLineaInterventoAsse() {
		return idLineaInterventoAsse;
	}

	public void setIdLineaInterventoAsse(Long idLineaInterventoAsse) {
		this.idLineaInterventoAsse = idLineaInterventoAsse;
	}

	public Long getProgrSoggettoProgetto() {
		return progrSoggettoProgetto;
	}

	public void setProgrSoggettoProgetto(Long progrSoggettoProgetto) {
		this.progrSoggettoProgetto = progrSoggettoProgetto;
	}

	public Long getIdSedeIntervento() {
		return idSedeIntervento;
	}

	public void setIdSedeIntervento(Long idSedeIntervento) {
		this.idSedeIntervento = idSedeIntervento;
	}

	public String getCodiceProgettoCipe() {
		return codiceProgettoCipe;
	}

	public void setCodiceProgettoCipe(String codiceProgettoCipe) {
		this.codiceProgettoCipe = codiceProgettoCipe;
	}

	public Long getIdDomanda() {
		return idDomanda;
	}

	public void setIdDomanda(Long idDomanda) {
		this.idDomanda = idDomanda;
	}

	public DatiProgetto() {
		super();

	}

	public String toString() {
		return super.toString();
	}
}
