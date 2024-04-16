/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbworkspace.business.service;

import java.security.InvalidParameterException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.jboss.resteasy.annotations.jaxrs.QueryParam;

import it.csi.pbandi.pbworkspace.exception.ErroreGestitoException;
import it.csi.pbandi.pbworkspace.exception.UtenteException;
import it.csi.pbandi.pbworkspace.integration.vo.NotificaAlertVO;

@Path("/notificheViaMail")
public interface NotificheViaMailService {

	@GET
	@Path("getMail")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getMail(@Context HttpServletRequest req) throws UtenteException, Exception;

	@POST
	@Path("saveMail")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response saveMail(@Context HttpServletRequest req, String mail)
			throws UtenteException, InvalidParameterException, Exception;

	@GET
	@Path("getNotificheFrequenze")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getNotificheFrequenze(@Context HttpServletRequest req) throws UtenteException, Exception;

	@POST
	@Path("associateNotificheIstruttore")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response associateNotificheIstruttore(@Context HttpServletRequest req, List<NotificaAlertVO> notificheAlert)
			throws UtenteException, InvalidParameterException, Exception;

	@GET
	@Path("getBandiProgetti")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getBandiProgetti(@Context HttpServletRequest req,
			@QueryParam("idSoggettoNotifica") Long idSoggettoNotifica) throws ErroreGestitoException, UtenteException;

	@POST
	@Path("associateProgettiToNotifica")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response associateProgettiToNotifica(@Context HttpServletRequest req, NotificaAlertVO notificaAlert)
			throws UtenteException, InvalidParameterException, Exception;

}
