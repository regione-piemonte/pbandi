/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.integration.dao.impl.rowmapper;

import it.csi.pbandi.pbgestfinbo.integration.vo.revoche.ProcedimentoRevocaMiniVO;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ProcedimentoRevocaMiniVORowMapper implements RowMapper<ProcedimentoRevocaMiniVO> {

    @Override
    public ProcedimentoRevocaMiniVO mapRow(ResultSet rs, int rowNum) throws SQLException {
        ProcedimentoRevocaMiniVO procedimentoRevocaMiniVO = new ProcedimentoRevocaMiniVO();

        procedimentoRevocaMiniVO.setIdProcedimentoRevoca(rs.getLong("idProcedimentoRevoca"));
        procedimentoRevocaMiniVO.setNumeroProcedimentoRevoca(rs.getLong("numeroProcedimentoRevoca"));
        procedimentoRevocaMiniVO.setIdSoggetto(rs.getLong("idSoggetto"));
        procedimentoRevocaMiniVO.setCodiceFiscaleSoggetto(rs.getString("codiceFiscale"));
        procedimentoRevocaMiniVO.setIdBeneficiario(rs.getLong("idBeneficiario"));
        procedimentoRevocaMiniVO.setDenominazioneBeneficiario(rs.getString("denominazioneBeneficiario"));
        procedimentoRevocaMiniVO.setIdDomanda(rs.getLong("idDomanda"));
        procedimentoRevocaMiniVO.setProgrBandoLineaIntervento(rs.getLong("progrBandoLineaIntervento"));
        procedimentoRevocaMiniVO.setNomeBandoLinea(rs.getString("nomeBandoLinea"));
        procedimentoRevocaMiniVO.setIdProgetto(rs.getLong("idProgetto"));
        procedimentoRevocaMiniVO.setTitoloProgetto(rs.getString("titoloProgetto"));
        procedimentoRevocaMiniVO.setCodiceVisualizzatoProgetto(rs.getString("codiceVisualizzatoProgetto"));
        procedimentoRevocaMiniVO.setIdCausaleBlocco(rs.getLong("idCausaleBlocco"));
        procedimentoRevocaMiniVO.setDescCausaleBlocco(rs.getString("descCausaleBlocco"));
        procedimentoRevocaMiniVO.setDataProcedimentoRevoca(rs.getDate("dataProcedimentoRevoca"));
        procedimentoRevocaMiniVO.setDataNotifica(rs.getDate("dataNotifica"));
        procedimentoRevocaMiniVO.setIdStatoRevoca(rs.getLong("idStatoRevoca"));
        procedimentoRevocaMiniVO.setDescStatoRevoca(rs.getString("descStatoRevoca"));
        procedimentoRevocaMiniVO.setDataStatoRevoca(rs.getDate("dataStatoRevoca"));
        procedimentoRevocaMiniVO.setIdAttivitaRevoca(rs.getLong("idAttivitaRevoca"));
        procedimentoRevocaMiniVO.setDescAttivitaRevoca(rs.getString("descAttivitaRevoca"));
        procedimentoRevocaMiniVO.setDataAttivitaRevoca(rs.getDate("dataAttivitaRevoca"));

        return procedimentoRevocaMiniVO;
    }
}
