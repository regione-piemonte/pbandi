/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebfin.business.service.impl;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import org.apache.log4j.Logger;

import it.csi.pbandi.pbwebfin.business.service.PingService;
import it.csi.pbandi.pbwebfin.util.Constants;

public class PingServiceImpl implements PingService {

	private Logger LOG = Logger.getLogger(Constants.COMPONENT_NAME);
	
	@Override
	public Response ping(SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
		String prf = "[PingServiceImpl::ping]";
		LOG.info(prf + " BEGIN-END");
		String res = "PING OK!";
		return Response.ok().entity(res).build();
	}

}
