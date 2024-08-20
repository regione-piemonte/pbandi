/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.integration.dao.impl.rowmapper;

import it.csi.pbandi.pbgestfinbo.integration.vo.ControlloAntimafiaVO;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ControlloAntimafiaVORowMapper implements RowMapper<ControlloAntimafiaVO> {
    @Override
    public ControlloAntimafiaVO mapRow(ResultSet rs, int rowNum) throws SQLException {
        ControlloAntimafiaVO controlloAntimafiaVO = new ControlloAntimafiaVO();

        controlloAntimafiaVO.setDataEmissioneAntimafia(rs.getDate("dtEmissioneAntimafia"));
        controlloAntimafiaVO.setDataScadenzaAntimafia(rs.getDate("dtScadenza"));
        controlloAntimafiaVO.setDescEsitoAntimafia(rs.getString("descEsitoRichieste"));
        controlloAntimafiaVO.setDescStatoRichiestaAntimafia(rs.getString("descStatoRichiesta"));
        controlloAntimafiaVO.setDataInvioBDNA(rs.getDate("dtRicezioneBdna"));
        controlloAntimafiaVO.setDescTipoRichiesta(rs.getString("descTipoRichiesta"));

        return controlloAntimafiaVO;
    }
}
