/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.business.service;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/ambitoErogazione")
public interface RicercaProposteErogazioneService {
	@GET
	@Path("/getProposteErogazione")
	@Produces(MediaType.APPLICATION_JSON)
	Response getProposteErogazione(
			@DefaultValue("-1") @QueryParam("progrBandoLinea") Long progrBandoLinea,
			@DefaultValue("-1") @QueryParam("agevolazione") Long idModalitaAgevolazione,
			@DefaultValue("-1") @QueryParam("idSoggetto") Long idSoggetto,
			@DefaultValue("-1") @QueryParam("idProgetto") Long idProgetto,
			@QueryParam("contrPreErogazione") String contrPreErogazione,
			@Context HttpServletRequest req) throws Exception;

	@GET
	@Path("/getSuggestion")
	@Produces(MediaType.APPLICATION_JSON)
	Response getSuggestion(
			@DefaultValue("000") @QueryParam("value") String value,
			@DefaultValue("1") @QueryParam("id") String id,
			@Context HttpServletRequest req) throws Exception;

}
