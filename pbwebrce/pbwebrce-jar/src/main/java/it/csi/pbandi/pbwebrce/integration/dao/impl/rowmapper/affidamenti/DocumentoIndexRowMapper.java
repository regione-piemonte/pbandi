/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebrce.integration.dao.impl.rowmapper.affidamenti;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.csi.pbandi.pbwebrce.integration.vo.affidamenti.DocumentoIndexMaxVersioneDefinitivoVO;


public class DocumentoIndexRowMapper implements RowMapper<DocumentoIndexMaxVersioneDefinitivoVO> {

	@Override
	public DocumentoIndexMaxVersioneDefinitivoVO mapRow(ResultSet rs, int rowNum) throws SQLException {
		DocumentoIndexMaxVersioneDefinitivoVO cm = new DocumentoIndexMaxVersioneDefinitivoVO();
		cm.setIdDocumentoIndex(rs.getBigDecimal("IDDOCUMENTOINDEX"));
		cm.setIdCategAnagraficaMitt(rs.getBigDecimal("IDCATEGANAGRAFICAMITT"));
		cm.setIdStatoDocumento(rs.getBigDecimal("IDSTATODOCUMENTO"));
		cm.setNoteDocumentoIndex(rs.getString("NOTEDOCUMENTOINDEX"));
		cm.setNomeFile(rs.getString("NOMEFILE"));
		cm.setIdUtenteAgg(rs.getBigDecimal("IDUTENTEAGG"));
		cm.setMessageDigest(rs.getString("MESSAGEDIGEST"));
		cm.setNumProtocollo(rs.getString("NUMPROTOCOLLO"));
		cm.setDtVerificaFirma(rs.getDate("DTVERIFICAFIRMA"));
		cm.setRepository(rs.getString("REPOSITORY"));
		cm.setDtInserimentoIndex(rs.getDate("DTINSERIMENTOINDEX"));
		cm.setIdModello(rs.getBigDecimal("IDMODELLO"));
		cm.setDtMarcaTemporale(rs.getDate("DTMARCATEMPORALE"));
		cm.setVersione(rs.getBigDecimal("VERSIONE"));
		cm.setFlagFirmaCartacea(rs.getString("FLAGFIRMACARTACEA"));
		cm.setIdEntita(rs.getBigDecimal("IDENTITA"));
		cm.setIdTarget(rs.getBigDecimal("IDTARGET"));
		cm.setIdTipoDocumentoIndex(rs.getBigDecimal("IDTIPODOCUMENTOINDEX"));
		cm.setUuidNodo(rs.getString("UUIDNODO"));
		cm.setIdUtenteIns(rs.getBigDecimal("IDUTENTEINS"));
		cm.setIdStatoTipoDocIndex(rs.getBigDecimal("IDSTATOTIPODOCINDEX"));
		cm.setIdProgetto(rs.getBigDecimal("IDPROGETTO"));
		cm.setDtAggiornamentoIndex(rs.getDate("DTAGGIORNAMENTOINDEX"));
		cm.setIdSoggDelegato(rs.getBigDecimal("IDSOGGDELEGATO"));
		cm.setIdSoggRapprLegale(rs.getBigDecimal("IDSOGGRAPPRLEGALE"));
		return cm;
	}



}
