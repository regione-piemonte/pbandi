/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservwelfare.business.service.impl;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import org.apache.log4j.Logger;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import it.csi.pbandi.pbservwelfare.business.service.AcquisizioneDomandeApi;
import it.csi.pbandi.pbservwelfare.dto.DomandeConcesseResponse;
import it.csi.pbandi.pbservwelfare.exception.ErroreGestitoException;
import it.csi.pbandi.pbservwelfare.exception.RecordNotFoundException;
import it.csi.pbandi.pbservwelfare.integration.dao.AcquisizioneDomandeDAO;
import it.csi.pbandi.pbservwelfare.util.CommonUtil;
import it.csi.pbandi.pbservwelfare.util.Constants;

@Transactional(timeout = 240, propagation = Propagation.REQUIRES_NEW, rollbackFor = { Exception.class })
@Component
public class AcquisizioneDomandeApiImpl extends BaseApiServiceImpl implements AcquisizioneDomandeApi {

	private Logger LOG = Logger.getLogger(Constants.COMPONENT_NAME);

	@Autowired
	AcquisizioneDomandeDAO acquisizioneDomandeDao;

	@Override
	public Response getDomandeConcesse(MultipartFormDataInput input, String numeroDomanda, String codiceBando,
			SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
		String prf = "[AcquisizioneDomandeApiImpl::getDomandeConcesse]";
		LOG.info(prf + " BEGIN");

		DomandeConcesseResponse datiResp = new DomandeConcesseResponse();
		try {
			String file = input.getFormDataMap().get("file").get(0).getBodyAsString();
			LOG.info(prf + " numeroDomanda=" + numeroDomanda + ", codiceBando=" + codiceBando);
			// Validazione parametri
			if (!CommonUtil.isEmpty(numeroDomanda)) {
				// Estraggo il nome del file
				String fileName = "";
				MultivaluedMap<String, String> headers = input.getFormDataMap().get("file").get(0).getHeaders();
				String[] contentDispositionHeader = headers.getFirst("Content-Disposition").split(";");
				for (String name : contentDispositionHeader) {
					if ((name.trim().startsWith("filename"))) {
						String[] tmp = name.split("=");
						fileName = tmp[1].trim().replaceAll("\"", "");
					}
				}
				// Verifico che il nome del file rispetti il pattern indicato
				String regex = "";
				if (Constants.CODICE_BANDO_DOMICILIARITA.equals(codiceBando)) {
					regex = "POR-FSE_2014-2020_FSE_FSE_DOM_[0-9]{1,9}_1_[0-9]{14}.xml";
				} else if (Constants.CODICE_BANDO_RESIDENZIALITA.equals(codiceBando)) {
					regex = "POR-FSE_2014-2020_FSE_FSE_RES_[0-9]{1,9}_1_[0-9]{14}.xml";
				}
				Pattern pattern = Pattern.compile(regex);
				Matcher matcher = pattern.matcher(fileName);
				if (matcher.matches()) {
					// Inserimento in tabella
					HashMap<String, String> result = acquisizioneDomandeDao.insertXml(file, fileName, numeroDomanda);
					if (!result.isEmpty() && Integer.parseInt(result.get("righeInserite")) > 0) {
						datiResp.setEsitoServizio(Constants.ESITO.OK);
						datiResp.setUUID(result.get("idCaricamento")
								+ result.get("timestamp").substring(0, 19).replaceAll("[-: ]", ""));
					} else {
						datiResp.setEsitoServizio(Constants.ESITO.KO);
					}
				} else {
					datiResp = getErroreNomeFileNonCorretto();
				}
			} else {
				datiResp = getErroreParametriInvalidiDomandeConcesse();
			}
		} catch (RecordNotFoundException e) {
			throw e;
		} catch (Exception e) {
			LOG.error(prf + "Exception while trying to read DomandeConcesseResponse", e);
			throw new ErroreGestitoException("DaoException while trying to read DomandeConcesseResponse", e);
		} finally {
			LOG.info(prf + " END");
		}

		return Response.ok(datiResp).build();
	}

}
