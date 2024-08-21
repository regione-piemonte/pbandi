/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.pbandisrv.dto.manager.report;

public class DichiarazioneDiRinunciaDTO {
	
	private java.lang.Long idDichiarazione = null;
	private java.util.Date dataRinuncia = null;
	private java.lang.String motivoRinuncia = null;
	private java.lang.Long giorniRinuncia = null;
	private java.lang.Double importoDaRestituire = null;
	
	
	public java.lang.Long getIdDichiarazione() {
		return idDichiarazione;
	}
	public void setIdDichiarazione(java.lang.Long idDichiarazione) {
		this.idDichiarazione = idDichiarazione;
	}
	public java.util.Date getDataRinuncia() {
		return dataRinuncia;
	}
	public void setDataRinuncia(java.util.Date dataRinuncia) {
		this.dataRinuncia = dataRinuncia;
	}
	public java.lang.String getMotivoRinuncia() {
		return motivoRinuncia;
	}
	public void setMotivoRinuncia(java.lang.String motivoRinuncia) {
		this.motivoRinuncia = motivoRinuncia;
	}
	public java.lang.Long getGiorniRinuncia() {
		return giorniRinuncia;
	}
	public void setGiorniRinuncia(java.lang.Long giorniRinuncia) {
		this.giorniRinuncia = giorniRinuncia;
	}
	public java.lang.Double getImportoDaRestituire() {
		return importoDaRestituire;
	}
	public void setImportoDaRestituire(java.lang.Double importoDaRestituire) {
		this.importoDaRestituire = importoDaRestituire;
	}

}
