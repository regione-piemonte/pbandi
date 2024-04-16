/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbworkspace.business.service.impl;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.csi.pbandi.pbservizit.security.UserInfoSec;
import it.csi.pbandi.pbworkspace.business.service.DashboardService;
import it.csi.pbandi.pbworkspace.dto.BandoWidgetDTO;
import it.csi.pbandi.pbworkspace.dto.EsitoOperazioneDTO;
import it.csi.pbandi.pbworkspace.dto.WidgetDTO;
import it.csi.pbandi.pbworkspace.exception.ErroreGestitoException;
import it.csi.pbandi.pbworkspace.integration.dao.DashboardDAO;
import it.csi.pbandi.pbworkspace.util.Constants;

@Service
public class DashboardServiceImpl implements DashboardService {

	private Logger LOG = Logger.getLogger(Constants.COMPONENT_NAME);

	@Autowired
	DashboardDAO dashboardDAO;

	@Override
	public Response isDashboardVisible(HttpServletRequest req) throws Exception {
		String className = Thread.currentThread().getStackTrace()[1].getClassName();
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		String prf = "[" + className + "::" + methodName + "]";
		LOG.info(prf + " BEGIN");

		UserInfoSec userInfo = (UserInfoSec) req.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);
		try {

			Boolean result = dashboardDAO.isDashboardVisible(userInfo.getCodiceRuolo(), userInfo.getIdSoggetto(),
					userInfo.getIdUtente(), userInfo.getIdIride());

			return Response.ok(result).build();

		} catch (Exception e) {
			LOG.error(prf + "Exception ", e);
			throw new ErroreGestitoException("Exception ", e);
		} finally {
			LOG.info(prf + " END");
		}
	}

	@Override
	public Response areWidgetsConfigured(HttpServletRequest req) throws Exception {
		String className = Thread.currentThread().getStackTrace()[1].getClassName();
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		String prf = "[" + className + "::" + methodName + "]";
		LOG.info(prf + " BEGIN");

		UserInfoSec userInfo = (UserInfoSec) req.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);
		try {

			Boolean result = dashboardDAO.areWidgetsConfigured(userInfo.getCodiceRuolo(), userInfo.getIdSoggetto(),
					userInfo.getIdUtente(), userInfo.getIdIride());

			return Response.ok(result).build();

		} catch (Exception e) {
			LOG.error(prf + "Exception ", e);
			throw new ErroreGestitoException("Exception ", e);
		} finally {
			LOG.info(prf + " END");
		}
	}

	@Override
	public Response getWidgets(HttpServletRequest req) throws Exception {
		String className = Thread.currentThread().getStackTrace()[1].getClassName();
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		String prf = "[" + className + "::" + methodName + "]";
		LOG.info(prf + " BEGIN");

		UserInfoSec userInfo = (UserInfoSec) req.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);
		try {

			List<WidgetDTO> result = dashboardDAO.getWidgets(userInfo.getCodiceRuolo(), userInfo.getIdSoggetto(),
					userInfo.getIdUtente(), userInfo.getIdIride());

			return Response.ok(result).build();

		} catch (Exception e) {
			LOG.error(prf + "Exception ", e);
			throw new ErroreGestitoException("Exception ", e);
		} finally {
			LOG.info(prf + " END");
		}
	}

	@Override
	public Response getBandiDaAssociare(HttpServletRequest req) throws Exception {
		String className = Thread.currentThread().getStackTrace()[1].getClassName();
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		String prf = "[" + className + "::" + methodName + "]";
		LOG.info(prf + " BEGIN");

		UserInfoSec userInfo = (UserInfoSec) req.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);
		try {

			List<BandoWidgetDTO> result = dashboardDAO.getBandiDaAssociare(userInfo.getCodiceRuolo(),
					userInfo.getIdSoggetto(), userInfo.getIdUtente(), userInfo.getIdIride());

			return Response.ok(result).build();

		} catch (Exception e) {
			LOG.error(prf + "Exception ", e);
			throw new ErroreGestitoException("Exception ", e);
		} finally {
			LOG.info(prf + " END");
		}
	}

	@Override
	public Response getBandiAssociati(Long idWidget, HttpServletRequest req) throws Exception {
		String className = Thread.currentThread().getStackTrace()[1].getClassName();
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		String prf = "[" + className + "::" + methodName + "]";
		LOG.info(prf + " BEGIN");

		UserInfoSec userInfo = (UserInfoSec) req.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);
		try {

			List<BandoWidgetDTO> result = dashboardDAO.getBandiAssociati(idWidget, userInfo.getCodiceRuolo(),
					userInfo.getIdSoggetto(), userInfo.getIdUtente(), userInfo.getIdIride());

			return Response.ok(result).build();

		} catch (Exception e) {
			LOG.error(prf + "Exception ", e);
			throw new ErroreGestitoException("Exception ", e);
		} finally {
			LOG.info(prf + " END");
		}
	}

	@Override
	public Response changeWidgetAttivo(Long idWidget, Boolean flagWidgetAttivo, HttpServletRequest req)
			throws Exception {
		String className = Thread.currentThread().getStackTrace()[1].getClassName();
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		String prf = "[" + className + "::" + methodName + "]";
		LOG.info(prf + " BEGIN");

		UserInfoSec userInfo = (UserInfoSec) req.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);
		try {

			EsitoOperazioneDTO result = dashboardDAO.changeWidgetAttivo(idWidget, flagWidgetAttivo,
					userInfo.getCodiceRuolo(), userInfo.getIdSoggetto(), userInfo.getIdUtente(), userInfo.getIdIride());

			return Response.ok(result).build();

		} catch (Exception e) {
			LOG.error(prf + "Exception ", e);
			throw new ErroreGestitoException("Exception ", e);
		} finally {
			LOG.info(prf + " END");
		}
	}

	@Override
	public Response associaBandoAWidget(Long idWidget, Long progrBandoLineaIntervento, HttpServletRequest req)
			throws Exception {
		String className = Thread.currentThread().getStackTrace()[1].getClassName();
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		String prf = "[" + className + "::" + methodName + "]";
		LOG.info(prf + " BEGIN");

		UserInfoSec userInfo = (UserInfoSec) req.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);
		try {

			EsitoOperazioneDTO result = dashboardDAO.associaBandoAWidget(idWidget, progrBandoLineaIntervento,
					userInfo.getCodiceRuolo(), userInfo.getIdSoggetto(), userInfo.getIdUtente(), userInfo.getIdIride());

			return Response.ok(result).build();

		} catch (Exception e) {
			LOG.error(prf + "Exception ", e);
			throw new ErroreGestitoException("Exception ", e);
		} finally {
			LOG.info(prf + " END");
		}
	}

	@Override
	public Response disassociaBandoAWidget(Long idBandoLinSoggWidget, HttpServletRequest req) throws Exception {
		String className = Thread.currentThread().getStackTrace()[1].getClassName();
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		String prf = "[" + className + "::" + methodName + "]";
		LOG.info(prf + " BEGIN");

		UserInfoSec userInfo = (UserInfoSec) req.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);
		try {

			EsitoOperazioneDTO result = dashboardDAO.disassociaBandoAWidget(idBandoLinSoggWidget,
					userInfo.getCodiceRuolo(), userInfo.getIdSoggetto(), userInfo.getIdUtente(), userInfo.getIdIride());

			return Response.ok(result).build();

		} catch (Exception e) {
			LOG.error(prf + "Exception ", e);
			throw new ErroreGestitoException("Exception ", e);
		} finally {
			LOG.info(prf + " END");
		}
	}

}
