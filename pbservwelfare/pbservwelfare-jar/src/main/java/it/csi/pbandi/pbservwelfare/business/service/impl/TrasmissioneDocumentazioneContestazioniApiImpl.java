/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservwelfare.business.service.impl;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import it.csi.pbandi.filestorage.business.api.impl.FileApiServiceImpl;
import it.csi.pbandi.pbservwelfare.business.service.TrasmissioneDocumentazioneContestazioniApi;
import it.csi.pbandi.pbservwelfare.dto.DocContestazione;
import it.csi.pbandi.pbservwelfare.dto.DocContestazioneResponse;
import it.csi.pbandi.pbservwelfare.dto.RequestDocContestazione;
import it.csi.pbandi.pbservwelfare.exception.ErroreGestitoException;
import it.csi.pbandi.pbservwelfare.exception.RecordNotFoundException;
import it.csi.pbandi.pbservwelfare.integration.dao.TrasmissioneDocumentazioneContestazioniDAO;
import it.csi.pbandi.pbservwelfare.util.CommonUtil;
import it.csi.pbandi.pbservwelfare.util.Constants;

@Transactional(timeout = 240, propagation = Propagation.REQUIRES_NEW, rollbackFor = { Exception.class })
@Component
public class TrasmissioneDocumentazioneContestazioniApiImpl extends BaseApiServiceImpl
		implements TrasmissioneDocumentazioneContestazioniApi {

	public static final String ROOT_FILE_SYSTEM = "/pbstorage_online";

	private final Logger LOG = Logger.getLogger(Constants.COMPONENT_NAME);

	protected FileApiServiceImpl fileApiServiceImpl;

	@Autowired
	TrasmissioneDocumentazioneContestazioniDAO trasmissioneDocContestazioneDao;

	@Override
	public Response getTrasmissioneDocContestazione(RequestDocContestazione body, SecurityContext securityContext,
			HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
		String prf = "[TrasmissioneDocumentazioneContestazioniApiImpl::getTrasmissioneDocContestazione]";
		LOG.info(prf + " BEGIN");

		DocContestazioneResponse datiResp = new DocContestazioneResponse();
		try {
			LOG.info(prf + " numeroDomanda=" + body.getNumeroDomanda() + ", identificativoContestazione="
					+ body.getIdentificativoContestazione());
			if (!CommonUtil.isEmpty(body.getNumeroDomanda()) && body.getIdentificativoContestazione() != null) {
				// Recupero informazioni documentazione contestazioni
				Integer idStatoContestazione = trasmissioneDocContestazioneDao
						.getInfoDocContestazioni(body.getIdentificativoContestazione());
				if (idStatoContestazione != null) {
					if (Constants.CONTRODEDUZIONE_INVIATA.equals(idStatoContestazione)) {
						// Recupero identificativo entità contestazione
						Integer idEntitaContestazione = trasmissioneDocContestazioneDao.getIdEntitaContestazione();
						// Folder per documentazione contestazioni
						HashMap<String, String> infoSoggettoProgetto = trasmissioneDocContestazioneDao.getInfoSoggettoProgetto(body.getIdentificativoContestazione());
						String nomeFolderContestazioni = "/Contestazioni" + body.getIdentificativoContestazione();
						Integer idFolderBeneficiario;
						Integer idFolderProgetto = trasmissioneDocContestazioneDao.getIdFolderProgettoPadre(infoSoggettoProgetto.get("idSoggetto"), infoSoggettoProgetto.get("idProgetto"));
						Integer idFolder = null;
						if (idFolderProgetto == null) {
							// 1a
							idFolderBeneficiario = trasmissioneDocContestazioneDao.getIdFolderSoggettoPadre(infoSoggettoProgetto.get("idSoggetto"));
							if (idFolderBeneficiario == null) {
								trasmissioneDocContestazioneDao.insertFolder(idFolderBeneficiario, null,
										"/root",
										infoSoggettoProgetto.get("idSoggetto"), null);
							}

							// 2a
							idFolderProgetto = trasmissioneDocContestazioneDao.getIdFolder();
							trasmissioneDocContestazioneDao.insertFolder(idFolderProgetto, idFolderBeneficiario,
									"/" + infoSoggettoProgetto.get("codiceVisualizzato"),
									infoSoggettoProgetto.get("idSoggetto"), infoSoggettoProgetto.get("idProgetto")
							);
						}else {
							idFolder = trasmissioneDocContestazioneDao.getIdFolderContestazioni(idFolderProgetto, nomeFolderContestazioni,
									infoSoggettoProgetto.get("idSoggetto"), infoSoggettoProgetto.get("idProgetto")
							);
						}
						// 3
						if(idFolder == null) {
							idFolder = trasmissioneDocContestazioneDao.getIdFolder();
							trasmissioneDocContestazioneDao.insertFolder(idFolder, idFolderProgetto,
									nomeFolderContestazioni,
									infoSoggettoProgetto.get("idSoggetto"), infoSoggettoProgetto.get("idProgetto")
							);
						}

						// Ricezione file allegati
						for (DocContestazione doc : body.getListaDocContestazione()) {
							Integer idDocIndex = trasmissioneDocContestazioneDao.getIdDocIndex();

							// Nome univoco con cui il file verra' salvato il file.
							String nomeFileSenzaEstensione = doc.getNomeDocumento().substring(0, doc.getNomeDocumento().lastIndexOf('.'));
							String estensioneFile = doc.getNomeDocumento().substring(doc.getNomeDocumento().lastIndexOf('.'));
							String nomeFileElaborato = nomeFileSenzaEstensione + "_" + idDocIndex + estensioneFile;

							trasmissioneDocContestazioneDao.insertDocumentoIndex(idDocIndex, idFolder,
									nomeFileElaborato, infoSoggettoProgetto.get("idProgetto"),
									infoSoggettoProgetto.get("idSoggetto"));
							// Salvataggio su file system
							InputStream is = new ByteArrayInputStream(doc.getDocumento());
							boolean esitoSalva = salvaSuFileSystem(is, nomeFileElaborato);
							if (!esitoSalva) {
								LOG.error(prf + "Salvataggio su file system fallito: termino la procedura.");
								datiResp.setEsito("KO");
							}
							// Registrazione del file
							Integer idFile = trasmissioneDocContestazioneDao.getIdFile();
							trasmissioneDocContestazioneDao.insertFile(idFile, idFolder, idDocIndex,
									nomeFileElaborato, doc.getDocumento().length);
							// Registrazione associazione file-entità
							trasmissioneDocContestazioneDao.insertFileEntita(idFile, idEntitaContestazione, idFolder,
									infoSoggettoProgetto.get("idProgetto"));
						}
						// Aggiornamento stato attività del record
						trasmissioneDocContestazioneDao.updateStatoAttivita(body.getIdentificativoContestazione());
						datiResp.setEsito("OK");
					} else {
						datiResp = getErroreDocContestazioneInElaborazione();
					}
				} else {
					datiResp = getErroreNessunDatoSelezionatoDocContestazione();
				}
			} else {
				datiResp = getErroreParametriInvalidiDocContestazione();
			}
		} catch (RecordNotFoundException e) {
			throw e;
		} catch (Exception e) {
			LOG.error(prf + "Exception while trying to read DocContestazioneResponse", e);
			throw new ErroreGestitoException("DaoException while trying to read DocContestazioneResponse", e);
		} finally {
			LOG.info(prf + " END");
		}

		return Response.ok(datiResp).build();
	}

	private boolean salvaSuFileSystem(InputStream inputStream, String strFileName) {
		String prf = "[TrasmissioneDocumentazioneContestazioniApiImpl::salvaFileSuFileSystem] ";
		LOG.info(prf + " BEGIN");
		boolean esito;
		try {
			fileApiServiceImpl = new FileApiServiceImpl(ROOT_FILE_SYSTEM);

			esito = fileApiServiceImpl.uploadFile(inputStream, strFileName, "AF");
			if (!esito) {
				// Se la cartella non esiste, la creo e riprovo.
				esito = fileApiServiceImpl.dirExists("AF", true);
				if (esito) {
					esito = fileApiServiceImpl.uploadFile(inputStream, strFileName, "AF");
					LOG.info(prf + " file [" + strFileName + "] salvato su storage");
				}
			}

		} catch (Exception e) {
			LOG.error(prf + " ERRORE: ", e);
			return false;
		}
		return esito;
	}

}
