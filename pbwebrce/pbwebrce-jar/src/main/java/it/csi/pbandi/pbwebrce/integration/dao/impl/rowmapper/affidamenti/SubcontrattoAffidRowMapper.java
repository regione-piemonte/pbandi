/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebrce.integration.dao.impl.rowmapper.affidamenti;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.csi.pbandi.pbwebrce.integration.vo.affidamenti.SubcontrattoAffidVO;

public class SubcontrattoAffidRowMapper implements RowMapper<SubcontrattoAffidVO> {

	@Override
	public SubcontrattoAffidVO mapRow(ResultSet rs, int rowNum) throws SQLException {
		SubcontrattoAffidVO cm = new SubcontrattoAffidVO();
		cm.setIdSubcontrattoAffidamento(rs.getBigDecimal("ID_SUBCONTRATTO_AFFIDAMENTO"));
		cm.setIdFornitore(rs.getBigDecimal("ID_FORNITORE"));
		cm.setIdAppalto(rs.getBigDecimal("ID_APPALTO"));
		cm.setIdSubcontraente(rs.getBigDecimal("ID_SUBCONTRAENTE"));
		cm.setDtSubcontratto(rs.getDate("DT_SUBCONTRATTO"));
		cm.setRiferimentoSubcontratto(rs.getString("RIFERIMENTO_SUBCONTRATTO"));
		cm.setImportoSubcontratto(rs.getBigDecimal("IMPORTO_SUBCONTRATTO"));
		cm.setDenominazioneSubcontraente(rs.getString("DENOMINAZIONE_SUBCONTRAENTE"));
		cm.setCfPivaSubcontraente(rs.getString("CF_PIVA_SUBCONTRAENTE"));
		return cm;
	}

}
