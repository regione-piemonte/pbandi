/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebrce.dto;

public class FonteFinanziaria implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	
	private java.lang.String descFonteFinanziaria = null;
	private java.lang.Double importoUltimaQuota = null;
	private java.lang.Double percentualeUltimaQuota = null;
	private java.lang.Double importoQuota = null;
	private java.lang.Double percentualeQuota = null;
	private Boolean modificabile = false;
	private Boolean isSubTotale = false;
	private Boolean isFontePrivata = false;
	private java.lang.Long idFonteFinanziaria = null;
	private Boolean isFonteTotale = false;

	public FonteFinanziaria() {
		super();
	}

	public java.lang.String getDescFonteFinanziaria() {
		return descFonteFinanziaria;
	}

	public void setDescFonteFinanziaria(java.lang.String descFonteFinanziaria) {
		this.descFonteFinanziaria = descFonteFinanziaria;
	}

	public java.lang.Double getImportoUltimaQuota() {
		return importoUltimaQuota;
	}

	public void setImportoUltimaQuota(java.lang.Double importoUltimaQuota) {
		this.importoUltimaQuota = importoUltimaQuota;
	}

	public java.lang.Double getPercentualeUltimaQuota() {
		return percentualeUltimaQuota;
	}

	public void setPercentualeUltimaQuota(java.lang.Double percentualeUltimaQuota) {
		this.percentualeUltimaQuota = percentualeUltimaQuota;
	}

	public java.lang.Double getImportoQuota() {
		return importoQuota;
	}

	public void setImportoQuota(java.lang.Double importoQuota) {
		this.importoQuota = importoQuota;
	}

	public java.lang.Double getPercentualeQuota() {
		return percentualeQuota;
	}

	public void setPercentualeQuota(java.lang.Double percentualeQuota) {
		this.percentualeQuota = percentualeQuota;
	}

	public Boolean getModificabile() {
		return modificabile;
	}

	public void setModificabile(Boolean modificabile) {
		this.modificabile = modificabile;
	}

	public Boolean getIsSubTotale() {
		return isSubTotale;
	}

	public void setIsSubTotale(Boolean isSubTotale) {
		this.isSubTotale = isSubTotale;
	}

	public Boolean getIsFontePrivata() {
		return isFontePrivata;
	}

	public void setIsFontePrivata(Boolean isFontePrivata) {
		this.isFontePrivata = isFontePrivata;
	}

	public java.lang.Long getIdFonteFinanziaria() {
		return idFonteFinanziaria;
	}

	public void setIdFonteFinanziaria(java.lang.Long idFonteFinanziaria) {
		this.idFonteFinanziaria = idFonteFinanziaria;
	}

	public Boolean getIsFonteTotale() {
		return isFonteTotale;
	}

	public void setIsFonteTotale(Boolean isFonteTotale) {
		this.isFonteTotale = isFonteTotale;
	}

}
