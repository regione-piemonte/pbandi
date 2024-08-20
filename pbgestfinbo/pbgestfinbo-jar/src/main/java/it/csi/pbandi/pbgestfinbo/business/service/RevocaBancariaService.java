/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.business.service;



import java.security.InvalidParameterException;
import java.util.ArrayList;

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

import it.csi.pbandi.pbgestfinbo.dto.profilazione.RevocaBancariaDTO;
import it.csi.pbandi.pbgestfinbo.dto.profilazione.StoricoRevocaDTO;
import it.csi.pbandi.pbgestfinbo.exception.DaoException;

@Path("/revoca")
public interface RevocaBancariaService {
	
	@GET
	@Path("/getRevocheBancaria")
	@Produces(MediaType.APPLICATION_JSON)
	Response getRevocheBancarie (
			@DefaultValue("0") @QueryParam("idUtente") Long idUtente, 
			@DefaultValue("0") @QueryParam("idProgetto") Long idProgetto,
			@DefaultValue("0") @QueryParam("idModalitaAgevolazione") int idModalitaAgev,
	        @Context HttpServletRequest req) throws DaoException;
	@GET
	@Path("/getRevocaBancaria")
	@Produces(MediaType.APPLICATION_JSON)
	RevocaBancariaDTO getRevocaBancaria (
			@DefaultValue("0") @QueryParam("idUtente") Long idUtente, 
			@DefaultValue("0") @QueryParam("idProgetto") Long idProgetto,
			@DefaultValue("0") @QueryParam("idModalitaAgevolazione") int idModalitaAgev,
	        @Context HttpServletRequest req) throws DaoException;
	@POST
	@Path("/salvaRevoca")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	Response salvaRevocaBancaria(
			RevocaBancariaDTO revocaBancaria,
			@DefaultValue("0") @QueryParam("idUtente") long idUtente,
			@DefaultValue("0") @QueryParam("idProgetto") long idProgetto,
			@DefaultValue("0") @QueryParam("idModalitaAgevolazione") int idModalitaAgev,
			@Context HttpServletRequest request) 
			throws InvalidParameterException, Exception;
	
	@GET
	@Path("/getStorico")
	@Produces(MediaType.APPLICATION_JSON)
	ArrayList<StoricoRevocaDTO> getStorico(
			@DefaultValue("0") @QueryParam("idUtente") Long idUtente, 
			@DefaultValue("0") @QueryParam("idProgetto") Long idProgetto,
			@DefaultValue("0") @QueryParam("idModalitaAgevolazione") int idModalitaAgev,
	        @Context HttpServletRequest req) throws DaoException;

}
