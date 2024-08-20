/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.integration.dao.impl.rowmapper.suggestion;

import it.csi.pbandi.pbgestfinbo.integration.vo.suggestion.AgevolazioneSuggestVO;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AgevolazioneSuggestVORowMapper implements RowMapper<AgevolazioneSuggestVO> {
    @Override
    public AgevolazioneSuggestVO mapRow(ResultSet rs, int rowNum) throws SQLException {
        AgevolazioneSuggestVO agevolazioneSuggestVO = new AgevolazioneSuggestVO();

        agevolazioneSuggestVO.setIdModalitaAgevolazione(rs.getLong("idModalitaAgevolazione"));
        agevolazioneSuggestVO.setDescModalitaAgevolazione(rs.getString("descModalitaAgevolazione"));
        agevolazioneSuggestVO.setDescBreveModalitaAgevolazione(rs.getString("descBreveModalitaAgevolazione"));

        return agevolazioneSuggestVO;
    }
}
