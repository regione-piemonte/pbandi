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
import it.csi.pbandi.pbservwelfare.business.service.TrasmissioneDocumentazionePerControdeduzioniApi;
import it.csi.pbandi.pbservwelfare.dto.DocControdeduzioni;
import it.csi.pbandi.pbservwelfare.dto.DocControdeduzioniResponse;
import it.csi.pbandi.pbservwelfare.dto.RequestDocControdeduzioni;
import it.csi.pbandi.pbservwelfare.exception.ErroreGestitoException;
import it.csi.pbandi.pbservwelfare.exception.RecordNotFoundException;
import it.csi.pbandi.pbservwelfare.integration.dao.TrasmissioneDocumentazioniPerControdeduzioniDAO;
import it.csi.pbandi.pbservwelfare.util.CommonUtil;
import it.csi.pbandi.pbservwelfare.util.Constants;

@Transactional(timeout = 240, propagation = Propagation.REQUIRES_NEW, rollbackFor = { Exception.class })
@Component
public class TrasmissioneDocumentazionePerControdeduzioniApiImpl extends BaseApiServiceImpl
		implements TrasmissioneDocumentazionePerControdeduzioniApi {

	public static final String ROOT_FILE_SYSTEM = "/pbstorage_online";

	private final Logger LOG = Logger.getLogger(Constants.COMPONENT_NAME);

	protected FileApiServiceImpl fileApiServiceImpl;

	@Autowired
	TrasmissioneDocumentazioniPerControdeduzioniDAO trasmissioneDocControdeduzioniDao;

	@Override
	public Response getTrasmissioneDocControdeduzioni(RequestDocControdeduzioni body, SecurityContext securityContext,
			HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
		String prf = "[TrasmissioneDocumentazionePerControdeduzioniApiImpl::getTrasmissioneDocControdeduzioni]";
		LOG.info(prf + " BEGIN");

		DocControdeduzioniResponse datiResp = new DocControdeduzioniResponse();

		try {
			LOG.info(prf + " numeroDomanda=" + body.getNumeroDomanda() + ", identificativoProcedimentoDiRevoca="
					+ body.getIdentificativoControdeduzione());
			if (!CommonUtil.isEmpty(body.getNumeroDomanda()) && body.getIdentificativoControdeduzione() != null) {
				// Recupero info documentazione per controdeduzioni
				Integer idStatoControdeduz = trasmissioneDocControdeduzioniDao.getInfoDocumentazioneControdeduzioni(body.getIdentificativoControdeduzione());
				if (idStatoControdeduz != null) {
					if (Constants.CONTRODEDUZIONE_INVIATA.equals(idStatoControdeduz)) {
						// Recupero identificativo entità controdeduzione
						Integer idEntitaControdeduzione = trasmissioneDocControdeduzioniDao
								.getIdEntitaControdeduzione();
						// Folder per controdeduzione
						HashMap<String, String> infoSoggettoProgetto = trasmissioneDocControdeduzioniDao.getInfoSoggettoProgetto(body.getIdentificativoControdeduzione());
						String nomeFolderControdeduzioni = "/Controdeduzioni" + body.getIdentificativoControdeduzione();
						Integer idFolderBeneficiario;
						Integer idFolderProgetto = trasmissioneDocControdeduzioniDao.getIdFolderProgettoPadre(infoSoggettoProgetto.get("idSoggetto"), infoSoggettoProgetto.get("idProgetto"));
						Integer idFolder = null;
						if (idFolderProgetto == null) {
							// 1a
							idFolderBeneficiario = trasmissioneDocControdeduzioniDao.getIdFolderSoggettoPadre(infoSoggettoProgetto.get("idSoggetto"));
							if (idFolderBeneficiario == null) {
								trasmissioneDocControdeduzioniDao.insertFolder(idFolderBeneficiario, null,
										"/root",
										infoSoggettoProgetto.get("idSoggetto"), null);
							}

							// 2a
							idFolderProgetto = trasmissioneDocControdeduzioniDao.getIdFolder();
							trasmissioneDocControdeduzioniDao.insertFolder(idFolderProgetto, idFolderBeneficiario,
									"/" + infoSoggettoProgetto.get("codiceVisualizzato"),
									infoSoggettoProgetto.get("idSoggetto"), infoSoggettoProgetto.get("idProgetto")
							);
						}else {
							idFolder = trasmissioneDocControdeduzioniDao.getIdFolderControdeduzioni(idFolderProgetto, nomeFolderControdeduzioni,
									infoSoggettoProgetto.get("idSoggetto"), infoSoggettoProgetto.get("idProgetto"));
						}
						// 3
						if(idFolder == null) {
							idFolder = trasmissioneDocControdeduzioniDao.getIdFolder();
							trasmissioneDocControdeduzioniDao.insertFolder(idFolder, idFolderProgetto,
									nomeFolderControdeduzioni,
									infoSoggettoProgetto.get("idSoggetto"), infoSoggettoProgetto.get("idProgetto")
							);
						}

						// Ricezione file allegati
						for (DocControdeduzioni doc : body.getListaDocControdeduzioni()) {
							Integer idDocIndex = trasmissioneDocControdeduzioniDao.getIdDocIndex();

							// Nome univoco con cui il file verra' salvato il file.
							String nomeFileSenzaEstensione = doc.getNomeDocumento().substring(0, doc.getNomeDocumento().lastIndexOf('.'));
							String estensioneFile = doc.getNomeDocumento().substring(doc.getNomeDocumento().lastIndexOf('.'));
							String nomeFileElaborato = nomeFileSenzaEstensione + "_" + idDocIndex + estensioneFile;

							trasmissioneDocControdeduzioniDao.insertDocumentoIndex(idDocIndex, idFolder, nomeFileElaborato, infoSoggettoProgetto.get("idProgetto"), infoSoggettoProgetto.get("idSoggetto"));
							// Salvataggio su file system
							InputStream is = new ByteArrayInputStream(doc.getDocumento());
							boolean esitoSalva = salvaSuFileSystem(is, nomeFileElaborato);
							if (!esitoSalva) {
								LOG.error(prf + "Salvataggio su file system fallito: termino la procedura.");
								datiResp.setEsito("KO");
							}
							// Registrazione file
							trasmissioneDocControdeduzioniDao.insertFile(idFolder, idDocIndex, nomeFileElaborato, doc.getDocumento().length);
							Integer idFile = trasmissioneDocControdeduzioniDao.getIdFile(idFolder, idDocIndex, doc.getNomeDocumento());
							// Registrazione associazione file-entità
							trasmissioneDocControdeduzioniDao.insertFileEntita(idFile, idEntitaControdeduzione, idFolder, infoSoggettoProgetto.get("idProgetto"));

						}
						// Aggiornamento stato attività del record
						trasmissioneDocControdeduzioniDao.updateStatoAttivita(body.getIdentificativoControdeduzione());
						datiResp.setEsito("OK");
					} else {
						datiResp = getErroreControdeduzioneInElaborazione();
					}
				} else {
					datiResp = getErroreNessunDatoSelezionatoDocContrRevoca();
				}
			} else {
				datiResp = getErroreParametriInvalidiDocControdeduzione();
			}
		} catch (RecordNotFoundException e) {
			throw e;
		} catch (Exception e) {
			LOG.error(prf + "Exception while trying to read DocControdeduzioniResponse", e);
			throw new ErroreGestitoException("DaoException while trying to read DocControdeduzioniResponse", e);
		} finally {
			LOG.info(prf + " END");
		}

		return Response.ok(datiResp).build();
	}

	private boolean salvaSuFileSystem(InputStream inputStream, String strFileName) {
		String prf = "[TrasmissioneDocumentazionePerControdeduzioniApiImpl::salvaFileSuFileSystem] ";
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
