/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.integration.dao.impl;

import it.csi.pbandi.pbgestfinbo.integration.dao.CreazioneFascicoliFPDao;
import it.csi.pbandi.pbgestfinbo.integration.vo.MetadatiActaVO;
import it.csi.pbandi.pbgestfinbo.util.Constants;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Service
public class CreazioneFascicoliFPDaoImpl extends JdbcDaoSupport implements CreazioneFascicoliFPDao {
    private final Logger LOG = Logger.getLogger(Constants.COMPONENT_NAME);

    @Autowired
    public CreazioneFascicoliFPDaoImpl(DataSource dataSource) {
        setDataSource(dataSource);
    }

    @Override
    public List<MetadatiActaVO> getMetadatiActaList(Integer progrBandoLineaInterveno){
        String prf = "[CreazioneFascicoliFPDaoImpl::getMetadatiActaList] ";
        LOG.info(prf + " BEGIN");

        List<MetadatiActaVO> metadatiActaVOList;

        try{
            metadatiActaVOList = getJdbcTemplate().query(
                "SELECT \n" +
                    "0 AS idTipoMetadati,\n" +
                    "prbli.ID_BANDO AS idBando,\n" +
                    "ptd.ID_DOMANDA AS idDomanda,\n" +
                    "prbli.ID_BANDO AS oggettoFascicolo,\n" +
                    "CASE\n" +
                    "WHEN pteg.ID_ENTE_GIURIDICO IS NOT NULL THEN pteg.DENOMINAZIONE_ENTE_GIURIDICO \n" +
                    "WHEN ptpf.ID_PERSONA_FISICA IS NOT NULL THEN ptpf.COGNOME || ' ' || ptpf.NOME \n" +
                    "ELSE '' END AS soggettoFascicolo,\n" +
                    "12 AS conservazioneGeneraleFascicolo,\n" +
                    "'' AS dataTopicaDocumento\n" +
                    "FROM PBANDI_R_BANDO_LINEA_INTERVENT prbli \n" +
                    "JOIN PBANDI_T_DOMANDA ptd ON ptd.PROGR_BANDO_LINEA_INTERVENTO = prbli.PROGR_BANDO_LINEA_INTERVENTO\n" +
                    "JOIN PBANDI_R_SOGGETTO_DOMANDA prsd ON prsd.ID_DOMANDA = ptd.ID_DOMANDA \n" +
                    "LEFT JOIN PBANDI_T_ENTE_GIURIDICO pteg on pteg.ID_ENTE_GIURIDICO = prsd.ID_ENTE_GIURIDICO\n" +
                    "LEFT JOIN PBANDI_T_PERSONA_FISICA ptpf on ptpf.ID_PERSONA_FISICA = prsd.ID_PERSONA_FISICA \n" +
                    "WHERE prbli.PROGR_BANDO_LINEA_INTERVENTO = ?\n" +
                    "AND prsd.ID_TIPO_BENEFICIARIO <> 4\n" +
                    "AND prsd.ID_TIPO_ANAGRAFICA = 1\n" +
                    "ORDER BY ptd.ID_DOMANDA ASC",
                ps -> ps.setInt(1, progrBandoLineaInterveno),
                (rs, rowNum) -> new MetadatiActaVO(
                    rs.getInt("idTipoMetadati"), //non serve
                    rs.getString("idBando"),
                    rs.getString("idDomanda"),
                    rs.getString("oggettoFascicolo"), //uguale all'idDomanda
                    rs.getString("soggettoFascicolo"), //beneficiario
                    rs.getInt("conservazioneGeneraleFascicolo"),  // N.B. deve essere compreso  tra 1 e 99 e >= cons cor
                    rs.getString("dataTopicaDocumento") //non serve
                ));
        }catch (Exception e){
            LOG.error(prf + " Error while trying to getMetadatiActaList: " + e);
            metadatiActaVOList = new ArrayList<>();
        }finally {
            LOG.info(prf + " END");
        }

        return metadatiActaVOList;
    }
}
