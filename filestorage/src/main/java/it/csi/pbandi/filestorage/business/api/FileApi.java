/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.filestorage.business.api;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;

import it.csi.pbandi.filestorage.dto.FileDetailsDTO;

public interface FileApi {

	/**
	 * Questo metodo effettua l'upload di un file sul fileSystem
	 * 
	 * @param file Oggetto InputStream da inviare allo storage
	 * @param strTipoDocumento Tipo di documento da storicizzare. Corrisponde alla directory sul FileSystem
	 * @return Boolean Ritorna l'esito dell'upload
	 * @exception IOException Eccezione generica
	 */
	Boolean uploadFile(File file, String strTipoDocumento) throws IOException;

	/**
	 * Questo metodo effettua l'upload di un file sul fileSystem
	 * 
	 * @param inputStream Oggetto File da inviare allo storage
	 * @param strFileName Nome del file da storicizzare 
	 * @param strTipoDocumento Tipo di documento da storicizzare. Corrisponde alla directory sul FileSystem
	 * @return Boolean Ritorna l'esito dell'upload
	 * @exception IOException Eccezione generica
	 */
	Boolean uploadFile(InputStream inputStream, String strFileName, String strTipoDocumento) throws IOException;

	/**
	 * Questo metodo effettua il download di un file dal fileSystem
	 * 
	 * @param strFileName Nome del file da scaricare
	 * @param strTipoDocumento Tipo di documento da scaricare. Corrisponde alla directory sul FileSystem
	 * @return File Ritorna l'oggetto File trovato. Null se non e' stato possibile recuperarlo.
	 * @exception IOException Eccezione generica
	 */
	File downloadFile(String strFileName, String strTipoDocumento) throws IOException;

	/**
	 * Questo metodo effettua la cancellazione di un file dal fileSystem
	 * 
	 * @param strFileName Nome del file da cancellare
	 * @param strTipoDocumento Tipo di documento da cancellare. Corrisponde alla directory sul FileSystem
	 * @return Boolean Ritorna l'esito della cancellazione
	 * @exception IOException Eccezione generica
	 */
	Boolean deleteFile(String strFileName, String strTipoDocumento) throws IOException;

	/**
	 * Questo metodo verifica se un file esiste in una cartella
	 * 
	 * @param strFileName nome del file da verificare
	 * @param strTipoDocumento Tipo di documento (directory) da creare. Corrisponde alla directory sul FileSystem
	 * @return Boolean Ritorna l'esito dell'operazione
	 * @exception IOException Eccezione generica
	 */
	Boolean fileExists(String strFileName, String strTipoDocumento) throws IOException;

	/**
	 * Questo metodo elenca i file contenuti in una directory
	 * 
	 * @param strTipoDocumento Tipo di documento da scaricare. Corrisponde alla directory sul FileSystem
	 * @return String[] Ritorna l'elenco dei nomi dei file della directory specificata.
	 * @exception IOException Eccezione generica
	 */
	String[] listDir(String strTipoDocumento) throws IOException;

	/**
	 * Questo metodo effettua la cancellazione di una directory vuota dal fileSystem
	 * 
	 * @param strTipoDocumento Tipo di documento (directory) da cancellare. Corrisponde alla directory sul FileSystem
	 * @return Boolean Ritorna l'esito dell'operazione
	 * @exception IOException Eccezione generica
	 */
	Boolean deleteDirectory(String strTipoDocumento) throws IOException;

	/**
	 * Questo metodo effettua la creazione di una directory vuota sul fileSystem
	 * 
	 * @param strTipoDocumento Tipo di documento (directory) da creare. Corrisponde alla directory sul FileSystem
	 * @return Boolean Ritorna l'esito dell'operazione
	 * @exception IOException Eccezione generica
	 */
	Boolean createDirectory(String strTipoDocumento) throws IOException;

	/**
	 * Questo metodo effettua ricerca di una cartella "Tipo di Documento". Se il secondo parametro vale true, crea la cartella
	 * se non dovesse esiste
	 * 
	 * @param strTipoDocumento Tipo di documento (directory) da creare. Corrisponde alla directory sul FileSystem
	 * @param booCreate true se si vuole creare la directory se non esiste false o null in caso contrario
	 * @return Boolean Ritorna l'esito dell'operazione
	 * @exception IOException Eccezione generica
	 */
	Boolean dirExists(String strTipoDocumento, Boolean booCreate) throws IOException;

	/**
	 * Questo metodo recupera il path della direcory di upload attiva
	 * 
	 * @return Path Ritorna il path della directory attiva
	 */
	Path getUploadDirecory();

	/**
	 * Questo metodo calcola la signature MD5 di un file specifico
	 * 
	 * @param strFileName nome del file da verificare
	 * @param strTipoDocumento Tipo di documento (directory) da creare. Corrisponde alla directory sul FileSystem
	 * @return String Ritorna l'MD5 calcolato per il file indicato
	 * @exception IOException Eccezione generica
	 */
	String getChecksumMD5(String strFileName, String strTipoDocumento) throws IOException;

	/**
	 * Questo metodo recupera informazioni di dettaglio di un file 
	 * 
	 * @param strFileName nome del file da verificare
	 * @param strTipoDocumento Tipo di documento (directory) da creare. Corrisponde alla directory sul FileSystem
	 * @return String Ritorna l'MD5 calcolato per il file indicato
	 * @exception IOException Eccezione generica
	 */
	FileDetailsDTO getFileDetails(String strFileName, String strTipoDocumento) throws IOException;

}