/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.business.api.impl;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import it.csi.pbandi.pbservizit.business.api.PingApi;
import it.csi.pbandi.pbservizit.util.Constants;

@Service
public class PingApiServiceImpl implements PingApi{

	private Logger LOG = Logger.getLogger(Constants.COMPONENT_NAME);
	
	@Override
	public Response ping(SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
		
		String prf = "[PingApiServiceImpl::ping]";
		LOG.info(prf + " BEGIN-END");
		
		String res = "PING OK!";
		
		//return Response.ok("PING OK!").header("someheader", ""+System.currentTimeMillis()).build();
		return Response.ok().entity(res).build();
	}

}
