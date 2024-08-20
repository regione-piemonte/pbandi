/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.integration.dao;

import it.csi.pbandi.pbgestfinbo.integration.vo.MetadatiActaVO;

import java.util.List;

public interface CreazioneFascicoliFPDao {
    List<MetadatiActaVO> getMetadatiActaList(Integer progrBandoLineaInterveno);
}
