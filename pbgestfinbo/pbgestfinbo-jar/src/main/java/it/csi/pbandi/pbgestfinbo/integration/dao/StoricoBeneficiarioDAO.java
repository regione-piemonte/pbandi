/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.integration.dao;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import it.csi.pbandi.pbgestfinbo.exception.DaoException;
import it.csi.pbandi.pbgestfinbo.integration.vo.search.StoricoBeneficiarioVO;
import it.csi.pbandi.pbgestfinbo.integration.vo.search.VersioniVO;

public interface StoricoBeneficiarioDAO {
	
	List<StoricoBeneficiarioVO> getStorico(Long idSoggetto, HttpServletRequest req)  throws DaoException;
	List<StoricoBeneficiarioVO> getStoricoFisico(Long idSoggetto, HttpServletRequest req) throws DaoException;
	List<VersioniVO> getVersioni(Long idSoggetto,HttpServletRequest req)  throws DaoException;

}
