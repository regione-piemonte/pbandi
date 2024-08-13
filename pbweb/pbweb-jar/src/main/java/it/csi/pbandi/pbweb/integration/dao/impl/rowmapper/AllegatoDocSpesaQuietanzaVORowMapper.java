/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweb.integration.dao.impl.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.csi.pbandi.pbweb.integration.vo.AllegatoDocSpesaQuietanzaVO;
import it.csi.pbandi.pbweb.integration.vo.Email;

public class AllegatoDocSpesaQuietanzaVORowMapper implements RowMapper<AllegatoDocSpesaQuietanzaVO> {

	@Override
	public AllegatoDocSpesaQuietanzaVO mapRow(ResultSet rs, int rowNum) throws SQLException {
		AllegatoDocSpesaQuietanzaVO cm = new AllegatoDocSpesaQuietanzaVO();

		cm.setNomeFile(rs.getString("NOME_FILE"));
		cm.setIdDocumentoDiSpesa(rs.getLong("ID_DOCUMENTO_DI_SPESA"));
		cm.setTipoNumeroDocSpesa(rs.getString("DOCUMENTO"));
		cm.setImportoDocSpesa(rs.getDouble("IMPORTO"));

		if (rs.getString("FORNITORE") != null) {
			cm.setDenomFornitore(rs.getString("FORNITORE"));
		}
		if (rs.getString("NOTE") != null) {
			cm.setNoteValidazione(rs.getString("NOTE"));
		}
		if (rs.getString("ID_PAGAMENTO") != null) {
			cm.setIdPagamento(rs.getLong("ID_PAGAMENTO"));
		}
		if (rs.getString("DESC_MODALITA_PAGAMENTO") != null) {
			cm.setModalitaPagamento(rs.getString("DESC_MODALITA_PAGAMENTO"));
		}
		if (rs.getString("IMPORTO_PAGAMENTO") != null) {
			cm.setImportoPagamento(rs.getDouble("IMPORTO_PAGAMENTO"));
		}
		if (rs.getString("DT_PAGAMENTO") != null) {
			cm.setDtPagamento(rs.getDate("DT_PAGAMENTO"));
		}

		return cm;
	}

}
