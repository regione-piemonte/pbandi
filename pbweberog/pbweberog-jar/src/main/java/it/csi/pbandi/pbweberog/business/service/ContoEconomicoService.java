/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweberog.business.service;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import it.csi.pbandi.pbservizit.exception.UtenteException;
import it.csi.pbandi.pbweberog.dto.contoeconomico.ContoEconomicoItem;

@Path("/contoEconomico")
@Api(value = "contoEconomico")
public interface ContoEconomicoService {
	@GET
	@Path("/") 
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Finds dati conto economico per visualizzazione", response=ContoEconomicoItem.class, responseContainer = "List")
	Response getDatiContoEconomico( @Context HttpServletRequest req, @QueryParam("idProgetto") Long idProgetto) throws UtenteException, Exception;

}
