/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.dto.cronoprogrammaFasi;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import org.springframework.jdbc.core.RowMapper;

public class AllegatiCronoprogrammaFasi implements java.io.Serializable {

	private Long idFileEntita;
	private Long idFile;
	private Long idFolder;
	private Long idDocumentoIndex;
	private String nomeFile;
	private Integer sizeFile;
	private Long idProgettoIter;
	private Long idProgetto;
	private Long idIter;
	private String descIter;
	private String flagFaseChiusa;
	private Long idDichiarazioneSpesa;
	private Boolean associato;

	public Long getIdFileEntita() {
		return idFileEntita;
	}

	public void setIdFileEntita(Long idFileEntita) {
		this.idFileEntita = idFileEntita;
	}

	public Long getIdFile() {
		return idFile;
	}

	public void setIdFile(Long idFile) {
		this.idFile = idFile;
	}

	public Long getIdFolder() {
		return idFolder;
	}

	public void setIdFolder(Long idFolder) {
		this.idFolder = idFolder;
	}

	public Long getIdDocumentoIndex() {
		return idDocumentoIndex;
	}

	public void setIdDocumentoIndex(Long idDocumentoIndex) {
		this.idDocumentoIndex = idDocumentoIndex;
	}

	public String getNomeFile() {
		return nomeFile;
	}

	public void setNomeFile(String nomeFile) {
		this.nomeFile = nomeFile;
	}

	public Integer getSizeFile() {
		return sizeFile;
	}

	public void setSizeFile(Integer sizeFile) {
		this.sizeFile = sizeFile;
	}

	public Long getIdProgettoIter() {
		return idProgettoIter;
	}

	public void setIdProgettoIter(Long idProgettoIter) {
		this.idProgettoIter = idProgettoIter;
	}

	public Long getIdProgetto() {
		return idProgetto;
	}

	public void setIdProgetto(Long idProgetto) {
		this.idProgetto = idProgetto;
	}

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

	public String getFlagFaseChiusa() {
		return flagFaseChiusa;
	}

	public void setFlagFaseChiusa(String flagFaseChiusa) {
		this.flagFaseChiusa = flagFaseChiusa;
	}

	public Long getIdDichiarazioneSpesa() {
		return idDichiarazioneSpesa;
	}

	public void setIdDichiarazioneSpesa(Long idDichiarazioneSpesa) {
		this.idDichiarazioneSpesa = idDichiarazioneSpesa;
	}

	public Boolean getAssociato() {
		return associato;
	}

	public void setAssociato(Boolean associato) {
		this.associato = associato;
	}

}
