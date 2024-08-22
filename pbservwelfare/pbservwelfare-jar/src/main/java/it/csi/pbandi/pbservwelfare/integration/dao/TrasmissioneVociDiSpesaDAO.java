/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservwelfare.integration.dao;

import it.csi.pbandi.pbservwelfare.dto.VociDiSpesa;

import java.util.List;

public interface TrasmissioneVociDiSpesaDAO {

    List<VociDiSpesa> getVociDiSpesa(String numeroDomanda);

}
