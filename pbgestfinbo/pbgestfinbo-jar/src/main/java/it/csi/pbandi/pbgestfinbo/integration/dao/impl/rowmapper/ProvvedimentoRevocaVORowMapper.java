/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.integration.dao.impl.rowmapper;

import it.csi.pbandi.pbgestfinbo.integration.vo.revoche.ProvvedimentoRevocaVO;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ProvvedimentoRevocaVORowMapper implements RowMapper<ProvvedimentoRevocaVO> {
    @Override
    public ProvvedimentoRevocaVO mapRow(ResultSet rs, int rowNum) throws SQLException {
        ProvvedimentoRevocaVO provvedimentoRevocaVO = new ProvvedimentoRevocaVO();

        provvedimentoRevocaVO.setIdProvvedimentoRevoca(rs.getLong("idProvvedimentoRevoca"));
        provvedimentoRevocaVO.setNumeroProvvedimentoRevoca(rs.getLong("numeroProvvedimentoRevoca"));
        provvedimentoRevocaVO.setIdSoggetto(rs.getLong("idSoggetto"));
        provvedimentoRevocaVO.setNdg(rs.getString("ndg"));
        provvedimentoRevocaVO.setCodiceFiscaleSoggetto(rs.getString("codiceFiscaleSoggetto"));
        provvedimentoRevocaVO.setIdBeneficiario(rs.getLong("idBeneficiario"));
        provvedimentoRevocaVO.setDenominazioneBeneficiario(rs.getString("denominazioneBeneficiario"));
        provvedimentoRevocaVO.setIdDomanda(rs.getLong("idDomanda"));
        provvedimentoRevocaVO.setNumeroDomanda(rs.getString("numeroDomanda"));
        provvedimentoRevocaVO.setIdBando(rs.getLong("idBando"));
        provvedimentoRevocaVO.setProgrBandoLineaIntervento(rs.getLong("progrBandoLineaIntervento"));
        provvedimentoRevocaVO.setNomeBandoLinea(rs.getString("nomeBandoLinea"));
        provvedimentoRevocaVO.setIdProgetto(rs.getLong("idProgetto"));
        provvedimentoRevocaVO.setTitoloProgetto(rs.getString("titoloProgetto"));
        provvedimentoRevocaVO.setCodiceVisualizzatoProgetto(rs.getString("codiceVisualizzatoProgetto"));
        provvedimentoRevocaVO.setNumeroProtocollo(rs.getLong("numeroProtocollo"));
        if(rs.wasNull()) provvedimentoRevocaVO.setNumeroProtocollo(null);
        provvedimentoRevocaVO.setDataAvvioProvvedimentoRevoca(rs.getDate("dataAvvioProvvedimentoRevoca"));
        if(rs.wasNull()) provvedimentoRevocaVO.setDataAvvioProvvedimentoRevoca(null);
        String flag = rs.getString("flagDetermina");
        provvedimentoRevocaVO.setFlagDetermina(flag != null && flag.equals("S"));
        provvedimentoRevocaVO.setDtDetermina(rs.getDate("dtDetermina"));
        if(rs.wasNull()) provvedimentoRevocaVO.setDtDetermina(null);
        provvedimentoRevocaVO.setEstremi(rs.getString("estremi"));
        flag = rs.getString("flagOrdineRecupero");
        provvedimentoRevocaVO.setFlagOrdineRecupero(flag != null && flag.equals("S"));
        provvedimentoRevocaVO.setIdMancatoRecupero(rs.getLong("idMancatoRecupero"));
        if(rs.wasNull()) provvedimentoRevocaVO.setIdMancatoRecupero(null);
        provvedimentoRevocaVO.setDescMancatoRecupero(rs.getString("descMancatoRecupero"));
        provvedimentoRevocaVO.setIdMotivoRevoca(rs.getLong("idMotivoRevoca"));
        if(rs.wasNull()) provvedimentoRevocaVO.setIdMotivoRevoca(null);
        provvedimentoRevocaVO.setDescMotivoRevoca(rs.getString("descMotivoRevoca"));
        provvedimentoRevocaVO.setIdCausaleBlocco(rs.getLong("idCausaleBlocco"));
        if(rs.wasNull()) provvedimentoRevocaVO.setIdCausaleBlocco(null);
        provvedimentoRevocaVO.setDescCausaleBlocco(rs.getString("descCausaleBlocco"));
        provvedimentoRevocaVO.setIdAutoritaControllante(rs.getLong("idAutoritaControllante"));
        if(rs.wasNull()) provvedimentoRevocaVO.setIdAutoritaControllante(null);
        provvedimentoRevocaVO.setDescAutoritaControllante(rs.getString("descAutoritaControllante"));
        provvedimentoRevocaVO.setIdStatoRevoca(rs.getLong("idStatoRevoca"));
        if(rs.wasNull()) provvedimentoRevocaVO.setIdStatoRevoca(null);
        provvedimentoRevocaVO.setDescStatoRevoca(rs.getString("descStatoRevoca"));
        provvedimentoRevocaVO.setDataStatoRevoca(rs.getDate("dataStatoRevoca"));
        provvedimentoRevocaVO.setIdAttivitaRevoca(rs.getLong("idAttivitaRevoca"));
        if(rs.wasNull()) provvedimentoRevocaVO.setIdAttivitaRevoca(null);
        provvedimentoRevocaVO.setDescAttivitaRevoca(rs.getString("descAttivitaRevoca"));
        provvedimentoRevocaVO.setDataAttivitaRevoca(rs.getDate("dataAttivitaRevoca"));
        provvedimentoRevocaVO.setDataNotifica(rs.getDate("dataNotifica"));
        if(rs.wasNull()) provvedimentoRevocaVO.setDataNotifica(null);
        provvedimentoRevocaVO.setGiorniScadenza(rs.getLong("giorniScadenza"));
        if(rs.wasNull()) provvedimentoRevocaVO.setGiorniScadenza(null);
        provvedimentoRevocaVO.setDataScadenza(rs.getDate("dataScadenza"));
        if(rs.wasNull()) provvedimentoRevocaVO.setDataScadenza(null);
        flag = rs.getString("flagContribRevoca");
        provvedimentoRevocaVO.setFlagContribRevoca(flag != null && flag.equals("S"));
        flag = rs.getString("flagContribMinorSpese");
        provvedimentoRevocaVO.setFlagContribMinorSpese(flag != null && flag.equals("S"));
        flag = rs.getString("flagContribDecurtaz");
        provvedimentoRevocaVO.setFlagContribDecurtaz(flag != null && flag.equals("S"));
        flag = rs.getString("flagFinanzRevoca");
        provvedimentoRevocaVO.setFlagFinanzRevoca(flag != null && flag.equals("S"));
        flag = rs.getString("flagFinanzMinorSpese");
        provvedimentoRevocaVO.setFlagFinanzMinorSpese(flag != null && flag.equals("S"));
        flag = rs.getString("flagFinanzDecurtaz");
        provvedimentoRevocaVO.setFlagFinanzDecurtaz(flag != null && flag.equals("S"));
        flag = rs.getString("flagGaranziaRevoca");
        provvedimentoRevocaVO.setFlagGaranziaRevoca(flag != null && flag.equals("S"));
        flag = rs.getString("flagGaranziaMinorSpese");
        provvedimentoRevocaVO.setFlagGaranziaMinorSpese(flag != null && flag.equals("S"));
        flag = rs.getString("flagGaranziaDecurtaz");
        provvedimentoRevocaVO.setFlagGaranziaDecurtaz(flag != null && flag.equals("S"));
        provvedimentoRevocaVO.setNumeroCovar(rs.getBigDecimal("covar"));

        provvedimentoRevocaVO.setIdModAgevContrib(rs.getLong("idModAgevContrib"));
        provvedimentoRevocaVO.setIdModAgevContribRif(rs.getLong("idModAgevContribRif"));
        provvedimentoRevocaVO.setIdModAgevFinanz(rs.getLong("idModAgevFinanz"));
        provvedimentoRevocaVO.setIdModAgevFinanzRif(rs.getLong("idModAgevFinanzRif"));
        provvedimentoRevocaVO.setIdModAgevGaranz(rs.getLong("idModAgevGaranz"));
        provvedimentoRevocaVO.setIdModAgevGaranzRif(rs.getLong("idModAgevGaranzRif"));

        provvedimentoRevocaVO.setModAgevContrib(rs.getString("modAgevContrib"));
        provvedimentoRevocaVO.setModAgevFinanz(rs.getString("modAgevFinanz"));
        provvedimentoRevocaVO.setModAgevGaranz(rs.getString("modAgevGaranz"));

        provvedimentoRevocaVO.setImportoAmmessoContributo(rs.getBigDecimal("importoAmmessoContributo"));
        if(rs.wasNull()) provvedimentoRevocaVO.setImportoAmmessoContributo(null);
        provvedimentoRevocaVO.setImportoConcessoContributo(rs.getBigDecimal("importoConcessoContributo"));
        if(rs.wasNull()) provvedimentoRevocaVO.setImportoConcessoContributo(null);
        provvedimentoRevocaVO.setImportoAmmessoFinanziamento(rs.getBigDecimal("importoAmmessoFinanziamento"));
        if(rs.wasNull()) provvedimentoRevocaVO.setImportoAmmessoFinanziamento(null);
        provvedimentoRevocaVO.setImportoConcessoFinanziamento(rs.getBigDecimal("importoConcessoFinanziamento"));
        if(rs.wasNull()) provvedimentoRevocaVO.setImportoConcessoFinanziamento(null);
        provvedimentoRevocaVO.setImportoAmmessoGaranzia(rs.getBigDecimal("importoAmmessoGaranzia"));
        if(rs.wasNull()) provvedimentoRevocaVO.setImportoAmmessoGaranzia(null);
        provvedimentoRevocaVO.setImportoConcessoGaranzia(rs.getBigDecimal("importoConcessoGaranzia"));
        if(rs.wasNull()) provvedimentoRevocaVO.setImportoConcessoGaranzia(null);
        provvedimentoRevocaVO.setImportoRevocatoContributo(rs.getBigDecimal("importoRevocatoContributo"));
        if(rs.wasNull()) provvedimentoRevocaVO.setImportoRevocatoContributo(null);
        provvedimentoRevocaVO.setImportoRevocatoFinanziamento(rs.getBigDecimal("importoRevocatoFinanziamento"));
        if(rs.wasNull()) provvedimentoRevocaVO.setImportoRevocatoFinanziamento(null);
        provvedimentoRevocaVO.setImportoRevocatoGaranzia(rs.getBigDecimal("importoRevocatoGaranzia"));
        if(rs.wasNull()) provvedimentoRevocaVO.setImportoRevocatoGaranzia(null);

        provvedimentoRevocaVO.setImpContribRevocaNoRecu(rs.getBigDecimal("impContribRevocaNoRecu"));
        if(rs.wasNull()) provvedimentoRevocaVO.setImpContribRevocaNoRecu(null);
        provvedimentoRevocaVO.setImpContribRevocaRecu(rs.getBigDecimal("impContribRevocaRecu"));
        if(rs.wasNull()) provvedimentoRevocaVO.setImpContribRevocaRecu(null);
        provvedimentoRevocaVO.setImpContribInteressi(rs.getBigDecimal("impContribInteressi"));
        if(rs.wasNull()) provvedimentoRevocaVO.setImpContribInteressi(null);
        provvedimentoRevocaVO.setImpFinanzRevocaNoRecu(rs.getBigDecimal("impFinanzRevocaNoRecu"));
        if(rs.wasNull()) provvedimentoRevocaVO.setImpFinanzRevocaNoRecu(null);
        provvedimentoRevocaVO.setImpFinanzRevocaRecu(rs.getBigDecimal("impFinanzRevocaRecu"));
        if(rs.wasNull()) provvedimentoRevocaVO.setImpFinanzRevocaRecu(null);
        provvedimentoRevocaVO.setImpFinanzInteressi(rs.getBigDecimal("impFinanzInteressi"));
        if(rs.wasNull()) provvedimentoRevocaVO.setImpFinanzInteressi(null);
        provvedimentoRevocaVO.setImpFinanzPreRecu(rs.getBigDecimal("impFinanzPreRecu"));
        if(rs.wasNull()) provvedimentoRevocaVO.setImpFinanzPreRecu(null);
        provvedimentoRevocaVO.setImpGaranziaRevocaNoRecu(rs.getBigDecimal("impGaranziaRevocaNoRecu"));
        if(rs.wasNull()) provvedimentoRevocaVO.setImpGaranziaRevocaNoRecu(null);
        provvedimentoRevocaVO.setImpGaranziaRevocaRecu(rs.getBigDecimal("impGaranziaRevocaRecu"));
        if(rs.wasNull()) provvedimentoRevocaVO.setImpGaranziaRevocaRecu(null);
        provvedimentoRevocaVO.setImpGaranziaInteressi(rs.getBigDecimal("impGaranziaInteressi"));
        if(rs.wasNull()) provvedimentoRevocaVO.setImpGaranziaInteressi(null);
        provvedimentoRevocaVO.setImpGaranzPreRecu(rs.getBigDecimal("impGaranziaPreRecu"));
        if(rs.wasNull()) provvedimentoRevocaVO.setImpGaranzPreRecu(null);
        provvedimentoRevocaVO.setNote(rs.getString("note"));
        provvedimentoRevocaVO.setIdSoggettoIstruttore(rs.getLong("idSoggettoIstruttore"));
        if(rs.wasNull()) provvedimentoRevocaVO.setIdSoggettoIstruttore(null);
        provvedimentoRevocaVO.setDenominazioneIstruttore(rs.getString("denominazioneIstruttore"));

        return provvedimentoRevocaVO;
    }
}
