/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebcert.integration.dao.impl.rowmapper;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.csi.pbandi.pbwebcert.integration.vo.DocumentoCertificazioneVO;

public class DocumentoCertificazioneRowMapper implements RowMapper<DocumentoCertificazioneVO> {

	@Override
	public DocumentoCertificazioneVO mapRow(ResultSet rs, int rowNum) throws SQLException {
		DocumentoCertificazioneVO cm = new DocumentoCertificazioneVO();
		cm.setCodiceProgettoVisualizzato(rs.getString("CODICE_PROGETTO_VISUALIZZATO"));
		cm.setIdProgetto(rs.getBigDecimal("ID_PROGETTO"));
		cm.setIdLineaDiIntervento(rs.getBigDecimal("ID_LINEA_DI_INTERVENTO"));
		cm.setIdSoggettoBeneficiario(rs.getBigDecimal("ID_SOGGETTO_BENEFICIARIO"));
		cm.setIdPropostaCertificaz(rs.getBigDecimal("ID_PROPOSTA_CERTIFICAZ"));
		//cm.setDescTipoDocIndexStato(rs.getString("DESC_BREVE_TIPO_DOC_INDEX"));
		cm.setDescTipoDocIndexStato(rs.getString("DESC_TIPO_DOC_INDEX_STATO"));
		cm.setDescBreveStatoPropostaCert(rs.getString("DESC_BREVE_STATO_PROPOSTA_CERT"));
		
		cm.setIdDocumentoIndex(rs.getBigDecimal("ID_DOCUMENTO_INDEX"));
		cm.setDtInserimentoIndex(rs.getDate("DT_INSERIMENTO_INDEX"));
		//cm.setNomeDocumento(rs.getString("NOME_DOCUMENTO"));
		cm.setNomeDocumento(rs.getString("NOME_FILE"));
		cm.setNoteDocumentoIndex(rs.getString("NOTE_DOCUMENTO_INDEX"));
		
		cm.setDescTipoDocIndex(rs.getString("DESC_TIPO_DOC_INDEX"));
		cm.setDescBreveTipoDocIndex(rs.getString("DESC_BREVE_TIPO_DOC_INDEX"));
		return cm;
	}

}
