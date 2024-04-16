/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbworkspace.integration.dao.impl.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import it.csi.pbandi.pbworkspace.integration.vo.PersonaFisicaVO;

public class PersonaFisicaRowMapper implements RowMapper<PersonaFisicaVO> {

	@Override
	public PersonaFisicaVO mapRow(ResultSet rs, int rowNum) throws SQLException {
		PersonaFisicaVO cm = new PersonaFisicaVO();
		
		cm.setCodiceFiscale(rs.getString("codice_fiscale"));
		cm.setCognome(rs.getString("cognome"));
		cm.setDtNascita(rs.getDate("dt_nascita"));
		cm.setIdPersonaFisica(rs.getBigDecimal("id_persona_fisica"));
		cm.setIdSoggetto(rs.getBigDecimal("id_soggetto"));
		cm.setNome(rs.getString("nome"));
		return cm;
	}

}
