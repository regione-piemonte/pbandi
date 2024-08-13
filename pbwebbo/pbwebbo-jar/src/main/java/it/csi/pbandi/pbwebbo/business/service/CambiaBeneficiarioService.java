/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebbo.business.service;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import it.csi.pbandi.pbservizit.pbandisrv.dto.gestionebackoffice.BeneficiarioDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.gestionebackoffice.DatiBeneficiarioProgettoDTO;

@Path("/associazione")
public interface CambiaBeneficiarioService {
	
	
	@GET
	@Path("cercabeneficiari") 
	@Produces(MediaType.APPLICATION_JSON)
	Response findBeneficiariByProgetto( @QueryParam("idU") Long idU,
										@QueryParam("idSoggetto") Long idSoggetto,
										@QueryParam("ruolo") String ruolo,
										@QueryParam("idProgetto") Long idProgetto,
								        @Context HttpServletRequest req) throws Exception;
	
	
	@GET
	@Path("cercaProgetti") 
	@Produces(MediaType.APPLICATION_JSON)
	Response findProgettiByBeneficiario( @QueryParam("idU") Long idU,
										 @QueryParam("idSoggetto") Long idSoggetto,
										 @QueryParam("ruolo") String ruolo,
										 @QueryParam("idSoggettoBeneficiario") Long idSoggettoBeneficiario,
								         @Context HttpServletRequest req) throws Exception;
	
	
	
	@GET
	@Path("cercabenprog") 
	@Produces(MediaType.APPLICATION_JSON)
	Response findProgettoBeneficiario( @QueryParam("idU") Long idU,
									   @QueryParam("idProgSelez") Long idProgSelez,
									   @QueryParam("idBeneficiario") String idBeneficiario,
									   @QueryParam("codiceVisualizzato") String codiceVisualizzato,   //Corrisponde alla descrizione a video del messaggio
								       @Context HttpServletRequest req) throws Exception;
	
	
	@POST
	@Path("cercabensub") 
	@Produces(MediaType.APPLICATION_JSON)
	Response findBeneficiarioSubentrante( @QueryParam("idU") Long idU,
										  @QueryParam("beneficiarioSubentrante") String beneficiarioSubentrante,
										  BeneficiarioDTO currentBeneficiario,
								          @Context HttpServletRequest req) throws Exception;
	
	
	@POST
	@Path("cambiabeneficiario") 
	@Produces(MediaType.APPLICATION_JSON)
	Response cambiaBeneficiario( @QueryParam("idU") Long idU,
							     DatiBeneficiarioProgettoDTO datiBeneficiarioProgetto,
							     @QueryParam("isAggiornabile") boolean isAggiornabile,
								 @Context HttpServletRequest req) throws Exception;
	
	
	
	@POST
	@Path("salvanuovobeneficiario") 
	@Produces(MediaType.APPLICATION_JSON)
	Response salvaDatiBeneficiario( @QueryParam("idU") Long idU,
					             	DatiBeneficiarioProgettoDTO datiBeneficiarioProgetto,
					             	@Context HttpServletRequest req) throws Exception;
	
	
	
	@GET
	@Path("findregioni") 
	@Produces(MediaType.APPLICATION_JSON)
	Response findRegioni( @QueryParam("idU") Long idU,
						  @Context HttpServletRequest req) throws Exception;
	
	@GET
	@Path("findprovince") 
	@Produces(MediaType.APPLICATION_JSON)
	Response findProvince( @QueryParam("idU") Long idU,
						   @QueryParam("idComune") String idComune,
						   @Context HttpServletRequest req) throws Exception;
	
	@GET
	@Path("findcomune") 
	@Produces(MediaType.APPLICATION_JSON)
	Response findComune( @QueryParam("idU") Long idU,
						 @QueryParam("idProvincia") String idProvincia,
						 @Context HttpServletRequest req) throws Exception;
	

}
