/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.integration.dao.impl.rowmapper;

import it.csi.pbandi.pbgestfinbo.integration.vo.PropostaErogazioneVO;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PropostaErogazioneVORowMapper implements RowMapper<PropostaErogazioneVO> {

    @Override
    public PropostaErogazioneVO mapRow(ResultSet rs, int rowNum) throws SQLException {
        PropostaErogazioneVO propostaErogazioneVO = new PropostaErogazioneVO();

        propostaErogazioneVO.setIdProposta(rs.getLong("idProposta"));
        propostaErogazioneVO.setIdProgetto(rs.getLong("idProgetto"));
        propostaErogazioneVO.setCodiceProgetto(rs.getString("codiceProgetto"));
        propostaErogazioneVO.setBeneficiario(rs.getString("denominazione"));
        propostaErogazioneVO.setImportoLordo(rs.getBigDecimal("importoLordo"));
        propostaErogazioneVO.setImportoIres(rs.getBigDecimal("importoIres"));
        propostaErogazioneVO.setImportoNetto(rs.getBigDecimal("importoNetto"));
        propostaErogazioneVO.setDataConcessione(rs.getDate("dataConcessione"));
        propostaErogazioneVO.setControlliPreErogazione(rs.getString("controlliPreErogazione"));
        propostaErogazioneVO.setFlagFinistr(rs.getString("flagFinistr"));
        if(rs.wasNull()){
            propostaErogazioneVO.setFlagFinistr("NO");
        }else{
            if(propostaErogazioneVO.getFlagFinistr().equals("S")){
                propostaErogazioneVO.setFlagFinistr("SI");
            }else{
                propostaErogazioneVO.setFlagFinistr("NO");
            }
        }

        return propostaErogazioneVO;
    }
}
