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

import it.csi.pbandi.pbgestfinbo.exception.DaoException;
import it.csi.pbandi.pbgestfinbo.integration.vo.AbbattimentoGaranzieVO;
import it.csi.pbandi.pbgestfinbo.integration.vo.search.AnagraficaBeneficiarioPfVO;
import it.csi.pbandi.pbgestfinbo.integration.vo.search.AnagraficaBeneficiarioVO;
import it.csi.pbandi.pbgestfinbo.integration.vo.search.DatiDomandaEgVO;
import it.csi.pbandi.pbgestfinbo.integration.vo.search.DatiDomandaVO;

@Path("/datiDomanda")
public interface DatiDomandaService {
	
	@GET
	@Path("/getDatidomanda")
	@Produces(MediaType.APPLICATION_JSON)
	Response getDatiDomanda(
			@DefaultValue("0") @QueryParam("idSoggetto") Long idSoggetto, 
			@DefaultValue("0") @QueryParam("idDomanda") String idDomanda,
	        @Context HttpServletRequest req) throws DaoException;
	
	
	@GET
	@Path("/getDatiDomandaEG") 
	@Produces(MediaType.APPLICATION_JSON)
	Response getDatiDomandaEG(
			@DefaultValue("0") @QueryParam("idSoggetto") Long idSoggetto, 
			@DefaultValue("0") @QueryParam("idDomanda") String idDomanda,
	        @Context HttpServletRequest req) throws DaoException;
	@GET
	@Path("/getAltreSedi") 
	@Produces(MediaType.APPLICATION_JSON)
	Response getAltreSedi(
			@DefaultValue("0") @QueryParam("idSoggetto") Long idSoggetto, 
			@DefaultValue("0") @QueryParam("idDomanda") String idDomanda,
			@Context HttpServletRequest req) throws DaoException;
	
	@GET
	@Path("/getElencoSoggCorrDipDaDomanda")
	@Produces(MediaType.APPLICATION_JSON)
	Response getElencoSoggCorrDipDaDomanda(
			@DefaultValue("0") @QueryParam("idDomanda") String idDomanda, 
			@DefaultValue("0") @QueryParam("idSoggetto") Long idSoggetto,
	        @Context HttpServletRequest req) throws DaoException;
	
	@GET
	@Path("/getAnagSoggCorrDipDaDomPF")
	@Produces(MediaType.APPLICATION_JSON)
	Response getAnagSoggCorrDipDaDomPF(@DefaultValue("0") @QueryParam("idSoggetto") Long idSoggetto,
			@DefaultValue("0") @QueryParam("idDomanda") String idDomanda,
			@DefaultValue("0") @QueryParam("idSoggCorr") BigDecimal idSoggCorr,
			@Context HttpServletRequest req) throws DaoException;
	
	@GET
	@Path("/getAnagSoggCorrDipDaDomEG")
	@Produces(MediaType.APPLICATION_JSON)
	Response getAnagSoggCorrDipDaDomEG(@DefaultValue("0") @QueryParam("idSoggetto") Long idSoggetto,
			@DefaultValue("0") @QueryParam("idDomanda") String idDomanda,
			@DefaultValue("0") @QueryParam("idSoggCorr") BigDecimal idSoggCorr,
			@Context HttpServletRequest req) throws DaoException;
	@GET
	@Path("/checKProgetto")
	@Produces(MediaType.APPLICATION_JSON)
	Response checKProgetto(
			@QueryParam("numeroDomanda") String numeroDomanda,
			@Context HttpServletRequest req) throws DaoException;
	
	///// salvataggio/// 
	
	@GET
	@Path("/listaRuoli")
	@Produces(MediaType.APPLICATION_JSON)
	Response listaRuoli() throws DaoException;
	
	@GET
	@Path("/suggest")
	@Produces(MediaType.APPLICATION_JSON)
	Response getListaSugg(
			@DefaultValue("000") @QueryParam("stringa") String stringa,
			@DefaultValue("1") @QueryParam("id") int id, 
			@Context HttpServletRequest req) throws DaoException;
	
	//// modifica// 
	@POST
	@Path("/modificaSoggettoDipDomandaPF")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	Response modificaPF(
			AnagraficaBeneficiarioPfVO soggetto,
			@DefaultValue("0") @QueryParam("idSoggetto") String idSoggetto,
			@DefaultValue("0") @QueryParam("idDomanda") String idDomanda,
			@Context HttpServletRequest request) 
			throws InvalidParameterException, Exception;
	@POST
	@Path("/modificaSoggettoDipDomandaEG")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	Response modificaSoggettoDipDomandaEG(
			AnagraficaBeneficiarioVO soggetto,
			@DefaultValue("0") @QueryParam("idSoggetto") String idSoggetto,
			@DefaultValue("0") @QueryParam("idDomanda") String idDomanda,
			@Context HttpServletRequest request) 
					throws InvalidParameterException, Exception;

	
	@POST
	@Path("/updateDatiDomandaEG")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	Response updateDatiDomandaEG(DatiDomandaEgVO domandaEG, 
			@DefaultValue("0")@QueryParam("idUtente") BigDecimal idUtente)throws InvalidParameterException, Exception; 
	
	@POST
	@Path("/updateDatiDomandaPF")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	Response updateDatiDomandaPF(DatiDomandaVO datiDomanda, 
			@QueryParam("idUtente") BigDecimal idUtente)throws InvalidParameterException, Exception; 

}
