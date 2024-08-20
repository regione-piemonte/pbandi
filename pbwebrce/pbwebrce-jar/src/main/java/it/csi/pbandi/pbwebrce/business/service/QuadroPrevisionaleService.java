/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebrce.business.service;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import it.csi.pbandi.pbservizit.exception.UtenteException;
import it.csi.pbandi.pbwebrce.dto.ExecResults;
import it.csi.pbandi.pbwebrce.dto.quadroprevisionale.EsitoFindQuadroPrevisionaleDTO;
import it.csi.pbandi.pbwebrce.dto.quadroprevisionale.EsitoSalvaQuadroPrevisionaleDTO;
import it.csi.pbandi.pbwebrce.dto.quadroprevisionale.ResponseGetQuadroPrevisionale;
import it.csi.pbandi.pbwebrce.integration.request.SalvaQuadroPrevisionaleRequest;
import it.csi.pbandi.pbwebrce.integration.request.ValidazioneDatiQuadroProvisionaleRequest;

@Path("/quadroPrevisionale")
@Api(value = "quadroPrevisionale")
public interface QuadroPrevisionaleService {
	
	@GET
	@Path("/progetto") 
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Finds Codice progetto by idProgetto", response = String.class)
	Response getCodiceProgetto( @Context HttpServletRequest req, @QueryParam("idProgetto") Long idProgetto ) throws UtenteException, Exception;
	
	@GET
	@Path("/") 
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Finds Quadro Previsionale by idProgetto", response = ResponseGetQuadroPrevisionale.class)
	Response getQuadroPrevisionale( @Context HttpServletRequest req, @QueryParam("idProgetto") Long idProgetto ) throws UtenteException, Exception;
	

	@POST
	@Path("/validazioneDati")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Controlla dati quadro previsionale per salvattaggio", response = ExecResults.class)
	Response controllaDatiPerSalvataggio( @Context HttpServletRequest req, ValidazioneDatiQuadroProvisionaleRequest vaProvisionaleRequest ) throws UtenteException, Exception;
	
	@POST
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Saves validated Quadro Previsionale data to database", response = EsitoSalvaQuadroPrevisionaleDTO.class)
	Response salvaQuadroPrevisionale( @Context HttpServletRequest req, ResponseGetQuadroPrevisionale salvaQuadroPrevisionaleRequest ) throws UtenteException, Exception;
	
	
	
}
