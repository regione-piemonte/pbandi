/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservwelfare.business.service;

import it.csi.pbandi.pbservwelfare.dto.*;


import it.csi.pbandi.pbservwelfare.dto.ApiError;
import it.csi.pbandi.pbservwelfare.dto.DomandeConcesseResponse;
import java.io.File;

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
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;
@Path("/acquisizioneDomande")


public interface AcquisizioneDomandeApi  {
   
    @POST
    @Path("/getDomandeConcesse")
    @Consumes({ "multipart/form-data" })
    @Produces({ "application/json" })
    Response getDomandeConcesse(MultipartFormDataInput input, @NotNull @QueryParam("numeroDomanda") String numeroDomanda, @NotNull @QueryParam("codiceBando") String codiceBando,@Context SecurityContext securityContext, @Context HttpHeaders httpHeaders , @Context HttpServletRequest httpRequest );

}
