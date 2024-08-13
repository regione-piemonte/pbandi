/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweb.business.service.impl;

import java.io.InputStream;
import java.math.BigDecimal;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

import it.csi.pbandi.pbweb.dto.DocumentoAllegatoDTO;
import org.apache.log4j.Logger;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.csi.findom.finservrest.dto.DocumentoListResponse;
import it.csi.pbandi.pbservizit.dto.archivioFile.EsitoLeggiFile;
import it.csi.pbandi.pbservizit.exception.ErroreGestitoException;
import it.csi.pbandi.pbservizit.exception.UtenteException;
import it.csi.pbandi.pbservizit.security.BeneficiarioSec;
import it.csi.pbandi.pbservizit.security.UserInfoSec;
import it.csi.pbandi.pbweb.business.manager.ActaManager;
import it.csi.pbandi.pbweb.business.service.DocumentiDiProgettoService;
import it.csi.pbandi.pbweb.dto.EsitoDTO;
import it.csi.pbandi.pbweb.dto.documentiDiProgetto.FiltroRicercaDocumentiDTO;
import it.csi.pbandi.pbweb.integration.dao.DocumentiDiProgettoDAO;
import it.csi.pbandi.pbweb.integration.vo.EnteProgettoDomandaVO;
import it.csi.pbandi.pbweb.integration.vo.PbandiTDocumentoIndexVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.dao.GenericDAO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.DocumentoIndexVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiTDocProtocolloVO;
import it.csi.pbandi.pbweb.util.Constants;
import it.csi.pbandi.pbweb.util.DateUtil;
import it.doqui.acta.actasrv.dto.acaris.type.common.AcarisContentStreamType;
import it.doqui.acta.actasrv.dto.acaris.type.common.PropertyType;
//import jakarta.ws.rs.client.Client;
//import jakarta.ws.rs.client.ClientBuilder;
//import jakarta.ws.rs.client.WebTarget;

@Service
public class DocumentiDiProgettoServiceImpl implements DocumentiDiProgettoService {

	@Autowired
	ActaManager actaManager;

	@Autowired
	GenericDAO genericDAO;

	@Autowired
	DocumentiDiProgettoDAO documentiDiProgettoDAO;

	private Logger LOG = Logger.getLogger(Constants.COMPONENT_NAME);
	private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle("conf.restServices");
	private static final String endpointFinservRestService = RESOURCE_BUNDLE.getString("endpointFinservRestService");

	@Override
	public Response inizializzaDocumentiDiProgetto(String codiceRuolo, HttpServletRequest req)
			throws InvalidParameterException, Exception {
		UserInfoSec userInfo = (UserInfoSec) req.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);
		return Response.ok().entity(documentiDiProgettoDAO.inizializzaDocumentiDiProgetto(codiceRuolo,
				userInfo.getIdSoggetto(), userInfo.getIdUtente(), userInfo.getIdIride())).build();
	}

	@Override
	public Response getBeneficiariDocProgettoByDenomOrIdBen(String denominazione, Long idBeneficiario,
			@Context HttpServletRequest req) throws InvalidParameterException, ErroreGestitoException, Exception {
		UserInfoSec userInfo = (UserInfoSec) req.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);

		return Response.ok()
				.entity(documentiDiProgettoDAO.getBeneficiariDocProgettoByDenomOrIdBen(denominazione, idBeneficiario,
						userInfo.getCodiceRuolo(), userInfo.getIdSoggetto(), userInfo.getIdUtente(),
						userInfo.getIdIride()))
				.build();
	}

	@Override
	public Response progettiBeneficiario(String codiceRuolo, Long idSoggettoBeneficiario, HttpServletRequest req)
			throws InvalidParameterException, Exception {
		UserInfoSec userInfo = (UserInfoSec) req.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);
		return Response.ok().entity(documentiDiProgettoDAO.progettiBeneficiario(codiceRuolo, idSoggettoBeneficiario,
				userInfo.getIdSoggetto(), userInfo.getIdUtente(), userInfo.getIdIride())).build();
	}

	@Override
	public Response cercaDocumenti(FiltroRicercaDocumentiDTO filtroRicercaDocumentiDTO, HttpServletRequest req)
			throws InvalidParameterException, Exception {
		UserInfoSec userInfo = (UserInfoSec) req.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);
		return Response.ok().entity(documentiDiProgettoDAO.cercaDocumenti(filtroRicercaDocumentiDTO,
				userInfo.getIdUtente(), userInfo.getIdIride())).build();
	}

	@Override
	public Response salvaUpload(MultipartFormDataInput multipartFormData, HttpServletRequest req)
			throws InvalidParameterException, Exception {
		UserInfoSec userInfo = (UserInfoSec) req.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);
		return Response.ok().entity(
				documentiDiProgettoDAO.salvaUpload(multipartFormData, userInfo.getIdUtente(), userInfo.getIdIride()))
				.build();
	}

	@Override
	public Response allegati(String codTipoDocIndex, Long idDocumentoIndex, HttpServletRequest req)
			throws InvalidParameterException, Exception {
		UserInfoSec userInfo = (UserInfoSec) req.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);
		return Response.ok().entity(documentiDiProgettoDAO.allegati(codTipoDocIndex, idDocumentoIndex,
				userInfo.getIdUtente(), userInfo.getIdIride())).build();
	}

	@Override
	public Response allegatiIntegrazioniDS(Long idDichiarazioneSpesa, String codTipoDocIndex, HttpServletRequest req)
			throws InvalidParameterException, Exception {
		UserInfoSec userInfo = (UserInfoSec) req.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);
		return Response.ok().entity(documentiDiProgettoDAO.allegatiIntegrazioniDS(idDichiarazioneSpesa, codTipoDocIndex,
				userInfo.getIdUtente(), userInfo.getIdIride())).build();
	}

	@Override
	public Response allegatiVerbaleChecklist(String codTipoDocIndex, Long idChecklist, Long idDocIndex,
			HttpServletRequest req) throws InvalidParameterException, Exception {
		UserInfoSec userInfo = (UserInfoSec) req.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);
		return Response.ok().entity(documentiDiProgettoDAO.allegatiVerbaleChecklist(codTipoDocIndex, idChecklist,
				idDocIndex, userInfo.getIdUtente(), userInfo.getIdIride())).build();
	}

	@Override
	public Response getAllegatiChecklist(String codTipoDocIndex, Long idChecklist, Long idDocIndex,
			HttpServletRequest req) throws Exception {
		UserInfoSec userInfo = (UserInfoSec) req.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);

		List<DocumentoAllegatoDTO> allegati = documentiDiProgettoDAO.allegatiVerbaleChecklist(codTipoDocIndex,
				idChecklist, idDocIndex, userInfo.getIdUtente(), userInfo.getIdIride());

		idChecklist = documentiDiProgettoDAO.getIdChecklist(idChecklist, idDocIndex);
		if (allegati != null) {
			allegati.addAll(documentiDiProgettoDAO.getAllegatiChecklist(idChecklist, userInfo.getIdUtente()));
		} else {
			allegati = documentiDiProgettoDAO.getAllegatiChecklist(idChecklist, userInfo.getIdUtente());
		}

		return Response.ok().entity(allegati).build();
	}

	/*
	 * modifica dei parametri di input se funziona rimuovere commento
	 * 
	 * @Override public Response allegatiVerbaleChecklist(Long idDocIndex,
	 * HttpServletRequest req) throws InvalidParameterException, Exception {
	 * UserInfoSec userInfo = (UserInfoSec)
	 * req.getSession().getAttribute(Constants.USERINFO_SESSIONATTR); return
	 * Response.ok().entity(documentiDiProgettoDAO.allegatiVerbaleChecklist(
	 * idDocIndex, userInfo.getIdUtente(), userInfo.getIdIride())).build(); }
	 */

	@Override
	public Response allegatiVerbaleChecklistAffidamento(String codTipoDocIndex, Long idDocumentoIndex,
			HttpServletRequest req) throws InvalidParameterException, Exception {
		UserInfoSec userInfo = (UserInfoSec) req.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);
		return Response.ok().entity(documentiDiProgettoDAO.allegatiVerbaleChecklistAffidamento(codTipoDocIndex,
				idDocumentoIndex, userInfo.getIdUtente(), userInfo.getIdIride())).build();
	}

	@Override
	public Response cancellaFileConVisibilita(Long idDocumentoIndex, HttpServletRequest req)
			throws InvalidParameterException, Exception {
		UserInfoSec userInfo = (UserInfoSec) req.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);
		return Response.ok().entity(documentiDiProgettoDAO.cancellaFileConVisibilita(idDocumentoIndex,
				userInfo.getIdUtente(), userInfo.getIdIride())).build();
	}

	@Override
	public Response cancellaRecordDocumentoIndex(Long idDocumentoIndex, HttpServletRequest req)
			throws InvalidParameterException, Exception {
		UserInfoSec userInfo = (UserInfoSec) req.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);
		return Response.ok().entity(documentiDiProgettoDAO.cancellaRecordDocumentoIndex(idDocumentoIndex,
				userInfo.getIdUtente(), userInfo.getIdIride())).build();
	}

	@Override
	public Response codiceStatoDocumentoIndex(Long idDocumentoIndex, HttpServletRequest req)
			throws InvalidParameterException, Exception {
		UserInfoSec userInfo = (UserInfoSec) req.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);
		return Response.ok().entity(documentiDiProgettoDAO.codiceStatoDocumentoIndex(idDocumentoIndex,
				userInfo.getIdUtente(), userInfo.getIdIride())).build();
	}

	@Override
	@Transactional
	public Response salvaDocumentoACTA(String indiceClassificazioneEsteso, Long idProgetto, HttpServletRequest req)
			throws InvalidParameterException, Exception {
		String prf = "[DocumentiDiProgettoServiceImpl::salvaDocumentoACTA] ";
		LOG.debug(prf + "BEGIN");

		EnteProgettoDomandaVO enteProgettoDomandaVO = documentiDiProgettoDAO.getEnteProgettoDomandaVO(idProgetto);
		LOG.debug(prf + " enteProgettoDomandaVO=" + enteProgettoDomandaVO);

		UserInfoSec userInfo = (UserInfoSec) req.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);
		EsitoDTO esito = new EsitoDTO();

		if (enteProgettoDomandaVO != null && enteProgettoDomandaVO.getSettore() != null) {
			LOG.debug(prf + " enteProgettoDomandaVO.getSettore()=" + enteProgettoDomandaVO.getSettore());
			try {
				AcarisContentStreamType acarisDoc = actaManager.ricercaClassificazioneByIndiceClassEstesoCXF(
						indiceClassificazioneEsteso, enteProgettoDomandaVO.getSettore());

				PropertyType[] props = actaManager.ricercaProtocolloDocumentoByIndiceClassEstesoCXF(
						indiceClassificazioneEsteso, enteProgettoDomandaVO.getSettore());

				String codiceProtocollo = "";
				PbandiTDocProtocolloVO docProt = new PbandiTDocProtocolloVO();

				if (props != null) {
					PbandiTDocumentoIndexVO p = new PbandiTDocumentoIndexVO();

					String cod = "";
					String anno = "";
					String codAOOResp = "";

					for (PropertyType property : props) {
						if (property.getValue().getContentLength() > 0) {

							for (String content : property.getValue().getContent()) {
								LOG.info(prf + "property [" + property.getQueryName().getPropertyName() + "] val= ["
										+ content + "]");
							}

							if (property.getQueryName().getPropertyName().equals("codice")) {
								cod = property.getValue().getContent()[0];
								LOG.debug(prf + " cod=" + cod);
							}
							if (property.getQueryName().getPropertyName().equals("anno")) {
								anno = property.getValue().getContent()[0];
								LOG.debug(prf + " anno=" + anno);
							}
							if (property.getQueryName().getPropertyName().equals("dataProtocollo")) {
								String dataProtocollo = property.getValue().getContent()[0];
								LOG.debug(prf + " dataProtocollo=" + dataProtocollo); // 26/07/2019

								java.util.Date d = DateUtil.getDate(dataProtocollo);
								docProt.setDtProtocollo(new java.sql.Date(d.getTime()));
							}

							if (property.getQueryName().getPropertyName().equals("codiceAooResponsabile")) {
								codAOOResp = property.getValue().getContent()[0];
								LOG.debug(prf + " codAOOResp=" + codAOOResp);
							}

						}
					}
					codiceProtocollo = cod + "_" + codAOOResp + anno;
					LOG.debug(prf + " codiceProtocollo=" + codiceProtocollo);

					// verifico se esiste gia' un documento associato e protocollato
					Boolean found = documentiDiProgettoDAO.numeroProtocolloExists(codiceProtocollo);
					if (found != null & found.equals(Boolean.TRUE)) {
						esito.setEsito(false);
						esito.setMessaggio("Il documento protocollato è già stato associato ad un progetto.");
						LOG.debug(prf + "END");
						return Response.ok().entity(esito).build();
					}
				}

				PbandiTDocumentoIndexVO documentoIndexVO = new PbandiTDocumentoIndexVO();

				documentoIndexVO.setUuidNodo("UUID");
				documentoIndexVO.setRepository("ACTA");
				documentoIndexVO.setIdTipoDocumentoIndex(new BigDecimal(31));
				documentoIndexVO.setIdEntita(new BigDecimal(74));
				documentoIndexVO.setNomeFile(acarisDoc.getFilename());
				documentoIndexVO.setActaIndiceClassificazioneEsteso(indiceClassificazioneEsteso);
				documentoIndexVO.setIdUtenteIns(new BigDecimal(userInfo.getIdUtente()));
				documentoIndexVO.setIdProgetto(new BigDecimal(idProgetto));
				documentoIndexVO.setIdStatoDocumento(new BigDecimal(2));

				int result = documentiDiProgettoDAO.salvaDocumentoIndex(documentoIndexVO);

				if (props != null) { // salvo gli eventuali dati del protocollo nella PBANDI_T_DOC_PROTOCOLLO
					Long idDocumentoIndex = new Long(result);
					LOG.debug(prf + " idDocumentoIndex=" + idDocumentoIndex);
					docProt.setIdDocumentoIndex(new BigDecimal(idDocumentoIndex));
					docProt.setIdSistemaProt(new BigDecimal(4)); // 1 automatico, 2 acta, 3 finpiemonte, 4 ICE
					docProt.setNumProtocollo(codiceProtocollo); // 00000007_A190002022
					docProt.setIdUtenteIns(new BigDecimal(userInfo.getIdUtente()));

					genericDAO.insert(docProt);
				}

				esito.setEsito(true);

			} catch (Exception e) {
				e.printStackTrace();
				esito.setEsito(false);
				esito.setMessaggio("Impossibile allegare il file. ");
			}

		} else {
			esito.setEsito(false);
			esito.setMessaggio("Impossibile allegare il file. Settore non coerente.");
		}
		LOG.debug(prf + "esito=" + esito);
		LOG.debug(prf + "END");
		return Response.ok().entity(esito).build();
	}

	@Override
	public Response leggiFileActa(Long idDocumentoIndex, HttpServletRequest req)
			throws InvalidParameterException, Exception {
		String prf = "[DocumentiDiProgettoServiceImpl::leggiFileActa] ";
		LOG.debug(prf + "BEGIN");

		DocumentoIndexVO docIndex = new DocumentoIndexVO();
		docIndex.setIdDocumentoIndex(new BigDecimal(idDocumentoIndex));
		docIndex = genericDAO.findSingleOrNoneWhere(docIndex);
		LOG.debug(prf + "docIndex=" + docIndex);

		EnteProgettoDomandaVO enteProgettoDomandaVO = documentiDiProgettoDAO
				.getEnteProgettoDomandaVO(docIndex.getIdProgetto().longValue());
		LOG.debug(prf + " enteProgettoDomandaVO=" + enteProgettoDomandaVO);

		EsitoLeggiFile esito = new EsitoLeggiFile();

		if (enteProgettoDomandaVO != null && enteProgettoDomandaVO.getSettore() != null) {
			LOG.debug(prf + " enteProgettoDomandaVO.getDescBreveSettore()="
					+ enteProgettoDomandaVO.getDescBreveSettore());
			LOG.debug(prf + " enteProgettoDomandaVO.getSettore()=" + enteProgettoDomandaVO.getSettore());
			try {
				AcarisContentStreamType acarisDoc = actaManager.ricercaClassificazioneByIndiceClassEstesoCXF(
						docIndex.getActaIndiceClassificazEsteso(), enteProgettoDomandaVO.getSettore());

				if (acarisDoc != null) {
					esito.setEsito(true);
					esito.setNomeFile(acarisDoc.getFilename());

					// usando i WS StreamMTOM sara' sempre valorizzato, mentre stream sara' sempre
					// null
					if (acarisDoc.getStreamMTOM() != null) {
						final InputStream in = acarisDoc.getStreamMTOM().getInputStream();
						esito.setBytes(org.apache.commons.io.IOUtils.toByteArray(in));
					} else {
						LOG.warn(prf + "acarisDoc.getStreamMTOM() NULL");
						throw new Exception("File non presente su acta.");
					}
				} else {
					LOG.warn(prf + "acarisDoc nullo");
					throw new Exception("File non presente su acta.");
				}
			} catch (Exception e) {
				e.printStackTrace();
				throw e;
			}

		} else {
			throw new Exception("Impossibile aprire il file. Settore non coerente.");
		}
		LOG.debug(prf + "esito=" + esito);
		LOG.debug(prf + "END");
		return Response.ok().header("header-nome-file", esito.getNomeFile()).entity(esito.getBytes()).build();
	}

	@Override
	public Response getDocumento(BigDecimal idDocumento, String fonteDocumento, HttpServletRequest req) {
		String prf = "[DocumentiDiProgettoServiceImpl::getDocumento] ";
		LOG.debug(prf + "BEGIN");

		LOG.debug(prf + "idDocumento=" + idDocumento);
		LOG.debug(prf + "fonteDocumento=" + fonteDocumento);

		Client client = ClientBuilder.newBuilder().build();
		LOG.debug(prf + "client=" + client);

		WebTarget target = client.target(endpointFinservRestService + "/documento");
		LOG.debug(prf + "target=" + target);

		target = target.queryParam("idDocumento", idDocumento).queryParam("fonteDocumento", fonteDocumento);
		LOG.debug(prf + "target 2=" + target);

		// jakarta.ws.rs.core.Response resp =
		// (jakarta.ws.rs.core.Response)target.request().header(Constants.HEADER_NOME_CHIAMANTE,
		// Constants.HEADER_VALORE_CHIAMANTE).get();
		javax.ws.rs.core.Response resp = (javax.ws.rs.core.Response) target.request()
				.header(Constants.HEADER_NOME_CHIAMANTE, Constants.HEADER_VALORE_CHIAMANTE).get();

		byte[] dataFile = resp.readEntity(byte[].class);
		if (dataFile != null) {
			LOG.debug(prf + "scaricato file di dimensione " + dataFile.length);
		} else {
			LOG.debug(prf + "file non trovato");
		}
		LOG.debug(prf + "END");
		return Response.ok(dataFile).build();
	}

	@Override
	public Response getDocumentoList(String idDomanda, HttpServletRequest req) {
		String prf = "[DocumentiDiProgettoServiceImpl::getDocumentoList] ";
		LOG.debug(prf + "BEGIN");

		LOG.debug(prf + "idDomanda=" + idDomanda);

		LOG.debug(prf + "endpointFinservRestService=" + endpointFinservRestService);
		DocumentoListResponse dlr = null;
		try {
			Client client = ClientBuilder.newBuilder().build();
			LOG.debug(prf + "client=" + client);

			WebTarget target = client.target(endpointFinservRestService + "/documentoList");
			LOG.debug(prf + "target=" + target);

			target = target.queryParam("idDomanda", idDomanda);
			LOG.debug(prf + "target 2=" + target);

			// jakarta.ws.rs.core.Response resp =
			// (jakarta.ws.rs.core.Response)target.request().header(Constants.HEADER_NOME_CHIAMANTE,
			// Constants.HEADER_VALORE_CHIAMANTE).get();
			javax.ws.rs.core.Response resp = (javax.ws.rs.core.Response) target.request()
					.header(Constants.HEADER_NOME_CHIAMANTE, Constants.HEADER_VALORE_CHIAMANTE).get();

			LOG.debug(prf + "resp=" + resp);

			dlr = resp.readEntity(DocumentoListResponse.class);
			LOG.debug(prf + "dlr=" + dlr);

		} catch (Exception e) {
			LOG.error(prf + "ERRORE =" + e.getMessage());
			e.printStackTrace();
		}

		LOG.info(prf + " END");
		return Response.ok(dlr).build();
	}

	@Override
	public Response getAllegatiByTipoDoc(Long idTarget, Long idProgetto, String descBreveTipoDoc,
			HttpServletRequest req) throws InvalidParameterException, Exception {
		UserInfoSec userInfo = (UserInfoSec) req.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);
		return Response.ok().entity(documentiDiProgettoDAO.getAllegatiByTipoDoc(idTarget, idProgetto, descBreveTipoDoc,
				userInfo.getIdUtente(), userInfo.getIdIride())).build();
	}
}
