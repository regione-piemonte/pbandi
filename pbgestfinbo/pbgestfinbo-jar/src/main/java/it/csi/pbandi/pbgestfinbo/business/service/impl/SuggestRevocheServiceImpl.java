/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.business.service.impl;

import it.csi.pbandi.pbgestfinbo.business.service.SuggestRevocheService;
import it.csi.pbandi.pbgestfinbo.integration.dao.SuggestRevocheDAO;
import it.csi.pbandi.pbgestfinbo.integration.vo.search.FiltroRevocaVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Response;

@Service
public class SuggestRevocheServiceImpl implements SuggestRevocheService {

    @Autowired
    SuggestRevocheDAO suggestRevocheDAO;

    @Override
    public Response newNumeroRevoca(HttpServletRequest req) {
        return Response.ok().entity(suggestRevocheDAO.newNumeroRevoca()).build();
    }

    @Override
    public Response suggestNumeroRevoche(FiltroRevocaVO filtroRevocaVO, HttpServletRequest req) throws Exception {
        return Response.ok().entity(suggestRevocheDAO.suggestNumeroRevoche(filtroRevocaVO)).build();
    }

    @Override
    public Response suggestBeneficiario(String nomeBeneficiario, Long progrBandoLineaIntervent, HttpServletRequest req) throws Exception {
        return Response.ok().entity(suggestRevocheDAO.suggestBeneficiario(nomeBeneficiario, progrBandoLineaIntervent)).build();
    }

    @Override
    public Response suggestBandoLineaIntervent(String nomeBandoLinea, Long idSoggetto, HttpServletRequest req) throws Exception {
        return Response.ok().entity(suggestRevocheDAO.suggestBandoLineaIntervent(nomeBandoLinea, idSoggetto)).build();
    }

    @Override
    public Response suggestProgetto(String codiceVisualizzatoProgetto, Long idSoggetto, Long progrBandoLineaIntervent, HttpServletRequest req) throws Exception {
        return Response.ok().entity(suggestRevocheDAO.suggestProgetto(codiceVisualizzatoProgetto, progrBandoLineaIntervent, idSoggetto)).build();
    }

    @Override
    public Response suggestCausaRevoche(FiltroRevocaVO filtroRevocaVO, HttpServletRequest req) {
        return Response.ok().entity(suggestRevocheDAO.suggestCausaRevoche(filtroRevocaVO)).build();
    }

    @Override
    public Response suggestStatoRevoche(FiltroRevocaVO filtroRevocaVO, HttpServletRequest req) {
        return Response.ok().entity(suggestRevocheDAO.suggestStatoRevoche(filtroRevocaVO)).build();
    }

    @Override
    public Response suggestAttivitaRevoche(FiltroRevocaVO filtroRevocaVO, HttpServletRequest req) {
        return Response.ok().entity(suggestRevocheDAO.suggestAttivitaRevoche(filtroRevocaVO)).build();
    }

    @Override
    public Response suggestAllCausaRevoca(HttpServletRequest req) {
        return Response.ok().entity(suggestRevocheDAO.suggestAllCausaRevoca()).build();
    }

    @Override
    public Response suggestAllAutoritaControllante(HttpServletRequest req) {
        return Response.ok().entity(suggestRevocheDAO.suggestAllAutoritaControllante()).build();
    }

    @Override
    public Response getModalitaRecupero(HttpServletRequest req) {
        return Response.ok().entity(suggestRevocheDAO.getModalitaRecupero()).build();
    }

    @Override
    public Response getMotivoRevoca(HttpServletRequest req) {
        return Response.ok().entity(suggestRevocheDAO.getMotivoRevoca()).build();
    }

}
