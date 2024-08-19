/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweberog.integration.vo;

public class TipoDocumentoIndexVO {
	private Long idTipoDocumentoIndex;
	private String descBreveTipoDocIndex;
	private String descTipoDocIndex;
	private String flagFirmabile;
	private String flagUploadable;
	public Long getIdTipoDocumentoIndex() {
		return idTipoDocumentoIndex;
	}
	public void setIdTipoDocumentoIndex(Long idTipoDocumentoIndex) {
		this.idTipoDocumentoIndex = idTipoDocumentoIndex;
	}
	public String getDescBreveTipoDocIndex() {
		return descBreveTipoDocIndex;
	}
	public void setDescBreveTipoDocIndex(String descBreveTipoDocIndex) {
		this.descBreveTipoDocIndex = descBreveTipoDocIndex;
	}
	public String getDescTipoDocIndex() {
		return descTipoDocIndex;
	}
	public void setDescTipoDocIndex(String descTipoDocIndex) {
		this.descTipoDocIndex = descTipoDocIndex;
	}
	public String getFlagFirmabile() {
		return flagFirmabile;
	}
	public void setFlagFirmabile(String flagFirmabile) {
		this.flagFirmabile = flagFirmabile;
	}
	public String getFlagUploadable() {
		return flagUploadable;
	}
	public void setFlagUploadable(String flagUploadable) {
		this.flagUploadable = flagUploadable;
	}
	
	

}
