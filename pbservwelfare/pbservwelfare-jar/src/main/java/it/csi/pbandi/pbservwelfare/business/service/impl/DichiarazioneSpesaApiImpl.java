/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservwelfare.business.service.impl;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import org.apache.log4j.Logger;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import it.csi.pbandi.pbservwelfare.business.service.DichiarazioneSpesaApi;
import it.csi.pbandi.pbservwelfare.dto.DichiarazioneSpesaResponse;
import it.csi.pbandi.pbservwelfare.exception.ErroreGestitoException;
import it.csi.pbandi.pbservwelfare.exception.RecordNotFoundException;
import it.csi.pbandi.pbservwelfare.integration.dao.DichiarazioneSpesaDAO;
import it.csi.pbandi.pbservwelfare.util.CommonUtil;
import it.csi.pbandi.pbservwelfare.util.Constants;

@Transactional(timeout = 240, propagation = Propagation.REQUIRES_NEW, rollbackFor = { Exception.class })
@Component
public class DichiarazioneSpesaApiImpl extends BaseApiServiceImpl implements DichiarazioneSpesaApi {

	private Logger LOG = Logger.getLogger(Constants.COMPONENT_NAME);

	@Autowired
	DichiarazioneSpesaDAO dichiarazioneSpesaDao;

	@Override
	public Response acquisisciDichiarazioneSpesa(MultipartFormDataInput input, SecurityContext securityContext,
			HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
		String prf = "[DichiarazioneSpesaApiImpl::acquisisciDichiarazioneSpesa]";
		LOG.info(prf + " BEGIN");

		DichiarazioneSpesaResponse datiResp = new DichiarazioneSpesaResponse();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		try {
			String dati = input.getFormDataMap().get("dati").get(0).getBodyAsString();
			JSONObject json = new JSONObject(dati);
			String numeroDomanda = "";
			if (json.has("Numero_domanda")) {
				numeroDomanda = json.getString("Numero_domanda");
			}
			InputStream file = input.getFormDataMap().get("file").get(0).getBody(InputStream.class, null);
			LOG.info(prf + " dati=" + dati);
			// Validazione dati
			if (!CommonUtil.isEmpty(numeroDomanda)) {
				String dateFormatted = sdf.format(new Date());
				String nomeFile = "WS_" + numeroDomanda + dateFormatted + ".zip";
				HashMap<String, String> result = dichiarazioneSpesaDao.caricaFile(file, nomeFile, numeroDomanda, dati);
				if (!result.isEmpty() && Integer.parseInt(result.get("righeInserite")) > 0) {
					datiResp.setEsitoServizio(Constants.ESITO.OK);
					datiResp.setUUID(result.get("idCaricamento")
							+ result.get("timestamp").substring(0, 19).replaceAll("[-: ]", ""));
				} else {
					datiResp = getErroreDocumentazioneNonAcquisita();
				}
			} else {
				datiResp = getErroreParametriInvalidiDichiarazioneSpese();
			}
		} catch (RecordNotFoundException e) {
			throw e;
		} catch (Exception e) {
			LOG.error(prf + "Exception while trying to read DichiarazioneSpesaResponse", e);
			throw new ErroreGestitoException("DaoException while trying to read DichiarazioneSpesaResponse", e);
		} finally {
			LOG.info(prf + " END");
		}

		return Response.ok(datiResp).build();
	}

}
