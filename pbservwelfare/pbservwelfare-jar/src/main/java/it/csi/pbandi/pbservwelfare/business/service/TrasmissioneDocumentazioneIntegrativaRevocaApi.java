/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservwelfare.business.service;

import it.csi.pbandi.pbservwelfare.dto.*;


import it.csi.pbandi.pbservwelfare.dto.ApiError;
import it.csi.pbandi.pbservwelfare.dto.DocIntRevocaResponse;
import it.csi.pbandi.pbservwelfare.dto.RequestDocIntRevoca;

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
@Path("/trasmissioneDocumentazioneIntegrativaRevoca")


public interface TrasmissioneDocumentazioneIntegrativaRevocaApi  {
   
    @GET
    @Path("/getTrasmissioneDocIntRevoca")
    @Consumes({ "*/*" })
    @Produces({ "application/json" })
    Response getTrasmissioneDocIntRevoca( RequestDocIntRevoca body,@Context SecurityContext securityContext, @Context HttpHeaders httpHeaders , @Context HttpServletRequest httpRequest );

}
