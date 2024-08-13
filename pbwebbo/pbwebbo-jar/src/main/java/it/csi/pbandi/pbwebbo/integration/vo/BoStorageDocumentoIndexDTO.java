/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebbo.integration.vo;

public class BoStorageDocumentoIndexDTO {

	private Long idDocumentoIndex;
	private Long idProgetto;
	private String codiceVisualizzatoProgetto;
	private Long idTipoDocIndex;
	private String descrizioneBreveTipoDocIndex;
	private String descrizioneTipoDocIndex;
	private String nomeFile;
	private Boolean flagFirmato;
	private Boolean flagMarcato;
	private Boolean flagFirmaAutografa;

	public BoStorageDocumentoIndexDTO(Long idDocumentoIndex, Long idProgetto, String codiceVisualizzatoProgetto,
			Long idTipoDocIndex, String descrizioneBreveTipoDocIndex, String descrizioneTipoDocIndex, String nomeFile,
			Boolean flagFirmato, Boolean flagMarcato, Boolean flagFirmaAutografa) {
		super();
		this.idDocumentoIndex = idDocumentoIndex;
		this.idProgetto = idProgetto;
		this.codiceVisualizzatoProgetto = codiceVisualizzatoProgetto;
		this.idTipoDocIndex = idTipoDocIndex;
		this.descrizioneBreveTipoDocIndex = descrizioneBreveTipoDocIndex;
		this.descrizioneTipoDocIndex = descrizioneTipoDocIndex;
		this.nomeFile = nomeFile;
		this.flagFirmato = flagFirmato;
		this.flagMarcato = flagMarcato;
		this.flagFirmaAutografa = flagFirmaAutografa;
	}

	public BoStorageDocumentoIndexDTO() {
		// TODO Auto-generated constructor stub
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

	public String getCodiceVisualizzatoProgetto() {
		return codiceVisualizzatoProgetto;
	}

	public void setCodiceVisualizzatoProgetto(String codiceVisualizzatoProgetto) {
		this.codiceVisualizzatoProgetto = codiceVisualizzatoProgetto;
	}

	public Long getIdTipoDocIndex() {
		return idTipoDocIndex;
	}

	public void setIdTipoDocIndex(Long idTipoDocIndex) {
		this.idTipoDocIndex = idTipoDocIndex;
	}

	public String getDescrizioneBreveTipoDocIndex() {
		return descrizioneBreveTipoDocIndex;
	}

	public void setDescrizioneBreveTipoDocIndex(String descrizioneBreveTipoDocIndex) {
		this.descrizioneBreveTipoDocIndex = descrizioneBreveTipoDocIndex;
	}

	public String getDescrizioneTipoDocIndex() {
		return descrizioneTipoDocIndex;
	}

	public void setDescrizioneTipoDocIndex(String descrizioneTipoDocIndex) {
		this.descrizioneTipoDocIndex = descrizioneTipoDocIndex;
	}

	public String getNomeFile() {
		return nomeFile;
	}

	public void setNomeFile(String nomeFile) {
		this.nomeFile = nomeFile;
	}

	public Boolean getFlagFirmato() {
		return flagFirmato;
	}

	public void setFlagFirmato(Boolean flagFirmato) {
		this.flagFirmato = flagFirmato;
	}

	public Boolean getFlagMarcato() {
		return flagMarcato;
	}

	public void setFlagMarcato(Boolean flagMarcato) {
		this.flagMarcato = flagMarcato;
	}

	public Boolean getFlagFirmaAutografa() {
		return flagFirmaAutografa;
	}

	public void setFlagFirmaAutografa(Boolean flagFirmaAutografa) {
		this.flagFirmaAutografa = flagFirmaAutografa;
	}

}
