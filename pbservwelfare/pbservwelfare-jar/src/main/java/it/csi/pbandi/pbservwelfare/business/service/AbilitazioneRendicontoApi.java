/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservwelfare.business.service;

import it.csi.pbandi.pbservwelfare.dto.*;


import it.csi.pbandi.pbservwelfare.dto.AbilitaRendicontazioneResponse;
import it.csi.pbandi.pbservwelfare.dto.ApiError;

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
@Path("/abilitazioneRendiconto")


public interface AbilitazioneRendicontoApi  {
   
    @GET
    @Path("/getAbilitaRendicontazione")
    
    @Produces({ "application/json" })
    Response getAbilitaRendicontazione( @QueryParam("numeroDomanda") String numeroDomanda, @QueryParam("codiceFiscaleUtente") String codiceFiscaleUtente,@Context SecurityContext securityContext, @Context HttpHeaders httpHeaders , @Context HttpServletRequest httpRequest );

}
