/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweb.integration.dao.impl.rowmapper;

import it.csi.pbandi.pbweb.dto.SettoreDTO;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SettoriRowMapper implements RowMapper<SettoreDTO> {

  @Override
  public SettoreDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
    SettoreDTO sezione = new SettoreDTO();
    sezione.setIdSettore(rs.getLong("ID_SETTORE_ENTE"));
    sezione.setDescBreveDirezione(rs.getString("DESC_BREVE_SETTORE"));
    sezione.setDescrizione(rs.getString("DESC_SETTORE"));
    return sezione;
  }
}
