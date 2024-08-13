/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweb.pbandisrv.dto.manager;


public class DocumentoIndexDTO implements java.io.Serializable {
	

	
	private Long idProgetto;
	private Long idTarget;
	private String nomeEntita;
	private String nomeFile;
	private Long idStatoTipoDocIndex;
	private String tipoDocIndex;
	private String uid;
	private Integer versione;
	/**
	 * @return the idProgetto
	 */
	public Long getIdProgetto() {
		return idProgetto;
	}

	/**
	 * @param idProgetto the idProgetto to set
	 */
	public void setIdProgetto(Long idProgetto) {
		this.idProgetto = idProgetto;
	}

	/**
	 * @return the idTarget
	 */
	public Long getIdTarget() {
		return idTarget;
	}

	/**
	 * @param idTarget the idTarget to set
	 */
	public void setIdTarget(Long idTarget) {
		this.idTarget = idTarget;
	}

	/**
	 * @return the nomeEntita
	 */
	public String getNomeEntita() {
		return nomeEntita;
	}

	/**
	 * @param nomeEntita the nomeEntita to set
	 */
	public void setNomeEntita(String nomeEntita) {
		this.nomeEntita = nomeEntita;
	}

	/**
	 * @return the nomeFile
	 */
	public String getNomeFile() {
		return nomeFile;
	}

	/**
	 * @param nomeFile the nomeFile to set
	 */
	public void setNomeFile(String nomeFile) {
		this.nomeFile = nomeFile;
	}

	/**
	 * @return the tipoDocIndex
	 */
	public String getTipoDocIndex() {
		return tipoDocIndex;
	}

	/**
	 * @param tipoDocIndex the tipoDocIndex to set
	 */
	public void setTipoDocIndex(String tipoDocIndex) {
		this.tipoDocIndex = tipoDocIndex;
	}

	

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getUid() {
		return uid;
	}

	public void setIdStatoTipoDocIndex(Long idStatoTipoDocIndex) {
		this.idStatoTipoDocIndex = idStatoTipoDocIndex;
	}

	public Long getIdStatoTipoDocIndex() {
		return idStatoTipoDocIndex;
	}

	public void setVersione(Integer versione) {
		this.versione = versione;
	}

	public Integer getVersione() {
		return versione;
	}

	

}
