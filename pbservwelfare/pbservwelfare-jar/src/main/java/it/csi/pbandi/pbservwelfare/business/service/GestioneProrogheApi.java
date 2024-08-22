/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservwelfare.business.service;

import it.csi.pbandi.pbservwelfare.dto.*;


import java.math.BigDecimal;
import it.csi.pbandi.pbservwelfare.dto.EsitoResponse;

import java.util.List;
import java.util.Map;

import java.io.InputStream;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.HttpHeaders;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.validation.constraints.*;
@Path("/gestioneProroghe")


public interface GestioneProrogheApi  {
   
    @POST
    @Path("/richiediProrogaControdeduzione")
    
    @Produces({ "application/json" })
    Response richiediProroga( @NotNull @QueryParam("numeroDomanda") String numeroDomanda, @NotNull @QueryParam("numeroRevoca") BigDecimal numeroRevoca, @NotNull @QueryParam("numeroGiorni") Integer numeroGiorni, @NotNull @QueryParam("motivazione") String motivazione,@Context SecurityContext securityContext, @Context HttpHeaders httpHeaders , @Context HttpServletRequest httpRequest );

}
