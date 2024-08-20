/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.business.service.impl;

import java.math.BigDecimal;
import java.security.InvalidParameterException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import it.csi.pbandi.pbgestfinbo.exception.DaoException;
import it.csi.pbandi.pbgestfinbo.integration.dao.DatiDomandaDAO;
import it.csi.pbandi.pbgestfinbo.integration.vo.search.AnagraficaBeneficiarioPfVO;
import it.csi.pbandi.pbgestfinbo.integration.vo.search.AnagraficaBeneficiarioVO;
import it.csi.pbandi.pbgestfinbo.integration.vo.search.DatiDomandaEgVO;
import it.csi.pbandi.pbgestfinbo.integration.vo.search.DatiDomandaVO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.checklist.datiChecklistDTO;
import it.csi.pbandi.pbgestfinbo.business.service.DatiDomandaService;

@Service
public class DatiDomandaServiceImpl implements DatiDomandaService{

	@Autowired
	protected DatiDomandaDAO DatiDomandaDao;
	
	@Override
	public Response getDatiDomanda(Long idSoggetto, String idDomanda, HttpServletRequest req) throws DaoException {
		
		List<DatiDomandaVO> datiDomanda = this.DatiDomandaDao.getDatiDomanda(idSoggetto, idDomanda, req);

		return Response.ok().entity(datiDomanda).build();
		
	}

	@Override
	public Response getDatiDomandaEG(Long idSoggetto, String idDomanda, HttpServletRequest req) throws DaoException {
		
		DatiDomandaEgVO datiDomanda = this.DatiDomandaDao.getDatiDomandaEG(idSoggetto, idDomanda, req);

		return Response.ok().entity(datiDomanda).build();
		
	}

	@Override
	public Response getElencoSoggCorrDipDaDomanda(String idDomanda, Long idSoggetto,HttpServletRequest req) throws DaoException {
		return Response.ok(DatiDomandaDao.getElencoSoggCorrDipDaDomanda(idDomanda, idSoggetto)).build();
	}

	@Override
	public Response getAnagSoggCorrDipDaDomPF(Long idSoggetto, String idDomanda,BigDecimal idSoggCorr,HttpServletRequest req) throws DaoException {
		return Response.ok(DatiDomandaDao.getAnagSoggCorrDipDaDomPF(idSoggetto, idDomanda,idSoggCorr)).build();
	}

	@Override
	public Response getAnagSoggCorrDipDaDomEG(Long idSoggetto,String idDomanda, BigDecimal idSoggCorr, HttpServletRequest req) throws DaoException {
		return Response.ok(DatiDomandaDao.getAnagSoggCorrDipDaDomEG(idSoggetto, idDomanda,idSoggCorr )).build();
	}

	@Override
	public Response listaRuoli() throws DaoException {
		return Response.ok(DatiDomandaDao.listRuoli()).build();
	}

	@Override
	public Response getListaSugg(String stringa, int id, HttpServletRequest req) throws DaoException {	
		return Response.ok(DatiDomandaDao.getListaSuggest(stringa, id)).build();
	}

	@Override
	public Response modificaPF(AnagraficaBeneficiarioPfVO soggetto, String idSoggetto, String idDomanda,
			HttpServletRequest request) throws InvalidParameterException, Exception {
		return Response.ok(DatiDomandaDao.modificaSoggetto(soggetto, idSoggetto, idDomanda)).build();
	}

	@Override
	public Response modificaSoggettoDipDomandaEG(AnagraficaBeneficiarioVO soggetto, String idSoggetto, String idDomanda,
			HttpServletRequest request) throws InvalidParameterException, Exception {
		return Response.ok(DatiDomandaDao.modificaSoggettoEG(soggetto, idSoggetto, idDomanda)).build();
	}

	@Override
	public Response updateDatiDomandaEG(DatiDomandaEgVO domandaEG, BigDecimal idUtente)
			throws InvalidParameterException, Exception {
		return Response.ok(DatiDomandaDao.updateDatiDomandaEG(domandaEG, idUtente)).build();
	}

	@Override
	public Response checKProgetto(String numeroDomanda, HttpServletRequest req) throws DaoException {
		// TODO Auto-generated method stub
		return Response.ok(DatiDomandaDao.checkProgetto(numeroDomanda)).build();
	}

	@Override
	public Response updateDatiDomandaPF(DatiDomandaVO datiDomanda, BigDecimal idUtente)
			throws InvalidParameterException, Exception {
		return Response.ok(DatiDomandaDao.updateDatiDomandaPF(datiDomanda, idUtente)).build();
	}

	@Override
	public Response getAltreSedi(Long idSoggetto, String idDomanda, HttpServletRequest req) throws DaoException {
		// TODO Auto-generated method stub
		return Response.ok(DatiDomandaDao.getAltreSedi(idSoggetto, idDomanda)).build();
	}



}
