/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.integration.dao.impl.rowmapper.affidamenti;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.csi.pbandi.pbgestfinbo.integration.vo.affidamenti.ProgettoCampioneVO;

public class ProgettoCampioneRowmapper implements RowMapper<ProgettoCampioneVO> {

	@Override
	public ProgettoCampioneVO mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		ProgettoCampioneVO prog = new ProgettoCampioneVO();
		
		prog.setCodiceVisualizzatoProgetto(rs.getString("CODICE_VISUALIZZATO"));
		prog.setIdProgetto(rs.getLong("ID_PROGETTO"));
		prog.setIdSoggetto(rs.getLong("ID_SOGGETTO"));
		prog.setProgrSoggProg(rs.getLong("PROGR_SOGGETTO_PROGETTO"));
		prog.setNomeBandoLinea(rs.getString("DESC_LINEA"));
		
		String denomEntegiur = null; 
		
		denomEntegiur= rs.getString("DENOMINAZIONE_ENTE_GIURIDICO"); 
		if(denomEntegiur!=null) {
			prog.setDenominazioneBeneficiario(denomEntegiur);
		} else {
			prog.setDenominazioneBeneficiario(rs.getString("COGNOME ") + " "+ rs.getString("NOME"));
		}
		
		return prog;
	}

}
