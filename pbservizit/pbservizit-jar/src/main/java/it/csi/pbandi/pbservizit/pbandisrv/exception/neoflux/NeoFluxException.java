/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.pbandisrv.exception.neoflux;

import it.csi.csi.wrapper.UserException;

////{PROTECTED REGION ID(R1884214804) ENABLED START////}
/**
 * Inserire qui la documentazione dell'eccezione NeoFluxException.
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
public class NeoFluxException extends UserException

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
	public NeoFluxException(String msg, String nestedExcClassName,
			String nestedExcMessage, String nestedExcStackTrace) {
		super(msg, nestedExcClassName, nestedExcMessage, nestedExcStackTrace);
	}

	/**
	 * @generated
	 */
	public NeoFluxException(String msg, String nestedExcClassName,
			String nestedExcMessage) {
		super(msg, nestedExcClassName, nestedExcMessage);
	}

	/**
	 * @generated
	 */
	public NeoFluxException(String msg, Throwable nested) {
		super(msg, nested);
	}

	/**
	 * @generated
	 */
	public NeoFluxException(String msg) {
		super(msg);
	}
}
