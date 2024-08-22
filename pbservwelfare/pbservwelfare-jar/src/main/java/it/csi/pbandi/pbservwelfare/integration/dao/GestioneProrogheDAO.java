/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservwelfare.integration.dao;

import java.math.BigDecimal;

public interface GestioneProrogheDAO {

    BigDecimal getIdControdeduzione(BigDecimal numeroRevoca);

    void insertProrogaControdeduzione(BigDecimal idControdeduzione, Integer numeroGiorni, String motivazione);
}
