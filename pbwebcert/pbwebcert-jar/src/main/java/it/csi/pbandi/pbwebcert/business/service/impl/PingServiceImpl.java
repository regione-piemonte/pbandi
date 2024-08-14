/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebcert.business.service.impl;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import it.csi.pbandi.pbwebcert.business.service.PingService;
import it.csi.pbandi.pbwebcert.util.Constants;

@Service
public class PingServiceImpl implements PingService {

	private Logger LOG = Logger.getLogger(Constants.COMPONENT_NAME);
	
	@Override
	public Response ping2(SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
		String prf = "[PingServiceImpl::ping]";
		LOG.info(prf + " BEGIN-END");
		
		String res = "PING2 OK!";
		
		//return Response.ok("PING OK!").header("someheader", ""+System.currentTimeMillis()).build();
		return Response.ok().entity(res).build();
	}

}
