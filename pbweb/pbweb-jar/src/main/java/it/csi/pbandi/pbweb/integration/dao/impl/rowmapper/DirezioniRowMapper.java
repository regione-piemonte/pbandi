/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweb.integration.dao.impl.rowmapper;

import it.csi.pbandi.pbweb.dto.DirezioneDTO;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DirezioniRowMapper implements RowMapper<DirezioneDTO> {

  @Override
  public DirezioneDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
    DirezioneDTO direzione = new DirezioneDTO();
    direzione.setIdDirezione(rs.getLong("ID_ENTE_COMPETENZA"));
    direzione.setDescBreveDirezione(rs.getString("DESC_ENTE"));
    return direzione;
  }
}
