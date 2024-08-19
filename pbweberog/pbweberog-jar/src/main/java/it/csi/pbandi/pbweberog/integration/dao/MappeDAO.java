/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweberog.integration.dao;

import it.csi.pbandi.pbweberog.pbandisrv.dto.LinkMappaDTO;

public interface MappeDAO {

	LinkMappaDTO linkMappa() throws Exception;

}
