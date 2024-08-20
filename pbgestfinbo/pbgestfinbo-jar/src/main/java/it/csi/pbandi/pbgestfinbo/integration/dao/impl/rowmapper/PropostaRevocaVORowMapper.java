/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.integration.dao.impl.rowmapper;

import it.csi.pbandi.pbgestfinbo.integration.vo.revoche.PropostaRevocaVO;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PropostaRevocaVORowMapper implements RowMapper<PropostaRevocaVO> {
    @Override
    public PropostaRevocaVO mapRow(ResultSet rs, int rowNum) throws SQLException {
        PropostaRevocaVO propostaRevocaVO = new PropostaRevocaVO();

        propostaRevocaVO.setIdPropostaRevoca(rs.getLong("idPropostaRevoca"));
        propostaRevocaVO.setNumeroPropostaRevoca(rs.getLong("numeroPropostaRevoca"));
        propostaRevocaVO.setIdSoggetto(rs.getLong("idSoggetto"));
        propostaRevocaVO.setCodiceFiscaleSoggetto(rs.getString("codiceFiscaleSoggetto"));
        propostaRevocaVO.setIdBeneficiario(rs.getLong("idBeneficiario"));
        propostaRevocaVO.setDenominazioneBeneficiario(rs.getString("denominazioneBeneficiario"));
        propostaRevocaVO.setIdDomanda(rs.getLong("idDomanda"));
        propostaRevocaVO.setProgrBandoLineaIntervento(rs.getLong("progrBandoLineaIntervento"));
        propostaRevocaVO.setNomeBandoLinea(rs.getString("nomeBandoLinea"));
        propostaRevocaVO.setTitoloProgetto(rs.getString("titoloProgetto"));
        propostaRevocaVO.setCodiceVisualizzatoProgetto(rs.getString("codiceVisualizzatoProgetto"));
        propostaRevocaVO.setIdCausaleBlocco(rs.getLong("idCausaleBlocco"));
        propostaRevocaVO.setDescCausaleBlocco(rs.getString("descCausaleBlocco"));
        propostaRevocaVO.setDataPropostaRevoca(rs.getDate("dataPropostaRevoca"));
        propostaRevocaVO.setIdStatoRevoca(rs.getLong("idStatoRevoca"));
        propostaRevocaVO.setDescStatoRevoca(rs.getString("descStatoRevoca"));
        propostaRevocaVO.setDataStatoRevoca(rs.getDate("dataStatoRevoca"));

        return propostaRevocaVO;
    }
}
