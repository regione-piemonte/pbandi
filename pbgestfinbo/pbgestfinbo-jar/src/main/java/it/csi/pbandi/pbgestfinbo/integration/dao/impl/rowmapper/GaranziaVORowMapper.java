/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.integration.dao.impl.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.csi.pbandi.pbgestfinbo.integration.vo.GaranziaVO;

public class GaranziaVORowMapper implements RowMapper<GaranziaVO> {

	@Override
	public GaranziaVO mapRow(ResultSet rs, int rowNum) throws SQLException {
		GaranziaVO gar = new GaranziaVO();
		
		gar.setIdSoggetto(rs.getString("ID_SOGGETTO"));
		gar.setIdProgetto(rs.getLong("ID_PROGETTO"));
		gar.setProgrSoggettoProgetto(rs.getString("PROGR_SOGGETTO_PROGETTO"));
		
		gar.setDescrizioneBando(rs.getString("TITOLO_BANDO"));
		gar.setCodiceProgetto(rs.getString("CODICE_VISUALIZZATO"));
		gar.setCodiceFiscale(rs.getString("CODICE_FISCALE_SOGGETTO"));
		gar.setNag(rs.getString("ID_SOGGETTO"));
		gar.setPartitaIva(rs.getString("PARTITA_IVA"));
		gar.setDenominazioneCognomeNome(rs.getString("DENOMINAZIONE_ENTE_GIURIDICO"));
		gar.setDenominazioneBanca(rs.getString("DESC_BANCA"));
		
		return gar;
	}

}
