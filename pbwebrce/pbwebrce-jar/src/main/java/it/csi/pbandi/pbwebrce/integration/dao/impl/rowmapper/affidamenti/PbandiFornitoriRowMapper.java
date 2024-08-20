/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebrce.integration.dao.impl.rowmapper.affidamenti;

import java.sql.ResultSet;
import java.sql.SQLException;


import org.springframework.jdbc.core.RowMapper;

import it.csi.pbandi.pbwebrce.integration.vo.affidamenti.FornitoreVO;

public class PbandiFornitoriRowMapper implements RowMapper<FornitoreVO> {

	@Override
	public FornitoreVO mapRow(ResultSet rs, int rowNum) throws SQLException {
		FornitoreVO cm = new FornitoreVO();
		cm.setCodiceFiscaleFornitore(rs.getString("CODICEFISCALEFORNITORE"));
		cm.setCognomeFornitore(rs.getString("COGNOMEFORNITORE"));
		cm.setDenominazioneFornitore(rs.getString("DENOMINAZIONEFORNITORE"));
		cm.setIdFornitore(rs.getLong("IDFORNITORE"));
		cm.setIdTipoSoggetto(rs.getLong("IDTIPOSOGGETTO"));
		cm.setNomeFornitore(rs.getString("NOMEFORNITORE"));
		cm.setPartitaIvaFornitore(rs.getString("PARTITAIVAFORNITORE"));
		cm.setIdFormaGiuridica(rs.getLong("IDFORMAGIURIDICA"));
		cm.setIdAttivitaAteco(rs.getLong("IDATTIVITAATECO"));
		cm.setIdNazione(rs.getLong("IDNAZIONE"));
		cm.setAltroCodice(rs.getString("ALTROCODICE"));
		cm.setDescTipoSoggetto(rs.getString("DESCTIPOSOGGETTO"));
		cm.setDescQualifica(rs.getString("DESCQUALIFICA"));
		cm.setIdQualifica(rs.getLong("IDQUALIFICA"));
		cm.setDtFineValidita(rs.getDate("DTFINEVALIDITA"));
		cm.setFlagPubblicoPrivato(rs.getLong("FLAGPUBBLICOPRIVATO"));
		cm.setCodUniIpa(rs.getString("CODUNIIPA"));
		return cm;
	}

	

}
