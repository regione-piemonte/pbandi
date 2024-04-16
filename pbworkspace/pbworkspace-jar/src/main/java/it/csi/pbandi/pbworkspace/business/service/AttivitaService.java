/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbworkspace.business.service;

import java.security.InvalidParameterException;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.jboss.resteasy.annotations.jaxrs.QueryParam;

import it.csi.pbandi.pbworkspace.exception.ErroreGestitoException;
import it.csi.pbandi.pbworkspace.exception.RecordNotFoundException;
import it.csi.pbandi.pbworkspace.exception.UtenteException;
import it.csi.pbandi.pbworkspace.integration.request.CambiaStatoNotificaRequest;
import it.csi.pbandi.pbworkspace.integration.request.NotificaRequest;

@Path("/attivita")
public interface AttivitaService {

	@GET
	@Path("bandi")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getBandi(@QueryParam("idBeneficiario") Long idBeneficiario, @Context HttpServletRequest req)
			throws ErroreGestitoException, UtenteException, RecordNotFoundException;

	@GET
	@Path("bandoByProgr")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getBandoByProgr(@Context HttpServletRequest req,
			@QueryParam("progrBandoLineaIntervento") Long progrBandoLineaIntervento)
			throws ErroreGestitoException, UtenteException, RecordNotFoundException;

	@GET
	@Path("progetti")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getProgetti(@Context HttpServletRequest req, @QueryParam("idBeneficiario") Long idBeneficiario,
			@QueryParam("progrBandoLineaIntervento") Long progrBandoLineaIntervento)
			throws ErroreGestitoException, UtenteException;

	@GET
	@Path("getAttivita")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAttivita(@Context HttpServletRequest req,
			@QueryParam("idBeneficiario") Long idBeneficiario,
			@QueryParam("progrBandoLineaIntervento") Long progrBandoLineaIntervento,
			@QueryParam("descrAttivita") String descrAttivita, @QueryParam("idProgetto") Long idProgetto,
			@QueryParam("start") Long start) throws InvalidParameterException, Exception;

	@PUT
	@Path("getNotifiche")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response getNotifiche(@Context HttpServletRequest req, NotificaRequest notificaRequest)
			throws InvalidParameterException, Exception;

	@PUT
	@Path("countNotifiche")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response countNotifiche(@Context HttpServletRequest req, NotificaRequest notificaRequest)
			throws InvalidParameterException, Exception;

	@POST
	@Path("cambiaStatoNotifica")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response cambiaStatoNotifica(@Context HttpServletRequest req,
			CambiaStatoNotificaRequest cambiaStatoNotificaRequest) throws InvalidParameterException, Exception;

	@POST
	@Path("cambiaStatoNotificheSelezionate")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response cambiaStatoNotificheSelezionate(@Context HttpServletRequest req,
			CambiaStatoNotificaRequest cambiaStatoNotificaRequest) throws InvalidParameterException, Exception;

	@GET
	@Path("startAttivita")
	@Produces(MediaType.APPLICATION_JSON)
	public Response startAttivita(@Context HttpServletRequest req, @QueryParam("descBreveTask") String descBreveTask,
			@QueryParam("idProgetto") Long idProgetto) throws InvalidParameterException, Exception;

	@GET
	@Path("trovaConsensoInvioComunicazione")
	@Produces(MediaType.APPLICATION_JSON)
	public Response trovaConsensoInvioComunicazione(@Context HttpServletRequest req)
			throws InvalidParameterException, Exception;

	@GET
	@Path("salvaConsensoInvioComunicazione")
	@Produces(MediaType.APPLICATION_JSON)
	public Response salvaConsensoInvioComunicazione(@Context HttpServletRequest req,
			@QueryParam("emailConsenso") String emailConsenso, @QueryParam("flagConsensoMail") String flagConsensoMail)
			throws InvalidParameterException, Exception;

	@GET
	@Path("ricercaAttivitaPrecedente")
	@Produces(MediaType.APPLICATION_JSON)
	public Response ricercaAttivitaPrecedente(@QueryParam("idBeneficiario") Long idBeneficiario,
			@Context HttpServletRequest req) throws InvalidParameterException, Exception;
}
