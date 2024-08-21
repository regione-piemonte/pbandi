/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservrest.business.service;

import it.csi.pbandi.pbservrest.dto.*;


import it.csi.pbandi.pbservrest.dto.ApiError;
import it.csi.pbandi.pbservrest.dto.EsitoDurcAntimafiaListResponse;

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
@Path("/esitiDurcAntimafia")


public interface EsitiDurcAntimafiaApi  {
   
    @GET
    @Path("/getEsitoDurcAntimafia")
    
    @Produces({ "application/json" })
    Response getEsitoDurcAntimafia( @NotNull @QueryParam("codFiscaleSoggetto") String codFiscaleSoggetto, @NotNull @QueryParam("cognomeIstruttore") String cognomeIstruttore, @NotNull @QueryParam("nomeIstruttore") String nomeIstruttore, @NotNull @QueryParam("codFiscaleIstruttore") String codFiscaleIstruttore, @NotNull @QueryParam("numeroDomanda") String numeroDomanda, @NotNull @QueryParam("tipoRichiesta") String tipoRichiesta, @NotNull @QueryParam("modalitaRichiesta") String modalitaRichiesta,@Context SecurityContext securityContext, @Context HttpHeaders httpHeaders , @Context HttpServletRequest httpRequest );

}
