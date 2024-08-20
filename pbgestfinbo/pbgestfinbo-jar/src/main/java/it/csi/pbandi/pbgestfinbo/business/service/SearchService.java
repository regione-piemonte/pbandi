/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.business.service;


import java.math.BigDecimal;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


import it.csi.pbandi.pbgestfinbo.exception.DaoException;

@Path("/cerca")
public interface SearchService {
	
	
	@GET
	@Path("/getBeneficiarioEg") 
	@Produces(MediaType.APPLICATION_JSON)
	Response cercaBeneficiarioEg(
			@DefaultValue("") @QueryParam("cf") String cf, 
			@DefaultValue("0") @QueryParam("idSoggetto") Long idSoggetto, 
			@QueryParam("pIva") String pIva,
			@QueryParam("denominazione") String denominazione,
			@QueryParam("idDomanda") String numeroDomanda,
			@DefaultValue("0")@QueryParam("idProgetto") BigDecimal codProgetto,
	        @Context HttpServletRequest req) throws DaoException;
	
	
	@GET
	@Path("/getBeneficiarioPf") 
	@Produces(MediaType.APPLICATION_JSON)
	Response cercaBeneficiarioPf(
			@DefaultValue("") @QueryParam("cf") String cf, 
			@DefaultValue("0") @QueryParam("idSoggetto") Long idSoggetto, 
		     @QueryParam("idDomanda") String numeroDomanda,
		     @DefaultValue("0") @QueryParam("idProgetto") BigDecimal codProgetto,
			@DefaultValue("") @QueryParam("nome") String nome,
			@DefaultValue("") @QueryParam("cognome") String cognome,
	        @Context HttpServletRequest req) throws DaoException;

}
