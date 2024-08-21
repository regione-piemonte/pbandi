/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservrest.business.service;

import it.csi.pbandi.pbservrest.dto.*;


import it.csi.pbandi.pbservrest.dto.ApiError;
import java.math.BigDecimal;
import it.csi.pbandi.pbservrest.dto.DomandaInfoListResponse;

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
@Path("/domandeBeneficiario")


public interface DomandeBeneficiarioApi  {
   
    @GET
    @Path("/getDatiBeneficiarioDoquiActa")
    
    @Produces({ "application/json" })
    Response getDatiBeneficiarioDoquiActa( @NotNull @QueryParam("tipoSogg") String tipoSogg, @QueryParam("codiceBando") BigDecimal codiceBando, @QueryParam("numeroDomanda") String numeroDomanda, @QueryParam("ndg") String ndg, @QueryParam("numRelazDichSpesa") BigDecimal numRelazDichSpesa, @QueryParam("pec") String pec, @QueryParam("codiceFiscaleSoggetto") String codiceFiscaleSoggetto, @QueryParam("denominazioneEnteGiuridico") String denominazioneEnteGiuridico, @QueryParam("partitaIva") String partitaIva, @QueryParam("acronimoProgetto") String acronimoProgetto, @QueryParam("nome") String nome, @QueryParam("cognome") String cognome,@Context SecurityContext securityContext, @Context HttpHeaders httpHeaders , @Context HttpServletRequest httpRequest );

}
