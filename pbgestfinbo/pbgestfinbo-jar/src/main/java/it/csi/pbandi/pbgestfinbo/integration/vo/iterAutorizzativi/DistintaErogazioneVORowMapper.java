/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.integration.vo.iterAutorizzativi;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DistintaErogazioneVORowMapper implements RowMapper<DistintaErogazioneVO> {
    @Override
    public DistintaErogazioneVO mapRow(ResultSet rs, int rowNum) throws SQLException {
        DistintaErogazioneVO distintaErogazioneVO = new DistintaErogazioneVO();


        distintaErogazioneVO.setIdDistinta(rs.getInt("idDistinta"));
        distintaErogazioneVO.setIdDistintaDett(rs.getInt("idDistintaDett"));
        distintaErogazioneVO.setRigaDistinta(rs.getInt("rigaDistinta"));
        distintaErogazioneVO.setDataErogazione(rs.getDate("dataErogazione"));
        distintaErogazioneVO.setImporto(rs.getLong("importo"));

        distintaErogazioneVO.setIdProgetto(rs.getInt("idProgetto"));
        distintaErogazioneVO.setIdBando(rs.getInt("idBando"));

        distintaErogazioneVO.setIdModalitaAgevolazione(rs.getInt("idModalitaAgevolazione"));
        distintaErogazioneVO.setIdModalitaAgevolazioneRif(rs.getInt("idModalitaAgevolazioneRif"));
        if(distintaErogazioneVO.getIdModalitaAgevolazioneRif() == 5 || distintaErogazioneVO.getIdModalitaAgevolazioneRif() == 10) {
            distintaErogazioneVO.setNumRate(rs.getInt("numeroRate"));
            distintaErogazioneVO.setFreqRate(rs.getInt("frequenzaRate"));
            distintaErogazioneVO.setTipoPeriodo(rs.getString("tipoPeriodo"));
            distintaErogazioneVO.setPercentualeInteressi(rs.getDouble("percentualeInteressi"));
            distintaErogazioneVO.setPreammortamento(rs.getInt("preammortamento"));
        }
        if(distintaErogazioneVO.getIdModalitaAgevolazioneRif() == 1 || distintaErogazioneVO.getIdModalitaAgevolazioneRif() == 5) {
            distintaErogazioneVO.setImportoIres(rs.getLong("importoIres"));
        }
        
        distintaErogazioneVO.setIdCausaleErogazione(rs.getInt("idCausaleErogazione"));

        return distintaErogazioneVO;
    }
}
