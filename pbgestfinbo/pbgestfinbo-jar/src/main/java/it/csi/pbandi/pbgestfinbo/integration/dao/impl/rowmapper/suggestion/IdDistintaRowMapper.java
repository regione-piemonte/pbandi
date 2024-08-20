/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.integration.dao.impl.rowmapper.suggestion;

import it.csi.pbandi.pbgestfinbo.integration.vo.suggestion.IdDistintaVO;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class IdDistintaRowMapper implements RowMapper<IdDistintaVO> {
    @Override
    public IdDistintaVO mapRow(ResultSet rs, int rowNum) throws SQLException {
        IdDistintaVO idDistintaVO = new IdDistintaVO();

        idDistintaVO.setIdDistinta(rs.getLong("idDistinta"));
        idDistintaVO.setDescrizioneDistinta(rs.getString("descrizioneDistinta"));
        idDistintaVO.setDescrizioneModalitaAgevolazione(rs.getString("descrModalitaAgevolaz"));

        return idDistintaVO;
    }
}
