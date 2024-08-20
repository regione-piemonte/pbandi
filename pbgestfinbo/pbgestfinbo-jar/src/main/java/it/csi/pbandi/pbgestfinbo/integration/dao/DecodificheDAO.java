/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.integration.dao;


import it.csi.pbandi.pbgestfinbo.exception.DaoException;


public interface DecodificheDAO {

	String costante(String attributo, boolean obbligatoria) throws DaoException; 
	
}
