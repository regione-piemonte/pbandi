/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbworkspace.business.service;

import java.security.InvalidParameterException;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.jboss.resteasy.annotations.jaxrs.QueryParam;

import it.csi.pbandi.pbworkspace.dto.schedaProgetto.SchedaProgetto;
import it.csi.pbandi.pbworkspace.exception.UtenteException;
import it.csi.pbandi.pbworkspace.integration.request.AvviaProgettiRequest;
import it.csi.pbandi.pbworkspace.integration.request.RicercaProgettiRequest;
import it.csi.pbandi.pbworkspace.integration.request.SalvaSchedaProgettoRequest;

@Path("/schedaProgetto")
public interface SchedaProgettoService {
	
	@GET
	@Path("inizializzaSchedaProgetto")
	@Produces(MediaType.APPLICATION_JSON)
	public Response inizializzaSchedaProgetto(
			@QueryParam("progrBandoLineaIntervento") Long progrBandoLineaIntervento,
			@QueryParam("idProgetto") Long idProgetto,
			@QueryParam("idSoggetto") Long idSoggetto,
			@QueryParam("codiceRuolo") String codiceRuolo,			
			@Context HttpServletRequest req) throws InvalidParameterException, Exception;
	
	@GET
	@Path("inizializzaCombo")
	@Produces(MediaType.APPLICATION_JSON)
	public Response inizializzaCombo(
			@QueryParam("progrBandoLineaIntervento") Long progrBandoLineaIntervento,			
			@Context HttpServletRequest req) throws InvalidParameterException, Exception;
	
	@GET
	@Path("popolaComboAttivitaAteco")
	@Produces(MediaType.APPLICATION_JSON)
	public Response popolaComboAttivitaAteco(
			@QueryParam("idSettoreAttivita") Long idSettoreAttivita,			
			@Context HttpServletRequest req) throws InvalidParameterException, Exception;
	
	@GET
	@Path("popolaComboObiettivoGeneraleQsn")
	@Produces(MediaType.APPLICATION_JSON)
	public Response popolaComboObiettivoGeneraleQsn(
			@QueryParam("idListaPrioritaQsn") Long idListaPrioritaQsn,			
			@Context HttpServletRequest req) throws InvalidParameterException, Exception;

	@GET
	@Path("popolaComboObiettivoSpecificoQsn")
	@Produces(MediaType.APPLICATION_JSON)
	public Response popolaComboObiettivoSpecificoQsn(
			@QueryParam("idObiettivoGenerale") Long idObiettivoGenerale,			
			@Context HttpServletRequest req) throws InvalidParameterException, Exception;
	
	@GET
	@Path("popolaComboClassificazioneRA")
	@Produces(MediaType.APPLICATION_JSON)
	public Response popolaComboClassificazioneRA(
			@QueryParam("idObiettivoTematico") Long idObiettivoTematico,			
			@Context HttpServletRequest req) throws InvalidParameterException, Exception;
	
	@GET
	@Path("popolaComboSottoSettoreCipe")
	@Produces(MediaType.APPLICATION_JSON)
	public Response popolaComboSottoSettoreCipe(
			@QueryParam("idSettoreCipe") Long idSettoreCipe,			
			@Context HttpServletRequest req) throws InvalidParameterException, Exception;
	
	@GET
	@Path("popolaComboCategoriaCipe")
	@Produces(MediaType.APPLICATION_JSON)
	public Response popolaComboCategoriaCipe(
			@QueryParam("idSottoSettoreCipe") Long idSottoSettoreCipe,			
			@Context HttpServletRequest req) throws InvalidParameterException, Exception;
	
	@GET
	@Path("popolaComboTipologiaCipe")
	@Produces(MediaType.APPLICATION_JSON)
	public Response popolaComboTipologiaCipe(
			@QueryParam("idNaturaCipe") Long idNaturaCipe,
			@Context HttpServletRequest req) throws InvalidParameterException, Exception;

	@GET
	@Path("popolaComboRegione")
	@Produces(MediaType.APPLICATION_JSON)
	public Response popolaComboRegione(
			@Context HttpServletRequest req) throws InvalidParameterException, Exception;
	
	@GET
	@Path("popolaComboRegioneNascita")
	@Produces(MediaType.APPLICATION_JSON)
	public Response popolaComboRegioneNascita(
			@Context HttpServletRequest req) throws InvalidParameterException, Exception;
	
	@GET
	@Path("popolaComboProvincia")
	@Produces(MediaType.APPLICATION_JSON)
	public Response popolaComboProvincia(
			@QueryParam("idRegione") Long idRegione,
			@Context HttpServletRequest req) throws InvalidParameterException, Exception;
	
	@GET
	@Path("popolaComboProvinciaNascita")
	@Produces(MediaType.APPLICATION_JSON)
	public Response popolaComboProvinciaNascita(
			@QueryParam("idRegione") Long idRegione,
			@Context HttpServletRequest req) throws InvalidParameterException, Exception;

	@GET
	@Path("popolaComboComuneEstero")
	@Produces(MediaType.APPLICATION_JSON)
	public Response popolaComboComuneEstero(
			@QueryParam("idNazione") Long idNazione,
			@Context HttpServletRequest req) throws InvalidParameterException, Exception;
	
	@GET
	@Path("popolaComboComuneEsteroNascita")
	@Produces(MediaType.APPLICATION_JSON)
	public Response popolaComboComuneEsteroNascita(
			@QueryParam("idNazione") Long idNazione,
			@Context HttpServletRequest req) throws InvalidParameterException, Exception;
	
	@GET
	@Path("popolaComboComuneItaliano")
	@Produces(MediaType.APPLICATION_JSON)
	public Response popolaComboComuneItaliano(
			@QueryParam("idProvincia") Long idProvincia,
			@Context HttpServletRequest req) throws InvalidParameterException, Exception;

	@GET
	@Path("popolaComboComuneItalianoNascita")
	@Produces(MediaType.APPLICATION_JSON)
	public Response popolaComboComuneItalianoNascita(
			@QueryParam("idProvincia") Long idProvincia,
			@Context HttpServletRequest req) throws InvalidParameterException, Exception;
	
	@GET
	@Path("popolaComboDenominazioneSettori")
	@Produces(MediaType.APPLICATION_JSON)
	public Response popolaComboDenominazioneSettori(
			@QueryParam("idEnte") Long idEnte,
			@Context HttpServletRequest req) throws InvalidParameterException, Exception;
	
	@GET
	@Path("ricercaBeneficiarioCsp")
	@Produces(MediaType.APPLICATION_JSON)
	public Response ricercaBeneficiarioCsp(
			@QueryParam("codiceFiscale") String codiceFiscale,
			@Context HttpServletRequest req) throws InvalidParameterException, Exception;
	
	@GET
	@Path("ricercaRapprLegaleCsp")
	@Produces(MediaType.APPLICATION_JSON)
	public Response ricercaRapprLegaleCsp(
			@QueryParam("codiceFiscale") String codiceFiscale,
			@Context HttpServletRequest req) throws InvalidParameterException, Exception;
	
	@GET
	@Path("popolaComboDenominazioneEnteDipUni")
	@Produces(MediaType.APPLICATION_JSON)
	public Response popolaComboDenominazioneEnteDipUni(
			@QueryParam("idAteneo") Long idAteneo,
			@Context HttpServletRequest req) throws InvalidParameterException, Exception;
	
	@POST
	@Path("salvaSchedaProgetto")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response salvaSchedaProgetto(
			SalvaSchedaProgettoRequest salvaSchedaProgettoRequest,
			@Context HttpServletRequest req) throws UtenteException, InvalidParameterException, Exception;

	@GET
	@Path("verificaNumeroDomandaUnico")
	@Produces(MediaType.APPLICATION_JSON)
	public Response verificaNumeroDomandaUnico(
			@QueryParam("numDomanda") String numDomanda,
			@QueryParam("idProgetto") Long idProgetto,
			@QueryParam("idBandoLinea") Long idBandoLinea,
			@Context HttpServletRequest req) throws InvalidParameterException, Exception;
	
	@GET
	@Path("verificaCupUnico")
	@Produces(MediaType.APPLICATION_JSON)
	public Response verificaCupUnico(
			@QueryParam("cup") String numDomanda,
			@QueryParam("idProgetto") Long idProgetto,
			@Context HttpServletRequest req) throws InvalidParameterException, Exception;
	
	@GET
	@Path("caricaInfoDirezioneRegionale")
	@Produces(MediaType.APPLICATION_JSON)
	public Response caricaInfoDirezioneRegionale(
			@QueryParam("idEnteCompetenza") Long idEnteCompetenza,
			@Context HttpServletRequest req) throws InvalidParameterException, Exception;
	
	@GET
	@Path("caricaInfoPubblicaAmministrazione")
	@Produces(MediaType.APPLICATION_JSON)
	public Response caricaInfoPubblicaAmministrazione(
			@QueryParam("idEnteCompetenza") Long idEnteCompetenza,
			@Context HttpServletRequest req) throws InvalidParameterException, Exception;
}
