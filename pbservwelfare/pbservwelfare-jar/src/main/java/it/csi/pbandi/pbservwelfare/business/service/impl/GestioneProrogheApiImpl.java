/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservwelfare.business.service.impl;

import it.csi.pbandi.pbservwelfare.business.service.GestioneProrogheApi;
import it.csi.pbandi.pbservwelfare.dto.EsitoResponse;
import it.csi.pbandi.pbservwelfare.integration.dao.GestioneProrogheDAO;
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
import java.math.BigDecimal;

@Transactional(timeout = 240, propagation = Propagation.REQUIRES_NEW, rollbackFor = { Exception.class })
@Component
public class GestioneProrogheApiImpl extends BaseApiServiceImpl implements GestioneProrogheApi {

    private final Logger LOG = Logger.getLogger(Constants.COMPONENT_NAME);

    @Autowired
    GestioneProrogheDAO gestioneProrogheDAO;

    @Override
    public Response richiediProroga(String numeroDomanda, BigDecimal numeroRevoca, Integer numeroGiorni, String motivazione, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        String prf = "[GestioneProroghe::richiediProroga]";
        LOG.info(prf + " BEGIN");

        LOG.info(prf + " numeroDomanda=" + numeroDomanda);
        LOG.info(prf + " numeroRevoca=" + numeroRevoca);
        LOG.info(prf + " numeroGiorni=" + numeroGiorni);
        LOG.info(prf + " motivazione=" + motivazione);

        EsitoResponse datiResp = new EsitoResponse();

        try {
            // Validazione parametri
            if (CommonUtil.isEmpty(numeroDomanda) || numeroRevoca == null
            || numeroGiorni == null || CommonUtil.isEmpty(motivazione)) {
                //Se ho trovato errori nei parametri restituisco l'errore
                datiResp = getErroreParametriInvalidi();
            } else {
                BigDecimal idControdeduzione = gestioneProrogheDAO.getIdControdeduzione(numeroRevoca);

                if(idControdeduzione == null){
                    datiResp = getErroreNessunDatoSelezionato();
                }else{
                    gestioneProrogheDAO.insertProrogaControdeduzione(idControdeduzione, numeroGiorni, motivazione);
                    datiResp.setEsitoServizio("OK");
                    datiResp.setMessaggio("Proroga per la controdeduzione inserita correttamente");
                }
            }

        } catch (DataAccessException ex) {
            LOG.error(prf + "Errore di communicazione con il database, ", ex);
            datiResp = getErroreConnessioneDB();
        } catch (Exception e) {
            LOG.error(prf + "Exception while trying to get richiediProroga", e);
            datiResp = getErroreGenerico();
        } finally {
            LOG.info(prf + " END");
        }

        return Response.ok(datiResp).build();
    }
}
