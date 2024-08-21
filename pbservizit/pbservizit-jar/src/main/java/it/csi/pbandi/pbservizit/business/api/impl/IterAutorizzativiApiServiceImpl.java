/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.business.api.impl;

import java.security.InvalidParameterException;

import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;

import it.csi.pbandi.pbservizit.business.api.IterAutorizzativiApi;
import it.csi.pbandi.pbservizit.integration.dao.IterAutorizzativiDAO;

public class IterAutorizzativiApiServiceImpl implements IterAutorizzativiApi {

	@Autowired
	private IterAutorizzativiDAO iterAutorizzativiDAO;
	
	@Override
	public Response avviaIterAutorizzativo(Long idTipoIter, Long idEntita, Long idTarget, Long idProgetto, Long idUtente) {
		return Response.ok().entity(iterAutorizzativiDAO.avviaIterAutorizzativo(idTipoIter, idEntita, idTarget, idProgetto, idUtente)).build();
	}

}
