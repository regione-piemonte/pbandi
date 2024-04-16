/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbworkspace.integration.dao.impl.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.csi.pbandi.pbworkspace.dto.WidgetDTO;
import it.csi.pbandi.pbworkspace.integration.vo.BandoVO;

public class WidgetDTORowMapper implements RowMapper<WidgetDTO> {

	@Override
	public WidgetDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
		WidgetDTO cm = new WidgetDTO();

		cm.setIdWidget(rs.getLong("ID_WIDGET"));
		cm.setDescBreveWIdget(rs.getString("DESC_BREVE_WIDGET"));
		cm.setDescWidget(rs.getString("DESC_WIDGET"));
		cm.setTitoloWidget(rs.getString("TITOLO_WIDGET"));
		if (rs.getString("FLAG_MODIFICA") != null && rs.getString("FLAG_MODIFICA").equals("S")) {
			cm.setFlagModifica(Boolean.TRUE);
		} else {
			cm.setFlagModifica(Boolean.FALSE);
		}

		if (rs.getString("FLAG_WIDGET_ATTIVO") != null && rs.getString("FLAG_WIDGET_ATTIVO").equals("S")) {
			cm.setFlagWidgetAttivo(Boolean.TRUE);
		} else {
			cm.setFlagWidgetAttivo(Boolean.FALSE);
		}

		return cm;
	}

}
