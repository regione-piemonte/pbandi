/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservwelfare.business.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import it.csi.pbandi.pbservwelfare.business.service.ElencoDocumentiSpesaApi;
import it.csi.pbandi.pbservwelfare.dto.DocumentiSpesaListResponse;
import it.csi.pbandi.pbservwelfare.dto.DocumentoSpesa;
import it.csi.pbandi.pbservwelfare.exception.ErroreGestitoException;
import it.csi.pbandi.pbservwelfare.exception.RecordNotFoundException;
import it.csi.pbandi.pbservwelfare.integration.dao.ElencoDocumentiSpesaDAO;
import it.csi.pbandi.pbservwelfare.util.CommonUtil;
import it.csi.pbandi.pbservwelfare.util.Constants;

@Transactional(timeout = 240, propagation = Propagation.REQUIRES_NEW, rollbackFor = { Exception.class })
@Component
public class ElencoDocumentiSpesaApiImpl extends BaseApiServiceImpl implements ElencoDocumentiSpesaApi {

	private Logger LOG = Logger.getLogger(Constants.COMPONENT_NAME);

	@Autowired
	ElencoDocumentiSpesaDAO elencoDocumentiSpesaDao;

	@Override
	public Response getElencoDocumentiSpesa(String numeroDomanda, Integer identificativoDichiarazioneDiSpesa,
			Integer identificativoDocumentoDiSpesa, SecurityContext securityContext, HttpHeaders httpHeaders,
			HttpServletRequest httpRequest) {
		String prf = "[ElencoDocumentiSpesaApiImpl::getElencoDocumentiSpesa]";
		LOG.info(prf + " BEGIN");

		DocumentiSpesaListResponse datiResp = new DocumentiSpesaListResponse();
		List<DocumentoSpesa> documentiSpesaList = new ArrayList<DocumentoSpesa>();
		try {
			LOG.info(prf + " numeroDomanda=" + numeroDomanda + ", identificativoDichiarazioneDiSpesa"
					+ identificativoDichiarazioneDiSpesa + ", identificativoDocumentoDiSpesa"
					+ identificativoDocumentoDiSpesa);
			// Validazione parametri
			if (!CommonUtil.isEmpty(numeroDomanda) || identificativoDichiarazioneDiSpesa != null
					|| identificativoDocumentoDiSpesa != null) {
				// Estrazione identificativi documenti spesa
				List<DocumentoSpesa> listaDocumenti = new ArrayList<DocumentoSpesa>();

				if (identificativoDocumentoDiSpesa != null) {
					listaDocumenti = elencoDocumentiSpesaDao
							.getDocumentiDiSpesaDaIdDocSpesa(identificativoDocumentoDiSpesa);
				} else if (identificativoDichiarazioneDiSpesa != null) {
					listaDocumenti = elencoDocumentiSpesaDao
							.getDocumentiDiSpesaDaIdDichiarazioneSpesa(identificativoDichiarazioneDiSpesa);
				}else if (!CommonUtil.isEmpty(numeroDomanda)) {
					listaDocumenti = elencoDocumentiSpesaDao.getDocumentiDiSpesaDaNumeroDomanda(numeroDomanda);
				}

				// Recupera documenti di spesa
				if (!listaDocumenti.isEmpty()) {
					for (DocumentoSpesa doc : listaDocumenti) {
						DocumentoSpesa info = elencoDocumentiSpesaDao
								.getInfoAggiuntive(doc.getIdentificativoDocumentoDiSpesa());
						if (info != null) {
							doc.setStatoDocumento(info.getStatoDocumento());
							doc.setImportoRendicontato(info.getImportoRendicontato());
							doc.setImportoQuietanzato(info.getImportoQuietanzato());
							doc.setImportoValidato(info.getImportoValidato());
							doc.setCodiceFiscaleFornitore(info.getCodiceFiscaleFornitore());
							doc.setDenominazioneFornitore(info.getDenominazioneFornitore());
							doc.setCognome(info.getCognome());
							doc.setNome(info.getNome());
							doc.setDataInizioContratto(info.getDataInizioContratto());
							doc.setDataFineContratto(info.getDataFineContratto());
							doc.setNoteValidazione(info.getNoteValidazione());
						}
						documentiSpesaList.add(doc);
					}
					datiResp.setDocumentiSpesaList(documentiSpesaList);
					datiResp.setMessaggio("OK");
				} else {
					datiResp = getErroreProgettoNonPresenteElencoDocumenti();
				}
			} else {
				datiResp = getErroreParametriInvalidiElencoDocumenti();
			}
		} catch (RecordNotFoundException e) {
			throw e;
		} catch (Exception e) {
			LOG.error(prf + "Exception while trying to read DocumentiSpesaListResponse", e);
			throw new ErroreGestitoException("DaoException while trying to read DocumentiSpesaListResponse", e);
		} finally {
			LOG.info(prf + " END");
		}

		return Response.ok(datiResp).build();
	}

}
