/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.business.api;

import java.security.InvalidParameterException;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/iterAutorizzativi")
public interface IterAutorizzativiApi {
	
	@GET
	@Path("avvia")
	@Produces(MediaType.APPLICATION_JSON)
	Response avviaIterAutorizzativo(
			@QueryParam("idTipoIter") Long idTipoIter,
			@QueryParam("idEntita") Long idEntita,
			@QueryParam("idTarget") Long idTarget,
			@QueryParam("idProgetto") Long idProgetto,
			@QueryParam("idUtente") Long idUtente);
}
