/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebcert.exception;

import it.csi.csi.wrapper.UserException;

public class UtenteException extends UserException{

	static final long serialVersionUID = 1;
	
	public UtenteException(String msg, String nestedExcClassName,
			String nestedExcMessage, String nestedExcStackTrace) {
		super(msg, nestedExcClassName, nestedExcMessage, nestedExcStackTrace);
	}

	public UtenteException(String msg, String nestedExcClassName,
			String nestedExcMessage) {
		super(msg, nestedExcClassName, nestedExcMessage);
	}

	public UtenteException(String msg, Throwable nested) {
		super(msg, nested);
	}

	public UtenteException(String msg) {
		super(msg);
	}
}
