/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.integration.dao.impl.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

import org.springframework.jdbc.core.RowMapper;

import it.csi.pbandi.pbgestfinbo.integration.vo.IscrizioneRuoloVO;

public class IscrizioneRuoloVORowMapper implements RowMapper<IscrizioneRuoloVO> {

	@Override
	public IscrizioneRuoloVO mapRow(ResultSet rs, int rowNum) throws SQLException {
		IscrizioneRuoloVO bc = new IscrizioneRuoloVO();
		
		bc.setIdProgetto(rs.getString("ID_PROGETTO"));
		
		bc.setIdCurrentRecord(rs.getString("ID_ISCRIZIONE_RUOLO"));
		bc.setIdModalitaAgevolazione(rs.getLong("ID_MODALITA_AGEVOLAZIONE"));
		bc.setDataRichiestaIscrizione(rs.getDate("DT_RICHIESTA_ISCRIZIONE"));
		bc.setNumProtocollo(rs.getString("NUM_PROTOCOLLO"));
		bc.setDataRichiestaDiscarico(rs.getDate("DT_RICHIESTA_DISCARICO"));
		bc.setNumProtocolloDiscarico(rs.getString("NUM_PROTOCOLLO_DISCARICO"));
		bc.setDataIscrizioneRuolo(rs.getDate("DT_ISCRIZIONE_RUOLO"));
		bc.setDataDiscarico(rs.getDate("DT_DISCARICO"));
		bc.setNumProtoDiscReg(rs.getString("NUM_PROTOCOLLO_DISCAR_REG"));
		bc.setDataRichiestaSospensione(rs.getDate("DT_RICHIESTA_SOSP"));
		bc.setNumProtoSosp(rs.getString("NUM_PROTOCOLLO_SOSP"));
		bc.setCapitaleRuolo(rs.getString("IMP_CAPITALE_RUOLO"));
		bc.setAgevolazioneRuolo(rs.getString("IMP_AGEVOLAZ_RUOLO"));
		bc.setDataIscrizione(rs.getDate("DT_ISCRIZIONE"));
		bc.setNumProtoReg(rs.getString("NUM_PROTOCOLLO_REGIONE"));
		
		String tipoPagamentoTemp = rs.getString("TIPO_PAGAMENTO");
		if("I".equals(tipoPagamentoTemp)) {
			bc.setTipoPagamento("Integrale");
		} else if ("R".equals(tipoPagamentoTemp)) {
			bc.setTipoPagamento("Rateizzato");
		} else {
			bc.setTipoPagamento("-");
		} 
		
		bc.setNote(rs.getString("NOTE"));
		
		bc.setStor_dataInizio(rs.getDate("DT_INIZIO_VALIDITA"));
		bc.setStor_dataFine(rs.getDate("DT_FINE_VALIDITA"));
		bc.setStor_dataInserimento(rs.getDate("DT_INSERIMENTO"));
		bc.setIstruttore(rs.getString("ISTRUTTORE"));
		
		return bc;
	}

}
