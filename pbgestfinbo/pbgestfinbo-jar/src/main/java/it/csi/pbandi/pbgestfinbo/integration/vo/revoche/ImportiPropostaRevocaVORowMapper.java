/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.integration.vo.revoche;


import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;


public class ImportiPropostaRevocaVORowMapper implements RowMapper<ImportiPropostaRevocaVO> {

    @Override
    public ImportiPropostaRevocaVO mapRow(ResultSet rs, int rowNum) throws SQLException {
    	ImportiPropostaRevocaVO importiPropostaRevocaVO = new ImportiPropostaRevocaVO();

    	importiPropostaRevocaVO.setIdGestioneRevoca(rs.getInt("id_gestione_revoca"));
    	importiPropostaRevocaVO.setIdProgetto(rs.getInt("id_progetto"));
    	importiPropostaRevocaVO.setIdDomanda(rs.getInt("id_domanda"));
		importiPropostaRevocaVO.setIdBando(rs.getInt("id_bando"));

		importiPropostaRevocaVO.setIdStatoRevoca(rs.getInt("id_stato_revoca"));
		importiPropostaRevocaVO.setDtStatoRevoca(rs.getDate("dt_stato_revoca"));

		importiPropostaRevocaVO.setIdModalitaAgevolazione(rs.getInt("id_modalita_agevolazione"));
		importiPropostaRevocaVO.setIdModalitaAgevolazioneRif(rs.getInt("id_modalita_agevolazione_rif"));
    	importiPropostaRevocaVO.setDescModalitaAgevolazione(rs.getString("desc_modalita_agevolazione"));

    	importiPropostaRevocaVO.setImportoConcesso(rs.getDouble("importo_concesso"));
    	importiPropostaRevocaVO.setImportoRevocato(rs.getDouble("importo_revocato"));
    	importiPropostaRevocaVO.setImportoAmmessoIniziale(rs.getDouble("importo_ammesso_iniziale"));

        return importiPropostaRevocaVO;
    }
}


