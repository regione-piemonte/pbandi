/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.integration.dao.impl.rowmapper.search;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.csi.pbandi.pbgestfinbo.integration.vo.search.ElencoDomandeProgettiVO;

public class ElencoRowMapper implements RowMapper<ElencoDomandeProgettiVO>{

	@Override
	public ElencoDomandeProgettiVO mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		ElencoDomandeProgettiVO cm = new ElencoDomandeProgettiVO();
		
		cm.setNumeroDomanda(rs.getString("NUMERO_DOMANDA"));
		cm.setCodiceBando(rs.getLong("PROGR_BANDO_LINEA_INTERVENTO"));;
		cm.setDescStatoDomanda(rs.getString("DESC_STATO_DOMANDA"));;
		cm.setDescProgetto(rs.getString("CODICE_VISUALIZZATO"));;
		cm.setDescStatoProgetto(rs.getString("DESC_STATO_PROGETTO"));;
		cm.setLegaleRappresentante(rs.getString("NOME") + " " + rs.getString("COGNOME"));
		cm.setIdDomanda(rs.getLong("ID_DOMANDA"));
		cm.setIdProgetto(rs.getLong("ID_PROGETTO"));
		return cm;
	}

}
