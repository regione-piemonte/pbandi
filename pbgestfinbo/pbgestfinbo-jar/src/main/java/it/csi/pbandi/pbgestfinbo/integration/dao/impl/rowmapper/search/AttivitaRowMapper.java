/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.integration.dao.impl.rowmapper.search;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.csi.pbandi.pbgestfinbo.dto.profilazione.AttivitaDTO;

public class AttivitaRowMapper implements RowMapper<AttivitaDTO> {

	@Override
	public AttivitaDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		AttivitaDTO attivita = new AttivitaDTO(); 
		
		attivita.setIdAttivita(rs.getLong("ID_ATTIVITA_MONIT_CRED"));
		attivita.setDescAttivita(rs.getString("DESC_ATT_MONIT_CRED"));
		
		return attivita;
	}

}
