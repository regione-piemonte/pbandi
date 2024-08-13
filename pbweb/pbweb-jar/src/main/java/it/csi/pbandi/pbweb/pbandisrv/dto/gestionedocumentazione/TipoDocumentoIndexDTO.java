/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweb.pbandisrv.dto.gestionedocumentazione;

public class TipoDocumentoIndexDTO implements java.io.Serializable {

	static final long serialVersionUID = 1;

	private java.lang.Long idTipoDocumentoIndex = null;
	private java.lang.String descTipoDocIndex = null;
	private java.lang.String descBreveTipoDocIndex = null;
	public java.lang.Long getIdTipoDocumentoIndex() {
		return idTipoDocumentoIndex;
	}
	public void setIdTipoDocumentoIndex(java.lang.Long idTipoDocumentoIndex) {
		this.idTipoDocumentoIndex = idTipoDocumentoIndex;
	}
	public java.lang.String getDescTipoDocIndex() {
		return descTipoDocIndex;
	}
	public void setDescTipoDocIndex(java.lang.String descTipoDocIndex) {
		this.descTipoDocIndex = descTipoDocIndex;
	}
	public java.lang.String getDescBreveTipoDocIndex() {
		return descBreveTipoDocIndex;
	}
	public void setDescBreveTipoDocIndex(java.lang.String descBreveTipoDocIndex) {
		this.descBreveTipoDocIndex = descBreveTipoDocIndex;
	}

}
