/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebbo.business.service.impl;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.csi.pbandi.pbservizit.exception.UtenteException;
import it.csi.pbandi.pbservizit.security.UserInfoSec;
import it.csi.pbandi.pbwebbo.integration.dao.InizializzazioneDAO;
import it.csi.pbandi.pbwebbo.util.Constants;


@Service
public class InizializzazioneServiceImpl implements it.csi.pbandi.pbwebbo.business.service.InizializzazioneService {

	@Autowired
	protected InizializzazioneDAO inizializzazioneDAO;
	
	private Logger LOG = Logger.getLogger(Constants.COMPONENT_NAME);
	
	
//	miniAppUrl: ----> http://dev-<VH_APACHE>/finanziamenti/bandijp/pbandiwebbosrv2/shib/HomePage.do?
		// idSg=2139302 &   --> pbandi_t_soggetto
		// idSgBen=5570 &
		// role=CSI-ASSISTENZA &
		// idU=25846    	--> pbandi_t_utente

		
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
		
		LOG.debug("[InizializzazioneServiceImpl::inizializzaHome] BEGIN");
		
		UserInfoSec user = inizializzazioneDAO.completaDatiUtente(idPrj, idSg, idSgBen, idU, role, taskIdentity, taskName, wkfAction, req);
		LOG.debug("[InizializzazioneServiceImpl::inizializzaHome] END");
		return Response.ok().entity(user).build();
	}	

}
