/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebrce.integration.dao.impl.rowmapper.affidamenti;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.csi.pbandi.pbwebrce.integration.vo.affidamenti.VariantiAffidamentoDescrizioneVO;


public class VarianteAffidamentoRowMapper implements RowMapper<VariantiAffidamentoDescrizioneVO> {

	@Override
	public VariantiAffidamentoDescrizioneVO mapRow(ResultSet rs, int rowNum) throws SQLException {
		VariantiAffidamentoDescrizioneVO cm = new VariantiAffidamentoDescrizioneVO();
		cm.setImporto(rs.getDouble("IMPORTO"));
		cm.setDescrizioneTipologiaVariante(rs.getString("DESCRIZIONETIPOLOGIAVARIANTE"));
		cm.setDtInvioVerificaAffidamento(rs.getDate("DTINVIOVERIFICAAFFIDAMENTO"));
		cm.setNote(rs.getString("NOTE"));
		cm.setIdAppalto(rs.getBigDecimal("IDAPPALTO"));
		cm.setDtInserimento(rs.getDate("DTINSERIMENTO"));
		cm.setFlgInvioVerificaAffidamento(rs.getString("FLGINVIOVERIFICAAFFIDAMENTO"));
		cm.setIdTipologiaVariante(rs.getBigDecimal("IDTIPOLOGIAVARIANTE"));
		cm.setIdVariante(rs.getBigDecimal("IDVARIANTE"));
		return cm;
	}

}
