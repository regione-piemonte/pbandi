/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebcert.integration.dao.impl.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.csi.pbandi.pbwebcert.integration.vo.ScostamentoAssePropostaCertificazioneVO;


public class ScostamentoAssePropostaRowMapper implements RowMapper<ScostamentoAssePropostaCertificazioneVO> {

	@Override
	public ScostamentoAssePropostaCertificazioneVO mapRow(ResultSet rs, int rowNum) throws SQLException {
		ScostamentoAssePropostaCertificazioneVO cm = new ScostamentoAssePropostaCertificazioneVO();
		cm.setDescLineaCompleta(rs.getString("DESC_LINEA_COMPLETA"));
		cm.setDescTipoSoggFinanziatore(rs.getString("DESC_TIPO_SOGG_FINANZIATORE"));
		cm.setIdLineaDiIntervento(rs.getBigDecimal("ID_LINEA_DI_INTERVENTO"));
		cm.setIdTipoSoggFinanziat(rs.getBigDecimal("ID_TIPO_SOGG_FINANZIAT"));
		cm.setPercScostamento(rs.getBigDecimal("PERC_SCOSTAMENTO"));
		cm.setValAssScostamento(rs.getBigDecimal("VAL_ASS_SCOSTAMENTO"));
		cm.setFlagComp(rs.getString("FLAG_COMP"));
		return cm;
	}

}
