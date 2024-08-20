/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.integration.dao.impl.rowmapper;

import it.csi.pbandi.pbgestfinbo.integration.vo.RicercaDistintaPropErogVO;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RicercaDistintaPropErogVORowMapper implements RowMapper<RicercaDistintaPropErogVO> {
    @Override
    public RicercaDistintaPropErogVO mapRow(ResultSet rs, int rowNum) throws SQLException {
        RicercaDistintaPropErogVO ricercaDistintaPropErogVO = new RicercaDistintaPropErogVO();

        ricercaDistintaPropErogVO.setIdDistinta(rs.getLong("idDistinta"));
        ricercaDistintaPropErogVO.setDescrizioneDistinta(rs.getString("descrizioneDistinta"));
        ricercaDistintaPropErogVO.setIdTipoDistinta(rs.getLong("idTipoDistinta"));
        ricercaDistintaPropErogVO.setDescTipoDistinta(rs.getString("descTipoDistinta"));
        ricercaDistintaPropErogVO.setDescBreveTipoDistinta(rs.getString("descBreveTipoDistinta"));
        ricercaDistintaPropErogVO.setUtenteCreazione(rs.getString("utenteCreazione"));
        ricercaDistintaPropErogVO.setIdStatoDistinta(rs.getLong("idStatoDistinta"));
        ricercaDistintaPropErogVO.setDescStatoDistinta(rs.getString("descStatoDistinta"));
        ricercaDistintaPropErogVO.setDataCreazioneDistinta(rs.getDate("dataCreazioneDistinta"));

        return ricercaDistintaPropErogVO;
    }
}
