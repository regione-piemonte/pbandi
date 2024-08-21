/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.pbandisrv.exception;

import it.csi.csi.wrapper.UserException;

public class ManagerException extends UserException{

	static final long serialVersionUID = 1;

	/**
	 * @generated
	 */
	public ManagerException(String msg, String nestedExcClassName,
			String nestedExcMessage, String nestedExcStackTrace) {
		super(msg, nestedExcClassName, nestedExcMessage, nestedExcStackTrace);
	}

	/**
	 * @generated
	 */
	public ManagerException(String msg, String nestedExcClassName,
			String nestedExcMessage) {
		super(msg, nestedExcClassName, nestedExcMessage);
	}

	/**
	 * @generated
	 */
	public ManagerException(String msg, Throwable nested) {
		super(msg, nested);
	}

	/**
	 * @generated
	 */
	public ManagerException(String msg) {
		super(msg);
	}
	
}
