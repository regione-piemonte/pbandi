/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebcert.integration.dao.impl.rowmapper;

import it.csi.pbandi.pbwebcert.integration.vo.AllegatoVExcel20212027VO;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AllegatoVExcel20212027RowMapper implements RowMapper<AllegatoVExcel20212027VO> {
  @Override
  public AllegatoVExcel20212027VO mapRow(ResultSet resultSet, int i) throws SQLException {
    AllegatoVExcel20212027VO allegatoVExcel20212027VO = new AllegatoVExcel20212027VO();
    allegatoVExcel20212027VO.setIdAsse(resultSet.getBigDecimal("ID_ASSE"));
    allegatoVExcel20212027VO.setDescAsse(resultSet.getString("DESC_ASSE"));
    allegatoVExcel20212027VO.setImportoCertificaizoneNetto(resultSet.getBigDecimal("IMPORTO_CERTIFICAZIONE_NETTO"));
    allegatoVExcel20212027VO.setDescBreveLinea(resultSet.getString("DESC_BREVE_LINEA"));
    return allegatoVExcel20212027VO;
  }
}
