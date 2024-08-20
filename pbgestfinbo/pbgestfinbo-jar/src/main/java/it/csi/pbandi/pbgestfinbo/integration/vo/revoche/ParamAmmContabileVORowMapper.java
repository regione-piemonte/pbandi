/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.integration.vo.revoche;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ParamAmmContabileVORowMapper implements RowMapper<ParamAmmContabileVO> {
    @Override
    public ParamAmmContabileVO mapRow(ResultSet rs, int rowNum) throws SQLException {
        ParamAmmContabileVO paramAmmContabileVO = new ParamAmmContabileVO();

        paramAmmContabileVO.setIdProgetto(rs.getInt("id_progetto"));
        paramAmmContabileVO.setIdBando(rs.getInt("id_bando"));
        paramAmmContabileVO.setIdStato(rs.getLong("id_stato_revoca"));
        paramAmmContabileVO.setDtStato(rs.getDate("dt_stato_revoca"));
        paramAmmContabileVO.setIdModalitaAgevolazione(rs.getInt("id_modalita_agevolazione"));
        paramAmmContabileVO.setIdModalitaAgevolazioneRif(rs.getInt("id_modalita_agevolazione_rif"));

        return paramAmmContabileVO;
    }
}
