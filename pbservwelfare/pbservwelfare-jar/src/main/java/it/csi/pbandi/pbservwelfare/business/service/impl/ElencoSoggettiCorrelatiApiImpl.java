/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservwelfare.business.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
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

import it.csi.pbandi.pbservwelfare.business.service.ElencoSoggettiCorrelatiApi;
import it.csi.pbandi.pbservwelfare.dto.IdentificativoBeneficiario;
import it.csi.pbandi.pbservwelfare.dto.IdentificativoSoggettoCorrelato;
import it.csi.pbandi.pbservwelfare.dto.SoggettiCorrelati;
import it.csi.pbandi.pbservwelfare.dto.SoggettiCorrelatiListResponse;
import it.csi.pbandi.pbservwelfare.dto.SoggettoCorrelato;
import it.csi.pbandi.pbservwelfare.exception.ErroreGestitoException;
import it.csi.pbandi.pbservwelfare.exception.RecordNotFoundException;
import it.csi.pbandi.pbservwelfare.integration.dao.ElencoSoggettiCorrelatiDAO;
import it.csi.pbandi.pbservwelfare.util.CommonUtil;
import it.csi.pbandi.pbservwelfare.util.Constants;

@Transactional(timeout = 240, propagation = Propagation.REQUIRES_NEW, rollbackFor = { Exception.class })
@Component
public class ElencoSoggettiCorrelatiApiImpl extends BaseApiServiceImpl implements ElencoSoggettiCorrelatiApi {

	private final Logger LOG = Logger.getLogger(Constants.COMPONENT_NAME);

	@Autowired
	ElencoSoggettiCorrelatiDAO soggettiCorrelatiDao;

	@Override
	public Response getSoggettoCorrelato(String numeroDomanda, SecurityContext securityContext, HttpHeaders httpHeaders,
			HttpServletRequest httpRequest) {
		String prf = "[ElencoSoggettiCorrelatiApiImpl::getSoggettoCorrelato]";
		LOG.info(prf + " BEGIN");

		SoggettiCorrelatiListResponse datiResp = new SoggettiCorrelatiListResponse();
		List<SoggettoCorrelato> soggettiCorrelatiList = new ArrayList<>();

		try {
			LOG.info(prf + " numeroDomanda=" + numeroDomanda);
			// Validazione parametri
			if (!CommonUtil.isEmpty(numeroDomanda)) {
				// Recupera identificativo beneficiario
				List<IdentificativoBeneficiario> listaBeneficiari = soggettiCorrelatiDao
						.getIdentificativoBeneficiario(numeroDomanda);
				if (!listaBeneficiari.isEmpty()) {
					for (IdentificativoBeneficiario benef : listaBeneficiari) {
						// Recupera identificativo soggetto correlato
						List<IdentificativoSoggettoCorrelato> listaSoggettiCorrelati = soggettiCorrelatiDao
								.getIdentificativoSoggettoCorrelato(benef.getIdSoggBeneficiario());
						for (IdentificativoSoggettoCorrelato soggettoCorrelato : listaSoggettiCorrelati) {
							List<SoggettiCorrelati> listaSoggetti = soggettiCorrelatiDao.getSoggettiCorrelati(
									soggettoCorrelato.getProgrSoggettiCorrelati(), benef.getIdProgetto());
							// Recupera info soggetti correlati
							for (SoggettiCorrelati soggetto : listaSoggetti) {
								SoggettoCorrelato sogg = new SoggettoCorrelato();
								// PBANDI_T_PERSONA_FISICA
								HashMap<String, String> infoAnagrafiche = soggettiCorrelatiDao
										.getInfoAnagrafiche(soggetto.getIdPersonaFisica());
								sogg.setIdentificativoSoggettoCorrelato(soggettoCorrelato.getIdSoggettoCorrelato());
								sogg.setTipoSoggetto(soggetto.getDescTipoSoggettoCorrelato());
								sogg.setNome(infoAnagrafiche.get("nome"));
								sogg.setCognome(infoAnagrafiche.get("cognome"));
								sogg.setCodiceFiscale(soggetto.getCodFiscaleSoggetto());
								sogg.setDataNascita(infoAnagrafiche.get("dataNascita"));
								if (!CommonUtil.isEmpty(infoAnagrafiche.get("idComuneItalianoNascita"))) {
									// PBANDI_D_COMUNE
									HashMap<String, String> infoComune = soggettiCorrelatiDao
											.getInfoComune(infoAnagrafiche.get("idComuneItalianoNascita"));
									sogg.setCodiceComuneNascita(infoComune.get("codiceComuneNascita"));
									sogg.setDescrizioneComuneNascita(infoComune.get("descrizioneComuneNascita"));
									sogg.setCodiceComuneResidenza(infoComune.get("codiceComuneNascita"));
									sogg.setDescrizioneComuneResidenza(infoComune.get("descrizioneComuneNascita"));
								} else if (!CommonUtil.isEmpty(infoAnagrafiche.get("idComuneEsteroNascita"))) {
									// PBANDI_D_COMUNE_ESTERO
									HashMap<String, String> infoComuneEstero = soggettiCorrelatiDao
											.getInfoComuneEstero(infoAnagrafiche.get("idComuneEsteroNascita"));
									sogg.setDescrizioneComuneEsteroNascita(
											infoComuneEstero.get("descrizioneComuneEsteroNascita"));
									sogg.setDescrizioneComuneEsteroResidenza(
											infoComuneEstero.get("descrizioneComuneEsteroNascita"));
								}
								// PBANDI_T_RECAPITI
								HashMap<String, String> infoRecapiti = soggettiCorrelatiDao
										.getInfoRecapiti(soggetto.getIdRecapitiPersonaFisica());
								sogg.setMail(infoRecapiti.get("mail"));
								sogg.setTelefono(infoRecapiti.get("telefono"));
								// PBANDI_T_INDIRIZZO
								HashMap<String, String> infoIndirizzo = soggettiCorrelatiDao
										.getInfoIndirizzo(soggetto.getIdIndirizzoPersonaFisica());
								sogg.setIndirizzo(infoIndirizzo.get("indirizzo") + ", " + infoIndirizzo.get("civico"));
								sogg.setCap(infoIndirizzo.get("cap"));
								soggettiCorrelatiList.add(sogg);
							}
						}
						// Recupera info beneficiario
						SoggettoCorrelato sogg = new SoggettoCorrelato();
						// PBANDI_T_PERSONA_FISICA
						HashMap<String, String> infoAnagrafiche = soggettiCorrelatiDao
								.getInfoAnagrafiche(benef.getIdPersonaFisicaBeneficiario());
						sogg.setIdentificativoSoggettoCorrelato(benef.getIdPersonaFisicaBeneficiario());
						sogg.setTipoSoggetto("BENEFICIARIO");
						sogg.setNome(infoAnagrafiche.get("nome"));
						sogg.setCognome(infoAnagrafiche.get("cognome"));
						sogg.setCodiceFiscale(benef.getCodiceFiscaleSoggetto());
						sogg.setDataNascita(infoAnagrafiche.get("dataNascita"));
						if (!CommonUtil.isEmpty(infoAnagrafiche.get("idComuneItalianoNascita"))) {
							// PBANDI_D_COMUNE
							HashMap<String, String> infoComune = soggettiCorrelatiDao
									.getInfoComune(infoAnagrafiche.get("idComuneItalianoNascita"));
							sogg.setCodiceComuneNascita(infoComune.get("codiceComuneNascita"));
							sogg.setDescrizioneComuneNascita(infoComune.get("descrizioneComuneNascita"));
							sogg.setCodiceComuneResidenza(infoComune.get("codiceComuneNascita"));
							sogg.setDescrizioneComuneResidenza(infoComune.get("descrizioneComuneNascita"));
						} else if (!CommonUtil.isEmpty(infoAnagrafiche.get("idComuneEsteroNascita"))) {
							// PBANDI_D_COMUNE_ESTERO
							HashMap<String, String> infoComuneEstero = soggettiCorrelatiDao
									.getInfoComuneEstero(infoAnagrafiche.get("idComuneEsteroNascita"));
							sogg.setDescrizioneComuneEsteroNascita(
									infoComuneEstero.get("descrizioneComuneEsteroNascita"));
							sogg.setDescrizioneComuneEsteroResidenza(
									infoComuneEstero.get("descrizioneComuneEsteroNascita"));
						}
						// PBANDI_T_RECAPITI
						HashMap<String, String> infoRecapiti = soggettiCorrelatiDao
								.getInfoRecapiti(benef.getIdRecapitiPersonaFisica());
						sogg.setMail(infoRecapiti.get("mail"));
						sogg.setTelefono(infoRecapiti.get("telefono"));
						// PBANDI_T_INDIRIZZO
						HashMap<String, String> infoIndirizzo = soggettiCorrelatiDao
								.getInfoIndirizzo(benef.getIdIndirizzoPersonaFisica());
						sogg.setIndirizzo(infoIndirizzo.get("indirizzo") + ", " + infoIndirizzo.get("civico"));
						sogg.setCap(infoIndirizzo.get("cap"));
						soggettiCorrelatiList.add(sogg);
					}
					datiResp.setSoggettiCorrelatiList(soggettiCorrelatiList);
					datiResp.setMessaggio("Soggetti correlati recuperati correttamente");
					datiResp.setEsitoServizio("OK");
				} else {
					datiResp = getErroreBeneficiarioNonTrovato();
				}
			} else {
				datiResp = getErroreParametriInvalidiListaSoggettiCorrelati();
			}
		} catch (RecordNotFoundException e) {
			throw e;
		} catch (Exception e) {
			LOG.error(prf + "Exception while trying to read SoggettiCorrelatiListResponse", e);
			throw new ErroreGestitoException("DaoException while trying to read SoggettiCorrelatiListResponse", e);
		} finally {
			LOG.info(prf + " END");
		}

		return Response.ok(datiResp).build();
	}

}
