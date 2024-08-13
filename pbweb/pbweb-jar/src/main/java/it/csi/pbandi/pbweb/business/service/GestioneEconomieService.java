/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweb.business.service;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import it.csi.pbandi.pbweb.dto.gestioneEconomica.ModificaDTO;
import it.csi.pbandi.pbweb.pbandisrv.dto.gestioneeconomie.EconomiaDTO;
import it.csi.pbandi.pbweb.pbandisrv.dto.gestioneeconomie.ParametriRicercaEconomieDTO;

@Path("/gestioneeconomie")
public interface GestioneEconomieService {

	@POST
	@Path("geteconomie")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getEconomie(@QueryParam("idU")Long idU, 
								ParametriRicercaEconomieDTO parametriRicercaEconomieDTO,
								@Context HttpServletRequest req) throws Exception;
	
	@GET
	@Path("deleteeconomia")
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteEconomia(@QueryParam("idU")Long idU, 
			                       @QueryParam("idEconomia") Long idEconomia,
							       @Context HttpServletRequest req) throws Exception;

	@POST
	@Path("modifica")
	@Produces(MediaType.APPLICATION_JSON)
	public Response modifica(@QueryParam("idU")Long idU, 
			                 EconomiaDTO economiaDTO,   //Passare solo idEconomia 
							 @Context HttpServletRequest req) throws Exception;
	
	@GET
	@Path("getbandi")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getBandi( @QueryParam("idU")Long idU, 
							  @Context HttpServletRequest req) throws Exception;
	
	
	//Viene invocato al cambio del bando aggiornando la lista dei progetti associati
	@GET
	@Path("getprogettibybando")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getProgettiByBando(@QueryParam("idU")Long idU, 
					                   @QueryParam("idBandoLinea") Long idBandoLinea,
									   @Context HttpServletRequest req) throws Exception;
	
	
	//Viene invocato al cambio del progetto aggiornando il beneficiario e le quote del progetto
	@GET
	@Path("cambiaprogetto")// DA MODIFICARE IN  cambiaprogetto
	@Produces(MediaType.APPLICATION_JSON)
	public Response cambiaProgetto(@QueryParam("idU")Long idU, 
									                 @QueryParam("idProgetto") Long idProgetto,
									                 @QueryParam("idEconomia") Long idEconomia,
									                 @QueryParam("idSoggettoBeneficiario") Long idSoggettoBeneficiario,
									                 @QueryParam("tipologiaProgetto") String tipologiaProgetto,  // tipologiaProgetto: C = cedente; R = ricevente.
													 @Context HttpServletRequest req) throws Exception;
	
	
	@GET
	@Path("infoProgetto")
	@Produces(MediaType.APPLICATION_JSON)
	public Response infoProgetto(@QueryParam("idU")Long idU, 
			                     @QueryParam("idProgetto") Long idProgetto,
							     @Context HttpServletRequest req) throws Exception;
	
	
//	@GET
//	@Path("gestionequote")
//	@Produces(MediaType.APPLICATION_JSON)
//	public Response gestioneQuote(@QueryParam("idU")Long idU, 
//								          @QueryParam("idProgetto") Long idProgetto,
//								          @QueryParam("idEconomia") Long idEconomia,
//								          @QueryParam("tipologiaProgetto") String tipologiaProgetto,  // tipologiaProgetto: C = cedente; R = ricevente.
//										  @Context HttpServletRequest req) throws Exception;
	
	
	@POST
	@Path("salvaaggiornamentieconomia")
	@Produces(MediaType.APPLICATION_JSON)
	public Response salvaAggiornamentiEconomia(@QueryParam("idU")Long idU, 
												ModificaDTO modificaDTO,
												@Context HttpServletRequest req) throws Exception;
	
	@POST
	@Path("salvanuovaeconomia")
	@Produces(MediaType.APPLICATION_JSON)
	public Response salvaNuovaEconomia(@QueryParam("idU")Long idU, 
												ModificaDTO modificaDTO,
												@Context HttpServletRequest req) throws Exception;
	
	
	@POST
	@Path("reportDettaglioEconomia") 
	@Produces(MediaType.APPLICATION_JSON)
	Response reportDettaglioEconomia(
									@QueryParam("idU")Long idU, 
									ParametriRicercaEconomieDTO parametriRicercaEconomieDTO,
									@Context HttpServletRequest req) throws Exception;
	
}
