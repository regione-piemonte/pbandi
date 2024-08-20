/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.integration.vo.revoche;


import java.util.Date;
import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;


public class ImportiPropostaRevocaVORowMapperOld implements RowMapper<ImportiPropostaRevocaVOOld> {

    @Override
    public ImportiPropostaRevocaVOOld mapRow(ResultSet rs, int rowNum) throws SQLException {
    	ImportiPropostaRevocaVOOld importiPropostaRevocaVO = new ImportiPropostaRevocaVOOld();

    	importiPropostaRevocaVO.setIdGestioneRevoca(rs.getLong("id_gestione_revoca"));
    	importiPropostaRevocaVO.setIdProgetto(rs.getLong("id_progetto"));
    	importiPropostaRevocaVO.setIdBando(rs.getLong("id_bando"));
    	importiPropostaRevocaVO.setIdDomanda(rs.getLong("id_domanda"));
        
    	importiPropostaRevocaVO.setDescModalitaAgevolazione(rs.getString("desc_modalita_agevolazione"));
    	importiPropostaRevocaVO.setImportoAmmesso(rs.getInt("importo_ammesso"));
    	importiPropostaRevocaVO.setImportoConcessoContributo(rs.getInt("importo_concesso"));
    	importiPropostaRevocaVO.setImportoConcessoFinanziamento(rs.getInt("importo_concesso_finanziamento"));
    	importiPropostaRevocaVO.setImportoConcessoGaranzia(rs.getInt("importo_concesso_garanzia"));
    	importiPropostaRevocaVO.setImportoRecuperatoContributo(rs.getInt("importo_revocato_contributo"));
    	importiPropostaRevocaVO.setImportoRecuperatoFinanziamento(rs.getInt("importo_revocato_finanziamento"));
    	importiPropostaRevocaVO.setImportoRecuperatoGaranzia(rs.getInt("importo_revocato_garanzia"));
    	
                
        return importiPropostaRevocaVO;
    }
}


