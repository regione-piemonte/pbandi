/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.business.service;

import it.csi.pbandi.pbgestfinbo.exception.DaoException;
import it.csi.pbandi.pbgestfinbo.exception.ErroreGestitoException;
import it.csi.pbandi.pbgestfinbo.exception.RecordNotFoundException;
import it.csi.pbandi.pbgestfinbo.integration.vo.ModificaEscussioneRiassicurazioniDTO;
import it.csi.pbandi.pbgestfinbo.integration.vo.VisualizzaStoricoEscussioneVO;

import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;

@Path("/riassicurazioni")
public interface GestioneRiassicurazioniService {
	
	@GET
	@Path("/test")
	@Produces(MediaType.APPLICATION_JSON)
	Response getTest(@Context HttpServletRequest req) throws DaoException;
	
	// Cerca Riassicurazioni //
	
	@GET
	@Path("/ricercaBeneficiariRiassicurazioni")
	@Produces(MediaType.APPLICATION_JSON)
	Response ricercaRiassicurazioni(
			@DefaultValue("") @QueryParam("descrizioneBando") String descrizioneBando,
			@DefaultValue("") @QueryParam("codiceProgetto") String codiceProgetto,
			@DefaultValue("") @QueryParam("codiceFiscale") String codiceFiscale,
			@DefaultValue("") @QueryParam("ndg") String ndg,
			@DefaultValue("") @QueryParam("partitaIva") String partitaIva,
			@DefaultValue("") @QueryParam("denominazioneCognomeNome") String denominazioneCognomeNome,
			@DefaultValue("") @QueryParam("statoEscussione") String statoEscussione,
			@DefaultValue("") @QueryParam("denominazioneBanca") String denominazioneBanca,
	        @Context HttpServletRequest req) throws Exception;
	
	
	@GET
	@Path("/getDettaglioRiassicurazioni")
	@Produces(MediaType.APPLICATION_JSON)
	Response getDettaglioRiassicurazioni(
			@DefaultValue("") @QueryParam("idProgetto") Long idProgetto,
			@DefaultValue("0") @QueryParam("idRiassicurazione") Long idRiassicurazione,
			@DefaultValue("false") @QueryParam("getStorico") Boolean getStorico,
	        @Context HttpServletRequest req) throws Exception;

	@GET
    @Path("/modificaRiassicurazione")
	@Produces(MediaType.APPLICATION_JSON)
    Response modificaRiassicurazione(
    		@DefaultValue("") @QueryParam("idRiassicurazione") Long idRiassicurazione,
    		//@DefaultValue("false") @QueryParam("isImportoModified") Boolean isImportoModified,
    		@QueryParam("importoEscusso") String importoEscusso,
    		//@DefaultValue("false") @QueryParam("isDataEscussioneModified") Boolean isDataEscussioneModified,
    		@QueryParam("dataEscussione") Date dataEscussione,
    		//@DefaultValue("false") @QueryParam("isDataScaricoModified") Boolean isDataScaricoModified,
    		@QueryParam("dataScarico") Date dataScarico,
            @Context HttpServletRequest req) throws Exception;

	// Gestione Escussione Riassicurazioni //
	
	@GET
	@Path("/initGestioneEscussioneRiassicurazioni")
	@Produces(MediaType.APPLICATION_JSON)
	Response initGestioneEscussioneRiassicurazioni(
			@DefaultValue("") @QueryParam("idProgetto") Long idProgetto,
			@Context HttpServletRequest req) throws Exception;


	@POST
	@Path("/modificaEscussioneRiassicurazioni")
	@Produces(MediaType.APPLICATION_JSON)
	public Response modificaEscussioneRiassicurazioni(
			@RequestBody ModificaEscussioneRiassicurazioniDTO dati,
			@DefaultValue("false") @QueryParam("inserisciNuovo") Boolean inserisciNuovo,
			@Context HttpServletRequest req) throws Exception;
}
