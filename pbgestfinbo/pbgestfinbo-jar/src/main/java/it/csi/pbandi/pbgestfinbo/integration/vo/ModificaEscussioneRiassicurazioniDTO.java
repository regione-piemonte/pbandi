/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.integration.vo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import it.csi.pbandi.pbgestfinbo.dto.GestioneAllegatiVO;

public class ModificaEscussioneRiassicurazioniDTO {
		
	//private Long idSoggetto;
	private Long idProgetto;
	//private Long progrSoggProg;
	
	// Escussione
	private Long idEscussioneCorrente;
	private Long idStatoEscussione;
	private Long idTipoEscussione;
	private String tipoEscussione;
	private String statoEscussione;
	private Date dataRicevimentoRichEscuss;
	private Date dataStatoEscussione;
	private Date dataNotificaEscussione;
	private String numeroProtocolloRichiesta;
	private String numeroProtocolloNotifica;
	private BigDecimal importoRichiesto;
	private BigDecimal importoApprovato;
	private String causaleBonifico;
	private String ibanBanca;
	private String denomBanca;
	private String note;
	
	private List<GestioneAllegatiVO> allegatiInseriti;
	
	

	public ModificaEscussioneRiassicurazioniDTO(Long idProgetto, Long idEscussioneCorrente, Long idStatoEscussione,
			Long idTipoEscussione, String tipoEscussione, String statoEscussione, Date dataRicevimentoRichEscuss,
			Date dataStatoEscussione, Date dataNotificaEscussione, String numeroProtocolloRichiesta,
			String numeroProtocolloNotifica, BigDecimal importoRichiesto, BigDecimal importoApprovato,
			String causaleBonifico, String ibanBanca, String denomBanca, String note,
			List<GestioneAllegatiVO> allegatiInseriti) {
		this.idProgetto = idProgetto;
		this.idEscussioneCorrente = idEscussioneCorrente;
		this.idStatoEscussione = idStatoEscussione;
		this.idTipoEscussione = idTipoEscussione;
		this.tipoEscussione = tipoEscussione;
		this.statoEscussione = statoEscussione;
		this.dataRicevimentoRichEscuss = dataRicevimentoRichEscuss;
		this.dataStatoEscussione = dataStatoEscussione;
		this.dataNotificaEscussione = dataNotificaEscussione;
		this.numeroProtocolloRichiesta = numeroProtocolloRichiesta;
		this.numeroProtocolloNotifica = numeroProtocolloNotifica;
		this.importoRichiesto = importoRichiesto;
		this.importoApprovato = importoApprovato;
		this.causaleBonifico = causaleBonifico;
		this.ibanBanca = ibanBanca;
		this.denomBanca = denomBanca;
		this.note = note;
		this.allegatiInseriti = allegatiInseriti;
	}


	public List<GestioneAllegatiVO> getAllegatiInseriti() {
		return allegatiInseriti;
	}


	public void setAllegatiInseriti(List<GestioneAllegatiVO> allegatiInseriti) {
		this.allegatiInseriti = allegatiInseriti;
	}


	public ModificaEscussioneRiassicurazioniDTO() {
	}


	public Long getIdProgetto() {
		return idProgetto;
	}


	public void setIdProgetto(Long idProgetto) {
		this.idProgetto = idProgetto;
	}


	public Long getIdEscussioneCorrente() {
		return idEscussioneCorrente;
	}


	public void setIdEscussioneCorrente(Long idEscussioneCorrente) {
		this.idEscussioneCorrente = idEscussioneCorrente;
	}


	public Long getIdStatoEscussione() {
		return idStatoEscussione;
	}


	public void setIdStatoEscussione(Long idStatoEscussione) {
		this.idStatoEscussione = idStatoEscussione;
	}


	public Long getIdTipoEscussione() {
		return idTipoEscussione;
	}


	public void setIdTipoEscussione(Long idTipoEscussione) {
		this.idTipoEscussione = idTipoEscussione;
	}


	public String getTipoEscussione() {
		return tipoEscussione;
	}


	public void setTipoEscussione(String tipoEscussione) {
		this.tipoEscussione = tipoEscussione;
	}


	public String getStatoEscussione() {
		return statoEscussione;
	}


	public void setStatoEscussione(String statoEscussione) {
		this.statoEscussione = statoEscussione;
	}


	public Date getDataRicevimentoRichEscuss() {
		return dataRicevimentoRichEscuss;
	}


	public void setDataRicevimentoRichEscuss(Date dataRicevimentoRichEscuss) {
		this.dataRicevimentoRichEscuss = dataRicevimentoRichEscuss;
	}


	public Date getDataStatoEscussione() {
		return dataStatoEscussione;
	}


	public void setDataStatoEscussione(Date dataStatoEscussione) {
		this.dataStatoEscussione = dataStatoEscussione;
	}


	public Date getDataNotificaEscussione() {
		return dataNotificaEscussione;
	}


	public void setDataNotificaEscussione(Date dataNotificaEscussione) {
		this.dataNotificaEscussione = dataNotificaEscussione;
	}


	public String getNumeroProtocolloRichiesta() {
		return numeroProtocolloRichiesta;
	}


	public void setNumeroProtocolloRichiesta(String numeroProtocolloRichiesta) {
		this.numeroProtocolloRichiesta = numeroProtocolloRichiesta;
	}


	public String getNumeroProtocolloNotifica() {
		return numeroProtocolloNotifica;
	}


	public void setNumeroProtocolloNotifica(String numeroProtocolloNotifica) {
		this.numeroProtocolloNotifica = numeroProtocolloNotifica;
	}


	public BigDecimal getImportoRichiesto() {
		return importoRichiesto;
	}


	public void setImportoRichiesto(BigDecimal importoRichiesto) {
		this.importoRichiesto = importoRichiesto;
	}


	public BigDecimal getImportoApprovato() {
		return importoApprovato;
	}


	public void setImportoApprovato(BigDecimal importoApprovato) {
		this.importoApprovato = importoApprovato;
	}


	public String getCausaleBonifico() {
		return causaleBonifico;
	}


	public void setCausaleBonifico(String causaleBonifico) {
		this.causaleBonifico = causaleBonifico;
	}


	public String getIbanBanca() {
		return ibanBanca;
	}


	public void setIbanBanca(String ibanBanca) {
		this.ibanBanca = ibanBanca;
	}


	public String getDenomBanca() {
		return denomBanca;
	}


	public void setDenomBanca(String denomBanca) {
		this.denomBanca = denomBanca;
	}


	public String getNote() {
		return note;
	}


	public void setNote(String note) {
		this.note = note;
	}


	@Override
	public String toString() {
		return "ModificaEscussioneRiassicurazioniDTO [idProgetto=" + idProgetto + ", idEscussioneCorrente="
				+ idEscussioneCorrente + ", idStatoEscussione=" + idStatoEscussione + ", idTipoEscussione="
				+ idTipoEscussione + ", tipoEscussione=" + tipoEscussione + ", statoEscussione=" + statoEscussione
				+ ", dataRicevimentoRichEscuss=" + dataRicevimentoRichEscuss + ", dataStatoEscussione="
				+ dataStatoEscussione + ", dataNotificaEscussione=" + dataNotificaEscussione
				+ ", numeroProtocolloRichiesta=" + numeroProtocolloRichiesta + ", numeroProtocolloNotifica="
				+ numeroProtocolloNotifica + ", importoRichiesto=" + importoRichiesto + ", importoApprovato="
				+ importoApprovato + ", causaleBonifico=" + causaleBonifico + ", ibanBanca=" + ibanBanca
				+ ", denomBanca=" + denomBanca + ", note=" + note + "]";
	}

	
}
