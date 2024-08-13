/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebbo.business.service;

import java.io.File;

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

import it.csi.pbandi.pbservizit.pbandisrv.dto.gestionebackoffice.UtenteRicercatoDTO;
import it.csi.pbandi.pbwebbo.dto.gestionenews.AvvisoDTO;

@Path("/gestionenews")
public interface GestioneNews {
	
	@GET
	@Path("inizializzaGestioneNews") 
	@Produces(MediaType.APPLICATION_JSON)
	Response inizializzaGestioneNews(@Context HttpServletRequest req) throws Exception;
	
	@POST
	@Path("aggiornaAvviso")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	Response aggiornaAvviso(AvvisoDTO avvisoDTO, @Context HttpServletRequest req) throws Exception;
	
	@GET
	@Path("eliminaAvviso") 
	@Produces(MediaType.APPLICATION_JSON)
	Response eliminaAvviso(	@QueryParam("idNews") Long idNews, 
							@Context HttpServletRequest req) throws Exception;
	
}
