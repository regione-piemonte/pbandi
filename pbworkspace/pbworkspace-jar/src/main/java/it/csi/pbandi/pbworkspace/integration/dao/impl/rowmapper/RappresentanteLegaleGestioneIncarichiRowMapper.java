/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbworkspace.integration.dao.impl.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import it.csi.pbandi.pbworkspace.integration.vo.RappresentanteLegaleGestioneIncarichiVO;

public class RappresentanteLegaleGestioneIncarichiRowMapper implements RowMapper<RappresentanteLegaleGestioneIncarichiVO> {

	@Override
	public RappresentanteLegaleGestioneIncarichiVO mapRow(ResultSet rs, int rowNum) throws SQLException {
		RappresentanteLegaleGestioneIncarichiVO cm = new RappresentanteLegaleGestioneIncarichiVO();
		
		cm.setCodiceFiscaleBeneficiario(rs.getString("codice_fiscale_beneficiario"));
		cm.setDescBreveTipoSoggCorrelato(rs.getString("desc_breve_tipo_sogg_correlato"));
		cm.setDescTipoSoggettoCorrelato(rs.getString("desc_tipo_soggetto_correlato"));
		cm.setIdSoggettoBeneficiario(rs.getBigDecimal("id_soggetto_beneficiario"));
		cm.setIdSoggettoDelegante(rs.getBigDecimal("id_soggetto_delegante"));
		cm.setIdTipoSoggettoCorrelato(rs.getBigDecimal("id_tipo_soggetto_correlato"));
		return cm;
	}

}
