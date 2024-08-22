/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservwelfare.business.service.impl;

import it.csi.pbandi.pbservwelfare.business.service.TrasmissioneVociDiSpesaApi;
import it.csi.pbandi.pbservwelfare.dto.EsitoVociDiSpesaResponse;
import it.csi.pbandi.pbservwelfare.integration.dao.TrasmissioneVociDiSpesaDAO;
import it.csi.pbandi.pbservwelfare.util.CommonUtil;
import it.csi.pbandi.pbservwelfare.util.Constants;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

@Transactional(timeout = 240, propagation = Propagation.REQUIRES_NEW, rollbackFor = { Exception.class })
@Component
public class TrasmissioneVociDiSpesaApiImpl extends BaseApiServiceImpl implements TrasmissioneVociDiSpesaApi {

    private final Logger LOG = Logger.getLogger(Constants.COMPONENT_NAME);

    @Autowired
    TrasmissioneVociDiSpesaDAO trasmissioneVociDiSpesaDAO;

    @Override
    public Response getTrasmissioneVociDiSpesa(String numeroDomanda, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        String prf = "[TrasmissioneVociDiSpesaApiImpl::getTrasmissioneVociDiSpesa]";
        LOG.info(prf + " BEGIN");

        LOG.info(prf + " numeroDomanda=" + numeroDomanda);

        EsitoVociDiSpesaResponse datiResp = new EsitoVociDiSpesaResponse();

        try {
            // Validazione parametri
            if (CommonUtil.isEmpty(numeroDomanda)) {
                //Se ho trovato errori nei parametri restituisco l'errore
                datiResp = getErroreParametriInvalidiEsitoVociDiSpesa();
            } else {
                datiResp.setVociDiSpesa(trasmissioneVociDiSpesaDAO.getVociDiSpesa(numeroDomanda));

                if(datiResp.getVociDiSpesa().isEmpty()){
                    datiResp = getErroreNessunDatoSelezionatoEsitoVociDiSpesa();
                }else{
                    datiResp.setMessaggio("Voci di spesa recuperate correttamente");
                    datiResp.setEsitoServizio("OK");
                }
            }

        } catch (DataAccessException ex) {
            LOG.error(prf + "Errore di communicazione con il database, ", ex);
            datiResp = getErroreConnessioneDbEsitoVociDiSpesa();
        } catch (Exception e) {
            LOG.error(prf + "Exception while trying to get vociDiSpesa", e);
            datiResp = getErroreGenericoEsitoVociDiSpesa();
        } finally {
            LOG.info(prf + " END");
        }

        return Response.ok(datiResp).build();
    }
}
