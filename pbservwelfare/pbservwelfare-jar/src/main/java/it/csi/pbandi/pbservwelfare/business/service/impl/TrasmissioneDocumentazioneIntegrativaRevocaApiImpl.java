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
import it.csi.pbandi.pbservwelfare.business.service.TrasmissioneDocumentazioneIntegrativaRevocaApi;
import it.csi.pbandi.pbservwelfare.dto.DocIntRevoca;
import it.csi.pbandi.pbservwelfare.dto.DocIntRevocaResponse;
import it.csi.pbandi.pbservwelfare.dto.RequestDocIntRevoca;
import it.csi.pbandi.pbservwelfare.exception.ErroreGestitoException;
import it.csi.pbandi.pbservwelfare.exception.RecordNotFoundException;
import it.csi.pbandi.pbservwelfare.integration.dao.TrasmissioneDocumentazioneIntegrativaRevocaDAO;
import it.csi.pbandi.pbservwelfare.util.CommonUtil;
import it.csi.pbandi.pbservwelfare.util.Constants;

@Transactional(timeout = 240, propagation = Propagation.REQUIRES_NEW, rollbackFor = { Exception.class })
@Component
public class TrasmissioneDocumentazioneIntegrativaRevocaApiImpl extends BaseApiServiceImpl implements TrasmissioneDocumentazioneIntegrativaRevocaApi {

	public static final String ROOT_FILE_SYSTEM = "/pbstorage_online";

	private final Logger LOG = Logger.getLogger(Constants.COMPONENT_NAME);

	protected FileApiServiceImpl fileApiServiceImpl;

	@Autowired
	TrasmissioneDocumentazioneIntegrativaRevocaDAO trasmissioneDocIntRevocaDao;

	@Override
	public Response getTrasmissioneDocIntRevoca(RequestDocIntRevoca body, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
		String prf = "[TrasmissioneDocumentazioneIntegrativaRevocaApiImpl::getTrasmissioneDocIntRevoca]";
		LOG.info(prf + " BEGIN");

		DocIntRevocaResponse datiResp = new DocIntRevocaResponse();
		try {
			LOG.info(prf + " numeroDomanda=" + body.getNumeroDomanda() + ", identificativoProcedimentoDiRevoca=" + body.getIdentificativoProcedimentoDiRevoca());
			if (!CommonUtil.isEmpty(body.getNumeroDomanda()) && body.getIdentificativoProcedimentoDiRevoca() != null) {
				// Recupero info documentazione integrativa di revoca
				Integer idStatoRevoca = trasmissioneDocIntRevocaDao.getInfoDocumentazioneIntRevoca(body.getIdentificativoProcedimentoDiRevoca());
				if (idStatoRevoca != null) {
					if (Constants.CONTRODEDUZIONE_INVIATA.equals(idStatoRevoca)) {
						// Recupero identificativo entità integrazione revoca
						Integer idEntitaIntRevoca = trasmissioneDocIntRevocaDao.getIdEntitaIntRevoca();
						// Folder per documentazione integrativa revoca
						HashMap<String, String> infoSoggettoProgetto = trasmissioneDocIntRevocaDao.getInfoPerCreazioneFolder(body.getIdentificativoProcedimentoDiRevoca());
						String nomeFolderRichiestaIntegrazione = "/Richiesta_integrazione" + body.getIdentificativoProcedimentoDiRevoca();
						Integer idFolderBeneficiario;
						Integer idFolderProgetto = trasmissioneDocIntRevocaDao.getIdFolderProgettoPadre(infoSoggettoProgetto.get("idSoggetto"), infoSoggettoProgetto.get("idProgetto"));
						Integer idFolder = null;
						if (idFolderProgetto == null) {
							// 1a
							idFolderBeneficiario = trasmissioneDocIntRevocaDao.getIdFolderSoggettoPadre(infoSoggettoProgetto.get("idSoggetto"));
							if (idFolderBeneficiario == null) {
								trasmissioneDocIntRevocaDao.insertFolder(idFolderBeneficiario, null,
										"/root",
										infoSoggettoProgetto.get("idSoggetto"), null);
							}

							// 2a
							idFolderProgetto = trasmissioneDocIntRevocaDao.getIdFolder();
							trasmissioneDocIntRevocaDao.insertFolder(idFolderProgetto, idFolderBeneficiario,
									"/" + infoSoggettoProgetto.get("codiceVisualizzato"),
									infoSoggettoProgetto.get("idSoggetto"), infoSoggettoProgetto.get("idProgetto")
							);
						}else{
							idFolder = trasmissioneDocIntRevocaDao.getIdFolderRichiestaIntegrazione(idFolderProgetto, nomeFolderRichiestaIntegrazione,
									infoSoggettoProgetto.get("idSoggetto"), infoSoggettoProgetto.get("idProgetto"));
						}
						// 3
						if(idFolder == null) {
							idFolder = trasmissioneDocIntRevocaDao.getIdFolder();
							trasmissioneDocIntRevocaDao.insertFolder(idFolder, idFolderProgetto,
									nomeFolderRichiestaIntegrazione,
									infoSoggettoProgetto.get("idSoggetto"), infoSoggettoProgetto.get("idProgetto")
							);
						}

						// Ricezione file allegati
						for (DocIntRevoca doc : body.getListaDocIntRevoca()) {
							Integer idDocIndex = trasmissioneDocIntRevocaDao.getIdDocIndex();

							// Nome univoco con cui il file verra' salvato il file.
							String nomeFileSenzaEstensione = doc.getNomeDocumento().substring(0, doc.getNomeDocumento().lastIndexOf('.'));
							String estensioneFile = doc.getNomeDocumento().substring(doc.getNomeDocumento().lastIndexOf('.'));
							String nomeFileElaborato = nomeFileSenzaEstensione + "_" + idDocIndex + estensioneFile;

							trasmissioneDocIntRevocaDao.insertDocumentoIndex(idDocIndex, idFolder, nomeFileElaborato, infoSoggettoProgetto.get("idProgetto"), infoSoggettoProgetto.get("idSoggetto"));
							// Salvataggio su file system
							InputStream is = new ByteArrayInputStream(doc.getDocumento());
							boolean esitoSalva = salvaSuFileSystem(is, nomeFileElaborato);
							if (!esitoSalva) {
								LOG.error(prf + "Salvataggio su file system fallito: termino la procedura.");
								datiResp.setEsito("KO");
							}
							// Registrazione del file
							Integer idFile = trasmissioneDocIntRevocaDao.getIdFile();
							trasmissioneDocIntRevocaDao.insertFile(idFile, idFolder, idDocIndex, nomeFileElaborato, doc.getDocumento().length);
							// Registrazione associazione file-entità
							trasmissioneDocIntRevocaDao.insertFileEntita(idFile, idEntitaIntRevoca, idFolder, infoSoggettoProgetto.get("idProgetto"));
						}
						// Aggiornamento stato attività del record
						trasmissioneDocIntRevocaDao.updateStatoAttivita(body.getIdentificativoProcedimentoDiRevoca());
						datiResp.setEsito("OK");
					} else {
						datiResp = getErroreIntRevocaInElaborazione();
					}
				} else {
					datiResp = getErroreNessunDatoSelezionatoIntRevoca();
				}
			} else {
				datiResp = getErroreParametriInvalidiDocIntRevoca();
			}
		} catch (RecordNotFoundException e) {
			throw e;
		} catch (Exception e) {
			LOG.error(prf + "Exception while trying to read DocIntRevocaResponse", e);
			throw new ErroreGestitoException("DaoException while trying to read DocIntRevocaResponse", e);
		} finally {
			LOG.info(prf + " END");
		}

		return Response.ok(datiResp).build();
	}

	private boolean salvaSuFileSystem(InputStream inputStream, String strFileName) {
		String prf = "[TrasmissioneDocumentazioneIntegrativaRevocaApiImpl::salvaFileSuFileSystem] ";
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
