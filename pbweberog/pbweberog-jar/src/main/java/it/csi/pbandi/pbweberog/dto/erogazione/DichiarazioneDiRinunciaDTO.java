/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweberog.dto.erogazione;

import java.util.Date;

public class DichiarazioneDiRinunciaDTO implements java.io.Serializable {

	static final long serialVersionUID = 1;
	private Date dataRinuncia;
	private String fileName;
	private Long giorniRinuncia;
	private Long idDelegato;
	private Long idDocumentoIndex;
	private Long idProgetto;
	private Long idRappresentanteLegale;
	private Double importoDaRestituire;
	private String motivoRinuncia;
	private String uuidNodoIndex;
	public Date getDataRinuncia() {
		return dataRinuncia;
	}
	public void setDataRinuncia(Date dataRinuncia) {
		this.dataRinuncia = dataRinuncia;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public Long getGiorniRinuncia() {
		return giorniRinuncia;
	}
	public void setGiorniRinuncia(Long giorniRinuncia) {
		this.giorniRinuncia = giorniRinuncia;
	}
	public Long getIdDelegato() {
		return idDelegato;
	}
	public void setIdDelegato(Long idDelegato) {
		this.idDelegato = idDelegato;
	}
	public Long getIdDocumentoIndex() {
		return idDocumentoIndex;
	}
	public void setIdDocumentoIndex(Long idDocumentoIndex) {
		this.idDocumentoIndex = idDocumentoIndex;
	}
	public Long getIdProgetto() {
		return idProgetto;
	}
	public void setIdProgetto(Long idProgetto) {
		this.idProgetto = idProgetto;
	}
	public Long getIdRappresentanteLegale() {
		return idRappresentanteLegale;
	}
	public void setIdRappresentanteLegale(Long idRappresentanteLegale) {
		this.idRappresentanteLegale = idRappresentanteLegale;
	}
	public Double getImportoDaRestituire() {
		return importoDaRestituire;
	}
	public void setImportoDaRestituire(Double importoDaRestituire) {
		this.importoDaRestituire = importoDaRestituire;
	}
	public String getMotivoRinuncia() {
		return motivoRinuncia;
	}
	public void setMotivoRinuncia(String motivoRinuncia) {
		this.motivoRinuncia = motivoRinuncia;
	}
	public String getUuidNodoIndex() {
		return uuidNodoIndex;
	}
	public void setUuidNodoIndex(String uuidNodoIndex) {
		this.uuidNodoIndex = uuidNodoIndex;
	}

	
}
