/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservwelfare.business.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import it.csi.pbandi.pbservwelfare.business.service.EsposizioneMensilitaApi;
import it.csi.pbandi.pbservwelfare.dto.ElencoMensilita;
import it.csi.pbandi.pbservwelfare.dto.ElencoMensilitaResponse;
import it.csi.pbandi.pbservwelfare.dto.InfoMensilita;
import it.csi.pbandi.pbservwelfare.exception.RecordNotFoundException;
import it.csi.pbandi.pbservwelfare.integration.dao.EsposizioneMensilitaDAO;
import it.csi.pbandi.pbservwelfare.util.CommonUtil;
import it.csi.pbandi.pbservwelfare.util.Constants;

/**
 * Servizio 28
 *
 */
@Transactional(timeout = 240, propagation = Propagation.REQUIRES_NEW, rollbackFor = { Exception.class })
@Component
public class EsposizioneMensilitaApiImpl extends BaseApiServiceImpl implements EsposizioneMensilitaApi {

	private Logger LOG = Logger.getLogger(Constants.COMPONENT_NAME);

	@Autowired
	EsposizioneMensilitaDAO esposizioneMensilitaDao;

	@Override
	public Response getElencoMensilita(String numeroDomanda, Integer identificativoDichiarazioneDiSpesa,
			SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
		String prf = "[EsposizioneMensilitaApiImpl::getElencoMensilita]";
		LOG.info(prf + " BEGIN");

		ElencoMensilitaResponse datiResp = new ElencoMensilitaResponse();
		List<InfoMensilita> listaRaggruppata = new ArrayList<InfoMensilita>();
		try {
			LOG.info(prf + " numeroDomanda=" + numeroDomanda + ", identificativoDichiarazioneDiSpesa="
					+ identificativoDichiarazioneDiSpesa);
			// Validazione parametri
			if (!CommonUtil.isEmpty(numeroDomanda) || identificativoDichiarazioneDiSpesa != null) {
				// Estrazione mensilità
				List<Integer> listaProgetti = esposizioneMensilitaDao.getListaProgetti(numeroDomanda);
				for (Integer idProgetto : listaProgetti) {
					// Recupera mensilità
					List<InfoMensilita> listaMensilita = esposizioneMensilitaDao.recuperaMensilita(idProgetto,
							identificativoDichiarazioneDiSpesa);
					// Calcolo mensilità per progetto
					if (!listaMensilita.isEmpty()) {
						if (identificativoDichiarazioneDiSpesa == null) {
							// Controllo se ci sono più occorrenze per la stessa mensilità
							Map<Integer, List<InfoMensilita>> anniGrouped = listaMensilita.stream()
									.collect(Collectors.groupingBy(InfoMensilita::getAnno));
							for (Map.Entry<Integer, List<InfoMensilita>> anno : anniGrouped.entrySet()) {
								Map<String, List<InfoMensilita>> mensilitaGrouped = anno.getValue().stream()
										.collect(Collectors.groupingBy(InfoMensilita::getMese));
								for (int i = 1; i <= 12; i++) {
									String prefisso = i < 10 ? "0" : "";
									String mese = prefisso + String.valueOf(i);
									// Caso di mese singolo
									if (mensilitaGrouped.containsKey(mese) && mensilitaGrouped.get(mese).size() == 1) {
										listaRaggruppata.addAll(mensilitaGrouped.get(mese));
									} else if (mensilitaGrouped.containsKey(mese)
											&& mensilitaGrouped.get(mese).size() > 1) {
										// Caso di più occorrenze per lo stesso mese
										boolean condVerificata = false;
										// 1° criterio di raggruppamento
										for (InfoMensilita mensilita : mensilitaGrouped.get(mese)) {
											if ("OK".equals(mensilita.getEsitoValidazione())) {
												listaRaggruppata.add(mensilita);
												condVerificata = true;
												break;
											}
										}
										// 2° criterio di raggruppamento (se non si è verificato il primo)
										if (!condVerificata) {
											for (InfoMensilita mensilita : mensilitaGrouped.get(mese)) {
												if (mensilita.isSabbatico()) {
													listaRaggruppata.add(mensilita);
													condVerificata = true;
													break;
												}
											}
										}
										// 3° criterio di raggruppamento (se non si è verificato il secondo)
										if (!condVerificata) {
											for (InfoMensilita mensilita : mensilitaGrouped.get(mese)) {
												if ("NV".equals(mensilita.getEsitoValidazione())) {
													listaRaggruppata.add(mensilita);
													condVerificata = true;
													break;
												}
											}
										}
										// 4° criterio di raggruppamento (se non si è verificato il terzo)
										if (!condVerificata) {
											for (InfoMensilita mensilita : mensilitaGrouped.get(mese)) {
												if ("KO".equals(mensilita.getEsitoValidazione())) {
													listaRaggruppata.add(mensilita);
													condVerificata = true;
													break;
												}
											}
										}
									}
								}
							}
							popolaResponse(datiResp, listaRaggruppata);
						} else {
							popolaResponse(datiResp, listaMensilita);
						}
					} else {
						datiResp = getErroreNessunDato();
					}
				}
			} else {
				datiResp = getErroreParametriInvalidiElencoMensilita();
			}
		} catch (RecordNotFoundException e) {
			datiResp = getErroreGenerico(e.getMessage());
			return Response.ok(datiResp).build();
		} catch (Exception e) {
			LOG.error(prf + "Exception while trying to read ElencoMensilitaResponse", e);
			datiResp = getErroreGenerico(e.getMessage());
			return Response.ok(datiResp).build();
		} finally {
			LOG.info(prf + " END");
		}

		return Response.ok(datiResp).build();
	}

	/**
	 * Metodo che popola l'oggetto di response a partire dalla lista iniziale
	 * 
	 * @param datiResp
	 * @param lista    Può essere quella raggruppata nel caso in cui in input non ci
	 *                 sia idDichiarazioneSpesa oppure quella originaria
	 */
	private void popolaResponse(ElencoMensilitaResponse datiResp, List<InfoMensilita> lista) {
		List<ElencoMensilita> elencoMensilita = new ArrayList<ElencoMensilita>();
		for (InfoMensilita info : lista) {
			ElencoMensilita mese = new ElencoMensilita();
			mese.setDichiarazioneSpesa(info.getDichiarazioneSpesa());
			mese.setAnno(info.getAnno());
			mese.setMese(info.getMese());
			if ("OK".equals(info.getEsitoValidazione())) {
				mese.setEsitoValidazione("Validato");
			} else if ("KO".equals(info.getEsitoValidazione())) {
				mese.setEsitoValidazione("Respinto");
			} else if ("NV".equals(info.getEsitoValidazione())) {
				mese.setEsitoValidazione("Invalidato");
			}
			mese.setSabbatico(info.isSabbatico());
			if ("OK".equals(info.getEsitoValidazione()) && info.getDtChiusuraValidazione() != null) {
				mese.setImportoRendicontato(600.0);
				mese.setImportoValidato(600.0);
			} else {
				mese.setImportoRendicontato(0.0);
				mese.setImportoValidato(0.0);
			}
			if ("OK".equals(info.getEsitoValidazione()) && !CommonUtil.isEmpty(info.getNoteErogazioni())) {
				mese.setImportoInErogazione(600.0);
			} else {
				mese.setImportoInErogazione(0.0);
			}
			mese.setDataAggiornamento(info.getDataAggiornamento());
			elencoMensilita.add(mese);
		}
		datiResp.setEsitoServizio("OK");
		datiResp.setElencoMensilita(elencoMensilita);
	}

}
