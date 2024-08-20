/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.integration.dao.impl.rowmapper;

import it.csi.pbandi.pbgestfinbo.integration.vo.revoche.ProcedimentoRevocaVO;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ProcedimentoRevocaVORowMapper implements RowMapper<ProcedimentoRevocaVO> {
    @Override
    public ProcedimentoRevocaVO mapRow(ResultSet rs, int rowNum) throws SQLException {
        ProcedimentoRevocaVO procedimentoRevocaVO = new ProcedimentoRevocaVO();

        procedimentoRevocaVO.setIdProcedimentoRevoca(rs.getLong("idProcedimentoRevoca"));
        procedimentoRevocaVO.setNumeroProcedimentoRevoca(rs.getLong("numeroProcedimentoRevoca"));

        procedimentoRevocaVO.setIdSoggetto(rs.getLong("idSoggetto"));
        if(rs.wasNull()) procedimentoRevocaVO.setIdSoggetto(null);
        procedimentoRevocaVO.setCodiceFiscaleSoggetto(rs.getString("codiceFiscaleSoggetto"));
        procedimentoRevocaVO.setIdBeneficiario(rs.getLong("idBeneficiario"));
        if(rs.wasNull()) procedimentoRevocaVO.setIdBeneficiario(null);
        procedimentoRevocaVO.setDenominazioneBeneficiario(rs.getString("denominazioneBeneficiario"));

        procedimentoRevocaVO.setIdDomanda(rs.getLong("idDomanda"));
        if(rs.wasNull()) procedimentoRevocaVO.setIdDomanda(null);
        procedimentoRevocaVO.setProgrBandoLineaIntervento(rs.getLong("progrBandoLineaIntervento"));
        if(rs.wasNull()) procedimentoRevocaVO.setProgrBandoLineaIntervento(null);
        procedimentoRevocaVO.setNomeBandoLinea(rs.getString("nomeBandoLinea"));

        procedimentoRevocaVO.setIdProgetto(rs.getLong("idProgetto"));
        if(rs.wasNull()) procedimentoRevocaVO.setIdProgetto(null);
        procedimentoRevocaVO.setTitoloProgetto(rs.getString("titoloProgetto"));
        procedimentoRevocaVO.setCodiceVisualizzatoProgetto(rs.getString("codiceVisualizzatoProgetto"));

        procedimentoRevocaVO.setIdCausaleBlocco(rs.getLong("idCausaleBlocco"));
        if(rs.wasNull()) procedimentoRevocaVO.setIdCausaleBlocco(null);
        procedimentoRevocaVO.setDescCausaleBlocco(rs.getString("descCausaleBlocco"));

        procedimentoRevocaVO.setIdAutoritaControllante(rs.getLong("idAutoritaControllante"));
        if(rs.wasNull()) procedimentoRevocaVO.setIdAutoritaControllante(null);
        procedimentoRevocaVO.setDescAutoritaControllante(rs.getString("descAutoritaControllante"));

        procedimentoRevocaVO.setIdStatoRevoca(rs.getLong("idStatoRevoca"));
        if(rs.wasNull()) procedimentoRevocaVO.setIdStatoRevoca(null);
        procedimentoRevocaVO.setDescStatoRevoca(rs.getString("descStatoRevoca"));

        procedimentoRevocaVO.setDataStatoRevoca(rs.getDate("dataStatoRevoca"));

        procedimentoRevocaVO.setIdAttivitaRevoca(rs.getLong("idAttivitaRevoca"));
        if(rs.wasNull()) procedimentoRevocaVO.setIdAttivitaRevoca(null);
        procedimentoRevocaVO.setDescAttivitaRevoca(rs.getString("descAttivitaRevoca"));

        procedimentoRevocaVO.setDataAttivitaRevoca(rs.getDate("dataAttivitaRevoca"));

        procedimentoRevocaVO.setDataNotifica(rs.getDate("dataNotifica"));

        procedimentoRevocaVO.setGiorniScadenza(rs.getLong("giorniScadenza"));
        if(rs.wasNull()) procedimentoRevocaVO.setGiorniScadenza(null);
        procedimentoRevocaVO.setDataScadenza(rs.getDate("dataScadenza"));

        String proroga = rs.getString("proroga");
        procedimentoRevocaVO.setProroga(proroga != null && proroga.equals("S"));

        procedimentoRevocaVO.setNote(rs.getString("note"));

        procedimentoRevocaVO.setIdSoggettoIstruttore(rs.getLong("idSoggettoIstruttore"));
        if(rs.wasNull()) procedimentoRevocaVO.setIdSoggettoIstruttore(null);
        procedimentoRevocaVO.setDenominazioneIstruttore(rs.getString("denominazioneIstruttore"));

        procedimentoRevocaVO.setNumeroProtocollo(rs.getLong("numeroProtocollo"));
        if(rs.wasNull()) procedimentoRevocaVO.setNumeroProtocollo(null);
        procedimentoRevocaVO.setDataAvvioProcedimento(rs.getDate("dataAvvioProcedimentoRevoca"));

        //calcolati esternamente
//        procedimentoRevocaVO.setModalitaAgevolazioneContributo(rs.getString("modalitaAgevContributo"));
//        procedimentoRevocaVO.setModalitaAgevolazioneFinanziamento(rs.getString("modalitaAgevFinanziamento"));
//        procedimentoRevocaVO.setModalitaAgevolazioneGaranzia(rs.getString("modalitaAgevGaranzia"));
//        procedimentoRevocaVO.setImportoConcessoContributo(rs.getBigDecimal("importoConcessoContributo"));
//        procedimentoRevocaVO.setImportoConcessoFinanziamento(rs.getBigDecimal("importoConcessoFinanziamento"));
//        procedimentoRevocaVO.setImportoConcessoGaranzia(rs.getBigDecimal("importoConcessoGaranzia"));
//        procedimentoRevocaVO.setImportoRevocatoContributo(rs.getBigDecimal("importoRevocatoContributo"));
//        procedimentoRevocaVO.setImportoRevocatoFinanziamento(rs.getBigDecimal("importoRevocatoFinanziamento"));
//        procedimentoRevocaVO.setImportoRevocatoGaranzia(rs.getBigDecimal("importoRevocatoGaranzia"));

        procedimentoRevocaVO.setImportoDaRevocareContributo(rs.getBigDecimal("importoDaRevocareContributo"));
        procedimentoRevocaVO.setImportoDaRevocareFinanziamento(rs.getBigDecimal("importoDaRevocareFinanziamento"));
        procedimentoRevocaVO.setImportoDaRevocareGaranzia(rs.getBigDecimal("importoDaRevocareGaranzia"));

        //valori per invia incarico ad erogazione
        procedimentoRevocaVO.setImpIres(rs.getBigDecimal("impIres"));
        procedimentoRevocaVO.setImpDaErogareContributo(rs.getBigDecimal("impDaErogareContributo"));
        procedimentoRevocaVO.setCausaleErogazioneContributo(rs.getString("causaleErogazioneContributo"));
        procedimentoRevocaVO.setImpDaErogareFinanziamento(rs.getBigDecimal("impDaErogareFinanziamento"));
        procedimentoRevocaVO.setCausaleErogazioneFinanziamento(rs.getString("causaleErogazioneFinanziamento"));
        procedimentoRevocaVO.setIdDichiarazioneSpesa(rs.getLong("idDichiarazioneSpesa"));
        if(rs.wasNull()) procedimentoRevocaVO.setIdDichiarazioneSpesa(null);

        return procedimentoRevocaVO;
    }
}
