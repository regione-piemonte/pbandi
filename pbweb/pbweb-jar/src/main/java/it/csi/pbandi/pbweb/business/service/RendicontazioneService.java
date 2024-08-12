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

@Path("/rendicontazione")
public interface RendicontazioneService {

	@GET
	@Path("inizializzaRendicontazione")
	@Produces(MediaType.APPLICATION_JSON)
	Response inizializzaRendicontazione(@DefaultValue("0") @QueryParam("idProgetto") long idProgetto,
			@DefaultValue("0") @QueryParam("idBandoLinea") long idBandoLinea,
			@DefaultValue("0") @QueryParam("idSoggetto") long idSoggetto,
			@DefaultValue("") @QueryParam("codiceRuolo") String codiceRuolo, @Context HttpServletRequest req)
			throws InvalidParameterException, Exception;

	@GET
	@Path("sal")
	@Produces(MediaType.APPLICATION_JSON)
	Response getSALCorrente(@DefaultValue("0") @QueryParam("idProgetto") long idProgetto,
			@Context HttpServletRequest req) throws InvalidParameterException, Exception;

	@GET
	@Path("salByIdDocSpesa")
	@Produces(MediaType.APPLICATION_JSON)
	Response getSALByIdDocSpesa(@DefaultValue("0") @QueryParam("idProgetto") long idProgetto,
			@DefaultValue("0") @QueryParam("idDocumentoSpesa") long idDocumentoSpesa, @Context HttpServletRequest req)
			throws InvalidParameterException, Exception;

	@GET
	@Path("colonneQteSal")
	@Produces(MediaType.APPLICATION_JSON)
	Response getDatiColonneQteSALCorrente(@DefaultValue("0") @QueryParam("idProgetto") Long idProgetto,
			@DefaultValue("0") @QueryParam("idIter") Long idIter, @Context HttpServletRequest req)
			throws InvalidParameterException, Exception;

	/*
	 * @GET
	 * 
	 * @Path("inizializzaRendicontazione")
	 * 
	 * @Produces(MediaType.APPLICATION_JSON) Response inizializzaRendicontazione(
	 * 
	 * @DefaultValue("0") @QueryParam("idUtente") long idUtente,
	 * 
	 * @DefaultValue("0") @QueryParam("idProgetto") long idProgetto,
	 * 
	 * @DefaultValue("0") @QueryParam("idSoggetto") long idSoggetto,
	 * 
	 * @DefaultValue("0") @QueryParam("idSoggettoBeneficiario") long
	 * idSoggettoBeneficiario,
	 * 
	 * @DefaultValue("") @QueryParam("codiceRuolo") String codiceRuolo,
	 * 
	 * @Context HttpServletRequest req) throws InvalidParameterException, Exception;
	 * 
	 * @GET
	 * 
	 * @Path("home")
	 * 
	 * @Produces(MediaType.APPLICATION_JSON) Response
	 * getRendicontazioneHome(@DefaultValue("0") @QueryParam("idPrj") Long idPrj,
	 * 
	 * @DefaultValue("0") @QueryParam("idSg") Long idSg,
	 * 
	 * @DefaultValue("0") @QueryParam("idSgBen") Long idSgBen,
	 * 
	 * @DefaultValue("0") @QueryParam("idU") Long idU,
	 * 
	 * @DefaultValue("--") @QueryParam("role") String role,
	 * 
	 * @DefaultValue("--") @QueryParam("taskIdentity") String taskIdentity,
	 * 
	 * @DefaultValue("--") @QueryParam("taskName") String taskName,
	 * 
	 * @DefaultValue("--") @QueryParam("wkfAction") String wkfAction, @Context
	 * HttpServletRequest req) throws UtenteException;
	 */
}
