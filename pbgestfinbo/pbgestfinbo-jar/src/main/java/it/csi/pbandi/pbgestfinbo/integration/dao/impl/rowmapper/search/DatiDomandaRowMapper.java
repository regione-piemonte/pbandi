/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.integration.dao.impl.rowmapper.search;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.csi.pbandi.pbgestfinbo.integration.vo.search.AnagraficaBeneficiarioVO;
import it.csi.pbandi.pbgestfinbo.integration.vo.search.DatiDomandaVO;

public class DatiDomandaRowMapper implements RowMapper<DatiDomandaVO>{

	@Override
	public DatiDomandaVO mapRow(ResultSet rs, int rowNum) throws SQLException {
		DatiDomandaVO cm = new DatiDomandaVO();
		//STATO DOMANDA
		cm.setNumeroDomanda(rs.getString("NUMERO_DOMANDA"));
		cm.setStatoDomanda(rs.getString("DESC_STATO_DOMANDA"));
		cm.setDataPresentazioneDomanda(rs.getString("DT_PRESENTAZIONE_DOMANDA"));
		//DOCUMENTO IDENTITA'
		cm.setDocumentoIdentita(rs.getString("DESC_TIPO_DOCUMENTO"));
		cm.setNumeroDocumento(rs.getString("NUMERO_DOCUMENTO"));
		cm.setDataRilascio(rs.getString("DT_RILASCIO_DOCUMENTO"));
		cm.setEnteRilascio(rs.getString("DOCUMENTO_RILASCIATO_DA"));
		cm.setScadenzaDocumento(rs.getString("DT_SCADENZA_DOCUMENTO"));
		cm.setIdTipoDocumentoIdentita(rs.getLong("ID_TIPO_DOCUMENTO"));
		//RECAPITI
		cm.setTelefono(rs.getString("TELEFONO"));
		cm.setFax(rs.getString("FAX"));
		cm.setEmail(rs.getString("EMAIL"));
		cm.setPec(rs.getString("PEC"));
		//CONTO CORRENTE
		cm.setNumeroConto(rs.getString("NUMERO_CONTO"));
		cm.setIban(rs.getString("IBAN"));
		//BANCA DI APPOGGIO
		cm.setBanca(rs.getString("DESC_BANCA"));
		cm.setAbi(rs.getString("ABI"));
		cm.setCab(rs.getString("CAB"));
		cm.setIdBanca(rs.getLong("id_banca"));
		return cm;
	}

}
