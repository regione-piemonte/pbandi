/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweb.dto;

public class SalCorrenteDTO implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long idIter;
	private String descIter;
	private String descBreveTipoDs;
	private Double percImportoContrib;
	private Boolean ultimoIter;

	public Long getIdIter() {
		return idIter;
	}

	public void setIdIter(Long idIter) {
		this.idIter = idIter;
	}

	public String getDescIter() {
		return descIter;
	}

	public void setDescIter(String descIter) {
		this.descIter = descIter;
	}

	public String getDescBreveTipoDs() {
		return descBreveTipoDs;
	}

	public void setDescBreveTipoDs(String descBreveTipoDs) {
		this.descBreveTipoDs = descBreveTipoDs;
	}

	public Double getPercImportoContrib() {
		return percImportoContrib;
	}

	public void setPercImportoContrib(Double percImportoContrib) {
		this.percImportoContrib = percImportoContrib;
	}

	public Boolean getUltimoIter() {
		return ultimoIter;
	}

	public void setUltimoIter(Boolean ultimoIter) {
		this.ultimoIter = ultimoIter;
	}

}
