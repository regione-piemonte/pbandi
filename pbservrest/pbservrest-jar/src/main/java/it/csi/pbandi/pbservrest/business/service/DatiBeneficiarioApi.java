/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservrest.business.service;

import it.csi.pbandi.pbservrest.dto.*;


import it.csi.pbandi.pbservrest.dto.ApiError;
import it.csi.pbandi.pbservrest.dto.DatiBeneficiarioListResponse;
import it.csi.pbandi.pbservrest.dto.Esito;
import it.csi.pbandi.pbservrest.dto.RequestDatiBeneficiarioVO;

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
@Path("/datiBeneficiario")


public interface DatiBeneficiarioApi  {
   
    @GET
    @Path("/getDatiBeneficiario")
    
    @Produces({ "application/json" })
    Response getDatiBeneficiario( @QueryParam("codiceFiscale") String codiceFiscale, @QueryParam("numeroDomanda") String numeroDomanda,@Context SecurityContext securityContext, @Context HttpHeaders httpHeaders , @Context HttpServletRequest httpRequest );

    @POST
    @Path("/setDatiBeneficiario")
    @Consumes({ "*/*" })
    @Produces({ "application/json" })
    Response setDatiBeneficiario( RequestDatiBeneficiarioVO body,@Context SecurityContext securityContext, @Context HttpHeaders httpHeaders , @Context HttpServletRequest httpRequest );

}
