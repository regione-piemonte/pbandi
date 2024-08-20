/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.integration.dao.impl.rowmapper.search;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.csi.pbandi.pbgestfinbo.dto.profilazione.StoricoRicercaCampionamentiDTO;

public class StoricoRicerccaCampionamentiRowMapper implements RowMapper<StoricoRicercaCampionamentiDTO> {

	@Override
	public StoricoRicercaCampionamentiDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		StoricoRicercaCampionamentiDTO strc = new StoricoRicercaCampionamentiDTO(); 
	
		strc.setIdCampione(rs.getBigDecimal("ID_CAMPIONAMENTO"));
		strc.setDescrizione(rs.getString("DESC_CAMPIONAMENTO"));
		strc.setDescTipologiaCamp(rs.getString("DESC_TIPO_CAMP"));
		strc.setNome(rs.getString("nome"));
		strc.setCognome(rs.getString("cognome"));
		strc.setNumProgettiSelezionati(rs.getBigDecimal("NUM_PROGETTI_SEL"));
		strc.setImpValidato(rs.getBigDecimal("IMPORTO_VALIDATO"));
		strc.setDataCampionamento(rs.getDate("DT_CAMPIONAMENTO"));
		strc.setPercEstratta(rs.getLong("PERC_ESTRATTA"));
		strc.setImpValPercEstratta(rs.getBigDecimal("IMP_VAL_PERC_ESTRATTA"));
		strc.setDataInizioValidita(rs.getDate("DT_INIZIO_VALIDITA"));
		strc.setDataFineValidita(rs.getDate("DT_FINE_VALIDITA"));
		strc.setIdUtenteIns(rs.getLong("ID_UTENTE_INS"));
		strc.setIdUtenteAgg(rs.getLong("ID_UTENTE_AGG"));
		strc.setIdFaseCamp(rs.getLong("ID_FASE_CAMP"));
		
		return strc;
	}

}
