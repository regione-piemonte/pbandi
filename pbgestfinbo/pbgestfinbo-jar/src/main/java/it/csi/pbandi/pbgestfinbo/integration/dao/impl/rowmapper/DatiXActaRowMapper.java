/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.integration.dao.impl.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.csi.pbandi.pbgestfinbo.dto.DatiXActaDTO;

public class DatiXActaRowMapper implements RowMapper<DatiXActaDTO>{

	@Override
	public DatiXActaDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
		DatiXActaDTO d = new DatiXActaDTO();
		
		d.setIdWorkflow(rs.getLong("ID_WORK_FLOW"));
		d.setIdEntita(rs.getLong("ID_ENTITA"));
		d.setIdTarget(rs.getLong("ID_TARGET"));
		d.setIdProgetto(rs.getLong("ID_PROGETTO"));
		d.setIdDomanda(rs.getLong("ID_DOMANDA"));
		d.setNumeroDomanda(rs.getString("NUMERO_DOMANDA"));
		d.setIdDocIndex(rs.getLong("ID_DOCUMENTO_INDEX"));
		d.setIdTipoDocIndex(rs.getLong("ID_TIPO_DOCUMENTO_INDEX"));
		d.setDescrBreveTipoDoc(rs.getString("DESC_BREVE_TIPO_DOC_INDEX"));
		d.setDescrTipoDoc(rs.getString("DESC_TIPO_DOC_INDEX"));
		d.setDescrFascicolo(rs.getString("DESC_FASCICOLO"));
		
		String ff = rs.getString("FLAG_FIRMA_AUTOGRAFA");
		if("S".equals(ff))
			d.setFirmaDigitale(true);
		else
			d.setFirmaDigitale(false);
		
		d.setNomeFile(rs.getString("NOME_FILE"));
		d.setNomeDocumento(rs.getString("NOME_DOCUMENTO"));
		
		// TODO PK : estrarre questi 5 valori dalla query
		d.setDenominazioneBeneficiario(null);
		d.setBeneficiarioNome(null);
		d.setBeneficiarioCognome(null);
		d.setLegaleRappresentanteNome(null);
		d.setLegaleRappresentanteCognome(null);
		 
		return d;
	}

}
