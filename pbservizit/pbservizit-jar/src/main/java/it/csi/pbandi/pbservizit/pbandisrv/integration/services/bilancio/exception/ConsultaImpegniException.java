/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.pbandisrv.integration.services.bilancio.exception;

public class ConsultaImpegniException extends Exception {
	
	public ConsultaImpegniException(String message) {
		super(message);
	}

	public ConsultaImpegniException (String msg, Throwable ex){
		super(msg,ex);
	}

}
