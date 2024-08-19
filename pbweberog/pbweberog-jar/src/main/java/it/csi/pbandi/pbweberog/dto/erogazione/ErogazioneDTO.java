/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweberog.dto.erogazione;

import java.util.Date;

public class ErogazioneDTO implements java.io.Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long idErogazione;
	private String codCausaleErogazione;
	private String descrizioneCausaleErogazione;
	private Double percentualeErogazioneEffettiva;
	private Double percentualeErogazioneFinanziaria;
	private Double importoErogazioneEffettivo;
	private Double importoErogazioneFinanziario;
	private String codModalitaErogazione;
	private String codModalitaAgevolazione;
	private String codDirezione;
	private Date dataContabile;
	private String numeroRiferimento;
	private String noteErogazione;
	private FideiussioneDTO[] fideiussioni;
	private ErogazioneDTO[] erogazioni;
	private RichiestaErogazione[] richiesteErogazioni;
	private SpesaProgettoDTO spesaProgetto;
	private Double importoTotaleErogato;
	private Double importoTotaleResiduo;
	private Double importoTotaleRichiesto;
	private String descModalitaAgevolazione;
	private String descTipoDirezione;
	private String descModalitaErogazione;
	private Double importoTotaleRecuperi;
	private Double importoRevocato;
	
	private String descBreveCausaleErogazione;
	private String descBreveModalitaAgezolazione;
	private String descBreveModalitaErogazione;
	
	private Double percErogazione;
	private Double percLimite;
	
	private ModalitaAgevolazioneErogazioneDTO[] modalitaAgevolazioni;
	
	
	public Double getImportoRevocato() {
		return importoRevocato;
	}
	public void setImportoRevocato(Double importoRevocato) {
		this.importoRevocato = importoRevocato;
	}
	public Long getIdErogazione() {
		return idErogazione;
	}
	public void setIdErogazione(Long idErogazione) {
		this.idErogazione = idErogazione;
	}
	public String getCodCausaleErogazione() {
		return codCausaleErogazione;
	}
	public void setCodCausaleErogazione(String codCausaleErogazione) {
		this.codCausaleErogazione = codCausaleErogazione;
	}
	public String getDescrizioneCausaleErogazione() {
		return descrizioneCausaleErogazione;
	}
	public void setDescrizioneCausaleErogazione(String descrizioneCausaleErogazione) {
		this.descrizioneCausaleErogazione = descrizioneCausaleErogazione;
	}
	public Double getPercentualeErogazioneEffettiva() {
		return percentualeErogazioneEffettiva;
	}
	public void setPercentualeErogazioneEffettiva(Double percentualeErogazioneEffettiva) {
		this.percentualeErogazioneEffettiva = percentualeErogazioneEffettiva;
	}
	public Double getPercentualeErogazioneFinanziaria() {
		return percentualeErogazioneFinanziaria;
	}
	public void setPercentualeErogazioneFinanziaria(Double percentualeErogazioneFinanziaria) {
		this.percentualeErogazioneFinanziaria = percentualeErogazioneFinanziaria;
	}
	public Double getImportoErogazioneEffettivo() {
		return importoErogazioneEffettivo;
	}
	public void setImportoErogazioneEffettivo(Double importoErogazioneEffettivo) {
		this.importoErogazioneEffettivo = importoErogazioneEffettivo;
	}
	public Double getImportoErogazioneFinanziario() {
		return importoErogazioneFinanziario;
	}
	public void setImportoErogazioneFinanziario(Double importoErogazioneFinanziario) {
		this.importoErogazioneFinanziario = importoErogazioneFinanziario;
	}
	public String getCodModalitaErogazione() {
		return codModalitaErogazione;
	}
	public void setCodModalitaErogazione(String codModalitaErogazione) {
		this.codModalitaErogazione = codModalitaErogazione;
	}
	public String getCodModalitaAgevolazione() {
		return codModalitaAgevolazione;
	}
	public void setCodModalitaAgevolazione(String codModalitaAgevolazione) {
		this.codModalitaAgevolazione = codModalitaAgevolazione;
	}
	public String getCodDirezione() {
		return codDirezione;
	}
	public void setCodDirezione(String codDirezione) {
		this.codDirezione = codDirezione;
	}
	public Date getDataContabile() {
		return dataContabile;
	}
	public void setDataContabile(Date dataContabile) {
		this.dataContabile = dataContabile;
	}
	public String getNumeroRiferimento() {
		return numeroRiferimento;
	}
	public void setNumeroRiferimento(String numeroRiferimento) {
		this.numeroRiferimento = numeroRiferimento;
	}
	public String getNoteErogazione() {
		return noteErogazione;
	}
	public void setNoteErogazione(String noteErogazione) {
		this.noteErogazione = noteErogazione;
	}
	public FideiussioneDTO[] getFideiussioni() {
		return fideiussioni;
	}
	public void setFideiussioni(FideiussioneDTO[] fideiussioni) {
		this.fideiussioni = fideiussioni;
	}
	public ErogazioneDTO[] getErogazioni() {
		return erogazioni;
	}
	public void setErogazioni(ErogazioneDTO[] erogazioni) {
		this.erogazioni = erogazioni;
	}
	public RichiestaErogazione[] getRichiesteErogazioni() {
		return richiesteErogazioni;
	}
	public void setRichiesteErogazioni(RichiestaErogazione[] richiesteErogazioni) {
		this.richiesteErogazioni = richiesteErogazioni;
	}
	public SpesaProgettoDTO getSpesaProgetto() {
		return spesaProgetto;
	}
	public void setSpesaProgetto(SpesaProgettoDTO spesaProgetto) {
		this.spesaProgetto = spesaProgetto;
	}
	public Double getImportoTotaleErogato() {
		return importoTotaleErogato;
	}
	public void setImportoTotaleErogato(Double importoTotaleErogato) {
		this.importoTotaleErogato = importoTotaleErogato;
	}
	public Double getImportoTotaleResiduo() {
		return importoTotaleResiduo;
	}
	public void setImportoTotaleResiduo(Double importoTotaleResiduo) {
		this.importoTotaleResiduo = importoTotaleResiduo;
	}
	public Double getImportoTotaleRichiesto() {
		return importoTotaleRichiesto;
	}
	public void setImportoTotaleRichiesto(Double importoTotaleRichiesto) {
		this.importoTotaleRichiesto = importoTotaleRichiesto;
	}
	public String getDescModalitaAgevolazione() {
		return descModalitaAgevolazione;
	}
	public void setDescModalitaAgevolazione(String descModalitaAgevolazione) {
		this.descModalitaAgevolazione = descModalitaAgevolazione;
	}
	public String getDescTipoDirezione() {
		return descTipoDirezione;
	}
	public void setDescTipoDirezione(String descTipoDirezione) {
		this.descTipoDirezione = descTipoDirezione;
	}
	public String getDescModalitaErogazione() {
		return descModalitaErogazione;
	}
	public void setDescModalitaErogazione(String descModalitaErogazione) {
		this.descModalitaErogazione = descModalitaErogazione;
	}
	public Double getImportoTotaleRecuperi() {
		return importoTotaleRecuperi;
	}
	public void setImportoTotaleRecuperi(Double importoTotaleRecuperi) {
		this.importoTotaleRecuperi = importoTotaleRecuperi;
	}
	public ModalitaAgevolazioneErogazioneDTO[] getModalitaAgevolazioni() {
		return modalitaAgevolazioni;
	}
	public void setModalitaAgevolazioni(ModalitaAgevolazioneErogazioneDTO[] modalitaAgevolazioni) {
		this.modalitaAgevolazioni = modalitaAgevolazioni;
	}
	public String getDescBreveCausaleErogazione() {
		return descBreveCausaleErogazione;
	}
	public void setDescBreveCausaleErogazione(String descBreveCausaleErogazione) {
		this.descBreveCausaleErogazione = descBreveCausaleErogazione;
	}
	public String getDescBreveModalitaAgezolazione() {
		return descBreveModalitaAgezolazione;
	}
	public void setDescBreveModalitaAgezolazione(String descBreveModalitaAgezolazione) {
		this.descBreveModalitaAgezolazione = descBreveModalitaAgezolazione;
	}
	public String getDescBreveModalitaErogazione() {
		return descBreveModalitaErogazione;
	}
	public void setDescBreveModalitaErogazione(String descBreveModalitaErogazione) {
		this.descBreveModalitaErogazione = descBreveModalitaErogazione;
	}
	public Double getPercErogazione() {
		return percErogazione;
	}
	public void setPercErogazione(Double percErogazione) {
		this.percErogazione = percErogazione;
	}
	public Double getPercLimite() {
		return percLimite;
	}
	public void setPercLimite(Double percLimite) {
		this.percLimite = percLimite;
	}

	
	
	
}
