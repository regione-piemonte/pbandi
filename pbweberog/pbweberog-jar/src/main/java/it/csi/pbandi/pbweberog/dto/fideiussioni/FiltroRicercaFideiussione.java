/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweberog.dto.fideiussioni;

public class FiltroRicercaFideiussione implements java.io.Serializable {

	/// Field [codiceRiferimento]
	private String codiceRiferimento;

	public void setCodiceRiferimento(String val) {
		codiceRiferimento = val;
	}

	public String getCodiceRiferimento() {
		return codiceRiferimento;
	}

	/// Field [dtDecorrenza]
	private String dtDecorrenza;

	public void setDtDecorrenza(String val) {
		dtDecorrenza = val;
	}

	public String getDtDecorrenza() {
		return dtDecorrenza;
	}

	/// Field [dtScadenza]
	private String dtScadenza;

	public void setDtScadenza(String val) {
		dtScadenza = val;
	}

	public String getDtScadenza() {
		return dtScadenza;
	}

	// il serial version uid e' fisso in quanto la classe in oggetto e' serializzabile
	// solo per la clusterizzazione della sessione web e non viene scambiata con altre
	// componenti.
	private static final long serialVersionUID = 1L;

	/**
	 * Costruttore vuoto del DTO.
	 */
	public FiltroRicercaFideiussione() {
		super();

	}

	public String toString() {
		/*PROTECTED REGION ID(R-151021816) ENABLED START*/
		/// inserire qui la logica desiderata per la rappresenatazione a stringa
		return super.toString();
		/*PROTECTED REGION END*/
	}

}
