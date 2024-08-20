/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.business.service.impl;

import java.sql.Date;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import it.csi.pbandi.pbgestfinbo.business.service.SuggModificaAnagraficaBeneficiarioService;
import it.csi.pbandi.pbgestfinbo.exception.DaoException;
import it.csi.pbandi.pbgestfinbo.util.Constants;

@Service
public class SuggModificaAnagraficaBeneficiariServiceImpl implements SuggModificaAnagraficaBeneficiarioService{
	
	private Logger LOG = Logger.getLogger(Constants.COMPONENT_NAME);

	@Override
	public Response getFormaGiuridica(String formaGiuridica, HttpServletRequest req) throws DaoException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Response getCodIpa(String codiceIpa, HttpServletRequest req) throws DaoException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Response getDataCostituzione(Date dataCostituzione, HttpServletRequest req) throws DaoException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Response getComune(String comune, HttpServletRequest req) throws DaoException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Response getProvincia(String provincia, HttpServletRequest req) throws DaoException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Response getCap(Long cap, HttpServletRequest req) throws DaoException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Response getRegione(String regione, HttpServletRequest req) throws DaoException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Response getNazione(String nazione, HttpServletRequest req) throws DaoException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Response getAteco(Long ateco, HttpServletRequest req) throws DaoException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Response getDescAteco(String descAteco, HttpServletRequest req) throws DaoException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Response getRea(String rea, HttpServletRequest req) throws DaoException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Response getDataIscrizione(Date dataIscrizione, HttpServletRequest req) throws DaoException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Response getProvinciaIscr(String provinciaIscr, HttpServletRequest req) throws DaoException {
		// TODO Auto-generated method stub
		return null;
	}

}
