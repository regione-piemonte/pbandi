/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.integration.dao.impl.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.csi.pbandi.pbgestfinbo.integration.vo.DettaglioBeneficiarioCreditiVO;
import it.csi.pbandi.pbgestfinbo.integration.vo.RevocaAmministrativaCreditiVO;

public class RevocaAmministrativaCreditiVORowMapper implements RowMapper<RevocaAmministrativaCreditiVO> {

	@Override
	public RevocaAmministrativaCreditiVO mapRow(ResultSet rs, int rowNum) throws SQLException {
		RevocaAmministrativaCreditiVO bc = new RevocaAmministrativaCreditiVO();
		
		bc.setIdProgetto(rs.getString("ID_PROGETTO"));
//		bc.setIdRevoca(rs.getString("ID_REVOCA"));
		bc.setIdCausaleBlocco(rs.getString("ID_CAUSALE_BLOCCO"));
		bc.setDescCausaleBlocco(rs.getString("DESC_CAUSALE_BLOCCO"));
//		bc.setIdCateAnag(rs.getString("ID_CATEG_ANAGRAFICA"));
//		bc.setDescCateAnag(rs.getString("DESC_CATEG_ANAGRAFICA"));
//		bc.setDataGestione(rs.getDate("DT_GESTIONE"));
//		bc.setDataStatoRevoca(rs.getDate("DT_STATO_REVOCA"));
//		bc.setDescStatoRevoca(rs.getString("DESC_STATO_REVOCA"));
//		bc.setIdMotivoRevoca(rs.getString("ID_MOTIVO_REVOCA"));
//		bc.setDescMotivoRevoca(rs.getString("DESC_MOTIVO_REVOCA"));
//		bc.setImpAmmesso(rs.getBigDecimal("IMPORTO_AMESSO"));
//		bc.setImpErogato(rs.getBigDecimal("TOTALE_EROGATO"));
//		bc.setImpRecupero(rs.getBigDecimal("IMPORTO_RECUPERO"));
//		bc.setFlagOrdineRecupero(rs.getString("FLAG_ORDINE_RECUPERO"));
//		bc.setImpRevoca(rs.getBigDecimal("IMPORTO_REVOCA"));
//		bc.setImpBando(rs.getBigDecimal("IMPORTO_BANDO"));
//		bc.setNumeroRevoca(rs.getLong("NUMERO_REVOCA"));
		
		bc.setIdGestioneRevoca(rs.getLong("ID_GESTIONE_REVOCA"));
		bc.setNumeroRevoca(rs.getLong("NUMERO_REVOCA"));
		bc.setDataProvedimentoRevoca(rs.getDate("DT_GESTIONE"));
		bc.setQuotaCapitale(rs.getBigDecimal("QUOTA_CAPITALE"));
		bc.setDescStatoRevoca(rs.getString("DESC_STATO_REVOCA"));
		bc.setDataNotificaProvRevoca(rs.getDate("DT_NOTIFICA"));
		bc.setImportoErogatoFinp(rs.getBigDecimal("importo_erogato_finp"));
	    bc.setImportoTotaleRevocato(rs.getBigDecimal("importo_totale_revocato"));
	    bc.setImpAgevolazione(rs.getBigDecimal("importo_agevolazione"));
	    bc.setImportoConcesso(rs.getBigDecimal("importo_concesso"));
	    bc.setDescCausa(rs.getString("DESC_CAUSALE_BLOCCO"));
	    
	    String importoConcesso = null, importoTotaleRevocato = null; 
	    importoConcesso = rs.getString("importo_concesso");
	    importoTotaleRevocato = rs.getString("importo_totale_revocato");
	    
	    if(!importoConcesso.equals(importoTotaleRevocato)) {
	    	bc.setTipoRevoca("Parziale");
	    } else {
	    	bc.setTipoRevoca("Totale");
	    }
		return bc;
	}

}
