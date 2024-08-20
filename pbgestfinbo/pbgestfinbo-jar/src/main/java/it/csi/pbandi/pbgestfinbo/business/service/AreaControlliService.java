/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.business.service;

import it.csi.pbandi.pbgestfinbo.dto.RicercaControlliDTO;
import it.csi.pbandi.pbgestfinbo.exception.DaoException;
import it.csi.pbandi.pbgestfinbo.integration.vo.affidamenti.ControlloLocoVO;
import it.csi.pbandi.pbgestfinbo.integration.vo.affidamenti.RichiestaIntegrazioneControlloLocoVO;
import it.csi.pbandi.pbgestfinbo.integration.vo.affidamenti.RichiestaProrogaVO;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.math.BigDecimal;
import java.util.ArrayList;

@Path("/controlli")
public interface AreaControlliService {
	@POST
	@Path("/suggess")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	Response getListaSugg(
			RicercaControlliDTO suggestDTO, 
			@DefaultValue("1") @QueryParam("id") int id, 
			@Context HttpServletRequest req) throws DaoException;
	@GET
	@Path("/statoControllo")
	@Produces(MediaType.APPLICATION_JSON)
	Response getStatoControllo(@Context HttpServletRequest req) throws DaoException;
	@GET
	@Path("/suggerimentoTipovista")
	@Produces(MediaType.APPLICATION_JSON)
	Response suggerimentoTipovista(@Context HttpServletRequest req) throws DaoException;
	@POST
	@Path("/getElencoControlli")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	Response getElencoControlli(
			RicercaControlliDTO controlloDTO,
			@Context HttpServletRequest req);
	@GET
	@Path("/getControllo")
	@Produces(MediaType.APPLICATION_JSON)
	Response getControllo(
			@DefaultValue("0") @QueryParam("idControllo") BigDecimal idControllo,
			@DefaultValue("0") @QueryParam("idProgetto") BigDecimal idProgetto,
			@Context HttpServletRequest req) throws DaoException;
	@GET
	@Path("/getAltroControllo")
	@Produces(MediaType.APPLICATION_JSON)
	Response getAltroControllo(
			@DefaultValue("0") @QueryParam("idControllo") BigDecimal idControllo,
			@DefaultValue("0") @QueryParam("idProgetto") BigDecimal idProgetto,
			@Context HttpServletRequest req) throws DaoException;
	// -----  GESTIONE CONTROLLI ------------
	@POST
	@Path("/gestioneControllo")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	Response gestioneControllo(
			ControlloLocoVO controllo,
			@Context HttpServletRequest req) throws DaoException;
	@GET
	@Path("/checkRichiestaIntegrazione")
	@Produces(MediaType.APPLICATION_JSON)
	Response checkRichiestaIntegrazione(
			@DefaultValue("0") @QueryParam("idControllo") BigDecimal idControllo,
			@Context HttpServletRequest req) throws DaoException;
//	@GET
//	@Path("/getElencoAllegati")
//	@Produces(MediaType.APPLICATION_JSON)
//	Response getElencoAllegati( @DefaultValue("0") @QueryParam("idControllo") BigDecimal idControllo,@Context HttpServletRequest req) throws DaoException;
	@GET
	@Path("/chiudiRichiestaIntegr")
	@Produces(MediaType.APPLICATION_JSON)
	Response chiudiRichiestaIntegr(
			@DefaultValue("0") @QueryParam("idRichiestaIntegr") BigDecimal idRichiestaIntegr,
			@DefaultValue("0") @QueryParam("idUtente") BigDecimal idUtente,
			@Context HttpServletRequest req) throws DaoException;
	@GET
	@Path("/getRichProroga")
	@Produces(MediaType.APPLICATION_JSON)
	Response getRichProroga(
			@DefaultValue("0") @QueryParam("idRichiestaIntegr") BigDecimal idRichiestaIntegr,
			@Context HttpServletRequest req) throws DaoException;
	
	@GET
	@Path("/getElencoRichProroga")
	@Produces(MediaType.APPLICATION_JSON)
	Response getElencoRichProroga(
			@DefaultValue("0") @QueryParam("idRichiestaIntegr") BigDecimal idRichiestaIntegr,
			@Context HttpServletRequest req) throws DaoException;
//	@GET
//	@Path("/getRichProrogaAltroControllo")
//	@Produces(MediaType.APPLICATION_JSON)
//	Response getRichProrogaAltroControllo(
//			@DefaultValue("0") @QueryParam("idRichiestaIntegr") BigDecimal idRichiestaIntegr,
//			@Context HttpServletRequest req) throws DaoException;
	@POST
	@Path("/gestisciProroga")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	Response gestisciProroga(RichiestaProrogaVO proroga, 
			@DefaultValue("0") @QueryParam("idUtente") BigDecimal idUtente,
			@DefaultValue("0") @QueryParam("id") int id,
			@Context HttpServletRequest req) throws DaoException;
	@POST
	@Path("/updateControlloFinp")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	Response updateControlloFinp(
			ControlloLocoVO controllo,
			@DefaultValue("0") @QueryParam("idUtente") BigDecimal idUtente,
			@DefaultValue("0") @QueryParam("idAttivitaControllo") int idAttivitaControllo,
			@Context HttpServletRequest req) throws DaoException;
	@POST
	@Path("/salvaAltroControllo")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	Response salvaAltroControllo(
			ControlloLocoVO controllo,
			@DefaultValue("0") @QueryParam("idUtente") BigDecimal idUtente,
			@DefaultValue("0") @QueryParam("idAttivitaControllo") int idAttivitaControllo,
			@Context HttpServletRequest req) throws DaoException;
	@POST
	@Path("/updateAltroControllo")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	Response updateAltroControllo(
			ControlloLocoVO controllo,
			@DefaultValue("0") @QueryParam("idUtente") BigDecimal idUtente,
			@Context HttpServletRequest req) throws DaoException;
	@POST
	@Path("/chiamaIterAuto002")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces(MediaType.APPLICATION_JSON)
	Response chiamaIterAuto002(
			MultipartFormDataInput multipartFormData,
			@Context HttpServletRequest req) throws DaoException;
	@POST
	@Path("/avviaIterIntegrazione")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	Response avviaIterIntegrazione(
			RichiestaIntegrazioneControlloLocoVO richiesta,
			@DefaultValue("0") @QueryParam("idUtente") BigDecimal idUtente,
			@Context HttpServletRequest req) throws DaoException; 
	@POST
	@Path("/salvaDataNotifica")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	Response salvaDataNotifica(
			RichiestaIntegrazioneControlloLocoVO richiesta,
			@Context HttpServletRequest req) throws DaoException;
	@POST
	@Path("/salvaAllegato")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces(MediaType.APPLICATION_JSON)
	Response salvaAllegato(
			MultipartFormDataInput multipartFormData,
			@Context HttpServletRequest req) throws Exception;
	
	@POST
	@Path("/salvaAllegati")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces(MediaType.APPLICATION_JSON)
	Response salvaAllegati(
			MultipartFormDataInput multipartFormData,
			@Context HttpServletRequest req) throws Exception;
	
	// ------  ALTRI CONTROLLI ---------
	@GET
	@Path("/autoritaControllante")
	@Produces(MediaType.APPLICATION_JSON)
	Response getAutoritaControllante(@Context HttpServletRequest req) throws DaoException;
	@GET
	@Path("/getListaBando")
	@Produces(MediaType.APPLICATION_JSON)
	Response getListaBando(
			@DefaultValue("0") @QueryParam("idSoggetto") BigDecimal idSoggetto,
			@Context HttpServletRequest req) throws DaoException;
	@GET
	@Path("/getListaProgetto")
	@Produces(MediaType.APPLICATION_JSON)
	Response getListaProgetto(
			@DefaultValue("0") @QueryParam("idSoggetto") BigDecimal idSoggetto,
			@DefaultValue("0") @QueryParam("progBandoLinea") BigDecimal progBandoLinea,
			@Context HttpServletRequest req) throws DaoException;
	@POST
	@Path("/getElencoAltriControlli")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	Response getElencoAltriControlli(RicercaControlliDTO controlloDTO, @Context HttpServletRequest req);
	@GET
	@Path("/checkRichiestaIntegrAltroControllo")
	@Produces(MediaType.APPLICATION_JSON)
	Response checkRichiestaIntegrAltroControllo(
			@DefaultValue("0") @QueryParam("idControllo") BigDecimal idControllo,
			@Context HttpServletRequest req) throws DaoException;
	@GET
	@Path("/getElencoAllegati")
	@Produces(MediaType.APPLICATION_JSON)
	Response getElencoFile(
			@DefaultValue("0") @QueryParam("idControllo") BigDecimal idControllo,
			@DefaultValue("0") @QueryParam("idEntita") BigDecimal idEntita,
			@Context HttpServletRequest req) throws DaoException;
	
	
	@GET
	@Path("/getElencoAllegatiBeneficiario")
	@Produces(MediaType.APPLICATION_JSON)
	Response getElencoAllegatiBeneficiario(
			@DefaultValue("0") @QueryParam("idTarget") BigDecimal idControllo,
			@DefaultValue("0") @QueryParam("idEntita") BigDecimal idEntita,
			@Context HttpServletRequest req) throws DaoException;
	
	//Common
	@POST
	@Path("/avviaIterEsitoPositivoControllo")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	Response avviaIterEsitoPositivoControllo(
			@DefaultValue("0") @QueryParam("idProgetto") Long idProgetto,
			@DefaultValue("0") @QueryParam("idTarget") Long idTarget,
			@DefaultValue("0") @QueryParam("isAltriControlli") Boolean isAltriControlli,
			@Context HttpServletRequest req) throws DaoException;
}
