/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.business.service.impl;

import it.csi.pbandi.pbgestfinbo.business.service.ContestazioniService;
import it.csi.pbandi.pbgestfinbo.dto.utils.ResponseCodeMessage;
import it.csi.pbandi.pbgestfinbo.integration.dao.ContestazioniDAO;
import it.csi.pbandi.pbgestfinbo.integration.vo.AllegatiContestazioniVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Response;
import java.util.List;

@Service
public class ContestazioniServiceImpl implements ContestazioniService {
    @Autowired
    ContestazioniDAO contestazioniDAO;

    @Override
    public Response getContestazioni(Long idProgetto, HttpServletRequest req) {
        return Response.ok(contestazioniDAO.getContestazioni(idProgetto)).build();
    }

    @Override
    public Response getCodiceProgetto(Long idProgetto, HttpServletRequest req) {
        return Response.ok(contestazioniDAO.getCodiceProgetto(idProgetto)).build();
    }

    @Override
    public Response getAllegati(Long idContestazione, HttpServletRequest req) {
        return Response.ok(contestazioniDAO.getAllegati(idContestazione)).build();
    }

    @Override
    public Response inserisciLetteraAllegato(Long idContestazione, List<AllegatiContestazioniVO> allegati, HttpServletRequest req) {
        ResponseCodeMessage result;

        try {
            contestazioniDAO.inserisciLetteraAllegato(allegati, idContestazione);
            result = new ResponseCodeMessage("OK", "Inserimento Lettera o Allegato avvenuto con successo!");
        } catch (Exception e) {
            result = new ResponseCodeMessage("KO", e.getMessage());
        }

        return Response.ok(result).build();
    }

    @Override
    public Response deleteAllegato(Long idFileEntita, HttpServletRequest req) {
        return Response.ok(contestazioniDAO.deleteAllegato(idFileEntita)).build();
    }

    @Override
    public Response inserisciContestazione(Long idGestioneRevoca, HttpServletRequest req) {
        return Response.ok(contestazioniDAO.inserisciContestazione(idGestioneRevoca, req)).build();
    }

    @Override
    public Response inviaContestazione(Long idContestazione, HttpServletRequest req) {
        return Response.ok(contestazioniDAO.inviaContestazione(idContestazione, req)).build();
    }

    @Override
    public Response eliminaContestazione(Long idContestazione, HttpServletRequest req) {
        return Response.ok(contestazioniDAO.eliminaContestazione(idContestazione, req)).build();
    }

}
