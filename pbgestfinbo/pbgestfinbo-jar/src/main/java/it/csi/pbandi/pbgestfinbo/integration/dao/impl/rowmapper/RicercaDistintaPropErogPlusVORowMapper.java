/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.integration.dao.impl.rowmapper;

import it.csi.pbandi.pbgestfinbo.integration.vo.RicercaDistintaPropErogPlusVO;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RicercaDistintaPropErogPlusVORowMapper implements RowMapper<RicercaDistintaPropErogPlusVO> {
    @Override
    public RicercaDistintaPropErogPlusVO mapRow(ResultSet rs, int rowNum) throws SQLException {
        RicercaDistintaPropErogPlusVO ricercaDistintaPropErogPlusVO = new RicercaDistintaPropErogPlusVO();

        ricercaDistintaPropErogPlusVO.setIdDistinta(rs.getLong("idDistinta"));
        ricercaDistintaPropErogPlusVO.setDescrizioneDistinta(rs.getString("descrizioneDistinta"));
        ricercaDistintaPropErogPlusVO.setIdTipoDistinta(rs.getLong("idTipoDistinta"));
        ricercaDistintaPropErogPlusVO.setDescTipoDistinta(rs.getString("descTipoDistinta"));
        ricercaDistintaPropErogPlusVO.setDescBreveTipoDistinta(rs.getString("descBreveTipoDistinta"));
        ricercaDistintaPropErogPlusVO.setUtenteCreazione(rs.getString("utenteCreazione"));
        ricercaDistintaPropErogPlusVO.setDataCreazioneDistinta(rs.getDate("dataCreazioneDistinta"));
        ricercaDistintaPropErogPlusVO.setDescStatoDistinta(rs.getString("descStatoDistinta"));
        ricercaDistintaPropErogPlusVO.setIdPropostaErogazione(rs.getLong("idPropostaErogazione"));
        ricercaDistintaPropErogPlusVO.setIdProgetto(rs.getLong("idProgetto"));
        ricercaDistintaPropErogPlusVO.setCodiceVisualizzato(rs.getString("codiceVisualizzato"));
        ricercaDistintaPropErogPlusVO.setCodiceFondoFinpis(rs.getString("codiceFondoFinpis"));
        ricercaDistintaPropErogPlusVO.setIdSoggetto(rs.getLong("idSoggetto"));
        ricercaDistintaPropErogPlusVO.setDenominazione(rs.getString("denominazione"));
        ricercaDistintaPropErogPlusVO.setCodiceFiscaleSoggetto(rs.getString("codiceFiscaleSoggetto"));
        ricercaDistintaPropErogPlusVO.setIdSede(rs.getLong("idSede"));
        ricercaDistintaPropErogPlusVO.setPartitaIva(rs.getString("partitaIva"));
        ricercaDistintaPropErogPlusVO.setProgrSoggettoProgetto(rs.getLong("progrSoggettoProgetto"));
        ricercaDistintaPropErogPlusVO.setIdEstremiBancari(rs.getLong("idEstremiBancari"));
        ricercaDistintaPropErogPlusVO.setIban(rs.getString("iban"));
        ricercaDistintaPropErogPlusVO.setImportoLordo(rs.getBigDecimal("importoLordo"));
        ricercaDistintaPropErogPlusVO.setImportoNetto(rs.getBigDecimal("importoNetto"));
        ricercaDistintaPropErogPlusVO.setDataContabileErogazione(rs.getDate("dataContabileErogazione"));
        ricercaDistintaPropErogPlusVO.setImportoErogato(rs.getBigDecimal("importoErogato"));

        return ricercaDistintaPropErogPlusVO;
    }
}
