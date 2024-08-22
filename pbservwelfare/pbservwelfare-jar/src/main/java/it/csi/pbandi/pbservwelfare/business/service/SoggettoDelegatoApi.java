/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservwelfare.business.service;

import it.csi.pbandi.pbservwelfare.dto.*;


import it.csi.pbandi.pbservwelfare.dto.ApiError;
import it.csi.pbandi.pbservwelfare.dto.SoggettoDelegatoResponse;

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
@Path("/soggettoDelegato")


public interface SoggettoDelegatoApi  {
   
    @POST
    @Path("/setSoggettoDelegato")
    
    @Produces({ "application/json" })
    Response setSoggettoDelegato( @NotNull @QueryParam("numeroDomanda") String numeroDomanda, @NotNull @QueryParam("nome") String nome, @NotNull @QueryParam("cognome") String cognome, @NotNull @QueryParam("codiceFiscale") String codiceFiscale, @QueryParam("codiceComuneNascita") String codiceComuneNascita, @QueryParam("descrizioneComuneNascita") String descrizioneComuneNascita, @QueryParam("descrizioneComuneEsteroNascita") String descrizioneComuneEsteroNascita, @QueryParam("dataNascita") String dataNascita, @QueryParam("mail") String mail, @QueryParam("telefono") String telefono, @QueryParam("codiceComuneResidenza") String codiceComuneResidenza, @QueryParam("descrizioneComuneResidenza") String descrizioneComuneResidenza, @QueryParam("descrizioneComuneEsteroResidenza") String descrizioneComuneEsteroResidenza, @QueryParam("indirizzo") String indirizzo, @QueryParam("cap") String cap,@Context SecurityContext securityContext, @Context HttpHeaders httpHeaders , @Context HttpServletRequest httpRequest );

}
