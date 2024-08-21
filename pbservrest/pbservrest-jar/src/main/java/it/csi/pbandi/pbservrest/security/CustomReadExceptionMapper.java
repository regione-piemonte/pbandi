/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservrest.security;

import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;


@Provider
public class CustomReadExceptionMapper implements ExceptionMapper<NotFoundException>{

	@Override
	public Response toResponse(NotFoundException arg0) {
		Response r = Response.serverError().entity("errore di unmarshal:"+arg0.getClass()).build();
		return r;
	}

}
