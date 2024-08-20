/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.business.service;

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

import it.csi.pbandi.pbgestfinbo.integration.vo.SegnalazioneCorteContiVO;

@Path("/segnalazioneCorteConti")
public interface SegnalazioneCorteContiService {
	
	@GET
	@Path("/getSegnalazione")
	@Produces(MediaType.APPLICATION_JSON)
	Response getSegnalazioneCorteConti(@DefaultValue("0") @QueryParam("idSegnalazioneCorteConti") Long idSegnalazioneCorteConti,
	        @Context HttpServletRequest req);
	
	@POST
	@Path("/insertSegnalazione")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	Response insertSegnalazioneCorteConti(SegnalazioneCorteContiVO segnalazioneCorteContiVO,
			@DefaultValue("0") @QueryParam("idModalitaAgevolazione") int idModalitaAgev); 
	
	@POST
	@Path("/modifcaSegnalazione")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	Response modifcaSegnalazioneCorteConti(SegnalazioneCorteContiVO segnalazioneCorteContiVO, 
			@DefaultValue("0") @QueryParam("idSegnalazioneCorteConti") Long idSegnalazioneCorteConti,
			@DefaultValue("0") @QueryParam("idModalitaAgevolazione") int idModalitaAgev);
	
	
	// storico con dt fine validita = null
	@GET
	@Path("/getStoricoSegnalazioni")
	@Produces(MediaType.APPLICATION_JSON)
	Response getStoricoSegnalazioni(
			@DefaultValue("0") @QueryParam("idProgetto") Long idProgetto,
			@DefaultValue("0") @QueryParam("idModalitaAgevolazione") int idModalitaAgev,
			@Context HttpServletRequest req);
	
	
	@GET
	@Path("/getStorico")
	@Produces(MediaType.APPLICATION_JSON)
	Response getStorico(@DefaultValue("0") @QueryParam("idProgetto") Long idProgetto,
			@DefaultValue("0") @QueryParam("idModalitaAgevolazione") int idModalitaAgev,
			@Context HttpServletRequest req);

}
