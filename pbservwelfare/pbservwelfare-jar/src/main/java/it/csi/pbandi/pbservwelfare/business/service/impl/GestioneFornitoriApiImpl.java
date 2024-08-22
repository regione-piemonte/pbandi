/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservwelfare.business.service.impl;

import java.util.Arrays;
import java.util.List;

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

import it.csi.pbandi.pbservwelfare.business.service.GestioneFornitoriApi;
import it.csi.pbandi.pbservwelfare.dto.EsitoFornitoriResponse;
import it.csi.pbandi.pbservwelfare.dto.EsitoResponse;
import it.csi.pbandi.pbservwelfare.dto.FileAllegato;
import it.csi.pbandi.pbservwelfare.dto.RequestInserisciFornitore;
import it.csi.pbandi.pbservwelfare.integration.dao.GestioneFornitoriDAO;
import it.csi.pbandi.pbservwelfare.util.CommonUtil;
import it.csi.pbandi.pbservwelfare.util.Constants;

@Transactional(timeout = 240, propagation = Propagation.REQUIRES_NEW, rollbackFor = { Exception.class })
@Component
public class GestioneFornitoriApiImpl extends BaseApiServiceImpl implements GestioneFornitoriApi {

    private final Logger LOG = Logger.getLogger(Constants.COMPONENT_NAME);

    @Autowired
    GestioneFornitoriDAO gestioneFornitoriDAO;

    @Override
    public Response elencoFornitori(String numeroDomanda, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        String prf = "[GestioneFornitoriApiImpl::elencoFornitori]";
        LOG.info(prf + " BEGIN");

        LOG.info(prf + " numeroDomanda=" + numeroDomanda);

        EsitoFornitoriResponse datiResp = new EsitoFornitoriResponse();

        try {
            // Validazione parametri
            if (CommonUtil.isEmpty(numeroDomanda)) {
                //Se ho trovato errori nei parametri restituisco l'errore
                datiResp = getErroreParametriInvalidiEsitoFornitori();
            } else {
                datiResp.setFornitori(gestioneFornitoriDAO.getFornitori(numeroDomanda));

                if(datiResp.getFornitori().isEmpty()){
                    datiResp = getErroreNessunDatoSelezionatoEsitoFornitori();
                }
            }

        } catch (DataAccessException ex) {
            LOG.error(prf + "Errore di communicazione con il database, ", ex);
            datiResp = getErroreConnessioneDbEsitoFornitori();
        } catch (Exception e) {
            LOG.error(prf + "Exception while trying to get elencoFornitori", e);
            datiResp = getErroreGenericoEsitoFornitori();
        } finally {
            LOG.info(prf + " END");
        }

        return Response.ok(datiResp).build();
    }

	@Override
	public Response setFornitore(RequestInserisciFornitore body, SecurityContext securityContext,
			HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
		String prf = "[GestioneFornitoriApiImpl::setFornitore]";
		LOG.info(prf + " BEGIN");

		LOG.info(prf + " numeroDomanda=" + body.getNumeroDomanda() + ", codiceFiscale= " + body.getCodiceFiscale()
				+ ", cognome=" + body.getCognome() + ", nome=" + body.getNome() + ", denominazione="
				+ body.getDenominazione() + ", dataInizio=" + body.getDataInizio());

		EsitoResponse datiResp = new EsitoResponse();

		try {
			// Validazione parametri
			boolean erroreParametri = CommonUtil.isEmpty(body.getNumeroDomanda())
					|| !CommonUtil.checkCodiceFiscale(body.getCodiceFiscale())
					|| CommonUtil.isEmpty(body.getDataInizio().toString());
			LOG.info("erroreParametri=" + erroreParametri);
			// Controllo i parametri del contratto
			boolean erroreParametriFile = false;
			if(body.getFiles().isEmpty()){
				erroreParametriFile = true;
			}else {
				for(FileAllegato fileAllegato : body.getFiles()){
					LOG.info("nomeFile=" + fileAllegato.getNomeFile());
					if(CommonUtil.isEmpty(Arrays.toString(fileAllegato.getFile())) || CommonUtil.isEmpty(fileAllegato.getNomeFile())){
						erroreParametriFile = true;
					}
				}
			}
			LOG.info("erroreParametriFile=" + erroreParametriFile);
			
			if (CommonUtil.isEmpty(body.getNome()) && CommonUtil.isEmpty(body.getCognome())
					&& CommonUtil.isEmpty(body.getDenominazione())) {
				body.setDenominazione("N.D.");
			}

			Long idSoggBenef = null;
			if (!erroreParametri && !erroreParametriFile) {
				//Controllo se c'è il soggetto beneficiario, in caso contrario lancio errore parametri
				idSoggBenef = gestioneFornitoriDAO.getIdSoggettoBeneficiario(body.getNumeroDomanda());
				if (idSoggBenef == null) {
					erroreParametri = true;
				}
			}

			if (!erroreParametri && !erroreParametriFile) {
				// Cerco l'id del vecchio fornitore per capire se aggiornare o inserire un nuovo
				// fornitore
				List<Long> idFornitori = gestioneFornitoriDAO.getIdFornitore(idSoggBenef, body.getCodiceFiscale());

				for(Long idFornitore : idFornitori){
					// Se ho un fornitore da aggiornare disabilito il vecchio fornitore per inserire il nuovo
					gestioneFornitoriDAO.disabilitaFornitore(idFornitore);
				}
				// Inserisco il fornitore se non ho errori nei parametri del file
				Long newIdFornitore = gestioneFornitoriDAO.salvaFornitore(idSoggBenef, body.getNome(), body.getCognome(),
						body.getDenominazione(), body.getCodiceFiscale(), body.getPartitaIva(),
						body.getCodiceFormaGiuridica(), body.getDataInizio(), body.getDataFine());
				// Salvo il nuovo contratto (e gli allegati passati)
				for(FileAllegato fileAllegato : body.getFiles()) {
					gestioneFornitoriDAO.salvaContratto(body.getNumeroDomanda(), idSoggBenef,
							body.getCodiceFiscale(), newIdFornitore, fileAllegato.getFile(), fileAllegato.getNomeFile(),
							body.getDataInizio(), body.getDataFine()
					);
				}
				datiResp.setEsitoServizio("OK");
				datiResp.setMessaggio(idFornitori.isEmpty()
						? "Il fornitore è stato inserito nella base dati con i nuovi dati anagrafici"
						: "Il fornitore è stato correttamente aggiornato.");
			}

			if (erroreParametri) {
				// Se ho trovato errori nei parametri restituisco l'errore
				datiResp = getErroreParametriInvalidi();
			} else if(erroreParametriFile) {
				datiResp = getErroreFileNonTrovato();
			}

		} catch (DataAccessException ex) {
			LOG.error(prf + "Errore di communicazione con il database, ", ex);
			datiResp = getErroreConnessioneDB();
		} catch (Exception e) {
			LOG.error(prf + "Exception while trying to setFornitore", e);
			datiResp = getErroreGenerico();
		} finally {
			LOG.info(prf + " END");
		}

		return Response.ok(datiResp).build();
	}
}
