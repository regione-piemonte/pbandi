/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebbo.integration.dao.impl.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.csi.pbandi.pbwebbo.integration.vo.NaturaCipeVO;

public class NaturaCipeRowMapper implements RowMapper<NaturaCipeVO> {
    @Override
    public NaturaCipeVO mapRow(ResultSet rs, int rowNum) throws SQLException {
    	NaturaCipeVO natura = new NaturaCipeVO();

    	natura.setIdNaturaCipe(rs.getLong("ID_NATURA_CIPE"));
    	natura.setCodNaturaCipe(rs.getString("COD_NATURA_CIPE"));
    	natura.setCodNaturaCipe(rs.getString("COD_NATURA_CIPE"));
    	natura.setDescNaturaCipe(rs.getString("DESC_NATURA_CIPE"));
    	natura.setDtInizioValidita(rs.getDate("DT_INIZIO_VALIDITA"));
    	natura.setDtFineValidita(rs.getDate("DT_FINE_VALIDITA"));
    	natura.setIdTipoOperazione(rs.getLong("ID_TIPO_OPERAZIONE"));
        return natura;
    }
}
