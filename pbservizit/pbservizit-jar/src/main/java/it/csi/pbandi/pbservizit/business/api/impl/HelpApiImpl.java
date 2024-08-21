/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.business.api.impl;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.csi.pbandi.pbservizit.business.api.HelpApi;
import it.csi.pbandi.pbservizit.dto.help.HelpDTO;
import it.csi.pbandi.pbservizit.integration.dao.HelpDAO;
import it.csi.pbandi.pbservizit.security.UserInfoSec;
import it.csi.pbandi.pbservizit.util.BeanUtil;
import it.csi.pbandi.pbservizit.util.Constants;

@Service
public class HelpApiImpl implements HelpApi {

	@Autowired
	protected BeanUtil beanUtil;

	@Autowired
	private HelpDAO helpDAO;

	@Override
	public Response getFlagEditHelpUser(HttpServletRequest req) throws Exception {
		UserInfoSec userInfo = (UserInfoSec) req.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);
		return Response.ok().entity(helpDAO.getFlagEditHelpUser(userInfo.getIdUtente(), userInfo.getIdIride())).build();
	}

	@Override
	public Response hasTipoAnagHelp(HttpServletRequest req) throws Exception {
		UserInfoSec userInfo = (UserInfoSec) req.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);
		return Response.ok().entity(
				helpDAO.hasTipoAnagHelp(userInfo.getCodiceRuolo(), userInfo.getIdUtente(), userInfo.getIdIride()))
				.build();
	}

	@Override
	public Response esisteHelpByPaginaAndTipoAnag(String descBrevePagina, HttpServletRequest req) throws Exception {
		UserInfoSec userInfo = (UserInfoSec) req.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);
		return Response.ok().entity(helpDAO.esisteHelpByPaginaAndTipoAnag(descBrevePagina, userInfo.getCodiceRuolo(),
				userInfo.getIdUtente(), userInfo.getIdIride())).build();
	}

	@Override
	public Response getHelpByPaginaAndTipoAnag(String descBrevePagina, HttpServletRequest req) throws Exception {
		UserInfoSec userInfo = (UserInfoSec) req.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);
		return Response.ok().entity(helpDAO.getHelpByPaginaAndTipoAnag(descBrevePagina, userInfo.getCodiceRuolo(),
				userInfo.getIdUtente(), userInfo.getIdIride())).build();
	}

	@Override
	public Response saveHelp(String descBrevePagina, HelpDTO helpDTO, HttpServletRequest req) throws Exception {
		UserInfoSec userInfo = (UserInfoSec) req.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);
		return Response.ok().entity(helpDAO.saveHelp(descBrevePagina, helpDTO, userInfo.getCodiceRuolo(),
				userInfo.getIdUtente(), userInfo.getIdIride())).build();
	}

	@Override
	public Response deleteHelp(Long idHelp, HttpServletRequest req) throws Exception {
		UserInfoSec userInfo = (UserInfoSec) req.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);
		return Response.ok().entity(helpDAO.deleteHelp(idHelp, userInfo.getIdUtente(), userInfo.getIdIride())).build();
	}

}
