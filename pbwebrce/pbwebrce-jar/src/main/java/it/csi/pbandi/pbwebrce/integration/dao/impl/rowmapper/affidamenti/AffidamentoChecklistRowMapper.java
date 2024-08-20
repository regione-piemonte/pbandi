/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebrce.integration.dao.impl.rowmapper.affidamenti;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import it.csi.pbandi.pbwebrce.integration.vo.affidamenti.PbandiTAffidamentoChecklistVO;

public class AffidamentoChecklistRowMapper implements RowMapper<PbandiTAffidamentoChecklistVO> {

	@Override
	public PbandiTAffidamentoChecklistVO mapRow(ResultSet rs, int rowNum) throws SQLException {
		PbandiTAffidamentoChecklistVO cm = new PbandiTAffidamentoChecklistVO();
		cm.setIdTipoAffidamento(rs.getBigDecimal("IDTIPOAFFIDAMENTO"));
		cm.setIdTipologiaAggiudicaz(rs.getBigDecimal("IDTIPOLOGIAAGGIUDICAZ"));
		cm.setIdNorma(rs.getBigDecimal("IDNORMA"));
		cm.setIdModelloCd(rs.getBigDecimal("IDMODELLOCD"));
		cm.setRifChecklistAffidamenti(rs.getString("RIFCHECKLISTAFFIDAMENTI"));
		cm.setIdAffidamentoChecklist(rs.getBigDecimal("IDAFFIDAMENTOCHECKLIST"));
		cm.setIdTipoAppalto(rs.getBigDecimal("IDTIPOAPPALTO"));
		cm.setIdModelloCl(rs.getBigDecimal("IDMODELLOCL"));
		cm.setSopraSoglia(rs.getString("SOPRASOGLIA"));
		cm.setIdLineaDiIntervento(rs.getBigDecimal("IDLINEADIINTERVENTO"));
		return cm;
	}
}
