/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweberog.dto.rinuncia;

import org.springframework.validation.annotation.Validated;

@Validated
public class DichiarazioneRinuncia implements java.io.Serializable {

	private String dtComunicazioneRinuncia;
	private String fileName;
	private Integer giorniRestituzione;
	private String idDocIndex;
	private Long idRappresentanteLegale;
	private Long idRinuncia;
	private Double importoDaRestituire;
	private String motivazione;

	private String uuidDocumentoIndex;
	private static final long serialVersionUID = 1L;

	
	
	public String getDtComunicazioneRinuncia() {
		return dtComunicazioneRinuncia;
	}

	public void setDtComunicazioneRinuncia(String dtComunicazioneRinuncia) {
		this.dtComunicazioneRinuncia = dtComunicazioneRinuncia;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public Integer getGiorniRestituzione() {
		return giorniRestituzione;
	}

	public void setGiorniRestituzione(Integer giorniRestituzione) {
		this.giorniRestituzione = giorniRestituzione;
	}

	public String getIdDocIndex() {
		return idDocIndex;
	}

	public void setIdDocIndex(String idDocIndex) {
		this.idDocIndex = idDocIndex;
	}

	public Long getIdRappresentanteLegale() {
		return idRappresentanteLegale;
	}

	public void setIdRappresentanteLegale(Long idRappresentanteLegale) {
		this.idRappresentanteLegale = idRappresentanteLegale;
	}

	public Long getIdRinuncia() {
		return idRinuncia;
	}

	public void setIdRinuncia(Long idRinuncia) {
		this.idRinuncia = idRinuncia;
	}

	public Double getImportoDaRestituire() {
		return importoDaRestituire;
	}

	public void setImportoDaRestituire(Double importoDaRestituire) {
		this.importoDaRestituire = importoDaRestituire;
	}

	public String getMotivazione() {
		return motivazione;
	}

	public void setMotivazione(String motivazione) {
		this.motivazione = motivazione;
	}

	public String getUuidDocumentoIndex() {
		return uuidDocumentoIndex;
	}

	public void setUuidDocumentoIndex(String uuidDocumentoIndex) {
		this.uuidDocumentoIndex = uuidDocumentoIndex;
	}

	public DichiarazioneRinuncia() {
		super();

	}

	public String toString() {
		return super.toString();
	}
}
