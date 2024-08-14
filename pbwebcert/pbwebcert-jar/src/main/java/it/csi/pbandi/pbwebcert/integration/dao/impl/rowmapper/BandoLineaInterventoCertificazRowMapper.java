/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebcert.integration.dao.impl.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.csi.pbandi.pbwebcert.integration.vo.BandoLineaInterventoCertificazioneVO;

public class BandoLineaInterventoCertificazRowMapper implements RowMapper<BandoLineaInterventoCertificazioneVO> {

	@Override
	public BandoLineaInterventoCertificazioneVO mapRow(ResultSet rs, int rowNum) throws SQLException {
		BandoLineaInterventoCertificazioneVO cm = new BandoLineaInterventoCertificazioneVO();
		cm.setDescLinea(rs.getString("DESCLINEA"));
		cm.setAnnoDelibera(rs.getBigDecimal("ANNODELIBERA"));
		cm.setCodLivGerarchico(rs.getString("CODLIVGERARCHICO"));
		cm.setCodIgrueT13T14(rs.getString("CODIGRUET13T14"));
		cm.setIdTipoLineaIntervento(rs.getBigDecimal("IDTIPOLINEAINTERVENTO"));
		cm.setIdLineaDiIntervento(rs.getBigDecimal("IDLINEADIINTERVENTO"));
		cm.setIdLineaDiInterventoPadre(rs.getBigDecimal("IDLINEADIINTERVENTOPADRE"));
		cm.setIdProcesso(rs.getBigDecimal("IDPROCESSO"));
		cm.setIdPropostaCertificaz(rs.getBigDecimal("IDPROPOSTACERTIFICAZ"));
		cm.setNumDelibera(rs.getBigDecimal("NUMDELIBERA"));
		cm.setFlagCampionRilev(rs.getString("FLAGCAMPIONRILEV"));
		cm.setIdTipoStrumentoProgr(rs.getBigDecimal("IDTIPOSTRUMENTOPROGR"));
		cm.setDtInizioValidita(rs.getDate("DTINIZIOVALIDITA"));
		cm.setDescBreveLinea(rs.getString("DESCBREVELINEA"));
		cm.setDtFineValidita(rs.getDate("DTFINEVALIDITA"));
		cm.setIdStrumentoAttuativo(rs.getBigDecimal("IDSTRUMENTOATTUATIVO"));
		return cm;
	}

}
