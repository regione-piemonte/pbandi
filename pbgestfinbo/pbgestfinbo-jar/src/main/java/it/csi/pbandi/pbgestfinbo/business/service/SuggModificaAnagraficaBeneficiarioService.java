/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.business.service;

import java.sql.Date;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import it.csi.pbandi.pbgestfinbo.exception.DaoException;

@Path("/suggAnagrafica")
public interface SuggModificaAnagraficaBeneficiarioService {
	
	@GET
	@Path("/getFormaGiuridica") 
	@Produces(MediaType.APPLICATION_JSON)
	Response getFormaGiuridica(
			@QueryParam("formaGiuridica") String formaGiuridica,
	        @Context HttpServletRequest req) throws DaoException;
	
	
	@GET
	@Path("/getCodIpa") 
	@Produces(MediaType.APPLICATION_JSON)
	Response getCodIpa(
			@QueryParam("codiceIpa") String codiceIpa,
	        @Context HttpServletRequest req) throws DaoException;
	
	@GET
	@Path("/getDataCostituzione") 
	@Produces(MediaType.APPLICATION_JSON)
	Response getDataCostituzione(
			@QueryParam("dataCostituzione") Date dataCostituzione,
	        @Context HttpServletRequest req) throws DaoException;
	
	
	
	@GET
	@Path("/getComune") 
	@Produces(MediaType.APPLICATION_JSON)
	Response getComune(
			@QueryParam("comune") String comune,
	        @Context HttpServletRequest req) throws DaoException;
	
	@GET
	@Path("/getProvincia") 
	@Produces(MediaType.APPLICATION_JSON)
	Response getProvincia(
			@QueryParam("provincia") String provincia,
	        @Context HttpServletRequest req) throws DaoException;
	
	
	@GET
	@Path("/getCap") 
	@Produces(MediaType.APPLICATION_JSON)
	Response getCap(
			@QueryParam("cap") Long cap,
	        @Context HttpServletRequest req) throws DaoException;
	
	@GET
	@Path("/getRegione") 
	@Produces(MediaType.APPLICATION_JSON)
	Response getRegione(
			@QueryParam("regione") String regione,
	        @Context HttpServletRequest req) throws DaoException;
	
	@GET
	@Path("/getNazione") 
	@Produces(MediaType.APPLICATION_JSON)
	Response getNazione(
			@QueryParam("nazione") String nazione,
	        @Context HttpServletRequest req) throws DaoException;
	
	@GET
	@Path("/getAteco") 
	@Produces(MediaType.APPLICATION_JSON)
	Response getAteco(
			@QueryParam("ateco") Long ateco,
	        @Context HttpServletRequest req) throws DaoException;
	
	@GET
	@Path("/getDescAteco") 
	@Produces(MediaType.APPLICATION_JSON)
	Response getDescAteco(
			@QueryParam("descAteco") String descAteco,
	        @Context HttpServletRequest req) throws DaoException;
	
	
	@GET
	@Path("/getRea") 
	@Produces(MediaType.APPLICATION_JSON)
	Response getRea(
			@QueryParam("rea") String rea,
	        @Context HttpServletRequest req) throws DaoException;
	
	@GET
	@Path("/getDataIscrizione") 
	@Produces(MediaType.APPLICATION_JSON)
	Response getDataIscrizione(
			@QueryParam("dataIscrizione") Date dataIscrizione,
	        @Context HttpServletRequest req) throws DaoException;
	
	@GET
	@Path("/getProvinciaIscr") 
	@Produces(MediaType.APPLICATION_JSON)
	Response getProvinciaIscr(
			@QueryParam("provinciaIscr") String provinciaIscr,
	        @Context HttpServletRequest req) throws DaoException;

}
