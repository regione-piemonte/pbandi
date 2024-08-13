/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebbo.business.service;

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

import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;

import it.csi.pbandi.pbservizit.pbandisrv.dto.gestionebackoffice.UtenteRicercatoDTO;

@Path("/gestioneutenti")
public interface GestioneUtenti {
	
	
	@GET
	@Path("findtipoanagrafica") 
	@Produces(MediaType.APPLICATION_JSON)
	Response findTipoAnagrafica( @QueryParam("idU") Long idU,
							@Context HttpServletRequest req) throws Exception;
	
	@POST
	@Path("cercautenti") 
	@Produces(MediaType.APPLICATION_JSON)
	Response findUtenti( @QueryParam("idU") Long idU,
						 @QueryParam("idSoggetto") Long idSoggetto,
						 @QueryParam("ruolo") String ruolo,
					     UtenteRicercatoDTO filtro,
				         @Context HttpServletRequest req) throws Exception;
	
	
	@GET
	@Path("dettaglioutente") 
	@Produces(MediaType.APPLICATION_JSON)
	Response dettaglioUtente( @QueryParam("idU") Long idU,
							  @QueryParam("idPersonaFisica") Long idPersonaFisica,
							  @QueryParam("idTipoAnagrafica") Long idTipoAnagrafica,
							  @QueryParam("descBreveTipoAnagrafica") String descBreveTipoAnagrafica,
							  @QueryParam("idSoggetto") Long idSoggetto,
							  @Context HttpServletRequest req) throws Exception;
	
	
	
	@GET
	@Path("getenti") 
	@Produces(MediaType.APPLICATION_JSON)
	Response getenti( @QueryParam("idU") Long idU,
					  @QueryParam("idSoggetto") Long idSoggetto,
					  @QueryParam("idTipoAnagrafica") Long idTipoAnagrafica,
					  @QueryParam("ruolo") String ruolo,
					  @Context HttpServletRequest req) throws Exception;
	
	
	
	@GET
	@Path("modifica") 
	@Produces(MediaType.APPLICATION_JSON)
	Response salvaModifica( @QueryParam("idU") Long idU,
							@QueryParam("idSoggetto") Long idSoggetto,
							@QueryParam("idProgetto") Long idProgetto,
							@QueryParam("codiceFiscaleBeneficiario") String codiceFiscaleBeneficiario,
							@QueryParam("codiceVisualizzatoProgetto") String codiceVisualizzatoProgetto,
							@QueryParam("isRappresentanteLegale") Boolean  isRappresentanteLegale,
							@Context HttpServletRequest req) throws Exception;
	
	
	@GET
	@Path("aggiungi") 
	@Produces(MediaType.APPLICATION_JSON)
	Response aggiungiAssociazioneBeneficiarioProgetto( @QueryParam("idU") Long idU,
											           @QueryParam("idSoggetto") Long idSoggetto,
													   @QueryParam("codiceFiscaleBeneficiario") String codiceFiscaleBeneficiario,
													   @QueryParam("codiceVisualizzatoProgetto") String codiceVisualizzatoProgetto,
													   @QueryParam("isRappresentanteLegale") Boolean  isRappresentanteLegale,
													   @Context HttpServletRequest req) throws Exception;
	
	
	@GET
	@Path("eliminabeneficiarioprogetto") 
	@Produces(MediaType.APPLICATION_JSON)
	Response eliminaAssociazioneBeneficiarioProgetto( @QueryParam("idU") Long idU,
			 										  @QueryParam("idSoggetto") Long idSoggetto,
			 										  @QueryParam("codiceProgettoVisualizzato") String codiceProgettoVisualizzato,
													  @Context HttpServletRequest req) throws Exception;
	
	
	@GET
	@Path("eliminautente") 
	@Produces(MediaType.APPLICATION_JSON)
	Response eliminaUtente( @QueryParam("idU") Long idU,
						   @QueryParam("idSoggetto") Long idSoggetto,
						   @QueryParam("idTipoAnagraficaSoggetto") Long idTipoAnagraficaSoggetto,
						   @QueryParam("idPersonaFisica") Long idPersonaFisica,
						   @Context HttpServletRequest req) throws Exception;
	
	
	@POST
	@Path("upload") 
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces(MediaType.APPLICATION_JSON)
	Response caricaFile(MultipartFormDataInput multipartFormData,
						 @Context HttpServletRequest req) throws Exception;
	
	
	@POST
	@Path("salvanuovoutente") 
	@Produces(MediaType.APPLICATION_JSON)
	Response salvaNuovoUtente( @QueryParam("idU") Long idU,
							   @QueryParam("nome") String nome,
							   @QueryParam("cognome") String cognome,
							   @QueryParam("codiceFiscale") String codiceFiscale,
							   @QueryParam("idTipoAnagrafica") Long idTipoAnagrafica,
							   @QueryParam("ruolo") String ruolo,
							   @QueryParam("email") String email,
//							   @QueryParam("idSoggetto") Long idSoggetto,
							   Long[] idEntiAssociati ,
							   @Context HttpServletRequest req) throws Exception;
	
	
	@POST
	@Path("salvamodificautente") 
	@Produces(MediaType.APPLICATION_JSON)
	Response salvaModificaUtente( @QueryParam("idU") Long idU,
								  @QueryParam("nome") String nome,
								  @QueryParam("cognome") String cognome,
								  @QueryParam("codiceFiscale") String codiceFiscale,
								  @QueryParam("idTipoAnagrafica") Long idTipoAnagrafica,
								  @QueryParam("email") String email,
								  Long[] idEntiAssociati,
								  @Context HttpServletRequest req) throws Exception;
	
	@GET
	@Path("gestioneBeneficiarioProgetto") 
	@Produces(MediaType.APPLICATION_JSON)
	Response gestioneBeneficiarioProgetto(
			@QueryParam("idU") Long idU,
			@QueryParam("idSoggetto") Long idSoggetto,
			@Context HttpServletRequest req) throws Exception;

}
