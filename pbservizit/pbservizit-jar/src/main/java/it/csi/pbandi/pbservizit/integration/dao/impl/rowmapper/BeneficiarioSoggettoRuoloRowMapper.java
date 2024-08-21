/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.integration.dao.impl.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

import it.csi.pbandi.pbservizit.integration.vo.BeneficiarioSoggettoRuoloVO;

public class BeneficiarioSoggettoRuoloRowMapper implements RowMapper<BeneficiarioSoggettoRuoloVO> {

	@Override
	public BeneficiarioSoggettoRuoloVO mapRow(ResultSet rs, int rowNum) throws SQLException {
		BeneficiarioSoggettoRuoloVO cm = new BeneficiarioSoggettoRuoloVO();
		
		cm.setCodiceFiscaleBeneficiario(rs.getString("codice_fiscale_beneficiario"));
		cm.setCodiceFiscaleSoggetto(rs.getString("codice_fiscale_soggetto"));
		cm.setDenominazioneBeneficiario(rs.getString("denominazione_beneficiario"));
		cm.setDescBreveTipoAnagrafica(rs.getString("desc_breve_tipo_anagrafica"));
		cm.setIdProgetto(rs.getBigDecimal("id_progetto"));
		cm.setIdSoggetto(rs.getBigDecimal("id_soggetto"));
		cm.setIdSoggettoBeneficiario(rs.getBigDecimal("id_soggetto_beneficiario"));
		cm.setIdTipoAnagrafica(rs.getBigDecimal("id_tipo_anagrafica"));
		cm.setProgrBandoLineaIntervento(rs.getBigDecimal("progr_bando_linea_intervento"));
		
		return cm;
	}
}
