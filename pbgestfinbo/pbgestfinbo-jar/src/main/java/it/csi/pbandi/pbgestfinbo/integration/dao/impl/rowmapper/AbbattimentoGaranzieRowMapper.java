/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.integration.dao.impl.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.csi.pbandi.pbgestfinbo.integration.vo.AbbattimentoGaranzieVO;

public class AbbattimentoGaranzieRowMapper implements RowMapper<AbbattimentoGaranzieVO> {

	@Override
	public AbbattimentoGaranzieVO mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		AbbattimentoGaranzieVO abbattimento =  new AbbattimentoGaranzieVO(); 
		    
		abbattimento.setIdAbbattimGaranzie(rs.getLong("ID_ABBATTIM_GARANZIE"));
		abbattimento.setIdAttivitaGaranzie(rs.getLong("ID_ATTIVITA_GARANZIE"));
		abbattimento.setIdProgetto(rs.getLong("ID_PROGETTO"));
		abbattimento.setDataAbbattimGaranzie(rs.getDate("DT_ABBATTIM_GARANZIE")); 
		abbattimento.setImpIniziale(rs.getBigDecimal("IMP_INIZIALE"));
		abbattimento.setImpLiberato(rs.getBigDecimal("IMP_LIBERATO"));
		abbattimento.setImpNuovo(rs.getBigDecimal("IMP_NUOVO"));
		abbattimento.setNote(rs.getString("NOTE"));
		abbattimento.setDataInizioValidita(rs.getDate("DT_INIZIO_VALIDITA"));
		abbattimento.setIdUtenteIns(rs.getLong("ID_UTENTE_INS"));
		abbattimento.setDataInserimento(rs.getDate("DT_INSERIMENTO"));
		abbattimento.setDataFineValidita(rs.getDate("DT_FINE_VALIDITA"));
		abbattimento.setIdUtenteAgg(rs.getLong("ID_UTENTE_AGG"));
		abbattimento.setDataAggiornamento(rs.getDate("DT_AGGIORNAMENTO"));
		
		
		return abbattimento;
	}

}
