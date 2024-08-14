/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebcert.integration.dao.impl.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.csi.pbandi.pbwebcert.integration.vo.DettPropostaCertifAnnualVO;

public class PropostaCertifAnnualRowMapper  implements RowMapper<DettPropostaCertifAnnualVO> {

	@Override
	public DettPropostaCertifAnnualVO mapRow(ResultSet rs, int rowNum) throws SQLException {
		DettPropostaCertifAnnualVO cm = new DettPropostaCertifAnnualVO();
		cm.setIdPropostaCertificaz(rs.getBigDecimal("IDPROPOSTACERTIFICAZ"));
		cm.setIdUtenteAgg(rs.getBigDecimal("IDUTENTEAGG"));
		cm.setImportoCertifNettoAnnual(rs.getBigDecimal("IMPORTOCERTIFNETTOANNUAL"));

		cm.setDataAgg(rs.getDate("DATAAGG"));
		
		cm.setImportoRevocheRilevCum(rs.getBigDecimal("IMPORTOREVOCHERILEVCUM"));
		

		cm.setCertificatoLordoCumulato(rs.getBigDecimal("CERTIFICATOLORDOCUMULATO"));
		
		cm.setImportoSoppressioniCum(rs.getBigDecimal("IMPORTOSOPPRESSIONICUM"));
		

		cm.setIdDettPropostaCertif(rs.getBigDecimal("IDDETTPROPOSTACERTIF"));
		
		cm.setImportoRecuperiCum(rs.getBigDecimal("IMPORTORECUPERICUM"));
		
		cm.setIdDettPropCertAnnual(rs.getBigDecimal("IDDETTPROPCERTANNUAL"));
		
		cm.setImportoErogazioniCum(rs.getBigDecimal("IMPORTOEROGAZIONICUM"));
		
		cm.setCertificatoNettoCumulato(rs.getBigDecimal("CERTIFICATONETTOCUMULATO"));
		cm.setImportoPagamValidCum(rs.getBigDecimal("IMPORTOPAGAMVALIDCUM"));
		cm.setColonnaC(rs.getBigDecimal("colonnaC"));
		return cm;
	}

}
