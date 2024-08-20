/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.integration.dao.impl.rowmapper;

import it.csi.pbandi.pbgestfinbo.integration.vo.DettaglioDistintaVO;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DettaglioDistintaVORowMapper implements RowMapper<DettaglioDistintaVO> {
    @Override
    public DettaglioDistintaVO mapRow(ResultSet rs, int rowNum) throws SQLException {
        DettaglioDistintaVO dettaglioDistintaVO = new DettaglioDistintaVO();

        dettaglioDistintaVO.setBozza(
                rs.getLong("idStatoDistinta") == 2L || //RESPINTA REFERENTE
                rs.getLong("idStatoDistinta") == 3L || //RESPINTA RESPONSABILE
                rs.getLong("idStatoDistinta") == 7L || //IN BOZZA
                rs.getLong("idStatoDistinta") == 9L    //RESPINTA DIRIGENTE
        );

        dettaglioDistintaVO.setIdBando(rs.getLong("idBando"));
        dettaglioDistintaVO.setTitoloBando(rs.getString("titoloBando"));

        dettaglioDistintaVO.setIdModalitaAgevolazione(rs.getLong("idModalitaAgevolazione"));
        dettaglioDistintaVO.setIdModalitaAgevolazioneRif(rs.getLong("idModalitaAgevolazioneRif"));
        dettaglioDistintaVO.setDescModalitaAgevolazione(rs.getString("descrModalitaAgevolaz"));

        dettaglioDistintaVO.setDescDistinta(rs.getString("descrizioneDistinta"));

        return dettaglioDistintaVO;
    }
}
