/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.filestorage.exceptions;

public class IncorrectUploadPathException extends Exception {
    /**
	 * Eccezione sollevata nel caso in cui il path della cartella di upload non sia valida
	 */
	private static final long serialVersionUID = 1L;

	public IncorrectUploadPathException(String errorMessage) {
        super(errorMessage);
    }
}
