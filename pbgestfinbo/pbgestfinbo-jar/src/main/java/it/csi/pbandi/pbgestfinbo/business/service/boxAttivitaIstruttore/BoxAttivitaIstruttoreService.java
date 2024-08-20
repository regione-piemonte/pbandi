/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.business.service.boxAttivitaIstruttore;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.springframework.stereotype.Service;

@Service
@Path("/boxAttivitaIstruttore")
public interface BoxAttivitaIstruttoreService {

    @GET
    @Path("/getSaldoStralcio")
    @Produces(MediaType.APPLICATION_JSON)
    Response getSaldoStralcio(
    		@DefaultValue("0") @QueryParam("idProgetto") Long idProgetto,
			@DefaultValue("0") @QueryParam("idModalitaAgevolazione") int idModalitaAgevolazione,
            @Context HttpServletRequest req) throws Exception;

    @GET
    @Path("/initSaldoStralcio")
    @Produces(MediaType.APPLICATION_JSON)
    Response initSaldoStralcio(@Context HttpServletRequest req) throws Exception;

}
