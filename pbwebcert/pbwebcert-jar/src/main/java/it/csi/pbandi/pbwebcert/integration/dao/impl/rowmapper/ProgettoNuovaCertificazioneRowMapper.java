/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebcert.integration.dao.impl.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.csi.pbandi.pbwebcert.integration.vo.ProgettoNuovaCertificazioneVO;

public class ProgettoNuovaCertificazioneRowMapper implements RowMapper<ProgettoNuovaCertificazioneVO> {

	@Override
	public ProgettoNuovaCertificazioneVO mapRow(ResultSet rs, int rowNum) throws SQLException {
		ProgettoNuovaCertificazioneVO cm = new ProgettoNuovaCertificazioneVO();
		cm.setIdStatoProgetto(rs.getBigDecimal("ID_STATO_PROGETTO"));
		cm.setImportoCertificazioneNetto(rs.getBigDecimal("IMPORTO_CERTIFICAZIONE_NETTO"));
		cm.setTitoloProgetto(rs.getString("TITOLO_PROGETTO"));
		cm.setIdProgetto(rs.getBigDecimal("ID_PROGETTO"));
		cm.setCodiceProgetto(rs.getString("CODICE_PROGETTO"));
		cm.setDenominazioneBeneficiario(rs.getString("DENOMINAZIONE_BENEFICIARIO"));
		cm.setIdDettPropostaCertif(rs.getBigDecimal("ID_DETT_PROPOSTA_CERTIF"));
		cm.setImpCertifNettoPremodifica(rs.getBigDecimal("IMP_CERTIF_NETTO_PREMODIFICA"));
		
		cm.setNomeBandoLinea(rs.getString("NOME_BANDO_LINEA"));
		cm.setIdLineaDiIntervento(rs.getBigDecimal("ID_LINEA_DI_INTERVENTO"));
		cm.setNota(rs.getString("NOTA"));
		return cm;
	}


}
