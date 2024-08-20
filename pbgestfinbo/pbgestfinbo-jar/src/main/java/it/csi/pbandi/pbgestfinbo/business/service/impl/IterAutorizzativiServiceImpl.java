/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.business.service.impl;

import it.csi.pbandi.pbgestfinbo.business.service.IterAutorizzativiService;
import it.csi.pbandi.pbgestfinbo.dto.RicercaControlliDTO;
import it.csi.pbandi.pbgestfinbo.integration.dao.IterAutorizzativiDAO;
import it.csi.pbandi.pbgestfinbo.integration.vo.iterAutorizzativi.IterListVO;
import it.csi.pbandi.pbgestfinbo.integration.vo.iterAutorizzativi.SuggestIterVO;
import it.csi.pbandi.pbgestfinbo.util.Constants;
import it.csi.pbandi.pbservizit.dto.archivioFile.EsitoLeggiFile;
import it.csi.pbandi.pbservizit.security.UserInfoSec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Response;
import java.math.BigDecimal;

@Service
public class IterAutorizzativiServiceImpl implements IterAutorizzativiService {
	@Autowired
	IterAutorizzativiDAO iterAutorizzativiDAO;

	//GET ITER
	@Override
	public Response getIterAutorizzativiResponse(RicercaControlliDTO searchVO, HttpServletRequest req) {
		return Response.ok().entity(iterAutorizzativiDAO.getIterAutorizzativi(searchVO, req)).build();
	}

	//GET DETTAGLIO ITER
	@Override
	public Response getDettaglioIterResponse(BigDecimal idWorkflow, HttpServletRequest req) {
		return Response.ok(iterAutorizzativiDAO.getDettaglioIter(idWorkflow)).build();
	}

	//GET ALLEGATI ITER
	@Override
	public Response getAllegatiIterResponse(BigDecimal idWorkflow, HttpServletRequest req) {
		return Response.ok(iterAutorizzativiDAO.getAllegati(idWorkflow)).build();
	}
	@Override
	public Response reportDettaglioDocumentoDiSpesa (Long idDichiarazioneDiSpesa, HttpServletRequest req) throws Exception {
		UserInfoSec userInfo = (UserInfoSec) req.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);
		EsitoLeggiFile esito = iterAutorizzativiDAO.reportDettaglioDocumentoDiSpesa(idDichiarazioneDiSpesa,userInfo.getIdUtente(),userInfo.getIdIride());
		return Response.ok().header("header-nome-file", esito.getNomeFile()).entity(esito.getBytes()).build();
	}

	//GESTIONE ITER
	@Override
	public Response respingiIter(IterListVO iterListVO, HttpServletRequest req) {
		boolean esito = true;
		for(Long idWorkFlow : iterListVO.getIdWorkFlowList()){
			try{
				iterAutorizzativiDAO.respingiIter(idWorkFlow, iterListVO.getMotivazione(), req);
			}catch (Exception e){
				esito = false;
			}
		}
		return Response.ok(esito).build();
	}

	@Override
	public Response autorizzaIter(IterListVO iterListVO, HttpServletRequest req) {
		boolean esito = true;
		for(Long idWorkFlow : iterListVO.getIdWorkFlowList()){
			try{
				iterAutorizzativiDAO.autorizzaIter(idWorkFlow, req);
			}catch (Exception e){
				esito = false;
			}
		}
		return Response.ok(esito).build();
	}

	//SUGGEST
	@Override
    public Response suggestBeneficiario(String value, HttpServletRequest req) throws Exception {
        return Response.ok().entity(iterAutorizzativiDAO.suggestBeneficiario(value, req)).build();
    }

    @Override
    public Response suggestBando(String value, Long idSoggetto, HttpServletRequest req) throws Exception {
        return Response.ok().entity(iterAutorizzativiDAO.suggestBando(value, idSoggetto)).build();
    }

    @Override
    public Response suggestProgetto(String value, Long idSoggetto, Long idBando, HttpServletRequest req) throws Exception {
        return Response.ok().entity(iterAutorizzativiDAO.suggestProgetto(value, idBando, idSoggetto, req)).build();
    }
    
    @Override
    public Response getStatoIterResponse( HttpServletRequest req) {
        return Response.ok().entity(iterAutorizzativiDAO.getStatoIter(req)).build();
    }

	@Override
	public Response suggestIter(SuggestIterVO sugVO, HttpServletRequest req) {
		return Response.ok(iterAutorizzativiDAO.suggestIter(sugVO)).build();
	}

	@Override
	public Response getTendinaBando(HttpServletRequest req) {
		return Response.ok(iterAutorizzativiDAO.getTendinaBando()).build();
	}

	@Override
	public Response getTendinaProgetto(HttpServletRequest req) {
		return Response.ok(iterAutorizzativiDAO.getTendinaProgetto()).build();
	}

	@Override
	public Response getTendinaBeneficiario(HttpServletRequest req) {
		return Response.ok(iterAutorizzativiDAO.getTendinaBeneficiario()).build();
	}

}
