/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.integration.dao.impl.rowmapper.search;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.csi.pbandi.pbgestfinbo.integration.vo.search.VisualizzaStoricoVO;

public class VisualizzaStoricoRowMapper implements RowMapper<VisualizzaStoricoVO>{

	@Override
	public VisualizzaStoricoVO mapRow(ResultSet rs, int rowNum) throws SQLException {
		// TODO Auto-generated method stub
		
		VisualizzaStoricoVO cm = new VisualizzaStoricoVO();
		
		cm.setRagioneSoc(rs.getString("DENOMINAZIONE_ENTE_GIURIDICO"));
		cm.setFormaGiuridica(rs.getString("DESC_FORMA_GIURIDICA"));
		cm.setFlagPubblicoPrivato(rs.getLong("FLAG_PUBBLICO_PRIVATO"));
		cm.setCodiceUniIpa(rs.getString("COD_UNI_IPA"));
		cm.setCodiceFiscale(rs.getString("CODICE_FISCALE_SOGGETTO"));
		cm.setDataCostituzione(rs.getDate("DT_COSTITUZIONE"));
		cm.setPec(rs.getString("PEC"));
		cm.setIndirizzo(rs.getString("DESC_INDIRIZZO"));
		cm.setPartitaIva(rs.getString("PARTITA_IVA"));
		cm.setComune(rs.getString("DESC_COMUNE"));
		cm.setProvincia(rs.getString("SIGLA_PROVINCIA"));
		cm.setCap(rs.getString("CAP"));
		cm.setRegione(rs.getString("DESC_REGIONE"));
		cm.setNazione(rs.getString("DESC_NAZIONE"));
		cm.setCodiceAteco(rs.getString("COD_ATECO"));
		cm.setDescAttivitaPrevalente(rs.getString("DESC_ATECO"));
		cm.setRatingDiLegalita(rs.getString("FLAG_RATING_LEGALITA"));
		cm.setStatoAttivita(rs.getString("DESC_STATO_ATTIVITA"));
		cm.setDataInizioAttivita(rs.getDate("DT_INIZIO_ATTIVITA_ESITO"));
		cm.setPeriodoChiusuraEsercizio(rs.getString("PERIODO_SCADENZA_ESERCIZIO"));
		cm.setDataChiusuraEsercizio(rs.getDate("DT_ULTIMO_ESERCIZIO_CHIUSO"));
		cm.setNumeroRea(rs.getString("NUM_ISCRIZIONE"));
		cm.setDataIscrizione(rs.getDate("DT_ISCRIZIONE"));
		cm.setProvinciaIscrizione(rs.getString("SIGLA_PROVINCIA"));
		cm.setSezioneDiAppartenenza(rs.getString("DESC_SEZIONE_SPECIALE"));
		
		return cm;
	}

}
