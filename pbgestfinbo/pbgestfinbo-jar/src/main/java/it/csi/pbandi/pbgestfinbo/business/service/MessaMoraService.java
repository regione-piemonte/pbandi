/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.business.service;

import java.security.InvalidParameterException;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import it.csi.pbandi.pbgestfinbo.exception.DaoException;
import it.csi.pbandi.pbgestfinbo.integration.vo.MessaMoraVO;

@Path("/messaMora")
public interface MessaMoraService {

	@GET
	@Path("/getMessaMora")
	@Produces(MediaType.APPLICATION_JSON)
	Response getMessaMora(
			@DefaultValue("0") @QueryParam("idMessaMora") Long idMessaMora,
			@DefaultValue("0") @QueryParam("idModalitaAgevolazione") int idModalitaAgev,
	        @Context HttpServletRequest req) throws DaoException;
	
	
	@POST
	@Path("/insertMessaMora")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	Response insertMessaMora(
			MessaMoraVO messaMora,
			@DefaultValue("0") @QueryParam("idModalitaAgevolazione") int idModalitaAgev,
			@Context HttpServletRequest request) 
			throws InvalidParameterException, Exception;
	
	@POST
	@Path("/modificaMessaMora")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	Response modificaMessaMora(
			MessaMoraVO messaMoraVO,
			@DefaultValue("0") @QueryParam("idMessaMora") Long idMessaMora,
			@DefaultValue("0") @QueryParam("idModalitaAgevolazione") int idModalitaAgev,
			@Context HttpServletRequest request) 
					throws InvalidParameterException, Exception;
	
	
	
	@GET
	@Path("/getStorico")
	@Produces(MediaType.APPLICATION_JSON)
	Response getStorico(
			@DefaultValue("0") @QueryParam("idProgetto") Long idProgetto,
			@DefaultValue("0") @QueryParam("idModalitaAgevolazione") int idModalitaAgev,
		    @Context HttpServletRequest req) throws DaoException;
	
	
	@GET
	@Path("/getStoricoMessaMora")
	@Produces(MediaType.APPLICATION_JSON)
	Response getStoricoMessaMora(
			@DefaultValue("0") @QueryParam("idProgetto") Long idProgetto,
			@DefaultValue("0") @QueryParam("idModalitaAgevolazione") int idModalitaAgev,
			@Context HttpServletRequest req) throws DaoException;
	
	@GET
	@Path("/getListaAttivitaMessaMora")
	@Produces(MediaType.APPLICATION_JSON)
	Response getListaAttivitaMessaMora() throws DaoException;
	
	@GET
	@Path("/getListaAttivitaRecupero")
	@Produces(MediaType.APPLICATION_JSON)
	Response getListaAttivitaRecupero() throws DaoException;
	
	
	
	


}
