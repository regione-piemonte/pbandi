/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.integration.dao.impl.rowmapper.affidamenti;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.csi.pbandi.pbgestfinbo.integration.vo.search.AnagraficaBeneficiarioVO;

public class EnteGiuridicaRowMapper implements RowMapper<AnagraficaBeneficiarioVO> {

	@Override
	public AnagraficaBeneficiarioVO mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		AnagraficaBeneficiarioVO pg = new AnagraficaBeneficiarioVO(); 
		
		pg.setIdSoggetto(rs.getLong("ID_SOGGETTO"));
		pg.setRagSoc(rs.getString("DENOMINAZIONE_ENTE_GIURIDICO"));
		pg.setDtCostituzione(rs.getDate("DT_COSTITUZIONE"));
		pg.setCapSociale(rs.getLong("CAPITALE_SOCIALE"));
		pg.setIdAttIuc(rs.getLong("ID_ATTIVITA_UIC"));
		pg.setIdFormaGiuridica(rs.getLong("ID_FORMA_GIURIDICA"));
		pg.setIdClassEnte(rs.getLong("ID_CLASSIFICAZIONE_ENTE"));
		pg.setIdDimensioneImpresa(rs.getLong("ID_DIMENSIONE_IMPRESA"));
		pg.setIdEnteGiuridico(rs.getLong("ID_ENTE_GIURIDICO"));
		pg.setDtInizioVal(rs.getDate("DT_INIZIO_VALIDITA"));
		pg.setDtFineVal(rs.getDate("DT_FINE_VALIDITA"));
		pg.setDtUltimoEseChiuso(rs.getDate("DT_ULTIMO_ESERCIZIO_CHIUSO"));
		pg.setIdUtenteIns(rs.getLong("ID_UTENTE_INS"));
		pg.setIdUtenteAgg(rs.getLong("ID_UTENTE_AGG"));
		pg.setFlagPubblicoPrivato(rs.getLong("FLAG_PUBBLICO_PRIVATO"));
		pg.setCodUniIpa(rs.getString("COD_UNI_IPA"));
		pg.setIdStatoAtt(rs.getLong("ID_STATO_ATTIVITA"));
		pg.setDtValEsito(rs.getDate("DT_VALUTAZIONE_ESITO"));
		pg.setPeriodoScadEse(rs.getString("PERIODO_SCADENZA_ESERCIZIO"));
		pg.setDtInizioAttEsito(rs.getDate("DT_INIZIO_ATTIVITA_ESITO"));
		pg.setFlagRatingLeg(rs.getString("FLAG_RATING_LEGALITA"));
		
		return pg;
	}

}
