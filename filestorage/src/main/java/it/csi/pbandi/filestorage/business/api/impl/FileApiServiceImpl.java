/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

/**
* Utility per storicizzazione dei documenti su FileSystem
*
* @author  CSI Piemonte 1542
* @version 1.0
* @since   2021-02-12
*/
package it.csi.pbandi.filestorage.business.api.impl;

//-------------------------
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
//--------------------------
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

//-------------------------
import org.apache.log4j.Logger;

//-------------------------
import it.csi.pbandi.filestorage.business.api.FileApi;
import it.csi.pbandi.filestorage.dto.FileDetailsDTO;
import it.csi.pbandi.filestorage.exceptions.IncorrectUploadPathException;
import it.csi.pbandi.filestorage.util.Constants;
import it.csi.pbandi.filestorage.util.Utility;
//-------------------------

public class FileApiServiceImpl implements FileApi {
	private Logger LOG = Logger.getLogger(Constants.COMPONENT_NAME);
	private String UPLOAD_DIRECTORY;

	/**
	 * 
	 * @param UPLOAD_DIRECTORY
	 */
	private void setUploadDirectory(String strUploadDirectory) {
		this.UPLOAD_DIRECTORY = strUploadDirectory;
	}
	
//	public FileApiServiceImpl() {
//		super();
//		UPLOAD_DIRECTORY = Constants.UPLOAD_DIRECTORY;
//	}
	
	/**
	 * Questo costruttore viene utilizzato per impostare la directory root di upload (Es. /pbandi_storage)
	 * @param strUploadDirectory directory root di upload
	 * @throws IncorrectUploadPathException eccezione sollevata se non e' una directory formalmente valida
	 */
	public FileApiServiceImpl(String strUploadDirectory) throws IncorrectUploadPathException {
		super();
		if (strUploadDirectory == null || strUploadDirectory.isEmpty() || Files.notExists(Paths.get(strUploadDirectory))) {
			throw new IncorrectUploadPathException(Constants.MESSAGE_UPLOAD_DIRECTORY_NOT_VALID + ": '" + strUploadDirectory + "'");
		} else {
			this.setUploadDirectory(strUploadDirectory);
		}
	}

	/**
	 * Questo metodo effettua l'upload di un file sul fileSystem
	 * 
	 * @param file Oggetto File da inviare allo storage
	 * @param strTipoDocumento Tipo di documento da storicizzare. Corrisponde alla directory sul FileSystem
	 * @return Boolean Ritorna l'esito dell'upload
	 * @exception IOException Eccezione generica
	 */
	@Override
	public Boolean uploadFile(File file, String strTipoDocumento) throws IOException {
		String prf = "[FileApiServiceImpl::uploadFile]";		
		LOG.info(prf + " BEGIN");

		Boolean response = true;
    	File fileFolder = null;

    	LOG.info(prf + "---------- PARAMETRI PASSATI ---------");
    	LOG.info(prf + "> file: " + file);
    	LOG.info(prf + "> strTipoDocumento: " + strTipoDocumento);
    	LOG.info(prf + "--------------------------------------");    	
		// Verifico i parametri passati e, in caso di errore, riporto gli oppurtuni messaggi.
		Boolean booParametriOk = true;
    	if (file == null || file.getName().isEmpty()) {
			LOG.error(prf + " " + Constants.MESSAGE_PARAMETER_FILE_IS_NULL);
			booParametriOk = false;			
		} else {
			if (strTipoDocumento == null || strTipoDocumento.isEmpty()) {
				LOG.error(prf + " " + Constants.MESSAGE_PARAMETER_TIPO_DOCUMENTO_NOT_VALID);
				booParametriOk = false;	
			} else {
				fileFolder = new File(UPLOAD_DIRECTORY + File.separator + strTipoDocumento);
				if(!fileFolder.exists()) {
					LOG.error(prf + " " + Constants.MESSAGE_DIR_NOT_FOUND);
					booParametriOk = false;	
				}
			}			
		} 
		// Se i parametri sono ok, proseguo con l'UPLOAD
		if (!booParametriOk) {
			LOG.error(prf + " " + Constants.MESSAGE_PARAMETERS_NOT_VALID);
			response = false;	
		} else {
			String remoteFile = fileFolder+File.separator+file.getName();

			try {
// ---- OLD STYLE ----
//				long start = System.currentTimeMillis();
//				InputStream inputStream = new FileInputStream(file);
//				byte[] bytes = StreamUtils.copyToByteArray(inputStream);					
//	            if (writeFile(bytes, remoteFile)) {
//	                LOG.info(prf + Constants.MESSAGE_SAVE_OK + ": " +  remoteFile);
//	            } else {
//	            	LOG.error(prf + Constants.MESSAGE_SAVE_FAILED + ": " +  remoteFile);
//	            }
//				long end = System.currentTimeMillis();
//	            System.out.println("2 Copied in " + (end - start) + " ms");				
				
// ---- UTILIZZO NIO LIBRARY ----		 		
	            Path filePath = Paths.get(remoteFile);
	            Files.copy(new FileInputStream(file), filePath);
	            if (Files.exists(filePath)) {
	            	LOG.info(prf + Constants.MESSAGE_SAVE_OK + ": " +  remoteFile);
	            } else {
	            	LOG.error(prf + Constants.MESSAGE_SAVE_FAILED + ": " +  remoteFile);
	            }
			} catch (Exception e) {
				LOG.error(prf + Constants.MESSAGE_SAVE_FAILED );
				e.printStackTrace();
			}
		}
		LOG.info(prf + " END");
		return response;
	}

	/**
	 * Questo metodo effettua l'upload di un file sul fileSystem
	 * 
	 * @param inputStream Oggetto InputStream da inviare allo storage
	 * @param strFileName Nome del file da storicizzare
	 * @param strTipoDocumento Tipo di documento da storicizzare. Corrisponde alla directory sul FileSystem
	 * @return Boolean Ritorna l'esito dell'upload
	 * @exception IOException Eccezione generica
	 */
	@Override
	public Boolean uploadFile(InputStream inputStream, String strFileName, String strTipoDocumento) throws IOException {
		String prf = "[FileApiServiceImpl::uploadFile]";		
		LOG.info(prf + " BEGIN");

		Boolean response = true;
    	File fileFolder = null;
    	
    	LOG.info(prf + "---------- PARAMETRI PASSATI ---------");
    	LOG.info(prf + "> inputStream: " + inputStream);
    	LOG.info(prf + "> strFileName: " + strFileName);   
    	LOG.info(prf + "> strTipoDocumento: " + strTipoDocumento);
    	LOG.info(prf + "--------------------------------------");
		// Verifico i parametri passati e, in caso di errore, riporto gli oppurtuni messaggi.
		Boolean booParametriOk = true;
    	if (inputStream == null) {
			LOG.error(prf + " " + Constants.MESSAGE_PARAMETER_INPUT_STREAM_NOT_VALID);
			booParametriOk = false;			
		} else {
			if (strFileName == null || strFileName.isEmpty()) {
				LOG.error(prf + " " + Constants.MESSAGE_PARAMETER_NOME_FILE_NOT_VALID);
				booParametriOk = false;					
			} else {
				if (strTipoDocumento == null || strTipoDocumento.isEmpty()) {
					LOG.error(prf + " " + Constants.MESSAGE_PARAMETER_TIPO_DOCUMENTO_NOT_VALID);
					booParametriOk = false;	
				} else {
					fileFolder = new File(UPLOAD_DIRECTORY + File.separator + strTipoDocumento);
					if(!fileFolder.exists()) {
						LOG.error(prf + " " + Constants.MESSAGE_DIR_NOT_FOUND);
						booParametriOk = false;	
					}
				}
			}
		} 
		// Se i parametri sono ok, proseguo con l'UPLOAD
		if (!booParametriOk) {
			LOG.error(prf + " " + Constants.MESSAGE_PARAMETERS_NOT_VALID);
			response = false;	
		} else {
			String remoteFile = fileFolder+File.separator+strFileName;
			try {	
	            Path filePath = Paths.get(remoteFile);
	            Files.copy(inputStream, filePath);
	            if (Files.exists(filePath)) {
	            	LOG.info(prf + Constants.MESSAGE_SAVE_OK + ": " +  remoteFile);
	            } else {
	            	LOG.error(prf + Constants.MESSAGE_SAVE_FAILED + ": " +  remoteFile);
	            	response = false;
	            }
			} catch (Exception e) {
				LOG.error(prf + Constants.MESSAGE_SAVE_FAILED );
				e.printStackTrace();
			}
		}
		LOG.info(prf + " END");
		return response;
	}
	
	/**
	 * Questo metodo effettua il download di un file dal fileSystem
	 * 
	 * @param strFileName Nome del file da scaricare
	 * @param strTipoDocumento Tipo di documento da scaricare. Corrisponde alla directory sul FileSystem
	 * @return File Ritorna l'oggetto File trovato. Null se non e' stato possibile recuperarlo.
	 * @exception IOException Eccezione generica
	 */
	@Override
	public File downloadFile(String strFileName, String strTipoDocumento) throws IOException {
		String prf = "[FileApiServiceImpl::downloadFile]";		
		LOG.info(prf + " BEGIN");

    	LOG.info(prf + "---------- PARAMETRI PASSATI ---------");
    	LOG.info(prf + "> strFileName: " + strFileName);   
    	LOG.info(prf + "> strTipoDocumento: " + strTipoDocumento);
    	LOG.info(prf + "--------------------------------------");		
		File downloadedFile =  null;
		// Verifico i parametri passati e, in caso di errore, riporto gli oppurtuni messaggi.
		Boolean booParametriOk = true;
		if (strFileName == null  || strFileName.isEmpty()) {
			LOG.error(prf + " " + Constants.MESSAGE_PARAMETER_FILE_NAME_NOT_VALID);
			booParametriOk = false;			
		} else {
			if (strTipoDocumento == null || strTipoDocumento.isEmpty()) {
				LOG.error(prf + " " + Constants.MESSAGE_PARAMETER_TIPO_DOCUMENTO_NOT_VALID);
				booParametriOk = false;	
			}		
		} 
		
		// Se i parametri sono ok, proseguo con il DOWNLOAD
		if (!booParametriOk) {
			LOG.error(prf + " " + Constants.MESSAGE_PARAMETERS_NOT_VALID);
		} else {
			String fileToRetrieve = UPLOAD_DIRECTORY+File.separator+strTipoDocumento+File.separator+strFileName;
			downloadedFile = new File(fileToRetrieve);
			if (!downloadedFile.exists()) {
				LOG.info(prf + " " + Constants.MESSAGE_FILE_NOT_FOUND);
			}
		}
		
		LOG.info(prf + " END");
		return downloadedFile;
	}

	/**
	 * Questo metodo effettua la cancellazione di un file dal fileSystem
	 * 
	 * @param strFileName Nome del file da cancellare
	 * @param strTipoDocumento Tipo di documento da cancellare. Corrisponde alla directory sul FileSystem
	 * @return Boolean Ritorna l'esito della cancellazione
	 * @exception IOException Eccezione generica
	 */
	@Override
	public Boolean deleteFile(String strFileName, String strTipoDocumento) throws IOException {
		String prf = "[FileApiServiceImpl::deleteFile]";		
		LOG.info(prf + " BEGIN");

    	LOG.info(prf + "---------- PARAMETRI PASSATI ---------");
    	LOG.info(prf + "> strFileName: " + strFileName);   
    	LOG.info(prf + "> strTipoDocumento: " + strTipoDocumento);
    	LOG.info(prf + "--------------------------------------");
		Boolean booSuccess = true;
		// Verifico i parametri passati e, in caso di errore, riporto gli oppurtuni messaggi.		
		Boolean booParametriOk = true;
		if (strFileName == null  || strFileName.isEmpty()) {
			LOG.error(prf + " " + Constants.MESSAGE_PARAMETER_FILE_NAME_NOT_VALID);
			booParametriOk = false;			
		} else {
			if (strTipoDocumento == null || strTipoDocumento.isEmpty()) {
				LOG.error(prf + " " + Constants.MESSAGE_PARAMETER_TIPO_DOCUMENTO_NOT_VALID);
				booParametriOk = false;	
			}		
		} 
		
		// Se i parametri sono ok, proseguo
		if (!booParametriOk) {
			LOG.error(prf + " " + Constants.MESSAGE_PARAMETERS_NOT_VALID);
			booSuccess = false;
		} else {
			File fileToDelete = new File(UPLOAD_DIRECTORY+File.separator+strTipoDocumento+File.separator+strFileName);
			if (fileToDelete.exists()) {
				if (fileToDelete.delete()) {
					if (!fileToDelete.exists()) {
						LOG.info(prf + " " + Constants.MESSAGE_DELETE_OK);
					} else {
						booSuccess = false;
						LOG.info(prf + " " + Constants.MESSAGE_DELETE_FAILED + " Il file risulta ancora esistente!");	
					}
				} else {
					booSuccess = false;
					LOG.info(prf + " " + Constants.MESSAGE_DELETE_FAILED);
				}
			} else {
				booSuccess = false;
				LOG.info(prf + " " + Constants.MESSAGE_FILE_NOT_FOUND);
			}
		}
        
		LOG.info(prf + " END");
		return booSuccess;
	}

	/**
	 * Questo metodo verifica se un file esiste in una cartella
	 * 
	 * @param strFileName nome del file da verificare
	 * @param strTipoDocumento Tipo di documento (directory) da creare. Corrisponde alla directory sul FileSystem
	 * @return Boolean Ritorna l'esito dell'operazione
	 * @exception IOException Eccezione generica
	 */		
	@Override
	public Boolean fileExists(String strFileName, String strTipoDocumento) throws IOException {
		String prf = "[FileApiServiceImpl::fileExists]";		
		LOG.info(prf + " BEGIN");

    	LOG.info(prf + "---------- PARAMETRI PASSATI ---------");
    	LOG.info(prf + "> strFileName: " + strFileName);   
    	LOG.info(prf + "> strTipoDocumento: " + strTipoDocumento);
    	LOG.info(prf + "--------------------------------------");		
		Boolean success = true;
		// Verifico i parametri passati e, in caso di errore, riporto gli oppurtuni messaggi.
		Boolean booParametriOk = true;
		if (strFileName == null  || strFileName.isEmpty()) {
			LOG.error(prf + " " + Constants.MESSAGE_PARAMETER_FILE_NAME_NOT_VALID);
			booParametriOk = false;			
		} else {
			if (strTipoDocumento == null || strTipoDocumento.isEmpty()) {
				LOG.error(prf + " " + Constants.MESSAGE_PARAMETER_TIPO_DOCUMENTO_NOT_VALID);
				booParametriOk = false;	
			}		
		} 
		
		// Se i parametri sono ok, proseguo
		if (!booParametriOk) {
			LOG.error(prf + " " + Constants.MESSAGE_PARAMETERS_NOT_VALID);
			booParametriOk = false;
		} else {
			String fileToCheck = UPLOAD_DIRECTORY+File.separator+strTipoDocumento+File.separator+strFileName;
			if (!new File(fileToCheck).exists()) {
				LOG.info(prf + " " + Constants.MESSAGE_FILE_NOT_FOUND);
				success = false;
			}
		}
		
		LOG.info(prf + " END");
		return success;
	}
	
	/**
	 * Questo metodo recupera informazioni di dettaglio di un file 
	 * 
	 * @param strFileName nome del file da verificare
	 * @param strTipoDocumento Tipo di documento (directory) da creare. Corrisponde alla directory sul FileSystem
	 * @return FileDetailsDTO Informazioni di dettaglio del file, null nel caso in cui il file non venga trovato
	 * @exception IOException Eccezione generica
	 */		
	@Override
	public FileDetailsDTO getFileDetails(String strFileName, String strTipoDocumento) throws IOException {
		String prf = "[FileApiServiceImpl::getFileDetails]";		
		LOG.info(prf + " BEGIN");

    	LOG.info(prf + "---------- PARAMETRI PASSATI ---------");
    	LOG.info(prf + "> strFileName: " + strFileName);   
    	LOG.info(prf + "> strTipoDocumento: " + strTipoDocumento);
    	LOG.info(prf + "--------------------------------------");		
		FileDetailsDTO fileDetailsDto = null;
		// Verifico i parametri passati e, in caso di errore, riporto gli oppurtuni messaggi.
		Boolean booParametriOk = true;
		if (strFileName == null  || strFileName.isEmpty()) {
			LOG.error(prf + " " + Constants.MESSAGE_PARAMETER_FILE_NAME_NOT_VALID);
			booParametriOk = false;			
		} else {
			if (strTipoDocumento == null || strTipoDocumento.isEmpty()) {
				LOG.error(prf + " " + Constants.MESSAGE_PARAMETER_TIPO_DOCUMENTO_NOT_VALID);
				booParametriOk = false;	
			}		
		} 
		
		// Se i parametri sono ok, proseguo 
		if (!booParametriOk) {
			LOG.error(prf + " " + Constants.MESSAGE_PARAMETERS_NOT_VALID);
			booParametriOk = false;
		} else {
			fileDetailsDto = new FileDetailsDTO();

			Path filePath = Paths.get(UPLOAD_DIRECTORY+File.separator+strTipoDocumento+File.separator+strFileName);
			if (Files.notExists(filePath)) {
				LOG.info(prf + " " + Constants.MESSAGE_FILE_NOT_FOUND);
			} else {
				try {
					BasicFileAttributes attr = Files.readAttributes(filePath, BasicFileAttributes.class);
					fileDetailsDto.setFileName(filePath.getFileName());
					
					DateFormat df=new SimpleDateFormat("dd/MM/yyyy mm:ss");
					fileDetailsDto.setCreationDate(df.parse(df.format(attr.creationTime().toMillis())));
					fileDetailsDto.setLastModified(df.parse(df.format(attr.lastModifiedTime().toMillis())));
					fileDetailsDto.setLastAccess(df.parse(df.format(attr.lastAccessTime().toMillis())));
					fileDetailsDto.setSize(attr.size());
				} catch (Exception e) {
					LOG.error(prf + Constants.MESSAGE_READ_ATTRIBUTE_FILE_FAILED);
					fileDetailsDto = null;
					e.printStackTrace();
				}
			}
		}
		
		LOG.info(prf + " END");
		return fileDetailsDto;
	}
	
	/**
	 * Questo metodo elenca i file contenuti in una directory
	 * 
	 * @param strTipoDocumento Tipo di documento da scaricare. Corrisponde alla directory sul FileSystem
	 * @return String[] Ritorna l'elenco dei nomi dei file della directory specificata.
	 * @exception IOException Eccezione generica
	 */
	@Override
	public String[] listDir(String strTipoDocumento) throws IOException {
		String prf = "[FileApiServiceImpl::listDir]";		
		LOG.info(prf + " BEGIN");

    	LOG.info(prf + "---------- PARAMETRI PASSATI ---------");
    	LOG.info(prf + "> strTipoDocumento: " + strTipoDocumento);
    	LOG.info(prf + "--------------------------------------");		
		String[] listaFiles = null;
		
		// Verifico i parametri passati e, in caso di errore, riporto gli oppurtuni messaggi.	
		if (strTipoDocumento == null  || strTipoDocumento.isEmpty()) {
			LOG.error(prf + " " + Constants.MESSAGE_PARAMETER_TIPO_DOCUMENTO_NOT_VALID);
		} else {
			// Se la cartella e' formalmente valida proseguo
			File dir = new File(UPLOAD_DIRECTORY + File.separator + strTipoDocumento);
			if (dir.exists()) {
				listaFiles = dir.list();
			}
		}
		
		LOG.info(prf + " END");
		return listaFiles;
	}
	
	/**
	 * Questo metodo effettua la cancellazione di una directory vuota dal fileSystem
	 * 
	 * @param strTipoDocumento Tipo di documento (directory) da cancellare. Corrisponde alla directory sul FileSystem
	 * @return Boolean Ritorna l'esito dell'operazione
	 * @exception IOException Eccezione generica
	 */	
	@Override
	public Boolean deleteDirectory(String strTipoDocumento) throws IOException {
		String prf = "[FileApiServiceImpl::deleteDirectory]";		
		LOG.info(prf + " BEGIN");
		
    	LOG.info(prf + "---------- PARAMETRI PASSATI ---------");
    	LOG.info(prf + "> strTipoDocumento: " + strTipoDocumento);
    	LOG.info(prf + "--------------------------------------");			
		Boolean success = true;
		// Verifico i parametri passati e, in caso di errore, riporto gli oppurtuni messaggi.
		if (strTipoDocumento == null  || strTipoDocumento.isEmpty()) {
			LOG.error(prf + " " + Constants.MESSAGE_PARAMETER_TIPO_DOCUMENTO_NOT_VALID);
			success = false;
		} else {
			String strDir = UPLOAD_DIRECTORY+File.separator+strTipoDocumento;
			// NOTA --- La cartella deve essere vuota !
			Files.deleteIfExists(Paths.get(strDir));
			if (!new File(strDir).exists()) {
				LOG.info(Constants.MESSAGE_CREATE_DIR_OK);
			} else {
				LOG.error(Constants.MESSAGE_CREATE_DIR_FAILED);
				success = false;
			}
		}
		return success;
	}

	/**
	 * Questo metodo effettua la creazione di una directory vuota sul fileSystem
	 * 
	 * @param strTipoDocumento Tipo di documento (directory) da creare. Corrisponde alla directory sul FileSystem
	 * @return Boolean Ritorna l'esito dell'operazione
	 * @exception IOException Eccezione generica
	 */	
	@Override
	public Boolean createDirectory(String strTipoDocumento) throws IOException {
		String prf = "[FileApiServiceImpl::createDirectory]";		
		LOG.info(prf + " BEGIN");
		
    	LOG.info(prf + "---------- PARAMETRI PASSATI ---------");
    	LOG.info(prf + "> strTipoDocumento: " + strTipoDocumento);
    	LOG.info(prf + "--------------------------------------");			
		Boolean success = true;
		// Verifico i parametri passati e, in caso di errore, riporto gli oppurtuni messaggi.
		if (strTipoDocumento == null  || strTipoDocumento.isEmpty()) {
			LOG.error(prf + " " + Constants.MESSAGE_PARAMETER_TIPO_DOCUMENTO_NOT_VALID);
			success = false;
		} else {
			String strDir = UPLOAD_DIRECTORY+File.separator+strTipoDocumento;
			Files.createDirectory(Paths.get(strDir));
			if (new File(strDir).exists()) {
				LOG.info(Constants.MESSAGE_CREATE_DIR_OK);
			} else {
				LOG.error(Constants.MESSAGE_CREATE_DIR_FAILED);
				success = false;
			}
		}
		return success;
	}

	/**
	 * Questo metodo effettua ricerca di una cartella "Tipo di Documento". Se il secondo parametro vale true, crea la cartella
	 * se non dovesse esiste
	 * 
	 * @param strTipoDocumento Tipo di documento (directory) da creare. Corrisponde alla directory sul FileSystem
	 * @param booCreate true se si vuole creare la directory se non esiste false o null in caso contrario
	 * @return Boolean Ritorna l'esito dell'operazione
	 * @exception IOException Eccezione generica
	 */		
	@Override
	public Boolean dirExists(String strTipoDocumento, Boolean booCreate) throws IOException {
		String prf = "[FileApiServiceImpl::dirExists]";		
		LOG.info(prf + " BEGIN");

    	LOG.info(prf + "---------- PARAMETRI PASSATI ---------");
    	LOG.info(prf + "> strTipoDocumento: " + strTipoDocumento);
    	LOG.info(prf + "> booCreate: " + booCreate);
    	LOG.info(prf + "--------------------------------------");	
    	
		Boolean success = true;
		
		// Verifico i parametri passati e, in caso di errore, riporto gli oppurtuni messaggi.	
		if (strTipoDocumento == null  || strTipoDocumento.isEmpty()) {
			LOG.error(prf + " " + Constants.MESSAGE_PARAMETER_TIPO_DOCUMENTO_NOT_VALID);
			success = false;
		} else {
			// Se la cartella e' formalmente valida proseguo
			File dir = new File(UPLOAD_DIRECTORY + File.separator + strTipoDocumento);
			if (!dir.exists()) {
				if (booCreate != null && booCreate == true) {
					if (!this.createDirectory(strTipoDocumento)) {
						success = false;
					}
				} 
			}
		}
		
		LOG.info(prf + " END");
		return success;
	}
		
	// =========================================================
	// Metodi di servizio
	// =========================================================	
//	/**
//	 * Scrivo il file
//	 * @param content
//	 * @param filename
//	 * @throws IOException
//	 */
//	private Boolean writeFile(byte[] content, String filename) {
//		String prf = "[FileApiServiceImpl::writeFile]";		
//		LOG.info(prf + " BEGIN");	
//		
//		Boolean success = true;
//		try {
//			File file = new File(filename);
//			if (!file.exists()) {
//				file.createNewFile();
//			}
//			FileOutputStream fop = new FileOutputStream(file);
//			fop.write(content);
//			fop.flush();
//			fop.close();
//		} catch (Exception e) {
//			LOG.error(prf + Constants.MESSAGE_WRITE_FAILED);
//			success = false;
//			e.printStackTrace();
//		}
//		if (!new File(filename).exists()) {
//			LOG.error(prf + Constants.MESSAGE_VERIFY_FAILED);
//			success = false;			
//		}		
//		
//		LOG.info(prf + "  END");
//		return success;		
//	}

	/**
	 * Questo metodo recupera il path della direcory di upload attiva
	 * 
	 * @return Path Ritorna il path della directory attiva
	 */	
	public Path getUploadDirecory() {
		return Paths.get(UPLOAD_DIRECTORY); 
	}

	/**
	 * Questo metodo calcola la signature MD5 di un file specifico
	 * 
	 * @param strFileName nome del file da verificare
	 * @param strTipoDocumento Tipo di documento (directory) da creare. Corrisponde alla directory sul FileSystem
	 * @return String Ritorna l'MD5 calcolato per il file indicato
	 * @exception IOException Eccezione generica
	 */		
	@Override
	public String getChecksumMD5(String strFileName, String strTipoDocumento) throws IOException {
		String prf = "[FileApiServiceImpl::getChecksumMD5]";		
		LOG.info(prf + " BEGIN");

    	LOG.info(prf + "---------- PARAMETRI PASSATI ---------");
    	LOG.info(prf + "> strFileName: " + strFileName);
    	LOG.info(prf + "> strTipoDocumento: " + strTipoDocumento);
    	LOG.info(prf + "--------------------------------------");	
		String md5 = null;
		// Verifico i parametri passati e, in caso di errore, riporto gli oppurtuni messaggi.
		Boolean booParametriOk = true;
		if (strFileName == null  || strFileName.isEmpty()) {
			LOG.error(prf + " " + Constants.MESSAGE_PARAMETER_FILE_NAME_NOT_VALID);
			booParametriOk = false;			
		} else {
			if (strTipoDocumento == null || strTipoDocumento.isEmpty()) {
				LOG.error(prf + " " + Constants.MESSAGE_PARAMETER_TIPO_DOCUMENTO_NOT_VALID);
				booParametriOk = false;	
			}		
		} 
		
		// Se i parametri sono ok, proseguo con il DOWNLOAD
		if (!booParametriOk) {
			LOG.error(prf + " " + Constants.MESSAGE_PARAMETERS_NOT_VALID);
			booParametriOk = false;
		} else {
			String fileToCheck = UPLOAD_DIRECTORY+File.separator+strTipoDocumento+File.separator+strFileName;
			if (!new File(fileToCheck).exists()) {
				LOG.info(prf + " " + Constants.MESSAGE_FILE_NOT_FOUND);
			} else {
				md5 = Utility.getMd5(new FileInputStream(fileToCheck));
			}
		}
		
		LOG.info(prf + " END");
		return md5;
	}
	

}
