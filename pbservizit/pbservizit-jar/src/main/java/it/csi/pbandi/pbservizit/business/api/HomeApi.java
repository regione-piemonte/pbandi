/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.business.api;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import it.csi.pbandi.pbservizit.exception.ErroreGestitoException;


@Path("/home")
public interface HomeApi {

	@GET
	@Path("operazioni")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getOperazioni(@Context HttpServletRequest req) throws ErroreGestitoException;
	
}
