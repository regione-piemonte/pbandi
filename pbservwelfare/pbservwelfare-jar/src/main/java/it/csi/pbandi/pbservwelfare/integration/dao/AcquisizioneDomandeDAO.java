/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservwelfare.integration.dao;

import java.sql.SQLException;
import java.util.HashMap;

public interface AcquisizioneDomandeDAO {
	
	HashMap<String, String> insertXml(String file, String nomeFile, String numeroDomanda) throws SQLException;

}
