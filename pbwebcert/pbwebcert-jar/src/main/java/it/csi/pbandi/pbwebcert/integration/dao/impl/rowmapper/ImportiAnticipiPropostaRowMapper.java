/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebcert.integration.dao.impl.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.csi.pbandi.pbwebcert.integration.vo.ImportiAnticipoPropostaCertificazioneVO;

public class ImportiAnticipiPropostaRowMapper implements RowMapper<ImportiAnticipoPropostaCertificazioneVO> {

	@Override
	public ImportiAnticipoPropostaCertificazioneVO mapRow(ResultSet rs, int rowNum) throws SQLException {
		ImportiAnticipoPropostaCertificazioneVO cm = new ImportiAnticipoPropostaCertificazioneVO();
		cm.setIdPropostaCertificaz(rs.getBigDecimal("ID_PROPOSTA_CERTIFICAZ"));
		cm.setDescBreveLinea(rs.getString("DESC_BREVE_LINEA"));
		cm.setIdLineaDiIntervento(rs.getBigDecimal("ID_LINEA_DI_INTERVENTO"));
		cm.setImportoAnticipo(rs.getBigDecimal("IMPORTO_ANTICIPO"));
		return cm;
	}

}
