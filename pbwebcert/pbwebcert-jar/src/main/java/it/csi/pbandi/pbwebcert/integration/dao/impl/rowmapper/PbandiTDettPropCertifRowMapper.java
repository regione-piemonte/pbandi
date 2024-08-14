/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebcert.integration.dao.impl.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.csi.pbandi.pbwebcert.integration.vo.PbandiTDettPropostaCertifVO;

public class PbandiTDettPropCertifRowMapper implements RowMapper<PbandiTDettPropostaCertifVO>{

	@Override
	public PbandiTDettPropostaCertifVO mapRow(ResultSet rs, int rowNum) throws SQLException {
		PbandiTDettPropostaCertifVO cm = new PbandiTDettPropostaCertifVO();
		cm.setIdPropostaCertificaz(rs.getBigDecimal("ID_PROPOSTA_CERTIFICAZ"));	
		cm.setTitoloProgetto(rs.getString("TITOLO_PROGETTO"));	
		cm.setCostoAmmesso(rs.getBigDecimal("COSTO_AMMESSO"));
		cm.setIdDettPropostaCertif(rs.getBigDecimal("ID_DETT_PROPOSTA_CERTIF"));
		cm.setIdProgetto(rs.getBigDecimal("ID_PROGETTO"));
		cm.setImportoEccendenzeValidazione(rs.getBigDecimal("IMPORTO_ECCENDENZE_VALIDAZIONE"));
		cm.setImportoErogazioni(rs.getBigDecimal("IMPORTO_EROGAZIONI"));
		cm.setImportoFideiussioni(rs.getBigDecimal("IMPORTO_FIDEIUSSIONI"));
		cm.setImportoPagamentiValidati(rs.getBigDecimal("IMPORTO_PAGAMENTI_VALIDATI"));
		cm.setSpesaCertificabileLorda(rs.getBigDecimal("SPESA_CERTIFICABILE_LORDA"));
		
		cm.setImportoCertificazioneNetto(rs.getBigDecimal("IMPORTO_CERTIFICAZIONE_NETTO"));
		cm.setImpCertifNettoPremodifica(rs.getBigDecimal("IMP_CERTIF_NETTO_PREMODIFICA"));
			
		return cm;
	}
}
