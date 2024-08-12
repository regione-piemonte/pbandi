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

import it.csi.csi.wrapper.UnrecoverableException;
import it.csi.pbandi.pbservizit.exception.UtenteException;
import it.csi.pbandi.pbweb.pbandisrv.exception.gestionedatididominio.GestioneDatiDiDominioException;


@Path("/decodifiche")
public interface DecodificheService {
	
	@GET
	@Path("tipologieDocumento") 
	@Produces(MediaType.APPLICATION_JSON)
	Response ottieniTipologieDocumentiDiSpesa(@DefaultValue("0") @QueryParam("idBandoLinea") int idBandoLinea);
	
	@GET
	@Path("tipologieFornitore") 
	@Produces(MediaType.APPLICATION_JSON)
	Response ottieniTipologieFornitore();
	
	@GET
	@Path("tipologieFornitorePerIdTipoDocSpesa") 
	@Produces(MediaType.APPLICATION_JSON)
	Response ottieniTipologieFornitorePerIdTipoDocSpesa(@DefaultValue("0") @QueryParam("idTipoDocumentoDiSpesa") int idTipoDocumentoDiSpesa);
	
	@GET
	@Path("fornitori") 
	@Produces(MediaType.APPLICATION_JSON)
	Response fornitori(
			@DefaultValue("0") @QueryParam("idSoggettoFornitore") long idSoggettoFornitore,
			@DefaultValue("0") @QueryParam("idTipoFornitore") long idTipoFornitore,
			@DefaultValue("")  @QueryParam("fornitoriValidi") String fornitoriValidi);
	
	@GET
	@Path("fornitoriCombo") 
	@Produces(MediaType.APPLICATION_JSON)
	Response fornitoriCombo(
			@DefaultValue("0") @QueryParam("idSoggettoFornitore") long idSoggettoFornitore,
			@DefaultValue("0") @QueryParam("idTipoFornitore") long idTipoFornitore,
			@DefaultValue("0") @QueryParam("idFornitore") long idFornitore);
	
	@GET
	@Path("fornitoriComboRicerca") 
	@Produces(MediaType.APPLICATION_JSON)
	Response fornitoriComboRicerca(
			@DefaultValue("0") @QueryParam("idProgetto") long idProgetto,
			@Context HttpServletRequest req) throws Exception;
	
	@GET
	@Path("tipologieFormaGiuridica") 
	@Produces(MediaType.APPLICATION_JSON)
	Response tipologieFormaGiuridica(
			@DefaultValue("") @QueryParam("flagPrivato") String flagPrivato) throws Exception ;
	
	@GET
	@Path("nazioni") 
	@Produces(MediaType.APPLICATION_JSON)
	Response nazioni() throws Exception ;
	
	@GET
	@Path("qualifiche") 
	@Produces(MediaType.APPLICATION_JSON)
	Response qualifiche(
			@DefaultValue("0") @QueryParam("idUtente") long idUtente) throws InvalidParameterException, Exception;
	
	// aggancio alle logiche vecchie
	@GET
	@Path("attivitaCombo") 
	@Produces(MediaType.APPLICATION_JSON)
	Response attivitaCombo(
			@DefaultValue("0") @QueryParam("idUtente") long idUtente,
			@Context HttpServletRequest req) throws UtenteException, GestioneDatiDiDominioException, UnrecoverableException;
	
	@GET
	@Path("elencoTask") 
	@Produces(MediaType.APPLICATION_JSON)
	Response elencoTask(
			@DefaultValue("0") @QueryParam("idProgetto") long idProgetto,
			@DefaultValue("0") @QueryParam("idUtente") long idUtente,
			@Context HttpServletRequest req) throws InvalidParameterException, Exception;
	
	@GET
	@Path("statiDocumentoDiSpesa") 
	@Produces(MediaType.APPLICATION_JSON)
	Response statiDocumentoDiSpesa() throws Exception;
	
	@GET
	@Path("tipiDocumentoSpesa") 
	@Produces(MediaType.APPLICATION_JSON)
	Response tipiDocumentoSpesa() throws Exception;
	
	@GET
	@Path("tipiDocumentoIndexUploadable") 
	@Produces(MediaType.APPLICATION_JSON)
	Response tipiDocumentoIndexUploadable() throws Exception;
	
}
