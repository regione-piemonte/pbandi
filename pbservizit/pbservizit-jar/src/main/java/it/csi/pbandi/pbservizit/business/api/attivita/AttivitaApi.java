/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.business.api.attivita;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import it.csi.pbandi.pbservizit.exception.ErroreGestitoException;
import it.doqui.index.ecmengine.exception.InvalidParameterException;
import org.jboss.resteasy.annotations.jaxrs.QueryParam;

@Path("/attivita")
public interface AttivitaApi {

	@GET
	@Path("chiudiAttivita")
	@Produces(MediaType.APPLICATION_JSON)
	public Response chiudiAttivita(@Context HttpServletRequest req,
			@QueryParam("descBreveTask") String descBreveTask, 
			@QueryParam("idProgetto") Long idProgetto)
			throws InvalidParameterException, Exception;
}