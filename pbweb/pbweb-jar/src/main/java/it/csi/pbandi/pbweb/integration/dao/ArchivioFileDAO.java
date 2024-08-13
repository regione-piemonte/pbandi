/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweb.integration.dao;

import java.security.InvalidParameterException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;

import it.csi.pbandi.pbweb.dto.EsitoAssociaFilesDTO;
import it.csi.pbandi.pbweb.dto.archivioFile.EsitoInsertFiles;
import it.csi.pbandi.pbweb.dto.archivioFile.EsitoLeggiFile;
import it.csi.pbandi.pbweb.dto.archivioFile.FolderDTO;
import it.csi.pbandi.pbweb.dto.archivioFile.InizializzaArchivioFileDTO;
import it.csi.pbandi.pbweb.dto.archivioFile.UserSpaceDTO;
import it.csi.pbandi.pbweb.integration.dao.request.AssociaFilesRequest;
import it.csi.pbandi.pbweb.pbandisrv.dto.archivio.FileInfoDTO;


public interface ArchivioFileDAO {
	/*
	InizializzaArchivioFileDTO inizializzaArchivioFile(long idSoggetto, long idSoggettoBeneficiario, String codiceRuolo, long idUtente, HttpServletRequest req) throws InvalidParameterException, Exception;
	UserSpaceDTO spazioDisco(long idSoggettoBeneficiario) throws InvalidParameterException, Exception;
	ArrayList<FolderDTO> leggiRoot(long idSoggetto, long idSoggettoBeneficiario, String codiceRuolo) throws InvalidParameterException, Exception;
	ArrayList<FolderDTO> leggiFolder(long idFolder, long idSoggetto, String codiceRuolo) throws InvalidParameterException, Exception;
	ArrayList<EsitoInsertFiles> salvaFiles(MultipartFormDataInput multipartFormData) throws InvalidParameterException, Exception;
	EsitoLeggiFile leggiFile(long idDocumentoIndex) throws InvalidParameterException, Exception;
	EsitoLeggiFile leggiFileFirmato(long idDocumentoIndex) throws InvalidParameterException, Exception;
	EsitoLeggiFile leggiFileMarcato(long idDocumentoIndex) throws InvalidParameterException, Exception;
	Long creaFolder(String nomeFolder, long idFolderPadre, long idSoggettoBeneficiario, long idUtente) throws InvalidParameterException, Exception;
	Boolean rinominaFolder(String nomeFolder, long idFolder, long idUtente) throws InvalidParameterException, Exception;
	Boolean spostaFolder(long idFolder, long idFolderDestinazione, long idUtente) throws InvalidParameterException, Exception;
	Boolean cancellaFolder(long idFolder, long idUtente) throws InvalidParameterException, Exception;
	Boolean rinominaFile(String nomeFile, long idDocumentoIndex, long idUtente) throws InvalidParameterException, Exception;
	Boolean spostaFile(long idDocumentoIndex, long idFolder, long idFolderDestinazione, long idUtente) throws InvalidParameterException, Exception;	
	Boolean cancellaFile(long idDocumentoIndex, long idUtente) throws InvalidParameterException, Exception;
	FileInfoDTO infoFile(long idDocumentoIndex, long idFolder, long idUtente, HttpServletRequest request) throws InvalidParameterException, Exception;
	EsitoAssociaFilesDTO associaFiles(AssociaFilesRequest associaFilesRequest, Long idEntita, Long idUtente) throws InvalidParameterException, Exception;
	*/
}

