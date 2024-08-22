/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservwelfare.business.service.impl;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import it.csi.pbandi.pbservwelfare.business.service.GestioneBeneficiarioApi;
import it.csi.pbandi.pbservwelfare.dto.EsitoResponse;
import it.csi.pbandi.pbservwelfare.dto.IbanBeneficiario;
import it.csi.pbandi.pbservwelfare.dto.RequestRicezioneIban;
import it.csi.pbandi.pbservwelfare.integration.dao.GestioneBeneficiarioDAO;
import it.csi.pbandi.pbservwelfare.util.CommonUtil;
import it.csi.pbandi.pbservwelfare.util.Constants;

/**
 * Servizio 30
 *
 */
@Transactional(timeout = 240, propagation = Propagation.REQUIRES_NEW, rollbackFor = { Exception.class })
@Component
public class GestioneBeneficiarioApiImpl extends BaseApiServiceImpl implements GestioneBeneficiarioApi {

	private final Logger LOG = Logger.getLogger(Constants.COMPONENT_NAME);

	@Autowired
	GestioneBeneficiarioDAO gestioneBeneficiarioDAO;

	@Override
	public Response getRicezioneIban(RequestRicezioneIban body, SecurityContext securityContext,
			HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
		String prf = "[GestioneBeneficiarioApiImpl::getRicezioneIban]";
		LOG.info(prf + " BEGIN");

		LOG.info(prf + " Request:" + body.toString());

		EsitoResponse datiResp = new EsitoResponse();

		try {
			// Validazione parametri
			boolean erroreParametri = CommonUtil.isEmpty(body.getNumeroDomanda()) || !checkIban(body.getIban());

			if (!erroreParametri) {
				// Recupero le informazioni del beneficiario
				IbanBeneficiario infoIbanBeneficiario = gestioneBeneficiarioDAO
						.getInfoIbanBeneficiario(body.getNumeroDomanda());

				if (infoIbanBeneficiario != null) {
					LOG.info(prf + " Recuperato infoIbanBeneficiario = " + infoIbanBeneficiario);
					// Censimento notifica
					gestioneBeneficiarioDAO.censimentoNotificaProcesso(infoIbanBeneficiario, body);

					// Aggiornamento estremi bancari (Se non presente ne crea uno nuovo)
					gestioneBeneficiarioDAO.updateEstremiBancari(infoIbanBeneficiario, body.getIban());

					datiResp.setEsitoServizio("OK");
					datiResp.setMessaggio("Iban beneficiario aggiornato correttamente!");
				} else {
					datiResp = getErroreNessunDatoSelezionato();
				}
			} else {
				datiResp = getErroreParametriInvalidi();
			}
		} catch (DataAccessException ex) {
			LOG.error(prf + "Errore di communicazione con il database, ", ex);
			datiResp = getErroreConnessioneDB();
		} catch (Exception e) {
			LOG.error(prf + "Exception while trying to getRicezioneIban", e);
			datiResp = getErroreGenerico();
		} finally {
			LOG.info(prf + " END");
		}

		return Response.ok(datiResp).build();
	}

	/**
	 * Verifica la validità dell'IBAN
	 * 
	 * Nelle prime due posizioni ci sono i caratteri IT 
	 * Deve essere lungo 27 e in 3^ e 4^ posizione c'è un numero 
	 * In 5^ posizione c'è una lettera
	 * Dalla posizione 6 alla 10 c'è l'ABI (numerico)  
	 * Dalla posizione 11 alla 15 c'è il CAB (numerico) 
	 * Dalla posizione 16 alla 27 ci devono essere 12 caratteri compresi anche di 0 non significativi in testa.
	 * 
	 * @param iban
	 * @return
	 */
	private boolean checkIban(String iban) {
		boolean ibanCorretto = false;
		if(!CommonUtil.isEmpty(iban)) {
			try {
				if (iban.length() > 2 && iban.substring(0, 2).equalsIgnoreCase("IT")) {
					if (iban.length() != 27) {
						LOG.error("IBAN incorretto: lunghezza errata");
						return ibanCorretto;
					}
					if (!(CommonUtil.isNumber(iban.substring(2, 4)))) {
						LOG.error("IBAN incorretto: caratteri non numerici in 3^ e 4^ posizione");
						return ibanCorretto;
					}
					String cin = iban.substring(4, 5);
					if (!(CommonUtil.isString(cin))) {
						LOG.error("IBAN incorretto: CIN non corretto");
						return ibanCorretto;
					}
					String abi = iban.substring(5, 10);
					if (!(CommonUtil.isNumber(abi))) {
						LOG.error("IBAN incorretto: ABI non numerico");
						return ibanCorretto;
					}
	
					String cab = iban.substring(10, 15);
					if (!(CommonUtil.isNumber(cab))) {
						LOG.error("IBAN incorretto: CAB non numerico");
						return ibanCorretto;
					}
	
//					String contoCorrente = iban.substring(15, 27);
//					if (!(CommonUtil.isNumber(contoCorrente))) {
//						LOG.error("IBAN incorretto: conto corrente non numerico");
//						return ibanCorretto;
//					}
	
				} else {
					LOG.error("IBAN incorretto: non inizia con IT");
					return ibanCorretto;
				}
			} catch (Exception e) {
				LOG.error(e.getMessage(), e);
			}
		} else {
			return ibanCorretto;
		}

		return true;
	}

}
