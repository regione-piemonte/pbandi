/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.filestorage.util;

public class Constants {
//  Costanti di configurazione	
	public final static String COMPONENT_NAME = "filestorage";
	
//	MESSAGE CODES
	public static final String MESSAGE_SAVE_OK = "Il file e' stato correttamente salvato ";
	public static final String MESSAGE_SAVE_FAILED = "Si sono verificati problemi durante il salvataggio del file ";
	public static final String MESSAGE_WRITE_FAILED = "Si sono verificati problemi durante il salvataggio del file ";
	public static final String MESSAGE_VERIFY_FAILED = "Il file non risulta essere stato scritto ";	
	public static final String MESSAGE_VERIFY_HASHCODE_FAILED = "L'HashCode del file salavato non corrisponde a quello da salvare ";	
	public static final String MESSAGE_DELETE_OK = "Il file e' stato cancellato correttamente ";
	public static final String MESSAGE_DELETE_FAILED = "La cancellazione del file non e' andata a buon fine ";
	public static final String MESSAGE_CREATE_DIR_OK = "La cartella e' stata creata correttamente ";
	public static final String MESSAGE_CREATE_DIR_FAILED = "La creazione della cartella non e' andata a buon fine ";
	public static final String MESSAGE_DIR_NOT_FOUND = "La cartella non esiste ";
	public static final String MESSAGE_DIR_EXISTS = "La cartella esiste gia' sul fileSystem ";	
	public static final String MESSAGE_FILE_NOT_FOUND = "File non trovato ";
	public static final String MESSAGE_FILE_EXISTS = "File gia' presente sul fyleSystem ";
	public static final String MESSAGE_READ_ATTRIBUTE_FILE_FAILED = "Ci sono stati problemi nel recupero degli attributi del file ";
// COSTANTI PER FTP
	public static final String FTP_SERVER = "127.0.0.1";
	public static final int FTP_SERVER_PORT = 21;
	public static final String FTP_USR = "********";
	public static final String FTP_PWD = "********";
	public static final int FTP_LIMIT_UPLOAD = 20000000; //Byte
	
	public static final String MESSAGE_FTP_CONNECTION_FAILED = "Impossibile connettersi al server FTP ";
	public static final String MESSAGE_FTP_LOGIN_FAILED = "Impossibile effettuare il login al server FTP ";
	public static final String MESSAGE_FTP_LOGOUT_FAILED = "Impossibile effettuare il logut dal server FTP ";
	public static final String MESSAGE_FTP_DELETE_FAILED = "E' fallita la cancellazione del file ";
	public static final String MESSAGE_FTP_DELETE_SUCCESS = "La cancellazione del file e' avvenuta con successo ";		
	public static final String MESSAGE_FTP_STORE_FAILED = "L'upload del file e' FALLITO ";
	public static final String MESSAGE_FTP_STORE_SUCCESS = "L'upload del file e' avvenuto con successo ";
	
	
// COSTANTI PER  PARAMETRI
	public static final String MESSAGE_UPLOAD_DIRECTORY_NOT_VALID = "La directory root di upload passata non e' valida ";
	public static final String MESSAGE_PARAMETERS_NOT_VALID = "I parametri passati non sono validi ";
	public static final String MESSAGE_PARAMETER_FILE_IS_NULL = "Il file passato risulta null ";
	public static final String MESSAGE_PARAMETER_FILE_NAME_NOT_VALID = "Il nome del file passato risulta non valido o null ";	
	public static final String MESSAGE_PARAMETER_TIPO_DOCUMENTO_NOT_VALID = "Il tipo di documento passato risulta non valido o null ";
	public static final String MESSAGE_PARAMETER_PATH_DOWNLOAD_NOT_VALID = "Il path di download risulta non valido o null ";	
	public static final String MESSAGE_PARAMETER_TIPO_DOCUMENTO_NOT_FOUND = "Il parametro tipo_documento non valorizzato ";
	public static final String MESSAGE_PARAMETER_FILE_NOT_FOUND = "Il parametro file non valorizzato ";
	public static final String MESSAGE_PARAMETER_NOME_FILE_NOT_VALID = "Il parametro nome file non valorizzato ";	
	public static final String MESSAGE_PARAMETER_INPUT_STREAM_NOT_VALID = "Il parametro InputStream non valorizzato ";	
}


