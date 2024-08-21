/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.integration.dao.impl.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

import it.csi.pbandi.pbservizit.dto.cronoprogrammaFasi.AllegatiCronoprogrammaFasi;
import it.csi.pbandi.pbservizit.dto.cronoprogrammaFasi.CronoprogrammaFasiItem;
import it.csi.pbandi.pbservizit.integration.vo.BeneficiarioSoggettoRuoloVO;

public class AllegatiCronoprogrammaFasiRowMapper implements RowMapper<AllegatiCronoprogrammaFasi> {

	@Override
	public AllegatiCronoprogrammaFasi mapRow(ResultSet rs, int rowNum) throws SQLException {
		AllegatiCronoprogrammaFasi cm = new AllegatiCronoprogrammaFasi();

		cm.setIdFileEntita(rs.getLong("ID_FILE_ENTITA"));
		cm.setIdFile(rs.getLong("ID_FILE"));
		cm.setIdFolder(rs.getLong("ID_FOLDER"));
		cm.setIdDocumentoIndex(rs.getLong("ID_DOCUMENTO_INDEX"));
		cm.setNomeFile(rs.getString("NOME_FILE"));
		cm.setSizeFile(new Integer(rs.getInt("SIZE_FILE")));
		cm.setIdProgettoIter(rs.getLong("ID_PROGETTO_ITER"));
		cm.setIdProgetto(rs.getLong("ID_PROGETTO"));
		cm.setIdIter(rs.getLong("ID_ITER"));
		cm.setDescIter(rs.getString("DESC_ITER"));
		cm.setFlagFaseChiusa(rs.getString("FLAG_FASE_CHIUSA"));
		if (rs.getLong("ID_DICHIARAZIONE_SPESA") != 0) {
			cm.setIdDichiarazioneSpesa(rs.getLong("ID_DICHIARAZIONE_SPESA"));
		}
		return cm;
	}
}
