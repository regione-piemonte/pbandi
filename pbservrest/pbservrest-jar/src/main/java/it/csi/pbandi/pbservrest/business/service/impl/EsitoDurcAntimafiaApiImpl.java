/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservrest.business.service.impl;

/**
 * Ultima modifica: 18 maggio 2023
 * 
 * Nota:
 * Questa classe é stata sviluppata in conformità con le specifiche
 * descritte nel documento: 08052023_PBAN-OPE-STE-V01_ANAGE001_getEsitoDurcAntimafia_01.doc
 */
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

import it.csi.pbandi.pbservrest.business.service.EsitiDurcAntimafiaApi;
import it.csi.pbandi.pbservrest.dto.Esito;
import it.csi.pbandi.pbservrest.dto.EsitoDurcAntimafia;
import it.csi.pbandi.pbservrest.dto.EsitoDurcAntimafiaListResponse;
import it.csi.pbandi.pbservrest.exception.ErroreGestitoException;
import it.csi.pbandi.pbservrest.exception.RecordNotFoundException;
import it.csi.pbandi.pbservrest.integration.dao.EsitoDurcAntimafiaDAO;
import it.csi.pbandi.pbservrest.model.EsitoDurcAntimafiaInRest;
import it.csi.pbandi.pbservrest.model.IstruttoreAsfTmp;
import it.csi.pbandi.pbservrest.model.SoggettoDomandaTmp;
import it.csi.pbandi.pbservrest.model.SoggettoRichiestaTmp;
import it.csi.pbandi.pbservrest.util.Constants;

@Transactional(timeout = 240, propagation = Propagation.REQUIRES_NEW, rollbackFor = { Exception.class })
@Component
public class EsitoDurcAntimafiaApiImpl implements EsitiDurcAntimafiaApi {

	private static final String LINEA_TRATTEGGIATA = "========================================================";

	private Logger LOG = Logger.getLogger(Constants.COMPONENT_NAME);

	@Autowired
	EsitoDurcAntimafiaDAO esitoDurcAntimafiaDao;

	@Override
	public Response getEsitoDurcAntimafia(String codFiscaleSoggetto, String cognomeIstruttore, String nomeIstruttore,
			String codFiscaleIstruttore, String numeroDomanda, String tipoRichiesta, String modalitaRichiesta,
			SecurityContext securityContext, HttpHeaders httpHeaders,
			HttpServletRequest httpRequest) {

		String prf = "[EsitoDurcAntimafiaServiceImpl::getEsitoDurcAntimafia]";
		LOG.info(prf + " BEGIN");

		LOG.info(prf + " LINEA_TRATTEGGIATA");
		LOG.info(prf + " Parametri in ingresso sono tutti obbligatori: ");
		LOG.info(prf + " codFiscaleSoggetto: " + codFiscaleSoggetto);
		LOG.info(prf + " cognomeIstruttore: " + cognomeIstruttore);
		LOG.info(prf + " nomeIstruttore: " + nomeIstruttore);
		LOG.info(prf + " codFiscaleIstruttore: " + codFiscaleIstruttore);
		LOG.info(prf + " numeroDomanda: " + numeroDomanda);
		LOG.info(prf + " tipoRichiesta: " + tipoRichiesta);
		LOG.info(prf + " modalitaRichiesta: " + modalitaRichiesta);
		LOG.info(prf + " LINEA_TRATTEGGIATA");

		List<Esito> esitoList = null;

		EsitoDurcAntimafiaInRest datiInputRest = new EsitoDurcAntimafiaInRest(
				codFiscaleSoggetto, cognomeIstruttore, nomeIstruttore, codFiscaleIstruttore, 
				numeroDomanda, tipoRichiesta, modalitaRichiesta);

		List<SoggettoDomandaTmp> soggDmdList = new ArrayList<SoggettoDomandaTmp>();
		List<SoggettoRichiestaTmp> soggettoRichiestaList = null;
		List<EsitoDurcAntimafia> esitiList = null;
		
		EsitoDurcAntimafiaListResponse esitiResp = new EsitoDurcAntimafiaListResponse();

		List<IstruttoreAsfTmp> istruttoreAsfList = null;

		Integer idSoggetto = null;
		Integer idDomanda = null;
		Integer idSoggettoDurc = null;
		Integer idSoggettoDsan = null;
		Integer idSoggettoAntimafia = null;
		boolean isError = false;
		
		try {

			LOG.info(prf + LINEA_TRATTEGGIATA);
			LOG.info(prf + " Eseguo test di connessione al database: ");
			if (!esitoDurcAntimafiaDao.testConnection()) {
				LOG.error(prf + " [Errore sistemistico bloccante: 003] ");
				Esito esito = new Esito();
				esito.setDescErrore(Constants.MESSAGGI.ERRORE.ERRORE_CONESSIONE_DATABASE);
				esito.setCodiceErrore(003);
				esito.setEsitoServizio(Constants.ESITO.KO);
				esitoList = new ArrayList<Esito>();
				esitoList.add(esito);
				return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(esitoList).build();
			}
			LOG.info(prf + LINEA_TRATTEGGIATA);

			LOG.info(prf + " numeroDomanda=" + numeroDomanda);

			LOG.info(prf + LINEA_TRATTEGGIATA);
			LOG.info(prf + " 2.a. Controllo match beneficiario-domanda");
			LOG.info(prf + LINEA_TRATTEGGIATA);
			soggDmdList = esitoDurcAntimafiaDao.getEsitoDurcAntimafia(codFiscaleSoggetto, numeroDomanda);

			LOG.info(prf + "Controllo match beneficiario-domanda - inizio");
			if (soggDmdList != null && !soggDmdList.isEmpty()) {
				for (int i = 0; i < soggDmdList.size(); i++) {
					
					/* recupero idSoggetto e idDomanda */
					idSoggetto = soggDmdList.get(0).getIdSoggetto();
					idDomanda = soggDmdList.get(0).getIdDomanda();
				}
				LOG.info(prf + "Controllo match beneficiario-domanda terminato con successo.");
				LOG.info(prf + "2.a.3: Prosegue con: 2.b.Controllo esistenza del documento richiesto: 2.b");
				
			} else {
				isError = true;
				LOG.error(prf + "2.a.4 Non esiste un match tra beneficiario e domanda sul sistema PBANDI");
				Esito esito = new Esito();
				esito.setDescErrore(Constants.MESSAGGI.ERRORE.MATCH_BENEFICIARIO_DOMANDA);
				esito.setCodiceErrore(006);
				esito.setEsitoServizio(Constants.ESITO.KO);
				esitoList = new ArrayList<Esito>();
				esitoList.add(esito);
				
				return Response.status(Response.Status.NOT_FOUND).entity(esitoList).build();
			}
			LOG.info(prf + " idSoggetto=" + idSoggetto);
			LOG.info(prf + " idDomanda=" + idDomanda);

			LOG.info(prf + LINEA_TRATTEGGIATA);
			LOG.info(prf + " 2.b: Controllo esistenza del documento richiesto");
			LOG.info(prf + LINEA_TRATTEGGIATA);

			if (!isError) {
				
				if (("DURC").equalsIgnoreCase(tipoRichiesta)) {
					
					// 2.b.1a: se Durc
					LOG.info(prf + LINEA_TRATTEGGIATA);
					LOG.info(prf + " INIZIO DURC");
					LOG.info(prf + LINEA_TRATTEGGIATA);

					LOG.info(prf + LINEA_TRATTEGGIATA);
					LOG.info(prf + " 2.b.1a: Controllo esistenza del documento richiesto");
					LOG.info(prf + LINEA_TRATTEGGIATA);

					if (idSoggetto != null && idSoggetto > 0) {
						idSoggettoDurc = esitoDurcAntimafiaDao.getIdSoggettoDurc(idSoggetto);
						LOG.info(prf + " idSoggettoDurc=" + idSoggettoDurc);
					}

					if (idSoggettoDurc != null && idSoggettoDurc > 0) {

						LOG.info("procedo al passo Estrazione DURC");

						try {

							esitiList = estrazioneDurc(numeroDomanda, idDomanda, idSoggettoDurc);
							
							if(esitiList == null || esitiList.isEmpty()) {
								Esito esito = new Esito();
								esito.setDescErrore(Constants.MESSAGGI.ERRORE.NO_DATO_SELEZIONATO);
								esito.setCodiceErrore(001);
								esito.setEsitoServizio(Constants.ESITO.KO);
								esitoList = new ArrayList<Esito>();
								esitoList.add(esito);
								
								return Response.status(Response.Status.NOT_FOUND).entity(esitoList).build();
							}

						} catch (Exception e) {
							LOG.info(e);
						}

					}
					else {

						LOG.info(prf + "start passo (2.b.1a.1) Durc>Dsan\n");
						LOG.info(prf + "Fallimento: idSoggettoDurc risulta null");

						// Ulteriore controllo: verifico che esista idSoggettoDsan
						LOG.info(prf + " Eseguo ulteriore controllo [esistenza record idSoggettoDsan]");
						
						// verifica presenza richiesta by idSoggetto
						List<SoggettoRichiestaTmp> richiesteList = new ArrayList<SoggettoRichiestaTmp>();
						richiesteList = esitoDurcAntimafiaDao.getRichiestaDurc(idSoggetto);
						LOG.info(prf + " dim.: " + richiesteList.size());
						
						if(richiesteList.size() > 0) {
							// estrazione DURC
							try {

								esitiList = estrazioneDurc(numeroDomanda, idDomanda, idSoggettoDurc);
								
								if(esitiList == null || esitiList.isEmpty()) {
									Esito esito = new Esito();
									esito.setDescErrore(Constants.MESSAGGI.ERRORE.NO_DATO_SELEZIONATO);
									esito.setCodiceErrore(001);
									esito.setEsitoServizio(Constants.ESITO.KO);
									esitoList = new ArrayList<Esito>();
									esitoList.add(esito);
									
									return Response.status(Response.Status.NOT_FOUND).entity(esitoList).build();
								}

							} catch (Exception e) {
								LOG.info(e);
							}
							
						} else {
							
							idSoggettoDsan = esitoDurcAntimafiaDao.getIdSoggettoDsan(idDomanda);
							LOG.info(prf + " idSoggettoDsan=" + idSoggettoDsan);
							
							if (idSoggettoDsan != null && idSoggettoDsan > 0) {
								
								LOG.info(prf + " controllo (2.b.1a.1) con successo, eseguo [estrazione DSAN]");
								esitiList = estrazioneDSAN(idSoggettoDsan, idDomanda, numeroDomanda);
								
								if(esitiList == null || esitiList.isEmpty()) {
									Esito esito = new Esito();
									esito.setDescErrore(Constants.MESSAGGI.ERRORE.NO_DATO_SELEZIONATO);
									esito.setCodiceErrore(001);
									esito.setEsitoServizio(Constants.ESITO.KO);
									esitoList = new ArrayList<Esito>();
									esitoList.add(esito);
									
									return Response.status(Response.Status.NOT_FOUND).entity(esitoList).build();
								}
								
							} else {
								
								LOG.info("[controllo (2.b.1a.1) fallito] - proseguo con [criteri di inserimento richiesta] e solo dopo ad [Estrazione (DSAN ?!? o DURC ?!?)] ");
								
								// 4.A
								// 4. Criteri di inserimento richiesta:
								// A. Controllo esistenza richiesta su base dati
								soggettoRichiestaList = new ArrayList<SoggettoRichiestaTmp>();
								
								LOG.info(prf + " 4.A.1 sono in Durc, eseguo recupero dati by: idDomanda");
								// soggettoRichiestaList = esitoDurcAntimafiaDao.getDomandaRichiestaDsan(idSoggetto);
								soggettoRichiestaList = esitoDurcAntimafiaDao.getDomandaRichiestaDsan(idDomanda);
								
								if (soggettoRichiestaList != null && !soggettoRichiestaList.isEmpty()) {
									
									LOG.info("4.A.2a: (success), eseguo estrazione DSAN");
									esitiList = estrazioneDSAN(idSoggettoDsan, idDomanda, numeroDomanda);
									
									if(esitiList == null || esitiList.isEmpty()) {
										Esito esito = new Esito();
										esito.setDescErrore(Constants.MESSAGGI.ERRORE.NO_DATO_SELEZIONATO);
										esito.setCodiceErrore(001);
										esito.setEsitoServizio(Constants.ESITO.KO);
										esitoList = new ArrayList<Esito>();
										esitoList.add(esito);
										
										return Response.status(Response.Status.NOT_FOUND).entity(esitoList).build();
									}
									
								} else {
									
									LOG.info("4.A.2b: (no success) ");
									LOG.info("Passo algoritmo: [B Controllo esistenza soggetto richiedente a sistema (Istruttore ASF)]");
									
									LOG.info(prf + " idSoggetto= " + idSoggetto);
									istruttoreAsfList = new ArrayList<IstruttoreAsfTmp>();
									
									LOG.info(prf + " 4.B.1: idSoggetto|nome|cognome|cf ");
									istruttoreAsfList = esitoDurcAntimafiaDao.getDatiIstruttoreASF(codFiscaleIstruttore);
									
									if (istruttoreAsfList != null && !istruttoreAsfList.isEmpty()) {
										
										LOG.info("4.C. Controllo esistenza utenza riferita al soggetto a sistema (Istruttore ASF)");
										
										Integer idIstruttore = istruttoreAsfList.get(0).getIdIstruttore();
										LOG.info("idIstruttore: " + idIstruttore);
										
										if (esitoDurcAntimafiaDao.recordExists(idIstruttore)) {
											LOG.info("(4.C.2a) - trovato record con idSoggetto");
											LOG.info("Sistema passa all'algoritmo: [E - Inserimento richiesta a sistema]");
											
											boolean isInseritoRichiestaASistema =  esitoDurcAntimafiaDao.insertRichiesta(datiInputRest, idDomanda, idIstruttore);
											LOG.info("isInseritoRichiestaASistema: " + isInseritoRichiestaASistema);
											
											if(isInseritoRichiestaASistema) {
												LOG.info("DURC: Inserimento richiesta a sistema avvenuta con successo.");
												esitiList = estrazioneDurc(numeroDomanda, idDomanda, idSoggettoDurc);
												
											}else {
												LOG.info("DURC: Inserimento richiesta a sistema NON risulta andato a buon fine!");
												Esito esito = new Esito();
												esito.setDescErrore(Constants.MESSAGGI.ERRORE.ERRORE_INSERIMENTO_UTENTE);
												esito.setCodiceErrore(007);
												esito.setEsitoServizio(Constants.ESITO.KO);
												esitoList = new ArrayList<Esito>();
												esitoList.add(esito);
												
												return Response.status(Response.Status.NOT_FOUND).entity(esitoList).build();
											}
											
										}else {
											LOG.info("(4.C.2b) - Nessun record trovato con idSoggetto");
											LOG.info("Sistema passa all'algoritmo: [D - Inserimento utente a sistema]");
											
											LOG.info(prf + " eseguo prima inserimento utente ");
											boolean isInseritoUtenteASistema = esitoDurcAntimafiaDao.insertUtente(datiInputRest, idIstruttore);
											LOG.info("isInseritoUtenteASistema: " + isInseritoUtenteASistema);
											
											if(isInseritoUtenteASistema) {
												LOG.info("DURC: Inserimento utente a sistema avvenuta con successo.");
												
												LOG.info(prf + " poi eseguo inserimento richiesta ");
												boolean isInseritoRichiestaASistema =  esitoDurcAntimafiaDao.insertRichiesta(datiInputRest, idDomanda, idIstruttore);
												LOG.info("isInseritoRichiestaASistema: " + isInseritoRichiestaASistema);
												
												if(isInseritoRichiestaASistema) {
													LOG.info("DURC: Inserimento richiesta a sistema avvenuta con successo.");
													LOG.info("eseguo estrazione Durc");
													esitiList = estrazioneDurc(numeroDomanda, idDomanda, idSoggettoDurc);
													
												}else {
													LOG.info("DURC: Inserimento richiesta a sistema NON risulta andato a buon fine!");
													Esito esito = new Esito();
													esito.setDescErrore(Constants.MESSAGGI.ERRORE.ERRORE_INSERIMENTO_UTENTE);
													esito.setCodiceErrore(007);
													esito.setEsitoServizio(Constants.ESITO.KO);
													esitoList = new ArrayList<Esito>();
													esitoList.add(esito);
													
													return Response.status(Response.Status.NOT_FOUND).entity(esitoList).build();
												}
												
											}else {
												LOG.info("DURC: Inserimento utente a sistema NON risulta andato a buon fine!");
												Esito esito = new Esito();
												esito.setDescErrore(Constants.MESSAGGI.ERRORE.ERRORE_INSERIMENTO_UTENTE);
												esito.setCodiceErrore(007);
												esito.setEsitoServizio(Constants.ESITO.KO);
												esitoList = new ArrayList<Esito>();
												esitoList.add(esito);
												
												return Response.status(Response.Status.NOT_FOUND).entity(esitoList).build();
											}
										}
									} 
									else {
										
										/*
										 * 4.B.2b. Altrimenti se il sistema non trova il codice fiscale passato in input
										 * all'interno delle tabelle specificate in precedenza, l'algoritmo termina per
										 * fallimento e passa alla sezione in: Scrittura, partendo dal passo D
										 * Inserimento soggetto a sistema.
										 */
										LOG.info("(4.b.2b) Scrittura: passaggio algoritmo (D) Inserimento soggetto a sistema");
										
										LOG.info("(D) Inserimento soggetto a sistema");
										
										Integer idSoggettoNuovo = esitoDurcAntimafiaDao.insertSoggetto(datiInputRest);
										LOG.info("idSoggettoNuovo: " + idSoggettoNuovo);
										
										if (idSoggettoNuovo != null && idSoggettoNuovo > 0) {
											LOG.info("DURC: Inserimento soggetto a sistema avvenuta con successo.");
											if(istruttoreAsfList != null && !istruttoreAsfList.isEmpty()) {
												istruttoreAsfList.get(0).setIdIstruttore(idSoggettoNuovo);
											}
										} else {
											LOG.info("DURC: Inserimento soggetto a sistema NON risulta andato a buon fine!");
										}
										
										LOG.info("ora eseguo inserimento utente");
										boolean isInseritoUtenteASistema = esitoDurcAntimafiaDao.insertUtente(datiInputRest, idSoggettoNuovo);
										LOG.info("isInseritoUtenteASistema: " + isInseritoUtenteASistema);
										
										if(isInseritoUtenteASistema) {
											LOG.info("DURC: Inserimento utente a sistema avvenuta con successo.");
											LOG.info("ora eseguo inserimento richiesta");
											boolean isInseritoRichiestaASistema =  esitoDurcAntimafiaDao.insertRichiesta(datiInputRest, idDomanda, idSoggettoNuovo);
											LOG.info("isInseritoRichiestaASistema: " + isInseritoRichiestaASistema);
											
											// inserire richiesta
											if(isInseritoRichiestaASistema) {
												LOG.info("DURC: Inserimento richiesta a sistema avvenuta con successo.");
												LOG.info("eseguo estrazione Durc");
												esitiList = estrazioneDurc(numeroDomanda, idDomanda, idSoggettoDurc);
												
											}else {
												LOG.info("DURC: Inserimento richiesta a sistema NON risulta andato a buon fine!");
												Esito esito = new Esito();
												esito.setDescErrore(Constants.MESSAGGI.ERRORE.ERRORE_INSERIMENTO_UTENTE);
												esito.setCodiceErrore(007);
												esito.setEsitoServizio(Constants.ESITO.KO);
												esitoList = new ArrayList<Esito>();
												esitoList.add(esito);
												
												return Response.status(Response.Status.NOT_FOUND).entity(esitoList).build();
											}
											
										}else {
											LOG.info("DURC: Inserimento utente a sistema NON risulta andato a buon fine!");
											Esito esito = new Esito();
											esito.setDescErrore(Constants.MESSAGGI.ERRORE.ERRORE_INSERIMENTO_UTENTE);
											esito.setCodiceErrore(007);
											esito.setEsitoServizio(Constants.ESITO.KO);
											esitoList = new ArrayList<Esito>();
											esitoList.add(esito);
											
											return Response.status(Response.Status.NOT_FOUND).entity(esitoList).build();
										}
									}
								}
							}
							LOG.info(prf + LINEA_TRATTEGGIATA);
							LOG.info(prf + " FINE DURC");
							LOG.info(prf + LINEA_TRATTEGGIATA);
							/* end passo b.1a.1 Durc>Dsan */
						}
					}
				} 
				else if (("DSAN in assenza di DURC").equalsIgnoreCase(tipoRichiesta)) {
					
					LOG.info(prf + LINEA_TRATTEGGIATA);
					LOG.info(prf + " INIZIO DSAN");
					LOG.info(prf + LINEA_TRATTEGGIATA);

					LOG.info(prf + LINEA_TRATTEGGIATA);
					LOG.info(prf + " b. Controllo esistenza del documento richiesto, sono in DSAN");
					LOG.info(prf + LINEA_TRATTEGGIATA);

					/* inizio algo DSAN */
					LOG.info(prf + " controllo 2.b.1a ");
					
					if (idDomanda != null) {
						LOG.info(prf + " recupero idSoggetto x DSAN ");
						idSoggettoDsan = esitoDurcAntimafiaDao.getIdSoggettoDsan(idDomanda);
						LOG.info(prf + " idSoggettoDsan= " + idSoggettoDsan);
					}

					// verifico se idSoggettoDsan diverso da null
					if (idSoggettoDsan != null && idSoggettoDsan > 0) {
						LOG.info("Procedo al passo Estrazione DSAN");

						try {

							/* start estrazione DSAN */
							esitiList = estrazioneDSAN(idSoggettoDsan, idDomanda, numeroDomanda);
							
							if(esitiList == null || esitiList.isEmpty()) {
								Esito esito = new Esito();
								esito.setDescErrore(Constants.MESSAGGI.ERRORE.NO_DATO_SELEZIONATO);
								esito.setCodiceErrore(001);
								esito.setEsitoServizio(Constants.ESITO.KO);
								esitoList = new ArrayList<Esito>();
								esitoList.add(esito);
								
								return Response.status(Response.Status.NOT_FOUND).entity(esitoList).build();
							}

						} catch (Exception e) {
							LOG.info(e);
						}

					} else {
						
						LOG.info("Algoritmo prosegue alla sezione [criteri di inserimento richiesta] e solo dopo ad [Estrazione (DSAN)]");
						
						// 4.A Criteri di inserimento richiesta: Controllo esistenza richiesta su base dati
						soggettoRichiestaList = new ArrayList<SoggettoRichiestaTmp>();

						// sono in DSAN, eseguo recupero dati by: idDomanda
						soggettoRichiestaList = esitoDurcAntimafiaDao.getDomandaRichiestaDsan(idDomanda);
						
						if(soggettoRichiestaList != null && !soggettoRichiestaList.isEmpty()) {
							LOG.info("4.A.2a: (success), eseguo estrazione DSAN");
							esitiList = estrazioneDSAN(idSoggettoDsan, idDomanda, numeroDomanda);
							
							if(esitiList == null || esitiList.isEmpty()) {
								Esito esito = new Esito();
								esito.setDescErrore(Constants.MESSAGGI.ERRORE.NO_DATO_SELEZIONATO);
								esito.setCodiceErrore(001);
								esito.setEsitoServizio(Constants.ESITO.KO);
								esitoList = new ArrayList<Esito>();
								esitoList.add(esito);
								
								return Response.status(Response.Status.NOT_FOUND).entity(esitoList).build();
							}
						}
						else {
							LOG.info("4.A.2b: (no success) ");
							LOG.info("B Controllo esistenza soggetto richiedente a sistema (Istruttore ASF)");
							
							LOG.info(prf + " idSoggetto= " + idSoggetto);
							istruttoreAsfList = new ArrayList<IstruttoreAsfTmp>();
							
							/* 4.B.1: idSoggetto|nome|cognome|cf */
							istruttoreAsfList = esitoDurcAntimafiaDao.getDatiIstruttoreASF(codFiscaleIstruttore);
							
							if( istruttoreAsfList != null && !istruttoreAsfList.isEmpty()) {
								
								/*
								4.B.2a Se esiste
									 algoritmo termina con successo 
									 e prosegue con il 
									 C. Controllo esistenza utenza riferita al soggetto a sistema (Istruttore ASF)
								 */
								LOG.info("C. Controllo esistenza utenza riferita al soggetto a sistema (Istruttore ASF)");
								
								// C.1
								Integer idIstruttore = istruttoreAsfList.get(0).getIdIstruttore();
								LOG.info("idIstruttore: " + idIstruttore);
								
								if(esitoDurcAntimafiaDao.recordExists(idIstruttore)) {
									/*
										C.2a.	Se il riscontro suddetto è positivo, 
										l'algoritmo di controllo termina con successo, 
										il sistema passa alla sezione 
											Scrittura : passaggio Inserimento richiesta a sistema.
									 **/
									LOG.info("C.2a (success)");
									LOG.info("E: inserimento richiesta a sistema su PBANDI_T_RICHIESTA ...");
									
									boolean isInseritoRichiestaASistema =  esitoDurcAntimafiaDao.insertRichiesta(datiInputRest, idDomanda, idIstruttore);
									LOG.info("isInseritoRichiestaASistema: " + isInseritoRichiestaASistema);
									
									if(isInseritoRichiestaASistema) {
										LOG.info("DSAN: Inserimento richiesta a sistema avvenuta con successo.");
										
										esitiList = estrazioneDSAN(idSoggettoDsan, idDomanda, numeroDomanda);
										
										if(esitiList == null || esitiList.isEmpty()) {
											Esito esito = new Esito();
											esito.setDescErrore(Constants.MESSAGGI.ERRORE.NO_DATO_SELEZIONATO);
											esito.setCodiceErrore(001);
											esito.setEsitoServizio(Constants.ESITO.KO);
											esitoList = new ArrayList<Esito>();
											esitoList.add(esito);
											
											return Response.status(Response.Status.NOT_FOUND).entity(esitoList).build();
										}
										
									}else {
										LOG.info("DSAN: Inserimento richiesta a sistema NON risulta andato a buon fine!");
										Esito esito = new Esito();
										esito.setDescErrore(Constants.MESSAGGI.ERRORE.ERRORE_INSERIMENTO_RICHIESTA);
										esito.setCodiceErrore(8);
										esito.setEsitoServizio(Constants.ESITO.KO);
										esitoList = new ArrayList<Esito>();
										esitoList.add(esito);
										
										return Response.status(Response.Status.NOT_FOUND).entity(esitoList).build();
									}
									
								}else {

									LOG.info("C. 2b: Scrittura: passaggio Insert");
									LOG.info("D: Inserimento utente a sistema");
									
									boolean isInseritoUtenteASistema = esitoDurcAntimafiaDao.insertUtente(datiInputRest, idIstruttore);
									LOG.info("isInseritoUtenteASistema: " + isInseritoUtenteASistema);
									
									if(isInseritoUtenteASistema) {
										LOG.info("DSAN: Inserimento utente a sistema avvenuta con successo.");
										
										boolean isInseritoRichiestaASistema =  esitoDurcAntimafiaDao.insertRichiesta(datiInputRest, idDomanda, idIstruttore);
										LOG.info("isInseritoRichiestaASistema: " + isInseritoRichiestaASistema);
										
										if(isInseritoRichiestaASistema) {
											LOG.info("DSAN: Inserimento richiesta a sistema avvenuta con successo.");
											
											esitiList = estrazioneDSAN(idSoggettoDsan, idDomanda, numeroDomanda);
											
											if(esitiList == null || esitiList.isEmpty()) {
												Esito esito = new Esito();
												esito.setDescErrore(Constants.MESSAGGI.ERRORE.NO_DATO_SELEZIONATO);
												esito.setCodiceErrore(001);
												esito.setEsitoServizio(Constants.ESITO.KO);
												esitoList = new ArrayList<Esito>();
												esitoList.add(esito);
												
												return Response.status(Response.Status.NOT_FOUND).entity(esitoList).build();
											}
											
										}else {
											LOG.info("DSAN: Inserimento richiesta a sistema NON risulta andato a buon fine!");
											Esito esito = new Esito();
											esito.setDescErrore(Constants.MESSAGGI.ERRORE.ERRORE_INSERIMENTO_RICHIESTA);
											esito.setCodiceErrore(8);
											esito.setEsitoServizio(Constants.ESITO.KO);
											esitoList = new ArrayList<Esito>();
											esitoList.add(esito);
											
											return Response.status(Response.Status.NOT_FOUND).entity(esitoList).build();
										}
										
									}else {
										LOG.info("DSAN: Inserimento utente a sistema NON risulta andato a buon fine!");
										
										Esito esito = new Esito();
										esito.setDescErrore(Constants.MESSAGGI.ERRORE.ERRORE_INSERIMENTO_UTENTE);
										esito.setCodiceErrore(7);
										esito.setEsitoServizio(Constants.ESITO.KO);
										esitoList = new ArrayList<Esito>();
										esitoList.add(esito);
										
										return Response.status(Response.Status.NOT_FOUND).entity(esitoList).build();
									}
								}
							}
							else {

								/* 4.B.2b */
								LOG.info("Scrittura: passaggio algoritmo (D) Inserimento soggetto a sistema");
								
								LOG.info("(D) Inserimento soggetto a sistema");
								Integer idSoggettoNuovo = esitoDurcAntimafiaDao.insertSoggetto(datiInputRest); 
								LOG.info("idSoggettoNuovo: " + idSoggettoNuovo);
								
								if(idSoggettoNuovo != null && idSoggettoNuovo > 0) {
									LOG.info("DSAN: Inserimento soggetto a sistema avvenuta con successo.");
									
									if(esitoDurcAntimafiaDao.recordExists(idSoggettoNuovo)) {

										LOG.info("4.C.2a (success)");
										LOG.info("E: inserimento richiesta a sistema su PBANDI_T_RICHIESTA ...");
										
										boolean isInseritoRichiestaASistema =  esitoDurcAntimafiaDao.insertRichiesta(datiInputRest, idDomanda, idSoggettoNuovo);
										LOG.info("isInseritoRichiestaASistema: " + isInseritoRichiestaASistema);
										
										if(isInseritoRichiestaASistema) {
											LOG.info("DSAN: Inserimento richiesta a sistema avvenuta con successo.");
											
											esitiList = estrazioneDSAN(idSoggettoDsan, idDomanda, numeroDomanda);
											
											if(esitiList == null || esitiList.isEmpty()) {
												Esito esito = new Esito();
												esito.setDescErrore(Constants.MESSAGGI.ERRORE.NO_DATO_SELEZIONATO);
												esito.setCodiceErrore(001);
												esito.setEsitoServizio(Constants.ESITO.KO);
												esitoList = new ArrayList<Esito>();
												esitoList.add(esito);
												
												return Response.status(Response.Status.NOT_FOUND).entity(esitoList).build();
											}
											
										}else {
											LOG.info("DSAN: Inserimento richiesta a sistema NON risulta andato a buon fine!");
											Esito esito = new Esito();
											esito.setDescErrore(Constants.MESSAGGI.ERRORE.ERRORE_INSERIMENTO_RICHIESTA);
											esito.setCodiceErrore(8);
											esito.setEsitoServizio(Constants.ESITO.KO);
											esitoList = new ArrayList<Esito>();
											esitoList.add(esito);
											
											return Response.status(Response.Status.NOT_FOUND).entity(esitoList).build();
										}
										
									}else {

										LOG.info("4.C. 2b: Scrittura: Insert");
										LOG.info("Algoritmo D: Insert utente a sistema");
										boolean isInseritoUtenteASistema = esitoDurcAntimafiaDao.insertUtente(datiInputRest, idSoggettoNuovo);
										LOG.info("isInseritoUtenteASistema: " + isInseritoUtenteASistema);
										
										if(isInseritoUtenteASistema) {
											LOG.info("DSAN: Inserimento utente a sistema avvenuta con successo.");
											
											boolean isInseritoRichiestaASistema =  esitoDurcAntimafiaDao.insertRichiesta(datiInputRest, idDomanda, idSoggettoNuovo);
											LOG.info("isInseritoRichiestaASistema: " + isInseritoRichiestaASistema);
											
											if(isInseritoRichiestaASistema) {
												LOG.info("DSAN: Inserimento richiesta a sistema avvenuta con successo.");
												
												esitiList = estrazioneDSAN(idSoggettoDsan, idDomanda, numeroDomanda);
												
												if(esitiList == null || esitiList.isEmpty()) {
													Esito esito = new Esito();
													esito.setDescErrore(Constants.MESSAGGI.ERRORE.NO_DATO_SELEZIONATO);
													esito.setCodiceErrore(001);
													esito.setEsitoServizio(Constants.ESITO.KO);
													esitoList = new ArrayList<Esito>();
													esitoList.add(esito);
													
													return Response.status(Response.Status.NOT_FOUND).entity(esitoList).build();
												}
												
											}else {
												LOG.info("DSAN: Inserimento richiesta a sistema NON risulta andato a buon fine!");
												Esito esito = new Esito();
												esito.setDescErrore(Constants.MESSAGGI.ERRORE.ERRORE_INSERIMENTO_RICHIESTA);
												esito.setCodiceErrore(8);
												esito.setEsitoServizio(Constants.ESITO.KO);
												esitoList = new ArrayList<Esito>();
												esitoList.add(esito);
												
												return Response.status(Response.Status.NOT_FOUND).entity(esitoList).build();
											}
											
										}else {
											LOG.info("DSAN: Inserimento utente a sistema NON risulta andato a buon fine!");
											
											Esito esito = new Esito();
											esito.setDescErrore(Constants.MESSAGGI.ERRORE.ERRORE_INSERIMENTO_UTENTE);
											esito.setCodiceErrore(7);
											esito.setEsitoServizio(Constants.ESITO.KO);
											esitoList = new ArrayList<Esito>();
											esitoList.add(esito);
											
											return Response.status(Response.Status.NOT_FOUND).entity(esitoList).build();
										}
									}
									
								}else {
									LOG.info("DSAN: Inserimento soggetto a sistema NON risulta andato a buon fine!");
									
									Esito esito = new Esito();
									esito.setDescErrore(Constants.MESSAGGI.ERRORE.ERRORE_INSERIMENTO_SOGGETTO);
									esito.setCodiceErrore(9);
									esito.setEsitoServizio(Constants.ESITO.KO);
									esitoList = new ArrayList<Esito>();
									esitoList.add(esito);
									
									return Response.status(Response.Status.NOT_FOUND).entity(esitoList).build();
								}					
							}
						}
					}
					LOG.info(prf + LINEA_TRATTEGGIATA);
					LOG.info(prf + " FINE DSAN");
					LOG.info(prf + LINEA_TRATTEGGIATA);
					/* end code DSAN */

				} 
				else if (("ANTIMAFIA").equalsIgnoreCase(tipoRichiesta)) {

					LOG.info(prf + LINEA_TRATTEGGIATA);
					LOG.info(prf + " INIZIO ANTIMAFIA");
					LOG.info(prf + LINEA_TRATTEGGIATA);

					/* algo ANTIMAFIA */
					LOG.info(prf + " controllo 2.b.1b ");
					
					if (idDomanda != null) {
						idSoggettoAntimafia = esitoDurcAntimafiaDao.getIdSoggettoAntimafia(idDomanda);
						LOG.info(prf + " idSoggettoAntimafia= " + idSoggettoAntimafia);
					}

					if (idSoggettoAntimafia != null && idSoggettoAntimafia > 0 ) {

						try {
							
							LOG.info("Procedo con estrazione ANTIMAFIA");
							esitiList = estrazioneANTIMAFIA(idSoggettoAntimafia, idDomanda, numeroDomanda);
							
							if(esitiList == null || esitiList.isEmpty()) {
								Esito esito = new Esito();
								esito.setDescErrore(Constants.MESSAGGI.ERRORE.NO_DATO_SELEZIONATO);
								esito.setCodiceErrore(001);
								esito.setEsitoServizio(Constants.ESITO.KO);
								esitoList = new ArrayList<Esito>();
								esitoList.add(esito);
								
								return Response.status(Response.Status.NOT_FOUND).entity(esitoList).build();
							}

						} catch (Exception e) {
							LOG.info(e);
						}

					} else {
						
						// 2.b.1b.1
						LOG.info(prf + "Fallimento: idSoggettoAntimafia risulta null");
						LOG.info("Procedo al passo 2.b.1b.1 ANTIMAFIA > BDNA");

						if (idDomanda != null) {
							idSoggettoAntimafia = esitoDurcAntimafiaDao.getIdSoggettoAntimafiaBDNA(idDomanda);
							LOG.info(prf + " idSoggettoAntimafia= " + idSoggettoAntimafia);
						}
						
						/*
						 * verifico se esiste un record con dt_ricezione_bdna valorizzato e
						 * dt_scadenza_antimafia non valorizzato
						 */
						if (esitoDurcAntimafiaDao.recordBdnaExist(idDomanda)) {
							
							/* Implementato nuova estrazione BDNA in data: 07072023 @dt */
							LOG.info("estrazione Antimafia");
							esitiList = estrazioneANTIMAFIA_BDNA(idSoggettoAntimafia, idDomanda, numeroDomanda);

							if(esitiList == null || esitiList.isEmpty()) {
								Esito esito = new Esito();
								esito.setDescErrore(Constants.MESSAGGI.ERRORE.NO_DATO_SELEZIONATO);
								esito.setCodiceErrore(001);
								esito.setEsitoServizio(Constants.ESITO.KO);
								esitoList = new ArrayList<Esito>();
								esitoList.add(esito);
								
								return Response.status(Response.Status.NOT_FOUND).entity(esitoList).build();
							}
							
						} else {

							LOG.info("Procedo ai criteri di inserimento richiesta");
							LOG.info("e successivamente al passo Estrazione ANTIMAFIA");

							soggettoRichiestaList = new ArrayList<SoggettoRichiestaTmp>();

							LOG.info("4.A.1: verifico se esiste una richiesta attiva per la combinazione domanda – tipo richiesta");
							soggettoRichiestaList = esitoDurcAntimafiaDao.getDomandaRichiestaAntimafia(idDomanda);

							if (soggettoRichiestaList != null && !soggettoRichiestaList.isEmpty()) {
								
								LOG.info("4.A.2a: (success), eseguo estrazione Antimafia");
								esitiList = estrazioneANTIMAFIA(idSoggettoAntimafia, idDomanda, numeroDomanda);
							
								if(esitiList == null || esitiList.isEmpty()) {
									Esito esito = new Esito();
									esito.setDescErrore(Constants.MESSAGGI.ERRORE.NO_DATO_SELEZIONATO);
									esito.setCodiceErrore(001);
									esito.setEsitoServizio(Constants.ESITO.KO);
									esitoList = new ArrayList<Esito>();
									esitoList.add(esito);
									
									return Response.status(Response.Status.NOT_FOUND).entity(esitoList).build();
								}
							
							} else {
								LOG.info("4.A.2b: (no success) ");
								LOG.info("B Controllo esistenza soggetto richiedente a sistema (Istruttore ASF)");

								LOG.info(prf + " idSoggetto= " + idSoggetto);
								istruttoreAsfList = new ArrayList<IstruttoreAsfTmp>();

								/* 4B.1: idSoggetto|nome|cognome|cf */
								istruttoreAsfList = esitoDurcAntimafiaDao.getDatiIstruttoreASF(codFiscaleIstruttore);

								if (istruttoreAsfList != null && !istruttoreAsfList.isEmpty()) {
									/*
									 * 4B.2a Se esiste algoritmo termina con successo e prosegue con il C. Controllo
									 * esistenza utenza riferita al soggetto a sistema (Istruttore ASF)
									 */
									LOG.info("4.C. Controllo esistenza utenza riferita al soggetto a sistema (Istruttore ASF)");

									Integer idIstruttore = istruttoreAsfList.get(0).getIdIstruttore();
									LOG.info("idIstruttore: " + idIstruttore);

									// 4C.1
									if (esitoDurcAntimafiaDao.recordExists(idIstruttore)) {

										LOG.info("4C.2a (success)");
										LOG.info("E: inserimento richiesta a sistema su PBANDI_T_RICHIESTA ...");

										boolean isInseritoRichiestaASistema = esitoDurcAntimafiaDao.insertRichiesta(datiInputRest, idDomanda, idIstruttore);
										LOG.info("isInseritoRichiestaASistema: " + isInseritoRichiestaASistema);

										if (isInseritoRichiestaASistema) {
											LOG.info("Antimafia: Inserimento richiesta a sistema avvenuta con successo.");
											
											LOG.info("estrazione Antimafia");
											esitiList = estrazioneANTIMAFIA(idSoggettoAntimafia, idDomanda, numeroDomanda);

											if(esitiList == null || esitiList.isEmpty()) {
												Esito esito = new Esito();
												esito.setDescErrore(Constants.MESSAGGI.ERRORE.NO_DATO_SELEZIONATO);
												esito.setCodiceErrore(001);
												esito.setEsitoServizio(Constants.ESITO.KO);
												esitoList = new ArrayList<Esito>();
												esitoList.add(esito);
												
												return Response.status(Response.Status.NOT_FOUND).entity(esitoList).build();
											}
											
										} else {
											LOG.info("Antimafia: Inserimento richiesta a sistema NON risulta andato a buon fine!");
											Esito esito = new Esito();
											esito.setDescErrore(Constants.MESSAGGI.ERRORE.ERRORE_INSERIMENTO_RICHIESTA);
											esito.setCodiceErrore(8);
											esito.setEsitoServizio(Constants.ESITO.KO);
											esitoList = new ArrayList<Esito>();
											esitoList.add(esito);
											
											return Response.status(Response.Status.NOT_FOUND).entity(esitoList).build();
										}

									} else {

										LOG.info("4C.2b: Scrittura: passaggio algoritmo D. Inserimento utente a sistema");
										// verifica se utente risulta a sistema
										
										LOG.info("D: Inserimento utente a sistema");
										boolean isInseritoUtenteASistema = esitoDurcAntimafiaDao.insertUtente(datiInputRest, idIstruttore);
										LOG.info("isInseritoUtenteASistema: " + isInseritoUtenteASistema);
										
										if(isInseritoUtenteASistema) {
											LOG.info("Antimafia: Inserimento utente a sistema avvenuta con successo.");
											
											boolean isInseritoRichiestaASistema = esitoDurcAntimafiaDao.insertRichiesta(datiInputRest, idDomanda, idIstruttore);
											LOG.info("isInseritoRichiestaASistema: " + isInseritoRichiestaASistema);

											if (isInseritoRichiestaASistema) {
												LOG.info("Antimafia: Inserimento richiesta a sistema avvenuta con successo.");
												
												LOG.info("estrazione Antimafia");
												esitiList = estrazioneANTIMAFIA(idSoggettoAntimafia, idDomanda, numeroDomanda);

												if(esitiList == null || esitiList.isEmpty()) {
													Esito esito = new Esito();
													esito.setDescErrore(Constants.MESSAGGI.ERRORE.NO_DATO_SELEZIONATO);
													esito.setCodiceErrore(001);
													esito.setEsitoServizio(Constants.ESITO.KO);
													esitoList = new ArrayList<Esito>();
													esitoList.add(esito);
													
													return Response.status(Response.Status.NOT_FOUND).entity(esitoList).build();
												}
												
											} else {
												LOG.info("Antimafia: Inserimento richiesta a sistema NON risulta andato a buon fine!");
												Esito esito = new Esito();
												esito.setDescErrore(Constants.MESSAGGI.ERRORE.ERRORE_INSERIMENTO_RICHIESTA);
												esito.setCodiceErrore(8);
												esito.setEsitoServizio(Constants.ESITO.KO);
												esitoList = new ArrayList<Esito>();
												esitoList.add(esito);
												
												return Response.status(Response.Status.NOT_FOUND).entity(esitoList).build();
											}
											
										}else {
											LOG.info("Antimafia: Inserimento utente a sistema NON risulta andato a buon fine!");
											Esito esito = new Esito();
											esito.setDescErrore(Constants.MESSAGGI.ERRORE.ERRORE_INSERIMENTO_UTENTE);
											esito.setCodiceErrore(9);
											esito.setEsitoServizio(Constants.ESITO.KO);
											esitoList = new ArrayList<Esito>();
											esitoList.add(esito);
											
											return Response.status(Response.Status.NOT_FOUND).entity(esitoList).build();
										}
									}
								}
								else {
									/* 4.B.2b */
									LOG.info("Scrittura: passaggio algoritmo (D) Inserimento soggetto a sistema");
									LOG.info("(D) Inserimento soggetto a sistema");

									Integer idSoggettoNuovo = esitoDurcAntimafiaDao.insertSoggetto(datiInputRest); 
									LOG.info("idSoggettoNuovo: " + idSoggettoNuovo);
									
									if(idSoggettoNuovo != null && idSoggettoNuovo > 0) {
										LOG.info("Antimafia: Inserimento soggetto a sistema avvenuta con successo.");
										
										boolean isInseritoUtenteASistema = esitoDurcAntimafiaDao.insertUtente(datiInputRest, idSoggettoNuovo);
										LOG.info("isInseritoUtenteASistema: " + isInseritoUtenteASistema);
										
										if(isInseritoUtenteASistema) {
											LOG.info("Antimafia: Inserimento utente a sistema avvenuta con successo.");
											
											boolean isInseritoRichiestaASistema = esitoDurcAntimafiaDao.insertRichiesta(datiInputRest, idDomanda, idSoggettoNuovo);
											LOG.info("isInseritoRichiestaASistema: " + isInseritoRichiestaASistema);

											if (isInseritoRichiestaASistema) {
												LOG.info("Antimafia: Inserimento richiesta a sistema avvenuta con successo.");
												
												LOG.info("estrazione Antimafia");
												esitiList = estrazioneANTIMAFIA(idSoggettoAntimafia, idDomanda, numeroDomanda);

												if(esitiList == null || esitiList.isEmpty()) {
													Esito esito = new Esito();
													esito.setDescErrore(Constants.MESSAGGI.ERRORE.NO_DATO_SELEZIONATO);
													esito.setCodiceErrore(001);
													esito.setEsitoServizio(Constants.ESITO.KO);
													esitoList = new ArrayList<Esito>();
													esitoList.add(esito);
													
													return Response.status(Response.Status.NOT_FOUND).entity(esitoList).build();
												}
												
											} else {
												LOG.info("Antimafia: Inserimento richiesta a sistema NON risulta andato a buon fine!");
												Esito esito = new Esito();
												esito.setDescErrore(Constants.MESSAGGI.ERRORE.ERRORE_INSERIMENTO_RICHIESTA);
												esito.setCodiceErrore(8);
												esito.setEsitoServizio(Constants.ESITO.KO);
												esitoList = new ArrayList<Esito>();
												esitoList.add(esito);
												
												return Response.status(Response.Status.NOT_FOUND).entity(esitoList).build();
											}
											
										}else {
											LOG.info("Antimafia: Inserimento utente a sistema NON risulta andato a buon fine!");
											Esito esito = new Esito();
											esito.setDescErrore(Constants.MESSAGGI.ERRORE.ERRORE_INSERIMENTO_UTENTE);
											esito.setCodiceErrore(9);
											esito.setEsitoServizio(Constants.ESITO.KO);
											esitoList = new ArrayList<Esito>();
											esitoList.add(esito);
											
											return Response.status(Response.Status.NOT_FOUND).entity(esitoList).build();
										}
										
									}else {
										LOG.info("Antimafia: Inserimento soggetto a sistema NON risulta andato a buon fine!");
										
										Esito esito = new Esito();
										esito.setDescErrore(Constants.MESSAGGI.ERRORE.ERRORE_INSERIMENTO_SOGGETTO);
										esito.setCodiceErrore(9);
										esito.setEsitoServizio(Constants.ESITO.KO);
										esitoList = new ArrayList<Esito>();
										esitoList.add(esito);
										
										return Response.status(Response.Status.NOT_FOUND).entity(esitoList).build();
									}
								}
							}
						}
					}
					LOG.info(prf + LINEA_TRATTEGGIATA);
					LOG.info(prf + " FINE ANTIMAFIA");
					LOG.info(prf + LINEA_TRATTEGGIATA);
				}
				/* fine algo ANTIMAFIA */

			} else {
				LOG.info("ERRORE");
			}

		} catch (RecordNotFoundException e) {
			throw e;
		} catch (Exception e) {
			LOG.error(prf + "Exception while trying to read EsitoDurcAntimafia", e);
			throw new ErroreGestitoException("DaoException while trying to read EsitoDurcAntimafia", e);
		} finally {
			LOG.info(prf + " END");
		}

		esitiResp.setEsitoDurcAntimafiaList(esitiList);
		return Response.ok(esitiResp).build();
		
	}
	
	
	/* sezione estrazione : (Durc - Dsan - Antimafia) */

	/******************
	 ** estrazione durc
	 ******************/
	private List<EsitoDurcAntimafia> estrazioneDurc(String numeroDomanda, Integer idDomanda, Integer idSoggettoDurc) {

		List<EsitoDurcAntimafia> esitiList;
		
		/* DESC_ESITO_RICHIESTE | DT_EMISSIONE_DURC | DT_SCADENZ **/
		List<EsitoDurcAntimafia> datiDurcList = new ArrayList<EsitoDurcAntimafia>();

		if(idSoggettoDurc != null && idSoggettoDurc > 0) {
			datiDurcList = esitoDurcAntimafiaDao.getDatiDurc(idSoggettoDurc, idDomanda, numeroDomanda);
			LOG.info(" datiDurcList dim= " + datiDurcList.size());
		}
		
		if (datiDurcList == null || datiDurcList.isEmpty()) {
			LOG.info("DURC: [Descrizione esito richieste] [Data emissione durc] [Data scadenza] - Nessun dato trovato per questo numeroDomanda: ["+ numeroDomanda + "]");
		}

		/* DT_CHIUSURA_RICHIESTA| DESC_TIPO_RICHIESTA| DESC_STATO_RICHIESTA */
		List<EsitoDurcAntimafia> datiRichiestiDurcList = new ArrayList<EsitoDurcAntimafia>();
		datiRichiestiDurcList = esitoDurcAntimafiaDao.getDatiRichiestiDurc(idDomanda, numeroDomanda);
		
		LOG.info(" datiRichiestiDurcList dim= " + datiRichiestiDurcList.size());

		if (datiRichiestiDurcList == null || datiRichiestiDurcList.isEmpty()) {
			LOG.info( "DURC: [Data chiusura richiesta] [Descrizione Tipo Richiesta] [Descrizione stato richiesta] - Nessun dato trovato per questo numeroDomanda: [" + numeroDomanda + "]");
		}

		// istanzio elenco per memorizzarvi tutti i dati (estratti) da restituire al client
		esitiList = new ArrayList<>();

		for (EsitoDurcAntimafia fq : datiDurcList) {

			EsitoDurcAntimafia existingDataDurc = esitiList.stream()
					.filter(b -> b.getIdDomanda().equals(fq.getIdDomanda())).findFirst()
					.orElse(null);
			if (existingDataDurc == null) {
				esitiList.add(fq);
			} else {
				/* DESC_ESITO_RICHIESTE | DT_EMISSIONE_DURC | DT_SCADENZA */
				existingDataDurc.setEsito(fq.getEsito() != null ? fq.getEsito() : existingDataDurc.getEsito());
				existingDataDurc.setDtEmissione(fq.getDtEmissione() != null ? fq.getDtEmissione() : existingDataDurc.getDtEmissione());
				existingDataDurc.setDtScadenza(fq.getDtScadenza() != null ? fq.getDtScadenza() : existingDataDurc.getDtScadenza());
			}
		}

		for (EsitoDurcAntimafia sq : datiRichiestiDurcList) {

			EsitoDurcAntimafia existingDataDurc = esitiList.stream()
					.filter(b -> b.getIdDomanda().equals(sq.getIdDomanda())).findFirst()
					.orElse(null);
			if (existingDataDurc == null) {
				esitiList.add(sq);
			} else {
				/* DT_CHIUSURA_RICHIESTA| DESC_TIPO_RICHIESTA| DESC_STATO_RICHIESTA  */
				existingDataDurc.setDtChiusuraRichiesta(sq.getDtChiusuraRichiesta() != null ? sq.getDtChiusuraRichiesta() : existingDataDurc.getDtChiusuraRichiesta());
				existingDataDurc.setDescTipoRichiesta(sq.getDescTipoRichiesta() != null ? sq.getDescTipoRichiesta() : existingDataDurc.getDescTipoRichiesta());
				existingDataDurc.setDescStatoRichiesta(sq.getDescStatoRichiesta() != null ? sq.getDescStatoRichiesta() : existingDataDurc.getDescStatoRichiesta());
			}
		}
		return esitiList;
	}

	
	
	/*****************
	* estrazione dsan
	******************/
	private List<EsitoDurcAntimafia> estrazioneDSAN(Integer idSoggettoDsan, Integer idDomanda, String numeroDomanda) {

		List<EsitoDurcAntimafia> esitiList = new ArrayList<EsitoDurcAntimafia>();

		if (idSoggettoDsan != null && idSoggettoDsan > 0) {

			/* dt_emissione_dsan | dt_scadenza | dt_chiusura_richiesta */
			List<EsitoDurcAntimafia> datiDurcList = new ArrayList<EsitoDurcAntimafia>();
			
			/* q1 estrazioneDSAN */
			datiDurcList = esitoDurcAntimafiaDao.getDatiDsan(idSoggettoDsan, idDomanda, numeroDomanda);
			LOG.info(" datiDurcList dim= " + datiDurcList.size());

			if (datiDurcList != null && !datiDurcList.isEmpty()) {
				LOG.info("DSAN: [Descrizione esito richieste] [Data emissione dsan] [Data scadenza] - Nessun dato trovato per questo numeroDomanda: ["+ numeroDomanda + "]");

				for (EsitoDurcAntimafia fq : datiDurcList) {
					
					EsitoDurcAntimafia existingDataDurc = esitiList.stream().filter(b -> b.getIdDomanda().equals(fq.getIdDomanda())).findFirst().orElse(null);
					if (existingDataDurc == null) {
						esitiList.add(fq);
					} else {
						/*
					 		DESC_ESITO_RICHIESTE | DT_EMISSIONE_DURC | DT_SCADENZA
					 	*/
						existingDataDurc.setEsito(fq.getEsito() != null ? fq.getEsito() : existingDataDurc.getEsito());
						existingDataDurc.setDtRicezioneDocumentazione(fq.getDtRicezioneDocumentazione() != null ? fq.getDtRicezioneDocumentazione() : existingDataDurc.getDtRicezioneDocumentazione());
						existingDataDurc.setDtScadenza( fq.getDtScadenza() != null ? fq.getDtScadenza() : existingDataDurc.getDtScadenza());
					}
				}
			}
		}

		/* DT_CHIUSURA_RICHIESTA | DESC_BREVE_TIPO_RICHIESTA | DESC_STATO_RICHIESTA */
		List<EsitoDurcAntimafia> datiRichiestiDurcList = new ArrayList<EsitoDurcAntimafia>();
		
		/* q2 estrazioneDSAN */
		datiRichiestiDurcList = esitoDurcAntimafiaDao.getDatiRichiestiDsan(idDomanda, numeroDomanda);
		LOG.info(" datiRichiestiDurcList dim= " + datiRichiestiDurcList.size());

		if (datiRichiestiDurcList == null || datiRichiestiDurcList.isEmpty()) {
			LOG.info("DURC: [Data chiusura richiesta] [Descrizione Tipo Richiesta] [Descrizione stato richiesta] - Nessun dato trovato per questo numeroDomanda: ["+ numeroDomanda + "]");
		}

		if(datiRichiestiDurcList != null && !datiRichiestiDurcList.isEmpty()) {
			for (EsitoDurcAntimafia sq : datiRichiestiDurcList) {
				
				EsitoDurcAntimafia existingDataDurc = esitiList.stream()
						.filter(b -> b.getIdDomanda().equals(sq.getIdDomanda())).findFirst()
						.orElse(null);
				if (existingDataDurc == null) {
					esitiList.add(sq);
				} else {
					/* DT_CHIUSURA_RICHIESTA | DESC_BREVE_TIPO_RICHIESTA | DESC_STATO_RICHIESTA  */
					existingDataDurc.setDtChiusuraRichiesta(sq.getDtChiusuraRichiesta() != null ? sq.getDtChiusuraRichiesta() : existingDataDurc.getDtChiusuraRichiesta());
					existingDataDurc.setDescTipoRichiesta(sq.getDescTipoRichiesta() != null ? sq.getDescTipoRichiesta() : existingDataDurc.getDescTipoRichiesta());
					existingDataDurc.setDescStatoRichiesta(sq.getDescStatoRichiesta() != null ? sq.getDescStatoRichiesta() : existingDataDurc.getDescStatoRichiesta());
				}
			}
		}

		return esitiList;
	}
	
	
	
	
	/*********************
	* estrazione antimafia
	*********************/
	private List<EsitoDurcAntimafia> estrazioneANTIMAFIA(Integer idSoggettoAntimafia, Integer idDomanda, String numeroDomanda) {
					
		List<EsitoDurcAntimafia> esitiList = new ArrayList<EsitoDurcAntimafia>();

		List<EsitoDurcAntimafia> datiAntimafiaList = null;
		if (idSoggettoAntimafia != null && idSoggettoAntimafia > 0) {

			/* recupero: desc_esito_richieste | dt_emissione | dt_scadenza_antimafia **/
			datiAntimafiaList = new ArrayList<EsitoDurcAntimafia>();
			datiAntimafiaList = esitoDurcAntimafiaDao.getDatiAntimafia(idSoggettoAntimafia, idDomanda, numeroDomanda);
			LOG.info(" datiDurcList dim= " + datiAntimafiaList.size());

			if (datiAntimafiaList != null && datiAntimafiaList.isEmpty()) {
				LOG.info("DSAN: [Descrizione esito richieste] [Data emissione antimafia] [Data scadenza antimafia] - Nessun dato trovato per questo numeroDomanda: ["+ numeroDomanda + "]");
			}
			
			if(datiAntimafiaList != null && !datiAntimafiaList.isEmpty()) {

				for (EsitoDurcAntimafia fq : datiAntimafiaList) {
					
					EsitoDurcAntimafia existingDataDurc = esitiList.stream().filter(b -> b.getIdDomanda().equals(fq.getIdDomanda())).findFirst().orElse(null);
					if (existingDataDurc == null) {
						esitiList.add(fq);
					} else {
						/* q3: DESC_ESITO_RICHIESTE | DT_EMISSIONE_DURC | DT_SCADENZA */
						existingDataDurc.setEsito(fq.getEsito() != null ? fq.getEsito() : existingDataDurc.getEsito());
						existingDataDurc.setDtRicezioneDocumentazione(fq.getDtRicezioneDocumentazione() != null ? fq.getDtRicezioneDocumentazione() : existingDataDurc.getDtRicezioneDocumentazione());
						existingDataDurc.setDtScadenza( fq.getDtScadenza() != null ? fq.getDtScadenza() : existingDataDurc.getDtScadenza());
					}
				}
			}
		}

			/* DT_CHIUSURA_RICHIESTA | DESC_TIPO_RICHIESTA | DESC_STATO_RICHIESTA */
			List<EsitoDurcAntimafia> datiRichiestiAntimafiaList = new ArrayList<EsitoDurcAntimafia>();
			datiRichiestiAntimafiaList = esitoDurcAntimafiaDao.getDatiRichiestiAntimafia(idDomanda, numeroDomanda);
			LOG.info(" datiRichiestiAntimafiaList dim= " + datiRichiestiAntimafiaList.size());

			if (datiRichiestiAntimafiaList == null || datiRichiestiAntimafiaList.isEmpty()) {
				LOG.info("DURC: [Data chiusura richiesta] [Descrizione Tipo Richiesta] [Descrizione stato richiesta] - Nessun dato trovato per questo numeroDomanda: ["+ numeroDomanda + "]");
			}

			for (EsitoDurcAntimafia sq : datiRichiestiAntimafiaList) {

				EsitoDurcAntimafia existingDataDurc = esitiList.stream()
						.filter(b -> b.getIdDomanda().equals(sq.getIdDomanda())).findFirst()
						.orElse(null);
				if (existingDataDurc == null) {
					esitiList.add(sq);
				} else {
					/* DT_CHIUSURA_RICHIESTA | DESC_TIPO_RICHIESTA | DESC_STATO_RICHIESTA */
					existingDataDurc.setDtChiusuraRichiesta(sq.getDtChiusuraRichiesta() != null ? sq.getDtChiusuraRichiesta() : existingDataDurc.getDtChiusuraRichiesta());
					existingDataDurc.setDescTipoRichiesta(sq.getDescTipoRichiesta() != null ? sq.getDescTipoRichiesta() : existingDataDurc.getDescTipoRichiesta());
					existingDataDurc.setDescStatoRichiesta(sq.getDescStatoRichiesta() != null ? sq.getDescStatoRichiesta() : existingDataDurc.getDescStatoRichiesta());
				}
			}

		return esitiList;
	}
	
	

	/*****************
	* estrazione BDNA
	******************/
	private List<EsitoDurcAntimafia> estrazioneANTIMAFIA_BDNA(Integer idSoggettoAntimafia, Integer idDomanda, String numeroDomanda) {
		
		LOG.info("Method: estrazioneANTIMAFIA_BDNA");
		LOG.info("Parametri ricevuti:");
		LOG.info("idSoggettoAntimafia: " + idSoggettoAntimafia);
		LOG.info("idDomanda: " + idDomanda);
		LOG.info("numeroDomanda: " + numeroDomanda);
		
		List<EsitoDurcAntimafia> esitiList = new ArrayList<EsitoDurcAntimafia>();

		List<EsitoDurcAntimafia> datiAntimafiaList = null;
		if (idSoggettoAntimafia != null && idSoggettoAntimafia > 0) {

			/* recupero: desc_esito_richieste | dt_emissione | dt_scadenza_antimafia | dt_ricezione_bdna **/
			datiAntimafiaList = new ArrayList<EsitoDurcAntimafia>();
			datiAntimafiaList = esitoDurcAntimafiaDao.getDatiAntimafia_BDNA(idSoggettoAntimafia, idDomanda, numeroDomanda);
			LOG.info("BDNA: datiAntimafiaList dim= " + datiAntimafiaList.size());

			if (datiAntimafiaList != null && datiAntimafiaList.isEmpty()) {
				LOG.info("BDNA: [Descrizione esito richieste] [Data emissione antimafia] [Data scadenza antimafia] [Data ricezione BDNA] - Nessun dato trovato per questo numeroDomanda: ["+ numeroDomanda + "]");
			}
			
			if(datiAntimafiaList != null && !datiAntimafiaList.isEmpty()) {

				for (EsitoDurcAntimafia fq : datiAntimafiaList) {
					
					EsitoDurcAntimafia existingDataDurc = esitiList.stream().filter(b -> b.getIdDomanda().equals(fq.getIdDomanda())).findFirst().orElse(null);
					if (existingDataDurc == null) {
						esitiList.add(fq);
					} else {
						/* q3: DESC_ESITO_RICHIESTE | DT_EMISSIONE_DURC | DT_SCADENZA */
						existingDataDurc.setEsito(fq.getEsito() != null ? fq.getEsito() : existingDataDurc.getEsito());
						existingDataDurc.setDtRicezioneDocumentazione(fq.getDtRicezioneDocumentazione() != null ? fq.getDtRicezioneDocumentazione() : existingDataDurc.getDtRicezioneDocumentazione());
						existingDataDurc.setDtScadenza( fq.getDtScadenza() != null ? fq.getDtScadenza() : existingDataDurc.getDtScadenza());
					}
				}
			}
		}

			/* DT_CHIUSURA_RICHIESTA | DESC_TIPO_RICHIESTA | DESC_STATO_RICHIESTA */
			List<EsitoDurcAntimafia> datiRichiestiAntimafiaList = new ArrayList<EsitoDurcAntimafia>();
			datiRichiestiAntimafiaList = esitoDurcAntimafiaDao.getDatiRichiestiAntimafia(idDomanda, numeroDomanda);
			LOG.info("BDNA: datiRichiestiAntimafiaList dim= " + datiRichiestiAntimafiaList.size());

			if (datiRichiestiAntimafiaList == null || datiRichiestiAntimafiaList.isEmpty()) {
				LOG.info("BDNA: [Data chiusura richiesta] [Descrizione Tipo Richiesta] [Descrizione stato richiesta] - Nessun dato trovato per questo numeroDomanda: ["+ numeroDomanda + "]");
			}

			for (EsitoDurcAntimafia sq : datiRichiestiAntimafiaList) {

				EsitoDurcAntimafia existingDataDurc = esitiList.stream()
						.filter(b -> b.getIdDomanda().equals(sq.getIdDomanda())).findFirst()
						.orElse(null);
				if (existingDataDurc == null) {
					esitiList.add(sq);
				} else {
					/* DT_CHIUSURA_RICHIESTA | DESC_TIPO_RICHIESTA | DESC_STATO_RICHIESTA */
					existingDataDurc.setDtChiusuraRichiesta(sq.getDtChiusuraRichiesta() != null ? sq.getDtChiusuraRichiesta() : existingDataDurc.getDtChiusuraRichiesta());
					existingDataDurc.setDescTipoRichiesta(sq.getDescTipoRichiesta() != null ? sq.getDescTipoRichiesta() : existingDataDurc.getDescTipoRichiesta());
					existingDataDurc.setDescStatoRichiesta(sq.getDescStatoRichiesta() != null ? sq.getDescStatoRichiesta() : existingDataDurc.getDescStatoRichiesta());
				}
			}

		return esitiList;
	}

}
