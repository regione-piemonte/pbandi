/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebcert.integration.dao.impl.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.csi.pbandi.pbwebcert.integration.vo.DocumentoIndexVO;

public class DocumentoIndexRowMapper implements RowMapper<DocumentoIndexVO> {

	@Override
	public DocumentoIndexVO mapRow(ResultSet rs, int rowNum) throws SQLException {
		DocumentoIndexVO cm = new DocumentoIndexVO();
		cm.setIdDocumentoIndex(rs.getBigDecimal("ID_DOCUMENTO_INDEX"));
		cm.setIdTarget(rs.getBigDecimal("ID_TARGET"));
		cm.setUuidNodo(rs.getString("UUID_NODO"));
		cm.setRepository(rs.getString("REPOSITORY"));
		cm.setDtInserimentoIndex(rs.getDate("DT_INSERIMENTO_INDEX"));
		cm.setIdTipoDocumentoIndex(rs.getBigDecimal("ID_TIPO_DOCUMENTO_INDEX"));
		cm.setIdEntita(rs.getBigDecimal("ID_ENTITA"));
		cm.setIdUtenteIns(rs.getBigDecimal("ID_UTENTE_INS"));
		cm.setIdUtenteAgg(rs.getBigDecimal("ID_UTENTE_AGG"));
		cm.setNomeFile(rs.getString("NOME_FILE"));
		cm.setNoteDocumentoIndex(rs.getString("NOTE_DOCUMENTO_INDEX"));
		cm.setIdProgetto(rs.getBigDecimal("ID_PROGETTO"));
		cm.setDtAggiornamentoIndex(rs.getDate("DT_AGGIORNAMENTO_INDEX"));
		cm.setVersione(rs.getBigDecimal("VERSIONE"));
		cm.setIdModello(rs.getBigDecimal("ID_MODELLO"));
		cm.setNumProtocollo(rs.getString("NUM_PROTOCOLLO"));
		cm.setFlagFirmaCartacea(rs.getString("FLAG_FIRMA_CARTACEA"));
		cm.setMessageDigest(rs.getString("MESSAGE_DIGEST"));
		cm.setIdStatoDocumento(rs.getBigDecimal("ID_STATO_DOCUMENTO"));
		cm.setDtVerificaFirma(rs.getDate("DT_VERIFICA_FIRMA"));
		cm.setDtMarcaTemporale(rs.getDate("DT_MARCA_TEMPORALE"));
		cm.setIdSoggRapprLegale(rs.getBigDecimal("ID_SOGG_RAPPR_LEGALE"));
		cm.setIdSoggDelegato(rs.getBigDecimal("ID_SOGG_DELEGATO"));
		cm.setNomeDocumento(rs.getString("NOME_DOCUMENTO"));
		//FLAG_TRASM_DEST non mapato
		cm.setIdCategAnagraficaMitt(rs.getBigDecimal("ID_CATEG_ANAGRAFICA_MITT"));
		cm.setDescBreveTipoDocIndex(rs.getString("DESC_BREVE_TIPO_DOC_INDEX"));
		cm.setDescTipoDocIndex(rs.getString("DESC_TIPO_DOC_INDEX"));
		return cm;
	}

}
