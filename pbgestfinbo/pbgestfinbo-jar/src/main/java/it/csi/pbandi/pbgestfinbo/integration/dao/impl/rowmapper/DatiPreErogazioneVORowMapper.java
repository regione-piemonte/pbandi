/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.integration.dao.impl.rowmapper;

import it.csi.pbandi.pbgestfinbo.integration.vo.DatiPreErogazioneVO;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

public class DatiPreErogazioneVORowMapper implements RowMapper<DatiPreErogazioneVO> {
    @Override
    public DatiPreErogazioneVO mapRow(ResultSet rs, int rowNum) throws SQLException {
        DatiPreErogazioneVO datiPreErogazioneVO = new DatiPreErogazioneVO();

        datiPreErogazioneVO.setIdProposta(rs.getLong("idProposta"));
        datiPreErogazioneVO.setImportoBeneficiario(rs.getBigDecimal("importoBeneficiario"));
        datiPreErogazioneVO.setImportoLordo(rs.getBigDecimal("importoLordo"));
        datiPreErogazioneVO.setIbanBeneficiario(rs.getString("ibanBeneficiario"));
        datiPreErogazioneVO.setCodiceProgetto(rs.getString("codiceProgetto"));
        datiPreErogazioneVO.setBeneficiario(rs.getString("denominazione"));
        datiPreErogazioneVO.setTitoloBando(rs.getString("titoloBando"));
        datiPreErogazioneVO.setIdModalitaAgevolazioneRif(rs.getLong("idModalitaAgevolazioneRif"));
        datiPreErogazioneVO.setDescrizioneModalitaAgevolazione(rs.getString("descrModalitaAgevolazione"));
        datiPreErogazioneVO.setDataControlli(rs.getDate("dataControlli"));
        datiPreErogazioneVO.setFlagPubblicoPrivato(String.valueOf(rs.getLong("flagPubblicoPrivato")));
        datiPreErogazioneVO.setIdSoggetto(rs.getLong("idSoggetto"));
        datiPreErogazioneVO.setCodiceFiscale(rs.getString("codiceFiscale"));
        datiPreErogazioneVO.setNumeroDomanda(rs.getString("numeroDomanda"));
        datiPreErogazioneVO.setCodiceBando(rs.getLong("codiceBando"));
        datiPreErogazioneVO.setIdProgetto(rs.getLong("idProgetto"));
        datiPreErogazioneVO.setFlagFinistr(Objects.equals("S", rs.getString("flagFinistr")));

        return datiPreErogazioneVO;
    }
}
