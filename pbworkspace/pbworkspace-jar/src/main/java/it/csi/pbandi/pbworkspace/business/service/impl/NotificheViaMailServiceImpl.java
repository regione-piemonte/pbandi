/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbworkspace.business.service.impl;

import java.security.InvalidParameterException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.csi.pbandi.pbservizit.security.UserInfoSec;
import it.csi.pbandi.pbworkspace.business.service.NotificheViaMailService;
import it.csi.pbandi.pbworkspace.exception.ErroreGestitoException;
import it.csi.pbandi.pbworkspace.exception.UtenteException;
import it.csi.pbandi.pbworkspace.integration.dao.NotificheViaMailDAO;
import it.csi.pbandi.pbworkspace.integration.vo.BandoProgettiVO;
import it.csi.pbandi.pbworkspace.integration.vo.NotificaAlertVO;
import it.csi.pbandi.pbworkspace.integration.vo.NotificheFrequenzeVO;
import it.csi.pbandi.pbworkspace.util.Constants;

@Service
public class NotificheViaMailServiceImpl implements NotificheViaMailService {

	private Logger LOG = Logger.getLogger(Constants.COMPONENT_NAME);

	@Autowired
	private NotificheViaMailDAO notificheViaMailDAO;

	@Override
	public Response getMail(HttpServletRequest req) throws UtenteException, Exception {
		String prf = "[NotificheViaMailServiceImpl::getMail]";
		LOG.info(prf + " BEGIN");
		UserInfoSec userInfo = (UserInfoSec) req.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);
		LOG.info("idSoggetto:" + userInfo.getIdSoggetto());
		try {
			String mail = notificheViaMailDAO.getMail(userInfo.getIdUtente(), userInfo.getIdIride(),
					userInfo.getIdSoggetto());

			if (mail != null) {
				LOG.info(prf + " mail found: " + mail);
			} else {
				LOG.info(prf + " mail is null");
			}
			return Response.ok(mail).build();
		} catch (UtenteException e) {
			throw e;
		} catch (Exception e) {
			throw e;
		} finally {
			LOG.info(prf + " END");
		}
	}

	@Override
	public Response saveMail(HttpServletRequest req, String mail)
			throws UtenteException, InvalidParameterException, Exception {
		String prf = "[NotificheViaMailServiceImpl::getMail]";
		LOG.info(prf + " BEGIN");
		UserInfoSec userInfo = (UserInfoSec) req.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);
		LOG.info(prf + " idSoggetto:" + userInfo.getIdSoggetto() + ", email: " + mail);
		boolean success = false;
		try {
			notificheViaMailDAO.saveMail(userInfo.getIdUtente(), userInfo.getIdIride(), userInfo.getIdSoggetto(), mail);
			success = true;
		} catch (UtenteException e) {
			throw e;

		} catch (InvalidParameterException e) {
			throw e;
		} catch (Exception e) {
			throw e;
		} finally {
			LOG.info(prf + " END");
		}

		return Response.ok().entity(success).build();
	}

	@Override
	public Response getNotificheFrequenze(HttpServletRequest req) throws UtenteException, Exception {
		String prf = "[NotificheViaMailServiceImpl::getNotificheFrequenze]";
		LOG.info(prf + " BEGIN");
		UserInfoSec userInfo = (UserInfoSec) req.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);
		LOG.info(prf + " idSoggetto:" + userInfo.getIdSoggetto());
		try {
			NotificheFrequenzeVO notificheFrequenze = notificheViaMailDAO.getNotificheFrequenze(userInfo.getIdUtente(),
					userInfo.getIdIride(), userInfo.getIdSoggetto());
			return Response.ok(notificheFrequenze).build();

		} catch (UtenteException e) {
			throw e;
		} catch (Exception e) {
			throw e;
		} finally {
			LOG.info(prf + " END");
		}
	}

	@Override
	public Response associateNotificheIstruttore(HttpServletRequest req, List<NotificaAlertVO> notificheAlert)
			throws UtenteException, InvalidParameterException, Exception {
		String prf = "[NotificheViaMailServiceImpl::associateNotificheIstruttore]";
		LOG.info(prf + " BEGIN");
		if (notificheAlert == null || notificheAlert.size() == 0) {
			throw new InvalidParameterException("notificheAlert non correttamente valorizzato");
		}
		UserInfoSec userInfo = (UserInfoSec) req.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);
		LOG.info(prf + " idSoggetto:" + userInfo.getIdSoggetto());
		boolean success = false;
		try {
			notificheViaMailDAO.associateNotificheIstruttore(userInfo.getIdUtente(), userInfo.getIdIride(),
					userInfo.getIdSoggetto(), notificheAlert);
			success = true;
		} catch (UtenteException e) {
			throw e;
		} catch (Exception e) {
			throw e;
		} finally {
			LOG.info(prf + " END");
		}
		return Response.ok().entity(success).build();
	}

	@Override
	public Response getBandiProgetti(HttpServletRequest req, Long idSoggettoNotifica)
			throws ErroreGestitoException, UtenteException {
		String prf = "[NotificheViaMailServiceImpl::getBandiProgetti]";
		LOG.info(prf + " BEGIN");
		if (idSoggettoNotifica == null) {
			throw new InvalidParameterException("idSoggettoNotifica non correttamente valorizzato");
		}
		UserInfoSec userInfo = (UserInfoSec) req.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);

		LOG.info("searching bandi for idSoggetto: " + userInfo.getIdSoggetto() + " and idSoggettoNotifica: "
				+ idSoggettoNotifica);
		try {
			List<BandoProgettiVO> bandiProgetti = notificheViaMailDAO.getBandiProgetti(userInfo.getIdUtente(),
					userInfo.getIdIride(), userInfo.getIdSoggetto(), idSoggettoNotifica);

			return Response.ok(bandiProgetti).build();
		} catch (UtenteException e) {
			throw e;
		} catch (Exception e) {
			throw e;
		} finally {
			LOG.info(prf + " END");
		}

	}

	@Override
	public Response associateProgettiToNotifica(HttpServletRequest req, NotificaAlertVO notificaAlert)
			throws UtenteException, InvalidParameterException, Exception {
		String prf = "[NotificheViaMailServiceImpl::associateProgettiToNotifica]";
		LOG.info(prf + " BEGIN");
		if (notificaAlert == null) {
			throw new InvalidParameterException("notificaAlert non correttamente valorizzato");
		}
		UserInfoSec userInfo = (UserInfoSec) req.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);
		LOG.info(prf + " idSoggetto:" + userInfo.getIdSoggetto());
		boolean success = false;
		try {
			notificheViaMailDAO.associateProgettiToNotifica(userInfo.getIdUtente(), userInfo.getIdIride(),
					userInfo.getIdSoggetto(), notificaAlert);
			success = true;
		} catch (UtenteException e) {
			throw e;
		} catch (Exception e) {
			throw e;
		} finally {
			LOG.info(prf + " END");
		}
		return Response.ok().entity(success).build();
	}

}
