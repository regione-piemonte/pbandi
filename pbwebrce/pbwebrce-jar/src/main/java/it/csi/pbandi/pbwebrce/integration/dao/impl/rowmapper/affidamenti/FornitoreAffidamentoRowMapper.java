/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebrce.integration.dao.impl.rowmapper.affidamenti;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.csi.pbandi.pbwebrce.integration.vo.affidamenti.FornitoreAffidamentoVO;

public class FornitoreAffidamentoRowMapper implements RowMapper<FornitoreAffidamentoVO> {

	@Override
	public FornitoreAffidamentoVO mapRow(ResultSet rs, int rowNum) throws SQLException {
		FornitoreAffidamentoVO cm = new FornitoreAffidamentoVO();
		cm.setCodiceFiscaleFornitore(rs.getString("CODICEFISCALEFORNITORE"));
		cm.setDescTipoPercettore(rs.getString("DESCTIPOPERCETTORE"));
		cm.setIdTipoSoggetto(rs.getBigDecimal("IDTIPOSOGGETTO"));
		cm.setDtInvioVerificaAffidamento(rs.getDate("DTINVIOVERIFICAAFFIDAMENTO"));
		cm.setPartitaIvaFornitore(rs.getString("PARTITAIVAFORNITORE"));
		cm.setIdTipoPercettore(rs.getBigDecimal("IDTIPOPERCETTORE"));
		cm.setDenominazioneFornitore(rs.getString("DENOMINAZIONEFORNITORE"));
		cm.setNomeFornitore(rs.getString("NOMEFORNITORE"));
		cm.setIdFornitore(rs.getBigDecimal("IDFORNITORE"));
		cm.setIdAppalto(rs.getBigDecimal("IDAPPALTO"));
		cm.setCognomeFornitore(rs.getString("COGNOMEFORNITORE"));
		cm.setFlgInvioVerificaAffidamento(rs.getString("FLGINVIOVERIFICAAFFIDAMENTO"));
		return cm;
	}

}
