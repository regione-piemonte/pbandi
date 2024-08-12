package it.csi.pbandi.pbweb.business.service;

import java.math.BigDecimal;
import java.security.InvalidParameterException;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;

import it.csi.pbandi.pbservizit.exception.ErroreGestitoException;
import it.csi.pbandi.pbservizit.exception.UtenteException;
import it.csi.pbandi.pbweb.dto.documentiDiProgetto.FiltroRicercaDocumentiDTO;

@Path("/documentiDiProgetto")
public interface DocumentiDiProgettoService {

	@GET
	@Path("inizializzaDocumentiDiProgetto")
	@Produces(MediaType.APPLICATION_JSON)
	Response inizializzaDocumentiDiProgetto(@QueryParam("codiceRuolo") String codiceRuolo,
			@Context HttpServletRequest req) throws InvalidParameterException, Exception;

	@GET
	@Path("beneficiari")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getBeneficiariDocProgettoByDenomOrIdBen(@QueryParam("denominazione") String denominazione,
			@QueryParam("idBeneficiario") Long idBeneficiario, @Context HttpServletRequest req)
			throws UtenteException, ErroreGestitoException, InvalidParameterException, Exception;

	@GET
	@Path("progettiBeneficiario")
	@Produces(MediaType.APPLICATION_JSON)
	Response progettiBeneficiario(@QueryParam("codiceRuolo") String codiceRuolo,
			@QueryParam("idSoggettoBeneficiario") Long idSoggettoBeneficiario, @Context HttpServletRequest req)
			throws InvalidParameterException, Exception;

	@POST
	@Path("cercaDocumenti")
	@Produces(MediaType.APPLICATION_JSON)
	Response cercaDocumenti(FiltroRicercaDocumentiDTO filtroRicercaDocumentiDTO, @Context HttpServletRequest req)
			throws InvalidParameterException, Exception;

	@POST
	@Path("salvaUpload")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces(MediaType.APPLICATION_JSON)
	public Response salvaUpload(MultipartFormDataInput multipartFormData, @Context HttpServletRequest req)
			throws InvalidParameterException, Exception;

	@GET
	@Path("allegati")
	@Produces(MediaType.APPLICATION_JSON)
	Response allegati(@QueryParam("codTipoDocIndex") String codTipoDocIndex,
			@QueryParam("idDocumentoIndex") Long idDocumentoIndex, @Context HttpServletRequest req)
			throws InvalidParameterException, Exception;

	@GET
	@Path("allegatiIntegrazioniDS")
	@Produces(MediaType.APPLICATION_JSON)
	Response allegatiIntegrazioniDS(@QueryParam("idDichiarazioneSpesa") Long idDichiarazioneSpesa,
			@QueryParam("codTipoDocIndex") String codTipoDocIndex, @Context HttpServletRequest req)
			throws InvalidParameterException, Exception;

	@GET
	@Path("allegatiVerbaleChecklist")
	@Produces(MediaType.APPLICATION_JSON)
	Response allegatiVerbaleChecklist(@QueryParam("codTipoDocIndex") String codTipoDocIndex,
			@QueryParam("idChecklist") Long idChecklist, @QueryParam("idDocumentoIndex") Long idDocIndex,
			@Context HttpServletRequest req) throws InvalidParameterException, Exception;

	@GET
	@Path("getAllegatiChecklist")
	@Produces(MediaType.APPLICATION_JSON)
	Response getAllegatiChecklist(@QueryParam("codTipoDocIndex") String codTipoDocIndex,
			@QueryParam("idChecklist") Long idChecklist, @QueryParam("idDocumentoIndex") Long idDocIndex,
			@Context HttpServletRequest req) throws Exception;

	@GET
	@Path("allegatiVerbaleChecklistAffidamento")
	@Produces(MediaType.APPLICATION_JSON)
	Response allegatiVerbaleChecklistAffidamento(@QueryParam("codTipoDocIndex") String codTipoDocIndex,
			@QueryParam("idDocumentoIndex") Long idDocumentoIndex, @Context HttpServletRequest req)
			throws InvalidParameterException, Exception;

	@GET
	@Path("cancellaFileConVisibilita")
	@Produces(MediaType.APPLICATION_JSON)
	Response cancellaFileConVisibilita(@QueryParam("idDocumentoIndex") Long idDocumentoIndex,
			@Context HttpServletRequest req) throws InvalidParameterException, Exception;

	@GET
	@Path("cancellaRecordDocumentoIndex")
	@Produces(MediaType.APPLICATION_JSON)
	Response cancellaRecordDocumentoIndex(@QueryParam("idDocumentoIndex") Long idDocumentoIndex,
			@Context HttpServletRequest req) throws InvalidParameterException, Exception;

	@GET
	@Path("codiceStatoDocumentoIndex")
	@Produces(MediaType.APPLICATION_JSON)
	Response codiceStatoDocumentoIndex(@QueryParam("idDocumentoIndex") Long idDocumentoIndex,
			@Context HttpServletRequest req) throws InvalidParameterException, Exception;

	@GET
	@Path("salvaDocumentoACTA")
	@Produces(MediaType.APPLICATION_JSON)
	Response salvaDocumentoACTA(@QueryParam("indiceClassificazioneEsteso") String indiceClassificazioneEsteso,
			@QueryParam("idProgetto") Long idProgetto, @Context HttpServletRequest req)
			throws InvalidParameterException, Exception;

	@GET
	@Path("leggiFileActa")
	@Produces(MediaType.APPLICATION_JSON)
	Response leggiFileActa(@QueryParam("idDocumentoIndex") Long idDocumentoIndex, @Context HttpServletRequest req)
			throws InvalidParameterException, Exception;

	// operazioni che wrappano le corrispondenti di FINDOM/finservrest
	@GET
	@Path("/documento")
	@Produces({ "multipart/form-data" })
	Response getDocumento(@QueryParam("idDocumento") BigDecimal idDocumento,
			@QueryParam("fonteDocumento") String fonteDocumento, @Context HttpServletRequest req);

	@GET
	@Path("/documentoList")
	@Produces({ "application/json" })
	Response getDocumentoList(@QueryParam("idDomanda") String idDomanda, @Context HttpServletRequest req);

	@GET
	@Path("allegatiByTipoDoc")
	@Produces(MediaType.APPLICATION_JSON)
	Response getAllegatiByTipoDoc(@QueryParam("idTarget") Long idTarget,
			@QueryParam("idProgetto") Long idProgetto, @QueryParam("descBreveTipoDoc") String descBreveTipoDoc,
			@Context HttpServletRequest req) throws InvalidParameterException, Exception;
}
