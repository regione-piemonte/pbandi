/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweb.exception;

/**
 * @generated
 */
public class DaoException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4899174376731011474L;
	/**
	 */
	protected Throwable throwable;

	/**
	 * Method 'DaoException'
	 * 
	 * @param message
	 */
	public DaoException(String message) {
		super(message);
	}

	/**
	 * Method 'DaoException'
	 * 
	 * @param message
	 * @param throwable
	 */
	public DaoException(String message, Throwable throwable) {
		super(message);
		this.throwable = throwable;
	}

	/**
	 * Method 'getCause'
	 * 
	 * @return Throwable
	 */
	public Throwable getCause() {
		return throwable;
	}

}
