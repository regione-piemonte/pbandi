/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservwelfare.integration.dao.impl;

import it.csi.pbandi.pbservwelfare.dto.VociDiSpesa;
import it.csi.pbandi.pbservwelfare.exception.ErroreGestitoException;
import it.csi.pbandi.pbservwelfare.integration.dao.BaseDAO;
import it.csi.pbandi.pbservwelfare.integration.dao.TrasmissioneVociDiSpesaDAO;
import it.csi.pbandi.pbservwelfare.util.Constants;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

import java.sql.Types;
import java.util.List;

public class TrasmissioneVociDiSpesaDAOImpl extends BaseDAO implements TrasmissioneVociDiSpesaDAO {

    private final Logger LOG;

    public TrasmissioneVociDiSpesaDAOImpl() {
        LOG = Logger.getLogger(Constants.COMPONENT_NAME);
    }

    @Override
    public List<VociDiSpesa> getVociDiSpesa(String numeroDomanda) {
        String prf = "[TrasmissioneVociDiSpesaDAOImpl::getVociDiSpesa]";
        LOG.info(prf + " BEGIN");
        LOG.info(prf + ", numeroDomanda=" + numeroDomanda);

        List<VociDiSpesa> vociDiSpesa;

        try {
            String sql = "SELECT \n" +
                    "pdvds.ID_VOCE_DI_SPESA,\n" +
                    "pdvds.DESC_VOCE_DI_SPESA,\n" +
                    "pdvds.COD_TIPO_VOCE_DI_SPESA,\n" +
                    "prbli.ID_BANDO\n" +
                    "FROM PBANDI_D_VOCE_DI_SPESA pdvds\n" +
                    "JOIN PBANDI_R_BANDO_VOCE_SPESA prbvs ON prbvs.ID_VOCE_DI_SPESA = pdvds.ID_VOCE_DI_SPESA \n" +
                    "JOIN PBANDI_R_BANDO_LINEA_INTERVENT prbli ON prbli.ID_BANDO = prbvs.ID_BANDO \n" +
                    "JOIN PBANDI_T_DOMANDA ptd ON ptd.PROGR_BANDO_LINEA_INTERVENTO = prbli.PROGR_BANDO_LINEA_INTERVENTO\n" +
                    "WHERE ptd.NUMERO_DOMANDA = :numeroDomanda";

            LOG.debug(sql);

            MapSqlParameterSource param = new MapSqlParameterSource();
            param.addValue("numeroDomanda", numeroDomanda, Types.VARCHAR);

            vociDiSpesa = this.namedParameterJdbcTemplate.query(sql, param, ((rs, rowNum) -> {
                VociDiSpesa dto = new VociDiSpesa();

                dto.setIdVoceDiSpesa(rs.getString("ID_VOCE_DI_SPESA"));
                dto.setDescrizioneVoceDiSpesa(rs.getString("DESC_VOCE_DI_SPESA"));
                dto.setCodTipoVoceDiSpesa(rs.getString("COD_TIPO_VOCE_DI_SPESA"));
                dto.setCodiceBando(rs.getString("ID_BANDO"));

                return dto;
            }));

        } catch (DataAccessException ex) {
            throw ex;
        } catch (Exception e) {
            LOG.error(prf + "Exception while trying to getVociDiSpesa", e);
            throw new ErroreGestitoException("DaoException while trying to getVociDiSpesa", e);
        } finally {
            LOG.info(prf + " END");
        }

        return vociDiSpesa;
    }
}
