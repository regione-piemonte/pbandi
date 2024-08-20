/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.integration.dao;

import javax.servlet.http.HttpServletRequest;

import it.csi.pbandi.pbgestfinbo.exception.DaoException;
import it.csi.pbandi.pbgestfinbo.integration.vo.search.BeneficiarioDomandaVO;

public interface BeneficiarioDomandaDAO {
	
	BeneficiarioDomandaVO getLegaleRappr(Long idSoggetto, Long idDomanda, HttpServletRequest req)  throws DaoException;
	
	BeneficiarioDomandaVO getSedeAmm(Long idSoggetto, Long idDomanda,  HttpServletRequest req)  throws DaoException;
	
	BeneficiarioDomandaVO getRecapiti(Long idSoggetto, Long idDomanda, HttpServletRequest req)  throws DaoException;
	
	BeneficiarioDomandaVO getConto(Long idSoggetto, Long idDomanda, HttpServletRequest req)  throws DaoException;
	
	BeneficiarioDomandaVO getDelegati(Long idDomanda, HttpServletRequest req)  throws DaoException;
	
	BeneficiarioDomandaVO getConsulenti(Long idDomanda, HttpServletRequest req)  throws DaoException;

}
