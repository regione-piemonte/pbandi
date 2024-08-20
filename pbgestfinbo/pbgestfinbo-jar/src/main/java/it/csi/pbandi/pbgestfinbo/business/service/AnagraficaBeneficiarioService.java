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

import org.springframework.web.bind.annotation.RequestBody;

import it.csi.pbandi.pbgestfinbo.exception.DaoException;
import it.csi.pbandi.pbgestfinbo.exception.ErroreGestitoException;
import it.csi.pbandi.pbgestfinbo.exception.RecordNotFoundException;
import it.csi.pbandi.pbgestfinbo.integration.vo.BloccoVO;
import it.csi.pbandi.pbgestfinbo.integration.vo.search.AnagraficaBeneficiarioPfVO;
import it.csi.pbandi.pbgestfinbo.integration.vo.search.AnagraficaBeneficiarioVO;
import it.csi.pbandi.pbgestfinbo.integration.vo.search.UpdateAnagraficaBeneficiarioPfVO;
import it.csi.pbandi.pbgestfinbo.integration.vo.search.UpdateAnagraficaBeneficiarioPgVO;

@Path("/anagrafica")
public interface AnagraficaBeneficiarioService {
	
	
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////// SERVIZI PER RICERCA E DETTAGLIO /////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	@GET
	@Path("/getAnagBeneficiario") 
	@Produces(MediaType.APPLICATION_JSON)
	Response getAnagBeneficiario(
			@DefaultValue("0") @QueryParam("idSoggetto") Long idSoggetto, 
			@DefaultValue("0") @QueryParam("idProgetto") Long idProgetto, 
			@DefaultValue("0") @QueryParam("idDomanda") String numDomanda, 
	        @Context HttpServletRequest req) throws DaoException;
	
	@GET
	@Path("/getAltriDati") 
	@Produces(MediaType.APPLICATION_JSON)
	Response getAltriDati(
			@DefaultValue("0") @QueryParam("idSoggetto") Long idSoggetto,
			@DefaultValue("0") @QueryParam("idEnteGiuridico") Long idEnteGiuridico, 
			@DefaultValue("0") @QueryParam("numeroDomanda") String numeroDomanda,
	        @Context HttpServletRequest req) throws DaoException;
	
	
	@GET
	@Path("/getDettaglioImpresa") 
	@Produces(MediaType.APPLICATION_JSON)
	Response getDettaglioImpresa(
			@DefaultValue("0") @QueryParam("idSoggetto") String idSoggetto,
			@DefaultValue("0") @QueryParam("anno") BigDecimal anno,
	        @Context HttpServletRequest req) throws DaoException;
	
	/*@GET
	@Path("/getDimensioneImpresa") 
	@Produces(MediaType.APPLICATION_JSON)
	Response getDimensioneImpresa(
			@DefaultValue("0") @QueryParam("idSoggetto") Long idSoggetto, 
			@DefaultValue("0") @QueryParam("numeroDomanda") Long numeroDomanda, 
	        @Context HttpServletRequest req) throws DaoException;*/
	
	/*@GET
	@Path("/getDurc") 
	@Produces(MediaType.APPLICATION_JSON)
	Response getDurc(
			@DefaultValue("0") @QueryParam("idSoggetto") Long idSoggetto, 
	        @Context HttpServletRequest req) throws DaoException;*/
	
	@GET
	@Path("/getIscrizioneRegistroImprese") 
	@Produces(MediaType.APPLICATION_JSON)
	Response getIscrizioneRegistroImprese(
			@QueryParam("idSoggetto") Long idSoggetto, 
	        @Context HttpServletRequest req) throws DaoException;
	
	@GET
	@Path("/getAnagBeneFisico") 
	@Produces(MediaType.APPLICATION_JSON)
	Response getAnagBeneFisico(
			@DefaultValue("0") @QueryParam("idSoggetto") Long idSoggetto,
			@DefaultValue("0") @QueryParam("idProgetto") Long idProgetto, 
			@DefaultValue("0") @QueryParam("idDomanda") String numDomanda,
	        @Context HttpServletRequest req) throws DaoException;
	
	
	@GET
	@Path("/getElencoDomandeProgetti") 
	@Produces(MediaType.APPLICATION_JSON)
	Response getElencoDomandeProgetti(
			@DefaultValue("0") @QueryParam("idSoggetto") Long idSoggetto, 
			@DefaultValue("0") @QueryParam("enteGiuridico") boolean isEnteGiuridico,
	        @Context HttpServletRequest req) throws DaoException;
	
	@GET
	@Path("/getStatoDomanda") 
	@Produces(MediaType.APPLICATION_JSON)
	Response getStatoDomanda(
			@DefaultValue("0") @QueryParam("idSoggetto") Long idSoggetto, 
			@DefaultValue("0") @QueryParam("idDomanda") Long idDomanda, 
	        @Context HttpServletRequest req) throws DaoException;
	
	@GET
	@Path("/getNazioni") 
	@Produces(MediaType.APPLICATION_JSON)
	Response getNazioni(
			@DefaultValue("0") 
	        @Context HttpServletRequest req) throws DaoException;
	@GET
	@Path("/getRegioni") 
	@Produces(MediaType.APPLICATION_JSON)
	Response getRegioni(
			@DefaultValue("0") 
	        @Context HttpServletRequest req) throws DaoException;
	@GET
	@Path("/getProvincie") 
	@Produces(MediaType.APPLICATION_JSON)
	Response getProvincie(
			@DefaultValue("0") 
	        @Context HttpServletRequest req) throws DaoException;
	@GET
	@Path("/getComuni") 
	@Produces(MediaType.APPLICATION_JSON)
	Response getComuni(
			@DefaultValue("0") @QueryParam("idProvincia") String idProvincia,
	        @Context HttpServletRequest req) throws DaoException;
	
	
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////// SERVIZI PER MODIFICA  ///////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	
//	@POST
//	@Path("/updateAnagBeneGiuridico")
//	@Consumes(MediaType.APPLICATION_JSON)
//	@Produces(MediaType.APPLICATION_JSON)
//	public Response updateAnagBeneGiuridico( AnagraficaBeneficiarioVO anag,
//			@DefaultValue("0") @QueryParam("idSoggetto") String idSoggetto, 
//			@DefaultValue("0") @QueryParam("idDomanda") String idDomanda,
//			HttpServletRequest req)
//			throws ErroreGestitoException,  RecordNotFoundException;
	
	@POST
	@Path("/updateAnagBeneGiuridico")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	Response updateAnagBeneGiuridico(
			AnagraficaBeneficiarioVO anag,
			@DefaultValue("0") @QueryParam("idSoggetto") String idSoggetto, 
			@DefaultValue("0") @QueryParam("idDomanda") String numeroDomanda,
			@Context HttpServletRequest request) 
					throws InvalidParameterException, Exception;
	
	
	@POST
	@Path("/updateAnagraficaPF")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	Response updateAnagraficaPF(
			AnagraficaBeneficiarioPfVO anag,
			@DefaultValue("0") @QueryParam("idSoggetto") String idSoggetto, 
			@DefaultValue("0") @QueryParam("idDomanda") String idDomanda,
			@Context HttpServletRequest request) 
					throws InvalidParameterException, Exception;
	
	
	
	
	
	////// BLOCCCHI ////////////////
	
	@GET
	@Path("/getElencoBlocchi") 
	@Produces(MediaType.APPLICATION_JSON)
	Response getElencoBlocchi(
			@DefaultValue("0") @QueryParam("idSoggetto") BigDecimal idSoggetto, 
	        @Context HttpServletRequest req) throws DaoException;

	
	@GET
	@Path("/getStoricoBlocchi") 
	@Produces(MediaType.APPLICATION_JSON)
	Response getStoricoBlocchi(
			@DefaultValue("0") @QueryParam("idSoggetto") Long idSoggetto, 
	        @Context HttpServletRequest req) throws DaoException;
	@GET
	@Path("/getListaCausali")
	@Produces(MediaType.APPLICATION_JSON)
	Response getListaCausali(@DefaultValue("0") @QueryParam("idSoggetto") Long idSoggetto, 
	        @Context HttpServletRequest req) throws DaoException;
	
	@POST
	@Path("/insertBlocco")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	Response insertBlocco(
			BloccoVO bloccoVO,
			@Context HttpServletRequest request) 
			throws InvalidParameterException, Exception;
	@POST
	@Path("/modificaBlocco")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	Response modificaBlocco(
			BloccoVO bloccoVO,
			@Context HttpServletRequest request) 
					throws InvalidParameterException, Exception;
	
	////////// SOGGETTI CORRELATI INDIPENDENTI DA DOMANDA//////////////////////
	
	@GET
	@Path("/getElencoSoggCorrIndipDaDomanda")
	@Produces(MediaType.APPLICATION_JSON)
	Response getElencoSoggCorrIndipDaDomanda(@DefaultValue("0") @QueryParam("idDomanda") String idDomanda, 
			@DefaultValue("0") @QueryParam("idSoggetto") String idSoggetto,
			@DefaultValue("0") @QueryParam("progSoggDomanda") String progSoggDomanda,
	        @Context HttpServletRequest req) throws DaoException;
	
	@GET
	@Path("/getSoggCorrIndipDaDomandaEG")
	@Produces(MediaType.APPLICATION_JSON)
	Response getSoggCorrIndipDaDomandaEG(@DefaultValue("0") @QueryParam("idDomanda") String idDomanda, 
			@DefaultValue("0") @QueryParam("idSoggetto") String idSoggetto,
			@DefaultValue("0") @QueryParam("idSoggCorr") BigDecimal idSoggCorr,
			@Context HttpServletRequest req) throws DaoException;
	
	@GET
	@Path("/getSoggCorrIndipDaDomandaPF")
	@Produces(MediaType.APPLICATION_JSON)
	Response getSoggCorrIndipDaDomandaPF(@DefaultValue("0") @QueryParam("idDomanda") String idDomanda, 
			@DefaultValue("0") @QueryParam("idSoggetto") String idSoggetto,
			@Context HttpServletRequest req) throws DaoException;
	
	
	@POST
	@Path("/modificaSoggCorrIndipDaDomandaEG")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	Response modificaSoggCorrIndipDaDomandaEG(
			AnagraficaBeneficiarioVO anag,
			@DefaultValue("0") @QueryParam("idSoggetto") String idSoggetto, 
			@DefaultValue("0") @QueryParam("idDomanda") String idDomanda,
			@DefaultValue("0") @QueryParam("idSoggCorr") BigDecimal idSoggCorr,
			@Context HttpServletRequest request) 
					throws InvalidParameterException, Exception;
	
	@POST
	@Path("/modificaSoggCorrIndipDaDomandaPF")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	Response modificaSoggCorrIndipDaDomandaPF(
			AnagraficaBeneficiarioPfVO anag,
			@DefaultValue("0") @QueryParam("idSoggetto") String idSoggetto, 
			@DefaultValue("0") @QueryParam("idDomanda") String idDomanda,
			@Context HttpServletRequest request) 
					throws InvalidParameterException, Exception;
	
	@GET
	@Path("/getElencoFormaGiuridica") 
	@Produces(MediaType.APPLICATION_JSON)
	Response getElencoformaGiuridica(
	        @Context HttpServletRequest req) throws DaoException;
	
	@GET
	@Path("/getElencoTipoAnag") 
	@Produces(MediaType.APPLICATION_JSON)
	Response getElencoTipoAnag(
			@Context HttpServletRequest req) throws DaoException;
	
	@GET
	@Path("/getElencoAteco") 
	@Produces(MediaType.APPLICATION_JSON)
	Response getElencoAteco(@DefaultValue("0") @QueryParam("idAttivitaAteco") String idAttivitaAteco,
			@Context HttpServletRequest req) throws DaoException;
	@GET
	@Path("/getElencoRuoloIndipendente") 
	@Produces(MediaType.APPLICATION_JSON)
	Response getElencoRuoloIndipendente(
			@Context HttpServletRequest req) throws DaoException;
	@GET
	@Path("/getElencoRuoloDipendente") 
	@Produces(MediaType.APPLICATION_JSON)
	Response getElencoRuoloDipendente(
			@Context HttpServletRequest req) throws DaoException;
	@GET
	@Path("/getElencoSezSpec") 
	@Produces(MediaType.APPLICATION_JSON)
	Response getElencoSezioneSpeciale(
			@Context HttpServletRequest req) throws DaoException;
	
	@GET
	@Path("/getStatoAttivita") 
	@Produces(MediaType.APPLICATION_JSON)
	Response getStatoAttivita(
			@Context HttpServletRequest req) throws DaoException;
	
	@GET
	@Path("/getDatiAreaCreditiSoggetto")
	@Produces(MediaType.APPLICATION_JSON)
	Response getDatiAreaCreditiSoggetto(
			@DefaultValue("0") @QueryParam("idSoggetto") String idSoggetto,
			@Context HttpServletRequest req) throws DaoException;
	
	@GET
	@Path("/getElencoBlocchiSoggettoDomanda") 
	@Produces(MediaType.APPLICATION_JSON)
	Response getElencoBlocchiSoggettoDomanda(
			@DefaultValue("0") @QueryParam("idSoggetto") String idSoggetto, 
			@DefaultValue("0") @QueryParam("idDomanda") String idDomanda, 			
	        @Context HttpServletRequest req) throws DaoException;
	
	@POST
	@Path("/insertBloccoDomanda")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	Response insertBloccoDomanda(
			BloccoVO bloccoVO,
			@DefaultValue("0") @QueryParam("idSoggetto") String idSoggetto, 
			@DefaultValue("0") @QueryParam("idDomanda") String idDomanda, 	
			@Context HttpServletRequest request) 
			throws InvalidParameterException, Exception;
	@POST
	@Path("/modificaBloccoDomanda")
	@Produces(MediaType.APPLICATION_JSON)
	Response modificaBloccoDomanda(
			BloccoVO bloccoVO,
			@DefaultValue("0") @QueryParam("numeroDomanda") String numeroDommanda, 
			@Context HttpServletRequest request) 
					throws InvalidParameterException, Exception;
	@GET
	@Path("/getListaCausaliDomanda")
	@Produces(MediaType.APPLICATION_JSON)
	Response getListaCausaliDomanda(@DefaultValue("0") @QueryParam("idSoggetto") String idSoggetto, 
			@DefaultValue("0") @QueryParam("idDomanda") String idDomanda,
	        @Context HttpServletRequest req) throws DaoException;
	
	@GET
	@Path("/getStoricoBlocchiDomanda") 
	@Produces(MediaType.APPLICATION_JSON)
	Response getStoricoBlocchiDomanda(
			@DefaultValue("0") @QueryParam("idSoggetto") Long idSoggetto, 
			@DefaultValue("0") @QueryParam("numeroDomanda") String numeroDomanda, 
	        @Context HttpServletRequest req) throws DaoException;
	
	@GET
	@Path("/getDatiImpresa") 
	@Produces(MediaType.APPLICATION_JSON)
	Response getDatiImpresa(
			@DefaultValue("0") @QueryParam("idSoggetto") Long idSoggetto, 
			@DefaultValue("0") @QueryParam("numeroDomanda") String numeroDomanda, 
			@Context HttpServletRequest req) throws DaoException;
	
	
	@GET
	@Path("/getDettaglioDimImpresaSoggetto") 
	@Produces(MediaType.APPLICATION_JSON)
	Response getDatiImpresaSoggetto(
			@DefaultValue("0") @QueryParam("idSoggetto") Long idSoggetto, 
			@Context HttpServletRequest req) throws DaoException;
	
	@GET
	@Path("/getDsan") 
	@Produces(MediaType.APPLICATION_JSON)
	Response getDsan(
			@DefaultValue("0") @QueryParam("idSoggetto") Long idSoggetto, 
			@DefaultValue("0") @QueryParam("numeroDomanda") String numeroDomanda, 
			@Context HttpServletRequest req) throws DaoException;
	//// comuni esteri e province 
	@GET
	@Path("/getComuneEstero") 
	@Produces(MediaType.APPLICATION_JSON)
	Response getComuneEstero(
			@DefaultValue("0") @QueryParam("idNazioneEstera") Long idNazione, 
			@Context HttpServletRequest req) throws DaoException;
	
	@GET
	@Path("/getProvinciaConIdRegione") 
	@Produces(MediaType.APPLICATION_JSON)
	Response getProvinciaConIdRegione(
			@DefaultValue("0") @QueryParam("idRegione") Long idRegione, 
			@Context HttpServletRequest req) throws DaoException;
	
	@GET
	@Path("/getElencoTipoDocumento") 
	@Produces(MediaType.APPLICATION_JSON)
	Response getTipoDocumento() throws DaoException;
	
	
	
	
	

}
