/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebcert.exception;

import it.csi.csi.wrapper.UserException;

public class CertificazioneException extends UserException{

	static final long serialVersionUID = 1;

	public CertificazioneException(String msg, String nestedExcClassName,
			String nestedExcMessage, String nestedExcStackTrace) {
		super(msg, nestedExcClassName, nestedExcMessage, nestedExcStackTrace);
	}

	public CertificazioneException(String msg, String nestedExcClassName,
			String nestedExcMessage) {
		super(msg, nestedExcClassName, nestedExcMessage);
	}

	public CertificazioneException(String msg, Throwable nested) {
		super(msg, nested);
	}

	public CertificazioneException(String msg) {
		super(msg);
	}
}
