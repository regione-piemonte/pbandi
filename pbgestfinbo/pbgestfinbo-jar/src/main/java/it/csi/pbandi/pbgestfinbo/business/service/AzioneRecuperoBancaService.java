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
import it.csi.pbandi.pbgestfinbo.integration.vo.AzioneRecuperoBancaVO;


@Path ("/azioniRecuperoBanca")
public interface AzioneRecuperoBancaService {
	
	@GET
	@Path("/getAzioneRecuperoBanca")
	@Produces(MediaType.APPLICATION_JSON)
	Response getAzioneRecuperoBanca(
			@DefaultValue("0") @QueryParam("idUtente") Long idUtente, 
			@DefaultValue("0") @QueryParam("idProgetto") Long idProgetto,
			@DefaultValue("0") @QueryParam("idAzioneRecuperoBanca") Long idAzioneRecupero,
			@DefaultValue("0") @QueryParam("idModalitaAgevolazione") int idModalitaAgev,
	        @Context HttpServletRequest req) throws DaoException;
	
	// per inserire una nuova azione
	@POST
	@Path("/insertAzioneRecuperoBanca")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	Response insertAzioneRecuperoBanca(
			AzioneRecuperoBancaVO azioneRecupBanca,
			@DefaultValue("0") @QueryParam("idUtente") Long idUtente,
			@DefaultValue("0") @QueryParam("idProgetto") Long idProgetto,
			@DefaultValue("0") @QueryParam("idModalitaAgevolazione") int idModalitaAgev,
			@Context HttpServletRequest request) 
			throws InvalidParameterException, Exception;
	
	// per modifica una azione creando una nuova
	@POST
	@Path("/modificaAzioneRecuperoBanca")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	Response modificaAzioneRecuperoBanca(
			AzioneRecuperoBancaVO azioneRecupBanca,
			@DefaultValue("0") @QueryParam("idUtente") Long idUtente,
			@DefaultValue("0") @QueryParam("idProgetto") Long idProgetto,
			@DefaultValue("0") @QueryParam("idAzioneRecuperoBanca") Long idAzioneRecuperoBanca,
			@DefaultValue("0") @QueryParam("idModalitaAgevolazione") int idModalitaAgev,
			@Context HttpServletRequest request) 
			throws InvalidParameterException, Exception;
	
	
	// questo storico di tutte le azioni legate a questo progetto (accesso tramite pulsante storico)
	@GET
	@Path("/getStoricoAzioni")
	@Produces(MediaType.APPLICATION_JSON)
	Response getStoricoAzioni(
			@DefaultValue("0") @QueryParam("idUtente") Long idUtente, 
			@DefaultValue("0") @QueryParam("idProgetto") Long idProgetto,
			@DefaultValue("0") @QueryParam("idModalitaAgevolazione") int idModalitaAgev,
	        @Context HttpServletRequest req) throws DaoException;
	
	// questo storico in pagina principale di solo le azioni con data_fine_validita == null 
	@GET
	@Path("/getStoricoAzioneRecuperoBanca")
	@Produces(MediaType.APPLICATION_JSON)
	Response getStoricoAzioneRecup(
			@DefaultValue("0") @QueryParam("idUtente") Long idUtente, 
			@DefaultValue("0") @QueryParam("idProgetto") Long idProgetto,
			@DefaultValue("0") @QueryParam("idModalitaAgevolazione") int idModalitaAgev,
			@Context HttpServletRequest req) throws DaoException;
	
	@GET
	@Path("/listaAttivitaAzioniRecuperoBanca")
	@Produces(MediaType.APPLICATION_JSON)
	Response getListaAzioniRecuperoBanca() throws DaoException;

}
