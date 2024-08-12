package it.csi.pbandi.pbweb.business.service;

import java.security.InvalidParameterException;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


@Path("/rendicontazioneBandoCultura")
public interface RendicontazioneCulturaService {

  @GET
  @Path("inizializzaRendicontazione")
  @Produces(MediaType.APPLICATION_JSON)
  Response inizializzaRendicontazione(
      @DefaultValue("0") @QueryParam("idProgetto") long idProgetto,
      @DefaultValue("0") @QueryParam("idBandoLinea") long idBandoLinea,
      @DefaultValue("0") @QueryParam("idSoggetto") long idSoggetto,
      @DefaultValue("") @QueryParam("codiceRuolo") String codiceRuolo,
      @Context HttpServletRequest req) throws InvalidParameterException, Exception;

  @GET
  @Path("direzioniSettori")
  @Produces(MediaType.APPLICATION_JSON)
  Response getDirezioniSettori(
      @Context HttpServletRequest req
  ) throws Exception;

}
