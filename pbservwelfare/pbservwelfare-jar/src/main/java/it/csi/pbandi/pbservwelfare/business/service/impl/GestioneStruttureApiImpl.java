/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservwelfare.business.service.impl;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import it.csi.pbandi.pbservwelfare.business.service.GestioneStruttureApi;
import it.csi.pbandi.pbservwelfare.dto.EsitoResponse;
import it.csi.pbandi.pbservwelfare.dto.RequestInfoStrutture;
import it.csi.pbandi.pbservwelfare.integration.dao.GestioneStruttureDAO;
import it.csi.pbandi.pbservwelfare.util.CommonUtil;
import it.csi.pbandi.pbservwelfare.util.Constants;

/**
 * Servizio 32
 *
 */
@Transactional(timeout = 240, propagation = Propagation.REQUIRES_NEW, rollbackFor = { Exception.class })
@Component
public class GestioneStruttureApiImpl extends BaseApiServiceImpl implements GestioneStruttureApi {

	private Logger LOG = Logger.getLogger(Constants.COMPONENT_NAME);

	@Autowired
	GestioneStruttureDAO gestioneStruttureDAO;

	@Override
	public Response setInfoStrutture(RequestInfoStrutture body, SecurityContext securityContext,
			HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
		String prf = "[GestioneStruttureApiImpl::setInfoStrutture]";
		LOG.info(prf + " BEGIN");

		EsitoResponse datiResp = new EsitoResponse();
		try {
			if (!CommonUtil.isEmpty(body.getCodiceStruttura()) && !CommonUtil.isEmpty(body.getEmail())) {
				int result = gestioneStruttureDAO.aggiornaMailStruttura(body.getCodiceStruttura(), body.getEmail());
				if (result == 1) {
					datiResp.setEsitoServizio("OK");
				} else {
					datiResp = getErroreNessunDatoSelezionato();
				}
			} else {
				datiResp = getErroreParametriInvalidi();
			}
		} catch (Exception e) {
			LOG.error(prf + "Exception while trying to read EsitoResponse", e);
			datiResp = getErroreGenerico();
			return Response.ok(datiResp).build();
		} finally {
			LOG.info(prf + " END");
		}

		return Response.ok(datiResp).build();
	}

}
