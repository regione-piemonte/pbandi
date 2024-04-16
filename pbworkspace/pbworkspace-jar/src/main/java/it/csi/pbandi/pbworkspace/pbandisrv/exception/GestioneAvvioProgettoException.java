/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbworkspace.pbandisrv.exception;

import it.csi.csi.wrapper.SystemException;
import it.csi.csi.wrapper.UserException;
import it.csi.csi.wrapper.UnrecoverableException;

public class GestioneAvvioProgettoException extends UserException

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
	public GestioneAvvioProgettoException(String msg,
			String nestedExcClassName, String nestedExcMessage,
			String nestedExcStackTrace) {
		super(msg, nestedExcClassName, nestedExcMessage, nestedExcStackTrace);
	}

	/**
	 * @generated
	 */
	public GestioneAvvioProgettoException(String msg,
			String nestedExcClassName, String nestedExcMessage) {
		super(msg, nestedExcClassName, nestedExcMessage);
	}

	/**
	 * @generated
	 */
	public GestioneAvvioProgettoException(String msg, Throwable nested) {
		super(msg, nested);
	}

	/**
	 * @generated
	 */
	public GestioneAvvioProgettoException(String msg) {
		super(msg);
	}
}
