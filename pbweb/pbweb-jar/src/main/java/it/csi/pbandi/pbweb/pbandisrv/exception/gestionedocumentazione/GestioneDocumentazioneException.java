/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweb.pbandisrv.exception.gestionedocumentazione;

import it.csi.csi.wrapper.SystemException;
import it.csi.csi.wrapper.UserException;
import it.csi.csi.wrapper.UnrecoverableException;

////{PROTECTED REGION ID(R-766763223) ENABLED START////}
/**
 * Inserire qui la documentazione dell'eccezione GestioneDocumentazioneException.
 * Consigli:
 * <ul>
 * <li> Dire se l'eccezione rappresenta una condizione di errore oppure 
 *      una casistica eccezionale applicativa
 * <li> Potrebbe essere meglio non dettagliare tanto la documentazione dell'
 *      eccezione quanto la documentazione delle clausole "throws" nei metodi
 *      che rilanciano effettivamente quest'eccezione
 * </ul>
 * @generated
 */
////{PROTECTED REGION END////}
public class GestioneDocumentazioneException extends UserException

{

	/**
	 * il serial version UID di una eccezione csi � sempre "1" perch� le
	 * eccezioni CSI non possono contenere features aggiuntive
	 * @generated
	 */
	static final long serialVersionUID = 1;

	/**
	 * @generated
	 */
	public GestioneDocumentazioneException(String msg,
			String nestedExcClassName, String nestedExcMessage,
			String nestedExcStackTrace) {
		super(msg, nestedExcClassName, nestedExcMessage, nestedExcStackTrace);
	}

	/**
	 * @generated
	 */
	public GestioneDocumentazioneException(String msg,
			String nestedExcClassName, String nestedExcMessage) {
		super(msg, nestedExcClassName, nestedExcMessage);
	}

	/**
	 * @generated
	 */
	public GestioneDocumentazioneException(String msg, Throwable nested) {
		super(msg, nested);
	}

	/**
	 * @generated
	 */
	public GestioneDocumentazioneException(String msg) {
		super(msg);
	}
}
