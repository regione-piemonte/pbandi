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

import it.csi.pbandi.pbweb.dto.FornitoreFormDTO;
//import it.csi.pbandi.pbweb.integration.dao.request.AssociaFilesRequest;
import it.csi.pbandi.pbservizit.integration.dao.request.AssociaFilesRequest;
import it.csi.pbandi.pbweb.integration.dao.request.SalvaQualificaRequest;
import it.csi.pbandi.pbweb.integration.dao.request.SalvaQualificheRequest;
import it.csi.pbandi.pbweb.pbandisrv.dto.gestionefornitori.FornitoreDTO;

@Path("/fornitore")
public interface FornitoreService {
	
	@GET
	@Path("testTransactional") 
	@Produces(MediaType.APPLICATION_JSON)
	Response testTransactional(@Context HttpServletRequest req) throws RuntimeException, InvalidParameterException, Exception;
	
	@GET
	@Path("alberoAttivitaAteco") 
	@Produces(MediaType.APPLICATION_JSON)
	Response alberoAttivitaAteco(
			@DefaultValue("0") @QueryParam("idUtente") long idUtente,
			@Context HttpServletRequest req) throws InvalidParameterException, Exception;
	
	@GET
	@Path("attivitaAteco") 
	@Produces(MediaType.APPLICATION_JSON)
	Response attivitaAteco(
			@DefaultValue("0") @QueryParam("idUtente") long idUtente,
			@Context HttpServletRequest req) throws InvalidParameterException, Exception;
	
	@POST
	@Path("salvaFornitore")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	Response salvaFornitore(
			FornitoreFormDTO fornitoreFormDTO,
			@DefaultValue("0") @QueryParam("idUtente") long idUtente,
			@DefaultValue("0") @QueryParam("idSoggettoBeneficiario") long idSoggettoBeneficiario,
			@Context HttpServletRequest request) 
			throws InvalidParameterException, Exception;

	@GET
	@Path("cercaFornitore") 
	@Produces(MediaType.APPLICATION_JSON)
	Response cercaFornitore(
			@QueryParam("idFornitore") Long idFornitore,
			@QueryParam("idSoggettoBeneficiario") Long idSoggettoBeneficiario,
			@QueryParam("idProgetto") Long idProgetto,
			@Context HttpServletRequest req) throws InvalidParameterException, Exception;

	@GET
	@Path("qualificheFornitore")
	@Produces(MediaType.APPLICATION_JSON)
	Response qualificheFornitore(
			@DefaultValue("0") @QueryParam("idFornitore") long idFornitore,
			@DefaultValue("0") @QueryParam("idUtente") long idUtente,
			@Context HttpServletRequest req) throws InvalidParameterException, Exception;
	
	@POST
	@Path("salvaQualifica")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	Response salvaQualifica(
			SalvaQualificaRequest salvaQualificaRequest,
			@Context HttpServletRequest request) 
			throws InvalidParameterException, Exception;
	
	@POST
	@Path("salvaQualifiche")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	Response salvaQualifiche(
			SalvaQualificheRequest salvaQualificheRequest,
			@Context HttpServletRequest request) 
			throws InvalidParameterException, Exception;
	
	@GET
	@Path("fattureFornitore")
	@Produces(MediaType.APPLICATION_JSON)
	Response fattureFornitore(
			@DefaultValue("0") @QueryParam("idFornitore") long idFornitore,
			@DefaultValue("0") @QueryParam("idProgetto") long idProgetto,
			@Context HttpServletRequest req) throws InvalidParameterException, Exception;
	
	@GET
	@Path("nazioni")
	@Produces(MediaType.APPLICATION_JSON)
	Response nazioni(
			@Context HttpServletRequest req) throws InvalidParameterException, Exception;
	
	@POST
	@Path("ricercaElencoFornitori")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	Response ricercaElencoFornitori(
			FornitoreDTO filtro,
			@QueryParam("idSoggettoBeneficiario") Long idSoggettoBeneficiario,
			@QueryParam("idProgetto") Long idProgetto,
			@Context HttpServletRequest request) throws InvalidParameterException, Exception;
	
	@GET
	@Path("disassociaAllegatoFornitore")
	@Produces(MediaType.APPLICATION_JSON)
	Response disassociaAllegatoFornitore(
			@QueryParam("idDocumentoIndex") Long idDocumentoIndex, 
			@QueryParam("idFornitore") Long idFornitore, 
			@QueryParam("idProgetto") Long idProgetto,
			@Context HttpServletRequest req) throws InvalidParameterException, Exception;
	
	@POST
	@Path("associaAllegatiAFornitore")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	Response associaAllegatiAFornitore(
			AssociaFilesRequest associaFilesRequest,
			@Context HttpServletRequest request) throws InvalidParameterException, Exception;
	
	@GET
	@Path("eliminaFornitore")
	@Produces(MediaType.APPLICATION_JSON)
	Response eliminaFornitore(
			@QueryParam("idFornitore") Long idFornitore, 
			@Context HttpServletRequest req) throws InvalidParameterException, Exception;
	
	@GET
	@Path("esistonoDocumentiAssociatiAQualificaFornitore")
	@Produces(MediaType.APPLICATION_JSON)
	Response esistonoDocumentiAssociatiAQualificaFornitore(
			@QueryParam("progrFornitoreQualifica") Long progrFornitoreQualifica, 
			@Context HttpServletRequest req) throws InvalidParameterException, Exception;
	
	@GET
	@Path("eliminaQualifica")
	@Produces(MediaType.APPLICATION_JSON)
	Response eliminaQualifica(
			@QueryParam("progrFornitoreQualifica") Long progrFornitoreQualifica, 
			@Context HttpServletRequest req) throws InvalidParameterException, Exception;
}
