/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweb.integration.dao.impl.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import it.csi.pbandi.pbweb.integration.vo.BeneficiarioVO;

public class BeneficiarioRowMapper  implements RowMapper<BeneficiarioVO> {

	@Override
	public BeneficiarioVO mapRow(ResultSet rs, int rowNum) throws SQLException {
		BeneficiarioVO cm = new BeneficiarioVO();
		
		cm.setCodiceFiscale(rs.getString("codicefiscale"));
		cm.setDataInizioValidita(rs.getDate("datainiziovalidita"));
		cm.setDescrizione(rs.getString("descrizione"));
		cm.setIdDimensioneImpresa(rs.getLong("iddimensioneimpresa"));
		cm.setIdFormaGiuridica(rs.getLong("idformagiuridica"));
		cm.setIdSoggetto(rs.getLong("id_soggetto"));
		cm.setIdProgetto(rs.getLong("id_progetto"));

//		cm.setNome(rs.getString("nome"));
//		cm.setCognome(rs.getString("cognome"));
		
		return cm;
	}
}
