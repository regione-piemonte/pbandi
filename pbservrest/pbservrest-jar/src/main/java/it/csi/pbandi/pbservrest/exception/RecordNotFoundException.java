/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservrest.exception;

public class RecordNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public RecordNotFoundException(String string) {
		super(string);
	}

}
