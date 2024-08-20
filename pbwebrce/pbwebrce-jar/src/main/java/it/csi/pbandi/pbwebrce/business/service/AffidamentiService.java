/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebrce.business.service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.InvalidParameterException;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import io.swagger.annotations.Api;
import it.csi.pbandi.pbservizit.exception.ErroreGestitoException;
import it.csi.pbandi.pbservizit.exception.UtenteException;
import it.csi.pbandi.pbwebrce.integration.request.AssociateFileRequest;
import it.csi.pbandi.pbwebrce.integration.request.RespingiAffiddamentoRequest;
import it.csi.pbandi.pbwebrce.integration.request.SalvaAffidamentoRequest;
import it.csi.pbandi.pbwebrce.integration.request.SalvaFornitoreRequest;
import it.csi.pbandi.pbwebrce.integration.request.SalvaVarianteRequest;
import it.csi.pbandi.pbwebrce.integration.request.SubcontrattoRequest;
import it.csi.pbandi.pbwebrce.integration.request.VerificaAffidamentoRequest;

@Path("/affidamenti")
@Api(value = "affidamenti")
public interface AffidamentiService {
//
//	@GET
//	@Path("/datiGenerali") 
//	@Produces(MediaType.APPLICATION_JSON)
//	Response getDatiGenerali( @Context HttpServletRequest req,  @QueryParam("idProgetto") Long idProgetto ) throws UtenteException, FileNotFoundException , IOException, Exception;
//	
//	@GET
//	@Path("/attivitaPregresse") 
//	@Produces(MediaType.APPLICATION_JSON)
//	Response getAttivitaPregresse( @Context HttpServletRequest req,  @QueryParam("idProgetto") Long idProgetto ) throws UtenteException, FileNotFoundException , IOException, Exception;
//	
	@GET
	@Path("/documenti/{idDocumentoIndex}")
	@Produces(MediaType.APPLICATION_JSON)
	Response getDocumento(@Context HttpServletRequest req, @PathParam("idDocumentoIndex") Long idDocumentoIndex)
			throws UtenteException, FileNotFoundException, IOException, Exception;

	@GET
	@Path("/affidamenti")
	@Produces(MediaType.APPLICATION_JSON)
	Response getElencoAffidamenti(@Context HttpServletRequest req, @QueryParam("idProgetto") Long idProgetto)
			throws UtenteException, FileNotFoundException, IOException, Exception;

	@GET
	@Path("/fornitori")
	@Produces(MediaType.APPLICATION_JSON)
	Response getFornitoriAssociabili(@Context HttpServletRequest req) throws UtenteException, Exception;

	@GET
	@Path("/affidamento")
	@Produces(MediaType.APPLICATION_JSON)
	Response getAffidamento(@Context HttpServletRequest req, @QueryParam("idAppalto") Long idAppalto)
			throws UtenteException, Exception;

	@GET
	@Path("/progetto")
	@Produces(MediaType.APPLICATION_JSON)
	Response getCodiceProgetto(@Context HttpServletRequest req, @QueryParam("idProgetto") Long idProgetto)
			throws UtenteException, Exception;

	@GET
	@Path("/affidamento/normative")
	@Produces(MediaType.APPLICATION_JSON)
	Response getNormativeAffidamento(@Context HttpServletRequest req) throws UtenteException, Exception;

	@GET
	@Path("/affidamento/tipologie")
	@Produces(MediaType.APPLICATION_JSON)
	Response getTipologieAffidamento(@Context HttpServletRequest req) throws UtenteException, Exception;

	@GET
	@Path("/appalto/tipologie")
	@Produces(MediaType.APPLICATION_JSON)
	Response getTipologieAppalto(@Context HttpServletRequest req) throws UtenteException, Exception;

	@GET
	@Path("/aggiudicazione/tipologie")
	@Produces(MediaType.APPLICATION_JSON)
	Response getTipologieProcedureAggiudicazione(@Context HttpServletRequest req,
			@QueryParam("idProgetto") Long idProgetto) throws UtenteException, Exception;

	@GET
	@Path("/varianti/tipologie")
	@Produces(MediaType.APPLICATION_JSON)
	Response getTipologieVarianti(@Context HttpServletRequest req) throws UtenteException, Exception;

	@GET
	@Path("/aggiudicazione/motiveAssenza")
	@Produces(MediaType.APPLICATION_JSON)
	Response getMotiveAssenza(@Context HttpServletRequest req) throws UtenteException, Exception;

	@POST
	@Path("/affidamento")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	Response salvaAffidamento(@Context HttpServletRequest req, SalvaAffidamentoRequest requestSalvaAffidamento)
			throws UtenteException, Exception;

	@DELETE
	@Path("/affidamenti/{idAppalto}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	Response cancellaAffidamento(@Context HttpServletRequest req, @PathParam("idAppalto") Long idAppalto)
			throws UtenteException, Exception;

	@POST
	@Path("/affidamento/verifica")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	Response inviaInVerifica(@Context HttpServletRequest req, VerificaAffidamentoRequest verificaRequest)
			throws UtenteException, Exception;

	@GET
	@Path("/ruoli")
	@Produces(MediaType.APPLICATION_JSON)
	Response getRuoli(@Context HttpServletRequest req) throws UtenteException, Exception;

	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////// SERVIZI DI VARIANTI, FORNITORI E ALLEGATI
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////// ///////////////////////////////////
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	@POST
	@Path("/affidamento/variante")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	Response salvaVariante(@Context HttpServletRequest req, SalvaVarianteRequest verificaRequest)
			throws UtenteException, Exception;

	@POST
	@Path("/affidamento/fornitore")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	Response salvaFornitore(@Context HttpServletRequest req, SalvaFornitoreRequest verificaRequest)
			throws UtenteException, Exception;

	@DELETE
	@Path("/affidamento/varianti/{idVariante}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	Response cancellaVariante(@Context HttpServletRequest req, @PathParam("idVariante") Long idVariante)
			throws UtenteException, Exception;

	@DELETE
	@Path("/affidamento/fornitori/{idFornitore}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	Response cancellaFornitore(@Context HttpServletRequest req, @PathParam("idFornitore") Long idFornitore,
			@QueryParam("idAppalto") Long idAppalto, @QueryParam("idTipoPercettore") Long idTipoPercettore)
			throws UtenteException, Exception;

	@GET
	@Path("/notifiche")
	@Produces(MediaType.APPLICATION_JSON)
	Response getNotifiche(@Context HttpServletRequest req, @QueryParam("idAppalto") Long idAppalto)
			throws UtenteException, Exception;

	@POST
	@Path("/affidamento/respingi")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	Response respingiAffidamento(@Context HttpServletRequest req, RespingiAffiddamentoRequest respingiRequest)
			throws UtenteException, Exception;

	@POST
	@Path("/affidamento/subcontratto")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	Response salvaSubcontratto(@Context HttpServletRequest req, SubcontrattoRequest request)
			throws UtenteException, Exception;

	@DELETE
	@Path("/affidamento/subcontratto/{idSubcontrattoAffidamento}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	Response cancellaSubcontratto(@Context HttpServletRequest req,
			@PathParam("idSubcontrattoAffidamento") Long idFornitore) throws UtenteException, Exception;

	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	////////////////////////////////////////// SERVIZI DI ARCHIVIO FILE
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////// ///////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	@GET
	@Path("/affidamenti/{idAppalto}/allegati")
	@Produces(MediaType.APPLICATION_JSON)
	Response getAllegati(@Context HttpServletRequest req, @PathParam("idAppalto") Long idAppalto)
			throws UtenteException, Exception;

	@DELETE
	@Path("/affidamento/allegati/{idDocumentoIndex}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	Response dissociateFileAffidamento(@Context HttpServletRequest req,
			@PathParam("idDocumentoIndex") Long idDocumentoIndex, @QueryParam("idProgetto") Long idProgetto,
			@QueryParam("idAppalto") Long idAppalto) throws UtenteException, Exception;

	@POST
	@Path("/affidamento/allegati")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	Response associateFileAffidamento(@Context HttpServletRequest req, AssociateFileRequest associateFileRequest)
			throws UtenteException, Exception;

	@GET
	@Path("/affidamenti/{idAppalto}/checkList")
	@Produces(MediaType.APPLICATION_JSON)
	Response getChecklistAssociatedAffidamento(@Context HttpServletRequest req, @PathParam("idAppalto") Long idAppalto)
			throws UtenteException, Exception;

	/* Modifica affidamenti - Servizio per le combo */
	@GET
	@Path("/affidamentoChecklist")
	@Produces(MediaType.APPLICATION_JSON)
	Response getAllAffidamentoChecklist(@Context HttpServletRequest req) throws UtenteException, Exception;

	@GET
	@Path("allegatiVerbaleChecklist")
	@Produces(MediaType.APPLICATION_JSON)
	Response allegatiVerbaleChecklist(@QueryParam("idDocumentoIndex") Long idDocIndex,
			@Context HttpServletRequest req) throws InvalidParameterException, Exception;
}
