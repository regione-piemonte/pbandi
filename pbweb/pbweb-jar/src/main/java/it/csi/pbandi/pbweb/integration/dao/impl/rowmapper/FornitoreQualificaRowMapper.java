/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweb.integration.dao.impl.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import it.csi.pbandi.pbweb.integration.vo.FornitoreQualificaVO;

public class FornitoreQualificaRowMapper  implements RowMapper<FornitoreQualificaVO> {

	@Override
	public FornitoreQualificaVO mapRow(ResultSet rs, int rowNum) throws SQLException {
		FornitoreQualificaVO cm = new FornitoreQualificaVO();
		
		cm.setCodiceFiscaleFornitore(rs.getString("codice_fiscale_fornitore"));
		cm.setCognomeFornitore(rs.getString("cognome_fornitore"));
		//cm.setCostoOrario(rs.getBigDecimal("costo_orario"));
		cm.setDenominazioneFornitore(rs.getString("denominazione_fornitore"));
		//cm.setDescBreveQualifica(rs.getString("desc_breve_qualifica"));
		cm.setDescBreveTipoSoggetto(rs.getString("desc_breve_tipo_soggetto"));
		//cm.setDescQualifica(rs.getString("desc_qualifica"));
		cm.setDescTipoSoggetto(rs.getString("desc_tipo_soggetto"));
		cm.setDtFineValiditaFornitore(rs.getDate("dt_fine_validita_fornitore"));
		//cm.setDtFineValiditaFornQual(rs.getDate("dt_fine_validita_forn_qual"));
		//cm.setDtFineValiditaQualifica(rs.getDate("dt_fine_validita_qualifica"));
		cm.setFlagHasQualifica(rs.getString("flag_has_qualifica"));
		cm.setIdFormaGiuridica(rs.getBigDecimal("id_forma_giuridica"));
		cm.setIdFornitore(rs.getBigDecimal("id_fornitore"));
		//cm.setIdQualifica(rs.getBigDecimal("id_qualifica"));
		cm.setIdSoggettoFornitore(rs.getBigDecimal("id_soggetto_fornitore"));
		cm.setIdTipoSoggetto(rs.getBigDecimal("id_tipo_soggetto"));
		cm.setNomeFornitore(rs.getString("nome_fornitore"));
		//cm.setNoteQualifica(rs.getString("note_qualifica"));
		cm.setPartitaIvaFornitore(rs.getString("partita_iva_fornitore"));
		//cm.setProgrFornitoreQualifica(rs.getBigDecimal("progr_fornitore_qualifica"));
		
		return cm;
	}
}
