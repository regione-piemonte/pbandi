/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.integration.dao.impl.rowmapper.search;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.csi.pbandi.pbgestfinbo.integration.vo.search.VisualizzaStoricoPFVO;
import it.csi.pbandi.pbgestfinbo.integration.vo.search.VisualizzaStoricoVO;

public class VisualizzaStoricoPFRowMapper implements RowMapper<VisualizzaStoricoPFVO>{

	@Override
	public VisualizzaStoricoPFVO mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		VisualizzaStoricoPFVO cm = new VisualizzaStoricoPFVO();
		
		cm.setCognome(rs.getString("COGNOME"));
		cm.setNome(rs.getString("NOME"));
		cm.setTipoSoggetto(rs.getString("DESC_TIPO_SOGGETTO"));
		cm.setCodiceFiscale(rs.getString("CODICE_FISCALE_SOGGETTO"));
		cm.setDataDiNascita(rs.getString("DT_NASCITA"));
		cm.setComuneDiNascita(rs.getString("DESC_COMUNE"));
		cm.setProvinciaDiNascita(rs.getString("SIGLA_PROVINCIA"));
		cm.setRegioneDiNascita(rs.getString("DESC_REGIONE"));
		cm.setNazioneDiNascita(rs.getString("DESC_NAZIONE"));
		cm.setIndirizzoPF(rs.getString("DESC_INDIRIZZO_P"));
		//Dalla query non viene presa la desc del comune di residenza.
		cm.setComunePF(rs.getString("ID_COMUNE_P"));
		cm.setProvinciaPF(rs.getString("SIGLA_PROVINCIA_P"));
		cm.setCapPF(rs.getString("CAP_P"));
		cm.setRegionePF(rs.getString("DESC_REGIONE_P"));
		cm.setNazionePF(rs.getString("DESC_NAZIONE_P"));
		
		return cm;
	}

}
