/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservwelfare.business.service.impl;

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

import it.csi.pbandi.pbservwelfare.business.service.RicezioneSegnalazioniApi;
import it.csi.pbandi.pbservwelfare.dto.InfoDaNumeroDomanda;
import it.csi.pbandi.pbservwelfare.dto.RequestRicezioneSegnalazioni;
import it.csi.pbandi.pbservwelfare.dto.RicezioneSegnalazioniResponse;
import it.csi.pbandi.pbservwelfare.exception.RecordNotFoundException;
import it.csi.pbandi.pbservwelfare.integration.dao.RicezioneSegnalazioniDAO;
import it.csi.pbandi.pbservwelfare.util.CommonUtil;
import it.csi.pbandi.pbservwelfare.util.Constants;

/**
 * Servizio 16
 *
 */
@Transactional(timeout = 240, propagation = Propagation.REQUIRES_NEW, rollbackFor = { Exception.class })
@Component
public class RicezioneSegnalazioniApiImpl extends BaseApiServiceImpl implements RicezioneSegnalazioniApi {

	private Logger LOG = Logger.getLogger(Constants.COMPONENT_NAME);

	@Autowired
	RicezioneSegnalazioniDAO ricezioneSegnalazioniDao;

	@Override
	public Response setRicezioneSegnalazioni(RequestRicezioneSegnalazioni body, SecurityContext securityContext,
			HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
		String prf = "[RicezioneSegnalazioniApiImpl::setRicezioneSegnalazioni]";
		LOG.info(prf + " BEGIN");

		RicezioneSegnalazioniResponse datiResp = new RicezioneSegnalazioniResponse();
		try {
			String numeroDomanda = body.getNumeroDomanda();
			String codiceNotifica = body.getCodiceNotifica();
			Date data = body.getData();
			String descrizioneNotifica = body.getDescrizioneNotifica();
			LOG.info(prf + " numeroDomanda=" + numeroDomanda + ", codiceNotifica=" + codiceNotifica + ", data=" + data
					+ ", descrizioneNotifica=" + descrizioneNotifica);
			// Validazione parametri
			if (!CommonUtil.isEmpty(numeroDomanda)) {
				// 1. Ricezione dei parametri
				// 1.1 Numero domanda
				InfoDaNumeroDomanda info = ricezioneSegnalazioniDao.getInfo(numeroDomanda);
				if (info != null) {
					// 2. Verifica dei parametri
					boolean dataObbligatoria = "DEC".equals(codiceNotifica) || "CR".equals(codiceNotifica)
							|| "RIN".equals(codiceNotifica) || "DTCONC".equals(codiceNotifica);
					boolean descNotificaObbligatoria = "PAM".equals(codiceNotifica) || "ISEE".equals(codiceNotifica);
					if ("MANCREND".equals(codiceNotifica) || ((dataObbligatoria && data != null)
							|| (descNotificaObbligatoria && !CommonUtil.isEmpty(descrizioneNotifica)))) {
						// 3. Elaborazione della notifica
						String descBreveTemplate = mappaCodiceNotifica(codiceNotifica);
						HashMap<String, String> infoTemplate = ricezioneSegnalazioniDao
								.getInfoTemplate(descBreveTemplate);
						if(infoTemplate != null) {
							String messaggioNotifica = infoTemplate.get("messaggioNotifica");
							String dataFormattata = "";
							if(data != null) {
								dataFormattata = new SimpleDateFormat("dd/MM/yyyy").format(data);
							}
							String messaggioNotificaNew = messaggioNotifica.replace("${data}", dataFormattata)
									.replace("${cod_fiscale}", info.getCodiceFiscaleSoggetto())
									.replace("${denominazione}", info.getNome() + " " + info.getCognome())
									.replace("${descr_notifica}", descrizioneNotifica);
							int result = ricezioneSegnalazioniDao.insertNotifica(info.getIdProgetto(),
									infoTemplate.get("oggettoNotifica"), messaggioNotificaNew,
									Integer.parseInt(infoTemplate.get("idTemplate")));
							if (result == 1) {
								datiResp.setEsitoServizio("OK");
							} else {
								datiResp = getErroreGenericoRicezioneSegnalazioni("Nessun record inserito");
							}
						} else {
							datiResp = getErroreGenericoRicezioneSegnalazioni("Non è stato trovato il template per " + descBreveTemplate);
						}
					} else {
						datiResp = getErroreParametriInvalidiRicezioneSegnalazioni();
					}
				} else {
					datiResp = getErroreNessunRisultato();
				}
			} else {
				datiResp = getErroreParametriInvalidiRicezioneSegnalazioni();
			}
		} catch (RecordNotFoundException e) {
			datiResp = getErroreGenericoRicezioneSegnalazioni(e.getMessage());
			return Response.ok(datiResp).build();
		} catch (Exception e) {
			LOG.error(prf + "Exception while trying to read RicezioneSegnalazioniResponse", e);
			datiResp = getErroreGenericoRicezioneSegnalazioni(e.getMessage());
			return Response.ok(datiResp).build();
		} finally {
			LOG.info(prf + " END");
		}

		return Response.ok(datiResp).build();
	}

	/**
	 * Mappa il codice con la relativa descrizione
	 * 
	 * @param codiceNotifica
	 * @return
	 */
	private String mappaCodiceNotifica(String codiceNotifica) {
		String descBreveTemplate = "";
		if ("DEC".equals(codiceNotifica)) {
			descBreveTemplate = "NotificaDecessoBeneficiario";
		} else if ("CR".equals(codiceNotifica)) {
			descBreveTemplate = "NotificaCambioResidenzaBeneficiario";
		} else if ("RIN".equals(codiceNotifica)) {
			descBreveTemplate = "NotificaRinunciaBeneficiario";
		} else if ("PAM".equals(codiceNotifica)) {
			descBreveTemplate = "NotificaPresenzaAltreMisure";
		} else if ("ISEE".equals(codiceNotifica)) {
			descBreveTemplate = "NotificaModificaISEE";
		} else if ("DTCONC".equals(codiceNotifica)) {
			descBreveTemplate = "NotificaModificaDataConcessione";
		} else if ("MANCREND".equals(codiceNotifica)) {
			descBreveTemplate = "NotificaMancataRendicontazione";
		}
		return descBreveTemplate;
	}

}
