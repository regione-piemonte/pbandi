/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.integration.vo.revoche;


import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;


public class DataPropostaRevocaVORowMapper implements RowMapper<DataPropostaRevocaVO> {

    @Override
    public DataPropostaRevocaVO mapRow(ResultSet rs, int rowNum) throws SQLException {
    	DataPropostaRevocaVO dataPropostaRevocaVO = new DataPropostaRevocaVO();

        dataPropostaRevocaVO.setIdGestioneRevoca(rs.getLong("id_gestione_revoca"));
        dataPropostaRevocaVO.setNumeroRevoca(rs.getLong("numero_revoca"));
        dataPropostaRevocaVO.setIdProgetto(rs.getLong("id_progetto"));
        dataPropostaRevocaVO.setIdSoggetto(rs.getLong("id_soggetto"));
        dataPropostaRevocaVO.setIdDomanda(rs.getLong("id_domanda"));
        dataPropostaRevocaVO.setDtGestione(rs.getDate("dt_gestione"));
        dataPropostaRevocaVO.setDtStatoRevoca(rs.getDate("dt_stato_revoca"));
        dataPropostaRevocaVO.setCodiceFiscaleSoggetto(rs.getString("codice_fiscale_soggetto"));
        dataPropostaRevocaVO.setDenominazioneBeneficiario(rs.getString("denominazione_beneficiario"));
        dataPropostaRevocaVO.setIdBeneficiario(rs.getLong("id_beneficiario"));
        dataPropostaRevocaVO.setNomeBandoLinea(rs.getString("nome_bando_linea"));
        dataPropostaRevocaVO.setCodiceVisualizzato(rs.getString("codice_visualizzato"));
        dataPropostaRevocaVO.setTitoloProgetto(rs.getString("titolo_progetto"));
        dataPropostaRevocaVO.setCausaRevoca(rs.getString("desc_causale_blocco"));
        dataPropostaRevocaVO.setDescCategAnagrafica(rs.getString("desc_categ_anagrafica"));
        dataPropostaRevocaVO.setDescStatoRevoca(rs.getString("desc_stato_revoca"));

        return dataPropostaRevocaVO;
    }
}


