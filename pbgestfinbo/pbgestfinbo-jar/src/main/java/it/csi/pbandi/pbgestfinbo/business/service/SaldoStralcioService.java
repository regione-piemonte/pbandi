/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.business.service;

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
import it.csi.pbandi.pbgestfinbo.integration.vo.SaldoStralcioVO;

@Path("/saldoStralcio")
public interface SaldoStralcioService {
	
	@GET
	@Path("/getSaldoStralcio")
	@Produces(MediaType.APPLICATION_JSON)
	Response getSaldoStralcio(
			@DefaultValue("0") @QueryParam("idSaldoStralcio") Long idSaldoStralcio,
			@DefaultValue("0") @QueryParam("idModalitaAgevolazione") int idModalitaAgev,
	        @Context HttpServletRequest req) throws DaoException;
	
	// inserzione saldo e stralcio	
	@POST
	@Path("/insertSaldoStralcio")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	Response insertSaldoStralcio(
			SaldoStralcioVO saldoStralcio,
			@DefaultValue("0") @QueryParam("idUtente") Long idUtente,
			@DefaultValue("0") @QueryParam("idProgetto") Long idProgetto,
			@DefaultValue("0") @QueryParam("idModalitaAgevolazione") int idModalitaAgev,
			@Context HttpServletRequest request) 
			throws InvalidParameterException, Exception;
	
	// modifcare saldo e stralcio	
	@POST
	@Path("/modificaSaldoStralcio")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	Response modificaSaldoStralcio(
			SaldoStralcioVO saldoStralcio,
			@DefaultValue("0") @QueryParam("idUtente") Long idUtente,
			@DefaultValue("0") @QueryParam("idProgetto") Long idProgetto,
			@DefaultValue("0") @QueryParam("idSaldoStralcio") Long idSaldoStralcio,
			@DefaultValue("0") @QueryParam("idModalitaAgevolazione") int idModalitaAgev,
			@Context HttpServletRequest request) 
					throws InvalidParameterException, Exception;
	
	// questo storico di tutti saldi e stralci
	@GET
	@Path("/getStorico")
	@Produces(MediaType.APPLICATION_JSON)
	Response getStorico(
			@DefaultValue("0") @QueryParam("idUtente") Long idUtente, 
			@DefaultValue("0") @QueryParam("idProgetto") Long idProgetto,
			@DefaultValue("0") @QueryParam("idModalitaAgevolazione") int idModalitaAgev,
		    @Context HttpServletRequest req) throws DaoException;
		
	// questo storico dei saldi con data_fine_validita == null 
	@GET
	@Path("/getStoricoSaldoStralcio")
	@Produces(MediaType.APPLICATION_JSON)
	Response getStoricoSaldoStralcio(
			@DefaultValue("0") @QueryParam("idUtente") Long idUtente, 
			@DefaultValue("0") @QueryParam("idProgetto") Long idProgetto,
			@DefaultValue("0") @QueryParam("idModalitaAgevolazione") int idModalitaAgev,
			@Context HttpServletRequest req) throws DaoException;
	
	
	@GET
	@Path("/listaAttivitaSaldoStralcio")
	@Produces(MediaType.APPLICATION_JSON)
	Response getListaAttivitaSaldoStralcio() throws DaoException;
	
	
	@GET
	@Path("/listaAttivitaEsito")
	@Produces(MediaType.APPLICATION_JSON)
	Response getListaAttivitaEsito() throws DaoException;
	
	
	@GET
	@Path("/listaAttivitaRecupero")
	@Produces(MediaType.APPLICATION_JSON)
	Response getListaAttivitaRecupero() throws DaoException;

	
	
	

}
