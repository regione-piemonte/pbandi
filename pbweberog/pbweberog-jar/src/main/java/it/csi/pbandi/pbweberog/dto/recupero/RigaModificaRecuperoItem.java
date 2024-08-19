/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweberog.dto.recupero;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RigaModificaRecuperoItem implements java.io.Serializable {

	private String data;
	private Boolean hasCausaliErogazione;
	private Double importoAgevolato;
	private Double importoErogato;
	private Double importoRevocato;
	private Double importoRecuperato;
	private Boolean isRigaModalita;
	private Boolean isRigaCausale;
	private Boolean isRigaRevoca;
	private Boolean isRigaRecupero;
	private Boolean isRigaTotale;
	private String idRecupero;
	private String idModalitaAgevolazione;
	private String label;
	private String riferimento;
	private Long idAnnoContabile;
	private static final long serialVersionUID = 1L;

	
	
	
	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public Boolean getHasCausaliErogazione() {
		return hasCausaliErogazione;
	}

	public void setHasCausaliErogazione(Boolean hasCausaliErogazione) {
		this.hasCausaliErogazione = hasCausaliErogazione;
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

	public String getIdRecupero() {
		return idRecupero;
	}

	public void setIdRecupero(String idRecupero) {
		this.idRecupero = idRecupero;
	}

	public String getIdModalitaAgevolazione() {
		return idModalitaAgevolazione;
	}

	public void setIdModalitaAgevolazione(String idModalitaAgevolazione) {
		this.idModalitaAgevolazione = idModalitaAgevolazione;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getRiferimento() {
		return riferimento;
	}

	public void setRiferimento(String riferimento) {
		this.riferimento = riferimento;
	}

	public Long getIdAnnoContabile() {
		return idAnnoContabile;
	}

	public void setIdAnnoContabile(Long idAnnoContabile) {
		this.idAnnoContabile = idAnnoContabile;
	}

	public RigaModificaRecuperoItem() {
		super();

	}

	public String toString() {
		return super.toString();
	}

}
