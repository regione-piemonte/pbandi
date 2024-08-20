/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.integration.vo.iterAutorizzativi;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DistintaEscussioniVORowMapper implements RowMapper<DistintaEscussioniVO> {
    @Override
    public DistintaEscussioniVO mapRow(ResultSet rs, int rowNum) throws SQLException {
        DistintaEscussioniVO distintaEscussioniVO = new DistintaEscussioniVO();

        distintaEscussioniVO.setIdProgetto(rs.getInt("id_progetto"));
        distintaEscussioniVO.setIdBando(rs.getInt("id_bando"));
        distintaEscussioniVO.setIdDistinta(rs.getInt("id_distinta"));
        distintaEscussioniVO.setIdDistintaDett(rs.getInt("id_distinta_dett"));
        distintaEscussioniVO.setRigaDistinta(rs.getInt("riga_distinta"));
        distintaEscussioniVO.setIbanBonifico(rs.getString("iban_bonifico"));
        distintaEscussioniVO.setDtInizioValidita(rs.getDate("dt_inizio_validita"));
        distintaEscussioniVO.setImportoApprovato(rs.getDouble("imp_approvato"));
        distintaEscussioniVO.setIdTipoEscussione(rs.getInt("id_tipo_escussione"));
        distintaEscussioniVO.setCausaleBonifico(rs.getString("causale_bonifico"));
        distintaEscussioniVO.setIdModalitaAgevolazione(rs.getInt("id_modalita_agevolazione"));
        distintaEscussioniVO.setIdModalitaAgevolazioneRif(rs.getInt("id_modalita_agevolazione_rif"));

        return distintaEscussioniVO;
    }
}
