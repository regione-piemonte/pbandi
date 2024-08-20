/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.business.service;

import it.csi.pbandi.pbgestfinbo.integration.vo.search.ControlloNonApplicabileVO;
import it.csi.pbandi.pbgestfinbo.integration.vo.search.NuovaRichiestaVO;
import it.csi.pbandi.pbgestfinbo.integration.vo.search.SaveControlloPreErogazioneVO;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.math.BigDecimal;

@Path("/controlliPreErogazione")
public interface ControlliPreErogazioneService {

	@GET
	@Path("/fetchData")
	@Produces(MediaType.APPLICATION_JSON)
	Response fetchData(
			@DefaultValue("") @QueryParam("idProposta") Long idProposta,
			@Context HttpServletRequest req) throws Exception;

	@GET
	@Path("/getBloccoSoggetto")
	@Produces(MediaType.APPLICATION_JSON)
	Response getBloccoSoggetto(
			@DefaultValue("") @QueryParam("idSoggetto") Long idSoggetto,
			@Context HttpServletRequest req) throws Exception;

	@GET
	@Path("/getBloccoDomanda")
	@Produces(MediaType.APPLICATION_JSON)
	Response getBloccoDomanda(
			@DefaultValue("") @QueryParam("idSoggetto") Long idSoggetto,
			@DefaultValue("") @QueryParam("numeroDomanda") String numeroDomanda,
			@Context HttpServletRequest req) throws Exception;

	@GET
	@Path("/getEsitoDurc")
	@Produces(MediaType.APPLICATION_JSON)
	Response getControlloDurc(
			@DefaultValue("") @QueryParam("idProgetto") Long idProgetto,
			@Context HttpServletRequest req) throws Exception;

	@GET
	@Path("/getEsitoAntimafia")
	@Produces(MediaType.APPLICATION_JSON)
	Response getControlloAntimafia(
			@DefaultValue("") @QueryParam("idProgetto") Long idProgetto,
			@Context HttpServletRequest req) throws Exception;

	@GET
	@Path("/getEsitoDeggendorf")
	@Produces(MediaType.APPLICATION_JSON)
	Response getControlloDeggendorf(
			@DefaultValue("") @QueryParam("idSoggetto") Long idSoggetto,
			@Context HttpServletRequest req) throws Exception;
	
	@GET
	@Path("/getControlloNonApplicabile")
	@Produces(MediaType.TEXT_PLAIN)
	Response getControlloNonApplicabile(
			@DefaultValue("") @QueryParam("idProposta") Long idProposta,
			@DefaultValue("") @QueryParam("idTipoRichiesta") Long idTipoRichiesta,
			@Context HttpServletRequest req) throws Exception;

	@POST
	@Path("/inviaRichiesta")
	@Produces(MediaType.APPLICATION_JSON)
	Response inviaRichiesta(
			NuovaRichiestaVO nuovaRichiestaVO,
			@Context HttpServletRequest req) throws Exception;
	

	@POST
	@Path("/setControlloNonApplicabile")
	@Produces(MediaType.APPLICATION_JSON)
	Response controlloNonApplicabile(
			ControlloNonApplicabileVO controlloNonApplicabileVO,
			@Context HttpServletRequest req) throws Exception;

	@POST
	@Path("/saveControlliPreErogazione")
	@Produces(MediaType.APPLICATION_JSON)
	Response saveControlliPreErogazione(
			SaveControlloPreErogazioneVO saveControlloPreErogazioneVO,
			@Context HttpServletRequest req) throws Exception;

	@GET
	@Path("/checkControlliPreErogazione")
	@Produces(MediaType.APPLICATION_JSON)
	Response checkControlliPreErogazione(
			@DefaultValue("") @QueryParam("idProposta") Long idProposta,
			@Context HttpServletRequest req) throws Exception;

	@POST
	@Path("/avviaIterEsitoValidazione")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces(MediaType.APPLICATION_JSON)
	Response avviaIterEsitoValidazione(
			@DefaultValue("") @QueryParam("idProposta") Long idProposta,
			MultipartFormDataInput multipartFormData,
			@Context HttpServletRequest req) throws Exception;

	@POST
	@Path("/avviaIterCommunicazioneIntervento")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces(MediaType.APPLICATION_JSON)
	Response avviaIterCommunicazioneIntervento(
			@DefaultValue("") @QueryParam("idProposta") Long idProposta,
			MultipartFormDataInput multipartFormData,
			@Context HttpServletRequest req) throws Exception;
	@POST
	@Path("/creaDistintaInterventi")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces(MediaType.APPLICATION_JSON)
	Response creaDistintaInterventi(
			MultipartFormDataInput multipartFormData,
			@Context HttpServletRequest req) throws Exception;

	@GET
	@Path("/getDestinatariInterventi")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	Response getDestinatariInterventi(
			@Context HttpServletRequest req) throws Exception;

	@GET
	@Path("/salvaImportoDaErogare")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	Response salvaImportoDaErogare(
			@DefaultValue("") @QueryParam("idProposta") Long idProposta,
			@DefaultValue("") @QueryParam("importoDaErogare") BigDecimal importoDaErogare,
			@Context HttpServletRequest req) throws Exception;
}
