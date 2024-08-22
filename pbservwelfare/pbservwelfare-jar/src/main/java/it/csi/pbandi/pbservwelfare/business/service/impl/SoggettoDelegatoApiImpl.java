/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservwelfare.business.service.impl;

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

import it.csi.pbandi.pbservwelfare.business.service.SoggettoDelegatoApi;
import it.csi.pbandi.pbservwelfare.dto.SoggettoDelegatoResponse;
import it.csi.pbandi.pbservwelfare.exception.ErroreGestitoException;
import it.csi.pbandi.pbservwelfare.exception.RecordNotFoundException;
import it.csi.pbandi.pbservwelfare.integration.dao.SoggettoDelegatoDAO;
import it.csi.pbandi.pbservwelfare.util.CommonUtil;
import it.csi.pbandi.pbservwelfare.util.Constants;

@Transactional(timeout = 240, propagation = Propagation.REQUIRES_NEW, rollbackFor = { Exception.class })
@Component
public class SoggettoDelegatoApiImpl extends BaseApiServiceImpl implements SoggettoDelegatoApi {

	private final Logger LOG = Logger.getLogger(Constants.COMPONENT_NAME);

	@Autowired
	SoggettoDelegatoDAO soggettoDelegatoDao;

	@Override
	public Response setSoggettoDelegato(String numeroDomanda, String nome, String cognome, String codiceFiscale,
			String codiceComuneNascita, String descrizioneComuneNascita, String descrizioneComuneEsteroNascita,
			String dataNascita, String mail, String telefono, String codiceComuneResidenza,
			String descrizioneComuneResidenza, String descrizioneComuneEsteroResidenza, String indirizzo, String cap,
			SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
		String prf = "[SoggettoDelegatoApiImpl::setSoggettoDelegato]";
		LOG.info(prf + " BEGIN");

		SoggettoDelegatoResponse datiResp = new SoggettoDelegatoResponse();

		try {
			LOG.info(prf + " numeroDomanda=" + numeroDomanda);
			// Validazione parametri
			if (!CommonUtil.isEmpty(codiceFiscale) && !CommonUtil.isEmpty(numeroDomanda)
					&& (CommonUtil.checkCodiceFiscale(codiceFiscale) || CommonUtil.checkPartitaIVA(codiceFiscale))) {
				// Passi comuni
				HashMap<String, String> soggBenef = soggettoDelegatoDao.getSoggettoBeneficiario(numeroDomanda);
				// Modifica/Inserimento soggetto
				String idSoggetto = soggBenef.get("idSoggetto");
				String idProgetto = soggBenef.get("idProgetto");
				String progrSoggettoProgetto = soggBenef.get("progrSoggettoProgetto");
				HashMap<String, String> resultMap = soggettoDelegatoDao.isSoggettoPresente(idSoggetto, idProgetto,
						codiceFiscale);
				if ("false".equals(resultMap.get("isSoggettoPresente"))) {
					// Inserimento soggetto
					Integer idSoggettoDelegato = soggettoDelegatoDao.verificaEsistenzaIdentificativoSoggetto(codiceFiscale);
					if (idSoggettoDelegato == null) {
						// Inserimento nella PBANDI_T_SOGGETTO
						soggettoDelegatoDao.insertSoggetto(codiceFiscale);
						idSoggettoDelegato = soggettoDelegatoDao.verificaEsistenzaIdentificativoSoggetto(codiceFiscale);
					}
					Integer idPersonaFisica = soggettoDelegatoDao.verificaEsistenzaIdentificativoPersonaFisica(idSoggettoDelegato);
					// Inserimento nella PBANDI_T_PERSONA_FISICA
					soggettoDelegatoDao.insertPersonaFisica(idSoggettoDelegato, cognome, nome, dataNascita,
							codiceComuneNascita, descrizioneComuneEsteroNascita);
					if (idPersonaFisica == null) {
						idPersonaFisica = soggettoDelegatoDao.verificaEsistenzaIdentificativoPersonaFisica(idSoggettoDelegato);
					}
					// Inserimento nella R_SOGGETTI_CORRELATI
					soggettoDelegatoDao.insertSoggettiCorrelati(idSoggettoDelegato, idSoggetto);
					Integer progrSoggettiCorrelati = soggettoDelegatoDao.getProgrSoggettiCorrelati(idSoggettoDelegato, idSoggetto);
					// Inserimento nella PBANDI_R_SOGGETTO_PROGETTO
					soggettoDelegatoDao.insertSoggettoProgetto(idSoggettoDelegato, idProgetto, idPersonaFisica);
					Integer progrSoggettoProgettoDelegato = soggettoDelegatoDao.getProgrSoggettoProgetto(idSoggettoDelegato, idProgetto, idPersonaFisica);
					// Inserimento nella PBANDI_R_SOGG_PROG_SOGG_CORREL
					soggettoDelegatoDao.insertSoggProgSoggCorrel(progrSoggettoProgettoDelegato, progrSoggettiCorrelati);

					// Inserimento nella PBANDI_T_INDIRIZZO
					if(indirizzo != null || cap != null || codiceComuneNascita != null || descrizioneComuneEsteroNascita != null) {
						soggettoDelegatoDao.updateIdIndirizzo(progrSoggettoProgetto, soggettoDelegatoDao.createIndirizzo(indirizzo, cap, codiceComuneNascita, descrizioneComuneEsteroNascita, progrSoggettoProgettoDelegato.toString()));
					}
					// Inserimento nella PBANDI_T_RECAPITI
					if(mail != null || telefono != null) {
						soggettoDelegatoDao.updateIdRecapiti(progrSoggettoProgetto, soggettoDelegatoDao.createRecapiti(mail, telefono, progrSoggettoProgettoDelegato.toString()));
					}

					datiResp.setIdentificativoSoggettoCorrelato(idSoggettoDelegato);
					datiResp.setMessaggio("Soggetto delegato inserito correttamente");
				} else if ("true".equals(resultMap.get("isSoggettoPresente"))) {
					// Modifica soggetto
					Integer idSoggettoDelegato = soggettoDelegatoDao
							.verificaEsistenzaIdentificativoSoggetto(codiceFiscale);
					// Aggiorna PBANDI_T_PERSONA_FISICA
					soggettoDelegatoDao.updatePersonaFisica(idSoggettoDelegato, cognome, nome, dataNascita,
							codiceComuneNascita, descrizioneComuneEsteroNascita);
					// Aggiorna PBANDI_T_INDIRIZZO e PBANDI_T_RECAPITI
					HashMap<String, Integer> ids = soggettoDelegatoDao.getIdIndirizzoERecapiti(resultMap.get("progrSoggProgettoDelegato"));
					if(indirizzo != null || cap != null || codiceComuneNascita != null || descrizioneComuneEsteroNascita != null) {
						if (ids.get("idIndirizzo") != null) {
							soggettoDelegatoDao.updateIndirizzo(ids.get("idIndirizzo"), indirizzo, cap, codiceComuneNascita, descrizioneComuneEsteroNascita);
						} else {
							ids.put("idIndirizzo", soggettoDelegatoDao.createIndirizzo(indirizzo, cap, codiceComuneNascita, descrizioneComuneEsteroNascita, resultMap.get("progrSoggProgettoDelegato")));
						}
						soggettoDelegatoDao.updateIdIndirizzo(progrSoggettoProgetto, ids.get("idIndirizzo"));
					}
					if(mail != null || telefono != null) {
						if (ids.get("idRecapiti") != null) {
							soggettoDelegatoDao.updateRecapiti(ids.get("idRecapiti"), mail, telefono);
						} else {
							ids.put("idRecapiti", soggettoDelegatoDao.createRecapiti(mail, telefono, resultMap.get("progrSoggProgettoDelegato")));
						}
						soggettoDelegatoDao.updateIdRecapiti(progrSoggettoProgetto, ids.get("idRecapiti"));
					}

					datiResp.setIdentificativoSoggettoCorrelato(idSoggettoDelegato);
					datiResp.setMessaggio("Soggetto delegato aggiornato correttamente");
				}
				datiResp.setEsito("OK");
			} else {
				datiResp = getErroreParametriInvalidiSoggettiCorrelati();
			}

		} catch (RecordNotFoundException e) {
			throw e;
		} catch (Exception e) {
			LOG.error(prf + "Exception while trying to read SoggettoDelegatoResponse", e);
			throw new ErroreGestitoException("DaoException while trying to read SoggettoDelegatoResponse", e);
		} finally {
			LOG.info(prf + " END");
		}

		return Response.ok(datiResp).build();
	}

}
