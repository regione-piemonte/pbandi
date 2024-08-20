/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.integration.vo.revoche;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class GestioneProrogaVORowMapper implements RowMapper<GestioneProrogaVO> {
    @Override
    public GestioneProrogaVO mapRow(ResultSet rs, int rowNum) throws SQLException {
        GestioneProrogaVO gestioneProrogaVO = new GestioneProrogaVO();

        gestioneProrogaVO.setIdProroga(rs.getLong("idProroga"));
        gestioneProrogaVO.setIdStatoProroga(rs.getLong("idStatoProroga"));
        gestioneProrogaVO.setDescStatoProroga(rs.getString("descStatoProroga"));
        gestioneProrogaVO.setDtRichiestaProroga(rs.getDate("dtRichiestaProroga"));
        gestioneProrogaVO.setDtEsitoRichiestaProroga(rs.getDate("dtEsitoRichiestaProroga"));
        gestioneProrogaVO.setNumGiorniRichiestiProroga(rs.getBigDecimal("numGiorniRichiestiProroga"));
        gestioneProrogaVO.setNumGiorniApprovatiProroga(rs.getBigDecimal("numGiorniApprovatiProroga"));
        gestioneProrogaVO.setMotivazioneProroga(rs.getString("motivazioneProroga"));
        gestioneProrogaVO.setDtInserimentoProroga(rs.getDate("dtInserimentoProroga"));

        return gestioneProrogaVO;
    }
}
