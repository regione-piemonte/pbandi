/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.pbandisrv.integration.services.bilancio.vo;

import java.math.BigDecimal;
import java.util.Date;

public class RitenutaAttoNewVO {

	// Classe che mappa i dati da passare a Contabilia per la nuova gestione della ritenuta (dicembre 2020).
	
	private BigDecimal importoImponibile;
	private BigDecimal impNonSoggettoRitenuta;
	private BigDecimal importoCaricoEnte;
	private BigDecimal importoCaricoSoggetto;
	private String codOnere;
	private String codNaturaOnere;
	
	public String getCodOnere() {
		return codOnere;
	}
	public void setCodOnere(String codOnere) {
		this.codOnere = codOnere;
	}
	public String getCodNaturaOnere() {
		return codNaturaOnere;
	}
	public void setCodNaturaOnere(String codNaturaOnere) {
		this.codNaturaOnere = codNaturaOnere;
	}
	public BigDecimal getImportoImponibile() {
		return importoImponibile;
	}
	public void setImportoImponibile(BigDecimal importoImponibile) {
		this.importoImponibile = importoImponibile;
	}
	public BigDecimal getImpNonSoggettoRitenuta() {
		return impNonSoggettoRitenuta;
	}
	public void setImpNonSoggettoRitenuta(BigDecimal impNonSoggettoRitenuta) {
		this.impNonSoggettoRitenuta = impNonSoggettoRitenuta;
	}
	public BigDecimal getImportoCaricoEnte() {
		return importoCaricoEnte;
	}
	public void setImportoCaricoEnte(BigDecimal importoCaricoEnte) {
		this.importoCaricoEnte = importoCaricoEnte;
	}
	public BigDecimal getImportoCaricoSoggetto() {
		return importoCaricoSoggetto;
	}
	public void setImportoCaricoSoggetto(BigDecimal importoCaricoSoggetto) {
		this.importoCaricoSoggetto = importoCaricoSoggetto;
	}

}
