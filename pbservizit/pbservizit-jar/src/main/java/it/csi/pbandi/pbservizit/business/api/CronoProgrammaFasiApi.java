/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.business.api;

import java.security.InvalidParameterException;
import java.util.List;

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

import org.springframework.web.bind.annotation.RequestBody;

import it.csi.pbandi.pbservizit.dto.cronoprogrammaFasi.CronoprogrammaListFasiItemAllegati;

@Path("/cronoprogrammaFasi")
public interface CronoProgrammaFasiApi {

	@GET
	@Path("/getDataCronoprogramma")
	@Produces(MediaType.APPLICATION_JSON)
	Response getDataCronoprogramma(@QueryParam("idProgetto") Long idProgetto, @Context HttpServletRequest req)
			throws Exception;

	@POST
	@Path("/saveDataCronoprogramma")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	Response saveDataCronoprogramma(@QueryParam("idProgetto") Long idProgetto,
			@RequestBody List<CronoprogrammaListFasiItemAllegati> dataCronoprogrammaItemeAllegati,
			@Context HttpServletRequest req) throws Exception;

	@POST
	@Path("disassociaAllegato")
	@Produces(MediaType.APPLICATION_JSON)
	Response disassociaAllegato(@DefaultValue("0") @QueryParam("idDocumentoIndex") Long idDocumentoIndex,
			@DefaultValue("0") @QueryParam("idProgettoIter") Long idProgettoIter,
			@DefaultValue("0") @QueryParam("idProgetto") Long idProgetto, @Context HttpServletRequest req)
			throws InvalidParameterException, Exception;

	@GET
	@Path("getAllegatiIterProgetto")
	@Produces(MediaType.APPLICATION_JSON)
	Response getAllegatiIterProgetto(@QueryParam("idProgetto") Long idProgetto, @QueryParam("idIter") Long idIter,
			@Context HttpServletRequest req) throws Exception;

}
