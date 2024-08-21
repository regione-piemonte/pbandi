/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.integration.dao.impl.rowmapper;


import it.csi.pbandi.pbservizit.pbandisrv.dto.gestionebackoffice.VoceDiEntrataCulturaDTO;
import it.csi.pbandi.pbservizit.util.BeanMapper;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class VoceDiEntrataCulturaRowMapper implements RowMapper<VoceDiEntrataCulturaDTO> {
  @Override
  public VoceDiEntrataCulturaDTO mapRow(ResultSet resultSet, int i) throws SQLException {
    return (VoceDiEntrataCulturaDTO) new BeanMapper().toBean(resultSet, VoceDiEntrataCulturaDTO.class);
  }
}

/*

  VoceDiEntrataCulturaDTO voceDiEntrataCulturaDTO = new VoceDiEntrataCulturaDTO();
    voceDiEntrataCulturaDTO.setIdContoEconomico(resultSet.getLong("ID_CONTO_ECONOMICO"));
    voceDiEntrataCulturaDTO.setIdUtenteIns(resultSet.getLong("ID_UTENTE_INS"));
    voceDiEntrataCulturaDTO.setDtInizioValidita(resultSet.getString("DT_INIZIO_VALIDITA"));
    voceDiEntrataCulturaDTO.setIdDomanda(resultSet.getLong("ID_DOMANDA"));
    voceDiEntrataCulturaDTO.setIdStatoContoEconomico(resultSet.getLong("ID_STATO_CONTO_ECONOMICO"));
    voceDiEntrataCulturaDTO.setImportoRichiesto(resultSet.getBigDecimal("IMPORTO_RICHIESTO"));
    voceDiEntrataCulturaDTO.setImportoValidato(resultSet.getBigDecimal("IMPORTO_VALIDATO"));
    voceDiEntrataCulturaDTO.setImportoQuietanzato(resultSet.getBigDecimal("IMPORTO_QUIETANZATO"));
    voceDiEntrataCulturaDTO.setImportoImpegnoContabile(resultSet.getBigDecimal("IMPORTO_IMPEGNO_CONTABILE"));
    voceDiEntrataCulturaDTO.setImportoImpegnoVincolante(resultSet.getBigDecimal("IMPORTO_IMPEGNO_VINCOLANTE"));
    voceDiEntrataCulturaDTO.setImportoFinanzBancaRichiesto(resultSet.getBigDecimal("IMPORTO_FINANZ_BANCA_RICHIESTO"));
    voceDiEntrataCulturaDTO.setImportoFinanziamentoBanca(resultSet.getBigDecimal("IMPORTO_FINANZIAMENTO_BANCA"));
    voceDiEntrataCulturaDTO.setIdBando(resultSet.getLong("ID_BANDO"));
    voceDiEntrataCulturaDTO.setDescrizione(resultSet.getString("DESCRIZIONE"));
    voceDiEntrataCulturaDTO.setDescrizioneBreve(resultSet.getString("DESCRIZIONE_BREVE"));
    voceDiEntrataCulturaDTO.setIdVoceDiEntrata(resultSet.getLong("ID_VOCE_DI_ENTRATA"));
    voceDiEntrataCulturaDTO.setFlagEdit(resultSet.getString("FLAG_EDIT"));
    voceDiEntrataCulturaDTO.setIdRigoContoEconomico(resultSet.getLong("ID_RIGO_CONTO_ECONOMICO"));
    voceDiEntrataCulturaDTO.setCompletamento(resultSet.getString("COMPLETAMENTO"));
    return voceDiEntrataCulturaDTO;
 */