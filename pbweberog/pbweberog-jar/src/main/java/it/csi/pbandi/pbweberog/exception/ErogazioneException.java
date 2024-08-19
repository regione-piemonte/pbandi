/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweberog.exception;

import it.csi.csi.wrapper.UserException;

public class ErogazioneException extends UserException{

	static final long serialVersionUID = 1;

	public ErogazioneException(String msg, String nestedExcClassName,
			String nestedExcMessage, String nestedExcStackTrace) {
		super(msg, nestedExcClassName, nestedExcMessage, nestedExcStackTrace);
	}

	public ErogazioneException(String msg, String nestedExcClassName,
			String nestedExcMessage) {
		super(msg, nestedExcClassName, nestedExcMessage);
	}

	/**
	 * @generated
	 */
	public ErogazioneException(String msg, Throwable nested) {
		super(msg, nested);
	}

	/**
	 * @generated
	 */
	public ErogazioneException(String msg) {
		super(msg);
	}
}
