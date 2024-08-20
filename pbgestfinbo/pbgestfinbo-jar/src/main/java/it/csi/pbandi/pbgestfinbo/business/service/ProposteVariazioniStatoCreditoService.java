/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.business.service;

import java.math.BigDecimal;
import java.security.InvalidParameterException;
import java.util.List;

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
import it.csi.pbandi.pbgestfinbo.integration.vo.CercaPropostaVarazioneStatoCreditoSearchVO;
import it.csi.pbandi.pbgestfinbo.integration.vo.SalvaVariazioneStatoCreditoVO;

@Path("/proposte")
public interface ProposteVariazioniStatoCreditoService {
	
	@GET
	@Path("/suggess")
	@Produces(MediaType.APPLICATION_JSON)
	Response getListaSugg(
			@DefaultValue("000") @QueryParam("stringa") String stringa,
			@DefaultValue("1") @QueryParam("id") int id, 
			@Context HttpServletRequest req) throws DaoException;
	@GET
	@Path("/listaAgev")
	@Produces(MediaType.APPLICATION_JSON)
	Response getListaAgev(
			@Context HttpServletRequest req) throws DaoException;
	
	@GET
	@Path("/listaStatoProposta")
	@Produces(MediaType.APPLICATION_JSON)
	Response getListaProp(@Context HttpServletRequest req) throws DaoException;
	
	@GET
	@Path("/statoCredito")
	@Produces(MediaType.APPLICATION_JSON)
	Response statoCredito(@Context HttpServletRequest req) throws DaoException;
			
	@POST
	@Path("/elencoProposte")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	Response getElencoProposte(
			CercaPropostaVarazioneStatoCreditoSearchVO  statocreditoSearchDTO,
			@Context HttpServletRequest request) 
			throws InvalidParameterException, Exception;
	
	
	@POST
	@Path("/salvaStatoCred")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	Response salvaStatoCredito(
			SalvaVariazioneStatoCreditoVO salvaVariazioneStatoCreditoVO,
			@Context HttpServletRequest request)
			throws InvalidParameterException, Exception;
	
	@POST
	@Path("/rifiutaAccettaMassiva")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	Response rifiutaAccettaMassivaStatoCredito(
			List<Long> proposteDaConfermare,
			@DefaultValue("0") @QueryParam("flagConferma") String flagConferma,
			@Context HttpServletRequest request)
					throws InvalidParameterException, Exception;
	
	
}
