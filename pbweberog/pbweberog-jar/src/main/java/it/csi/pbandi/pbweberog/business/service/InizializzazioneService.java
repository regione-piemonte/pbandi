/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweberog.business.service;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.DefaultValue;
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
import it.csi.pbandi.pbservizit.security.UserInfoSec;

@Path("/inizializzazione")
@Api(value = "inizializzazione")
public interface InizializzazioneService {

	@GET
	@Path("home")
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Activity initialization - Authenticates user and returns UserInfoSec", response = UserInfoSec.class)
	Response inizializzaHome(@DefaultValue("0") @QueryParam("idPrj") Long idPrj,
			@DefaultValue("0") @QueryParam("idSg") Long idSg, @DefaultValue("0") @QueryParam("idSgBen") Long idSgBen,
			@DefaultValue("0") @QueryParam("idU") Long idU, @DefaultValue("--") @QueryParam("role") String role,
			@DefaultValue("--") @QueryParam("taskIdentity") String taskIdentity,
			@DefaultValue("--") @QueryParam("taskName") String taskName,
			@DefaultValue("--") @QueryParam("wkfAction") String wkfAction, @Context HttpServletRequest req)
			throws UtenteException;

	@GET
	@Path("home2")
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Operation initialization - Authenticates user and returns UserInfoSec", response = UserInfoSec.class)
	Response inizializzaHome2(@DefaultValue("0") @QueryParam("idSg") Long idSg,
			@DefaultValue("0") @QueryParam("idSgBen") Long idSgBen, @DefaultValue("0") @QueryParam("idU") Long idU,
			@DefaultValue("--") @QueryParam("role") String role, @Context HttpServletRequest req)
			throws UtenteException;

	@GET
	@Path("progetto")
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Finds CODICE_VISUALIZZATO by idProgetto", response = String.class)
	Response getCodiceProgetto(@Context HttpServletRequest req, @QueryParam("idProgetto") Long idProgetto)
			throws UtenteException, Exception;

//	@GET
//	@Path("/datiGenerali")
//	@Produces(MediaType.APPLICATION_JSON)
//	@ApiOperation(value = "Finds dati generali by idProgetto", response = DatiGenerali.class)
//	Response getDatiGenerali(@Context HttpServletRequest req, @QueryParam("idProgetto") Long idProgetto)
//			throws UtenteException, FileNotFoundException, IOException, Exception;
//
//	@GET
//	@Path("/attivitaPregresse")
//	@Produces(MediaType.APPLICATION_JSON)
//	@ApiOperation(value = "Finds attivita pregresse by idProgetto", response = AttivitaPregresseDTO.class, responseContainer = "List")
//	Response getAttivitaPregresse(@Context HttpServletRequest req, @QueryParam("idProgetto") Long idProgetto)
//			throws UtenteException, FileNotFoundException, IOException, Exception;

}
