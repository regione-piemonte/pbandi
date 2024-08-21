/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.dto.archivioFile;

import java.util.ArrayList;

public class FolderDTO implements java.io.Serializable {
	
	static final long serialVersionUID = 1;

	private java.util.Date dtInserimento = null;
	private java.util.Date dtAggiornamento = null;
	private java.lang.Long idFolder = null;
	private java.lang.Long idPadre = null;
	private java.lang.Long idSoggettoBen = null;
	private ArrayList<FileDTO> files = null;
	private ArrayList<FolderDTO> folders = null;
	private java.lang.String nomeFolder = null;
	private java.lang.Long idProgettoFolder = null;
	
	public java.util.Date getDtInserimento() {
		return dtInserimento;
	}
	public void setDtInserimento(java.util.Date dtInserimento) {
		this.dtInserimento = dtInserimento;
	}
	public java.util.Date getDtAggiornamento() {
		return dtAggiornamento;
	}
	public void setDtAggiornamento(java.util.Date dtAggiornamento) {
		this.dtAggiornamento = dtAggiornamento;
	}
	public java.lang.Long getIdFolder() {
		return idFolder;
	}
	public void setIdFolder(java.lang.Long idFolder) {
		this.idFolder = idFolder;
	}
	public java.lang.Long getIdPadre() {
		return idPadre;
	}
	public void setIdPadre(java.lang.Long idPadre) {
		this.idPadre = idPadre;
	}
	public java.lang.Long getIdSoggettoBen() {
		return idSoggettoBen;
	}
	public void setIdSoggettoBen(java.lang.Long idSoggettoBen) {
		this.idSoggettoBen = idSoggettoBen;
	}
	public ArrayList<FileDTO> getFiles() {
		return files;
	}
	public void setFiles(ArrayList<FileDTO> files) {
		this.files = files;
	}
	public ArrayList<FolderDTO> getFolders() {
		return folders;
	}
	public void setFolders(ArrayList<FolderDTO> folders) {
		this.folders = folders;
	}
	public java.lang.String getNomeFolder() {
		return nomeFolder;
	}
	public void setNomeFolder(java.lang.String nomeFolder) {
		this.nomeFolder = nomeFolder;
	}
	public java.lang.Long getIdProgettoFolder() {
		return idProgettoFolder;
	}
	public void setIdProgettoFolder(java.lang.Long idProgettoFolder) {
		this.idProgettoFolder = idProgettoFolder;
	}

}