/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.integration.vo.revoche;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class NotaRevocaVORowMapper implements RowMapper<NotaRevocaVO> {

    @Override
    public NotaRevocaVO mapRow(ResultSet rs, int rowNum) throws SQLException {
    	NotaRevocaVO notaRevocaVO = new NotaRevocaVO();
    	notaRevocaVO.setNota(rs.getString("note"));  	
     

        return notaRevocaVO;
    }

}
