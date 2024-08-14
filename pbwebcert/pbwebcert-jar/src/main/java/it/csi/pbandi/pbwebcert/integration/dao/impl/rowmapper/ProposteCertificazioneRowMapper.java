/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebcert.integration.dao.impl.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.csi.pbandi.pbwebcert.integration.vo.PropostaCertificazioneVO;

public class ProposteCertificazioneRowMapper  implements RowMapper<PropostaCertificazioneVO> {

	@Override
	public PropostaCertificazioneVO mapRow(ResultSet rs, int rowNum) throws SQLException {
		PropostaCertificazioneVO cm = new PropostaCertificazioneVO();
		
		cm.setIdPropostaCertificaz(rs.getBigDecimal("ID_PROPOSTA_CERTIFICAZ"));
		cm.setDtOraCreazione(rs.getDate("DT_ORA_CREAZIONE"));
		cm.setDescProposta(rs.getString("DESC_PROPOSTA"));
		cm.setDtCutOffPagamenti(rs.getDate("DT_CUT_OFF_PAGAMENTI"));
		cm.setDtCutOffValidazioni(rs.getDate("DT_CUT_OFF_VALIDAZIONI"));
		cm.setDtCutOffErogazioni(rs.getDate("DT_CUT_OFF_EROGAZIONI"));
		cm.setDtCutOffFideiussioni(rs.getDate("DT_CUT_OFF_FIDEIUSSIONI"));
		cm.setIdStatoPropostaCertif(rs.getBigDecimal("ID_STATO_PROPOSTA_CERTIF"));
		cm.setIdUtenteIns(rs.getBigDecimal("ID_UTENTE_INS"));
		cm.setIdUtenteAgg(rs.getBigDecimal("ID_UTENTE_AGG"));
		cm.setIdPropostaPrec(rs.getBigDecimal("ID_PROPOSTA_PREC"));
		cm.setDescBreveStatoPropostaCert(rs.getString("DESC_BREVE_STATO_PROPOSTA_CERT"));
		cm.setDescStatoPropostaCertif(rs.getString("DESC_STATO_PROPOSTA_CERTIF"));	
		cm.setIdLineaDiIntervento(rs.getBigDecimal("ID_LINEA_DI_INTERVENTO"));
		cm.setIdDocumentoIndex(rs.getBigDecimal("ID_DOCUMENTO_INDEX"));
		cm.setNomeDocumento(rs.getString("NOME_DOCUMENTO"));
		//non mapatto DT_DOMANDA_PAGAMENTO, ID_PERIODO, ID_PROP_CERT_RIF_AC, DOMANDA_PAGAMENTO, 
		return cm;
	}

}
