/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservrest.business.service;

import it.csi.pbandi.pbservrest.dto.*;


import it.csi.pbandi.pbservrest.dto.RecuperoNote;

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
@Path("/gestioneNote")


public interface GestioneNoteApi  {
   
    @GET
    @Path("/recuperoNote")
    
    @Produces({ "application/json" })
    Response recuperoNote( @QueryParam("codiceDomanda") String codiceDomanda, @QueryParam("codiceBeneficiario") String codiceBeneficiario, @QueryParam("agevolazione") String agevolazione, @QueryParam("codiceFondo") String codiceFondo, @QueryParam("codiceFiscaleBeneficiario") String codiceFiscaleBeneficiario,@Context SecurityContext securityContext, @Context HttpHeaders httpHeaders , @Context HttpServletRequest httpRequest );

}
