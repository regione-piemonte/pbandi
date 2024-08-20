/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.business.service;


import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import it.csi.pbandi.pbgestfinbo.exception.DaoException;

@Path("/suggestion")
public interface SuggestionService {

	@GET
	@Path("/getIdSoggetto") 
	@Produces(MediaType.APPLICATION_JSON)
	Response suggerimentoIdSoggetto(
			@QueryParam("idSoggetto") String idSoggetto,
			@Context HttpServletRequest req) throws DaoException;


	@GET
	@Path("/getCodiceFiscale") 
	@Produces(MediaType.APPLICATION_JSON)
	Response suggerimentoCodiceFiscale(
			@QueryParam("codiceFiscale") String codiceFiscale,
			@DefaultValue("0") @QueryParam("idSoggetto") Long idSoggetto,
			@Context HttpServletRequest req) throws DaoException;


	@GET
	@Path("/getPartitaIva") 
	@Produces(MediaType.APPLICATION_JSON)
	Response suggerimentoPartitaIva(
			@QueryParam("partitaIva") String partitaIva,
			@DefaultValue("0") @QueryParam("idSoggetto") Long idSoggetto,
			@Context HttpServletRequest req) throws DaoException;

	@GET
	@Path("/getDenominazione") 
	@Produces(MediaType.APPLICATION_JSON)
	Response suggerimentoDenominazione(
			@QueryParam("denominazione") String denominazione,
			@DefaultValue("0") @QueryParam("idSoggetto") Long idSoggetto,
			@Context HttpServletRequest req) throws DaoException;

	@GET
	@Path("/getIdDomanda") 
	@Produces(MediaType.APPLICATION_JSON)
	Response suggerimentoIdDomanda(
			@QueryParam("numeroDomanda") String numeroDomanda,
			@DefaultValue("0") @QueryParam("idSoggetto") Long idSoggetto,
			@Context HttpServletRequest req) throws DaoException;


	@GET
	@Path("/getIdProgetto") 
	@Produces(MediaType.APPLICATION_JSON)
	Response suggerimentoIdProgetto(
			@QueryParam("codProgetto") String codProgetto,
			@DefaultValue("0") @QueryParam("idSoggetto") Long idSoggetto,
			@Context HttpServletRequest req) throws DaoException;

	@GET
	@Path("/getNome") 
	@Produces(MediaType.APPLICATION_JSON)
	Response getNome(
			@QueryParam("nome") String nome,
			@DefaultValue("0") @QueryParam("idSoggetto") Long idSoggetto,
			@Context HttpServletRequest req) throws DaoException;

	@GET
	@Path("/getCognome") 
	@Produces(MediaType.APPLICATION_JSON)
	Response getCognome(
			@QueryParam("cognome") String cognome,
			@DefaultValue("0") @QueryParam("idSoggetto") Long idSoggetto,
			@Context HttpServletRequest req) throws DaoException;

	@GET
	@Path("/getAutofill") 
	@Produces(MediaType.APPLICATION_JSON)
	Response getAutofill(
			@QueryParam("idSoggetto") Long idSoggetto,
			@QueryParam("tipoSoggetto") String tipoSoggetto,
			@DefaultValue("0")@QueryParam("idDomanda") Long idDomanda,
			@DefaultValue("0")@QueryParam("idProgetto") Long idProgetto,
			@Context HttpServletRequest req) throws DaoException;



}
