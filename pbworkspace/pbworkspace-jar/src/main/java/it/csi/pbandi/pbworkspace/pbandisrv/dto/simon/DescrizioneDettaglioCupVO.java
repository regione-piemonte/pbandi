/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbworkspace.pbandisrv.dto.simon;


public class DescrizioneDettaglioCupVO {
	
	private String titoloProgetto;
	private String descStrumento;
	private String denominazioneProgetto;
	
	private String denominazione; // denominazione_impresa_stabilimento, ente, denominazione_ente_corso, beneficiario 
	private String partitaIva;
	
	private Boolean lavoriPubblici = false;
	private Boolean realizzAcquistoServiziFormazione = false;
	private Boolean concessioneIncentiviUnitaProduttive = false;
	private Boolean realizzAcquistoServiziNoFormazioneRicerca;
	private Boolean realizzAquistoServiziRicerca = false;
	private Boolean concessioneContributiNoUnitaProduttive = false;
	private Boolean acquistoBeni = false;
	
	public Boolean getAcquistoBeni() {
		return acquistoBeni;
	}
	public void setAcquistoBeni(Boolean acquistoBeni) {
		this.acquistoBeni = acquistoBeni;
	}
	public String getTipoDescrizione() {
		return tipoDescrizione;
	}
	public void setTipoDescrizione(String tipoDescrizione) {
		this.tipoDescrizione = tipoDescrizione;
	}
	private String tipoDescrizione;
	
	public String getTitoloProgetto() {
		return titoloProgetto;
	}
	public void setTitoloProgetto(String titoloProgetto) {
		this.titoloProgetto = titoloProgetto;
	}
	public Boolean getLavoriPubblici() {
		return lavoriPubblici;
	}
	public void setLavoriPubblici(Boolean lavoriPubblici) {
		this.lavoriPubblici = lavoriPubblici;
	}
	public Boolean getRealizzAcquistoServiziFormazione() {
		return realizzAcquistoServiziFormazione;
	}
	public void setRealizzAcquistoServiziFormazione(
			Boolean realizzAcquistoServiziFormazione) {
		this.realizzAcquistoServiziFormazione = realizzAcquistoServiziFormazione;
	}
	public Boolean getConcessioneIncentiviUnitaProduttive() {
		return concessioneIncentiviUnitaProduttive;
	}
	public void setConcessioneIncentiviUnitaProduttive(
			Boolean concessioneIncentiviUnitaProduttive) {
		this.concessioneIncentiviUnitaProduttive = concessioneIncentiviUnitaProduttive;
	}
	public Boolean getRealizzAcquistoServiziNoFormazioneRicerca() {
		return realizzAcquistoServiziNoFormazioneRicerca;
	}
	public void setRealizzAcquistoServiziNoFormazioneRicerca(
			Boolean realizzAcquistoServiziNoFormazioneRicerca) {
		this.realizzAcquistoServiziNoFormazioneRicerca = realizzAcquistoServiziNoFormazioneRicerca;
	}
	public Boolean getRealizzAquistoServiziRicerca() {
		return realizzAquistoServiziRicerca;
	}
	public void setRealizzAquistoServiziRicerca(Boolean realizzAquistoServiziRicerca) {
		this.realizzAquistoServiziRicerca = realizzAquistoServiziRicerca;
	}
	public Boolean getConcessioneContributiNoUnitaProduttive() {
		return concessioneContributiNoUnitaProduttive;
	}
	public void setConcessioneContributiNoUnitaProduttive(
			Boolean concessioneContributiNoUnitaProduttive) {
		this.concessioneContributiNoUnitaProduttive = concessioneContributiNoUnitaProduttive;
	}
	public String getDescStrumento() {
		return descStrumento;
	}
	public void setDescStrumento(String descStrumento) {
		this.descStrumento = descStrumento;
	}

	public String getDenominazioneProgetto() {
		return denominazioneProgetto;
	}
	public void setDenominazioneProgetto(String denominazioneProgetto) {
		this.denominazioneProgetto = denominazioneProgetto;
	}
	public String getDenominazione() {
		return denominazione;
	}
	public void setDenominazione(String denominazione) {
		this.denominazione = denominazione;
	}

	public String getPartitaIva() {
		return partitaIva;
	}
	public void setPartitaIva(String partitaIva) {
		this.partitaIva = partitaIva;
	}


	
}
