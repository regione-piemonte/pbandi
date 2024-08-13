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

import it.csi.pbandi.pbservizit.pbandisrv.dto.gestionebackoffice.PermessoDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.gestionebackoffice.TipoAnagraficaDTO;

@Path("/associazione")
public interface AssociazionePermessoRuolo {
	
	
	@GET
	@Path("cercapermessi") 
	@Produces(MediaType.APPLICATION_JSON)
	Response cercaPermessi( @QueryParam("idU") Long idU,
							@QueryParam("descrizione") String descrizione,
							@QueryParam("codice") String codice,
							@Context HttpServletRequest req) throws Exception;
	
	
	@GET
	@Path("cercaruolidaassociare") 
	@Produces(MediaType.APPLICATION_JSON)
	Response cercaRuoliDaAssociare( @QueryParam("idU") Long idU,
									@QueryParam("codice") String codice,
									@Context HttpServletRequest req) throws Exception;
	
	
	@GET
	@Path("cercaruoliassociati") 
	@Produces(MediaType.APPLICATION_JSON)
	Response cercaRuoliAssociati( @QueryParam("idU") Long idU,
								  @QueryParam("codice") String codice,
								  @Context HttpServletRequest req) throws Exception;
	
	@POST
	@Path("associaruoli") 
	@Produces(MediaType.APPLICATION_JSON)
	Response associaRuoli( @QueryParam("idU") Long idU,
						   @QueryParam("codice") String codice,
			                TipoAnagraficaDTO[] tipoAnagraficaDTO,   //In questo oggetto il parametro "descBreveTipoAnagrafica" corrisponde al valore del ruola da ossociare. ES: "ADA-OPE-MASTER"
			                                                         //Il parametro "descTipoAnagrafica" non va passato nel json 
						   @Context HttpServletRequest req) throws Exception;
	
	
	@POST
	@Path("disassociaruoli") 
	@Produces(MediaType.APPLICATION_JSON)
	Response disassociaRuoli( @QueryParam("idU") Long idU,
						      @QueryParam("codice") String codice,
			                   TipoAnagraficaDTO[] tipoAnagraficaDTO,   //In questo oggetto il parametro "descBreveTipoAnagrafica" corrisponde al valore del ruola da ossociare. ES: "ADA-OPE-MASTER"
                               											//Il parametro "descTipoAnagrafica" non va passato
						      @Context HttpServletRequest req) throws Exception;
	
	
	
	@GET
	@Path("cercaruoli") 
	@Produces(MediaType.APPLICATION_JSON)
	Response cercaRuoli( @QueryParam("idU") Long idU,
						 @QueryParam("descrizione") String descrizione,
						 @QueryParam("codice") String codice,
						 @Context HttpServletRequest req) throws Exception;
	
	
	@GET
	@Path("cercapermessidaassociare") 
	@Produces(MediaType.APPLICATION_JSON)
	Response cercaPermessiDaAssociare( @QueryParam("idU") Long idU,
									   @QueryParam("codice") String codice,
									   @Context HttpServletRequest req) throws Exception;
	
	
	@GET
	@Path("cercapermessiassociati") 
	@Produces(MediaType.APPLICATION_JSON)
	Response cercaPermessiAssociati( @QueryParam("idU") Long idU,
								     @QueryParam("codice") String codice,
								     @Context HttpServletRequest req) throws Exception;
	
	
	@POST
	@Path("associapermessi") 
	@Produces(MediaType.APPLICATION_JSON)
	Response associaPermessi( @QueryParam("idU") Long idU,
						      @QueryParam("codice") String codice,
						      PermessoDTO[] permessoDTO,   //In questo oggetto il parametro "descBrevePermesso" corrisponde al valore del ruola da ossociare. ES: "ENTE-GIURIDICO"
			                                                         //Il parametro "descPermesso" non va passato nel json 
						      @Context HttpServletRequest req) throws Exception;
	
	
	@POST
	@Path("disassociapermessi") 
	@Produces(MediaType.APPLICATION_JSON)
	Response disassociaPermessi( @QueryParam("idU") Long idU,
						         @QueryParam("codice") String codice,
						         PermessoDTO[] permessoDTO,   //In questo oggetto il parametro "descBrevePermesso" corrisponde al valore del ruola da ossociare. ES: "ADA-OPE-MASTER"
                               											   //Il parametro "descPermesso" non va passato
						        @Context HttpServletRequest req) throws Exception;
	
	

}
