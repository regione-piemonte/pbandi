/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.integration.vo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import it.csi.pbandi.pbgestfinbo.dto.GestioneAllegatiVO;

public class initGestioneEscussioneRiassicurazioniVO {
	
	/* Oggetto usato per popolare e visualizzare la pagina principale di modifica escussione di Riassicurazione. */
	
	private Long idSoggetto;
	private Long idProgetto;
	private Long progrSoggProg;
	
	// Testata
	private String testata_bando;
	private String testata_codProgetto;
	
	// Dati anagrafici
	private String datiAnagrafici_beneficiario;
	private String datiAnagrafici_codFiscale;
	private String datiAnagrafici_partitaIva;
	private String datiAnagrafici_formaGiuridica;
	private String datiAnagrafici_tipoAnagrafica;
	private String datiAnagrafici_denomBanca;
	
	// Escussioni passate
	private List<initGestioneEscussioneRiassicurazioniVO> escussioniPassate;
	
	// Escussione corrente
	private Long escussione_idEscussioneCorrente;
	private Long escussione_idStatoEscussione;
	private Long escussione_idTipoEscussione;
	private String escussione_tipoEscussione;
	private String escussione_statoEscussione;
	private Date escussione_dataRicevimentoRichEscuss;
	private Date escussione_dataStatoEscussione;
	private Date escussione_dataNotificaEscussione;
	private String escussione_numeroProtocolloRichiesta;
	private String escussione_numeroProtocolloNotifica;
	private BigDecimal escussione_importoRichiesto;
	private BigDecimal escussione_importoApprovato;
	private String escussione_causaleBonifico;
	private String escussione_ibanBanca;
	private String escussione_denomBanca;
	private String escussione_note;
	
	// Escussione nuova
	private List<String> statiNuovaEscussione;
	
	// Controllo stato iter Escussione
	private Boolean esitoInviato;
	private Boolean integrazioneInviata;
	private List<String> tipiEscussione;
	private List<String> statiEscussione;
	private Integer statoPulsanteEscussione; // 1 = Nuova // 2 = Modifica // 3 = Disabilitato
	private Boolean canSendEsito;
	private Boolean canSendIntegrazione;
	private BigDecimal sommaImportiApprovatiInseriti;
	
	private List<GestioneAllegatiVO> allegatiInseriti;
	

	
	

	public initGestioneEscussioneRiassicurazioniVO(Long idSoggetto, Long idProgetto, Long progrSoggProg,
			String testata_bando, String testata_codProgetto, String datiAnagrafici_beneficiario,
			String datiAnagrafici_codFiscale, String datiAnagrafici_partitaIva, String datiAnagrafici_formaGiuridica,
			String datiAnagrafici_tipoAnagrafica, String datiAnagrafici_denomBanca,
			List<initGestioneEscussioneRiassicurazioniVO> escussioniPassate, Long escussione_idEscussioneCorrente,
			Long escussione_idStatoEscussione, Long escussione_idTipoEscussione, String escussione_tipoEscussione,
			String escussione_statoEscussione, Date escussione_dataRicevimentoRichEscuss,
			Date escussione_dataStatoEscussione, Date escussione_dataNotificaEscussione,
			String escussione_numeroProtocolloRichiesta, String escussione_numeroProtocolloNotifica,
			BigDecimal escussione_importoRichiesto, BigDecimal escussione_importoApprovato,
			String escussione_causaleBonifico, String escussione_ibanBanca, String escussione_denomBanca,
			String escussione_note, List<String> statiNuovaEscussione, Boolean esitoInviato,
			Boolean integrazioneInviata, List<String> tipiEscussione, List<String> statiEscussione,
			Integer statoPulsanteEscussione, Boolean canSendEsito, Boolean canSendIntegrazione,
			BigDecimal sommaImportiApprovatiInseriti, List<GestioneAllegatiVO> allegatiInseriti) {
		this.idSoggetto = idSoggetto;
		this.idProgetto = idProgetto;
		this.progrSoggProg = progrSoggProg;
		this.testata_bando = testata_bando;
		this.testata_codProgetto = testata_codProgetto;
		this.datiAnagrafici_beneficiario = datiAnagrafici_beneficiario;
		this.datiAnagrafici_codFiscale = datiAnagrafici_codFiscale;
		this.datiAnagrafici_partitaIva = datiAnagrafici_partitaIva;
		this.datiAnagrafici_formaGiuridica = datiAnagrafici_formaGiuridica;
		this.datiAnagrafici_tipoAnagrafica = datiAnagrafici_tipoAnagrafica;
		this.datiAnagrafici_denomBanca = datiAnagrafici_denomBanca;
		this.escussioniPassate = escussioniPassate;
		this.escussione_idEscussioneCorrente = escussione_idEscussioneCorrente;
		this.escussione_idStatoEscussione = escussione_idStatoEscussione;
		this.escussione_idTipoEscussione = escussione_idTipoEscussione;
		this.escussione_tipoEscussione = escussione_tipoEscussione;
		this.escussione_statoEscussione = escussione_statoEscussione;
		this.escussione_dataRicevimentoRichEscuss = escussione_dataRicevimentoRichEscuss;
		this.escussione_dataStatoEscussione = escussione_dataStatoEscussione;
		this.escussione_dataNotificaEscussione = escussione_dataNotificaEscussione;
		this.escussione_numeroProtocolloRichiesta = escussione_numeroProtocolloRichiesta;
		this.escussione_numeroProtocolloNotifica = escussione_numeroProtocolloNotifica;
		this.escussione_importoRichiesto = escussione_importoRichiesto;
		this.escussione_importoApprovato = escussione_importoApprovato;
		this.escussione_causaleBonifico = escussione_causaleBonifico;
		this.escussione_ibanBanca = escussione_ibanBanca;
		this.escussione_denomBanca = escussione_denomBanca;
		this.escussione_note = escussione_note;
		this.statiNuovaEscussione = statiNuovaEscussione;
		this.esitoInviato = esitoInviato;
		this.integrazioneInviata = integrazioneInviata;
		this.tipiEscussione = tipiEscussione;
		this.statiEscussione = statiEscussione;
		this.statoPulsanteEscussione = statoPulsanteEscussione;
		this.canSendEsito = canSendEsito;
		this.canSendIntegrazione = canSendIntegrazione;
		this.sommaImportiApprovatiInseriti = sommaImportiApprovatiInseriti;
		this.allegatiInseriti = allegatiInseriti;
	}

	public void setSezioniTestataAndDatiAnagrafici(initGestioneEscussioneRiassicurazioniVO obj1) {
		if(obj1 != null) {
			this.idSoggetto = obj1.getIdSoggetto();
			this.idProgetto = obj1.getIdProgetto();
			this.progrSoggProg = obj1.getProgrSoggProg();

			this.testata_bando = obj1.getTestata_bando();
			this.testata_codProgetto = obj1.getTestata_codProgetto();

			this.datiAnagrafici_beneficiario = obj1.getDatiAnagrafici_beneficiario();
			this.datiAnagrafici_codFiscale = obj1.getDatiAnagrafici_codFiscale();
			this.datiAnagrafici_partitaIva = obj1.getDatiAnagrafici_partitaIva();
			this.datiAnagrafici_formaGiuridica = obj1.getDatiAnagrafici_formaGiuridica();
			this.datiAnagrafici_tipoAnagrafica = obj1.getDatiAnagrafici_tipoAnagrafica();
			this.datiAnagrafici_denomBanca = obj1.getDatiAnagrafici_denomBanca();
		}
	}
	
	public void setSezioneEscussione(initGestioneEscussioneRiassicurazioniVO obj1) {
		if(obj1 != null) {
			this.escussione_idEscussioneCorrente = obj1.getEscussione_idEscussioneCorrente();
			this.escussione_idStatoEscussione = obj1.getEscussione_idStatoEscussione();
			this.escussione_idTipoEscussione = obj1.getEscussione_idTipoEscussione();
			this.escussione_tipoEscussione = obj1.getEscussione_tipoEscussione();
			this.escussione_statoEscussione = obj1.getEscussione_statoEscussione();
			this.escussione_dataRicevimentoRichEscuss = obj1.getEscussione_dataRicevimentoRichEscuss();
			this.escussione_dataStatoEscussione = obj1.getEscussione_dataStatoEscussione();
			this.escussione_dataNotificaEscussione = obj1.getEscussione_dataNotificaEscussione();
			this.escussione_numeroProtocolloRichiesta = obj1.getEscussione_numeroProtocolloRichiesta();
			this.escussione_numeroProtocolloNotifica = obj1.getEscussione_numeroProtocolloNotifica();
			this.escussione_importoRichiesto = obj1.getEscussione_importoRichiesto();
			this.escussione_importoApprovato = obj1.getEscussione_importoApprovato();
			this.escussione_causaleBonifico = obj1.getEscussione_causaleBonifico();
			this.escussione_ibanBanca = obj1.getEscussione_ibanBanca();
			this.escussione_denomBanca = obj1.getEscussione_denomBanca();
			this.escussione_note = obj1.getEscussione_note();
			
			this.allegatiInseriti = obj1.getAllegatiInseriti();
		}
	}
	
	public void setLogicaEscussione(initGestioneEscussioneRiassicurazioniVO obj1) {
		if(obj1 != null) { 
			this.tipiEscussione = obj1.getTipiEscussione();
			this.statiEscussione = obj1.getStatiEscussione();
			this.statiNuovaEscussione = obj1.getStatiNuovaEscussione();
			
			this.esitoInviato = obj1.getEsitoInviato();
			this.integrazioneInviata = obj1.getIntegrazioneInviata();
			this.statoPulsanteEscussione = obj1.getStatoPulsanteEscussione(); // 1 = Nuova // 2 = Modifica // 3 = Disabilitato
			this.canSendEsito = obj1.getCanSendEsito();
			this.canSendIntegrazione = obj1.getCanSendIntegrazione();
			this.sommaImportiApprovatiInseriti = obj1.getSommaImportiApprovatiInseriti();
		}
	}

	
	
	
	public List<GestioneAllegatiVO> getAllegatiInseriti() {
		return allegatiInseriti;
	}

	public void setAllegatiInseriti(List<GestioneAllegatiVO> allegatiInseriti) {
		this.allegatiInseriti = allegatiInseriti;
	}

	public List<String> getStatiNuovaEscussione() {
		return statiNuovaEscussione;
	}

	public void setStatiNuovaEscussione(List<String> statiNuovaEscussione) {
		this.statiNuovaEscussione = statiNuovaEscussione;
	}

	public List<initGestioneEscussioneRiassicurazioniVO> getEscussioniPassate() {
		return escussioniPassate;
	}

	public void setEscussioniPassate(List<initGestioneEscussioneRiassicurazioniVO> escussioniPassate) {
		this.escussioniPassate = escussioniPassate;
	}

	public BigDecimal getSommaImportiApprovatiInseriti() {
		return sommaImportiApprovatiInseriti;
	}


	public void setSommaImportiApprovatiInseriti(BigDecimal sommaImportiApprovatiInseriti) {
		this.sommaImportiApprovatiInseriti = sommaImportiApprovatiInseriti;
	}


	public Boolean getCanSendEsito() {
		return canSendEsito;
	}

	public void setCanSendEsito(Boolean canSendEsito) {
		this.canSendEsito = canSendEsito;
	}

	public Boolean getCanSendIntegrazione() {
		return canSendIntegrazione;
	}

	public void setCanSendIntegrazione(Boolean canSendIntegrazione) {
		this.canSendIntegrazione = canSendIntegrazione;
	}

	public Integer getStatoPulsanteEscussione() {
		return statoPulsanteEscussione;
	}



	public void setStatoPulsanteEscussione(Integer statoPulsanteEscussione) {
		this.statoPulsanteEscussione = statoPulsanteEscussione;
	}



	public initGestioneEscussioneRiassicurazioniVO() {
	}



	public Long getIdSoggetto() {
		return idSoggetto;
	}



	public void setIdSoggetto(Long idSoggetto) {
		this.idSoggetto = idSoggetto;
	}



	public Long getIdProgetto() {
		return idProgetto;
	}



	public void setIdProgetto(Long idProgetto) {
		this.idProgetto = idProgetto;
	}



	public Long getProgrSoggProg() {
		return progrSoggProg;
	}



	public void setProgrSoggProg(Long progrSoggProg) {
		this.progrSoggProg = progrSoggProg;
	}



	public String getTestata_bando() {
		return testata_bando;
	}



	public void setTestata_bando(String testata_bando) {
		this.testata_bando = testata_bando;
	}



	public String getTestata_codProgetto() {
		return testata_codProgetto;
	}



	public void setTestata_codProgetto(String testata_codProgetto) {
		this.testata_codProgetto = testata_codProgetto;
	}



	public String getDatiAnagrafici_beneficiario() {
		return datiAnagrafici_beneficiario;
	}



	public void setDatiAnagrafici_beneficiario(String datiAnagrafici_beneficiario) {
		this.datiAnagrafici_beneficiario = datiAnagrafici_beneficiario;
	}



	public String getDatiAnagrafici_codFiscale() {
		return datiAnagrafici_codFiscale;
	}



	public void setDatiAnagrafici_codFiscale(String datiAnagrafici_codFiscale) {
		this.datiAnagrafici_codFiscale = datiAnagrafici_codFiscale;
	}



	public String getDatiAnagrafici_partitaIva() {
		return datiAnagrafici_partitaIva;
	}



	public void setDatiAnagrafici_partitaIva(String datiAnagrafici_partitaIva) {
		this.datiAnagrafici_partitaIva = datiAnagrafici_partitaIva;
	}



	public String getDatiAnagrafici_formaGiuridica() {
		return datiAnagrafici_formaGiuridica;
	}



	public void setDatiAnagrafici_formaGiuridica(String datiAnagrafici_formaGiuridica) {
		this.datiAnagrafici_formaGiuridica = datiAnagrafici_formaGiuridica;
	}



	public String getDatiAnagrafici_tipoAnagrafica() {
		return datiAnagrafici_tipoAnagrafica;
	}



	public void setDatiAnagrafici_tipoAnagrafica(String datiAnagrafici_tipoAnagrafica) {
		this.datiAnagrafici_tipoAnagrafica = datiAnagrafici_tipoAnagrafica;
	}



	public String getDatiAnagrafici_denomBanca() {
		return datiAnagrafici_denomBanca;
	}



	public void setDatiAnagrafici_denomBanca(String datiAnagrafici_denomBanca) {
		this.datiAnagrafici_denomBanca = datiAnagrafici_denomBanca;
	}



	public Long getEscussione_idEscussioneCorrente() {
		return escussione_idEscussioneCorrente;
	}



	public void setEscussione_idEscussioneCorrente(Long escussione_idEscussioneCorrente) {
		this.escussione_idEscussioneCorrente = escussione_idEscussioneCorrente;
	}



	public Long getEscussione_idStatoEscussione() {
		return escussione_idStatoEscussione;
	}



	public void setEscussione_idStatoEscussione(Long escussione_idStatoEscussione) {
		this.escussione_idStatoEscussione = escussione_idStatoEscussione;
	}



	public Long getEscussione_idTipoEscussione() {
		return escussione_idTipoEscussione;
	}



	public void setEscussione_idTipoEscussione(Long escussione_idTipoEscussione) {
		this.escussione_idTipoEscussione = escussione_idTipoEscussione;
	}



	public String getEscussione_tipoEscussione() {
		return escussione_tipoEscussione;
	}



	public void setEscussione_tipoEscussione(String escussione_tipoEscussione) {
		this.escussione_tipoEscussione = escussione_tipoEscussione;
	}



	public String getEscussione_statoEscussione() {
		return escussione_statoEscussione;
	}



	public void setEscussione_statoEscussione(String escussione_statoEscussione) {
		this.escussione_statoEscussione = escussione_statoEscussione;
	}



	public Date getEscussione_dataRicevimentoRichEscuss() {
		return escussione_dataRicevimentoRichEscuss;
	}



	public void setEscussione_dataRicevimentoRichEscuss(Date escussione_dataRicevimentoRichEscuss) {
		this.escussione_dataRicevimentoRichEscuss = escussione_dataRicevimentoRichEscuss;
	}



	public Date getEscussione_dataStatoEscussione() {
		return escussione_dataStatoEscussione;
	}



	public void setEscussione_dataStatoEscussione(Date escussione_dataStatoEscussione) {
		this.escussione_dataStatoEscussione = escussione_dataStatoEscussione;
	}



	public Date getEscussione_dataNotificaEscussione() {
		return escussione_dataNotificaEscussione;
	}



	public void setEscussione_dataNotificaEscussione(Date escussione_dataNotificaEscussione) {
		this.escussione_dataNotificaEscussione = escussione_dataNotificaEscussione;
	}



	public String getEscussione_numeroProtocolloRichiesta() {
		return escussione_numeroProtocolloRichiesta;
	}



	public void setEscussione_numeroProtocolloRichiesta(String escussione_numeroProtocolloRichiesta) {
		this.escussione_numeroProtocolloRichiesta = escussione_numeroProtocolloRichiesta;
	}



	public String getEscussione_numeroProtocolloNotifica() {
		return escussione_numeroProtocolloNotifica;
	}



	public void setEscussione_numeroProtocolloNotifica(String escussione_numeroProtocolloNotifica) {
		this.escussione_numeroProtocolloNotifica = escussione_numeroProtocolloNotifica;
	}



	public BigDecimal getEscussione_importoRichiesto() {
		return escussione_importoRichiesto;
	}



	public void setEscussione_importoRichiesto(BigDecimal escussione_importoRichiesto) {
		this.escussione_importoRichiesto = escussione_importoRichiesto;
	}



	public BigDecimal getEscussione_importoApprovato() {
		return escussione_importoApprovato;
	}



	public void setEscussione_importoApprovato(BigDecimal escussione_importoApprovato) {
		this.escussione_importoApprovato = escussione_importoApprovato;
	}



	public String getEscussione_causaleBonifico() {
		return escussione_causaleBonifico;
	}



	public void setEscussione_causaleBonifico(String escussione_causaleBonifico) {
		this.escussione_causaleBonifico = escussione_causaleBonifico;
	}



	public String getEscussione_ibanBanca() {
		return escussione_ibanBanca;
	}



	public void setEscussione_ibanBanca(String escussione_ibanBanca) {
		this.escussione_ibanBanca = escussione_ibanBanca;
	}



	public String getEscussione_denomBanca() {
		return escussione_denomBanca;
	}



	public void setEscussione_denomBanca(String escussione_denomBanca) {
		this.escussione_denomBanca = escussione_denomBanca;
	}



	public String getEscussione_note() {
		return escussione_note;
	}



	public void setEscussione_note(String escussione_note) {
		this.escussione_note = escussione_note;
	}



	public Boolean getEsitoInviato() {
		return esitoInviato;
	}



	public void setEsitoInviato(Boolean esitoInviato) {
		this.esitoInviato = esitoInviato;
	}



	public Boolean getIntegrazioneInviata() {
		return integrazioneInviata;
	}



	public void setIntegrazioneInviata(Boolean integrazioneInviata) {
		this.integrazioneInviata = integrazioneInviata;
	}



	public List<String> getTipiEscussione() {
		return tipiEscussione;
	}



	public void setTipiEscussione(List<String> tipiEscussione) {
		this.tipiEscussione = tipiEscussione;
	}



	public List<String> getStatiEscussione() {
		return statiEscussione;
	}



	public void setStatiEscussione(List<String> statiEscussione) {
		this.statiEscussione = statiEscussione;
	}



	@Override
	public String toString() {
		return "initGestioneEscussioneRiassicurazioniVO [idSoggetto=" + idSoggetto + ", idProgetto=" + idProgetto
				+ ", progrSoggProg=" + progrSoggProg + ", testata_bando=" + testata_bando + ", testata_codProgetto="
				+ testata_codProgetto + ", datiAnagrafici_beneficiario=" + datiAnagrafici_beneficiario
				+ ", datiAnagrafici_codFiscale=" + datiAnagrafici_codFiscale + ", datiAnagrafici_partitaIva="
				+ datiAnagrafici_partitaIva + ", datiAnagrafici_formaGiuridica=" + datiAnagrafici_formaGiuridica
				+ ", datiAnagrafici_tipoAnagrafica=" + datiAnagrafici_tipoAnagrafica + ", datiAnagrafici_denomBanca="
				+ datiAnagrafici_denomBanca + ", escussione_idEscussioneCorrente=" + escussione_idEscussioneCorrente
				+ ", escussione_idStatoEscussione=" + escussione_idStatoEscussione + ", escussione_idTipoEscussione="
				+ escussione_idTipoEscussione + ", escussione_tipoEscussione=" + escussione_tipoEscussione
				+ ", escussione_statoEscussione=" + escussione_statoEscussione
				+ ", escussione_dataRicevimentoRichEscuss=" + escussione_dataRicevimentoRichEscuss
				+ ", escussione_dataStatoEscussione=" + escussione_dataStatoEscussione
				+ ", escussione_dataNotificaEscussione=" + escussione_dataNotificaEscussione
				+ ", escussione_numeroProtocolloRichiesta=" + escussione_numeroProtocolloRichiesta
				+ ", escussione_numeroProtocolloNotifica=" + escussione_numeroProtocolloNotifica
				+ ", escussione_importoRichiesto=" + escussione_importoRichiesto + ", escussione_importoApprovato="
				+ escussione_importoApprovato + ", escussione_causaleBonifico=" + escussione_causaleBonifico
				+ ", escussione_ibanBanca=" + escussione_ibanBanca + ", escussione_denomBanca=" + escussione_denomBanca
				+ ", escussione_note=" + escussione_note + ", esitoInviato=" + esitoInviato + ", integrazioneInviata="
				+ integrazioneInviata + ", tipiEscussione=" + tipiEscussione + ", statiEscussione=" + statiEscussione
				+ ", statoPulsanteEscussione=" + statoPulsanteEscussione + "]";
	}


	
	
}
