/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.business.service;

import java.math.BigDecimal;
import java.security.InvalidParameterException;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;
import org.springframework.web.bind.annotation.RequestBody;

import it.csi.pbandi.pbgestfinbo.exception.DaoException;
import it.csi.pbandi.pbgestfinbo.integration.vo.search.ElaboraRichiestaVO;
import it.csi.pbandi.pbgestfinbo.integration.vo.search.NuovaRichiestaVO;

@Path("/gestioneRichieste")
public interface GestioneRichiesteService {



	/////////////////////////////SERVIZI SUGGESTION////////////////////////////////////////

	@GET
	@Path("/getSuggestion")
	@Produces(MediaType.APPLICATION_JSON)
	Response getSuggestion(
			@DefaultValue("0") @QueryParam("value") String value,
			@DefaultValue("1") @QueryParam("id") String id, 
	        @Context HttpServletRequest req) throws DaoException;
	
	@GET
	@Path("/getDomandaNuovaRichiesta") 
	@Produces(MediaType.APPLICATION_JSON)
	Response getDomandaNuovaRichiesta(@DefaultValue("") @QueryParam("domandaNuovaRichiesta") String domandaNuovaRichiesta, @Context HttpServletRequest req) throws DaoException;

	@GET
	@Path("/getCodiceProgetto") 
	@Produces(MediaType.APPLICATION_JSON)
	Response getCodiceProgetto(@DefaultValue("") @QueryParam("codiceProgetto") String codiceProgetto, @Context HttpServletRequest req) throws DaoException;


	////////////////////////////////SERVIZI RICHIESTE/////////////////////////////////////////

	@GET
	@Path("/getRichieste") 
	@Produces(MediaType.APPLICATION_JSON)
	Response getRichieste(@Context HttpServletRequest req) throws DaoException;
	
	@GET
	@Path("/getStoricoRichieste") 
	@Produces(MediaType.APPLICATION_JSON)
	Response getStoricoRichieste(@DefaultValue("") @QueryParam("idRichiesta") String idRichiesta,@Context HttpServletRequest req) throws DaoException;
	
	@GET
	@Path("/getRichiesta") 
	@Produces(MediaType.APPLICATION_JSON)
	Response getRichiesta(@DefaultValue("") @QueryParam("idRichiesta") String idRichiesta,@Context HttpServletRequest req) throws DaoException;
	
	@GET
	@Path("/annullaRichiestaAntimafia") 
	@Produces(MediaType.APPLICATION_JSON)
	Response annullaRichiestaAntimafia(@DefaultValue("") @QueryParam("idRichiestaAntimafia") String idRichiesta,@Context HttpServletRequest req) throws DaoException;
	
	@GET
	@Path("/getTipoRichiesta") 
	@Produces(MediaType.APPLICATION_JSON)
	Response getTipoRichiesta() throws DaoException;

	@GET
	@Path("/findRichieste") 
	@Produces(MediaType.APPLICATION_JSON)
	Response findRichieste(
			@DefaultValue("0")@QueryParam("tipoRichiesta") BigDecimal tipoRichiesta, 
			@DefaultValue("0")@QueryParam("statoRichiesta") BigDecimal statoRichiesta, 
			@DefaultValue("") @QueryParam("numeroDomanda") String numeroDomanda,
			@DefaultValue("") @QueryParam("codiceFondo") String codiceFondo,
			@DefaultValue("") @QueryParam("richiedente") String richiedente,
			@DefaultValue("DESC") @QueryParam("ordinamento") String ordinamento,
			@DefaultValue("") @QueryParam("colonna") String colonna,
			@Context HttpServletRequest req) throws DaoException;

	@POST
	@Path("/insertNuovaRichiesta")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response insertNuovaRichiesta(@RequestBody NuovaRichiestaVO nuovaRichiesta, @Context HttpServletRequest req)
			throws DaoException;
	@POST
	@Path("/elaboraRichiesta")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response elaboraRichiesta(@RequestBody ElaboraRichiestaVO elaboraRichiesta, @Context HttpServletRequest req)
			throws DaoException;
	
	@POST
	@Path("/annullaRichiesta")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response annullaRichiesta(@RequestBody ElaboraRichiestaVO elaboraRichiesta, @Context HttpServletRequest req)
			throws DaoException;
	
	@POST
	@Path("/elaboraDurc")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response elaboraDurc(@RequestBody ElaboraRichiestaVO elaboraDurc, 
			@DefaultValue("") @QueryParam("isDocumento") boolean isDocumento,
			@Context HttpServletRequest req)
			throws DaoException;
	
	@POST
	@Path("/elaboraBdna")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response elaboraBdna(@RequestBody ElaboraRichiestaVO elaboraBdna,
			@DefaultValue("") @QueryParam("isDocumento") boolean isDocumento,
			@Context HttpServletRequest req)
			throws DaoException;
	
	@POST
	@Path("/elaboraAntimafia")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response elaboraAntimafia(@RequestBody ElaboraRichiestaVO elaboraAntimafia,
			@DefaultValue("") @QueryParam("isDocumento") boolean isDocumento,
			@Context HttpServletRequest req)
			throws DaoException;
	
	@POST
	@Path("/salvaUploadAntiMafia")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces(MediaType.APPLICATION_JSON)
	public  Response salvaUploadAntiMafia(
			MultipartFormDataInput multipartFormData,
			@Context HttpServletRequest req) throws InvalidParameterException, Exception;
	
	@POST
	@Path("/salvaUploadDurc")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces(MediaType.APPLICATION_JSON)
	public  Response salvaUploadDurc(
			MultipartFormDataInput multipartFormData,
			@Context HttpServletRequest req) throws InvalidParameterException, Exception;

	
	@POST
	@Path("/salvaUploadBdna")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces(MediaType.APPLICATION_JSON)
	public  Response salvaUploadBdna(
			MultipartFormDataInput multipartFormData,
			@Context HttpServletRequest req) throws InvalidParameterException, Exception;
}
