/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweberog.integration.vo;

public class DocumentoIndexVO extends PbandiTDocumentoIndexVO{
	private String descBreveTipoDocIndex;
	private String descTipoDocIndex;
	
	public void setDescBreveTipoDocIndex(String descBreveTipoDocIndex) {
		this.descBreveTipoDocIndex = descBreveTipoDocIndex;
	}
	public String getDescBreveTipoDocIndex() {
		return descBreveTipoDocIndex;
	}
	public void setDescTipoDocIndex(String descTipoDocIndex) {
		this.descTipoDocIndex = descTipoDocIndex;
	}
	public String getDescTipoDocIndex() {
		return descTipoDocIndex;
	}
	
}