/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.integration.dao.impl.rowmapper.suggestion;

import it.csi.pbandi.pbgestfinbo.integration.vo.suggestion.DenominazioneSuggestVO;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DenominazioneSuggestVORowMapper implements RowMapper<DenominazioneSuggestVO> {
    @Override
    public DenominazioneSuggestVO mapRow(ResultSet rs, int rowNum) throws SQLException {
        DenominazioneSuggestVO denominazioneSuggestVO = new DenominazioneSuggestVO();

        denominazioneSuggestVO.setIdSoggetto(rs.getLong("idSoggetto"));
        denominazioneSuggestVO.setDenominazione(rs.getString("denominazione"));
        denominazioneSuggestVO.setCodiceFiscaleSoggetto(rs.getString("codiceFiscaleSoggetto"));

        return denominazioneSuggestVO;
    }
}
