/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.integration.dao.impl.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.csi.pbandi.pbgestfinbo.integration.vo.SchedaClienteMainVO;

public class SchedaClienteMainVORowMapper implements RowMapper<SchedaClienteMainVO> {

	@Override
	public SchedaClienteMainVO mapRow(ResultSet rs, int rowNum) throws SQLException {
		SchedaClienteMainVO bc = new SchedaClienteMainVO();
		String banca = null; 
		
		banca = rs.getString("DESC_BANCA");
		if(banca != null) {
			// questo collegato alla tabella PBANDI_R_SOGG_PROG_BANCA_BEN
			bc.setBanca(banca);
			bc.setBanBen_idBanca(rs.getBigDecimal("ID_BANCA"));
		} else {
			// questo gestisce la nuova logica fra le tabelle PBANDI_T_ESTREMI_BANCARI e pbandi_t_agenzia
			bc.setBanca(rs.getString("desc_banca2"));
			bc.setBanBen_idBanca(rs.getBigDecimal("id_banca2"));
		}
		bc.setDeliberatoBanca(rs.getString("deliberato_banca"));
		bc.setConfidi(rs.getString("CONFIDI"));
		bc.setAltreGaranzie(rs.getString("GARANZIE"));
		
		bc.setIdSoggetto(rs.getBigDecimal("ID_SOGGETTO"));
		bc.setIdProgetto(rs.getBigDecimal("ID_PROGETTO"));
		bc.setProgrSoggProg(rs.getBigDecimal("PROGR_SOGGETTO_PROGETTO"));
		bc.setStaAz_currentId(rs.getBigDecimal("STAAZ_CURRENTID"));
		bc.setStaAz_idStatoAzienda(rs.getBigDecimal("STAAZ_IDSTATOAZIENDA"));
		bc.setStaAz_dtInizioValidita(rs.getDate("STAAZ_DTINIZIOVALIDITA"));
		bc.setStaAz_dtfineValidita(rs.getDate("STAAZ_DTFINEVALIDITA"));
		bc.setStaCre_currentId(rs.getBigDecimal("STACRE_CURRENTID"));
		bc.setStaCre_idStatoCredito(rs.getBigDecimal("stacre_idstatocredito"));
		bc.setRating_currentId(rs.getBigDecimal("SORAT_CURRENTID"));
		bc.setBando(rs.getString("BANDO"));
		bc.setProgetto(rs.getString("PROGETTO"));
		bc.setDenominazEnteGiu(rs.getString("DENOMINAZIONE_ENTE_GIURIDICO"));
		bc.setDenominazPerFis(rs.getString("DENOMINAZIONE_PERSONA_FISICA"));
		bc.setCodiceFiscale(rs.getString("CODICE_FISCALE_SOGGETTO"));
		bc.setPartitaIva(rs.getString("PARTITA_IVA"));
		bc.setFormaGiuridica(rs.getString("DESC_FORMA_GIURIDICA"));
		bc.setTipoSoggetto(rs.getString("DESC_TIPO_SOGGETTO"));
		bc.setStatoAzienda(rs.getString("DESC_STATO_AZIENDA"));
		bc.setStatoCredito(rs.getString("DESC_STATO_CREDITO_FP"));
		bc.setRating(rs.getString("RATING"));
		bc.setProvider(rs.getString("PROVIDER"));
		bc.setDataClassRating(rs.getDate("DATA_CLASSIFICAZIONE_RATING"));
		bc.setClasseRischio(rs.getString("CLASSE_RISCHIO"));
		bc.setDataClassRischio(rs.getDate("DATA_CLASSIFICAZIONE_RISCHIO"));
		bc.setBanBen_currentId(rs.getBigDecimal("banben_currentid"));
		
		return bc;
	}

}
