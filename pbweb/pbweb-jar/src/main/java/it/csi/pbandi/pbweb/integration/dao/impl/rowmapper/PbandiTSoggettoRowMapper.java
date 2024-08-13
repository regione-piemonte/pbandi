/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweb.integration.dao.impl.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

import it.csi.pbandi.pbweb.integration.vo.PbandiTSoggettoVO;

public class PbandiTSoggettoRowMapper implements RowMapper<PbandiTSoggettoVO> {

	@Override
	public PbandiTSoggettoVO mapRow(ResultSet rs, int rowNum) throws SQLException {
		PbandiTSoggettoVO cm = new PbandiTSoggettoVO();
		
		
		cm.setIdSoggetto(rs.getBigDecimal("id_soggetto"));
		cm.setCodiceFiscaleSoggetto(rs.getString("CODICE_FISCALE_SOGGETTO"));
		cm.setIdTipoSoggetto(rs.getBigDecimal("ID_TIPO_SOGGETTO"));
		cm.setIdUtenteAgg(rs.getBigDecimal("ID_UTENTE_AGG"));
		cm.setIdUtenteIns(rs.getBigDecimal("ID_UTENTE_INS"));
		cm.setDtAggiornamento(rs.getDate("DT_AGGIORNAMENTO"));
		cm.setDtInserimento(rs.getDate("DT_INSERIMENTO"));
		
		// TODO : campo  RICEVENTE_TRASF non mappato nel vecchio
		
		return cm;
	}
}
