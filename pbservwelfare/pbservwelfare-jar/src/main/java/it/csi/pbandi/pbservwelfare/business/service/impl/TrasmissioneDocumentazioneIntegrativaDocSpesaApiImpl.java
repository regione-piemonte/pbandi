/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservwelfare.business.service.impl;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
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
import it.csi.pbandi.pbservwelfare.business.service.TrasmissioneDocumentazioneIntegrativaDocSpesaApi;
import it.csi.pbandi.pbservwelfare.dto.DocIntegrativaSpesaResponse;
import it.csi.pbandi.pbservwelfare.dto.DocIntegrazioneSpesa;
import it.csi.pbandi.pbservwelfare.dto.RequestDocIntegrativaSpesa;
import it.csi.pbandi.pbservwelfare.exception.ErroreGestitoException;
import it.csi.pbandi.pbservwelfare.exception.RecordNotFoundException;
import it.csi.pbandi.pbservwelfare.integration.dao.TrasmissioneDocumentazioneIntegrativaDocSpesaDAO;
import it.csi.pbandi.pbservwelfare.util.Constants;

/**
 * Servizio 11
 *
 */
@Transactional(timeout = 240, propagation = Propagation.REQUIRES_NEW, rollbackFor = { Exception.class })
@Component
public class TrasmissioneDocumentazioneIntegrativaDocSpesaApiImpl extends BaseApiServiceImpl
		implements TrasmissioneDocumentazioneIntegrativaDocSpesaApi {

	public static final String ROOT_FILE_SYSTEM = "/pbstorage_online";

	private final Logger LOG = Logger.getLogger(Constants.COMPONENT_NAME);

	protected FileApiServiceImpl fileApiServiceImpl;

	@Autowired
	TrasmissioneDocumentazioneIntegrativaDocSpesaDAO trasmissioneIntDocSpesaDao;

	@Override
	public Response setTrasmissioneDocIntegrativaSpesa(RequestDocIntegrativaSpesa body, SecurityContext securityContext,
			HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
		String prf = "[TrasmissioneDocumentazioneIntegrativaDocSpesaApiImpl::setTrasmissioneDocIntegrativaSpesa]";
		LOG.info(prf + " BEGIN");

		DocIntegrativaSpesaResponse datiResp = new DocIntegrativaSpesaResponse();
		try {
			LOG.info(prf + " identificativoRichiestaDiIntegrazione=" + body.getIdentificativoRichiestaDiIntegrazione());
			if (body.getIdentificativoRichiestaDiIntegrazione() != null) {
				// Recupero informazioni richiesta di integrazione
				HashMap<String, String> infoIntegrazione = trasmissioneIntDocSpesaDao
						.getInfoRichiestaIntegrazione(body.getIdentificativoRichiestaDiIntegrazione());
				if (!infoIntegrazione.isEmpty()) {
					if (infoIntegrazione.get("dtInvio") == null) {
						// Recupero identificativo entità richiesta di integrazione
						Integer idEntitaDichSpesa = trasmissioneIntDocSpesaDao.getIdEntitaDichSpesa();
						// Folder per richiesta integrazione
						HashMap<String, String> infoSoggettoProgetto = trasmissioneIntDocSpesaDao
								.getInfoSoggettoProgetto(infoIntegrazione.get("idDichiarazioneSpesa"));
						String nomeFolderRichiestaIntegrazioneSpesa = "/Richiesta_integrazione_spesa"
								+ body.getIdentificativoRichiestaDiIntegrazione();
						Integer idFolderBeneficiario;
						Integer idFolderProgetto = trasmissioneIntDocSpesaDao.getIdFolderProgettoPadre(
								infoSoggettoProgetto.get("idSoggetto"), infoSoggettoProgetto.get("idProgetto"),
								"/" + infoSoggettoProgetto.get("codiceVisualizzato"));
						Integer idFolder = null;
						if (idFolderProgetto == null) {
							// 1a
							idFolderBeneficiario = trasmissioneIntDocSpesaDao
									.getIdFolderSoggettoPadre(infoSoggettoProgetto.get("idSoggetto"));
							if (idFolderBeneficiario == null) {
								idFolderBeneficiario = trasmissioneIntDocSpesaDao.getIdFolder();
								trasmissioneIntDocSpesaDao.insertFolder(idFolderBeneficiario, null, "/root",
										infoSoggettoProgetto.get("idSoggetto"), null);
							}

							// 2a
							idFolderProgetto = trasmissioneIntDocSpesaDao.getIdFolder();
							trasmissioneIntDocSpesaDao.insertFolder(idFolderProgetto, idFolderBeneficiario,
									"/" + infoSoggettoProgetto.get("codiceVisualizzato"),
									infoSoggettoProgetto.get("idSoggetto"), infoSoggettoProgetto.get("idProgetto"));
						} else {
							idFolder = trasmissioneIntDocSpesaDao.getIdFolderRichiestaIntegrazioneSpesa(
									idFolderProgetto, nomeFolderRichiestaIntegrazioneSpesa,
									infoSoggettoProgetto.get("idSoggetto"), infoSoggettoProgetto.get("idProgetto"));
						}
						// 3
						if (idFolder == null) {
							idFolder = trasmissioneIntDocSpesaDao.getIdFolder();
							trasmissioneIntDocSpesaDao.insertFolder(idFolder, idFolderProgetto,
									nomeFolderRichiestaIntegrazioneSpesa, infoSoggettoProgetto.get("idSoggetto"),
									infoSoggettoProgetto.get("idProgetto"));
						}

						// Ricezione file allegati
						for (DocIntegrazioneSpesa doc : body.getListaDocIntegrazioneSpesa()) {
							Integer idDocIndex = trasmissioneIntDocSpesaDao.getIdDocIndex();

							// Nome univoco con cui il file verra' salvato il file.
							String nomeFileSenzaEstensione = doc.getNomeDocumento().substring(0,
									doc.getNomeDocumento().lastIndexOf('.'));
							String estensioneFile = doc.getNomeDocumento()
									.substring(doc.getNomeDocumento().lastIndexOf('.'));
							String nomeFileElaborato = nomeFileSenzaEstensione + "_" + idDocIndex + estensioneFile;

							trasmissioneIntDocSpesaDao.insertDocumentoIndex(idDocIndex, idFolder, nomeFileElaborato,
									infoSoggettoProgetto.get("idProgetto"), infoSoggettoProgetto.get("idSoggetto"));
							// Salvataggio su file system
							InputStream is = new ByteArrayInputStream(doc.getDocumento());
							boolean esitoSalva = salvaSuFileSystem(is, nomeFileElaborato);
							if (!esitoSalva) {
								LOG.error(prf + "Salvataggio su file system fallito: termino la procedura.");
								datiResp.setEsito("KO");
							}
							// Registrazione del file
							Integer idFile = trasmissioneIntDocSpesaDao.getIdFile();
							trasmissioneIntDocSpesaDao.insertFile(idFile, idFolder, idDocIndex, nomeFileElaborato,
									doc.getDocumento().length);
							// Registrazione associazione file-entità
							trasmissioneIntDocSpesaDao.insertFileEntita(idFile, idEntitaDichSpesa,
									body.getIdentificativoRichiestaDiIntegrazione(),
									infoSoggettoProgetto.get("idProgetto"));
						}
						
						// Chiusura richiesta di integrazione
						trasmissioneIntDocSpesaDao
								.chiusuraRichiestaIntegrazione(body.getIdentificativoRichiestaDiIntegrazione());
						// Censimento notifica per istruttore
						HashMap<String, String> templateNotifica = trasmissioneIntDocSpesaDao.getTemplateNotifica();
						SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
						Date today = new Date(System.currentTimeMillis());
						Date dtDichiarazioneSpesa = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S")
								.parse(infoSoggettoProgetto.get("dtDichiarazioneSpesa"));
						String dtDichiarazioneSpesaFormattata = sdf.format(dtDichiarazioneSpesa);
						String compMessage = templateNotifica.get("compMessage");
						String compMessageNew = compMessage.replace("${data_integraz_dic}", sdf.format(today))
								.replace("${num_dichiarazione_di_spesa}", infoIntegrazione.get("idDichiarazioneSpesa"))
								.replace("${data_dichiarazione_di_spesa}", dtDichiarazioneSpesaFormattata);
						trasmissioneIntDocSpesaDao.insertNotificaProcesso(infoSoggettoProgetto.get("idProgetto"),
								compMessageNew, templateNotifica.get("idTemplateNotifica"));

						datiResp.setEsito("OK");
					} else {
						datiResp = getErroreRichiestaChiusa();
					}
				} else {
					datiResp = getErroreNessunDatoSelezionatoDocIntegrativaSpesa();
				}
			} else {
				datiResp = getErroreParametriInvalidiDocIntegrativaSpesa();
			}
		} catch (RecordNotFoundException e) {
			throw e;
		} catch (Exception e) {
			LOG.error(prf + "Exception while trying to read DocIntegrativaSpesaResponse", e);
			throw new ErroreGestitoException("DaoException while trying to read DocIntegrativaSpesaResponse", e);
		} finally {
			LOG.info(prf + " END");
		}

		return Response.ok(datiResp).build();
	}

	private boolean salvaSuFileSystem(InputStream inputStream, String strFileName) {
		String prf = "[TrasmissioneDocumentazioneIntegrativaDocSpesaApiImpl::salvaFileSuFileSystem] ";
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
