package it.csi.pbandi.pbweb.dto.archivioFile;

import java.util.ArrayList;

import it.csi.pbandi.pbservizit.security.UserInfoSec;

public class InizializzaArchivioFileDTO implements java.io.Serializable {

	static final long serialVersionUID = 1;

	private UserSpaceDTO  userSpaceDTO = null;
	
	private ArrayList<FolderDTO> folders = null;
	
	private Long dimMaxSingoloFile = null;
	
	private ArrayList<String> estensioniConsentite = null;
	
	private UserInfoSec userInfoSec = null;

	public UserInfoSec getUserInfoSec() {
		return userInfoSec;
	}

	public void setUserInfoSec(UserInfoSec userInfoSec) {
		this.userInfoSec = userInfoSec;
	}

	public ArrayList<String> getEstensioniConsentite() {
		return estensioniConsentite;
	}

	public void setEstensioniConsentite(ArrayList<String> estensioniConsentite) {
		this.estensioniConsentite = estensioniConsentite;
	}

	public Long getDimMaxSingoloFile() {
		return dimMaxSingoloFile;
	}

	public void setDimMaxSingoloFile(Long dimMaxSingoloFile) {
		this.dimMaxSingoloFile = dimMaxSingoloFile;
	}

	public ArrayList<FolderDTO> getFolders() {
		return folders;
	}

	public void setFolders(ArrayList<FolderDTO> folders) {
		this.folders = folders;
	}

	public UserSpaceDTO getUserSpaceDTO() {
		return userSpaceDTO;
	}

	public void setUserSpaceDTO(UserSpaceDTO userSpaceDTO) {
		this.userSpaceDTO = userSpaceDTO;
	}

}
