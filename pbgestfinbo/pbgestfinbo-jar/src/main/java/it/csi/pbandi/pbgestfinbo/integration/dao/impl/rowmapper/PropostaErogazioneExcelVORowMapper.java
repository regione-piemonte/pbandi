/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.integration.dao.impl.rowmapper;

import it.csi.pbandi.pbgestfinbo.integration.vo.PropostaErogazioneExcelVO;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PropostaErogazioneExcelVORowMapper implements RowMapper<PropostaErogazioneExcelVO> {
    @Override
    public PropostaErogazioneExcelVO mapRow(ResultSet rs, int rowNum) throws SQLException {
        PropostaErogazioneExcelVO propostaErogazioneExcelVO = new PropostaErogazioneExcelVO();

        propostaErogazioneExcelVO.setTitoloProgetto(rs.getString("titoloProgetto"));
        propostaErogazioneExcelVO.setCodiceVisualizzatoProgetto(rs.getString("codiceVisualizzatoProgetto"));
        propostaErogazioneExcelVO.setDenominazioneBeneficiario(rs.getString("denominazioneBeneficiario"));
        propostaErogazioneExcelVO.setCodiceFiscale(rs.getString("codiceFiscale"));
        propostaErogazioneExcelVO.setPartitaIva(rs.getString("partitaIva"));
        propostaErogazioneExcelVO.setIban(rs.getString("iban"));
        propostaErogazioneExcelVO.setImportoLordo(rs.getBigDecimal("importoLordo"));
        propostaErogazioneExcelVO.setImportoIres(rs.getBigDecimal("importoIres"));
        propostaErogazioneExcelVO.setImportoNetto(rs.getBigDecimal("importoNetto"));

        return propostaErogazioneExcelVO;
    }
}
