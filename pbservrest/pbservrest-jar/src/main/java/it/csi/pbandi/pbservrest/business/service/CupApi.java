/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservrest.business.service;

import it.csi.pbandi.pbservrest.dto.*;


import it.csi.pbandi.pbservrest.dto.ApiError;
import it.csi.pbandi.pbservrest.dto.Esito;
import it.csi.pbandi.pbservrest.dto.RequestCupVO;

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
@Path("/cup")


public interface CupApi  {
   
    @POST
    @Path("/acquisizioneCup")
    @Consumes({ "*/*" })
    @Produces({ "application/json" })
    Response acquisizioneCup( RequestCupVO body,@Context SecurityContext securityContext, @Context HttpHeaders httpHeaders , @Context HttpServletRequest httpRequest );

}
