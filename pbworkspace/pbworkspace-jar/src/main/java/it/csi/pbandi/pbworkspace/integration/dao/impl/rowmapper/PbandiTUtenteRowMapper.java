/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbworkspace.integration.dao.impl.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.csi.pbandi.pbworkspace.integration.vo.PbandiTUtenteVO;

public class PbandiTUtenteRowMapper implements RowMapper<PbandiTUtenteVO> {

	@Override
	public PbandiTUtenteVO mapRow(ResultSet rs, int rowNum) throws SQLException {
		PbandiTUtenteVO cm = new PbandiTUtenteVO();
		
		cm.setIdSoggetto(rs.getBigDecimal("ID_SOGGETTO"));
		cm.setCodiceUtente(rs.getString("CODICE_UTENTE"));
		cm.setEmailConsenso(rs.getString("EMAIL_CONSENSO"));
		cm.setFlagConsensoMail(rs.getString("FLAG_CONSENSO_MAIL"));
		cm.setIdTipoAccesso(rs.getBigDecimal("ID_TIPO_ACCESSO"));
		cm.setIdUtente(rs.getBigDecimal("ID_UTENTE"));
		cm.setLogin(rs.getString("LOGIN"));
		cm.setPassword(rs.getString("PASSWORD"));
		cm.setDtAggiornamento(rs.getDate("DT_AGGIORNAMENTO"));
		cm.setDtInserimento(rs.getDate("DT_INSERIMENTO"));
		
		return cm;
	}

}
