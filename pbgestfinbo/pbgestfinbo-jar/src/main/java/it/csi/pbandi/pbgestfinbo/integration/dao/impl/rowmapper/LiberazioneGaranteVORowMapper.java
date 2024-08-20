/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.integration.dao.impl.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.csi.pbandi.pbgestfinbo.integration.vo.LiberazioneGaranteVO;

public class LiberazioneGaranteVORowMapper implements RowMapper<LiberazioneGaranteVO> {

	@Override
	public LiberazioneGaranteVO mapRow(ResultSet rs, int rowNum) throws SQLException {
		LiberazioneGaranteVO bc = new LiberazioneGaranteVO();
		
		
		bc.setIdLibGar(rs.getBigDecimal("ID_LIBERAZ_GARANTE"));
		bc.setIdModalitaAgevolazione(rs.getBigDecimal("ID_MODALITA_AGEVOLAZIONE"));
		bc.setGaranteLiberato(rs.getString("DENOM_GARANTE_LIBERATO"));
		bc.setDataLiberazione(rs.getDate("DT_LIBERAZ_GARANTE"));
		bc.setUtenteModifica(rs.getString("COGNOME_NOME"));
		bc.setImportoLiberazione(rs.getBigDecimal("IMP_LIBERAZIONE"));
		bc.setNote(rs.getString("NOTE"));
		bc.setStory_dataAgg(rs.getDate("DT_AGGIORNAMENTO"));
		bc.setStory_dataIns(rs.getDate("DT_INSERIMENTO"));
		bc.setFineValidita(rs.getString("DT_FINE_VALIDITA"));
		
		return bc;
	}

}
