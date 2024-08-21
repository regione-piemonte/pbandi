/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservrest.model;

public class EsitoDurcAntimafiaInRest {
	
	private String codFiscaleSoggetto = null;
	private String cognomeIstruttore = null;
	private String nomeIstruttore = null;
	private String codFiscaleIstruttore = null;
	private String numeroDomanda = null;
	private String tipoRichiesta = null;
	private String modalitaRichiesta = null;
	
	public EsitoDurcAntimafiaInRest() {
		// TODO Auto-generated constructor stub
	}

	public EsitoDurcAntimafiaInRest(String codFiscaleSoggetto, String cognomeIstruttore, String nomeIstruttore,
			String codFiscaleIstruttore, String numeroDomanda, String tipoRichiesta, String modalitaRichiesta) {
		super();
		this.codFiscaleSoggetto = codFiscaleSoggetto;
		this.cognomeIstruttore = cognomeIstruttore;
		this.nomeIstruttore = nomeIstruttore;
		this.codFiscaleIstruttore = codFiscaleIstruttore;
		this.numeroDomanda = numeroDomanda;
		this.tipoRichiesta = tipoRichiesta;
		this.modalitaRichiesta = modalitaRichiesta;
	}

	public String getCodFiscaleSoggetto() {
		return codFiscaleSoggetto;
	}

	public void setCodFiscaleSoggetto(String codFiscaleSoggetto) {
		this.codFiscaleSoggetto = codFiscaleSoggetto;
	}

	public String getCognomeIstruttore() {
		return cognomeIstruttore;
	}

	public void setCognomeIstruttore(String cognomeIstruttore) {
		this.cognomeIstruttore = cognomeIstruttore;
	}

	public String getNomeIstruttore() {
		return nomeIstruttore;
	}

	public void setNomeIstruttore(String nomeIstruttore) {
		this.nomeIstruttore = nomeIstruttore;
	}

	public String getCodFiscaleIstruttore() {
		return codFiscaleIstruttore;
	}

	public void setCodFiscaleIstruttore(String codFiscaleIstruttore) {
		this.codFiscaleIstruttore = codFiscaleIstruttore;
	}

	public String getNumeroDomanda() {
		return numeroDomanda;
	}

	public void setNumeroDomanda(String numeroDomanda) {
		this.numeroDomanda = numeroDomanda;
	}

	public String getTipoRichiesta() {
		return tipoRichiesta;
	}

	public void setTipoRichiesta(String tipoRichiesta) {
		this.tipoRichiesta = tipoRichiesta;
	}

	public String getModalitaRichiesta() {
		return modalitaRichiesta;
	}

	public void setModalitaRichiesta(String modalitaRichiesta) {
		this.modalitaRichiesta = modalitaRichiesta;
	}
	
}
