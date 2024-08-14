/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebcert.integration.dao.impl.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.csi.pbandi.pbwebcert.integration.vo.DettPropostaCertifAnnualCCVO;


public class DettPropostaCertifAnnualCCRowMapper implements RowMapper<DettPropostaCertifAnnualCCVO> {

	@Override
	public DettPropostaCertifAnnualCCVO mapRow(ResultSet rs, int rowNum) throws SQLException {
		DettPropostaCertifAnnualCCVO cm = new DettPropostaCertifAnnualCCVO();
		cm.setCodiceProgetto(rs.getString("CODICEPROGETTO"));
		cm.setDescStatoPropostaCertif(rs.getString("DESCSTATOPROPOSTACERTIF"));
		cm.setIdLineaDiIntervento(rs.getBigDecimal("IDLINEADIINTERVENTO"));
		cm.setDiffCna(rs.getBigDecimal("DIFFCNA"));
		cm.setIdPropostaCertificaz(rs.getBigDecimal("IDPROPOSTACERTIFICAZ"));
		cm.setIdUtenteAgg(rs.getBigDecimal("IDUTENTEAGG"));
		cm.setColonnaC(rs.getBigDecimal("COLONNAC"));
		cm.setDiffSoppr(rs.getBigDecimal("DIFFSOPPR"));
		cm.setImportoCertifNettoAnnual(rs.getBigDecimal("IMPORTOCERTIFNETTOANNUAL"));
		cm.setImportoPagamValidCum(rs.getBigDecimal("IMPORTOPAGAMVALIDCUM"));
		cm.setDataAgg(rs.getDate("DATAAGG"));
		cm.setDiffRec(rs.getBigDecimal("DIFFREC"));
		cm.setImportoRevocheRilevCum(rs.getBigDecimal("IMPORTOREVOCHERILEVCUM"));
		cm.setDescBreveStatoPropostaCert(rs.getString("DESCBREVESTATOPROPOSTACERT"));
		cm.setCertificatoLordoCumulato(rs.getBigDecimal("CERTIFICATOLORDOCUMULATO"));
		cm.setImportoSoppressioniCum(rs.getBigDecimal("IMPORTOSOPPRESSIONICUM"));
		cm.setIdStatoPropostaCertif(rs.getBigDecimal("IDSTATOPROPOSTACERTIF"));
		cm.setNomeBandoLinea(rs.getString("NOMEBANDOLINEA"));
		cm.setIdProgetto(rs.getBigDecimal("IDPROGETTO"));
		cm.setIdDettPropostaCertif(rs.getBigDecimal("IDDETTPROPOSTACERTIF"));
		cm.setImportoRecuperiCum(rs.getBigDecimal("IMPORTORECUPERICUM"));
		cm.setIdDettPropCertAnnual(rs.getBigDecimal("IDDETTPROPCERTANNUAL"));
		cm.setImportoErogazioniCum(rs.getBigDecimal("IMPORTOEROGAZIONICUM"));
		cm.setBeneficiario(rs.getString("BENEFICIARIO"));
		cm.setDiffRev(rs.getBigDecimal("DIFFREV"));
		cm.setDescProposta(rs.getString("DESCPROPOSTA"));
		cm.setCertificatoNettoCumulato(rs.getBigDecimal("CERTIFICATONETTOCUMULATO"));
		cm.setAsse(rs.getString("ASSE"));
		return cm;
	}

	

}
