/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.business.service.impl;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.csi.pbandi.pbgestfinbo.integration.dao.InizializzazioneDAO;
import it.csi.pbandi.pbgestfinbo.util.Constants;
import it.csi.pbandi.pbservizit.exception.UtenteException;


@Service
public class InizializzazioneServiceImpl implements it.csi.pbandi.pbgestfinbo.business.service.InizializzazioneService {

	@Autowired
	protected InizializzazioneDAO inizializzazioneDAO;
	
	private Logger LOG = Logger.getLogger(Constants.COMPONENT_NAME);
	
	
	@Override
	public Response inizializzaHome(
			@DefaultValue("0") @QueryParam("idPrj") Long idPrj,
	        @DefaultValue("0") @QueryParam("idSg") Long idSg,
	        @DefaultValue("0") @QueryParam("idSgBen") Long idSgBen,
	        @DefaultValue("0") @QueryParam("idU") Long idU,
	        @DefaultValue("--") @QueryParam("role") String role,
	        @DefaultValue("--") @QueryParam("taskIdentity") String taskIdentity,
	        @DefaultValue("--") @QueryParam("taskName") String taskName,
	        @DefaultValue("--") @QueryParam("wkfAction") String wkfAction,
	        HttpServletRequest req) throws UtenteException {
		
		return Response.ok().entity(inizializzazioneDAO.inizializzaHome(idPrj, idSg, idSgBen, idU, role, taskIdentity, taskName, wkfAction, req)).build();		
	}
	
	@Override
	public Response inizializzaHome2(Long idSg, Long idSgBen, Long idU, String role, HttpServletRequest req) throws UtenteException {
		
		return Response.ok().entity(inizializzazioneDAO.inizializzaHome2(idSg, idSgBen, idU, role, req)).build();
	}

}
