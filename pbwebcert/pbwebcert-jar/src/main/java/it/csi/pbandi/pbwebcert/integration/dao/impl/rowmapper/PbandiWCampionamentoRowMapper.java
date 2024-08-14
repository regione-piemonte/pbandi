/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebcert.integration.dao.impl.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.csi.pbandi.pbwebcert.integration.vo.PbandiWCampionamentoVO;

public class PbandiWCampionamentoRowMapper implements RowMapper<PbandiWCampionamentoVO>{

	@Override
	public PbandiWCampionamentoVO mapRow(ResultSet rs, int rowNum) throws SQLException {
		PbandiWCampionamentoVO cm = new PbandiWCampionamentoVO();
		cm.setIdCampione(rs.getBigDecimal("ID_CAMPIONE"));
		cm.setProgrOperazione(rs.getBigDecimal("PROGR_OPERAZIONE"));
		cm.setIdDettPropostaCertif(rs.getBigDecimal("ID_DETT_PROPOSTA_CERTIF"));
		
		cm.setAsse(rs.getString("ASSE"));
		cm.setIdProgetto(rs.getBigDecimal("ID_PROGETTO"));
		cm.setTitoloProgetto(rs.getString("TITOLO_PROGETTO"));
		cm.setCodiceVisualizzato(rs.getString("CODICE_VISUALIZZATO"));
		cm.setAvanzamento(rs.getBigDecimal("AVANZAMENTO"));
		cm.setUniverso(rs.getString("UNIVERSO"));
		
		
		cm.setTipoCampione(rs.getString("TIPO_CAMPIONE")); 
		cm.setFlagEscludi(rs.getString("FLAG_ESCLUDI"));
		cm.setFlagPrimoEstratto(rs.getString("FLAG_PRIMO_ESTRATTO"));
		
		return cm;
	}

}
