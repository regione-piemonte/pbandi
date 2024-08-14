/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebcert.integration.dao.impl.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.csi.pbandi.pbwebcert.integration.vo.DettPropostaCertifVO;

public class DettPropostaCertifRowMapper implements RowMapper<DettPropostaCertifVO>{

	@Override
	public DettPropostaCertifVO mapRow(ResultSet rs, int rowNum) throws SQLException {
		DettPropostaCertifVO cm = new DettPropostaCertifVO();
		cm.setIdPropostaCertificaz(rs.getBigDecimal("IDPROPOSTACERTIFICAZ"));
		cm.setDtOraCreazione(rs.getDate("DTORACREAZIONE"));
		cm.setDescProposta(rs.getString("DESCPROPOSTA"));
		cm.setDtCutOffPagamenti(rs.getDate("DTCUTOFFPAGAMENTI"));
		cm.setDtCutOffValidazioni(rs.getDate("DTCUTOFFVALIDAZIONI"));		
		cm.setDtCutOffErogazioni(rs.getDate("DTCUTOFFEROGAZIONI"));
		cm.setDtCutOffFideiussioni(rs.getDate("DTCUTOFFFIDEIUSSIONI"));
		cm.setIdStatoPropostaCertif(rs.getLong("IDSTATOPROPOSTACERTIF"));
		cm.setCodiceVisualizzato(rs.getString("CODICEVISUALIZZATO"));
		cm.setTitoloProgetto(rs.getString("TITOLOPROGETTO"));
		cm.setDescBreveStatoPropostaCert(rs.getString("DESCBREVESTATOPROPOSTACERT"));
		cm.setDescStatoPropostaCertif("DESCSTATOPROPOSTACERTIF");
		cm.setCostoAmmesso(rs.getBigDecimal("COSTOAMMESSO"));
		cm.setIdDettPropostaCertif(rs.getBigDecimal("IDDETTPROPOSTACERTIF"));
		cm.setIdProgetto(rs.getBigDecimal("IDPROGETTO"));
		cm.setImportoEccendenzeValidazione(rs.getBigDecimal("IMPORTOECCENDENZEVALIDAZIONE"));
		cm.setImportoErogazioni(rs.getBigDecimal("IMPORTOEROGAZIONI"));
		cm.setImportoFideiussioni(rs.getBigDecimal("IMPORTOFIDEIUSSIONI"));
		cm.setImportoPagamentiValidati(rs.getBigDecimal("IMPORTOPAGAMENTIVALIDATI"));
		cm.setSpesaCertificabileLorda(rs.getBigDecimal("SPESACERTIFICABILELORDA"));
		cm.setAttivita(rs.getString("ATTIVITA"));
		cm.setDescBreveCompletaAttivita(rs.getString("DESCBREVECOMPLETAATTIVITA"));
		cm.setPercContributoPubblico(rs.getBigDecimal("PERCCONTRIBUTOPUBBLICO"));
		cm.setPercCofinFesr(rs.getBigDecimal("PERCCOFINFESR"));
		cm.setBeneficiario(rs.getString("BENEFICIARIO"));
		return cm;
	}

}
