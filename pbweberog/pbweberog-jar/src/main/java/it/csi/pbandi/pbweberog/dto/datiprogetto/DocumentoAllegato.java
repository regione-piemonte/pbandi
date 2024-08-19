/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweberog.dto.datiprogetto;

public class DocumentoAllegato implements java.io.Serializable {

	private Long id;
	private String nome;
	private String protocollo;
	private Long idParent;
	private Boolean isDisassociabile;
	private Long sizeFile;

	private static final long serialVersionUID = 1L;

	
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getProtocollo() {
		return protocollo;
	}

	public void setProtocollo(String protocollo) {
		this.protocollo = protocollo;
	}

	public Long getIdParent() {
		return idParent;
	}

	public void setIdParent(Long idParent) {
		this.idParent = idParent;
	}

	public Boolean getIsDisassociabile() {
		return isDisassociabile;
	}

	public void setIsDisassociabile(Boolean isDisassociabile) {
		this.isDisassociabile = isDisassociabile;
	}

	public Long getSizeFile() {
		return sizeFile;
	}

	public void setSizeFile(Long sizeFile) {
		this.sizeFile = sizeFile;
	}

	public DocumentoAllegato() {
		super();

	}

	public String toString() {
		return super.toString();
	}
}
