/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebcert.integration.dao.impl.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.csi.pbandi.pbwebcert.integration.vo.PbandiTPeriodoVO;

public class PeriodoRowMapper implements RowMapper<PbandiTPeriodoVO>{

	@Override
	public PbandiTPeriodoVO mapRow(ResultSet rs, int rowNum) throws SQLException {
		PbandiTPeriodoVO cm = new PbandiTPeriodoVO();
		cm.setIdPeriodo(rs.getBigDecimal("ID_PERIODO"));
		cm.setIdTipoPeriodo(rs.getBigDecimal("ID_TIPO_PERIODO"));
		cm.setDescPeriodo(rs.getString("DESC_PERIODO"));
		cm.setDescPeriodoVisualizzata(rs.getString("DESC_PERIODO_VISUALIZZATA"));
		cm.setDtInizioValidita(rs.getDate("DT_INIZIO_VALIDITA"));
		cm.setDtFineValidita(rs.getDate("DT_FINE_VALIDITA"));
		cm.setIdUtenteIns(rs.getBigDecimal("ID_UTENTE_INS"));
		cm.setIdUtenteAgg(rs.getBigDecimal("ID_UTENTE_AGG"));
		cm.setDtInizioContabile(rs.getDate("DT_INIZIO_CONTABILE"));
		cm.setDtFineContabile(rs.getDate("DT_FINE_CONTABILE"));
		return cm;
	}

}
