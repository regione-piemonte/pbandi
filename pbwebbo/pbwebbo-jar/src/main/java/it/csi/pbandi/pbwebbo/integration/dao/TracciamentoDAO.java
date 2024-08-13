/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebbo.integration.dao;

import it.csi.pbandi.pbwebbo.exception.DaoException;

public interface TracciamentoDAO {

	Long insertTraccia(String operazione, Long idUtente, String esito) throws DaoException;

	void updateTraccia(Long idTracciamento, long time) throws DaoException;

}

