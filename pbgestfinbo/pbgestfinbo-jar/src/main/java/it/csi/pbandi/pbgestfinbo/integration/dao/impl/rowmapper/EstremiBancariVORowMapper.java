/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.integration.dao.impl.rowmapper;

import it.csi.pbandi.pbgestfinbo.integration.vo.EstremiBancariVO;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class EstremiBancariVORowMapper implements RowMapper<EstremiBancariVO> {
    @Override
    public EstremiBancariVO mapRow(ResultSet rs, int rowNum) throws SQLException {
        EstremiBancariVO estremiBancariVO = new EstremiBancariVO();

        estremiBancariVO.setIdEstremiBancari(rs.getLong("idEstremiBancari"));
        estremiBancariVO.setIban(rs.getString("iban"));
        estremiBancariVO.setCodiceFondoFinpis(rs.getString("codiceFondoFinpis"));

        return estremiBancariVO;
    }
}
