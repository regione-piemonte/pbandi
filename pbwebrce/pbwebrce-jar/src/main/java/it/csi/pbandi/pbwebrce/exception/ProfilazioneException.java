/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebrce.exception;

import it.csi.csi.wrapper.UserException;

public class ProfilazioneException extends UserException{
	static final long serialVersionUID = 1;

	public ProfilazioneException(String msg, String nestedExcClassName,
			String nestedExcMessage, String nestedExcStackTrace) {
		super(msg, nestedExcClassName, nestedExcMessage, nestedExcStackTrace);
	}


	public ProfilazioneException(String msg, String nestedExcClassName,
			String nestedExcMessage) {
		super(msg, nestedExcClassName, nestedExcMessage);
	}

	public ProfilazioneException(String msg, Throwable nested) {
		super(msg, nested);
	}

	public ProfilazioneException(String msg) {
		super(msg);
	}
}
