/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweberog.dto.revoca;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RigaRevocaItem implements java.io.Serializable {

	private String data;
	private Boolean hasCausaliErogazione;
	private Boolean hasIrregolarita;
	private String idModalitaAgevolazione;
	private Double importoAgevolato;
	private Double importoErogato;
	private Double importoRecuperato;
	private Double importoRevocato;
	private Double importoRevocaNew;
	private Boolean isRigaModalita;
	private Boolean isRigaTotale;
	private String label;
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

	public Boolean getHasIrregolarita() {
		return hasIrregolarita;
	}

	public void setHasIrregolarita(Boolean hasIrregolarita) {
		this.hasIrregolarita = hasIrregolarita;
	}

	public String getIdModalitaAgevolazione() {
		return idModalitaAgevolazione;
	}

	public void setIdModalitaAgevolazione(String idModalitaAgevolazione) {
		this.idModalitaAgevolazione = idModalitaAgevolazione;
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

	public Double getImportoRecuperato() {
		return importoRecuperato;
	}

	public void setImportoRecuperato(Double importoRecuperato) {
		this.importoRecuperato = importoRecuperato;
	}

	public Double getImportoRevocato() {
		return importoRevocato;
	}

	public void setImportoRevocato(Double importoRevocato) {
		this.importoRevocato = importoRevocato;
	}

	public Double getImportoRevocaNew() {
		return importoRevocaNew;
	}

	public void setImportoRevocaNew(Double importoRevocaNew) {
		this.importoRevocaNew = importoRevocaNew;
	}

	public Boolean getIsRigaModalita() {
		return isRigaModalita;
	}

	public void setIsRigaModalita(Boolean isRigaModalita) {
		this.isRigaModalita = isRigaModalita;
	}

	public Boolean getIsRigaTotale() {
		return isRigaTotale;
	}

	public void setIsRigaTotale(Boolean isRigaTotale) {
		this.isRigaTotale = isRigaTotale;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public RigaRevocaItem() {
		super();

	}

	public String toString() {
		return super.toString();
	}
}
