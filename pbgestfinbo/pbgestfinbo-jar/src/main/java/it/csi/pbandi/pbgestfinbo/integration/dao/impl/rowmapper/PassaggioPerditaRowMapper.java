/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.integration.dao.impl.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.csi.pbandi.pbgestfinbo.integration.vo.PassaggioPerditaVO;

public class PassaggioPerditaRowMapper implements RowMapper<PassaggioPerditaVO> {

	@Override
	public PassaggioPerditaVO mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		PassaggioPerditaVO pp =  new PassaggioPerditaVO(); 
		
		pp.setIdPassaggioPerdita(rs.getLong("ID_PASSAGGIO_PERDITA"));
		pp.setIdProgetto(rs.getLong("ID_PROGETTO"));
		pp.setDataPassaggioPerdita(rs.getDate("DT_PASSAGGIO_PERDITA"));
		pp.setImpPerditaComplessiva(rs.getBigDecimal("IMP_PERDITA_COMPLESSIVA"));
		pp.setImpPerditaCapitale(rs.getBigDecimal("IMP_PERDITA_CAPITALE"));
		pp.setImpPerditaInterressi(rs.getBigDecimal("IMP_PERDITA_INTERESSI"));
		pp.setImpPerditaAgevolaz(rs.getBigDecimal("IMP_PERDITA_AGEVOLAZ"));
		pp.setImpPerditaMora(rs.getBigDecimal("IMP_PERDITA_MORA"));
		pp.setNote(rs.getString("NOTE"));
		pp.setDataInizioValidita(rs.getDate("DT_INIZIO_VALIDITA"));
		pp.setDataFineValidita(rs.getDate("DT_FINE_VALIDITA"));
		pp.setIdUtenteIns(rs.getLong("ID_UTENTE_INS"));
		pp.setIdUtenteAgg(rs.getLong("ID_UTENTE_AGG"));
		pp.setDataInserimento(rs.getDate("DT_INSERIMENTO"));
		pp.setDataAggiornamento(rs.getDate("DT_AGGIORNAMENTO"));
		
		return pp;
	}

}
