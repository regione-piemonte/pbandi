/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweberog.business.service.impl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.csi.pbandi.pbservizit.security.UserInfoSec;
import it.csi.pbandi.pbweberog.business.service.MappeService;
import it.csi.pbandi.pbweberog.integration.dao.MappeDAO;
import it.csi.pbandi.pbweberog.util.Constants;

@Service
public class MappeServiceImpl implements MappeService{

	@Autowired
	MappeDAO mappeDAO;
	
	@Override
	public Response linkMappa(HttpServletRequest req) throws Exception {
		UserInfoSec userInfo = (UserInfoSec) req.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);	
		return Response.ok().entity(mappeDAO.linkMappa()).build();
	}

}
