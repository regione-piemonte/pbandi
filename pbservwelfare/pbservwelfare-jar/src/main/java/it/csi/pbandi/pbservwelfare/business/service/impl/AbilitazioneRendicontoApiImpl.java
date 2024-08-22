/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservwelfare.business.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
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

import it.csi.pbandi.pbservwelfare.business.service.AbilitazioneRendicontoApi;
import it.csi.pbandi.pbservwelfare.dto.AbilitaRendicontazioneResponse;
import it.csi.pbandi.pbservwelfare.exception.ErroreGestitoException;
import it.csi.pbandi.pbservwelfare.exception.RecordNotFoundException;
import it.csi.pbandi.pbservwelfare.integration.dao.AbilitazioneRendicontoDAO;
import it.csi.pbandi.pbservwelfare.util.CommonUtil;
import it.csi.pbandi.pbservwelfare.util.Constants;

@Transactional(timeout = 240, propagation = Propagation.REQUIRES_NEW, rollbackFor = { Exception.class })
@Component
public class AbilitazioneRendicontoApiImpl extends BaseApiServiceImpl implements AbilitazioneRendicontoApi {

	private Logger LOG = Logger.getLogger(Constants.COMPONENT_NAME);

	@Autowired
	AbilitazioneRendicontoDAO abilitazioneRendicontoDao;

	@Override
	public Response getAbilitaRendicontazione(String numeroDomanda, String codiceFiscaleUtente,
			SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
		String prf = "[AbilitazioneRendicontoApiImpl::getAbilitaRendicontazione]";
		LOG.info(prf + " BEGIN");

		AbilitaRendicontazioneResponse datiResp = new AbilitaRendicontazioneResponse();

		try {
			LOG.info(prf + " numeroDomanda=" + numeroDomanda);
			// Validazione parametri
			if (!CommonUtil.isEmpty(codiceFiscaleUtente) && !CommonUtil.isEmpty(numeroDomanda)
					&& (CommonUtil.checkCodiceFiscale(codiceFiscaleUtente)
							|| CommonUtil.checkPartitaIVA(codiceFiscaleUtente))) {

				// Verifico se il bando è Domiciliarità o Residenzialità
				boolean isBandoDomiciliarita = false;
				boolean isAbilitato = false;
				if (codiceFiscaleUtente.length() == 16) {
					isBandoDomiciliarita = true;
				}

				/** 
				 * Caso Bando Domiciliarità
				 */
				if (isBandoDomiciliarita) {
					// Controllo codice fiscale
					if (abilitazioneRendicontoDao.isCodiceFiscalePresentePerDomiciliarita(codiceFiscaleUtente,
							numeroDomanda)) {
						// Estrazione identificativi soggetto beneficiario
						HashMap<String, String> resp = estrazioneIdentificativiSoggettoBeneficiario(numeroDomanda,
								datiResp);
						String idProgetto = resp.get("idProgetto");
						if (resp.size() != 0) {
							// Verifica soggetto
							if(abilitazioneRendicontoDao.verificaSoggettoPerDomiciliarita(idProgetto, codiceFiscaleUtente)) {
								isAbilitato = true;
								datiResp = verificaAbilitazioneARendicontare(datiResp, idProgetto);
							}
							if (!isAbilitato) {
								datiResp = getErroreSoggettoNonAssociato();
							}
						} else {
							datiResp = getErroreProgettoNonPresente();
						}
					} else {
						datiResp = getErroreCodiceFiscaleErrato();
					}
				/** 
				 * Caso Bando Residenzialità
				 */
				} else {
					// Controllo codice fiscale
					if (abilitazioneRendicontoDao.isCodiceFiscalePresentePerResidenzialita(codiceFiscaleUtente)) {
						// Estrazione identificativi soggetto beneficiario
						HashMap<String, String> resp = estrazioneIdentificativiSoggettoBeneficiario(numeroDomanda,
								datiResp);
						String idProgetto = resp.get("idProgetto");
						if (resp.size() != 0) {
							// Verifica soggetto
							ArrayList<String> listaCodiceFiscaleFornitore = abilitazioneRendicontoDao
									.getListaCodiceFiscaleFornitore(datiResp.getIdSoggettoBeneficiario());
							for (String codiceFiscale : listaCodiceFiscaleFornitore) {
								if (codiceFiscale.equals(codiceFiscaleUtente)) {
									isAbilitato = true;
									datiResp = verificaAbilitazioneARendicontare(datiResp, idProgetto);
								}
							}
							if (!isAbilitato) {
								datiResp = getErroreSoggettoNonCensito();
							}
						} else {
							datiResp = getErroreProgettoNonPresente();
						}
					} else {
						datiResp = getErroreCodiceFiscaleErrato();
					}
				}

			} else {
				datiResp = getErroreParametriInvalidiAbilitazioneRendicontazione();
			}
		} catch (RecordNotFoundException e) {
			throw e;
		} catch (Exception e) {
			LOG.error(prf + "Exception while trying to read AbilitaRendicontazioneResponse", e);
			throw new ErroreGestitoException("DaoException while trying to read AbilitaRendicontazioneResponse", e);
		} finally {
			LOG.info(prf + " END");
		}

		return Response.ok(datiResp).build();
	}

	/**
	 * @param numeroDomanda
	 * @param datiResp
	 * @return
	 */
	private HashMap<String, String> estrazioneIdentificativiSoggettoBeneficiario(String numeroDomanda,
			AbilitaRendicontazioneResponse datiResp) {
		// Estrazione identificativi soggetto beneficiario
		HashMap<String, String> resp = abilitazioneRendicontoDao.getSoggettoBeneficiario(numeroDomanda);
		if(resp != null && resp.size() > 0) {
			datiResp.setCodiceFiscaleBeneficiario(resp.get("codFiscaleSoggetto"));
			datiResp.setCodiceProgetto(resp.get("codiceVisualizzato"));
			datiResp.setIdSoggettoBeneficiario(Integer.parseInt(resp.get("idSoggetto")));
		}
		return resp;
	}

	/**
	 * @param datiResp
	 * @param idProgetto
	 * @return
	 */
	private AbilitaRendicontazioneResponse verificaAbilitazioneARendicontare(AbilitaRendicontazioneResponse datiResp,
			String idProgetto) {
		String descBreveStatoProgetto = abilitazioneRendicontoDao.verificaAbilitazioneARendicontare(idProgetto);
		if (Arrays.asList(Constants.STATO_PROGETTO_NON_ABILITATO).contains(descBreveStatoProgetto)) {
			datiResp = getErroreProgettoNonAbiliato();
		} else {
			datiResp.setAbilitato(true);
			datiResp.setEsito("OK");
		}
		return datiResp;
	}

}