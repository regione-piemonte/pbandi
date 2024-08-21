/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.pbandisrv.business.manager.tracciamento;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

import it.csi.pbandi.pbservizit.pbandisrv.business.BusinessImpl;
import it.csi.pbandi.pbservizit.pbandisrv.dto.tracciamento.TracciamentoDTO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.dao.GenericDAO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.dao.PbandiDAO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.AttributoVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.util.Condition;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiCOperazioneVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiTTracciamentoEntitaVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiTTracciamentoVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.util.BeanMapper;
import it.csi.pbandi.pbservizit.pbandisrv.util.validation.ValidatorInput;
import it.csi.pbandi.pbservizit.pbandiutil.common.DateUtil;
import it.csi.pbandi.pbservizit.pbandiutil.common.NumberUtil;
import it.csi.pbandi.pbservizit.pbandiutil.common.ObjectUtil;
import it.csi.pbandi.pbservizit.pbandiutil.common.StringUtil;

/**
 * 
 * L'algoritmo riceve in input diversi parametri che servono a comporre il
 * tracciamento che verr� salvato sulla base dati:
 * 
 * � Source (obbligatorio) ID_UTENTE : Identificativo della persona che ha
 * eseguito l'operazione;
 * 
 * � Operazione (obbligatorio)ID_OPERAZIONE : Identificativo dell'operazione
 * sull'entit� COperazione;
 * 
 * � Esito (obbligatorio): indica se l'operazione si � conclusa con successo o
 * meno (S o N);
 * 
 * � Errore (opzionale): eccezione applicativa in caso di esito N;
 * 
 * � Entit� (opzionale) NOME-TABELLA: Contiene l'identificativo della
 * classificazione dell'entit� su cui si � eseguita l'operazione. Tutte le
 * entit� sono censite a loro volta sull'entit� CEntit�;
 * 
 * � Attributo (opzionale) NOME-CAMPO : Contiene l'identificativo della
 * classificazione dell�attributo su cui si � eseguita l�operazione. Tutti gli
 * attributi sono censiti a loro volta sull�entit� CAttributi e collegati
 * all'entit� CEntit�;
 * 
 * 
 * � Valore precedente (opzionale)( eseguire SELECT prima di insert/update per
 * estraarre il valore precedente): Contiene il valore precedente all�operazione
 * effettuata dell'attributo inserito;
 * 
 * � Valore successivo (opzionale): Contiene il valore successivo all'operazione
 * effettuata dell'attributo inserito;
 * 
 * � Target (opzionale): Identificativo dell'istanza entit� destinataria
 * dell'evento;
 * 
 * � Data tracciamento (obbligatorio): � valorizzata dal sistema in automatico.
 * 
 * 
 * 
 */
public class TracciamentoBusinessImpl extends BusinessImpl implements
		TracciamentoSrv, ApplicationContextAware {

	private TracciamentoDTO tracciamentoDTO;
	@Autowired
	private GenericDAO genericDAO;
	private ApplicationContext applicationContext;
	private Map <String,java.util.List<AttributoVO>>  cacheEntita = new HashMap<String,java.util.List<AttributoVO>>();
	private long start = 0;
	private long timeOut = 60 * 1000;

	private Map <String,java.util.List<PbandiCOperazioneVO>>cacheOperazioni = new HashMap<String,java.util.List<PbandiCOperazioneVO>>();
	private long startCacheOperazioni = 0;
	private long timeOutCacheOperazioni = 60 * 1000 * 20;// cache 15 min

	
	public void setTracciamentoDTO(TracciamentoDTO tracciamentoDTO) {
		this.tracciamentoDTO = tracciamentoDTO;
	}

	public TracciamentoDTO getTracciamentoDTO() {
		return tracciamentoDTO;
	}

	public void setGenericDAO(GenericDAO genericDAO) {
		this.genericDAO = genericDAO;
	}

	public GenericDAO getGenericDAO() {
		return genericDAO;
	}

	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		this.applicationContext = applicationContext;
	}
	
	public void init() {

		String beans[] = applicationContext.getBeanDefinitionNames();

		Set<String> listDescFisiche = new HashSet<String>();

		PbandiCOperazioneVO orderFilter = new PbandiCOperazioneVO();
		orderFilter.setDescendentOrder("idOperazione");
		List<PbandiCOperazioneVO> listOperazioni = genericDAO
				.findListWhere(orderFilter);

		for (int i = 0; i < beans.length; i++) {
			if (beans[i].endsWith("Business")) {

				Object objectBusinessClass = applicationContext
						.getBean(beans[i]);
				Class<?> businessClazz = objectBusinessClass.getClass();
				String nomeClasseBusiness = businessClazz.getSimpleName();

				Package pkg = businessClazz.getPackage();
				String pkgName = pkg != null ? pkg.getName() : "";

				String nomeClasseImpl = nomeClasseBusiness.replaceAll(
						"Business", "");
				nomeClasseImpl = nomeClasseImpl.replaceAll("Impl", "Srv");
				String intfPkgName = pkgName
						.replace("business", "interfacecsi");

				String fullClassName = intfPkgName + "." + nomeClasseImpl;

				try {
					Class<?> implClass = Class.forName(fullClassName);
					Method[] metodiClasseDaInvocare = implClass
							.getDeclaredMethods();

					for (int j = 0; j < metodiClasseDaInvocare.length; j++) {
						String descFisica = objectBusinessClass.getClass()
								.getSimpleName()
								+ "."
								+ metodiClasseDaInvocare[j].getName();
						descFisica = descFisica.replace("BusinessImpl", "Impl");

						addIfNotAlreadyRegistered(listDescFisiche,
								listOperazioni, descFisica);
					}
				} catch (ClassNotFoundException e) {
					logger.warn("Impossibile risalire all'interfaccia "
							+ fullClassName + ", ignoro le sue operazioni: "
							+ e.getMessage());
				}
			}
		}

		BigDecimal maxIdOperazione = listOperazioni.size() == 0 ? new BigDecimal(
				0) : listOperazioni.get(0).getIdOperazione();
		for (String descOperazione : listDescFisiche) {
			maxIdOperazione = maxIdOperazione.add(new BigDecimal(1));
			try {
				addOperazione(maxIdOperazione, descOperazione);
			} catch (Exception e) {
				logger.warn("Impossibile aggiungere l'operazione "
						+ descOperazione + ": " + e.getMessage());
			}

		}

	}

	private void addOperazione(BigDecimal maxIdOperazione, String descOperazione)
			throws Exception {
		PbandiCOperazioneVO operazioneVO = new PbandiCOperazioneVO();
		operazioneVO.setDescFisica(descOperazione);
		operazioneVO.setDescLogica(descOperazione);
		operazioneVO.setDtInizioValidita(new java.sql.Date(System
				.currentTimeMillis()));
		operazioneVO
				.setFlagDaTracciare(it.csi.pbandi.pbservizit.pbandiutil.common.Constants.FLAG_TRUE);
		operazioneVO.setIdOperazione(maxIdOperazione);

		genericDAO.insertWithoutTrace(operazioneVO);
	}

	private void addIfNotAlreadyRegistered(Set<String> listDescFisiche,
			List<PbandiCOperazioneVO> listOperazioni, String descFisica) {
		boolean operazionePresente = false;
		for (PbandiCOperazioneVO operazione : listOperazioni) {
			if (operazione.getDescFisica().equalsIgnoreCase(descFisica)) {
				operazionePresente = true;
				break;
			}
		}
		if (!operazionePresente) {
			listDescFisiche.add(descFisica);
		}
	}

	public Long tracciaOperazione(String nomeOperazione, Long idUtente) {
		Long result = null;
		try {

			String nameParameter[] = { "nomeOperazione", "idUtente" };
			ValidatorInput.verifyNullValue(nameParameter, nomeOperazione,
					idUtente);

			PbandiTTracciamentoVO tracciamento = new PbandiTTracciamentoVO();
			BigDecimal idOperazione = getIdOperazione(nomeOperazione);

			if (idOperazione != null) {
				tracciamento.setIdOperazione(idOperazione);
				tracciamento.setFlagEsito(ESITO_TRACCIAMENTO_OK);
				tracciamento.setDtTracciamento(DateUtil
						.utilToSqlTimeStamp(System.currentTimeMillis()));
				tracciamento.setIdUtente(beanUtil.transform(idUtente,
						BigDecimal.class));
				tracciamento = genericDAO.insertWithoutTrace(tracciamento);

				result = tracciamento.getIdTracciamento().longValue();
			} else {
				String errorMessage = " Operazione <" + nomeOperazione
						+ "> NON configurata sul database";
				logger.warn(errorMessage);
			}
		} catch (Exception x) {
			logger.warn("Errore nell'operazione di tracciamento!!! : "
					+ x.getMessage());
		}  
		return result;
	}

	private void tracciaEsito(Long idTracciamento, String esito,
			String messaggio, java.lang.Long durataOperazione, String trace) {
		try {
			if (idTracciamento != null) {
				PbandiTTracciamentoVO tracciamentoDaAggiornare = new PbandiTTracciamentoVO(
						new BigDecimal(idTracciamento));

				tracciamentoDaAggiornare.setFlagEsito(esito);

				if (messaggio != null && messaggio.length() > 255) {
					messaggio = messaggio.substring(0, 254);
				}

				tracciamentoDaAggiornare.setMessaggioErrore(messaggio);

				double d = (double) durataOperazione / 1000;
				BigDecimal bDurata = new BigDecimal(d);
				bDurata = bDurata.setScale(4, BigDecimal.ROUND_HALF_UP);
				tracciamentoDaAggiornare.setDurata(bDurata);

				genericDAO.updateWithoutTrace(tracciamentoDaAggiornare);
				if (!StringUtil.isEmpty(trace)) {
					PbandiTTracciamentoVO vo = new PbandiTTracciamentoVO(BigDecimal.valueOf(idTracciamento));
					genericDAO.updateClob(vo, "dettaglioErrore", trace);
				}

			} else {
				throw new Exception("Operazione da tracciare nulla");
			}
		} catch (Exception ex) {
			logger.error("traccia esito fallito! " + ex.getMessage(), ex);
			try {
				if (idTracciamento == null) {
					logger.warn("idTracciamento nullo, impossibile eliminare il tracciamento.");
				} else {
					eliminaTracciamento(idTracciamento);
				}
			} catch (Exception exx) {
				logger.error(
						"elimina tracciamento fallito! " + exx.getMessage(),
						exx);
			}
		}
	}

	private BigDecimal getIdOperazione(String descOperazioneFisica) {

		PbandiCOperazioneVO filtroOperazioneVO = new PbandiCOperazioneVO();
		filtroOperazioneVO.setDescFisica(descOperazioneFisica);
		filtroOperazioneVO.setAscendentOrder("idOperazione");

		List<PbandiCOperazioneVO> operazioni = null;

		if (((System.currentTimeMillis() - startCacheOperazioni) < timeOutCacheOperazioni)
				&& cacheOperazioni.containsKey(descOperazioneFisica)) {
			operazioni = (java.util.List<PbandiCOperazioneVO>) cacheOperazioni
					.get(descOperazioneFisica);
		} else {
			operazioni = genericDAO.findListWhere(filtroOperazioneVO);
			startCacheOperazioni = System.currentTimeMillis();
			cacheOperazioni.put(descOperazioneFisica, operazioni);
		}

		BigDecimal idOperazione = null;
		if (operazioni.size() == 0) {
			logger.warn("Operazione non trovata sul database, la inserisco...");
			PbandiCOperazioneVO ultimaOperazioneVO = new PbandiCOperazioneVO();
			ultimaOperazioneVO.setDescendentOrder("idOperazione");
			try {
				idOperazione = genericDAO.findListWhere(ultimaOperazioneVO)
						.get(0).getIdOperazione().add(new BigDecimal(1));

				addOperazione(idOperazione, descOperazioneFisica);
			} catch (Exception e) {
				logger.error("Errore nella addOperazione", e);
				idOperazione = null; // gestire l'assenza di idOperazione fuori!
			}
		} else {
			if (operazioni.size() > 1) {
				logger.warn("Trovate operazioni duplicate! Verra' utilizzata quella con id pi� basso (prima inserita)");
			}
			idOperazione = operazioni.get(0).getIdOperazione();
		}

		return idOperazione;
	}

	private void eliminaTracciamento(Long idTracciamento) throws Exception {
		PbandiTTracciamentoVO tracciamentoDaEliminare = new PbandiTTracciamentoVO();
		tracciamentoDaEliminare.setIdTracciamento(beanUtil.transform(
				idTracciamento, BigDecimal.class));
		genericDAO.deleteWithoutTrace(tracciamentoDaEliminare);
	}

	

	@SuppressWarnings("unchecked")
	public void tracciaEntita(String sql, MapSqlParameterSource params,
			String operazione) {

		BigDecimal idTracciamento = null;
		if (isNull(getTracciamentoDTO())
				|| isNull(getTracciamentoDTO().getIdTracciamento())) {
			getLogger()
					.warn("ATTENZIONE!!! TracciamentoDTO preso dal contesto di spring NULLO");
		} else {
			idTracciamento = beanUtil.transform(
					tracciamentoDTO.getIdTracciamento(), BigDecimal.class);
		}
		try {

			// 1) estraggo i nomi delle tabelle su cui eseguiamo operazioni di
			// insert/update/delete
			// dovrebbe essere sempre una sola tabella, se presente nella
			// tabella delle entita
			// ne traccio l'operazione
			Set<String> tables = PbandiDAO.findTableNames(sql);

			// 3) cerco sulla tabella PBANDI_C_ENTITA in base al nome_tabella
			// trovato nella query di insert e mi aspetto che sia presente nel
			// campo
			// PBANDI_C_ENTITA.NOME_ENTITA
			// NB: in teoria tutte le T e le R dovrebbero essere presenti
			Map<String, java.util.List<AttributoVO>> entities = findEntitiesWithTraceEnabled(tables);

			// per tutti gli attributi con flag a S cerco il valore precedente e
			// lo setto insieme al valore successivo(quello attuale)
			// e all'idAttributo, altrimenti non setto l'idAttributo
			// il target rappresenta le chiavi separate da virgola,SEMPRE
			// PRESENTE

			if (!ObjectUtil.isEmpty(entities)) {
				for (String tabella : entities.keySet()) {

					java.util.List<AttributoVO> attributi = entities
							.get(tabella);

					if (ObjectUtil.isEmpty(attributi))
						continue;
					// target: rappresenta le chiavi del record separate da
					// virgole
					String target = "";

					//
					java.util.List<PbandiTTracciamentoEntitaVO> attributiDaTracciare = new ArrayList<PbandiTTracciamentoEntitaVO>();

					BigDecimal idEntita = null;

					MapSqlParameterSource parametriChiave = new MapSqlParameterSource();

					Map attributiChiave = new HashMap();
					Map attributo = new HashMap();
					for (AttributoVO attributoVO : attributi) {
						PbandiTTracciamentoEntitaVO tracciamentoEntitaVO = new PbandiTTracciamentoEntitaVO();
						tracciamentoEntitaVO.setIdAttributo(attributoVO
								.getIdAttributo());

						attributo.put(attributoVO.getIdAttributo(),
								attributoVO.getNomeAttributo());

						idEntita = attributoVO.getIdEntita();

						// eseguire tante insert quanti sono gli
						// attributiDaTracciare
						if (attributoVO.getFlagDaTracciare() != null
								&& attributoVO.getFlagDaTracciare()
										.equalsIgnoreCase("S")) {
							attributiDaTracciare.add(tracciamentoEntitaVO);
						}
						// popola il target con le chiavi del record
						if (attributoVO.getKeyPositionId() != null) {
							// target+=attributoVO.getKeyPositionId()+",";
							// devo trovare il valore attuale della chiave nella
							// query
							String propertyName = BeanMapper
									.getPropertyNameByDBFieldName(attributoVO
											.getNomeAttributo());
							// logger.debug("nome dell'attributo chiave : "+propertyName);
							// logger.debug("valore dell'attributo chiave: "+params.getValue(propertyName));
							int sqlType = params.getSqlType(propertyName);
							attributiChiave.put(attributoVO.getNomeAttributo(),
									propertyName);
							parametriChiave.addValue(propertyName,
									params.getValue(propertyName), sqlType);

						}

					}

					Iterator iter = attributiChiave.entrySet().iterator();
					while (iter.hasNext()) {
						Map.Entry entry = (Map.Entry) iter.next();
						String nomeChiave = (String) entry.getKey();
						String valoreChiave = genericDAO.findValorePrecedente(
								tabella, nomeChiave, attributiChiave,
								parametriChiave);
						if (!isNull(valoreChiave))
							target += valoreChiave + ",";
					}

					if (!isEmpty(target)) {
						target = target.substring(0, target.length() - 1);
					}

					/**
					 * TRACCIO SEMPRE ALMENO UNA OPERAZIONE (L'OPERAZIONE
					 * sull'ENTITA)
					 */
					PbandiTTracciamentoEntitaVO entitaVO = new PbandiTTracciamentoEntitaVO();
					entitaVO.setDescAttivita(operazione);
					entitaVO.setIdEntita(idEntita);
					entitaVO.setIdTracciamento(idTracciamento);
					entitaVO.setTarget(target);
					if (isEmpty(target)) {
						getLogger()
								.warn("Attenzione! target vuoto! non posso tracciare  ");
					}

					boolean salvaOperazione = true;

					/**
					 * SE PRESENTI,TRACCIO GLI ATTRIBUTI CON FLAG a 'S' il
					 * target rappresenta le chiavi del record separate da
					 * virgola
					 */
					for (PbandiTTracciamentoEntitaVO attributoDaInserire : attributiDaTracciare) {
						attributoDaInserire.setDescAttivita(operazione);
						attributoDaInserire.setIdEntita(idEntita);
						attributoDaInserire.setIdTracciamento(idTracciamento);
						attributoDaInserire.setTarget(target);
						String nomeAttributo = (String) attributo
								.get(NumberUtil.toLong(attributoDaInserire
										.getIdAttributo()));

						// devo leggerlo dal db
						// logger.debug("tabella/entita  "+tabella);
						String valoreSuccessivo = "";
						String valorePrecedente = "";
						if (!INSERT.equalsIgnoreCase(operazione)) {

							String val = genericDAO.findValorePrecedente(
									tabella, nomeAttributo, attributiChiave,
									parametriChiave);

							if (!isEmpty(val))
								valorePrecedente = val;

							attributoDaInserire
									.setValorePrecedente(valorePrecedente);

							// logger.debug(nomeAttributo+" , valorePrecedente : "+valorePrecedente);
						}
						// se si tratta di delete non inserisco il valore
						// successivo
						if (!DELETE.equalsIgnoreCase(operazione)) {
							try {
								String propertyName = BeanMapper
										.getPropertyNameByDBFieldName(nomeAttributo);
								Object obj = (Object) params
										.getValue(propertyName);
								if (obj != null) {
									if (obj instanceof java.util.Date)
										valoreSuccessivo = DateUtil
												.getTime((java.util.Date) obj);
									else
										valoreSuccessivo = obj.toString();
								}
							} catch (Exception e) {
								getLogger()
										.warn("Attenzione:valore successivo NULLO nell'oggetto da tracciare");
							}

							// NB: il valore successivo salvato sulla
							// tracciamento entita pu� non corrispondere
							// a quello sulla tabella dove viene effettivamente
							// salvato nel caso di numeri decimali che abbiamo
							// uno scale:
							// 13.123456789 viene salvato come 13.12 sulla sua
							// tabella se dichiarato come Number(13,2) ,
							// su quella del tracciamento invece viene salvato
							// interamente come 13.123456789 !!!
							// logger.debug(nomeAttributo+" , valoreSuccessivo : "+valoreSuccessivo);

							attributoDaInserire
									.setValoreSuccessivo(valoreSuccessivo);
						}
						/*
						 * TRACCIO l'ATTRIBUTO
						 */

						if (UPDATE.equalsIgnoreCase(operazione)
								&& !isEmpty(valoreSuccessivo)
								&& !valorePrecedente.equals(valoreSuccessivo)) {
							getLogger()
									.info("\n\ninserisco operazione di <"
											+ operazione
											+ "> per attributo <"
											+ nomeAttributo
											+ "> dell'entita <"
											+ entitaVO
													.getTableNameForValueObject()
											+ ">");

							genericDAO.insertWithoutTrace(attributoDaInserire);
							salvaOperazione = false;

						} else {
							getLogger()
									.info("\n\ninserisco operazione di <"
											+ operazione
											+ "> per attributo <"
											+ nomeAttributo
											+ "> dell'entita <"
											+ entitaVO
													.getTableNameForValueObject()
											+ ">");

							genericDAO.insertWithoutTrace(attributoDaInserire);
							salvaOperazione = false;
						}

					}
					/*
					 * INSERISCO OPERAZIONE SU ENTITA
					 */
					if (salvaOperazione) {
						getLogger().debug(
								"inserisco operazione di <" + operazione
										+ "> per entita <"
										+ entitaVO.getTableNameForValueObject()
										+ ">");
						genericDAO.insertWithoutTrace(entitaVO);
					}

				}

			}
		} catch (Exception ex) {
			getLogger().warn(operazione + " , ERRORE: " + ex.getMessage());
			// loggo l'eccezione MA VADO AVANTI LO STESSO: NO ROLLBAKK!!!!
		}  
	}

	private Map<String, java.util.List<AttributoVO>> findEntitiesWithTraceEnabled(
			Set<String> tableNames) {
		Map<String, java.util.List<AttributoVO>> entities = new HashMap<String, java.util.List<AttributoVO>>();

		for (String nomeEntita : tableNames) {
			AttributoVO filtro = new AttributoVO();
			filtro.setFlagDaTracciare(it.csi.pbandi.pbservizit.pbandiutil.common.Constants.FLAG_TRUE);
			filtro.setNomeEntita(nomeEntita.toUpperCase());
			java.util.List<AttributoVO> attributi = null;

			if ((System.currentTimeMillis() - start < timeOut)
					&& cacheEntita.containsKey(nomeEntita))
				attributi = (java.util.List<AttributoVO>) cacheEntita.get(nomeEntita);
			else {
				attributi = genericDAO.where(Condition.filterBy(filtro))
						.select();
				start = System.currentTimeMillis();
				cacheEntita.put(nomeEntita, attributi);

			}
			entities.put(nomeEntita, attributi);

		}

		return entities;

	}

	public void tracciaEsitoPositivo(Long idTracciamento, long start) {
		tracciaEsitoPositivoConMessaggio(idTracciamento, start, "");
	}

	public void tracciaEsitoPositivoConMessaggio(Long idTracciamento,
			long start, String messaggio) {
		long durata = System.currentTimeMillis() - start;
		tracciaEsito(idTracciamento, ESITO_TRACCIAMENTO_OK, messaggio, durata,
				null);
	}

	public void tracciaEsitoNegativo(Long idTracciamento, long start,
			String messaggio) {
		tracciaEsitoNegativo(idTracciamento, start, messaggio, null);
	}

	public void tracciaEsitoNegativo(Long idTracciamento, long start,
			String messaggio, Throwable exception) {

		String trace = null;

		if (exception != null) {
			StringBuilder sb = new StringBuilder(exception.getClass().getName()
					+ " at:\n");
			StackTraceElement[] stackTrace = exception.getStackTrace();

			// PBANDI-2498  inizio
			
			/* codice originale commentato che nascondeva alcune righe del msg di errore.
			if (stackTrace != null && stackTrace.length > 0) {
				boolean etc = false;
				for (StackTraceElement stackTraceElement : stackTrace) {
					if (stackTraceElement.getClassName().startsWith(
							Constants.NOME_PACKAGE_JAVA)) {
						if (etc) {
							sb = sb.append("...\n");
							etc = false;
						}
						sb = sb.append(stackTraceElement + "\n");
					} else {
						if (!etc) {
							sb = sb.append(stackTraceElement + "\n");
							etc = true;
						}
					}
				}
			}
			*/
			
			// Salva tutto il msg di errore (pbandi_t_tracciamento.DETTAGLIO_ERRORE).
			if (stackTrace != null && stackTrace.length > 0) {				
				for (StackTraceElement stackTraceElement : stackTrace) {					
					sb = sb.append(stackTraceElement + "\n");			
				}
			}
			// PBANDI-2498  fine
			
			trace = sb.toString();
		}

		tracciaEsito(idTracciamento, ESITO_TRACCIAMENTO_KO, messaggio,
				(System.currentTimeMillis() - start), trace);
	}
}
