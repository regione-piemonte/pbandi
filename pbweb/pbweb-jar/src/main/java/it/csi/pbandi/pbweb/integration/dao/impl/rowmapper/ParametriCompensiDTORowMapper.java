/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweb.integration.dao.impl.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.csi.pbandi.pbweb.dto.ParametroCompensiDTO;
import it.csi.pbandi.pbweb.integration.vo.TipoDocumentiSpesaVO;

public class ParametriCompensiDTORowMapper implements RowMapper<ParametroCompensiDTO> {

	@Override
	public ParametroCompensiDTO mapRow(ResultSet rs, int rowNum) throws SQLException {

		ParametroCompensiDTO cm = new ParametroCompensiDTO();

		cm.setIdParametroCompenso(rs.getLong("ID_PARAMETRO_COMPENSO"));
		cm.setCategoria(rs.getLong("CATEGORIA"));
		cm.setOreSettimanali(rs.getLong("ORE_SETTIMANALI"));
		cm.setCompensoDovutoMensile(rs.getDouble("COMPENSO_DOVUTO_MENSILE"));
		cm.setGiorniLavorabiliSettimanali(rs.getLong("GIORNI_LAVORABILI_SETTIMANALI"));
		cm.setOrarioMedioGiornaliero(rs.getDouble("ORARIO_MEDIO_GIORNALIERO"));
		cm.setBudgetInizialeTirocinante(rs.getDouble("BUDGET_INIZIALE_TIROCINANTE"));
		cm.setBudgetInizialeImpresa(rs.getDouble("BUDGET_INIZIALE_IMPRESA"));

		return cm;
	}
}
