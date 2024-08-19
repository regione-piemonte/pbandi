/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweberog.dto.datiprogetto;


public class DatiProgettoDTO implements java.io.Serializable {
	static final long serialVersionUID = 1;
	private DettaglioProgettoDTO dettaglio;
	private Long idProgetto;
	private Long idSoggettoBeneficiario;
	private Long idSedeIntervento;
	private Long progrSoggettoProgetto;
	private String cup;
	private String numeroDomanda;
	private Long idDomanda;
	private String titoloProgetto;
	private String codiceVisualizzato;
	private String codiceProgetto;
	private Long idLineaInterventoAsse;
	private String codiceProgettoCipe;
	public DettaglioProgettoDTO getDettaglio() {
		return dettaglio;
	}
	public void setDettaglio(DettaglioProgettoDTO dettaglio) {
		this.dettaglio = dettaglio;
	}
	public Long getIdProgetto() {
		return idProgetto;
	}
	public void setIdProgetto(Long idProgetto) {
		this.idProgetto = idProgetto;
	}
	public Long getIdSoggettoBeneficiario() {
		return idSoggettoBeneficiario;
	}
	public void setIdSoggettoBeneficiario(Long idSoggettoBeneficiario) {
		this.idSoggettoBeneficiario = idSoggettoBeneficiario;
	}
	public Long getIdSedeIntervento() {
		return idSedeIntervento;
	}
	public void setIdSedeIntervento(Long idSedeIntervento) {
		this.idSedeIntervento = idSedeIntervento;
	}
	public Long getProgrSoggettoProgetto() {
		return progrSoggettoProgetto;
	}
	public void setProgrSoggettoProgetto(Long progrSoggettoProgetto) {
		this.progrSoggettoProgetto = progrSoggettoProgetto;
	}
	public String getCup() {
		return cup;
	}
	public void setCup(String cup) {
		this.cup = cup;
	}
	public String getNumeroDomanda() {
		return numeroDomanda;
	}
	public void setNumeroDomanda(String numeroDomanda) {
		this.numeroDomanda = numeroDomanda;
	}
	public Long getIdDomanda() {
		return idDomanda;
	}
	public void setIdDomanda(Long idDomanda) {
		this.idDomanda = idDomanda;
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
	public String getCodiceProgetto() {
		return codiceProgetto;
	}
	public void setCodiceProgetto(String codiceProgetto) {
		this.codiceProgetto = codiceProgetto;
	}
	public Long getIdLineaInterventoAsse() {
		return idLineaInterventoAsse;
	}
	public void setIdLineaInterventoAsse(Long idLineaInterventoAsse) {
		this.idLineaInterventoAsse = idLineaInterventoAsse;
	}
	public String getCodiceProgettoCipe() {
		return codiceProgettoCipe;
	}
	public void setCodiceProgettoCipe(String codiceProgettoCipe) {
		this.codiceProgettoCipe = codiceProgettoCipe;
	}
	
	
	
}
