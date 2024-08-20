/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.business.service;

import it.csi.pbandi.pbgestfinbo.integration.vo.controdeduzioni.AllegatiControdeduzioniVO;
import it.csi.pbandi.pbgestfinbo.integration.vo.controdeduzioni.RichiestaProrogaVO;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/controdeduzioni")
public interface GestioneControdeduzioniService {
	@GET
	@Path("/getAllegati")
	@Produces(MediaType.APPLICATION_JSON)
	Response getAllegati ( 
			@DefaultValue("0") @QueryParam("idControdeduz") Long idControdeduz,
	        @Context HttpServletRequest req);

	@POST
	@Path("/inserisciAllegati/{idControdeduz}")
	@Produces(MediaType.APPLICATION_JSON)
	Response inserisciAllegati(
			@PathParam("idControdeduz") Long idControdeduz,
			List<AllegatiControdeduzioniVO> allegati,
			@Context HttpServletRequest req);

	@POST
	@Path("/deleteAllegato")
	@Produces(MediaType.APPLICATION_JSON)
	Response deleteAllegato(
			@DefaultValue("0") @QueryParam("idFileEntita") Long idFileEntita,
			@Context HttpServletRequest req);

	@GET
	@Path("/getIntestazioneControdeduzioni")
	@Produces(MediaType.APPLICATION_JSON)
	Response getIntestazioneControdeduzioni (
			@DefaultValue("0") @QueryParam("idProgetto") Long idProgetto,
			@Context HttpServletRequest req);
	@GET
	@Path("/getControdeduzioni")
	@Produces(MediaType.APPLICATION_JSON)
	Response getControdeduzioni (
			@DefaultValue("0") @QueryParam("idProgetto") Long idProgetto,
			@Context HttpServletRequest req);

	@POST
	@Path("/insertControdeduz")
	@Produces(MediaType.APPLICATION_JSON)
	Response insertControdeduz(
			@DefaultValue("0") @QueryParam("idGestioneRevoca") Long idGestioneRevoca,
			@Context HttpServletRequest req);
	@POST
	@Path("/richiediAccessoAtti")
	@Produces(MediaType.APPLICATION_JSON)
	Response richiediAccessoAtti(
			@DefaultValue("0") @QueryParam("idControdeduz") Long idControdeduz,
			@Context HttpServletRequest req);

	@POST
	@Path("/inviaControdeduz")
	@Produces(MediaType.APPLICATION_JSON)
	Response inviaControdeduz(
			@DefaultValue("0") @QueryParam("idControdeduz") Long idControdeduz,
			@Context HttpServletRequest req);

	@POST
	@Path("/deleteControdeduz")
	@Produces(MediaType.APPLICATION_JSON)
	Response deleteControdeduz(
			@DefaultValue("0") @QueryParam("idControdeduz") Long idControdeduz,
			@Context HttpServletRequest req);

	@GET
	@Path("/getRichiestaProroga")
	@Produces(MediaType.APPLICATION_JSON)
	Response getRichiestaProroga (
			@DefaultValue("0") @QueryParam("idControdeduz") Long idControdeduz,
			@Context HttpServletRequest req);

	@POST
	@Path("/richiediProroga/{idControdeduz}")
	@Produces(MediaType.APPLICATION_JSON)
	Response richiediProroga(
			@PathParam("idControdeduz") Long idControdeduz,
			@RequestBody RichiestaProrogaVO richiestaProrogaVO,
			@Context HttpServletRequest req);

}
