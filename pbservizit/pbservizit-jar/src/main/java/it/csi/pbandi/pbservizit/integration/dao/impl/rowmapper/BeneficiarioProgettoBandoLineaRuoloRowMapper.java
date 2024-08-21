/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.integration.dao.impl.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.csi.pbandi.pbservizit.integration.vo.BeneficiarioProgettoBandoLineaRuoloVO;

public class BeneficiarioProgettoBandoLineaRuoloRowMapper implements RowMapper<BeneficiarioProgettoBandoLineaRuoloVO> {

	@Override
	public BeneficiarioProgettoBandoLineaRuoloVO mapRow(ResultSet rs, int rowNum) throws SQLException {
		BeneficiarioProgettoBandoLineaRuoloVO cm = new BeneficiarioProgettoBandoLineaRuoloVO();

		cm.setBeneficiario(rs.getString("beneficiario"));
		cm.setCodiceFiscaleSoggetto(rs.getString("codiceFiscaleSoggetto"));
		cm.setCodiceVisualizzato(rs.getString("codiceVisualizzato"));
		cm.setDescBreveTipoAnagrafica(rs.getString("descBreveTipoAnagrafica"));
		cm.setIdProgetto(rs.getLong("idProgetto"));
		cm.setIdSoggetto(rs.getLong("idSoggetto"));
		cm.setNomeBandoLinea(rs.getString("nomeBandoLinea"));
		cm.setIdIstanzaProcesso(rs.getString("idIstanzaProcesso"));
		cm.setProgrBandoLineaIntervento(rs.getLong("progrBandoLineaIntervento"));

		return cm;
	}
}
