/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebrce.exception;

import it.csi.csi.wrapper.UserException;

public class GestioneFornitoriException extends UserException {


	static final long serialVersionUID = 1;

	public GestioneFornitoriException(String msg, String nestedExcClassName,
			String nestedExcMessage, String nestedExcStackTrace) {
		super(msg, nestedExcClassName, nestedExcMessage, nestedExcStackTrace);
	}

	public GestioneFornitoriException(String msg, String nestedExcClassName,
			String nestedExcMessage) {
		super(msg, nestedExcClassName, nestedExcMessage);
	}

	public GestioneFornitoriException(String msg, Throwable nested) {
		super(msg, nested);
	}

	public GestioneFornitoriException(String msg) {
		super(msg);
	}
}
