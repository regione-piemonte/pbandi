/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservwelfare.exception;

public class ErroreGestitoException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private String codice;

	public String getCodice() {
		return codice;
	}

	public void setCodice(String codice) {
		this.codice = codice;
	}

	public ErroreGestitoException(String message) {
		super(message);
	}

	public ErroreGestitoException(String message, String codice) {
		super(message);
		this.codice = codice;
	}

	public ErroreGestitoException(Throwable cause) {
		super(cause);
	}

	public ErroreGestitoException(String message, Throwable cause) {
		super(message, cause);
	}

	public ErroreGestitoException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}

