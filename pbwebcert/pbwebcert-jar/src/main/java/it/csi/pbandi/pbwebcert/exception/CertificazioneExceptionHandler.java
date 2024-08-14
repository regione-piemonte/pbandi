/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebcert.exception;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;

public class CertificazioneExceptionHandler implements ExceptionMapper<CertificazioneException> {

	@Override
	public Response toResponse(CertificazioneException ce) {
		return Response.status(Status.BAD_REQUEST).entity(ce.getMessage()).build(); 
	} 

}
