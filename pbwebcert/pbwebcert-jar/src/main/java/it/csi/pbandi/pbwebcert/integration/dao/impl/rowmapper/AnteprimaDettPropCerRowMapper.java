/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebcert.integration.dao.impl.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.RowMapper;

import it.csi.pbandi.pbwebcert.integration.vo.AnteprimaDettPropCerVO;
import it.csi.pbandi.pbwebcert.util.Constants;

public class AnteprimaDettPropCerRowMapper implements RowMapper<AnteprimaDettPropCerVO> {
	private Logger LOG = Logger.getLogger(Constants.COMPONENT_NAME);
	@Override
	public AnteprimaDettPropCerVO mapRow(ResultSet rs, int rowNum) throws SQLException {
		AnteprimaDettPropCerVO cm = new AnteprimaDettPropCerVO();
		cm.setDescLinea(rs.getString("DESCLINEA"));
		cm.setCodiceProgetto(rs.getString("CODICEPROGETTO"));
		cm.setProgrBandoLineaIntervento(rs.getBigDecimal("PROGRBANDOLINEAINTERVENTO"));
		cm.setImportoPagamenti(rs.getBigDecimal("IMPORTOPAGAMENTI"));
		cm.setIdPreviewDettPropCer(rs.getBigDecimal("IDPREVIEWDETTPROPCER"));
		cm.setIdSoggettoBeneficiario(rs.getBigDecimal("IDSOGGETTOBENEFICIARIO"));
		cm.setIdPropostaCertificaz(rs.getBigDecimal("IDPROPOSTACERTIFICAZ"));
		cm.setIdUtenteIns(rs.getBigDecimal("IDUTENTEINS"));
		cm.setIdUtenteAgg(rs.getBigDecimal("IDUTENTEAGG"));
		cm.setNomeBandoLinea(rs.getString("NOMEBANDOLINEA"));
		cm.setIdProgetto(rs.getBigDecimal("IDPROGETTO"));
		if(rs.getString("FLAGATTIVO").equals("S")) cm.setFlagAttivo(true);
		else cm.setFlagAttivo(false);
		cm.setImportoRevoche(rs.getBigDecimal("IMPORTOREVOCHE"));
		cm.setDescBreveLinea(rs.getString("DESCBREVELINEA"));
		cm.setImportoCertificazioneNetto(rs.getBigDecimal("IMPORTOCERTIFICAZIONENETTO"));
		cm.setDenominazioneBeneficiario(rs.getString("DENOMINAZIONEBENEFICIARIO"));
		return cm;
	}

}
