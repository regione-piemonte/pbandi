/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.integration.dao.impl.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import it.csi.pbandi.pbservizit.integration.vo.BeneficiarioVO;

public class PbandiBeneficiariRowMapper implements RowMapper<BeneficiarioVO> {
	
	@Override
	public BeneficiarioVO mapRow(ResultSet rs, int rowNum) throws SQLException {
		BeneficiarioVO cm = new BeneficiarioVO();
		
		cm.setCodiceFiscale(rs.getString("codiceFiscale"));
		cm.setCognome(null);
		cm.setDataInizioValidita(rs.getDate("dataIniziovalidita"));
		cm.setDescrizione(rs.getString("descrizione"));
		cm.setId(null);
		cm.setId_progetto(null);
		cm.setId_soggetto(rs.getLong("id_soggetto"));
		cm.setIdDimensioneImpresa(rs.getLong("idDimensioneImpresa"));
		cm.setIdFormaGiuridica(rs.getLong("idFormaGiuridica"));
		cm.setNome(null);

		return cm;
	}

}
