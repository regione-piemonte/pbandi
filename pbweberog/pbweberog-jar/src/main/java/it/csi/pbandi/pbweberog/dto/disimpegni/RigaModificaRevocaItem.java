/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweberog.dto.disimpegni;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RigaModificaRevocaItem implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String causaleDisimpegno;
	private String data;
	private String dtDecorRevoca;
	private String descPeriodoVisualizzata;
	private Boolean hasCausaliErogazione;
	private Boolean hasIrregolarita;
	private String idAnnoContabile;
	private String idCausaleDisimpegno;
	private String idModalitaAgevolazione;
	private String idRevoca;
	private Double importoAgevolato;
	private Double importoErogato;
	private Double importoIrregolarita;
	private Double importoRevocato;
	private Double importoRecuperato;
	private ArrayList<RevocaIrregolarita> irregolarita = new ArrayList<RevocaIrregolarita>();
	private Boolean isDeletable;
	private Boolean isRigaModalita;
	private Boolean isRigaCausale;
	private Boolean isRigaRevoca;
	private Boolean isRigaRecupero;
	private Boolean isRigaTotale;
	private Boolean isUpdatable;
	private String label;
	private String lnkElimina;
	private String lnkIrregolarita;
	private String lnkModifica;
	private String motivazione;
	private String riferimento;
	private String idMotivoRevoca;
	private String ordineRecupero;
	private Long idModalitaRecupero;
	private String descModalitaRecupero;

	
	
	public String getCausaleDisimpegno() {
		return causaleDisimpegno;
	}

	public void setCausaleDisimpegno(String causaleDisimpegno) {
		this.causaleDisimpegno = causaleDisimpegno;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getDtDecorRevoca() {
		return dtDecorRevoca;
	}

	public void setDtDecorRevoca(String dtDecorRevoca) {
		this.dtDecorRevoca = dtDecorRevoca;
	}

	public String getDescPeriodoVisualizzata() {
		return descPeriodoVisualizzata;
	}

	public void setDescPeriodoVisualizzata(String descPeriodoVisualizzata) {
		this.descPeriodoVisualizzata = descPeriodoVisualizzata;
	}

	public Boolean getHasCausaliErogazione() {
		return hasCausaliErogazione;
	}

	public void setHasCausaliErogazione(Boolean hasCausaliErogazione) {
		this.hasCausaliErogazione = hasCausaliErogazione;
	}

	public Boolean getHasIrregolarita() {
		return hasIrregolarita;
	}

	public void setHasIrregolarita(Boolean hasIrregolarita) {
		this.hasIrregolarita = hasIrregolarita;
	}

	public String getIdAnnoContabile() {
		return idAnnoContabile;
	}

	public void setIdAnnoContabile(String idAnnoContabile) {
		this.idAnnoContabile = idAnnoContabile;
	}

	public String getIdCausaleDisimpegno() {
		return idCausaleDisimpegno;
	}

	public void setIdCausaleDisimpegno(String idCausaleDisimpegno) {
		this.idCausaleDisimpegno = idCausaleDisimpegno;
	}

	public String getIdModalitaAgevolazione() {
		return idModalitaAgevolazione;
	}

	public void setIdModalitaAgevolazione(String idModalitaAgevolazione) {
		this.idModalitaAgevolazione = idModalitaAgevolazione;
	}

	public String getIdRevoca() {
		return idRevoca;
	}

	public void setIdRevoca(String idRevoca) {
		this.idRevoca = idRevoca;
	}

	public Double getImportoAgevolato() {
		return importoAgevolato;
	}

	public void setImportoAgevolato(Double importoAgevolato) {
		this.importoAgevolato = importoAgevolato;
	}

	public Double getImportoErogato() {
		return importoErogato;
	}

	public void setImportoErogato(Double importoErogato) {
		this.importoErogato = importoErogato;
	}

	public Double getImportoIrregolarita() {
		return importoIrregolarita;
	}

	public void setImportoIrregolarita(Double importoIrregolarita) {
		this.importoIrregolarita = importoIrregolarita;
	}

	public Double getImportoRevocato() {
		return importoRevocato;
	}

	public void setImportoRevocato(Double importoRevocato) {
		this.importoRevocato = importoRevocato;
	}

	public Double getImportoRecuperato() {
		return importoRecuperato;
	}

	public void setImportoRecuperato(Double importoRecuperato) {
		this.importoRecuperato = importoRecuperato;
	}

	public ArrayList<RevocaIrregolarita> getIrregolarita() {
		return irregolarita;
	}

	public void setIrregolarita(ArrayList<RevocaIrregolarita> irregolarita) {
		this.irregolarita = irregolarita;
	}

	public Boolean getIsDeletable() {
		return isDeletable;
	}

	public void setIsDeletable(Boolean isDeletable) {
		this.isDeletable = isDeletable;
	}

	public Boolean getIsRigaModalita() {
		return isRigaModalita;
	}

	public void setIsRigaModalita(Boolean isRigaModalita) {
		this.isRigaModalita = isRigaModalita;
	}

	public Boolean getIsRigaCausale() {
		return isRigaCausale;
	}

	public void setIsRigaCausale(Boolean isRigaCausale) {
		this.isRigaCausale = isRigaCausale;
	}

	public Boolean getIsRigaRevoca() {
		return isRigaRevoca;
	}

	public void setIsRigaRevoca(Boolean isRigaRevoca) {
		this.isRigaRevoca = isRigaRevoca;
	}

	public Boolean getIsRigaRecupero() {
		return isRigaRecupero;
	}

	public void setIsRigaRecupero(Boolean isRigaRecupero) {
		this.isRigaRecupero = isRigaRecupero;
	}

	public Boolean getIsRigaTotale() {
		return isRigaTotale;
	}

	public void setIsRigaTotale(Boolean isRigaTotale) {
		this.isRigaTotale = isRigaTotale;
	}

	public Boolean getIsUpdatable() {
		return isUpdatable;
	}

	public void setIsUpdatable(Boolean isUpdatable) {
		this.isUpdatable = isUpdatable;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getLnkElimina() {
		return lnkElimina;
	}

	public void setLnkElimina(String lnkElimina) {
		this.lnkElimina = lnkElimina;
	}

	public String getLnkIrregolarita() {
		return lnkIrregolarita;
	}

	public void setLnkIrregolarita(String lnkIrregolarita) {
		this.lnkIrregolarita = lnkIrregolarita;
	}

	public String getLnkModifica() {
		return lnkModifica;
	}

	public void setLnkModifica(String lnkModifica) {
		this.lnkModifica = lnkModifica;
	}

	public String getMotivazione() {
		return motivazione;
	}

	public void setMotivazione(String motivazione) {
		this.motivazione = motivazione;
	}

	public String getRiferimento() {
		return riferimento;
	}

	public void setRiferimento(String riferimento) {
		this.riferimento = riferimento;
	}

	public String getIdMotivoRevoca() {
		return idMotivoRevoca;
	}

	public void setIdMotivoRevoca(String idMotivoRevoca) {
		this.idMotivoRevoca = idMotivoRevoca;
	}

	public String getOrdineRecupero() {
		return ordineRecupero;
	}

	public void setOrdineRecupero(String ordineRecupero) {
		this.ordineRecupero = ordineRecupero;
	}

	public Long getIdModalitaRecupero() {
		return idModalitaRecupero;
	}

	public void setIdModalitaRecupero(Long idModalitaRecupero) {
		this.idModalitaRecupero = idModalitaRecupero;
	}

	public String getDescModalitaRecupero() {
		return descModalitaRecupero;
	}

	public void setDescModalitaRecupero(String descModalitaRecupero) {
		this.descModalitaRecupero = descModalitaRecupero;
	}

	public RigaModificaRevocaItem() {
		super();

	}

	public String toString() {
		return super.toString();
	}

}
