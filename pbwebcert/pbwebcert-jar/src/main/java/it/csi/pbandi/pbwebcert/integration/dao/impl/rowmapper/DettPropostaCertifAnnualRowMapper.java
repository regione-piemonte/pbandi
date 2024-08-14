/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebcert.integration.dao.impl.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.RowMapper;

import it.csi.pbandi.pbwebcert.integration.vo.DettPropostaCertifAnnualVO;

public class DettPropostaCertifAnnualRowMapper implements RowMapper<DettPropostaCertifAnnualVO> {

	@Override
	public DettPropostaCertifAnnualVO mapRow(ResultSet rs, int rowNum) throws SQLException {
		DettPropostaCertifAnnualVO cm = new DettPropostaCertifAnnualVO();
		cm.setCodiceProgetto(rs.getString("CODICEPROGETTO"));
		cm.setDescStatoPropostaCertif(rs.getString("DESCSTATOPROPOSTACERTIF"));
		cm.setIdLineaDiIntervento(rs.getBigDecimal("IDLINEADIINTERVENTO"));
		cm.setIdPropostaCertificaz(rs.getBigDecimal("IDPROPOSTACERTIFICAZ"));
		cm.setIdUtenteAgg(rs.getBigDecimal("IDUTENTEAGG"));
		cm.setColonnaC(rs.getBigDecimal("COLONNAC"));
		cm.setImportoCertifNettoAnnual(rs.getBigDecimal("IMPORTOCERTIFNETTOANNUAL"));
		cm.setImportoPagamValidCum(rs.getBigDecimal("IMPORTOPAGAMVALIDCUM"));
		cm.setDataAgg(rs.getDate("DATAAGG"));
		cm.setImportoRevocheRilevCum(rs.getBigDecimal("IMPORTOREVOCHERILEVCUM"));
		cm.setDescBreveStatoPropostaCert(rs.getString("DESCBREVESTATOPROPOSTACERT"));
		cm.setCertificatoLordoCumulato(rs.getBigDecimal("CERTIFICATOLORDOCUMULATO"));
		cm.setImportoSoppressioniCum(rs.getBigDecimal("IMPORTOSOPPRESSIONICUM"));
		cm.setIdStatoPropostaCertif(rs.getBigDecimal("IDSTATOPROPOSTACERTIF"));
		cm.setIdProgetto(rs.getBigDecimal("IDPROGETTO"));
		cm.setNomeBandoLinea(rs.getString("NOMEBANDOLINEA"));
		cm.setIdDettPropostaCertif(rs.getBigDecimal("IDDETTPROPOSTACERTIF"));
		cm.setImportoRecuperiCum(rs.getBigDecimal("IMPORTORECUPERICUM"));
		cm.setIdDettPropCertAnnual(rs.getBigDecimal("IDDETTPROPCERTANNUAL"));
		cm.setImportoErogazioniCum(rs.getBigDecimal("IMPORTOEROGAZIONICUM"));
		cm.setBeneficiario(rs.getString("BENEFICIARIO"));
		cm.setDescProposta(rs.getString("DESCPROPOSTA"));
		cm.setCertificatoNettoCumulato(rs.getBigDecimal("CERTIFICATONETTOCUMULATO"));
		cm.setAsse(rs.getString("ASSE"));
		return cm;
	}


}
