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
import it.csi.pbandi.pbgestfinbo.integration.vo.AbbattimentoGaranzieVO;

@Path ("/abbattimentoGaranzie")
public interface AbbattimentoGaranzieService {
	@GET
	@Path("/getAbbattimentoGaranzie")
	@Produces(MediaType.APPLICATION_JSON)
	Response getAbbattimentoGaranzia(
			@DefaultValue("0") @QueryParam("idUtente") Long idUtente, 
			@DefaultValue("0") @QueryParam("idProgetto") Long idProgetto,
			@DefaultValue("0") @QueryParam("idAbbattimentoGaranzia") Long idAbbattimentoGaranzia,
			@DefaultValue("0") @QueryParam("idModalitaAgevolazione") int idModalitaAgev,
	        @Context HttpServletRequest req) throws DaoException;
	
	
	@POST
	@Path("/insertAbbattimentoGaranzie")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	Response insertAbbattimentoGaranzia(
			AbbattimentoGaranzieVO abbattimento,
			@DefaultValue("0") @QueryParam("idUtente") Long idUtente,
			@DefaultValue("0") @QueryParam("idProgetto") Long idProgetto,
			@DefaultValue("0") @QueryParam("idModalitaAgevolazione") int idModalitaAgev,
			@Context HttpServletRequest request) 
			throws InvalidParameterException, Exception;

	
	@POST
	@Path("/modificaAbbattimentoGaranzie")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	//@Consumes(MediaType.MULTIPART_FORM_DATA)
	Response modificaAbbattimentoGaranzia(
			AbbattimentoGaranzieVO abbattimento,
			@DefaultValue("0") @QueryParam("idUtente") Long idUtente,
			@DefaultValue("0") @QueryParam("idProgetto") Long idProgetto,
			@DefaultValue("0") @QueryParam("idAbbattimentoGaranzia") Long idAbbattimentoGaranzia,
			@DefaultValue("0") @QueryParam("idModalitaAgevolazione") int idModalitaAgev,
			@Context HttpServletRequest request) 
			throws InvalidParameterException, Exception;
	
	
	@GET
	@Path("/getStoricoAbbattimenti")
	@Produces(MediaType.APPLICATION_JSON)
	Response getStoricoAbbattimenti(
			@DefaultValue("0") @QueryParam("idUtente") Long idUtente, 
			@DefaultValue("0") @QueryParam("idProgetto") Long idProgetto,
			@DefaultValue("0") @QueryParam("idModalitaAgevolazione") int idModalitaAgev,
	        @Context HttpServletRequest req) throws DaoException;
	 
	@GET
	@Path("/getStoricoAbbattimentoGaranzia")
	@Produces(MediaType.APPLICATION_JSON)
	Response getStoricoAbbattimentoGaranzia(
			@DefaultValue("0") @QueryParam("idUtente") Long idUtente, 
			@DefaultValue("0") @QueryParam("idProgetto") Long idProgetto,
			@DefaultValue("0") @QueryParam("idModalitaAgevolazione") int idModalitaAgev,
			@Context HttpServletRequest req) throws DaoException;
	
	@GET
	@Path("/listaAttivitaAbbattimento")
	@Produces(MediaType.APPLICATION_JSON)
	Response getListalistaAttivitaAbbattimento() throws DaoException;


}
