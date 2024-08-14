/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebcert.integration.dao.impl.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

import it.csi.pbandi.pbwebcert.integration.vo.CampioneCertificazioneVO;
import it.csi.pbandi.pbwebcert.integration.vo.PbandiWCampionamentoVO;

public class CampionamentoCertificazioneRowMapper implements RowMapper<CampioneCertificazioneVO>{

	@Override
	public CampioneCertificazioneVO mapRow(ResultSet rs, int rowNum) throws SQLException {
		CampioneCertificazioneVO cm = new CampioneCertificazioneVO();
		cm.setIdCampione(rs.getBigDecimal("ID_CAMPIONE"));
		cm.setProgrOperazione(rs.getBigDecimal("PROGR_OPERAZIONE"));
		//cm.setIdDettPropostaCertif(rs.getBigDecimal("ID_DETT_PROPOSTA_CERTIF"));
		
		cm.setAsse(rs.getString("ASSE"));
		cm.setIdProgetto(rs.getBigDecimal("ID_PROGETTO"));
		cm.setTitoloProgetto(rs.getString("TITOLO_PROGETTO"));
		cm.setCodiceVisualizzato(rs.getString("CODICE_VISUALIZZATO"));
		cm.setAvanzamento(rs.getBigDecimal("AVANZAMENTO"));
		cm.setUniverso(rs.getString("UNIVERSO"));
		
		cm.setIdTipoOperazione(rs.getBigDecimal("ID_TIPO_OPERAZIONE"));
		
		cm.setDescLinea(rs.getString("DESC_LINEA"));
		cm.setDescPeriodoVisualizzata(rs.getString("DESC_PERIODO_VISUALIZZATA"));
		cm.setIdPropostaCertificaz(rs.getBigDecimal("ID_PROPOSTA_CERTIFICAZ"));
		cm.setDataCampionamento(rs.getDate("DATA_CAMPIONAMENTO"));
		
		
//		cm.setTipoCampione(rs.getString("TIPO_CAMPIONE")); 
//		cm.setFlagEscludi(rs.getString("FLAG_ESCLUDI"));
//		cm.setFlagPrimoEstratto(rs.getString("FLAG_PRIMO_ESTRATTO"));
		
		return cm;
	}

}
