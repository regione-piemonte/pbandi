/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.integration.dao.impl.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.csi.pbandi.pbgestfinbo.integration.vo.affidamenti.ControlloLocoVO;

public class ControlloLocoVORowMapper implements RowMapper<ControlloLocoVO> {
	
	@Override
	public ControlloLocoVO mapRow(ResultSet rs, int rowNum) throws SQLException {
		ControlloLocoVO controllo = new ControlloLocoVO();
		
		controllo.setCodVisualizzato(rs.getString("CODICE_VISUALIZZATO"));
		String denom = rs.getString("denominazione_ente");
		if(denom!=null) {
			controllo.setIdGiuPersFis(rs.getBigDecimal("ID_ENTE_GIURIDICO"));
			controllo.setDenominazione(denom);
			controllo.setPersonaGiuridica(true);
		}else {
			controllo.setDenominazione(rs.getString("denominazione_fis"));
			controllo.setIdGiuPersFis(rs.getBigDecimal("ID_PERSONA_FISICA"));
			controllo.setPersonaGiuridica(false);
		}
		controllo.setDescAttivita(rs.getString("DESC_ATTIV_CONTR_LOCO"));
		controllo.setDescBando(rs.getString("NOME_BANDO_LINEA"));
		controllo.setDescStatoControllo(rs.getString("DESC_STATO_CONTR_LOCO"));
		controllo.setDescTipoControllo("In loco");
		controllo.setIdControllo(rs.getBigDecimal("ID_CONTROLLO_LOCO"));
		controllo.setImportoDaControllare(rs.getBigDecimal("IMP_DA_CONTROLLARE"));
		controllo.setFlagSif(rs.getString("FLAG_SIF"));
		controllo.setDataAvvioControlli(rs.getDate("DT_AVVIO_CONTROLLI"));
		controllo.setDataFineControlli(rs.getDate("DT_FINE_CONTROLLI"));
		controllo.setDataInizioControlli(rs.getDate("DT_INIZIO_CONTROLLI"));
		controllo.setDataVisitaControllo(rs.getDate("DT_VISITA_CONTROLLO"));
		controllo.setIstruttoreVisita(rs.getString("ISTRUTTORE_VISITA"));
		controllo.setImportoAgevIrreg(rs.getBigDecimal("IMP_AGEVOLAZ_IRREG"));
		controllo.setImportoDaControllare(rs.getBigDecimal("IMP_DA_CONTROLLARE"));
		controllo.setImportoIrregolarita(rs.getBigDecimal("IMP_IRREGOLARITA"));
		controllo.setIdProgetto(rs.getBigDecimal("ID_PROGETTO"));
		controllo.setDescTipoVisita(rs.getString("TIPO_VISITA"));
		controllo.setIstruttoreVisita(rs.getString("ISTRUTTORE_VISITA"));
		controllo.setDataAvvioControlli(rs.getDate("DT_AVVIO_CONTROLLI"));
		controllo.setDataFineControlli(rs.getDate("DT_FINE_CONTROLLI"));
		controllo.setDataInizioControlli(rs.getDate("DT_INIZIO_CONTROLLI"));
		controllo.setDataVisitaControllo(rs.getDate("DT_VISITA_CONTROLLO"));
		controllo.setImportoAgevIrreg(rs.getBigDecimal("IMP_AGEVOLAZ_IRREG"));
		controllo.setImportoIrregolarita(rs.getBigDecimal("IMP_IRREGOLARITA"));
		controllo.setIdStatoControllo(rs.getInt("ID_STATO_CONTR_LOCO"));
		controllo.setIdAttivitaContrLoco(rs.getInt("ID_ATTIV_CONTR_LOCO"));
		controllo.setProgrBandoLinea(rs.getBigDecimal("ID_LINEA_DI_INTERVENTO"));
		controllo.setIdSoggettoBenef(rs.getBigDecimal("id_soggetto"));
		return controllo;
	}

}
