/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.business.api;

import java.security.InvalidParameterException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.web.bind.annotation.RequestBody;

import it.csi.pbandi.pbservizit.exception.ErroreGestitoException;
import it.csi.pbandi.pbservizit.exception.UtenteException;
import it.csi.pbandi.pbservizit.security.BeneficiarioSec;
import it.csi.pbandi.pbservizit.security.UserInfoSec;

@Path("/utente")
public interface UserApi {

	@GET
	@Path("infoUtente")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getInfoUtente(@Context HttpServletRequest req) throws UtenteException, ErroreGestitoException;

	@POST
	@Path("/accedi")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response accedi(@Context HttpServletRequest req, @Valid @RequestBody UserInfoSec userInfo)
			throws UtenteException, ErroreGestitoException;

	@GET
	@Path("beneficiari")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getBeneficiari(@QueryParam("denominazione") String denominazione, @Context HttpServletRequest req)
			throws UtenteException, ErroreGestitoException;

	@POST
	@Path("/salvaBeneficiarioSelezionato")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response salvaBeneficiarioSelezionato(@Context HttpServletRequest req,
			@Valid @RequestBody BeneficiarioSec beneficiario) throws UtenteException, ErroreGestitoException;

	@GET
	@Path("SSOLogout")
	public void SSOLogout(@Context HttpServletRequest req);

	@GET
	@Path("avvisi")
	@Produces(MediaType.APPLICATION_JSON)
	public Response avvisi(@Context HttpServletRequest req) throws InvalidParameterException, Exception;

	@GET
	@Path("isRegolaApplicabileForBandoLinea")
	@Produces(MediaType.APPLICATION_JSON)
	public Response isRegolaApplicabileForBandoLinea(@QueryParam("idBandoLinea") Long idBandoLinea,
			@QueryParam("codiceRegola") String codiceRegola, @Context HttpServletRequest req)
			throws InvalidParameterException, Exception;

	@GET
	@Path("isCostanteFinpiemonte")
	@Produces(MediaType.APPLICATION_JSON)
	public Response isCostanteFinpiemonte(@Context HttpServletRequest req) throws Exception;

	@GET
	@Path("bandoIsEnteCompetenzaFinpiemonte")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	Response bandoIsEnteCompetenzaFinpiemonte(@QueryParam("progBandoLineaIntervento") Long progBandoLineaIntervento,
			@Context HttpServletRequest req) throws Exception;

	@GET
	@Path("isBandoSif")
	@Produces(MediaType.APPLICATION_JSON)
	public Response isBandoSif(@QueryParam("progBandoLineaIntervento") Long progBandoLineaIntervento,
			@Context HttpServletRequest req) throws InvalidParameterException, Exception;

	@GET
	@Path("isBandoSiffino")
	@Produces(MediaType.APPLICATION_JSON)
	public Response isBandoSiffino(@QueryParam("progBandoLineaIntervento") Long progBandoLineaIntervento,
			@QueryParam("idBando") Long idBando, @Context HttpServletRequest req)
			throws InvalidParameterException, Exception;

	@GET
	@Path("/hasProgettoLineaByDescBreve")
	@Produces(MediaType.APPLICATION_JSON)
	Response hasProgettoLineaByDescBreve(@Context HttpServletRequest req, @QueryParam("idProgetto") Long idProgetto,
			@QueryParam("descBreveLinea") String descBreveLinea) throws UtenteException, Exception;

	@GET
	@Path("progrBandoLineaByIdProgetto")
	@Produces(MediaType.APPLICATION_JSON)
	Response getProgrBandoLineaByIdProgetto(@Context HttpServletRequest req, @QueryParam("idProgetto") Long idProgetto)
			throws UtenteException, Exception;
}
