/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebfin.integration.dao;

import it.csi.pbandi.pbwebfin.exception.DaoException;

public interface TracciamentoDAO {

	Long insertTraccia(String operazione, Long idUtente, String esito) throws DaoException;

	void updateTraccia(Long idTracciamento, long time) throws DaoException;

}

