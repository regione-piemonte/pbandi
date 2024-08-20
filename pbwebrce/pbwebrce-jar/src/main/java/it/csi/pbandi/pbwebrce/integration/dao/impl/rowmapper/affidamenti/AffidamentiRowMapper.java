/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebrce.integration.dao.impl.rowmapper.affidamenti;

import java.sql.ResultSet;
import java.sql.SQLException;


import org.springframework.jdbc.core.RowMapper;

import it.csi.pbandi.pbwebrce.integration.vo.affidamenti.AffidamentoVO;

public class AffidamentiRowMapper implements RowMapper<AffidamentoVO> {

	@Override
	public AffidamentoVO mapRow(ResultSet rs, int rowNum) throws SQLException {

		AffidamentoVO cm = new AffidamentoVO();
		cm.setImportoContratto(rs.getBigDecimal("IMPORTOCONTRATTO"));
		cm.setIdTipoAffidamento(rs.getBigDecimal("IDTIPOAFFIDAMENTO"));
		cm.setOggettoAppalto(rs.getString("OGGETTOAPPALTO"));
		cm.setDtGuri(rs.getDate("DTGURI"));
		cm.setImpresaAppaltatrice(rs.getString("IMPRESAAPPALTATRICE"));
		cm.setIdTipologiaAppalto(rs.getBigDecimal("IDTIPOLOGIAAPPALTO"));
		cm.setDtFirmaContratto(rs.getDate("DTFIRMACONTRATTO"));
		cm.setPercRibassoAsta(rs.getBigDecimal("PERCRIBASSOASTA"));
		cm.setDtConsegnaLavori(rs.getDate("DTCONSEGNALAVORI"));
		cm.setIdUtenteAgg(rs.getBigDecimal("IDUTENTEAGG"));
		cm.setDtWebStazAppaltante(rs.getDate("DTWEBSTAZAPPALTANTE"));
		cm.setDtWebOsservatorio(rs.getDate("DTWEBOSSERVATORIO"));
		cm.setDtInserimento(rs.getDate("DTINSERIMENTO"));
		cm.setImpRibassoAsta(rs.getBigDecimal("IMPRIBASSOASTA"));
		cm.setIdStatoAffidamento(rs.getBigDecimal("IDSTATOAFFIDAMENTO"));
		cm.setBilancioPreventivo(rs.getBigDecimal("BILANCIOPREVENTIVO"));
		cm.setImpRendicontabile(rs.getBigDecimal("IMPRENDICONTABILE"));
		cm.setDtAggiornamento(rs.getDate("DTAGGIORNAMENTO"));
		cm.setIdProceduraAggiudicaz(rs.getBigDecimal("IDPROCEDURAAGGIUDICAZ"));
		cm.setInterventoPisu(rs.getString("INTERVENTOPISU"));
		cm.setIdNorma(rs.getBigDecimal("IDNORMA"));

		cm.setIdUtenteIns(rs.getBigDecimal("IDUTENTEINS"));
		cm.setIdTipoPercettore(rs.getBigDecimal("IDTIPOPERCETTORE"));
		cm.setDtInizioPrevista(rs.getDate("DTINIZIOPREVISTA"));
		cm.setDtQuotNazionali(rs.getDate("DTQUOTNAZIONALI"));
		cm.setDtGuue(rs.getDate("DTGUUE"));
		cm.setIdAppalto(rs.getBigDecimal("IDAPPALTO"));
		cm.setSopraSoglia(rs.getString("SOPRASOGLIA"));
		return cm;
	}



}
