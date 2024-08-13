/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebbo.business.service.impl;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;
import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.csi.pbandi.pbservizit.dto.archivioFile.FileDTO;
import it.csi.pbandi.pbservizit.security.UserInfoSec;
import it.csi.pbandi.pbwebbo.business.service.BoStorageService;
import it.csi.pbandi.pbwebbo.dto.utils.ResponseCodeMessage;
import it.csi.pbandi.pbwebbo.integration.dao.impl.BoStorageDAOImpl;
import it.csi.pbandi.pbwebbo.integration.vo.BoStorageDocumentoIndexDTO;
import it.csi.pbandi.pbwebbo.util.Constants;
import it.csi.pbandi.pbwebbo.util.FileUtil;

@Service
public class BoStorageServiceImpl implements BoStorageService {

	private Logger LOG = Logger.getLogger(Constants.COMPONENT_NAME);

	@Autowired
	private BoStorageDAOImpl boStorageDAOImpl;

	@Override
	public Response getTipiDocIndex(HttpServletRequest req) throws Exception {
		String prf = "[BoStorageServiceImpl::getTipiDocIndex]";
		LOG.info(prf + " BEGIN");
		UserInfoSec userInfo = (UserInfoSec) req.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);
		LOG.info(prf + " END");
		return Response.ok().entity(boStorageDAOImpl.getTipiDocIndex(userInfo)).build();
	}

	@Override
	public Response getProgettiByDesc(String descrizione, HttpServletRequest req) throws Exception {
		String prf = "[BoStorageServiceImpl::getCodiciProgettoByDesc]";
		LOG.info(prf + " BEGIN");
		UserInfoSec userInfo = (UserInfoSec) req.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);
		LOG.info(prf + " END");
		return Response.ok().entity(boStorageDAOImpl.getProgettiByDesc(descrizione, userInfo)).build();
	}

	@Override
	public Response ricercaDocumenti(String nomeFile, Long idTipoDocumentoIndex, Long idProgetto,
			HttpServletRequest req) throws Exception {
		String prf = "[BoStorageServiceImpl::ricercaDocumenti]";
		LOG.info(prf + " BEGIN");

		if ((nomeFile == null || nomeFile.length() == 0) && (idTipoDocumentoIndex == null || idTipoDocumentoIndex == 0)
				&& (idProgetto == null || idProgetto == 0)) {
			throw new InvalidParameterException("Nessun filtro valorizzato");
		}

		LOG.info(prf + "Parametri in input -> nomeFile = " + nomeFile + ", idTipoDocumentoIndex = "
				+ idTipoDocumentoIndex + ", idProgetto = " + idProgetto);

		UserInfoSec userInfo = (UserInfoSec) req.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);
		List<BoStorageDocumentoIndexDTO> boStorageDocumentoIndexDTOs = boStorageDAOImpl.ricercaDocumenti(nomeFile,
				idTipoDocumentoIndex, idProgetto, userInfo);
		List<BoStorageDocumentoIndexDTO> result = new ArrayList<BoStorageDocumentoIndexDTO>();

		for (BoStorageDocumentoIndexDTO doc : boStorageDocumentoIndexDTOs) {
			BoStorageDocumentoIndexDTO dto = new BoStorageDocumentoIndexDTO(doc.getIdDocumentoIndex(),
					doc.getIdProgetto(), doc.getCodiceVisualizzatoProgetto(), doc.getIdTipoDocIndex(),
					doc.getDescrizioneBreveTipoDocIndex(), doc.getDescrizioneTipoDocIndex(), doc.getNomeFile(),
					Boolean.FALSE, Boolean.FALSE, Boolean.FALSE);
			result.add(dto);
			if (doc.getFlagFirmato()) {
				if (doc.getFlagFirmaAutografa()) {
					dto = new BoStorageDocumentoIndexDTO(doc.getIdDocumentoIndex(), doc.getIdProgetto(),
							doc.getCodiceVisualizzatoProgetto(), doc.getIdTipoDocIndex(),
							doc.getDescrizioneBreveTipoDocIndex(), doc.getDescrizioneTipoDocIndex(), doc.getNomeFile(),
							Boolean.TRUE, Boolean.FALSE, Boolean.TRUE);
				} else {
					dto = new BoStorageDocumentoIndexDTO(doc.getIdDocumentoIndex(), doc.getIdProgetto(),
							doc.getCodiceVisualizzatoProgetto(), doc.getIdTipoDocIndex(),
							doc.getDescrizioneBreveTipoDocIndex(), doc.getDescrizioneTipoDocIndex(),
							doc.getNomeFile() + ".p7m", Boolean.TRUE, Boolean.FALSE, Boolean.FALSE);
				}
				result.add(dto);
			}
			if (doc.getFlagMarcato() && !doc.getFlagFirmaAutografa()) {
				// se un file è firmato con firma autografa non deve avere la marcatura ma ci
				// sono dati sporchi
				dto = new BoStorageDocumentoIndexDTO(doc.getIdDocumentoIndex(), doc.getIdProgetto(),
						doc.getCodiceVisualizzatoProgetto(), doc.getIdTipoDocIndex(),
						doc.getDescrizioneBreveTipoDocIndex(), doc.getDescrizioneTipoDocIndex(),
						doc.getNomeFile() + ".p7m.tsd", Boolean.TRUE, Boolean.TRUE, Boolean.FALSE);
				result.add(dto);
			}
		}

		LOG.info(prf + " END");
		return Response.ok().entity(result).build();

	}

	@SuppressWarnings("resource")
	@Override
	public Response sostituisciDocumento(MultipartFormDataInput multipartFormData, HttpServletRequest req)
			throws Exception {

		String prf = "[BoStorageServiceImpl::sostituisciDocumento]";
		LOG.debug(prf + " BEGIN");

		if (multipartFormData == null) {
			throw new InvalidParameterException("multipartFormData non valorizzato");
		}

		UserInfoSec userInfo = (UserInfoSec) req.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);

		ResponseCodeMessage resp = new ResponseCodeMessage();
		LOG.debug(prf + "userInfo = " + userInfo);

		// Legge il file firmato dal multipart.
		Map<String, List<InputPart>> map = multipartFormData.getFormDataMap();
		List<InputPart> listInputPart = map.get("file");

		if (listInputPart == null) {
			LOG.info("listInputPart NULLO");
		} else {
			LOG.info("listInputPart SIZE = " + listInputPart.size());
		}
		for (InputPart i : listInputPart) {
			MultivaluedMap<String, String> m = i.getHeaders();
			Set<String> s = m.keySet();
			for (String x : s) {
				LOG.info("SET = " + x);
			}
		}

		FileDTO file = FileUtil.leggiFileDaMultipart(listInputPart, null);
		if (file == null) {
			throw new InvalidParameterException("File non valorizzato");
		}

		LOG.info(prf + "input nomeFile = " + file.getNomeFile());
		LOG.info(prf + "input bytes.length = " + file.getBytes().length);

		List<InputPart> inputPartDesc = map.get("descrizioneBreveTipoDocIndex");
		List<InputPart> inputPartIdDocIndex = map.get("idDocumentoIndex");
		List<InputPart> inputPartFlagFileFirmato = map.get("flagFirmato");
		List<InputPart> inputPartFlagFileMarcato = map.get("flagMarcato");
		List<InputPart> inputPartFlagFileFirmaAutografa = map.get("flagFirmaAutografa");
		String descrizioneBreveTipoDocIndex = "";
		Long idDocumentoIndex = null;
		Boolean flagFileFirmato = Boolean.FALSE;
		Boolean flagFileMarcato = Boolean.FALSE;
		Boolean flagFileFirmaAutografa = Boolean.FALSE;
		try {
			if (inputPartDesc != null && inputPartDesc.size() > 0 && inputPartIdDocIndex != null
					&& inputPartIdDocIndex.size() > 0) {
				descrizioneBreveTipoDocIndex = inputPartDesc.get(0).getBodyAsString();
				idDocumentoIndex = Long.parseLong(inputPartIdDocIndex.get(0).getBodyAsString());
				flagFileFirmato = new Boolean(inputPartFlagFileFirmato.get(0).getBodyAsString());
				flagFileMarcato = new Boolean(inputPartFlagFileMarcato.get(0).getBodyAsString());
				flagFileFirmaAutografa = new Boolean(inputPartFlagFileFirmaAutografa.get(0).getBodyAsString());
				resp = boStorageDAOImpl.sostituisciDocumento(file, descrizioneBreveTipoDocIndex, idDocumentoIndex,
						flagFileFirmato, flagFileMarcato, flagFileFirmaAutografa, userInfo);
			}
		} catch (Exception e) {
			String msg = "Errore durante la sostituzione del file.";
			LOG.error(prf + msg, e);
			throw new Exception(msg, e);
		} finally {
			LOG.info(prf + " END");
		}

		return Response.ok().entity(resp).build();
	}

}
