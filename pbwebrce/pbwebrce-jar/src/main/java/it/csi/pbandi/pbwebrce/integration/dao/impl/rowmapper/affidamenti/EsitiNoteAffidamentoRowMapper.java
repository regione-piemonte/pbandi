/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebrce.integration.dao.impl.rowmapper.affidamenti;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import it.csi.pbandi.pbwebrce.pbandisrv.integration.db.vo.PbandiTEsitiNoteAffidamentVO;

public class EsitiNoteAffidamentoRowMapper implements RowMapper<PbandiTEsitiNoteAffidamentVO> {

	@Override
	public PbandiTEsitiNoteAffidamentVO mapRow(ResultSet rs, int rowNum) throws SQLException {
		PbandiTEsitiNoteAffidamentVO cm = new PbandiTEsitiNoteAffidamentVO();
		cm.setIdEsito(rs.getBigDecimal("ID_ESITO"));
		cm.setFase(rs.getBigDecimal("FASE"));
		cm.setEsito(rs.getString("ESITO"));
		cm.setIdChecklist(rs.getBigDecimal("ID_CHECKLIST"));
		cm.setDataIns(rs.getDate("DATA_INS"));
		cm.setIdUtenteIns(rs.getBigDecimal("ID_UTENTE_INS"));
		cm.setNote(rs.getString("NOTE"));
		cm.setFlagRettifica(rs.getString("FLAG_RETTIFICA"));
		return cm;
	}

}
