/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.integration.dao.impl.rowmapper;

import it.csi.pbandi.pbgestfinbo.integration.vo.revoche.ProvvedimentoRevocaMiniVO;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ProvvedimentoRevocaMiniVORowMapper implements RowMapper<ProvvedimentoRevocaMiniVO> {
    @Override
    public ProvvedimentoRevocaMiniVO mapRow(ResultSet rs, int rowNum) throws SQLException {
        ProvvedimentoRevocaMiniVO provvedimentoRevocaMiniVO = new ProvvedimentoRevocaMiniVO();

        provvedimentoRevocaMiniVO.setIdProvvedimentoRevoca(rs.getLong("idProvvedimentoRevoca"));
        provvedimentoRevocaMiniVO.setNumeroProvvedimentoRevoca(rs.getLong("numeroProvvedimentoRevoca"));
        provvedimentoRevocaMiniVO.setIdSoggetto(rs.getLong("idSoggetto"));
        provvedimentoRevocaMiniVO.setCodiceFiscaleSoggetto(rs.getString("codiceFiscale"));
        provvedimentoRevocaMiniVO.setIdBeneficiario(rs.getLong("idBeneficiario"));
        provvedimentoRevocaMiniVO.setDenominazioneBeneficiario(rs.getString("denominazioneBeneficiario"));
        provvedimentoRevocaMiniVO.setIdDomanda(rs.getLong("idDomanda"));
        provvedimentoRevocaMiniVO.setProgrBandoLineaIntervento(rs.getLong("progrBandoLineaIntervento"));
        provvedimentoRevocaMiniVO.setNomeBandoLinea(rs.getString("nomeBandoLinea"));
        provvedimentoRevocaMiniVO.setIdProgetto(rs.getLong("idProgetto"));
        provvedimentoRevocaMiniVO.setTitoloProgetto(rs.getString("titoloProgetto"));
        provvedimentoRevocaMiniVO.setCodiceVisualizzatoProgetto(rs.getString("codiceVisualizzatoProgetto"));
        provvedimentoRevocaMiniVO.setIdCausaleBlocco(rs.getLong("idCausaleBlocco"));
        provvedimentoRevocaMiniVO.setDescCausaleBlocco(rs.getString("descCausaleBlocco"));
        provvedimentoRevocaMiniVO.setDataProvvedimentoRevoca(rs.getDate("dataProvvedimentoRevoca"));
        provvedimentoRevocaMiniVO.setDataNotifica(rs.getDate("dataNotifica"));
        provvedimentoRevocaMiniVO.setIdStatoRevoca(rs.getLong("idStatoRevoca"));
        provvedimentoRevocaMiniVO.setDescStatoRevoca(rs.getString("descStatoRevoca"));
        provvedimentoRevocaMiniVO.setDataStatoRevoca(rs.getDate("dataStatoRevoca"));
        provvedimentoRevocaMiniVO.setIdAttivitaRevoca(rs.getLong("idAttivitaRevoca"));
        provvedimentoRevocaMiniVO.setDescAttivitaRevoca(rs.getString("descAttivitaRevoca"));
        provvedimentoRevocaMiniVO.setDataAttivitaRevoca(rs.getDate("dataAttivitaRevoca"));

        return provvedimentoRevocaMiniVO;
    }
}
