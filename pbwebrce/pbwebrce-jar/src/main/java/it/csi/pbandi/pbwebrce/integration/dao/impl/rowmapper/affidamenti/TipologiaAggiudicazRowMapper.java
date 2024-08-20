/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebrce.integration.dao.impl.rowmapper.affidamenti;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.csi.pbandi.pbwebrce.pbandisrv.integration.db.vo.PbandiDTipologiaAggiudicazVO;


public class TipologiaAggiudicazRowMapper implements RowMapper<PbandiDTipologiaAggiudicazVO> {

	@Override
	public PbandiDTipologiaAggiudicazVO mapRow(ResultSet rs, int rowNum) throws SQLException {
		PbandiDTipologiaAggiudicazVO cm = new PbandiDTipologiaAggiudicazVO();
		cm.setIdTipologiaAggiudicaz(rs.getBigDecimal("ID_TIPOLOGIA_AGGIUDICAZ"));
		cm.setCodIgrueT47(rs.getBigDecimal("COD_IGRUE_T47"));
		cm.setDescTipologiaAggiudicazione(rs.getString("DESC_TIPOLOGIA_AGGIUDICAZIONE"));
		cm.setDtInizioValidita(rs.getDate("DT_INIZIO_VALIDITA"));
		cm.setDtFineValidita(rs.getDate("DT_FINE_VALIDITA"));
		return cm;
	}


}
