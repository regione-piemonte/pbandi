/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebrce.dto.affidamenti;

public class DocumentoAllegato implements java.io.Serializable {
	static final long serialVersionUID = 1L;
	private Long id;
	private Long idParent;
	private String nome;
	private Long protocollo;
	private Long sizeFile;
	private Boolean isDisassociabile;
	private String linkCmd;
	private String dataInvio;
	private String descStatoTipoDocIndex;
	private String descBreveTipoDocIndex;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getIdParent() {
		return idParent;
	}

	public void setIdParent(Long idParent) {
		this.idParent = idParent;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Long getProtocollo() {
		return protocollo;
	}

	public void setProtocollo(Long protocollo) {
		this.protocollo = protocollo;
	}

	public Long getSizeFile() {
		return sizeFile;
	}

	public void setSizeFile(Long sizeFile) {
		this.sizeFile = sizeFile;
	}

	public Boolean getIsDisassociabile() {
		return isDisassociabile;
	}

	public void setIsDisassociabile(Boolean isDisassociabile) {
		this.isDisassociabile = isDisassociabile;
	}

	public String getLinkCmd() {
		return linkCmd;
	}

	public void setLinkCmd(String linkCmd) {
		this.linkCmd = linkCmd;
	}

	public String getDataInvio() {
		return dataInvio;
	}

	public void setDataInvio(String dataInvio) {
		this.dataInvio = dataInvio;
	}

	public String getDescStatoTipoDocIndex() {
		return descStatoTipoDocIndex;
	}

	public void setDescStatoTipoDocIndex(String descStatoTipoDocIndex) {
		this.descStatoTipoDocIndex = descStatoTipoDocIndex;
	}

	public String getDescBreveTipoDocIndex() {
		return descBreveTipoDocIndex;
	}

	public void setDescBreveTipoDocIndex(String descBreveTipoDocIndex) {
		this.descBreveTipoDocIndex = descBreveTipoDocIndex;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public DocumentoAllegato() {
		super();

	}

	public String toString() {
		return super.toString();
	}

}
