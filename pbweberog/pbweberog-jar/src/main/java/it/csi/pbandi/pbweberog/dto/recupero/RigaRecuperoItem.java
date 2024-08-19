/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweberog.dto.recupero;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RigaRecuperoItem implements java.io.Serializable {

	private Long idRecupero;
	private Long idModalitaAgevolazione;
	private String label;
	private Double importoAgevolato;
	private Double importoErogato;
	private Double importoTotaleRevoche;
	private String dtRevoca;
	private String estremiRevoca;
	private Double importoTotaleRecuperato;
	private Double importoRecupero;
	private Boolean hasRevoche;
	private Boolean isRigaModalita;
	private Boolean isRigaTotale;
	private Long idAnnoContabile;
	private static final long serialVersionUID = 1L;

	public Long getIdRecupero() {
		return idRecupero;
	}

	public void setIdRecupero(Long idRecupero) {
		this.idRecupero = idRecupero;
	}

	public Long getIdModalitaAgevolazione() {
		return idModalitaAgevolazione;
	}

	public void setIdModalitaAgevolazione(Long idModalitaAgevolazione) {
		this.idModalitaAgevolazione = idModalitaAgevolazione;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
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

	public Double getImportoTotaleRevoche() {
		return importoTotaleRevoche;
	}

	public void setImportoTotaleRevoche(Double importoTotaleRevoche) {
		this.importoTotaleRevoche = importoTotaleRevoche;
	}

	public String getDtRevoca() {
		return dtRevoca;
	}

	public void setDtRevoca(String dtRevoca) {
		this.dtRevoca = dtRevoca;
	}

	public String getEstremiRevoca() {
		return estremiRevoca;
	}

	public void setEstremiRevoca(String estremiRevoca) {
		this.estremiRevoca = estremiRevoca;
	}

	public Double getImportoTotaleRecuperato() {
		return importoTotaleRecuperato;
	}

	public void setImportoTotaleRecuperato(Double importoTotaleRecuperato) {
		this.importoTotaleRecuperato = importoTotaleRecuperato;
	}

	public Double getImportoRecupero() {
		return importoRecupero;
	}

	public void setImportoRecupero(Double importoRecupero) {
		this.importoRecupero = importoRecupero;
	}

	public Boolean getHasRevoche() {
		return hasRevoche;
	}

	public void setHasRevoche(Boolean hasRevoche) {
		this.hasRevoche = hasRevoche;
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

	public Long getIdAnnoContabile() {
		return idAnnoContabile;
	}

	public void setIdAnnoContabile(Long idAnnoContabile) {
		this.idAnnoContabile = idAnnoContabile;
	}

	public RigaRecuperoItem() {
		super();

	}

	public String toString() {
		return super.toString();
	}
}
