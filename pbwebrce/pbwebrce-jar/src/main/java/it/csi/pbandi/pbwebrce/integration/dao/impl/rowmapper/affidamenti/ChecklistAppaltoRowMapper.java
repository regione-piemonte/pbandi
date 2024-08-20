/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebrce.integration.dao.impl.rowmapper.affidamenti;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.csi.pbandi.pbwebrce.integration.vo.affidamenti.CheckListAppaltoVO;


public class ChecklistAppaltoRowMapper implements RowMapper<CheckListAppaltoVO> {

	@Override
	public CheckListAppaltoVO mapRow(ResultSet rs, int rowNum) throws SQLException {
		CheckListAppaltoVO cm = new CheckListAppaltoVO();
		cm.setDtControllo(rs.getDate("DT_CONTROLLO"));
		cm.setSoggettoControllore(rs.getString("SOGGETTO_CONTROLLORE"));
		cm.setFlagIrregolarita(rs.getString("FLAG_IRREGOLARITA"));
		cm.setIdChecklist(rs.getBigDecimal("ID_CHECKLIST"));
		cm.setIdDichiarazioneSpesa(rs.getBigDecimal("ID_DICHIARAZIONE_SPESA"));
		
		return cm;
	}



}
