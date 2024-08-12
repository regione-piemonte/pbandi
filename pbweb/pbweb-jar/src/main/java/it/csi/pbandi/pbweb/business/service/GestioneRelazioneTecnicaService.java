package it.csi.pbandi.pbweb.business.service;

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

@Path("/relazioneTecnica")
public interface GestioneRelazioneTecnicaService {
	
    @GET
    @Path("/getCodiceProgetto")
    @Produces(MediaType.APPLICATION_JSON)
    Response getCodiceProgetto(
            @DefaultValue("0") @QueryParam("idProgetto") Long idProgetto,
            @Context HttpServletRequest req) throws Exception;
    
    @GET
    @Path("/getRelazioneTecnica")
    @Produces(MediaType.APPLICATION_JSON)
    Response getRelazioneTecnica(
    		@DefaultValue("0") @QueryParam("idProgetto") Long idProgetto,
    		@DefaultValue("0") @QueryParam("idTipoRelazioneTecnica") int idTipoRelazioneTecnica,
    		@Context HttpServletRequest req) throws Exception;
	
    @POST
	@Path("/salvaRelazioneTecnica")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces(MediaType.APPLICATION_JSON)
	public  Response salvaRelazioneTecnica(
			MultipartFormDataInput multipartFormData,
			@Context HttpServletRequest req) throws InvalidParameterException, Exception;
    @GET
    @Path("/validaRifiutaRelazioneTecnica")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public  Response validaRifiutaRelazioneTecnica(
    		@DefaultValue("0") @QueryParam("idRelazioneTecnica") Long idRelazioneTecnica,
    		@DefaultValue("0") @QueryParam("flagConferma") String flagConferma,
    		@DefaultValue("0") @QueryParam("nota") String nota,
    		@Context HttpServletRequest req) throws InvalidParameterException, Exception;
    

}
