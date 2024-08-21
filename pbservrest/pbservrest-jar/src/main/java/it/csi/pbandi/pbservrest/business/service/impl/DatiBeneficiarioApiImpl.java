/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservrest.business.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import it.csi.pbandi.pbservrest.business.service.DatiBeneficiarioApi;
import it.csi.pbandi.pbservrest.dto.DatiBeneficiario;
import it.csi.pbandi.pbservrest.dto.DatiBeneficiarioListResponse;
import it.csi.pbandi.pbservrest.dto.Esito;
import it.csi.pbandi.pbservrest.dto.RequestDatiBeneficiarioVO;
import it.csi.pbandi.pbservrest.exception.ErroreGestitoException;
import it.csi.pbandi.pbservrest.exception.RecordNotFoundException;
import it.csi.pbandi.pbservrest.integration.dao.DatiBeneficiarioDAO;
import it.csi.pbandi.pbservrest.model.SoggettoEProgressivoTmp;
import it.csi.pbandi.pbservrest.util.Constants;
import it.csi.pbandi.pbservrest.util.DateConverter;
import it.csi.pbandi.pbservrest.util.ValidaDati;

@Transactional(timeout = 240, propagation = Propagation.REQUIRES_NEW, rollbackFor = { Exception.class })
@Component
public class DatiBeneficiarioApiImpl extends BaseApiServiceImpl implements DatiBeneficiarioApi {

	private static final String KO = "KO";

	private Logger LOG = Logger.getLogger(Constants.COMPONENT_NAME);
	
	@Autowired
	DatiBeneficiarioDAO attivitaDao;

	private int idDimImpresa;
	private int idDimImpresaDB;
	
	private SimpleDateFormat oracleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
	
	@Override
	public Response getDatiBeneficiario(String codiceFiscale, String numeroDomanda, SecurityContext securityContext,
			HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
		
		String prf = "[DatiBeneficiarioApiImpl::getDatiBeneficiario]";
		LOG.info(prf + " BEGIN");

		LOG.info(prf + " PK codiceFiscale=" + codiceFiscale);
		LOG.info(prf + " PK numeroDomanda=" + numeroDomanda);
		
		List<DatiBeneficiario> datiBeneficiario = null;
		DatiBeneficiarioListResponse datiResp = new DatiBeneficiarioListResponse();
		List<SoggettoEProgressivoTmp> soggettoEProg = new ArrayList<>();
		
		List<Esito> esitoList = new ArrayList<Esito>();
		
		String idDataBen = "";
		
		try {

			/* eseguo test di connessione al database */
			if(!attivitaDao.testConnection()) {
				LOG.error(prf + " [Errore sistemistico bloccante: 003] ");
				Esito esito = new Esito();
				esito.setDescErrore(Constants.MESSAGGI.ERRORE.ERRORE_CONESSIONE_DATABASE);
				esito.setCodiceErrore(003);
				esito.setEsitoServizio(Constants.ESITO.KO);
				esitoList.add(esito);
				return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(esitoList).build();
			}
			
			LOG.info(prf + " PK numeroDomanda=" + numeroDomanda);

			if ((codiceFiscale.length() == 11 || codiceFiscale.length() == 16) && (numeroDomanda != null && !numeroDomanda.isEmpty())) {
				
				// q1: verifico se cf risulta presente ed associato al numero domanda
				if (Boolean.TRUE.equals(attivitaDao.isCorrect(codiceFiscale, numeroDomanda))) {
					
					// q2: recupero soggetto e progressivo by (numeroDomanda)
					soggettoEProg = attivitaDao.getSoggettoEProg(numeroDomanda);

					LOG.info(prf + " idTipoSoggetto vale= " + soggettoEProg.get(0).getIdTipoSoggetto());
					if (soggettoEProg.get(0).getIdTipoSoggetto() == 2) {
						
						idDataBen = codiceFiscale + "-" + numeroDomanda;
						LOG.info(prf + " idDataBeneficiario vale= " + idDataBen);
						
						// q3: recupero (cod_dimensione_impresa | dt_valutazione_esito | pec)
						List<DatiBeneficiario> firstQueryList = new ArrayList<DatiBeneficiario>();
						firstQueryList = attivitaDao.getDimensioneEValutazione(codiceFiscale, numeroDomanda);
						LOG.info(" firstQueryList dim= " + firstQueryList.size());
						
						if(firstQueryList != null && firstQueryList.isEmpty()) {
							LOG.info("[codice dimensione impresa] e [data valutazione esito] - Nessun dato trovato per questo cf: ["+codiceFiscale+"] e num.domanda: ["+numeroDomanda+"]");
						}
						
						int idProgressivo = soggettoEProg.get(0).getProgrSoggettoDomanda();
						LOG.info(prf + " idProgressivo vale= " + idProgressivo);
						
						// q24: recupero idIndirizzo
						int idIndirizzo = attivitaDao.getIdIndirizzo(idProgressivo);
						LOG.info(prf + " idIndirizzo vale= " + idIndirizzo);
						
						List<DatiBeneficiario> secondQueryList = new ArrayList<DatiBeneficiario>();
						if(idIndirizzo > 0) {
							secondQueryList = attivitaDao.getIndirizzoCompleto(idIndirizzo, idDataBen);
							LOG.info(" secondQueryList dim= " + secondQueryList.size());
						}
						
						if(secondQueryList != null && secondQueryList.isEmpty()) {
							LOG.info("[Indirizzo completo] - Nessun dato trovato per questo idIndirizzo: ["+idIndirizzo+"]");
						}
						
						// cod_dimensione_impresa, dt_valutazione_esito
						datiBeneficiario = new ArrayList<>();

						
						for (DatiBeneficiario fq : firstQueryList) {
								/**
								 * funzione lambda:
								 * - ciclo su elenco: firstQueryList
								 * - cerco in datiBeneficiario IdDataBeneficiario e se risulta uguale ad IdDataBeneficiario passato dal servizio
								 * - allora l'oggetto risulta inizializzato ed aggiorno solo i valori recuperati dalla query corrente
								 * - altrimenti se null, inizializzo l'oggetto datiBeneficiario
								 */
								DatiBeneficiario existingDataBen = datiBeneficiario.stream()
						                              .filter(b -> b.getIdDataBeneficiario().equals(fq.getIdDataBeneficiario()))
						                              .findFirst()
						                              .orElse(null);
							    if (existingDataBen == null) {
							    	datiBeneficiario.add(fq);
							    } else {
							    	existingDataBen.setCodiceDimensioneImpresa(fq.getCodiceDimensioneImpresa() != null ? fq.getCodiceDimensioneImpresa() : existingDataBen.getCodiceDimensioneImpresa());
							    	existingDataBen.setDtValutazioneEsito(fq.getDtValutazioneEsito() != null ? fq.getDtValutazioneEsito() : existingDataBen.getDtValutazioneEsito());
							    }
						}

						for (DatiBeneficiario sq : secondQueryList) {
							
								/** funzione lambda */
								DatiBeneficiario existingDataBen = datiBeneficiario.stream()
									  .filter(b -> b.getIdDataBeneficiario().equals(sq.getIdDataBeneficiario()))
		                              .findFirst()
		                              .orElse(null);
							    if (existingDataBen == null) {
							    	datiBeneficiario.add(sq);
							    } else {
							    	existingDataBen.setDescIndirizzo(sq.getDescIndirizzo() != null ? sq.getDescIndirizzo() : existingDataBen.getDescIndirizzo());
							    	existingDataBen.setCap(sq.getCap() != null ? sq.getCap() : existingDataBen.getCap());
							    	existingDataBen.setDescComune(sq.getDescComune() != null ? sq.getDescComune() : existingDataBen.getDescComune());
							    	existingDataBen.setDescProvincia(sq.getDescProvincia() != null ? sq.getDescProvincia() : existingDataBen.getDescProvincia());
							    	existingDataBen.setDescNazione(sq.getDescNazione() != null ? sq.getDescNazione() : existingDataBen.getDescNazione());
							    	existingDataBen.setDescComuneEstero(sq.getDescComuneEstero() != null ? sq.getDescComuneEstero() : existingDataBen.getDescComuneEstero());
							    }
						}
						
						// q5: recupero ( desc_provider e codice_rating )
						List<DatiBeneficiario> thirdQueryList = new ArrayList<DatiBeneficiario>();
						thirdQueryList = attivitaDao.getProviderERating(idDataBen, numeroDomanda);
						LOG.info(" thirdQueryList dim= " + thirdQueryList.size());
						
						if(thirdQueryList != null && thirdQueryList.isEmpty()) {
							LOG.info("[Descrizione provider] e [Codice rating] - Nessun dato trovato per questo numeroDomanda: ["+numeroDomanda+"]");
						}
						
						for (DatiBeneficiario tq : thirdQueryList) {
							
							/** funzione lambda */
							DatiBeneficiario existingDataBen = datiBeneficiario.stream()
								  .filter(b -> b.getIdDataBeneficiario().equals(tq.getIdDataBeneficiario()))
	                              .findFirst()
	                              .orElse(null);
						    if (existingDataBen == null) {
						    	datiBeneficiario.add(tq);
						    } else {
						    	existingDataBen.setDescProvider(tq.getDescProvider() != null ? tq.getDescProvider() : existingDataBen.getDescProvider());
						    	existingDataBen.setCodiceRating(tq.getCodiceRating() != null ? tq.getCodiceRating() : existingDataBen.getCodiceRating());
						    	existingDataBen.setDtClassificazione(tq.getDtClassificazione() != null ? tq.getDtClassificazione() : existingDataBen.getDtClassificazione());
						    }
						}
						
						// q6: desc_breve_classe_rischio
						List<DatiBeneficiario> fourthQueryList = new ArrayList<DatiBeneficiario>();
						fourthQueryList = attivitaDao.getDescrClasseRischio(idDataBen, numeroDomanda);
						LOG.info(" fourthQueryList dim= " + fourthQueryList.size());
						
						if(fourthQueryList != null && fourthQueryList.isEmpty()) {
							LOG.info("[Descrizione breve classe rischio] - Nessun dato trovato per questo numeroDomanda: ["+numeroDomanda+"]");
						}
						
						for (DatiBeneficiario fq : fourthQueryList) {
							
							/** funzione lambda */
							DatiBeneficiario existingDataBen = datiBeneficiario.stream()
								  .filter(b -> b.getIdDataBeneficiario().equals(fq.getIdDataBeneficiario()))
	                              .findFirst()
	                              .orElse(null);
						    if (existingDataBen == null) {
						    	datiBeneficiario.add(fq);
						    } else {
						    	existingDataBen.setDesBreveClasseRischio(fq.getDesBreveClasseRischio() != null ? fq.getDesBreveClasseRischio() : existingDataBen.getDesBreveClasseRischio());
						    }
						}
						
					} else {
						idDataBen = codiceFiscale + "-" + numeroDomanda;
						LOG.info(prf + " idDataBeneficiario vale= " + idDataBen);
						// q7:
						datiBeneficiario = attivitaDao.getDescrizioniSoggettoFisico(soggettoEProg.get(0).getProgrSoggettoDomanda(), idDataBen);
					}

				} else {
					LOG.error(prf + "Il codice fiscale del beneficiario non corrisponde alla domanda inviata");
					Esito esito = new Esito();
					esito.setDescErrore(Constants.MESSAGGI.ERRORE.CODICE_FISCALE_ERRATO);
					esito.setCodiceErrore(007);
					esito.setEsitoServizio(Constants.ESITO.KO);
					esitoList.add(esito);
					return Response.status(Response.Status.BAD_REQUEST).entity(esitoList).build();
				}

			} else {
				LOG.error(prf + "Parametri di ricerca non corretti");
				Esito esito = new Esito();
				esito.setDescErrore(Constants.MESSAGGI.ERRORE.PARAMETRI_INVALIDI);
				esito.setCodiceErrore(005);
				esito.setEsitoServizio(Constants.ESITO.KO);
				esitoList.add(esito);
				return Response.status(Response.Status.BAD_REQUEST).entity(esitoList).build();
			}

			LOG.info(prf + "sono stati trovati " + datiBeneficiario.size() + " datiBeneficiario");

		} catch (RecordNotFoundException e) {
			throw e;
		} catch (Exception e) {
			LOG.error(prf + "Exception while trying to read DatiBeneficiario", e);
			throw new ErroreGestitoException("DaoException while trying to read DatiBeneficiario", e);
		} finally {
			LOG.info(prf + " END");
		}
		datiResp.setDatiBeneficiarioList(datiBeneficiario);
		return Response.ok(datiResp).build();
		
	}
	// fine getDatiBeneficiario


	/**
	 * Codice fiscale			: 11 o 16 caratteri alfanumerici obbligatorio (*)
	 * Numero domanda			: obbl. numerico (*)
	 * Codice dimensione impresa: facoltativo 
	 * Data valutazione esito	: facoltativo (Date)
	 * Descrizione provider		: facoltativo
	 * Codice rating			: facoltativo
	 * Data di classificazione	: facoltativo
	 */
	@Override
	public Response setDatiBeneficiario(RequestDatiBeneficiarioVO rdbv, SecurityContext securityContext,
			HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
		String prf = "[AttivitaServiceImpl::setDatiBeneficiario]";
		LOG.info(prf + " BEGIN");

		Set<DatiBeneficiario> datiBeneficiario = new HashSet<DatiBeneficiario>();
		List<Esito> esitoList = new ArrayList<Esito>();
		
		int idEnteGiuridico;
		int idSoggetto = 0;
		int idRating;
		int idProvider;
		int vecchioIdRating;
		int idClasseRischio;
		int vecchioIdClasseRischio;
		
		String s_dtValutazioneEsitoInRest = "";
		String s_dtClassificazioneInRest = "";
		
		Date dtValutazioneEsitoInRest = null;
		Date dtClassificazioneInRest = null;
		
		try {
			
			/* eseguo test di connessione al database */
			if(!attivitaDao.testConnection()) {
				LOG.error(prf + " [Errore sistemistico bloccante: 003] ");
				Esito esito = new Esito();
				esito.setDescErrore(Constants.MESSAGGI.ERRORE.ERRORE_CONESSIONE_DATABASE);
				esito.setCodiceErrore(003);
				esito.setEsitoServizio(Constants.ESITO.KO);
				esitoList.add(esito);
				return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(esitoList).build();
			}

			// Controllo Codice Fiscale beneficiario
			if ( (rdbv.getCodiceFiscale().length() == 11 || rdbv.getCodiceFiscale().length() == 16) && (rdbv.getNumeroDomanda() != null && !rdbv.getNumeroDomanda().isEmpty())) {

				// verifica cf e numero domanda
				if (Boolean.TRUE.equals(attivitaDao.isCorrect(rdbv.getCodiceFiscale(), rdbv.getNumeroDomanda()))) {

					// recupero: codiceFiscale
					String codiceFiscale = rdbv.getCodiceFiscale();
					LOG.info(prf + " codiceFiscale= " + codiceFiscale);
					
					// recupero: numeroDomanda
					String numeroDomanda = rdbv.getNumeroDomanda();
					LOG.info(prf + " numeroDomanda= " + numeroDomanda);
					
					// set idDataBen univoco composto da (codiceFiscale + numeroDomanda)
					String idDataBen = codiceFiscale + "-" + numeroDomanda;
					LOG.info(prf + " idDataBeneficiario vale= " + idDataBen);
					
					 /* dtValutazioneEsito */
					s_dtValutazioneEsitoInRest = rdbv.getDtValutazioneEsito();
			        LOG.info(prf + " s_dtValutazioneEsitoInRest vale= " + s_dtValutazioneEsitoInRest);
			        
			        if(s_dtValutazioneEsitoInRest != null && s_dtValutazioneEsitoInRest.length() > 0) {
			        	dtValutazioneEsitoInRest = DateConverter.stringToDate(s_dtValutazioneEsitoInRest);
			        	LOG.info("dtValutazioneEsitoInRest risulta: " + dtValutazioneEsitoInRest);
			        }else {
			        	dtValutazioneEsitoInRest = new Date(0); 
						LOG.info("Validazione dati: [Data dtValutazioneEsitoInRest esito] risulta null, dato non obbligatorio, proseguo elaborazione.");
			        }
			        
			        /* dtClassificazione */
			        s_dtClassificazioneInRest = rdbv.getDtClassificazione();
			        LOG.info(prf + " s_dtClassificazioneInRest vale= " + s_dtClassificazioneInRest);
			        
			        if(s_dtClassificazioneInRest != null && s_dtClassificazioneInRest.length() > 0) {
			        	dtClassificazioneInRest = DateConverter.stringToDate(s_dtClassificazioneInRest);
			        	LOG.info("dtClassificazioneInRest risulta: " + dtClassificazioneInRest);
			        }else {
			        	dtClassificazioneInRest = new Date(0); 
						LOG.info("Validazione dati: [Data dtClassificazioneInRest esito] risulta null, dato non obbligatorio, proseguo elaborazione.");
			        }
				    
					// recupero (cod_dimensione_impresa e dt_valutazione_esito)
					List<DatiBeneficiario> primaQueryList = new ArrayList<DatiBeneficiario>();
					primaQueryList = attivitaDao.getDimensioneEValutazione(codiceFiscale, numeroDomanda);
					LOG.info(" primaQueryList dim= " + primaQueryList.size());
					
					// recupero: (desc_provider e codice_rating)
					List<DatiBeneficiario> secondaQueryList = new ArrayList<DatiBeneficiario>();
					secondaQueryList = attivitaDao.getProviderERating(idDataBen, numeroDomanda);
					LOG.info(" secondaQueryList dim= " + secondaQueryList.size());
					
					for (DatiBeneficiario fq : primaQueryList) {
						
						/* funzione lambda */
						DatiBeneficiario existingDataBen = datiBeneficiario.stream()
				                              .filter(b -> b.getIdDataBeneficiario().equals(fq.getIdDataBeneficiario()))
				                              .findFirst()
				                              .orElse(null);
					    if (existingDataBen == null) {
					    	datiBeneficiario.add(fq);
					    } else {
					    	existingDataBen.setCodiceDimensioneImpresa(fq.getCodiceDimensioneImpresa() != null ? fq.getCodiceDimensioneImpresa() : existingDataBen.getCodiceDimensioneImpresa());
					    	existingDataBen.setDtValutazioneEsito(fq.getDtValutazioneEsito() != null ? fq.getDtValutazioneEsito() : existingDataBen.getDtValutazioneEsito());
					    }
					}
	
					for (DatiBeneficiario sq : secondaQueryList) {
						
						/* funzione lambda */
						DatiBeneficiario existingDataBen = datiBeneficiario.stream()
							  .filter(b -> b.getIdDataBeneficiario().equals(sq.getIdDataBeneficiario()))
                              .findFirst()
                              .orElse(null);
					    if (existingDataBen == null) {
					    	datiBeneficiario.add(sq);
					    } else {
					    	existingDataBen.setDescProvider(sq.getDescProvider() != null ? sq.getDescProvider() : existingDataBen.getDescProvider());
					    	existingDataBen.setCodiceRating(sq.getCodiceRating() != null ? sq.getCodiceRating() : existingDataBen.getCodiceRating());
					    }
					}
				
					// recupero: (desc_breve_classe_rischio)
					List<DatiBeneficiario> terzaQueryList = new ArrayList<DatiBeneficiario>();
					terzaQueryList = attivitaDao.getDescrClasseRischio(idDataBen, numeroDomanda);
					LOG.info(" terzaQueryList dim= " + terzaQueryList.size());
					
					for (DatiBeneficiario tq : terzaQueryList) {
						
						/* funzione lambda */
						DatiBeneficiario existingDataBen = datiBeneficiario.stream()
							  .filter(b -> b.getIdDataBeneficiario().equals(tq.getIdDataBeneficiario()))
                              .findFirst()
                              .orElse(null);
					    if (existingDataBen == null) {
					    	datiBeneficiario.add(tq);
					    } else {
					    	existingDataBen.setDesBreveClasseRischio(tq.getDesBreveClasseRischio() != null ? tq.getDesBreveClasseRischio() : existingDataBen.getDesBreveClasseRischio());
					    }
					}
					
					// recupero: (id_soggetto) 
					idSoggetto = attivitaDao.getIdSoggetto(rdbv.getCodiceFiscale());
					LOG.info(" idSoggetto= " + idSoggetto);
					
					// recupero: (id_rating)
					vecchioIdRating = attivitaDao.getVecchioIdRating(idSoggetto);
					LOG.info(" vecchioIdRating= " + vecchioIdRating);
					
					String codDimImp = rdbv.getCodiceDimensioneImpresa() == null ? "" : rdbv.getCodiceDimensioneImpresa();
					LOG.info(" codDimImp= " + codDimImp);
					
					if(codDimImp != null && codDimImp.length()>0) {
						idDimImpresa = attivitaDao.getIdDimImpresa(rdbv.getCodiceDimensioneImpresa());
						LOG.info(" idDimImpresa= " + idDimImpresa);

					}else {
						LOG.info("Valido [Codice dimensione impresa]: risulta NULL. (Dato non obbligatorio), proseguo elaborazione.");
					}
					
					
					/***********************
					 * check find dati Input
					 ***********************/
					boolean trovatoDimImpresa = false;
					boolean trovatoCodRating = false;
					boolean trovatoDescClasseRischio = false;
					
					/* validazione dati in input */
					for (DatiBeneficiario item : datiBeneficiario) {
						
						/* CodiceDimensioneImpresa facoltativo max.: 2 */
						LOG.info(" CodiceDimensioneImpresa= " + rdbv.getCodiceDimensioneImpresa());
						if (rdbv.getCodiceDimensioneImpresa() == null) {
							LOG.info(" Codice dimensione impresa risulta null, dato non obbligatorio, proseguo elaborazione.");
						}
						
						if( (item.getCodiceDimensioneImpresa() != null && !item.getCodiceDimensioneImpresa().isEmpty()) && item.getDtValutazioneEsito() != null) {
							
							
							// recupero idDimensione impresa memorizzato su database
							String codDimImpDB = item.getCodiceDimensioneImpresa() == null ? "" : item.getCodiceDimensioneImpresa();
							LOG.info(" codice dimensione impresa presente a DB risulta = [" + codDimImpDB + "]");
							
							if(codDimImpDB != null && codDimImpDB.length()>0) {
								idDimImpresaDB = attivitaDao.getIdDimImpresa(codDimImpDB);
								LOG.info(" idDimImpresaDB= " + idDimImpresaDB);
							}else {
								LOG.info("Valido [Codice dimensione impresa a database risulta assente o NULL, proseguo elaborazione.");
							}
							
							// verifico codice dimensione impresa
							String codDimImpresa = rdbv.getCodiceDimensioneImpresa();
							String regexFormat = "^[a-zA-Z0-9\\s]{0,%d}$"; 
							int maxLength = 2;
							
							boolean isValidCodDimImp = ValidaDati.isValidAlphanumericString(codDimImpresa, regexFormat, maxLength);
									
							// verifico formato data: ('2022-01-01', 'YYYY-MM-DD')
							Date dtValutEsito = item.getDtValutazioneEsito();
							LOG.info(" Data valutazione esito: " + dtValutEsito);
							
							if(ValidaDati.isValidDate(dtValutEsito) && isValidCodDimImp) {
								LOG.info(" La data " + dtValutEsito + " risulta nel formato corretto.");
								if ( rdbv.getCodiceDimensioneImpresa().equals(item.getCodiceDimensioneImpresa()) && dtValutazioneEsitoInRest.compareTo(dtValutEsito) == 0) {
									trovatoDimImpresa = true;
								}
							}else {
								LOG.info(" La data valutazione esito: [" + dtValutazioneEsitoInRest + "] e/o il codice dimensione impresa: [" + codDimImpresa + "] non risultano corrette.");
							}
						}
						
						
						/* Descrizione provider facoltativo max.: 20 */
						LOG.info(" Descrizione provider= " + rdbv.getDescrizioneProvider());
						if (rdbv.getDescrizioneProvider() == null || rdbv.getDescrizioneProvider().length() == 0) {
							LOG.info("Codice descrizione provider risulta null o vuoto. Non risulta obbligatorio, proseguo elaborazione.");
					        
						}else {
							String descrProvider = rdbv.getDescrizioneProvider();
							String regexFormat = "^[a-zA-Z0-9\\s]{0,%d}$"; 
							int maxLength = 20;
							
							if(!ValidaDati.isValidAlphanumericString(descrProvider, regexFormat, maxLength)) {
								LOG.info("Errore validazione dati: [Codice descrizione provider] NON risulta formalmente corretto");
								
							}
						}
						
						/* CodiceRating facoltativo max.: 20 */
						LOG.info(" CodiceRating= " + rdbv.getCodiceRating());
						if (rdbv.getCodiceRating() == null || rdbv.getCodiceRating().isEmpty()) {
							trovatoCodRating = true;
							LOG.info("[Codice rating] risulta null. Parametro non obbligatorio, proseguo elaborazione.");
						}
						
						LOG.info(" CodiceRating= " + rdbv.getCodiceRating() + " vs " + item.getCodiceRating());
						if(rdbv.getCodiceRating() != null && !rdbv.getCodiceRating().isEmpty()) {
							
							String codRating = rdbv.getCodiceRating();
							String regexFormat = "^[a-zA-Z0-9\\s]{0,%d}$"; 
							int maxLength = 20;
							
							if(ValidaDati.isValidAlphanumericString(codRating, regexFormat, maxLength)) {
								if (rdbv.getCodiceRating().equals(item.getCodiceRating())) {
									trovatoCodRating = true;
								}
							}else {
								LOG.error(" CodiceRating= " + rdbv.getCodiceRating() + " risulta avere una dimensione superiore di 20 caratteri.");
								Esito esito = new Esito();
								esito.setDescErrore("[Errore 005] [Codice rating] - Risulta avere una dimensione superiore ai 20 caratteri per questo numero domanda: ["+numeroDomanda+"]");
						        esito.setCodiceErrore(5);
						        esito.setEsitoServizio(KO);
						        esitoList.add(esito);
						        return Response.status(Response.Status.BAD_REQUEST).entity(esitoList).build();
							}
						}
						
						/* DesBreveClasseRischio facoltativo max.: 20 */
						LOG.info(" DesBreveClasseRischio= " + rdbv.getDesBreveClasseRischio());
						if (rdbv.getDesBreveClasseRischio() == null || rdbv.getDesBreveClasseRischio().isEmpty()) {
							trovatoDescClasseRischio = true;
							LOG.info("Errore validazione dati: [Descrizione breve classe rischio] risulta null");
						}
						
						LOG.info(" DesBreveClasseRischio= " + rdbv.getDesBreveClasseRischio() + " vs " + item.getDesBreveClasseRischio());
						if(rdbv.getDesBreveClasseRischio() != null && !rdbv.getDesBreveClasseRischio().isEmpty()) {
							
							String descrBreveClasseRischio = rdbv.getDesBreveClasseRischio();
							
							// valido stringhe di lunghezza massima variabile
							String regexFormat = "^[a-zA-Z0-9\\s]{0,%d}$"; 
							int maxLength = 20;
							
							if(ValidaDati.isValidAlphanumericString(descrBreveClasseRischio, regexFormat, maxLength)) {
								if (rdbv.getDesBreveClasseRischio().equals(item.getDesBreveClasseRischio())) {
									trovatoDescClasseRischio = true;
								}
								
							}else {
								LOG.error(" DesBreveClasseRischio= " + rdbv.getDesBreveClasseRischio() + " risulta avere una dimensione superiore di 20 caratteri.");
								Esito esito = new Esito();
								esito.setDescErrore("[Errore 005] [Descrizione breve classe rischio] - Risulta avere una dimensione superiore di 20 caratteri per questo numero domanda: ["+numeroDomanda+"]");
						        esito.setCodiceErrore(5);
						        esito.setEsitoServizio(KO);
						        esitoList.add(esito);
						        return Response.status(Response.Status.BAD_REQUEST).entity(esitoList).build();
							}
						}
						

						if (dtClassificazioneInRest == null) {
							// imposto una data vuota
							dtClassificazioneInRest = new Date(0); 
							LOG.info("Validazione dati: [Data di classificazione] risulta null. Dato non obbligatorio, proseguo elaborazione.");
						}
						else {
							// verifico formato data: ('2022-01-01', 'YYYY-MM-DD')
							LOG.info(" La data di classificazione " + dtClassificazioneInRest);
							
							if(ValidaDati.isValidDate(dtClassificazioneInRest)) {
								LOG.info(" La data di classificazione " + dtClassificazioneInRest + " risulta nel formato corretto.");
							}else {
								LOG.error(" La data di classificazione " + dtClassificazioneInRest + " NON risulta nel formato corretto.");
								Esito esito = new Esito();
								esito.setDescErrore("[Errore 005] [Data di classificazione] - Risulta presente ma formalmente NON corretta per questo numero domanda: ["+numeroDomanda+"]");
						        esito.setCodiceErrore(5);
						        esito.setEsitoServizio(KO);
						        esitoList.add(esito);
						        return Response.status(Response.Status.BAD_REQUEST).entity(esitoList).build();
							}
						}
						
					}
					// check find dati FINE

					LOG.info(" trovatoDimImpresa= " + trovatoDimImpresa);
					if (trovatoDimImpresa) {
						LOG.info(prf + "Dato già esistente: [DimImpresa]");

					} else {
						
						LOG.info(" idDimImpresa [input-rest]= " + idDimImpresa);
						LOG.info(" DtValutazioneEsito [input-rest]= " + dtValutazioneEsitoInRest);
						
						/** 
						 * devo recuperare (idDimImpresa: presente a database in base al codice-dimensione-impresa)
						 * ricevuto come parametro dalla chiamata rest, in input.
						 * Poi confrontare (idDimImpresaDb con idDimImpresaInputRest), se diversi
						 * deve eseguire insert... 
						 */
						if (!attivitaDao.trovatoRecord(idDimImpresa, dtValutazioneEsitoInRest) && (idDimImpresa != idDimImpresaDB)) {
							
							LOG.info(" idSoggetto= " + idSoggetto);
							LOG.info(" idDimImpresa= " + idDimImpresa);
							LOG.info(" dtValutazioneEsitoInRest= " + dtValutazioneEsitoInRest);
							
							if(idDimImpresa >0) {
								attivitaDao.insertDimensioneEValutazione(idSoggetto, idDimImpresa, dtValutazioneEsitoInRest);
								idEnteGiuridico = attivitaDao.getIdEnteGiuridico(idSoggetto);
								LOG.info(" idEnteGiuridico= " + idEnteGiuridico);
								
								attivitaDao.updateEnteGiuridico(idEnteGiuridico, idSoggetto);
								esitoList = setEsitoOK();
								LOG.info(" esito= " + esitoList);
							}
							
						} else {
							LOG.info(prf + "Dati dim impresa e dt valutazione esito già esistenti");
						}
					}

					LOG.info(" trovatoCodRating= " + trovatoCodRating);
					if (trovatoCodRating) {
						LOG.info(prf + "Dato già esistente: [CodRating]");

					} else {
						idProvider = attivitaDao.getIdProvider(rdbv.getDescrizioneProvider());
						LOG.info(prf + "idProvider: " + idProvider);
						
						idRating = attivitaDao.getIdRating(rdbv.getCodiceRating(), idProvider);
						LOG.info(prf + "idRating: " + idRating);
						
						// errore 010
						if(idRating == 0) {
							Esito esito = new Esito();
							esito.setDescErrore("[codice rating] risulta NON trovato a database per questo [codice rating: "+rdbv.getCodiceRating()+"]");
					        esito.setCodiceErrore(10);
					        esito.setEsitoServizio(KO);
					        esitoList.add(esito);
					        return Response.status(Response.Status.NOT_FOUND).entity(esitoList).build();
						}
						
						vecchioIdRating = attivitaDao.getVecchioIdRating(idSoggetto);
						LOG.info(prf + "vecchioIdRating: " + vecchioIdRating);
						
						LOG.info(" idSoggetto= " + idSoggetto);
						LOG.info(" idRating= " + idRating);
						
						if (attivitaDao.idSoggettoExist(idSoggetto) && ( vecchioIdRating > 0 && idRating != vecchioIdRating)) {
							attivitaDao.updateDtFineValidita(idSoggetto);
							attivitaDao.insertRatingESogg(idRating, idSoggetto, dtClassificazioneInRest);
							esitoList = setEsitoOK();
							
						} else if (!attivitaDao.idSoggettoExist(idSoggetto)) {
							attivitaDao.insertRatingESogg(idRating, idSoggetto, dtClassificazioneInRest);
							esitoList = setEsitoOK();
						}
					}

					LOG.info(" trovatoDescClasseRischio= " + trovatoDescClasseRischio);
					if (trovatoDescClasseRischio) {
						LOG.info(prf + "Dato già esistente: [DescClasseRischio]");

					} else {
						LOG.info(" DesBreveClasseRischio()= " + rdbv.getDesBreveClasseRischio());
						idClasseRischio = attivitaDao.getIdClasseRischio(rdbv.getDesBreveClasseRischio());
						LOG.info(prf + "idClasseRischio: " + idClasseRischio);
						
						// errore 011
						if(idClasseRischio == 0) {
							LOG.info("[idClasseRischio] risulta NON trovato a database, proseguo...");
							Esito esito = new Esito();
							esito.setDescErrore("[descrizione classe rischio] risulta NON trovata a database per questo [descrizione classe rischio: "+rdbv.getDesBreveClasseRischio()+"]");
					        esito.setCodiceErrore(11);
					        esito.setEsitoServizio(KO);
					        esitoList.add(esito);
					        return Response.status(Response.Status.NOT_FOUND).entity(esitoList).build();
						}
						
						LOG.info(" idSoggetto= " + idSoggetto);
						vecchioIdClasseRischio = attivitaDao.getVecchioIdClasseRischio(idSoggetto);
						LOG.info(prf + "vecchioIdClasseRischio: " + vecchioIdClasseRischio);
						
						LOG.info(" idSoggetto= " + idSoggetto);
						LOG.info(" idClasseRischio= " + idClasseRischio + " vs " + " vecchioIdClasseRischio= " + vecchioIdClasseRischio);
						if (idClasseRischio != vecchioIdClasseRischio) {
							attivitaDao.updateDtFineValiditaClasse(idSoggetto);
							attivitaDao.insertClasseESogg(idSoggetto, idClasseRischio);
							esitoList = setEsitoOK();
							LOG.info(" esito= " + esitoList);
						
						} else if (idClasseRischio >0) {
							attivitaDao.insertClasseESogg(idSoggetto, idClasseRischio);
							esitoList = setEsitoOK();
							LOG.info(" esito= " + esitoList);
						}else {
							LOG.info(" Casistica non considerata!");
						}
					}

				} else {
					esitoList = setErrore007();
					LOG.error(" err007= " + esitoList);
				}
			} else {
				// errore 005
				esitoList = setErrore005();
				LOG.error(" err005= " + esitoList);
			}

			if(datiBeneficiario.size() > 0) {
				esitoList = setEsitoOK();
			}
			
			LOG.info(prf + "sono stati trovati: " + datiBeneficiario.size() + " datiBeneficiario");

		} catch (RecordNotFoundException e) {
			throw e;
			
		} catch (Exception e) {
			
			LOG.error(prf + "Exception while trying to read DatiBeneficiario", e);
			esitoList = setErrore006();
			LOG.error(" err006= " + esitoList);
			
		} finally {
			LOG.info(prf + " END");
		}
		
		LOG.info(" esito finale= " + esitoList);
		
		return Response.ok(esitoList).build();
	}
	// fine setDatiBeneficiario



	public int getIdDimImpresa() {
		return idDimImpresa;
	}



	public void setIdDimImpresa(int idDimImpresa) {
		this.idDimImpresa = idDimImpresa;
	}


	public SimpleDateFormat getOracleDateFormat() {
		return oracleDateFormat;
	}


	public void setOracleDateFormat(SimpleDateFormat oracleDateFormat) {
		this.oracleDateFormat = oracleDateFormat;
	}
	
}
