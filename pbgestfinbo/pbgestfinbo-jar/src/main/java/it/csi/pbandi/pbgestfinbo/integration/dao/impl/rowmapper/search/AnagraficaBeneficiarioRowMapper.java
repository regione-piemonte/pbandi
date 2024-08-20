/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.integration.dao.impl.rowmapper.search;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.csi.pbandi.pbgestfinbo.integration.vo.search.AnagraficaBeneficiarioVO;

public class AnagraficaBeneficiarioRowMapper implements RowMapper<AnagraficaBeneficiarioVO>{

	@Override
	public AnagraficaBeneficiarioVO mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		
		AnagraficaBeneficiarioVO cm = new AnagraficaBeneficiarioVO();
		
		cm.setNumIscrizione(rs.getString("NUM_ISCRIZIONE"));
		cm.setDtIscrizione(rs.getDate("DT_ISCRIZIONE"));
		cm.setDescIndirizzo(rs.getString("DESC_INDIRIZZO"));
		cm.setCap(rs.getString("CAP"));
		cm.setDescComune(rs.getString("DESC_COMUNE"));
		cm.setIdComune(rs.getLong("ID_COMUNE"));
		cm.setDescProvincia(rs.getString("DESC_PROVINCIA"));
		cm.setIdProvincia(rs.getLong("ID_PROVINCIA"));
		cm.setDescRegione(rs.getString("DESC_REGIONE"));
		cm.setIdRegione(rs.getLong("ID_REGIONE"));
		cm.setDescNazione(rs.getString("DESC_NAZIONE"));
		cm.setIdNazione(rs.getLong("ID_NAZIONE"));
		cm.setCfSoggetto(rs.getString("CODICE_FISCALE_SOGGETTO"));
		cm.setIdTipoSogg(rs.getLong("ID_TIPO_SOGGETTO"));
		cm.setCodAteco(rs.getString("COD_ATECO"));
		cm.setDescAteco(rs.getString("DESC_ATECO"));
		cm.setIdSoggetto(rs.getLong("ID_SOGGETTO"));
		cm.setRagSoc(rs.getString("DENOMINAZIONE_ENTE_GIURIDICO"));
		cm.setDtCostituzione(rs.getDate("DT_COSTITUZIONE"));
		cm.setCapSociale(rs.getLong("CAPITALE_SOCIALE"));
		cm.setIdAttIuc(rs.getLong("ID_ATTIVITA_UIC"));
		cm.setIdFormaGiuridica(rs.getLong("ID_FORMA_GIURIDICA"));
		cm.setIdClassEnte(rs.getLong("ID_CLASSIFICAZIONE_ENTE"));
		cm.setIdDimensioneImpresa(rs.getLong("ID_DIMENSIONE_IMPRESA"));
		cm.setIdEnteGiuridico(rs.getLong("ID_ENTE_GIURIDICO"));
		cm.setDtInizioVal(rs.getDate("DT_INIZIO_VALIDITA"));
		cm.setDtFineVal(rs.getDate("DT_FINE_VALIDITA"));
		cm.setDtUltimoEseChiuso(rs.getDate("DT_ULTIMO_ESERCIZIO_CHIUSO"));
		cm.setIdUtenteIns(rs.getLong("ID_UTENTE_INS"));
		cm.setIdUtenteAgg(rs.getLong("ID_UTENTE_AGG"));
		cm.setFlagPubblicoPrivato(rs.getLong("FLAG_PUBBLICO_PRIVATO"));
		cm.setCodUniIpa(rs.getString("COD_UNI_IPA"));
		cm.setIdStatoAtt(rs.getLong("ID_STATO_ATTIVITA"));
		cm.setDtValEsito(rs.getDate("DT_VALUTAZIONE_ESITO"));
		cm.setPeriodoScadEse(rs.getString("PERIODO_SCADENZA_ESERCIZIO"));
		cm.setDtInizioAttEsito(rs.getDate("DT_INIZIO_ATTIVITA_ESITO"));
		cm.setFlagRatingLeg(rs.getString("FLAG_RATING_LEGALITA"));
		cm.setDescFormaGiur(rs.getString("DESC_FORMA_GIURIDICA"));
		cm.setIdFormaGiuridica(rs.getLong("ID_FORMA_GIURIDICA"));
		cm.setpIva(rs.getString("PARTITA_IVA"));
		cm.setIdSede(rs.getLong("ID_SEDE"));
		cm.setIdAttAteco(rs.getLong("ID_ATTIVITA_ATECO"));
		cm.setDtInizioValSede(rs.getDate("DT_INIZIO_VAL_SEDE"));
		cm.setDescTipoSoggetto(rs.getString("DESC_TIPO_SOGGETTO"));
		
		
		return cm;
	}

}
