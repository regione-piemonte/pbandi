/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebrce.integration.dao.impl.rowmapper.affidamenti;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.csi.pbandi.pbwebrce.pbandisrv.integration.db.vo.PbandiTProceduraAggiudicazVO;

public class ProceduraAggiudicazRowMapper implements RowMapper<PbandiTProceduraAggiudicazVO> {

	@Override
	public PbandiTProceduraAggiudicazVO mapRow(ResultSet rs, int rowNum) throws SQLException {
		PbandiTProceduraAggiudicazVO cm = new PbandiTProceduraAggiudicazVO();
		cm.setDtAggiudicazione(rs.getDate("DTAGGIUDICAZIONE"));
		cm.setImporto(rs.getBigDecimal("IMPORTO"));
		cm.setIdMotivoAssenzaCig(rs.getBigDecimal("IDMOTIVOASSENZACIG"));
		cm.setIdProceduraAggiudicaz(rs.getBigDecimal("IDPROCEDURAAGGIUDICAZ"));
		cm.setIva(rs.getBigDecimal("IVA"));
		cm.setIdTipologiaAggiudicaz(rs.getBigDecimal("IDTIPOLOGIAAGGIUDICAZ"));
		cm.setCodProcAgg(rs.getString("CODPROCAGG"));
		cm.setIdUtenteIns(rs.getBigDecimal("IDUTENTEINS"));
		cm.setDescProcAgg(rs.getString("DESCPROCAGG"));
		cm.setIdUtenteAgg(rs.getBigDecimal("IDUTENTEAGG"));
		cm.setIdProgetto(rs.getBigDecimal("IDPROGETTO"));
		cm.setDtInizioValidita(rs.getDate("DTINIZIOVALIDITA"));
		cm.setDtFineValidita(rs.getDate("DTFINEVALIDITA"));
		cm.setCigProcAgg(rs.getString("CIGPROCAGG"));
		return cm;
	}

}
