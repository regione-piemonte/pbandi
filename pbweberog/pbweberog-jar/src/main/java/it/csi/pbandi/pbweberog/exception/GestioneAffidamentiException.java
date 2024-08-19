/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweberog.exception;

import it.csi.csi.wrapper.UserException;

public class GestioneAffidamentiException extends UserException {


	static final long serialVersionUID = 1;

	public GestioneAffidamentiException(String msg, String nestedExcClassName,
			String nestedExcMessage, String nestedExcStackTrace) {
		super(msg, nestedExcClassName, nestedExcMessage, nestedExcStackTrace);
	}

	public GestioneAffidamentiException(String msg, String nestedExcClassName,
			String nestedExcMessage) {
		super(msg, nestedExcClassName, nestedExcMessage);
	}

	public GestioneAffidamentiException(String msg, Throwable nested) {
		super(msg, nested);
	}

	public GestioneAffidamentiException(String msg) {
		super(msg);
	}
}
