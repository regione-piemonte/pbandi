/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.integration.dao;

import java.math.BigDecimal;
import java.util.List;

import javax.servlet.http.HttpServletRequest;


import it.csi.pbandi.pbgestfinbo.exception.DaoException;
import it.csi.pbandi.pbgestfinbo.integration.vo.search.SearchVO;

public interface SearchDAO {
	
	List<SearchVO> cercaBeneficiarioEg(String cf, Long idSoggetto, String pIva, String denominazione, String numeroDomanda, BigDecimal codProgetto, HttpServletRequest req)  throws DaoException;
	List<SearchVO> cercaBeneficiarioPf(String cf, Long idSoggetto, String numeroDomanda, BigDecimal codProgetto, String nome, String cognome, HttpServletRequest req)  throws DaoException;

}
